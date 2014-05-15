package spacegame;

import spacegame.actions.*;
import websocket.Transport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Leo on 08/05/2014.
 */
public class Game {
    private static final Game instance;

    static {
        instance = new Game();
    }

    public static Game getInstance() {
        return instance;
    }

    private Map<String, Player> players;
    private SpaceSystem spaceSystem;
    private ScheduledExecutorService executorService;
    private GameEventQueue eventQueue;

    public Game() {
        players = new HashMap<String, Player>();
        spaceSystem = new SpaceSystem(1, "Slow Bob I");
        spaceSystem.addAsteroid(new Asteroid(1, 5, 23, 44));
        spaceSystem.addAsteroid(new Asteroid(2, 100, 71, 132));

        executorService = new ScheduledThreadPoolExecutor(4);
        eventQueue = new GameEventQueue();
    }

    public void onLogin(String sessionId, String username) {
        Player player = new Player(sessionId, username);
        SendMessageAction sendMessageAction = new SendMessageAction(sessionId,
                String.format("Welcome, %s!", username));
        executorService.execute(sendMessageAction);
        String formattedMessage = String.format("<i>%s has joined.</i>", username);
        BroadcastMessageAction broadcastMessageAction = new BroadcastMessageAction(
                spaceSystem.getPlayerSessions(), formattedMessage);
        executorService.execute(broadcastMessageAction);
        players.put(sessionId, player);
        spaceSystem.addPlayer(player);
    }

    public void onChatMessage(String sessionId, String message) {
        Player player = players.get(sessionId);
        String formattedMessage = String.format("<b>%s</b>: %s", player.getUsername(), message);
        BroadcastMessageAction broadcastMessageAction = new BroadcastMessageAction(
                player.getSpaceSystem().getPlayerSessions(), formattedMessage);
        executorService.execute(broadcastMessageAction);
    }

    public void onScan(String sessionId) {
        Player player = players.get(sessionId);
        StringBuilder resp = new StringBuilder();
        resp.append("Scanning system...");
        resp.append("<ul>");
        for(Asteroid asteroid : player.getSpaceSystem().scanAsteroids()) {
            resp.append(String.format("<li>Asteroid #%d [Metal] @ (%d, %d)</li>",
                    asteroid.getId(), asteroid.getPositionX(), asteroid.getPositionY()));
        }
        resp.append("</ul>");
        SendMessageAction sendMessageAction = new SendMessageAction(sessionId, resp.toString());
        executorService.execute(sendMessageAction);
    }

    public void onWarp(String sessionId, int positionX, int positionY) {
        Player player = players.get(sessionId);
        int distance = (int) Math.floor(Math.sqrt(Math.pow(positionX - player.getPositionX(), 2) +
                Math.pow(positionY - player.getPositionY(), 2)));
        int eta = Math.max((int) (distance / 10), 1);
        WarpAction warpAction = new WarpAction(player, positionX, positionY);
        new MineCancelAction(player).run();
        executorService.schedule(warpAction, eta, TimeUnit.SECONDS);
        SendMessageAction sendMessageAction = new SendMessageAction(sessionId,
                String.format("Initiating warp to (%d, %d), ETA: %d sec", positionX, positionY, eta));
        executorService.execute(sendMessageAction);
    }

    public void onMine(String sessionId, int asteroidIndex) {
        Player player = players.get(sessionId);
        Asteroid asteroid = player.getSpaceSystem().getAsteroid(asteroidIndex);
        if(Util.calculateDistance(player, asteroid) > 10) {
            SendMessageAction sendMessageAction = new SendMessageAction(sessionId, "Too far away...");
            executorService.execute(sendMessageAction);
            return;
        }
        MineAction mineAction = new MineAction(player, asteroid);
        new MineCancelAction(player).run();
        player.setMiningActivity(executorService.scheduleWithFixedDelay(mineAction, 1, 5, TimeUnit.SECONDS));
        SendMessageAction sendMessageAction = new SendMessageAction(sessionId, "Beginning mining operations...");
        executorService.execute(sendMessageAction);
    }

    public void onStatus(String sessionId) {
        Player player = players.get(sessionId);
        Ship ship = player.getShip();
        SendMessageAction sendMessageAction = new SendMessageAction(sessionId,
                String.format("You are located at (%d, %d) | Cargo: %d/%d", player.getPositionX(),
                        player.getPositionY(), ship.getCargoCurrent(), ship.getCargoMaximum()));
        executorService.execute(sendMessageAction);
    }
}

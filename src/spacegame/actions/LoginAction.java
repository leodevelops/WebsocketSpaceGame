package spacegame.actions;

import spacegame.Game;
import spacegame.GameEventQueue;
import spacegame.Player;

/**
 * Created by Leo on 14/05/2014.
 */
public class LoginAction implements Runnable {
    private String session;
    private String username;

    public LoginAction(String session, String username) {
        this.session = session;
        this.username = username;
    }

    @Override
    public void run() {
        Player player = new Player(session, username);
        SendMessageAction sendMessageAction = new SendMessageAction(session,
                String.format("Welcome, %s!", username));
        Game game = Game.getInstance();
        GameEventQueue eventQueue = game.getEventQueue();
        eventQueue.execute(sendMessageAction);
        String formattedMessage = String.format("<i>%s has joined.</i>", username);
        BroadcastMessageAction broadcastMessageAction = new BroadcastMessageAction(
                game.getSpaceSystem().getPlayerSessions(), formattedMessage);
        //executorService.execute(broadcastMessageAction);
        eventQueue.execute(broadcastMessageAction);
        game.getPlayers().put(session, player);
        game.getSpaceSystem().addPlayer(player);
    }
}

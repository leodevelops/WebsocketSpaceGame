package spacegame.actions;

import spacegame.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Leo on 14/05/2014.
 */
public class MiningInitiationAction implements Runnable {
    private String session;
    private int asteroid;

    public MiningInitiationAction(String session, int asteroid) {
        this.session = session;
        this.asteroid = asteroid;
    }

    @Override
    public void run() {
        Player player = Game.getInstance().getPlayers().get(session);
        Ship ship = player.getShip();
        Asteroid asteroid = player.getSpaceSystem().getAsteroid(this.asteroid);
        if(Util.calculateDistance(player, asteroid) > 10) {
            SendMessageAction sendMessageAction = new SendMessageAction(session, "Too far away...");
            Game.getInstance().getEventQueue().execute(sendMessageAction);
            return;
        }
        MiningProgressAction mineAction = new MiningProgressAction(player, asteroid);
        new MineCancelAction(player).run();
        ship.setMiningActivity(Game.getInstance().getEventQueue().scheduleRepeat(mineAction, 5, TimeUnit.SECONDS));
        SendMessageAction sendMessageAction = new SendMessageAction(session, "Beginning mining operations...");
        Game.getInstance().getEventQueue().execute(sendMessageAction);
    }
}

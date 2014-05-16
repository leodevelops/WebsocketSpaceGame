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
        if(GeometryUtil.getDistance(player, asteroid) > 10) {
            new SendMessageAction(session, "Too far away...").run();
            return;
        }
        if(ship.isWarping()) {
            new SendMessageAction(session, "Cannot mine while warping.").run();
            return;
        }
        MiningProgressAction mineAction = new MiningProgressAction(player, asteroid);
        new MineCancelAction(player).run();
        ship.setMiningActivity(Game.getInstance().getEventQueue().scheduleRepeat(mineAction, 5, TimeUnit.SECONDS));
        new SendMessageAction(session, "Beginning mining operations...").run();
    }
}

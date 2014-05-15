package spacegame.actions;

import spacegame.Game;
import spacegame.Player;
import spacegame.Util;

import java.util.concurrent.TimeUnit;

/**
 * Created by Leo on 14/05/2014.
 */
public class WarpDepartureAction implements Runnable {
    private String session;
    private int destinationX;
    private int destinationY;

    public WarpDepartureAction(String session, int destinationX, int destinationY) {
        this.session = session;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    @Override
    public void run() {
        Player player = Game.getInstance().getPlayers().get(session);
        int distance = Util.calculateDistance(destinationX, destinationY,
                player.getPositionX(), player.getPositionY());
        int eta = Math.max((int) (distance / 10), 1);
        WarpArrivalAction warpAction = new WarpArrivalAction(player, destinationX, destinationY);
        new MineCancelAction(player).run();
        Game.getInstance().getEventQueue().schedule(warpAction, eta, TimeUnit.SECONDS);
        SendMessageAction sendMessageAction = new SendMessageAction(session,
                String.format("Initiating warp to (%d, %d), ETA: %d sec", destinationX, destinationY, eta));
        Game.getInstance().getEventQueue().execute(sendMessageAction);
    }
}

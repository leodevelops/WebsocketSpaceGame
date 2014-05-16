package spacegame.actions;

import spacegame.Player;
import spacegame.Ship;

/**
 * Created by Leo on 10/05/2014.
 */
public class MineCancelAction implements Runnable {
    private Player player;

    public MineCancelAction(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        Ship ship = player.getShip();
        if(ship.getMiningActivity() != null) {
            ship.getMiningActivity().cancel(false);
            ship.setMiningActivity(null);
            new SendMessageAction(player.getSessionId(), "Mining operations halted.").run();
        }
    }
}

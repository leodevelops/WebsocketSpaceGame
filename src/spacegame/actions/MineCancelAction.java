package spacegame.actions;

import spacegame.Player;

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
        if(player.getMiningActivity() != null) {
            player.getMiningActivity().cancel(false);
            player.setMiningActivity(null);
            new SendMessageAction(player.getSessionId(), "Mining operations halted.").run();
        }
    }
}

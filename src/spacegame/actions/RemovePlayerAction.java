package spacegame.actions;

import spacegame.Game;
import spacegame.Player;

/**
 * Created by Leo on 16/05/2014.
 */
public class RemovePlayerAction implements Runnable {
    private String session;

    public RemovePlayerAction(String session) {
        this.session = session;
    }

    @Override
    public void run() {
        Player player = Game.getInstance().getPlayers().get(session);
        Game.getInstance().getPlayers().remove(session);
        player.getSpaceSystem().removePlayer(player);
    }
}

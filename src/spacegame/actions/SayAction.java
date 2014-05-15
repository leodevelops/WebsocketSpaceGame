package spacegame.actions;

import spacegame.Game;
import spacegame.Player;

/**
 * Created by Leo on 14/05/2014.
 */
public class SayAction implements Runnable {
    private String session;
    private String message;

    public SayAction(String session, String message) {
        this.session = session;
        this.message = message;
    }

    @Override
    public void run() {
        Player player = Game.getInstance().getPlayers().get(session);
        String formattedMessage = String.format("<b>%s</b>: %s", player.getUsername(), message);
        BroadcastMessageAction broadcastMessageAction = new BroadcastMessageAction(
                player.getSpaceSystem().getPlayerSessions(), formattedMessage);
        Game.getInstance().getEventQueue().execute(broadcastMessageAction);
    }
}

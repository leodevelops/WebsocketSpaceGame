package spacegame.actions;

import spacegame.Asteroid;
import spacegame.Game;
import spacegame.Player;

/**
 * Created by Leo on 14/05/2014.
 */
public class ScanAction implements Runnable {
    private String session;

    public ScanAction(String session) {
        this.session = session;
    }

    @Override
    public void run() {
        Player player = Game.getInstance().getPlayers().get(session);
        StringBuilder resp = new StringBuilder();
        resp.append("Scanning system...");
        resp.append("<ul>");
        for(Asteroid asteroid : player.getSpaceSystem().scanAsteroids()) {
            resp.append(String.format("<li>Asteroid #%d [Metal] @ (%d, %d)</li>",
                    asteroid.getId(), asteroid.getPositionX(), asteroid.getPositionY()));
        }
        resp.append("</ul>");
        SendMessageAction sendMessageAction = new SendMessageAction(session, resp.toString());
        Game.getInstance().getEventQueue().execute(sendMessageAction);
    }
}

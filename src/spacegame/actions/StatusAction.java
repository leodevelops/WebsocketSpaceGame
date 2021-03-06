package spacegame.actions;

import spacegame.Game;
import spacegame.Player;
import spacegame.Ship;

/**
 * Created by Leo on 14/05/2014.
 */
public class StatusAction implements Runnable {
    private String session;

    public StatusAction(String session) {
        this.session = session;
    }

    @Override
    public void run() {
        Player player = Game.getInstance().getPlayers().get(session);
        Ship ship = player.getShip();
        SendMessageAction sendMessageAction = new SendMessageAction(session,
                String.format("You are located at [%s] (%d, %d) | Cargo: %d/%d | Credits: %d",
                        player.getSpaceSystem().getName(), player.getPositionX(), player.getPositionY(),
                        ship.getCargoCurrent(), ship.getCargoMaximum(), player.getCredits()));
        Game.getInstance().getEventQueue().execute(sendMessageAction);
    }
}

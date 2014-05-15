package spacegame.actions;

import spacegame.Player;

/**
 * Created by Leo on 09/05/2014.
 */
public class WarpAction implements Runnable {
    private Player player;
    private int positionX;
    private int positionY;
    private SendMessageAction notification;

    public WarpAction(Player player, int positionX, int positionY) {
        this.player = player;
        this.positionX = positionX;
        this.positionY = positionY;
        notification = new SendMessageAction(player.getSessionId(),
                String.format("Warp to (%d, %d) complete.", positionX, positionY));
    }

    @Override
    public void run() {
        player.setPositionX(positionX);
        player.setPositionY(positionY);
        notification.run();
    }
}

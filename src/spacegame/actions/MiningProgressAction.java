package spacegame.actions;

import spacegame.Asteroid;
import spacegame.Player;
import spacegame.Ship;

/**
 * Created by Leo on 10/05/2014.
 */
public class MiningProgressAction implements Runnable {
    private Player player;
    private Asteroid asteroid;

    public MiningProgressAction(Player player, Asteroid asteroid) {
        this.player = player;
        this.asteroid = asteroid;
    }

    @Override
    public void run() {
        Ship ship = player.getShip();
        int asteroidQuantity = asteroid.getMetalQuantity();
        int cargoCurrent = ship.getCargoCurrent();
        if(cargoCurrent < ship.getCargoMaximum()) {
            if(asteroidQuantity > 0) {
                asteroid.setMetalQuantity(asteroidQuantity - 1);
                ship.setCargoCurrent(cargoCurrent + 1);
                ship.setMetalQuantity(ship.getMetalQuantity() + 1);
                new SendMessageAction(player.getSessionId(), String.format("You mined %d metal.", 1)).run();
            } else {
                new SendMessageAction(player.getSessionId(), "Asteroid has no minerals.").run();
                new MineCancelAction(player).run();
            }
        } else {
            new SendMessageAction(player.getSessionId(), "Your cargo hold is full.").run();
            new MineCancelAction(player).run();
        }
    }
}

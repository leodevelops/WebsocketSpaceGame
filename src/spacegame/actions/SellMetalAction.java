package spacegame.actions;

import spacegame.*;

/**
 * Created by Leo on 5/15/2014.
 */
public class SellMetalAction implements Runnable {
    private String session;
    private int planet;
    private int sellQuantity;

    public SellMetalAction(String session, int planet, int sellQuantity) {
        this.session = session;
        this.planet = planet;
        this.sellQuantity = sellQuantity;
    }

    @Override
    public void run() {
        Player player = Game.getInstance().getPlayers().get(session);
        Ship ship = player.getShip();
        Planet planet = player.getSpaceSystem().getPlanet(this.planet);
        if(GeometryUtil.getDistance(player, planet) > 10) {
            Game.getInstance().getEventQueue().execute(new SendMessageAction(session, "You are too far away."));
            return;
        }
        if(ship.getMetalQuantity() >= sellQuantity) {
            ship.setMetalQuantity(ship.getMetalQuantity() - sellQuantity);
            ship.setCargoCurrent(ship.getCargoCurrent() - sellQuantity);
            int sellPrice = planet.getMetalPrice();
            int profit = sellQuantity * sellPrice;
            player.setCredits(player.getCredits() + profit);
            Game.getInstance().getEventQueue().execute(new SendMessageAction(session, String.format(
                    "You sold %d metal for %d credits.", sellQuantity, profit)));
        } else {
            Game.getInstance().getEventQueue().execute(new SendMessageAction(session, "You don't have enough metal."));
        }
    }
}

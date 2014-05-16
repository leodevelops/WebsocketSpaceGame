package spacegame;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Leo on 10/05/2014.
 */
public class Ship {
    private String name;
    private int metalQuantity;
    private int cargoMaximum;
    private int cargoCurrent;
    private ScheduledFuture miningActivity;

    public Ship(String name) {
        this.name = name;
        cargoMaximum = 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMetalQuantity() {
        return metalQuantity;
    }

    public void setMetalQuantity(int metalQuantity) {
        this.metalQuantity = metalQuantity;
    }

    public int getCargoMaximum() {
        return cargoMaximum;
    }

    public void setCargoMaximum(int cargoMaximum) {
        this.cargoMaximum = cargoMaximum;
    }

    public int getCargoCurrent() {
        return cargoCurrent;
    }

    public void setCargoCurrent(int cargoCurrent) {
        this.cargoCurrent = cargoCurrent;
    }

    public ScheduledFuture getMiningActivity() {
        return miningActivity;
    }

    public void setMiningActivity(ScheduledFuture miningActivity) {
        this.miningActivity = miningActivity;
    }
}

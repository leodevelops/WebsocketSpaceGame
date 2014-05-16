package spacegame;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Leo on 08/05/2014.
 */
public class Player implements CartesianPosition {
    private String sessionId;
    private String username;
    private SpaceSystem spaceSystem;
    private int positionX;
    private int positionY;
    private Ship ship;
    private int credits;

    public Player(String sessionId, String username) {
        this.sessionId = sessionId;
        this.username = username;

        ship = new Ship("Nebuchadnezzar");
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SpaceSystem getSpaceSystem() {
        return spaceSystem;
    }

    public void setSpaceSystem(SpaceSystem spaceSystem) {
        this.spaceSystem = spaceSystem;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}

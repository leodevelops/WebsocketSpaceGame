package spacegame;

/**
 * Created by Leo on 09/05/2014.
 */
public class Asteroid implements CartesianPosition {
    private int id;
    private int metalQuantity;
    private int positionX;
    private int positionY;

    public Asteroid(int id, int metalQuantity, int positionX, int positionY) {
        this.id = id;
        this.metalQuantity = metalQuantity;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMetalQuantity() {
        return metalQuantity;
    }

    public void setMetalQuantity(int metalQuantity) {
        this.metalQuantity = metalQuantity;
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
}

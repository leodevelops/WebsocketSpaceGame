package spacegame;

/**
 * Created by Leo on 5/15/2014.
 */
public class Planet implements CartesianPosition {
    private int id;
    private int metalPrice;
    private int positionX;
    private int positionY;

    public Planet(int id, int metalPrice, int positionX, int positionY) {
        this.id = id;
        this.metalPrice = metalPrice;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    @Override
    public void setPositionX(int x) {
        this.positionX = x;
    }

    @Override
    public void setPositionY(int y) {
        this.positionY = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMetalPrice() {
        return metalPrice;
    }

    public void setMetalPrice(int metalPrice) {
        this.metalPrice = metalPrice;
    }
}

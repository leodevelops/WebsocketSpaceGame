package spacegame;

/**
 * Created by Leo on 12/05/2014.
 */
public class GeometryUtil {
    public static int getDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.floor(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }

    public static int getDistance(CartesianPosition a, CartesianPosition b) {
        return GeometryUtil.getDistance(a.getPositionX(), a.getPositionY(), b.getPositionX(), b.getPositionY());
    }
}

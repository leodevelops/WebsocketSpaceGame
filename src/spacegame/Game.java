package spacegame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leo on 08/05/2014.
 */
public class Game {
    private static final Game instance;

    static {
        instance = new Game();
    }

    public static Game getInstance() {
        return instance;
    }

    private Map<String, Player> players;
    private SpaceSystem spaceSystem;
    private GameEventQueue eventQueue;

    public Game() {
        players = new HashMap<String, Player>();
        spaceSystem = new SpaceSystem(1, "Slow Bob I");
        spaceSystem.addAsteroid(new Asteroid(1, 500, 23, 44));
        spaceSystem.addAsteroid(new Asteroid(2, 500, 71, 132));

        spaceSystem.addPlanet(new Planet(1, 1, 91, 4));
        spaceSystem.addPlanet(new Planet(2, 3, 168, 74));

        eventQueue = new GameEventQueue();
        new Thread(eventQueue).start();
    }

    public GameEventQueue getEventQueue() {
        return eventQueue;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public SpaceSystem getSpaceSystem() {
        return spaceSystem;
    }
}

package spacegame;

import java.util.*;

/**
 * Created by Leo on 09/05/2014.
 */
public class SpaceSystem {
    private int id;
    private String name;
    private Map<String, Player> players;
    private Map<Integer, Asteroid> asteroids;

    public SpaceSystem(int id, String name) {
        this.id = id;
        this.name = name;

        players = new HashMap<String, Player>();
        asteroids = new HashMap<Integer, Asteroid>();
    }

    public Collection<Asteroid> scanAsteroids() {
        return asteroids.values();
    }

    public void addAsteroid(Asteroid asteroid) {
        asteroids.put(Integer.valueOf(asteroid.getId()), asteroid);
    }

    public Asteroid getAsteroid(int asteroidId) {
        return asteroids.get(Integer.valueOf(asteroidId));
    }

    public void addPlayer(Player player) {
        players.put(player.getSessionId(), player);
        player.setSpaceSystem(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    public Collection<String> getPlayerSessions() {
        return players.keySet();
    }
}

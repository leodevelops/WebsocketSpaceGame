package spacegame.actions;

import websocket.Transport;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leo on 14/05/2014.
 */
public class BroadcastMessageAction implements Runnable {
    private List<String> sessions;
    private String message;

    public BroadcastMessageAction(Collection<String> sessions, String message) {
        this.message = message;
        this.sessions = new LinkedList<String>();
        this.sessions.addAll(sessions);
    }

    @Override
    public void run() {
        for(String session : sessions) {
            Transport.send(session, message);
        }
    }
}

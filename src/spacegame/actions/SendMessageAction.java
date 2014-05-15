package spacegame.actions;

import websocket.Transport;

/**
 * Created by Leo on 09/05/2014.
 */
public class SendMessageAction implements Runnable {
    private String sessionId;
    private String message;

    public SendMessageAction(String sessionId, String message) {
        this.sessionId = sessionId;
        this.message = message;
    }

    @Override
    public void run() {
        Transport.send(sessionId, message);
    }
}

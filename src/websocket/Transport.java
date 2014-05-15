package websocket;

import spacegame.MessageHandler;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Leo on 08/05/2014.
 */
@ServerEndpoint(value = "/game/")
public class Transport {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private static Map<String, Session> sessions;

    static {
        sessions = new HashMap<String, Session>();
    }

    public static void send(String sessionId, String message) {
        try {
            Session session = sessions.get(sessionId);
            if(session == null) {
                return;
            }
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        //logger.info("Connected: " + session.getId());
        sessions.put(session.getId(), session);
        //logger.info(String.format("%d clients connected.", sessions.size()));
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        //logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        sessions.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        MessageHandler.getInstance().handle(session.getId(), message);
    }
}

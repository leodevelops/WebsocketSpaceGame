package spacegame;

import spacegame.actions.*;

/**
 * Created by Leo on 14/05/2014.
 */
public class MessageHandler {
    private static MessageHandler instance;

    static {
        instance = new MessageHandler();
    }

    public static MessageHandler getInstance() {
        return instance;
    }

    public enum MessageType { LOGIN, SAY, SCAN, WARP, MINE, STATUS }

    public void handle(String session, String message) {
        String[] command = message.split(" ", 2);
        MessageType type = MessageType.valueOf(command[0].toUpperCase());
        switch(type) {
            case LOGIN:
                Game.getInstance().getEventQueue().execute(new LoginAction(session, command[1]));
                break;
            case SAY:
                Game.getInstance().getEventQueue().execute(new SayAction(session, command[1]));
                break;
            case SCAN:
                Game.getInstance().getEventQueue().execute(new ScanAction(session));
                break;
            case WARP:
                String[] coord = command[1].split(" ", 2);
                Game.getInstance().getEventQueue().execute(new WarpDepartureAction(session,
                        Integer.valueOf(coord[0]), Integer.valueOf(coord[1])));
                break;
            case MINE:
                Game.getInstance().getEventQueue().execute(new MiningInitiationAction(session,
                        Integer.valueOf(command[1])));
                break;
            case STATUS:
                Game.getInstance().getEventQueue().execute(new StatusAction(session));
                break;
        }
    }
}

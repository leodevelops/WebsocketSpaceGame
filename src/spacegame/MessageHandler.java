package spacegame;

import spacegame.actions.*;

import java.util.logging.Logger;

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

    public enum MessageType { NONE, HELP, LOGIN, SAY, SCAN, WARP, MINE, STATUS, SELL }

    private final Logger log = Logger.getLogger(MessageHandler.class.getName());

    public void connectionClosed(String session) {
        Game.getInstance().getEventQueue().execute(new RemovePlayerAction(session));
    }

    public void handle(String session, String message) {
        String[] command = message.split(" ", 2);
        MessageType type;
        try {
            type = MessageType.valueOf(command[0].toUpperCase());
        } catch(IllegalArgumentException e) {
            type = MessageType.NONE;
        }
        if(type != MessageType.HELP && type != MessageType.LOGIN && type != MessageType.NONE) {
            if(Game.getInstance().getPlayers().get(session) == null) {
                Game.getInstance().getEventQueue().execute(new SendMessageAction(session, "Please login first."));
                return;
            }
        }
        switch(type) {
            case HELP:
                Game.getInstance().getEventQueue().execute(new HelpAction(session));
                break;
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
            case SELL:
                String[] sellArgs = command[1].split(" ", 2);
                Game.getInstance().getEventQueue().execute(new SellMetalAction(session,
                        Integer.parseInt(sellArgs[0]), Math.abs(Integer.parseInt(sellArgs[1]))));
                break;
            default:
                log.warning(String.format("Invalid command: %s", command[0].toUpperCase()));
                Game.getInstance().getEventQueue().execute(new SendMessageAction(session, "You can't do that."));
                break;
        }
    }
}

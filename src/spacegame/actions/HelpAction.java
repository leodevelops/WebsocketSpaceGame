package spacegame.actions;

import spacegame.Game;

/**
 * Created by Leo on 5/15/2014.
 */
public class HelpAction implements Runnable {
    private String session;

    public HelpAction(String session) {
        this.session = session;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        sb.append("<li>Login - login [username]</li>");
        sb.append("<li>Scan - scan</li>");
        sb.append("<li>Warp - warp [x] [y]</li>");
        sb.append("<li>Mine - mine [asteroid]</li>");
        sb.append("<li>Chat - say [message]</li>");
        sb.append("<li>Status - status</li>");
        sb.append("<li>Sell metal - sell [planet] [quantity]</li>");
        sb.append("</ul>");
        Game.getInstance().getEventQueue().execute(new SendMessageAction(session, sb.toString()));
    }
}

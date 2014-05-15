package websocket;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.DeploymentException;
import javax.websocket.server.*;

/**
 * Created by Leo on 14/05/2014.
 */
public class WebsocketServer {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;
        if(args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(host);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler websocket = new ServletContextHandler(ServletContextHandler.SESSIONS);
        websocket.setContextPath("/ws/");

        ResourceHandler staticResource = new ResourceHandler();
        staticResource.setDirectoriesListed(false);
        staticResource.setResourceBase("./static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { websocket, staticResource });

        server.setHandler(handlers);

        ServerContainer container = WebSocketServerContainerInitializer.configureContext(websocket);
        try {
            container.addEndpoint(Transport.class);

        } catch (DeploymentException e) {
            e.printStackTrace();
        }

        try {
            server.start();
            server.dump(System.err);
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

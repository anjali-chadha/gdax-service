package com.anjalichadha.server;


import com.anjalichadha.controller.GdaxTradingController;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.security.ProtectionDomain;

public class EmbeddedServer {

    private static Logger logger= LoggerFactory.getLogger(GdaxTradingController.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9999);
        server.setConnectors(new Connector[] { connector});

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath("/");

        ProtectionDomain protectionDomain = EmbeddedServer.class
                .getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        context.setWar(location.toExternalForm());

        server.setHandler(context);
        while (true) {
            try {
                logger.info("Starting the Jetty server");
                server.start();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("Error while running the Jetty ", e);
            }
        }
        try {
            System.in.read();
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

}

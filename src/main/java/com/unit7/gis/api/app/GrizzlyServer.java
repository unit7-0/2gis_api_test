package com.unit7.gis.api.app;


import com.unit7.gis.api.rest.RestApplication;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import java.io.IOException;
import java.net.URI;

/**
 * Создание и настройка Grizzly сервера
 *
 * Created by breezzo on 15.11.15.
 */
public class GrizzlyServer {

    private static final Logger logger = LoggerFactory.getLogger(GrizzlyServer.class);

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/2gis/";

    private URI baseUri = URI.create(BASE_URI);
    private HttpServer server;

    public void start() {
        logger.info("init server with baseUri: {}", BASE_URI);

        server = GrizzlyHttpServerFactory.createHttpServer(
                baseUri, ResourceConfig.forApplicationClass(RestApplication.class));

        logger.info("Server [ {} ] was created", getServerName());

        try {
            server.start();
            logger.info("Server [ {} ] was started", getServerName());
        } catch (IOException e) {
            throw new ProcessingException(e.getMessage(), e);
        }
    }

    public void stop() {
        logger.info("Stopping server [ {} ] ...", getServerName());

        server.shutdownNow();

        logger.info("Server [ {} ] was stopped", getServerName());
    }

    private String getServerName() {
        return server.getServerConfiguration().getHttpServerName();
    }
}
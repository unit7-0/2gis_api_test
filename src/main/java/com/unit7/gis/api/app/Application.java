package com.unit7.gis.api.app;

import java.io.IOException;

/**
 * Created by breezzo on 15.11.15.
 */
public class Application {

    public static void main(String[] args) throws IOException {

        final long start = System.currentTimeMillis();

        final GrizzlyServer server = new GrizzlyServer();
        server.start();

        System.out.println("Start time " + (System.currentTimeMillis() - start) + " ms");

        System.out.println(String.format(
                "Jersey app started with WADL available at %sapplication.wadl\nHit enter to stop it...",
                GrizzlyServer.BASE_URI));


        System.in.read();

        server.stop();
    }
}

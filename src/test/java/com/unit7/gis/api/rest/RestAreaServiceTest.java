package com.unit7.gis.api.rest;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.unit7.gis.api.app.GrizzlyServer;
import com.unit7.gis.api.model.RestAreaDescription;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by breezzo on 15.11.15.
 */
public class RestAreaServiceTest {

    private static final String SERVICE_URI = "rest-area/";
    private static final String URI_STRING = GrizzlyServer.BASE_URI + SERVICE_URI;

    private static GrizzlyServer server;
    private static WebResource webResource;
    private static final Gson gson = new Gson();

    @BeforeClass
    public static void setup() {
        server = new GrizzlyServer();
        server.start();

        final Client client = Client.create();
        webResource = client.resource(URI_STRING);
    }

    @Test
    public void haveResponse() {
        String response = webResource
                .path("test")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        System.out.println(response);
    }

    @Test
    public void returnJson() {
        String response = webResource
                .path("Кинотеатр")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        // noinspection unchecked
        List<RestAreaDescription> responseList = gson.fromJson(response, List.class);

        Assert.assertNotNull(responseList);
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }
}

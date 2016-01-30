package com.crossover;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MyResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testStatic() {
        int responseMsg = target.path("web/upload.html").request().get().getStatus();
//        int responseMsg = target.path("upload.html").request().get().getStatus();
        assertEquals(200, responseMsg);

    }


    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testUpload() throws IOException {


        FileDataBodyPart filePart = new FileDataBodyPart("file",
                File.createTempFile("tmp", "test"));

//        filePart.setContentDisposition(
//                FormDataContentDisposition.name("file")
//                        .fileName("stackoverflow.png").build());


        MultiPart multipartEntity = new FormDataMultiPart()
                .bodyPart(filePart);

        int responseMsg = target.path("files/upload")
                .request()
                .post(Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA))
                .getStatus();
        assertEquals(200, responseMsg);
    }

}

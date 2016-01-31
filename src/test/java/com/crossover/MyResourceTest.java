package com.crossover;

import com.crossover.resources.JournalResources;
import com.crossover.resources.UserResources;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileInputStream;
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
        Client c = ClientBuilder.newBuilder()
                .register(MultiPart.class)
                .register(MultiPartFeature.class)
                .register(JacksonFeature.class)
                .build();


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
        int responseMsg = target.path("/upload.html").request().get().getStatus();
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

        MultiPart multipartEntity = new FormDataMultiPart()
                .bodyPart(filePart);

        int responseMsg = target.path("files/upload")
                .request()
                .post(Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA))
                .getStatus();
        assertEquals(200, responseMsg);
    }

    @Test
    public void testUsers() {
        int responseMsg = target.path("/users").request().get().getStatus();
//        int responseMsg = target.path("upload.html").request().get().getStatus();
        assertEquals(200, responseMsg);

    }

    @Test
    public void testJournals() {
        String responseMsg = target.path("/journals").request().get(String.class);
        System.out.println(responseMsg);

    }

    @Test
    public void testLogin() throws InterruptedException {
        MultivaluedMap<String, String> form  = new MultivaluedHashMap<String, String>();
        form.putSingle("email", "ani@ode.al");
        form.putSingle("password", "1234..");
        int responseMsg = target.path("users/login")
                .request()
                .post(Entity.form(form))
                .getStatus();
        assertEquals(200, responseMsg);

        testJournals();
        Thread.sleep(UserResources.SESSION_TIMEOUT);
        Thread.sleep(1000);

        testJournals();

    }


    @Test
    public void mimeType() throws IOException {
        File f = new File("/users/anlcan/Desktop/crossover.pdf");
        JournalResources.storeLocal(new FileInputStream(f));
    }
}

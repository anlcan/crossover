package com.crossover.resources;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.crossover.application.Config;
import com.crossover.model.Journal;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.UUID;

import static com.crossover.application.Config.BUCKET_NAME;


@Path("/journals")
@Produces(MediaType.APPLICATION_JSON)
public class JournalResources {

    @DELETE
    @Path("/{key}")
    public Response deleteJournal(@PathParam("key")String key){

        return Response.status(200).build();
    }

    @GET
    public Response get(){
        ArrayList<Journal> journals = new ArrayList<>();
        Journal  j = new Journal();
        j.key = "2134";
        journals.add(j);
        return Response
                .status(200)
                .entity(journals)
                .build();
    }


    @GET
    @Path("/{key}")
    public Response getJournal(@PathParam("key")String key){

        return Response.status(200).build();
    }

    /**
     * Upload a Journal
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream
            ) {

        // save the file to the server
        saveFile(fileInputStream);

        return Response.status(200).build();
    }

    // save uploaded file to a defined location on the server
    private void saveFile(InputStream uploadedInputStream) {

        Journal j = new Journal();
        j.key = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        AmazonS3 s3client = new AmazonS3Client(Config.WRITER_CREDENTIALS);
        s3client.putObject(new PutObjectRequest(BUCKET_NAME, j.key, uploadedInputStream, metadata));

    }

    public static void storeLocal(InputStream uploadedInputStream) throws IOException {

        java.nio.file.Path tmpPath = Files.createTempFile("updload" ,"suffix");

        OutputStream outpuStream = new FileOutputStream(new File(tmpPath.toString()));
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = uploadedInputStream.read(bytes)) != -1) {
            outpuStream.write(bytes, 0, read);
        }
        outpuStream.flush();
        outpuStream.close();


        File tmpFile = new File(tmpPath.toUri());
        InputStream is = new BufferedInputStream(new FileInputStream(tmpFile));
        String mimeType = URLConnection.guessContentTypeFromStream(is);
        System.out.println(mimeType);
    }
}
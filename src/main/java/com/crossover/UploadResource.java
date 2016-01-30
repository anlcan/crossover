package com.crossover;

import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;


@Path("/files")
public class UploadResource {


    /**
     * Upload a File
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

        try {

            String serverLocation = Files.createTempDirectory("file").toString();


            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}
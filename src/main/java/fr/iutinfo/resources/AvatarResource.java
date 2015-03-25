package fr.iutinfo.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.NotifLevel;
import fr.iutinfo.beans.User;
import fr.iutinfo.utils.Session;


@Path("/avatars")
@Produces(MediaType.APPLICATION_JSON)
public class AvatarResource {

	public AvatarResource() {}

	@POST
	@Path("add")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Feedback getLevel(@FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileDetail) {
		/*
		if(!Session.isLogged(cookie)) {
			return new Feedback(false, "Not logged");
		}
		
		User user = Session.getUser(cookie);*/
		
		try {
			saveToFile(uploadedInputStream, "src/main/webapp/images/avatars/" + "rfgfd.png");
		} catch (Exception e) {
			return new Feedback(false, e.getMessage());
		}
		
		return new Feedback(true, "Avatar added");
	}
	
	
	/**
	 * Sauvegarde un flux dans un fichier
	 */
	private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws Exception {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        int size = 0;
        
        out = new FileOutputStream(new File(uploadedFileLocation));
	    while ((read = uploadedInputStream.read(bytes)) != -1) {
			size += read;
			if (size > 42000)
				throw new Exception("Image trop volumineuse (> 42Ko)");
			out.write(bytes, 0, read);
	    }
	    
        out.flush();
        out.close();
	}


}
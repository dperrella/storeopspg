package pkg.servlet.ats;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
//import sun.misc.BASE64Decoder;

import org.json.simple.JSONObject;

import pkg.beans.ats.Photo;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@MultipartConfig
@WebServlet("/ats/SetPhotoWeb")
public class SetPhotoWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 9.0\\webapps\\SmartSuiteNew\\source\\images\\";
    
    private static final String URL = "http://pc.smart-control.it/SmartSuiteNew2/source/images/";
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
 
    public static String randomAlphaNumeric(int count) {
    	StringBuilder builder = new StringBuilder();
    	while (count-- != 0) {
	    	int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	    	builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	    }
    	return builder.toString();
    }
    
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPhotoWeb() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject responseJson = new JSONObject();
		String msg = "KO - USE POST";
		responseJson.put("status", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		
		
		String idItem="";
		String url="";
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		try {
			idItem = request.getParameter("idItem");
			//get the file chosen by the user
			Part filePart = request.getPart("fileToUpload");
			
			System.out.println(filePart);
			
			String ext[] = filePart.getSubmittedFileName().split("\\.");
		    String _ext = ext[ext.length-1];
		    
			//get the InputStream to store the file somewhere
		    InputStream fileInputStream = filePart.getInputStream();
		    
		    
		    
		    // write the image to a file
 			String name = randomAlphaNumeric(10)+"."+_ext;
 			url = URL+name;
 			
 			System.out.println(name);
 			System.out.println(url);
 			
 			
 			String UPLOAD_DIRECTORY2 = getServletContext().getRealPath("source"+File.separator+"images"+File.separator+name);
 			
		    
		    //for example, you can copy the uploaded file to the server
		    //note that you probably don't want to do this in real life!
		    //upload it to a file host like S3 or GCS instead
		    File fileToSave = new File(UPLOAD_DIRECTORY2);
			Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			conn=Utility.getConnData();
			
			Photo p = new Photo();
		
//			switch (type) {
//            
//            case "I": 
            	
            	p.setIdItem(Integer.parseInt(idItem));
            	p.setUrl(url);
            	p.add(conn);
            	
//            	break;
//            	
//            }
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		//response.sendRedirect(request.getContextPath() + "/source/anag/items-list.htm");
		response.sendRedirect("https://storeops.checkpointsystems.com/source/anag/items-list.htm");
	}

}

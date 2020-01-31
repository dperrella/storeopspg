package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import pkg.beans.ats.Photo;
import pkg.utility.Utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetPhoto")
public class SetPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 9.0\\webapps\\SmartSuiteNew\\source\\images\\";
    //private static final String URL = "http://pc.smart-control.it/SmartSuiteNew/source/images/";
    //private static final String UPLOAD_DIRECTORY = "/users/danieleperrella/test/";
    private static final String URL = "http://pc.smart-control.it/SmartSuiteNew/source/images/";
    
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPhoto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		
		String idItem="";
		String type="I";
		String url="";
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			responseJson.put("status", "Error: Form must has enctype=multipart/form-data.");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
            return;
        }
 
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = UPLOAD_DIRECTORY;
         
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        System.out.println("***"+uploadDir.getPath());
        
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(request));
            
            System.out.println("List lenght: " + formItems.size());
            
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                    	System.out.println("!item.isFormField()");
                        String fileName = new File(item.getName()).getName();
                        url = URL+fileName;
                        
                        System.out.println(url);
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        Path path = Paths.get(filePath);
                        
                        /*
                        Set<PosixFilePermission> perms = Files.readAttributes(path,PosixFileAttributes.class).permissions();

    	                System.out.format("Permissions before: %s%n",  PosixFilePermissions.toString(perms));

    	                perms.add(PosixFilePermission.OWNER_WRITE);
    	                perms.add(PosixFilePermission.OWNER_READ);
    	                perms.add(PosixFilePermission.OWNER_EXECUTE);
    	                perms.add(PosixFilePermission.GROUP_WRITE);
    	                perms.add(PosixFilePermission.GROUP_READ);
    	                perms.add(PosixFilePermission.GROUP_EXECUTE);
    	                perms.add(PosixFilePermission.OTHERS_WRITE);
    	                perms.add(PosixFilePermission.OTHERS_READ);
    	                perms.add(PosixFilePermission.OTHERS_EXECUTE);
    	                Files.setPosixFilePermissions(path, perms);
    	                */
                        // saves the file on disk
                        item.write(storeFile);
                        
                        System.out.println("File " + fileName + " has uploaded successfully!\n");
                    }
                    else {
                        //here...
                    	
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        
                        System.out.println(fieldname);
                        if (fieldname.equals("idItem")) {
                            //logic goes here...
                        	idItem = fieldvalue;
                        } 
                    }
                    
                }
                
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
		
		try {
			conn=Utility.getConnData();
			
			Photo p = new Photo();
		
			switch (type) {
            
            case "I": 
            	
            	p.setIdItem(Integer.parseInt(idItem));
            	p.setUrl(url);
            	p.add(conn);
            	
            	break;
            	
            }
			
			
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
		responseJson.put("status", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

package pkg.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/ImportCsv")
public class ImportCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportCsv() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private boolean isMultipart;
    private String filePath="";
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file ;
    
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	String idcus = "";
        String category = "";

    	// Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter( );
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
     
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
     
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );

        try { 
           // Parse the request to get file items.
           List fileItems = upload.parseRequest(request);
           
           // Process the uploaded file items
           Iterator i = fileItems.iterator();

           
           while ( i.hasNext () ) {
              FileItem fi = (FileItem)i.next();
              
              if ( !fi.isFormField () ) {
            	  
            	 System.out.println("1. ***"+fi.getName());
            	  
                 // Get the uploaded file parameters
                 String fieldName = fi.getFieldName();
                 String fileName = fi.getName();
                 String contentType = fi.getContentType();
                 boolean isInMemory = fi.isInMemory();
                 long sizeInBytes = fi.getSize();
              
                 // Write the file
                 if( fileName.lastIndexOf("\\") >= 0 ) {
                    file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                 } else {
                    file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                 }
                 fi.write( file ) ;
                 
                 
                 System.out.println("Saved "+file.getAbsolutePath());
                 
                 
                 
                 
                 
              }
              else {
            	  
            	  System.out.println("2. ***"+fi.getFieldName());
            	  
            	  String fieldname = fi.getFieldName();
                  String fieldvalue = fi.getString();
                  
                  if(fieldname.equals("idcus")) {
                	  idcus = fieldvalue;
                  }
                  if(fieldname.equals("category")) {
                	  category = fieldvalue;
                  }
              }
              
              //System.out.println("idcus: "+idcus+" - category: "+category+" - file: "+file.getAbsolutePath());
              
              
              
              
              
              
           }
           
           ArrayList<String> l = new ArrayList<String>();
           try  
           {  
	              //the file to be opened for reading  
	              FileInputStream fis=new FileInputStream(file);       
	              Scanner sc=new Scanner(fis);    //file to be scanned  
	              //returns true if there is another line to read  
	              while(sc.hasNextLine())  
	              {  
	            	  //System.out.println(sc.nextLine());      //returns the line that was skipped  
	            	  l.add("\""+sc.nextLine()+"\"");
	              }  
	              sc.close();     //closes the scanner  
	              
	              
	              Connection conn = Utility.getConnData();
	              Statement st=conn.createStatement();
	              
	              String sql  = "update customers_category set value = '"+l.toString()+"' "
							+ "where id_customer = "+idcus+" and category = '"+category.replaceAll("'","''")+"' ";
	              System.out.println(sql);
	              st.executeUpdate(sql);
	              st.close();
	              conn.close();
	              
           }  
           catch(IOException e)  
           {  
         	  e.printStackTrace();  
           }  
           
       } catch(Exception ex) {
    	   ex.printStackTrace();
          System.out.println(ex);
       }
    }
        
    

}

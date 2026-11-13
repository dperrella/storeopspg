package pkg.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
import pkg.utility.SGTIN96;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@MultipartConfig
@WebServlet("/rfid/SetCsv")
public class SetCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// location to store file uploaded
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
    public SetCsv() {
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
		PreparedStatement prstm = null;
		
		String iddoc="";
		String idcus="";
		String type="";
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		try {
			iddoc = request.getParameter("iddoc");
			idcus = request.getParameter("idcus");
			type = request.getParameter("type"); 
			
			//get the file chosen by the user
			Part filePart = request.getPart("fileToUpload");
			
			System.out.println(filePart);
			
			String ext[] = filePart.getSubmittedFileName().split("\\.");
		    String _ext = ext[ext.length-1];
		    
			//get the InputStream to store the file somewhere
		    InputStream fileInputStream = filePart.getInputStream();
		    
		    
		    
		    // write the image to a file
 			String name = randomAlphaNumeric(10)+"."+_ext;
 			String UPLOAD_DIRECTORY2 = getServletContext().getRealPath("rfid2"+File.separator+"csv"+File.separator+name);
 			System.out.println(UPLOAD_DIRECTORY2);
 			
		    //for example, you can copy the uploaded file to the server
		    //note that you probably don't want to do this in real life!
		    //upload it to a file host like S3 or GCS instead
		    File fileToSave = new File(UPLOAD_DIRECTORY2);
			Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			conn=Utility.getConnRfid();
			conn.setAutoCommit(false);
			
			
			FileInputStream fstream = new FileInputStream(UPLOAD_DIRECTORY2);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			
			if(type.equals("EAN")) {
				String sql = "insert into documents_detail "
						+ "(sku, iddoc) "
						+ "values"
						+ "(?, ?) ";
				prstm = conn.prepareStatement(sql);
				
				//Read File Line By Line
				while ((strLine = br.readLine()) != null)   {
					// Print the content on the console
					try {
						
						String sku = strLine.split(";")[0].trim().length()!=13 ? getSku(conn,strLine.split(";")[0], idcus) : strLine.split(";")[0];
						for(int i=0;i<Integer.parseInt(strLine.split(";")[1]);i++) {
							
							prstm.setString(1, sku);
							prstm.setInt(2, Integer.parseInt(iddoc));
							prstm.addBatch();
							
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
					//System.out.println (strLine);
				}
	
				int [] numUpdates=prstm.executeBatch();
				
				conn.commit();
				
			}
			else if(type.equals("EPC")) {
				String sql = "insert into documents_detail "
						+ "(sku, epc, iddoc) "
						+ "values"
						+ "(?, ?, ?) ";
				prstm = conn.prepareStatement(sql);
				
				//Read File Line By Line
				while ((strLine = br.readLine()) != null)   {
					// Print the content on the console
					try {
						String epc = strLine.split(";")[0].trim();
						String sku = SGTIN96.decodeEpc(epc);
						prstm.setString(1, sku);
						prstm.setString(2, epc);
						prstm.setInt(3, Integer.parseInt(iddoc));
						prstm.addBatch();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
					//System.out.println (strLine);
				}
	
				int [] numUpdates=prstm.executeBatch();
				
				conn.commit();
			}
			else if(type.equals("REF")) {
				String sql = "insert into documents_detail "
						+ "(sku, iddoc, package_id) "
						+ "values"
						+ "(?, ?, ?) ";
				prstm = conn.prepareStatement(sql);
				
				//Read File Line By Line
				while ((strLine = br.readLine()) != null)   {
					// Print the content on the console
					try {
						
						String sku = strLine.split(";")[0].trim().length()!=13 ? getSku(conn,strLine.split(";")[0], idcus) : strLine.split(";")[0];
						String packageId = "";
						try {
							packageId = strLine.split(";")[2].trim();
						}
						catch(Exception err) {}
						for(int i=0;i<Integer.parseInt(strLine.split(";")[1]);i++) {
							
							prstm.setString(1, sku);
							prstm.setInt(2, Integer.parseInt(iddoc));
							prstm.setString(3, packageId);
							prstm.addBatch();
							
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
					//System.out.println (strLine);
				}
	
				int [] numUpdates=prstm.executeBatch();
				
				conn.commit();
				
			}
			
			//Close the input stream
			fstream.close();
			
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
		response.sendRedirect(request.getContextPath() + "/rfid2/docs/documents-create.htm?iddoc="+iddoc);
		
	}
	
	private String getSku(Connection conn, String reference, String idcus) {
		
		String sku = "";
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			String sql = "select sku from products where id_customer = "+idcus+" and reference = '"+reference+"' ";
			rs = st.executeQuery(sql);
			System.out.println(sql);
			if(rs.next()) {
				sku = rs.getString("sku");
			}
			else {
				sku = reference.trim();
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return sku;
		
	}

}

package pkg.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
//import sun.misc.BASE64Decoder;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;


import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@MultipartConfig
@WebServlet("/rfid/SetXlsx")
public class SetXlsx extends HttpServlet {
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
    public SetXlsx() {
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
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		try {
			iddoc = request.getParameter("iddoc");
			idcus = request.getParameter("idcus");
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
			
			Integer idsite = getIdsiteFromDoc(conn, Integer.parseInt(iddoc));
			
			
			String sql = "insert into documents_detail "
					+ "(sku, iddoc, location_id) "
					+ "values"
					+ "(?, ?, ?) ";
			prstm = conn.prepareStatement(sql);
			
			File file = new File(UPLOAD_DIRECTORY2);   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
			while (itr.hasNext())                 
			{  
				Row row = itr.next();  
				
				try {
					System.out.println(row.getCell(1).getStringCellValue()+" "+row.getCell(10).getNumericCellValue());
					
					String sku = row.getCell(1).getStringCellValue().trim().length()!=13 ? getSku(conn,row.getCell(1).getStringCellValue().trim(), idcus) : row.getCell(1).getStringCellValue();
					for(int i=0;i<(int) row.getCell(10).getNumericCellValue();i++) {
						
						prstm.setString(1, sku);
						prstm.setInt(2, Integer.parseInt(iddoc));
						prstm.setInt(3, getLocation(conn, idsite, row.getCell(3).getStringCellValue().trim()+" ", idcus));
						prstm.addBatch();
						
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println("");  
			}  
			

			int [] numUpdates=prstm.executeBatch();
			System.out.println(numUpdates+" inserted.");
			
			conn.commit();
			
			//Close the input stream
			fis.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERR: "+e.getMessage());
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
			rs = st.executeQuery("select sku from products where id_customer = "+idcus+" and reference = '"+reference+"' ");
			if(rs.next()) {
				sku = rs.getString("sku");
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return sku;
		
	}
	
	private Integer getLocation(Connection conn, Integer idsite, String warehouse, String idcus) {
		
		Integer locationId = -1;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			Integer idlocationType = 108;
			
			if(warehouse.length()>=5)
				warehouse = warehouse.substring(5,6);
			
			
			switch(warehouse) {
			case "":
				idlocationType = 108;
				break;
			case "2":
				idlocationType = 110;	
				break;
			case "3":
				idlocationType = 115;
				break;
			case "4":
				idlocationType = 116;
				break;
			case "5":
				idlocationType = 202;
				break;
			default:
				idlocationType = 108;
				break;
			}
			
			st = conn.createStatement();
			rs = st.executeQuery("select id from location where idsite = "+idsite+" and id_locationtype = "+idlocationType);
			if(rs.next()) {
				locationId = rs.getInt("id");
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return locationId;
		
	}
	
	private Integer getIdsiteFromDoc(Connection conn, Integer iddoc) {
		Integer idsite = -1;
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select idsite from documents where id = "+iddoc);
			if(rs.next()) {
				idsite = rs.getInt("idsite");
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return idsite;
	}

}

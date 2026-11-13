package pkg.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
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
@WebServlet("/rfid/SetXlsxCocama_BKP20220928")
public class SetXlsxCocama_BKP20220928 extends HttpServlet {
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
    public SetXlsxCocama_BKP20220928() {
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
		
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		try {
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
			
			String sql = "";
			
			
			sql = "delete from impcoca ";
			
			Statement st = conn.createStatement();
			st.executeUpdate(sql);
			st.close();
			
			sql = "insert into impcoca "
					+ "(marca, famiglia, gruppo, sottogruppo, stagione, id_dorig, cd_ar, descrizione, ds_vr1, ds_vr2, qtadorig, barcode, alias, cd_do, numerodoc, numerodocrif, datadoc, cd_cf, rag_soc, cd_mg_p, magazzinopartenza, cd_mg_a, magazzinoarrivo, notepiede, dataconsegna, qta, qtares) "
					+ "values"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			prstm = conn.prepareStatement(sql);
			
			File file = new File(UPLOAD_DIRECTORY2);   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
			
			Integer num = 0;
			while (itr.hasNext())                 
			{  
				Row row = itr.next();  
				
				if(num>0) {
				
					try {
						//System.out.println(row.getCell(1).getStringCellValue()+" "+row.getCell(10).getNumericCellValue());
						//for(int i=0;i<(int) row.getCell(10).getNumericCellValue();i++) {
							
							prstm.setString(1, row.getCell(0).getStringCellValue());
							prstm.setString(2, row.getCell(1).getStringCellValue());
							prstm.setString(3, row.getCell(2).getStringCellValue());
							prstm.setString(4, row.getCell(3).getStringCellValue());
							prstm.setString(5, row.getCell(4).getStringCellValue());
							prstm.setString(6, ""+row.getCell(5).getNumericCellValue());
							prstm.setString(7, row.getCell(6).getStringCellValue());
							prstm.setString(8, row.getCell(7).getStringCellValue());
							prstm.setString(9, row.getCell(8).getStringCellValue());
							prstm.setString(10, row.getCell(9).getStringCellValue());
							prstm.setString(11, ""+row.getCell(10).getNumericCellValue());
							prstm.setString(12, row.getCell(11).getStringCellValue());
							prstm.setString(13, row.getCell(12).getStringCellValue());
							prstm.setString(14, row.getCell(13).getStringCellValue());
							prstm.setString(15, row.getCell(14).getStringCellValue());
							prstm.setString(16, row.getCell(15)==null?"":row.getCell(15).getStringCellValue());
							
							prstm.setDate(17, new java.sql.Date(row.getCell(16).getDateCellValue().getTime()));
							
							prstm.setString(18, row.getCell(17).getStringCellValue());
							prstm.setString(19, row.getCell(18).getStringCellValue());
							prstm.setString(20, row.getCell(19)==null?"":row.getCell(19).getStringCellValue());
							try {
								prstm.setString(21, row.getCell(20).getStringCellValue());
							}
							catch(Exception e) {
								prstm.setString(21, "");
							}
							try {
								prstm.setString(22, row.getCell(21).getStringCellValue());
							}
							catch(Exception e) {
								prstm.setString(22, "");
							}
							try {
								prstm.setString(23, row.getCell(22).getStringCellValue());
							}
							catch(Exception e) {
								prstm.setString(23, "");
							}
							try {
								prstm.setString(24, row.getCell(23).getStringCellValue());
							}
							catch(Exception e) {
								prstm.setString(24, "");
							}
							if(row.getCell(24)==null) {
								prstm.setNull(25,java.sql.Types.DATE);
							}
							else {
								try {
									
									System.out.println(row.getCell(24).getDateCellValue().getTime());
									prstm.setDate(25, new java.sql.Date(row.getCell(24).getDateCellValue().getTime())); 
								}
								catch(Exception err) {
									prstm.setNull(25,java.sql.Types.DATE);
								}
							}
							
							
							
							prstm.setInt(26, (int) row.getCell(25).getNumericCellValue());
							prstm.setInt(27, (int) row.getCell(26).getNumericCellValue());
							
							prstm.addBatch();
							
						//}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				num++;
				System.out.println("");  	
			}  
			

			int [] numUpdates=prstm.executeBatch();
			System.out.println(numUpdates+" inserted.");
			
			prstm.close();
			
			//Close the input stream
			fis.close();
			
			
			
			sql = "select * from d_import_cocama_multi()";
			prstm = conn.prepareStatement(sql);
			prstm.executeQuery();
			prstm.close();
			
			
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
		response.sendRedirect(request.getContextPath() + "/rfid2/docs/documents-list.htm");
		
	}
	
	
}

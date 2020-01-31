package pkg.posws;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

import com.oreilly.servlet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.security.MessageDigest;


@WebServlet("/FileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*		
		String nomeFile	= "";
		String msg = "";
		String tpmsg = "";
		
		int maxUploadSize 	= 50000000;
		MultipartRequest multi = new MultipartRequest(request,".",maxUploadSize); 
		
		File myFile	= null;

		String root = "C:\\xampp\\tomcat\\webapps\\SmartAssets\\images\\";
		
		try
		{	
			
				
			// Carica il file
			myFile = multi.getFile("File"); 
			if(myFile!=null) {
				FileInputStream inStream = new FileInputStream(myFile);
				
				System.out.println(myFile.getName());
				
				FileOutputStream outStream = new FileOutputStream(root+myFile.getName());
				while (inStream.available()>0) 
				{
					outStream.write(inStream.read()); 
				}
				// Chiude il flusso
				inStream.close();
				outStream.close();
			}
			
		}
		catch(Exception err)
		{
			err.printStackTrace();
			msg = "ERRORE";
			tpmsg = "N";
		}
		
*/
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nomeFile	= "";
		String msg = "";
		String tpmsg = "";
		
		int maxUploadSize 	= 50000000;
		MultipartRequest multi = new MultipartRequest(request,".",maxUploadSize); 
		
		File myFile	= null;

		//String roots = "C:\\xampp\\tomcat\\webapps\\SmartAssets\\images\\";
		String roots = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\Montoro\\excel\\";
		//String roots = "C:\\xampp\\tomcat\\webapps\\img\\";
		//String roots2 = "C://xampp/tomcat/webapps/img/";
		String resp = "";
		
		try
		{	
			
				
			// Carica il file
			myFile = multi.getFile("file"); 
			if(myFile!=null) {
				FileInputStream inStream = new FileInputStream(myFile);
				
				//System.out.println(roots+myFile.getName());
				
				FileOutputStream outStream = new FileOutputStream(roots+myFile.getName());
				while (inStream.available()>0) 
				{
					outStream.write(inStream.read()); 
				}
				// Chiude il flusso
				inStream.close();
				outStream.close();
				
				
				//Lettura File
				//File myFile_2 = new File("C://temp/Employee.xlsx");
				//File myFile_2 = new File("C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/Montoro/images/");
				String pp = roots+myFile.getName();
				
				//System.out.println("pp = " + pp);
				
				FileInputStream fis = new FileInputStream(roots+myFile.getName());
				//System.out.println(roots+myFile.getName());
				
				XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
				XSSFSheet mySheet = myWorkBook.getSheetAt(0);
				Iterator<Row> rowIterator = mySheet.iterator();
				
				String valors = "";

	            while (rowIterator.hasNext()) {
	                Row row = rowIterator.next();
	                Iterator<Cell> cellIterator = row.cellIterator();
	                while (cellIterator.hasNext()) {
	                	Cell cell = cellIterator.next();
	                	
	                	

	                	switch (cell.getCellType()) {
	                		case Cell.CELL_TYPE_STRING:
	                			//System.out.print("str = "+cell.getStringCellValue() + "\t");
	                        break;
	                		case Cell.CELL_TYPE_NUMERIC:
	                			
	                			Long val =  (long) cell.getNumericCellValue();
	                			if ("".equals(valors)) {
	                				valors = val.toString();
	                			}else {
	                				valors = valors +"$"+val.toString();
	                			}

	                        break;
	                		case Cell.CELL_TYPE_BOOLEAN: 
	                			//System.out.print("bool = "+cell.getBooleanCellValue() + "\t"); 
	                		break; 
	                		default : 
	                	}

	                }
	                System.out.println("");
	            }

				//
	            myWorkBook.close();
				
	    		resp = valors;

	    		response.setContentType("text/plain");
	    		
	    		response.getWriter().write(resp);

			}
			
		}
		catch(Exception err)
		{
			err.printStackTrace();
			msg = "ERRORE";
			tpmsg = "N";
		}
	}

}

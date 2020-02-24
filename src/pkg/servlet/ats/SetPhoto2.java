package pkg.servlet.ats;

import java.io.IOException;

import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import pkg.beans.ats.Photo;
import pkg.utility.Utility;

import java.io.File;
 
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetPhoto2")
public class SetPhoto2 extends HttpServlet {
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
    public SetPhoto2() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		
		
		String idItem="";
		String type="I";
		String url="";
		String sourceData="";
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		
		
		try {
			idItem = request.getParameter("idItem");
			sourceData = request.getParameter("sourceData");
			String parts[] = sourceData.split(",");
			String imageString = parts[1];
			
			BufferedImage image = null;
			byte[] imageByte;

			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
			
			// write the image to a file
			String name = randomAlphaNumeric(10)+".jpeg";
			url = URL+name;
			
			String UPLOAD_DIRECTORY2 = getServletContext().getRealPath("source"+File.separator+"images"+File.separator+name);
			
			//File outputfile = new File(UPLOAD_DIRECTORY+name);
			File outputfile = new File(UPLOAD_DIRECTORY2);
			
			ImageIO.write(image, "jpeg", outputfile);
			
			
			
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
		responseJson.put("status", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

}

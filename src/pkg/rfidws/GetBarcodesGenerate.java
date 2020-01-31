package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.utility.Utility;


public class GetBarcodesGenerate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetBarcodesGenerate() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String resp = "";
		HttpSession session = request.getSession();

		String barcodes = Utility.noNull(request.getParameter("barcode"));
		
		/**/

		try{

			resp = Utility.getBarcode(barcodes);
			
			
			System.out.println(resp);
    	
		}catch(Exception e){
			e.printStackTrace();
		}


		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

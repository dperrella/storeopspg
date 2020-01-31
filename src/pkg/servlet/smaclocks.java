package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import pkg.utility.Utility;

@WebServlet("/smaclocks")
public class smaclocks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public smaclocks() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String tz = request.getParameter("timezone");
		
		String resp = "";
		try{

			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date today = Calendar.getInstance().getTime();        
			resp = df.format(today);
		
			if (tz == null){
				tz = "Europe/Berlin";
			}
			
			Date date = new Date();			
			TimeZone.setDefault(TimeZone.getTimeZone(tz));
			df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			today = date;        
			resp = df.format(today);
			
			
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

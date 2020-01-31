package pkg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.Tables2;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetActiveAlarms
 */
@WebServlet("/Wirama")
public class Wirama extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Wirama() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Wirama called me!!!");
		
		String GATE_ID = request.getParameter("GATE_ID");
		String TAGID = request.getParameter("TAGID");
		
		System.out.println("GATE_ID: "+GATE_ID);
		System.out.println("TAGID  : "+TAGID);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setStatus(200);
		response.setDateHeader("Response_Timestamp",  System.currentTimeMillis());
		response.setContentLength(10);
		
		PrintWriter out = response.getWriter();
		out.println("ACCEPTED");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.ats.Item;
import pkg.beans.rfid.Readers;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/GetItemByBarcode2")
public class GetItemByBarcode2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetItemByBarcode2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			conn=Utility.getConnData();
			
			String barcode = request.getParameter("barcode");
			
			sql   = "select iditem from ats.barcode where barcode = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, barcode);
			System.out.println(st.toString());
			rs=st.executeQuery();
			
			JSONObject jsonObj = new JSONObject();
			
			if(rs.next()) {
				jsonObj.put("iditem", rs.getInt("iditem"));
			}
			else {
				jsonObj.put("iditem", -1);
			}
			rs.close();
			st.close();
			
			//responseJson.put("item", jsonObj);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObj.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

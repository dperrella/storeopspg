package pkg.posws;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;


@WebServlet("/GetFromBarcode")
public class GetFromBarcode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetFromBarcode() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn = Utility.getConnPos();
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			st=conn.createStatement();

			sql = "select * from products where code = '"+request.getParameter("barcode")+"'";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("Code", Utility.noNull(rs.getString("code")));
				jsonObj.put("Name", Utility.noNull(rs.getString("name")));
				double przint_ = Double.parseDouble(rs.getString("pricesell")) * 1.22;
				double przint = new BigDecimal(przint_).setScale(2 , BigDecimal.ROUND_UP).doubleValue();
				jsonObj.put("Pricesell", przint);
				jsonObj.put("Category", Utility.noNull(rs.getString("category")));
				jsonObj.put("Codart", Utility.noNull(rs.getString("codart")));
				jsonObj.put("Codcol", Utility.noNull(rs.getString("codcol")));
				jsonObj.put("Taglia", Utility.noNull(rs.getString("taglia")));
				jsonObj.put("Listino", Utility.noNull(rs.getString("listino")));
				jsonObj.put("Stagione", Utility.noNull(rs.getString("stagione")));
				jsonObj.put("Parte", Utility.noNull(rs.getString("parte")));
				jsonObj.put("Variante", Utility.noNull(rs.getString("variante")));
				
				jsonArray.add(jsonObj);
			}
			rs.close();

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
		
		
		
		
		
		responseJson.put("listings", jsonArray);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());



		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

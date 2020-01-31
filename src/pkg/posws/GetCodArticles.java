package pkg.posws;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;


@WebServlet("/GetCodArticles")
public class GetCodArticles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetCodArticles() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String listart = "";
		
		String Art = Utility.noNull(request.getParameter("codart"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		conn = Utility.getConnPos();
		
		try {
			String sql = "";
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select *,split_part(name,' ',1) codalt from products where code in('"+Art+"')";
			//System.out.println("SQL3 = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {

				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("Id", Utility.noNull(rs.getString("id")));
				jsonObj.put("Codalt", Utility.noNull(rs.getString("codalt")));
				jsonObj.put("Reference", Utility.noNull(rs.getString("reference")));
				jsonObj.put("Name", Utility.noNull(rs.getString("name")));
				jsonObj.put("Pricesell", Utility.noNull(rs.getString("pricesell")));
				jsonObj.put("Category", Utility.noNull(rs.getString("category")));
				jsonObj.put("Codcol", Utility.noNull(rs.getString("codcol")));
				jsonObj.put("Taglia", Utility.noNull(rs.getString("taglia")));
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

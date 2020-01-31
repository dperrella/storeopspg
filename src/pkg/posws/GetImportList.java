package pkg.posws;

import java.io.IOException;
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

@WebServlet("/GetImportList")
public class GetImportList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetImportList() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {

			conn = Utility.getConnPos();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select * from codartload ";
			//System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				String status = "";
				String sexo = "";
				String add = "";

				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("Id", Utility.noNull(rs.getString("id")));
				jsonObj.put("Codart", Utility.noNull(rs.getString("codart")));
				jsonObj.put("Dataload", Utility.noNull(rs.getString("dataload")));

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

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


@WebServlet("/GetClosecash")
public class GetClosecash extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetClosecash() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String where = "";
		String Ids = Utility.noNull(request.getParameter("client"));
		String dat = Utility.noNull(request.getParameter("data"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {

			conn = Utility.getConnPos();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select host,hostsequence,datestart,dateend,sum(total) "+
						  "from closedcash "+ 
						  "join receipts on receipts.money = closedcash.money and receipts.idclient = closedcash.idclient "+
						  "join payments on receipts.id = payments.receipt and payments.idclient = receipts.idclient "+
						  "where receipts.idclient = '"+Ids+"' and dateend::date >= '"+dat+"'::date "+
						  "group by host,hostsequence,datestart,dateend,receipts.idclient";
			
			//System.out.println("SQL1 = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Host", Utility.noNull(rs.getString("host")));
				jsonObj.put("HostS", Utility.noNull(rs.getString("hostsequence")));
				jsonObj.put("Datestart", Utility.noNull(rs.getString("datestart")));
				jsonObj.put("Dateend", Utility.noNull(rs.getString("dateend")));
				jsonObj.put("Sum", Utility.noNull(rs.getString("sum")));
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

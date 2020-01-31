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


@WebServlet("/GetTicketsDetail")
public class GetTicketsDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetTicketsDetail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String IdC = Utility.noNull(request.getParameter("ticketcli"));
		String Tickid = Utility.noNull(request.getParameter("ticketid"));
		String Ticktyp = Utility.noNull(request.getParameter("tickettyp"));
		String Ticktyear = Utility.noNull(request.getParameter("ticketyear"));
		String Ticktcassa = Utility.noNull(request.getParameter("ticketcassa"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {

			conn = Utility.getConnPos();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql = "select prezzointero*(select 1+rate from taxes where idclient = '"+IdC+"' and category = '001' order by validfrom desc limit 1) p_intero,price*(select 1+rate from taxes where idclient = '"+IdC+"' and category = '001' order by validfrom desc limit 1) p_price,* from ticketlines "+
					"left join tickets on id = ticket "+
					"where tickets.idclient = '"+IdC+"' "+
					"and tickettype = '"+Ticktyp+"' "+
					"and ticketid = '"+Tickid+"'";
			//System.out.println("SQL1 = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {

				JSONObject jsonObj = new JSONObject();
				
				
				// Arrotondamento alle 2 cifre decimali
				double przint = new BigDecimal(rs.getString("p_intero")).setScale(2 , BigDecimal.ROUND_UP).doubleValue();
				double prz = new BigDecimal(rs.getString("p_price")).setScale(2 , BigDecimal.ROUND_UP).doubleValue();
				double scnt = new BigDecimal(rs.getString("sconto")).setScale(2 , BigDecimal.ROUND_UP).doubleValue();
				
				
				jsonObj.put("Product", Utility.noNull(rs.getString("product")));
				jsonObj.put("Units", Utility.noNull(rs.getString("units")));
				jsonObj.put("PrezzoIntero", przint);
				//jsonObj.put("PrezzoIntero", Utility.noNull(rs.getString("p_intero")));
				jsonObj.put("Sconto", scnt);
				//jsonObj.put("Sconto", Utility.noNull(rs.getString("sconto")));
				jsonObj.put("Price", prz);
				//jsonObj.put("Price", Utility.noNull(rs.getString("p_price")));
				
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

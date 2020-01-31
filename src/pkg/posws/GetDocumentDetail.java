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


@WebServlet("/GetDocumentDetail")
public class GetDocumentDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetDocumentDetail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String where = "";
		String Ids = Utility.noNull(request.getParameter("ticketcli"));
		String num = Utility.noNull(request.getParameter("numres"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {

			conn = Utility.getConnPos();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select codart,parte,codcol,variante,taglia,qtabolla,qtanegozio,(qtabolla-qtanegozio) differenza from tabdettagliobolle tb "+ 
							"join products p on tb.idclient = p.idclient and codicebarre = code "+
							"where tb.idclient = '"+Ids+"' "+
							"and idbolla = "+num+" "+
							"order by codart,parte,codcol,variante,taglia";
			
			//System.out.println("SQL1 = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Codart", Utility.noNull(rs.getString("codart")));
				jsonObj.put("Parte", Utility.noNull(rs.getString("parte")));
				jsonObj.put("Codcol", Utility.noNull(rs.getString("codcol")));
				jsonObj.put("Variante", Utility.noNull(rs.getString("variante")));
				jsonObj.put("Taglia", Utility.noNull(rs.getString("taglia")));
				jsonObj.put("Qtabolla", Utility.noNull(rs.getString("qtabolla")));
				jsonObj.put("Qtanegozio", Utility.noNull(rs.getString("qtanegozio")));
				jsonObj.put("Differenza", Utility.noNull(rs.getString("differenza")));

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

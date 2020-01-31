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


@WebServlet("/GetVenduto")
public class GetVenduto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetVenduto() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String listcat = "";
		String listspr = "";
		String listart = "";
		String listcol = "";
		String listloc = "";
		String listpar = "";
		String listdoc = "";
		
		String Ids = Utility.noNull(request.getParameter("client"));
		String Dat = Utility.noNull(request.getParameter("data"));
		String Dat2 = Utility.noNull(request.getParameter("dataa"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		conn = Utility.getConnPosext();
		
		try {
			String sql = "";
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			//sql = "select codice,descrizione from tipodoc where idclient = '"+Ids+"'";
			sql = "select * from venduto_giornaliero where idclient = '"+Ids+"' and giorno >= '"+Dat+"' and giorno <= '"+Dat2+"' order by codart,parte,codcol,variante ";
			//System.out.println("sql = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Idclient", Utility.noNull(rs.getString("idclient")));
				jsonObj.put("Giorno", Utility.noNull(Utility.getTurnDate(rs.getString("giorno"))));
				jsonObj.put("Codart", Utility.noNull(rs.getString("codart")));
				jsonObj.put("Codartalt", Utility.noNull(rs.getString("codartalt")));
				jsonObj.put("Taglia", Utility.noNull(rs.getString("taglia")));
				jsonObj.put("Parte", Utility.noNull(rs.getString("parte")));
				jsonObj.put("Codcol", Utility.noNull(rs.getString("codcol")));
				jsonObj.put("Variante", Utility.noNull(rs.getString("variante")));
				jsonObj.put("Pezzi", Utility.noNull(rs.getString("pezzi")));
				jsonObj.put("Valore", Utility.noNull(rs.getString("valore")));
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

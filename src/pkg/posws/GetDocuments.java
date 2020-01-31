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



@WebServlet("/GetDocuments")
public class GetDocuments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetDocuments() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String where = "";
		String Ids = Utility.noNull(request.getParameter("client"));
		String dat = Utility.noNull(request.getParameter("data"));
		String war = Utility.noNull(request.getParameter("war"));
		String tip = Utility.noNull(request.getParameter("tipdoc"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {

			conn = Utility.getConnPosext();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select autoid,tipodoc.descrizione,datadoc,numdoc,locations.name,case chiuso when true then 'CHIUSO' else 'APERTO' end "+
							",(select sum(qtabolla) from tabdettagliobolle where idclient = tabbolle.idclient and idbolla = tabbolle.autoid) teorico "+ 
							",(select sum(qtanegozio) from tabdettagliobolle where idclient = tabbolle.idclient and idbolla = tabbolle.autoid) fisico "+ 
							"from tabbolle "+ 
							"join tipodoc on tipork = tipodoc.codice and tabbolle.idclient = tipodoc.idclient "+
							"join locations on magazzino = locations.id and tabbolle.idclient = locations.idclient "+
							"where tipork = '"+tip+"' "+	
							"and datadoc::date >= '"+dat+"' "+
							"and magazzino = '"+war+"' "+
							"and tabbolle.idclient = '"+Ids+"'";
			
			//System.out.println("SQL1 = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Autoid", Utility.noNull(rs.getString("autoid")));
				jsonObj.put("Descrizione", Utility.noNull(rs.getString("descrizione")));
				jsonObj.put("Datadoc", Utility.noNull(rs.getString("Datadoc")));
				jsonObj.put("Numdoc", Utility.noNull(rs.getString("numdoc")));
				jsonObj.put("Name", Utility.noNull(rs.getString("Name")));
				jsonObj.put("Case", Utility.noNull(rs.getString("case")));
				jsonObj.put("Teorico", Utility.noNull(rs.getString("teorico")));
				jsonObj.put("Fisico", Utility.noNull(rs.getString("fisico")));

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

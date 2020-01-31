package pkg.posws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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


@WebServlet("/GetParameters")
public class GetParameters extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetParameters() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String listcat = "";
		String listspr = "";
		String listart = "";
		String listcol = "";
		String listloc = "";
		String listpar = "";
		
		String Ids = Utility.noNull(request.getParameter("id"));
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		conn = Utility.getConnPos();
		
		try {
			String sql = "";
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql = "select * from categories where idclient = '"+Ids+"'";
			//sql = "select * from categories where idclient = 'GLOBAL'";
			//System.out.println("SQL1 = " + sql);
			rs = st.executeQuery(sql);
			listcat = "<option value=''>-- Choise Category --</option>";
			while(rs.next()) {
				if (!"".equals(Utility.noNull(rs.getString("id")))){
					listcat = listcat + "<option value='"+Utility.noNull(rs.getString("id"))+"'>"+Utility.noNull(rs.getString("name").toUpperCase())+"</option>";
				}
			}
			rs.close();
			
			sql = "select distinct stagione from products where idclient = '"+Ids+"'";
			//System.out.println("SQL2 = " + sql);
			rs = st.executeQuery(sql);
			listspr = "<option value=''>-- Choise Spring --</option>";
			while(rs.next()) {
				if (!"".equals(Utility.noNull(rs.getString("stagione")))){
					listspr = listspr + "<option value='"+Utility.noNull(rs.getString("stagione"))+"'>"+Utility.noNull(rs.getString("stagione").toUpperCase())+"</option>";
				}
			}
			rs.close();
			
			/*
			sql = "select distinct codart from products where idclient = '"+Ids+"'";
			rs = st.executeQuery(sql);
			System.out.println("SQL3 = " + sql);
			//listart = "<option value=''>-- Choise Article --</option>";
			while(rs.next()) {
				if (!"".equals(Utility.noNull(rs.getString("codart")))){
					listart = listart + "<option value='"+Utility.noNull(rs.getString("codart"))+"'>"+Utility.noNull(rs.getString("codart").toUpperCase())+"</option>";
				}
			}
			rs.close();
			*/
			
			sql = "select distinct codcol from products where idclient = '"+Ids+"'";
			//System.out.println("SQL4 = " + sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				if (!"".equals(Utility.noNull(rs.getString("codcol")))){
					listcol = listcol + "<option value='"+Utility.noNull(rs.getString("codcol"))+"'>"+Utility.noNull(rs.getString("codcol").toUpperCase())+"</option>";		
				}
			}
			
			sql = "select distinct parte from products where idclient = '"+Ids+"'";
			//System.out.println("SQL5 = " + sql);
			rs = st.executeQuery(sql);
			//listpar = "<option value=''>-- Choise Parts --</option>";
			while(rs.next()) {
				if (!"".equals(Utility.noNull(rs.getString("parte")))){
					listpar = listpar + "<option value='"+Utility.noNull(rs.getString("parte"))+"'>"+Utility.noNull(rs.getString("parte").toUpperCase())+"</option>";
				}
			}
			
			sql = "select * from locations where idclient = '"+Ids+"' order by name";
			//System.out.println("SQL6 = " + sql);
			rs = st.executeQuery(sql);
			listloc = "<option value=''>-- Choise Warehouse --</option>";
			while(rs.next()) {
				if (!"".equals(Utility.noNull(rs.getString("id")))){
					listloc = listloc + "<option value='"+Utility.noNull(rs.getString("id"))+"'>"+Utility.noNull(rs.getString("name").toUpperCase())+"</option>";
				}
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("Listcat", Utility.noNull(listcat));
			jsonObj.put("Listspr", Utility.noNull(listspr));
			jsonObj.put("Listart", Utility.noNull(listart));
			jsonObj.put("Listcol", Utility.noNull(listcol));
			jsonObj.put("Listpar", Utility.noNull(listpar));
			jsonObj.put("Listloc", Utility.noNull(listloc));
			jsonArray.add(jsonObj);
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

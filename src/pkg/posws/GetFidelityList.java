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

@WebServlet("/GetFidelityList")
public class GetFidelityList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetFidelityList() {
        super();
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

			sql = "select * from fidelitycard left join categoryfid on fidelitycategory = idcategoryfid ";
			//System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				String status = "";
				String sexo = "";
				String add = "";

				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("Idfidelity", Utility.noNull(rs.getString("idfidelity")));
				jsonObj.put("Fidalityname", Utility.noNull(rs.getString("fidelityname")));
				jsonObj.put("Fidelitysurname", Utility.noNull(rs.getString("fidelitysurname")));

				if (rs.getString("fidelitysex").equals("M")){
					sexo = "UOMO";
				}else{
					sexo = "DONNA";
				}
				
				jsonObj.put("Fidelitysex", Utility.noNull(sexo));
				
				if (!"".equals(rs.getString("fidelityaddress"))){
					add = rs.getString("fidelityaddress");
				}else{
					add = rs.getString("fidelityaddress");
				}
				
				jsonObj.put("Fidelityaddress", Utility.noNull(add));
				jsonObj.put("Fidelitytel", Utility.noNull(rs.getString("fidelitytel")));
				jsonObj.put("Fidelitymail", Utility.noNull(rs.getString("fidelitymail")));
				jsonObj.put("Fidelitycodfiscale", Utility.noNull(rs.getString("fidelitycodfiscale")));
				//jsonObj.put("Fidelitycategory", Utility.noNull(rs.getString("fidelitycategory")));
				jsonObj.put("Fidelitycategory", Utility.noNull(rs.getString("descriptioncategoryfid")));
				

				if (rs.getString("fidelitystatus").equals("f")){
					status = "<div style='align: center; text-align: center;'><i class='fa fa-circle' style='color: #A00000;'><spna style='display: none'>B</span></i></div>";
				}else{
					status = "<div style='align: center; text-align: center;'><i class='fa fa-circle' style='color: green;'><spna style='display: none'>A</span></i></div>";
				}
				
				jsonObj.put("Fidelitystatus", status);
				jsonObj.put("Fidelitycode", Utility.noNull(rs.getString("fidelitycode")));
				jsonObj.put("Fidelitydate", Utility.noNull(Utility.getTurnDate(rs.getString("fidelitydate"))));
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
		//System.out.println("IN POST");
	}

}

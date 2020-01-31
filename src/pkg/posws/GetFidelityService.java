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


@WebServlet("/GetFidelityService")
public class GetFidelityService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetFidelityService() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String id_scheda = request.getParameter("id_scheda");
		
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Double tot_imp_num = 0.00;
		String tot_imp = "";
		String punti = "";
		
		JSONObject jsonObj = new JSONObject();
		
		try {

			conn = Utility.getConnPos();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select * from getfidelity('"+id_scheda+"') ";
			//System.out.println(sql);
			
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String status = "";
				String sexo = "";
				String add = "";
				
				jsonObj.put("Idfidelity", Utility.noNull(rs.getString("_idfidelity")));
				jsonObj.put("Fidalityname", Utility.noNull(rs.getString("_fidelityname")));
				jsonObj.put("Fidelitysurname", Utility.noNull(rs.getString("_fidelitysurname")));

				if (rs.getString("_fidelitysex").equals("M")){
					sexo = "UOMO";
				}else{
					sexo = "DONNA";
				}
				
				jsonObj.put("Fidelitysex", Utility.noNull(sexo));
				
				if (!"".equals(rs.getString("_fidelityaddress"))){
					add = rs.getString("_fidelityaddress");
				}else{
					add = rs.getString("_fidelityaddress");
				}
				
				jsonObj.put("Fidelityaddress", Utility.noNull(add));
				jsonObj.put("Fidelitytel", Utility.noNull(rs.getString("_fidelitytel")));
				jsonObj.put("Fidelitymail", Utility.noNull(rs.getString("_fidelitymail")));
				jsonObj.put("Fidelitycodfiscale", Utility.noNull(rs.getString("_fidelitycodfiscale")));

				jsonObj.put("Fidelitystatus", Utility.noNull(rs.getString("_fidelitystatus")));
				jsonObj.put("Fidelitycode", Utility.noNull(rs.getString("_fidelitycode")));
				jsonObj.put("Fidelitydate", rs.getString("_fidelitydate"));
				jsonObj.put("Fidelitybirth", rs.getString("_fidelitybirth"));
				
				jsonObj.put("Saldo", rs.getString("_saldo"));
				
				//jsonObj.put("Puntifidelity", Integer.parseInt(rs.getString("_puntifidelity_detail")));
				String app_s_p = Utility.noNull(rs.getString("_saldopunti"));
				
				if (!"".equals(app_s_p)) {
					jsonObj.put("Puntifidelity", Integer.parseInt(rs.getString("_saldopunti")));
				}else {
					jsonObj.put("Puntifidelity", rs.getString("_saldopunti"));
				}
				
				jsonObj.put("Step", Utility.noNull(rs.getString("_step")));
				jsonObj.put("Percentuale", Utility.noNull(rs.getString("_percentuale")));
				
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
		
		
		responseJson.put("scheda", jsonObj);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonObj.toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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



@WebServlet("/GetFidelityListDetail")
public class GetFidelityListDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetFidelityListDetail() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String sql = "";
		Double tot_imp_num = 0.00;
		String tot_imp = "";
		String punti = "";
		Double punti_ = 0.00;
		Double _punti_ = 0.00;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {

			conn = Utility.getConnPos();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			sql = "select * from fidelity_detail where codicefidelity_detail = '"+Utility.noNull(request.getParameter("codfid"))+"'";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				String status = "";
				String flagfidelity = "";
				String add = "";

				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("Idfidelity", Utility.noNull(rs.getString("idfidelity_detail")));
				jsonObj.put("Codstorefidelity", Utility.noNull(rs.getString("codstorefidelity_detail").toUpperCase()));
				jsonObj.put("Datafidelity", Utility.getTurnDate(Utility.noNull(rs.getString("datafidelity_detail"))));
				jsonObj.put("Codicefidelity", Utility.noNull(rs.getString("codicefidelity_detail").toUpperCase()));
				jsonObj.put("Importofidelity", Utility.noNull(rs.getString("importofidelity_detail")));
				
				System.out.println("punti = " + rs.getString("puntifidelity_detail"));
				if (!"".equals(Utility.noNull(rs.getString("puntifidelity_detail")))) {
					punti_ = Double.parseDouble(rs.getString("puntifidelity_detail"));
				}else {
					punti_ = 0.00;
				}
				
				jsonObj.put("Punti", punti_);

				if (rs.getString("flagfidelity_detail").equals("C")){
					flagfidelity = "CARICO";
				}else{
					flagfidelity = "SCARICO";
				}
								
				jsonObj.put("Flagfidelity", Utility.noNull(flagfidelity.toUpperCase()));
				
				/*
				if (flagfidelity.equals("CARICO")) {
					tot_imp_num = tot_imp_num + Double.parseDouble(rs.getString("importofidelity_detail"));
					_punti_ = _punti_ + punti_;
				}else {
					tot_imp_num = tot_imp_num - Double.parseDouble(rs.getString("importofidelity_detail"));
					_punti_ = _punti_ - punti_;
				}
				*/
				
				tot_imp_num += Double.parseDouble(rs.getString("importofidelity_detail"));
				_punti_ += punti_;
				
				tot_imp = Double.toString(tot_imp_num);
				
				
				jsonObj.put("Saldo", tot_imp);
				
				jsonObj.put("Saldopunti", _punti_);
				
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

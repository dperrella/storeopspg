package pkg.rfidws;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

import java.sql.*;

/**
 * Servlet implementation class GetBarcodeEPC
 */
@WebServlet("/rfid/ws/GetBarcodeEPCAPK")
public class GetBarcodeEPCAPK extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBarcodeEPCAPK() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String cond = "";
		String cond2 = "";
		
		String barcode = ""+request.getParameter("barcode");
		barcode = Utility.noNull(barcode);
		
		barcode = "00184310014144101333";
		
		// -----------
		String corr = ""+request.getParameter("corr");
		corr = Utility.noNull(corr);

		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			
			if (corr.equals("true")){
				cond+= " where flag_diff <> 0 ";
				cond2+= " and flag_diff <> 0 ";
			}
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			JSONObject responseJsontot = new JSONObject();
			JSONArray jsonArraytot = new JSONArray();
			
			
			sql = "select reference, name, code, barcode, sum(qty_theoretical) qty_theoretical, sum(qty_readed) qty_readed, package_id, flag_diff "
					+ "from (select *, qty_theoretical - qty_readed as flag_diff from "
					+ "documents_details "
					+ "left join products on code = barcode "
					+ "where package_id = '"+barcode+"') a "
					+ cond 
					+ "group by reference, name, code, barcode, package_id, flag_diff "
					+ "order by reference ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				String status = "";
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("PackageID", barcode);
				jsonObj.put("Barcode", rs.getString("barcode"));
				jsonObj.put("Sum_Att", rs.getString("qty_theoretical"));
				jsonObj.put("Sum_Rea", rs.getString("qty_readed"));
				jsonObj.put("Sum_Dif", rs.getString("flag_diff"));
				if (!rs.getString("flag_diff").equals("0")){
					if (rs.getInt("flag_diff") > 0){
						status = "PLUS";
					}else{
						status = "MIN";
					}
				}else{
					status = "OK";
				}
				jsonObj.put("Descrizione", rs.getString("reference"));
				jsonObj.put("Status", status);
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			sql   = "select package_id,sum(qty_theoretical) sommaatteso,sum(qty_readed) sommaletto , "
					+ "sum(qty_readed - qty_theoretical) sommadiff "
					+ "from documents_details where package_id = '"+barcode.replaceAll("'", "''")+"' "
					+ "group by package_id";
			System.out.println(sql);
			rs=st.executeQuery(sql);	
			while(rs.next()) {
				JSONObject jsonObjtot = new JSONObject();
				jsonObjtot.put("sum_att", rs.getString("sommaatteso"));
				jsonObjtot.put("sum_rea", rs.getString("sommaletto"));
				jsonObjtot.put("sum_dif", rs.getString("sommadiff"));
				
				jsonArraytot.add(jsonObjtot);
			}	
			rs.close();
			
			responseJson.put("listings", jsonArray);
			responseJson.put("listingstot", jsonArraytot);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}

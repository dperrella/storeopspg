package pkg.rfidws;

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

/**
 * Servlet implementation class GetBarcodeEPCDetail
 */
@WebServlet("/rfid/ws/GetBarcodeEPCDetailAPK")
public class GetBarcodeEPCDetailAPK extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBarcodeEPCDetailAPK() {
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
		
		String pack = ""+request.getParameter("pack");
		pack = Utility.noNull(pack);
		// -----------
		String barcode = ""+request.getParameter("barcode");
		barcode = Utility.noNull(barcode);
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql = "select epc, reference, case when qty_theoretical <> qty_readed then false else true end flag "
					+ "from documents_details "
					+ "left join products on code = barcode "
					+ "where package_id = '"+pack+"' "
					+ "and barcode = '"+barcode+"' ";
			
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				String status = "", EPC = "";
				JSONObject jsonObj = new JSONObject();
				
				if (rs.getString("epc").equals("999999999999999999999999")){
					EPC = "EPC NOT FOUND";
					status = "NOT";
				}else{
					EPC = rs.getString("epc");
				}
				
				jsonObj.put("Epc", EPC);

				if (status == ""){
					if (rs.getString("flag").equals("f")){
						status = "ERR";
					}else{
						status = "OK";
					}
				}
				
				jsonObj.put("Codpro", rs.getString("reference"));
				jsonObj.put("Status", status);
				jsonArray.add(jsonObj);
			}	
			rs.close();
			responseJson.put("listings", jsonArray);
			
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

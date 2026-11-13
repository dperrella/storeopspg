package pkg.servlet.occ;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/occupancy/GetSettings2")
public class GetSettings2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSettings2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String accountId = "";
		String idCustomer = "";
		String settings = "";
		JSONObject responseJson = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();
		
		
		HttpSession session = request.getSession();
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
		
		String type = ""+request.getParameter("type");
		type = Utility.noNull(type);
		
		try {
			
				
			accountId = request.getParameter("accountId");
			
			conn=Utility.getConnData();
			st=conn.createStatement();
		
			sql = "select id from customers where account_code = '"+accountId+"' ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				idCustomer = rs.getString("id");
			}
			else {
				idCustomer = idcus;
			}
			rs.close();
			
			
			
			
			
			sql   = "select row_to_json(settings) settings from (select s.id,mailfrom,mailto,mailsmtp,mailport,"
					+ "mailuser,mailpass,template,mailsend,mailwrn,mailover,mailtype,hhopen,hhclose,msgwrn,"
					+ "machine_learning,enable_normal,enable_warning,enable_error,data_type,df,traffic_light_count_type,"
					+ "tz,ntp,privacy,reset1,reset2,avgqueue,sm, \n" + 
					"json_agg(p.*) properties,\n" +
					"(SELECT array_to_json(array_agg(occ2_aree)) FROM occ2_aree where id_customer = "+idCustomer+") aree,\n" + 
					"(SELECT array_to_json(array_agg(occ2_doors)) FROM occ2_doors where id_customer = "+idCustomer+") doors,\n" + 
					"(SELECT array_to_json(array_agg(occ2_labels)) FROM occ2_labels where id_customer = "+idCustomer+") labels,\n" + 
					"(SELECT array_to_json(array_agg(occ2_dailyoperation_stores)) FROM occ2_dailyoperation_stores where id_customer = "+idCustomer+") hours,\n" + 
					"(SELECT array_to_json(array_agg(occ2_rel_door_area)) FROM occ2_rel_door_area where id_customer = "+idCustomer+") rel\n" +
					"from occ2_settings s\n" + 
					"join occ2_properties p on s.id_customer = p.id_customer\n" + 
					"where s.id_customer = "+idCustomer+" and p.tile <> 1 "+
					"group by s.id,mailfrom,mailto,mailsmtp,mailport,mailuser,mailpass,template,mailsend,"
					+ "mailwrn,mailover,mailtype,hhopen,hhclose,msgwrn,machine_learning,enable_normal,"
					+ "enable_warning,enable_error,data_type,df,traffic_light_count_type,tz,ntp,privacy,"
					+ "reset1,reset2,avgqueue,sm) settings ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			if(rs.next()) {
				settings = rs.getString("settings");
			}
			rs.close();
			
			
			json = (JSONObject) parser.parse(settings);
			
			
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
		
		
		
		if(type.length()==0) {
			responseJson.put("settings", json);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
		}
		else {
			response.setContentType("text/plain");
	        response.setHeader("Content-Disposition", "attachment; filename=\"settings_"+accountId+".txt\"");
	        
	        try {
	            OutputStream outputStream = response.getOutputStream();
	            String outputResult = settings;
	            outputStream.write(outputResult.getBytes());
	            outputStream.flush();
	            outputStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

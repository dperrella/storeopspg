package pkg.servlet;

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
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/SetCustomerConfiguration")
public class SetCustomerConfiguration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetCustomerConfiguration() {
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
		
		try {
			conn=Utility.getConnAdmin();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			String msg = "";
			
			String tipoForm = request.getParameter("tipoForm");
			
			
			if(tipoForm.equals("I"))  {
			
				
				String customerId = request.getParameter("customerId");
				String ip1 = request.getParameter("ip1");
				String ip2 = request.getParameter("ip2");
				String ip3 = request.getParameter("ip3");
				String ip4 = request.getParameter("ip4");
				
				String from1 = "";
				String from2 = "";
				String from3 = "";
				String from4 = "";
				String to1 = "";
				String to2 = "";
				String to3 = "";
				String to4 = "";
				
				try {from1 = Utility.noNull(request.getParameter("from1"));}catch(Exception e) {from1=ip1;}
				try {from2 = Utility.noNull(request.getParameter("from2"));}catch(Exception e) {from1=ip2;}
				try {from3 = Utility.noNull(request.getParameter("from3"));}catch(Exception e) {from1=ip3;}
				try {from4 = Utility.noNull(request.getParameter("from4"));}catch(Exception e) {from1=ip4;}
				try {to1 = Utility.noNull(request.getParameter("to1"));}catch(Exception e) {from1=ip1;}
				try {to2 = Utility.noNull(request.getParameter("to2"));}catch(Exception e) {from1=ip2;}
				try {to3 = Utility.noNull(request.getParameter("to3"));}catch(Exception e) {from1=ip3;}
				try {to4 = Utility.noNull(request.getParameter("to4"));}catch(Exception e) {from1=ip4;}
				
				if(from1.length()==0)
					from1 = ip1;
				if(from2.length()==0)
					from2 = ip2;
				if(from3.length()==0)
					from3 = ip3;
				if(from4.length()==0)
					from4 = ip4;
				
				if(to1.length()==0)
					to1 = ip1;
				if(to2.length()==0)
					to2 = ip2;
				if(to3.length()==0)
					to3 = ip3;
				if(to4.length()==0)
					to4 = ip4;
				
				String range_from = from1+"."+from2+"."+from3+"."+from4;
				String range_to = to1+"."+to2+"."+to3+"."+to4;
				
				String size = request.getParameter("size");
				String tunneling = request.getParameter("tunneling");
				
				sql   = "select count(id) num from customers_device_rule where idcustomer = "+customerId;
				System.out.println(sql);
				rs=st.executeQuery(sql);
				
				int num = 0;
				if(rs.next()) {
					num = rs.getInt("num");
				}
				rs.close();
				
				if(num>0) {
					msg = "Rule Already present from Customer. Please remove the rule.";
				}
				else {
					
					sql = "INSERT INTO public.customers_device_rule(" + 
							"	idcustomer, ip1, ip2, ip3, ip4, range_from, range_to, size, tunneling) " + 
							"	VALUES ("+customerId+", '"+ip1+"', '"+ip2+"', '"+ip3+"', '"+ip4+"', " +
							"   '"+range_from+"', '"+range_to+"', "+size+", "+tunneling+")";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					msg = "Rule Inserted.";
				}
				
				
			}
			else if(tipoForm.equals("E")) {
				
				String ruleid = request.getParameter("ruleid");
				
				sql = "delete from public.customers_device_rule where id = "+ruleid;
				System.out.println(sql);
				st.executeUpdate(sql);
				
				msg = "Rule Deleted.";
			}
			
			responseJson.put("message", msg);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

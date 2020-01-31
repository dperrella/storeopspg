package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.utility.Utility;


public class SetRouters extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SetRouters() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		Connection conn = null;
		Statement st = null;
		
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnControl();

		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {

			sql = "INSERT INTO routers(routers_type, customer, customer_description, account_id, store_id, store_description, brepbins, store_sap_code, client_open_vpn, "+ 
					"ip_address_router, sim, router_code, router_serial_number, imsi_number, master_antenna, slave_antenna, notes) "+
					"VALUES ('"+Utility.noNull(request.getParameter("routertype"))+"', '"+Utility.noNull(request.getParameter("customerid"))+"', '"+Utility.noNull(request.getParameter("customerdescription").replaceAll("'", "''"))+"', "+
					"'"+Utility.noNull(request.getParameter("accountid"))+"', '"+Utility.noNull(request.getParameter("storeid"))+"', '"+Utility.noNull(request.getParameter("storedescription").replaceAll("'", "''"))+"', "+
					"'"+Utility.noNull(request.getParameter("brepbins"))+"', '"+Utility.noNull(request.getParameter("storesapcode"))+"', '"+Utility.noNull(request.getParameter("clientopenvpn"))+"', "+
					"'"+Utility.noNull(request.getParameter("ipaddressrouter"))+"', '"+Utility.noNull(request.getParameter("sim"))+"', '"+Utility.noNull(request.getParameter("routercode"))+"', "+
					"'"+Utility.noNull(request.getParameter("routerserialnumber"))+"', '"+Utility.noNull(request.getParameter("imsinumber"))+"', '"+Utility.noNull(request.getParameter("masterantenna"))+"', "+
					"'"+Utility.noNull(request.getParameter("slaveantenna"))+"', '"+Utility.noNull(request.getParameter("notes"))+"');";
				System.out.println("sql = " + sql);
			st.executeUpdate(sql);
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			
		resp = "";

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

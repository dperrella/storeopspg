package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;

public class GetNameControllerValues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetNameControllerValues() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("CONTROLLO NOME VALUE");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String resp = "";
		String resp_app = "";
		String kpi_app = "";
		String kpi_app2 = "";
		
		String code = Utility.noNull(""+request.getParameter("code"));
		String kpi1 = Utility.noNull(""+request.getParameter("kpi1"));
		String kpisymb = Utility.noNull(""+request.getParameter("kpisymb"));
		String kpi2 = Utility.noNull(""+request.getParameter("kpi2"));
		String ctype = Utility.noNull(""+request.getParameter("ctype"));
		String kpi_group = Utility.noNull(""+request.getParameter("kpi_group"));
		
		System.out.println("kpi_group = " +kpi_group);
		if (!"".equals(kpi_group)) {
			String[] appk = kpi_group.split(",");
			for (int r=0; r < appk.length; r++) {
				if ("".equals(kpi_app2)) {
					kpi_app2 = appk[r];
				}else {
					kpi_app2 = kpi_app2 + "*"+appk[r];
				}
			}
		}
		
		kpi_app = kpi_app2;

		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
			
			if (ctype.equals("1")) {
				sql   = "select kpi as xcdel from public.calculatedkpi where upper(kpi1) = upper('"+kpi1+"') "+
						"and upper(sign) = upper('"+kpisymb+"') and upper(kpi2) = upper('"+kpi2+"')";
			}else {
				sql   = "select _kpi as xcdel from public.test_kpi_grouped('"+kpi_app+"'); ";
			}

			rs=st.executeQuery(sql);
			
			if(rs.next()) {
				resp_app = rs.getString("xcdel");
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

		if ("".equals(resp_app)) {
			resp_app = "OK";
			resp = "OK"+";"+resp_app;
		}else {
			resp = "KO"+";"+resp_app;
		}
		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

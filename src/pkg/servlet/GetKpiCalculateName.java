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


public class GetKpiCalculateName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetKpiCalculateName() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("CONTROLLO NOME");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String resp = "";
		String resp_app = "0";
		/*
		String kpi1 = Utility.noNull(""+request.getParameter("kpi1"));
		String kpis = Utility.noNull(""+request.getParameter("kpiS"));
		String kpi2 = Utility.noNull(""+request.getParameter("kpi2"));
		*/
		String kpi1 = Utility.noNull(""+request.getParameter("xcdel"));
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
					
			sql   = "select count(*) from rel_user_kpi where idkpi = '"+kpi1+"'";
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				resp_app = rs.getString("count");
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


			resp = resp_app;
		
		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;

@WebServlet("/smnotification")
public class smnotification extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public smnotification() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String flag = request.getParameter("flag");
		String tab = request.getParameter("Tab");
		String ids = request.getParameter("id");
		String idusr = request.getParameter("idUSR");
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnAdmin();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("tab = " + tab);
		
		if (tab.equals("Templ-Exp") || tab == "Templ-Exp"){
		
			try {
	
				if (flag == "O" || flag.equals("O")){
					sql = "update znotif set stato = 'A' where idnota = "+ids;
				}else{
					sql = "update znotif set stato = 'A' where idusr = '"+idusr+"' ";
				}
				st.executeUpdate(sql);
			}catch(Exception e) {
				e.printStackTrace();
			}

		}else{
			
			String idusr_ = request.getParameter("idusr");
			
			try {

				sql  =  "select count(*) from znotif where idusr = '"+idusr_+"' and stato <> 'A'";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					resp=rs.getString("count");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

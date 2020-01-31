package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pkg.utility.Utility;


@WebServlet("/GetHRDepartment")
public class GetHRDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetHRDepartment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		

		HttpSession session = request.getSession();
		
		String ids = request.getParameter("ids");
		String dat = request.getParameter("dat");
		ids = Utility.noNull(""+ids);
		dat = Utility.noNull(""+dat);

		String upd = "";
		
		Connection conn = null;
		Statement st = null;
		
		ResultSet rs = null;
		String sql = "";
		String sql2 = "";
		conn=Utility.getConnHR();

		String resp = "<option value=''>-</option>";
		
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
		
		try {
			
			st=conn.createStatement();
			
				sql   = "select * from hr_departments where id_customer = "+session.getAttribute("idcus");
				System.out.println("sql = " + sql);
				rs=st.executeQuery(sql);
				
				while(rs.next()) {
					resp += "<option value='"+rs.getString("id")+"'>"+rs.getString("description")+"</option>";
				}			
			
				
		} catch (SQLException e) {
				e.printStackTrace();
				resp = "KO";
		}
		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

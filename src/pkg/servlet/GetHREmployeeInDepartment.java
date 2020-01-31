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

@WebServlet("/GetHREmployeeInDepartment")
public class GetHREmployeeInDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetHREmployeeInDepartment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		

		

		HttpSession session = request.getSession();
		
		String ids = request.getParameter("dep");
		ids = Utility.noNull(""+ids);

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
			
				sql   = "select a.id_employee, b.name, b.surname from rel_dep_employee a, hr_employee b where a.id_employee = b.id and a.id_departments = '"+ids+"'  and b.id_customer = "+session.getAttribute("idcus");
				System.out.println("sql = " + sql);
				rs=st.executeQuery(sql);
				
				while(rs.next()) {
					resp += "<option value='"+rs.getString("id_employee")+"'>"+rs.getString("name") + " "+ rs.getString("surname") +"</option>";
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

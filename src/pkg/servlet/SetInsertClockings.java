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
import javax.servlet.http.HttpSession;

import pkg.utility.Utility;


@WebServlet("/SetInsertClockings")
public class SetInsertClockings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetInsertClockings() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		String id_dep = request.getParameter("dep");
		id_dep = Utility.noNull(id_dep);
		String id_dip = request.getParameter("dip");
		id_dip = Utility.noNull(id_dip);
		String dat = request.getParameter("dat");
		dat = Utility.noNull(dat);
		String hou = request.getParameter("hou");
		hou = Utility.noNull(hou);
		String rag = request.getParameter("rag");
		rag = Utility.noNull(rag);
		String not = request.getParameter("not");
		not = Utility.noNull(not);
		String app = request.getParameter("app");
		app = Utility.noNull(app);
		
		Connection conn = null;
		Statement st = null;
		
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnHR();

		String resp = "OK";
		String badge = "";
		
		String timestamp_ = dat + " " + hou+":00+02";
		System.out.println("timestamp_ = " + timestamp_);
		
		try {
			
			st=conn.createStatement();
			
			sql   = "select * from rel_emp_badge r , hr_badge b where id_employ = '"+id_dip+"' and r.id_badge = b.id";
			System.out.println("sql = " + sql);
			rs=st.executeQuery(sql);
			
			if(rs.next()) {
				badge = rs.getString("number");
			}

			sql = "INSERT INTO public.hr_clockings(" + 
					"            id_employee, badge, reason, \"timestamp\", note_justificative, " + 
					"            approved, exported, id_departments, autoid, timeins) " + 
					"    VALUES ('"+id_dip+"', '"+badge+"', '"+rag+"', '"+timestamp_+"', '"+not.replaceAll("'", "''")+"', '"+app+"', '0', '"+id_dep+"', 0, '"+timestamp_+"')";
			System.out.println("sql_ins = " + sql);
			st.executeUpdate(sql);
				
				
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

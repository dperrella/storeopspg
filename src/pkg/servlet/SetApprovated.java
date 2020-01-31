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

@WebServlet("/SetApprovated")
public class SetApprovated extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetApprovated() {
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

		String resp = "OK";
		
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
		
		try {
			
			st=conn.createStatement();
			
				sql   = "select note_justificative from hr_clockings where id_employee = '"+ids+"' "
						+ "and timestamp::date = '"+day.format(Long.parseLong(dat))+"'::date "
						+ "and timestamp::time = '"+hour.format(Long.parseLong(dat))+"'::time ";
				System.out.println("sql = " + sql);
				rs=st.executeQuery(sql);
				
				if(rs.next()) {
					resp = rs.getString("note_justificative")+" approvato da: "+session.getAttribute("username");
				}
				
				resp = Utility.noNull(""+resp);
			
				sql = "update hr_clockings set note_justificative = '"+resp.replaceAll("'","''")+"' , approved = '1' "
						+ "where id_employee = '"+ids+"' "
						+ "and timestamp::date = '"+day.format(Long.parseLong(dat))+"'::date "
						+ "and timestamp::time = '"+hour.format(Long.parseLong(dat))+"'::time ";
				System.out.println("sql = " + sql);
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

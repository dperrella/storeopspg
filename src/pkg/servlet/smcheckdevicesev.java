package pkg.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;

@WebServlet("/smcheckdevicesev")
public class smcheckdevicesev extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public smcheckdevicesev() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**/
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int Hh = cal.get(Calendar.HOUR_OF_DAY);
		
		month = (month + 1);


		String idStore = "";
		String flags = "";
		String tabname = "";
		String tab = "";
		int flag = 0;
		int flag2 = 0;
		String tabint = "";
		String tablasttime = "";
		String tabunintime = "";
		String tabtimezone = "";
		
		String idDev = "";
		String DevType = "";
		String s = "";
		String ss = "";
		int k = 0;
		int chk = 1;
		
		Connection conn = null;
		Statement st = null;
		
		Connection conn2 = null;
		Statement st2 = null;

		
		ResultSet rs = null;
		String sql = "";
		String sql_ = "";
		conn=Utility.getConnCounter();
		conn2=Utility.getConnControl();
		String resp = "";
		try {
			st=conn.createStatement();
			st2=conn2.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			if (request.getParameter("chk").equals("EV")) {
				chk = 1;
			}else {
				chk = 2;
			}

				//sql = "select * from devices where accountid = '"+request.getParameter("account")+"' and storeid = '"+request.getParameter("store")+"' and doorid = '"+request.getParameter("door")+"'";
				sql = "select * from devices where ip = '"+request.getParameter("ip")+"' and port = '"+request.getParameter("port")+"' and device_type = "+chk;
				System.out.println("sql = " + sql);
				rs = st.executeQuery(sql);
				if(rs.next()) {
					flag++;
					//idStore = rs.getString("id_store");
					idDev = rs.getString("id");
					DevType = rs.getString("device_type");
				}
				rs.close();
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			
			if (!"".equals(DevType)) {
				
				String command = "java -classpath \".;c:\\checknow\\smartcounter_lib\\*;c:\\checknow\\*;\" smartcontrol.CheckNow2 " + idDev;
				System.out.println("command = " + command);
				
				try {
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
					// reading output stream of the command
					while ((s = inputStream.readLine()) != null) {
						
						System.out.println(s);
						ss += s;
						
					}
					p.waitFor();
					k = p.exitValue();
					System.out.println("k = " + k);
					
					
					flags = "OK";
					
				} catch (Exception e) {
					e.printStackTrace();
					flags = "KO";
					
				}
				
				
			}else {
				flags = "KO";
			}
		
		
		resp = flags + "$" + k + "$" + ss;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
			
	/**/	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

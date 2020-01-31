package pkg.posws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import pkg.beans.pos.Stores_;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;


@WebServlet("/smcontrolzpl")
public class smcontrolzpl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public smcontrolzpl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		String tab = request.getParameter("tab");
		String flag = request.getParameter("flag");
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnPos();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
if(tab.equals("Sto")){
			
			
			
			if (flag.equals("E")){
				

				String ids = request.getParameter("id");
				try {
					
					sql = "delete from app_stores where idclient = '"+ids+"'";
					st.executeUpdate(sql);
				}catch(Exception e) {
					e.printStackTrace();
				}
	
				resp = propLang.get("msgSuccess");
				
			}else if (flag.equals("M")){
			
				String ids = request.getParameter("id");
				String desc = request.getParameter("desc");
				String connstr = request.getParameter("conn");
				String ip = request.getParameter("ip");
				String userdb = request.getParameter("userdb");
				String pwddb = request.getParameter("pwddb");
				String nomedb = request.getParameter("nomedb");
				
			try{
				sql = "UPDATE app_stores "+					
					  "SET descrizione='"+desc+"', conn_string='"+connstr+"', ip='"+ip+"', utenzadb='"+userdb+"', passworddb='"+pwddb+"', nomedb='"+nomedb+"'" +	
       				  "WHERE idclient = '"+ids+"'";
					st.executeUpdate(sql);
				System.out.println("update = " + sql);
			}catch(Exception e) {
				e.printStackTrace();
			}
				resp = propLang.get("msgSuccess");
			
			}else{
				// insert
				
				
				String ids = request.getParameter("id");
				String desc = request.getParameter("desc");
				String connstr = request.getParameter("conn");
				String ip = request.getParameter("ip");
				String userdb = request.getParameter("userdb");
				String pwddb = request.getParameter("pwddb");
				String nomedb = request.getParameter("nomedb");
			
			try{

				sql = "INSERT INTO app_stores(idclient, descrizione, conn_string, ip, utenzadb, passworddb, nomedb) "+ 
					  "VALUES "+
					  "('"+ids+"', '"+desc+"', '"+connstr+"', '"+ip+"', '"+userdb+"', '"+pwddb+"', '"+nomedb+"')";
					System.out.println("sql insert = " + sql);
					st.executeUpdate(sql);

					
			}catch(Exception e) {
				e.printStackTrace();
			}
				resp = propLang.get("msgSuccess");
			
			}
		}

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
	
	}


	
}

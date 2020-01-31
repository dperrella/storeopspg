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

import pkg.properties.ReadProperties;
import pkg.utility.Utility;

@WebServlet("/smnotificationRead")
public class smnotificationRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public smnotificationRead() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**/
		HttpSession session = request.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		String username = (String) session.getAttribute("username");
		String analyt = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String sql_ = "";
		conn=Utility.getConnAdmin();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// -------------------------------
		// Legge le notifiche
		int numNotif = 0;
		String rigaNotif = "";
		String alertNotif = "";
		String notif = "";
		String reads = "";
		
			try {
				
				sql   = "select count(*) from znotif where upper(idusr) = upper('"+username+"') and stato <> 'A'";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					numNotif = Integer.parseInt(rs.getString("count"));
				}
				//System.out.println("numNotif = " + numNotif);
				rs.close();
				
				sql   = "select * from znotif where idusr = '"+username+"' and stato <> 'A' order by idnota desc limit 5 ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					if(!rs.getString("stato").equals("A")){
						//numNotif+=1;
					
						rigaNotif+= "<a href=\"../config/"+rs.getString("link")+"?id="+rs.getString("idnota")+"\" class=\"media list-group-item\">"+
					                " 	<span class=\"pull-left thumb-sm text-center\">"+rs.getString("img")+"</span>"+
					                "   <span class=\"media-body block m-b-none\">"+rs.getString("titolo")+"<br>"+
					                "      <small class=\"text-muted\">"+rs.getString("nota")+"</small>"+
					                "    </span>"+
					                "</a>";	
					}
					
				}
				rs.close();
				
				if(numNotif > 0){
					alertNotif = "<span id=\"spanCount\" class=\"badge badge-sm up bg-danger count\" style='font-size: 10px;'>"+numNotif+"</span>";
				}
				
				notif = "  <a href=\"#\" class=\"dropdown-toggle lt\" data-toggle=\"dropdown\">"+
				        "    <i class=\"icon-bell\"></i>"+alertNotif+
				        "  </a>"+
				        "  <section class=\"dropdown-menu aside-xl animated fadeInUp\">"+
				        "    <section class=\"panel bg-white\">"+
				        "      <div class=\"panel-heading b-light bg-light\">"+
				        "        <strong>"+propLang.get("hai")+" <span id=\"spanCount4\" class=\"count\">"+numNotif+"</span> "+propLang.get("notifiche")+"</strong>"+
				        "      </div>"+
				        "        <div class=\"list-group list-group-alt\">"+
				        rigaNotif+
				        "      </div>"+
				        "      <div class=\"panel-footer text-sm\">"+
				        "        <a href=\"../../app/config/notification-list.htm\" class=\"pull-right\"><i class=\"fa fa-cog fa-spin fa-fw\"></i></a>"+
				        "        <a href=\"../../app/config/notification-list.htm\">"+propLang.get("visnotif")+"</a>"+
				        "      </div>"+
				        "    </section>"+
				        "  </section>";
				
				st.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		response.setContentType("text/plain");
			

		
		resp = notif+"$"+numNotif;
		response.getWriter().write(resp);
			
	/**/	
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package pkg.posws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;
import pkg.beans.pos.Sm_DevnotP;


@WebServlet("/smmodality")
public class smmodality extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public smmodality() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Sm_DevnotP> devnp = new ArrayList<Sm_DevnotP>();
		
		devnp.clear();
		
		String flag = request.getParameter("flag");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnPos();
		String resp = "";
		
		
		Connection conn2 = null;
		Statement st2 = null;
		ResultSet rs2 = null;
		conn2 = Utility.getConnPos();
		
		try {
			st = conn.createStatement();
			st2 = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/**/
		
		String Tab = ""; int num_rows = 1;
		String a = "", b = "",c = "",d = "",e = "",f = "",g = "",h = "",i = "",l = ""; 
		String devname = ""; String sitename = ""; String ip = ""; String device_type = ""; String macaddr = "";
		try {
			
			if (flag.equals("all")){
				
				sql="select * from v_dnotpaired";
			}else if (flag.equals("ip")){
				
				sql="select * from v_dnotpaired where macaddr <> '' ";
			}else{
				//System.out.println("clear");
				sql="delete from devicenotpaired";
				st.executeUpdate(sql);
				
				sql="select * from v_dnotpaired";
				
			}
			
			
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				devname = rs.getString("devicename");
				sitename = rs.getString("sitename");
				ip = rs.getString("ip");
				device_type = rs.getString("device_type");
				macaddr = rs.getString("macaddr");
				if (devname == null){
					devname = "";
				}
				if (sitename == null){
					sitename = "";
				}
				if (ip == null){
					ip = "";
				}
				if (device_type == null){
					device_type = "";
				}
				if (macaddr == null){
					macaddr = "";
				}
				  //Tab = "<c:forEach items="${Devnp}" var="Devnp" varStatus="status">
				Tab = Tab + "<tr>" + 
					  "<td><span class='label bg-light'>"+rs.getString("id")+"</span></td>"+
					  "<td>"+rs.getString("deviceid")+"</td>"+
					  "<td>"+devname+"</td>"+
					  "<td>"+rs.getString("siteid")+"</td>"+
                      "<td>"+sitename+"</td>"+
					  "<td>"+rs.getString("tstamp")+"</td>"+			                      			                      
					  "<td>"+rs.getString("userstr")+"</td>"+
					  "<td>"+ip+"</td>"+
					  "<td>"+device_type+"</td>"+
					  "<td>"+macaddr+"</td>"+			                      			                      
					"</tr>";
				  //</c:forEach>
				
				/*
				a = rs.getString("id");
				b = rs.getString("deviceid");
				c = rs.getString("devicename");
				d = rs.getString("siteid");
				e = rs.getString("sitename");
				f = rs.getString("tstamp");
				g = rs.getString("userstr");
				h = rs.getString("ip");
				i = rs.getString("device_type");
				l = rs.getString("macaddr");
				devnp.add(new Sm_DevnotP(a,b,c,d,e,f,g,h,i,l));
				*/
			}
			
			

		    
			rs.close();
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		/**/

		System.out.println("TAB = " + num_rows);
		
		resp = Tab+"$"+flag;
		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

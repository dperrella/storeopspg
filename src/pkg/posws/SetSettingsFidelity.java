package pkg.posws;

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

@WebServlet("/SetSettingsFidelity")
public class SetSettingsFidelity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SetSettingsFidelity() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

/**/
		HttpSession session = request.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String app = "";
		conn = Utility.getConnPos();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		/**/
		String flag = "";

		try{
			
			sql = "select * from fidelity_param ";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				app = rs.getString("valpunto");
			}
			
			rs.close();
			
			
			
				if ("".equals(app)) {
	    		sql = "INSERT INTO public.fidelity_param(valpunto, valvaluta, percentuale, step)" + 
	    				"    VALUES ("+request.getParameter("valp")+", "+request.getParameter("vale")+", "+request.getParameter("perc")+", "+request.getParameter("vals")+");";
				}else {
					sql = "UPDATE public.fidelity_param SET valpunto="+request.getParameter("valp")+", valvaluta="+request.getParameter("vale")+", percentuale="+request.getParameter("perc")+", step="+request.getParameter("vals");
				}
				System.out.println("sql = "+ sql);
			st.executeUpdate(sql);
			flag = "OK";

		}catch(Exception e){
			e.printStackTrace();
			flag = "KO";
		}
		

		
				
		resp = flag;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);	
/**/
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

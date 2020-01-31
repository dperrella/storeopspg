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

@WebServlet("/GetFidelityControlImport")
public class GetFidelityControlImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetFidelityControlImport() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		HttpSession session = request.getSession();
		//ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		double punti = 0;
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
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
		String sign = "";
		int app = 0;
		int apppunts = 0;

		/**/
		try{
			sql = "select sum(puntifidelity_detail) punti from fidelity_detail  where codicefidelity_detail = '"+request.getParameter("card")+"'";
			rs = st.executeQuery(sql);
			System.out.println("SQL3 = " + sql);
			if(rs.next()) {
				app = Integer.parseInt(rs.getString("punti"));	
			}
			rs.close();

			apppunts = Integer.parseInt(request.getParameter("punts"));
			if (punti >= apppunts) {
				flag = "OK";
			}else {
				flag = "KO";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			flag = "KO";
		}
		

				
		resp = flag;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);		

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

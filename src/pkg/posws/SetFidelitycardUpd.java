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


@WebServlet("/SetFidelitycardUpd")
public class SetFidelitycardUpd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SetFidelitycardUpd() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		HttpSession session = request.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

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

		/**/
		try{

			sql = "UPDATE fidelitycard "+
					"SET fidelityname='"+request.getParameter("nam")+"', fidelitysurname='"+request.getParameter("sur")+"', fidelitysex='"+request.getParameter("sex")+"', "+ 
					"fidelityaddress='"+request.getParameter("add")+"', fidelitytel='"+request.getParameter("tel")+"', fidelitymail='"+request.getParameter("ema")+"', fidelitycodfiscale='"+request.getParameter("fis")+"', "+ 
					"fidelitycategory='"+request.getParameter("cat")+"', fidelitystatus='"+request.getParameter("sta")+"', fidelitybirth='"+request.getParameter("bir")+"', fidelitycity='"+request.getParameter("com")+"' "+
					"WHERE idfidelity = '"+request.getParameter("idf")+"'";
			//System.out.println("sql = "+ sql);
			st.executeUpdate(sql);
			flag = "OK";
		}catch(Exception e){
			e.printStackTrace();
			flag = "KO";
		}
				
		resp = flag;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

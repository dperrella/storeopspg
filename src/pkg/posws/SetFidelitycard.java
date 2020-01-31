package pkg.posws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;



@WebServlet("/SetFidelitycard")
public class SetFidelitycard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetFidelitycard() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		
		
		
		
		/*Random String*/
        //String saltStr = Utility.RandomString();
		String saltStr = request.getParameter("cof");
		/**/
		try{
			
			sql = "select idfidelity from fidelitycard where fidelitycode = '"+saltStr+"'";
			System.out.println("sql = "+ sql);
			rs = st.executeQuery(sql);
			if(rs.next()) {
				app = rs.getString("idfidelity");
			}
			
			rs.close();
			
			
			
			if ("".equals(app)) {
    		sql = "INSERT INTO fidelitycard(idfidelity, fidelityname, fidelitysurname, fidelitysex, fidelityaddress,fidelitytel, fidelitymail, fidelitycodfiscale, fidelitycategory, fidelitystatus, fidelitycode, fidelitydate, fidelitybirth, fidelitycity) "+
    				"VALUES (uuid_generate_v1(), '"+request.getParameter("nam")+"', '"+request.getParameter("sur")+"', '"+request.getParameter("sex")+"', '"+request.getParameter("add")+"', "+ 
    						"'"+request.getParameter("tel")+"', '"+request.getParameter("ema")+"', '"+request.getParameter("fis")+"', '"+request.getParameter("cat")+"', "+ 
    						"'"+request.getParameter("sta")+"', '"+saltStr+"', now(), '"+request.getParameter("bir")+"', '"+request.getParameter("com")+"')";
			//System.out.println("sql = "+ sql);
			st.executeUpdate(sql);
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
		doGet(request, response);
	}

}

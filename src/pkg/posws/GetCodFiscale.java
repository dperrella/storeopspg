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

@WebServlet("/GetCodFiscale")
public class GetCodFiscale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetCodFiscale() {
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
		String codf = "";
		
		
		/*Random String*/
        String saltStr = Utility.RandomString();
		/**/
		try{
    		sql = "select * from fidelitycard where upper(fidelitycodfiscale) = upper('"+request.getParameter("fis")+"')";
    		//System.out.println("sql = " + sql);
    		rs = st.executeQuery(sql);
    		
    		if(rs.next()) {
    			codf = rs.getString("fidelitycodfiscale");
    		}
    		rs.close();
    		
    		if (!"".equals(codf)){
    			flag = "KO";    			
    		}else{
    			flag = "OK";
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

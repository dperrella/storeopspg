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


@WebServlet("/GetFidelityServiceImport")
public class GetFidelityServiceImport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetFidelityServiceImport() {
        super();
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
		
		punti = Utility.CalcPunti(request.getParameter("importo"), "S");

		sign = "-";

		/**/
		try{
    		sql = "INSERT INTO public.fidelity_detail( " + 
    				"	codstorefidelity_detail, datafidelity_detail, codicefidelity_detail, importofidelity_detail, puntifidelity_detail, flagfidelity_detail) " + 
    				"	VALUES ('"+request.getParameter("negozio")+"', now(), '"+request.getParameter("id_scheda")+"', "+sign+request.getParameter("importo")+","+sign+punti+", 'S');";
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
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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


@WebServlet("/SetFidelitycardDetailScarico")
public class SetFidelitycardDetailScarico extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SetFidelitycardDetailScarico() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/**/
		HttpSession session = request.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

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
		
		punti = Utility.CalcPuntiScarico(request.getParameter("impo"), request.getParameter("flag"));

		
		if (request.getParameter("flag").equals("S")) {
			sign = "-";
		}
		
		System.out.println("punti = "+ punti);
		/**/
		try{
    		sql = "INSERT INTO public.fidelity_detail( " + 
    				"	codstorefidelity_detail, datafidelity_detail, codicefidelity_detail, importofidelity_detail, puntifidelity_detail, flagfidelity_detail) " + 
    				"	VALUES ('"+request.getParameter("nego")+"', '"+request.getParameter("date")+"', '"+request.getParameter("codi")+"', "+sign+punti+","+sign+request.getParameter("impo")+", '"+request.getParameter("flag")+"');";
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

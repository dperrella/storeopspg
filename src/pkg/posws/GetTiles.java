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

import pkg.utility.Utility;


@WebServlet("/GetTiles")
public class GetTiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetTiles() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String stores = request.getParameter("fis");

		String idNew = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "", sql1 = "";
		conn = Utility.getConnPosext();
		String resp = "";
		int j = 0;
		
		String pezzi = "";
		String scontrini = "";
		String incasso = "";
		String scontrino_medio = "";
		
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/**/

		try {
			
			sql = "select * from totali_giornalieri where idclient = '"+stores+"'";
			System.out.println("sql = " + sql);
			rs = st.executeQuery(sql);
			if(rs.next()) {
				incasso = rs.getString("incasso").replace(".", ",");
				pezzi = rs.getString("pezzi");
				scontrini = rs.getString("scontrini");
				scontrino_medio = rs.getString("scontrino_medio").replace(".", ",");
			}
			rs.close();		
			//conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		/*
		obj.put("Incasso", incasso);
		obj.put("Pezzi", pezzi);
		obj.put("Scontrini", scontrini);
		obj.put("ScontrinoMedio", scontrino_medio);
		*/
		resp = incasso+"$"+pezzi+"$"+scontrini+"$"+scontrino_medio;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

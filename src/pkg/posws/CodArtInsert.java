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

@WebServlet("/CodArtInsert")
public class CodArtInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CodArtInsert() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String stores = request.getParameter("rec");

		String idNew = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "", sql1 = "";
		conn=Utility.getConnPos();
		String resp = "";
		int j = 0;

		/**/
		
		String flag = "OK";
		String[] parts = stores.split("-");
		int conts = parts.length;

		try {
			
			System.out.println("parts = "+ conts);
			
			for (int i = 0; i < conts; i++) {
				sql += "insert into codartload (codart, dataload) values ('"+parts[i]+"',now());";
			}
			
			st.executeUpdate(sql);
			flag = "OK";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = "KO";
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

		resp = flag;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

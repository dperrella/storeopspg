package pkg.servlet;

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

@WebServlet("/GetSteps")
public class GetSteps extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetSteps() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String flows = request.getParameter("flow");
		String $idstepdef = "";

		String idNew = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "", sql1 = "";
		conn=Utility.getConnRfid();
		String resp = "";
		int j = 0;
	
		
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	

		try {
			


			sql  =  "select * from documentsflowdetails where idflow = '"+flows+"'";
			System.out.println(sql);
			// Prepara il cursore
			
			// Esegue l'sql
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				$idstepdef += "<option value='"+rs.getString("step")+"'>"+rs.getString("description")+"</option>";
				
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

		resp = $idstepdef;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

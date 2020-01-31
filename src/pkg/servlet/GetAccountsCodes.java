package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;


public class GetAccountsCodes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetAccountsCodes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String $id_customer = request.getParameter("idcust");
		String resp = "";
		String accountCode = "";
		
		try {
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			
			
				sql = "select account_code from customers where id = "+$id_customer;
				//System.out.print("sql = " + sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					accountCode = rs.getString("account_code");
				}
				rs.close();
				accountCode = Utility.noNull(""+accountCode);
			
			
		}
		catch(Exception e) {
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
		
		resp = accountCode;
		
		response.setContentType("text/plain");
		response.getWriter().write(resp);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

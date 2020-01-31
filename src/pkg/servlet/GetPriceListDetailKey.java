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


public class GetPriceListDetailKey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetPriceListDetailKey() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String $idprice = request.getParameter("idprice");
		String $idproduct = request.getParameter("idproduct");
		
		$idprice = Utility.noNull(""+$idprice);
		$idproduct = Utility.noNull(""+$idproduct);
		
		String resp = "";
		
		try {
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			
				sql = "select * from price_list_detail where price_list_code = '"+$idprice+"' and products_code = '"+$idproduct+"'";
				System.out.print("sql = " + sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					resp = rs.getString("price_list_code");
				}
				rs.close();
				resp = Utility.noNull(""+resp);
			
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

		
		response.setContentType("text/plain");
		response.getWriter().write(resp);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

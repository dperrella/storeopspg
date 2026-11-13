package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetLog
 */
@WebServlet("/servlet/ws/GetLogChart")
public class GetLogChart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLogChart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		HttpSession session = request.getSession();
		
		try {
			conn=Utility.getConnControl();
			st=conn.createStatement();
			
			String uc = ""+request.getParameter("uc");
			uc = Utility.noNull(uc);
			String country = ""+request.getParameter("country");
			country = Utility.noNull(country);
			String cond = "";
			if(uc.length()>0)
				cond+= " and v_dashlog_2_new.under_costruction = "+uc+" ";
			if(country.length()>0)
				cond+= " and v_dashlog_2_new.customer_country_id = "+country+" ";
			
			
			sql   = "select v_dashlog_2_new.brandname, count(store) num  \n" + 
					"from v_dashlog_2_new left join stores on v_dashlog_2_new.id = stores.id::text \n" + 
					"where stores.id in(select idstore::integer from rel_user_stores where idusr = '"+session.getAttribute("username").toString().trim().toUpperCase()+"') "+cond+" \n" + 
					"group by v_dashlog_2_new.brandname\n" + 
					"order by v_dashlog_2_new.brandname \n";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			int tot = 0;
			String cus = "";
			String data = "";
			while(rs.next()) {
				tot+=rs.getInt("num");
				
				cus+=rs.getString("brandname")+",";
				data+=rs.getString("num")+",";
				
				
			}
			rs.close();
			
			if(cus.length()>0)
				cus=cus.substring(0, cus.length()-1);
			if(data.length()>0)
				data=data.substring(0, data.length()-1);
			
			String output = tot+"##@@"+cus+"##@@"+data;
			
			response.setContentType("application/text;charset=UTF-8");
			response.getWriter().write(output);
			
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package pkg.billing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.utility.Utility;

public class PaymentTypeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> tab = new ArrayList<Tables2>();
		tab.clear();
		

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnAdmin();
			st=conn.createStatement();

			sql   = "select * from payments_type ";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				String active_ = "";
				
				if (rs.getString("active").equals("f")) {
					active_ = "<i class='fa fa-thumbs-o-down text-danger'></i>";
				}else {
					active_ = "<i class='fa fa-thumbs-o-up text-success'></i>";
				}
				
				
				tab.add(new Tables2(rs.getString("code"),rs.getString("description"),active_,"","","","","","","","","",""));
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("tab", tab);
		obj.put("msg", msg);
		
		
		
		return new ModelAndView("/app/billing/payment_type-list", obj);
	}
	
}

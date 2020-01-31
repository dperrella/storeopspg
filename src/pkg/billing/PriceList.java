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

public class PriceList extends AbstractController {
	
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

			//sql   = "select * from price_list left";
			sql = "select price_list.*, currency.description currency_code_desc, price_list_type.description price_list_desc from price_list " + 
					"left join price_list_type on price_list.price_list_type = price_list_type.code " + 
					"left join currency on price_list.currency_code = currency.code";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				String active_ = "";
				
				if (rs.getString("active").equals("f")) {
					active_ = "<i class='fa fa-thumbs-o-down text-danger'></i>";
				}else {
					active_ = "<i class='fa fa-thumbs-o-up text-success'></i>";
				}
				
				
				tab.add(new Tables2(rs.getString("code"),rs.getString("description"),rs.getString("validity_date"),rs.getString("currency_code_desc"),rs.getString("price_list_desc"),active_,"","","","","","",""));
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
		
		
		
		return new ModelAndView("/app/billing/price_list", obj);
	}
	
}

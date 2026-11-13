package eu.mcsistemi.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.utility.Utility;



public class ItemsController extends AbstractController {
	   @Override
	    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        Map<String, Object> obj = new HashMap<String, Object>();
	        
	        HttpSession session = req.getSession();
	        String username = session.getAttribute("username").toString();
	        obj.put("idusr",username);
	        
	        String role = session.getAttribute("tipo").toString();
	        obj.put("role",role);
	        
	        List<Tables> cus = new ArrayList<Tables>();
			cus.clear();
			
	        
	        // Retailer List
	        try {
		        Connection conn = Utility.getConnData();
		        Statement st = conn.createStatement();
		        ResultSet rs = null;
		        String sql   = "select id, account_code, description, "
						+ "case when (select idcustomer "
						+ "from pref_user_customers where idusr = '"+username.toUpperCase()+"' and idcustomer = id) "
						+ "is null then 0 else 1 end checked "
						+ "from customers "
						+ "where "
						+ "id in(select idcustomer from rel_user_customers "
						+ "where idusr = '"+username.toUpperCase()+"') "
						+ "and id in(select idcustomer from ats.rel_item_customer) order by description";
				System.out.println("----\n"+sql+"\n----");
				rs=st.executeQuery(sql);
				
				while(rs.next()) {
					if(rs.getInt("checked")==1) {
						cus.add(new Tables("CUS",rs.getString("id"), rs.getString("description"),rs.getString("account_code"),"","",""));
					}
				}
				rs.close();
				st.close();
				conn.close();
				
				obj.put("cus", cus);
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        return new ModelAndView("/source/anag/items-list", obj);
	    }
}

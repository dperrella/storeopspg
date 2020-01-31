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

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.utility.Utility;

public class FiltersController extends AbstractController {
	@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
        
        HttpSession session = req.getSession();
        String username = session.getAttribute("username").toString();
        
        List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			
			conn = Utility.getConnData();
			st = conn.createStatement();
			
			sql   = "select id, account_code, description, "
					+ "case when (select idcustomer "
					+ "from pref_user_customers where idusr = '"+username.toUpperCase()+"' and idcustomer = id) "
					+ "is null then 0 else 1 end checked "
					+ "from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+username.toUpperCase()+"') "
					+ "and id in(select idcustomer from ats.rel_item_customer) ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				String chk = "";
				if(rs.getInt("checked")==1)
					chk = "checked";
				cus.add(new Tables(rs.getString("account_code"),rs.getString("id"),rs.getString("description"),
						rs.getString("checked"),chk,"",""));
				
			}
			rs.close();
			
			
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
		
		obj.put("cus", cus);
        obj.put("userId",username.trim().toUpperCase());
        return new ModelAndView("/source/anag/filters", obj);
        
    }
}

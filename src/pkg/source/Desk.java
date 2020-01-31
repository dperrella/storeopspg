package pkg.source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import eu.mcsistemi.dao.ItemDAO;
import eu.mcsistemi.dao.ItemDAOImpl;
import eu.mcsistemi.dao.StoreDAO;
import eu.mcsistemi.dao.StoreDAOImpl;
import eu.mcsistemi.model.Item;
import eu.mcsistemi.model.Store;
import pkg.beans.Tables;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		HttpSession session = req.getSession();
		
		Connection connAdmin = null;
		
			connAdmin = Utility.getConnAdmin();
			PreparedStatement st = null;
			ResultSet rs = null;
			int numItems = 0;
			int numRequests = 0;
			int numAudits = 0;
			String sql = "";
			try {
				sql = "select(select count(1) "+ 
					  "from ats.items where protected='1') as numItems, "+
					  "(select count(1) "+  
					  "from ats.audit) as numAudits, "+
					  "(select count(1) "+ 
					  "from ats.items where protected='0') as numRequests ";
				st = connAdmin.prepareStatement(sql);
				rs = st.executeQuery();
				while(rs.next()) {
					numItems = rs.getInt("numItems");
					numRequests = rs.getInt("numRequests");
					numAudits = rs.getInt("numAudits");
				}

		}
		catch(Exception e) {
			
		}
		finally {
			try {
				if(connAdmin!=null)
					connAdmin.close();
			}
			catch(Exception e) {}
		}
		System.out.println(numItems);
		System.out.println(numRequests);
		System.out.println(numAudits);
		obj.put("numItems", numItems);
		obj.put("numRequests", numRequests);
		obj.put("numAudits", numAudits);
		return new ModelAndView("/source/main/index", obj);
	}
	
	

}

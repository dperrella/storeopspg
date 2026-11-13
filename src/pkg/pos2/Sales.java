package pkg.pos2;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.ccn.Stores;
import pkg.utility.Utility;

public class Sales extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		HttpSession session = req.getSession();
		
		List<Stores> sto = new ArrayList<Stores>();
		sto.clear();
		
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection connAdmin = null;
		
		try {
			connAdmin = Utility.getConnAdmin();
			
			//sto = Stores.getStoresByIdCustomer(connAdmin, Integer.parseInt(idcus));
			sto = Stores.getStoresByIdCustomerAndUsername3(connAdmin, Integer.parseInt(session.getAttribute("idcus").toString()), session.getAttribute("username").toString());
			obj.put("sto", sto);
			obj.put("from", iso.format(new Date()));
			obj.put("to", iso.format(new Date()));
			
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
		
		return new ModelAndView("/pos2/sales/sales", obj);
	}
	
	

}

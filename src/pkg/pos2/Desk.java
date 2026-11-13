package pkg.pos2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

import pkg.beans.Tables;
import pkg.beans.ccn.Stores;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		HttpSession session = req.getSession();
		
		// Lista clienti da passare alla pagina
		List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
		
		List<Stores> sto = new ArrayList<Stores>();
		sto.clear();
		
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection connAdmin = null;
		
		try {
			connAdmin = Utility.getConnAdmin();
			
			// NON TOCCARE
			// =================
			// campi passati dalla request
			String idcus = ""+req.getParameter("idcus");
			// in caso nu null lo imposto a *blank (vuoto)
			idcus = Utility.noNull(idcus);
			int nr_customers = 0;
			// Se dalla request è vuoto legge dalla session
			if(idcus.length()==0) {
				idcus = ""+session.getAttribute("idcus");
				idcus = Utility.noNull(idcus);
			}
			if(idcus.length()>0) {
				session.setAttribute("idcus", idcus);
				nr_customers = 1;
			}
			
			Tables t = new Tables();
			cus = t.selectCustomers(connAdmin, (String)session.getAttribute("username"));
			if(idcus.length()==0) {
				nr_customers = cus.size();
			}
			obj.put("cus", cus);
			obj.put("idcus", idcus);
			obj.put("nr_customers", nr_customers);
			// =================
			// FINE NON TOCCARE
			
			if(idcus.length()>0) {
				//sto = Stores.getStoresByIdCustomer(connAdmin, Integer.parseInt(idcus));
				sto = Stores.getStoresByIdCustomerAndUsername3(connAdmin, Integer.parseInt(idcus), session.getAttribute("username").toString());
				obj.put("sto", sto);
				
				obj.put("from", iso.format(new Date()));
				obj.put("to", iso.format(new Date()));
				
				obj.put("dates", iso.format(new Date())+" / "+iso.format(new Date()));
				
				
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
		
		return new ModelAndView("/pos2/main/index", obj);
	}
	
	

}

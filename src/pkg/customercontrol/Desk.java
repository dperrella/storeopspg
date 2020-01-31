package pkg.customercontrol;

import java.sql.Connection;
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
		
		Connection connAdmin = null;
		
		int countOk = 0;
		int countErr = 0;
		
		int count0 = 0;
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		
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
			
			// apps - has control FS?
			String apps = session.getAttribute("apps").toString();
			String temp[] = apps.split(";");
			boolean hasControl = false;
			for(String app : temp) {
				
				String fld[] = app.split("##@@");
				if(fld[0].trim().equalsIgnoreCase("SCN")) {
					hasControl = true;
				}
			}
			obj.put("hasControl", hasControl);
			
			
			// =================
			// FINE NON TOCCARE
			
			if(idcus.length()>0) {
				//sto = Stores.getStoresByIdCustomer(connAdmin, Integer.parseInt(idcus));
				sto = Stores.getStoresByIdCustomerAndUsernameAndre(connAdmin, Integer.parseInt(idcus), session.getAttribute("username").toString());
				
				obj.put("sto", sto);
				
				for(int i=0;i<sto.size();i++) {
					if(sto.get(i).isInError())
						countErr++;
					else
						countOk++;
					
					if(sto.get(i).getColor().equalsIgnoreCase("0-RED")) {
						count0++;
					}
					else if(sto.get(i).getColor().equalsIgnoreCase("1-YELLOW")) {
						count1++;
					}
					else if(sto.get(i).getColor().equalsIgnoreCase("2-GREY")) {
						count2++;
					}
					else if(sto.get(i).getColor().equalsIgnoreCase("3-GREEN")) {
						count3++;
					}
					else if(sto.get(i).getColor().equalsIgnoreCase("4-BLUE")) {
						count4++;
					}
					else {
						count2++;
					}
					
				}
				
			}
			
			
			
			obj.put("countOk", countOk);
			obj.put("countErr", countErr);
			
			obj.put("count0", count0);
			obj.put("count1", count1);
			obj.put("count2", count2);
			obj.put("count3", count3);
			obj.put("count4", count4);
			
			
			
			
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
		
		return new ModelAndView("/customercontrol/main/index", obj);
	}
	
	

}

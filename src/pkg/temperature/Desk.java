package pkg.temperature;

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
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		// Lista clienti da passare alla pagina
		List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
				
		HttpSession session = req.getSession();
		
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
		
		
		return new ModelAndView("/temperature/main/index", obj);
	}
	
}

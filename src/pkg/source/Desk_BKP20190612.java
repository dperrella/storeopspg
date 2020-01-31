package pkg.source;

import java.sql.Connection;
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

public class Desk_BKP20190612 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		HttpSession session = req.getSession();
		
		// Lista clienti da passare alla pagina
		List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
		
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
			
			StoreDAO storeDao = new StoreDAOImpl();
			List<Store> storeList = new LinkedList<>(storeDao.getStoresByUserAndCustomerId(connAdmin, session.getAttribute("username").toString(),Integer.parseInt(idcus)));
	        
			obj.put("storeList", storeList);
			obj.put("userId",session.getAttribute("username").toString());
			
			String xAxis = "";
			String series = "";
			int tot = 0;
			for(int i=0;i<storeList.size();i++) {
				
				xAxis+="'"+storeList.get(i).getDescription()+"',";
				series+=storeList.get(i).getItemCount()+",";
				tot+=storeList.get(i).getItemCount();
				
			}
			
			
			ItemDAO itemDao = new ItemDAOImpl();
			List<Item> itemsList = itemDao.getItemsByCustomerId(connAdmin, Integer.parseInt(idcus));
			
			obj.put("xAxis", xAxis);
			obj.put("series", series);
			obj.put("totalItems", tot);
			
			obj.put("itemsList", itemsList);
			
			
			
			
			
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
		
		return new ModelAndView("/source/main/index", obj);
	}
	
	

}

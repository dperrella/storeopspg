package pkg.rfid2;

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
import pkg.beans.rfid.Location;
import pkg.beans.rfid.Products;
import pkg.beans.rfid.Site;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StockSearch extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Products> products = new ArrayList<Products>();
		products.clear();
		
		List<Location> location = new ArrayList<Location>();
		location.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		obj.put("idcus", idcus);
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String idsite = req.getParameter("idsite");
		String dssite = "";
		
		obj.put("idsite", idsite);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			dssite = Site.getSiteDescriptionById(conn, Integer.parseInt(idsite));
			
			
			location = Location.getLocationByIdsite(conn, Integer.parseInt(idsite));
			obj.put("location", location);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("dssite", dssite);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/stock/stock-search", obj);
	}
	
}

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

import pkg.beans.rfid.Products;
import pkg.beans.rfid.Site;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StockList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Products> products = new ArrayList<Products>();
		products.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String sku = ""+req.getParameter("sku");
		sku=Utility.noNull(sku);
		
		obj.put("sku", sku);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			if(sku.length()==13) {
			
				Products prod = new Products();
				prod.setSku(sku);
				prod.selectRow(conn);
				
				obj.put("code", prod.getCode());
				obj.put("description", prod.getDescription());
				obj.put("sku", prod.getSku());
				
				
				// Selezione record (tutti)
				products = Products.getEpcProductsByIdCustomer(conn, idcus, sku);
			
			}
			
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
		
		obj.put("products", products);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/stock/stock-list", obj);
	}
	
}

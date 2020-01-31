package pkg.pos2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.pos2.StockFilters;
import pkg.utility.Utility;

public class StockDetail extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		List<StockFilters> loc = new ArrayList<StockFilters>();
		loc.clear();
		
		List<StockFilters> var = new ArrayList<StockFilters>();
		var.clear();
		List<StockFilters> forn = new ArrayList<StockFilters>();
		forn.clear();
		List<StockFilters> size = new ArrayList<StockFilters>();
		size.clear();
		
		String idclient = ""+req.getParameter("idclient");
		idclient = Utility.noNull(idclient);
		
		String q = ""+req.getParameter("q");
		q = Utility.noNull(q);
		
		
		obj.put("idclient", idclient);
		obj.put("q", q);
		
		Connection conn = null;
		
		try {
			conn = Utility.getConnPos();
			
			//sto = Stores.getStoresByIdCustomer(connAdmin, Integer.parseInt(idcus));
			loc = StockFilters.getLocationsByIdclient(conn, idclient);
			obj.put("loc", loc);
			
			var = StockFilters.getVariantByIdclient(conn, idclient);
			obj.put("var", var);
			forn = StockFilters.getSupplierByIdclient(conn, idclient);
			obj.put("forn", forn);
			size = StockFilters.getSizeByIdclient(conn, idclient);
			obj.put("size", size);
			
			
			
		}
		catch(Exception e) {
			
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		return new ModelAndView("/pos2/anag/stock-detail", obj);
	}
	
	

}

package pkg.nrf;

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
import pkg.beans.nrf.ConnectedStores;
import pkg.beans.nrf.Country;
import pkg.beans.nrf.Heatmap;
import pkg.beans.nrf.Linechart;
import pkg.beans.nrf.Pie;
import pkg.beans.nrf.Trend;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		ArrayList<Country> cnt = new ArrayList<>();
		cnt.clear();
//		
//		ArrayList<Country> naz = new ArrayList<>();
//		naz.clear();
//		
//		ArrayList<ConnectedStores> sto = new ArrayList<>();
//		sto.clear();
//		
//		ArrayList<Pie> pie = new ArrayList<>();
//		pie.clear();
//		
//		ArrayList<Heatmap> heatmap = new ArrayList<>();
//		heatmap.clear();
//		
//		ArrayList<Linechart> linechart = new ArrayList<>();
//		linechart.clear();
//		
//		ArrayList<Trend> trend = new ArrayList<>();
//		trend.clear();
//		
		Connection conn = null;
		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String today = df.format(new Date());
//		String year = today.substring(0,4);
//		
		try {
			
			conn = Utility.getConnData();
			cnt = Country.getContinent(conn);
//			naz = Country.getCountry(conn, "");
//			sto = ConnectedStores.getStores(conn, null, null, null);
//			pie = Pie.getPie(conn, year, null, null, null, null, "VIS");
//			heatmap = Heatmap.getHeatmap(conn, year+"-01-01", year+"-12-31", null, null, null, "VIS");
//			linechart = Linechart.getLinechart(conn, year, null, null, null, null, "VIS");
//			trend = Trend.getTrend(conn, year, null, null, null, null);
//			
			obj.put("cnt", cnt);
//			obj.put("naz", naz);
//			obj.put("sto", sto);
//			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		return new ModelAndView("/nrf/main/index", obj);
	}
	
	

}

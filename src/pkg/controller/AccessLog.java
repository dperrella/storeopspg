package pkg.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Stores;
import pkg.beans.Tables2;
import pkg.utility.Utility;

public class AccessLog extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> npa = new ArrayList<Tables2>();
		List<Tables2> nup = new ArrayList<Tables2>();
		
		// Pulisce la lista
		npa.clear();
		nup.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		HttpSession session = req.getSession();
		String arrayStores = (String) session.getAttribute("arrayStores");
		if(arrayStores.length()==0)
			arrayStores = "0";
		
		try {

		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		
		obj.put("dsfun", "Lista Access-Logs");
		obj.put("npa", npa);
		obj.put("nup", nup);
		obj.put("Today", time);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/config/access-log", obj);
	}
	
}

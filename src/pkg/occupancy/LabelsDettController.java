package pkg.occupancy;

import java.sql.Connection;
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

import pkg.occupancy.model2.Labels;
import pkg.utility.Utility;

public class LabelsDettController extends AbstractController {
	@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        HttpSession session = req.getSession();
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
        
        
        String lang = ""+req.getParameter("lang");
        lang = Utility.noNull(lang);
        lang=lang.replaceAll("\\?cd=undefined", "");
        
        obj.put("lang", lang);
        
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		obj.put("tipoForm", tipoForm);
		
		List<Labels> l = new ArrayList<Labels>();
		l.clear();
		
		Connection conn = null;
		String msg = "";
		
		ArrayList<String> ipr = new ArrayList<String>();
		
		try {
			// Apertura connessione
			conn = Utility.getConnData();
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>Confirm Delete Record</strong></div>";
			}
			
			if(!tipoForm.equals("I")) {
				
				l = Labels.getLabelsByLang(conn, lang, Integer.parseInt(idcus));
				obj.put("l", l);
				
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
		
		obj.put("msg", msg);
        
		return new ModelAndView("/occupancy/popup/labels-dett", obj);
	}
}

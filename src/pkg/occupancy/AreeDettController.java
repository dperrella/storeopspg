package pkg.occupancy;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.occupancy.model2.Aree;
import pkg.utility.Utility;

public class AreeDettController extends AbstractController {
	@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        HttpSession session = req.getSession();
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
        
        String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		ArrayList<String> ipr = new ArrayList<String>();
		
		Connection conn = null;
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnData();
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>Confirm Delete Record</strong></div>";
			}
			
			if(!tipoForm.equals("I")) {
				Aree aree = new Aree();
				aree.setId(Integer.parseInt(id));
				aree.setIdCustomer(Integer.parseInt(idcus));
				aree.selectRow(conn);
				obj.put("id", id);
				obj.put("idCustomer", aree.getIdCustomer());
				obj.put("description", aree.getDescription());
				obj.put("idStore", aree.getIdStore());
				obj.put("percWarning", aree.getPercWarning());
				obj.put("normalColor", aree.getNormalColor());
				obj.put("warningColor", aree.getWarningColor());
				obj.put("overthresholdColor", aree.getOverthresholdColor());
				obj.put("threshold", aree.getThreshold());
				obj.put("iprelay", aree.getIprelay());
				String temp[]=aree.getIprelay().split(";");
				for(String a : temp) {
					ipr.add(a);
				}
				obj.put("ipr", ipr);
				obj.put("useRelay", aree.isUseRelay());
				obj.put("relayCmd", aree.getRelayCmd());
				obj.put("relayType", aree.getRelayType());
				
				
				System.out.println("use_relay "+aree.isUseRelay());
				
				if(aree.isUseRelay())
					obj.put("chk", "checked");
				else
					obj.put("chk", "");
				
				obj.put("subArea", aree.isSubArea());
				System.out.println("sub_area "+aree.isSubArea());
				
				if(aree.isSubArea())
					obj.put("chk2", "checked");
				else
					obj.put("chk2", "");
				
				
				obj.put("zone", aree.isZone());
				System.out.println("zone "+aree.isZone());
				
				if(aree.isZone())
					obj.put("chk3", "checked");
				else
					obj.put("chk3", "");
				
				
				
				
				obj.put("ml", aree.isAreaMl());
				System.out.println("ml "+aree.isAreaMl());
				
				if(aree.isAreaMl())
					obj.put("chk4", "checked");
				else
					obj.put("chk4", "");
				
				obj.put("sm", aree.isAreaSm());
				System.out.println("sm "+aree.isAreaSm());
				
				if(aree.isAreaSm())
					obj.put("chk5", "checked");
				else
					obj.put("chk5", "");
				
			}
			
			obj.put("tipoForm", tipoForm);
			
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
        
		return new ModelAndView("/occupancy/anag/aree-dett", obj);
	}
}

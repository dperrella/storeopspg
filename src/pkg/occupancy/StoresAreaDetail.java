package pkg.occupancy;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.occupancy.model2.firebase.Area;
import pkg.utility.Utility;

public class StoresAreaDetail extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        
		Connection conn = null;
		String msg = "";
		
		String idStore = req.getParameter("idstore");
		idStore=idStore.replaceAll("\\?cd=undefined", "");
		String idArea = req.getParameter("idarea");
		idArea=idArea.replaceAll("\\?cd=undefined", "");
		
		obj.put("idstore", idStore);
		obj.put("idarea", idArea);
		
		try {
			System.out.println("user --> settings");
			
			// Apertura connessione
			conn = Utility.getConnData();
			Area bean = new Area();
			bean.setIdStore(Integer.parseInt(idStore));
			bean.setId(Integer.parseInt(idArea));
			
			bean.selectRow(conn);
			
			obj.put("percWarning", bean.getWarningPercentage());
			obj.put("threshold", bean.getThreshold());
			obj.put("description", bean.getDescription());
			
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
		
		return new ModelAndView("/occupancy/popup/stores-area-detail", obj);
	}
	
	

}

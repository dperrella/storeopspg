package pkg.occupancy;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.occupancy.model.StoresSettings;
import pkg.utility.Utility;

public class StoresHours extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        
		Connection conn = null;
		String msg = "";
		
		String idStore = req.getParameter("idstore");
		idStore=idStore.replaceAll("\\?cd=undefined", "");
		obj.put("idstore", idStore);
		
		try {
			System.out.println("user --> settings");
			
			// Apertura connessione
			conn = Utility.getConnData();
			StoresSettings bean = new StoresSettings();
			bean.setIdStore(Integer.parseInt(idStore));
			bean.selectRow(conn);
			
			obj.put("hhopen", bean.getHhopen());
			obj.put("hhclose", bean.getHhclose());
			
			String time1[] = bean.getHhopen().split(":");
			String time2[] = bean.getHhclose().split(":");
			obj.put("hh1", time1[0]);
			obj.put("mm1", time1[1]);
			obj.put("hh2", time2[0]);
			obj.put("mm2", time2[1]);
			
			System.out.println(bean.getHhopen()+" "+bean.getHhclose());
			
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
		
		return new ModelAndView("/occupancy/popup/stores-hours", obj);
	}
	
	

}

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

import pkg.beans.rfid.Client;
import pkg.beans.rfid.ReaderConfig;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.Step;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class GroupedStepDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		
		List<Step> steps = new ArrayList<Step>();
		steps.clear();
		
		HttpSession session = req.getSession();
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String idflow = ""+req.getParameter("idflow");
		idflow = Utility.noNull(idflow);
		idflow=idflow.replaceAll("\\?cd=undefined", "");
		
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		Connection conn = null;
		String msg = "";
		
		try {
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";
			}
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			if(!tipoForm.equals("I")) {
				Step s = new Step();
				s.setIdStep(Integer.parseInt(id));
				s.selectRow(conn);
				
				obj.put("id", id);
				obj.put("description", s.getDescription());
				obj.put("color", s.getColor());
				obj.put("valtype", s.getValtype());
						
				System.out.println(s.getColor()+" "+s.getValtype());
				obj.put("idStep", Step.selectIdstepByGroupedStep(conn, Integer.parseInt(id)));
			}
			
			steps = Step.getStepByIdCustomer(conn, idcus);
			
			obj.put("tipoForm", tipoForm);
			obj.put("steps", steps);
			
			obj.put("idflow", idflow);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/grouped-step-dett", obj);
	}
	
}

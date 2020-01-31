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
import pkg.beans.rfid.Flow;
import pkg.beans.rfid.Interface;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.Step;
import pkg.beans.rfid.StepType;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class FlowDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Interface> in = new ArrayList<Interface>();
		in.clear();
		
		List<StepType> steptype = new ArrayList<StepType>();
		steptype.clear();
		
		List<Step> step = new ArrayList<Step>();
		step.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String msg = "";
		
		try {
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";
			}
			// Apertura connessione
			conn = Utility.getConnRfid();
			if(!tipoForm.equals("I")) {
				Flow flow = new Flow();
				flow.setIdflow(Integer.parseInt(id));
				flow.selectRow(conn);
				obj.put("id", id);
				obj.put("description", flow.getDescription());
				
				in = Interface.getAllInterfaces(conn);
				steptype = StepType.getStepType(conn, idcus);
				
				obj.put("in", in);
				obj.put("steptype", steptype);
				
				step = Step.getStepByIdFlow(conn, Integer.parseInt(id));
				obj.put("step", step);
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
		
		return new ModelAndView("/rfid2/anag/flow-dett", obj);
	}
	
}

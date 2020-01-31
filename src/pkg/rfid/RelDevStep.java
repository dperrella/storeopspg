package pkg.rfid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Rfid_devices;
import pkg.beans.Rfid_flow;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelDevStep extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		List<Rfid_devices> dev = new ArrayList<Rfid_devices>();
		List<Rfid_flow> flow = new ArrayList<Rfid_flow>();
		List<Tables> step = new ArrayList<Tables>();
		
		
		// Pulisce la lista
		dev.clear();
		flow.clear();
		step.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String iddevice = ""+req.getParameter("iddevice");
		iddevice = Utility.noNull(iddevice);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		// Flow
		String idflow = ""+req.getParameter("idflow");
		idflow = Utility.noNull(idflow);
		// Roles
		String idstep = ""+req.getParameter("idstep");
		idstep = Utility.noNull(idstep);
		
		obj.put("iddevice", iddevice);
		obj.put("idflow", idflow);
		obj.put("idstep", idstep);
		
		if(iddevice.length()==0)
			iddevice = "0";
		if(idflow.length()==0)
			idflow = "0";
		if(idstep.length()==0)
			idstep = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnRfid();
			
			if(azione.length()>0) {
				
				// Cancella il contenuto della tabella an_report_user per il report
				sql   = "delete from rel_dev_step where id_device = ? and idflow = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(iddevice));
				st.setInt(2, Integer.parseInt(idflow));
				
				// Esegue l'sql
				st.executeUpdate();
					
				String temp[] = idstep.split(",");
				
				for(int c=0; c<temp.length; c++) {
					
					if(temp[c].length()>0) {
						
						sql   = "insert into rel_dev_step "+
								"(idstep, idflow, id_device) "+
								"values "+
								"(?, ?, ?) ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setInt(1, Integer.parseInt(temp[c]));
						st.setInt(2, Integer.parseInt(idflow));
						st.setInt(3, Integer.parseInt(iddevice));
						
						// Esegue l'sql
						st.executeUpdate();
					
					}
					
				}
				
				msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				
			}
			
			// lista Devices
			Rfid_devices bean_dev = new Rfid_devices();
			dev = bean_dev.selectAll(conn, "");
			
			// Lista Flows 
			Rfid_flow bean_flow = new Rfid_flow();
			flow = bean_flow.selectAll(conn, "");
			
			// Steps
			sql  =  "select a.*, b.idstep as idstep_rel from step a "
				+ "left join rel_dev_step b on a.idstep = b.idstep and b.id_device = '"+iddevice+"' and b.idflow = "+idflow+" "
				+ "where a.idflow = "+idflow+" ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			int $step_idstep = 0;
			int $step_levelnum = 0;
			String $step_descr="";
			boolean $step_enabled=false;
			
			String $cmp1="";
			String $cmp2="";
			
			while(rs.next()) {
				
				if(rs.getString("idstep").equals(rs.getString("idstep_rel"))) {
					$cmp1 = "checked";
				}
				count+=1;
				
				$step_idstep=rs.getInt("idstep");
				$step_levelnum=rs.getInt("levelnum");
				$step_descr=rs.getString("description");
				$step_enabled=rs.getBoolean("enabled");
				if($step_enabled==true)
					$cmp2 = "<i class=\"icon-checked text-success\"></i>";
				else
					$cmp2 = "<i class=\"icon-ban text-danger\"></i>";
				
				step.add(new Tables(""+$step_levelnum,""+$step_idstep,""+$step_descr,$cmp1,$cmp2,""+$step_enabled,""));
				
				$cmp1 = "";
				$cmp2 = "";
				
			}
			rs.close();
			
			if(st!=null)
				st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Device / Steps");
		obj.put("dev", dev);
		obj.put("flow", flow);
		obj.put("step", step);
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/rel/rel_dev_step", obj);
	}
	
}

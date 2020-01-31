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

import org.postgresql.util.PGobject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Rfid_device_type;
import pkg.beans.Rfid_devices;
import pkg.beans.Rfid_flow;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.ReadConfigFields;
import pkg.utility.Utility;

public class RelDevtypeStep extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		List<Rfid_device_type> dev = new ArrayList<Rfid_device_type>();
		List<Rfid_flow> flow = new ArrayList<Rfid_flow>();
		List<Tables2> step = new ArrayList<Tables2>();
		
		
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
		
		String id_devicetype = ""+req.getParameter("id_devicetype");
		id_devicetype = Utility.noNull(id_devicetype);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		// Flow
		String idflow = ""+req.getParameter("idflow");
		idflow = Utility.noNull(idflow);
		// Step
		String idstep = ""+req.getParameter("idstep");
		idstep = Utility.noNull(idstep);
		
		// tipoForm
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm = Utility.noNull(tipoForm);
		
		
		obj.put("id_devicetype", id_devicetype);
		obj.put("idflow", idflow);
		obj.put("idstep", idstep);
		
		if(id_devicetype.length()==0)
			id_devicetype = "0";
		if(idflow.length()==0)
			idflow = "0";
		if(idstep.length()==0)
			idstep = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnRfid();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("M")) {
					// Config Fields
					String config = ReadConfigFields.getConfig(req);
					
					
					if(config.length()>0) {
						
						
						sql   = "update rel_devicetype_step "+
								"set config = ? "+
								"where id_devicetype = ? and id_flow = ? and id_step = ? ";
						st = conn.prepareStatement(sql);
						
						PGobject jsonConfig = new PGobject();
						jsonConfig.setType("json");
						jsonConfig.setValue(config);
						st.setObject(1, jsonConfig);
						
						st.setInt(2, Integer.parseInt(id_devicetype));
						st.setInt(3, Integer.parseInt(idflow));
						st.setInt(4, Integer.parseInt(idstep));
						
						st.executeUpdate();
						
					}
					
				}
				else {
					
					/*
					// Cancella il contenuto della tabella an_report_user per il report
					sql   = "delete from rel_devicetype_step where id_devicetype = ? and id_flow = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt(id_devicetype));
					st.setInt(2, Integer.parseInt(idflow));
					
					// Esegue l'sql
					st.executeUpdate();
					*/
					
					String temp[] = idstep.split(",");
					
					for(int c=0; c<temp.length; c++) {
						
						if(temp[c].length()>0) {
							
							System.out.println(temp[c]);
							
							sql   = "select count(*) num from rel_devicetype_step "
									+ "where id_step = ? and id_flow = ? and id_devicetype = ? ";
							// Prepara il cursore
							st = conn.prepareStatement(sql);
							
							// Passa i parametri
							st.setInt(1, Integer.parseInt(temp[c]));
							st.setInt(2, Integer.parseInt(idflow));
							st.setInt(3, Integer.parseInt(id_devicetype));
							
							rs = st.executeQuery();
							
							boolean is = false;
							if(rs.next()) {
								if(rs.getInt("num")>0)
									is = true;
							}
							rs.close();
							
							if(is==false) {
							
								sql   = "insert into rel_devicetype_step "+
										"(id_step, id_flow, id_devicetype) "+
										"values "+
										"(?, ?, ?) ";
								// Prepara il cursore
								st = conn.prepareStatement(sql);
								
								// Passa i parametri
								st.setInt(1, Integer.parseInt(temp[c]));
								st.setInt(2, Integer.parseInt(idflow));
								st.setInt(3, Integer.parseInt(id_devicetype));
								
								// Esegue l'sql
								st.executeUpdate();
							}	
						}
					}
					
						
						// Cancella il contenuto
						sql   = "delete from rel_devicetype_step where id_devicetype = ? and id_flow = ? "
								+ "and id_step not in("+idstep+") ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setInt(1, Integer.parseInt(id_devicetype));
						st.setInt(2, Integer.parseInt(idflow));
						
						// Esegue l'sql
						st.executeUpdate();
						
					
				}
				
				msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				
			}
			
			// lista Devices
			Rfid_device_type bean_dev = new Rfid_device_type();
			dev = bean_dev.selectAll(conn, "");
			
			// Lista Flows 
			Rfid_flow bean_flow = new Rfid_flow();
			flow = bean_flow.selectAll(conn, "");
			
			// Steps
			sql  =  "select a.*, b.id_step as idstep_rel from step a "
				+ "left join rel_devicetype_step b on a.idstep = b.id_step and b.id_devicetype = '"+id_devicetype+"' and b.id_flow = "+idflow+" "
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
				
				step.add(new Tables2(""+$step_levelnum,""+$step_idstep,""+$step_descr,
						$cmp1,$cmp2,""+$step_enabled,"",
						"","","",id_devicetype,idflow,""+$step_idstep));
				
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
		
		obj.put("dsfun", "DeviceType / Steps");
		obj.put("dev", dev);
		obj.put("flow", flow);
		obj.put("step", step);
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/rel/rel_dev_step", obj);
	}
	
}

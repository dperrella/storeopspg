package pkg.analytics;

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

import pkg.beans.Aree;
import pkg.beans.Devices;
import pkg.beans.Device_Detail;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DeviceDetailList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Device_Detail> dd = new ArrayList<Device_Detail>();
		List<Aree> aree = new ArrayList<Aree>();
		// Pulisce la lista
		dd.clear();
		aree.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String id_store = ""+req.getParameter("id_store");
		id_store = Utility.noNull(id_store);
		
		String id_device = ""+req.getParameter("id_device");
		id_device = Utility.noNull(id_device);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		obj.put("id_store", id_store);
		obj.put("id_device", id_device);
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			
			
			if(azione.length()>0) {
				
				sql   = "delete from tab_device_detail where id_device = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(id_device));
				
				// Esegue l'sql
				st.executeUpdate();
				
				// Inserimento in rel_user_customers
				String[] cd = req.getParameterValues("nrrig");
				String id_line = "";
				String id_area_from = "";
				String id_area_to = "";
				String note = "";
				String status = "";
				if(cd!=null) {
					// prendiamo tutti i valori dell'input multiplo
					if (cd.length>0) {
						
						System.out.println(cd.length);
						
						// riversiamo l'array
						for(int i=0; i<cd.length; i++) {
							
							id_line = req.getParameterValues("id_line")[i];
							try {
								id_area_from = ""+req.getParameter("id_area_from"+id_line);
								id_area_from = Utility.noNull(id_area_from);
							}
							catch(Exception e) {}
							try {
								id_area_to = ""+req.getParameter("id_area_to"+id_line);
								id_area_to = Utility.noNull(id_area_to);
							}
							catch(Exception e) {}
							try {
								note = ""+req.getParameter("note"+id_line);
								note = Utility.noNull(note);
							}
							catch(Exception e) {}
							try {
								status = ""+req.getParameter("status"+id_line);
								status = Utility.noNull(status);
							}
							catch(Exception e) {}
							
							if(status.equals("ON"))
								status="";
							else
								status="A";
							
							
							
							
							
							sql   = "insert into tab_device_detail "+
									"(id_device, id_line, id_area_from, id_area_to, note, status) "+
									"values "+
									"(?, ?, ?, ?, ?, ?) ";
							// Prepara il cursore
							st = conn.prepareStatement(sql);
							
							// Passa i parametri
							st.setInt(1, Integer.parseInt(id_device));
							st.setInt(2, Integer.parseInt(id_line));
							if(id_area_from.length()==0)
								st.setNull(3, java.sql.Types.INTEGER);
							else
								st.setInt(3, Integer.parseInt(id_area_from));
							
							if(id_area_from.length()==0)
								st.setNull(4, java.sql.Types.INTEGER);
							else
								st.setInt(4, Integer.parseInt(id_area_to));
							
							st.setString(5, note);
							st.setString(6, status);
							
							// Esegue l'sql
							st.executeUpdate();
							
						}
						
					}
				
				}
					
					
				msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
					"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
			
				
			}
			
			// sql da eseguire
			/*
			sql  =  "select a.id, id_device, id_line, "
					+ "id_area_from, id_area_to, note, "
					+ "b.descrizione desc_from, b.codice cod_from, "
					+ "c.descrizione desc_to, c.codice cod_to, a.status "
					+ "from tab_device_detail a, tab_aree b, tab_aree c, devices d "
					+ "where a.id_area_from = b.id_area "
					+ "and a.id_area_to = c.id_area "
					+ "and d.id = id_device and d.id_store = "+id_store+" "
					+ "and id_device = "+id_device+" "
					+ "order by id_line ";
			*/
			sql  =  "select * from tab_device_detail where id_device = "+id_device+" ";
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $dd_id="";
			String $dd_id_device="";
			String $dd_id_line="";
			String $dd_id_area_from="";
			String $dd_id_area_to="";
			String $dd_note="";
			String $dd_device_description="";
			String $dd_area_from_description="";
			String $dd_area_to_description="";
			String $dd_status="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			
			while(rs.next()) {
				
				count+=1;
				
				$dd_id=rs.getString("id");
				$dd_id_device=rs.getString("id_device");
				$dd_id_line=rs.getString("id_line");
				$dd_id_area_from=rs.getString("id_area_from");
				$dd_id_area_to=rs.getString("id_area_to");
				$dd_note=rs.getString("note");
				$dd_device_description="";
				$dd_area_from_description="";
				$dd_area_to_description="";
				$dd_status=rs.getString("status");
				
				if($dd_status.trim().length()==0) {
					// attivo
					$dd_status = "checked";
					$cmp1 = "";
				}
				else {
					$dd_status = "";
					$cmp1 = "disabled";
				}
				
				dd.add(new Device_Detail($dd_id,$dd_id_device,$dd_id_line,
						$dd_id_area_from,$dd_id_area_to,$dd_note,
						$dd_device_description,$dd_area_from_description,$dd_area_to_description, $dd_status,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
				$dd_status = "";
				$cmp1 = "";
			}
			rs.close();
			st.close();
			
			if(count==0) {
				
				obj.put("tipoForm", "I");
				
				for(int i=0;i<=15;i++) {
					
					dd.add(new Device_Detail("",id_device,""+i,
							"","","",
							"","","", "",
							"disabled","","",""));
					
				}
				
			}
			
			// doorid e devicename
			sql  =  "select doorid, devicename from devices where id = "+id_device;
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				obj.put("device_doorid", rs.getString("doorid"));
				obj.put("device_name", rs.getString("devicename"));
			}
			rs.close();
			st.close();
			
			// Lista Aree per device
			sql  =  "select a.* from tab_aree a, stores b, devices "
					+ "c where a.id_store = b.id "
					+ "and b.id = c.id_store "
					+ "and c.id = "+id_device+" "
					+ "order by a.id_area ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $id_store="";
			String $id_area="";
			String $description="";
			String $codice="";
			String $store_description="";
			String $esterna="";
			String $gruppo="";
			String $tipo="";
		
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id_store=rs.getString("id_store");
				$id_area=rs.getString("id_area");
				$description=rs.getString("descrizione");
				$codice=rs.getString("codice");
				$store_description="";
				if($esterna.toUpperCase().equals("t"))
					$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-out\"></i></span>";
				else
					$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-in\"></i></span>";
				$gruppo=rs.getString("gruppo");
				$tipo=rs.getString("tipo");
				aree.add(new Aree($id_store,$id_area,$description,$codice,$store_description,$esterna,$gruppo,$tipo,
						"","",$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			
			if(st!=null)
				st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Device / Allarmi");
		obj.put("devdet", dd);
		obj.put("aree", aree);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/anag/device-detail-list", obj);
	}
	
}

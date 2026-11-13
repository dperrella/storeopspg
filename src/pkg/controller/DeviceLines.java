package pkg.controller;

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
import pkg.beans.Tables;
import pkg.beans.Device_Detail;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DeviceLines extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> dd = new ArrayList<Tables>();
		List<Tables> door = new ArrayList<Tables>();
		// Pulisce la lista
		dd.clear();
		door.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String id_device = req.getParameter("id_device");
		String id_customer = req.getParameter("id_customer");
		String account_id = req.getParameter("account_id");
		String linNum = req.getParameter("linNum");
		String device_type = req.getParameter("device_type");
		String ids = req.getParameter("ids");
		
		obj.put("id_device", id_device);
		obj.put("id_customer", id_customer);
		obj.put("account_id", account_id);
		obj.put("linNum", linNum);
		
		
		try {
			// Apre la connessione
			conn = Utility.getConnAdmin();
			sql  =  "select * from device_lines where id_device = "+id_device+" order by id_line ";
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $dd_id_device="";
			String $dd_id_line="";
			String $dd_status="";
			String $dd_door_id="";
			String $dd_entrance="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			System.out.println(device_type);
			
			
			while(rs.next()) {
				
				count+=1;
				
				$dd_id_device=rs.getString("id_device");
				$dd_id_line=rs.getString("id_line");
				$dd_status=rs.getString("status");
				
				
				if($dd_status.trim().length()==0) {
					// attivo
					$dd_status = "checked";
					$cmp1 = "";
					
					if(rs.getBoolean("entrance"))
						$dd_entrance="checked";
					else
						$dd_entrance="";
					
				}
				else {
					$dd_status = "";
					$cmp1 = "disabled";
					$dd_entrance="";
				}
				$dd_door_id=rs.getString("door_id");
				
				if(!device_type.trim().equalsIgnoreCase("5")) {
					dd.add(new Tables(Utility.getCharForNumber(Integer.parseInt($dd_id_line)),$dd_id_device,$dd_id_line,
							$cmp1,$dd_status,$dd_door_id,$dd_entrance));
				}
				else {
					dd.add(new Tables(""+(Integer.parseInt($dd_id_line)+1),$dd_id_device,$dd_id_line,
							$cmp1,$dd_status,$dd_door_id,$dd_entrance));
				}
				
				
				$dd_status = "";
				$cmp1 = "";
			}
			rs.close();
			st.close();
			
			if(count==0) {
				
				for(int i=0;i<Integer.parseInt(linNum);i++) {
					
					if(!device_type.trim().equalsIgnoreCase("5")) {
						dd.add(new Tables(Utility.getCharForNumber(i),id_device,""+i,
								"disabled","","",""));
					}
					else {
						dd.add(new Tables(""+(i+1),id_device,""+i,
								"disabled","","",""));
					}
					
				}
				
			}
			
			// Lista Doors per device
			sql  =  "select door_type_id, door_description "
					+ "from doors where id_customer = "+id_customer;
			
			sql  = "select door_type_id, door_description \r\n" + 
					", case when (select door_id from f_stores\r\n" + 
					"join devices on devices.id_store = f_stores.id\r\n" + 
					"join device_lines on device_lines.id_device = devices.id and status != 'A'\r\n" + 
					"where f_stores.id = "+ids+" and door_id = door_type_id LIMIT 1) is null then false else true end used\r\n" + 
					"from doors \r\n" + 
					"where id_customer = "+id_customer;
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $door_type_id="";
			String $door_description="";
			
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$door_type_id=rs.getString("door_type_id");
				$door_description=rs.getString("door_description");
				
				if(rs.getBoolean("used")) {
					$door_description+=" - Already Used";
					$cmp4 = "disabled";
				}
				else {
					$cmp4 = "";
				}
				
				door.add(new Tables("",$door_type_id,$door_description+" ",
						$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Device / Lines");
		obj.put("devdet", dd);
		obj.put("door", door);
		obj.put("count", count);
		obj.put("device_type", device_type);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/app/popup/device-lines", obj);
	}
	
}

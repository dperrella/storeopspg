package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import pkg.beans.Device_Detail;
import pkg.beans.Devices;
import pkg.beans.Eventcode;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StaticReportConfigDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Devices> dev = new ArrayList<Devices>();
		dev.clear();
		List<Device_Detail> dd = new ArrayList<Device_Detail>();
		dd.clear();
		List<Tables2> sr = new ArrayList<Tables2>();
		sr.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String $id_store = req.getParameter("id_store");
		String $id_flow = req.getParameter("id_flow");
		String $flow_desc = req.getParameter("description");
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm = Utility.noNull(tipoForm);
		
		String $id_device = ""+req.getParameter("id_device");
		$id_device=Utility.noNull($id_device);
		String $id_line = ""+req.getParameter("id_line");
		$id_line=Utility.noNull($id_line);
		
		try {
			
			
			conn = Utility.getConnAnalytics();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					System.out.println("INSERT...");
					
					sql   = "insert into flowreport_detail "+
							"(id_flow, id_device, numline) "+
							"values "+
							"(?, ?, ?) ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($id_flow));
					st.setInt(2, Integer.parseInt($id_device));
					st.setInt(3, Integer.parseInt($id_line));
					
					// Esegue l'sql
					try {
						st.executeUpdate();
						
						msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
							"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
					}
					catch(Exception e){
						msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
							"<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
					}
					$id_device = "";
					$id_line = "";
				}
				
				
				if(tipoForm.equals("E")) {
					System.out.println("DELETE...");
					
					sql   = "delete from flowreport_detail "+
							"where id_flow = ? "+
							"and id_device = ? and numline = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($id_flow));
					st.setInt(2, Integer.parseInt($id_device));
					st.setInt(3, Integer.parseInt($id_line));
					
					// Esegue l'sql
					try {
						st.executeUpdate();
						
						msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
							"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
					}
					catch(Exception e){
						msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
							"<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
					}
					$id_device = "";
					$id_line = "";
				}
				
				
				
			}
			
			sql  =  "select a.*, b.description, c.brandname, d.description ds_devicetype "
					+ "from devices a, stores b, customers c, devicetype d "
					+ "where a.id_store = b.id and b.id_customer = c.id "
					+ "and a.device_type = d.id and b.id = "+$id_store+" "
					+ "and a.id_store in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $ip="";
			String $port="";
			String $macaddr="";
			String $lasttime="";
			String $enabled="";
			String $accountid="";
			String $storeid="";
			String $timezone="";
			String $lin="";
			String $lout="";
			String $device_type="";
			String $readevery="";
			String $dev_id="";
			$id_store="";
			String $updating="";
			String $fromdate="";
			String $deviceid="";
			String $devicename="";
			String $siteid="";
			String $sitename="";
			String $doorid="";
			String $logenabled="";
			String $dev_notes="";
			String $update_time="";
			String $unit_time="";
			String $cause="";
			String $date_disabled="";
			String $adjustment_factor="";
			String $checktime="";
			String $lasteventcode="";
			String $diff_file_time="";
			String $customer_devicename="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$ip=rs.getString("ip");
				$port=rs.getString("port");
				$macaddr=rs.getString("macaddr");
				$lasttime=rs.getString("lasttime");
				$enabled=rs.getString("enabled");
				$accountid=rs.getString("accountid");
				$storeid=rs.getString("storeid");
				$timezone=rs.getString("timezone");
				$lin=rs.getString("lin");
				$lout=rs.getString("lout");
				$device_type=rs.getString("device_type");
				$readevery=rs.getString("readevery");
				$dev_id=rs.getString("id");
				$id_store=rs.getString("id_store");
				$updating=rs.getString("updating");
				$fromdate=rs.getString("fromdate");
				$deviceid=rs.getString("deviceid");
				$devicename=rs.getString("devicename");
				$siteid=rs.getString("siteid");
				$sitename=rs.getString("sitename");
				$doorid=rs.getString("doorid");
				$logenabled=rs.getString("logenabled");
				$dev_notes=rs.getString("notes");
				$update_time=rs.getString("update_time");
				$unit_time=rs.getString("unit_time");
				$cause=rs.getString("cause");
				$date_disabled=rs.getString("date_disabled");
				$adjustment_factor=rs.getString("adjustment_factor");
				$checktime=rs.getString("checktime");
				$lasteventcode=rs.getString("lasteventcode");
				$diff_file_time=rs.getString("diff_file_time");
				$customer_devicename=rs.getString("customer_devicename");
				
				$cmp1=rs.getString("brandname");
				$cmp2=rs.getString("description");
				$cmp3=rs.getString("ds_devicetype");
				
				dev.add(new Devices($ip,$port,$macaddr,$lasttime,$enabled,$accountid,$storeid,
						$timezone,$lin,$lout,$device_type,$readevery,$dev_id,$id_store,
						$updating,$fromdate,$deviceid,$devicename,$siteid,$sitename,
						$doorid,$logenabled,$dev_notes,$update_time,$unit_time,$cause,
						$date_disabled,$adjustment_factor,$checktime,$lasteventcode,
						$diff_file_time,$customer_devicename,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			
			if($id_device.length()>0) {
				
				sql  =  "select * from tab_device_detail where id_device = "+$id_device+" and status = '' ";
				
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
				
				$cmp1="";
				$cmp2="";
				$cmp3="";
				$cmp4="";
				
				
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
				
			}
			
			// Dettagli Flowreport_detail
			sql   = "select a.id_device, a.numline, note, c.doorid, c.devicename "+
					"from flowreport_detail a , tab_device_detail b, devices c "+
					"where a.numline = b.id_line and a.id_device = b.id_device "+
					"and a.id_device = c.id "+
					"and a.id_flow = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Parametri
			st.setInt(1, Integer.parseInt($id_flow));
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			count = 0;
			while(rs.next()) {
				
				count+=1;
				
				sr.add(new Tables2("","","",
						""+count,rs.getString("id_device"),	rs.getString("numline"),rs.getString("note"),rs.getString("doorid"),
						rs.getString("devicename"),"","","",""));
				
			}
			rs.close();
			st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Static report Config Dettaglio");
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		obj.put("id_store", $id_store);
		obj.put("id_flow", $id_flow);
		obj.put("description", $flow_desc);
		obj.put("id_device", $id_device);
		obj.put("id_line", $id_line);
		
		obj.put("dev", dev);
		obj.put("dd", dd);
		obj.put("sr", sr);
		
		return new ModelAndView("/app/report/static-report-config-dett", obj);
	}
	
}

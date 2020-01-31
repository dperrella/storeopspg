package pkg.counter;

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

import pkg.beans.Devices;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DevicesList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Devices> dev = new ArrayList<Devices>();
		
		// Pulisce la lista
		dev.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		HttpSession session = req.getSession();
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
				
		
		String arrayStores = (String) session.getAttribute("arrayStores");
		if(arrayStores.length()==0)
			arrayStores = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				String id = ""+req.getParameter("id");
				id = Utility.noNull(id);
				
				String name = ""+req.getParameter("name");
				name = Utility.noNull(name);
				
				sql  = "update devices set "
						+ "devicename = ? "
						+ "where id = ? ";
						
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, name);
				st.setInt(2, Integer.parseInt(id));
				
				// Esegue l'sql
				st.executeUpdate();
				
				msg="<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div></div>";
					
			}
			
			
			// sql da eseguire
			sql  =  "select a.*, b.description, c.brandname, d.description ds_devicetype "
					+ "from devices a, stores b, customers c, devicetype d "
					+ "where a.id_store = b.id and b.id_customer = c.id "
					+ "and a.device_type = d.id "
					+ "and a.id_store in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+"";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and b.description || c.brandname like ? ";
			}
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			
			System.out.println(sql);
			
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
			String $id="";
			String $id_store="";
			String $updating="";
			String $fromdate="";
			String $deviceid="";
			String $devicename="";
			String $siteid="";
			String $sitename="";
			String $doorid="";
			String $logenabled="";
			String $notes="";
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
				$id=rs.getString("id");
				$id_store=rs.getString("id_store");
				$updating=rs.getString("updating");
				$fromdate=rs.getString("fromdate");
				$deviceid=rs.getString("deviceid");
				$devicename=rs.getString("devicename");
				$siteid=rs.getString("siteid");
				$sitename=rs.getString("sitename");
				$doorid=rs.getString("doorid");
				$logenabled=rs.getString("logenabled");
				$notes=rs.getString("notes");
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
						$timezone,$lin,$lout,$device_type,$readevery,$id,$id_store,
						$updating,$fromdate,$deviceid,$devicename,$siteid,$sitename,
						$doorid,$logenabled,$notes,$update_time,$unit_time,$cause,
						$date_disabled,$adjustment_factor,$checktime,$lasteventcode,
						$diff_file_time,$customer_devicename,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Dispositivi");
		obj.put("dev", dev);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/counter/anag/devices-list", obj);
	}
	
}

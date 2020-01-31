package pkg.counter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class HourDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> tmp = new ArrayList<Tables>();
		
		// Utente
		String idusr = session.getAttribute("username").toString().trim().toUpperCase();
		
		// Pulisce la lista
		tmp.clear();
		
		Connection connControl = null;
		PreparedStatement stControl = null;
		PreparedStatement stControl2 = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String id_customer = ""+req.getParameter("id_customer");
		id_customer=Utility.noNull(id_customer);
		
		String id_store = ""+req.getParameter("id_store");
		id_store=Utility.noNull(id_store);
		
		String id_template = ""+req.getParameter("id_template");
		id_template=Utility.noNull(id_template);
		
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		obj.put("id_store", id_store);
		
		try {
			// Apre la connessione
			connControl = Utility.getConnControl();
			
			if(azione.length()>0) {
				
				String id_optempl = req.getParameter("id_optempl");
				
				sql  = "delete from dailyoperation_stores where id_stores = '"+id_store+"' ";
				stControl = connControl.prepareStatement(sql);
				stControl.executeUpdate();
				
				sql  = "select * from dailyop_template where id_optempl = "+id_optempl+" ";
				System.out.println(sql);
				
				stControl = connControl.prepareStatement(sql);
				
				rs=stControl.executeQuery();
				while(rs.next()) {
					
					sql   = "INSERT INTO dailyoperation_stores( "
							+ "id_stores, days, fromhour, tohour, id_optempl) "
							+ "VALUES (?, ?, ?, ?, ?) ";
					
					System.out.println(sql);
					
					stControl2 = connControl.prepareStatement(sql);
					
					Time fromhour = rs.getTime("fromhour");
					Time tohour = rs.getTime("tohour");
					
					stControl2.setInt(1, Integer.parseInt(id_store));
					stControl2.setInt(2, rs.getInt("days"));
					stControl2.setTime(3, fromhour);
					stControl2.setTime(4, tohour);
					stControl2.setInt(5, Integer.parseInt(id_optempl));
					
					stControl2.executeUpdate();
				}
				rs.close();
				
				sql   = "update stores set id_optempl = "+id_optempl+" where id = "+id_store+" ";
				stControl = connControl.prepareStatement(sql);
				stControl.executeUpdate();
				
				msg="<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div></div>";
					
				
			}
			
			// sql da eseguire
			sql   = "select * from dailyoperation_stores "
					+ "where id_stores = '"+id_store+"' ";
			System.out.println(sql);
			// Prepara il cursore
			stControl = connControl.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stControl.executeQuery();
			
			// Campi database
			String $days="";
			String $fromhour="";
			String $tohour="";
			String $id="";
			String $id_optempl="";
			
			while(rs.next()) {
				
				count+=1;
				
				switch (rs.getInt("days")) {
	         	case 0: 
	         		$days = propLang.get("lun");
	         		break;
	         	case 1: 
	         		$days = propLang.get("mar");
	         		break;
	         	case 2: 
	         		$days = propLang.get("mer");
	         		break;
	         	case 3: 
	         		$days = propLang.get("gio");
	         		break;
	         	case 4: 
	         		$days = propLang.get("ven");
	         		break;
	         	case 5: 
	         		$days = propLang.get("sab");
	         		break;
	         	case 6: 
	         		$days = propLang.get("dom");
	         		break;
				}
				$fromhour = rs.getString("fromhour");
				$tohour = rs.getString("tohour");
				$id = rs.getString("id");
				$id_optempl = rs.getString("id_optempl");
				
				tmp.add(new Tables("TMP", $days, $fromhour,
						$tohour,$id,$id_optempl, ""));
				
			}
			rs.close();
			
			if(stControl!=null)
				stControl.close();
			if(connControl!=null)
				connControl.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div></div>";			
		}
		
		obj.put("dsfun", "Gestione Orari");
		obj.put("tmp", tmp);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/counter/anag/hour-dett", obj);
	}
	
}

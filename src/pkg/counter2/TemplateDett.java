package pkg.counter2;

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

import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class TemplateDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> tmp = new ArrayList<Tables>();
		
		// Utente
		String idusr = session.getAttribute("username").toString().trim().toUpperCase();
		String id_optempl = req.getParameter("id_optempl");
		String whereIAm = req.getParameter("cdapp");
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
		
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		try {
			// Apre la connessione
			connControl = Utility.getConnControl();
			
			if(azione.length()>0) {
				
				/*
				sql  = "delete from dailyop_template where id_optempl = "+id_optempl+" ";
				System.out.println(sql);
				stControl = connControl.prepareStatement(sql);
				stControl.executeUpdate();
				*/
				
				// Inserisce
				try {
					String[] nrrig = req.getParameterValues("nrrig");
					if(nrrig!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (nrrig.length>0) {
							
							String[] cd = req.getParameterValues("id");
							String id = "";
							String fromhour = "";
							String tohour = "";
							String days = "";
							
							if(cd!=null) {
								
								// riversiamo l'array
								for(int i=0; i<cd.length; i++) {
								
									id = req.getParameterValues("id")[i];
									fromhour = req.getParameterValues("fromhour")[i];
									tohour = req.getParameterValues("tohour")[i];
									days = req.getParameterValues("days")[i];
									
									sql   = "update dailyop_template "+
											"set fromhour = ?, tohour = ? "+
											"where id_optempl = ? and id = ? and days = ? ";
									
									// Prepara il cursore
									stControl = connControl.prepareStatement(sql);
									
									System.out.println(fromhour);
									System.out.println(tohour);
									
									// Passa i parametri
									stControl.setTime(1, java.sql.Time.valueOf(fromhour+":00"));
									stControl.setTime(2, java.sql.Time.valueOf(tohour+":00"));
									stControl.setInt(3, Integer.parseInt(id_optempl));
									stControl.setInt(4, Integer.parseInt(id));
									stControl.setInt(5, Integer.parseInt(days));
									
									// Esegue l'sql
									stControl.executeUpdate();
									
									
								}
								
							}
							
						}
						
					}
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				
				msg="<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div></div>";
					
				
			}
			
			// sql da eseguire
			sql   = "select days, fromhour::time without time zone, "
					+ "tohour::time without time zone, t.id, id_customer, description from dailyop_template t, customers c "
					+ "where c.id=t.id_customer::integer and t.id_optempl = "+id_optempl;
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
			String $id_customer="";
			
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
				$fromhour = new SimpleDateFormat("HH:mm").format(rs.getTime("fromhour"));
				$tohour = new SimpleDateFormat("HH:mm").format(rs.getTime("tohour"));
				$id = rs.getString("id");
				$id_customer = rs.getString("id_customer");
				
				tmp.add(new Tables(""+rs.getInt("days"), $days, $fromhour,
						$tohour,$id,$id_customer, rs.getString("description")));
				
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
		
		obj.put("dsfun", "Dettaglio Template");
		obj.put("tmp", tmp);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("id_optempl", id_optempl);
		obj.put("whereIAm", whereIAm);
		
		return new ModelAndView("/counter2/anag/template-dett", obj);
	}
	
}

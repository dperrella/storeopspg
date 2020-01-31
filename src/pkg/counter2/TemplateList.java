package pkg.counter2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class TemplateList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> tmp = new ArrayList<Tables>();
		List<Tables> cus = new ArrayList<Tables>();
		
		// Utente
		String idusr = session.getAttribute("username").toString().trim().toUpperCase();
		String whereIAm = req.getParameter("cdapp");
		// Pulisce la lista
		tmp.clear();
		cus.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		
		Connection connControl = null;
		PreparedStatement stControl = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String $id = ""+req.getParameter("id");
		$id = Utility.noNull($id);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm = Utility.noNull(tipoForm);
		
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
		if(idcus.length()==0)
			idcus = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnCounter();
			connControl = Utility.getConnControl();
		
			if(azione.length()>0) {
				
				String id_customer = ""+req.getParameter("id_customer");
				id_customer = Utility.noNull(id_customer);
				
				String descr = ""+req.getParameter("description");
				descr = Utility.noNull(descr);
				
				String descrShort = ""+req.getParameter("shortdescr");
				descrShort = Utility.noNull(descrShort);
				
				String fromHour = ""+req.getParameter("fromhour");
				fromHour = Utility.noNull(fromHour);
				
				String toHour = ""+req.getParameter("tohour");
				toHour = Utility.noNull(toHour);
				
				
				
				if(tipoForm.equals("M")) {
					
					String id = ""+req.getParameter("id");
					id = Utility.noNull(id);
					
					sql  = "update optempl set "
							+ "id_customer = ? ,"
							+ "description = ? , "
							+ "shortdescr = ? "
							+ "where id = ? ";
							
					// Prepara il cursore
					stControl = connControl.prepareStatement(sql);
					
					// Passa i parametri
					stControl.setString(1, id_customer);
					stControl.setString(2, descr);
					stControl.setString(3, descrShort);
					stControl.setInt(4, Integer.parseInt(id));
					
					// Esegue l'sql
					stControl.executeUpdate();
					
				}
				
				if(tipoForm.equals("I")) {
					
					int cusOld = 0;
					
							
						sql  = "insert into optempl "
								+ "(id_customer, description, shortdescr) "
								+ "values "
								+ "(?, ?, ?) RETURNING id ";
						System.out.println(sql);
								
						// Prepara il cursore
						stControl = connControl.prepareStatement(sql);
						
						// Passa i parametri
						stControl.setString(1, idcus);
						stControl.setString(2, descr);
						stControl.setString(3, descrShort);
						
						// Esegue l'sql
						rs = stControl.executeQuery();
						
						if(rs.next()) {
							$id = rs.getString(1);
							System.out.println("ID: "+rs.getString(1));
						}
						else {
							$id = "-1";
							System.out.println("ID: -1");
						}
						rs.close();
						
						sql   = "delete from dailyop_template where id_customer = '"+idcus+"' and id_optempl = "+$id+" ";
						stControl = connControl.prepareStatement(sql);
						stControl.executeUpdate();
						
						/*
						final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
						
						fromHour = sdf.format(getSomeDate(
							      fromHour, TimeZone.getTimeZone("Europe/Berlin")));
						System.out.println(fromHour);
						
						toHour = sdf.format(getSomeDate(
							      toHour, TimeZone.getTimeZone("Europe/Berlin")));
						System.out.println(toHour);
						
						System.out.println(getCurrentTimezoneOffset());
						*/
						
						
						
						for(int d=0;d<7;d++) {
							
							sql   = "insert into dailyop_template "+
									"(id_optempl, days, fromhour, tohour, id_customer) "+
									"values "+
									"("+$id+" , "+d+" , ? , ? , '"+idcus+"') ";
							
							System.out.println(sql);
							stControl = connControl.prepareStatement(sql);
							stControl.setTime(1, java.sql.Time.valueOf(fromHour+":00"));
							stControl.setTime(2, java.sql.Time.valueOf(toHour+":00"));
							
							stControl.executeUpdate();
							
						}
						
					
					
				}
				
				if(tipoForm.equals("E")) {
					
					$id = req.getParameter("id");
					
					sql   = "delete from dailyop_template where id_optempl = ? ";
					// Prepara il cursore
					stControl = connControl.prepareStatement(sql);
					// Passa i parametri
					stControl.setInt(1, Integer.parseInt($id));
					// Esegue l'sql
					stControl.executeUpdate();
					
					
					sql   = "delete from optempl where id = ? ";
					// Prepara il cursore
					stControl = connControl.prepareStatement(sql);
					// Passa i parametri
					stControl.setInt(1, Integer.parseInt($id));
					// Esegue l'sql
					stControl.executeUpdate();
					
				}
				
				
				
				
				
				
				msg="<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div></div>";
					
			}
			
			// Clienti, per modal
			sql   = "select id, description, brandname from customers where id in("+idcus+") ";
			
			
			
			// Prepara il cursore
			stControl = connControl.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stControl.executeQuery();
						
			while(rs.next()) {
				
				count+=1;
				
				cus.add(new Tables("cus", rs.getString("id"), rs.getString("description"),
						rs.getString("brandname"),"","", ""));
				
			}
			rs.close();
						
			// sql da eseguire
			sql   = "SELECT t.*, c.description as cust FROM optempl t, customers c "
					+ "where c.id = t.id_customer::integer and t.id_customer in( '"+idcus+"' ) ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and t.description like ? ";
			}
			
			// Prepara il cursore
			stControl = connControl.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				stControl.setString(1, "%" + q + "%");
			}
			System.out.println(sql);
			
			// Esegue l'sql
			rs=stControl.executeQuery();
			
			// Campi database
			$id="";
			String $description="";
			String $shortdescr="";
			String $id_customer="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id = rs.getString("id");
				$description = rs.getString("description");
				$shortdescr = rs.getString("shortdescr");
				$id_customer = rs.getString("id_customer");
				
				tmp.add(new Tables("tmp", $id, $description,
						$shortdescr,$id_customer,"", rs.getString("cust") ));
				
			}
			rs.close();
			
			if(stControl!=null)
				stControl.close();
			if(connControl!=null)
				connControl.close();
			
			if(stControl!=null)
				stControl.close();
			if(conn!=null)
				conn.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div></div>";			
		}
		
		obj.put("dsfun", "Adjustment Factor");
		obj.put("tmp", tmp);
		obj.put("cus", cus);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("whereIAm", whereIAm);
		
		return new ModelAndView("/counter2/anag/template-list", obj);
	}
	
	
	public static Date getSomeDate(final String str, final TimeZone tz)
		    throws ParseException {
		  final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
		  sdf.setTimeZone(tz);
		  return sdf.parse(str);
		}
	
	public static String getCurrentTimezoneOffset() {

	    TimeZone tz = TimeZone.getDefault();  
	    Calendar cal = GregorianCalendar.getInstance(tz);
	    int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

	    String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
	    offset = (offsetInMillis >= 0 ? "+" : "-") + offset;

	    return offset;
	} 
}

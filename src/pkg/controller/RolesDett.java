package pkg.controller;

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

import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RolesDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Users> usr = new ArrayList<Users>();
		
		// Pulisce la lista
		usr.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		
		try {
			
			// Campi database
			String $id="";
			String $name="";
			String $description="";
			String $is_admin="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			conn = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$name = req.getParameter("name");
					$description = req.getParameter("description");
					$is_admin = req.getParameter("is_admin");
					
					sql   = "insert into roles "+
							"(name, description, is_admin) "+
							"values "+
							"(?, ?, ?) RETURNING id ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $name);
					st.setString(2, $description);
					
					if($is_admin.equals("S"))
						st.setBoolean(3, true);
					else
						st.setBoolean(3, false);
					
					// Esegue l'sql
					rs = st.executeQuery();
					
					if(rs.next()) {
						$id = rs.getString(1);
						System.out.println("ID: "+rs.getString(1));
					}
					else {
						$id = "-1";
						System.out.println("ID: -1");
					}
					rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MOD...");
					
					$name = req.getParameter("name");
					$description = req.getParameter("description");
					$is_admin = req.getParameter("is_admin");
					
					sql  =  "update roles "+
							"set name = ?, description = ?, is_admin = ? "+
							"where id = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $name);
					st.setString(2, $description);
					
					if($is_admin.equals("S"))
						st.setBoolean(3, true);
					else
						st.setBoolean(3, false);
					
					st.setInt(4, Integer.parseInt($id));
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					sql   = "delete from roles where id = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($id));
					
					// Esegue l'sql
					st.executeUpdate();
					
					// *blank id
					$id = "";
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from roles where id = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setInt(1, Integer.parseInt($id));
				// Esegue l'sql
				rs=st.executeQuery();
				
				if(rs.next()) {
					$name=rs.getString("name");
					$description=rs.getString("description");
					$is_admin=rs.getString("is_admin");
				}
				rs.close();
				
				if($is_admin.equalsIgnoreCase("t"))
					$is_admin = "S";
				else
					$is_admin = "N";
				
				// ====================
				// Lista utenti del ruolo
				sql  =  "select * from zutenti where tipo = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $id);
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					
					usr.add(new Users(rs.getString("idusr"),"",rs.getString("nome"),
							rs.getString("cogn"),rs.getString("tipo"),rs.getString("foto"),
							rs.getString("mail"),rs.getString("db"),rs.getString("style"),
							rs.getString("logo"),rs.getString("sysinf"),rs.getString("language"),
							"","","",""));
					
				}
				rs.close();
				
			}
			
			obj.put("id", $id);
			obj.put("name", $name);
			obj.put("description", $description);
			obj.put("is_admin", $is_admin);
			
			if(st!=null)
				st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Ruolo");
		obj.put("tipoForm", tipoForm);
		obj.put("usr", usr);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/app/anag/roles-dett", obj);
	}
	
}

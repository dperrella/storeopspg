package pkg.counter2;

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

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StoresList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> sto = new ArrayList<Tables>();
		
		// Utente
		String idusr = session.getAttribute("username").toString().trim().toUpperCase();
		
		// Pulisce la lista
		sto.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
			
			if(azione.length()>0) {
				
				String id_store = ""+req.getParameter("id_store");
				id_store = Utility.noNull(id_store);
				
				
				/*
				String af = ""+req.getParameter("af");
				af = Utility.noNull(af);
				String afm = ""+req.getParameter("afm");
				afm = Utility.noNull(afm);
				String afs = ""+req.getParameter("afs");
				afs = Utility.noNull(afs);
				
				if(af.length()==0)
					af = "0";
				if(afm.length()==0)
					afm = "0";
				if(afs.length()==0)
					afs = "0";
				
				sql  = "update devices set "
						+ "adjustment_factor = ? ,"
						+ "adjustment_factor_m = ? , "
						+ "adjustment_factor_s = ?  "
						+ "where id_store = ? ";
						
				// Prepara il cursore
				stAdmin = connAdmin.prepareStatement(sql);
				
				// Passa i parametri
				stAdmin.setInt(1, Integer.parseInt(af));
				stAdmin.setInt(2, Integer.parseInt(afm));
				stAdmin.setInt(3, Integer.parseInt(afs));
				stAdmin.setInt(4, Integer.parseInt(id_store));
				
				// Esegue l'sql
				stAdmin.executeUpdate();
				*/
				
				msg="<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div></div>";
					
			}
			
			// sql da eseguire
			sql   = "select s.id, s.store_id, s.store_name, count(iddoor) doors "+
					"from rel_user_stores r, stores s " + 
					"left join rel_store_door d on d.idstore = s.id " + 
					"where idusr = '"+idusr+"' and s.id_customer = "+session.getAttribute("idcus")+" " + 
					"and r.idstore = s.id "+
					"group  by s.id, s.store_id, s.store_name "+
					"order by s.id ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and s.store_name like ? ";
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
			String $id="";
			String $store_id="";
			String $store_name="";
			String $doors="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id = rs.getString("id");
				$store_id = rs.getString("store_id");
				$store_name = rs.getString("store_name");
				$doors = rs.getString("doors");
				
				sto.add(new Tables("DOORS", $id, $store_id,
						$store_name,$doors,"", ""));
				
			}
			rs.close();
			
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div></div>";			
		}
		
		obj.put("dsfun", "Adjustment Factor");
		obj.put("sto", sto);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/counter2/anag/stores-list", obj);
	}
	
}

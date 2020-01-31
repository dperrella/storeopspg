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

public class AdjustmentFactorList extends AbstractController {
	
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
		
		Connection connAdmin = null;
		PreparedStatement stAdmin = null;
		
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
			conn = Utility.getConnCounter();
			connAdmin = Utility.getConnAdmin();
			
			
			if(azione.length()>0) {
				
				String id_store = ""+req.getParameter("id_store");
				id_store = Utility.noNull(id_store);
				
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
				
				msg="<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div></div>";
					
			}
			
			// sql da eseguire
			sql   = "select distinct s.id, s.description, "
					+ "d.adjustment_factor, "
					+ "coalesce(adjustment_factor_m , null , 0) adjustment_factor_m, "
					+ "coalesce(adjustment_factor_s , null , 0) adjustment_factor_s "
					+ "from rel_user_stores r, stores s "
					+ "left join devices d on s.id = d.id_store "
					+ "where idusr = '"+idusr+"' and s.id_customer = "+session.getAttribute("idcus")+" "
					+ "and idstore = s.id "
					+ "order by s.id ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and s.description like ? ";
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
			String $id_store="";
			String $description="";
			String $adjustment_factor="";
			String $adjustment_factor_m="";
			String $adjustment_factor_s="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id_store = rs.getString("id");
				$description = rs.getString("description");
				$adjustment_factor = rs.getString("adjustment_factor");
				$adjustment_factor_m = rs.getString("adjustment_factor_m");
				$adjustment_factor_s = rs.getString("adjustment_factor_s");
				
				sto.add(new Tables("ADJ", $id_store, $description,
						$adjustment_factor,$adjustment_factor_m,$adjustment_factor_s, ""));
				
			}
			rs.close();
			
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
			
			if(stAdmin!=null)
				stAdmin.close();
			if(connAdmin!=null)
				connAdmin.close();
			
			
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
		
		return new ModelAndView("/counter2/anag/adjustment-factor-list", obj);
	}
	
}

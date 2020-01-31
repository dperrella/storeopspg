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

public class HourList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> sto = new ArrayList<Tables>();
		String whereIAm = req.getParameter("cdapp");
		// Utente
		String idusr = session.getAttribute("username").toString().trim().toUpperCase();
		
		// Pulisce la lista
		sto.clear();
		
		Connection connControl = null;
		PreparedStatement stControl = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
		if(idcus.length()==0)
			idcus = "0";
		
		
		try {
			// Apre la connessione
			connControl = Utility.getConnControl();
			
			// sql da eseguire
			sql   = "select distinct s.id, s.description, s.id_customer, "
					+ "coalesce(s.id_optempl , null , 0) idtempl, "
					+ "(select shortdescr from optempl d where d.id = s.id_optempl) shortdescr "
					+ "from rel_user_stores r, stores s "
					+ "where idusr = '"+idusr+"' "
					+ "and idstore::integer = s.id and s.id_customer = "+idcus+" "
					+ "order by s.id ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and s.description like ? ";
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
			String $id_store="";
			String $description="";
			String $idtempl="";
			String $shortdescr="";
			String $id_customer="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id_store = rs.getString("id");
				$description = rs.getString("description");
				$idtempl = rs.getString("idtempl");
				$shortdescr = rs.getString("shortdescr");
				$id_customer = rs.getString("id_customer");
				
				sto.add(new Tables("STO", $id_store, $description,
						$idtempl,$shortdescr,$id_customer, ""));
				
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
		obj.put("sto", sto);
		obj.put("whereIAm", whereIAm);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/counter2/anag/hour-list", obj);
	}
	
}

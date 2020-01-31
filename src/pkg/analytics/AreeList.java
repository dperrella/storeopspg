package pkg.analytics;

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
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AreeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Aree> aree = new ArrayList<Aree>();
		
		// Pulisce la lista
		aree.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String id_store = req.getParameter("id_store");
		obj.put("id_store", id_store);
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			// sql da eseguire
			sql  =  "select a.id_store, a.id_area, a.descrizione description, "
					+ "a.codice, b.description store_description, esterna, gruppo, tipo, id_area_from, id_area_to "
					+ "from tab_aree a, stores b "
					+ "where a.id_store = b.id and a.id_store = "+id_store+" ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" where a.codice || descrizione like ? ";
			}
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $id_store="";
			String $id_area="";
			String $description="";
			String $codice="";
			String $store_description="";
			String $esterna="";
			String $gruppo="";
			String $tipo="";
			String $id_area_from="";
			String $id_area_to="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id_store=rs.getString("id_store");
				$id_area=rs.getString("id_area");
				$description=rs.getString("description");
				$codice=rs.getString("codice");
				$store_description=rs.getString("store_description");
				$esterna=rs.getString("esterna");
				if($esterna.toUpperCase().equals("T"))
					$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-out\"></i> "+propLang.get("aar_S")+"</span>";
				else
					$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-in\"></i> "+propLang.get("aar_N")+"</span>";
				$gruppo=rs.getString("gruppo");
				$tipo=rs.getString("tipo");
				
				$id_area_from = ""+rs.getString("id_area_from");
				$id_area_from = Utility.noNull($id_area_from);
				$id_area_to = ""+rs.getString("id_area_to");
				$id_area_to = Utility.noNull($id_area_to);
				
				aree.add(new Aree($id_store,$id_area,$description,$codice,$store_description,$esterna,$gruppo,$tipo,
						$id_area_from, $id_area_to,$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Lista Aree");
		obj.put("aree", aree);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/anag/aree-list", obj);
	}
	
}

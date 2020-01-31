package pkg.hb;

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

import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelUserKpiList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Users> users = new ArrayList<Users>();
		List<Tables> kpi = new ArrayList<Tables>();
		
		// Pulisce la lista
		users.clear();
		kpi.clear();
		
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
		
		String idusr = ""+req.getParameter("idusr");
		idusr = Utility.noNull(idusr);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String idkpi = ""+req.getParameter("idkpi");
		idkpi = Utility.noNull(idkpi);
		
		obj.put("idusr", idusr);
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
			// Apre la connessione Admin
			connAdmin = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "delete from rel_user_kpi where idusr = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, idusr);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// Inserimento in rel_user_customers
				String temp1[] = idkpi.split(",");
				
				for(int c=0; c<temp1.length; c++) {
					
					if(temp1[c].length()>0) {
						
						System.out.println(temp1[c]);
						sql   = "insert into rel_user_kpi "+
								"(idusr, idkpi, visorder) "+
								"values "+
								"(?, ?, ?) ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1, idusr.toUpperCase());
						st.setString(2, temp1[c]);
						st.setInt(3, 0);
						
						// Esegue l'sql
						st.executeUpdate();
						
					}
					
					msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				}
				
			}
			
			
			
			// ==================
			// Lista Utenti
			sql  =  "select * from zutenti ";
			
			// Prepara il cursore
			stAdmin = connAdmin.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stAdmin.executeQuery();
			
			// Campi datebase
			String $idusr="";
			String $pwd="";
			String $nome="";
			String $cogn="";
			String $tipo="";
			String $foto="";
			String $usrmail="";
			String $db="";
			String $style="";
			String $logo="";
			String $sysinf="";
			String $language="";
			while(rs.next()) {
				
				count+=1;
				
				$idusr=rs.getString("idusr");
				$pwd=rs.getString("pwd");
				$nome=rs.getString("nome");
				$cogn=rs.getString("cogn");
				$tipo=rs.getString("tipo");
				$foto=rs.getString("foto");
				$usrmail=rs.getString("mail");
				$db=rs.getString("db");
				$style=rs.getString("style");
				$logo=rs.getString("logo");
				$sysinf=rs.getString("sysinf");
				$language=""+rs.getString("language");
				$language=Utility.noNull($language);
				
				users.add(new Users($idusr,$pwd,$nome,$cogn,$tipo,$foto,$usrmail,$db,$style,$logo,$sysinf,$language,"","","",""));
				
			}
			rs.close();
			// Fine Lista Utenti
			// ==================
			
			// sql da eseguire
			sql  =  "select a.* , b.idkpi "
					+ "from ztables a "
					+ "left join rel_user_kpi b on trim(a.xcdel) = trim(b.idkpi) and b.idusr = '"+idusr+"' "
					+ "where xcdtb = 'KPI'  ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and a.xdtab || a.xdesc like ? ";
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
			String $xcdtb="";
			String $xcdel="";
			String $xdtab="";
			String $xdesc="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				if(rs.getString("xcdel").equals(rs.getString("idkpi"))) {
					$cmp3 = "checked";
				}
				count+=1;
				
				$xcdtb=rs.getString("xcdtb");
				$xcdel=rs.getString("xcdel");
				$xdtab=rs.getString("xdtab");
				$xdesc=rs.getString("xdesc");
				
				kpi.add(new Tables($xcdtb,$xcdel,$xdtab,$xdesc,
						"",$cmp3,$cmp4));
				
				$cmp3 = "";
				
			}
			rs.close();
			
			if(st!=null)
				st.close();
			if(stAdmin!=null)
				stAdmin.close();
			
			conn.close();
			connAdmin.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Utenti / Kpi");
		obj.put("users", users);
		obj.put("kpi", kpi);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hb/rel/rel_user_kpi-list", obj);
	}
	
}

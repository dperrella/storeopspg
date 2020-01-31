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

import pkg.beans.Stores;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelUserStoresList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Users> users = new ArrayList<Users>();
		List<Stores> sto = new ArrayList<Stores>();
		
		// Pulisce la lista
		users.clear();
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
		
		String idusr = ""+req.getParameter("idusr");
		idusr = Utility.noNull(idusr);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String idstores = ""+req.getParameter("idstores");
		idstores = Utility.noNull(idstores);
		
		obj.put("idusr", idusr);
		
		String arrayStores = (String) session.getAttribute("arrayStores");
		if(arrayStores.length()==0)
			arrayStores = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnCounter();
			// Apre la connessione Admin
			connAdmin = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				/*
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "delete from rel_user_stores where idusr = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, idusr);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// Inserimento in rel_user_customers
				String temp1[] = idstores.split(",");
				
				for(int c=0; c<temp1.length; c++) {
					
					if(temp1[c].length()>0) {
						
						System.out.println(temp1[c]);
						sql   = "insert into rel_user_stores "+
								"(idusr, idstore) "+
								"values "+
								"(?, ?) ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1, idusr.toUpperCase());
						st.setInt(2, Integer.parseInt(temp1[c]));
						
						// Esegue l'sql
						st.executeUpdate();
						
					}
					
					msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				}
				*/
				
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
			sql  =  "select a.*, c.brandname custbrand, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices, b.idstore "
					+ "from customers c, stores a "
					+ "left join rel_user_stores b on a.id = b.idstore and b.idusr = '"+idusr+"' "
					+ "where a.id_customer = c.id "
					+ "and id_customer in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+idusr+"') ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and a.description || c.brandname like ? ";
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
			String $description="";
			String $address="";
			String $country="";
			String $city="";
			String $postalcode="";
			String $region="";
			String $contact="";
			String $cphone="";
			String $sphone="";
			String $cmail="";
			String $smail="";
			String $coordinates="";
			String $store_code="";
			String $brandname="";
			String $brandlogo="";
			String $id_customer="";
			String $id="";
			String $cmobile="";
			String $province="";
			String $id_optempl="";
			String $customeridstore="";
			String $sapid="";
			String $latitude="";
			String $longitude="";
			String $notes="";
			String $openhh="";
			String $closehh="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				if(rs.getString("id").equals(rs.getString("idstore"))) {
					$cmp3 = "checked";
				}
				count+=1;
				
				$description=rs.getString("description");
				$address=rs.getString("address");
				$country=rs.getString("country");
				$city=rs.getString("city");
				$postalcode=rs.getString("postalcode");
				$region=rs.getString("region");
				$contact=rs.getString("contact");
				$cphone=rs.getString("cphone");
				$sphone=rs.getString("sphone");
				$cmail=rs.getString("cmail");
				$smail=rs.getString("smail");
				$coordinates=rs.getString("coordinates");
				$store_code=rs.getString("store_code");
				$brandname=rs.getString("brandname");
				$brandlogo=rs.getString("brandlogo");
				$id_customer=rs.getString("id_customer");
				$id=rs.getString("id");
				$cmobile=rs.getString("cmobile");
				$province=rs.getString("province");
				$id_optempl=rs.getString("id_optempl");
				$customeridstore=rs.getString("customeridstore");
				$sapid=rs.getString("sapid");
				$latitude=rs.getString("latitude");
				$longitude=rs.getString("longitude");
				$notes=rs.getString("notes");
				$openhh=rs.getString("openhh");
				$closehh=rs.getString("closehh");
				
				$cmp1=rs.getString("custbrand");
				$cmp2=rs.getString("nrdevices");
				
				
				sto.add(new Stores($description,$address,$country,$city,
						$postalcode,$region,$contact,$cphone,$sphone,$cmail,$smail,
						$coordinates,$store_code,$brandname,$brandlogo,$id_customer,
						$id,$cmobile,$province,$id_optempl,$customeridstore,$sapid,
						$latitude,$longitude,$notes,$openhh,$closehh,
						"","","",
						$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Utenti / Negozi");
		obj.put("users", users);
		obj.put("sto", sto);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/counter/rel/rel_user_stores-list", obj);
	}
	
}

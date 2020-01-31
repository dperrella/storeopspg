package pkg.analytics;

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

import pkg.beans.Aree;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AreeDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Stores> sto = new ArrayList<Stores>();
		List<Tables> tp = new ArrayList<Tables>();
		List<Tables> tp2 = new ArrayList<Tables>();
		List<Aree> aree = new ArrayList<Aree>();
		
		
		// Pulisce la lista
		sto.clear();
		tp.clear();
		tp2.clear();
		aree.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		Connection connAdmin = null;
		PreparedStatement stAdmin = null;
		ResultSet rsAdmin = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		String $id_store = req.getParameter("id_store");
		
		String btnActive = "";
		
		try {
			
			// Campi database
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
			
			$id_area = ""+req.getParameter("id_area");
			$id_area=Utility.noNull($id_area);
			
			conn = Utility.getConnAnalytics();
			connAdmin = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$id_area = req.getParameter("id_area");
					$description = req.getParameter("description");
					$codice = req.getParameter("codice");
					$esterna = req.getParameter("esterna");
					boolean est = false;
					if($esterna.equals("t"))
						est = true;
					else
						est = false;
					System.out.println($esterna);
					$gruppo = req.getParameter("gruppo");
					$tipo = req.getParameter("tipo");
					
					sql   = "insert into tab_aree "+
							"(id_store, id_area, descrizione, codice, esterna, gruppo, tipo) "+
							"values "+
							"(?, ?, ?, ?, ?, ?, ?) ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($id_store));
					st.setInt(2, Integer.parseInt($id_area));
					st.setString(3, $description);
					st.setString(4, $codice);
					st.setBoolean(5, est);
					st.setString(6, $gruppo);
					st.setString(7, $tipo);
					
					// Esegue l'sql
					st.executeUpdate();
					
					if($tipo.equals("FITT")) {
						$id_area_from = req.getParameter("id_area_from");
						$id_area_to = req.getParameter("id_area_to");
						
						sql  =  "update tab_aree "+
								"set id_area_from = ?, id_area_to = ? "+
								"where id_store = ? and id_area = ? ";
						
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setInt(1, Integer.parseInt($id_area_from));
						st.setInt(2, Integer.parseInt($id_area_to));
						st.setInt(3, Integer.parseInt($id_store));
						st.setInt(4, Integer.parseInt($id_area));
						
						// Esegue l'sql
						st.executeUpdate();
					}
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MOD...");
					
					$id_area = req.getParameter("id_area");
					$description = req.getParameter("description");
					$codice = req.getParameter("codice");
					$esterna = req.getParameter("esterna");
					boolean est = false;
					if($esterna.equals("t"))
						est = true;
					else
						est = false;
					System.out.println($esterna);
					$gruppo = req.getParameter("gruppo");
					$tipo = req.getParameter("tipo");
					
					if($tipo.equals("REAL")) {
						sql  =  "update tab_aree "+
								"set descrizione = ?, codice = ?, esterna = ?, gruppo = ?, tipo = ?, "+
								"id_area_from = null, id_area_to = null "+
								"where id_store = ? and id_area = ? ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1, $description);
						st.setString(2, $codice);
						st.setBoolean(3, est);
						st.setString(4, $gruppo);
						st.setString(5, $tipo);
						st.setInt(6, Integer.parseInt($id_store));
						st.setInt(7, Integer.parseInt($id_area));
					}
					else {
						$id_area_from = req.getParameter("id_area_from");
						$id_area_to = req.getParameter("id_area_to");
						
						sql  =  "update tab_aree "+
								"set descrizione = ?, codice = ?, esterna = ?, gruppo = ?, tipo = ?, "+
								"id_area_from = ?, id_area_to = ? "+
								"where id_store = ? and id_area = ? ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1, $description);
						st.setString(2, $codice);
						st.setBoolean(3, est);
						st.setString(4, $gruppo);
						st.setString(5, $tipo);
						st.setInt(6, Integer.parseInt($id_area_from));
						st.setInt(7, Integer.parseInt($id_area_to));
						st.setInt(8, Integer.parseInt($id_store));
						st.setInt(9, Integer.parseInt($id_area));
					}
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					$id_area = req.getParameter("id_area");
					
					sql   = "delete from tab_aree where id_store = ? and id_area = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($id_store));
					st.setInt(2, Integer.parseInt($id_area));
					
					// Esegue l'sql
					st.executeUpdate();
					
					// *blank id
					$id_area = "";
					
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
				// Dettaglio / Modifica / Elimina 
				sql  =  "select a.id_store, a.id_area, a.descrizione description, "
						+ "a.codice, b.description store_description, esterna, gruppo, tipo, id_area_from, id_area_to "
						+ "from tab_aree a, stores b "
						+ "where a.id_store = b.id "
						+ "and id_store = ? and id_area = ? ";
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setInt(1, Integer.parseInt($id_store));
				st.setInt(2, Integer.parseInt($id_area));
				// Esegue l'sql
				rs=st.executeQuery();
				
				if(rs.next()) {
					$description=rs.getString("description");
					$codice=rs.getString("codice");
					$esterna=rs.getString("esterna");
					$gruppo=rs.getString("gruppo");
					$gruppo=Utility.noNull($gruppo);
					
					$tipo=rs.getString("tipo");
					$tipo=Utility.noNull($tipo);
					$id_area_from=""+rs.getString("id_area_from");
					$id_area_from=Utility.noNull($id_area_from);
					$id_area_to=""+rs.getString("id_area_to");
					$id_area_to=Utility.noNull($id_area_to);
				}
				rs.close();
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				obj.put("key", "readonly");
			}
			
			obj.put("id_store", $id_store);
			obj.put("id_area", $id_area);
			obj.put("description", $description);
			obj.put("codice", $codice);
			obj.put("store_description", $store_description);
			obj.put("esterna", $esterna);
			obj.put("gruppo", $gruppo);
			obj.put("tipo", $tipo);
			obj.put("id_area_from", $id_area_from);
			obj.put("id_area_to", $id_area_to);
			
			// ====================
			// Dettaglio store
			sql  =  "select * from stores where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt($id_store));
			// Esegue l'sql
			rs=st.executeQuery();
			if(rs.next()) {
				
				obj.put("address", rs.getString("address"));
				obj.put("country", rs.getString("country"));
				obj.put("city", rs.getString("city"));
				obj.put("postalcode", rs.getString("postalcode"));
				obj.put("region", rs.getString("region"));
				obj.put("cphone", rs.getString("cphone"));
				obj.put("sphone", rs.getString("sphone"));
				obj.put("cmail", rs.getString("cmail"));
				obj.put("smail", rs.getString("smail"));
				obj.put("brandname", rs.getString("brandname"));
				obj.put("brandlogo", rs.getString("brandlogo"));
				
			}
			rs.close();
			st.close();
			
			// Lista Tipo Gruppi Aree
			sql   = "select xcdel, xdtab from ztables where xcdtb = 'GRA' and ata32 = ' ' ";
			stAdmin = connAdmin.prepareStatement(sql);
			rsAdmin=stAdmin.executeQuery();
			while(rsAdmin.next()) {
				tp.add(new Tables("GRA",rsAdmin.getString("xcdel").trim(),rsAdmin.getString("xdtab").trim(),
						"","","",""));
			}
			rsAdmin.close();
			stAdmin.close();
			
			// Lista Tipo Tipi Aree
			sql   = "select xcdel, xdtab from ztables where xcdtb = 'TPA' and ata32 = ' ' ";
			stAdmin = connAdmin.prepareStatement(sql);
			rsAdmin=stAdmin.executeQuery();
			while(rsAdmin.next()) {
				tp2.add(new Tables("TPA",rsAdmin.getString("xcdel").trim(),rsAdmin.getString("xdtab").trim(),
						"","","",""));
			}
			rsAdmin.close();
			stAdmin.close();
			
			// Stores
			// --------------
			// sql da eseguire
			sql  =  "select a.*, c.brandname custbrand, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id and a.id="+$id_store+" "
					+ "and a.id in(select idstore::integer from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $sto_description="";
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
			
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$sto_description=rs.getString("description");
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
				
				sto.add(new Stores($sto_description,$address,$country,$city,
						$postalcode,$region,$contact,$cphone,$sphone,$cmail,$smail,
						$coordinates,$store_code,$brandname,$brandlogo,$id_customer,
						$id,$cmobile,$province,$id_optempl,$customeridstore,$sapid,
						$latitude,$longitude,$notes,$openhh,$closehh,
						"","","",
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			// Lista Aree per device
			sql  =  "select a.id_store, a.id_area, a.descrizione description, "
					+ "a.codice, b.description store_description, esterna, gruppo, tipo, id_area_from, id_area_to "
					+ "from tab_aree a, stores b "
					+ "where a.id_store = b.id and a.id_store = "+$id_store+" and tipo='REAL' ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $area_id_store="";
			String $area_id_area="";
			String $area_description="";
			String $area_codice="";
			String $area_store_description="";
			String $area_esterna="";
			String $area_gruppo="";
			String $area_tipo="";
		
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$area_id_store=rs.getString("id_store");
				$area_id_area=rs.getString("id_area");
				$area_description=rs.getString("description");
				$area_codice=rs.getString("codice");
				$area_store_description="";
				if($area_esterna.toUpperCase().equals("t"))
					$area_esterna="OUT";
				else
					$area_esterna="IN";
				$area_gruppo=rs.getString("gruppo");
				$area_tipo=rs.getString("tipo");
				aree.add(new Aree($area_id_store,$area_id_area,$area_description,$area_codice,$area_store_description,
						$area_esterna,$area_gruppo,$area_tipo,
						"","",$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			
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
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Area");
		obj.put("tipoForm", tipoForm);
		obj.put("sto", sto);
		obj.put("tp", tp);
		obj.put("tp2", tp2);
		obj.put("aree", aree);
		
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/app/anag/aree-dett", obj);
	}
	
}

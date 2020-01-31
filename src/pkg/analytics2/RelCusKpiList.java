package pkg.analytics2;

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

import pkg.beans.Customers;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelCusKpiList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Customers> cus = new ArrayList<Customers>();
		List<Tables> kpi = new ArrayList<Tables>();
		
		// Pulisce la lista
		cus.clear();
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
		
		String whereIAm = req.getParameter("app");
		obj.put("whereIAm", whereIAm);
		
		String idcus = ""+req.getParameter("idcus");
		idcus = Utility.noNull(idcus);
		if(idcus.length()==0)
			idcus = "0";
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String idkpi = ""+req.getParameter("idkpi");
		idkpi = Utility.noNull(idkpi);
		
		obj.put("idcus", idcus);
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
			// Apre la connessione Admin
			connAdmin = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				// legge l'eventuale kpi di counter
				sql   = "select * from rel_cus_kpi where idcus = ? and counter = true ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(idcus));
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String kpi_counter = "";
				
				if(rs.next()) {
					kpi_counter = rs.getString("idkpi");
				}
				rs.close();
				st.close();
				
				// ---------
				
				// legge l'eventuale kpi di counter
				sql   = "select * from rel_cus_kpi where idcus = ? and counterout = true ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(idcus));
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String kpi_counter_out = "";
				
				if(rs.next()) {
					kpi_counter_out = rs.getString("idkpi");
				}
				rs.close();
				st.close();
				
				// ----------
				
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "delete from rel_cus_kpi where idcus = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(idcus));
				
				// Esegue l'sql
				st.executeUpdate();
				
				// Inserimento in rel_user_customers
				String temp1[] = idkpi.split(",");
				
				for(int c=0; c<temp1.length; c++) {
					
					if(temp1[c].length()>0) {
						
						System.out.println(temp1[c]);
						sql   = "insert into rel_cus_kpi "+
								"(idcus, idkpi, visorder) "+
								"values "+
								"(?, ?, ?) ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setInt(1, Integer.parseInt(idcus));
						st.setString(2, temp1[c]);
						st.setInt(3, 0);
						
						// Esegue l'sql
						st.executeUpdate();
						
					}
					
					msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				}
				
				// update rel_cus_kpi per eventuale kpi counter
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "update rel_cus_kpi set counter = true where idcus = ? and idkpi = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(idcus));
				st.setString(2, kpi_counter);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// -----------
				
				// update rel_cus_kpi per eventuale kpi counter
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "update rel_cus_kpi set counterout = true where idcus = ? and idkpi = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(idcus));
				st.setString(2, kpi_counter_out);
				
				// Esegue l'sql
				st.executeUpdate();
				
			}
			
			
			
			// ==================
			// Lista Customers
			sql  =  "select * , '"+(String)session.getAttribute("username")+"' as user from customers a where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "union all "
					+ "select * from customerscountry b where idusr = '"+(String)session.getAttribute("username")+"' ";
			
			// Prepara il cursore
			stAdmin = connAdmin.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stAdmin.executeQuery();
			
			// Campi database
			String $companyname;
			String $description;
			String $contact;
			String $brandname;
			String $brandlogo;
			String $account_code;
			String $id;
			String $phone;
			String $mail;
			String $mobile;
			String $id_optempl;
			String $customer_country_id;
			String $agreement;
			String $wskey;
			String $wsdecrypt_key;
			String $wscrypt;
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$companyname=rs.getString("companyname");
				$description=rs.getString("description");
				$contact=rs.getString("contact");
				$brandname=rs.getString("brandname");
				$brandlogo=rs.getString("brandlogo");
				$account_code=rs.getString("account_code");
				$id=rs.getString("id");
				$phone=rs.getString("phone");
				$mail=rs.getString("mail");
				$mobile=rs.getString("mobile");
				$id_optempl=rs.getString("id_optempl");
				$customer_country_id=rs.getString("customer_country_id");
				$agreement=rs.getString("agreement");
				$wskey=rs.getString("wskey");
				$wsdecrypt_key=rs.getString("wsdecrypt_key");
				$wscrypt=rs.getString("wscrypt");
				
				cus.add(new Customers($companyname,$description,$contact,$brandname,
						$brandlogo,$account_code,$id,$phone,$mail,$mobile,$id_optempl,
						$customer_country_id,$agreement,$wskey,$wsdecrypt_key,$wscrypt,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			// Fine Lista Utenti
			// ==================
			
			// sql da eseguire
			sql  =  "select a.* , b.idkpi, b.counter, b.counterout "
					+ "from ztables a "
					+ "left join rel_cus_kpi b on trim(a.xcdel) = trim(b.idkpi) and b.idcus = "+idcus+" "
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
			
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			while(rs.next()) {
				
				if(rs.getString("xcdel").equals(rs.getString("idkpi"))) {
					$cmp3 = "checked";
				}
				count+=1;
				
				if(rs.getBoolean("counter")) 
					$cmp2 = "S";
				else
					$cmp2 = "";
				
				if(rs.getBoolean("counterout")) 
					$cmp4 = "S";
				else
					$cmp4 = "";
				
				$xcdtb=rs.getString("xcdtb");
				$xcdel=rs.getString("xcdel");
				$xdtab=rs.getString("xdtab");
				$xdesc=rs.getString("xdesc");
				
				kpi.add(new Tables($xcdtb,$xcdel,$xdtab,$xdesc,
						$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Clientu / Kpi");
		obj.put("cus", cus);
		obj.put("kpi", kpi);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/analytics2/rel/rel_cus_kpi-list", obj);
	}
	
}

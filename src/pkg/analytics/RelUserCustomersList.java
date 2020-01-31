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

import pkg.beans.Customers;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelUserCustomersList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Users> users = new ArrayList<Users>();
		List<Customers> cus = new ArrayList<Customers>();
		
		// Pulisce la lista
		users.clear();
		cus.clear();
		
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
		
		String idcustomers = ""+req.getParameter("idcustomers");
		idcustomers = Utility.noNull(idcustomers);
		
		obj.put("idusr", idusr);
		
		String arrayCustomers = (String) session.getAttribute("arrayCustomers");
		if(arrayCustomers.length()==0)
			arrayCustomers = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			// Apre la connessione Admin
			connAdmin = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				/*
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "delete from rel_user_customers where idusr = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, idusr);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// Inserimento in rel_user_customers
				String temp1[] = idcustomers.split(",");
				
				for(int c=0; c<temp1.length; c++) {
					
					System.out.println(temp1[c]);
					sql   = "insert into rel_user_customers "+
							"(idusr, idcustomer) "+
							"values "+
							"(?, ?) ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, idusr.toUpperCase());
					st.setInt(2, Integer.parseInt(temp1[c]));
					
					// Esegue l'sql
					st.executeUpdate();
					
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
			sql  =  "select a.*, b.idcustomer from customers a "
					+ "left join rel_user_customers b on a.id = b.idcustomer::integer and b.idusr = '"+idusr+"' ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and brandname || description like ? ";
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
				
				if(rs.getString("id").equals(rs.getString("idcustomer"))) {
					$cmp1 = "checked";
				}
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
				
				$cmp1 = "";
				
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
		
		obj.put("dsfun", "Utenti / Clienti");
		obj.put("users", users);
		obj.put("cus", cus);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/rel/rel_user_customers-list", obj);
	}
	
}

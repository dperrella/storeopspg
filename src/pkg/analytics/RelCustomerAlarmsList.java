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
import pkg.beans.Eventcode;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelCustomerAlarmsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Customers> cus = new ArrayList<Customers>();
		List<Eventcode> ec = new ArrayList<Eventcode>();
		
		// Pulisce la lista
		cus.clear();
		ec.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String idcustomer = ""+req.getParameter("idcustomer");
		idcustomer = Utility.noNull(idcustomer);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String idalarms = ""+req.getParameter("idalarms");
		idalarms = Utility.noNull(idalarms);
		
		obj.put("idcustomer", idcustomer);
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			
			if(azione.length()>0) {
				
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "delete from rel_customer_alarms where idcustomer = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(idcustomer));
				
				// Esegue l'sql
				st.executeUpdate();
				
				// Inserimento in rel_user_customers
				String temp1[] = idalarms.split(",");
				
				for(int c=0; c<temp1.length; c++) {
					
					System.out.println(temp1[c]);
					sql   = "insert into rel_customer_alarms "+
							"(idcustomer, alarmcode) "+
							"values "+
							"(?, ?) ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt(idcustomer));
					st.setString(2, temp1[c]);
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				}
				
			}
			
			
			
			// ==================
			// Lista Customers
			sql  =  "select * from customers where "
					+ "id in(select idcustomer::integer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $companyname;
			String $cust_description;
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
			
			while(rs.next()) {
				
				count+=1;
				
				$companyname=rs.getString("companyname");
				$cust_description=rs.getString("description");
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
				
				cus.add(new Customers($companyname,$cust_description,$contact,$brandname,
						$brandlogo,$account_code,$id,$phone,$mail,$mobile,$id_optempl,
						$customer_country_id,$agreement,$wskey,$wsdecrypt_key,$wscrypt,
						"","","",""));
				
			}
			rs.close();
			// Fine Lista Customers
			// ==================
			
			if(idcustomer.length()==0)
				idcustomer = "0";
			
			// sql da eseguire
			sql  =  "select a.*, b.alarmcode from analytics_eventcode a "
					+ "left join rel_customer_alarms b on a.code = b.alarmcode and b.idcustomer = "+idcustomer+" ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" where a.description || a.range_description like ? ";
			}
			
			sql += " order by CAST(coalesce(a.code, '0') AS integer) ";
			
			System.out.println(sql);
			
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
			String $code="";
			String $description="";
			String $range_description="";
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				if(rs.getString("code").equals(rs.getString("alarmcode"))) {
					$cmp1 = "checked";
				}
				count+=1;
				
				$code=rs.getString("code");
				$description=rs.getString("description");
				$range_description=rs.getString("range_description");
				
				ec.add(new Eventcode($code,$description,$range_description,$cmp1,$cmp2,$cmp3,$cmp4));
				
				$cmp1 = "";
				
			}
			rs.close();
			
			if(st!=null)
				st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Clienti / Allarmi");
		obj.put("cus", cus);
		obj.put("ec", ec);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/rel/rel_customer_alarms-list", obj);
	}
	
}

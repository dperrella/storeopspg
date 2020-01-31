package pkg.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class CustomersDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> naz = new ArrayList<Tables>();
		List<Tables> tmp = new ArrayList<Tables>();
		List<Stores> sto = new ArrayList<Stores>();
		
		// Pulisce la lista
		naz.clear();
		tmp.clear();
		sto.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		String key = "";
		
		try {
			
			// Campi database
			String $companyname="";
			String $description="";
			String $contact="";
			String $brandname="";
			String $brandlogo="";
			String $account_code="";
			String $account_code_evolve="";
			String $phone="";
			String $mail="";
			String $mobile="";
			String $id_optempl="";
			String $customer_country_id="";
			String $agreement="";
			String $wskey="";
			String $wsdecrypt_key="";
			String $wscrypt="";
			String $date_agree_start="";
			String $date_agree_end="";
			
			
			String $analytics_export = "f";
			
			String $id_customers = ""+req.getParameter("id");
			$id_customers=Utility.noNull($id_customers);
			
			// Apre la connessione
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					// Inserimento
					System.out.println("INS...");
					
					$companyname=req.getParameter("companyname");
					$description=req.getParameter("description");
					$contact=req.getParameter("contact");
					$brandname=req.getParameter("brandname");
					$brandlogo=req.getParameter("brandlogo");
					$account_code=req.getParameter("account_code");
					$account_code_evolve=req.getParameter("account_code_evolve");
					$phone=req.getParameter("phone");
					$mail=req.getParameter("mail");
					$mobile=req.getParameter("mobile");
					$id_optempl=""+req.getParameter("id_optempl");
					$id_optempl=Utility.noNull($id_optempl);
					$customer_country_id=req.getParameter("customer_country_id");
					$agreement=req.getParameter("agreement");
					$wskey=req.getParameter("wskey");
					$wsdecrypt_key=req.getParameter("wsdecrypt_key");
					$wscrypt=req.getParameter("wscrypt");
					$date_agree_start=req.getParameter("date_agree_start");
					$date_agree_end=req.getParameter("date_agree_end");
					
					String $id = Utility.getNewProgr(conn, "customers", "id", "");
					
					
					if($id_optempl.trim().length()==0)
						$id_optempl = "0";
					
					boolean esiste = false;
					sql   = "select id from customers where account_code::text = '"+$account_code+"'";	
					rs=st.executeQuery(sql);
					if(rs.next()) {
						esiste = true;
					}
					rs.close();
					
					if(esiste) {
						msg="<div class=\"alert alert-danger\"><strong>Account Code Already Exist</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "I";
					}
					else {
						sql = "INSERT INTO customers( "+
							  "id, companyname, description, contact, brandname, brandlogo, account_code, "+ 
							  "phone, mail, mobile, id_optempl, customer_country_id, agreement, wskey, wsdecrypt_key, wscrypt, date_agree_start,date_agree_end, account_code_evolvestore) "+
							  "VALUES ("+$id+" , '"+$companyname.replaceAll("'", "''")+"', '"+$description.replaceAll("'", "''")+"', '"+$contact.replaceAll("'", "''")+"', '"+$brandname.replaceAll("'", "''")+"', '"+$brandlogo.replaceAll("'", "''")+"', '"+$account_code.replaceAll("'", "''")+"', "+ 
							  "'"+$phone.replaceAll("'", "''")+"', '"+$mail.replaceAll("'", "''")+"', '"+$mobile.replaceAll("'", "''")+"', "+$id_optempl+", "+$customer_country_id+", "+$agreement+" , null, null, null , '"+$date_agree_start+"', '"+$date_agree_end+"','"+$account_code_evolve.replaceAll("'", "''")+"') returning id ";
						System.out.println(sql);
						
						//insert
						rs = st.executeQuery(sql);
						if(rs.next()) {
							$id_customers = rs.getString("id");
						}
						rs.close();
						System.out.println("sql_insert = " + sql);
	
						$analytics_export = req.getParameter("analytics_export");
						if ($analytics_export.equals("true") || $analytics_export.equals("t")){
						
							sql = "select * from customers where account_code = '"+$account_code+"'";
							rs = st.executeQuery(sql);
							while(rs.next()) {
								$id_customers = rs.getString("id");
							}
							rs.close();
							sql = "INSERT INTO customers_analytics( "+
								  "accountid, id_customers)"+
								  "VALUES ('"+$account_code+"', '"+$id_customers+"')";
							st.executeUpdate(sql);
							
						}
						
						msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "M";
					}
					
				}
				else if(tipoForm.equals("M")) {
					// Modifica
					System.out.println("MOD...");
					
					$companyname=req.getParameter("companyname");
					$description=req.getParameter("description");
					$contact=req.getParameter("contact");
					$brandname=req.getParameter("brandname");
					$brandlogo=req.getParameter("brandlogo");
					$account_code=req.getParameter("account_code");
					$account_code_evolve=req.getParameter("account_code_evolve");
					$phone=req.getParameter("phone");
					$mail=req.getParameter("mail");
					$mobile=req.getParameter("mobile");
					$id_optempl=""+req.getParameter("id_optempl");
					$id_optempl=Utility.noNull($id_optempl);
					$customer_country_id=req.getParameter("customer_country_id");
					$agreement=req.getParameter("agreement");
					$wskey=req.getParameter("wskey");
					$wsdecrypt_key=req.getParameter("wsdecrypt_key");
					$wscrypt=req.getParameter("wscrypt");
					$date_agree_start=req.getParameter("date_agree_start");
					$date_agree_end=req.getParameter("date_agree_end");
					
					if($id_optempl.trim().length()==0)
						$id_optempl = "0";
					
					boolean esiste = false;
					sql   = "select id from customers where account_code::text = '"+$account_code+"' and id<>"+$id_customers+"";	
					System.out.println(sql);
					rs=st.executeQuery(sql);
					if(rs.next()) {
						esiste = true;
					}
					rs.close();
					
					if(esiste) {
						msg="<div class=\"alert alert-danger\"><strong>Account Code Already Exist</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "M";
					}
					else {
					
						sql   = "update customers set "
								+ "companyname = '"+$companyname.replaceAll("'", "''")+"' , "
								+ "description = '"+$description.replaceAll("'", "''")+"' , "
								+ "contact = '"+$contact.replaceAll("'", "''")+"' , "
								+ "brandname = '"+$brandname.replaceAll("'", "''")+"' , "
								+ "brandlogo = '"+$brandlogo.replaceAll("'", "''")+"' , "
								+ "account_code = '"+$account_code.replaceAll("'", "''")+"' , "
								+ "phone = '"+$phone.replaceAll("'", "''")+"' , "
								+ "mail = '"+$mail.replaceAll("'", "''")+"' , "
								+ "mobile = '"+$mobile.replaceAll("'", "''")+"' , "
								+ "id_optempl = "+$id_optempl+" , "
								+ "customer_country_id = "+$customer_country_id+" , "
								+ "agreement = "+$agreement+" , "
								+ "date_agree_start = '"+$date_agree_start+"' , "
								+ "date_agree_end = '"+$date_agree_end+"',  "
								+ "account_code_evolvestore = '"+$account_code_evolve.replaceAll("'", "''")+"'  "
								+ "where id = "+$id_customers+" ";
						
						System.out.println(sql);
						st.executeUpdate(sql);
								
						$analytics_export = req.getParameter("analytics_export");
						if ($analytics_export.equals("true") || $analytics_export.equals("t")){
							sql   = "delete from customers_analytics where id_customers = '"+$id_customers+"' ";
							st.executeUpdate(sql);
							
							sql = "INSERT INTO customers_analytics( "+
								  "accountid, id_customers)"+
								  "VALUES ('"+$account_code+"', '"+$id_customers+"')";
							st.executeUpdate(sql);
						}
						else {
							
						}
						
						
						msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					}
				}
				else if(tipoForm.equals("E")) {
					// Cancellazione
					System.out.println("ELI...");
									
					sql = "delete from f_stores where id_customer = "+$id_customers+" ";
					st.executeUpdate(sql);
					
					/*
					sql   = "delete from customers_analytics where id_customers = '"+$id_customers+"' ";
					st.executeUpdate(sql);
					*/
					
					sql = "delete from customers where id = "+$id_customers+" ";
					st.executeUpdate(sql);
					
					
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
				
				if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
					key = "readonly";	
				}
				
				
				sql   = "select * from customers where id = "+$id_customers;
				rs=st.executeQuery(sql);
				if(rs.next()) {
					
					$companyname=rs.getString("companyname");
					$description=rs.getString("description");
					$contact=rs.getString("contact");
					$brandname=rs.getString("brandname");
					$brandlogo=rs.getString("brandlogo");
					$account_code=rs.getString("account_code");
					$phone=rs.getString("phone");
					$mail=rs.getString("mail");
					$mobile=rs.getString("mobile");
					$id_optempl=""+rs.getString("id_optempl");
					$id_optempl=Utility.noNull($id_optempl);
					$customer_country_id=rs.getString("customer_country_id");
					$agreement=rs.getString("agreement");
					$wskey=rs.getString("wskey");
					$wsdecrypt_key=rs.getString("wsdecrypt_key");
					$wscrypt=rs.getString("wscrypt");
					$date_agree_start=rs.getString("date_agree_start");
					$date_agree_end=rs.getString("date_agree_end");
					$account_code_evolve=rs.getString("account_code_evolvestore");
					
				}
				rs.close();
				
				/*
				// Controlla se è presente un record in customers_analytics
				sql   = "select count(id_customers) num from customers_analytics where id_customers = '"+$id_customers+"' ";				
				rs=st.executeQuery(sql);
				if(rs.next()) {
					if(rs.getInt("num")>0)
						$analytics_export = "t";
					else {
						$analytics_export = "f";
					}
				}
				rs.close();
				*/
				
				
				// Negozi del cliente
				sql  =  "select a.*, c.brandname custbrand, "
						+ "(select storeid from devices d where d.id_store = a.id fetch first 1 rows only) storeid, "
						+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
						+ "from stores a, customers c "
						+ "where a.id_customer = c.id "
						+ "and c.id = "+$id_customers;
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					String storeid = ""+rs.getString("storeid");
					storeid = Utility.noNull(storeid);
					if(storeid.length()==0)
						storeid = ""+rs.getString("store_code");
					
					storeid = Utility.noNull(storeid);
					if(storeid.length()==0)
						storeid = ""+rs.getString("id");
					
					
					sto.add(new Stores(rs.getString("description"),rs.getString("address"),rs.getString("country"),rs.getString("city"),
							rs.getString("postalcode"),rs.getString("region"),rs.getString("contact"),rs.getString("cphone"),rs.getString("sphone"),rs.getString("cmail"),rs.getString("smail"),
							rs.getString("coordinates"),rs.getString("store_code"),rs.getString("brandname"),rs.getString("brandlogo"),rs.getString("id_customer"),
							rs.getString("id"),rs.getString("cmobile"),rs.getString("province"),rs.getString("id_optempl"),rs.getString("customeridstore"),rs.getString("sapid"),
							rs.getString("latitude"),rs.getString("longitude"),rs.getString("notes"),rs.getString("openhh"),rs.getString("closehh"),
							"","","",
							rs.getString("custbrand"),rs.getString("nrdevices"),storeid,""));
					
				}
				rs.close();
			}
			
			// ====================
			// Lista nazioni
			sql = "select distinct * from nazioni order by nome_nazione";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				naz.add(new Tables("NAZ",rs.getString("id_naz"),rs.getString("nome_nazione"),"","","",""));
			}
			rs.close();
			
			
			// ====================
			// Lista templates
			sql = "select * from optempl order by description";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				tmp.add(new Tables("NAZ",rs.getString("id"),rs.getString("description"),"","","",""));
			}
			rs.close();
			
			obj.put("id_customers", $id_customers);
			obj.put("companyname", $companyname);
			obj.put("description", $description);
			obj.put("contact", $contact);
			obj.put("brandname", $brandname);
			obj.put("brandlogo", $brandlogo);
			obj.put("account_code", $account_code);
			obj.put("phone", $phone);
			obj.put("mail", $mail);
			obj.put("mobile", $mobile);
			obj.put("id_optempl", $id_optempl);
			obj.put("customer_country_id", $customer_country_id);
			obj.put("agreement", $agreement);
			obj.put("wskey", $wskey);
			obj.put("wsdecrypt_key", $wsdecrypt_key);
			obj.put("wscrypt", $wscrypt);
			obj.put("date_agree_start", $date_agree_start);
			obj.put("date_agree_end", $date_agree_end);
			obj.put("analytics_export", $analytics_export);
			obj.put("account_code_evolve", $account_code_evolve);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("dsfun", "Dettaglio Customers");
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		
		obj.put("naz", naz);
		obj.put("tmp", tmp);
		obj.put("sto", sto);
		
		return new ModelAndView("/app/anag/customers-dett", obj);
	}
	
}

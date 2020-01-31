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
import pkg.beans.HR.Departments;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StoresDett_20191011 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> naz = new ArrayList<Tables>();
		List<Tables> reg = new ArrayList<Tables>();
		List<Tables> pro = new ArrayList<Tables>();
		List<Tables> cit = new ArrayList<Tables>();
		List<Tables> tmp = new ArrayList<Tables>();
		List<Tables> cus = new ArrayList<Tables>();
		List<Tables> dev = new ArrayList<Tables>();
		
		List<Tables> tmz = new ArrayList<Tables>();
		
		// Pulisce la lista
		naz.clear();
		reg.clear();
		pro.clear();
		cit.clear();
		tmp.clear();
		cus.clear();
		
		tmz.clear();
		
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
			String $flag_analytics = "f";
			String $description="";
			String $store_code="";
			String $id_customer="";
			String $country="0";
			String $region="0";
			String $province="0";
			String $city="0";
			String $address="";
			String $postalcode="";
			String $sphone="";
			String $smail="";
			String $id_optempl="";
			String $sapid="";
			String $customeridstore="";
			String $brandname="";
			String $brandlogo="";
			String $notes="";
			String $contact="";
			String $cphone="";
			String $cmobile="";
			String $cmail="";
			String $flagclose="";
			String accountCode = "";
			
			// PTF
			String $adjustment_factor="";
			
			// PTF
			String $timezone="";
			
			$id_customer = ""+req.getParameter("id_customer");
			$id_customer=Utility.noNull($id_customer);
			
			String $storeid = ""+req.getParameter("id");
			$storeid=Utility.noNull($storeid);
			
			String $customerid = ""+req.getParameter("customerid");
			$customerid=Utility.noNull($customerid);
			
			obj.put("customerid", $customerid);
			
			// Apre la connessione
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					// Inserimento
					System.out.println("INS...");
					
					$flag_analytics = "f";
					$description=req.getParameter("description");
					$store_code=req.getParameter("store_code");
					$id_customer=req.getParameter("id_customer");
					$country=req.getParameter("country");
					$region=""+req.getParameter("region");
					$province=""+req.getParameter("province");
					$city=""+req.getParameter("city");
					$address=req.getParameter("address");
					$postalcode=req.getParameter("postalcode");
					$sphone=req.getParameter("sphone");
					$smail=req.getParameter("smail");
					$id_optempl=""+req.getParameter("id_optempl");
					$id_optempl=Utility.noNull($id_optempl);
					if($id_optempl.trim().length()==0)
						$id_optempl = "null";
					$sapid=req.getParameter("sapid");
					$customeridstore=req.getParameter("customeridstore");
					$brandname=req.getParameter("brandname");
					$brandlogo=req.getParameter("brandlogo");
					$notes=req.getParameter("notes");
					$contact=req.getParameter("contact");
					$cphone=req.getParameter("cphone");
					$cmobile=req.getParameter("cmobile");
					$cmail=req.getParameter("cmail");
					$flagclose=""+req.getParameter("flagclose");
					if($flagclose.equals("null"))
						$flagclose = "false";
					
					String $id = Utility.getNewProgr(conn, "f_stores", "id", "");
					$storeid = $id;
					
					if($province.trim().length()==0)
						$province = "null";
					if($region.trim().length()==0)
						$region = "null";
					if($city.trim().length()==0)
						$city = "null";
					
					$adjustment_factor = ""+req.getParameter("adjustment_factor");
					if($adjustment_factor.equals("null"))
						$adjustment_factor = "0";
					
					
					$timezone=""+req.getParameter("timezone");
					$timezone=Utility.noNull($timezone);
					

					
					boolean esiste = false;
					sql   = "select account_code from customers where id = "+$id_customer;
					String $ac="";
					rs=st.executeQuery(sql);
					if(rs.next()) {
						$ac=rs.getString("account_code");
					}
					rs.close();
					sql   = "select id from f_stores where id_customer = "+$id_customer+" and store_code::text = '"+$store_code+"'";
					rs=st.executeQuery(sql);
					if(rs.next()) {
						esiste = true;
					}
					rs.close();
					
					if(esiste) {
						msg="<div class=\"alert alert-danger\"><strong>Account Code / Store ID Already Exist</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "I";
					}
					else {
					
						// Non lo scrive in inserimento perché non ha ancora il devices...
						
						sql   = "INSERT INTO f_stores( "
								+ "id, description, store_code, id_customer, country, region, province, "
								+ "city, address, postalcode, sphone, smail, id_optempl, sapid, customeridstore,"
								+ "brandname, brandlogo, notes, contact, cphone, cmobile, cmail, flagclose, flag_analytics, timezone ) "
								+ "values ("
								+ $id+" , '"+$description.replaceAll("'", "''")+"' , "
								+ "'"+$store_code.replaceAll("'", "''")+"', "+$id_customer+" , "
								+ $country+" , "+$region+" , "+$province+" , "+$city+" , "
								+ "'"+$address.replaceAll("'", "''")+"', '"+$postalcode.replaceAll("'", "''")+"' , "
								+ "'"+$sphone.replaceAll("'", "''")+"', '"+$smail.replaceAll("'", "''")+"' , "
								+ $id_optempl+" , '"+$sapid.replaceAll("'", "''")+"', '"+$customeridstore.replaceAll("'", "''")+"' , "
								+ "'"+$brandname.replaceAll("'", "''")+"', '"+$brandlogo.replaceAll("'", "''")+"' , "
								+ "'"+$notes.replaceAll("'", "''")+"', '"+$contact.replaceAll("'", "''")+"' , "
								+ "'"+$cphone.replaceAll("'", "''")+"', '"+$cmobile.replaceAll("'", "''")+"' , "
								+ "'"+$cmail.replaceAll("'", "''")+"' , "+$flagclose+", false, '"+$timezone+"' ) ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
						// ****************************
						// Inserisce il department (HR)
						try {
							Departments dep = new Departments();
							dep.setDescription($description);
							dep.setDepartment_code($store_code);
							dep.setMinnum_intervals("0");
							dep.setTollerance("10");
							dep.setNocturne("22");
							dep.setId_store(Integer.parseInt($id));
							dep.setDepartment_type(1);
							dep.setId_customer(Integer.parseInt($id_customer));
							
							dep.insert();
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						// ****************************
						
						
											
						msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "M";
					}
				}
				else if(tipoForm.equals("M")) {
					// Modifica
					System.out.println("MOD...");
					
					$flag_analytics = "f";
					$description=req.getParameter("description");
					$store_code=req.getParameter("store_code");
					$id_customer=req.getParameter("id_customer");
					$country=req.getParameter("country");
					$region=""+req.getParameter("region");
					$province=""+req.getParameter("province");
					$city=""+req.getParameter("city");
					$address=req.getParameter("address");
					$postalcode=req.getParameter("postalcode");
					$sphone=req.getParameter("sphone");
					$smail=req.getParameter("smail");
					$id_optempl=""+req.getParameter("id_optempl");
					$id_optempl=Utility.noNull($id_optempl);
					if($id_optempl.trim().length()==0)
						$id_optempl = "null";
					$sapid=req.getParameter("sapid");
					$customeridstore=req.getParameter("customeridstore");
					$brandname=req.getParameter("brandname");
					$brandlogo=req.getParameter("brandlogo");
					$notes=req.getParameter("notes");
					$contact=req.getParameter("contact");
					$cphone=req.getParameter("cphone");
					$cmobile=req.getParameter("cmobile");
					$cmail=req.getParameter("cmail");
					$flagclose=""+req.getParameter("flagclose");
					if($flagclose.equals("null"))
						$flagclose = "false";
					
					if($province.trim().length()==0)
						$province = "null";
					if($region.trim().length()==0)
						$region = "null";
					if($city.trim().length()==0)
						$city = "null";
					
					$adjustment_factor = ""+req.getParameter("adjustment_factor");
					if($adjustment_factor.equals("null"))
						$adjustment_factor = "0";
					
					$timezone=""+req.getParameter("timezone");
					$timezone=Utility.noNull($timezone);
					
					boolean esiste = false;
					sql   = "select account_code from customers where id = "+$id_customer;
					String $ac="";
					rs=st.executeQuery(sql);
					if(rs.next()) {
						$ac=rs.getString("account_code");
					}
					rs.close();
					sql   = "select id from f_stores where id_customer = "+$id_customer+" and store_code::text = '"+$store_code+"' and id<>"+$storeid+"";
					System.out.println(sql);
					rs=st.executeQuery(sql);
					if(rs.next()) {
						esiste = true;
					}
					rs.close();
					
					if(esiste) {
						msg="<div class=\"alert alert-danger\"><strong>Account Code / Store ID Already Exist</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "M";
					}
					else {
						sql   = "update f_stores set "
								+ "description = '"+$description.replaceAll("'", "''")+"' , "
								+ "store_code = '"+$store_code.replaceAll("'", "''")+"' , "
								+ "id_customer = "+$id_customer+" , "
								+ "country = "+$country+" , "
								+ "region = "+$region+" , "
								+ "province = "+$province+" , "
								+ "city = "+$city+" , "
								+ "address = '"+$address.replaceAll("'", "''")+"' , "
								+ "postalcode = '"+$postalcode.replaceAll("'", "''")+"' , "
								+ "sphone = '"+$sphone.replaceAll("'", "''")+"' , "
								+ "smail = '"+$smail.replaceAll("'", "''")+"' , "
								+ "id_optempl = "+$id_optempl+" , "
								+ "sapid = '"+$sapid.replaceAll("'", "''")+"' , "
								+ "customeridstore = '"+$customeridstore.replaceAll("'", "''")+"' , "
								+ "brandname = '"+$brandname.replaceAll("'", "''")+"' , "
								+ "brandlogo = '"+$brandlogo.replaceAll("'", "''")+"' , "
								+ "notes = '"+$notes.replaceAll("'", "''")+"' , "
								+ "contact = '"+$contact.replaceAll("'", "''")+"' , "
								+ "cphone = '"+$cphone.replaceAll("'", "''")+"' , "
								+ "cmobile = '"+$cmobile.replaceAll("'", "''")+"' , "
								+ "cmail = '"+$cmail.replaceAll("'", "''")+"' , "
								+ "flagclose = "+$flagclose+", "
								+ "timezone = '"+$timezone+"'  "
								+ "where id = "+$storeid+" ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
						sql   = "update devices set "+
								"adjustment_factor = "+$adjustment_factor+" "+
								"where id_store = "+$storeid+" ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
						// ****************************
						// Modifica il department (HR)
						try {
							Departments dep = new Departments();
							dep.setDescription($description);
							dep.setDepartment_code($store_code);
							dep.setId_store(Integer.parseInt($storeid));
							
							dep.update();
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						// ****************************
						
						
						
						msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					}
				}
				else if(tipoForm.equals("E")) {
					// Cancellazione
					System.out.println("ELI...");
									
					sql   = "delete from devices where id_store = "+$storeid+" ";
					st.executeUpdate(sql);
					sql   = "delete from f_stores where id = "+$storeid+" ";
					st.executeUpdate(sql);
					
					// ****************************
					// Cancella il department (HR)
					try {
						Departments dep = new Departments();
						dep.setId_store(Integer.parseInt($storeid));
						
						dep.delete();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					// ****************************
					
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
				
				
				sql   = "select * from f_stores where id = "+$storeid;
				rs=st.executeQuery(sql);
				if(rs.next()) {
					
					$flag_analytics=rs.getString("flag_analytics");
					$description=rs.getString("description");
					$store_code=rs.getString("store_code");
					$id_customer=rs.getString("id_customer");
					$country=rs.getString("country");
					$region=rs.getString("region");
					$province=rs.getString("province");
					$city=rs.getString("city");
					$address=rs.getString("address");
					$postalcode=rs.getString("postalcode");
					$sphone=rs.getString("sphone");
					$smail=rs.getString("smail");
					$id_optempl=rs.getString("id_optempl");
					$sapid=rs.getString("sapid");
					$customeridstore=rs.getString("customeridstore");
					$brandname=rs.getString("brandname");
					$brandlogo=rs.getString("brandlogo");
					$notes=rs.getString("notes");
					$contact=rs.getString("contact");
					$cphone=rs.getString("cphone");
					$cmobile=rs.getString("cmobile");
					$cmail=rs.getString("cmail");
					$cmail=rs.getString("cmail");
					$flagclose=rs.getString("flagclose");
					$timezone=Utility.noNull(rs.getString("timezone"));
					
				}
				rs.close();
				
				// Lista devices del negozio
				sql  =  "select a.*, b.description, c.brandname, d.description ds_devicetype, e.description errdesc "
						+ "from devices a "
						+ "left join error_type e on e.errorcode = a.lasteventcode , "
						+ "f_stores b, customers c, devicetype d "
						+ "where a.id_store = b.id and b.id_customer = c.id "
						+ "and a.device_type = d.id "
						//+ "and a.id_store in(select idstore from rel_user_stores "
						//+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
						+"and a.id_store = "+$storeid;
				System.out.println(sql);
				rs=st.executeQuery(sql);
				
				String evcode = "";
				
				while(rs.next()) {
					evcode = ""+rs.getString("lasteventcode");
					evcode = Utility.noNull(evcode);
					
					if(evcode.trim().length()>0){
						if(evcode.equalsIgnoreCase("OK"))
							evcode = "<span class=\"pull-right\"><i class=\"icon-check text-success\"></i> OK</span>";
						else 
							evcode = "<a href=\"#\" class=\"pull-right\" data-toggle=\"popover\" data-html=\"true\" data-placement=\"bottom\" "
									+ " data-content=\""+Utility.noNull(rs.getString("errdesc"))+"\" "
									+ "title=\"\" data-original-title='<button type=\"button\" class=\"close pull-right\" data-dismiss=\"popover\">&times;</button>"+evcode+"'><i class=\"icon-ban text-danger\"></i> "+evcode+"</a>";
					}
					dev.add(new Tables("DEV",rs.getString("id"),rs.getString("ds_devicetype"),
							rs.getString("device_type"),rs.getString("ip"),rs.getString("accountid")+"-"+rs.getString("storeid")+"-"+rs.getString("doorid"),evcode));
				
					evcode = "";
				}
				rs.close();
				
				// Max Adjustment_factor dello store
				sql  =  "select max(adjustment_factor) af from devices where id_store = "+$storeid;
				System.out.println(sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$adjustment_factor = ""+rs.getString("af");
					$adjustment_factor = Utility.noNull($adjustment_factor);
				}
				rs.close();
				
				
				if($adjustment_factor.trim().length()==0)
					$adjustment_factor = "0";
			}
			
			// Negozi del cliente
			sql  =  "select * from customers order by brandname";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				cus.add(new Tables("CUS", rs.getString("id"),rs.getString("companyname"),rs.getString("brandname"),"","",""));
				
			}
			rs.close();
			
			// ====================
			// Lista nazioni
			sql = "select distinct * from nazioni order by nome_nazione";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				naz.add(new Tables("NAZ",rs.getString("id_naz"),rs.getString("nome_nazione"),"","","",""));
			}
			rs.close();
			
			// ====================
			// Lista regioni
			sql = "select * from regioni where id_naz = "+$country;
			rs=st.executeQuery(sql);
			while(rs.next()) {
				reg.add(new Tables("REG",rs.getString("id_reg"),rs.getString("nome_regione"),"","","",""));
			}
			rs.close();
			
			// ====================
			// Lista province
			try {
				sql = "select * from province where id_reg = "+$region;
				rs=st.executeQuery(sql);
				while(rs.next()) {
					pro.add(new Tables("PRO",rs.getString("id_pro"),rs.getString("nome_provincia"),"","","",""));
				}
				rs.close();
			}
			catch(Exception e) {}
			
			// ====================
			// Lista comuni
			try {
				if($country.equals("3")) {
					sql = "select * from comuni where id_pro = "+$province;
				}
				else {
					sql   = "select city_est.id_city as id_com, city as comune "
							+ "from city_est join nazioni_rel on city_est.naz = nazioni_rel.id_naz "
							+ "join nazioni on nazioni_rel.nome_nazione = nazioni.nome_nazione "
							+ "where nazioni.id_naz = "+$country+" order by city";
				}
				rs=st.executeQuery(sql);
				while(rs.next()) {
					cit.add(new Tables("COM",rs.getString("id_com"),rs.getString("comune"),"","","",""));
				}
				rs.close();
			}
			catch(Exception e) {}

			// ====================
			// Lista templates
			sql = "select * from optempl order by description";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				tmp.add(new Tables("TMP",rs.getString("id"),rs.getString("description"),"","","",""));
			}
			rs.close();
			
			// ====================
			// Lista TMZ
			sql = "select name, utc_offset from pg_timezone_names " + 
					"group by name,utc_offset " + 
					"order by utc_offset, name ";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				tmz.add(new Tables("TMZ",rs.getString("name"),rs.getString("name"),rs.getString("utc_offset"),"","",""));
			}
			rs.close();
			
			
			
			// Lista AccountCode
			try {
				sql = "select account_code from customers where id = "+$id_customer;
				//System.out.print("sql = " + sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					accountCode = rs.getString("account_code");
				}
				rs.close();
				accountCode = Utility.noNull(""+accountCode);
			}
			catch(Exception e) {}
			
			
			
			obj.put("flag_analytics", $flag_analytics);
			obj.put("description", $description);
			obj.put("store_code", $store_code);
			obj.put("id_customer", $id_customer);
			obj.put("country", $country);
			obj.put("region", $region);
			obj.put("province", $province);
			obj.put("city", $city);
			obj.put("address", $address);
			obj.put("postalcode", $postalcode);
			obj.put("sphone", $sphone);
			obj.put("smail", $smail);
			obj.put("id_optempl", $id_optempl);
			obj.put("sapid", $sapid);
			obj.put("customeridstore", $customeridstore);
			obj.put("brandname", $brandname);
			obj.put("brandlogo", $brandlogo);
			obj.put("notes", $notes);
			obj.put("contact", $contact);
			obj.put("cphone", $cphone);
			obj.put("cmobile", $cmobile);
			obj.put("cmail", $cmail);
			obj.put("storeid", $storeid);
			obj.put("flagclose", $flagclose);
			obj.put("adjustment_factor", $adjustment_factor);
			obj.put("AccountCode", accountCode);
			obj.put("timezone", $timezone);
			
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
		
		obj.put("dsfun", "Dettaglio Stores");
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		obj.put("naz", naz);
		obj.put("reg", reg);
		obj.put("pro", pro);
		obj.put("cit", cit);
		obj.put("tmp", tmp);
		obj.put("cus", cus);
		obj.put("dev", dev);
		
		obj.put("tmz", tmz);
		
		return new ModelAndView("/app/anag/stores-dett", obj);
	}
	
}

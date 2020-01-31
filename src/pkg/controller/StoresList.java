package pkg.controller;

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
import pkg.utility.Utility;

public class StoresList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Customers> cus = new ArrayList<Customers>();
		List<Stores> sto = new ArrayList<Stores>();
		
		// Pulisce la lista
		cus.clear();
		sto.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		
		
		
		String customerid = ""+req.getParameter("customerid");
		customerid = Utility.noNull(customerid);
		obj.put("customerid", customerid);
		
		String cond = "";
		if(customerid.length()>0)
			cond = "and c.id = "+customerid+" ";
		
		HttpSession session = req.getSession();
		
		String allStores = ""+req.getParameter("allStores");
		allStores = Utility.noNull(allStores);
		if(allStores.length()>0)
			session.setAttribute("allStores", allStores);
		if(allStores.equals("N"))
			session.setAttribute("allStores", "");
		
		
		String arrayStores = ""+ session.getAttribute("arrayStores");
		if(arrayStores.length()==0)
			arrayStores = "0";
		
		allStores = ""+session.getAttribute("allStores");
		allStores = Utility.noNull(allStores);
		
		obj.put("allStores", allStores);
		
		String tabRel = "rel_user_stores";
		
		if(allStores.length()==0)
			tabRel = "rel_user_stores";
		else
			tabRel = "rel_user_stores_all";
		
		
		try {
			// Apre la connessione
			conn = Utility.getConnAdmin();
			
			// sql da eseguire
			sql  =  "select a.*, c.brandname custbrand, c.account_code, "
					+ "(select storeid from devices d where d.id_store = a.id fetch first 1 rows only) storeid, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
					+ "from f_stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore from "+tabRel+" "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+""+cond;
			
			System.out.println(sql);
			
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
			String $google_uid="";
			String $google_places_types="";
			String $under_construction="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			
			while(rs.next()) {
				
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
				//$notes=rs.getString("notes");
				$notes=rs.getString("flagclose");
				$openhh=rs.getString("openhh");
				$closehh=rs.getString("closehh");
				$google_uid=""+rs.getString("google_uid");
				$google_uid=Utility.noNull($google_uid);
				$google_places_types=""+rs.getString("google_places_types");
				$google_places_types=Utility.noNull($google_places_types);
				
				$cmp1=rs.getString("custbrand");
				$cmp2=rs.getString("nrdevices");
				$cmp3=rs.getString("account_code");
				
				String storeid = ""+rs.getString("storeid");
				storeid = Utility.noNull(storeid);
				if(storeid.length()==0)
					storeid = ""+rs.getString("store_code");
				storeid = Utility.noNull(storeid);
				if(storeid.length()==0)
					storeid = ""+rs.getString("id");
				
				$under_construction = rs.getString("under_costruction");
				
				sto.add(new Stores($description,$address,$country,$city,
						$postalcode,$region,$contact,$cphone,$sphone,$cmail,$smail,
						$coordinates,$store_code,$brandname,$brandlogo,$id_customer,
						$id,$cmobile,$province,$id_optempl,$customeridstore,$sapid,
						$latitude,$longitude,$notes,$openhh,$closehh,
						$google_uid,$google_places_types,$under_construction,
						$cmp1,$cmp2,$cmp3,storeid));
				
			}
			rs.close();
			st.close();
			
			
			
			
			// ==================
			// Lista Customers
			sql  =  "select * , '"+(String)session.getAttribute("username")+"' as user from customers a where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "union all "
					+ "select * from customerscountry b where idusr = '"+(String)session.getAttribute("username")+"' ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $companyname;
			String $account_code;
			String $phone;
			String $mail;
			String $mobile;
			String $customer_country_id;
			String $agreement;
			String $wskey;
			String $wsdecrypt_key;
			String $wscrypt;
			
			
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
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Negozi");
		obj.put("sto", sto);
		obj.put("cus", cus);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/anag/stores-list", obj);
	}
	
}

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
import pkg.beans.Customers;
import pkg.beans.Devices;
import pkg.beans.Device_Detail;
import pkg.beans.Stores;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DeviceDetailRic extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Customers> cus = new ArrayList<Customers>();
		List<Stores> sto = new ArrayList<Stores>();
		List<Devices> dev = new ArrayList<Devices>();
		
		// Pulisce la lista
		cus.clear();
		sto.clear();
		dev.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String id_customer = ""+req.getParameter("id_customer");
		id_customer = Utility.noNull(id_customer);
		
		String id_store = ""+req.getParameter("id_store");
		id_store = Utility.noNull(id_store);
		
		String id_device = ""+req.getParameter("id_device");
		id_device = Utility.noNull(id_device);
		
		
		
		obj.put("id_customer", id_customer);
		obj.put("id_store", id_store);
		obj.put("id_device", id_device);
		
		try {
			
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			// sql da eseguire
			sql  =  "select * from customers where "
					+ "id in(select idcustomer::integer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			
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
			rs.close();
			st.close();
			
			// Se è stato selezionato un cliente prova a selezionarne gli Stores
			if(id_customer.length()>0) {
				
				// sql da eseguire
				sql  =  "select a.*, c.brandname custbrand, "
						+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
						+ "from stores a, customers c "
						+ "where a.id_customer = c.id and c.id = "+id_customer+" "
						+ "and a.id in(select idstore::integer from rel_user_stores "
						+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
				
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
				String $sto_contact="";
				String $cphone="";
				String $sphone="";
				String $cmail="";
				String $smail="";
				String $coordinates="";
				String $store_code="";
				String $sto_brandname="";
				String $sto_brandlogo="";
				String $id_customer="";
				String $sto_id="";
				String $cmobile="";
				String $province="";
				String $sto_id_optempl="";
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
					$sto_contact=rs.getString("contact");
					$cphone=rs.getString("cphone");
					$sphone=rs.getString("sphone");
					$cmail=rs.getString("cmail");
					$smail=rs.getString("smail");
					$coordinates=rs.getString("coordinates");
					$store_code=rs.getString("store_code");
					$sto_brandname=rs.getString("brandname");
					$sto_brandlogo=rs.getString("brandlogo");
					$id_customer=rs.getString("id_customer");
					$sto_id=rs.getString("id");
					$cmobile=rs.getString("cmobile");
					$province=rs.getString("province");
					$sto_id_optempl=rs.getString("id_optempl");
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
							$postalcode,$region,$sto_contact,$cphone,$sphone,$cmail,$smail,
							$coordinates,$store_code,$sto_brandname,$sto_brandlogo,$id_customer,
							$sto_id,$cmobile,$province,$sto_id_optempl,$customeridstore,$sapid,
							$latitude,$longitude,$notes,$openhh,$closehh,
							"","","",
							$cmp1,$cmp2,$cmp3,$cmp4));
					
					
					
				}
				rs.close();
				st.close();
				
				if(id_store.length()>0) {
					
					sql  =  "select a.*, b.description, c.brandname, d.description ds_devicetype "
							+ "from devices a, stores b, customers c, devicetype d "
							+ "where a.id_store = b.id and b.id_customer = c.id "
							+ "and a.device_type = d.id and b.id = "+id_store+" "
							+ "and a.id_store in(select idstore::integer from rel_user_stores "
							+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Esegue l'sql
					rs=st.executeQuery();
					
					// Campi database
					String $ip="";
					String $port="";
					String $macaddr="";
					String $lasttime="";
					String $enabled="";
					String $accountid="";
					String $storeid="";
					String $timezone="";
					String $lin="";
					String $lout="";
					String $device_type="";
					String $readevery="";
					String $dev_id="";
					String $id_store="";
					String $updating="";
					String $fromdate="";
					String $deviceid="";
					String $devicename="";
					String $siteid="";
					String $sitename="";
					String $doorid="";
					String $logenabled="";
					String $dev_notes="";
					String $update_time="";
					String $unit_time="";
					String $cause="";
					String $date_disabled="";
					String $adjustment_factor="";
					String $checktime="";
					String $lasteventcode="";
					String $diff_file_time="";
					String $customer_devicename="";
					
					$cmp1="";
					$cmp2="";
					$cmp3="";
					$cmp4="";
					
					while(rs.next()) {
						
						count+=1;
						
						$ip=rs.getString("ip");
						$port=rs.getString("port");
						$macaddr=rs.getString("macaddr");
						$lasttime=rs.getString("lasttime");
						$enabled=rs.getString("enabled");
						$accountid=rs.getString("accountid");
						$storeid=rs.getString("storeid");
						$timezone=rs.getString("timezone");
						$lin=rs.getString("lin");
						$lout=rs.getString("lout");
						$device_type=rs.getString("device_type");
						$readevery=rs.getString("readevery");
						$dev_id=rs.getString("id");
						$id_store=rs.getString("id_store");
						$updating=rs.getString("updating");
						$fromdate=rs.getString("fromdate");
						$deviceid=rs.getString("deviceid");
						$devicename=rs.getString("devicename");
						$siteid=rs.getString("siteid");
						$sitename=rs.getString("sitename");
						$doorid=rs.getString("doorid");
						$logenabled=rs.getString("logenabled");
						$dev_notes=rs.getString("notes");
						$update_time=rs.getString("update_time");
						$unit_time=rs.getString("unit_time");
						$cause=rs.getString("cause");
						$date_disabled=rs.getString("date_disabled");
						$adjustment_factor=rs.getString("adjustment_factor");
						$checktime=rs.getString("checktime");
						$lasteventcode=rs.getString("lasteventcode");
						$diff_file_time=rs.getString("diff_file_time");
						$customer_devicename=rs.getString("customer_devicename");
						
						$cmp1=rs.getString("brandname");
						$cmp2=rs.getString("description");
						$cmp3=rs.getString("ds_devicetype");
						
						dev.add(new Devices($ip,$port,$macaddr,$lasttime,$enabled,$accountid,$storeid,
								$timezone,$lin,$lout,$device_type,$readevery,$dev_id,$id_store,
								$updating,$fromdate,$deviceid,$devicename,$siteid,$sitename,
								$doorid,$logenabled,$dev_notes,$update_time,$unit_time,$cause,
								$date_disabled,$adjustment_factor,$checktime,$lasteventcode,
								$diff_file_time,$customer_devicename,
								$cmp1,$cmp2,$cmp3,$cmp4));
						
					}
					rs.close();
					st.close();
				}
				
			}
			
			
			if(st!=null)
				st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Device / Allarmi");
		obj.put("cus", cus);
		obj.put("sto", sto);
		obj.put("dev", dev);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/anag/device-detail-ric", obj);
	}
	
}

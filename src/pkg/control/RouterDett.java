package pkg.control;

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

import pkg.beans.Documents;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RouterDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Documents> doc = new ArrayList<Documents>();
		
		// Pulisce la lista
		doc.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		String $idS="";
		
		try {
			
			// Campi database
			String $id="";
			String $routertype="";
			String $customer="";
			String $customer_description="";
			String $account_id="";
			String $store_id="";
			String $store_description="";
			String $brepbip="";
			String $store_sap_code="";
			String $client_open_vpn="";
			String $ip_address_router="";
			String $sim="";
			String $routercode="";
			String $routerserialnumber = "";
			String $imsi_number = "";
			String $master_antenna = "";
			String $slave_antenna = "";
			String $note = "";
			Boolean closed = false;
			String dateclosed = "";
			String $close = "";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			String ip_add_router = "";
			
			$idS = ""+req.getParameter("id");
			$idS=Utility.noNull($idS);
			
			conn = Utility.getConnControl();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					
					System.out.println("DEV = "+req.getParameter("devtypeName"));
					
					if (Utility.noNull(req.getParameter("devtypeName")).equals("OpenVPN")) {
						ip_add_router = Utility.noApex(Utility.noNull(req.getParameter("ipaddressroutero")));
					}else if (Utility.noNull(req.getParameter("devtypeName")).equals("WIND")) {
						ip_add_router = Utility.noApex(Utility.noNull(req.getParameter("ipaddressrouterw")));
					}else {
						ip_add_router = Utility.noApex(Utility.noNull(req.getParameter("ipaddressroutere")));
					}
					
					if (Utility.noNull(req.getParameter("Closed")).equals("Y")) {
						closed = true;
						dateclosed = " NOW() ";
					}else {
						dateclosed = null;
					}


					sql = "INSERT INTO routers(" + 
							"            routers_type, customer, customer_description, account_id, " + 
							"            store_id, store_description, brepbins, store_sap_code, client_open_vpn, " + 
							"            ip_address_router, sim, router_code, router_serial_number, imsi_number, " + 
							"            master_antenna, slave_antenna, notes, closes, date_closes) " + 
							"    VALUES ('"+Utility.noApex(Utility.noNull(req.getParameter("devtype")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("customer")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("customer_description")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("account_id")))+"', " + 
							"            '"+Utility.noApex(Utility.noNull(req.getParameter("store_id")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("store_description")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("brepbins")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("store_sap_code")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("clientopenvpn")))+"', " + 
							"            '"+Utility.noApex(Utility.noNull(ip_add_router))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("sim")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("routercode")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("routerserialnumber")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("imsinumber")))+"', " + 
							"            '"+Utility.noApex(Utility.noNull(req.getParameter("master_antenna")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("slave_antenna")))+"', '"+Utility.noApex(Utility.noNull(req.getParameter("note")))+"', "+closed+", "+dateclosed+");";
					// Prepara il cursore
					System.out.println("sql = "+sql);
					st = conn.prepareStatement(sql);
					st.executeUpdate();
					

					//rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MOD...");
					
					//System.out.println("DEV = "+req.getParameter("devtypeName"));
					
					if (Utility.noNull(req.getParameter("devtypeName")).equals("OpenVPN")) {
						ip_add_router = Utility.noApex(Utility.noNull(req.getParameter("ipaddressroutero")));
					}else if (Utility.noNull(req.getParameter("devtypeName")).equals("WIND")) {
						ip_add_router = Utility.noApex(Utility.noNull(req.getParameter("ipaddressrouterw")));
					}else {
						ip_add_router = Utility.noApex(Utility.noNull(req.getParameter("ipaddressroutere")));
					}
					
					
					
					if (Utility.noNull(req.getParameter("Closed")).equals("Y")) {
						closed = true;
						dateclosed = ", date_closes=NOW() ";
					}else {
						dateclosed = " , date_closes = " + null;
					}

					sql = "UPDATE routers " + 
							"   SET routers_type='"+Utility.noApex(Utility.noNull(req.getParameter("devtype")))+"', customer='"+Utility.noApex(Utility.noNull(req.getParameter("customer")))+"', customer_description='"+Utility.noApex(Utility.noNull(req.getParameter("customer_description")))+"', account_id='"+Utility.noApex(Utility.noNull(req.getParameter("account_id")))+"', " + 
							"       store_id='"+Utility.noApex(Utility.noNull(req.getParameter("store_id")))+"', store_description='"+Utility.noApex(Utility.noNull(req.getParameter("store_description")))+"', brepbins='"+Utility.noApex(Utility.noNull(req.getParameter("brepbins")))+"', store_sap_code='"+Utility.noApex(Utility.noNull(req.getParameter("store_sap_code")))+"', " + 
							"       client_open_vpn='"+Utility.noApex(Utility.noNull(req.getParameter("clientopenvpn")))+"', ip_address_router='"+Utility.noApex(Utility.noNull(ip_add_router))+"', sim='"+Utility.noApex(Utility.noNull(req.getParameter("sim")))+"', router_code='"+Utility.noApex(Utility.noNull(req.getParameter("routercode")))+"', " + 
							"       router_serial_number='"+Utility.noApex(Utility.noNull(req.getParameter("routerserialnumber")))+"', imsi_number='"+Utility.noApex(Utility.noNull(req.getParameter("imsinumber")))+"', master_antenna='"+Utility.noApex(Utility.noNull(req.getParameter("master_antenna")))+"', slave_antenna='"+Utility.noApex(Utility.noNull(req.getParameter("slave_antenna")))+"', " + 
							"       notes='"+Utility.noApex(Utility.noNull(req.getParameter("note")))+"', closes="+closed+"  " + dateclosed + 
							" WHERE id = '"+req.getParameter("id")+"'";
					System.out.println("sql = "+sql);
					st = conn.prepareStatement(sql);

					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					sql   = "delete from routers where id = '"+req.getParameter("id")+"'";
					// Prepara il cursore
					
					st = conn.prepareStatement(sql);
					/*
					// Passa i parametri
					st.setString(1, $id);
					*/
					// Esegue l'sql
					System.out.println("sql = "+sql);
					st.executeUpdate();
					
					// *blank id
					$id = "";
					
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
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select *, routertype.description routerdescr from routers left join routertype on idrouter = routers_type where id::text = ? ";
				// Prepara il cursore
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				st.setString(1, $idS);
				// Esegue l'sql
				rs=st.executeQuery();
				
				if(rs.next()) {
					$id=rs.getString("id");
					$routertype=rs.getString("routers_type");
					$customer=rs.getString("customer");
					$customer_description=rs.getString("customer_description");
					$account_id=rs.getString("account_id");
					$store_id=rs.getString("store_id");
					$store_description=rs.getString("store_description");
					$brepbip=rs.getString("brepbins");
					$store_sap_code=rs.getString("store_sap_code");
					$client_open_vpn=rs.getString("client_open_vpn");
					$ip_address_router=rs.getString("ip_address_router");
					$sim=rs.getString("sim");
					$routercode=rs.getString("router_code");
					$routerserialnumber = rs.getString("router_serial_number");
					$imsi_number = rs.getString("imsi_number");
					$master_antenna = rs.getString("master_antenna");
					$slave_antenna = rs.getString("slave_antenna");
					$note = rs.getString("notes");
					$close = rs.getString("closes");
					
					
				}
				rs.close();
				


			}
			
			sql  =  "select * from routertype ";
			//System.out.println(sql);
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			String $devtype = "";
			// Esegue l'sql
			rs=st.executeQuery();
			while(rs.next()) {
				
				$devtype += "<option value='"+rs.getString("idrouter")+"'>"+rs.getString("description")+"</option>";
				
			}
			rs.close();


			obj.put("Devtype", $devtype);
			obj.put("Id", $id);
/**/
			obj.put("Routertype", $routertype);
			obj.put("Customer", $customer);
			obj.put("Customer_description", $customer_description);
			obj.put("Account_id", $account_id);
			obj.put("Store_id", $store_id);
			obj.put("Store_description", $store_description);
			obj.put("Brepbip", $brepbip);
			obj.put("Store_sap_code", $store_sap_code);
			obj.put("Client_open_vpn", $client_open_vpn);
			obj.put("Ip_address_router", $ip_address_router);
			obj.put("Sim", $sim);
			obj.put("Routercode", $routercode);
			obj.put("Routerserialnumber", $routerserialnumber);
			obj.put("Imsinumber", $imsi_number);
			obj.put("Master_antenna", $master_antenna);
			obj.put("Slave_antenna", $slave_antenna);
			obj.put("Note", $note);
			obj.put("Close", $close);
			obj.put("DevName", req.getParameter("devtypeName"));
/**/
			if(st!=null)
				st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Devices");
		obj.put("tipoForm", tipoForm);
		obj.put("doc", doc);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/app/anag/router-dett", obj);
	}
	
}

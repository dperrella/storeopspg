package pkg.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

import pkg.beans.Tables;
import pkg.beans.DeviceSlaves;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DevicesDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> tmz = new ArrayList<Tables>();
		List<Tables> typ = new ArrayList<Tables>();
		List<DeviceSlaves> sla = new ArrayList<DeviceSlaves>();
		tmz.clear();
		typ.clear();
		sla.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String rsn = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		String key = "";
		String $storeid="";
		String $accountid="";
		String $id_store = "";
		String accountCode = "";
		String storeCode = "";
		String id_customer = "";
		String id_account = "";
		
		try {
			
			//buttons
			String $btn1 = "";
			String $btn2 = "";
			
			// Campi database
			String $ip="";
			String $port="";
			String $readevery="";
			String $fromdate="";
			String $enabled="true";
			String $devusername="";
			String $devpassword="";
			String $devupdtime = "";
			boolean $devupdtime_ = false;

			String $timezone="";
			String $device_type="";
			String $minute_not_update="";
			String $updating="false";
			String $doorid="";
			String $notes="";
			/**/
			String $nome="";
			String $scan="";
			Boolean $scan_=null;
			String $macaddress="";
			String $linein="";
			String $lineout="";
			String $vetrina="";
			String $altropassaggio="";
			String $cause="";
			Boolean $scans = false;
			int $lineins=0;
			int $lineouts=0;
			int $vetrinas=0;
			int $altropassaggios=0;			
			/**/
			
			$id_store = ""+req.getParameter("id_store");
			$id_store = Utility.noNull($id_store);
			
			String $id_device = ""+req.getParameter("id");
			$id_device = Utility.noNull($id_device);
			
			$accountid = ""+req.getParameter("accountid");
			$accountid = Utility.noNull($accountid);
			
			$storeid = ""+req.getParameter("storeid");
			$storeid = Utility.noNull($storeid);
			
			$doorid = ""+req.getParameter("doorid");
			$doorid = Utility.noNull($doorid);
			
			// Apre la connessione
			conn = Utility.getConnControl();
			st=conn.createStatement();
			
			// PTF 
			String openModal = "N";
			String openLink = "N";
			String linNum = "";
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					// Inserimento
					System.out.println("INS...");
					
					Date date = null;
					$ip=req.getParameter("ip");
					$port=req.getParameter("port");
					$readevery=Utility.noNull(req.getParameter("readevery"));
					$fromdate=req.getParameter("fromdate");
					$enabled=req.getParameter("enabled");
					$accountid=req.getParameter("accountid");
					$storeid=req.getParameter("storeid");
					$timezone=req.getParameter("timezone");
					$device_type=req.getParameter("device_type");
					$minute_not_update=req.getParameter("minute_not_update");
					$updating=req.getParameter("updating");
					$doorid=req.getParameter("doorid");
					$notes=req.getParameter("notes");
					/**/
					$nome=Utility.noNull(req.getParameter("nome"));//
					$nome = $nome.replaceAll("[^a-zA-Z 0-9]+","");
					
					System.out.println("nome = " + $nome);
					
					$scan=Utility.noNull(req.getParameter("scan"));//
					$macaddress=Utility.noNull(req.getParameter("macaddress"));//
					$linein=Utility.noNull(req.getParameter("linein"));//
					$lineout=Utility.noNull(req.getParameter("lineout"));//
					$vetrina=Utility.noNull(req.getParameter("vetrina"));
					$altropassaggio=Utility.noNull(req.getParameter("altropassaggio"));
					$cause=Utility.noNull(req.getParameter("reason"));
					
					$devusername=Utility.noNull(req.getParameter("devusername"));
					$devpassword=Utility.noNull(req.getParameter("devpassword"));
					$devupdtime=Utility.noNull(req.getParameter("devupdtime"));
					/**/

					if ("".equals($devupdtime)) {
						$devupdtime_ = false;
					}else {
						if ($devupdtime.equals("t") || $devupdtime.equals("true")) {
							$devupdtime_ = true;
						}else {
							$devupdtime_ = false;
						}
					}
					
					
					if ("".equals($readevery)) {
						$readevery = null;
					}
					
					if ($scan.equals("Y")) {
						$scans = true;
					}else {
						$scans = false;
					}
					if ("".equals($linein)) {
						$lineins = 0;
					}else {
						$lineins = Integer.parseInt($linein);
					}
					if ("".equals($lineout)) {
						$lineouts = 0;
					}else {
						$lineouts = Integer.parseInt($lineout);
					}			
					
					if ("".equals($vetrina)) {
						$vetrinas = 0;
					}else {
						$vetrinas = Integer.parseInt($vetrina);
					}
					if ("".equals($altropassaggio)) {
						$altropassaggios = 0;
					}else {
						$altropassaggios = Integer.parseInt($altropassaggio);
					}							

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					if(!("".equals($fromdate)))
						date = sdf.parse($fromdate);
					
					
					
					
					boolean esiste = false;
					sql   = "select id from devices where accountid = "+$accountid+" and storeid = "+$storeid+" and doorid = "+$doorid;
					rs=st.executeQuery(sql);
					if(rs.next()) {
						esiste = true;
					}
					rs.close();
					
					if(esiste) {
						msg="<div class=\"alert alert-danger\"><strong>Account Code /Store ID / Device ID Already Exist</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "I";
					}
					else {
					
						$id_device = Utility.getNewProgr(conn, "devices", "id", "");
						Long dateToSet = null;
						if(date != null)
							dateToSet = Long.valueOf(date.getTime()/1000);
						sql   = "insert into devices ( "
								+ "id, id_store, ip, port, readevery, fromdate, enabled, accountid, storeid, timezone, "
								+ "device_type, minute_not_update, updating, doorid, notes, log, lin, lout, logenabled, devicename, macaddr, line_antenna1, line_antenna2, cause  "
								+ ", userid, pwd, update_time) "
								+ "values ( "
								+ $id_device+" , "+$id_store+" , '"+Utility.noApex($ip)+"' , '"+$port+"' , "
								+ $readevery+" , "+dateToSet+" , "+$enabled+" , '"+$accountid+"' , "
								+ "'"+$storeid+"' , '"+$timezone+"' , "+$device_type+" , "+$minute_not_update+" , "
								+ $updating+" , '"+$doorid+"' , '"+Utility.noApex($notes)+"' , "+$scans+", "+$lineins+" , "+$lineouts+", false,'"+Utility.noApex($nome)+"', '"+Utility.noApex($macaddress)+"',"+$vetrinas+","+$altropassaggios+", '"+Utility.noNull($cause)+"', "
								+ "'"+$devusername+"', '"+$devpassword+"', "+$devupdtime_+")"; 
						System.out.println(sql);
						st.executeUpdate(sql);
						
						msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "M";
						
						linNum = getLinNum(conn, $device_type);
						if(linNum.length()>0)
							openModal = "Y";
					}
					
				}
				else if(tipoForm.equals("M")) {
					// Modifica
					System.out.println("MOD...");
					
					
					//System.out.println("readevery... = *" + Utility.noNull(""+req.getParameter("readevery"))+"*");
					
					
					$ip=req.getParameter("ip");
					$port=req.getParameter("port");
					$readevery=Utility.noNull(req.getParameter("readevery"));
					$fromdate=req.getParameter("fromdate");
					$enabled=req.getParameter("enabled");
					$accountid=req.getParameter("accountid");
					$storeid=req.getParameter("storeid");
					$timezone=req.getParameter("timezone");
					$device_type=req.getParameter("device_type");
					$minute_not_update=req.getParameter("minute_not_update");
					$updating=req.getParameter("updating");
					$doorid=req.getParameter("doorid");
					$notes=req.getParameter("notes");
					/**/
					$nome=Utility.noNull(req.getParameter("nome"));//
					$scan=Utility.noNull(req.getParameter("scan"));//
				
					$macaddress=Utility.noNull(req.getParameter("macaddress"));//
					$linein=Utility.noNull(req.getParameter("linein"));//
					$lineout=Utility.noNull(req.getParameter("lineout"));//
					$vetrina=Utility.noNull(req.getParameter("vetrina"));
					$altropassaggio=Utility.noNull(req.getParameter("altropassaggio"));
					$cause=Utility.noNull(req.getParameter("reason"));
					
					$devusername=Utility.noNull(req.getParameter("devusername"));
					$devpassword=Utility.noNull(req.getParameter("devpassword"));
					$devupdtime=Utility.noNull(req.getParameter("devupdtime"));
					/**/
					
					if ("".equals($devupdtime)) {
						$devupdtime_ = false;
					}else {
						if ($devupdtime.equals("t") || $devupdtime.equals("true")) {
							$devupdtime_ = true;
						}else {
							$devupdtime_ = false;
						}
					}
					
					if ("".equals($readevery)) {
						$readevery = null;
					}
					
					if ($scan.equals("Y")) {
						$scans = true;
					}else {
						$scans = false;
					}
					if ("".equals($linein)) {
						$lineins = 0;
					}else {
						$lineins = Integer.parseInt($linein);
					}
					if ("".equals($lineout)) {
						$lineouts = 0;
					}else {
						$lineouts = Integer.parseInt($lineout);
					}			
					
					if ("".equals($vetrina)) {
						$vetrinas = 0;
					}else {
						$vetrinas = Integer.parseInt($vetrina);
					}
					if ("".equals($altropassaggio)) {
						$altropassaggios = 0;
					}else {
						$altropassaggios = Integer.parseInt($altropassaggio);
					}	
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					Date date = null;
					if(!("".equals($fromdate)))
						date = sdf.parse($fromdate);
					
					boolean esiste = false;
					sql   = "select id from devices where accountid = "+$accountid+" and storeid = "+$storeid+" and doorid = "+$doorid+" and id<>"+$id_device;
					rs=st.executeQuery(sql);
					if(rs.next()) {
						esiste = true;
					}
					rs.close();
					
					if(esiste) {
						msg="<div class=\"alert alert-danger\"><strong>Account Code /Store ID / Device ID Already Exist</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "M";
					}
					else {
						Long dateToSet = null;
						if(date != null)
							dateToSet = Long.valueOf(date.getTime()/1000);
						sql   = "update devices set "
								+ "ip = '"+Utility.noApex($ip)+"' , "
								+ "port = '"+$port+"' , "
								+ "readevery = "+$readevery+" , "
								+ "fromdate = "+dateToSet+" , "
								+ "enabled = "+$enabled+" , "
								+ "accountid = '"+$accountid+"' , "
								+ "storeid = '"+$storeid+"' , "
								+ "timezone = '"+$timezone+"' , "
								+ "device_type = "+$device_type+" , "
								+ "minute_not_update = "+$minute_not_update+" , "
								+ "updating = "+$updating+" , "
								+ "doorid = '"+$doorid+"' , "
								+ "notes = '"+Utility.noApex($notes)+"' , "
								+ "log= "+$scans+" , "
								+ "lin= "+$lineins+" , "
								+ "lout= "+$lineouts+" , "
								+ "devicename= '"+Utility.noApex($nome)+"' , "
								+ "macaddr= '"+Utility.noApex($macaddress)+"' , "
								+ "line_antenna1= "+$vetrinas+" , "
								+ "cause= '"+$cause+"' , "
								+ "line_antenna2= "+$altropassaggios+" , "
								+ "userid= '"+$devusername+"' , "
								+ "pwd= '"+$devpassword+"', "
								+ "update_time= "+$devupdtime_+" "
								+ "where id = "+$id_device+" ";
						System.out.println(sql);
						st.executeUpdate(sql);
								
						
						msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
							
					}
				}
				else if(tipoForm.equals("E")) {
					// Cancellazione
					System.out.println("ELI...");
									
					//sql   = "delete from eventlog_details "
					//		+ "where id_eventlog in(select id from eventslog where id_device = "+$id_device+") ";
					//st.executeUpdate(sql);
					
					//sql   = "delete from eventslog where id_device = "+$id_device+" ";
					//st.executeUpdate(sql);
					
					//sql   = "delete from devices where id = "+$id_device+" ";
					//st.executeUpdate(sql);
					
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
				
				
				sql   = "select * from devices where id = "+$id_device;
				System.out.println("sql = " + sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					
					$ip=Utility.noNull(rs.getString("ip"));
					$port=Utility.noNull(rs.getString("port"));
					$readevery=Utility.noNull(rs.getString("readevery"));
					$fromdate=Utility.noNull(rs.getString("fromdate"));
					$enabled=Utility.noNull(rs.getString("enabled"));
					$accountid=Utility.noNull(rs.getString("accountid"));
					$storeid=Utility.noNull(rs.getString("storeid"));
					$timezone=Utility.noNull(rs.getString("timezone"));
					$device_type=Utility.noNull(rs.getString("device_type"));
					$minute_not_update=Utility.noNull(rs.getString("minute_not_update"));
					$updating=Utility.noNull(rs.getString("updating"));
					$doorid=Utility.noNull(rs.getString("doorid"));
					$notes=Utility.noNull(rs.getString("notes"));
					$id_store=Utility.noNull(rs.getString("id_store"));
					
					$nome=Utility.noNull(rs.getString("devicename"));
					$scan=Utility.noNull(rs.getString("log"));//
					$macaddress=Utility.noNull(rs.getString("macaddr"));//
					$linein=Utility.noNull(rs.getString("lin")).replaceAll("0", "");//
					$lineout=Utility.noNull(rs.getString("lout")).replaceAll("0", "");//
					$vetrina=Utility.noNull(rs.getString("line_antenna1")).replaceAll("0", "");
					$altropassaggio=Utility.noNull(rs.getString("line_antenna2")).replaceAll("0", "");
					$cause=Utility.noNull(rs.getString("cause"));
					$devusername=Utility.noNull(rs.getString("userid"));
					$devpassword=Utility.noNull(rs.getString("pwd"));
					
					$devupdtime=Utility.noNull(rs.getString("update_time"));
					
					linNum = getLinNum(conn, $device_type);
					if(linNum.length()>0)
						openLink = "Y";
					
				}
				rs.close();
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date = null;
				if(!"".equals($fromdate)){
					date = new Date((Long.parseLong($fromdate)*1000));
					$fromdate = sdf.format(date);
				}
				
				
				// Device Slaves
				sql = "select * from deviceslaves left join error_type on eventcode = errorcode where id_device = "+$id_device;
				rs=st.executeQuery(sql);
				
				String enab = "";
				String eventcode = "";
				String provv = "";

				while(rs.next()) {
					enab = rs.getString("enabled");
					provv = ""+rs.getString("provissorio");
					provv = Utility.noNull(provv);
					eventcode = ""+rs.getString("eventcode");
					eventcode = Utility.noNull(eventcode);

					if(enab.equals("t") && provv.equals("t")) {
						enab = "<i class=\"text-warning icon-check\"></i>"  + "&nbsp;&nbsp;&nbsp;" + rs.getString("description") + " - ";
						$btn1 = "<i class=\"fas fa-edit\" style=\"cursor: pointer;\" onclick=\"updated('U','"+rs.getString("id_device")+"','"+rs.getString("id_slave")+"')\"></i>";
						$btn2 = "<i class=\"fas fa-times\" style=\"cursor: pointer;\" onclick=\"updated('D','"+rs.getString("id_device")+"','"+rs.getString("id_slave")+"')\"></i>";
					}else if(enab.equals("t") && (provv.equals("f") || "".equals(provv))){
						//if (!"".equals(eventcode)) {
						
						System.out.println(eventcode);
						
						
						if(eventcode.trim().length()==0||eventcode.trim().equalsIgnoreCase("OK")) {
							$btn1 = "";
							$btn2 = "";
							enab = "<i class=\"text-success icon-check\"></i>";
						}
						else {
							enab = "<i class=\"text-danger icon-ban\"></i>" + "&nbsp;&nbsp;&nbsp;" + rs.getString("description") + " - ";
							$btn1 = "";
							$btn2 = "<i class=\"fas fa-times\" style=\"cursor: pointer;\" onclick=\"updated('D','"+rs.getString("id_device")+"','"+rs.getString("id_slave")+"')\"></i>";
						}
						
						/*
						if (!"".equals(eventcode)) {
							
							enab = "<i class=\"text-danger icon-ban\"></i>" + "&nbsp;&nbsp;&nbsp;" + rs.getString("description") + " - ";
							$btn1 = "";
							$btn2 = "<i class=\"fas fa-times\" style=\"cursor: pointer;\" onclick=\"updated('D','"+rs.getString("id_device")+"','"+rs.getString("id_slave")+"')\"></i>";
						}else {
							$btn1 = "";
							$btn2 = "";
							enab = "<i class=\"text-success icon-check\"></i>";
						}
						*/
					}else {
						$btn1 = "";
						$btn2 = "";
						enab = "<i class=\"text-success icon-check\"></i>";
					}
					
					
					
					/*
					System.out.println(rs.getString("id_slave")+" "+
							rs.getString("enabled")+" "+
							rs.getString("id_device")+" "+
							rs.getString("slavename")+" "+
							rs.getString("parent_serial_no")+" "+
							rs.getString("device_type_id")+" "+
							rs.getString("address")+" "+
							rs.getString("door_description")+" "+
							rs.getString("serial_no")+" "+
							rs.getString("door_type_id")+" "+
							rs.getString("id_slave_char")+" "+
							rs.getString("ip"));
					*/
					
					sla.add(new DeviceSlaves(rs.getString("id_slave"),
							rs.getString("enabled"),
							rs.getString("id_device"),
							rs.getString("slavename"),
							rs.getString("parent_serial_no"),
							rs.getString("device_type_id"),
							rs.getString("address"),
							rs.getString("door_description"),
							rs.getString("serial_no"),
							rs.getString("door_type_id"),
							rs.getString("id_slave_char"),
							rs.getString("ip"),eventcode, enab,"",$btn1,$btn2));
				}
				rs.close();
				
			}
			
			sql = "select * from timezone ";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				tmz.add(new Tables("TMZ",rs.getString("code"),rs.getString("descrizione"),rs.getString("reltoutc"),"","",""));
			}
			rs.close();
			
			/*
			sql = "select name, utc_offset from pg_timezone_names " + 
					"group by name,utc_offset " + 
					"order by utc_offset, name ";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				tmz.add(new Tables("TMZ",rs.getString("name"),rs.getString("name"),rs.getString("utc_offset"),"","",""));
			}
			rs.close();
			*/
			
			
			sql = "select * from cause ";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				rsn += "<option value='"+rs.getString("id")+"'>"+rs.getString("description")+"</option>";
			}
			rs.close();
			
			sql = "select * from devicetype where enabled = true order by id ";
			rs = st.executeQuery(sql);
			String lines_number = "";
			while(rs.next()) {
				lines_number = ""+rs.getString("lines_number");
				lines_number = Utility.noNull(lines_number);
				typ.add(new Tables("TYP",rs.getString("id"),rs.getString("description").replaceAll("[^a-zA-Z0-9()-]", " "),lines_number,"","",""));
			}
			rs.close();
			
			
			// Lista AccountCode
			try {
				sql = "select account_code, store_code " + 
						"from customers " + 
						"left join f_stores on customers.id = id_customer " + 
						"where f_stores.id = "+req.getParameter("ids");
				System.out.print("sql = " + sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					accountCode = rs.getString("account_code");
					storeCode = rs.getString("store_code");
				}
				rs.close();
				accountCode = Utility.noNull(""+accountCode);
				storeCode = Utility.noNull(""+storeCode);
				
				sql = "select customers.id , customers.account_code " + 
						"from customers " + 
						"left join f_stores on customers.id = id_customer " + 
						"where f_stores.id = "+$id_store;
				System.out.print("sql = " + sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					id_account = rs.getString("account_code");
					id_customer = rs.getString("id");
				}
				rs.close();
				
				id_customer = Utility.noNull(""+id_customer);
				id_account = Utility.noNull(""+id_account);
				
				
			}
			catch(Exception e) {}
			
			
			obj.put("ip", $ip);
			obj.put("port", $port);
			obj.put("readevery", $readevery);
			obj.put("fromdate", $fromdate);
			if($enabled.equals("t"))
				$enabled = "true";
			else
				$enabled = "false";
			obj.put("enabled", $enabled);
			obj.put("accountid", $accountid);
			obj.put("storeid", $storeid);
			obj.put("timezone", $timezone);
			obj.put("device_type", $device_type);
			obj.put("minute_not_update", $minute_not_update);
			if($updating.equals("t"))
				$updating = "true";
			else
				$updating = "false";
			obj.put("updating", $updating);
			obj.put("doorid", $doorid);
			obj.put("notes", $notes);
			
			obj.put("id_device", $id_device);
			obj.put("id_store", $id_store);
			
			obj.put("nome", $nome);
			obj.put("scan", $scan);//
			obj.put("macaddress", $macaddress);//
			obj.put("linein", $linein);//
			obj.put("lineout", $lineout);//
			obj.put("vetrina", $vetrina);
			obj.put("altropassaggio", $altropassaggio);
			obj.put("cause", $cause);
			obj.put("devusername", $devusername);
			obj.put("devpassword", $devpassword);
			
			if ("".equals($devupdtime)) {
				$devupdtime_ = false;
			}else {
				if ($devupdtime.equals("f")) {
				      $devupdtime_ = false;
				}else {
					  $devupdtime_ = true;
				}
			}
			
			obj.put("devupdtime", $devupdtime_);
			
			
			obj.put("AccountCode", accountCode);
			obj.put("StoreCode", storeCode);
			obj.put("id_customer", id_customer);
			obj.put("id_account", id_account);
			
			obj.put("openModal", openModal);
			obj.put("openLink", openLink);
			obj.put("linNum", linNum);
			
			
			
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
		
		obj.put("dsfun", "Dettaglio Devices");
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		
		obj.put("rsn", rsn);
		obj.put("tmz", tmz);
		obj.put("typ", typ);
		obj.put("sla", sla);
		obj.put("Ids", $id_store);
		
		
		
		return new ModelAndView("/app/anag/devices-dett", obj);
	}
	
	private static String getLinNum(Connection conn, String devicetype) {
		
		String linNum = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql = "select lines_number from devicetype where id = "+devicetype;
			rs = st.executeQuery(sql);
			if(rs.next()) {
				linNum = ""+rs.getString("lines_number");
				linNum = Utility.noNull(linNum);
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		return linNum;
		
	}
	
}

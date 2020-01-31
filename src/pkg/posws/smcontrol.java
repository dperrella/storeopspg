package pkg.posws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import pkg.beans.pos.Stores_;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;


@WebServlet("/smcontrol")
public class smcontrol extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public smcontrol() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		String tab = request.getParameter("tab");
		String flag = request.getParameter("flag");
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnPos();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		if (tab.equals("Dev")){
			
			if (flag.equals("E")){

				try {
					
					sql = "delete from eventlog_details where id_device = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);
					sql = "delete from eventslog where id_device = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);
					sql = "delete from rel_device_ftp where id_device = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);
					sql = "delete from deviceslaves where id_device = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);		
					sql = "delete from devices where id = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);
				}catch(Exception e) {
					e.printStackTrace();
				}
	
				resp = propLang.get("msgSuccess");
				
			}else if (flag.equals("M")){
				
				System.out.println("sql1=");
				String ip = request.getParameter("ip");
				String port = request.getParameter("port");
				String macaddress = request.getParameter("macaddress");
				int accountid = Integer.parseInt(request.getParameter("accountid"));
				int storeid = Integer.parseInt(request.getParameter("storeid"));
				String timezone = request.getParameter("timezone");
				int devicetype = Integer.parseInt(request.getParameter("devicetype"));
				int readevery = Integer.parseInt(request.getParameter("readevery"));
				String notes = request.getParameter("notes");
				int cause = 0;
				String enabled = request.getParameter("enabled");
				String fromdate = request.getParameter("fromdate");
				int linein = 0;
				int lineout = 0;
				int doorid = 0;
				int antenna1 = 0;
				int antenna2 = 0;
				String logenabled = "f";
				String update_time = "f";
				String updating = "f";
				String fromdate_ = "";
				int doorid_ = 0;
				int linein_ = 0;
				int lineout_ = 0;
				int antenna1_ = 0;
				int antenna2_ = 0;
				int cause_ = 0;
				String lasttime_ = "";
				int lasttime = 0;
				System.out.println("sql2=");
				if (request.getParameter("devicetype").equals("1")){
					updating = request.getParameter("updating1");
				}else if (request.getParameter("devicetype").equals("2")){
					updating = request.getParameter("updating2");
				}else if (request.getParameter("devicetype").equals("3")){
					update_time = request.getParameter("updatetime");
					if (request.getParameter("doorid").equals("")){
						doorid_ = 0;
					}else{
						doorid_ = Integer.parseInt(request.getParameter("doorid"));
					}
					doorid = doorid_;
					System.out.println("sql2.1=");
				}else if (request.getParameter("devicetype").equals("5")){
					update_time = request.getParameter("updatetime2");
					if (request.getParameter("linein").equals("")){
						linein_ = 0;
					}else{
						linein_ = Integer.parseInt(request.getParameter("linein"));
					}
					linein = linein_;
					System.out.println("sql2.1.2=");
					if (request.getParameter("lineout").equals("")){
						lineout_ = 0;
					}else{
						lineout_ = Integer.parseInt(request.getParameter("lineout"));
					}
					lineout = lineout_;
					System.out.println("sql2.1.1=");
					if (request.getParameter("doorid2").equals("")){
						doorid_ = 0;
					}else{
						doorid_ = Integer.parseInt(request.getParameter("doorid2"));
					}
					doorid = doorid_;
					System.out.println("sql2.2=");
					if (request.getParameter("antenna1").equals("")){
						antenna1_ = 0;
					}else{
						antenna1_ = Integer.parseInt(request.getParameter("antenna1"));
					}
					antenna1 = antenna1_;
					System.out.println("sql2.2.1=");
					if (request.getParameter("antenna2").equals("")){
						antenna2_ = 0;
					}else{
						antenna2_ = Integer.parseInt(request.getParameter("antenna2"));
					}
					antenna2 = antenna2_;				
					System.out.println("sql2.3=");
				}else{
					update_time = request.getParameter("updatetime3");
					if (request.getParameter("doorid3").equals("")){
						doorid_ = 0;
					}else{
						doorid_ = Integer.parseInt(request.getParameter("doorid3"));
					}
					doorid = doorid_;
				}
				System.out.println("sql6=");
				
				if (enabled.equals("f") || enabled.equals("false") || enabled.equals(false)){
					System.out.println("sql6.1=");	
					String cau = request.getParameter("reason"); 
					System.out.println("cau="+cau);
					if (cau == "" || cau == "null" || cau == null){
						System.out.println("sql6.2=");
						cause_ = 0;
					}else{
						System.out.println("sql6.3=");
						cause_ = Integer.parseInt(request.getParameter("reason"));
					}
					System.out.println("sql6.4=");
					cause = cause_;
				}
				System.out.println("sql7=");
				try{
				sql = "select extract(epoch from '"+fromdate+"'::timestamp with time zone)";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					fromdate_ = rs.getString("date_part");
				}
				rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				System.out.println("sql3=");
				try{
					sql = "select extract(epoch from now()::timestamp with time zone)";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						lasttime_ = rs.getString("date_part");
					}
					lasttime_ = lasttime_.substring(0, 10);
					lasttime = Integer.parseInt(lasttime_);
					rs.close();
				}catch(Exception e){
						e.printStackTrace();
				}
				
				
				System.out.println("sql4=");
				try {
					sql = "UPDATE devices "+
						  "SET ip ='"+ip+"', "+
						  "port ='"+port+"', "+ 
						  "macaddr ='"+macaddress+"', "+
						  "lasttime = "+lasttime+" ,"+
						  "accountid ="+accountid+", "+ 
						  "storeid ="+storeid+", "+ 
						  "timezone ='"+timezone+"', "+
						  "lin = "+linein+" , "+ 
						  "lout = "+lineout+", "+ 
						  "doorid = " +doorid+ ","+
						  "line_antenna1 = " +antenna1+ ","+
						  "line_antenna2 = " +antenna2+ ","+
						  "device_type = "+devicetype+", "+ 
						  "readevery = "+readevery+", "+ 
						  "fromdate ="+fromdate_+", "+ 
						  "logenabled = '"+logenabled+"', "+
						  "update_time = '"+update_time+"', "+
						  "updating ='"+updating+"', "+
						  "notes = '"+notes+"', "+
						  "cause = "+cause+", "+
						  "date_disabled = now() ,"+
						  "enabled = '"+enabled+"' "+
						  "WHERE id = " + request.getParameter("id") ;
					st.executeUpdate(sql);
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println("sql5="+sql);
				resp = propLang.get("msgSuccess");
			}else{
				
				/******************************************************************/
				
				String idstore = request.getParameter("id");
				String ip = request.getParameter("ip");
				String port = request.getParameter("port");
				String macaddress = request.getParameter("macaddress");
				int accountid = Integer.parseInt(request.getParameter("accountid"));
				int storeid = Integer.parseInt(request.getParameter("storeid"));
				String timezone = request.getParameter("timezone");
				int devicetype = Integer.parseInt(request.getParameter("devicetype"));
				int readevery = Integer.parseInt(request.getParameter("readevery"));
				String notes = request.getParameter("notes");
				int cause = 0;
				String enabled = request.getParameter("enabled");
				String fromdate = request.getParameter("fromdate");
				int linein = 0;
				int lineout = 0;
				int doorid = 0;
				int antenna1 = 0;
				int antenna2 = 0;
				String logenabled = "f";
				String update_time = "f";
				String updating = "f";
				String fromdate_ = "";
				int doorid_ = 0;
				int linein_ = 0;
				int lineout_ = 0;
				int antenna1_ = 0;
				int antenna2_ = 0;
				int cause_ = 0;
				String lasttime_ = "";
				int lasttime = 0;
				
				
				
				if (request.getParameter("devicetype").equals("1")){
					updating = request.getParameter("updating1");
				}else if (request.getParameter("devicetype").equals("2")){
					updating = request.getParameter("updating2");
				}else if (request.getParameter("devicetype").equals("3")){
					update_time = request.getParameter("updatetime");
					if (request.getParameter("doorid").equals("")){
						doorid_ = 0;
					}else{
						doorid_ = Integer.parseInt(request.getParameter("doorid"));
					}
					doorid = doorid_;
				}else if (request.getParameter("devicetype").equals("5")){
					update_time = request.getParameter("updatetime2");
					if (request.getParameter("linein").equals("")){
						linein_ = 0;
					}else{
						linein_ = Integer.parseInt(request.getParameter("linein"));
					}
					linein = linein_;
					if (request.getParameter("lineout").equals("")){
						lineout_ = 0;
					}else{
						lineout_ = Integer.parseInt(request.getParameter("lineout"));
					}
					lineout = lineout_;
					if (request.getParameter("doorid2").equals("")){
						doorid_ = 0;
					}else{
						doorid_ = Integer.parseInt(request.getParameter("doorid2"));
					}
					doorid = doorid_;
					if (request.getParameter("antenna1").equals("")){
						antenna1_ = 0;
					}else{
						antenna1_ = Integer.parseInt(request.getParameter("antenna1"));
					}
					antenna1 = antenna1_;
					if (request.getParameter("antenna2").equals("")){
						antenna2_ = 0;
					}else{
						antenna2_ = Integer.parseInt(request.getParameter("antenna2"));
					}
					antenna2 = antenna2_;					
				}else{
					update_time = request.getParameter("updatetime3");
					if (request.getParameter("doorid3").equals("")){
						doorid_ = 0;
					}else{
						doorid_ = Integer.parseInt(request.getParameter("doorid3"));
					}
					doorid = doorid_;
				}
				
				if (enabled.equals("f") || enabled.equals("false") || enabled.equals(false)){
					if (request.getParameter("cause").equals("")){
						cause_ = 0;
					}else{
						cause_ = Integer.parseInt(request.getParameter("cause"));
					}
					cause = cause_;
				}
				
				try{
				sql = "select extract(epoch from '"+fromdate+"'::timestamp with time zone)";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					fromdate_ = rs.getString("date_part");
				}
				rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			
				try{
					sql = "select extract(epoch from now()::timestamp with time zone)";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						lasttime_ = rs.getString("date_part");
					}
					lasttime_ = lasttime_.substring(0, 10);
					lasttime = Integer.parseInt(lasttime_);
					rs.close();
				}catch(Exception e){
						e.printStackTrace();
				}
				
				try {
					
					sql = "insert into devices(ip, port, macaddr, lasttime, enabled, accountid, storeid, timezone,lin, lout, device_type, readevery, id_store, "
							+ "updating, fromdate, doorid, logenabled, notes, "
							+ "update_time, cause,"
							+ "line_antenna1, line_antenna2) values ('"+ip+"','"+port+"','"+macaddress+"',"+lasttime+",'"+enabled+"',"+accountid+","+storeid+","
									+ "'"+timezone+"',"+linein+","+lineout+","+devicetype+","+readevery+","+idstore+",'"+updating+"',"+fromdate_+"," +doorid+ ",'"+logenabled+"',"
											+ "'"+notes+"','"+update_time+"',"+cause+"," +antenna1+ "," +antenna2+ ")";
						
					System.out.println("SQL INSert = "+sql);
					st.executeUpdate(sql);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				resp = propLang.get("msgSuccess");
				
				/******************************************************************/
			}

		}else if(tab.equals("Sto")){
			
			
			
			if (flag.equals("E")){
				

				try {
					String iddev_ = "";
					sql = "select * from devices where id_store = '"+request.getParameter("id")+"'";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						if(iddev_.equals("")){
							iddev_ = rs.getString("id");
						}else{
							iddev_ = iddev_ + "," + rs.getString("id");
						}
					}
					rs.close();
					
					
					if (iddev_ == ""){
						iddev_ = "VUOTO";
					}
					
					if (!"VUOTO".equals(iddev_)){
						sql = "delete from eventlog_details where id_device in ("+iddev_+")";
						st.executeUpdate(sql);
						sql = "delete from eventslog where id_device in ("+iddev_+")";
						st.executeUpdate(sql);
						sql = "delete from rel_device_ftp where id_device in ("+iddev_+")";
						st.executeUpdate(sql);
						sql = "delete from deviceslaves where id_device in ("+iddev_+")";
						st.executeUpdate(sql);				
						sql = "delete from devices where id in ("+iddev_+")";
						st.executeUpdate(sql);				
					}
					
					sql = "delete from stores where id = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);
					System.out.println("delete = " + sql);
				}catch(Exception e) {
					e.printStackTrace();
				}
	
				resp = propLang.get("msgSuccess");
				
			}else if (flag.equals("M")){
			
				//System.out.println("IN STORE - Modifica");
				
				int pc = 0;
				int iop = 0;
				int country = 0;
				int region = 0;
				int province = 0;
				int city = 0;
				int customer = 0;
				
				String storeid_ = request.getParameter("storeid");
				
				if (request.getParameter("postalcode").equals("")){
					pc = 0;	
				}else{
					pc = Integer.parseInt(request.getParameter("postalcode"));
				}
				
				if (request.getParameter("closingtime").equals("")){
					iop = 0;	
				}else{
					iop = Integer.parseInt(request.getParameter("closingtime"));
				}
				
				if (request.getParameter("country").equals("")){
					country = 0;	
				}else{
					country = Integer.parseInt(request.getParameter("country"));
				}
				
				if (request.getParameter("region").equals("")){
					region = 0;	
				}else{
					region = Integer.parseInt(request.getParameter("region"));
				}
				
				if (request.getParameter("province").equals("")){
					province = 0;	
				}else{
					province = Integer.parseInt(request.getParameter("province"));
				}
				
				if (request.getParameter("city").equals("")){
					city = 0;	
				}else{
					city = Integer.parseInt(request.getParameter("city"));
				}	
				
				if (request.getParameter("customer").equals("")){
					customer = 0;	
				}else{
					customer = Integer.parseInt(request.getParameter("customer"));
				}
				
			try{
				sql = "UPDATE stores "+					
					  "SET description='"+request.getParameter("description")+"', address='"+request.getParameter("address")+"', country="+country+", city="+city+", postalcode="+pc+", region="+region+", "+	
					  "contact='"+request.getParameter("contactperson")+"', cphone='"+request.getParameter("contactphone")+"', sphone='"+request.getParameter("phone")+"', cmail='"+request.getParameter("contactmailaddress")+"', smail='"+request.getParameter("mailaddress")+"', "+
       				  "brandname='"+request.getParameter("brandname")+"', brandlogo='"+request.getParameter("brandlogo")+"', id_customer="+customer+", "+
       				  "cmobile='"+request.getParameter("contactmobile")+"', province="+province+", id_optempl="+iop+", sapid='"+request.getParameter("accountnumber")+"', "+ 
       				  "notes='"+request.getParameter("notes")+"', customeridstore = '"+request.getParameter("customeridstore")+"', store_code =  '"+storeid_+"' "+
       				  "WHERE id = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);
				System.out.println("update = " + sql);
			}catch(Exception e) {
				e.printStackTrace();
			}
				resp = propLang.get("msgSuccess");
			
			}else{
				// insert
				
				int pc = 0;
				int iop = 0;
				int country = 0;
				int region = 0;
				int province = 0;
				int city = 0;
				int customer = 0;
				
				String storeid_ = request.getParameter("storeid");
				
				if (request.getParameter("postalcode").equals("")){
					pc = 0;	
				}else{
					pc = Integer.parseInt(request.getParameter("postalcode"));
				}
				
				if (request.getParameter("closingtime").equals("")){
					iop = 0;	
				}else{
					iop = Integer.parseInt(request.getParameter("closingtime"));
				}
				
				if (request.getParameter("country").equals("")){
					country = 0;	
				}else{
					country = Integer.parseInt(request.getParameter("country"));
				}
				
				if (request.getParameter("region").equals("")){
					region = 0;	
				}else{
					region = Integer.parseInt(request.getParameter("region"));
				}
				
				if (request.getParameter("province").equals("")){
					province = 0;	
				}else{
					province = Integer.parseInt(request.getParameter("province"));
				}
				
				if (request.getParameter("city").equals("")){
					city = 0;	
				}else{
					city = Integer.parseInt(request.getParameter("city"));
				}	
				
				if (request.getParameter("customer").equals("")){
					customer = 0;	
				}else{
					customer = Integer.parseInt(request.getParameter("customer"));
				}
				
			try{

				sql = "INSERT INTO stores(description, address, country, city, postalcode, region, contact, "+ 
					  "cphone, sphone, cmail, smail, store_code, coordinates, brandname, "+ 
					  "brandlogo, id_customer, cmobile, province, id_optempl, customeridstore, "+ 
					  "sapid, latitude, longitude, notes, openhh, closehh) "+
					  "VALUES "+
					  "('"+request.getParameter("description")+"', '"+request.getParameter("address")+"', "+country+", "+city+", "+pc+", "+region+", "+
					  "'"+request.getParameter("contactperson")+"','"+request.getParameter("contactphone")+"', '"+request.getParameter("phone")+"', '"+request.getParameter("contactmailaddress")+"', '"+request.getParameter("mailaddress")+"', '"+storeid_+"', null, "+
					  "'"+request.getParameter("brandname")+"', '"+request.getParameter("brandlogo")+"', "+customer+", "+
					  "'"+request.getParameter("contactmobile")+"', "+province+", "+iop+",'"+request.getParameter("customeridstore")+"', '"+request.getParameter("accountnumber")+"', null,null,"+
					  "'"+request.getParameter("notes")+"',null,null)";
					System.out.println("sql insert = " + sql);
					st.executeUpdate(sql);
			
					String maxid = "";
					sql = "select max(id) as maxid from stores";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						maxid = rs.getString("maxid");
					}
					
					String appsql = "";
					sql = "select * from loginc where id_customer = " + customer + " and role = 'ADMIN'";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						appsql = appsql + "insert into rel_loginc_stores (id_customer,id_store,id_user,visible) values("+customer+","+maxid+","+rs.getString("id")+",true);";
					}
					sql = appsql;
					st.executeUpdate(sql);
					rs.close();
					
			}catch(Exception e) {
				e.printStackTrace();
			}
				resp = propLang.get("msgSuccess");
			
			}
		}else if(tab.equals("DEVO")){
			
			try {
				sql = "update devices set enabled = '"+request.getParameter("choiseenabled")+"', cause = '"+request.getParameter("choisereason")+"', updating = '"+request.getParameter("choiseupdating")+"', notes = '"+request.getParameter("choisenotes")+"' where id = '"+request.getParameter("id")+"'";
				st.executeUpdate(sql);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else if(tab.equals("Cus")){
			
			if (flag.equals("E")){
				
				try {
					

					String idsto_ = "";
					sql = "select * from stores where id_customer = '"+request.getParameter("id")+"'";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						if(idsto_.equals("")){
							idsto_ = rs.getString("id");
						}else{
							idsto_ = idsto_ + "," + rs.getString("id");
						}
					}
					
					if (idsto_.equals("")){
						idsto_ = "0";
					}
					rs.close();
					
					String iddev_ = "";
					sql = "select * from devices where id_store in ("+idsto_+")";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						if(iddev_.equals("")){
							iddev_ = rs.getString("id");
						}else{
							iddev_ = iddev_ + "," + rs.getString("id");
						}
					}
					rs.close();
					
					if (iddev_ == ""){
						iddev_ = "VUOTO";
					}
					
					if (!"VUOTO".equals(iddev_)){
						sql = "delete from eventlog_details where id_device in ("+iddev_+")";
						st.executeUpdate(sql);
						sql = "delete from eventslog where id_device in ("+iddev_+")";
						st.executeUpdate(sql);
						sql = "delete from rel_device_ftp where id_device in ("+iddev_+")";
						st.executeUpdate(sql);
						sql = "delete from deviceslaves where id_device in ("+iddev_+")";
						st.executeUpdate(sql);				
						sql = "delete from devices where id in ("+iddev_+")";
						st.executeUpdate(sql);				
						sql = "delete from stores where id in ("+idsto_+")";
						st.executeUpdate(sql);
					}
					sql = "delete from customers where id = '"+request.getParameter("id")+"'";
					st.executeUpdate(sql);
				}catch(Exception e) {
					e.printStackTrace();
				}
	
				resp = propLang.get("msgSuccess");
				
			}else if (flag.equals("M")){
				
				try {
					
					int idopt = 0, country_ = 0;
					if (request.getParameter("idoptempl").equals("")){
						idopt = 0; 
					}
					
					
					String app_country = request.getParameter("country");
					//System.out.println("country = *" + app_country +"*");
					if (app_country == "" || app_country.equals("")){
						country_ = 0; 
					}else{
						country_ = Integer.parseInt(app_country);
					}				
					
					sql = "UPDATE customers "+
					  "SET companyname='"+request.getParameter("companyname")+"', description='"+request.getParameter("description")+"', contact='"+request.getParameter("contact")+"', brandname='"+request.getParameter("brandname")+"', brandlogo='"+request.getParameter("brandlogo")+"', "+ 
					  "account_code='"+request.getParameter("accountid")+"', phone='"+request.getParameter("phone")+"', mail='"+request.getParameter("mail")+"', mobile='"+request.getParameter("mobile")+"', id_optempl="+idopt+", "+ 
					  "customer_country_id="+request.getParameter("country")+", agreement = '"+request.getParameter("agreement")+"', date_agree_start = '"+request.getParameter("dateagreestart")+"', date_agree_end = '"+request.getParameter("dateagreeend")+"' WHERE id = '"+request.getParameter("id")+"'";
					System.out.println("sql_update = " + sql);
					st.executeUpdate(sql);
					
					
					
					String expyn = request.getParameter("exptoanalitics");
					//System.out.println("expyn = " + expyn);
					if (expyn.equals("true") || expyn.equals("t") || expyn == "true" || expyn == "t"){
					
								
						sql = "delete from customers_analytics where id_customers = '"+request.getParameter("id")+"'";
						st.executeUpdate(sql);
						
						sql = "INSERT INTO customers_analytics( "+
								  "accountid, id_customers)"+
								  "VALUES ('"+request.getParameter("accountid")+"', '"+request.getParameter("id")+"')";
						st.executeUpdate(sql);
						
					}else{

						sql = "delete from customers_analytics where id_customers = '"+request.getParameter("id")+"'";
						st.executeUpdate(sql);
						
					}
					
					
					
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
	
				
				resp = propLang.get("msgSuccess");
			}else{
				
				int idopt = 0, country_ = 0;
				if (request.getParameter("idoptempl").equals("")){
					idopt = 0; 
				}
				/*
				if (request.getParameter("country").equals("")){
					country_ = 0; 
				}	
				*/
				String app_country = request.getParameter("country");
				System.out.println("country = *" + app_country +"*");
				if (app_country == "" || app_country.equals("")){
					country_ = 0; 
				}else{
					country_ = Integer.parseInt(app_country);
				}
				
				try{
				sql = "INSERT INTO customers( "+
					  "companyname, description, contact, brandname, brandlogo, account_code, "+ 
					  "phone, mail, mobile, id_optempl, customer_country_id, agreement, wskey, wsdecrypt_key, wscrypt, date_agree_start,date_agree_end) "+
					  "VALUES ('"+request.getParameter("companyname")+"', '"+request.getParameter("description")+"', '"+request.getParameter("contact")+"', '"+request.getParameter("brandname")+"', '"+request.getParameter("brandlogo")+"', '"+request.getParameter("accountid")+"', "+ 
					  "'"+request.getParameter("phone")+"', '"+request.getParameter("mail")+"', '"+request.getParameter("mobile")+"', "+idopt+", "+country_+", '"+request.getParameter("agreement")+"',null,null,null, '"+request.getParameter("dateagreestart")+"', '"+request.getParameter("dateagreeend")+"')";
				//insert
				st.executeUpdate(sql);
				System.out.println("sql_insert = " + sql);

				String expyn = request.getParameter("exptoanalitics");
				if (expyn.equals("true") || expyn.equals("t") || expyn == "true" || expyn == "t"){
				
		
					sql = "select * from customers where account_code = '"+request.getParameter("accountid")+"'";
					rs = st.executeQuery(sql);
					
					//request.getParameter("accountid")
					String id_customers = "";
					while(rs.next()) {
						id_customers = rs.getString("id");
					}
					
					rs.close();
					sql = "INSERT INTO customers_analytics( "+
							  "accountid, id_customers)"+
							  "VALUES ('"+request.getParameter("accountid")+"', '"+id_customers+"')";
					st.executeUpdate(sql);
					
				}
				
				
				System.out.println("sql_insert = " + sql);
				//st.executeUpdate(sql);
			}catch(Exception e) {
				e.printStackTrace();
			}
				resp = propLang.get("msgSuccess");
			}
		}else{
			
		}
		

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
	
	}


	
}

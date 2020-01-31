<%@ page 
language="java"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"

import="java.security.InvalidKeyException"
import="java.security.NoSuchAlgorithmException"
import="java.util.Base64"
import="java.util.Random"

import="javax.crypto.Cipher"
import="javax.crypto.KeyGenerator"
import="javax.crypto.NoSuchPaddingException"
import="javax.crypto.SecretKey"
import="javax.crypto.spec.SecretKeySpec"
import="javax.crypto.KeyGenerator"
import="java.security.MessageDigest"

import="com.sun.mail.util.BASE64DecoderStream"
import="com.sun.mail.util.BASE64EncoderStream"

contentType="application/json; charset=UTF-8"
pageEncoding="ISO-8859-1"
%>
<%
String json="";
String list="";

String key = ""+request.getParameter("key");
key=Utility.noNull(key);

DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


// Data di sistema
DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
Date date = new Date();
long tstamp = date.getTime()/1000;
String dtSys = dateFormat.format(date);

// Connessione e sql
Connection conn = null;
Statement st = null;
Statement up = null;
ResultSet rs = null;

String sql = "";
String cond = "";
try {
	
	//conn = Utility.getConn();
	Class.forName("org.postgresql.Driver");
	conn = DriverManager.getConnection("jdbc:postgresql://52.233.137.119:5432/smartdata", "postgres", "fiJhjOC3o7VC");
	st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		  ResultSet.CONCUR_UPDATABLE);
	up = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			  ResultSet.CONCUR_UPDATABLE);
		
	// ===============================
	// Controlla se esiste una "key" corrispondente al campo "account_id"
	String wskey = "";
	String id_customer = "";
	String account_id = "";
	String wsdecrypt_key = "";
	String wscrypt = "";
	
	
	
	sql   = "select wskey, wsdecrypt_key, wscrypt, account_code, id from customers "+
			"where wskey = '"+key+"' ";
	
	rs=st.executeQuery(sql);
	if(rs.next()) {
		wskey = rs.getString("wskey");
		wsdecrypt_key = rs.getString("wsdecrypt_key");
		wscrypt = rs.getString("wscrypt");
		account_id = rs.getString("account_code");
		id_customer = rs.getString("id");
	}
	rs.close();
	
	if(key.length()==0 || !key.equals(wskey)) {
		
		json+="{\n"+
			  "    \"response\" : {\n"+
			  "        \"application_response_code\" : \"200\",\n"+
			  "        \"created_http\" : \""+dtSys+"\",\n"+
			  "        \"status_text\" : \"KO\",\n"+
			  "        \"error_message\" : \"Key parameter is not valid\",\n"+
			  "        \"pag\" : 1,\n"+
			  "        \"total_pages\" : 1,\n"+
			  "        \"total_results\" : 0\n"+
			  "    }\n"+
			  "}";	
		
	}
	else {
		
		sql   = "select store_code, description, postalcode, flagclose "+
				"from stores s "+
				"where id_customer = "+id_customer+" ";
		
		rs=st.executeQuery(sql);
		
		// Campi database
		int $riga = 0;
		String $store_id="";
		String $description="";
		String $postal_code="";
		String $flagclose="";
		
		json+="{\n"+
			  "    \"response\" : {\n"+
			  "        \"application_response_code\" : \"200\",\n"+
			  "        \"created_http\" : \""+dtSys+"\",\n"+
			  "        \"listings\" : [\n";	
				  
		int $count=0;
		while(rs.next()) {
			
			$count++;
			$riga = rs.getRow();
			$store_id = rs.getString("store_code");
			$description = rs.getString("description");
			$postal_code = rs.getString("postalcode");
			if(rs.getBoolean("flagclose")==false) 
				$flagclose = "false";
			else {
				$flagclose = "true";
			}
			
			
			list+="            {\n"+
				  "                \"row\" : "+$riga+",\n"+
				  "                \"store_id\" : \""+$store_id+"\",\n "+
				  "                \"description\" : \""+$description+"\",\n "+
				  "                \"postal_code\" : \""+$postal_code+"\",\n "+
				  "                \"store_close\" : "+$flagclose+"\n "+
				  "            },\n";
		}
		rs.close();
		
		if(list.length()>0)
			list=list.substring(9,list.length()-2);
		
		json+=list+"\n";
		
		json+="        ],\n"+
			  "    \"status_text\" : \"OK\",\n"+
			  "    \"pag\" : 1,\n"+
			  "    \"total_pages\" : 1,\n"+
			  "    \"total_results\" : "+$count+"\n"+
			  "    }\n"+
			  "}";
			  
			  
		
		
	}
	
}
catch(Exception e) {
	out.println(e);
	
	out.println(sql);
	
	//e.printStackTrace();
}

if(up!=null)
	up.close();
if(st!=null)
	st.close();

if(conn!=null)
	conn.close();

%><%=json %>
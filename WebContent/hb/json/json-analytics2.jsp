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
%><%!
private static Cipher ecipher;
private static Cipher dcipher;

private static SecretKey crkey;

public static String encrypt(String str) {

	try {
		// encode the string into a sequence of bytes using the named charset
		// storing the result into a new byte array. 
		byte[] utf8 = str.getBytes("UTF8");
		byte[] enc = ecipher.doFinal(utf8);
		// encode to base64
		enc = BASE64EncoderStream.encode(enc);
		return new String(enc);

	}

	catch (Exception e) {
		e.printStackTrace();
	}

	return null;

}

public static String getSessionKey() {
	String sessionKey = "";
	
	try {
		// ===============
		//wskkey
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(output.getBytes());
        
        byte byteData[] = md.digest();
		
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
    	sessionKey = hexString.toString();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
	return sessionKey;
}
%><%
String json="";
String list="";

String sessionKey = ""+request.getParameter("id_transaction");
sessionKey=Utility.noNull(sessionKey);
// controlla se esiste una chiave per la sessione
if(sessionKey.trim().length()==0) {
	// genera una chiave di sessione
	sessionKey = getSessionKey();
	// la inserisce in sessione
	session.setAttribute("sessionKey", sessionKey);
}

String key = ""+request.getParameter("key");
key=Utility.noNull(key);

DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

String fromdate = ""+request.getParameter("fromdate"); //date,
fromdate=Utility.noNull(fromdate);
if(fromdate.length()==0) {
	fromdate = "null";
}
else {
	Date date = new Date(Long.parseLong(fromdate)*1000);
	fromdate = "'"+df.format(date)+"'";
}
String todate = ""+request.getParameter("todate"); //date,
todate=Utility.noNull(todate);
if(todate.length()==0) {
	todate = "null";
}
else {
	Date date = new Date(Long.parseLong(todate)*1000);
	todate = "'"+df.format(date)+"'";
}
String kpi = ""+request.getParameter("kpi"); //String --- REQUIRED???,
kpi=Utility.noNull(kpi);
if(kpi.length()==0)
	kpi = "null";
else
	kpi = "'"+kpi.replaceAll(",","*")+"'";
String store_id = ""+request.getParameter("store_id"); //integer,
String account_id = ""+request.getParameter("account_id"); //integer,
String year = ""+request.getParameter("year"); //integer,
String month = ""+request.getParameter("month"); //integer,
String day = ""+request.getParameter("day"); //integer,
String customer_idstore = ""+request.getParameter("customer_idstore"); //String,
customer_idstore=Utility.noNull(customer_idstore);
if(customer_idstore.length()==0)
	customer_idstore = "null";
else
	customer_idstore = "'"+customer_idstore+"'";
String timegrouped = ""+request.getParameter("timegrouped"); //integer

// Paginazione
String pag = ""+request.getParameter("pag");
pag = Utility.noNull(pag);

if(pag.length()==0) {
	pag = "1";
}

String orario = ""+request.getParameter("orario"); 
orario=Utility.noNull(orario);
if(orario.length()==0) {
	orario = "false";
}

String adjustmentFactor = ""+request.getParameter("adjustmentFactor");
adjustmentFactor=Utility.noNull(adjustmentFactor);
if(adjustmentFactor.length()==0) {
	adjustmentFactor = "false";
}

String nrRecord = ""+request.getParameter("nrRecord");
nrRecord=Utility.noNull(nrRecord);

int rowIni = 0;
int rowFin = 0;
int nrPag = 0;

int numRow = 0;
String prev = "";
String next = "";
if(nrRecord.length()==0)
	nrRecord = "20";
else
	if(nrRecord.equals("0"))
		nrRecord = "";

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
	
	//conn = Utility.getConnData();
	Class.forName("org.postgresql.Driver");
	conn = DriverManager.getConnection("jdbc:postgresql://52.233.137.119:5432/smartdata2", "postgres", "fiJhjOC3o7VC");
	
	st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		  ResultSet.CONCUR_UPDATABLE);
	up = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			  ResultSet.CONCUR_UPDATABLE);
		
	// ===============================
	// Controlla se esiste una "key" corrispondente al campo "account_id"
	String wskey = "";
	String id_customer = "";
	String wsdecrypt_key = "";
	String wscrypt = "";
	
	
	if(!fromdate.equals("null")) 
		cond+=" and parm_fromdate = "+fromdate+" ";
	if(!todate.equals("null")) 
		cond+=" and parm_todate = "+todate+" ";
	if(!kpi.equals("null")) 
		cond+=" and parm_kpi = "+kpi+" ";
	if(!store_id.equals("null")) 
		cond+=" and parm_store_id = "+store_id+" ";
	if(!account_id.equals("null")) 
		cond+=" and parm_account_id = "+account_id+" ";
	if(!year.equals("null")) 
		cond+=" and parm_year = "+year+" ";
	if(!month.equals("null")) 
		cond+=" and parm_month = "+month+" ";
	if(!day.equals("null")) 
		cond+=" and parm_day = "+day+" ";
	if(!customer_idstore.equals("null")) 
		cond+=" and parm_customer_idstore = "+customer_idstore+" ";
	if(!timegrouped.equals("null")) 
		cond+=" and parm_timegrouped = "+timegrouped+" ";
	if(orario.equals("true")) 
		cond+=" and parm_orario = "+orario+" ";
	if(adjustmentFactor.equals("true")) 
		cond+=" and parm_adjustmentfactor = "+adjustmentFactor+" ";
	
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
			  "    \"request\" : {\n"+
			  "        \"fromdate\" : \""+fromdate+"\",\n "+
			  "        \"todate\" : \""+todate+"\",\n "+
			  "        \"kpi\" : \""+kpi+"\",\n "+
			  "        \"store_id\" : \""+store_id+"\",\n "+
			  "        \"account_id\" : \""+account_id+"\",\n "+
			  "        \"year\" : \""+year+"\",\n "+
			  "        \"month\" : \""+month+"\",\n "+
			  "        \"day\" : \""+day+"\",\n "+
			  "        \"customer_idstore\" : \""+customer_idstore+"\",\n "+
			  "        \"timegrouped\" : \""+timegrouped+"\", \n "+
			  "        \"orario\" : \""+orario+"\", \n "+
			  "        \"adjustmentFactor\" : \""+adjustmentFactor+"\", \n "+
			  "        \"id_transaction\" : \""+sessionKey+"\" \n "+
			  "    },\n"+
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
		
		// =========================================
		// Numero pagine totali
		
		sql  =  "select count(*) num from counter.ws_analytics( "+
					"	"+fromdate+", "+
					"	"+todate+", "+
					"   "+kpi+", "+
					"	"+store_id+", "+
					"	'"+account_id+"', "+
					"	"+year+", "+
					"	"+month+", "+
					"	"+day+", "+
					"	"+customer_idstore+", "+
					"	"+timegrouped+", "+
					"   "+orario+", "+
					"   "+adjustmentFactor+", "+
					"   "+id_customer+"  "+
					") ";
		
		rs=st.executeQuery(sql);
		if(rs.next()) {
			numRow=rs.getInt("num");
		}
		rs.close();
		
		if(nrRecord.length()>0)
			nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
		else
			nrPag = 1;
		
		int max = 0;
		
		if(nrRecord.length()>0) {
			
			for (int i=1; i<=nrPag; i++) {
				
				
				max+=Integer.parseInt(nrRecord);
				
				if(i==Integer.parseInt(pag)) {
					
					rowIni = (max-Integer.parseInt(nrRecord));
					rowFin = Integer.parseInt(nrRecord);
					
				}
				
			}
		
		}
		
		// ========================================
		if(nrRecord.length()>0) {
			
			
			
			sql = "SELECT * from counter.ws_analytics( "+
					"	"+fromdate+", "+
					"	"+todate+", "+
					"   "+kpi+", "+
					"	"+store_id+", "+
					"	'"+account_id+"', "+
					"	"+year+", "+
					"	"+month+", "+
					"	"+day+", "+
					"	"+customer_idstore+", "+
					"	"+timegrouped+", "+
					"   "+orario+", "+
					"   "+adjustmentFactor+", "+
					"   "+id_customer+"  "+
					") limit "+rowFin+" offset "+rowIni+" ";
			
		} else {
			sql = "SELECT * from counter.ws_analytics( "+
					"	"+fromdate+", "+
					"	"+todate+", "+
					"   "+kpi+", "+
					"	"+store_id+", "+
					"	'"+account_id+"', "+
					"	"+year+", "+
					"	"+month+", "+
					"	"+day+", "+
					"	"+customer_idstore+", "+
					"	"+timegrouped+", "+
					"   "+orario+", "+
					"   "+adjustmentFactor+", "+
					"   "+id_customer+"  "+
					") ";
		}
		//out.println(sql);
		
		rs=st.executeQuery(sql);
		
		// Campi database
		int $riga = 0;
		String $storeid="";
		String $doorid="";
		String $accountid="";
		String $id_store="";
		String $id_device="";
		String $tic="";
		String $lasttime="";
		String $lasteventcode="";
		String $customer_devicename="";
		String $customer_idstore="";
		String $description="";
		String $_year="";
		String $_month="";
		String $_day="";
		String $_hour="";
		String $_minute="";
		String $kpi="";
		String $kpi_description="";
		String $val="";
		
		
		json+=//"{\n"+
			 // "        \"listings\" : [\n";	
			 "	[\n";	
				  
		while(rs.next()) {
			
			$riga = rs.getRow();
			$storeid = rs.getString("storeid");
			$doorid = rs.getString("doorid");
			$accountid = rs.getString("accountid");
			$id_store = rs.getString("id_store");
			$id_device = rs.getString("id_device");
			$tic = rs.getString("tic");
			$lasttime = rs.getString("lasttime");
			$lasteventcode = rs.getString("lasteventcode");
			$customer_devicename = rs.getString("customer_devicename");
			$customer_idstore = rs.getString("customer_idstore");
			$description = rs.getString("description");
			$_year = rs.getString("year");
			$_month = rs.getString("month");
			$_day = rs.getString("day");
			$_hour = rs.getString("hour");
			$_minute = rs.getString("minute");
			$kpi = rs.getString("kpi");
			$kpi_description = rs.getString("kpi_description");
			$val = rs.getString("val");
			
			
			list+="            {\n"+
				  "                \"row\" : "+$riga+",\n"+
				  "                \"storeid\" : "+$storeid+",\n "+
				  "                \"doorid\" : "+$doorid+",\n "+
				  "                \"accountid\" : "+$accountid+",\n "+
				  //"                \"id_store\" : "+$id_store+",\n "+
				  //"                \"id_device\" : "+$id_device+",\n "+
				  //"                \"tic\" : "+$tic+",\n "+
				  //"                \"lasttime\" : "+$lasttime+",\n "+
				  //"                \"lasteventcode\" : \""+$lasteventcode+"\",\n "+
				  "                \"customer_devicename\" : \""+$customer_devicename+"\",\n "+
				  "                \"customer_idstore\" : \""+$customer_idstore+"\",\n "+
				  "                \"description\" : \""+$description+"\",\n "+
				  "                \"year\" : "+$_year+",\n "+
				  "                \"month\" : "+$_month+",\n "+
				  "                \"day\" : "+$_day+",\n "+
				  "                \"hour\" : "+$_hour+",\n "+
				  "                \"minute\" : "+$_minute+",\n "+
				  "                \"kpi\" : \""+$kpi+"\",\n "+
				  "                \"kpi_description\" : \""+$kpi_description+"\",\n "+
				  "                \"val\" : "+$val+" \n "+	  
				  "            },\n";
		}
		rs.close();
		
		if(list.length()>0)
			list=list.substring(9,list.length()-2);
		
		json+=list+"\n";
		
		json+="        ]\n"+
			 // "}";
			  "";
			  
		// Se impostato a 'S' deve cryptare la stringa
		if(wscrypt.equals("S")) {
			
			byte[] decodedKey = Base64.getDecoder().decode(wsdecrypt_key);
			crkey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
			
			ecipher = Cipher.getInstance("AES");
			ecipher.init(Cipher.ENCRYPT_MODE, crkey);
			
			json = encrypt(json);
			
		}
		
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
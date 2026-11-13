<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st 	= null;
String sql 		= "";
String idtri = request.getParameter("idtri");
String idcus = request.getParameter("idcus");
String idsto = request.getParameter("idsto");

String send_mail = ""+request.getParameter("send_mail");
send_mail=Utility.noNull(send_mail);
if(send_mail.length()==0)
	send_mail = "false";

try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	/*
	sql   = "delete from rel_trigger_stores where trigger_id = '"+idtri+"' ";
	st.executeUpdate(sql);
	*/
	
	String temp[] = idsto.split(",");
	for(int i=0;i<temp.length;i++) {
		
		sql   = "insert into rel_trigger_stores "+
				"(trigger_id, customer_id, store_id, send_mail) "+
				"values "+
				"('"+idtri+"', "+idcus+" , '"+temp[i]+"', "+send_mail+")";
		System.out.println(sql);
		st.executeUpdate(sql);
		
	}
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
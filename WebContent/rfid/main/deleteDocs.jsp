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
String msg 		= "";
try {
	conn=Utility.getConnRfid();
	st=conn.createStatement();
	
	sql   = "delete from scannerdetail";
	st.executeUpdate(sql);
	sql   = "delete from scanner";
	st.executeUpdate(sql);
	sql   = "delete from documents_details";
	st.executeUpdate(sql);
	sql   = "delete from documents";
	st.executeUpdate(sql);
	
	msg = "All DOCS deleted";
}
catch(Exception e) {
	e.printStackTrace();
	msg = e.getMessage();
}
st.close();
conn.close();
%>
<%=msg%>
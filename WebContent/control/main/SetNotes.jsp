<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String idstore = request.getParameter("idstore");
String notes = request.getParameter("notes");

Connection conn = null;
Statement st 	= null;

String sql = "";
String msg = "";

try {
	conn=Utility.getConnAnalytics();
	st=conn.createStatement();
	
	sql   = "update f_stores set notes = '"+notes.replaceAll("'", "''")+"' where id = '"+idstore+"' ";
	System.out.println(sql);
	st.executeUpdate(sql);
}
catch(Exception e) {
	e.printStackTrace();
	msg = e.getMessage();
}
finally {
	if(st!=null)
		st.close();
	if(conn!=null)
		conn.close();
}
%>
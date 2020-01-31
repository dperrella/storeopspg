<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String accountid = request.getParameter("accountid");
String storeid = request.getParameter("storeid");
String doorid = request.getParameter("doorid");

Connection conn = null;
Statement st 	= null;

String sql = "";
String msg = "";

try {
	conn=Utility.getConnDispatcher();
	st=conn.createStatement();
	
	sql   = "select * from clear_notpaired('"+accountid+"','"+storeid+"','"+doorid+"') ";
	System.out.println(sql);
	st.executeQuery(sql);
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
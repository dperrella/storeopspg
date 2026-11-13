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
String idstore = request.getParameter("idstore");
String url = request.getParameter("url");

try {
	conn=Utility.getConnAdmin();
	st=conn.createStatement();
	
	sql   = "delete from store_images where idstore = "+idstore+" and url = '"+url+"' ";
	st.executeUpdate(sql);
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
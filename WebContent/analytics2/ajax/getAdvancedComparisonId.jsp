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
String id 		= "";
try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	id = Utility.getNewProgr(conn, "advanced_comparison", "id", "");
}
catch(Exception e) {
	e.printStackTrace();
	id = e.getMessage();
}
st.close();
conn.close();
%>
<%=id%>
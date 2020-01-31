<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.Calendar"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st 	= null;
String sql 		= "";
String idrel 	= request.getParameter("idrel");

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	sql   = "delete from rel_dep_employee where id_reldepemployee = '"+idrel+"' ";
	System.out.println(sql);
	st.executeUpdate(sql);
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
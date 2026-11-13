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

try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	sql   = "delete from rel_trigger_stores "+
			"where trigger_id = '"+idtri+"' "+
			"and customer_id = "+idcus+" and store_id = '"+idsto+"' ";
	st.executeUpdate(sql);
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
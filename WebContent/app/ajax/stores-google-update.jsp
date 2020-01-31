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
String id = request.getParameter("id");
String googleUid = request.getParameter("googleUid");
String lat = request.getParameter("lat");
String lon = request.getParameter("lon");
String types = request.getParameter("types");


try {
	conn=Utility.getConnAdmin();
	st=conn.createStatement();
	
	sql   = "update f_stores set latitude = '"+lat+"', longitude = '"+lon+"', google_places_types = '"+types+"', "+
			"google_uid = '"+googleUid+"' where id = "+id+" ";
	st.executeUpdate(sql);
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
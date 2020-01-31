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
String username = (String) session.getAttribute("username").toString().trim().toUpperCase();
String output 	= "";
Connection conn = null;
Statement st 	= null;
String sql 		= "";
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String tipo = request.getParameter("tipo");
String special = ""+request.getParameter("special");
special = Utility.noNull(special);

try {
	conn=Utility.getConnRfid();
	st=conn.createStatement();
	
	sql   = "delete from pref_user_period where idusr = '"+username+"' ";
	st.executeUpdate(sql);
		
	sql   = "insert into pref_user_period "+
			"(idusr, dtda, dtal, tipo, special) "+
			"values "+
			"('"+username+"', '"+dtda+"', '"+dtal+"', '"+tipo+"', '"+special+"')";
	System.out.println(sql);
	st.executeUpdate(sql);
	
	output = "Saved";
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
st.close();
conn.close();
%>
<%=output%>
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
String comparison = request.getParameter("comparison");

try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	sql   = "delete from pref_user_comparison where idusr = '"+username+"' ";
	st.executeUpdate(sql);
		
	sql   = "insert into pref_user_period "+
			"(idusr, comparison) "+
			"values "+
			"('"+username+"', '"+comparison+"' )";
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
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
ResultSet rs 	= null;
String sql 		= "";
String idemployee = request.getParameter("idemployee");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String file = "";
try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	
	dtda = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
	dtal = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
	
	System.out.println(idemployee);
	System.out.println(dtda);
	System.out.println(dtal);
	
	sql   = "select * from exportantress('"+idemployee+"','"+dtda+"','"+dtal+"') ";
	System.out.println(sql);
	rs = st.executeQuery(sql);
	if(rs.next()) {
		file = rs.getString(1);
	}
	rs.close();
	
	String temp[] = file.split("\\\\");
	String f = temp[temp.length-1];
	
	System.out.println(f);
	file=f;
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
<%=file%>
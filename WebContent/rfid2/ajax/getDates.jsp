<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Date"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String sql = "";
String dates = "";
String pref = request.getParameter("pref");

try {
	conn = Utility.getConnRfid();
	st = conn.createStatement();
	
	sql   = "select * from getperiodType('"+pref+"')";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	if(rs.next()) {
		dates=rs.getString("_dtda")+"##@@"+rs.getString("_dtal");
	}
	rs.close();
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
	}
	catch(Exception e) {}
}
%>
<%=dates%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Locale"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";

String msg = "";
try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
		
	sql   = "select * from verifica_minimo('"+id_departments+"','"+week+"','"+year+"') ";
	System.out.println(sql);
	
	rs=st.executeQuery(sql);
	while(rs.next()) {
		if(rs.getInt("_presenza")<rs.getInt("_minimo")) {
			msg+="- La presenza per la fascia "+rs.getString("_fascia")+" non rispetta il minimo.\n";
		}
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

st.close();
conn.close();
%>
<%=msg%>
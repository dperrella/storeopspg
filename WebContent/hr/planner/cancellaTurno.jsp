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
String $id = request.getParameter("id");

String tp = request.getParameter("tp");
String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");


Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
		
	sql   = "delete from hr_planification where id = '"+$id+"' ";
	System.out.println(sql);
		
	st.executeUpdate(sql);
}	
catch(Exception e) {
	e.printStackTrace();
}

st.close();
conn.close();
%>
<script>
javascript:window.location='planner-list2.htm?tp=<%=tp%>&id_departments=<%=id_departments%>&year=<%=year%>&week=<%=week%>';
window.location.href='planner-list2.htm?tp=<%=tp%>&id_departments=<%=id_departments%>&year=<%=year%>&week=<%=week%>';
</script>
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
String id_employee = request.getParameter("id_employee");
String year = request.getParameter("year");
String week = request.getParameter("week");
String day = request.getParameter("day");

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
		
	sql   = "delete from hr_planification "
			+"where id_employee = '"+id_employee+"' "
			+"and plan_year = '"+year+"' "
			+"and plan_week = '"+week+"' "
			+"and plan_day = '"+day+"' ";
	System.out.println(sql);
	st.executeUpdate(sql);
}	
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
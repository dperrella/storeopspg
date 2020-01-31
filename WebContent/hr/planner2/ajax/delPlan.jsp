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


try {
	String id_departments=request.getParameter("id_departments");
	String year=request.getParameter("year");
	String week=request.getParameter("week");
	String day=request.getParameter("day");
	String id_employee=request.getParameter("id_employee");
	String id_planification_type=request.getParameter("id_planification_type");
	String datetime_start=request.getParameter("datetime_start");
	String datetime_end=request.getParameter("datetime_end");
	
	
	
	conn=Utility.getConnHR();
	st=conn.createStatement();
	sql = "delete from hr_planification "
			+"where id_departments = '"+id_departments+"' "
			+"and plan_year = '"+year+"' "
			+"and plan_week = '"+week+"' "
			+"and plan_day = '"+day+"' "
			+"and id_employee = '"+id_employee+"' "
			+"and datetime_start = "+datetime_start+" "
			+"and datetime_end = "+datetime_end+" ";
	st.executeUpdate(sql);
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
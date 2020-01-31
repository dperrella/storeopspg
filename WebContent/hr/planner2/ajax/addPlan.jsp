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
	
	String status = "";
	long last_ts = new Date().getTime()/1000;
	String idusr_ins = session.getAttribute("username").toString();
	
	conn=Utility.getConnHR();
	st=conn.createStatement();
	
	String sqlDel   = "delete "+
			"from hr_planification "+
			"where id_Departments = '"+id_departments+"' "+
			"and id_employee = '"+id_employee+"' "+
			"and plan_year = '"+year+"' "+
			"and plan_week = '"+week+"' "+
			"and plan_day = '"+day+"' "+
			"and "+
			"("+
			"(datetime_start > "+datetime_start+" and datetime_start < "+datetime_end+") "+
			"or "+
			"(datetime_end > "+datetime_start+" and datetime_end < "+datetime_end+")"+
			"or (datetime_start = "+datetime_start+") or (datetime_end = "+datetime_end+") "+
			") ";
	System.out.println(sqlDel);
	st.executeUpdate(sqlDel);
	
	sql = "insert into hr_planification "
			+"(id_employee, id_departments, datetime_start, datetime_end, "
			+"id_planification_type,plan_year, plan_week,plan_day,status,last_ts,idusr_ins)"
			+"values "
			+"('"+id_employee+"', '"+id_departments+"', "+datetime_start+", "+datetime_end+" , "
			+"'"+id_planification_type+"', '"+year+"', '"+week+"', '"+day+"', '"+status+"', "+last_ts+", '"+idusr_ins+"') ";
	System.out.println(sql);
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
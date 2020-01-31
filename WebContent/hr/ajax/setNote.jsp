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
String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");
String status = request.getParameter("status");
String tp = request.getParameter("tp");

String note = ""+request.getParameter("note");
note = Utility.noNull(note);

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	
	if(note.length()>0) {
		
		sql   = "insert into hr_planification_note "+
				"(id_departments, plan_year, plan_week, idusr, tstamp, note) "+
				"values "+
				"('"+id_departments+"', '"+year+"', '"+week+"', '"+session.getAttribute("username")+"', current_date, '"+note.toUpperCase().replaceAll("'","''")+"')";
		st.executeUpdate(sql);
		
	}
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
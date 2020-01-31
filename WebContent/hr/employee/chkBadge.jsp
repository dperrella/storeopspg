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
String id_employee = request.getParameter("id_employee");
String id_badge = request.getParameter("id_badge");

String sSql 		= "";
String output 		= "";

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;

conn=Utility.getConnHR();
st=conn.createStatement();

sSql  = "select count(id_employ) count from rel_emp_badge "+
		"where id_badge = '"+id_badge+"' "+
		"and date_end is null ";
System.out.println(sSql);
rs = st.executeQuery(sSql);

int count = 0;
if(rs.next()) {
	
	count = rs.getInt("count");
	
}
rs.close();
st.close();
conn.close();

if(count>0) {
	output = "Il badge è gia' associato ad un altro dipendente. Confermando annullerai l'associazione precedente.";
}
%>
<%=output%>
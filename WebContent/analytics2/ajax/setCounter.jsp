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
Connection conn = null;
Statement st 	= null;
String sql 		= "";
String idcus = request.getParameter("idcus");
String idkpi = request.getParameter("idkpi");
String type = request.getParameter("type");

String fld = "";
try {
	if(type.equalsIgnoreCase("IN"))
		fld = "counter";
	else if(type.equalsIgnoreCase("OUT"))
		fld = "counterout";

	conn=Utility.getConnData();
	st=conn.createStatement();
	
	sql   = "update rel_cus_kpi set "+fld+" = false "+
			"where idcus = "+idcus+" ";
	st.executeUpdate(sql);
	
	sql   = "update rel_cus_kpi set "+fld+" = true "+
			"where idcus = "+idcus+" and idkpi = '"+idkpi+"' ";
	st.executeUpdate(sql);
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
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
String kpi = request.getParameter("kpi");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String account_code = request.getParameter("account_code");
String id_customer = request.getParameter("id_customer");
String mail = request.getParameter("mail");
String dataType = request.getParameter("dataType");

String idstore = request.getParameter("idstore");
if(idstore.length()==0)
	idstore = "null";

String hour = request.getParameter("hour");

String msg = "";
try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	sql   = "insert into data_export "+
			"(kpi, dtda, dtal, account_code, id_customer, mail, datatype, idstore, hour) "+
			"values "+
			"('"+kpi+"', '"+dtda+"', '"+dtal+"', '"+account_code+"', "+id_customer+", '"+mail+"', '"+dataType+"' , '"+idstore+"', '"+hour+"') ";
	System.out.println(sql);
	st.executeUpdate(sql);
	msg = "Data export scheduled";
}
catch(Exception e) {
	e.printStackTrace();
	msg = e.getMessage();
}
st.close();
conn.close();
%><%=msg%>
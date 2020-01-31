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
String id = request.getParameter("id");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String stores = request.getParameter("stores");
String kpi = request.getParameter("kpi");
String type = request.getParameter("type");
String ordinamento = request.getParameter("ordinamento");
String group = request.getParameter("group");
String l4l = ""+request.getParameter("l4l");
l4l = Utility.noNull(l4l);
if(l4l.length()==0)
	l4l = "false";
else
	l4l = "true";

//Formattazione data
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
			
System.out.println(dtda);
System.out.println(dtal);

try {
	Date d1 = sdf.parse(dtda);
dtda = iso.format(d1);
}
catch(Exception e) {}

try {
	Date d2 = sdf.parse(dtal);
	dtal = iso.format(d2);
}
catch(Exception e) {}

String output 	= "";
Connection conn = null;
Statement st 	= null;
String sql 		= "";
try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	sql   = "insert into advanced_comparison "+
		"(id, dtda, dtal, stores, kpi, ordinamento, tgroup, groupby, likex) "+
		"values "+
		"("+id+" , '"+dtda+"', '"+dtal+"', '"+stores.replaceAll(",", "*")+"', "+
		"'"+kpi.replaceAll(",", "*")+"', "+ordinamento+" , '"+type+"' , "+group+", "+l4l+") ";
	System.out.println(sql);
	st.executeUpdate(sql);
	output = "Saved";
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
st.close();
conn.close();
%>
<%=output%>
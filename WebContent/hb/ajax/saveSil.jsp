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
String sec = request.getParameter("sec");
String tm = request.getParameter("tm");
String checked = request.getParameter("checked");

try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	
	String temp[] = checked.split(",");
	for(int i=0;i<temp.length;i++) {
		
		String temp2[] = temp[i].split(";");
		
		sql   = "insert into silenced_trigger "+
				"(id_trigger, id_store, fromdate, todate) "+
				"values "+
				"('"+temp2[0]+"', "+temp2[1]+" , now(), now() + interval '"+sec+"' second)";
		System.out.println(sql);
		st.executeUpdate(sql);
		
	}
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
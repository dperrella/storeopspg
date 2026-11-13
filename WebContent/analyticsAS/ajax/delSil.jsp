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
String checked = request.getParameter("checked");

try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	
	String temp[] = checked.split(",");
	for(int i=0;i<temp.length;i++) {
		
		String temp2[] = temp[i].split(";");
		
		sql   = "delete from silenced_trigger "+
				"where id_trigger = '"+temp2[0]+"' and id_store = "+temp2[1]+" ";
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
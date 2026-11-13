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
import="java.io.*"
import="java.net.*"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st 	= null;
ResultSet rs	= null;
String sql		= "";
String iddoc = request.getParameter("iddoc");
String sku = "";

try {
	conn=Utility.getConnRfid();
	st=conn.createStatement();
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String docDate = "";
	sql = "select doc_date from documents where id = "+iddoc;
	rs = st.executeQuery(sql);
	if(rs.next()) {
		docDate = df.format(rs.getDate("doc_date"));	
	}
	rs.close();
	
	sql = "select * from clean_inventory("+iddoc+", '"+docDate+"')";
	System.out.println(sql);
	rs = st.executeQuery(sql);
	rs.close();
	
	st.close();
	conn.close();
}
catch(Exception e) {
	e.printStackTrace();
}
%>
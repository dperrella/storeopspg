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

String[] values=request.getParameterValues("chkSku");
for(int i=0;i<values.length;i++)
{
   sku+="'"+values[i]+"',";
}
if(sku.length()>0)
	sku=sku.substring(0,sku.length()-1);

try {
	conn=Utility.getConnRfid();
	st=conn.createStatement();
	
	String brk = "";
	sql = "delete from documents_detail where iddoc = "+iddoc+" and sku in("+sku+")";
	System.out.println(sql);
	st.executeUpdate(sql);
	
	sql = "select cpunt(*) num from documents_detail where iddoc = "+iddoc+" ";
	rs=st.executeQuery(sql);
	int num = 0;
	if(rs.next()) {
		num = rs.getInt("num");
	}
	rs.close();
	
	if(num==0) {
		sql = "update documents set filter_type = 1 where id = "+iddoc+" ";
		st.executeUpdate(sql);
	}
	
	
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
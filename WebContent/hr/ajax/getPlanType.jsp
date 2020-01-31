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
<span class="btn btn-default  m-b-sm"
data-toggle="popover" data-html="true" data-placement="bottom" 
data-content="<div class='scrollable'>
<%
Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	sql = "select * from hr_planification_type";
	rs=st.executeQuery(sql);
	while(rs.next()) {
%>
<span class='label <%=rs.getString("color")%>'><%=rs.getString("description") %></span>
<%
	}
	rs.close();
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
</div>" 
title="" data-original-title="<button type=&quot;button&quot; class=&quot;close pull-right&quot; data-dismiss=&quot;popover&quot;>&times;</button>Legenda">
<img src="info.png" style="width:18px;height:18px"></span>
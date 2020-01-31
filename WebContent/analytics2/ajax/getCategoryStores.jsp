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
ResultSet rs 	= null;
String sql 		= "";
String idusr = session.getAttribute("username").toString().toUpperCase().trim();
String category = request.getParameter("category");
String group = request.getParameter("group");
String storesOld = request.getParameter("idstores");
String fld = "";

if(group.equals("1"))
	fld = "category1";
if(group.equals("2"))
	fld = "category2";
if(group.equals("3"))
	fld = "category3";

String idstores = "";

try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	sql   = "select id from stores "+
			"where id in(select idstore from rel_user_stores where idusr = '"+idusr+"') "+
			"and "+fld+" = '"+category+"' and id in("+storesOld+")"; 
	System.out.println(sql);
	rs=st.executeQuery(sql);
	while(rs.next()) {
		idstores+=rs.getString("id")+",";
	}
	rs.close();
	
	if(idstores.length()>0)
		idstores = idstores.substring(0,idstores.length()-1);
	
}
catch(Exception e) {
	e.printStackTrace();
	idstores = "";
}
st.close();
conn.close();
%>
<%=idstores%>
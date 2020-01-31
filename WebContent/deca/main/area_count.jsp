<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
import="java.sql.*"
pageEncoding="ISO-8859-1"
%>
<%

Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
String sql = "";
String count = "";
try {
	
	ReadProperties prop = new ReadProperties("connection.properties");
	String usr = "mcsistemi";
	String psw = "Aprtssm$";
	String url = "jdbc:postgresql://192.168.51.107:5432/";
	String drv = "org.postgresql.Driver";
	String database  = "hella";
	
	Class.forName(drv);
	conn = DriverManager.getConnection(url+database, usr, psw);
	
	sql = "SELECT array_to_json(array_agg(vw_area_count)) FROM vw_area_count ";
	st=conn.prepareStatement(sql);
	rs=st.executeQuery();
	if(rs.next()) {
		count = rs.getString(1);
	}
	rs.close();
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
	}
	catch(Exception e) {}
}
%>
<%=count%>
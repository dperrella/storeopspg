<%@ 
page language="java" 
contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"
import="java.sql.*"
import="pkg.utility.Utility"
import="pkg.utility.Tsocket"
%>
<%
Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;

String sql = "";
String msg = "";


// request
String username = request.getParameter("username");
try {
	conn = Utility.getConnData();
	
	sql = "select origin from zutenti where idusr = ? ";
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	if(rs.next()) {
		String origin = Utility.noNull(rs.getString("origin"));
		session.setAttribute("origin", origin.length()==0 ? "Suite" : origin);
		msg = "User Origin: "+origin;
	}
	else {
		session.setAttribute("origin", null);
		msg = "Error! User Unknown";
	}
	rs.close();
	
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		if(conn!=null)
			conn.close();
	}
	catch(Exception e) {}
}
%>
<%=msg%>
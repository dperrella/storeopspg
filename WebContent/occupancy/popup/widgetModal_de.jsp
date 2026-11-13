<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="pkg.utility.TrippleDes"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%!
private static String getStore(Connection conn, String id) {
	String store = "";
	
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	
	try {
		st=conn.createStatement();
		sql= "select account_id||'-'||store_code store from stores where id = "+id+" ";
		System.out.println("***** "+sql);
		rs=st.executeQuery(sql);
		if(rs.next()) {
			store = rs.getString("store");
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
		}
		catch(Exception e) {}
	}
		
	return store;
}
%>
<% 
Connection conn = null;
String license = "";
String id = request.getParameter("id");
id=id.replaceAll("\\?cd=undefined", "");
try {
	conn = Utility.getConnData();
	String store = getStore(conn, id);
	System.out.println(store);
	TrippleDes td = new TrippleDes();
    license = td.encrypt(store);
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	if(conn!=null)
		conn.close();
}
%>
<div class="modal-dialog bg-dark dker modal-lg">
  <div class="modal-content bg-dark dker">
	
	<div>
		<iframe src="../main/widget-medium_de.jsp?key=<%=license.replaceAll("\\+","%2b") %>" width="100%" height="500px" frameBorder="0">Browser not compatible.</iframe>
	</div>
  </div>
  	
</div>

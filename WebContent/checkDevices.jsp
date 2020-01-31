<%@ 
page language="java" 
contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"
import="java.sql.*"
import="pkg.utility.Utility"
import="pkg.utility.Tsocket"
%>
<%
response.setHeader("Access-Control-Allow-Origin", "*"); 

Connection conn = null;
Statement st = null;
ResultSet rs = null;

String sql = "";

// request
String ip = request.getParameter("ip");
String port = request.getParameter("port");
String chk = request.getParameter("chk");

String log = "";
try {
	conn = Utility.getConnControl();
	st=conn.createStatement();
	
	int device_type = -1;
	if(chk.equalsIgnoreCase("EV"))
		device_type = 1;
	else if(chk.equalsIgnoreCase("EVS"))
		device_type = 2;
	else if(chk.equalsIgnoreCase("NEO"))
		device_type = 10;
	
	sql = "select * from devices where ip = '"+ip+"' and port = '"+port+"' and device_type = "+device_type;
	log+=sql+"<br/>";
	
	int iddevice;
	rs = st.executeQuery(sql);
	if(rs.next()) {
		//idStore = rs.getString("id_store");
		iddevice = rs.getInt("id");
	}
	else {
		iddevice = -1;
	}
	rs.close();
	log+="<h4>Iddevice: "+iddevice+"</h4>";
	
	Tsocket ts = new Tsocket();
	
	log+=ts.SendReceiveData(iddevice, ip, port, device_type);
	
	
	
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
<%=log%>
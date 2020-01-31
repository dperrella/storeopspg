<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String accountid = request.getParameter("accountid");
String storeid = request.getParameter("storeid");
String doorid = request.getParameter("doorid");

Connection conn = null;
Statement st 	= null;

String sql = "";
String msg = "";

try {
	conn=Utility.getConnAnalytics();
	st=conn.createStatement();
	
	sql   = "INSERT INTO devices(ip, port, macaddr, lasttime, enabled, accountid, storeid, timezone,lin, lout, device_type, readevery, id_store, updating, fromdate, "+
			"deviceid, devicename, siteid, sitename, doorid, logenabled, notes, update_time, unit_time, cause, date_disabled, adjustment_factor, "+
			"checktime, lasteventcode, diff_file_time, customer_devicename, adjustment_factor_m, adjustment_factor_s, line_antenna1, line_antenna2) "+ 
			"select null,null,null,null,true,userstr::integer,siteid::integer,'CET',null,null,4,60,'"+storeid+"',false,extract(epoch from now()),null, "+
			"null,doorid_evolvestore.description,null, v_dnotpaired.deviceid::integer,false,'EvolveStore inserito automaticamente',false,null,null,null,0, "+
			"null,null,null,null,0,0,0,0 from v_dnotpaired join doorid_evolvestore on deviceid::integer = doorid where siteid = '"+storeid+"'  and userstr = '"+accountid+"' ";
	st.executeUpdate(sql);
}
catch(Exception e) {
	e.printStackTrace();
	msg = e.getMessage();
}
finally {
	if(st!=null)
		st.close();
	if(conn!=null)
		conn.close();
}
%>
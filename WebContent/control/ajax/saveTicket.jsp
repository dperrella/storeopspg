<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="pkg.beans.StoreTicket"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
String sql 		= "";
try {
	conn=Utility.getConnAdmin();
	
	String id = request.getParameter("id");
	String idStore = request.getParameter("idStore");
	String sapCode = request.getParameter("sapCode");
	String callId = request.getParameter("callId");
	String callDescriptionShort = request.getParameter("callDescriptionShort");
	String callDescriptionLong = request.getParameter("callDescriptionLong");
	
	StoreTicket st = new StoreTicket();
	st.setIdStore(Integer.parseInt(idStore));
	
	DateFormat df = new SimpleDateFormat("yyyyMMdd");
	String dt = df.format(new Date());
	String year = dt.substring(0,4);
	if(id.length()==0) {
		
		st.setCallId(year+st.getLastCallId(conn));
		st.setChargeCode(st.getChargeCode(conn, Integer.parseInt(idStore)));
		st.setOpen(true);
		st.setSapCode(sapCode);
		st.setCallDescriptionShort(callDescriptionShort);
		st.setCallDescriptionLong(callDescriptionLong);
		st.add(conn);
	}
	else {
		st.setId(Integer.parseInt(id));
		st.setCallDescriptionShort(callDescriptionShort);
		st.setCallDescriptionLong(callDescriptionLong);
		st.update(conn);
		
	}
		
	
	
}
catch(Exception e) {
	e.printStackTrace();
}
conn.close();
%>
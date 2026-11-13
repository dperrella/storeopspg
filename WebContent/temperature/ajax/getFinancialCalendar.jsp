<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Date"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String sql = "";
String dates = "";
String year = request.getParameter("year");
DateFormat dateIso = new SimpleDateFormat("dd-MM-yyyy");

try {
	conn = Utility.getConnData();
	st = conn.createStatement();
	
	//Seleziono gli anno da financial_calendar
	sql = "select min(fromdate) fromdate, max(todate) todate from financial_calendar2 "+
		"where customer_id = "+session.getAttribute("idcus")+" and caltype = 'M' and year = "+year+" ";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	String sel = "";
	String fin_dtda = "";
	String fin_dtal = "";

	boolean is = false;
	if(rs.next()) {
		fin_dtda = dateIso.format(rs.getDate("fromdate"));
		fin_dtal = dateIso.format(rs.getDate("todate"));
		is = true;
	}
	rs.close();

	if(is==false) {
		
		sql = "select * from financial_calendar where customer_id = 9999 and  and year = "+year+" ";
		System.out.println(sql);
		rs=st.executeQuery(sql);
		sel = "";
		fin_dtda = "";
		fin_dtal = "";

		if(rs.next()) {
			fin_dtda = dateIso.format(rs.getDate("fromdate"));
			fin_dtal = dateIso.format(rs.getDate("todate"));
			
			is = true;
		}
		rs.close();
	}

	dates=fin_dtda+"##@@"+fin_dtal;
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
<%=dates%>
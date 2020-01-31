<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String tp = request.getParameter("tp");
String code = request.getParameter("code");
String drv = request.getParameter("drv");
String kpi_code = request.getParameter("kpi");
String kpi_old = request.getParameter("kpi_old");


String msg = "";

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnDispatcher();
	st = conn.createStatement();
	
	if(tp.equals("I")) {
		
		// Inserisce il kpi
		String name = request.getParameter("name");
		String desc = request.getParameter("desc");
		String symbol = request.getParameter("symbol");
		String color = request.getParameter("color");
		
		sql   = "insert into public.ztables "+
				"(xcdtb, xcdel, xdtab, xdesc, ata32, parent, calculated, color, kpi_symbol) "+
				"values "+
				"('KPI', '"+kpi_code.replaceAll("'","''")+"', '"+name.replaceAll("'","''")+"', '"+desc.replaceAll("'","''")+"', '', '', false, '"+color+"', '"+symbol+"') ";
		try {
			st.executeUpdate(sql);
		}
		catch(Exception e) {
			msg = "Warning: KPI already set. Will not be inserted...<br/>";
		}
	}
	
	if(kpi_old.trim().length()>0)
		sql = "delete from public.events_kpi where code_events = '"+code.replaceAll("'","''")+"' and driver_code = '"+drv.replaceAll("'","''")+"' and code_kpi = '"+kpi_old.replaceAll("'","''")+"' ";
	else
		sql = "delete from public.events_kpi where code_events = '"+code.replaceAll("'","''")+"' and driver_code = '"+drv.replaceAll("'","''")+"' and code_kpi = '"+kpi_code.replaceAll("'","''")+"' ";
	System.out.println(sql);
	st.executeUpdate(sql);
	
	sql = "insert into public.events_kpi (code_events, driver_code, code_kpi) "+
		  "values "+
		  "('"+code.replaceAll("'","''")+"', '"+drv.replaceAll("'","''")+"', '"+kpi_code.replaceAll("'","''")+"') ";
	System.out.println(sql);
	
	st.executeUpdate(sql);
	
	msg+="Operation successfull";
	
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
<%=msg %>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.GregorianCalendar"
import="pkg.utility.Utility"
import="java.util.ArrayList"
import="java.util.List"
import="pkg.beans.Tables"
import="pkg.beans.rfid.Step"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<%
List<String> series = new ArrayList<String>();
series.clear();


Connection conn = null;
PreparedStatement st 	= null;

ResultSet rs 	= null;

String sql 		= "";
String idsites = request.getParameter("idsites");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");

//Formattazione data
Date d1 = sdf.parse(dtda);
dtda = iso.format(d1);
Date d2 = sdf.parse(dtal);
dtal = iso.format(d2);

String output = "";

String legend = "";
String xAxis = "";
String serie = "";

try {
	conn=Utility.getConnRfid();
	
	// 1. select sites
	sql   = "select coalesce(sum(units) , null, 0) units, s.id, s.description "+
			"from sites s "+
			"left join stockdiary on ts::date <= now()::date "+
			"and id_site = s.id "+
			"where s.id in("+idsites+") "+
			"group by s.id, s.description "+
			"order by s.id";
	
	sql   = "select id, description, count(distinct(epc)) units from ( "
			+"select coalesce(sum(units) , null, 0) units, epc, s.id, s.description "
			+"from sites s "
			+"left join stockdiary on ts::date <= now()::date "
			+"and id_site = s.id "
			+"where s.id in("+idsites+") "
			+"group by s.id, s.description, epc "
			+"having sum(units) >= 1 "
			+"order by s.id "
			+") a "
			+"group by id, description "
			+"order by id";
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	while(rs.next()) {
		
		legend+=""+rs.getString("description")+",";
		
		
		String s = "{\"value\": "+rs.getString("units")+",\"name\":\""+rs.getString("description")+"\"}";
		series.add(s);
		
	}
	rs.close();
	
	
	if(legend.length()>0)
		legend=legend.substring(0,legend.length()-1);
	
	for(int i=0;i<series.size();i++) {
		serie+=series.get(i)+",";
	}
	
	if(serie.length()>0)
		serie="["+serie.substring(0,serie.length()-1)+"]";
	
	
	output = legend+"##@@"+serie;
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
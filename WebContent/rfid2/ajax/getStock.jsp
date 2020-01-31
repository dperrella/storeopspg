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
PreparedStatement st2 	= null;

ResultSet rs 	= null;
ResultSet rs2 	= null;

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
	sql   = "select id, description from sites where id in("+idsites+")";
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	while(rs.next()) {
		
		legend+=""+rs.getString("description")+",";
		
		// 2. select series
		sql   = "SELECT day::date , coalesce(sum(units) , null, 0) units, s.id, s.description "+
			"FROM   generate_series(timestamp '"+dtda+"', timestamp '"+dtal+"' , interval  '1 day') AS t(day) "+
			"left join sites s on id in("+rs.getString("id")+") "+
	        "left join stockdiary on ts::date <= day::date "+
	        "and id_site = s.id "+
	        "group by day::date, s.id, s.description "+
	        "order by s.id, day::date ";
		
		String s = "";
		st2 = conn.prepareStatement(sql);
		rs2 = st2.executeQuery();
		while(rs2.next()) {
			s+=rs2.getString("units")+",";
		}
		rs2.close();
		
		if(s.length()>0)
			s=s.substring(0,s.length()-1);
		
		s = "{\"name\":\""+rs.getString("description")+"\",\"type\":\"line\",\"data\":["+s+"],\"smooth\":true}";
		series.add(s);
		
	}
	rs.close();
	
	
	if(legend.length()>0)
		legend=legend.substring(0,legend.length()-1);
	
	
	
	
	// 3. select series
	sql   = "SELECT day::date FROM   generate_series(timestamp '"+dtda+"', timestamp '"+dtal+"', interval  '1 day') AS t(day) ";
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	while(rs.next()) {
		xAxis+=""+rs.getString("day")+",";
	}
	rs.close();
	
	if(xAxis.length()>0)
		xAxis=xAxis.substring(0,xAxis.length()-1);
	
	for(int i=0;i<series.size();i++) {
		serie+=series.get(i)+",";
	}
	
	if(serie.length()>0)
		serie="["+serie.substring(0,serie.length()-1)+"]";
	
	
	output = legend+"##@@"+xAxis+"##@@"+serie;
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
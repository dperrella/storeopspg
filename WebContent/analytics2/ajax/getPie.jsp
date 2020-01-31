<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.ArrayList"
import="java.util.List"
import="pkg.beans.Tables"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
List<Tables> tablist = new ArrayList<Tables>();
tablist.clear();

Connection conn = null;
PreparedStatement st 	= null;
ResultSet rs 	= null;
String sql 		= "";
String idstores = request.getParameter("idstores");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String kpi = request.getParameter("kpi");
String kpi_selected = request.getParameter("kpi_selected");
String uid = request.getParameter("uid");

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");

//Formattazione data
Date d1 = sdf.parse(dtda);
dtda = iso.format(d1);
Date d2 = sdf.parse(dtal);
dtal = iso.format(d2);

String output = "";

try {
	conn=Utility.getConnData();
	
	// *******
	// tablist...
	sql   = "select * from landing_widget('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', '"+uid+"') , "
			+ "ztables where xcdtb = 'KPI' and trim(_kpi) = trim(xcdel) and _kpi = '"+kpi_selected+"' order by _kpi, _inc desc";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	while(rs.next()) {
		
		tablist.add(new Tables("KPI",rs.getString("_kpi"),rs.getString("_description"),
				rs.getString("_inc"),rs.getString("_id_store"),"",""));
		
	}
	rs.close();
	st.close();
	
	sql   = "delete from rel_user_chart where idusr = '"+(String) session.getAttribute("username")+"' ";
	st = conn.prepareStatement(sql);
	st.executeUpdate();
	st.close();
	sql   = "insert into rel_user_chart (idusr, idchart) values ('"+(String) session.getAttribute("username")+"', 'P') ";
	st = conn.prepareStatement(sql);
	st.executeUpdate();
	st.close();
	
	
	st.close();
	
	String legend = "[";
	String indicator = "[";
	for(int i=0;i<tablist.size();i++) {
		
		indicator+="{ \"value\": "+tablist.get(i).getCmp1()+", \"name\": \""+tablist.get(i).getXdtab()+"\" },";
		legend+="\""+tablist.get(i).getXdtab()+"\",";
	}
	
	if(indicator.length()>0)
		indicator = indicator.substring(0,indicator.length()-1);
	if(legend.length()>0)
		legend = legend.substring(0,legend.length()-1);
	
	indicator+="]";
	legend+="]";
	
	output = indicator+"##@@"+legend;
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
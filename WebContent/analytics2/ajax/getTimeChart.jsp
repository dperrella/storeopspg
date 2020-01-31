<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.sql.*"
import="pkg.beans.Tables"
import="java.util.List"
import="java.util.ArrayList"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%

List<Tables> chart = new ArrayList<Tables>();
chart.clear();

String idstores = request.getParameter("idstores");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String kpi = request.getParameter("kpi");
String tipo = request.getParameter("tipoPeriod");
String sum_avg = request.getParameter("sum_avg");

String like4like = ""+request.getParameter("like4like");
like4like=Utility.noNull(like4like);

String group = request.getParameter("group");
String comparison = request.getParameter("comparison");

boolean l4l = false;
if(like4like.length()>0)
	l4l = true;


System.out.println(idstores);
System.out.println(dtda);
System.out.println(dtal);
System.out.println(kpi);
System.out.println(tipo);
System.out.println(sum_avg);
System.out.println(like4like);
System.out.println(group);
System.out.println(comparison);


Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
String sql = "";

String table = "";

try {

	conn = Utility.getConnData();
	
	// Formato data
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
	
	//Formattazione data
	Date d1 = sdf.parse(dtda);
	dtda = iso.format(d1);
	Date d2 = sdf.parse(dtal);
	dtal = iso.format(d2);
	
	sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol "
			+ "from public.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
			+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"') a, "
			+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' "
			+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	String td = "";
    int count = 0;
    int num = 0;
    
    String border = "";

	int rowspan = Integer.parseInt(request.getParameter("rowspan"));
	
	
	String val = "";
	String op = "";
	while(rs.next()) {
		
		for(int i=0;i<55;i++) {
			if(rs.getString((i+5))!=null) {
				val+=Utility.round(rs.getDouble((i+5)) , 2)+",";
			}
		}
		
		if(val.length()>0)
			val=val.substring(0,val.length()-1);
		
		if(rs.getString("op").equalsIgnoreCase(sum_avg))
			chart.add(new Tables("VAL", rs.getString("codkpi"), val, rs.getString("color"),rs.getString("codkpi"),"",""));
		
		val = "";
		
		num+=1;
    	
	}
	rs.close();
	st.close();
		
	for(int i=0;i<chart.size();i++) {
		
		//System.out.println(chart.get(i).getXcdel()+" "+chart.get(i).getXdtab());
		
		table+=chart.get(i).getXdtab()+"##@@"+chart.get(i).getCmp1()+"##@@"+chart.get(i).getCmp2()+";";
	}
	
	if(table.length()>0)
		table=table.substring(0,table.length()-1);
	
	System.out.println(table);
	
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
<%=table%>
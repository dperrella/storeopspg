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
String tipoPeriod = request.getParameter("tipoPeriod");

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
	//String tipo = "DIFF";
	String tipo = "";
	sql   = "select a.*, color from public.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', "
			+"'"+tipoPeriod+"',false, 4 ,'C02') a , ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' and op = 'SUM' order by codkpi, tipo ";
	System.out.println("***** "+sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	String val = "";
	while(rs.next()) {
		
		if(rs.getString("tipo").trim().equalsIgnoreCase("")) {
			for(int i=0;i<55;i++) {
				if(rs.getString((i+5))!=null) {
					val+=Utility.round(rs.getDouble((i+5)) , 2)+",";
				}
			}
			if(val.length()>0)
				val=val.substring(0,val.length()-1);
			
		}
		
		
		if(rs.getString("tipo").trim().equalsIgnoreCase("DIFF")) {
			String icon = "";
			String color = rs.getString("color");
			
			if(Utility.round(rs.getDouble("tot") , 2)>0)
				icon = "<i style=\"font-size:25px\" class=\"fa fa-caret-up text-success\"></i>";
			else
				icon = "<i style=\"font-size:25px\" class=\"fa fa-caret-down text-danger\"></i>";
				
			tablist.add(new Tables("KPI",rs.getString("codkpi"),val,
					""+Utility.round(rs.getDouble("tot") , 2),icon,color,""));	
			
			val="";
		}
		
	}
	rs.close();
	st.close();
	
	for(int i=0;i<tablist.size();i++) {
		output+=tablist.get(i).getXcdel()+"##@@"+tablist.get(i).getXdtab()+"##@@"+tablist.get(i).getCmp1()+"##@@"+tablist.get(i).getCmp2()+"##@@"+tablist.get(i).getCmp3()+";";
	}
	
	if(output.length()>0)
		output=output.substring(0,output.length()-1);
	
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.text.DecimalFormat"
import="java.util.Arrays"
import="java.sql.*"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%!
private static String getKpiDesc(Connection conn,String kpi) {
	String desc = "";
	try {
		Statement st = conn.createStatement();
		ResultSet rs = null;
		
		String sql = "select xdtab from ztables where xcdtb = 'KPI' and xcdel = '"+kpi+"' ";
		rs=st.executeQuery(sql);
		if(rs.next()) {
			desc = rs.getString("xdtab");
		}
		rs.close();
		
		st.close();
	}
	catch(Exception e) {
		desc = e.getMessage();
	}
	return desc;
}
%>
<table class="table">
	<thead>
<%	
	// **********************************************************
	// Connessione al DATABASE
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	
	DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
	try
	{
		conn = Utility.getConnData();
		st=conn.createStatement();
		
		String kpi = request.getParameter("kpi");
		String dtda = request.getParameter("dtda");
		String dtal = request.getParameter("dtal");
		
		dtda = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
		dtal = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
		
		String account_code = request.getParameter("account_code");
		String id_customer = request.getParameter("id_customer");
		
		
		String idstore = ""+request.getParameter("idstore");
		idstore=Utility.noNull(idstore);
		if(idstore.length()==0)
			idstore = "null";
		else 
			idstore = "'"+idstore+"'";
		
		String hour = ""+request.getParameter("hour");
		hour=Utility.noNull(hour);
		if(hour.length()==0)
			hour = "false";
		
		
		String cols = "";
		String cols2= "";
		String kpis[] = kpi.split(",");
		Arrays.sort(kpis);
		
		for(String k : kpis) {
			cols+= "SELECT '"+k+"' UNION ALL ";
			cols2+= k+" NUMERIC,";
		}
		
		if(cols.length()>0) {
			cols = cols.substring(0,cols.length()-10);
			cols2= cols2.substring(0,cols2.length()-1);
		}
%>
	<tr>
		<th style="width:100">Date</th>
		<th>Store Id</th>
		<th>Store Description</th>
	
<%
for(String k : kpis) {
%>
		<th><%=getKpiDesc(conn,k) %></th>
<%	
}
%>
	</tr>
	</thead>
	<tbody>
<%		
		sql  =  "SELECT * FROM crosstab( "+
				"$$select make_date(year,month,day) dt, storeid,description,kpi, "+
				"case when kpi = 'ACOD' then val*100 else val end from counter.ws_analytics_dan( "+
				"'"+dtda+"','"+dtal+"','"+kpi.replaceAll(",", "*")+"',null,'"+account_code+"', "+
				"null,null,null,null,1440,"+hour+",false,"+id_customer+") "+
				"order by storeid, make_date(year,month,day), kpi$$, "+
				"$$"+cols+"$$ "+
				") "+
				"AS ct(dt date, storeid VARCHAR, description VARCHAR, "+cols2+");";
		
		System.out.println("**** "+sql);
		rs=st.executeQuery(sql);
		
		String $dt="";
		String $storeid="";
		String $accountid="";
		String $description="";
		String $val="";
		while(rs.next()) {
%>
	<tr>
		<td><%=rs.getString("dt") %></td>
		<td><span class="label bg-light"><%=rs.getString("storeid") %></span></td>
		<td><%=rs.getString("description") %></td>
<%
			for(String k : kpis) {
%>
		<td><%=Utility.noNull(rs.getString(k)).length()==0 ? "0" : "<b>"+Utility.round(rs.getDouble(k), 2)+"</b>" %></td>
<%
			}
%>
	</tr>
<%
		}
		rs.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
	}
%>
	</tbody>
</table>
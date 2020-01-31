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

String idticket = ""+request.getParameter("idticket");
idticket=idticket.replaceAll("\\?cd=undefined", "");
String print = ""+request.getParameter("print");
print=print.replaceAll("\\?cd=undefined", "");

String res[] = idticket.split("-");
String ticketcli = res[0];
String ticketcassa = res[1];
String ticketyear = res[2];
String tickettyp = res[3];
String ticketid = res[4];

%>

<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Ticket Detail</h3>
	</div>
	<div class="modal-body" id="tickdett">
		
		
		<h4><%=idticket %></h4>
		<hr/>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Product</th>
					<th>Units</th>
					<th>Full Price</th>
					<th>Discount</th>
					<th>Price</th>
				</tr>
			</thead>
		<tbody>
		
<%

//**********************************************************
//Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";

//**********************************************************
//Connessione al DATABASE
try
{
	java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
	
	conn=Utility.getConnPos();
	st = conn.createStatement();
	
	sql = "select prezzointero*(select 1+rate from taxes where idclient = '"+ticketcli+"' and category = '001' order by validfrom desc limit 1) p_intero,price*(select 1+rate from taxes where idclient = '"+ticketcli+"' and category = '001' order by validfrom desc limit 1) p_price,* from ticketlines "+
			"left join tickets on id = ticket join products on products.idclient = tickets.idclient and product = products.id "+
			"where tickets.idclient = '"+ticketcli+"' "+
			"and tickettype = '"+tickettyp+"' "+
			"and ticketid = '"+ticketid+"'";
	//System.out.println("SQL1 = " + sql);
	rs = st.executeQuery(sql);
	while(rs.next()) {
%>
			<tr>
				<td><%=Utility.noNull(rs.getString("name")) %></td>
				<td><%=Utility.noNull(rs.getString("units"))%></td>
				<td><%=df.format(rs.getDouble("p_intero")) %></td>
				<td><%=df.format(rs.getDouble("sconto")) %></td>
				<td><%=df.format(rs.getDouble("p_price")) %></td>
			</tr>
<%	
	}
	rs.close();
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


if(print.equals("Y")) {
	print="printDiv('tickdett')";	
}
%>
			</tbody>
		</table>
		
		
		
		
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="<%=print%>"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
	</div>
  </div>
</div>

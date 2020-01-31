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

String money = ""+request.getParameter("money");
money=money.replaceAll("\\?cd=undefined", "");
String print = ""+request.getParameter("print");
print=print.replaceAll("\\?cd=undefined", "");
%>

<div class="modal-dialog modal-sm">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Closed Cash</h3>
	</div>
	<div class="modal-body" id="tickdett">
		
		
		
		
<%

//**********************************************************
//Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";

//**********************************************************
//Connessione al DATABASE
String datestart = "";
String dateend = "";
String total = "";
String idclient = "";
try
{
	java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
	
	conn=Utility.getConnPos();
	st = conn.createStatement();
	
	sql  =  "select c.money,c.idclient,host,hostsequence,datestart::date,dateend::date, sum(total) total\n" + 
			"from closedcash c join receipts r on c.idclient = r.idclient and c.money = r.money\n" + 
			"join payments p on c.idclient = p.idclient and p.receipt = r.id\n" + 
			"where c.money = '"+money+"'\n" + 
			"group by c.money,c.idclient,host,hostsequence,datestart,dateend ";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	
	if(rs.next()) {
		datestart = rs.getString("datestart");
		dateend = rs.getString("dateend");
		total = df.format(rs.getDouble("total"));
		idclient = rs.getString("idclient");
	}
	rs.close();
%>
		<h4><%=idclient %></h4>
		<p><%=datestart %> / <%=dateend %></p>
		<hr/>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Payment</th>
					<th>Value</th>
				</tr>
			</thead>
		<tbody>

<%	
	sql = "select upper(tp.descrizione) descr ,sum(total) total from payments p join receipts r on p.receipt = r.id "+
			"join tipopagamento tp on tp.idclient = p.idclient and p.tipopagamento = tp.id "+
			"where money = '"+money+"' "+
			"group by money,tp.descrizione ";
	System.out.println(sql);

	//System.out.println("SQL1 = " + sql);
	rs = st.executeQuery(sql);
	while(rs.next()) {
%>
			<tr>
				<td><%=Utility.noNull(rs.getString("descr")) %></td>
				<td style="text-align:right"><%=df.format(rs.getDouble("total")) %></td>
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
			<tfoot>
				<tr>
					<td></td>
					<td style="text-align:right"><b><%=total %></b></td>
				</tr>
			</tfoot>
		</table>
		
		
		
		
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="<%=print%>"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
	</div>
  </div>
</div>

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

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";
int count = 0;

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnDispatcher();
	st = conn.createStatement();
	sql   = "select account_id, case when c.description is null then '!Undefined!' else c.description end "+
			"from neo.account_details a "+
			"left join neo.customers c on case when account_code_evolvestore is null or account_code_evolvestore = '' then account_code else account_code_evolvestore end = account_id "+ 
			"group by account_id, c.description "+
			"order by account_id ";
	rs=st.executeQuery(sql);
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Event Customers List</h3>
	</div>
	<div class="modal-body">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_us_filtra") %>">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th style="width:20%">Account ID</th>  
			  <th style="width:60%">Description</th>  
			  <th style="width:20%"></th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
while (rs.next())
{
%>
			<tr>
			  <td><%=rs.getString("account_id") %></td>
			  <td><%=rs.getString("description") %></td>
			  <td><a href="#" onclick="viewCustomerEvents('<%=rs.getString("account_id") %>')" class="btn btn-default btn-sm"><i class="icon-magnifier"></i> View Customer Events</a></td>
			</tr>
<%
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

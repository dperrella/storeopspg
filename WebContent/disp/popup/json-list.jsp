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

String folder = ""+request.getParameter("folder");
folder=Utility.noNull(folder);
folder=folder.replaceAll("\\?cd=undefined", "");

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
	sql   = "select * from downloaded_neo where folder = '"+folder+"' and status = 4 ";
	rs=st.executeQuery(sql);
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Json List</h3>
	</div>
	<div class="modal-body">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_us_filtra") %>">
		
		<div style="overflow:auto; height:350px;">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th>Folder</th>
			  <th>Zip File</th>  
			  <th>Json File</th>  
			  <th></th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
String chk = "";
while (rs.next())
{
%>
			<tr>
			  <td><%=rs.getString("folder") %></td>
			  <td><%=rs.getString("zipfile") %></td>
			  <td><%=rs.getString("jsonfile") %></td>
			  <td><a href="fast-view.htm?id=<%=rs.getString("id") %>" class="btn btn-default btn-xs"><i class="icon-eye"></i> Fast View</a></td>
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
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

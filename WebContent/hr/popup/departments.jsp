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
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String department_description = request.getParameter("department_description");
department_description = Utility.noNull(department_description);

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
	conn=Utility.getConnHR();
	st = conn.createStatement();
	
	if(department_description.length()>0) {
		cond+=" and upper(description) like '%"+ department_description.toUpperCase()+"%' ";
	}
	
	sql   = "select * from hr_departments where id_customer = "+session.getAttribute("idcus")+" "+cond;
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
		<h3 class="font-thin"><%=propLang.get("pop_dp_title") %></h3>
	</div>
	<div class="modal-body">
		
		<!--
		<input type="text" placeholder="Live search" class="popupSearch form-control"/>
		-->
		<div style="overflow:auto; height:275px;">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_dp_filtra") %>">
		
		<table class="popupTable table table-striped b-t b-light">
		  <thead>
			<tr>
			<!-- 
			  <th><%=propLang.get("pop_dp_id") %></th>
			-->  
			  <th><%=propLang.get("pop_dp_descrizione") %></th>
			  <th></th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
String color = "";
String reg 	 = "";

while (rs.next())
{
%>
			<tr>
			<!-- 
			  <td><span class="label bg-light"><%=rs.getString("id") %></span></td>
			-->  
			  <td><%=rs.getString("description") %></td>
			  <td><a href="#" data-dismiss="modal" class="btn btn-default btn-xs"
			  onclick="document.getElementById('id_department').value = '<%=rs.getString("id")%>';
			  document.getElementById('department_description').value = '<%=rs.getString("description").trim().replaceAll("'"," ").replaceAll("\""," ")%>';
			  $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_dp_sel") %></a></td>
<%
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_dp_chiudi") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

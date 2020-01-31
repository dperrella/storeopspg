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

String emp_description = ""+request.getParameter("emp_description");
emp_description = Utility.noNull(emp_description);

System.out.println(emp_description);

String manager = ""+request.getParameter("manager");
manager = Utility.noNull(manager);

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
	
	if(manager.length()==0) {
		if(emp_description.length()>0) {
			cond+=" and upper(name) || upper(surname) like '%"+emp_description.toUpperCase()+"%' ";
		}
		
	}
	else {
		if(emp_description.length()>0) {
			cond+=" and upper(name) || upper(surname) like '%"+emp_description.toUpperCase()+"%' ";
		}
	}
	
	if(manager.length()==0) {
		sql   = "select * from hr_employee where id_customer = "+session.getAttribute("idcus")+" "+cond;
	}
	else {
		sql   = "select * from hr_employee a "+
				"left join rel_dep_employee b on a.id = b.id_employee "+
				"where id_roles = '92b2a236-ba12-4b94-96ef-11bde7b85769' and a.id_customer = "+session.getAttribute("idcus")+" "+cond ;
	}
	System.out.println(sql);
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
		<h3 class="font-thin"><%=propLang.get("pop_em_title") %></h3>
	</div>
	<div class="modal-body">
		
		<div style="overflow:auto; height:275px;">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_em_filtra") %>">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			<!-- 
			  <th><%=propLang.get("pop_em_id") %></th>
			-->  
			  <th><%=propLang.get("pop_em_nome") %></th>
			  <th><%=propLang.get("pop_em_cognome") %></th>
			  <th></th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
while (rs.next())
{
%>
			<tr>
			<!-- 
			  <td><span class="label bg-light"><%=rs.getString("id") %></span></td>
			-->  
			  <td><%=rs.getString("name") %></td>
			  <td><%=rs.getString("surname") %></td>
			  <td><a href="#" data-dismiss="modal" class="btn btn-default btn-xs"
			  onclick="document.getElementById('id_employee').value = '<%=rs.getString("id")%>';
			  document.getElementById('emp_description').value = '<%=rs.getString("name").trim().replaceAll("'"," ").replaceAll("\""," ")%> <%=rs.getString("surname").trim().replaceAll("'"," ").replaceAll("\""," ")%>';
			  $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_em_sel") %></a></td>
<%
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_em_chiudi") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

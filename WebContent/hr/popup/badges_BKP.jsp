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

String id_badge = request.getParameter("id_badge");
id_badge = Utility.noNull(id_badge);

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
	conn=Utility.getConnAdmin();
	st = conn.createStatement();
	
	if(id_badge.length()>0) {
		cond+=" where id like '%"+id_badge.toUpperCase()+"%' ";
	}
	
	sql   = "select a.id, a.number, c.name, c.surname, b.date_end, "+
			"To_timestamp(b.date_end) dataFine "+
			"from hr_badge a "+
			"left join rel_emp_badge b on b.id_badge = a.id "+
			"left join hr_employee c on c.id = b.id_employ ";
	
	rs=st.executeQuery(sql);
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin"><%=propLang.get("pop_ba_title") %></h3>
	</div>
	<div class="modal-body">
		
		<div style="overflow:auto; height:275px;">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			<!-- 
			  <th><%=propLang.get("pop_ba_id") %></th>
			-->  
			  <th><%=propLang.get("pop_ba_numero") %></th>
			  <th><%=propLang.get("pop_ba_dip") %></th>
			  <th><%=propLang.get("pop_ba_dtfin") %></th>
			  <th></th>
			</tr>
		  </thead>
		  <tbody>
<%
String name = "";
String surname = "";
String employee = "";
String date_end = "";

String btn = "";

while (rs.next())
{
	name = ""+rs.getString("name");
	surname = ""+rs.getString("surname");
	date_end = ""+rs.getString("dataFine");
	
	name = Utility.noNull(name);
	surname = Utility.noNull(surname);
	date_end = Utility.noNull(date_end);
	
	if(surname.length()==0)
		employee = "-";
	else
		employee = name+" "+surname;
	
	if(date_end.length()==0)
		date_end = "-";
	
	if(date_end.length()==1) {
		btn = "<a href=\"#\" data-dismiss=\"modal\" class=\"btn btn-default btn-xs\" "+
			  "onclick=\"document.getElementById('id_badge').value = '"+rs.getString("id")+"'\">"+propLang.get("pop_ba_sel")+"</a>";
	}
	else {
		btn = "-";
	}
%>
			<tr>
			<!-- 
			  <td><span class="label bg-light"><%=rs.getString("id") %></span></td>
			-->
			  <td><%=rs.getString("number") %></td>
			  <td><%=employee %></td>
			  <td><%=date_end %></td>
			  <td><%=btn %></td>
<%
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ba_chiudi") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

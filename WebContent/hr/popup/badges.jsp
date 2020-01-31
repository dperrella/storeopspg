<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
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
	conn=Utility.getConnHR();
	st = conn.createStatement();
	
	if(id_badge.length()>0) {
		cond+=" and id like '%"+id_badge.toUpperCase()+"%' ";
	}
	/*
	sql   = "select a.id, a.number, max(To_timestamp(b.date_end))  dataFine "+
			"from hr_badge a "+
			"left join rel_emp_badge b on a.id = b.id_badge "+
			"group by a.id, a.number "+
			"order by a.number ";
	*/
	sql = "select a.id, a.number, max(To_timestamp(b.date_end))  dataFine, e.name, e.surname "+
			"from hr_badge a "+ 
			"left join rel_emp_badge b on a.id = b.id_badge "+ 
			"left join hr_employee e on b.id_employ = e.id and e.id_customer = "+session.getAttribute("idcus")+" "+
			"where a.id_customer = "+session.getAttribute("idcus")+" "+
			"and a.number not in(select x.number from hr_badge x "+
			"join rel_emp_badge y on x.id = y.id_badge where y.date_end is null) "+
			"group by a.id, a.number, e.name, e.surname "+  
			"order by a.number";
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
		<h3 class="font-thin"><%=propLang.get("pop_ba_title") %></h3>
	</div>
	<div class="modal-body">
		
		<div style="overflow:auto; height:275px;">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_em_filtra") %>">
		
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
		  <tbody id="fbody">
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
	if(date_end.length()>0)
		date_end = df.format(rs.getDate("dataFine"));
	
	if(surname.length()==0)
		employee = "-";
	else
		employee = name+" "+surname;
	
	if(date_end.length()==0)
		date_end = "-";
	
	//if(date_end.length()==1) {
		btn = "<a href=\"#\" data-dismiss=\"modal\" class=\"btn btn-default btn-xs\" "+
			  "onclick=\"document.getElementById('id_badge').value = '"+rs.getString("id")+"';document.getElementById('nr_badge').value = '"+rs.getString("number")+"'; $('.modal').remove(); $('div.modal-backdrop').remove();\">"+propLang.get("pop_ba_sel")+"</a>";
	//}
	//else {
	//	btn = "-";
	//}
	
	int diff = -1;
	if(employee.equals("-")) {
		diff = 1;	
	}
	
	if(!date_end.equals("-")) {
		
		String d1 = df.format(new Date());
		diff = Utility.getDateDiffDays2(date_end, d1);
		
		
		
	}
	
	System.out.println("**** "+diff);
	
	if(diff>0) {
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
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ba_chiudi") %></a>
	</div>
  </div>
</div>

<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

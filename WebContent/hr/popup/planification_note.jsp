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

String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");
String tp = request.getParameter("tp");

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
	
	sql   = "select idusr, tstamp, note, id "+
			"from hr_planification_note "+
			"where id_departments = '"+id_departments+"' "+
			"and plan_year = '"+year+"' "+
			"and plan_week = '"+week+"' ";
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
		<h3 class="font-thin">Note Planner</h3>
	</div>
	<div class="modal-body">
		
		<div style="overflow:auto; height:275px;">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th>Id</th>
			  <th>User</th>
			  <th>Date</th>
			  <th>Note</th>
			</tr>
		  </thead>
		  <tbody>
<%
String name = "";
String surname = "";
String employee = "";
String date_end = "";

while (rs.next())
{
%>
			<tr>
			  <td><%=rs.getString("id") %></td>
			  <td><%=rs.getString("idusr") %></td>
			  <td><%=rs.getString("tstamp") %></td>
			  <td><%=rs.getString("note") %></td>
			</tr>
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

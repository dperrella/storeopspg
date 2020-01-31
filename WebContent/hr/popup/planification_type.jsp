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

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnHR();
	st = conn.createStatement();
	
	sql   = "select description, color, col "+
			"from hr_planification_type ";
	rs=st.executeQuery(sql);
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog modal-sm">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin"><%=propLang.get("plt_dsfun")%></h3>
	</div>
	<div class="modal-body">
		
		<table class="table">
		  <thead>
			<tr>
			  <th><%=propLang.get("plt_descrizione") %></th>
			  <th><%=propLang.get("plt_colore") %></th>
			</tr>
		  </thead>
		  <tbody>
<%
while (rs.next())
{
%>
			<tr>
			  <td><%=rs.getString("description") %></td>
			  <td><span class="label" style="background-color:<%=rs.getString("col")%>"><%=rs.getString("color")%></span></td>
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

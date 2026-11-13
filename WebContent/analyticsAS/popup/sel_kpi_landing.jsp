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
	conn=Utility.getConnData();
	st = conn.createStatement();
		
	sql   = "select xcdel, xdtab, xdesc "+
			"from ztables a , rel_user_kpi "+
			"where xcdtb = 'KPI' "+
			"and trim(xcdel) = trim(idkpi) and idusr = '"+session.getAttribute("username")+"' ";
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
		<h3 class="font-thin"><%=propLang.get("pop_us_sel") %> KPI</h3>
	</div>
	<div class="modal-body">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th><%=propLang.get("ala_code") %></th>
			  <th><%=propLang.get("tab_xdtab") %></th>
			  <th></th>
			</tr>
		  </thead>
		  <tbody>
<%
String chk = "";
while (rs.next())
{
%>
			<tr>
			  <td><span class="label bg-light"><%=rs.getString("xcdel").trim() %></span></td>
			  <td><%=rs.getString("xdtab") %><br/><small><%=rs.getString("xdesc") %></small></td>
			  <td><a href="#" class="btn btn-default" onclick="fastKPI('<%=rs.getString("xcdel").trim().toLowerCase() %>','<%=rs.getString("xdtab").trim()%>')">Select KPI</a>
			</tr>
<%
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

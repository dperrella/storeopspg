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

String iddest = request.getParameter("iddest");
iddest = Utility.noNull(iddest);

String dsdest = request.getParameter("dsdest");
dsdest = Utility.noNull(dsdest);


//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
int count = 0;

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnHR();
	st = conn.createStatement();
	
	sql   = "select * from hr_departments where id_customer = "+session.getAttribute("idcus")+" ";
	rs=st.executeQuery(sql);
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.mia2').remove(); $('div.modal-backdrop').remove();">&times;</a>
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
while (rs.next())
{
%>
			<tr>
			<!-- 
			  <td><span class="label bg-light"><%=rs.getString("id") %></span></td>
			-->  
			  <td><%=rs.getString("description") %></td>
			  <td><a href="#" data-dismiss="modal" class="btn btn-default btn-xs"
			  onclick="document.getElementById('<%=iddest %>').value = '<%=rs.getString("id")%>';
			  		   document.getElementById('<%=dsdest %>').value = '<%=rs.getString("description").replaceAll("'","\'") %>';
			  		   $('.mia2').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_dp_sel") %></a></td>
<%
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.mia').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_dp_chiudi") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

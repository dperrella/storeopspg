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


String day = request.getParameter("day");
String idemployee = request.getParameter("idemployee");


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

<form id="frmTrasferta" name="frmTrasferta" method="post">
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
		<div style="overflow:auto; height:250px;">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_dp_filtra") %>">
		
		<input type="hidden" name="ds" id="ds" value=""/>
		
		<table class="popupTable table table-striped b-t b-light">
		  <thead>
			<tr>
			<!-- 
			  <th><%=propLang.get("pop_dp_id") %></th>
			-->  
			  <th></th>
			  <th><%=propLang.get("pop_dp_descrizione") %></th>
			  
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
			  <td><input type="radio" name="iddep" value="<%=rs.getString("id") %>" onclick="$('#ds').val('<%=rs.getString("description").replaceAll("'","\'") %>')"></td>
			  <td><%=rs.getString("description") %></td>
			  
			  <!-- 
			  <td><a href="#" data-dismiss="modal" class="btn btn-default btn-xs"
			  onclick="document.getElementById('<%=iddest %>').value = '<%=rs.getString("id")%>';
			  		   document.getElementById('<%=dsdest %>').value = '<%=rs.getString("description").replaceAll("'","\'") %>';
			  		   $('.mia2').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_dp_sel") %></a></td>
			  -->
<%
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	
	<p>Seleziona il turno per la Trasferta:</p>
	<!-- 
	<div class="radio i-checks"><label><input type="radio" name="turno" value="XXX"><i></i> Intera Giornata</label></div>	
	-->
	<div id="turniii">
		
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();visualizzaTurni('<%=day %>' , '<%=idemployee%>')"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.mia').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_dp_chiudi") %></a>
		<a class="btn btn-default btn-primary btn-lg" data-dismiss="modal" onclick="confirmTrasferta('<%=day %>' , '<%=idemployee%>')">Conferma</a>
	</div>
  </div>
</div>
</form>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

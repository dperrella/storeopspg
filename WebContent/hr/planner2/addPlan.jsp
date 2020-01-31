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

%>
<div class="modal-dialog modal-sm" id="planModal">
	<div class="modal-content">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">&times;</a>
			<h3 class="font-thin"><%=propLang.get("pop_ap_title") %></h3>
		</div>
		<div class="modal-body">
<%
//**********************************************************
//Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String sql = "";

//**********************************************************
//Connessione al DATABASE
try
{
	conn=Utility.getConnHR();
	st = conn.createStatement();
	
	sql   = "select id, description, color, col "+
			"from hr_planification_type ";
	rs=st.executeQuery(sql);
%>
	
			<input type="text" id="modalDay" name="modalDay" value="<%=day%>"/>
			
			<label>Durata:</label><br/>
			<input type="number" class="form-control" placeholder="" id="modalDurata" name="durata" value="1"/>
			
			<div class="line line-dashed b-b line-lg pull-in"></div>
			
			<label>Giustificativo:</label><br/>
			<select class="form-control" name="planification_type" id="modalPlanification_type">
<%
	while(rs.next()) {
%>
				<option value="<%=rs.getString("id") %>"><%=rs.getString("description") %></option>
	
<%
	}
	rs.close();
%>
			</select>
		</div>
		<div class="modal-footer">
			<a class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ba_chiudi") %></a>
			<a class="btn btn-primary btn-lg" onclick="setPlan<%=day %>(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("btnConferma") %></a>
		</div>
  	</div>
</div>
<%
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
	}
	catch(Exception e) {}
}
%>
 
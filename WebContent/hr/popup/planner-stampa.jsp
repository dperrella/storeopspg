<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");
String tp = request.getParameter("tp");
%>

<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin">Stampa Planner</h3>
	</div>
	<div class="modal-body">
		
		<iframe src="planner-stampa.htm?tp=<%=tp%>&id_departments=<%=id_departments%>&year=<%=year%>&week=<%=week%>&day=0" style="border:0px;width:100%"></iframe>
					
	</div>
	<div class="modal-footer">
	    <a class="btn btn-default" data-dismiss="modal" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();"><%=propLang.get("pop_ap_chiudi") %></a>
	</div>
  </div>
</div>

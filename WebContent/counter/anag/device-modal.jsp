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

String id = ""+request.getParameter("id");
id = Utility.noNull(id);
String name = ""+request.getParameter("name");
name = Utility.noNull(name);
%>

<div class="modal-dialog modal-sm">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Device Name</h3>
	</div>
	<div class="modal-body">
		
		<input type="hidden" name="id" id="id" value="<%=id %>"/>
		
		<label>Device name</label><br/>
		<input type="text" name="name" id="name" value="<%=name %>" class="form-control"/>

	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setName(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("btnConferma") %></a>
	</div>
  </div>
</div>


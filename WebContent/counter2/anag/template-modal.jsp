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

String id_customer = ""+request.getParameter("id_customer");
id_customer = Utility.noNull(id_customer);
%>

<div class="modal-dialog modal-sm">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">New Template</h3>
	</div>
	<div class="modal-body">
		
		<input type="hidden" class="form-control" id="id_customer" name="id_customer" value="<%=id_customer%>"/>
		
		<label>Description:</label><br/>
		<input type="text" class="form-control" placeholder="Description" id="description" name="description" value=""/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>Short Description (Displayed):</label><br/>
		<input type="text" class="form-control" placeholder="Short Description (Displayed)" id="shortdescr" name="shortdescr" value=""/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>From Time:</label><br/>
		<input type="time" id="fromhour" class="form-control" placeholder="From Time">
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>To Time:</label><br/>
		<input type="time" id="tohour" class="form-control" placeholder="To Time">
		
			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setTemplate(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("btnConferma") %></a>
	</div>
  </div>
</div>

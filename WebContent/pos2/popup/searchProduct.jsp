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
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("tick_cerc") %></h3>
	</div>
	<div class="modal-body">
		
		
		<label><%=propLang.get("tick_cerc") %></label>
		<input class="form-control" type="text" name="searchFld" id="searchFld" value="" placeholder="Barcode or Description"/>
		
		
	    
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-primary btn-lg" onclick="searchProd()"><%=propLang.get("tick_cerc") %></a>
	</div>
  </div>
</div>

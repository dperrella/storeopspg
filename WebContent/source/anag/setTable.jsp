<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
String tipo = request.getParameter("tipo");
tipo = Utility.noNull(tipo);
tipo=tipo.replaceAll("\\?cd=undefined","");

String tipoForm = request.getParameter("tipoForm");
tipoForm = Utility.noNull(tipoForm);
tipoForm=tipoForm.replaceAll("\\?cd=undefined","");

String key = request.getParameter("key");
key = Utility.noNull(key);
key=key.replaceAll("\\?cd=undefined","");

String value = request.getParameter("value");
value = Utility.noNull(value);
value=value.replaceAll("\\?cd=undefined","");

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Record Detail</h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
		
			<%
			if(tipoForm.trim().equals("D")) {
			%>
			<div class="col-sm-12">
				<div class="alert alert-warning"><button type="button" class="close" data-dismiss="alert">&times;</button>
  				<i class="fa fa-ban-circle"></i><strong>Confirm Delete Record</strong></div>
			</div>
			<%
			}
			%>
	        <div class="col-sm-12">
	          <label>Description</label>
	          <input type="hidden" name="tipo" id="tipo" value="<%=tipo%> ">
	          <input type="hidden" name="tipoForm" id="tipoForm" value="<%=tipoForm%> ">
	          <input type="hidden" name="key" id="key" value="<%=key%> ">
	          <input type="text" id="value" name="value" class="form-control" value="<%=value %>"/>
	        </div>
	    </div>
	   
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-primary btn-lg" data-dismiss="modal" onclick="saveTable()"><%=propLang.get("save") %></a>
	</div>
  </div>
</div>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<form method="post" action="" enctype="multipart/form-data" id="myform">
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Add Category</h3>
	</div>
	<div class="modal-body">
		
		<label>Select Data File</label>
		<input type="file" id="file" name="file" />
		<input type="text" id="___idcus" name="idcus" value=""/>
		<input type="text" id="___category" name="category" value=""/>
		
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-primary btn-lg" onclick="importCsv()">Import Data</a>	
	</div>
  </div>
</div>
</form>
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

String type = request.getParameter("type");
type = type.replaceAll("\\?cd=undefined","");
type = Utility.noNull(type);

%>
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Add Category</h3>
	</div>
	<div class="modal-body">
		
		<label>Category</label>
	    <input type="text" class="form-control" id="category" name="category" value="" placeholder="Category Name"/>
	    
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-primary btn-lg" onclick="add(<%=type%>)">Add Category</a>	
	</div>
  </div>
</div>

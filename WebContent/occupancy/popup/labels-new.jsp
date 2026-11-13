<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<form data-validate="parsley" id="label-form" action="properties.htm" method="post">
<input type="hidden" name="tipoForm" value="G"/>
<input type="hidden" name="azioneLabel" value="Y"/>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Add New Label Group</h3>
	</div>
	<div class="modal-body">
		
		<label>Label Group Name</label>
		<input type="text" class="form-control" name="lang" value=""/>
		
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload=""/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">Close</a>
		<input type="submit" class="btn btn-primary btn-lg" value="Save"/>
	</div>
  </div>
</div>
</form>
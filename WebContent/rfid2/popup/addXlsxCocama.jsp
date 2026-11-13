<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.util.Date"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
String iddoc = request.getParameter("iddoc");
iddoc = iddoc.replaceAll("\\?cd=undefined", "");
%>
<form id="actionForm" action="../../rfid/SetXlsxCocama" enctype="multipart/form-data" method="POST" onsubmit="return chkUploadXlsxCocama()">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Import Xlsx (Cocama)</h3>
	</div>
	<div class="modal-body" id="modalBody">
		
		<input type="file" class="form-control" id="fileToUpload" name="fileToUpload"/>
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		
		<input type="submit" id="xlsxBtn" class="btn btn-primary btn-lg">
	</div>
  </div>
</div>
</form>

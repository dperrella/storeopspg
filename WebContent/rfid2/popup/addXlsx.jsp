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
<form id="actionForm" action="../../rfid/SetXlsx" enctype="multipart/form-data" method="POST" onsubmit="return chkUploadXlsx()">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Import Xlsx (Cegid)</h3>
	</div>
	<div class="modal-body" id="modalBody">
		
		<h4>Document id:</h4>
		
		<input type="text" name="iddoc" id="iddoc" class="form-control" value="<%=iddoc%>" readonly/>
		<input type="hidden" name="idcus" id="idcus" class="form-control" value="<%=session.getAttribute("idcus") %>" readonly/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<input type="file" class="form-control" id="fileToUpload" name="fileToUpload"/>
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		
		<input type="submit" id="xlsxBtn" class="btn btn-primary btn-lg">
	</div>
  </div>
</div>
</form>

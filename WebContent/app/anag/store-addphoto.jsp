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
%>
<!-- 
<form id="actionForm" action="../../ats/SetPhotoWeb" enctype="multipart/form-data" method="POST">
-->
<form id="actionForm" action="../../SetPhotoStore" enctype="multipart/form-data" method="POST">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Add Store Image</h3>
	</div>
	<div class="modal-body" id="modalBody">
		
		<h4>Store <%=request.getParameter("storeCode").replaceAll("\\?cd=undefined","") %> - <%=request.getParameter("storeName").replaceAll("\\?cd=undefined","") %></h4>
		
		<input type="text" name="idstore" id="idstore" class="form-control" value="<%=request.getParameter("idstore").replaceAll("\\?cd=undefined","") %>" readonly/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>Note</label><br/>
		<textarea style="height:100px" name="note" id="imgnote" class="form-control"></textarea>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<input type="file" class="form-control" id="fileToUpload" name="fileToUpload"/>
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		
		<input type="submit" onclick="showLoader2()" class="btn btn-primary btn-lg">
	</div>
  </div>
</div>
</form>

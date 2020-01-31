<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<form id="actionForm" action="sites-location-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
<input type="text" name="idsite" value="${idsite}">


<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Sites Location</h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("sit_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Description</label>
	          <input type="text" class="form-control" name="description" id="description"
	          	value="${description}" data-required="true"/>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Location Type</label>
	          <select name="id_locationtype" id="id_locationtype" 
              	class="form-control ss" data-required="true">
                <option value="">-</option>
                <c:forEach items="${loctype}" var="loctype" varStatus="status">
				<option value="${loctype.idLocationtype}">${loctype.description}</option>
				</c:forEach>
              </select>
              <script>
              	$('#id_locationtype').val('${id_locationtype}');
           	  </script>
	        </div>
	    </div>
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

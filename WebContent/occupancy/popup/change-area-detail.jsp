<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<form id="actionForm" action="stores-area-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="M">
<input type="hidden" name="idstore" value="${idstore}">

<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Change Area Detail</h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <input type="hidden" id="idarea" name="idarea" class="form-control" value="${idarea}" readonly>   
        <input type="hidden" id="idstore" name="idstore" class="form-control" value="${idstore}">   
        <input type="hidden" id="storeCode" name="storeCode" class="form-control" value="${storeCode}">   
        <input type="hidden" id="storeCodeEncoded" name="storeCodeEncoded" class="form-control" value="${storeCodeEncoded}">   
        	<input type="hidden" id="description" name="description" 
         	class="form-control" value="${description}" readonly>   
   
	    <div id="firmwaremsg"><div class="alert alert-warning">Firmware Version must be >= 1.4.2_Test/Release</div></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Firmware Version</label>
	          <input type="text" id="firmware" name="firmware" 
	          	class="form-control" value="" readonly>  
	        </div>
	    </div>
	    
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <iframe src="../main/widget-medium.jsp?key=${license}" width="100%" height="520px" frameBorder="0">Browser not compatible.</iframe>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix" >
	        <div class="col-sm-6" id="directionForm">
	          <label>Direction</label>
	          <select class="form-control" id="direction" name="direction" data-required="true">
	          	<option value="">Select Direction</option>
	          	<option value="IN">In</option>
	          	<option value="OUT">Out</option>
	          </select>
	          <span class="text-danger" id="directionErr"></span>
	        </div>
	        
	        <div class="col-sm-6" id="valueForm">
	          <label>Value</label>
	          <input type="number" id="value" name="value" min="0"
	          	class="form-control" value="" data-required="true">   
	          <span class="text-danger" id="valueErr"></span>
	        </div>
	    </div>
	    
	    
	    
	</div>
	<div class="modal-footer">
		
		<img src="../../images/spacer.gif" onload="parsleyForm();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">Close</a>
		<a class="btn btn-primary btn-lg" id="confirm" disabled onclick="sendOccupancy();">Confirm</a>
	</div>
  </div>
</div>
</form>	

<script>

readFirebase('${storeCode}');
</script>

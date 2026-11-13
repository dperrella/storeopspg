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

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Change Area Detail</h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label>Id</label>
	          <input type="text" id="idarea" name="idarea" class="form-control" value="${idarea}" readonly>   
	          <input type="hidden" id="idstore" name="idstore" class="form-control" value="${idstore}">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
        		
		        
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Description</label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Warning Percentage</label>
	          <input type="text" id="percWarning" name="percWarning" 
	          	class="form-control parsley-validated" value="${percWarning}" data-required="true" data-min="1" data-max="100">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Threshold</label>
	          <input type="number" id="threshold" name="threshold" 
	          	class="form-control" value="${threshold}" data-type="number" data-required="true">   
	        </div>
	    </div>
	    
	    
	    
	    
	</div>
	<div class="modal-footer">
		
		<img src="../../images/spacer.gif" onload="parsleyForm();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">Close</a>
		<input type="submit" id="mybtn" class="btn btn-primary btn-lg" value="Confirm">	
	</div>
  </div>
</div>
</form>	

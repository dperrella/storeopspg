<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<form data-validate="parsley" id="label-form" action="properties.htm" method="post">
<input type="hidden" name="tipoForm" value="${tipoForm}"/>
<input type="hidden" name="azioneLabelGroup" value="Y"/>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Edit Labels for Group ${lang}</h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        
		<c:forEach items="${l}" var="l" varStatus="status">
	                        	
       	<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
		      	<label>${l.description}</label>
	          	<div>
	          		<input type="hidden" name="key" value="${l.key}"/>
				  	<input type="hidden" name="description" value="${l.description}"/>
				  	<input type="hidden" name="tile" value="${l.tile}"/>
				  	<input type="hidden" name="lang" value="${l.lang}"/>
				  	<input type="text" name="val" id="${l.key}" 
				  	class="form-control" value="${l.value}"/>
				</div>
			</div>
	    </div>
	                   
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		</c:forEach>
		
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload=""/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">Close</a>
		<input type="submit" class="btn btn-primary btn-lg" value="Save"/>
	</div>
  </div>
</div>
</form>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String rid = request.getParameter("rid");
%>
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin">EDIT Certificate</h3>
      </div>
      <div class="modal-body">
      	    <Label>Id</Label>
      	    <input type="text" class="form-control" id="rid" name="rid" value="<%=rid %>" readonly/>      
			<div class="line line-dashed b-b line-lg pull-in"></div>
			<label>Name:</label>	  
      	    <input type="text" class="form-control" id="name" name="name" value=""/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary btn-lg" onclick="editAction()">Confirm</button>
        
      </div>
    </div>
</div>
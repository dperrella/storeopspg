<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<form id="actionForm" action="stores-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="M">
<input type="hidden" name="idstore" value="${idstore}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Change Store Opening Hours</h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
        	<div class="col-sm-6">
	    		<div class="panel-footer bg-light">
		        	<div class="padder">
		            	<span class="h4 block">Store Open</span>
		            </div>
		        </div>
		    </div>
		    
		    <div class="col-sm-6">
	    		<div class="panel-footer bg-light">
		        	<div class="padder">
		            	<span class="h4 block">Store Close</span>
		            </div>
		        </div>
		    </div>
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		        				
		        
	    <div class="form-group pull-in clearfix">
	    	
	        <div class="col-sm-3">
	          <label>Hours</label>
	          <select name="hhopen" id="hhopen" class="ss form-control">
   		        <%
	          	for(int i=0;i<24;i++) {
	          	%>
	          	<option value="<%=String.format("%02d", i+1) %>"><%=String.format("%02d", i+1)  %></option>
			  	<%
	          	}
	          	%>
	          </select>
	          <script>
	          $('#hhopen').val('${hh1}');
	          </script>
	        </div>
	        <div class="col-sm-3">
	          <label>Minutes</label>
	          <select name="mmopen" id="mmopen" class="ss form-control">
   		        <%
	          	for(int i=0;i<60;i++) {
	          	%>
	          	<option value="<%=String.format("%02d", i) %>"><%=String.format("%02d", i)  %></option>
			  	<%
	          	}
	          	%>
	          </select>
	          <script>
	          $('#mmopen').val('${mm1}');
	          </script>
	        </div>
	        
	        <div class="col-sm-3">
	          <label>Hours</label>
	          <select name="hhclose" id="hhclose" class="ss form-control">
   		        <%
	          	for(int i=0;i<24;i++) {
	          	%>
	          	<option value="<%=String.format("%02d", i) %>"><%=String.format("%02d", i)  %></option>
			  	<%
	          	}
	          	%>
	          </select>
	          <script>
	          $('#hhclose').val('${hh2}');
	          </script>
	        </div>
	        <div class="col-sm-3">
	          <label>Minutes</label>
	          <select name="mmclose" id="mmclose" class="ss form-control">
   		        <%
	          	for(int i=0;i<60;i++) {
	          	%>
	          	<option value="<%=String.format("%02d", i) %>"><%=String.format("%02d", i)  %></option>
			  	<%
	          	}
	          	%>
	          </select>
	          <script>
	          $('#mmclose').val('${mm2}');
	          </script>
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
	

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
<form id="actionForm" action="printers-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("pri_dsfun") %></h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("pri_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("pri_descrizione") %></label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-8">
	          <label><%=propLang.get("pri_ip") %></label>
	          <input type="text" id="ip" name="ip" 
	          	class="form-control" value="${ip}">   
	        </div>
	        
	        <div class="col-sm-4">
	          <label><%=propLang.get("pri_port") %></label>
	          <input type="text" id="port" name="port" 
	          	class="form-control" value="${port}">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <div class="checkbox i-checks">
                <label>
                  <input id="enabled" name="enabled" type="checkbox" ><i></i> <%=propLang.get("pri_enabled") %>
                </label>
              </div>
              <script>
              if('${enabled}'=='true') {
              	$('#enabled').prop('checked', true);
              }
              </script>
	        </div>
	    </div>
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

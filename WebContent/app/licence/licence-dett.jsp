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
<form id="actionForm" action="licence-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Licence</h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>App</label>
	          <select name=idapp id="idapp" 
              	class="form-control ss" data-required="true">
              	<option value="">-</option>
                <c:forEach items="${mobapp}" var="mobapp" varStatus="status">
				<option value="${mobapp.id}">${mobapp.name}</option>
				</c:forEach>
              </select>
	        </div>
	    </div>
	    
	    <script>
	    $('#idapp').val('${idapp}');
	    </script>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
        
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Customer</label>
	          <select name=idcustomer id="idcustomer" 
              	class="form-control ss" data-required="true">
              	<option value="">-</option>
                <c:forEach items="${cus}" var="cus" varStatus="status">
				<option value="${cus.xcdel}">${cus.cmp1} ${cus.xdtab}</option>
				</c:forEach>
              </select>
	        </div>
	    </div>
	    
	    <script>
	    $('#idcustomer').val('${idcustomer}');
	    </script>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Mac Address</label>
	          <input type="text" id="macaddr" name="macaddr" class="form-control" value="${macaddr}" data-required="true">   
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

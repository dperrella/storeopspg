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
<form id="actionForm" action="step-type-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("stt_dsfun") %></h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("stt_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("stt_descrizione") %></label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("stt_doctype") %></label>
	          <select name="id_doctype" id="id_doctype" data-required="true"
              	class="form-control ss">
                <option value="">-</option>
                <c:forEach items="${doctype}" var="doctype" varStatus="status">
				<option value="${doctype.idDoctype}">${doctype.description}</option>
				</c:forEach>
              </select>
              
              <script>
              	$('#id_doctype').val('${id_doctype}');
           	  </script>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("stt_doctype") %> [2]</label>
	          <select name="id_doctype2" id="id_doctype2"
              	class="form-control ss">
                <option value="-1">-</option>
                <c:forEach items="${doctype}" var="doctype" varStatus="status">
				<option value="${doctype.idDoctype}">${doctype.description}</option>
				</c:forEach>
              </select>
              
              <script>
              	$('#id_doctype2').val('${id_doctype2}');
           	  </script>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Power</label>
	          <select name="power" id="power"
              	class="form-control ss">
                <option value="-1">-</option>
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="30">30</option>
                <option value="40">40</option>
                <option value="50">50</option>
				<option value="80">80</option>
				<option value="120">120</option>
				<option value="150">150</option>
				<option value="180">180</option>
				<option value="210">210</option>
				<option value="240">240</option>
				<option value="270">270</option>
				<option value="300">300</option>
			  </select>
              
              <script>
              	$('#power').val('${power}');
           	  </script>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Step is Inventory</label>
	          <select name="inventory" id="inventory"
              	class="form-control ss">
                <option value="false">NO</option>
                <option value="true">YES</option>
              </select>
              
              <script>
              	$('#inventory').val('${inventory}');
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

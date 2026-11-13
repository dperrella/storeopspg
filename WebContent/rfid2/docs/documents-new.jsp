<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<form id="actionForm" name="actionForm" action="documents-create.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="I">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">New Document</h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	        	<label>Document Reference</label>
				<input type="text" class="form-control" name="doc_ref" id="doc_ref" value="" data-required="true"/>
			</div>
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("sit_dsfun") %></label>
	          <select name="sites" id="sites" 
              	class="form-control ss" data-required="true">
                <option value="">-</option>
                <c:forEach items="${sites}" var="sites" varStatus="status">
				<option value="${sites.idSite}">${sites.description}</option>
				</c:forEach>
              </select>
            </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Destination <%=propLang.get("sit_dsfun") %> (Optional)</label>
	          <select name="sites_dest" id="sites_dest" 
              	class="form-control ss">
                <option value="-1">-</option>
                <c:forEach items="${sites}" var="sites" varStatus="status">
				<option value="${sites.idSite}">${sites.description}</option>
				</c:forEach>
              </select>
            </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Steps</label>
	          <select name="steps" id="steps" 
              	class="form-control ss" data-required="true">
                <option value="">-</option>
                <c:forEach items="${steps}" var="steps" varStatus="status">
				<option value="${steps.idStep}">${steps.description}</option>
				</c:forEach>
              </select>
            </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	   
	   	<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <div class="checkbox i-checks">
                <label>
                  <input id="filter_type" name="filter_type" value="2" type="checkbox"><i></i> Enable Expected Filter
                </label>
              </div>
              
	        </div>
	    </div>
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<input type="submit" name="Next" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>
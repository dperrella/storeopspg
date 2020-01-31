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
String table="";
%>
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Document Print</h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	        	<label>ID DOC</label>
				<input type="text" class="form-control" name="iddoc" id="iddoc" value="${iddoc}" readonly/>
			</div>
			
			<div class="col-sm-4">
	        	<label>SKU</label>
				<input type="text" class="form-control" name="sku" id="sku" value="${sku}" readonly/>
			</div>
			
			<div class="col-sm-4">
	        	<label>QTY</label>
				<input type="text" class="form-control" name="qty" id="qty" value="${qty}" />
			</div>
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("pri_dsfun") %></label>
	          <select name="idprinter" id="idprinter" 
              	class="form-control ss" data-required="true">
                <option value="">-</option>
                <c:forEach items="${printers}" var="printers" varStatus="status">
				<option value="${printers.id}">${printers.description}</option>
				</c:forEach>
              </select>
            </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("prt_dsfun") %></label>
	          <select name="idtemplate" id="idtemplate" 
              	class="form-control ss" data-required="true">
                <option value="">-</option>
                <c:forEach items="${templates}" var="templates" varStatus="status">
				<option value="${templates.id}">${templates.name}</option>
				</c:forEach>
              </select>
            </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-6">
	          <div class="checkbox i-checks">
                <label>
                  <input id="zplfile" name="zplfile" type="checkbox" value="Y">
                  <i></i> Generate ZPL and Download File
                </label>
              </div>
            </div>
            
            <div class="col-sm-6">
	          <div class="checkbox i-checks">
                <label>
                  <input id="zplbrowser" name="zplbrowser" type="checkbox" value="Y">
                  <i></i> Generate ZPL and Print from Browser
                </label>
              </div>
            </div>
	    </div>
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-primary btn-lg" onclick="stampaBarcode()"><%=propLang.get("btnStampa") %></a>
	</div>
  </div>
</div>

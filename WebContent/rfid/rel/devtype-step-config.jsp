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
<form id="actionForm" action="rel_dev_step.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="M">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("rty_new") %></h3>
	</div>
	<div class="modal-body">
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label>Device Type</label>
	          <input type="text" id="id_devicetype" name="id_devicetype" class="form-control" value="${id_devicetype}" readonly>   
	        </div>
	        
	        <div class="col-sm-4">
	          <label>Flow</label>
	          <input type="text" id="idflow" name="idflow" class="form-control" value="${idflow}" readonly>   
	        </div>
	        
	        <div class="col-sm-4">
	          <label>Step</label>
	          <input type="text" id="idstep" name="idstep" class="form-control" value="${idstep}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("config") %> <a href="#" onclick="addRowConfig('config')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="config">
                	<thead>
                		<tr>
                			<th style="width:20%"><%=propLang.get("extra_name") %></th>
                			<th style="width:20%"><%=propLang.get("extra_type") %></th>
                			<th style="width:50%"><%=propLang.get("extra_value") %> (Default)</th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${config}" var="config" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${config.cmp3}config_nrrig" name="config_nrrig" value="${config.cmp3}" class="idrow">
               					<input type="text" id="${config.cmp3}config_name" name="config_name" value="${config.name}" class="form-control" readonly/>
               				</td>
               				<td><select id="${config.cmp3}config_type_vid" name="config_type_vid" class="form-control" disabled>
               					<option value="text" ${config.cmp1}>Text</option>
               					<option value="number" ${config.cmp2}>Number</option>
               				</select>
               				<input type="hidden" id="${config.cmp3}config_type" name="config_type" value="${config.type}"></td>
               				<td><input type="text" id="${config.cmp3}config_value" name="config_value" value="${config.value}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
			</div>
		
		</div>	
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

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
<form id="actionForm" action="location-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("loc_new") %></h3>
	</div>
	<div class="modal-body">
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("loc_id") %></label>
	          <input type="text" id="idlocation" name="idlocation" class="form-control" value="${idlocation}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("loc_descrizione") %></label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("rlt_dsfun") %></label>
	          <select class="form-control" name="id_loctype" id="id_loctype">
	          	<option value="">-</option>
	          	<c:forEach items="${loctype}" var="loctype" varStatus="status">
              	<option value="${loctype.id}">${loctype.description}</option>
              	</c:forEach>
              </select>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("loc_id_warehouse") %></label>
	          <select class="form-control" name="id_warehouse" id="id_warehouse">
	          	<option value="">-</option>
	          	<c:forEach items="${war}" var="war" varStatus="status">
              	<option value="${war.idwarehouse}">${war.description}</option>
              	</c:forEach>
              </select>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("extra") %> <a href="#" onclick="addRow('extra')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="extra">
                	<thead>
                		<tr>
                			<th style="width:20%"><%=propLang.get("extra_name") %></th>
                			<th style="width:20%"><%=propLang.get("extra_type") %></th>
                			<th style="width:50%"><%=propLang.get("extra_value") %></th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${extra}" var="extra" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${extra.cmp3}nrrig" name="nrrig" value="${extra.cmp3}" class="idrow">
               					<input type="text" id="${extra.cmp3}extra_name" name="extra_name" value="${extra.name}" class="form-control" readonly/>
               				</td>
               				<td><select id="${extra.cmp3}extra_type_vid" name="extra_type_vid" class="form-control" disabled>
               					<option value="text" ${extra.cmp1}>Text</option>
               					<option value="number" ${extra.cmp2}>Number</option>
               				</select>
               				<input type="hidden" id="${extra.cmp3}extra_type" name="extra_type" value="${extra.type}"></td>
               				<td><input type="text" id="${extra.cmp3}extra_value" name="extra_value" value="${extra.value}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
			</div>
		
		</div>					
					
        
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();setIdwarehouse('${id_warehouse}');setIdloctype('${id_loctype}') "/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

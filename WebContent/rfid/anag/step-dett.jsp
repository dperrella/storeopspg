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
<form id="actionForm" action="flow-step.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("flo_new") %></h3>
	</div>
	<div class="modal-body">
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("flo_idflow") %></label>
	          <input type="text" id="idflow" name="idflow" class="form-control" value="${idflow}" readonly>   
	        </div>
	        
	        <div class="col-sm-4">
	          <label><%=propLang.get("flo_idstep") %></label>
	          <input type="text" id="idstep" name="idstep" class="form-control" value="${idstep}" readonly>   
	        </div>
	        
	        <div class="col-sm-4">
	          <label><%=propLang.get("flo_levelnum") %></label>
	          <input type="text" id="levelnum" name="levelnum" class="form-control" data-type="number" data-required="true" value="${levelnum}" >   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("flo_descrizione") %></label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("origin_location") %> in JSON</label>
	          <textarea id="origin_location" name="origin_location" style="height:100px"
	          	class="form-control">${origin_location}</textarea>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("destination_location") %> in JSON</label>
	          <textarea id="destination_location" name="destination_location" style="height:100px"
	          	class="form-control">${destination_location}</textarea>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("config") %> BEFORE <a href="#" onclick="addRowConfig('config_before', 'before_')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="config_before">
                	<thead>
                		<tr>
                			<th style="width:20%"><%=propLang.get("extra_name") %></th>
                			<th style="width:20%"><%=propLang.get("extra_type") %></th>
                			<th style="width:50%"><%=propLang.get("extra_value") %> (Default)</th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${config_before}" var="config_before" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${config_before.cmp3}before_config_nrrig" name="before_config_nrrig" value="${config_before.cmp3}" class="idrow">
               					<input type="text" id="${config_before.cmp3}before_config_name" name="before_config_name" value="${config_before.name}" class="form-control" readonly/>
               				</td>
               				<td><select id="${config_before.cmp3}before_config_type_vid" name="before_config_type_vid" class="form-control" disabled>
               					<option value="text" ${config_before.cmp1}>Text</option>
               					<option value="number" ${config_before.cmp2}>Number</option>
               				</select>
               				<input type="hidden" id="${config_before.cmp3}before_config_type" name="before_config_type" value="${config_before.type}"></td>
               				<td><input type="text" id="${config_before.cmp3}before_config_value" name="before_config_value" value="${config_before.value}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
			</div>
		
		</div>	
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("config") %> DURING <a href="#" onclick="addRowConfig('config_during', 'during_')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="config_during">
                	<thead>
                		<tr>
                			<th style="width:20%"><%=propLang.get("extra_name") %></th>
                			<th style="width:20%"><%=propLang.get("extra_type") %></th>
                			<th style="width:50%"><%=propLang.get("extra_value") %> (Default)</th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${config_during}" var="config_during" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${config_during.cmp3}during_config_nrrig" name="during_config_nrrig" value="${config_during.cmp3}" class="idrow">
               					<input type="text" id="${config_during.cmp3}during_config_name" name="during_config_name" value="${config_during.name}" class="form-control" readonly/>
               				</td>
               				<td><select id="${config_during.cmp3}during_config_type_vid" name="during_config_type_vid" class="form-control" disabled>
               					<option value="text" ${config_during.cmp1}>Text</option>
               					<option value="number" ${config_during.cmp2}>Number</option>
               				</select>
               				<input type="hidden" id="${config_during.cmp3}during_config_type" name="during_config_type" value="${config_during.type}"></td>
               				<td><input type="text" id="${config_during.cmp3}during_config_value" name="during_config_value" value="${config_during.value}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
			</div>
		
		</div>	
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("config") %> AFTER <a href="#" onclick="addRowConfig('config_after', 'after_')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="config_after">
                	<thead>
                		<tr>
                			<th style="width:20%"><%=propLang.get("extra_name") %></th>
                			<th style="width:20%"><%=propLang.get("extra_type") %></th>
                			<th style="width:50%"><%=propLang.get("extra_value") %> (Default)</th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${config_after}" var="config_after" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${config_after.cmp3}after_config_nrrig" name="after_config_nrrig" value="${config_after.cmp3}" class="idrow">
               					<input type="text" id="${config_after.cmp3}after_config_name" name="after_config_name" value="${config_after.name}" class="form-control" readonly/>
               				</td>
               				<td><select id="${config_after.cmp3}after_config_type_vid" name="after_config_type_vid" class="form-control" disabled>
               					<option value="text" ${config_after.cmp1}>Text</option>
               					<option value="number" ${config_after.cmp2}>Number</option>
               				</select>
               				<input type="hidden" id="${config_after.cmp3}after_config_type" name="after_config_type" value="${config_after.type}"></td>
               				<td><input type="text" id="${config_after.cmp3}after_config_value" name="after_config_value" value="${config_after.value}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
			</div>
		
		</div>	
	    
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <div class="checkbox i-checks">
                <label>
                  <input type="checkbox" id="enabled" name="enabled" value="t" ${check}><i></i> <%=propLang.get("flo_enabled") %>
                </label>
              </div> 
	        </div>
	    </div>
	    
	    
	    
	    
	    
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("flo_extra_doc_top") %> <a href="#" onclick="addRow_prefix('extra_doc_top', '_doc_top_')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="extra_doc_top">
                	<thead>
                		<tr>
                			<th style="width:20%"><%=propLang.get("extra_name") %></th>
                			<th style="width:20%"><%=propLang.get("extra_type") %></th>
                			<th style="width:50%"><%=propLang.get("extra_value") %></th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${extra_doc_top}" var="extra" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${extra.cmp3}nrrig_doc_top_" name="nrrig_doc_top_" value="${extra.cmp3}" class="idrow">
               					<input type="text" id="${extra.cmp3}extra_doc_top_name" name="extra_doc_top_name" value="${extra.name}" class="form-control" readonly/>
               				</td>
               				<td><select id="${extra.cmp3}extra__doc_top_type_vid" name="extra_doc_top_type_vid" class="form-control" disabled>
               					<option value="text" ${extra.cmp1}>Text</option>
               					<option value="number" ${extra.cmp2}>Number</option>
               				</select>
               				<input type="hidden" id="${extra.cmp3}extra_doc_top_type" name="extra_doc_top_type" value="${extra.type}"></td>
               				<td><input type="text" id="${extra.cmp3}extra_doc_top_value" name="extra_doc_top_value" value="${extra.value}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
			</div>
		
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("flo_extra_doc_detail") %> <a href="#" onclick="addRow_prefix('extra_doc_detail', '_doc_detail_')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="extra_doc_detail">
                	<thead>
                		<tr>
                			<th style="width:20%"><%=propLang.get("extra_name") %></th>
                			<th style="width:20%"><%=propLang.get("extra_type") %></th>
                			<th style="width:50%"><%=propLang.get("extra_value") %></th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${extra_doc_detail}" var="extra" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${extra.cmp3}nrrig_doc_detail_" name="nrrig_doc_detail_" value="${extra.cmp3}" class="idrow">
               					<input type="text" id="${extra.cmp3}extra_doc_detail_name" name="extra_doc_detail_name" value="${extra.name}" class="form-control" readonly/>
               				</td>
               				<td><select id="${extra.cmp3}extra_doc_detail_type_vid" name="extra_doc_detail_type_vid" class="form-control" disabled>
               					<option value="text" ${extra.cmp1}>Text</option>
               					<option value="number" ${extra.cmp2}>Number</option>
               				</select>
               				<input type="hidden" id="${extra.cmp3}extra_doc_detail_type" name="extra_doc_detail_type" value="${extra.type}"></td>
               				<td><input type="text" id="${extra.cmp3}extra_doc_detail_value" name="extra_doc_detail_value" value="${extra.value}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
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
		<img src="../../images/spacer.gif" onload="parsleyForm();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>
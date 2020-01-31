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
<form id="actionForm" action="flow-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("flw_dsfun") %></h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("flw_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("flw_descrizione") %></label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block">Step <a href="#" onclick="addRow('config')" class="pull-right"><i class="icon-plus"></i></a></span>
             </div>
        </div>
        						
		<div class="form-group pull-in clearfix">
			
			<div class="col-sm-12">
            	<table class="table" id="config">
                	<thead>
                		<tr>
                			<th style="width:20%">Step Type</th>
                			<th style="width:20%">Interface</th>
                			<th style="width:30%">Description</th>
                			<th style="width:15%">Can Create Doc</th>
                			<th style="width:15%">Color</th>
                			<th></th>
                		</tr>
                	</thead>
                	<tbody>
                		<c:forEach items="${step}" var="step" varStatus="status">
                		<tr>
               				<td><input type="hidden" id="${step.count}nrrig" name="nrrig" value="${step.count}" class="idrow">
               					<input type="hidden" id="${step.count}idstep" name="idstep" value="${step.idStep}">
               					<select name="steptype" id="${step.count}steptype" class="form-control ss">
               						<option value="">-</option>
               						<c:forEach items="${steptype}" var="steptype" varStatus="status">
               						<option value="${steptype.idStepType}">${steptype.description}</option>
               						</c:forEach>
               					</select>
               					<script>
               					$('#${step.count}steptype').val('${step.idStepType}')
               					</script>
               				</td>
               				<td><select name="interface" id="${step.count}interface" class="form-control ss">
               						<option value="">-</option>
               						<c:forEach items="${in}" var="in" varStatus="status">
               						<option value="${in.idInterface}">${in.description}</option>
               						</c:forEach>
               					</select>
               					<script>
               					$('#${step.count}interface').val('${step.idInterface}')
               					</script>
               					</td>
               				<td><input type="text" id="${step.count}description" name="stepdescription" value="${step.description}" class="form-control"/></td>
               				<td><div class="checkbox i-checks">
				                <label>
				                  <input id="${step.count}enabled" name="${step.count}enabled" type="checkbox" ><i></i>
				                </label>
				              </div>
				              <script>
				                //alert('${step.canCreateDoc}');
				                if('${step.canCreateDoc}'=='true') {
				                	$('#${step.count}enabled').prop('checked', true);
				                }
				              </script>
				              </td>
				            <td><input type="text" id="${step.count}color" name="color" value="${step.color}" class="form-control"/></td>
               				<td><span onclick="$(this).parents('tr').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>
               			</tr>
               			</c:forEach>
               		</tbody>
               	</table>
			</div>
		
		</div>	
	    	
        
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

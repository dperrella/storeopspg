<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<form id="actionForm" action="aree-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Area Detail</h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label>Id</label>
	          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
	        </div>
	        
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Description</label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Warning Percentage</label>
	          <input type="text" id="percWarning" name="percWarning" 
	          	class="form-control parsley-validated" value="${percWarning}" data-required="true" data-min="1" data-max="100">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Threshold</label>
	          <input type="number" id="threshold" name="threshold" 
	          	class="form-control" value="${threshold}" data-type="number" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	    	<div class="col-sm-6">
	          <div class="checkbox i-checks">
                  <label>
                    <input id="sub_area" name="sub_area" type="checkbox" value="true" ${chk2}>
                    <i></i>
                  </label>
                  Is Sub Area
              </div>  
	        </div>
	        
	        <div class="col-sm-6">
	          <div class="checkbox i-checks">
                  <label>
                    <input id="zone" name="zone" type="checkbox" value="true" ${chk3} onclick="showAlert($(this));">
                    <i></i>
                  </label>
                  Is Zone Area
              </div> 
              <div class="alert alert-info" id="zoneInfo">Data collection (Count Data and Zone Data) will be scheduled</div> 
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	    	<div class="col-sm-6">
	        	<div class="checkbox i-checks">
                  <label>
                    <input id="ml" name="ml" type="checkbox" value="true" disabled>
                    <i></i>
                  </label>
                  Enable Machine Learning for this Area
                  <br/>* NOT Available in Cloud
              </div> 
            </div>
	        <div class="col-sm-6">
	        	<div class="checkbox i-checks">
                  <label>
                    <input id="sm" name="sm" type="checkbox" value="true" disabled>
                    <i></i>
                  </label>
                  Enable Staff Management for this Area
                  <br/>* NOT Available in Cloud
              </div> 
            </div>
        </div>
	        
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    
	    <div class="panel-footer bg-light">
	    	<div class="padder">
	        	<span class="h4 block">Relay Config
	        	<a href="#" onclick="addIp()" class="btn btn-default btn-xs m-l-sm pull-right" id="addIp">
                    <i class="icon-plus"></i>
                    Add Relay Ip
                </a>
	        	</span>
	        </div>
	    </div>
	    <br/>
	    
	    <div class="form-group pull-in clearfix">
	    	<div class="col-sm-12">
	          <div class="checkbox i-checks">
                  <label>
                    <input id="use_relay" name="use_relay" type="checkbox" value="true" ${chk}>
                    <i></i>
                  </label>
                  Use Web Relay
              </div>  
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Relay Type</label>
	          <select class="form-control" id="relay_type" name="relay_type" onchange="chkCommands(this.value)">
	          	<option value="1">Shelly Web Relay</option>
	          	<option value="2">Teracom TCW122B-CM Remote I/O Module</option>
	          </select>
	          <script>
	          $('#relay_type').val('${relayType}');
	          </script>
	        </div>
	        
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div id="ipr">
	    <c:forEach items="${ipr}" var="ipr" varStatus="status">
	    	<div class="ipr">
	    	<div class="form-group pull-in clearfix">
		        <div class="col-sm-10">
		          <label>IP Web Relay</label>
		          <input type="text" id="iprelay" name="iprelay" 
		          	class="_ipr form-control parsley-validated" value="${ipr}">   
		        </div>
		        <div class="_ipr col-sm-2 pull-right" align="right">
		        	<label>&nbsp;</label>
		        	<div id="btndel"><span onclick="if(confirm('Confirm Delete Ip?')){deleteIp(this);}" class="btn btn-danger"><i class="icon-close"></i></span></div>
		        </div>
		    </div>
		    <div class="line line-dashed b-b line-lg pull-in"></div>
			</div>
			
	    </c:forEach>
	   	</div>
	    
	    
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Relay Command</label>
	          <select class="form-control" id="relay_cmd" name="relay_cmd">
	          	<option value="on">Send "ON" Command on Threshold Reached</option>
	          	<option value="off">Send "OFF" Command on Threshold Reached</option>
	          	<option value="tl">Traffic Light</option>
	          </select>
	          <script>
	          $('#relay_cmd').val('${relayCmd}');
	          </script>
	        </div>
	        
	    </div>
	    
	</div>
	<div class="modal-footer">
		<input type="hidden" id="normalColor" name="normalColor" value="${normalColor}">   
		<input type="hidden" id="warningColor" name="warningColor" value="${warningColor}">
		<input type="hidden" id="overthresholdColor" name="overthresholdColor" value="${overthresholdColor}" >
		
		<img src="../../images/spacer.gif" onload="parsleyForm();relay()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">Close</a>
		<input type="submit" class="btn btn-primary btn-lg" value="Confirm">	
	</div>
  </div>
</div>
<script>
var chk3 = '${chk3}';
if(chk3=='checked') {
	$('#zoneInfo').show();
}
else {
	$('#zoneInfo').hide();
} 
	

function showAlert(t) {
	if (t.is(':checked')) {
		$('#zoneInfo').show();
	}
	else {
		$('#zoneInfo').hide();
	}
}
</script>
</form>

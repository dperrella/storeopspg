<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.util.Date"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>

<form id="actionForm"  method="post">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("aud_all") %></h3>
	</div>
	<div class="modal-body" id="modalBody" >
		
	<div id="myBody">
	 			<div class="form-group pull-in clearfix" id="idDiv">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aud_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" readonly>   
	        </div>
	    </div>
	    <div class="line line-dashed b-b line-lg pull-in" id="idDashed"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aud_name") %></label>
	          <input type="text" id="name" name="name" 
	          	class="form-control myCheck" data-required="true">   
	        </div>
	    </div>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
        <div class="spinner" id="loadTypes">
        	<label><%=propLang.get("aud_type") %></label>
        	<div class="form-group pull-in clearfix">
        		<div class="col-sm-12 mySDiv">
					  <select id="idtype" name="idtype" class="form-control ss" data-required="true">
		         	  </select>
		        </div>
   			</div>
   		</div>
   			 	    
	   <div class="line line-dashed b-b line-lg pull-in" id="crDateDashed"></div>
	    <div class="form-group pull-in clearfix" id="crDateDiv">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aud_cr_date") %></label>
			   <input type="text" id="creationdate" name="creationdate" 
	          	class="form-control" data-required="true" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aud_cl_date") %></label>
			   <input type="text" id="closingdate" name="closingdate" 
	          	class="form-control" data-required="true">   
	        </div>
	    </div>
        
           <div class="line line-dashed b-b line-lg pull-in"></div>
			<div class="spinner" id="loadCustomer">
				<label><%=propLang.get("cus_dsfun") %></label>
				<div class="form-group pull-in clearfix">
					<div class="col-sm-12 mySDiv">
						<select id="idcustomer" name="idcustomer" class="form-control ss" data-required="true">
						</select>
					</div>
				</div>
			</div> 	  
   		
           <div class="line line-dashed b-b line-lg pull-in"></div>
        	<div class="spinner" id="loadStores">
	        	<label><%=propLang.get("aud_store") %></label>
	        	<div class="form-group pull-in clearfix">
	        		<div class="col-sm-12 mySDiv">
	        			
						  <select id="idstore" name="idstore" class="form-control ss" data-required="true">
			         	  </select>
			        </div>
	   			 </div>
   			</div>	
        
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	   			
		<div class="spinner" id="loadItems">
    		<label><%=propLang.get("itm_all") %></label>
    		<div class="form-group pull-in clearfix">
    			<div class="col-sm-12 mySDiv">
	  				<select id="iditem" name="iditem" class="form-control ss" data-required="true" multiple>
       	  			</select>
       	  		</div>
			</div>
		</div>
	</div>
</div>    
	   
	
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<button type="button" class="btn btn-primary btn-lg" id="saveButton"><%=propLang.get("pop_ap_conferma") %></button>	
	</div>
	</div>
</div>
</form>
<!-- 	          <select name="description" id="description"  -->
<!--               	class="form-control ss" data-required="true"> -->
<!--                 <option value="">-</option> -->
<%--                 <c:forEach items="${type}" var="type" varStatus="status"> --%>
<%-- 				<option value="${type.id}">${type.description}</option> --%>
<%-- 				</c:forEach> --%>
<!--               </select> -->
<script src="js/auditAjaxCalls.js?v=<%=new Date()%>"></script> 
<script src="js/jquery.datetimepicker.full.min.js?v=<%=new Date()%>"></script>
<script>
jQuery('#closingdate').datetimepicker({
	timepicker:false,
	format:'Y-m-d',
	minDate:'+1970/01/02'
});
  
	var tipoForm = "${tipoForm}";
	var id = "${id}";
	var idusr = "${idusr}";
	var auditJson = -1;
	$(document).ready(function(){
		$('#closingdate').addClass('myCheck');
		switch (tipoForm){
			case "I":
				$('#idDiv').hide();
				$('#idDashed').hide();
				$('#crDateDiv').hide();
				$('#crDateDashed').hide();
				$.when(getTypes(-1), getStores(-1),getItems(null,null),getCustomers(null)).done(function(a1, a2,a3,a4){
			 	});
			break;
			
			case "E":
				 	
			 	  $.ajax({
					url: '../../ats/GetAudit',
					type: 'GET',
					dataType: "json",
					data: {idAudit:id},
					success: function (data) {
									 auditJson = data;
									 $('#id').val(auditJson.id);
									 $('#name').val(auditJson.name);
									 $('#creationdate').val(auditJson.creation_date);
									 $('#closingdate').val(auditJson.closing_date);
									 $.when(getTypes(auditJson.idstore), getStores(auditJson.idtype,auditJson.idcustomer),getItems(auditJson.auditItems,auditJson.idcustomer),getCustomers(auditJson.idcustomer)).done(function(a1, a2,a3,a4){
									 	});
							}
					});
			break;
			
			case "D":
				$('#myBody').hide();
				$('#modalBody').append("<h4><%=propLang.get("aud_confirm1")%>?</h4>");
			break;
			case "C":
				$('#myBody').hide();
				$('#modalBody').append("<h4><%=propLang.get("aud_confirm2")%></h4>");
			break;
			
		}
	});
	
	$('#saveButton').click(function(){
		if(validateInput(tipoForm)){
			switch(tipoForm) {
			  case "I":
				  var data = $('form').serialize()+ 
				  '&type=' + tipoForm;
				
				  $.ajax({
						url: '../../ats/SetAudit',
						type: 'GET',
						dataType: "html",
						data: data,
						success: function (data) {
										location.reload();
								}
						});
			    break;
			  case "E":
				  var data = $('form').serialize()+ 
				  '&type=' + tipoForm+
				  '&auditstate=0';
				
				  $.ajax({
						url: '../../ats/SetAudit',
						type: 'GET',
						dataType: "html",
						data: data,
						success: function (data) {
										location.reload();
								}
						});
			    break;
			  case "D":
				  $.ajax({
						url: '../../ats/SetAudit',
						type: 'GET',
						dataType: "html",
						data: {id:id, type:tipoForm},
						success: function (data) {
										location.reload();
								}
						});
			    break;
			    
			  case "C":
				  $.ajax({
						url: '../../ats/CloseAudit',
						type: 'GET',
						dataType: "html",
						data: {id:id},
						success: function (data) {
										location.reload();
								}
						});
				break;
			}
		}
	});

</script>

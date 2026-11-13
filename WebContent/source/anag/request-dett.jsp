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
		<h3 class="font-thin"><%=propLang.get("itm_all") %></h3>
	</div>
	<div class="modal-body" id="modalBody" >
		
	<div id="myBody">	
        <div class="form-group pull-in clearfix" id="idDiv">
	        <div class="col-sm-12">
	          <label><%=propLang.get("itm_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" readonly>   
	        </div>
	    </div>
	    <div class="line line-dashed b-b line-lg pull-in" id="idDashed"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("itm_name") %></label>
	          <input type="text" id="name" name="name" 
	          	class="form-control myCheck" data-required="true">   
	        </div>
	    </div>


	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("itm_description") %></label>
			   <input type="text" id="description" name="description" 
	          	class="form-control myCheck" data-required="true">   
	        </div>
	    </div>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
        <div class="spinner" id="loadVendor">
        	<label><%=propLang.get("itm_vendor_name") %></label>
        	<div class="input-group mySDiv">
					  <select id="idvendor" name="idvendor" class="form-control ss" data-required="true">
		         	  </select>
      		 <span class="input-group-addon btn-default myPlus" id="addVendor"><i class="fa fa-plus" aria-hidden="true"></i></span>   
   			 </div>
   		</div> 	    
        <div class="line line-dashed b-b line-lg pull-in"></div>
        <div class="spinner" id="loadBrand">
        	<label><%=propLang.get("itm_brand_name") %></label>
        	<div class="input-group mySDiv">
					  <select id="idbrand" name="idbrand" class="form-control ss" data-required="true">
		         	  </select>
      		 <span class="input-group-addon btn-default myPlus" id="addBrand"><i class="fa fa-plus" aria-hidden="true"></i></span>   
   			 </div>
   		</div>	
   			     
        <div class="line line-dashed b-b line-lg pull-in"></div>
        <div class="spinner" id="loadCategory">
        	<label><%=propLang.get("itm_category_name") %></label>
        	<div class="input-group mySDiv">
					  <select id="idcategory" name="idcategory" class="form-control ss" data-required="true">
		         	  </select>
      		 <span class="input-group-addon btn-default myPlus" id="addCategory" ><i class="fa fa-plus" aria-hidden="true"></i></span>   
   			 </div>
   		</div>	
   			     
        <div class="line line-dashed b-b line-lg pull-in"></div>
			         
			          
	    
<!-- 	    <div class="line line-dashed b-b line-lg pull-in"></div> -->
	   
<!-- 	    <div class="form-group pull-in clearfix"> -->
<!-- 	        <div class="col-sm-12"> -->
<%-- 	          <label><%=propLang.get("itm_brand_name") %></label> --%>
<!-- 			  <select id="idbrand" name="idbrand" class="form-control ss" data-required="true"> -->
<!--          	  </select> -->
<!-- 	        </div> -->
<!-- 	    </div> -->
	     
        
<!-- 	    <div class="line line-dashed b-b line-lg pull-in"></div> -->
	    
<!-- 	    <div class="form-group pull-in clearfix"> -->
<!-- 	        <div class="col-sm-12"> -->
<%-- 	          <label><%=propLang.get("itm_category_name") %></label> --%>
<!-- 			  <select id="idcategory" name="idcategory" class="form-control ss" data-required="true"> -->
<!--          	  </select> -->
<!-- 	        </div> -->
<!-- 	    </div> -->
	    
<!-- 	    <div class="line line-dashed b-b line-lg pull-in"></div> -->
	    <div class="spinner" id="loadCountry">
		    <div class="form-group pull-in clearfix">
		        <div class="col-sm-12 mySDiv">
		          <label><%=propLang.get("itm_country_name") %></label>
				  <select id="country" name="country" class="form-control ss" data-required="true">
	         	  </select>
		        </div>
		    </div>
		</div>
	     
        
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	   
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("itm_protected") %></label>
			   <input type="number" id="isProtected" name="isProtected" value="0"
	          	class="form-control" data-required="true" readonly>   
	        </div>
	    </div>
        
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	   
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("itm_evrl") %></label>
			   <input type="text" id="evreportlink" name="evreportlink" 
	          	class="form-control" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Note</label>
			   <textarea id="note" name="note" 
	          	class="form-control" style="height:100px"></textarea>   
	        </div>
	    </div>
	    
	</div>
	<div class="spinner" id="loadCustomer" style="display:none">
        	<label><%=propLang.get("cus_dsfun") %></label>
        	   <div class="form-group pull-in clearfix">
	        		<div class="col-sm-12 mySDiv">
					  <select id="idcustomer" name="idcustomer" class="form-control ss" data-required="true" multiple>
		         	  </select>
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
<script src="js/innerAjaxCalls2.js?v=<%=new Date()%>"></script>
<script>
	
	var tipoForm = "${tipoForm}";
	var username = "${username}";
	var id = "${id}";
	var itemJson = -1;
	$(document).ready(function(){
		switch (tipoForm){
			case "I":
				$('#idDiv').hide();
				$('#idDashed').hide();
				 $.when(getVendors(-1,""), getBrands(-1,""), getCategories(-1,""), getCountries(-1)).done(function(a1, a2, a3, a4){
					});
			break;
			
			case "E":
			 	  $.ajax({
					url: '../../ats/GetItem',
					type: 'GET',
					dataType: "json",
					data: {idItem:id},
					success: function (data) {
									 itemJson = data;
									 $('#id').val(itemJson.id);
									 $('#name').val(itemJson.name);
									 $('#description').val(itemJson.description);
									 $('#isProtected').val(itemJson.isProtected);
									 $('#evreportlink').val(itemJson.evreportlink);
									 $('#note').val(itemJson.note);
									 
									 $.when(getVendors(itemJson.idvendor,""), getBrands(itemJson.idbrand,""), getCategories(itemJson.idcategory,""), getCountries(itemJson.country)).done(function(a1, a2, a3, a4){
									  });
							}
					});
			break;
			
			case "D":
				$('#myBody').hide();
				$('#modalBody').append("<h4>Sei sicuro di voler eliminare questo elemento?</h4>");
			break;
			case "A":
				$('#myBody').hide();
				$('#loadCustomer').show();
				getCustomers(null);
			break;
			
		}
	});
	
	$('#saveButton').click(function(){
		if(validateInput(tipoForm)){
			switch(tipoForm) {
			  case "I":
				  var data = $('form').serialize()+ 
				  '&vendorname=' + $('#idvendor option:selected').text()+
				  '&type=' + tipoForm+
				  '&brandname=' + $('#idbrand option:selected').text()+
				  '&categoryname=' + $('#idcategory option:selected').text()+
				  '&brandname=' + $('#country option:selected').text();
				  '&sc=';
				
				  $.ajax({
						url: '../../ats/SetItem',
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
				  '&vendorname=' + $('#idvendor option:selected').text()+
				  '&type=' + tipoForm+
				  '&brandname=' + $('#idbrand option:selected').text()+
				  '&categoryname=' + $('#idcategory option:selected').text()+
				  '&brandname=' + $('#country option:selected').text();
				  '&sc=';
				
				  $.ajax({
						url: '../../ats/SetItem',
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
						url: '../../ats/SetItem',
						type: 'GET',
						dataType: "html",
						data: {id:id, type:tipoForm},
						success: function (data) {
										location.reload();
								}
						});
			    break;
			  case "A":
				  var itemId,name,description,evreportlink,idvendor,idbrand,idcategory,country,note;
			 	  $.ajax({
						url: '../../ats/GetItem',
						type: 'GET',
						dataType: "json",
						data: {idItem:id},
						async: false,
						success: function (data) {
										 var itemJson = data;
										 itemId = itemJson.id;
										 name = itemJson.name;
										 description = itemJson.description;
										 evreportlink = itemJson.evreportlink;
										 idvendor=itemJson.idvendor;
										 idbrand = itemJson.idbrand;
										 idcategory = itemJson.idcategory;
										 country = itemJson.country;
										 note = itemJson.note;
								}
						});
			 	 var sc = null;
				  $("#idcustomer option:selected").each(function(){
					  if(sc!= null)
					  	sc=sc+','+$(this).val();
					  else
						sc=$(this).val();
				  });
						
				  $.ajax({
						url: '../../ats/SetItem',
						type: 'GET',
						dataType: "html",
						data: {	id:itemId,name:name,description:description,
							   	evreportlink:evreportlink,note:note,idvendor:idvendor,
							   	idbrand:idbrand,idcategory:idcategory,country:country,isProtected:"1",sc:sc, type:"E"
							},
						success: function (data) {
										location.replace('items-list.htm');
								}
						});
			  break;
			}
		}
		
	});

	$('#addVendor').click(function(){
		 var value = prompt("Insert new Vendor name", "");
		  if (value != null) {
			  $.ajax({
					url: '../../ats/SetVendor',
					type: 'GET',
					dataType: "json",
					data: {type:"I",name:value},
					success: function (data) {
						getVendors(-2,data);
					}
		 	  });
		  }

	});
	$('#addBrand').click(function(){
		 var value = prompt("Insert new Brand name", "");
		  if (value != null) {
			  $.ajax({
					url: '../../ats/SetBrand',
					type: 'GET',
					dataType: "json",
					data: {type:"I",name:value},
					success: function (data) {
						getBrands(-2,data);
					}
		 	  });
		  }

	});
	
	$('#addCategory').click(function(){
		 var value = prompt("Insert new Category name", "");
		  if (value != null) {
			  $.ajax({
					url: '../../ats/SetCategory',
					type: 'GET',
					dataType: "json",
					data: {type:"I",name:value},
					success: function (data) {
						getCategories(-2,data);
					}
		 	  });
		  }

	});
	
</script>

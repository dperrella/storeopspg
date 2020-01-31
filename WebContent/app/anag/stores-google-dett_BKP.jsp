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

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h4>${name}</h4>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12" align="center">
	          <img src="${icon}"/>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          	<label><b>Address</b></label><br/>
				<span>${address}</span>
	        </div>
	    </div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          	<label><b>Telefono</b></label><br/>
				<span>${phone}</span>
	        </div>
	    </div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
						    
		
		
		<!-- 
		
		obj.put("name", place.name());
			obj.put("address", place.formattedAddress());
			obj.put("latitude", place.latitude());
			obj.put("longitude", place.longitude());
			obj.put("icon", place.icon());
			//obj.put("rating", place.rating());
			obj.put("country", place.longCountry());
			obj.put("region", place.region());
			obj.put("longProvince", place.longProvince());
			obj.put("shortProvince", place.shortProvince());
			obj.put("zipCode", place.zipCode());
			obj.put("postalCode", place.postalCode());
			obj.put("phone", place.phone());
			obj.put("url", place.url());
			
		-->
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="saveUid()"><%=propLang.get("save") %></a>
	</div>
  </div>
</div>


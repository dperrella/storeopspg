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

<div class="modal-dialog modal-lg" id="myModal1">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" id="myClose" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("ait_all") %></h3>
	</div>
	<div class="modal-body" id="modalBody" >
		
		<div class="row">
			<div class="col-xs-8"><div id="audit"></div></div>
			<div class="col-xs-4">
				<ul style="list-style-type: none;text-align:right">
					<li>Protected <i class="fa fa-check"></i></li>
					<li>Not Protected <i class="fa fa-times"></i></li>
					<li>Not in Store <i class="fa fa-dropbox"></i></li>
					<li>Note <i class="fa fa-edit"></i></li>
				</ul>
			</div>
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<div class="spinner" id="myBody"></div>
	   		
	</div>
		<div class="modal-footer">
			<a class="btn btn-default"  id="myClose2" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		</div>
	</div>
  </div>
</form>

<script src="js/auditItemsJs.js?v=<%=new Date()%>"></script>
<script>
	var id = "${id}";
	var idusr = "${idusr}";
	$(document).ready(function(){
		
	 	  $.ajax({
			url: '../../ats/GetAudit',
			type: 'GET',
			dataType: "json",
			data: {idAudit:id},
			success: function (data) {
				
				console.log(JSON.stringify(data));
				
				var json = data;
				 
				var name = json.name;
				var crDate = json.creation_date;
				var type = json.type;
				var store = json.store;
				 
				 
				for(var i = 0; i < json.auditItems.length; i++) {
					var obj = json.auditItems[i];
					
					createElement(obj.idItem,obj.note,obj.itemName,obj.itemDescription,obj.auditResult, obj.url);
				}
				
				$('#audit').html('');
				$('#audit').append('<div class="h3 text-primary m-b">'+name+'</div>');
				$('#audit').append('<div class="text-dark">Audit Type: '+type+'</div>');
				$('#audit').append('<div class="text-dark">'+store+'</div>');
				//$('#audit').append('<div class="line line-dashed b-b line-lg pull-in"></div>');
				
			 	$('#myBody').removeClass('spinner');
			}
		});

	});
	 
	
</script>

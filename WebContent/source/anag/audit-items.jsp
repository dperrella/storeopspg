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
				
				<a class="btn btn-default pull-right" onclick="_print();">Print Audit</a>
				<a class="btn btn-default pull-right" onclick="_excel();">Excel Export</a>
			</div>
			<input type=hidden name="state" id="_state" value=""/>
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<div class="spinner" id="myBody"></div>
	   		
	</div>
		<div class="modal-footer">
			<a class="btn btn-default"  id="myClose2" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
			<a class="btn btn-primary btn-lg" onclick="_print();">Print Audit</a>
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
				var state = json.state;
				
				$('#_state').val(state);
				 
				 
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
			 	
			 	/*
			 	if(state==1) {
			 		$('.myPlus').attr("disabled", true);
			 	}
			 	*/
			}
		});

	});
	 
	
	function _print() {
		
		showLoader2();
		
		$.ajax({
			url: '../../ats/GetAudit',
			type: 'GET',
			dataType: "json",
			data: {idAudit:id},
			success: function (data) {
				
				hideLoader2();
				console.log(JSON.stringify(data));
				
				$('#_auditTable').show();
				
				$('#_name').html(data.name);
				$('#_store').html(data.store);
				$('#_closing_date').html(data.closing_date);
				
				$("#_tbody").empty();
				
				for(var i = 0; i < data.auditItems.length; i++) {
					var obj = data.auditItems[i];
					
					var result = obj.auditResult;
					if(result==0) {
						result = 'Protected';
					}
					else if(result==1) {
						result = 'Not Protected';
					}
					else if(result==2) {
						result = 'Not in store';
					}
					else {
						result = '';
					}
					
					var photo = '';
					if(obj.url!='') {
						photo = '<img src="'+obj.url+'" style="width:100px"/>';
					}
					
					$("#_auditTable tbody").append('<tr>'
							+'<td>'+obj.itemName+'</td>'
							+'<td>'+obj.vendorname+'</td>'
							+'<td>'+obj.brandname+'</td>'
							+'<td>'+obj.categoryname+'</td>'
							+'<td rowspan="2">'+photo+'</td>'
							+'<td rowspan="2">'+result+'</td>'
							+'</tr><tr>'
							+'<td colspan="2" valign="top"><small style="color:#c0c0c0">Note:</small><br/>'+obj.note+'</td>'
							+'<td colspan="2" valign="top"><small style="color:#c0c0c0">Barcodes:</small><br/>'+obj.barcodes.replace('"','')+'</td>'
							+'</tr>');
			
				}	
				
				
				
				var divContents = $("#_prt").html();
	            
				
				var printWindow = window.open('', '', 'height=400,width=800');
	            printWindow.document.write('<html><head><title>Audit</title>');
	            printWindow.document.write('</head><body >');
	            printWindow.document.write(divContents);
	            printWindow.document.write('</body></html>');
	            printWindow.document.close();
	            printWindow.print();
	            printWindow.close();
	            
				
	            $('#_auditTable').hide();
				
	            $('.modal').remove(); 
	            $('div.modal-backdrop').remove();
				
				
			}
		});
		
	}
	
	
	
	function _excel() {
		
		showLoader2();
		
		$.ajax({
			url: '../../ats/GetAudit',
			type: 'GET',
			dataType: "json",
			data: {idAudit:id},
			success: function (data) {
				
				hideLoader2();
				console.log(JSON.stringify(data));
				
				$('#_auditTable2').show();
				
				$("#_name2").html(" = ("+data.name+")");
				$('#_store2').html(' = ('+data.store+')');
				$('#_closing_date2').html(' = ('+data.closing_date+')');
				
				$("#_tbody2").empty();
				
				for(var i = 0; i < data.auditItems.length; i++) {
					var obj = data.auditItems[i];
					
					var result = obj.auditResult;
					if(result==0) {
						result = 'Protected';
					}
					else if(result==1) {
						result = 'Not Protected';
					}
					else if(result==2) {
						result = 'Not in store';
					}
					else {
						result = '';
					}
					
					var photo = '';
					if(obj.url!='') {
						photo = '<img src="'+obj.url+'" style="width:100px"/>';
					}
					
					$("#_auditTable2 tbody").append('<tr>'
							+'<td>'+obj.idItem+'</td>'
							+'<td>'+obj.itemName+'</td>'
							+'<td>'+obj.vendorname+'</td>'
							+'<td>'+obj.brandname+'</td>'
							+'<td>'+obj.categoryname+'</td>'
							+'<td>'+obj.barcodes+'</td>'
							+'<td>'+obj.note+'</td>'
							+'<td>'+result+'</td>'
							+'</tr>');
			
				}	
				
				
				
				var divContents = $("#_xls").html();
	            var printWindow = window.open('', '', 'height=400,width=800');
	            printWindow.document.write('<html><head><title>Audit</title>');
	            printWindow.document.write('</head><body >');
	            printWindow.document.write(divContents);
	            printWindow.document.write('</body></html>');
	            printWindow.document.close();
	            //printWindow.print();
	            printWindow.close();
	            
	            
	            
	            function doit(type, fn, dl) {
					var elt = document.getElementById('_auditTable2');
					var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
					return dl ?
						XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
						XLSX.writeFile(wb, fn || ('download.' + (type || 'xlsx')));
				}
				doit('xlsx');
				
				$('#_name2').html('');
				$('#_store2').html('');
				$('#_closing_date2').html('');
				
				$("#_tbody2").empty();
				$('#_auditTable2').hide();
				
	            $('.modal').remove(); 
	            $('div.modal-backdrop').remove();
				
				
			}
		});
		
	}
	
</script>

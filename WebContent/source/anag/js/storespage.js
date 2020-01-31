// 				$('#storeButton').click(function(e){
// 			        e.preventDefault();
// 			        $('#loader').show();
//		        $.ajax({
// 			            url: 'GetStores',
// 			            type: 'POST',
// 			            data: {userId:"1"},
// 			            dataType: "html",
// 			            success: function (data) {
// 			            	$('#loader').hide();
// 			                alert("Data upload result: " + data);
// 			            }
// 			        });
// 			        return false;
// 				});
	var auditTab;
	var auditItemsTab;
	var currentRow;
	var currentRowData;
	var auditId;

	$(document).ready(function() {
	  $('#auditTableDiv').hide();
	  $('#auditItemsTableDiv').hide();
	  
	  $('#auditPanel').hide();
	   
	  
	
	});
	
	function toJSONString(form) {
		var obj = {};
		var elements = form.querySelectorAll( "input" );
		for( var i = 0; i < elements.length; ++i ) {
			var element = elements[i];
			var name = element.name;
			var value = element.value;
	
			if( name ) {
				obj[ name ] = value;
			}
		}
		//obj['userId'] = userId;
		//obj['storeId'] = storeId;
		return JSON.stringify( obj );
	}
	
	function hideAndDestroy() {
	
	}
	
	$('#storesTable tbody').on('click', 'button', function() {
	  var data = $('#storesTable').DataTable().row($(this).parents('tr')).data();
	  //var data = auditTab.row('.selected').data();
	});
	
	
	$('.audits').click(function() {
	  var storeId = $(this).attr('id');
	
	  $('#auditsTable').DataTable().destroy();
	  $('#auditItemsTable').DataTable().destroy();
	  $('#auditItemsTableDiv').hide();
	  
	  $('#auditPanel').show();
	  $('#intro').hide();
	  
	  auditTab = $('#auditsTable').DataTable({
	    "processing": true,
	    "language": {
	        "emptyTable":     "Non ci sono audit associati allo Store",
	        "search":     "Cerca:",
	        "paginate": {
	            "first":      "Primo",
	            "last":       "Ultimo",
	            "next":       "Successivo",
	            "previous":   "Precedente"
	        },
    	    "lengthMenu": "Mostra _MENU_ righe per pagina",
            "zeroRecords": "La ricerca non ha prodotto alcun risultato",
            "info": "Pagina _PAGE_ di _PAGES_",
            "infoEmpty": "Nessuna riga disponibile",
            "infoFiltered": "(Filtrato da _MAX_ righe totali)"
	    },
	    "sDom": "<'row'<'col-sm-12'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	    "ajax": {
	      "url": "../../GetAudits",
	      "type": 'POST',
	      "data": {
	        storeId: storeId
	      },
	      "dataSrc": 'Audits'
	
	    },
	    "columns": [{
	        "data": "auditId",
	        "width": "5%"
	      },
	      {
	        "data": "name"
	      },
	      {
	        "data": "creationDate",
	        "width": "15%"
	      },
	      {
	        "data": "closingDate",
	        "width": "15%"
	      },
	      {
	        "data": "state",
	        "width": "10%"
	      },
	      {
	        "data": null,
	        "width": "10%",
	        "defaultContent": "<button type = 'button' class='btn btn-default' data-toggle='modal' data-target='#detailModal'>Mostra AuditItems <i class='fa fa-edit'></i></button>"
	      }
	    ],
	    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
      	    $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData["auditId"] + '</span>');
      	    if(aData["state"]=='opened') {
      	    	$('td:eq(4)', nRow).html('<span class="label bg-success">' + aData["state"] + '</span>');
      	    }
      	    else {
      	    	$('td:eq(4)', nRow).html('<span class="label bg-danger">' + aData["state"] + '</span>');
      	    }
            return nRow;
        },
	    "destroy": true
	  });
	
	  $('#auditTableDiv').show();
	  $('#loader').show();
	
	});
	
	function CreateAuditItemDataTable(auditId){
		 $('#auditItemsTable').DataTable().destroy();
	
		  auditItemsTab = $('#auditItemsTable').DataTable({
		    "processing": true,
		    "language": {
		        "emptyTable": "Non ci sono item associati all'audit",
		        "search":     "Cerca:",
		        "paginate": {
		            "first":      "Primo",
		            "last":       "Ultimo",
		            "next":       "Successivo",
		            "previous":   "Precedente"
		        },
        	    "lengthMenu": "Mostra _MENU_ righe per pagina",
                "zeroRecords": "La ricerca non ha prodotto alcun risultato",
                "info": "Pagina _PAGE_ di _PAGES_",
                "infoEmpty": "Nessuna riga disponibile",
                "infoFiltered": "(Filtrato da _MAX_ righe totali)"
		    },
		    "ajax": {
		      "url": "../../GetAuditItems",
		      "type": 'POST',
		      "data": {
		        auditId: auditId
		      },
		      "dataSrc": 'AuditItems'
		    },
		    "columns": [
		      {
		        "data": "barcode"
		      },
		      {
		        "data": "name"
		      },
		      {
		        "data": "description"
		      },
		      {
		        "data": "vendor"
		      },
		      {
		        "data": "brand"
		      },
		      
		      {
		        "data": "country"
		      },
		      {
		        "data": "protected"
		      },
		      {
		        "data": "evolutionReportLink"
		      },
		      {
		    	"data": "auditResult"
		      },
		      {
		      	"data": "note"
		      },
		      {
		    	"data": "lot"
		      },
		      {
		    	"data": "expiry"
		      }
		    ],
		    "sDom": "<'row'<'col-sm-12'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
		    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	      	    $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData["barcode"] + '</span>');
	      	    return nRow;
	        },
		    "destroy": true
		  });
	}
	
	$(document).on('click', '#auditsTable tbody button', function() {
	  var data = auditTab.row($(this).parents('tr')).data();
	  //alert(JSON.stringify(auditTab.row().data()));
	  auditId = data.auditId;
	  CreateAuditItemDataTable(auditId);
	  $('#auditItemsTableDiv').show();
	});
	
	$('#closeSuccess').click(function(){
		$('#success').hide();
	});
	$('#closeError').click(function(){
		$('#error').hide();
	});
	
	
	function myAlertTop(element){
		  $(element).show();
		  setTimeout(function(){
		    $(element).hide(); 
		  }, 2000);
		}
	
	$('#updateItem').click(function(){
		 $('#detailModal').modal('hide');
		var json = toJSONString(document.getElementById("updateItemForm"));
		var web = $('#web').val();
		$.ajax({
		         url: 'UpdateItem',
		         type: 'POST',
		         data: {jsonParameters:json,web},
		         dataType: "html",
		         success: function (data) {
		         	$('#loader').hide();
		         	 if(data){
		         		 auditItemsTab.ajax.reload();
		         		 $('#detailModal').hide();
		         		 myAlertTop($('#success'));
		         	 }
		         	 else
		         		myAlertTop($('#error'));
		         }
		     });
			return false;
		
	});
//	var value;
//	$('.updateIt').change(function(){
//		$('#updateItem').removeAttr('disabled');
//			value = $(this).val();
//		
//		
//	});
	
	$(document).on('click', '#auditItemsTable tbody button', function() {
		currentRow = auditItemsTab.row($(this).parents('tr'));
		currentRowData = auditItemsTab.row($(this).parents('tr')).data();
		$('#mBarcode').val(currentRowData.barcode);
		$('#mName').val(currentRowData.name);
		$('#mDescription').val(currentRowData.description);
		$('#mVendor').val(currentRowData.vendor);
		$('#mCountry').val(currentRowData.country);
		$('#mBrand').val(currentRowData.brand);
		$('#mProtected').val(currentRowData.protected);
		$('#mEvolutionReportLink').val(currentRowData.evolutionReportLink);
	});

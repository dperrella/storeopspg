	var itemsTable;
	var currentRow;
	
	$(document).ready(function() {
		
		$('#itemsPanel').hide();
		
	
	  $('#myModal').hide();
	
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
	
	
	$('#createItem').click(function(){
		$('#createItemModal').modal('hide');
		var json = toJSONString(document.getElementById("insertItemForm"));
		
		//alert(json);
		var web = $('#web').val();
		$.ajax({
		         url: '../../CreateItem',
		         type: 'POST',
		         data: {jsonParameters:json,web},
		         dataType: "html",
		         success: function (data) {
		         	$('#loader').hide();
		         	 if(data){
			         	 
		         		itemsTable.ajax.reload();
		         		/*
		         		 var dt = $('#itemsTable').DataTable();
			         	 var d = JSON.parse(data);
			        	 dt.row.add( [
			                 "<span class='label bg-light'>"+d.barcode+"</span>",
			                 d.name,
			                 d.description,
			                 d.vendor,
			                 d.brand,
			                 d.country,
			                 d.protectionType,
			                 d.evolutionReportLink,
			                 "<button type='button' class='btn btn-light items' id='"+d.barcode+"' data-toggle='modal' data-target='#detailModal'>Dettaglio<i class='fa fa-edit'></i></button>"
			             ] ).draw();
			             */
		         	 
		         	 $('#createItemModal').hide();
		         	 myAlertTop($('#success'), "Successo:", "L'item è stato inserito con successo!");
		         	 }
		         	 else
		         		myAlertTop($('#error'), "Attenzione:", "Qualcosa è andato storto nel processo d'inserimento dell'item.");
		         }
		     });
			return false;
		
	});
	
	$('#updateItem').click(function(){
		 $('#detailModal').modal('hide');
		var json = toJSONString(document.getElementById("updateItemForm"));
		var web = $('#web').val();
		$.ajax({
		         url: '../../UpdateItem',
		         type: 'POST',
		         data: {jsonParameters:json,web},
		         dataType: "html",
		         success: function (data) {
		         	$('#loader').hide();
		         	 if(data){
		         		 
		         		 
		         		 
		         		 itemsTable.ajax.reload();
		         	 
		         	 $('#detailModal').hide();
		         	 myAlertTop($('#success'), "Successo:", "Le informazioni relative all' item sono state aggiornate con successo!");
		         	 }
		         	 else
		         		myAlertTop($('#error'),"Attenzione:", "Qualcosa è andato storto nel processo d'aggiornamento dell'item.");
		         }
		     });
			return false;
		
	});
	
	$('#closeSuccess').click(function(){
		$('#success').hide();
	});
	$('#closeError').click(function(){
		$('#error').hide();
	});
	
	$('#vergonia').click(function(){
		myAlertTop($('#success'));
	});
	
	function myAlertTop(element,strong,message){
		  $(element).html('');
		  $(element).append("<strong id = 'test'>"+strong+"</strong>"+message);
		  $(element).show();
		  setTimeout(function(){
		    $(element).hide(); 
		  }, 2000);
		}
	
	
	$(document).on('click', '#itemsTable tbody button', function() {
		
		currentRow = itemsTable.row($(this).parents('tr'));
		
		
		data = itemsTable.row($(this).parents('tr')).data();
		
		//alert(JSON.stringify(data));
		$('#mBarcode').val(data.barcode);
		$('#mName').val(data.name);
		$('#mDescription').val(data.description);
		$('#mVendor').val(data.vendor);
		$('#mBrand').val(data.brand);
		$('#mCountry').val(data.country);
		$('#mProtected').val(data.protected);
		$('#mEvolutionReportLink').val(data.evolutionReportLink);
	});
	
	
	
	
	$('.items').click(function() {
		  var storeId = $(this).attr('id');
		
		  $('#itemsTable').DataTable().destroy();
		  
		  $('#itemsPanel').show();
		  $('#intro').hide();
		  
		  itemsTable = $('#itemsTable').DataTable({
		    "processing": true,
		    "language": {
		        "emptyTable":     "Non ci sono items associati allo Store",
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
		      "url": "../../GetStoreItems",
		      "type": 'POST',
		      "data": {
		        storeId: storeId
		      },
		      "dataSrc": 'Items'
		    },
		    "columns": [
		      {
		        "data": "barcode",
		        "width": "10%"
		      },
		      {
		        "data": "name",
		        "width": "10%"
		      },
		      {
		        "data": "description",
		        "width": "10%"
		        
		      },
		      {
		        "data": "vendor",
		        "width": "10%"
		      },
		      {
		        "data": "brand",
		        "width": "10%"
		      },
		      
		      {
		        "data": "country",
		        "width": "10%"
		      },
		      {
		        "data": "protected",
		        "width": "10%"
		      },
		      {
		        "data": "evolutionReportLink",
		        "width": "10%"
		      },
		      {
		        "data": null,
		        "width": "10%"
		        //"defaultContent": "<button type = 'button' class='btn btn-default' data-toggle='modal' data-target='#detailModal'>Edit Item <i class='fa fa-edit'></i></button>"
		      }
		    ],
		    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	      	    $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData["barcode"] + '</span>');
	      	    
	      	    $('td:eq(8)', nRow).html('<button type="button" class="btn btn-default" data-toggle="modal" id="' + aData["barcode"] + '" data-target="#detailModal">Edit Item <i class="fa fa-edit"></i></button>');
	      	    return nRow;
	        },
		    "destroy": true
		  });
		
		  $('#itemsTableDiv').show();
		  $('#loader').show();
		
		});
	
	
	
	
	
	$('.items2').click(function() {
		  var storeId = $(this).attr('id');
		
		  $('#items').DataTable().destroy();
		  
		  itemsTable = $('#items').DataTable({
		    "processing": true,
		    "language": {
		        "emptyTable":     "Non ci sono items associati allo Store",
		        "search":     "Cerca: ",
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
		      "url": "../../GetStoreItems",
		      "type": 'POST',
		      "data": {
		        storeId: storeId
		      },
		      "dataSrc": 'Items'
		    },
		    "columns": [
		      {
		        "data": "barcode",
		        "width": "10%"
		      },
		      {
		        "data": "name",
		        "width": "10%"
		      },
		      {
		        "data": "description"
		      },
		      {
		        "data": "vendor",
		        "width": "10%"
		      },
		      {
		        "data": "brand",
		        "width": "10%"
		      },
		      
		      {
		        "data": "country",
		        "width": "10%"
		      },
		      {
		        "data": "protected",
		        "width": "10%"
		      },
		      {
		        "data": "evolutionReportLink",
		        "width": "10%"
		      }
		     
		    ],
		    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	      	    $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData["barcode"] + '</span>');
	      	    
	      	    return nRow;
	        },
		    "destroy": true
		  });
		
		  
		});
	
//	
//	
//	$('.items').click(function() {
//		  var storeId = $(this).attr('id');
//		
//		  $('#items').DataTable().destroy();
//		  
//		  itemsTable = $('#items').DataTable({
//		    "processing": true,
//		    "language": {
//		        "emptyTable":     "Non ci sono items associati allo Store",
//		        "search":     "Cerca: ",
//		        "paginate": {
//		            "first":      "Primo",
//		            "last":       "Ultimo",
//		            "next":       "Successivo",
//		            "previous":   "Precedente"
//		        },
//	    	    "lengthMenu": "Mostra _MENU_ righe per pagina",
//	            "zeroRecords": "La ricerca non ha prodotto alcun risultato",
//	            "info": "Pagina _PAGE_ di _PAGES_",
//	            "infoEmpty": "Nessuna riga disponibile",
//	            "infoFiltered": "(Filtrato da _MAX_ righe totali)"
//		    },
//		    "sDom": "<'row'<'col-sm-12'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
//		    "ajax": {
//		      "url": "../../GetStoreItems",
//		      "type": 'POST',
//		      "data": {
//		        storeId: storeId
//		      },
//		      "dataSrc": 'Items'
//		    },
//		    "columns": [
//		      {
//		        "data": "barcode",
//		        "width": "10%"
//		      },
//		      {
//		        "data": "name",
//		        "width": "10%"
//		      },
//		      {
//		        "data": "description"
//		      },
//		      {
//		        "data": "vendor",
//		        "width": "10%"
//		      },
//		      {
//		        "data": "brand",
//		        "width": "10%"
//		      },
//		      
//		      {
//		        "data": "country",
//		        "width": "10%"
//		      },
//		      {
//		        "data": "protected",
//		        "width": "10%"
//		      },
//		      {
//		        "data": "evolutionReportLink",
//		        "width": "10%"
//		      }
//		     
//		    ],
//		    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
//	      	    $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData["barcode"] + '</span>');
//	      	    
//	      	    return nRow;
//	        },
//		    "destroy": true
//		  });
//		
//		  
//		});
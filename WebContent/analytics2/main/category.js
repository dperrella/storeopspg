	//////////////////////////////////////////
  	
  	function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		 });
	}
  	
  	function getCategory() {
  		loadCategory('${idcus}');
  	}
  	
  	
	function loadCategory(idcus) {
		
		if(idcus!='') {
			console.log("../../servlet/ws/GetCustomersCategory?idcus="+idcus);
			
			$.ajax({ 
				url: "../../servlet/ws/GetCustomersCategory?idcus="+idcus,
			   	method:"POST",
			   	dataType: "json",       
			   	beforeSend: function( xhr ) {
			   	    showLoader2();
			   	},
			   	success: function(data)  
		   		{
			   		hideLoader2();
		  			
			   		if(data.length>0) {
			   			$('#_cat').show();
			   			//console.log(JSON.stringify(data))
			   			var cat = data;
			   			$.each(cat, function(index, cat) {
			   				parseCategory(cat, idcus, '_catsel');
			   			});
			   			
			   		}
			   		
		   		}   
		 	});
		}
	}
	
	function parseCategory(cat, idcus, _div) {
		
		var markup = '';
		var callfun = false;
		if(cat.value.length==0) {
			
			if(cat.nodes.length==0) {
				console.log("no value for "+cat.name+" in nodes");
			}
			else {
				
				markup = '<input type="hidden" name="_cat" value="'+cat.name+'"/><select id="_cat_'+cat.name+'" name="_cat_'+cat.name+'" class="ss form-control" onchange="checkNodes(this.value, \''+cat.name+'\',  \''+idcus+'\')" multiple>'
					//+'<option value="">-- All --</option>';
					'';
					
				//var val = [];
				$.each(cat.nodes, function(index, node) {
					//val.push(node.name);
					markup+= '<option value="'+node.name+'">'+node.name+'</option>';
				});
				//console.log("value for "+cat.name+": "+JSON.stringify(val));
				markup+= '</select>';
				
				callfun = true;
			}
		}
		else {
			
			markup = '<input type="hidden" name="_cat" value="'+cat.name+'"/><select id="_cat_'+cat.name+'" name="_cat_'+cat.name+'" class="ss form-control" multiple>'
				//+'<option value="">-- All --</option>';
				'';
			
			$.each(cat.value, function(index, value) {
				markup+= '<option value="'+value+'">'+value+'</option>';
			});
			//console.log("value for "+cat.name+": "+JSON.stringify(val));
			markup+= '</select>';
			
			callfun = false;
			
			//console.log("value for "+cat.name+": "+JSON.stringify(cat.value));
		}
		
		markup='<div class="form-group pull-in clearfix"><div class="col-sm-12"><label>'+cat.name+'</label>'+markup+'</div></div>'
			+'<div id="_'+cat.name+'_parent"></div>';
		
		//$('#'+_div).html('');
		$('#'+_div).append(markup);
		
		
		if(callfun==true) {
			//alert("callfun");
			$("[id='_cat_"+cat.name+"']").on('change', function () {
		        var val = this.options[this.selectedIndex].value;
				//alert(val);
				
			});
		}	
		
		selpick();
		
		
		
	}
	
	function checkNodes(_category, _parent_category, _idcus) {
		//alert(_category+' '+_parent_category);
		
		$.ajax({ 
			url: "../../servlet/ws/GetCustomersCategory2?idcus="+_idcus+"&category="+_category+"&parent_category="+_parent_category,
		   	method:"POST",
		   	dataType: "json",       
		   	beforeSend: function( xhr ) {
		   	    showLoader2();
		   	},
		   	success: function(data)  
	   		{
		   		hideLoader2();
	  			
		   		if(data.length>0) {
		   			$('#_cat').show();
		   			//console.log(JSON.stringify(data))
		   			var cat = data;
		   			$.each(cat, function(index, cat) {
		   				//parseCategory(cat);
		   				
		   				//alert(JSON.stringify(cat));
		   				
		   				$('#_'+_parent_category+'_parent').html('');
		   				//$('#_'+_parent_category+'_parent').append(JSON.stringify(cat));
		   				
		   				parseCategory(cat, _idcus, '_'+_parent_category+'_parent');
		   				
		   			});
		   			
		   		}
		   		
	   		}   
	 	});
	}
	
	function getStoresFromCategory() {
		
		var _str = $( "#_catfrm" ).serialize();
		console.log(_str);
		
		$.ajax({
		      type: "POST",
		      url: "../../servlet/ws/GetStoreFromCategory",
		      data: _str,
		      dataType: "json",
		      success: function(result)
		      {
		         //alert(JSON.stringify(result));
		    	
				
				$('#idstores').val(result.stores);
				$('#group').val('4');
				$( "#deskForm" ).submit();
				 
		      },
		      error: function()
		      {
		        alert("Chiamata fallita, si prega di riprovare...");
		      }
		});
	}
	
	function getNodes2() {
		getStoresFromCategory();
	}
	
	////////////////////////////
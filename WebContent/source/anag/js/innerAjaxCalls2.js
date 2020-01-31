

function getVendors(vendorId,data){
	if(vendorId != -2){
		 $.ajax({
				url: '../../ats/GetVendor',
				type: 'GET',
				dataType: "json",
				success: function (data) {
								 var json = data;
								 var selectItems="";
								 var select = document.getElementById('idvendor');
									for(var i = 0; i < json.listings.length; i++) {
										var obj = json.listings[i];
										selectItems += '<option value="'+obj.value+'">'+obj.label+'</option>';
										//$('#vendorselect').append('<option value="'+obj.value+'">'+obj.label+'</option>');
										
									}
									select.innerHTML = selectItems;
									if(vendorId != -1)
										$('#idvendor').val(vendorId);
									$('#idvendor').selectpicker({
									    liveSearch: true,
									    tickIcon: 'fa fa-check'
									 });
									$('#idvendor').addClass('myCheckS');
									$("#loadVendor").removeClass('spinner');
						}
				});
	 }else{
		 $("#loadVendor").addClass('spinner');
		 $('#idvendor').selectpicker('destroy');
			var json = data;
			var selectItems="";
			var select = document.getElementById('idvendor');
				for(var i = 0; i < json.vendorList.length; i++) {
					var obj = json.vendorList[i];
					
					selectItems += '<option value="'+obj.value+'">'+obj.label+'</option>';
					//$('#brandselect').append('<option value="'+obj.value+'">'+obj.label+'</option>');
					
				}
			select.innerHTML = selectItems;
			$('#idvendor').val(json.vendor);
			$('#idvendor').selectpicker({
			    liveSearch: true,
			    tickIcon: 'fa fa-check'
			 });
		$('#idvendor').addClass('myCheckS');
		$("#loadVendor").removeClass('spinner');
	 }
	 
 }
 
 function getBrands(brandId,data){
	 if(brandId != -2){
		  $.ajax({
			url: '../../ats/GetBrand',
			type: 'GET',
			dataType: "json",
			success: function (data) {
							 var json = data;
							 var selectItems="";
							 var select = document.getElementById('idbrand');
								for(var i = 0; i < json.listings.length; i++) {
									var obj = json.listings[i];
									
									selectItems += '<option value="'+obj.value+'">'+obj.label+'</option>';
									//$('#brandselect').append('<option value="'+obj.value+'">'+obj.label+'</option>');
									
								}
							select.innerHTML = selectItems;
							if(brandId != -1)
								$('#idbrand').val(brandId);
							$('#idbrand').selectpicker({
							    liveSearch: true,
							    tickIcon: 'fa fa-check'
							 });
							$('#idbrand').addClass('myCheckS');
							$("#loadBrand").removeClass('spinner');
					}
			});
	 }else{
		 $("#loadBrand").addClass('spinner');
		 $('#idbrand').selectpicker('destroy');
			var json = data;
			var selectItems="";
			var select = document.getElementById('idbrand');
				for(var i = 0; i < json.brandList.length; i++) {
					var obj = json.brandList[i];
					
					selectItems += '<option value="'+obj.value+'">'+obj.label+'</option>';
					//$('#brandselect').append('<option value="'+obj.value+'">'+obj.label+'</option>');
					
				}
			select.innerHTML = selectItems;
			$('#idbrand').val(json.brand);
			$('#idbrand').selectpicker({
			    liveSearch: true,
			    tickIcon: 'fa fa-check'
			 });
			$('#idbrand').addClass('myCheckS');
			$("#loadBrand").removeClass('spinner');
	 }
 }
 
 function getCategories(categoryId, data){
	 if(categoryId != -2){
		  $.ajax({
			url: '../../ats/GetCategory',
			type: 'GET',
			dataType: "json",
			success: function (data) {
							 var json = data;
							 var selectItems="";
							 var select = document.getElementById('idcategory');
								for(var i = 0; i < json.listings.length; i++) {
									var obj = json.listings[i];
									selectItems+='<option value="'+obj.value+'">'+obj.label+'</option>';
									//$('#categoryselect').append('<option value="'+obj.value+'">'+obj.label+'</option>');
								}
							select.innerHTML = selectItems;
							if(categoryId != -1)
								$('#idcategory').val(categoryId);
							$('#idcategory').selectpicker({
							    liveSearch: true,
							    tickIcon: 'fa fa-check'
							 });
							$('#idcategory').addClass('myCheckS');
							$("#loadCategory").removeClass('spinner');
					}
			});
	 }else{
		 $("#loadCategory").addClass('spinner');
		 $('#idcategory').selectpicker('destroy');
			var json = data;
			var selectItems="";
			var select = document.getElementById('idcategory');
				for(var i = 0; i < json.categoryList.length; i++) {
					var obj = json.categoryList[i];
					
					selectItems += '<option value="'+obj.value+'">'+obj.label+'</option>';
					//$('#brandselect').append('<option value="'+obj.value+'">'+obj.label+'</option>');
					
				}
			select.innerHTML = selectItems;
			$('#idcategory').val(json.category);
			$('#idcategory').selectpicker({
			    liveSearch: true,
			    tickIcon: 'fa fa-check'
			 });
			$('#idcategory').addClass('myCheckS');
			$("#loadCategory").removeClass('spinner');
	 }
 }
 
 function getCountries(countryId){
	   $.ajax({
		url: '../../ats/GetCountry',
		type: 'GET',
		dataType: "json",
		success: function (data) {
						 var json = data;
						 var selectItems="";
						 var select = document.getElementById('country');
							for(var i = 0; i < json.listings.length; i++) {
								var obj = json.listings[i];
								selectItems+= '<option value="'+obj.value+'">'+obj.label+'</option>';
									//$('#countryselect').append('<option value="'+obj.value+'">'+obj.label+'</option>')
							}
							select.innerHTML = selectItems;
							if(countryId != -1)
								$('#country').val(countryId);
							$('#country').selectpicker({
							    liveSearch: true,
							    tickIcon: 'fa fa-check'
							 });
							$('#country').addClass('myCheckS');
							$("#loadCountry").removeClass('spinner');
				}
		});
 }
 function getCustomers(customers){
		 $.ajax({
			 url: '../../ats/GetCustomers',
			 type: 'GET',
			 data:{username:username},
			 dataType: "json",
			 success: function (data) {
				 var json = data;
				 var selectItems="";
				 var select = document.getElementById('idcustomer');
				 for(var i = 0; i < json.customers.length; i++) {
					 var obj = json.customers[i];
					 selectItems+= '<option value="'+obj.id+'">'+obj.description+'</option>';
					 //$('#countryselect').append('<option value="'+obj.value+'">'+obj.label+'</option>')
				 }
				 select.innerHTML = selectItems;
				 if (customers != null){
					 for(var i = 0; i < customers.length; i++) {
						 $("#idcustomer option[value='" + customers[i] + "']").prop("selected", true);
					 }
				 }
				 $('#idcustomer').selectpicker({
					 liveSearch: true,
					 tickIcon: 'fa fa-check'
				 });
				 $('#idcustomer').addClass('myCheckS');
				 $("#loadCustomer").removeClass('spinner');
			 }
		 });
			
 }
 function validateInput(tipoForm){
	 if(tipoForm =="D")
			return true;
	 else if(tipoForm == "A"){
		 if($('#idcustomer').val() != null)
				return true;
		 $('#idcustomer').parent().closest('div').find('button').css('border-color','red');
		 return false;
	 }
	 else{
		 var result = true;
		 $('#actionForm').find('.myCheck').each(function(){
			 if($(this).val() =="" || $(this).val() == null ){
				 $(this).addClass('myDanger');
				 result = false;
			 }
		 });
		 $('#actionForm').find('.myCheckS').each(function(){
			 if($(this).val() =="" || $(this).val() == null ){
				 //$(this).css('border-color','red');
				 $(this).parent().closest('div').find('button').css('border-color','red');
				 result = false;
			 }
		 });
		 return result;
	 }
 }

	$('#actionForm').on('click','.mySDiv', function(){
		$(this).find('.myCheckS').closest('div').find('button').css('border-color','#cbd5dd');
	});
 $('#actionForm').on('click','.myCheck', function(){
	 $(this).removeClass('myDanger');
 });
 

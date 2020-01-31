


 function getTypes(typeId){
	   $.ajax({
		url: '../../ats/GetAuditType',
		type: 'GET',
		dataType: "json",
		success: function (data) {
						 var json = data;
						 var selectItems="";
						 var select = document.getElementById('idtype');
							for(var i = 0; i < json.listings.length; i++) {
								var obj = json.listings[i];
								selectItems+= '<option value="'+obj.value+'">'+obj.label+'</option>';
									//$('#countryselect').append('<option value="'+obj.value+'">'+obj.label+'</option>')
							}
							select.innerHTML = selectItems;
							if(typeId != -1)
								$('#idtype').val(typeId);
							$('#idtype').selectpicker({
							    liveSearch: true,
							    tickIcon: 'fa fa-check'
							 });
							$('#idtype').addClass('myCheckS');
							 $("#loadTypes").removeClass('spinner');
				}
		});
	  
 }
 
 function getStores(storeId,customerId){
	 $.ajax({
		 url: '../../ats/GetStores',
		 type: 'GET',
		 data: {idusr:idusr,idcustomer:customerId},
		 dataType: "json",
		 success: function (data) {
			 var json = data;
			 var selectItems="";
			 var select = document.getElementById('idstore');
			 for(var i = 0; i < json.listings.length; i++) {
				 var obj = json.listings[i];
				 selectItems+= '<option value="'+obj.value+'">'+obj.label+'</option>';
				 //$('#countryselect').append('<option value="'+obj.value+'">'+obj.label+'</option>')
			 }
			 select.innerHTML = selectItems;
			 if(storeId != -1)
				 $('#idstore').val(storeId);
			 $('#idstore').selectpicker({
				 liveSearch: true,
				 tickIcon: 'fa fa-check'
			 });
			 $('#idstore').addClass('myCheckS');
			 $("#loadStores").removeClass('spinner');
		 }
	 });
 }

function getItems(auditItems,customerId){
	$.ajax({
		url: '../../ats/GetItems',
		type: 'GET',
		dataType: "json",
		data: {idcustomer:customerId},
		success: function (data) {
									 var json = data;
									 var selectItems="";
									 var select = document.getElementById('iditem');
									 for(var i = 0; i < json.listings.length; i++) {
										 var obj = json.listings[i];
										 selectItems+= '<option value="'+obj.id+'">'+obj.name+'</option>';
									 }
									 select.innerHTML = selectItems;
									 if(auditItems != null){
										 for(var i = 0; i < auditItems.length; i++) {
											 var obj = auditItems[i];
											 //alert(obj.idItem);
											 $("#iditem option[value='" + obj.idItem + "']").prop("selected", true);
											 //$("#iditem").val(obj.idItem).prop("selected", true);
										 }
									 }
									
									 $('#iditem').selectpicker({
										 liveSearch: true,
										 tickIcon: 'fa fa-check'
									 });
									 $('#iditem').addClass('myCheckS');
									 $("#loadItems").removeClass('spinner');
								}
		});
}
function getCustomers(customerId){
	 $.ajax({
		 url: '../../ats/GetCustomers',
		 type: 'GET',
		 data:{username:idusr},
		 dataType: "json",
		 success: function (data) {
			 var json = data;
			 var selectItems="";
			 var select = document.getElementById('idcustomer');
			 selectItems = '<option class="disabled" value="">Scegli un customer...</option>';
			 for(var i = 0; i < json.customers.length; i++) {
				 var obj = json.customers[i];
				 selectItems+= '<option value="'+obj.id+'">'+obj.description+'</option>';
				 //$('#countryselect').append('<option value="'+obj.value+'">'+obj.label+'</option>')
			 }
			 select.innerHTML = selectItems;
			 if(customerId != null){
				 $('#idcustomer').val(customerId);
				 $('#idcustomer').attr('disabled','true');
				 $('#myBody').append("<input type='hidden' id='idcustomer' name='idcustomer' value='"+customerId+"' class='form-control' data-required='true'>");
				 
			 }
				 
			 else
				 $('#idcustomer').val("");
			
			 $('#idcustomer').selectpicker({
				 liveSearch: true,
				 tickIcon: 'fa fa-check'
			 });
			 $('#idcustomer').addClass('myCheckS');
			 $("#loadCustomer").removeClass('spinner');
		 }
	 });
}

$('body').on('change','#idcustomer',function(){
	if($(this).val() != ""){
		var idCustomer = $(this).val();
		$("#loadStores").addClass('spinner');
		$("#loadItems").addClass('spinner');
		$('#idstore').removeClass('myCheckS');
		$('#iditem').removeClass('myCheckS');
		$('#idstore').selectpicker('destroy');
		$('#iditem').selectpicker('destroy');
		getStores(null,idCustomer);
		getItems(null,idCustomer);
	}
});

function validateInput(tipoForm){
	if(tipoForm =="D" || tipoForm == "C")
		return true;
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
			 //$(this).selectpicker('setStyle', 'myDanger','add');
			 result = false;
		 }
	 });
	 return result;
}

$('#actionForm').on('click','.mySDiv', function(){
	 //$(this).find('.myCheckS').selectpicker('setStyle', 'myDanger','remove');
	//alert("nijmkol");
	$(this).find('.myCheckS').closest('div').find('button').css('border-color','#cbd5dd');
});
$('#actionForm').on('click','.myCheck', function(){
	 $(this).removeClass('myDanger');
});

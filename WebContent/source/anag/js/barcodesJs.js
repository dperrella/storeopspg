function createElement(id,barcode){
	 var currentDiv = document.getElementById("myBody"); 
	 var localId = id;
	 var newDiv = document.createElement("div"); 
	 newDiv.setAttribute("class","input-group");
	 newDiv.setAttribute("id","barcode"+id);
	 var newInput = document.createElement("input");
	 newInput.setAttribute("type","text");
	 newInput.setAttribute("class","form-control");
	 newInput.setAttribute("value",barcode);
	 newInput.readOnly= true;
	 var newSpan = document.createElement("span"); 
	 newSpan.setAttribute("class","input-group-addon btn-default myMinus");
	 newSpan.setAttribute("id",id);
	 var newI = document.createElement("i"); 
	 newI.setAttribute("class","fa fa-minus");
	 newI.setAttribute("aria-hidden","true");
	 newI.setAttribute("id",id);
	 newSpan.appendChild(newI);
	 newDiv.appendChild(newInput);
	 newDiv.appendChild(newSpan);
	 var newDashed = document.createElement("div");
	 newDashed.setAttribute("class","line line-dashed b-b line-lg pull-in");
	 currentDiv.appendChild(newDiv);
	 currentDiv.appendChild(newDashed);
}
function appendButton(label){
	 var currentDiv = document.getElementById("myBody"); 
	 var addButton = document.createElement("button");
	 addButton.setAttribute("type","button");
	 addButton.setAttribute("class","btn btn-primary");
	 addButton.setAttribute("id","addBarc");
	 addButton.textContent= label;
	 currentDiv.appendChild(addButton);
}
$('#myBody').on('click','#addBarc',function(){
	var value = prompt("Insert new Barcode", "");
	if(value != null){
		$("#myBody").addClass('spinner');
		$.ajax({
			url: '../../ats/SetBarcode',
			type: 'GET',
			data:{type:"I",idItem:id,barcode:value},
			dataType: "json",
			success: function (data) {
							 var json = data;
							 $('#myBody').html('');
								for(var i = 0; i < json.barcodeList.length; i++) {
									var obj = json.barcodeList[i];
									createElement(obj.id,obj.barcode);
								}
								 appendButton(label);
								 $("#myBody").removeClass('spinner');
					}
			});
	}
	
});

$('#myBody').on('click','.myMinus',function(){
	var value = confirm("Are you sure to proceed?");
	var barId = this.id;
	if(value){
		$("#myBody").addClass('spinner');
		$.ajax({
			url: '../../ats/SetBarcode',
			type: 'GET',
			data:{type:"D",idItem:id,barcode:value,id:barId},
			dataType: "json",
			success: function (data) {
				var json = data;
				$('#myBody').html('');
				for(var i = 0; i < json.barcodeList.length; i++) {
					var obj = json.barcodeList[i];
					createElement(obj.id,obj.barcode);
				}
				appendButton(label);
				$("#myBody").removeClass('spinner');
			}
		});
	}
	
});
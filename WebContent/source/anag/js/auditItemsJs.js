function createElement(id,note,nome,descrizione,risultato, url){
	 var currentDiv = document.getElementById("myBody"); 
	 var localId = id;
	 var newDiv = document.createElement("div"); 
	 newDiv.setAttribute("class","form-group pull-in clearfix");
	 newDiv.setAttribute("style","display:block");
	 newDiv.setAttribute("id","item"+id);
	 
	 var colDivx = document.createElement("div");
	 colDivx.setAttribute("class","col-sm-1");
	 
	 
	 var image = document.createElement("img");
	 image.setAttribute("class","img-circle pull-left");
	 image.setAttribute("style","width: 60px; height: 60px");
	 
	 image.setAttribute("src", url);
	 
	 if(url!='') {
		 colDivx.appendChild(image);
	 }
	 
	 
	 var colDiv = document.createElement("div");
	 colDiv.setAttribute("class","col-sm-7");
	 var a = document.createElement("a");
	 a.setAttribute("href","#");
	 a.setAttribute("style","cursor:default");
	 var outerSpan = document.createElement("span");
	 var h3Span = document.createElement("span");
	 h3Span.setAttribute("class","h3 block");
	 h3Span.textContent=nome;
	 var h5Span = document.createElement("span");
	 h5Span.setAttribute("class","h5 block");
	 h5Span.textContent=descrizione;
	 
	 var h5Span2 = document.createElement("span");
	 h5Span2.setAttribute("class","h5 block ");
	 h5Span2.textContent="Note: "+note;
	 
	 
	 
	 
	 
	 var colDiv2 = document.createElement("div");
	 colDiv2.setAttribute("class","col-sm-4");
	 colDiv2.setAttribute("style","margin-top:0.7%");
	 var newSpan = document.createElement("span");
	 newSpan.setAttribute("id",id);
	 newSpan.setAttribute("value",'0');
	 if(risultato == '0')
		 newSpan.setAttribute("class","input-group-addon btn-default active myPlus line"+id);
	 else
		 newSpan.setAttribute("class","input-group-addon btn-default myPlus line"+id);
	 var newI = document.createElement("i"); 
	 newI.setAttribute("class","fa fa-check");
	 newI.setAttribute("aria-hidden","true");
	 var newSpan2 = document.createElement("span"); 
	 newSpan2.setAttribute("id",id);
	 newSpan2.setAttribute("value",'1');
	 if(risultato == '1')
		 newSpan2.setAttribute("class","input-group-addon btn-default active myPlus line"+id);
	 else
		 newSpan2.setAttribute("class","input-group-addon btn-default myPlus line"+id);
	 var newI2 = document.createElement("i"); 
	 newI2.setAttribute("class","fa fa-times");
	 newI2.setAttribute("aria-hidden","true");
	 var newSpan3 = document.createElement("span"); 
	 newSpan3.setAttribute("id",id);
	 newSpan3.setAttribute("value",'2');
	 if(risultato == '2')
		 newSpan3.setAttribute("class","input-group-addon btn-default active myPlus line"+id);
	 else
		 newSpan3.setAttribute("class","input-group-addon btn-default myPlus line"+id);
	 var newI3 = document.createElement("i"); 
	 newI3.setAttribute("class","fa fa-dropbox");
	 newI3.setAttribute("aria-hidden","true");
	 newSpan.appendChild(newI);
	 newSpan2.appendChild(newI2);
	 newSpan3.appendChild(newI3);
	 colDiv2.appendChild(newSpan);
	 colDiv2.appendChild(newSpan2);
	 colDiv2.appendChild(newSpan3);
	 a.appendChild(h3Span);
	 a.appendChild(h5Span);
	 a.appendChild(h5Span2);
	 
	 outerSpan.appendChild(a);
	 colDiv.appendChild(outerSpan);
	 newDiv.appendChild(colDiv);
	 newDiv.appendChild(colDivx);
	 
	 
	 newDiv.appendChild(colDiv2);
	 
	 var newDashed = document.createElement("div");
	 newDashed.setAttribute("class","line line-dashed b-b line-lg pull-in");
	 currentDiv.appendChild(newDiv);
	 
	 currentDiv.appendChild(newDashed);
}

$('#myBody').on('click','.myPlus',function(){
	
	var _state = parseInt($('#_state').val());
	
	if(_state==0) {
	
		//$('#myModal1').modal({backdrop: 'static', keyboard: false});
		$('#myClose').attr("onclick","");
		$('#myClose2').attr("onclick","");
		var idItem = $(this).attr('id');
		$('.line'+idItem).each(function(){
			$(this).removeClass("active");
		});
		if($(this).hasClass('active'))
			$(this).removeClass("active");
		else{
			$(this).addClass("active");
			var result = $(this).attr('value');
			  $.ajax({
					url: '../../ats/SetAuditItem',
					type: 'GET',
					data: {type:"E",idaudit:id,iditem:idItem,result:result,note:""},
					success: function(data){
						//$('#myModal1').modal({backdrop:false, keyboard: true});
						$('#myClose').attr("onclick","$('.modal').remove(); $('div.modal-backdrop').remove();");
						$('#myClose2').attr("onclick","$('.modal').remove(); $('div.modal-backdrop').remove();");
					}
					});
		}
	}
	else {
		alert('Audit is closed.\nCannot change Item Status');
	}
	
	
});





//$('.modal-content').on('click','#saveButton',function(){
//	$('.myPlus').each(function(){
//		if($(this).hasClass('active')){
//			
//		}
//	});
//});

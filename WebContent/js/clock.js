
//window.setTimeout(clocks, 10);
window.setInterval(clocks, 1000);
function clocks(){
	
	var usr = "";	
	$.ajax({
		url : "../../smaclocks",
		data : {
			usr : usr
		},
		success : function(responseText) {
			document.getElementById("timepicker1").value = responseText;
			//myVar = setTimeout(clocks, 1000);
			//window.setTimeout(clocks, 1000);
		}
	});	
	
}

var flag = true;
function clocken(){
	
	if (flag == true){

		document.getElementById("timepicker1").style.width = "0px";
		flag = false;
	}else{

		document.getElementById("timepicker1").style.width = "150px";
		flag = true;
	}
	
}



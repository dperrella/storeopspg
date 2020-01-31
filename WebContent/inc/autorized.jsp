<!--
<div id="controls">
    <input type="password" id="pwds" />
    <button id="unlocked" onclick="javascript: setTimeout(Unlockeds, 700);">Unlock screen</button>
</div>
-->
<script src="../../js/clock/jstz-1.0.4.min.js"></script>
<script>
//window.setInterval(clocks, 1000);
function clocks(){
	
	/*
    timezone = jstz.determine()
    tz = timezone.name();
    
	var usr = "pp";
	$.ajax({
		url : "../../smaclocks",
		data : {
			usr : usr,
			timezone : tz
		},
		success : function(responseText) {
			document.getElementById("timepicker1").value = responseText;			
		}
	});
	*/

}

//refreshSmsId = setInterval(checkSms, 1000);
//refreshNotId = setInterval(checkNot, 1000);

/*
//function checkSms() {
	
//	$.ajax({
//		url : "../../GetSms",
//		data : {
//			username : '<%=session.getAttribute("username")%>'
//		},
//		success : function(data) {
//			$('#sms').html(data);		
//		}
//	});	
	
//}
*/

function checkNot() {
	
	/*
	//$.ajax({
	//	url : "../../GetNot",
	//	data : {
	//		username : '<%=session.getAttribute("username")%>'
	//	},
	//	success : function(data) {
	//		$('#not').html(data);		
	//	}
	//});	
	*/
	
}


function change() {
	
	var user = $('#users').val();
	
	location.href='../../login-esegui-redirect.htm?username='+user;
	
}


</script>
 
<link rel="stylesheet" href="../../js/dist/css/bootstrap-select.css">
<script src="../../js/dist/js/bootstrap-select.js"></script>

<div id="myModalUserss" class="modal fade" role="dialog">
  <div class="modal-dialog" style="width: 800px;">

    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Change Users</h4>
      </div>
      <div class="modal-body" id="Sellll" style="width:100%;">
		<select id="users" name="users" title="Change User" class="selectpicker remove-example" data-live-search="true" style="background: #fff; min-width:100%; width:100%; border: 1px;">
			<%//=session.getAttribute("Exch") %>
		</select>   
		<div id="users" name="users" style="background: #eee; min-width:100%; width:100%; border: 1px;"></div>     
      </div>
      <div class="modal-footer" style="margin-top: 20px;">
        <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-power-off" aria-hidden="true"></i> Close</button>
        <button type="button" class="btn btn-default" onclick="change()" data-dismiss="modal"><i class="icon-user" aria-hidden="true"></i>&nbsp;<i class="fa fa-exchange" aria-hidden="true"></i> Change</button>
      </div>
    </div>

  </div>
  
</div>

<script>
function GetPeopless(){
	var username = "<%=session.getAttribute("username")%>";
	
	$.ajax({
		url: '../../GetUsersChange',
		data : {
			username : username
		},
		success : function(responseText) {
			$('#users').html(responseText);
			$('.remove-example').selectpicker('refresh');
		}
	
	});
}
</script>

<script>
// Locked Page///
/*
//var content = $('.row row-sm'),
//contents;
//var contr = $('#controls').html();
//function GetPagesLocked(){
//	    contents = content.html();
//	    content.empty();
//	    content.html(contr);
//	    $(this).hide().siblings().show();
//	    $("#locks").removeClass("fa fa-unlock-alt").addClass("fa fa-lock");
//	    GetLcokUnlock("L");    
//}

//function Unlockeds(){
//	GetLcokUnlock("U");
//}

//function GetLcokUnlock(flags){
	
//	$.ajax({
//		url: '../../GetUnlocked',
//		data : {
//			flag : flags,
//			body : contents,
//			pwd : pwds
//		},
//		success : function(responseText) {
//			alert(responseText)
//			if (responseText == "Y"){
//				var bod = "<%//=session.getAttribute("Body")%>";
//			    content.html(bod);
//			    $(this).closest('div').children().hide().first().show();
//			}else{
//				content.empty();
//			}
//		}
	
//	});
//}
*/
</script>

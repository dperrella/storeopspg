<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>Gift Card | <%=propLang.get("APPS") %></title>
  <link rel="shortcut icon" href="../../images/logos.ico">
  <meta name="description" content="ZPL" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>

  <script src="../../js/jquery.min.js"></script>
  <script src="../../js/bootstrap.js"></script>
  
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <script src="../../js/app.js"></script>
  <script src="../../js/gethoms.js"></script>    
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>

<!--[if lt IE 9]>
   <script src="../../js/ie/html5shiv.js"></script>
   <script src="../../js/ie/respond.min.js"></script>
   <script src="../../js/ie/excanvas.js"></script>
<![endif]-->
<style type="text/css">
     #loading-image {
		background-color: #333;
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 1;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
</style>  

</head>
<body>
<div id="loading-image">
		<img src="../../images/loading.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 208px;
				   height: 13px;
				   margin-top: -8px;
				   margin-left: -104px;" />
  </div>
  <div id="loading-image2" style="display:none">
		<img src="../../images/spinner.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 200px;
				   height: 200px;
				   margin-top: -100px;
				   margin-left: -50px;" />
  </div>
  <section class="vbox">
    <header class="bg-white-only header header-md navbar navbar-fixed-top-xs">
  
  	<%@ include file="../../inc2/top.jsp" %>
      
      <div class="navbar-right ">
        <ul class="nav navbar-nav m-n hidden-xs nav-user user">
          <%@ include file="../../inc2/topMenu.jsp" %>
          
          <%@ include file="../../inc2/top2.jsp" %>
        </ul>
      </div>      
    </header>
    <section>
      <section class="hbox stretch">
        <!-- .aside -->
        <aside class="bg-black dk nav-xs aside hidden-print" id="nav">           
          <section class="vbox">
            <section class="w-f-md scrollable">
              <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">
                

                <!-- nav -->                 
                
                <%@ include file="../../inc/menu-left.jsp" %>
                
                <!-- / nav -->
                
                
              </div>
            </section>
  
			<%@ include file="../../inc2/leftFooter.jsp" %>
			
          </section>
        </aside>

        <section id="content">
          <section class="hbox stretch">   
           <%@ include file="../../inc2/menuApp8.jsp" %>       
            <section>
              <section class="vbox">
                <section class="scrollable padder " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="Gift Card " %></h2>

<!-- Fine blocchi -->			              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-gift" aria-hidden="true"></i> <%="Gift Card"%>
							</div>
							<!-- /.panel-heading -->
							 
							<div class="panel-body">
						  	<section class="panel wrapper" style="display: none;">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="printer.htm" class="btn btn-default"><%="Nuova Gift Card"%></a></li>
								</ul>
							</div>
						  </section>
			              <div class="table-responsive">
			              <form action="../../smfileupl" method="POST" enctype="multipart/form-data">


			              <table>
			              	<tr>
			              		<td><%=propLang.get("tick_crca") %></td>
			              		<td>&nbsp;
									<div class="btn-group" data-toggle="buttons">
									  <label class="btn btn-default active" onclick="Vis2(true)">
									    <input type="radio" id="radiosi" name="radiobutton" value="true" checked> <%=propLang.get("tick_crea") %>
									  </label>
									  <label class="btn btn-default" onclick="Vis2(false)">
									    <input type="radio" id="radiono" name="radiobutton" value="false"> <%=propLang.get("tick_cari")+"&nbsp;" %>
									  </label>
									</div>
								</td>
			              	</tr>
			              	<tr><td colspan="2">&nbsp;</td></tr>			              
			              	<tr id="TRcrea">
			              		<td><%=propLang.get("tick_buon") %></td>
			              		<td>&nbsp;<input type="text" id="numgiftcard" style="width: 80px;"  onkeyup="numerics(this.value)" /></td>
			              	</tr>
			              	<tr><td colspan="2">&nbsp;</td></tr>
			              	<tr id="TRcarica">
			              		<td><%=propLang.get("tick_csv") %></td>
			              		<td>							        
			              		<div class="input-group">
							                <label class="input-group-btn">
							                    <span class="btn btn-default">
							                        Browse&hellip; <input type="file" style="display: none;" multiple id="file" name="file">
							                    </span>
							                </label>
							                <!-- <input type="text" class="form-control" style=" width: 94%;" id="brandlogo" readonly value=""/>-->
							                <input type="text" class="form-control" style=" width: 94%;" id="brandlogo" readonly value="" />
							        </div>
							   </td>
			              	</tr>
			              	<tr><td colspan="2">&nbsp;</td></tr>			              	
			              </table>
			              
			              <!--
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
							        <th width="20%">Date Time</th>
							        <th width="15%">Package Session</th>
							        <th width="15%">Package Number</th>
							        <th width="43%">Path</th>
							        <th width="7%">Download</th>
							      </tr>
							  </thead>	      					  
	      					  <tbody>
							  <c:forEach items="${Pepl}" var="Pepl" varStatus="status">
								<tr>
								  <td><span class="label bg-light">${Pepl.ids}</span></td>
								  <td>${Pepl.cus}</td>
			                      <td>${Pepl.devn}</td>
								  <td>${Pepl.lastt}</td>
								  <td align="center"><a href="${Pepl.lastt}" download><i class="fa fa-download" aria-hidden="true"></i></a></td>	                      			                      
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					-->
		  				  </div>
							</div>
		  				 		<div style="margin-left: 16px;">
										<table width="100%">
								              <tbody>
								              <tr><td>&nbsp;</td></tr>
								              <tr><td align="left">
								              <button type="button" id="back" class="btn btn-default" onclick="document.location.href='index.htm'"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> <%=propLang.get("tick_back") %></button>
								              <button type="button" id="delete" class="btn btn-default" onclick="Erase()"><i class="fa fa-refresh" aria-hidden="true"></i> <%=propLang.get("tick_canc") %></button> 								                                 
								              <!--  <button type="button" id="delete" class="btn btn-default" onclick="document.location.href='printer.htm'"><i class="fa fa-refresh" aria-hidden="true"></i> Cancel</button>-->
											  <button type="submit" id="print" name="print" class="btn btn-default"><i class="fa fa-upload"></i> <%=propLang.get("tick_uplo") %></button>
											  <!-- <button type="submit" id="print" onclick="Uploads()" class="btn btn-default"><i class="fa fa-upload"></i> Upload</button>-->								                                  
								              </td>
								        </tr></tbody></table>
								</div>
</form>															
							<!-- /.panel-body -->
						</div>
								</div>
					</div>	
			
													            		
			              		<!--  -->

		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>
                
                <!-- side content -->
<!-- 
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	            
						<%@ include file="../../inc/smartcontrol-menu-right.jsp" %>
				
	                </section>
	                
	                <footer class="footer footer-md bg-black">
	                  <form action="#" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                        </span> 
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            -->
	            <!-- / side content -->
                
               
                
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  
  	<script src="../../js/alertify.min.js"></script>
    <link href="../../css/alertify.core.css" rel="stylesheet">
	<link href="../../css/alertify.default.css" rel="stylesheet">
  <script>
    $('#example').dataTable( {
      "language": {
          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_",
          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
          "infoFiltered": "(Filtrati da _MAX_ records totali)",
          "decimal": ",",
          "thousands": ".",
          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
      },
      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
      "aaSorting": [],
      "iDisplayLength": 10
  } );
    
  </script>
  <script>
var Mod,Id;
function oper(mod,id){
	Mod = mod;
	Id = id;
}  
function SavesDne(){
	
	if (document.getElementById("choiseenabled").value == ""){
		alertify.log("<%=propLang.get("sm_obb_def") %>");
		document.getElementById("choiseenabled").focus();
		return;
	}
	
	if (document.getElementById("choisereason").value == ""){
		alertify.log("<%=propLang.get("sm_obb_def") %>");
		document.getElementById("choisereason").focus();
		return;
	}
	if (document.getElementById("choiseupdating").value == ""){
		alertify.log("<%=propLang.get("sm_obb_def") %>");
		document.getElementById("choiseupdating").focus();
		return;
	}
	
choiseenabled = document.getElementById("choiseenabled").value;
choisereason = document.getElementById("choisereason").value;
choiseupdating = document.getElementById("choiseupdating").value;
choisenotes = document.getElementById("choisenotes").value;

	$.ajax({
		url : "../../smcontrol",
		data : {
			tab : Mod,
			id : Id,
			choiseenabled : choiseenabled,
			choisereason : choisereason,
			choiseupdating : choiseupdating,
			choisenotes : choisenotes
		},
		success : function(responseText) {
			location.href = "smartcontrol.htm";
			alertify.success("<%=propLang.get("msgSuccess")%>");
		}
	});

}
</script>
<script>
var delay = "<%=session.getAttribute("Timing")%>";
if (delay != "" && delay > 0){
	myVar = setTimeout(Refreshh,delay);
}

function Refreshh(){
	location.href='smartcontrol.htm';
}

</script>
<script>
function Prints(){
	
	if (document.getElementById("numzpl").value == ""){
		alertify.error("Numero di ZPL Obbligatorio");
		document.getElementById("numzpl").focus();
		return;
	}
	
	
	$.ajax({
		url : "../../zplprint",
		data : {
			File : document.getElementById("HidfileZPL").value,
			SeqN : document.getElementById("HidSeqN").value,
			SeqS : document.getElementById("HidSeqS").value,
			Numero : document.getElementById("numzpl").value
		},
		success : function(responseText) {

			alertify.success("File Creato e Salvato in: " + responseText);
			var num = parseInt(document.getElementById("progressivo").innerHTML);
			num = num + parseInt(document.getElementById("numzpl").value);
			document.getElementById("progressivo").innerHTML = num;
			document.getElementById("down").href = responseText;
			document.getElementById("downfile").style.display = "inline";
	
		}
	});	
	
}

function Reload(obj){
	
	var Flag = obj.value;

		$.ajax({
			url : "../../smmodality",
			data : {
				flag : Flag,
			},
			success : function(responseText) {

				var pp = responseText;

				
				var parts = pp.split("$");
				var resp = parts[0]; 
				var flags = parts[1];

				document.getElementById("DivExample3").innerHTML = resp;
				
				if(flags == "ip"){
					document.getElementById("example3_info").style.visibility = "hidden";
					document.getElementById("example3_paginate").style.visibility = "hidden";
				}else if(flags == "all"){
					document.getElementById("example3_info").style.visibility = "visible";
					document.getElementById("example3_paginate").style.visibility = "visible";
					//$("#example3_length").val(100)
					var vals = $("[name='example3_length']");
					//vals.val(10);
					//$("[name='example3_length']").val(10).change();

					$("[name='example3_length']").val(10).trigger('change');
				}else{
					document.getElementById("example3_info").style.visibility = "hidden";
					document.getElementById("example3_paginate").style.visibility = "hidden";
					location.href='smartcontrol.htm';
				}
		
			}
		});	

}

/*
$('.paginate_button').on('click', function() {
	  alert($(this).attr('data-dt-idx'));
	  alert($(this).attr('aria-controls'));
});
*/
function numerics(ob){
	if (isNaN(ob)){
		alertify.error("Inserire solo valori numerici");
		document.getElementById("numgiftcard").value = "";
		document.getElementById("numgiftcard").focus();
		return;
	}
}

function Erase(){
	document.getElementById("numgiftcard").value = "";
} 

function Uploads(){
	

	alert("file = " + $('#file').val())

    var File = document.getElementById("file").files[0];

    var frmdata = new FormData();
    frmdata.append("File", File);
    var sub = document.getElementById("form1").action;
    alert(sub)
    sub = sub + "?uplfile="+File; 
    alert(sub)
    
    document.getElementById("form1").actin = sub; 
    document.getElementById("form1").submit();
    mostra_loading_screen();
    //alert("2")

}

$(function() {

	  // We can attach the `fileselect` event to all file inputs on the page
	  $(document).on('change', ':file', function() {
	    var input = $(this),
	        numFiles = input.get(0).files ? input.get(0).files.length : 1,
	        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	    input.trigger('fileselect', [numFiles, label]);
	  });

	  // We can watch for our custom `fileselect` event like this
	  $(document).ready( function() {
	      $(':file').on('fileselect', function(event, numFiles, label) {

	          var input = $(this).parents('.input-group').find(':text'),
	              log = numFiles > 1 ? numFiles + ' files selected' : label;

	          if( input.length ) {
	              input.val(log);
	          } else {
	              if( log ) alert(log);
	          }

	      });
	  });
	  
	}); 


/**/


$("#radexpsi").removeClass("btn btn-default").addClass("btn btn-default active");
$("#radexpno").removeClass("btn btn-default active").addClass("btn btn-default");
$('#TRcrea').css({pointerEvents: "visible"});
$('#TRcarica').css({pointerEvents: "none"});
document.getElementById("print").innerHTML = "<i class='fa fa-gift'></i> <%=propLang.get("tick_crea") %>";
$("#print").prop("type", "button");
$("#print").click(Create);

function Vis2(flag){

	if (flag == true){
		document.getElementById("brandlogo").value = "";
		document.getElementById("file").value = "";
		$("#radexpsi").removeClass("btn btn-default").addClass("btn btn-default active");
		$("#radexpno").removeClass("btn btn-default active").addClass("btn btn-default");
		$('#TRcrea').css({pointerEvents: "visible"});
		$('#TRcarica').css({pointerEvents: "none"});
		document.getElementById("print").innerHTML = "<i class='fa fa-gift'></i> <%=propLang.get("tick_crea") %>";
		$("#print").prop("type", "button");
		$("#print").click(Create);
	}else{		
		document.getElementById("numgiftcard").value = "";
		$("#radexpsi").removeClass("btn btn-default active").addClass("btn btn-default");
		$("#radexpno").removeClass("btn btn-default").addClass("btn btn-default active");
		$('#TRcarica').css({pointerEvents: "visible"});
		$('#TRcrea').css({pointerEvents: "none"});
		document.getElementById("print").innerHTML = "<i class='fa fa-upload'></i> <%=propLang.get("tick_cari") %>";
		$("#print").prop("type", "submit");
		$("#print").prop('onclick',null).off('click');
	}
		
}
var suc = "<%=session.getAttribute("Succ") %>";
if (suc != ""){
	
	if (suc == 0){
		alertify.success("Operazione Completata");	
	}else{
		alertify.error("Operazione NON Riuscita");
	}
	
}

function Create(){
	
	if (document.getElementById("numgiftcard").value == ""){
		alertify.log("Valorizzare campo Numero di buoni/Giftcard");
		document.getElementById("numgiftcard").focus();
		return;
	}
	
	$.ajax({
		url : "../../giftprint",
		data : {
			Numero : document.getElementById("numgiftcard").value
		},
		success : function(responseText) {
			alertify.success("Operazione avvenuta con successo");
		}
	});
	
}

</script>
<script>
//var err = "<%//=propLang.get("sm_notautorized") %>";
</script>
<!--  
<script src="../../js/autorized.js"></script>
<script src="../../js/clock.js"></script>
-->
<!-- 
<% //if (session.getAttribute("RIDC").equals("super") || session.getAttribute("RIDC").equals("countryadmin") || session.getAttribute("RIDC").equals("list-admin")) {%>
<script>
document.getElementById("id_notification").style.display = "inline";
</script> 
<% //} //else {%>
<script>
document.getElementById("id_notification").style.display = "none";
</script> 
<% //}%>
-->
<input type="hidden" id="HidfileZPL" value="<%=session.getAttribute("FileZPL")%>">
<input type="hidden" id="HidSeqN" value="<%=session.getAttribute("SeqN")%>">
<input type="hidden" id="HidSeqS" value="<%=session.getAttribute("SeqS")%>">
  <script>
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();
		
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
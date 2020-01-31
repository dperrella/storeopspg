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
  
  <title>Sales Situation - Closed Cash | <%=propLang.get("APPS") %></title>
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
	}</style>  

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
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="Sales Situation" %></h2>


				<form id="myForm" method="post">
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-money" aria-hidden="true"></i> <%="Close Cash" %>  
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive" style="overflow:hidden;">
							<table width="100%" style="overflow:hidden;">
								<tr>
									<td width="25%">Cod. Store 
										<select id="codstore" class="form-control" style="width: 80%;">
											<option value="">-- Seleziona Store --</option>
											${ComboCli}
										</select>
									</td>
									<td width="25%" align="left">Data <input type="text" id="datestore"  class="form-control" style="width: 80%;" readonly></td>
									<td width="25%" align="left">&nbsp;</td>
									<td width="25%" style="float: right;">
										<input type="button" onclick="Esegui()" class="btn btn-default" style="margin-top: 19px;" value="Esegui" />
									</td>
								</tr>
							</table>		  					
		  				  </div>
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>	
	</form>					
	<!--  -->	     
<section class="panel panel-default">
             <header class="panel-heading">
               <span id="Testata_" style="text-align: left;"><i class="fa fa-list-alt" aria-hidden="true"></i> Result</span>
             </header>
              <section class="panel wrapper"  id="subContent">
              	<div class="subnav" >         		
					<div class="row" id="ticket_list" style="visibility: hidden;">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-list-alt" aria-hidden="true"></i> <%="Close Cash List" %>  
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive" id="ticketResp1">
			              	<table id="test" class="table table-striped m-b-none" width="100%"></table>
		  				  </div>
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>
					</div>
			</section>
</section>		
			<!-- fine -->

		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>               
               
<!-- Right Menu  -->                
<!-- side content -->
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
						<%@ include file="../../inc/salessituation-menu-right.jsp" %>
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
<!-- / side content -->                
<!-- Fine Right Menu -->
                
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

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<!--  <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- Modal -->
<div id="myModalDetails" class="modal fade" role="dialog">
  <div class="modal-dialog" style="width: 65%;">
  
    <div class="modal-content">
      <div class="modal-header" style="display: fixed;">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Closed Cash-Details <spam id="namespamDepartment" style="font-weight: bold;"></spam></h4>
      </div>
				<div class="panel-heading">
					<i class="fa fa-money" aria-hidden="true"></i>&nbsp;Closed cash-number:&nbsp;<span id="Ticket_code" style="font-weight: bold;"></span>
					<div style="float: right; display: none;">Closed cash-total:&nbsp;<span id="Ticket_total" style="font-weight: bold;"></span></div>  
				</div>
				<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
			             <div class="table-responsive" id="details_list">
			               <table class="table table-striped m-b-none" id="details" width="100%"></table>
						</div>
						</div>
					</div>
				</div>
				</div>		
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
      </div>
    </div>

  </div>
</div>
<!-- fine pietro -->
<script>
options = {
		changeMonth: true,
		changeYear: true,
		yearRange: '1950:2100',
		closeText: 'Chiudi',
		prevText: 'Prec',
		nextText: 'Succ',
		currentText: 'Oggi',
		monthNames: ['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
		monthNamesShort: ['Gen','Feb','Mar','Apr','Mag','Giu', 'Lug','Ago','Set','Ott','Nov','Dic'],
		dayNames: ['Domenica','Lunedì','Martedì','Mercoledì','Giovedì','Venerdì','Sabato'],
		dayNamesShort: ['Dom','Lun','Mar','Mer','Gio','Ven','Sab'],
		dayNamesMin: ['Do','Lu','Ma','Me','Gio','Ve','Sa'],
		dateFormat: 'dd-mm-yy', 
		firstDay: 1,
		isRTL: false	
}
	
$( function() {
    $("#datestore").datepicker(options);
} );

var SSum = "";
function Esegui(){

	if (document.getElementById("codstore").value == ""){
		alertify.log("Campo cod store Obbligatorio");
		document.getElementById("codstore").focus();
		return;
	}	
	
	if (document.getElementById("datestore").value == ""){
		alertify.log("Campo data Obbligatorio");
		document.getElementById("datestore").focus();
		return;
	}

	var codstore = document.getElementById("codstore").value;
	var datestore = document.getElementById("datestore").value;
	//var numstore = document.getElementById("numstore").value;
	var numstore = "";
 	var head = "";
 	
	document.getElementById("ticket_list").style.visibility= "visible";
	document.getElementById("ticket_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
	var urls = "../../GetClosecash?client="+codstore+"&data="+datestore;
	
	$.getJSON(urls, function (data) {

		var lista = data.listings;
		
		  	var host = "";
		  	var hosts = "";
		  	var datestart = "";
		  	var dateend = "";
		  	var sum = "";
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";

		 	$.each(lista, function(index, lista) {
		 		
		 		host = lista.Host;
		 		hosts = lista.HostS;
		 		datestart = lista.Datestart;
		 		dateend = lista.Dateend;
		 		sum = lista.Sum;
		 		SSum = lista.Sum;
		 		emptys = "<div class='btn-group'> "+
                         "<button class='btn btn-default dropdown-toggle btn-sm' data-toggle='dropdown'> Azioni <span class='caret'></span></button> "+
                		 "<ul class='dropdown-menu' style='margin-left: -100px;'> "+
                		 "<li><a onclick=Sel('"+hosts+"') data-toggle='modal' data-target='#myModalDetails' style='cursor: pointer;'><i class='fa fa-external-link'></i> Seleziona</a></li> "+
                		 "</ul> "+
            			 "</div>";

		 		if (testdata_row == ""){
		 			testdata_row = '{"host":"'+host+'","hosts":"'+hosts+'","datestart":"'+datestart+'","dateend":"'+dateend+'","sum":"'+sum+'","empty":"'+emptys+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"host":"'+host+'","hosts":"'+hosts+'","datestart":"'+datestart+'","dateend":"'+dateend+'","sum":"'+sum+'","empty":"'+emptys+'"}';
		 		}

			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 	document.getElementById("ticket_list").innerHTML = "<table id='test' class='table table-striped m-b-none' width='100%'></table>";
	 		var head = "<thead><th width='25%'>Host</th><th width='20%'>Host Sequence</th><th width='15%'>Date Start</th><th width='15%'>Date End</th><th width='20%'>Sum</th><th width='5%' align='center'>&nbsp;</th></thead>";
	 		document.getElementById("test").innerHTML = head;
	 		$('#test').dataTable({
	 		      "language": {
	 		          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
	 		          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
	 		          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_ <%=" - " + propLang.get("nRec") %> _MAX_ <%=" : "+propLang.get("nRecVis")+"("%> _START_ <%=" - "%> _END_ <%=")"%>",
	 		          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
	 		          "infoFiltered": "(Filtrati da _MAX_ records totali)",
	 		          "decimal": ",",
	 		          "thousands": ".",
	 		          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
	 		      },
	 			"bProcessing": true,
	 			"aaData": testdata,
	 		    "aoColumns": [{
	 		        "mDataProp": "host"
	 		    }, {
	 		        "mDataProp": "hosts"
	 		    }, {
	 		        "mDataProp": "datestart"
	 		    }, {
	 		        "mDataProp": "dateend"
	 		    }, {
	 		        "mDataProp": "sum"
	 		    }, {
	 		        "mDataProp": "empty"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});
					 	

		});
}

function Sel(tickNum){
	
	ticketcli = $("#codstore").val();
	$("#Ticket_code").html(tickNum);
	$("#Ticket_total").html(SSum);
	var head = "";
	
/**/
	document.getElementById("details_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading222.gif' /></div>";
	var urls = "../../GetClosecashDetail?ticketcli="+ticketcli+"&numres="+tickNum;
	
	$.getJSON(urls, function (data) {
		
		var lista = data.listings;
		
		  	var pay = "";
		  	var sums = "";
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";
	
		 	$.each(lista, function(index, lista) {

		 		pay = lista.Payment;
		 		sums = lista.Sums;
		 		
		 		if (testdata_row == ""){
		 			testdata_row = '{"pay":"'+pay+'","sums":"'+sums+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"pay":"'+pay+'","sums":"'+sums+'"}';
		 		}
				
			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 	document.getElementById("details_list").innerHTML = "<table id='details' class='table table-striped m-b-none' width='100%'></table>";
	 		
		 	head = "<thead><th width='70%'>Payment</th><th width='30%'>Sum</th></thead>";

	 		document.getElementById("details").innerHTML = head;

	 		$('#details').dataTable({
	 		      "language": {
	 		          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
	 		          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
	 		          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_ <%=" - " + propLang.get("nRec") %> _MAX_ <%=" : "+propLang.get("nRecVis")+"("%> _START_ <%=" - "%> _END_ <%=")"%>",
	 		          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
	 		          "infoFiltered": "(Filtrati da _MAX_ records totali)",
	 		          "decimal": ",",
	 		          "thousands": ".",
	 		          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
	 		      },
	 			"bProcessing": true,
	 			"aaData": testdata,
	 		    "aoColumns": [{
	 		        "mDataProp": "pay"
	 		    }, {
	 		        "mDataProp": "sums"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});
					 	

		});
/**/	
}
</script>
<style>
TD {
   
    padding: 5px;
    vertical-align:top;
}

</style>
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
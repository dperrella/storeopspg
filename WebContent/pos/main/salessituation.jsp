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
  
  <title>Sales Situation - Tickets | <%=propLang.get("APPS") %></title>
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
  
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />



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
		z-index: 10000;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
	#ajaxModal {
		z-index: 10000;
	}

@media 
only screen and (max-width: 760px),
(min-device-width: 768px) and (max-device-width: 1024px)  {

	/* Force table to not be like tables anymore */
	table, thead, tbody, th, td, tr { 
		display: block; 
	}
	
	/* Hide table headers (but not display: none;, for accessibility) */
	thead tr { 
		position: absolute;
		top: -9999px;
		left: -9999px;
	}
	
	tr { border: 1px solid #ccc; }
	
	td { 
		/* Behave  like a "row" */
		width: 100%; 
		border: none;
		border-bottom: 1px solid #eee; 
		position: relative;
		padding-left: 100%; 
	}
	
	td:before { 
		/* Now like a table header */
		position: absolute;
		/* Top/left values mimic padding */
		top: 6px;
		left: 6px;
		width: 100%; 
		padding-right: 10px; 
		white-space: nowrap;
	}
	
	
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
                      
                	  
		                <h2 class="font-thin m-b"><%="Sales Situation" %></h2>

						 <div class="row row-sm">
			              	
			              	<div class="col-xs-12">
		
								<div class="row m-b-lg text-center">
				                    <div class="col-lg-2-4 col-md-6">
				                      <div class="bg-danger lter wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fas fa-euro-sign i-lg"></i> <%=propLang.get("tick1") %></span>
				                          <span class="text-muted text-ellipsis font-thin" id="tick1" style="text-align:center;font-size:50px">${Incasso}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-lg-2-4 col-md-6">
				                      <div class="bg-black wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fas fa-puzzle-piece i-lg"></i> <%=propLang.get("tick2") %></span>
				                          <span class="text-muted text-ellipsis font-thin" id="tick2" style="text-align:center;font-size:50px">${Pezzi}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-lg-2-4 col-md-6">
				                      <div class="bg-light dker wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fas fa-list-ol i-lg"></i> <%=propLang.get("tick5") %></span>
				                          <span class="text-muted text-ellipsis font-thin" id="tick5" style="text-align:center;font-size:50px">${PezziMedi}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-lg-2-4 col-md-6">
				                      <div class="bg-primary lt wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fa fa-ticket i-lg"></i> <%=propLang.get("tick3") %></span>
				                          <span class="text-muted text-ellipsis font-thin" id="tick3" style="text-align:center;font-size:50px">${Scontrini}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-lg-2-4 col-md-6">
				                      <div class="bg-info lt wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fas fa-ticket-alt i-lg"></i> <%=propLang.get("tick4") %></span>
				                          <span class="text-muted text-ellipsis font-thin" id="tick4" style="text-align:center;font-size:50px">${ScontrinoMedio}</span>
				                        </a>
				                      </div>
				                    </div>
				                  </div>
							
							  </div>
							  
							</div>

						
						<form id="myForm" method="post">
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-cog" aria-hidden="true"></i> <%="Parameters" %>  
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

				              <div class="table-responsive" style="overflow:hidden;">
				              
				              	<div class="row">
				              		<div class="col-lg-3 col-md-6">
				              			<label><%=propLang.get("sm_a_sc") %></label>
				              			<select id="codstore" class="form-control" onchange="camb()">
											<option value="">-- Seleziona Store --</option>
											${ComboCli}
										</select>
										
									</div>
				              		<div class="col-lg-3 col-md-6">
				              			<label><%=propLang.get("fin_from") %></label>
				              			<input type="text" id="datestore" class="form-control" readonly onchange="camb()">
				              		</div>
				              		<div class="col-lg-3 col-md-6">
				              			<label><%=propLang.get("fin_to") %></label>
				              			<input type="text" id="datestorea" class="form-control" readonly onchange="camb()">
				              		</div>		
				              		<div class="col-lg-3 col-md-6">
				              		
				              			<div class="row">
				              				<div class="col-sm-6">
				              					<label>&nbsp;</label><br/>
				              					<input type="button" onclick="Esegui()" id="ticketsBtn" class="btn btn-default btn-block " value="<%=propLang.get("tick_tick") %>" />
				              				</div>
				              				<div class="col-sm-6">
				              					<label>&nbsp;</label><br/>
				              					<input type="button" onclick="EseguiVenduto()" id="vendutoBtn" class="btn btn-default btn-block " value="<%=propLang.get("tick_vend") %>" />
						   
				              				</div>
				              			</div>
				              		</div>
				              		
				              		         
				              	</div>
				              	
				              	
				              	
			              	</div>
			              	
			              	</div>
			              	
			              	
						    	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
			              	<div id="ticket_list" style="visibility: hidden;">
							
								<div class="panel panel-default">
								
								
									<div class="table-responsive" id="ticketResp1_Dot">
					              	<table id="test" class="table table-striped b-t m-b-none" width="100%"></table>
				  				  	</div>
									
								</div>
							</div>
			              	<!-- 
			              	<footer class="panel-footer text-right bg-light lter">
			              		<input type="button" onclick="Esegui()" id="ticketsBtn" style="width:120px" class="btn btn-default btn-lg btn-success " value="Tickets" />
			              		<input type="button" onclick="EseguiVenduto()" id="vendutoBtn" style="width:120px" class="btn btn-default btn-lg btn-success" value="Venduto" />
						    </footer>  
						    -->
						</div>
								
	</form>					
	<!--  -->	     
	
	<!-- 
	<div id="sel" class="row row-sm">
		<div class="col-xs-12">
                 	<div class="text-center m-b-lg">
				<h1 class="fontBig text-white animated">Select Period</h1>
			</div>
		</div>
	</div>
	-->
	
				
			<!-- fine -->


			        </section> 
	              </section>
	            </section>               

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
        <h4 class="modal-title">Ticket-Details <spam id="namespamDepartment" style="font-weight: bold;"></spam></h4>
      </div>
				<div class="panel-heading">
					<i class="fa fa-ticket" aria-hidden="true"></i>&nbsp;Ticket-number:&nbsp;<span id="Ticket_code" style="font-weight: bold;"></span>  
				</div>
				<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
			             <div class="table-responsive" id="details_list">
			               <table class="table table-striped m-b-none" id="details"></table>
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
		dateFormat: 'yy-mm-dd', 
		firstDay: 1,
		isRTL: false	
}
	
$( function() {
    $("#datestore").datepicker(options);
    $("#datestorea").datepicker(options);
} );

function Esegui(){
		
	if (document.getElementById("datestore").value == ""){
		alertify.log("Campo data Obbligatorio");
		document.getElementById("datestore").focus();
		return;
	}
	
	//document.getElementById("ticket_list").style.display = "inline";

	var codstore = document.getElementById("codstore").value;
	var datestore = document.getElementById("datestore").value;
	//var numstore = document.getElementById("numstore").value;
	var numstore = 0;
 	var head = "";
 	
	document.getElementById("ticket_list").style.visibility= "visible";
	document.getElementById("ticket_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
	
	//var urls = "http://intra.morato.it:3213/Concentratore/GetTickets?client="+codstore+"&data="+datestore+"&soglia="+numstore;
	var urls = "http://montoro.smartretail.it:8080/Concentratore/GetTickets?client="+codstore+"&data="+datestore+"&soglia="+numstore;

	$.getJSON(urls, function (data) {

	
		var lista = data.listings;

		  	var totals = "";
		  	var tickets = "";
		  	var datas = "";
		  	var names = "";
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";
	 	
		 	$.each(lista, function(index, lista) {
		 		totals = lista.total;
		 		tickets = lista.ticket;
		 		datas = lista.datenew;
		 		names = lista.name;

		 		emptys = "<div class='btn-group'> "+
                         "<button class='btn btn-default dropdown-toggle btn-sm' data-toggle='dropdown'> Azioni <span class='caret'></span></button> "+
                		 "<ul class='dropdown-menu' style='margin-left: -100px;'> "+
                		 "<li><a onclick=Sel('"+tickets+"') data-toggle='modal' data-target='#myModalDetails' style='cursor: pointer;'><i class='fa fa-external-link'></i> Seleziona</a></li> "+
                		 "</ul> "+
            			 "</div>";

		 		if (testdata_row == ""){
		 			testdata_row = '{"ticket":"'+tickets+'","datenew":"'+datas+'","total":'+totals+',"name":"'+names+'","empty":"'+emptys+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"ticket":"'+tickets+'","datenew":"'+datas+'","total":'+totals+',"name":"'+names+'","empty":"'+emptys+'"}';
		 		}

			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 
		 	document.getElementById("ticket_list").innerHTML = "<table id='test' class='table table-striped m-b-none' width='100%'></table>";
		 
	 		//var head = "<thead><th width='45%'>Ticket</th><th width='20%'>Date</th><th width='10%'>Totals</th><th width='20%'>Name</th><th width='5%' align='center'>&nbsp;</th></thead>";
	 			 		var head = "<thead><th width='45%'><%=propLang.get("tick_tick") %></th><th width='20%'><%=propLang.get("tick_data") %></th><th width='10%'><%=propLang.get("tick_tot") %></th><th width='20%'><%=propLang.get("tick_name") %></th><th width='5%' align='center'>&nbsp;</th></thead>";
	 		
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
	 		        "mDataProp": "ticket"
	 		    }, {
	 		        "mDataProp": "datenew"
	 		    }, {
	 		        "mDataProp": "total"
	 		    }, {
	 		        "mDataProp": "name"
	 		    }, {
	 		        "mDataProp": "empty"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});

		});
	
}

function EseguiVenduto(){
	
	if (document.getElementById("datestore").value == ""){
		alertify.log("Campo data Obbligatorio");
		document.getElementById("datestore").focus();
		return;
	}

	/**/
	
	var dat1 = document.getElementById("datestore").value;
	dat1 = new Date(dat1);
	
	var dat2 = document.getElementById("datestorea").value;
	dat2 = new Date(dat2);
	
	/**/

	
	if (dat1 > dat2){
		alertify.log("Il campo 'Da Data' non può essere maggiore di 'A Data'");
		document.getElementById("datestore").focus();
		return;
	}
	
	

	document.getElementById("ticket_list").style.display = "inline";
	
	var codstore = document.getElementById("codstore").value;
	var datestore = document.getElementById("datestore").value;
	var datestorea = document.getElementById("datestorea").value;
 	var head = "";
 	
	//document.getElementById("ticket_list").style.visibility= "visible";
	//document.getElementById("ticket_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
	
	showLoader2();
	
	//var urls = "http://intra.morato.it:3213/Concentratore/GetTickets?client="+codstore+"&data="+datestore+"&soglia="+numstore;
	//var urls = "http://52.166.67.231:8080/Concentratore/GetTickets?client="+codstore+"&data="+datestore+"&soglia="+numstore;
	var urls = "../../GetVenduto?client="+codstore+"&data="+datestore+"&dataa="+datestorea;

	$.getJSON(urls, function (data) {

		var lista = data.listings;

		  	var idclient = "";
		  	var giorno = "";
		  	var codart = "";
		  	var codartalt = "";
		  	var parte = "";
		  	var codcol = "";
		  	var variante = "";
		  	var pezzi = "";
		  	var valore = "";
		  	
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";

		 	$.each(lista, function(index, lista) {
		 		
		 		idclient = lista.Idclient;
		 		giorno = lista.Giorno;
		 		codart = lista.Codart;
		 		//codartalt = "888888";//lista.Codartalt;
		 		codartalt = lista.Codartalt;
		 		taglia = lista.Taglia;
		 		parte = lista.Parte;
		 		codcol = lista.Codcol;
		 		variante = lista.Variante;
		 		pezzi = lista.Pezzi;
		 		valore = lista.Valore;

		 		/*
		 		emptys = "<div class='btn-group'> "+
                         "<button class='btn btn-default dropdown-toggle btn-sm' data-toggle='dropdown'> Azioni <span class='caret'></span></button> "+
                		 "<ul class='dropdown-menu' style='margin-left: -100px;'> "+
                		 "<li><a onclick=Sel('"+tickets+"') data-toggle='modal' data-target='#myModalDetails' style='cursor: pointer;'><i class='fa fa-external-link'></i> Seleziona</a></li> "+
                		 "</ul> "+
            			 "</div>";
            	*/		 

		 		if (testdata_row == ""){
		 			//testdata_row = '{"idclient":"'+idclient+'","giorno":"'+giorno+'","codart":"'+codart+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","pezzi":"'+pezzi+'","valore":"'+valore+'"}';
		 			testdata_row = '{"giorno":"'+giorno+'","codart":"'+codart+'","codartalt":"'+codartalt+'","taglia":"'+taglia+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","pezzi":"'+pezzi+'","valore":"'+valore+'"}';
		 		}else{
		 			//testdata_row = testdata_row + ',{"idclient":"'+idclient+'","giorno":"'+giorno+'","codart":"'+codart+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","pezzi":"'+pezzi+'","valore":"'+valore+'"}';
		 			testdata_row = testdata_row + ',{"giorno":"'+giorno+'","codart":"'+codart+'","codartalt":"'+codartalt+'","taglia":"'+taglia+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","pezzi":"'+pezzi+'","valore":"'+valore+'"}';
		 		}

			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 	
		 	var tfooter = "<tfoot><tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th></tr></tfoot>"
		 	
		 	document.getElementById("ticket_list").innerHTML = "<table id='test' class='table table-striped m-b-none' width='100%'></table>";
		 
	 		//var head = "<thead><th width='10%'>Data</th><th width='15%'>Cod. Articolo</th><th width='15%'>Cod. Alternativo</th><th width='8%'>Taglia</th><th width='5%'>Parte</th><th width='10%'>Cod. Colore</th><th width='10%'>Variante</th><th width='10%'>Pezzi</th><th width='12%'>Valore</th></thead>";
	 		var head = "<thead><th width='10%'><%=propLang.get("tick_data") %></th><th width='15%'><%=propLang.get("tick_art") %></th><th width='15%'><%=propLang.get("tick_alt") %></th><th width='8%'><%=propLang.get("tick_siz") %></th><th width='5%'><%=propLang.get("tick_part") %></th><th width='10%'><%=propLang.get("tick_col") %></th><th width='10%'><%=propLang.get("tick_var") %></th><th width='10%'><%=propLang.get("tick_pez") %></th><th width='12%'><%=propLang.get("tick_val") %></th></thead>";
	 		
	 		document.getElementById("test").innerHTML = head + tfooter;

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
	 			"aaData": testdata,
	 		    "aoColumns": [{
	 		        "mDataProp": "giorno"
	 		    }, {
	 		        "mDataProp": "codart"
	 		    }, {
	 		        "mDataProp": "codartalt"
	 		    }, {
	 		        "mDataProp": "taglia"
	 		    }, {
	 		        "mDataProp": "parte"
	 		    }, {
	 		        "mDataProp": "codcol"
	 		    }, {
	 		        "mDataProp": "variante"
	 		    }, {
	 		        "mDataProp": "pezzi"
	 		    }, {
	 		        "mDataProp": "valore"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true,
	 		    "footerCallback": function ( row, data, start, end, display ) {
	 	            var api = this.api(), data;
	 	 
	 	            // Remove the formatting to get integer data for summation
	 	            var intVal = function ( i ) {
	 	                return typeof i === 'string' ?
	 	                    i.replace(/[\$,]/g, '')*1 :
	 	                    typeof i === 'number' ?
	 	                        i : 0;
	 	            };
	 	 
	 	            // Total over all pages
	 	            total2 = api
	 	                .column(7)
	 	                .data()
	 	                .reduce(function (a, b) {
	 	                    return intVal(a) + intVal(b);
	 	                }, 0);
	 	            
	 	            total = api
	 	                .column(8)
	 	                .data()
	 	                .reduce(function (a, b) {
	 	                    return intVal(a) + intVal(b);
	 	                }, 0);
	 	 
	 	            // Total over this page
	 	            pageTotal2 = api
	 	                .column(7, { page: 'current'} )
	 	                .data()
	 	                .reduce(function (a, b) {
	 	                    return intVal(a) + intVal(b);
	 	                }, 0);
	 	            
	 	            pageTotal = api
	 	                .column(8, { page: 'current'} )
	 	                .data()
	 	                .reduce(function (a, b) {
	 	                    return intVal(a) + intVal(b);
	 	                }, 0);
	 	 
	 	            // Update footer
	 	            $(api.column(7).footer()).html(
	 	                ''+pageTotal2+' ('+ total2 +' Totali)'
	 	            );
	 	            
	 	            $(api.column(8).footer()).html(
	 	                '&euro;'+pageTotal.toFixed(2) +' (&euro; '+ total.toFixed(2) +' Totali)'
	 	            );
	 	        }
	 		});
	 		
	 		
	 		hideLoader2();
	 		document.getElementById("ticket_list").style.visibility= "visible";
	 		
	 		
		});
	
}

function Sel(tickNum){
	var NumRes = tickNum;
	var res = tickNum.split("-");
	var ticketcli = res[0];
	var ticketcassa = res[1];
	var ticketyear = res[2];
	var tickettyp = res[3];
	var ticketid = res[4];
	
	$("#Ticket_code").html(NumRes);
	
/**/
	document.getElementById("details_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading222.gif' /></div>";
	var urls = "../../GetTicketsDetail?ticketcli="+ticketcli+"&tickettyp="+tickettyp+"&ticketid="+ticketid+"&ticketyear="+ticketyear+"&ticketcassa="+ticketcassa;
	
	$.getJSON(urls, function (data) {
		
		var lista = data.listings;
		
		  	var prod = "";
		  	var unit = "";
		  	var scont = "";
		  	var prezzointero = "";
		  	var price = "";
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";
	
		 	$.each(lista, function(index, lista) {
		 		prod = lista.Product;
		 		unit = lista.Units;
		 		scont = lista.Sconto;
		 		prezzointero = lista.PrezzoIntero;
		 		price = lista.Price;
				
		 		if (testdata_row == ""){
		 			testdata_row = '{"prod":"'+prod+'","unit":"'+unit+'","prezzointero":"'+prezzointero+'","scont":"'+scont+'","price":"'+price+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"prod":"'+prod+'","unit":"'+unit+'","prezzointero":"'+prezzointero+'","scont":"'+scont+'","price":"'+price+'"}';
		 		}
				
			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 	document.getElementById("details_list").innerHTML = "<table id='details' class='table table-striped m-b-none' width='100%'></table>";
		 	
	 		var head = "<thead><th width='30%'>Product</th><th width='10%'>Units</th><th width='20%'>Full Price</th><th width='20%'>Discount</th><th width='20%'>Price</th></thead>";

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
	 		        "mDataProp": "prod"
	 		    }, {
	 		        "mDataProp": "unit"
	 		    }, {
	 		        "mDataProp": "prezzointero"
	 		    }, {
	 		        "mDataProp": "scont"
	 		    }, {
	 		        "mDataProp": "price"
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
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();

if(dd<10) {
    dd = '0'+dd
} 

if(mm<10) {
    mm = '0'+mm
} 

//today = dd + '-' + mm + '-' + yyyy;
today = yyyy + '-' + mm + '-' + dd;

document.getElementById("datestore").value = today;
document.getElementById("datestorea").value = today;

document.getElementById("codstore").value = "${First_}";

function aggtile(){
	
	
	
	var stores = document.getElementById("codstore").value;
	
	if (stores == ""){
		document.getElementById("ticketsBtn").disabled = true;
		document.getElementById("vendutoBtn").disabled = true;
		alertify.error("Selezionare uno Store");
		return;
	}

	document.getElementById("ticketsBtn").disabled = false;
	document.getElementById("vendutoBtn").disabled = false;

	
	$.ajax({
		url: '../../GetTiles',
		data : {
			fis : stores
		},
		success : function(responseText) {
			
			var tick = responseText.split("$");
			
			document.getElementById("tick1").innerHTML = tick[0];
			document.getElementById("tick2").innerHTML = tick[1];
			document.getElementById("tick3").innerHTML = tick[2];
			document.getElementById("tick4").innerHTML = tick[3];
			
		}
	
	});
	
}

function camb(){
	
	document.getElementById("ticket_list").style.display = "none";
	
	var stores = document.getElementById("codstore").value;
	var dates = document.getElementById("datestore").value;
	var dates2 = document.getElementById("datestorea").value;
	
	if (stores == ""){
		document.getElementById("ticketsBtn").disabled = true;
		document.getElementById("vendutoBtn").disabled = true;
		alertify.error("Selezionare uno Store");
		return;
	}

	document.getElementById("ticketsBtn").disabled = false;
	document.getElementById("vendutoBtn").disabled = false;
	
	
	$.ajax({
		url: '../../GetTilesDate',
		data : {
			fis : stores,
			dat : dates,
			dat2 : dates2
		},
		success : function(responseText) {
			
			var tick = responseText.split("$");
			
			var INC = tick[0] / tick[2];
			var PEZ = tick[1] / tick[2];
			
			document.getElementById("tick1").innerHTML = tick[0].replace(".", ",")+" &euro;";
			document.getElementById("tick2").innerHTML = tick[1];
			document.getElementById("tick3").innerHTML = tick[2];
			document.getElementById("tick4").innerHTML = INC.toFixed(2).replace(".", ",")+" &euro;";
			document.getElementById("tick5").innerHTML = PEZ.toFixed(2).replace(".", ",");
			
		}
	
	});
}

</script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
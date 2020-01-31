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
  
  <title>View Price - Desk | <%=propLang.get("APPS") %></title>
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
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="View - Category" %></h2>

					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-barcode" aria-hidden="true"></i> <%="View - Category" %>  
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive" style="overflow:hidden;">
							<table width="100%" style="overflow:hidden;">
								<tr>
									<td colspan="4" width="100%"><span id="pack_num" style="font-weight: bold;"></span></td>
								</tr>
								<tr>
									<td width="25%" align="left">Barcode <input type="text" id="barcode" class="form-control" onkeypress="KeyEvent(event)" /></td>
									<td width="25%" align="center">
										<input type="button" onclick="Esegui()" class="btn btn-default" style="margin-top: 19px; margin-left: 20px;" value="Esegui" />
									</td>
									<td width="25%">&nbsp;</td>
									<td width="25%">&nbsp;</td>
								</tr>
							</table>		  					
		  				  </div>
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>	
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
								<i class="fa fa-list-alt" aria-hidden="true"></i> <%="Ticket List" %>  
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
               
                
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  <style>
  /*
  .dataTables_filter { visibility: hidden; display: none;}
  .dataTables_paginate { display: none;}
  */
  </style>

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

function Esegui(){
		
	if (document.getElementById("barcode").value == ""){
		alertify.log("Campo Barcode Obbligatorio");
		document.getElementById("barcode").focus();
		return;
	}

	var barcode = document.getElementById("barcode").value;
 	var head = "";
 	
	document.getElementById("ticket_list").style.visibility= "visible";
	document.getElementById("ticket_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
	
	//var urls = "../../GetFromBarcode?barcode="+barcode;
	var urls = "../../GetFromCategory?barcode="+barcode;

	
	$.getJSON(urls, function (data) {
		
		var lista = data.listings;
		
		  	var code = "";
		  	var name = "";
		  	var pric = "";
		  	var coda = "";
		  	var codc = "";
		  	var tagl = "";
		  	var list = "";
		  	var stag = "";		  	
		  	var part = "";
		  	var vari = "";
		  	
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";
	 		
		 	$.each(lista, function(index, lista) {
		 		
		 		
		 		code = lista.Code;
		 		name = lista.Name;
		 		pric = lista.Pricesell;
		 		coda = lista.Codart;
		 		codc = lista.Codcol;
		 		tagl = lista.Taglia;
		 		//list = lista.Listino;
		 		stag = lista.Stagione;
		 		part = lista.Parte;
		 		vari = lista.Variante;
		 		cate = lista.Category;
		 		
				emptys = "";
		 		
				if (testdata_row == ""){
		 			testdata_row = '{"code":"'+code+'","name":"'+name+'","pric":"'+pric+'","cate":"'+cate+'","coda":"'+coda+'","codc":"'+codc+'","tagl":"'+tagl+'","stag":"'+stag+'","part":"'+part+'","vari":"'+vari+'","empty":"'+emptys+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"code":"'+code+'","name":"'+name+'","pric":"'+pric+'","cate":"'+cate+'","coda":"'+coda+'","codc":"'+codc+'","tagl":"'+tagl+'","stag":"'+stag+'","part":"'+part+'","vari":"'+vari+'","empty":"'+emptys+'"}';
		 		}

			});
	 		

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 
	 	
	 		
		 	document.getElementById("ticket_list").innerHTML = "<table id='test' class='table table-striped m-b-none' width='100%'></table>";
				 	
	 		var head = "<thead> "+
	 				   "<th width='8%'>Code</th>"+
	 				   "<th width='20%'>Name</th>"+
	 				   "<th width='7%'>Prezzo</th>"+
	 				   "<th width='7%'>Categoria</th>"+
	 				   "<th width='10%'>Cod Art.</th>"+
	 				   "<th width='10%'>Cod Col.</th>"+
	 				   "<th width='10%'>Taglia</th>"+
	 				   "<th width='8%'>Stagione</th>"+
	 				   "<th width='5%'>Parte</th>"+
	 				   "<th width='10%'>Variante</th>"+
	 				   "<th width='5%' align='center'>&nbsp;</th>"+
	 				   "</thead>";
	 		
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
	 		        "mDataProp": "code"
	 		    }, {
	 		        "mDataProp": "name"
	 		    }, {
	 		        "mDataProp": "pric"
	 		    }, {
	 		        "mDataProp": "cate"
	 		    }, {
	 		        "mDataProp": "coda"
	 		    }, {
	 		        "mDataProp": "codc"
	 		    }, {
	 		        "mDataProp": "tagl"
	 		    }, {
	 		        "mDataProp": "stag"
	 		    }, {
	 		        "mDataProp": "part"
	 		    }, {
	 		        "mDataProp": "vari"
	 		    }, {
	 		        "mDataProp": "empty"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});
					 	

		});
}

function KeyEvent(event){
    var char_ = event.which || event.keyCode;
    
    if (char_ == "13"){
    	var z = "";	
    	var x = document.getElementById("barcode").value;	
    	var y = x.length; 	
    		
    	if (y >= 16){
    		z = x.substring(1, 16);
    	}else{
    		z = $("#barcode").val();
    	}
    	
    	document.getElementById("pack_num").innerHTML = z;
    	Esegui();
    	document.getElementById("barcode").value = "";
    }
}
</script>
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
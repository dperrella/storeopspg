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
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
  <style>
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
				   margin-left: -100px;" />
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
        <!-- /.aside -->
        
        <section id="content">
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <c:if test = "${idcus==''}">
                   	<div class="row row-sm">
						<div class="col-xs-12">
	                    	<div class="text-center m-b-lg">
								<h1 class="fontBig text-white animated"><%=propLang.get("sel_cust") %></h1>
							</div>
						</div>
					</div>
              </c:if>
                   
              <c:if test = "${idcus!=''}">
               
              <%@ include file="../../inc2/menuApp8.jsp" %>
              
              <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b>Filters</b>
		                </span>
		                
		                <form id="frm-filter" name="frm-filter" method="post">
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                
		                
		                	<li class="list-group-item">
	                        	<label>Stores</label>
						        <select name="store" id="store" class="form-control ss" onchange="reloadTable()">
						        	<c:forEach items="${sto}" var="sto" varStatus="status">
						        	<option value="${sto.customerIdStore}">${sto.description}</option>
						        	</c:forEach>
						        </select>
	                        </li>
	                        
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtda") %></label>
						        <input class="datepicker-input2 form-control" name="from" id="from" 
						          	autocomplete="off" type="text" value="${from}" data-date-format="yyyy-MM-dd" > 
	                        </li>
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtal") %></label>
						        <input class="datepicker-input2 form-control" name="to" id="to" 
						          	autocomplete="off" type="text" value="${to}" data-date-format="yyyy-MM-dd" >  
	                        </li>
	                        
	                    </ul>
                        </form>
                      </div>
                    </section>
                    
                  </section>
                </aside>
                <!-- / side content -->
              
              <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      <h2 class="font-thin m-b">Tickets</h2>
                      	
					  <div class="row row-sm">
						<div class="col-xs-12">
						
						
						
						<section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span id="table_id">Tickets</span>
			                
			                
			                <a href="#" onclick="excelExport()" class="btn btn-default btn-xs m-l-sm pull-right" >
	                            <i class="fa fa-table"></i>
	                            Export Data
	                        </a>
	                        
	                        
			              </header>
			              
			              <div class="table-responsive">
                      		<table id="test" class="table table-striped">
							<thead>
								<tr>
									<th style="width:5%">#</th>
									<th style="width:20%">Ticket</th>
									<th style="width:20%">Date</th>
									<th style="width:20%">Total</th>
									<th style="width:20%">Name</th>
									<th style="width:5%"></th>
									<th style="width:5%"></th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
							<tfoot>
	      					  	  <tr>
	      					  	  	<td colspan="7"><div id="total" style="margin-left:5px"></div></td>
	      					  	  </tr>
	      					</tfoot>
							</table>
                      	  </div>
			              
			              
		  				</section>
						
						
						</div>
					  </div>
					</section>
				  </section>
			  </section>	 
                
	            
	            
              </c:if>
             	  
              </section>
              
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
  <script type="text/javascript" src="../../rfid2/main/xlsx/shim.min.js"></script>
  <script type="text/javascript" src="../../rfid2/main/xlsx/xlsx.full.min.js"></script>
  <script type="text/javascript" src="../../rfid2/main/xlsx/Blob.js"></script>
  <script type="text/javascript" src="../../rfid2/main/xlsx/FileSaver.js"></script>
 
  
  <script>
  	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		
		loadTable();
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
	
	$('.datepicker-input2').datepicker({
	    format: 'yyyy-mm-dd',
	    orientation: 'top',
	    autoclose: true
	});  
	
	$("#from").on('changeDate', function(ev) {
		reloadTable();
	});
	$("#to").on("changeDate", function (e) {
		reloadTable();
	});
	
	function reloadTable() {
		
		loadTable();
		
	}
	
	$('.ss').selectpicker({
	    liveSearch: true,
	    tickIcon: 'fa fa-check'
	});
	
	function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		});
    }
	
	
	
	var tb;
	
	function loadTable() {
		
		var store = $('#store').val();
		var from = $('#from').val();
		var to = $('#to').val();
		
		console.log(store+' '+from+' '+to);
		
		nr = 0;
		
		//alert("../../servlet/ws/GetStock?idsite="+idsite+"&code="+code+"&category="+category+"&season="+season+"&itemcode="+itemcode+"&colourcode="+colourcode+"&size="+size);
		
		
		//alert("../../servlet/ws/GetDocs?idflow="+idflow+"&idstep="+idstep+"&idsite="+idsite+"&idcus="+idcus);
		tb = $('#test').DataTable( {
			"bDestroy": true,
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
		      "iDisplayLength": 10,
		      "aLengthMenu": [[10, 50, 100, 500, 1000], [10, 50, 100, 500, 1000]],
		    "processing": true,
	        "serverSide": true,
	        "ajax": {
	        	"url": "../../pos/GetSalesSituationTicket?store="+store+"&from="+from+"&to="+to,
	        	"type": "POST"
	        },
	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  nr++;
	        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
	        	  $('td:eq(3)', nRow).html('<span style="text-align:right">' + aData[3] + '</span>');
	        	  $('td:eq(5)', nRow).html('<a href="../popup/ticket_detail.jsp?idticket='+aData[1]+'&print=N" data-toggle="ajaxModal" class="btn btn-default btn-sm"><i class="fa fa-search"></i></a>');
	        	  $('td:eq(6)', nRow).html('<a href="../popup/ticket_detail.jsp?idticket='+aData[1]+'&print=Y" data-toggle="ajaxModal" class="btn btn-default btn-sm"><i class="fa fa-print"></i></a>');
	        	  
	        	  return nRow;
	        },
	        "fnInitComplete": function (settings,json) {
	        	console.log(JSON.stringify(json));
	        	
	        	/*
	        	console.log(JSON.stringify(json.scontrini));
	        	console.log(JSON.stringify(json.incasso));
	        	console.log(JSON.stringify(json.pezzi_medi));
	        	console.log(JSON.stringify(json.pezzi));
	        	console.log(JSON.stringify(json.scontrino_medio));
	        	
	        	$('#tick1').html(json.incasso);
	        	$('#tick2').html(json.pezzi);
	        	$('#tick5').html(json.pezzi_medi);
	        	$('#tick3').html(json.scontrini);
	        	$('#tick4').html(json.scontrino_medio);
	        	*/
	        	
	              $('#total').html('<b>Total Records: '+settings.fnRecordsTotal()+' records</b>');
	        }
	    } );
		
		
		  tb.on( 'search.dt', function () {
	          nr=0;
	      } );
	    
	}
	
	
	function printDiv(iddiv) 
	{

	  var divToPrint=document.getElementById(iddiv);

	  var newWin=window.open('','Print-Window');

	  newWin.document.open();

	  newWin.document.write('<html><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');

	  newWin.document.close();

	  setTimeout(function(){newWin.close();},10);

	}
	
	function print(tickNum){
		var NumRes = tickNum;
		var res = tickNum.split("-");
		var ticketcli = res[0];
		var ticketcassa = res[1];
		var ticketyear = res[2];
		var tickettyp = res[3];
		var ticketid = res[4];
		
		
	}
	
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  
  
  <!-- --------------------- -->
  <form id="chgUser" action="index.htm" method="post">
  	<input type="hidden" name="idcus" id="idcus_frm"/>
  </form>
  
  	<div id="myModalCus" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" onclick="dismissCus()">&times;</button>
	        <h3 class="font-thin">Select Customer</h3>
	      </div>
	      <div class="modal-body" id="SelCus" style="width:100%;">
			<select id="idcus" name="idcus" title="Select Customer" class="selectpicker remove-example" data-live-search="true" style="background: #fff; min-width:100%; width:100%; border: 1px;">
				<c:forEach items="${cus}" var="cus" varStatus="status">
				<option value="${cus.xcdel}">${cus.xdtab}</option>
				</c:forEach>
			</select>   
			<div id="customers" name="customers" style="background: #eee; min-width:100%; width:100%; border: 1px;"></div>     
	      </div>
	      <div class="modal-footer" style="margin-top: 20px;">
	        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="dismissCus()"><i class="fa fa-power-off" aria-hidden="true"></i> Close</button>
	        <button type="button" class="btn btn-primary btn-lg" onclick="selectCustomer('idcus')" data-dismiss="modal"><i class="icon-user" aria-hidden="true"></i>&nbsp;<i class="fa fa-exchange" aria-hidden="true"></i> Change</button>
	      </div>
	    </div>
	
	  </div>
	  
	</div>
	
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  
  <c:if test = "${nr_customers > 1}">

  <script>
  	$('#myModalCus').modal('show');
  </script>
  
  </c:if>
  
  <script>
    function openModalCus() {
    	$('#myModalCus').modal('show');
    }
    function dismissCus() {
    	var idcus = $('#idcus').val();
    	if(idcus=='') {
    		$('#chgUser').submit();
    	}
    }
    
  	function selectCustomer(id) {
  		
  		var idcus = $('#'+id).val();
  		$('#idcus_frm').val(idcus);
  		
  		$('#chgUser').submit();
  	}
  </script>
  	
  </body>
</html>
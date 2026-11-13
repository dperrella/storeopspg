<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
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
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
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
		z-index: 1;
	}	
  </style>
</head>
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
              
              <%@ include file="../../inc2/menuApp3.jsp" %>
              
              
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                  	  <a href="index2.htm" class="pull-right text-muted m-t-lg"><i class="icon-refresh i-lg inline"></i> Beta</a>
                  
                      <h2 class="font-thin m-b">Desk Smart Control <%=propLang.get("versione") %> 2.0.0</h2>
                      
                      <div class="row row-sm">
	              	
		              	<div class="col-xs-12">
	
							<div class="row m-b-lg text-center">
								
								<div class="col-sm-4">
			                      <div class="bg-danger lter wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="icon-users i-lg"></i> <%=propLang.get("nr_customers") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_customers}</span>
			                        </a>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-4">
			                      <div class="bg-black wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="fa fa-building-o i-lg"></i> <%=propLang.get("nr_stores") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_stores}</span>
			                        </a>
			                      </div>
			                    </div>
			                    <div class="col-sm-4">
			                      <div class="bg-light dker wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="icon-feed icon i-lg"></i> <%=propLang.get("nr_devices") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_devices}</span>
			                        </a>
			                      </div>
			                    </div>
			                    
			                </div>
			                
			                <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-list-alt"></i></span>
			                      <span class="hidden-sm">Logs List</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="logs" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="10%"><%=propLang.get("sm_cont_dd") %></th>
									        <th width="15%"><%=propLang.get("sm_cont_do") %></th>
									        <th width="17%"><%=propLang.get("sm_cont_cl") %></th>
									        <th width="25%"><%=propLang.get("sm_cont_ne") %></th>
									        <th width="25%"><%=propLang.get("sm_cont_ce") %></th>
									        <th width="8%"><%=propLang.get("sm_cont_is") %></th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="7"><div id="logs_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-desktop"></i></span>
			                      <span class="hidden-sm">Device not Paired</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="dnp" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="5%">Account ID</th>
									        <th width="5%">Store ID</th>
									        <th width="10%">Store Name</th>
									        <th width="5%">Device ID</th>
									        <th width="10%"><%=propLang.get("sm_cont_dn") %></th>
									        <th width="10%"><%=propLang.get("sm_cont_ts") %></th>
									        <th width="10%"><%=propLang.get("sm_cont_ip_") %></th>
									        <th width="5%"><%=propLang.get("sm_notpaired") %></th>
									        <th width="5%"><%=propLang.get("sm_preregistration") %></th>
									        <th width="5%"><%=propLang.get("sm_storename") %></th>
									        <th width="5%"><%=propLang.get("sm_storenote") %></th>	
									        <th></th>
									        <th></th>								        
									       
									      </tr>
									  </thead>
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="13"><div id="dnp_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-users"></i></span>
			                      <span class="hidden-sm">People Counter Not Update</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="pcnu" data-ride="datatables">
					                  <thead>
										  <tr>
									        <!-- 
									        <th width="10%"><%=propLang.get("sm_cont_is") %></th>
									        -->
									        <th width="10%">Account ID</th>
									        <th width="10%">Store ID</th>
									        <th width="10%">Door ID</th>
									        <th width="25%"><%=propLang.get("sm_cont_cl") %></th>
									        <th width="25%"><%=propLang.get("sm_cont_dn") %></th>
									        <th width="20%"><%=propLang.get("sm_cont_lt") %></th>
									      </tr>
									  </thead>
			      					 
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="6"><div id="pcnu_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="icon-bar-chart"></i></span>
			                      <span class="hidden-sm">Analytics Last Update</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="annu" data-ride="datatables">
					                  <thead>
										  <tr>
									        <!-- 
									        <th width="10%"><%=propLang.get("sm_cont_is") %></th>
									        -->
									        <th width="10%">Account ID</th>
									        <th width="10%">Store ID</th>
									        <th width="10%">Door ID</th>
									        <th width="25%"><%=propLang.get("sm_cont_cl") %></th>
									        <th width="25%"><%=propLang.get("sm_cont_dn") %></th>
									        <th width="20%"><%=propLang.get("sm_cont_lt") %></th>
									      </tr>
									  </thead>
			      					 
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="6"><div id="annu_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-bullhorn"></i></span>
			                      <span class="hidden-sm">Analytics Active Alarms</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="al" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="12%"><%=propLang.get("sm_a_id") %></th>
									        <th width="3%"><%=propLang.get("sm_a_dn") %></th>
									        <th width="3%"><%=propLang.get("sm_a_si") %></th>
									        <th width="6%"><%=propLang.get("sm_a_sn") %></th>
									        <th width="12%"><%=propLang.get("sm_a_ts") %></th>
									        <th width="15%"><%=propLang.get("sm_a_us") %></th>
									        <th width="14%"><%=propLang.get("sm_a_ip_") %></th>
									        <th width="2%"><%=propLang.get("sm_a_dt") %></th>
									        <th width="2%"><%=propLang.get("sm_a_ma") %></th>
									        <th width="9%"><%=propLang.get("sm_a_za") %></th>
									        <th width="7%"><%=propLang.get("sm_a_sc") %></th>
									        <th width="7%"><%=propLang.get("sm_a_ai") %></th>
									        <th width="7%"><%=propLang.get("sm_a_di") %></th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="14"><div id="al_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                
			                
			                
			                
			                
			                
			                <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-list"></i></span>
			                      <span class="hidden-sm">Updating List</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="ul" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="20%"><%=propLang.get("sm_cont_cl") %></th>
									        <th width="20%"><%=propLang.get("sm_cont_ne") %></th>
									        <th width="20%"><%=propLang.get("sm_cont_ip") %></th>
									        <th width="5%">Attempts</th>
									        <th width="10%">Updating From</th>
									        <th width="10%">Status</th>
									        <th width="5%">Account Id</th>
									        <th width="5%">Store Id</th>
									        <th width="5%">Door Id</th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="10"><div id="ul_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-ban"></i></span>
			                      <span class="hidden-sm">Device not Enabled</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="dne" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="8%"><%=propLang.get("sm_cont_id") %></th>
							        		<th width="13%"><%=propLang.get("sm_cont_cl") %></th>
							        		<th width="8%">Store Id</th>
									        <th width="23%"><%=propLang.get("sm_cont_ne") %></th>
									        <th width="8%"><%=propLang.get("sm_cont_ip_") %></th>
									        <th width="14%"><%=propLang.get("sm_cont_dt") %></th>
									        <th width="16%"><%=propLang.get("sm_cont_dd_") %></th>
									        <th width="10%"><%=propLang.get("sm_cont_re") %></th>
							        		<th width="8%"></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="8"><div id="dne_total" style="margin-left:5px"></div></td>
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
              
              
              
              
              </section>
            </section>
          </section>
        </section>
        
        
      </section>
    </section>    
  </section>
  <script src="../../js/jquery.min.js"></script>
  <!-- Filter list -->
  <script src="../../js/jquery.fastLiveFilter.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <!--
  <script src="../../js/bootstrap-modal.js"></script>
  -->
  <script>
  
  
  
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
	/*
	//MODAL
	$('.getModal').click(function(e) {
	
		alert('modal');
		e.preventDefault();
		var href = $(e.target).attr('href');
	
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
	*/
	
	var logs = $('#logs').dataTable( {
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
	      "processing": true,
	      //"serverSide": true,
	      "ajax": {
            "url": "../../servlet/ws/GetLog",
            //"dataType": "jsonp"
          },
          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
        	  $('td:eq(6)', nRow).html('<a href="../../app/anag/stores-dett.htm?tipoForm=M&id=' + aData[6] + '" class="btn btn-default">Edit</a>');
              $('td:eq(5)', nRow).html('<span class="label bg-light">' + aData[5] + '</span>');
              
              return nRow;
          },
          "fnInitComplete": function (settings,json) {
              $('#logs_total').html('<b>Total Logs: '+settings.fnRecordsTotal()+' records</b>');
           }
          

	  } );
	
	var al = $('#al').dataTable( {
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
	      "processing": true,
	      //"serverSide": true,
	      "ajax": {
            "url": "../../servlet/ws/GetActiveAlarms",
            //"dataType": "jsonp"
          },
          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
        	  $('td:eq(13)', nRow).html('<a id="' + aData[14] + '" href="#" onclick="setAsRead(\'' + aData[14] + '\')" class="btn btn-default"><i class="fa fa-check"></i></a>');
              $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
              return nRow;
          },
          "fnInitComplete": function (settings,json) {
              $('#al_total').html('<b>Total Active Alarms: '+settings.fnRecordsTotal()+' records</b>');
           }
	  } );
	
	var ul = $('#ul').dataTable( {
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
	      "processing": true,
	      //"serverSide": true,
	      "ajax": {
            "url": "../../servlet/ws/GetUpdating2",
            //"dataType": "jsonp"
          },
          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
              $('td:eq(9)', nRow).html('<a href="../../app/anag/stores-dett.htm?tipoForm=M&id=' + aData[1] + '" class="btn btn-default">Edit</a>');
              $('td:eq(6)', nRow).html('<span class="label bg-light">' + aData[4] + '</span>');
              $('td:eq(7)', nRow).html('<span class="label bg-light">' + aData[5] + '</span>');
              $('td:eq(8)', nRow).html('<span class="label bg-light">' + aData[6] + '</span>');
              return nRow;
          },
          "fnInitComplete": function (settings,json) {
              $('#ul_total').html('<b>Total Updating List: '+settings.fnRecordsTotal()+' records</b>');
           }
	  } );
	
	var dnp = $('#dnp').dataTable( {
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
	      "processing": true,
	      //"serverSide": true,
	      "ajax": {
            "url": "../../servlet/ws/GetNotPaired",
            //"dataType": "jsonp"
          },
          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
        	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
              $('td:eq(3)', nRow).html('<span class="label bg-light">' + aData[3] + '</span>');
              $('td:eq(11)', nRow).html('<a href="devices-notpaired.jsp?tipoForm=I&accountid='+aData[0]+'&storeid= '+aData[1]+'&doorid='+aData[3]+'" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-edit"></i></a>');
              //$('td:eq(10)', nRow).html('<a href="devices-notpaired.jsp?tipoForm=E&accountid='+aData[5]+'&storeid= '+aData[2]+'&doorid='+aData[0]+'" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-times"></i></a>');
              
              $('td:eq(12)', nRow).html('<a href="#" onclick="DeleteNotPaired(\'' + aData[0] + '\', \'' + aData[1] + '\', \'' + aData[3] + '\')" class="btn btn-default"><i class="fa fa-times"></i></a>');
              
              
              return nRow;
          },
          "fnInitComplete": function (settings,json) {
              $('#dnp_total').html('<b>Total Device Not Paired: '+settings.fnRecordsTotal()+' records</b>');
              
              /*
            //MODAL
          	$('.getModal').click(function(e) {
          		e.preventDefault();
          		var href = $(e.target).attr('href');
          	
          		if (href.indexOf('#') == 0) {
          			$(href).modal('open');
          		} else {
          			$.get(href, function(data) {
          				$('<div class="modal" >' + data + '</div>').modal();
          			});
          		}
          	});
              */
          	
           },
           "fnCreatedRow": function( nRow, aData, iDataIndex ) {
               $(nRow).attr('id', aData[5]+'_'+aData[2]+'_'+aData[0]);
           }

	  } );
	
	var pcnu = $('#pcnu').dataTable( {
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
	      "processing": true,
	      //"serverSide": true,
	      "ajax": {
            "url": "../../servlet/ws/GetNotUpdate",
            //"dataType": "jsonp"
          },
          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
        	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
        	  $('td:eq(2)', nRow).html('<span class="label bg-light">' + aData[2] + '</span>');
        	  return nRow;
          },
          "fnInitComplete": function (settings,json) {
              $('#pcnu_total').html('<b>Total People Counter Not Update: '+settings.fnRecordsTotal()+' records</b>');
           }
      } );
	
	
	var annu = $('#annu').dataTable( {
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
	      "processing": true,
	      //"serverSide": true,
	      "ajax": {
          "url": "../../servlet/ws/GetNotUpdateAnalytics",
          //"dataType": "jsonp"
        },
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
      	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
      	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
      	  $('td:eq(2)', nRow).html('<span class="label bg-light">' + aData[2] + '</span>');
      	  return nRow;
        },
        "fnInitComplete": function (settings,json) {
            $('#annu_total').html('<b>Total Analytics Not Update: '+settings.fnRecordsTotal()+' records</b>');
         }
    } );
	
	
	
	
	var dne = $('#dne').dataTable( {
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
	      
	      "processing": true,
	      //"serverSide": true,
	      "ajax": {
            "url": "../../servlet/ws/GetNotEnabled",
            //"dataType": "jsonp"
          },
          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
        	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
        	  $('td:eq(8)', nRow).html('<a href="../../app/anag/stores-dett.htm?tipoForm=M&id=' + aData[2] + '" class="btn btn-default">Edit</a>');
        	  
        	  return nRow;
          },
          "fnInitComplete": function (settings,json) {
              $('#dne_total').html('<b>Total Device Not Enabled: '+settings.fnRecordsTotal()+' records</b>');
           }
	  } );
	
	
	function setAsRead(id) {
		
		//alert(id);
		
  		var msg = '';
  		$.ajax({
	        url: 'SetRead.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'id='+id,
	        success: function (data) {
	        	msg = data;
	        	$('#'+id).closest('tr').remove();
	        },
	        error: function () {
	        	alert('Errore! si e\' verificato un errore.');
	        }
	    });
  		
  		if(msg!='') {
  			alert(msg);
  		}
  		else {
  			// Nascondi riga
  		}
  	}
	
	function DeleteNotPaired(accountid, storeid, doorid) {
		
		//alert(id);
		
  		var msg = '';
  		$.ajax({
	        url: 'DeleteNotPaired.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'accountid='+accountid+'&storeid='+storeid+'&doorid='+doorid,
	        success: function (data) {
	        	msg = data;
	        	$('#'+accountid+'_'+storeid+'_'+doorid).remove();
	        },
	        error: function () {
	        	alert('Errore! si e\' verificato un errore.');
	        }
	    });
  		
  		if(msg!='') {
  			alert(msg);
  		}
  		else {
  			// Nascondi riga
  		}
  	}
	
	function insertDeviceSlave(accountid , storeid , doorid) {
		
		//alert(accountid+' '+storeid+' '+doorid);
		$.ajax({
	        url: 'insertDeviceSlave.jsp?accountid='+accountid+'&storeid='+storeid+'&doorid='+doorid,
	        type: 'POST',
	        dataType: 'html',
	        async: false,
	        beforeSend : function (){
	        
	        },
	        success: function (data) {
	        	$("#notpaireddmsg").html("<div class=\"alert alert-success\"><strong><%=propLang.get("msgSuccess")%></strong></div>");
	        },
	        error: function () {
	        	alert("<%=propLang.get("errore") %>");
	        }
	    });
		
		
	}
	
  </script> 
	
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
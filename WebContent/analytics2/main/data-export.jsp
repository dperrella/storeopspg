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
          <section class="hbox stretch">   
          	<!-- menu app -->
        	<c:choose>
         		<c:when test = "${whereIAm == 'TM'}">
         			<%@ include file="../../inc2/menuApp14.jsp" %>
         		</c:when>
         		<c:when test = "${whereIAm == 'AN'}">
         			<%@ include file="../../inc2/menuApp6.jsp" %>
         		</c:when>
     		</c:choose>
                   
            <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover" style="margin-bottom:65px">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b>Data Export Filters</b>
		                </span>
		                
		                <form id="frm-export" name="frm-export" method="post">
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                
		                
		                	<li class="list-group-item">
	                        	<label>Data Type</label>
						        <select name="dataType" id="dataType" class="form-control" onchange="chgDataType(this.value)">
						        	<option value="G" selected>Group data</option>
						        	<option value="R">Raw Data</option>
						        	<option value="P">Pivot data</option>
						        </select>
	                        </li>
	                        
		                	<li class="list-group-item" id="kpi_li">
		                	  <label><%=propLang.get("pop_us_sel") %> KPI - <a href="#" onclick="selAll()"
	                        		class="btn btn-default btn-xs"><%=propLang.get("sel_all") %></a></label>
					          <select multiple id="kpi" name="kpi" data-required="true" 
					          	class="form-control parsley-validated chosen-select">
						          <c:forEach items="${kpi}" var="kpi" varStatus="status">
		                          <option value="${kpi.xcdel}">${kpi.xdtab}</option>
		                          </c:forEach>
	                       	  </select>
		                	</li>
		                	
		                	<li class="list-group-item" id="ev_li">
		                	  <label><%=propLang.get("pop_us_sel") %> Event</label>
					          <select id="event" name="event" data-required="true" 
					          	class="form-control parsley-validated chosen-select">
						          <c:forEach items="${ev}" var="ev" varStatus="status">
		                          <option value="${ev.xcdel}">${ev.xdtab}</option>
		                          </c:forEach>
	                       	  </select>
		                	</li>
		                	
		                	<li class="list-group-item">
		                	  <label><%=propLang.get("pop_us_sel") %> Store</label>
					          <select id="idstore" name="idstore"
					          	class="form-control parsley-validated chosen-select">
					          	  <option value="">All Stores</option>
						          <c:forEach items="${sto}" var="sto" varStatus="status">
		                          <option value="${sto.cmp1}">${sto.cmp1} ${sto.xdtab}</option>
		                          </c:forEach>
	                       	  </select>
		                	</li>
		                	
		                	<li class="list-group-item">
	                        	<label>Consider Store Opening Hour</label>
						        <select id="hour" name="hour" 
						        	class="form-control parsley-validated chosen-select">
						        	<option value="true">True</option>
						        	<option value="false" selected>False</option>
						        </select>
	                        </li>
	                        
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtda") %></label>
						        <input class="datepicker-input2 form-control" name="dtda" id="dtda" 
						          	autocomplete="off" type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
	                        </li>
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtal") %></label>
						        <input class="datepicker-input2 form-control" name="dtal" id="dtal" 
						          	autocomplete="off" type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
	                        </li>
	                        <li class="list-group-item">
	                        	<label>Export Type</label>
						        <select id="export_type" name="export_type" class="form-control" onchange="checkExport(this.value)">
						          	<option value="M" selected>E-Mail</option>
						          	<option value="W">View and Download</option>
						        </select>
	                        </li>
	                        <li class="list-group-item">
	                        	<label>E-Mail to send</label>
						        <input type="text" id="mail" name="mail" value=""
						        	class="form-control"/>
	                        </li>
	                    </ul>
                        </form>
                      </div>
                    </section>
                    <footer class="footer footer-md bg-light dker">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <a href="#" onclick="esporta()" class="btn btn-primary btn-block"><i class="fa fa-gear"></i> Data Export</a>
	                        </span>
	                      </div>
	                    </div>
                    </footer>
                  </section>
                </aside>
                <!-- / side content -->
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      	
					  <div class="row row-sm" id="intro">
							<div class="col-xs-12">
		                    	<div class="text-center m-b-lg">
									<h1 class="fontBig text-white animated"><%=propLang.get("sel_filters") %></h1>
									
								</div>
							</div>
						</div>
						<div class="row m-t row-sm">
							<div class="col-xs-12">
								<div id="msg"></div>
								
								
		                      	
		                      	<section class="panel panel-default" id="tb-data">
					              <header class="panel-heading font-bold">
					                <span id="table_id">Data Results</span>
					                
					                <a href="#" onclick="excelExport()" class="btn btn-default btn-xs m-l-sm pull-right" >
			                            <i class="fa fa-table"></i>
			                            Export Data
			                        </a>
					              </header>
					              
					              <div class="table-responsive">
		                      		<table id="test" class="table table-striped">
									<thead>
										<tr>
											<th>Row</th>
											<th>AccountID</th>
											<th>StoreID</th>
											<th>DoorID</th>
											<th>Store</th>
											<th>Year</th>
											<th>Month</th>
											<th>Day</th>
											<th>Hour</th>
											<th>Minute</th>
											<th>KPI</th>
											<th>KPI Desc</th>
											<th>Value</th>
										</tr>
									</thead>
									<tbody>
									
									</tbody>
									<tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="13"><div id="total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					</tfoot>
									</table>
		                      	  </div>
		                        </section>
		                        
		                        <!--  ----  -->
		                        
		                        <section class="panel panel-default" id="tb-data2">
					              <header class="panel-heading font-bold">
					                <span id="table_id2">Data Results</span>
					                
					                <a href="#" onclick="excelExport2()" class="btn btn-default btn-xs m-l-sm pull-right" >
			                            <i class="fa fa-table"></i>
			                            Export Data
			                        </a>
					              </header>
					              
					              <div class="table-responsive">
		                      		<table id="test2" class="table table-striped">
									<thead>
										<tr>
											<th>Row</th>
											<th>AccountID</th>
											<th>StoreID</th>
											<th>DoorID</th>
											<th>Store</th>
											<th>Event</th>
											<th>Event Time</th>
										</tr>
									</thead>
									<tbody>
									
									</tbody>
									<tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="7"><div id="total2" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					</tfoot>
									</table>
		                      	  </div>
		                        </section>
		                        
		                        <!--  ---  -->
		                        
		                        <section class="panel panel-default" id="tb-data3">
					              <header class="panel-heading font-bold">
					                <span id="table_id3">Data Results</span>
					                
					                <a href="#" onclick="excelExport3()" class="btn btn-default btn-xs m-l-sm pull-right" >
			                            <i class="fa fa-table"></i>
			                            Export Data
			                        </a>
					              </header>
					              
					              <div class="table-responsive" id="tbl_data3">
					              	
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
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js?v=2"></script>
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script type="text/javascript" src="xlsx/shim.min.js"></script>
  <script type="text/javascript" src="xlsx/xlsx.full.min.js"></script>
  <script type="text/javascript" src="xlsx/Blob.js"></script>
  <script type="text/javascript" src="xlsx/FileSaver.js"></script>
  
  <script>
  
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		$('#tb-data').hide();
		$('#tb-data2').hide();
		$('#tb-data3').hide();
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
	
	$('#ev_li').hide();
	
	function chgDataType(type) {
		if(type=='G') {
			$('#ev_li').hide();
			$('#kpi_li').show();
			$('#export_type').attr('disabled', false);
			$('#mail').prop('disabled', false);
		}
		if(type=='R') {
			$('#kpi_li').hide();
			$('#ev_li').show();
			$('#export_type').attr('disabled', false);
			$('#mail').prop('disabled', false);
		}
		if(type=='P') {
			$('#ev_li').hide();
			$('#kpi_li').show();
			$('#export_type').val('V');
			$('#export_type').attr('disabled', true);
			$('#mail').val('');
			$('#mail').prop('disabled', true);
		}
	}
	
	$('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'bottom',
	    autoclose: true
	});  
	
	function selAll() {
  		$('#kpi option').prop('selected', true);
  		$('#kpi').trigger("chosen:updated");
  	}
	
	function checkExport(type) {
		if(type=='W'){
			$('#mail').val('');
			$('#mail').prop('disabled', true);
		}
		else {
			$('#mail').prop('disabled', false);
		}
	}
	
	function esporta() {
		
		var err = "";
		var dataType = $('#dataType').val();
		
		var idstore = $('#idstore').val();
		var hour = $('#hour').val();
		
		if(dataType=='G'||dataType=='P') {
			if (!$("#kpi").val())
			{
				err+="<br/> - Choose at least one KPI";
			}
		}
		if(dataType=='R') {
			if (!$("#event").val())
			{
				err+="<br/> - Choose an event";
			}
		}
		var kpi = $('#kpi').val();
		var event = $('#event').val();
		
		var dtda = $('#dtda').val();	
		var dtal = $('#dtal').val();
		
		var from = $("#dtda").val().split("-")
		var f = new Date(from[2], from[1] - 1, from[0])
		
		var to = $("#dtal").val().split("-")
		var t = new Date(to[2], to[1] - 1, to[0])
		
		
		// end - start returns difference in milliseconds 
		var diff = new Date(t.getTime() - f.getTime());
		
		// get days
		var days = diff/1000/60/60/24;
		
		if($.trim(dtda)=='') {
			err+="<br/> - Set From date";
		}
		if($.trim(dtal)=='') {
			err+="<br/> - Set To date";
		}
		if(days<0) {
			err+="<br/> - To date < From date";
		}
		
		var exportType = $('#export_type').val();
		
		if(exportType=='M'){
			var mail = $('#mail').val();
			if($.trim(mail)=='') {
				err+="<br/> - Insert a valid email";
			}
		}
		
		var data = $('#frm-export').serialize();
		
		//err='';
		if($.trim(err)!='') {
			
			$('#tb-data').hide();
			$('#tb-data2').hide();
			
			
			$('#msg').html("<div class=\"alert alert-warning\"><strong>Check the errors:</strong>"+err+"</div>");
		}
		else {
			
			$('#msg').html('');
			$('#msg').hide();
			
			$('#intro').hide();
			showLoader2();
			
			
			if(dataType=='G') {
				$('#tb-data').show();
				$('#tb-data2').hide();
				$('#tb-data3').hide();
				
				
				$('#test').DataTable( {
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
				      "sDom": "<'row'<'col-sm-12'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
				       "aaSorting": [],
				      //"iDisplayLength": 50,
				      
		            "processing": true,
		            "serverSide": true,
		            "ajax": {
		            	"url": "../../servlet/ws/GetKpi?kpi="+kpi+"&from="+dtda+"&to="+dtal+"&idstore="+idstore+"&hour="+hour+"&account_id=${account_code}&id_customer=${id_customer}",
		            	"type": "POST"
		            },
		            "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
			        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
			        	  $('td:eq(2)', nRow).html('<span class="label bg-light">' + aData[2] + '</span>');
			        	  $('td:eq(3)', nRow).html('<span class="label bg-light">' + aData[3] + '</span>');
			        	  $('td:eq(10)', nRow).html('<span class="label bg-light">' + aData[10] + '</span>');
			        	  $('td:eq(12)', nRow).html('<b>' + aData[12] + '</b>');
			              
			              return nRow;
			        },
			        "fnInitComplete": function (settings,json) {
			              $('#total').html('<b>Total Records: '+settings.fnRecordsTotal()+' records</b>');
			        }
		        } );
				
			}
			
			if(dataType=='R') {
				$('#tb-data').hide();
				$('#tb-data2').show();
				$('#tb-data3').hide();
				
				
				$('#test2').DataTable( {
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
				      "sDom": "<'row'<'col-sm-12'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
				       "aaSorting": [],
				      //"iDisplayLength": 50,
				      
		            "processing": true,
		            "serverSide": true,
		            "ajax": "../../servlet/ws/GetRawData?kpi="+event+"&from="+dtda+"&to="+dtal+"&idstore="+idstore+"&hour="+hour+"&account_id=${account_code}&id_customer=${id_customer}",
		            "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
			        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
			        	  $('td:eq(2)', nRow).html('<span class="label bg-light">' + aData[2] + '</span>');
			        	  $('td:eq(3)', nRow).html('<span class="label bg-light">' + aData[3] + '</span>');
			        	  
			              return nRow;
			        },
			        "fnInitComplete": function (settings,json) {
			              $('#total2').html('<b>Total Records: '+settings.fnRecordsTotal()+' records</b>');
			        }
		        } );
				
			}
			if(dataType=='P') {
				$('#tb-data').hide();
				$('#tb-data2').hide();
				$('#tb-data3').show();
				
				showLoader();
				
				$.ajax({
			        url: "../ajax/export_pivot_data.jsp",
			        type: 'POST',
			        dataType: 'html',
			        data: "kpi="+kpi+"&dtda="+dtda+"&dtal="+dtal+"&idstore="+idstore+"&hour="+hour+"&account_code=${account_code}&id_customer=${id_customer}&mail="+mail+"&dataType="+dataType,
			        beforeSend: function () {
			        	
					},
			        success: function (data) {
			        	$('#tbl_data3').html('');
			        	$('#tbl_data3').html($.trim(data));
			        	
			        	hideLoader();
			        },
			        error: function () {
			        	alert('Errore! si e\' verificato un errore.');
			        }
			    });
				
				
			}
			
			
			hideLoader2();
				
			
			
			
			if(exportType=='M'){
				var mail = $('#mail').val();
				
				if(dataType=='R') {
					kpi = event;
				}
				
				$.ajax({
			        url: "../ajax/write_data_export.jsp",
			        type: 'POST',
			        dataType: 'html',
			        data: "kpi="+kpi+"&dtda="+dtda+"&dtal="+dtal+"&idstore="+idstore+"&hour="+hour+"&account_code=${account_code}&id_customer=${id_customer}&mail="+mail+"&dataType="+dataType,
			        beforeSend: function () {
			        	
			        },
			        success: function (data) {
			        	$('#msg').html('');
			        	$('#msg').show();
			        	$('#msg').html("<div class=\"alert alert-success\"><strong>"+$.trim(data)+"</strong></div>");
			        },
			        error: function () {
			        	alert('Errore! si e\' verificato un errore.');
			        }
			    });
			
			}
			
		}
		
		//alert(data);
		
	}
	
	function excelExport() {
		
		var kpi = $('#kpi').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		var idstore = $('#idstore').val();
		var hour = $('#hour').val();
		
		
		var formData = $('#deskForm').serialize();
		var href = '../popup/excelExport2.jsp?kpi='+kpi+'&dtda='+dtda+'&dtal='+dtal+'&idstore='+idstore+'&hour='+hour+'&account_code=${account_code}&id_customer=${id_customer}';
    	
        $('div.modal-backdrop ').remove();
        $('#ajaxModal').remove();
        $("div.modal").remove();
        
        showLoader2();
		
        var data = '';
        $.ajax({
    	  url: href,
    	  async: true,
    	  data: formData,
    	  dataType: 'html',
    	  beforeSend: function () {
    		  
    	  },
    	  success: function(html) {
    	      
    		  var $modal = $('<div class="modal fade" id="ajaxModal"><div class="modal-body"></div></div>');
    	      $('body').append($modal);
    	      $modal.modal();
    	        
    		  //alert(html);
    		  $modal.append(html);
    		  
    		  hideLoader2();
    		  
    	  },
    	  error: function (xhr, ajaxOptions, thrownError) {
   	        alert(xhr.status);
   	        alert(thrownError);
   	        
   	        hideLoader2();
   	        
   	      }
    	});
		
	}
	
	function excelExport2() {
		
		var kpi = $('#event').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		var idstore = $('#idstore').val();
		var hour = $('#hour').val();
		
		var formData = $('#deskForm').serialize();
		var href = '../popup/excelExport2_2.jsp?kpi='+kpi+'&dtda='+dtda+'&dtal='+dtal+'&idstore='+idstore+'&hour='+hour+'&account_code=${account_code}&id_customer=${id_customer}';
    	
		console.log(href);
		
		
        $('div.modal-backdrop ').remove();
        $('#ajaxModal').remove();
        $("div.modal").remove();
        
        showLoader2();
		
        var data = '';
        $.ajax({
    	  url: href,
    	  async: true,
    	  data: formData,
    	  dataType: 'html',
    	  beforeSend: function () {
    		  
    	  },
    	  success: function(html) {
    	      
    		  var $modal = $('<div class="modal fade" id="ajaxModal"><div class="modal-body"></div></div>');
    	      $('body').append($modal);
    	      $modal.modal();
    	        
    		  //alert(html);
    		  $modal.append(html);
    		  
    		  hideLoader2();
    		  
    	  },
    	  error: function (xhr, ajaxOptions, thrownError) {
   	        alert(xhr.status);
   	        alert(thrownError);
   	        
   	        hideLoader2();
   	        
   	      }
    	});
		
	}
	
	function excelExport3() {
		
		
		function doit(type, fn, dl) {
			var elt = document.getElementById('tbl_data3');
			var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
			return dl ?
				XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
				XLSX.writeFile(wb, fn || ('download.' + (type || 'xlsx')));
		}
		doit('xlsx');
		
	}
	
	
	
	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  </body>
</html>
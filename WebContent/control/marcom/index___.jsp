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

String username = (String) session.getAttribute("username").toString().toUpperCase();
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
		z-index: 1;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
	div.horizontal {
	    width: 100%;
	    overflow: auto;
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
                  
                  
                  <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                  	  <h2 class="font-thin m-b">Desk Smart Control <%=propLang.get("versione") %> 2.0.0</h2>
                      
                      <div class="row row-sm">
	              	  	<div class="col-xs-12">
	                    
	                    	<section class="panel panel-default" id="clients_data_view">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-desktop"></i></span>
			                      <span class="hidden-sm">Clients</span>
			                      
			                    </header>
			                    <section class="panel wrapper">
					              	<div class="subnav">
										<ul class="nav nav-pills">
											<li><a href="client-dett.jsp" data-toggle="ajaxModal">New Client</a></li>
										</ul>
									</div>
								</section>
								
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="clients_data_table" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th>Client</th>
		                                    <th>Store</th>
		                                    <th>Device</th>
		                                    <th>Device Type</th>
		                                    <th>IP Address</th>
		                                    <th>Connected</th>
									        <th></th>								        
									      </tr>
									  </thead>
			      					  <tbody id="clients_list">
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="7"><div id="clients_total" style="margin-left:5px"></div></td>
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
  
  <script src="../../js/jquery.min.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
<script>
    
     jQuery(window).load(function() {
		jQuery('#loading-image').hide();
        
		showLoader2();
        authenticate();
	
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
    
	function parsleyForm() {
		$('#actionForm').parsley();
	}
	function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		 });
	  }
	  
    var service = 'https://51.136.12.25:5001';
    var accessToken = '';
    
    var customers = [];
    var stores = [];
    var devices = [];
    var clients = [];
    
    
    function authenticate() {
        const obj = {
            username: 'marcomsrl',
            password: ')T_tLMkvY4zD_Ad&'
        };
        $.ajax({
            type: "POST",
            accepts: "application/json",
            url: service + '/api/Auth/login',
            contentType: "application/json",
            data: JSON.stringify(obj),
            error: function (jqXHR, textStatus, errorThrown) {                
                console.log(errorThrown);
            },
            success: function (result) {
                accessToken = result.tokenString;
                //$('#available_buttons').show();
                
                getClients();
            }
        });
    }
    
    var table;
    
    function getClients() {
        $.ajax({
            type: "GET",
            accepts: "application/json",
            url: service + '/api/openvpn/clients/get',
            contentType: "application/json",
            cache: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'bearer ' + accessToken);
            },
            success: function (data) {
                clients = data;
                fillClientsTable(data);
                
                if (! $.fn.dataTable.isDataTable('#clients_data_table')) {
                    table = $('#clients_data_table').DataTable({ 
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
          		    });
                    
                    //$('#clients_data_view').show();
                    
                    $('#clients_total').html('<b>Total Clients: '+clients.length+'</b>')
                    
                }
                
                //table.fnPageChange("first",1);
                
                
                hideLoader2();
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                console.log(errorThrown);
            }
        });
    }
    
    function fillClientsTable(data) {
        const tBody = $("#clients_list");
        
        //$(tBody).empty();
        
        $('#clients_list > tr').remove();
        $.each(data, function (i, obj) {
        	
            const tr = $("<tr></tr>")
                .append($("<td></td>").html("<span class='label bg-light'>"+obj.device.store.customer.name + "[" + obj.device.store.customer.code + "]</span>"))
                .append($("<td></td>").text(obj.device.store.name + "[" + obj.device.store.code + "]"))
                .append($("<td></td>").text(obj.device.name + "[" + obj.device.code + "]"))
                .append($("<td></td>").text(getDeviceType(obj.device.deviceType)))
                .append($("<td></td>").text(obj.ipAddress))
                .append($("<td></td>").text(obj.connected))
                .append($("<td><a href='#' class='btn btn-danger' onclick='deleteClient(" + i.toString() + ");'><i class='fa fa-times'></i></a></td>"));
                
                
            tr.appendTo(tBody);
        });
    }
    
    function deleteClient(index) {        
    	
    	if(confirm("Confirm Delete record?")) {
    		
    		console.log(JSON.stringify(clients[index]));
    		
    		showLoader2();
    		
	        $.ajax({
	            type: "POST",
	            accepts: "application/json",
	            url: service + '/api/openvpn/clients/delete',
	            contentType: "application/json",
	            data: JSON.stringify(clients[index]),
	            beforeSend: function (xhr) {
	                xhr.setRequestHeader('Authorization', 'bearer ' + accessToken);
	            },
	            error: function (jqXHR, textStatus, errorThrown) {                
	                console.log(errorThrown);
	                hideLoader2();
	            },
	            success: function (result) {
	            	
	            	//clients.splice(index, 1);
	                //fillClientsTable(clients);
	                //$('#clients_data_table').DataTable();
	                
	                //$('#clients_data_table').DataTable();
	                //$('#clients_data_table').DataTable({
	                //	"iDisplayLength": 10
	                //});
	                
	                loadTable();
	                
	                //$('#clients_total').html('<b>Total Clients: '+clients.length+'</b>')
	                
	                console.log('successfully deleted!');
	                
	                getClients();
	                
	                hideLoader2();
	                
	            }
	        });
	        
    	}
    }
    
  
    
    function getDeviceType(index) {
            var type = "";
            
            switch (index) {
                case 0:
                    type = "Router";
                    break;
                case 1:
                    type = "Checkpoint Device";
                    break;
                case 2: 
                    type = "Router NAT";
                    break;
                case 3:
                    type = "User";
                    break;
                default:
                    type = "ERROR";
                    break;
            }

            return type;
    }
    
    function getCustomers() {
    	
    	var username = '<%=username%>';
    	
    	showLoader2();
    	
    	$.ajax({
            type: "GET",
            accepts: "application/json",
            url: "../../ats/GetCustomers?username="+username,
            contentType: "application/json",
            cache: false,
            success: function (data) {
                
            	//alert(JSON.stringify(data));
            	
            	 $.each(data.customers, function (i, obj) {
            		 
            		
            		 $('#customerId').append('<option value="'+obj.id+'">'+obj.accountid+' - '+obj.description+'</option>'); 
            		 
                 	
            	 });
            	 
            	 $('#customerId').selectpicker('refresh');
            	
                
                hideLoader2();
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                console.log(errorThrown);
            }
        });
    	
    	
    }
    
	function getStores(idCustomer) {
    	
    	var username = '<%=username%>';
    	
    	showLoader2();
    	
    	$.ajax({
            type: "GET",
            accepts: "application/json",
            url: "../../ats/GetStores?idusr="+username+'&idcustomer='+idCustomer,
            contentType: "application/json",
            cache: false,
            success: function (data) {
                
            	//alert(JSON.stringify(data));
            	
            	
            	$('#storeId')
				    .find('option')
				    .remove()
				    .end()
				    .append('<option value="">-</option>')
				    .val('');
            	
            	 $.each(data.listings, function (i, obj) {
            		 
            		
            		 $('#storeId').append('<option value="'+obj.label+'">'+obj.label+'</option>'); 
            		 
                 	
            	 });
            	 
            	 $('#storeId').selectpicker('refresh');
            	
                
                hideLoader2();
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                console.log(errorThrown);
            }
        });
    	
    	
    }
	
	$(document).on("submit", "form", function(e){
	    e.preventDefault();
	    //alert('it works!');
	    
	    showLoader2();
	    
	    var data = $('#actionForm').serialize();
	    
	    var _devType = $('#devType').val();
	    var _name = $('#name').val();
	    var _code = $('#code').val();
	    var _customerId = $('#customerId').val();
	    var _customerText = $("#customerId option:selected").html();
        
	    var _customerCode = _customerText.split(' - ')[0];
	    var _customerName = _customerText.split(' - ')[1];
	    
	    var _storeId = $('#storeId').val();
	    var _storeCode = _storeId.split(' - ')[0];
	    var _storeName = _storeId.split(' - ')[1]+' '+_storeId.split(' - ')[2]!='Undefined' ? _storeId.split(' - ')[2] : "";
	    
	    var _ip2nd = $('#iP2nd').val();
	    var _ip3rd = $('#iP3rd').val();
	    var _ip4th = $('#iP4th').val();
	    
	    
	    
	    client_obj = {
	            device: {
	                store: {
	                    customer: {
	                        id: 1,
	                        name: "TEST1",
	                        code: "00001",
	                        iP2nd: 1
	                    },
	                    id: 0,
	                    name: "STORE2",
	                    code: "00002",
	                    iP3rd: 2
	                },
	                id: 0,
	                name: "DEVICE1",
	                code: "00000",
	                deviceType: 0,
	                iP4th: 0           
	            }
	        }
	    
	    client_obj = {
            device: {
                store: {
                    customer: {
                        id: 0,
                        name: _customerName,
                        code: _customerCode,
                        iP2nd: parseInt(_ip2nd)
                    },
                    id: 0,
                    name: _storeName,
                    code: _storeCode,
                    iP3rd: parseInt(_ip3rd)
                },
                id: 0,
                name: _name,
                code: _code,
                deviceType: parseInt(_devType),
                iP4th: parseInt(_ip4th)        
            }
        }
        
	    console.log(JSON.stringify(client_obj));
	    
	    $.ajax({
            type: "POST",
            accepts: "application/json",
            url: service + '/api/openvpn/clients/add',
            contentType: "application/json",
            cache: false,
            data: JSON.stringify(client_obj),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'bearer ' + accessToken);
            },
            success: function (data) {
                console.log('successfully added!');
                
                $('.modal').remove(); $('div.modal-backdrop').remove();
                loadTable();
                
                getClients();
                
                hideLoader2();
            },
            error: function (jqXHR, textStatus, errorThrown) {  
            	
            	console.log(jqXHR);
            	console.log(textStatus);
                console.log(errorThrown);
                
                hideLoader2();
            }
        });
	    
	    
	    
	    //alert(data);
	    return  false;
	});
	
	
	function loadTable() {
		
		table.destroy();
		
	//	$("#clients_data_table  tbody > tr").remove();
		$('#clients_data_table tbody').remove();
		$('#clients_data_table').append("<tbody id=\"clients_list\"></tbody>");
		
		//$('#clients_list').empty();
        $('#clients_list').append('<tr><td colspan="7">Please Wait...</td></tr>');
		
		
		
		/*
		table = $('#clients_data_table').DataTable({ 
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
		    });
		*/
	} 
	
</script>

<%@ include file="../../inc/autorized.jsp" %>
      
</body>
</html>
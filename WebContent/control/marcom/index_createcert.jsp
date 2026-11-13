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
                  
                  <%@ include file="../../inc2/menuApp3.jsp" %>
              
                  <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                  	  <h2 class="font-thin m-b">Desk CheckPoint Clients</h2>
                      
                      <div class="row row-sm">
	              	  	<div class="col-xs-12">
	                    
	                    	<section class="panel panel-default" id="clients_data_view">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-desktop"></i></span>
			                      <span class="hidden-sm">Add Clients</span>
			                      
			                      
			                    </header>
			                    <section class="panel wrapper">
					              	<div class="subnav">
										<ul class="nav nav-pills">
											<li><a href="#" onclick="addClients()">Add Clients</a></li>
										</ul>
									</div>
									
									
									<div class="line line-dashed b-b line-lg pull-in"></div>
									
									<label>Select Server</label>
									<select id="_server" class="form-control input-sm ss" onchange="selServer(this.value)">
			                           <option value="https://vpn-eu-01.smart-control.it" selected>Europe Server</option>
			                           <option value="https://vpn-au-01.smart-control.it">Australia Server</option>
			                           <option value="https://vpn-us-01.smart-control.it">US Server</option>
			                           <option value="https://vpn-cn-01.smart-control.it">Cina Server</option>
			                        </select>
			                         
									<div id="message"></div>
									
								</section>
								
			                    
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
        
		var server = $('#_server').val();
        selServer(server);
        
		//showLoader2();
        //authenticate();
        selpick();
        
        
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
	
	
	  
    //var service = 'https://51.136.12.25:5001';
    var service = ' https://vpn-eu-01.smart-control.it/'
    var accessToken = '';
    
    function selServer(val) {
    	
    	//$('#clients_total').html('');
    	clients = [];
    	
    	showLoader2();
    	
    	service = val;
    	authenticate();
    	
    }
    
    var ids_to_devices = {
    	0: "Router",
		1: "Checkpoint Device",
     	2: "Router NAT",
     	3: "User"
	};
    
    var customers = [];
    var stores = [];
    var clients = [];
    
    var ips = [];
    
    
    function reverseObj(o) {
    	var r = {};
    	
    	//Object.entries(o).forEach(function(i) { r[i[1]]=i[0]; });
    	
    	for (const [k, v] of Object.entries(o))
    		r[v]=k;
    	
    	return r;
    }
    
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
                
                alert(textStatus+' '+errorThrown);
                hideLoader2();
            },
            success: function (result) {
                accessToken = result.tokenString;
                
                console.log(JSON.stringify(result));		
                hideLoader2();
                
            }
        });
    }
    	
	function addClients() {
		
		username = 'DEVELOP';
		idCustomer = '248';
		
		showLoader2();
    	
    	$.ajax({
            type: "GET",
            accepts: "application/json",
            url: "../../ats/GetStores2?idusr="+username+'&idcustomer='+idCustomer,
            contentType: "application/json",
            cache: false,
            async: false,
            success: function (data) {
                
            	//console.log(JSON.stringify(data));
            	
            	
            	x = 0;
            	ip1 = 1;
            	ip2 = 0;
            	$.each(data.listings, function (i, obj) {
            		 
            		
            		x+=1;
            		ip2+=1;
            		
            		if(ip2>254) {
            			ip1=ip1+1;
            			ip2=1;
            		}
            		
            		var _devType = 1;
             	    var _name = 'NEO '+x;
             	    var _code = ''+x;
             	    var _customerId = '248';
             	    var _customerText = 'Coles';
                     
             	    var _customerCode = '04484';
             	    var _customerName = 'Coles';
             	    
             	    var _storeId = obj.value;
             	    var _storeCode = obj.storecode;
             	    var _storeName = obj.label; 
             	    
             	    var _ip  = "11.3."+ip1+"."+ip2;
             	    var _networkIp =  '192.168.2.1';
             	    
             	       
             	    client_obj = {
                         device: {
                             store: {
                                 customer: {
                                     id: parseInt(zeroFill(parseInt(_customerCode), 5)),
                                     name: _customerName,
                                     code: zeroFill(parseInt(_customerCode), 5)
                                     
                                 },
                                 id: parseInt(zeroFill(parseInt(_customerCode), 5)+''+zeroFill(parseInt(_storeCode), 5)),
                                 name: _storeName,
                                 code: _storeCode
                             },
                             id: 0,
                             name: _name,
                             code: _code,
                             deviceType: parseInt(_devType)
                             
                         },
                         id: 0,
                         vpnipAddress: _ip,
                         networkIPClass: _networkIp,
                         disabled: false
                     };
                     
             	    console.log("\n\n"+JSON.stringify(client_obj)+"\n\n");
             	    
             	   	
             	    
             	   $.ajax({
                       type: "POST",
                       accepts: "application/json",
                       url: service + '/api/openvpn/clients/add',
                       contentType: "application/json",
                       cache: false,
                       async: false,
                       data: JSON.stringify(client_obj),
                       beforeSend: function (xhr) {
                           xhr.setRequestHeader('Authorization', 'bearer ' + accessToken);
                       },
                       success: function (data) {
                           console.log('successfully added!');
                           
                       },
                       error: function (jqXHR, textStatus, errorThrown) {  
                       	
                       	console.log(jqXHR);
                       	console.log(textStatus);
                           console.log(errorThrown);
                           
                           hideLoader2();
                       }
                   });
             	   
             	  wait(2000);
           	    
             	   
             	    
             	  	x+=1;
	           		ip2+=1;
	           		
	           		if(ip2>254) {
	           			ip1=ip1+1;
	           			ip2=1;
	           		}
	           		
	           		var _devType = 1;
            	    var _name = 'NEO '+x;
            	    var _code = ''+x;
            	    var _customerId = '248';
            	    var _customerText = 'Coles';
                    
            	    var _customerCode = '04484';
            	    var _customerName = 'Coles';
            	    
            	    var _storeId = obj.value;
            	    var _storeCode = obj.storecode;
            	    var _storeName = obj.label; 
            	    
            	    
            	    	
            	    
            	    var _ip  = "11.3."+ip1+"."+ip2;
            	    var _networkIp =  '192.168.2.1';
            	    
            	       
            	    client_obj = {
                        device: {
                            store: {
                                customer: {
                                    id: parseInt(zeroFill(parseInt(_customerCode), 5)),
                                    name: _customerName,
                                    code: zeroFill(parseInt(_customerCode), 5)
                                    
                                },
                                id: parseInt(zeroFill(parseInt(_customerCode), 5)+''+zeroFill(parseInt(_storeCode), 5)),
                                name: _storeName,
                                code: _storeCode
                            },
                            id: 0,
                            name: _name,
                            code: _code,
                            deviceType: parseInt(_devType)
                            
                        },
                        id: 0,
                        vpnipAddress: _ip,
                        networkIPClass: _networkIp,
                        disabled: false
                    };
                    
            	    console.log("\n\n"+JSON.stringify(client_obj)+"\n\n");
             	    
             	    
             	    
             	    $.ajax({
                         type: "POST",
                         accepts: "application/json",
                         url: service + '/api/openvpn/clients/add',
                         contentType: "application/json",
                         cache: false,
                         async: false,
                         data: JSON.stringify(client_obj),
                         beforeSend: function (xhr) {
                             xhr.setRequestHeader('Authorization', 'bearer ' + accessToken);
                         },
                         success: function (data) {
                             console.log('successfully added!');
                           
                         },
                         error: function (jqXHR, textStatus, errorThrown) {  
                         	
                         	console.log(jqXHR);
                         	console.log(textStatus);
                             console.log(errorThrown);
                             
                         }
                     });
             	    
             	   wait(2000);
            	    
             	    
	            		 
	            		 
	           	});
	            	
            	
            	
            	
            	
                
                hideLoader2();
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                console.log(errorThrown);
            }
        });
    	
    	showLoader2();
		
		
	}
	
	
	function zeroFill( number, width )
	{
	  width -= number.toString().length;
	  if ( width > 0 )
	  {
	    return new Array( width + (/\./.test( number ) ? 2 : 1) ).join( '0' ) + number;
	  }
	  return number + ""; // always return a string
	}
	
	function wait(ms){
	   var start = new Date().getTime();
	   var end = start;
	   while(end < start + ms) {
	     end = new Date().getTime();
	  }
	}
	
	
</script>

<%@ include file="../../inc/autorized.jsp" %>
      
</body>
</html>
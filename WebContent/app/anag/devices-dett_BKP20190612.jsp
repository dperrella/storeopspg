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
  <title><%=propLang.get("dev_dett") %> | SmartSuite</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
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
            <section id="bjax-target">
              <section class="hbox stretch">
            	
            	
                <aside class="col-sm-9 padder " >
	              <section class="vbox">
	                <section class="scrollable hover">
                      
                      <!-- 
                      	<h1>${id_customer} - ${id_account} - ${openModal} - ${openLink} - ${linNum}</h1>
		              -->  
		                <h2 class="font-thin m-b"><%=propLang.get("dev_dett") %></h2>
		                
		                <form method="post" action="devices-dett.htm">
		                <input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("dev_dett") %> ${ds_devicetype}
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<!-- <li><a href="devices-list.htm"><%//=propLang.get("torna") %></a></li>-->
									<li><a href="stores-dett.htm?tipoForm=M&id=${Ids}"><%=propLang.get("torna") %></a></li>
									<c:if test = "${openLink == 'Y'}">
									<li><a href="#" onclick="openModal();">Lines Config</a></li>
									</c:if>
								</ul>
							</div>
						  </section>
						  
			              <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">General Information</span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <!-- 
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-2">
						          <label>Id Device</label>
						          <input type="text" id="id" name="id" class="form-control" 
						          	value="${id_device}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-2">
						          <label>Id Store</label>
						          <input type="text" id="id_store" name="id_store" class="form-control" 
						          	value="${id_store}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    -->
						    <input type="hidden" id="id" name="id" value="${id_device}">   
						    <input type="hidden" id="id_store" name="id_store" value="${id_store}"/>   
						    
						    <div class="form-group pull-in clearfix">

						        <div class="col-sm-4">
						          <label>Device Type</label>
						          <select id="device_type" name="device_type" class="form-control" ${readonly} onchange="SelDeviceType(this.value)">
						          	<option value="">-</option>
						          	<c:forEach items="${typ}" var="typ" varStatus="status">
						          	<option value="${typ.xcdel}">${typ.xcdel} - ${typ.xdtab}</option>
						         	</c:forEach>
						          </select>  
						        </div>
						        
						        <div class="col-sm-4">
						          <label>IP Address</label>
						          <input type="text" id="ip" name="ip" class="form-control"  maxlength="15" value="${ip}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Port</label>
						          <input type="text" id="port" name="port" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57" maxlength="7" value="" ${readonly}>   
						        </div>
						        
						    </div>
						  	
						  	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-2">
						          <label>Read Every</label>
						          <input type="text" id="readevery" name="readevery" class="form-control" onkeypress="return event.charCode >= 48 && event.charCode <= 57"  maxlength="4" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-2">
						          <label>From Date</label>
						          <input class="form-control" name="fromdate" id="fromdate" type="text" value="${fromdate}" readonly> 
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Enabled</label>
						          <select id="enabled" name="enabled" class="form-control" onchange="SelChangeEnabled(this)" ${readonly}>
						          	<option value="true">TRUE</option>
						          	<option value="false">FALSE</option>
						          </select>   
						        </div>
						        
						        <div class="col-sm-4" id="divreasons" style="visibility: hidden;">
						          <label>Reason</label>
						          <select id="reason" name="reason" class="form-control" ${readonly}>
						          	<option value="">-</option>
										${rsn}
						          </select>  
						        </div>
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Account ID</label>
						          <input type="text" id="accountid" name="accountid" onkeypress="return event.charCode >= 48 && event.charCode <= 57"  maxlength="4" class="form-control" value="${accountid}" readonly>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Store ID</label>
						          <input type="text" id="storeid" name="storeid" onkeypress="return event.charCode >= 48 && event.charCode <= 57"  maxlength="4" class="form-control" value="${storeid}" readonly>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Device ID</label>
						          <input type="text" id="doorid" name="doorid" onkeypress="return event.charCode >= 48 && event.charCode <= 57"  maxlength="4" class="form-control" value="${doorid}" ${readonly}>   
						        </div>
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Minute Not Update(in minutes)</label>
						          <input type="text" id="minute_not_update" name="minute_not_update" onkeypress="return event.charCode >= 48 && event.charCode <= 57"  maxlength="4" class="form-control" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Create or update the monitored device list (only for scan application)</label>
						          <select id="updating" name="updating" class="form-control" ${readonly}>
						          	<option value="true">TRUE</option>
						          	<option value="false">FALSE</option>
						          </select>   
						        </div>
						  	
						        <div class="col-sm-4">
						          <label>Device Name</label>
						          <input type="text" id="nome" name="nome" class="form-control"  maxlength="70" value="${nome}" ${readonly}>  
						         </div>
						        						  	
						  	</div>
						  	
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Scan</label>
									<fieldset>
										<input type="checkbox" name="scan" id="scan" value="Y" data-required="true" ${readonly}/>  
									</fieldset>										
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Download Data</label>
									<fieldset>
										<input type="checkbox" name="downloaddata" id="downloaddata" checked disabled value="Y"  ${readonly}/>  
									</fieldset>										
						        </div>
						  	
						        <div class="col-sm-4">
						          <label>Ftp Import</label>
									<fieldset>
										<input type="checkbox" name="ftpimport" id="ftpimport"  checked disabled value="Y" ${readonly}/>  
									</fieldset>										
						        </div>
						        						  	
						  	</div>		
						  	
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Mac Address</label>
						          <input type="text" id="macaddress" name="macaddress" class="form-control" onkeypress="return event.charCode != 39 && event.charCode != 34" maxlength="25" value="${macaddress}" ${readonly}> 									
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Line In</label>
						          <input type="text" id="linein" name="linein" onkeypress="return event.charCode >= 48 && event.charCode <= 57" maxlength="4" class="form-control" value="${linein}" ${readonly}> 									
						        </div>
						  	
						        <div class="col-sm-4">
						          <label>Line Out</label>
						          <input type="text" id="lineout" name="lineout" onkeypress="return event.charCode >= 48 && event.charCode <= 57" maxlength="4" class="form-control" value="${lineout}" ${readonly}> 									
						        </div>
						        						  	
						  	</div>							  					  	
						  	
						  	
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Shop Window</label>
						          <input type="text" id="vetrina" name="vetrina" onkeypress="return event.charCode >= 48 && event.charCode <= 57"  maxlength="4" class="form-control" value="${vetrina}" ${readonly}> 									
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Other Passage</label>
						          <input type="text" id="altropassaggio" name="altropassaggio" onkeypress="return event.charCode >= 48 && event.charCode <= 57" maxlength="4" class="form-control" value="${altropassaggio}" ${readonly}> 									
						        </div>

						        <div class="col-sm-4">
						          <label>Time Zone</label>
						          <select id="timezone" name="timezone" class="form-control" ${readonly}>
						          	<option value="">-</option>
						          	<c:forEach items="${tmz}" var="tmz" varStatus="status">
						          	<option value="${tmz.xcdel}">${tmz.cmp1} - ${tmz.xcdel} - ${tmz.xdtab}</option>
						         	</c:forEach>
						          </select>  
						        </div>
						        						  	
						  	</div>
						  	
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Username</label>
						          <input type="text" id="devusername" name="devusername" class="form-control" value="${devusername}" ${readonly}> 									
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Password</label>
						          <input type="text" id="devpassword" name="devpassword" class="form-control" value="${devpassword}" ${readonly}> 									
						        </div>
						        	
						        <div class="col-sm-4">
						          <label>Update Time</label>
						          <select id="devupdtime" name="devupdtime" class="form-control" ${readonly}>
						          	<option value="false" selected>FALSE</option>
						          	<option value="true">TRUE</option>
						          </select>    									
						        </div>						        						  	
						  	</div>							  								  	
						  	
						  	
						  	<div class="line line-dashed b-b line-lg pull-in"></div>
						  	
						  	<div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">Notes</span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Notes</label>
						          <textarea id="notes" name="notes" class="form-control" maxlength="400" style="height:100px"
						          	${readonly}>${notes}</textarea>  
						        </div>
						    </div>
						  	
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" onclick="return Controlla();" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>  
		  				
		  				</section>
		  				</section>
	              </section>
	            </aside>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin">Devices Slave</p>
                        </div>
                        <div id="stores">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${sla}" var="sla" varStatus="status">
	                          <li class="list-group-item">
	                          	<div class="clear text-ellipsis" style="float: right;">
		                        	${sla.cmp3} &nbsp;&nbsp; ${sla.cmp4}
		                        </div> 
	                            <div class="pull-left m-l-xs m-r-sm">
			                          <span class="label bg-light">${sla.slavename} - ${sla.id_slave} ${sla.door_description}</span>
		                        </div>
		                        <br>
	                          	<div class="pull-left m-l-xs m-r-sm">
	                          	 	<span class="label bg-light">${sla.cmp1} &nbsp;&nbsp; ${sla.eventcode}</span>
	                          	</div>
	                          	<br>
<!--           	
	                          	<div class="pull-left m-l-xs m-r-sm">
	                          	 	<span class="label bg-light"></span>
	                          	</div>
-->	                          	
		                      </li>
	                        </c:forEach>
	                        </ul>
                        </div>
                    </section>
                    
                  </section>
                </section>
                
                </form>
                
                <!-- side content -->
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="devices-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-feed icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("dev_dev") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="devices-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("sto_cerca") %>">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            <!-- / side content -->
                
               
                
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
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/alertify.min.js"></script>
  <link href="../../css/alertify.core.css" rel="stylesheet">
  <link href="../../css/alertify.default.css" rel="stylesheet">
  
  
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<!--  <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
	
	$('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'top',
	    autoclose: true
	});  
    
  	
	$('#device_type').val('${device_type}');
	$('#enabled').val('${enabled}');
	$('#timezone').val('${timezone}');
	$('#updating').val('${updating}');
	$('#devupdtime').val('${devupdtime}');
	
	if (document.getElementById("enabled").value == "false" || document.getElementById("enabled").value == "f"){
			document.getElementById("divreasons").style.visibility = "visible";
			$('#reason').val('${cause}');
	}
	
	
	
	var om = '${openModal}';
  	
  	if(om=='Y') {
  		
		openModal();
		
	}
  	
  	function openModal() {
  		$( 'div.modal-backdrop').remove();
		$( '.modal' ).remove();
		
		var href = "../../app/popup/device-lines.htm?id_device=${id_device}&id_customer=${id_customer}&account_id=${id_account}&linNum=${linNum}&device_type=${device_type}";
		
		$.get(href, function(data) {
			$('<div class="modal" >' + data + '</div>').modal();
		});
  	}
  	
	function rowStatus(row) {
		
		if ($('#'+row+'status').is(":checked"))
		{
			$('#'+row+'doorid').prop('disabled', false);
			$('#'+row+'doorid').val($('#'+row+'doorid option:first').val());
			
			//reinit parsley
	        $('#dd-form').parsley();
			
		}
		else {
			$('#'+row+'doorid').prop('disabled', true);
			$('#'+row+'doorid').val($('#'+row+'doorid option:first').val());
			$('#'+row+'description').val('');
			
			setNew('SEL', row);
			
			$('#dd-form').parsley();
			
		}
		
	}
	
	function setNew(val, line) {
		
		if(val=='NEW') {
			$('#'+line+'sel').hide();
			$('#'+line+'new').show();
		}
		else {
			$('#'+line+'sel').show();
			$('#'+line+'new').hide();
		}
		
	}
	
	function saveLines() {
		
		var fld = $('#dd-form').serialize();
		
		$.ajax({
		      url: '../ajax/saveLines.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: fld,
		      success: function (data) {
		    	$( 'div.modal-backdrop').remove();
				$( '.modal' ).remove();
				
				//alert("<%=propLang.get("msgSuccess")%>");
				alertify.success("<%=propLang.get("msgSuccess")%>");
				
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
  </script>
  
  
  
  
  
  
<script>
function SelDeviceType(obj){
	
	 var str = obj;
	 
		document.getElementById("readevery").value = "";
		document.getElementById("minute_not_update").value = "";
		document.getElementById("port").value = "";

	 
	 switch(str) {
	    case "0":
			/*ok*/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = false;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			//document.getElementById("downloaddata").disabled = false;
			//document.getElementById("ftpimport").disabled = true;
			//$("#ftpimport").attr("data-required","false");
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			
			document.getElementById("macaddress").readOnly = false;
			document.getElementById("linein").readOnly = false;
			document.getElementById("lineout").readOnly = false;
			document.getElementById("vetrina").readOnly = false;
			document.getElementById("altropassaggio").readOnly = false;		
			
			/**//**/
			document.getElementById("readevery").value = "5";
			document.getElementById("minute_not_update").value = "180";
			document.getElementById("port").value = "5000";
			/**//**/
			
			/**/
	        break;
	 	case "1":
			/**/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = false;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			document.getElementById("scan").checked = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;	
			
			/**//**/
			document.getElementById("readevery").value = "60";
			document.getElementById("minute_not_update").value = "180";
			document.getElementById("port").value = "2101";
			/**//**/
			/**/
	        break;
	    case "2":
			/**/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = false;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			document.getElementById("scan").checked = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = true;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;
			
			/**//**/
			document.getElementById("readevery").value = "60";
			document.getElementById("minute_not_update").value = "180";
			document.getElementById("port").value = "8081";
			/**//**/
			
			/**/
	        break;
	    case "3":
			/**/
			document.getElementById("ip").readOnly = true;
			document.getElementById("port").readOnly = true;
			document.getElementById("readevery").readOnly = true;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = true;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;
			/**//**/
			document.getElementById("minute_not_update").value = "180";
			/**//**/
			/**/
	        break;
	    case "4":
	        /**/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = true;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = false;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;
			
			/**//**/
			document.getElementById("readevery").value = "60";
			document.getElementById("minute_not_update").value = "180";
			document.getElementById("port").value = "2101";
			/**//**/
			
	        /**/
	        break;
	    case "5":
	        /*ok*/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = false;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			//document.getElementById("downloaddata").disabled = false;
			//document.getElementById("ftpimport").disabled = true;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").disabled = false;
			
			
			document.getElementById("macaddress").readOnly = false;
			document.getElementById("linein").readOnly = false;
			document.getElementById("lineout").readOnly = false;
			document.getElementById("vetrina").readOnly = false;
			document.getElementById("altropassaggio").readOnly = false;	
			
			/**//**/
			document.getElementById("readevery").value = "5";
			document.getElementById("minute_not_update").value = "180";
			document.getElementById("port").value = "5000";
			/**//**/
			
	        /**/
	        break;
	    case "6":
	        /**/
			document.getElementById("ip").readOnly = true;
			document.getElementById("port").readOnly = true;
			document.getElementById("readevery").readOnly = true;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = true;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;	
			
			/**//**/
			document.getElementById("minute_not_update").value = "180";
			/**//**/
			
	        /**/
	        break;
	    case "7":
	        /**/
			document.getElementById("ip").readOnly = true;
			document.getElementById("port").readOnly = true;
			document.getElementById("readevery").readOnly = true;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = true;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;	
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;
	        /**/
	        
	        /**//**/
			document.getElementById("minute_not_update").value = "180";
			/**//**/
	        break;
	    case "8":
	        /**/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = true;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = true;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = false;
			//document.getElementById("downloaddata").disabled = false;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;	
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;    
	        /**/
	        break;

	    case "9":
			/**/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = false;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			document.getElementById("scan").checked = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = true;
			document.getElementById("devusername").readOnly = false;
			document.getElementById("devpassword").readOnly = false;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;
			
			/**//**/
			document.getElementById("readevery").value = "60";
			document.getElementById("minute_not_update").value = "180";
			document.getElementById("port").value = "8081";
			/**//**/
			
			/**/
	        break;	        
	        
	    case "99":
	        /*ok*/
			document.getElementById("ip").readOnly = true;
			document.getElementById("port").readOnly = true;
			document.getElementById("readevery").readOnly = true;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = true;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;
	        /**/
	        
	        /**//**/
			document.getElementById("minute_not_update").value = "180";
			/**//**/
	        break;	     
			
	    case "10":
			/**/
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = false;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = true;
			document.getElementById("scan").checked = true;
			//document.getElementById("downloaddata").disabled = true;
			//document.getElementById("ftpimport").disabled = true;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			
			document.getElementById("macaddress").readOnly = true;
			document.getElementById("linein").readOnly = true;
			document.getElementById("lineout").readOnly = true;
			document.getElementById("vetrina").readOnly = true;
			document.getElementById("altropassaggio").readOnly = true;
			
			/**//**/
			document.getElementById("readevery").value = "60";
			document.getElementById("minute_not_update").value = "180";
			document.getElementById("port").value = "8081";
			/**//**/
			
			/**/
	        break;
	    default:
	    	/*ok*/
	    	
			document.getElementById("ip").readOnly = false;
			document.getElementById("port").readOnly = false;
			document.getElementById("readevery").readOnly = false;
			//document.getElementById("fromdate").readOnly = false;
			document.getElementById("enabled").disabled = false;
			document.getElementById("timezone").disabled = false;
			document.getElementById("accountid").readOnly = true;
			document.getElementById("storeid").readOnly = true;
			document.getElementById("doorid").readOnly = false;
			document.getElementById("minute_not_update").readOnly = false;
			document.getElementById("updating").disabled = false;
			document.getElementById("nome").readOnly = false;
			document.getElementById("scan").disabled = false;
			//document.getElementById("downloaddata").disabled = false;
			//document.getElementById("ftpimport").disabled = false;
			document.getElementById("devusername").readOnly = true;
			document.getElementById("devpassword").readOnly = true;
			document.getElementById("devupdtime").value = false;
			document.getElementById("devupdtime").disabled = true;
			
			document.getElementById("macaddress").readOnly = false;
			document.getElementById("linein").readOnly = false;
			document.getElementById("lineout").readOnly = false;
			document.getElementById("vetrina").readOnly = false;
			document.getElementById("altropassaggio").readOnly = false;	
			 
			/**/
	}
	 
}
</script>

<script>
function Controlla(){
	var str = "";
	if ($("#device_type").val() == ""){
		alertify.error("Device Type Required");
		document.getElementById("device_type").focus();
		return false;
	}else{
		str = $("#device_type").val();
	}
	
	
	var tipoForm = '${tipoForm}';
	
	
	if(tipoForm!="E") {
	
		switch(str) {
		    case "0":
				/*ok*/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("updating").value == ""){
					alertify.error("Updating Required");
					document.getElementById("updating").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					alertify.error("Device Name Required");
					document.getElementById("nome").focus();
					return false;
				}
	/**/
				if (document.getElementById("macaddress").value == ""){
					alertify.error("Macaddress Required");
					document.getElementById("macaddress").focus();
					return false;
				}
				if (document.getElementById("linein").value == ""){
					alertify.error("LineIN Required");
					document.getElementById("linein").focus();
					return false;
				}
				if (document.getElementById("lineout").value == ""){
					alertify.error("LineOUT Required");
					document.getElementById("lineout").focus();
					return false;
				}
				/*
				if (document.getElementById("vetrina").value == ""){
					alertify.error("Shop Window Required");
					document.getElementById("vetrina").focus();
					return false;
				}
				if (document.getElementById("altropassaggio").value == ""){
					alertify.error("Other Passage Required");
					document.getElementById("altropassaggio").focus();
					return false;
				}
				*/
				/**/
		        break;
		 	case "1":
				/**/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
				if (document.getElementById("scan").value == ""){
					alertify.error("Scan Required");
					document.getElementById("scan").focus();
					return false;
				}			
				/**/
		        break;
		    case "2":
				/**/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
	/**/
				if (document.getElementById("scan").value == ""){
					alertify.error("Scan Required");
					document.getElementById("scan").focus();
					return false;
				}
	
				/**/
		        break;
		    case "3":
				/**/
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
				/**/
		        break;
		    case "4":
		        /**/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
				if (document.getElementById("scan").value == ""){
					alertify.error("Scan Required");
					document.getElementById("scan").focus();
					return false;
				}			
		        /**/
		        break;
		    case "5":
		        /*ok*/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("updating").value == ""){
					alertify.error("Updating Required");
					document.getElementById("updating").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					alertify.error("Device Name Required");
					document.getElementById("nome").focus();
					return false;
				}
	/**/
				if (document.getElementById("macaddress").value == ""){
					alertify.error("Macaddress Required");
					document.getElementById("macaddress").focus();
					return false;
				}
				if (document.getElementById("linein").value == ""){
					alertify.error("LineIN Required");
					document.getElementById("linein").focus();
					return false;
				}
				if (document.getElementById("lineout").value == ""){
					alertify.error("LineOUT Required");
					document.getElementById("lineout").focus();
					return false;
				}
				/*
				if (document.getElementById("vetrina").value == ""){
					alertify.error("Shop Window Required");
					document.getElementById("vetrina").focus();
					return false;
				}
				if (document.getElementById("altropassaggio").value == ""){
					alertify.error("Other Passage Required");
					document.getElementById("altropassaggio").focus();
					return false;
				}     
				*/
		        /**/
		        break;
		    case "6":
		        /**/
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}		        
		        /**/
		        break;
		    case "7":
		        /**/
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
		        /**/
		        break;
		    case "8":
		    	/**/
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
		        /**/
		        break;
		        
		    case "9":
				/**/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
	/**/
				if (document.getElementById("scan").value == ""){
					alertify.error("Scan Required");
					document.getElementById("scan").focus();
					return false;
				}
				
				if (document.getElementById("devusername").value == ""){
					alertify.error("Username Required");
					document.getElementById("devusername").focus();
					return false;
				}			
	
				if (document.getElementById("devpassword").value == ""){
					alertify.error("Password Required");
					document.getElementById("devpassword").focus();
					return false;
				}			
	
				/**/
		        break;	        
		        
		    case "99":
		        /*ok*/
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}       
		        /**/
		        break;	
		        
		    case "10":
				/**/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
	/**/
				if (document.getElementById("scan").value == ""){
					alertify.error("Scan Required");
					document.getElementById("scan").focus();
					return false;
				}
	
				/**/
		        break;
				
		    default:
		    	/*ok*/
				if (document.getElementById("ip").value == ""){
					alertify.error("Ip Required");
					document.getElementById("ip").focus();
					return false;
				}
				if (document.getElementById("port").value == ""){
					alertify.error("Port Required");
					document.getElementById("port").focus();
					return false;
				}
				if (document.getElementById("readevery").value == ""){
					alertify.error("Read Every Required");
					document.getElementById("readevery").focus();
					return false;
				}
				if (document.getElementById("fromdate").value == ""){
					alertify.error("From Date Required");
					document.getElementById("fromdate").focus();
					return false;
				}
				if (document.getElementById("enabled").value == ""){
					alertify.error("Enabled Required");
					document.getElementById("enabled").focus();
					return false;
				}
				if (document.getElementById("timezone").value == ""){
					alertify.error("TimeZone Required");
					document.getElementById("timezone").focus();
					return false;
				}
				/*
				if (document.getElementById("accountid").value == ""){
					alertify.error("Account Id Required");
					document.getElementById("accountid").focus();
					return false;
				}
				if (document.getElementById("storeid").value == ""){
					alertify.error("Store Id Required");
					document.getElementById("storeid").focus();
					return false;
				}
				*/
				if (document.getElementById("doorid").value == ""){
					alertify.error("Device Id Required");
					document.getElementById("doorid").focus();
					return false;
				}
				if (document.getElementById("minute_not_update").value == ""){
					alertify.error("Minute not Update Required");
					document.getElementById("minute_not_update").focus();
					return false;
				}
				if (document.getElementById("updating").value == ""){
					alertify.error("Updating Required");
					document.getElementById("updating").focus();
					return false;
				}
				if (document.getElementById("nome").value == ""){
					//alertify.error("Device Name Required");
					//document.getElementById("nome").focus();
					//return false;
				}
				if (document.getElementById("scan").value == ""){
					alertify.error("Scan Name Required");
					document.getElementById("scan").focus();
					return false;
				}			
	/**/
				if (document.getElementById("macaddress").value == ""){
					alertify.error("Macaddress Required");
					document.getElementById("macaddress").focus();
					return false;
				}
				if (document.getElementById("linein").value == ""){
					alertify.error("LineIN Required");
					document.getElementById("linein").focus();
					return false;
				}
				if (document.getElementById("lineout").value == ""){
					alertify.error("LineOUT Required");
					document.getElementById("lineout").focus();
					return false;
				}
				/*
				if (document.getElementById("vetrina").value == ""){
					alertify.error("Shop Window Required");
					document.getElementById("vetrina").focus();
					return false;
				}
				if (document.getElementById("altropassaggio").value == ""){
					alertify.error("Other Passage Required");
					document.getElementById("altropassaggio").focus();
					return false;
				}
				*/
				/**/
		}	
		 
	}	 
	 
	 
	 /**/
	 
	 

	var resp = "OK";
	if (document.getElementById("tipoForm").value == "I"){
	
	/**/
	    	$.ajax({
	    		url : "../../GetUnique",
	    		async: false,
	    		data : {
	    			tabs: 'D',
	    			accid : document.getElementById("accountid").value,
	    			stoid : document.getElementById("storeid").value,
	    			dooid : document.getElementById("doorid").value
	    		},
	    		success : function(responseText) {
	    			if (responseText == "KO"){
	    				resp = "KO";
	    				alertify.error("Accountid, Storeid, Doorid Already Present");
	    			}
	    		}
	    	});	 
	/**/
	
	}	 
	  
	if (resp == "KO"){
		return false;
	}	 	
	 
}



if (document.getElementById("tipoForm").value != "I" && document.getElementById("tipoForm").value != "E"){

	var logs = '${scan}';
	
	if (logs == true || logs == "t"){
		document.getElementById("scan").checked = true;
	}else{
		document.getElementById("scan").checked = false;
	}

	SelDeviceType($('#device_type').val());
	
	document.getElementById("readevery").value = "${readevery}";
	document.getElementById("minute_not_update").value = "${minute_not_update}";
	document.getElementById("port").value = "${port}";

	
}

function updated(flag ,dev,slav){
/**/
			alertify.set({ labels: {
			    ok     : "CONFIRM",
			    cancel : "CANCEL"
			} });
			
			alertify.confirm("Perform Action?", function (e) {
				
			    if (e) {

			    	$.ajax({
			    		url : "../../SetSlaves",
			    		data : {
			    			flag : flag,
			    			dev : dev,
			    			slav : slav
			    		},
			    		success : function(responseText) {
			    			//location.href="smartcontrol-stores.htm";
			    			if (responseText == "OK"){
			    				alertify.success("Operation performed successfully");
			    				location.reload();
			    			}else{
			    				alertify.error("Operation Error");
			    			}
			    			

			    		}
			    	});

			    } else {
			    	alertify.log("Action Cancelled");
			    }
			});
/**/

	
} 


/**/
options = {
		changeMonth: true,
		changeYear: true,
		yearRange: '2015:2049',
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
    $("#fromdate").datepicker(options);
} );


function SelChangeEnabled(obj){
	
	if(obj.value == "false"){
		document.getElementById("divreasons").style.visibility = "visible";
	}else{
		document.getElementById("divreasons").style.visibility = "hidden";	
		document.getElementById("reason").value="";
	}
}



var accountCode = "${AccountCode}";
if (accountCode != ""){
	$("#accountid").val(accountCode);
	$("#accountid").prop("readonly", true);
}

var storeCode = "${StoreCode}";
if (storeCode != ""){
	$("#storeid").val(storeCode);
	$("#storeid").prop("readonly", true);
}
</script>
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
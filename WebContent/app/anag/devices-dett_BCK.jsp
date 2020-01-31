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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("dev_dett") %></h2>
		                
		                <form data-validate="parsley" method="post" action="devices-dett.htm">
		                <input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("dev_dett") %> ${ds_devicetype}
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="devices-list.htm"><%=propLang.get("torna") %></a></li>
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
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>IP Address</label>
						          <input type="text" id="ip" name="ip" class="form-control" 
						          	value="${ip}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Port</label>
						          <input type="text" id="port" name="port" class="form-control" 
						          	value="${port}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Device Type</label>
						          <select id="device_type" name="device_type" data-required="true" class="form-control" ${readonly}>
						          	<option value="">-</option>
						          	<c:forEach items="${typ}" var="typ" varStatus="status">
						          	<option value="${typ.xcdel}">${typ.xcdel} - ${typ.xdtab}</option>
						         	</c:forEach>
						          </select>  
						        </div>
						    </div>
						  	
						  	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-2">
						          <label>Read Every</label>
						          <input type="text" id="readevery" name="readevery" class="form-control" 
						          	data-required="true" value="${readevery}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-2">
						          <label>From Date</label>
						          <input class="datepicker-input2 form-control" name="fromdate" id="fromdate" 
						          	type="text" value="${fromdate}" data-date-format="dd-mm-yyyy" > 
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Enabled</label>
						          <select id="enabled" name="enabled" class="form-control" ${readonly}>
						          	<option value="true">TRUE</option>
						          	<option value="false">FALSE</option>
						          </select>   
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
						          <label>Account ID</label>
						          <input type="text" id="accountid" name="accountid" class="form-control" 
						          	data-required="true" value="${accountid}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Store ID</label>
						          <input type="text" id="storeid" name="storeid" class="form-control" 
						          	data-required="true" value="${storeid}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Door ID</label>
						          <input type="text" id="doorid" name="doorid" class="form-control" 
						          	data-required="true" value="${doorid}" ${readonly}>   
						        </div>
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Minute Not Update(in minutes)</label>
						          <input type="text" id="minute_not_update" name="minute_not_update" class="form-control" 
						          	data-required="true" value="${minute_not_update}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Updating</label>
						          <select id="updating" name="updating" class="form-control" ${readonly}>
						          	<option value="true">TRUE</option>
						          	<option value="false">FALSE</option>
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
						          <textarea id="notes" name="notes" class="form-control" style="height:100px"
						          	${readonly}>${notes}</textarea>  
						        </div>
						    </div>
						  	
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
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
	                          	<div class="pull-left m-l-xs m-r-sm">
	                          	 	${sla.cmp1}
	                          	</div>
	                          	<div class="pull-left m-l-xs m-r-sm">
	                          	 	<span class="label bg-light">${sla.slavename}</span>
	                          	</div>
	                          	<div class="clear text-ellipsis">
		                        	${sla.id_slave} ${sla.door_description}
		                        </div> 
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
                          <i class="icon-user icon"></i> 
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
	
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
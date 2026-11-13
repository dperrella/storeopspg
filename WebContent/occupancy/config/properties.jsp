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
  <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
  <script src="../../js/jquery.min.js"></script>
  
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
        <!-- /.aside -->
        
        <section id="content">
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp16.jsp" %>
              
              <section>
                  <section class="vbox">
              
               		<section class="scrollable padder-lg " id="bjax-target">
               			<a href="#" class="pull-right text-muted m-t-lg">IP: ${ip}<br/>MAC: ${mac}</a>
		              	<h2 class="font-thin m-b">Settings</h2>
		              
		              	
		              	
		              	<form id="action-form" class="bs-example form-horizontal" method="post" onsubmit="showLoader2();return saveSystemConfig()">
		              	<input type="hidden" name="azione" value="S"/>
		              	<input type="hidden" name="azionesettings" value="S"/>
		              	  
		              	<section class="panel panel-default">
		                    <header class="panel-heading bg-light">
		                      <ul class="nav nav-tabs nav-justified">
		                        <li class="active"><a href="#__network" data-toggle="tab"><b>Network and Account</b></a></li>
		                        <li><a href="#__occupancy" data-toggle="tab"><b>Occupancy Config</b></a></li>
		                        <li><a href="#__hours" data-toggle="tab"><b>Opening Hours </b></a></li>
		                        <li><a href="#__labels" data-toggle="tab"><b>Labels and Colors</b></a></li>
		                        <li><a href="#__mail" data-toggle="tab"><b>Mail Settings</b></a></li>
		                        <li><a href="#__sounds" data-toggle="tab"><b>Sounds Settings</b></a></li>
		                        <li><a href="#__system" data-toggle="tab"><b>System</b></a></li>
		                      </ul>
		                    </header>
		                    <div class="panel-body">
		                      <div class="tab-content">
		                        <div class="tab-pane active" id="__network">
		                        	
		                        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Network and Account</span>
			                          </div>
				                    </div>
							        <br/>
							        <c:forEach items="${prop1}" var="prop1" varStatus="status">
	                        	
		                        	<div class="form-group">
			                          <label class="col-lg-2 control-label">${prop1.description}</label>
			                          <div class="col-lg-10">
			                          	<input type="hidden" name="key" value="${prop1.key}" disabled/>
									  	<input type="hidden" name="description" value="${prop1.description}" disabled/>
									  	<input type="hidden" name="tile" value="${prop1.tile}" disabled/>
									  	<input type="text" name="val" id="${prop1.key}" 
										  	class="form-control" value="NOT Available in Cloud" disabled/>
									  </div>
			                        </div>
			                        
									<div class="line line-dashed b-b line-lg pull-in"></div>
							    		
		                        	</c:forEach>
					              
		                        </div>
		                        
		                        <div class="tab-pane" id="__occupancy">
		                        	
		                        	
		                        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Occupancy Config</span>
			                          </div>
				                    </div>
							        <br/>
							        
		                        	
	                        	
	                        	
	                        	<c:forEach items="${prop2}" var="prop2" varStatus="status">
	                        		
	                        		<c:choose>
									    <c:when test="${prop2.key == 'RESET'}">
									        
									        <div class="form-group">
					                          <label class="col-lg-2 control-label">${prop2.description}</label>
					                          <div class="col-lg-8">
					                          	<input type="hidden" name="key" value="${prop2.key}"/>
												  	<input type="hidden" name="description" value="${prop2.description}"/>
												  	<input type="hidden" name="tile" value="${prop2.tile}"/>
												  	<input type="text" name="val" id="${prop2.key}" 
												  	class="form-control" value="${prop2.value}"/>
												  	<span class="text-muted">* Need Server Reboot to take change</span>
							          
											  </div>
											  <div class="col-lg-2">
										          <div class="checkbox i-checks">
									                  <label>
									                    <input name="reset1" type="checkbox" value="true" ${chkReset1}>
									                    <i></i>
									                  </label>
									                  Reboot
									              </div>  
										       </div>
					                        </div>
					                        
									    </c:when>  
									    <c:when test="${prop2.key == 'INTERVAL'}">
									    	<div class="form-group">
					                          <label class="col-lg-2 control-label">${prop2.description}</label>
					                          <div class="col-lg-10">
					                          	<input type="hidden" name="key" value="${prop2.key}"/>
												  	<input type="hidden" name="description" value="${prop2.description}"/>
												  	<input type="hidden" name="tile" value="${prop2.tile}"/>
												  	<input type="text" name="val" id="${prop2.key}" 
												  	class="form-control" value="${prop2.value}"/>
												<span class="text-muted">* Need Server Reboot to take change</span>
							          		  </div>
					                        </div>
									    </c:when>  
									    <c:when test="${prop2.key == 'CLEAR'}">
									    	<div class="form-group">
					                          <label class="col-lg-2 control-label">${prop2.description}</label>
					                          <div class="col-lg-10">
					                          	<input type="hidden" name="key" value="${prop2.key}"/>
												  	<input type="hidden" name="description" value="${prop2.description}"/>
												  	<input type="hidden" name="tile" value="${prop2.tile}"/>
												  	<input type="text" name="val" id="${prop2.key}" 
												  	class="form-control" value="${prop2.value}"/>
												<span class="text-muted">* Need Server Reboot to take change - Clear will be executed 20 minutes before Store Opening Hour</span>
							          		  </div>
					                        </div>
									    </c:when>  
									      
									    <c:otherwise>
									        
									        <div class="form-group">
					                          <label class="col-lg-2 control-label">${prop2.description}</label>
					                          <div class="col-lg-10">
					                          	<input type="hidden" name="key" value="${prop2.key}"/>
												  	<input type="hidden" name="description" value="${prop2.description}"/>
												  	<input type="hidden" name="tile" value="${prop2.tile}"/>
												  	<input type="text" name="val" id="${prop2.key}" 
												  	class="form-control" value="${prop2.value}"/>
											  </div>
					                        </div>
					                        
									    </c:otherwise>
									</c:choose>
		                        
									<div class="line line-dashed b-b line-lg pull-in"></div>
									
		                        	</c:forEach>
		                        	
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">Traffic Light Count Type</label>
								        <div class="col-lg-10">
								          <select name="traffic_light_count_type" id="traffic_light_count_type" class="ss form-control">
								          	<option value="1">Show Available Places</option>
								          	<option value="2">Show Available Places Percentage</option>
								          	<option value="3">Show Occupancy Percentage</option>
								          	<option value="4">Show Stop/Go Label</option>
								          	<option value="5">No Occupancy Info</option>
								          </select>
								          <script>
								          	$('#traffic_light_count_type').val('${trafficLightCountType}');
								          </script>
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
						        	
		                        	<div class="form-group">
								    	<label class="col-lg-2 control-label">Error Label</label>
								        <div class="col-lg-10">
								          <div class="checkbox i-checks">
							                  <label>
							                    <input name="msgwrn" type="checkbox" value="true" ${chkMsgwrn}>
							                    <i></i>
							                  </label>
							                  Show Error Label on Threshold reach
							              </div>  
								        </div>
								        
								    </div>
								    
								    
								    <input type="hidden" name="machineLearning" value="false"/>
								    <input type="hidden" name="sm" value="false"/>
								    
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <input type="hidden" name="hhopen" value="${hh1}"/>
								    <input type="hidden" name="mmopen" value="${mm1}"/>
								    <input type="hidden" name="hhclose" value="${hh2}"/>
								    <input type="hidden" name="mmclose" value="${mm2}"/>
								    
									<div class="form-group">
								    	<label class="col-lg-2 control-label">Template Type</label>
								        <div class="col-lg-10">
								          <select name="template" id="template" class="ss form-control">
								          	<option value="1">Single with carousel</option>
								          	<option value="2">Grid</option>
								          </select>
								          <script>
								          	$('#template').val('${template}');
								          </script>
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">Date Format</label>
			                          <div class="col-lg-10">
								          <select class="ss form-control" name="df" id="df">
								          	<option value="1">Europe Format (dd/MM/yyyy)</option>
								          	<option value="2">ISO Format (yyyy-MM-dd)</option>
								          	<option value="3">US Format (MM/dd/yyyy)</option>
								          </select>
								          <script>
								          	$('#df').val('${df}');
								          </script>
								        </div>
								    </div>
						        
						        	<div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">Time Zone</label>
			                          <div class="col-lg-10">
								          <select class="ss form-control" name="tz" id="tz">
											<option value="">-</option>
											<c:forEach items="${tmz}" var="tmz" varStatus="status">
								          	<option value="${tmz.xcdel}">${tmz.cmp1} - ${tmz.xdtab}</option>
								         	</c:forEach>
										</select>
										<span class="text-muted">* Need Server Reboot to take change</span>
								          
								          <script>
								          	$('#tz').val('${tz}');
								          </script>
								        </div>
								    </div>
						        
						        	<div class="line line-dashed b-b line-lg pull-in"></div>
						        	
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">NTP Type</label>
								        <div class="col-lg-10">
								          <select name="ntp" id="ntp" class="ss form-control">
								          	<option value="1">Standard NTP</option>
								          	<option value="2">Hella NTP</option>
								          </select>
								          <span class="text-muted">* Need Server Reboot to take change</span>
								          
								          <script>
								          	$('#ntp').val('${ntp}');
								          </script>
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">Privacy</label>
								        <div class="col-lg-10">
								          <select name="privacy" id="privacy" class="ss form-control">
								          	<option value="1">Normal</option>
								          	<option value="2">Enanched Privacy Mode</option>
								          </select>
								          <span class="text-muted">* Need Camera Reboot to take change</span>
								          
								          <script>
								          	$('#privacy').val('${privacy}');
								          </script>
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">Average Queue</label>
								        <div class="col-lg-10">
								        	  <div class="checkbox i-checks">
								                  <label>
								                    <input name="avgqueue" type="checkbox" value="true" ${chkAvgqueue}>
								                    <i></i>
								                  </label>
								                  Show Average Queue Information when Threshold is reached 
								              </div>  
									     </div>
								    </div>
								    
								    <input type="hidden" name="dataType" id="dataType" value="1"/>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
		                        	
		                        	
		                        </div>
		                        
		                        
		                        <div class="tab-pane" id="__hours">
	                        	
		                        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Opening Hours</span>
			                          </div>
				                    </div>
							        <br/>
							        
							        
			                        
							        
							        <!-- 
							        <div class="form-group">
			                          <label class="col-lg-2 control-label">IP Address</label>
			                          <div class="col-lg-10">
			                            <input type="text" name="__ip" class="form-control" placeholder="IP Address" value="${ip}" readonly>
			                          </div>
			                        </div>
							        
			        				<div class="line line-dashed b-b line-lg pull-in"></div>
			        				
			        				<div class="form-group">
			                          <label class="col-lg-2 control-label">MAC Address</label>
			                          <div class="col-lg-10">
			                            <input type="text" name="__mac" class="form-control" placeholder="MAC Address" value="${mac}" readonly>
			                          </div>
			                        </div>
			                        
		                        	<div class="line line-dashed b-b line-lg pull-in"></div>
		                        	-->
		                        	
		                        	<div class="form-group">
								    	<label class="col-lg-2 control-label">Opening Hour Reboot</label>
								        <div class="col-lg-10">
								          <div class="checkbox i-checks">
							                  <label>
							                    <input name="reset2" type="checkbox" value="true" ${chkReset2}>
							                    <i></i>
							                  </label>
							                  Reboot 10 Minute Before Opening Hour
							              </div>  
								        </div>
								    </div>
								    <div class="form-group">
									     <div class="col-lg-2">&nbsp;</div>
									     <div class="col-lg-10">
									     	<span class="text-muted">* Reboot 10 Minutes before Option need Server Reboot to take change</span>
								         </div>
								    </div>
								    
								    <!-- 
								    <div class="line line-dashed b-b line-lg pull-in"></div>
		                        	-->
		                        	
		                        	<table class="table">
		                        		<tr>
		                        			<th>#</th>
		                        			<th style="width:35%">Day</th>
		                        			<th style="width:2%"></th>
		                        			<th style="width:2%"></th>
		                        			<th style="width:30%" colspan="2">Opening Hour</th>
		                        			<th style="width:2%"></th>
		                        			<th style="width:30%" colspan="2">Closing Hour</th>
		                        		</tr>
		                        		
		                        		<tbody>
	                        		
		                        		<c:forEach items="${openingHours}" var="openingHours" varStatus="status">
		                        			<tr>
		                        				<td>${openingHours.day}.</td>
		                        				<td>${openingHours.daystring}</td>
		                        				<td><label>H24</label>
		                        					<div class="checkbox i-checks">
									                  <label>
									                    <input name="h24_${openingHours.day}" id="h24_${openingHours.day}" type="checkbox" value="true" onclick="boxDisable_${openingHours.day}($(this));">
									                    <i></i>
									                  </label>
									                </div></td>
		                        				<td><i class="fa fa-circle text-success" style="margin-top:34px"></i></td>
		                        				<td><label>Hours</label>
		                        				  <select name="fromhour_${openingHours.day}" id="fromhour_${openingHours.day}" class="ss form-control">
										          	<%
										          	for(int i=0;i<24;i++) {
										          	%>
										          	<option value="<%=String.format("%02d", i) %>"><%=String.format("%02d", i)  %></option>
										          	<%
										          	}
										          	%>
										          </select>
										          <script>
										          	$('#fromhour_${openingHours.day}').val('${openingHours.fromHour}');
										          </script>
		                        				</td>
		                        				<td><label>Minutes</label>
		                        				  <select name="fromminute_${openingHours.day}" id="fromminute_${openingHours.day}" class="ss form-control">
										          	<%
										          	for(int i=0;i<60;i++) {
										          	%>
										          	<option value="<%=String.format("%02d", i) %>"><%=String.format("%02d", i)  %></option>
										          	<%
										          	}
										          	%>
										          </select>
										          <script>
										          	$('#fromminute_${openingHours.day}').val('${openingHours.fromMinute}');
										          </script>
		                        				</td>
		                        				
		                        				<td><i class="fa fa-circle text-danger" style="margin-top:34px"></i></td>
		                        				<td><label>Hours</label>
		                        				  <select name="tohour_${openingHours.day}" id="tohour_${openingHours.day}" class="ss form-control">
										          	<%
										          	for(int i=0;i<24;i++) {
										          	%>
										          	<option value="<%=String.format("%02d", i) %>"><%=String.format("%02d", i)  %></option>
										          	<%
										          	}
										          	%>
										          </select>
										          <script>
										          	$('#tohour_${openingHours.day}').val('${openingHours.toHour}');
										          </script>
		                        				</td>
		                        				<td><label>Minutes</label>
		                        				  <select name="tominute_${openingHours.day}" id="tominute_${openingHours.day}" class="ss form-control">
										          	<%
										          	for(int i=0;i<60;i++) {
										          	%>
										          	<option value="<%=String.format("%02d", i) %>"><%=String.format("%02d", i)  %></option>
										          	<%
										          	}
										          	%>
										          </select>
										          <script>
										          	$('#tominute_${openingHours.day}').val('${openingHours.toMinute}');
										          	
										          	
										          	
										          	
										          	var h24 = ${openingHours.h24};
										          	if(h24==true) {
										          		$('#h24_${openingHours.day}').prop('checked', true);
										          		$('#fromhour_${openingHours.day}').val('00');
										          		$('#fromminute_${openingHours.day}').val('00');
										          		$('#tohour_${openingHours.day}').val('23');
										          		$('#tominute_${openingHours.day}').val('59');
										          		
										          		$('#fromhour_${openingHours.day}').attr('disabled', true);
										          		$('#fromminute_${openingHours.day}').attr('disabled', true);
										          		$('#tohour_${openingHours.day}').attr('disabled', true);
										          		$('#tominute_${openingHours.day}').attr('disabled', true);
										          	}
										          	else {
										          		$('#h24_${openingHours.day}').prop('checked', false);
										          		
										          		$('#fromhour_${openingHours.day}').attr('disabled', false);
										          		$('#fromminute_${openingHours.day}').attr('disabled', false);
										          		$('#tohour_${openingHours.day}').attr('disabled', false);
										          		$('#tominute_${openingHours.day}').attr('disabled', false);
										          	}
										          	
										          	function boxDisable_${openingHours.day}(t) {
										          		
										          		//alert(t.is(':checked'));
										          	    if (t.is(':checked')) {
										          	    	$('#fromhour_${openingHours.day}').val('00');
											          		$('#fromminute_${openingHours.day}').val('00');
											          		$('#tohour_${openingHours.day}').val('23');
											          		$('#tominute_${openingHours.day}').val('59');
											          		
											          		$('#fromhour_${openingHours.day}').attr('disabled', true);
											          		$('#fromminute_${openingHours.day}').attr('disabled', true);
											          		$('#tohour_${openingHours.day}').attr('disabled', true);
											          		$('#tominute_${openingHours.day}').attr('disabled', true);
										          	    } else {
										          	    	$('#fromhour_${openingHours.day}').attr('disabled', false);
											          		$('#fromminute_${openingHours.day}').attr('disabled', false);
											          		$('#tohour_${openingHours.day}').attr('disabled', false);
											          		$('#tominute_${openingHours.day}').attr('disabled', false);
										          	    }
										          	    
										          	  	$('.ss').selectpicker('refresh');
										          	}
										          </script>
		                        				</td>
		                        			</tr>
		                        		</c:forEach>
		                        		
		                        		</tbody>
		                        		
		                        	</table>
		                        	
		                        	<div class="line line-dashed b-b line-lg pull-in"></div>
		                        	
		                        </div>
		                        
		                        <div class="tab-pane" id="__labels">
	                        	
		                        	<!-- 
		                        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="pull-left h4 block">Pi Network</span>
			                            <span>&nbsp;</span>
			                            <span class="pull-right"><a href="../popup/licence.jsp" class="alert-link" data-toggle="ajaxModal">Show Licence Key</a></span>
			                          </div>
				                    </div>
							        <br/>
							        
							        
							        
							        <div class="form-group">
			                          <label class="col-lg-2 control-label">IP Address</label>
			                          <div class="col-lg-10">
			                            <input type="text" name="__ip" class="form-control" placeholder="IP Address" value="${ip}" readonly>
			                          </div>
			                        </div>
							        
			        				<div class="line line-dashed b-b line-lg pull-in"></div>
			        				
			        				<div class="form-group">
			                          <label class="col-lg-2 control-label">MAC Address</label>
			                          <div class="col-lg-10">
			                            <input type="text" name="__mac" class="form-control" placeholder="MAC Address" value="${mac}" readonly>
			                          </div>
			                        </div>
							        
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									-->
									
		                        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Labels</span>
			                          </div>
				                    </div>
							        <br/>
							        
							        <input type="hidden" name="res" id="res" value="${res}"/>
								    
								    
							        
							        
		                        	<c:forEach items="${prop3}" var="prop3" varStatus="status">
		                        	
		                        	<div class="form-group">
			                          <label class="col-lg-2 control-label">${prop3.description}</label>
			                          <div class="col-lg-10">
			                          	<input type="hidden" name="key" value="${prop3.key}"/>
										  	<input type="hidden" name="description" value="${prop3.description}"/>
										  	<input type="hidden" name="tile" value="${prop3.tile}"/>
										  	<input type="text" name="val" id="${prop3.key}" 
										  	class="form-control" value="${prop3.value}"/>
									  </div>
			                        </div>
			                        
									<div class="line line-dashed b-b line-lg pull-in"></div>
									
									</c:forEach>
									
									<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Additional Labels
			                            <a href="../popup/labels-new.jsp" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" data-toggle="ajaxModal">
				                            <i class="icon-plus"></i>
				                            Add New Label Group
				                        </a>
				                        </span>
			                          </div>
				                    </div>
							        <br/>
							        
							        <table class="table">
							        	<thead>
							        	<tr>
							        		<th style="width:90%">Label Group Name</th>
							        		<th></th>
							        		<th></th>
							        	</tr>
							        	</thead>
							        	<tbody>
							        	<c:forEach items="${labels}" var="labels" varStatus="status">
							        	<tr>
							        		<td>${labels.lang}</td>
							        		<td><a href="../popup/labels-dett.htm?tipoForm=M&lang=${labels.lang}" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-edit"></i></a></td>
									  		<td><a href="../popup/labels-dett.htm?tipoForm=E&lang=${labels.lang}" class="btn btn-danger" data-toggle="ajaxModal"><i class="fa fa-times"></i></a></td>								
							        	</tr>
							        	</c:forEach>
							        	</tbody>
							        </table>
							        
							        
							        <div class="line line-dashed b-b line-lg pull-in"></div>
									
									
									<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Normal Status Colors
			                            <a href="#" onclick="setDefault('N')" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" >
				                            <i class="icon-reload"></i>
				                            Set Default Normal Color
				                        </a>
				                        </span>
			                          </div>
				                    </div>
							        <br/>
							        
		                        	
		                        	<c:forEach items="${prop5}" var="prop5" varStatus="status">
		                        	
		                        	<div class="form-group">
			                          <label class="col-lg-2 control-label">${prop5.description}</label>
			                          <div class="col-lg-10">
			                          	<input type="hidden" name="key" value="${prop5.key}"/>
										  	<input type="hidden" name="description" value="${prop5.description}"/>
										  	<input type="hidden" name="tile" value="${prop5.tile}"/>
										  	<input type="text" id="${prop5.key}" name="val" id="${prop5.key}" 
										  	class="form-control" value="${prop5.value}"/>
									  </div>
			                        </div>
			                        
									<div class="line line-dashed b-b line-lg pull-in"></div>
									
									</c:forEach>
									
									<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Warning Status Colors
			                            <a href="#" onclick="setDefault('W')" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" >
				                            <i class="icon-reload"></i>
				                            Set Default Warning Color
				                        </a>
			                            </span>
			                          </div>
				                    </div>
							        <br/>
							        
		                        	
		                        	<c:forEach items="${prop6}" var="prop6" varStatus="status">
		                        	
		                        	<div class="form-group">
			                          <label class="col-lg-2 control-label">${prop6.description}</label>
			                          <div class="col-lg-10">
			                          	<input type="hidden" name="key" value="${prop6.key}"/>
										  	<input type="hidden" name="description" value="${prop6.description}"/>
										  	<input type="hidden" name="tile" value="${prop6.tile}"/>
										  	<input type="text" id="${prop6.key}" name="val" id="${prop6.key}" 
										  	class="form-control" value="${prop6.value}"/>
									  </div>
			                        </div>
			                        
									<div class="line line-dashed b-b line-lg pull-in"></div>
									
									</c:forEach>
									
									<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">OverThreshold Status Colors
			                            <a href="#" onclick="setDefault('O')" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" >
				                            <i class="icon-reload"></i>
				                            Set Default OverThreshold Color
				                        </a>
			                            </span>
			                          </div>
				                    </div>
							        <br/>
							        
		                        	
		                        	<c:forEach items="${prop7}" var="prop7" varStatus="status">
		                        	
		                        	<div class="form-group">
			                          <label class="col-lg-2 control-label">${prop7.description}</label>
			                          <div class="col-lg-10">
			                          	<input type="hidden" name="key" value="${prop7.key}"/>
										  	<input type="hidden" name="description" value="${prop7.description}"/>
										  	<input type="hidden" name="tile" value="${prop7.tile}"/>
										  	<input type="text" id="${prop7.key}" name="val" id="${prop7.key}" 
										  	class="form-control" value="${prop7.value}"/>
									  </div>
			                        </div>
			                        
									<div class="line line-dashed b-b line-lg pull-in"></div>
									
									</c:forEach>
									
									
									<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Customer Logo
			                            <a href="#" onclick="setDefaultLogo()" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" disabled >
				                            <i class="icon-reload"></i>
				                            Remove Customer Logo
				                        </a>
				                        </span>
			                          </div>
				                    </div>
							        <br/>
							        
		                        	<div class="form-group">
								    	<label class="col-lg-2 control-label">Select Customer Logo</label>
								        <div class="col-sm-8">
								          <input type="file" id="fileL" class="form-control" name="file" disabled />
								        </div>
								        <div class="col-sm-2">
								        	<a href="#" onclick="uploadLogo()" class="btn btn-default btn-block" disabled>Upload New File</a>
								        </div>
								        
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									
		                        </div>
		                        
		                        
		                        
		                        
		                        <div class="tab-pane" id="__mail">
		                        	
		                        	
		                        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Mail Settings</span>
			                          </div>
				                    </div>
							        <br/>
							        
							        <div class="form-group">
			                          <label class="col-lg-2 control-label">Mail Send Type</label>
			                          <div class="col-lg-10">
			                          	<select name="mailsend" id="mailsend" class="form-control" onchange="showHide(this.value)">
								          	<option value="D">Default</option>
								          	<option value="C">Custom Config</option>
								          </select>
								          <script>
								          	$('#mailsend').val('${mailsend}');
								          </script>
									  </div>
			                        </div>
			                        
							        <div id="mailsettings">
			    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
				                          <label class="col-lg-2 control-label">Smtp</label>
				                          <div class="col-lg-10">
									          <input type="text" id="mailsmtp" name="mailsmtp" 
									          	class="form-control" value="${mailsmtp}">   
									        </div>
									    </div>
									    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
				                          <label class="col-lg-2 control-label">Smtp Port</label>
				                          <div class="col-lg-10">
									          <input type="text" id="mailport" name="mailport" 
									          	class="form-control" value="${mailport}">   
									        </div>
									    </div>
									    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
				                          <label class="col-lg-2 control-label">Smtp Username</label>
				                          <div class="col-lg-10">
									          <input type="text" id="mailuser" name="mailuser" 
									          	class="form-control" value="${mailuser}">   
									        </div>
									    </div>
									    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
				                          <label class="col-lg-2 control-label">Smtp Password</label>
				                          <div class="col-lg-10">
									          <input type="password" id="mailpass" name="mailpass" 
									          	class="form-control" value="${mailpass}">   
									        </div>
									    </div>
									    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
				                          <label class="col-lg-2 control-label">From</label>
				                          <div class="col-lg-10">
									          <input type="text" id="mailfrom" name="mailfrom" 
									          	class="form-control" value="${mailfrom}">   
									        </div>
									    </div>
									    
									    
									    
									    </div>
									    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
				                          <label class="col-lg-2 control-label">To</label>
				                          <div class="col-lg-10">
									          <input type="text" id="mailto" name="mailto" 
									          	class="form-control" value="${mailto}">   
									        </div>
									    </div>
									    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
									    	<label class="col-lg-2 control-label">Mail Type</label>
									        <div class="col-sm-5">
									          <div class="checkbox i-checks">
								                  <label>
								                    <input name="chkMailwrn" type="checkbox" value="true" ${chkMailwrn}>
								                    <i></i>
								                  </label>
								                  Send Mail at Warning
								              </div>  
									        </div>
									        <div class="col-sm-5">
									          <div class="checkbox i-checks">
								                  <label>
								                    <input name="chkMailover" type="checkbox" value="true" ${chkMailover}>
								                    <i></i>
								                    Send Mail at Over Threshold
								                  </label>
								              </div>
									        </div>
									    </div>
									    
									    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
									    <div class="form-group">
				                          <label class="col-lg-2 control-label">Send Type</label>
				                          <div class="col-lg-10">
									          <select class="form-control" name="mailtype" id="mailtype">
									          	<option value="E">Send at Event</option>
									          	<option value="H">Max 1 Mail for Hour</option>
									          </select>
									          <script>
									          	$('#mailtype').val('${mailtype}');
									          </script>
									        </div>
									    </div>
							        
							        	<div class="line line-dashed b-b line-lg pull-in"></div>
							        	
							        	
							        
							        
		                        </div>
		                        
		                        <div class="tab-pane" id="__sounds">
		                        
		                        	
		                        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Normal Status Sounds
			                            <a href="#" onclick="setDefaultSounds('N')" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" disabled>
				                            <i class="icon-reload"></i>
				                            Set Default Normal Sounds
				                        </a>
				                        </span>
			                          </div>
				                    </div>
							        <br/>
		                        
		                        	<div class="form-group">
								    	<label class="col-lg-2 control-label">Enable Normal Sounds</label>
								        <div class="col-sm-10">
								          <div class="checkbox i-checks">
							                  <label>
							                    <input name="enableNormal" type="checkbox" value="true" ${chkEnableNormal}>
							                    <i></i>
							                  </label>
							                  Enable Sounds on Areas Entrance / Exit
							              </div>  
								        </div>
								        
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">Select Normal Sound</label>
								        <div class="col-sm-8">
								          <input type="file" id="fileN" class="form-control" name="file" disabled/>
								          <a href="#" onclick="audioElementN.play();">Play Sound</a>
								        </div>
								        <div class="col-sm-2">
								        	<a href="#" onclick="upload('N')" class="btn btn-default btn-block" disabled>Upload New File</a>
								        </div>
								        
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Warning Status Sounds
			                            <a href="#" onclick="setDefaultSounds('W')" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" disabled>
				                            <i class="icon-reload"></i>
				                            Set Default Warning Sounds
				                        </a>
				                        </span>
			                          </div>
				                    </div>
							        <br/>
							        
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">Enable Warning Sounds</label>
								        <div class="col-sm-10">
								          <div class="checkbox i-checks">
							                  <label>
							                    <input name="enableWarning" type="checkbox" value="true" ${chkEnableWarning}>
							                    <i></i>
							                  </label>
							                  Enable Warning Sounds on Areas Entrance / Exit
							              </div>  
								        </div>
								        
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">Select Warning Sound</label>
								        <div class="col-sm-8">
								          <input type="file" id="fileW" class="form-control" name="file" disabled/>
								          <a href="#" onclick="audioElementW.play();">Play Sound</a>
								        </div>
								        <div class="col-sm-2">
								        	<a href="#" onclick="upload('W')" class="btn btn-default btn-block" disabled>Upload New File</a>
								        </div>
								        
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
								    
								    
								    <div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">ObeerThreshold Status Colors
			                            <a href="#" onclick="setDefaultSounds('E')" style="width:200px" class="btn btn-default btn-xs m-l-sm pull-right" disabled >
				                            <i class="icon-reload"></i>
				                            Set Default OverThreshold Sounds
				                        </a>
				                        </span>
			                          </div>
				                    </div>
							        <br/>
							        
								    <div class="form-group">
								    	<label class="col-lg-2 control-label">Enable OverThreshold Sounds</label>
								        <div class="col-sm-10">
								          <div class="checkbox i-checks">
							                  <label>
							                    <input name="enableError" type="checkbox" value="true" ${chkEnableError}>
							                    <i></i>
							                  </label>
							                  Enable OverThreshold Sounds on Areas Entrance / Exit
							              </div>  
								        </div>
								        
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
		                        
		                        	<div class="form-group">
								    	<label class="col-lg-2 control-label">Select Normal Sound</label>
								        <div class="col-sm-8">
								          <input type="file" id="fileE" class="form-control" name="file" disabled/>
								          <a href="#" onclick="audioElementE.play();">Play Sound</a>
								        </div>
								        <div class="col-sm-2">
								        	<a href="#" onclick="upload('E')" class="btn btn-default btn-block" disabled>Upload New File</a>
								        </div>
								        
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
		                        	
		                        </div>
		                        
		                        <div class="tab-pane" id="__system">
		                        	
							    	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Network Settings</span>
			                          </div>
				                    </div>
							        <br/>
							            
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">Ip Address</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__ip" name="__ip" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">Subnet Mask</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__subnet" name="__subnet" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">Gateway</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__gateway" name="__gateway" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">Breadcast</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__broadcast" name="__broadcast" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">Network</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__nw" name="__nw" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									    
								    <div class="form-group">
			                          <label class="col-lg-2 control-label">DNS</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__dns" name="__dns" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
									
								    <div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">Date, Time and NTP Settings</span>
			                          </div>
				                    </div>
							        <br/>
							    
							    	
			                        <div class="form-group">
			                          <label class="col-lg-2 control-label">Date</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__date" name="__date" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								          <span class="text-muted">* In ISO format (yyyy-MM-dd)</span>
								        </div>
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
								    
			                        <div class="form-group">
			                          <label class="col-lg-2 control-label">Time</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__time" name="__time" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								          <span class="text-muted">* 24 Hour format (HH:mm)</span>
								        </div>
								    </div>
							        
							        <div class="line line-dashed b-b line-lg pull-in"></div>
								    
			                        <div class="form-group">
			                          <label class="col-lg-2 control-label">NTP</label>
			                          <div class="col-lg-10">
								          <input type="text" id="__ntp" name="__ntp" 
								          	class="form-control" value="Non Available on Cloud" disabled>   
								        </div>
								    </div>
								    
								    
							        
			                      </div>
		                        
		                      </div>
		                    </div>
		                    <footer class="panel-footer text-right bg-light lter">
		                    
		                    	<a href="#" onclick="DownloadSettings()" class="btn btn-default m-r">Download Settings</a>
						      	<button type="submit" id="submit" class="btn btn-primary btn-lg">Save Settings</button>
						    </footer>  
		                </section>
		                </form>
		                  
		                  
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
  
  <form id="chgUser" action="../main/index.htm" method="post">
  	<input type="hidden" name="idcus" id="idcus_frm"/>
  </form>
  
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  
  <!--  modal -->
  <script src="../../js/bootstrap-modal.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js?v=2"></script>
  
  
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
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
	$('.ss').selectpicker({
	    liveSearch: true,
	    tickIcon: 'fa fa-check'
	});
	
	
	$('#mailsettings').hide()
	showHide('${mailsend}');
	
	function showHide(val) {
		
		if(val=='D')
			$('#mailsettings').hide();
		else
			$('#mailsettings').show();
		
	}
	
	function setDefault(type) {
		
		switch(type) {
		  case 'N':
		    
			  $('#NORMALCOLOR').val('#57788E');
			  $('#NORMALCOLOR2').val('#72889A');
			  $('#NORMALCOLORBG').val('#466072');
			  
		    break;
		  case 'W':
		    
			  $('#WRNCOLOR').val('#E5C62E');
			  $('#WRNCOLOR2').val('#E4CD64');
			  $('#WRNCOLORBG').val('#D6852D');
			  
		    break;
		  case 'O':
		    
			  $('#THRESHOLDCOLOR').val('#EF0A0A');
			  $('#THRESHOLDCOLOR2').val('#E04A3F');
			  $('#THRESHOLDCOLORBG').val('#B20202');
			  
		    break;
		
		}
	}
	
	var audioElementN = document.createElement('audio');
  audioElementN.setAttribute('src', '../sounds/normal.wav');
  
  var audioElementW = document.createElement('audio');
  audioElementW.setAttribute('src', '../sounds/warning.wav');
  
  var audioElementE = document.createElement('audio');
  audioElementE.setAttribute('src', '../sounds/error.wav');
	
	function setDefaultSounds(type) {
		
		
		alert("Cannot Set Sound from Cloud")
		
		
	}
	
	function upload(type) {
		//alert('upload '+type);
		
		alert("Cannot Update files to Pi from Cloud")
	}
  
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
  	
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();
	});
      
    
	function DownloadSettings() {
	
		var accountId = $('#ACCOUNTID').val();
		
		location.href = "../../occupancy/GetSettings2?accountId="+accountId+"&type=D";
		
	}
	
	
	function wait(ms){
	   var start = new Date().getTime();
	   var end = start;
	   while(end < start + ms) {
	     end = new Date().getTime();
	  }
	}
	
	var _openModal = '${_modal}';
	if(_openModal=='true') {
		_modal('../popup/labels-dett.htm?tipoForm=M&lang=${_lang}');
	}
	
	function _modal(href) {
		  
		  //alert(href);
		    	
	        $('#ajaxModal').remove();
	        var $this = $(this)
	          , $remote = $this.data('remote') || href
	          , $modal = $('<div class="modal fade" id="ajaxModal"><div class="modal-body"></div></div>');
	        $('body').append($modal);
	        $modal.modal();
	        
	        //alert('remote'+$remote);
	        
	        var data = '';
	        $.ajax({
	    	  url: $remote,
	    	  dataType: 'html',
	    	  success: function(html) {
	    	      
	    		  //alert(html);
	    		  $modal.append(html);
	    		  
	    	  }
	    	});
	        
		      
		  
	  }
	
	function selectCustomer(id) {
		
		var idcus = $('#'+id).val();
		
		$('#idcus_frm').val(idcus);
			
		$('#chgUser').submit();
	  }
	
	////////////////////////////
  </script> 
  
  
  
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  
  
	
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
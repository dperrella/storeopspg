<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
              
              <%@ include file="../../inc2/menuApp18.jsp" %>
              
              <section>
                  <section class="vbox">
              
               		<div class="row" id="div" style="opacity: 0.8; filter: alpha(opacity=50);
                  		z-index: 0; width:100%; position:absolute; background:#fff;margin-left: 0px;">
	              			
		              		<div class="col-sm-4 b-b b-2x">
		                      <div class="wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/sel_kpi.jsp" data-toggle="ajaxModal">
		                          <span class="h4 block"><i class="icon-puzzle i-lg"></i> WHAT?</span></a>
		                          <div class="h3 m-t-xs block text-ellipsis" id="sel_kpi">${nrkpi} Selected</div>
		                      </div>
		                    </div>
			                    
		              		<div class="col-sm-4 b-b b-2x">
		                      <div class=" wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/stores.jsp?nrstores=${nrstores}" data-toggle="ajaxModal">
		                        	<span class="h4 block text-ellipsis"><i class="icon-map i-lg"></i> WHERE? <%=session.getAttribute("dscus") %></span></a>
		                        	<div class="h3 m-t-xs block text-ellipsis" id="sel_stores2">${nrstores} Selected</div>
		                      </div>
		                    </div>
		                    
		                    <div class="col-sm-4 b-b b-2x">
		                      <div class=" wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/sel_period.jsp" data-toggle="ajaxModal">
		                          <span class="h4 block"><i class="icon-calendar i-lg"></i> WHEN?</span></a>
		                          <div class="h3 m-t-xs block text-ellipsis" id="sel_date2">${dates}</div>
		                      </div>
		                    </div>
		          
	              	</div>
	              	
                    <section class="scrollable padder" id="scrollable">
              			
              			<form action="index.htm" id="deskForm" name="deskForm" method="post">
	                      
	                      <input type="hidden" name="idstores" id="idstores" value="${idstores}"/>
	                      <input type="hidden" name="tipoPeriod" id="tipoPeriod" value="${tipoPeriod}"/>
	                      <input type="hidden" name="tipoPeriod2" id="tipo" value="Y"/>
	                      <input type="hidden" name="dtda" id="dtda" value="${dtda}"/>
	                      <input type="hidden" name="dtal" id="dtal" value="${dtal}"/>
	                      <input type="hidden" name="quarter" id="quarterForm" value="${quarter}"/>
	                      <input type="hidden" name="kpi" id="kpi" value="${kpi}"/>
	                      <input type="hidden" name="group" id="group" value="${group}"/>
	                      <input type="hidden" name="special" id="special" value=""/>
	                      <input type="hidden" name="upd" id="upd" value=""/>
	                      <input type="hidden" name="chart" id="chart" value="${chart}"/>
	                      
	                      <input type="hidden" name="_idstores" id="_idstores" value="${_idstores}"/>
	                      
	                    </form>
	                    
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
	                    <c:if test = "${nrkpi==0}">
	                    <div class="row row-sm">
							<div class="col-xs-12">
		                    	<div class="text-center m-b-lg">
									<h1 class="fontBig text-white animated"><%=propLang.get("nokpi") %></h1>
									<h3><%=propLang.get("nokpi2") %></h3>
								</div>
							</div>
						</div>
	                    </c:if>
	                    <c:if test = "${nrkpi>0}">
	                    <div class="row row-sm">
								
						  	<div class="col-xs-12">
							  
							  
							  <div class="row m-t-md m-b-md text-center">
								
								<div class="col-sm-4">
			                      <div class="bg-primary lter wrapper-md r">
			                        <a href="../popup/sel_kpi.jsp" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-puzzle i-lg"></i> WHAT?</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_kpi2">${nrkpi} Selected</div>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-4">
			                      <div class="bg-black wrapper-md r">
			                        <a href="../popup/stores.jsp?nrstores=${nrstores}"  data-toggle="ajaxModal">
			                        	<span class="h4 block"><i class="icon-map i-lg"></i> WHERE? <%=session.getAttribute("dscus") %></span></a>
			                        	<div class="h2 m-t-xs block text-ellipsis" id="sel_stores">${nrstores} Selected</div>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-4">
			                      <div class="bg-light dker wrapper-md r">
			                        <a href="../popup/sel_period.jsp" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-calendar i-lg"></i> WHEN?</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_date">${dates}</div>
			                      </div>
			                    </div>
			                    
			                  </div>
			                  
			                  
			                  <div id="msg">${msg}</div>
			                  
			                  
			                  <c:if test = "${nrkpi>1}">
			                  <!-- Tiles -->
			                  <div class="row row-sm">
			                  
			                  	<c:forEach items="${kpilist}" var="kpilist" varStatus="status">
			                  		<div class="col-xs-12 col-md-6 ${col}">
			                      	    <section class="panel panel-default ">
			                            <div id="panel_${kpilist.xcdel}" class="panel-body ${kpilist.cmp3} text-center">
			                              
			                              <!-- 
			                              <div class="sparkline inline" data-type="bar" data-height="40" data-width="100%" data-bar-width="10" data-bar-spacing="8" data-bar-color="${kpilist.color}">${kpilist.cmp8}</div>
			                              -->
			                              <h5 class="text-ellipsis" 
			                                data-toggle="popover" data-html="true" data-placement="bottom" data-content="<div class=&quot;h3&quot;>${kpilist.cmp1}</div>" data-trigger="hover" title="" data-original-title="<button type=&quot;button&quot; class=&quot;close pull-right&quot; data-dismiss=&quot;popover&quot;>&times;</button>Real Value ">${kpilist.xdtab}</h5>
			                              <span id="trend_${kpilist.xcdel}" class="m-t-xs pull-right"></span>
			                              <span class="h1 step"><a href="#" onclick="changeKpi('${kpilist.xcdel}','${kpilist.xdtab}')" data-toggle="tab">${kpilist.cmp2} ${kpilist.kpi_symbol}</a></span>
			                              
			                              
			                            </div>
			                            
			                            <div class="tiles_loader ${kpilist.cmp3} text-center" id="tiles_${kpilist.xcdel}">
			                              	<img src="../../images/loader_xs.gif"/>
			                            </div>
			                              
			                            </section>
			                            
			                        </div>
			                  	</c:forEach>
			                  
			                  </div>
			                  </c:if>
			                  
			                  <!-- Table e Chart -->
			                  <div class="row">
							
			                    <div class="col-sm-6">
			                      	<section class="panel panel-default">
					                    <header class="panel-heading bg-light">
					                      
					                      <c:if test = "${nrkpi==1}">
					                      <c:forEach items="${kpilist}" var="kpilist" varStatus="status">
			                  				<span><b>${kpilist.xdtab}</b></span>
				                      		<span class="hidden-sm pull-right" data-toggle="popover" data-html="true" data-placement="bottom" data-content="<div class=&quot;h3&quot;>${kpilist.cmp1}</div>" data-trigger="hover" title="" data-original-title="<button type=&quot;button&quot; class=&quot;close pull-right&quot; data-dismiss=&quot;popover&quot;>&times;</button>Real Value "><b>Tot.</b></h5>
				                              <span><a href="#" onclick="changeKpi('${kpilist.xcdel}','${kpilist.xdtab}')" data-toggle="tab"><b>${kpilist.cmp2} ${kpilist.kpi_symbol}</b></a></span>
				                      		</span>
			                  			  </c:forEach>
					                      </c:if>
					                      <c:if test = "${nrkpi>1}">
					                      <ul class="nav nav-tabs nav-justified">
					                      <c:forEach items="${kpilist}" var="kpilist" varStatus="status">
			                  				<li class="${kpilist.cmp4}"><a href="#${kpilist.xcdel}" data-toggle="tab" class="text-ellipsis"
					                        	onclick="changeKpi('${kpilist.xcdel}','${kpilist.xdtab}')" >
					                        	<c:if test="${fn:length(kpilist.xdtab) > 10}">
					                        	<span data-toggle="tooltip" data-container="body" data-placement="top" title="${kpilist.xdtab}">
					                        	${fn:substring(kpilist.xdtab, 0, 10)}...
					                        	</span>
					                        	</c:if>
					                        	<c:if test="${fn:length(kpilist.xdtab) <= 10}">
					                        	${kpilist.xdtab}
					                        	</c:if>
					                        	</a>
					                        </li>
					                      </c:forEach>          
					                      </ul>
					                      </c:if>
					                    </header>
					                    <div class="tab-content">
					                      <c:forEach items="${tablist}" var="tablist" varStatus="status">
					                      	
					                      	<div class="tab-pane ${tablist.cmp4}" id="${tablist.xcdel}">
					                        	
					                        	<header class="panel-heading bg-light" style="background-color:#fff">
						                          <div class="clearfix">
						                            <div class="clear">
						                              <div class="h3 m-t-xs m-b-xs">
						                                10 Highest ${tablist.cmp3} 
						                                <i class="fa fa-circle pull-right text-xs m-t-xs" style="color:${tablist.cmp5}"></i>
						                              </div>
						                            </div>
						                          </div>
						                        </header>
					                        	
					                        	<div class="table-responsive">
									                <table class="table table-striped b-t m-b-none" id="vistable" data-ride="datatables">
									                  <thead>
														  <tr>
													        <th width="5%">No.</th>
													        <th width="55%">Store</th>
													        <th width="25%">${tablist.cmp3}</th>
													        <th></th>
													      </tr>
													  </thead>
							      					  
							      					  <tbody>
													  	${tablist.xdtab}
								  					  </tbody>
								  					</table>
								  				</div>
					                        </div>
					                      	
					                      </c:forEach>
					                    </div>
					                    
					              </section>
			                    </div>
			                    
			                    <div class="col-sm-6">
								  
								  
								  
								  <section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
				                      <span class="hidden-sm"><b>Chart</b></span>
				                      
				                      <!-- 
				                      <span class="pull-right">
				                      <a href="#" onclick="setChart('R');changeKpi('${kpi_selected}','${kpi_desc_selected}');getRadar('${kpi_selected}')">Radar</a>
				                      <a href="#" onclick="setChart('P');changeKpi('${kpi_selected}','${kpi_desc_selected}');getRadar('${kpi_selected}')">Pie</a>
				                      </span>
				                      -->
				                      <div class="btn-group pull-right" data-toggle="buttons">
										  <label class="btn btn-default btn-xs active" id="btn_chart_radar"
										  	onclick="setChart('R');changeKpi2('${kpi_selected}','${kpi_desc_selected}')">
										    <input type="radio" id="chart_radar" name="tp_chart" value="R" ${s1}/> RADAR
										  </label>
										  <label class="btn btn-default btn-xs " id="btn_chart_pie"
										  	onclick="setChart('P');changeKpi2('${kpi_selected}','${kpi_desc_selected}')">
										    <input type="radio" id="chart_pie" name="tp_chart" value="P" ${s2}/> PIE
										  </label>
										  <label class="btn btn-default btn-xs " id="btn_chart_area"
										  	onclick="setChart('A');changeKpi2('${kpi_selected}','${kpi_desc_selected}')">
										    <input type="radio" id="chart_area" name="tp_chart" value="A" ${s3}/> AREA
										  </label>
									  </div>
				                      
				                    </header>
				                    <div class="panel-body">
				                    
				                    	<div id="main" style="height:435px;">
				                    		<h3>LOADINGGGGG</h3>
				                    	</div>
				                     
				                    </div>
				                  </section>
				                  
				                  
								</div>
							  </div>	
			                    
			                </div>
			            </div>
			            
			            <div class="row row-sm">
							<div class="col-xs-12">
							
								<section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
				                      <span class="hidden-sm"><b>HeatMap</b></span>
				                    </header>
				                    <div class="panel-body">
				                    
				                    	<div id="main2" style="height:${height}px;"></div>
				                     
				                    </div>
				                </section>
						  	
						  	</div>
						</div>
						
						
						<c:if test="${showAlarm != ''}">
						
						<div class="row row-sm">
							<div class="col-xs-12">
						
								<section class="panel panel-default">
					              <header class="panel-heading font-bold">
					                <span class="text-muted m-r-sm"><i class="fa fa-warning"></i></span>
				                    <span class="hidden-sm"><b><%=propLang.get("lastAl") %></b></span>
					                
					                <a href="../../analytics2/main/silenced-alarms-list.htm" class="btn btn-default btn-xs m-l-sm pull-right" >
			                            <i class="fa fa-power-off"></i>
			                            <%=propLang.get("asi") %> 
			                        </a>
					              </header>
					              
					              <c:if test="${tot > 0}">
					              
					              <div class="row wrapper">
				              	    <div class="col-sm-4 hidden-xs">
				              	    	
				              	    	<!-- 
				                    	<div class="btn-group">
						                  <button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><%=propLang.get("sil") %> <span class="caret"></span></button>
						                  <ul class="dropdown-menu">
						                    <li><a href="#" onclick="sil('3600', '1H')">1 Hour</a></li>
						                    <li><a href="#" onclick="sil('10800', '3H')">3 Hours</a></li>
						                    <li><a href="#" onclick="sil('21600', '6H')">6 Hours</a></li>
						                    <li><a href="#" onclick="sil('43200', '12H')">12 Hours</a></li>
						                    <li><a href="#" onclick="sil('86400', '1D')">1 Day</a></li>
						                    <li><a href="#" onclick="sil('604800', '1W')">1 Week</a></li>
						                    <li><a href="#" onclick="sil('2628000', '1M')">1 Month</a></li>
						                  </ul>
						                </div>
						                -->
						                <a href="active-alarms-list.htm" class="btn btn-default"><%=propLang.get("gestAl") %></a>
				                    </div>
				                    <div class="col-sm-4 text-center">
				                      <small class="text-muted inline m-t-sm m-b-sm"><%=propLang.get("staivis") %> ${ini}-${fin+ini} <%=propLang.get("di") %> ${tot} </small>
				                    </div>
				                    <div class="col-sm-4 text-right text-center-xs">       
				                      <!--          
				                      <ul class="pagination pagination m-t-none m-b-none">
				                        <li><a href="#" onclick="setPage('${prev}');showLoader()"><i class="fa fa-chevron-left"></i> <%=propLang.get("prev") %></a></li>
				                        <li><a href="#" onclick="setPage('${next}');showLoader()"><%=propLang.get("next") %> <i class="fa fa-chevron-right"></i></a></li>
				                      </ul>
				                      -->
				                    </div>
				                 
					              </div>
					              
					              <div id="tab" class="table-responsive">
					                <table class="table table-striped b-t m-b-none">
					                  <thead>
										  <tr>
										  <!-- 
									        <th><a href="active-alarms-list.htm?order=id&sort=${sort}">Id</a></th>
									      -->
									      
									        <th></th>
									        <th><a href="active-alarms-list.htm?order=customer&sort=${sort}"><%=propLang.get("cli") %></a></th>
									        <th><a href="active-alarms-list.htm?order=store&sort=${sort}"><%=propLang.get("aa_store") %></a></th>
									        <th><a href="active-alarms-list.htm?order=code&sort=${sort}"><%=propLang.get("aa_code") %></a></th>
									        <th><a href="active-alarms-list.htm?order=description&sort=${sort}"><%=propLang.get("aa_descr") %></a></th>
									        <th><a href="active-alarms-list.htm?order=threshold&sort=${sort}"><%=propLang.get("aa_threshold") %></a></th>
									        <th><a href="active-alarms-list.htm?order=timeunit&sort=${sort}"><%=propLang.get("aa_timeunit") %></a></th>
									        <th><a href="active-alarms-list.htm?order=control_time&sort=${sort}"><%=propLang.get("aa_control_time") %></a></th>
									        <th><a href="active-alarms-list.htm?order=from_time&sort=${sort}"><%=propLang.get("aa_from_time") %></a></th>
									        <th><a href="active-alarms-list.htm?order=to_time&sort=${sort}"><%=propLang.get("aa_to_time") %></a></th>
									        <th><a href="active-alarms-list.htm?order=value&sort=${sort}">Value</a></th>
									        <th></th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
									  <c:forEach items="${alarms}" var="alarms" varStatus="status">
										<tr>
										<!-- 
										  <td>${alarms.cmp1}</td>
										-->
										  <td>${alarms.xcdtb}</td>
										  <td>${alarms.cmp2}</td>
										  <td>${alarms.cmp3}</td>
										  <td><span class="label bg-light">${alarms.cmp4}</span></td>
										  <td>${alarms.cmp5}</td>
										  <td>${alarms.cmp6}</td>
										  <td>${alarms.cmp7}</td>
										  <td>${alarms.cmp8}</td>
										  <td>${alarms.cmp9}</td>
										  <td>${alarms.cmp10}</td>
										  <td><b>${alarms.xdtab}</b></td>
										  <td><a href="../../control/popup/alarm-dett.jsp?id=${alarms.cmp12}" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-search"></i></a></td>
										  
										  <c:choose>
											  <c:when test="${alarms.cmp10 == '0.0'}">
											    <td>-</td>
											  </c:when>
											  <c:otherwise>
											    <td><a href="../../analytics2/main/index-report-devices-modal.htm?idstores=${alarms.cmp13}&kpi=${alarms.cmp11}&dtda=${alarms.cmp14}&dtal=${alarms.cmp15}&tipoPeriod=DD&tipo2=DD&kpi_selected=${alarms.cmp11}" class="btn btn-default" data-toggle="ajaxModal" onclick="showLoader2()"><i class="icon-bar-chart"></i></a></td>
										  	  </c:otherwise>
										  </c:choose>
										  
										</tr>
									  </c:forEach>
				  					  </tbody>
				  					</table>
				  					
				  				  </div>
				  				  
				  				  <footer class="panel-footer">
					                  <div class="row">
					                    <div class="col-sm-4 hidden-xs">
					                      	               
					                    </div>
					                    <div class="col-sm-4 text-center">
					                      <small class="text-muted inline m-t-sm m-b-sm"><%=propLang.get("staivis") %> ${ini}-${fin+ini} <%=propLang.get("di") %> ${tot} </small>
					                    </div>
					                    <div class="col-sm-4 text-right text-center-xs">                
					                      
					                      <!-- 
					                      <ul class="pagination pagination m-t-none m-b-none">
					                        <li><a href="#" onclick="setPage('${prev}');showLoader()"><i class="fa fa-chevron-left"></i> <%=propLang.get("prev") %></a></li>
					                        <li><a href="#" onclick="setPage('${next}');showLoader()"><%=propLang.get("next") %> <i class="fa fa-chevron-right"></i></a></li>
					                      </ul>
					                      -->
					                    </div>
					                  </div>
					              </footer>
				  				  
				  				  </c:if>
					              
					              <c:if test="${tot == 0}">
					              	
					              	<div class="row wrapper text-center">
					              		
					              		<p class="h3 font-thin"><%=propLang.get("dt_nessunrecord") %></p><p></p>
					              		<p><%=propLang.get("msgparm") %></p> 
					              		
					              	</div>
					              	<footer class="panel-footer">
					                  <div class="row">
					                    <div class="col-sm-4 hidden-xs">
					                      	               
					                    </div>
					                    <div class="col-sm-4 text-center">
					                      <a href="../main/index.htm" onclick="showLoader()" class="btn btn-success btn-lg"><%=propLang.get("backDash") %></a> 
					                    </div>
					                    <div class="col-sm-4 text-right text-center-xs">                
					                      
					                    </div>
					                  </div>
					              	</footer>
					              	
					              </c:if>
				  				
				  				</section>
				  				
				  			</div>
				  		</div>
				  		</c:if>
								
						</c:if>
						</c:if>
			            
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
  
  <!-- Sparkline Chart -->
  <script src="../../js/charts/sparkline/jquery.sparkline.min.js"></script>
  
  <script src="../../js/app.plugin.js?v=2"></script>
  
  <!-- Treeview -->
  <script src="../../js/bootstrap-treeview/bootstrap-treeview.min.js"></script>
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js?v=2"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <script>
  
  	var nrkpi = ${nrkpi};
  	if(nrkpi<3) {
  		$('#btn_chart_radar').remove();
  		$('#chart_pie').attr('checked', true);	
  		$('#btn_chart_pie').addClass('active');
  		
  	}
  
    $("#div").hide();
	$('#scrollable').scroll(function() {
	    if ($('#scrollable').scrollTop() > 400) {
	        // > 100px from top - show div
	        //$('#scrollable').css('margin-top',69	);
	        $('#div').css('z-index',9999 )
	    	$("#div").fadeIn("slow");
	    	
	    }
	    else {
	        // <= 100px from top - hide div
	        //$('#scrollable').css('margin-top',0);
	       	$('#dic').css('z-index',-1)
	    	$("#div").fadeOut("fast");
	    }
	});
  	
	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();
		<c:if test = "${idcus!=''}">
	    <c:if test = "${nrkpi>0}">
		loadTiles();
		</c:if>
		</c:if>
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
	
	function loadTiles() {
			
		
		var xxx = '${newTiles}'.split(";");
		for (i = 0; i < xxx.length; i++) { 
			
			var yyy = xxx[i].split('##@@');
			var kpi = yyy[0];
			var dt = yyy[1];
			var tt = yyy[2];
			var ic = yyy[3];
			var cl = yyy[4];
			
			var col1 = cl;
			var col2 = convertHex(cl,40);
			var col3 = convertHex(cl,80);
			
			
			//alert(kpi+"-"+dt);
			$('#trend_'+$.trim(kpi)).html(ic);
			$('#tiles_'+$.trim(kpi)).html('<div class="row"><div class="col-sm-12"><div class="sparkline2 css'+i+'" data-type="line" data-height="60" data-width="100%" data-line-width="3" data-line-color="'+col1+'" data-spot-color="'+col2+'" data-fill-color="'+col3+'" data-spot-radius="1" data-highlight-line-color="#fff">'+dt+'</div></div></div>');
			
		}
		
		spark();
		
		/*
		var datastring = $("#deskForm").serialize();
		
		$.ajax({
		      url: '../ajax/getTilesChart.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
					//alert(data);
					
					console.log(data);
					
					$('.tiles_loader').empty();
					
					var xxx = data.split(";");
					
					for (i = 0; i < xxx.length; i++) { 
						
						var yyy = xxx[i].split('##@@');
						var kpi = yyy[0];
						var dt = yyy[1];
						var tt = yyy[2];
						var ic = yyy[3];
						var cl = yyy[4];
						
						var col1 = cl;
						var col2 = convertHex(cl,40);
						var col3 = convertHex(cl,80);
						
						
						//alert(kpi+"-"+dt);
						$('#trend_'+$.trim(kpi)).html(ic);
						$('#tiles_'+$.trim(kpi)).html('<div class="row"><div class="col-sm-12"><div class="sparkline2 css'+i+'" data-type="line" data-height="60" data-width="100%" data-line-width="3" data-line-color="'+col1+'" data-spot-color="'+col2+'" data-fill-color="'+col3+'" data-spot-radius="1" data-highlight-line-color="#fff">'+dt+'</div></div></div>');
						
					}
					
					spark();
					
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		*/
		
	}
	
	 function convertHex(hex,opacity){
	        hex = hex.replace('#','');
	        r = parseInt(hex.substring(0,2), 16);
	        g = parseInt(hex.substring(2,4), 16);
	        b = parseInt(hex.substring(4,6), 16);

	        result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
	        
	        //alert(result);
	        
	        return result;
	    }
	
	var fn;
	var sparkResize;
	function spark() {
		
		/*
		// sparkline
		var sr, sparkline = function($re){
			$(".sparkline2").each(function(){
				var $data = $(this).data();
				if($re && !$data.resize) return;
				($data.type == 'pie') && $data.sliceColors && ($data.sliceColors = eval($data.sliceColors));
				($data.type == 'bar') && $data.stackedBarColor && ($data.stackedBarColor = eval($data.stackedBarColor));
				$data.valueSpots = {'0:': $data.spotColor};
				$(this).sparkline('html', $data);
			});
		};
		
		
		$(window).resize(function(e) {
			
			clearTimeout(sr);
			sr = setTimeout(function(){sparkline(true)}, 500);
			
		});
		
		sparkline(false);
		*/
		
		$(".sparkline2").each(function(){
			var $data = $(this).data();
			
			/*
			try {
				$data = JSON.parse($data);
			}
			catch(err) {
				alert('chitemm');
			}
			*/
			
			console.log($data);
			($data.type == 'bar') && $data.stackedBarColor && ($data.stackedBarColor = eval($data.stackedBarColor));
			$data.valueSpots = {'0:': $data.spotColor};
			$(this).sparkline('html', $data);
		});
	
	}
	
	function spark2() {
		
		/*
		// sparkline
		var sr, sparkline = function($re){
			$(".sparkline2").each(function(){
				var $data = $(this).data();
				if($re && !$data.resize) return;
				($data.type == 'pie') && $data.sliceColors && ($data.sliceColors = eval($data.sliceColors));
				($data.type == 'bar') && $data.stackedBarColor && ($data.stackedBarColor = eval($data.stackedBarColor));
				$data.valueSpots = {'0:': $data.spotColor};
				$(this).sparkline('html', $data);
			});
		};
		
		
		$(window).resize(function(e) {
			
			clearTimeout(sr);
			sr = setTimeout(function(){sparkline(true)}, 500);
			
		});
		
		sparkline(false);
		*/
		
		$(".sparkl").each(function(index, value){
			var $data = $(this).data();
			
			/*
			try {
				$data = JSON.parse($data);
			}
			catch(err) {
				alert('chitemm');
			}
			*/
			
			//$('.css'+index+' > canvas').remove();
			
			console.log($data);
			($data.type == 'bar') && $data.stackedBarColor && ($data.stackedBarColor = eval($data.stackedBarColor));
			$data.valueSpots = {'0:': $data.spotColor};
			$('.css'+index).sparkline('html', $data);
		});
	
	}
	
	/*
	$(window).resize(function(e) {
		clearTimeout(sparkResize);
		sparkResize = setTimeout(spark, 500);
	});
	*/
	
	// based on prepared DOM, initialize echarts instance
    var myChart = echarts.init(document.getElementById('main'));
	
	var chart = $('#chart').val();
	
	switch(chart) {
    case 'R':
	    // specify chart configuration item and data
	    option = {
	   		title: {
	   	        text: '${kpi_selected}'
	   	    },
		    tooltip: {},
		    legend: {
		        data: ['Highest Store', 'Top 10', 'All Stores'],
		        orient: 'vertical',
		        x: 'right',
		    
		    },
		    radar: {
		        // shape: 'circle',
		        name: {
		            textStyle: {
		                color: '#fff',
		                backgroundColor: '#999',
		                borderRadius: 3,
		                padding: [3, 5]
		           }
		        },
		        indicator: [
					<c:forEach items="${radarlimit}" var="radarlimit" varStatus="status">
	
		           { name: '${radarlimit.xdtab}', max: ${radarlimit.cmp1}},
		           
		           </c:forEach>
		        ]
		    },
		    series: [{
		        name: 'Stores',
		        type: 'radar',
		        // areaStyle: {normal: {}},
		        data : [
		            {
		                value : [${serie1}],
		                name : 'Highest Store',
		                itemStyle: {
			                normal: {
			                    color: '#EE5154'
			                }
			            },
		                
		            },
		            {
		                value : [${serie2}],
		                name : 'Top 10',
		                itemStyle: {
			                normal: {
			                    color: '#242C32'
			                }
			            },
		                
		            },
		            {
		                value : [${serie3}],
		                name : 'All Stores',
		                itemStyle: {
			                normal: {
			                    color: '#7980B2'
			                }
			            },
		                
		            }
		        ]
		    }]
		};
		
	    break;
	    
    case 'P':
    	
    	option = {
		    title : {
		        text: '${kpi_selected}',
		        subtext: 'Best Stores',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	 bottom: 10,
		         left: 'center',
		         data: ${legend},
		         type: 'scroll',
		         
		    },
		    toolbox: {
		    	show : true,
		        showTitle: false,
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    series : [
		        {
		            name: 'Stores',
		            type: 'pie',
		            radius : '55%',
		            //center: ['50%', '60%'],
		            data: ${indicator},
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
    	
    	break;
    	
	}
    	
    // use configuration item and data specified to show chart
    myChart.setOption(option);
	
	
    
	// HEATMAP
    // based on prepared DOM, initialize echarts instance
    var myChart2 = echarts.init(document.getElementById('main2'));
	
	/*
 	// specify chart configuration item and data
    option2 = {
    		
	    tooltip: {
	        position: 'top'
	    },
	    animation: true,
	    grid: {
	        height: '70%',
	        y: '10%'
	    },
	    xAxis: {
	        type: 'category',
	        //data: [${xAxis}],
	        axisLabel: { rotate: 60, },
	        data: ['00\n01','01\n02','02\n03','03\n04','04\n05','05\n06','06\n07','07\n08','08\n09','09\n10','10\n11','11\n12','12\n13','13\n14','14\n15','15\n16','16\n17','17\n18','18\n19','19\n20','20\n21','21\n22','22\n23','23\n24'],
	        splitArea: {
	            show: true
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: [${yAxis}],
	        splitArea: {
	            show: true
	        }
	    },
	    visualMap: {
	        min: 0,
	        max: ${max},
	        calculable: true,
	        orient: 'vertical',
	        left: 'right',
	        top: 'center',
	        inRange: {
	            
	        	//color: [ '#3F4787',  '#F9CD2F',  '#EF8384', '#EE6B6D', '#EE5254', '#EE393D',    '#EF2027']
	        	color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
	        }
	    },
	    series: [{
	        name: '${kpi_desc_selected}',
	        type: 'heatmap',
	        data: [${heatmap}],
	        label: {
	            normal: {
	                show: true
	            }
	        },
	        itemStyle: {
	            emphasis: {
	                shadowBlur: 10,
	                shadowColor: 'rgba(0, 0, 0, 0.5)'
	                
	            },
	            normal: {
                    color: '#EE5154'
                }
	        }
	    }]
	
 		
 	}
    // use configuration item and data specified to show chart
    myChart2.setOption(option2);
    
	*/
	
	function setCal() {
  		
  		 $('.datepicker-input2').datepicker({
  		    format: 'dd-mm-yyyy',
  		    orientation: 'bottom',
  		    autoclose: true
  		});  
  		
  	}
	
	function setSelectPicker() {
		$('.selectpicker').selectpicker();
	}
	
	function cambiaSettimana(anno) {
		
		$.ajax({
		      url: '../ajax/getWeeks.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+anno,
		      success: function (data) {
				//alert(data);
		    	  $('#week option:gt(0)').remove();
		    	  $('#week').append(data);
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function cambiaMese(anno) {
		
		$.ajax({
		      url: '../ajax/getMonths.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+anno,
		      success: function (data) {
				//alert(data);
		    	  $('#month option:gt(0)').remove();
		    	  $('#month').append(data);
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function cambiaQuarter(anno) {
		
		$.ajax({
		      url: '../ajax/getQuarters.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+anno,
		      success: function (data) {
				//alert(data);
		    	  $('#quarter option:gt(0)').remove();
		    	  $('#quarter').append(data);
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function _getChildren(node) {
	    if (node.nodes === undefined) return [];
	    var childrenNodes = node.nodes;
	    node.nodes.forEach(function(n) {
	        childrenNodes = childrenNodes.concat(_getChildren(n));
	    });
	    return childrenNodes;
	}
	
	var $searchableTree;
	
	
	function getStores() {
		
		var num = 0;
		//var $searchableTree;
		
		//$('#tree').html('<div align="center">Please wait...<br/><img src="../../images/loader_xs.gif"/></div>');
		
		console.log('${idstores}');
		
		$.ajax({
		      type: "POST",
		      url: "../../servlet/ws/GetStoresNodes",
		      data: "username=${username}&idstores=${idstores}&idcus=${idcus}",
		      dataType: "json",
		      success: function(result)
		      {
		        
		    	  $('#tree').html('');	
					
					$searchableTree = $('#tree').treeview({
						data: result,
						levels: 1,
						multiSelect: true,
						selectedBackColor: '#4CB6CB',
						showCheckbox: true,
						checkedIcon: 'fa fa-check-circle-o',
						uncheckedIcon: 'fa fa-circle-o',
						collapseIcon: 'fa fa-minus-square-o',
						expandIcon: 'fa fa-plus-square-o'
							
					}).on('nodeChecked', function (event, node){
						//alert(JSON.stringify(node));
						var childrenNodes = _getChildren(node);
						//alert(JSON.stringify(childrenNodes));
						$(childrenNodes).each(function(){
					        $('#tree').treeview('checkNode', [ this.nodeId, { silent: true } ]);;
					        
					        $('#modal-nrstores').html('Send request to recalculate Stores');
					    });
						
					}).on('nodeUnchecked', function (event, node){
						//alert(JSON.stringify(node));
						var childrenNodes = _getChildren(node);
						//alert(JSON.stringify(childrenNodes));
						$(childrenNodes).each(function(){
					        $('#tree').treeview('uncheckNode', [ this.nodeId, { silent: true } ]);;
					        //num-=1;
					        
					        $('#modal-nrstores').html('Send request to recalculate Stores');
					    });
					});
					
					$('#btn-uncheck').prop("disabled", false);
					
					getCategory();
		    	  
		      },
		      error: function()
		      {
		        alert("Chiamata fallita, si prega di riprovare...");
		      }
		    });
		
// 		$.getJSON("../../servlet/ws/GetStoresNodes?username=${username}&idstores=${idstores}&idcus=${idcus}", function(result){
			
// 			$('#tree').html('');
			
// 			$searchableTree = $('#tree').treeview({
// 				data: result,
// 				levels: 1,
// 				multiSelect: true,
// 				selectedBackColor: '#4CB6CB',
// 				showCheckbox: true,
// 				checkedIcon: 'fa fa-check-circle-o',
// 				uncheckedIcon: 'fa fa-circle-o',
// 				collapseIcon: 'fa fa-minus-square-o',
// 				expandIcon: 'fa fa-plus-square-o'
					
// 			}).on('nodeChecked', function (event, node){
// 				//alert(JSON.stringify(node));
// 				var childrenNodes = _getChildren(node);
// 				//alert(JSON.stringify(childrenNodes));
// 				$(childrenNodes).each(function(){
// 			        $('#tree').treeview('checkNode', [ this.nodeId, { silent: true } ]);;
			        
// 			        $('#modal-nrstores').html('Send request to recalculate Stores');
// 			    });
				
// 			}).on('nodeUnchecked', function (event, node){
// 				//alert(JSON.stringify(node));
// 				var childrenNodes = _getChildren(node);
// 				//alert(JSON.stringify(childrenNodes));
// 				$(childrenNodes).each(function(){
// 			        $('#tree').treeview('uncheckNode', [ this.nodeId, { silent: true } ]);;
// 			        //num-=1;
			        
// 			        $('#modal-nrstores').html('Send request to recalculate Stores');
// 			    });
// 			});
			
// 			$('#btn-uncheck').prop("disabled", false);
			
// 		});
		
		
		var search = function(e) {
		  var pattern = $('#input-search').val();
          var options = {
            ignoreCase: true,
            exactMatch: false,
            revealResults: true
          };
          var results = $searchableTree.treeview('search', [ pattern, options ]);
        }
		//$('#input-search').on('keyup', search);
		$('#search-btn').on('click', search);
		
	}
	
	function getNodes(azione) {
		
		var json = $('#tree').treeview('getChecked');
		
		var num = 0;
		var idstores = '';
		for (var i=0;i<json.length;++i)
	    {
			if(json[i].type=='STORE') {
				num+=1;
	        	//$('#results').append('<div class="name">'+json[i].type+' '+json[i].id+'</div>');
	        	idstores+=json[i].id+',';
			}
	    }
		idstores=idstores.slice(0, -1);
		$('#idstores').val(idstores);
		$('#sel_stores').html(num+" Selected");
		
		if($('#updStores').is(':checked')) {
			$('#upd').val('S');
		}
		else {
			$('#upd').val('');
		}
		
		$('.modal').remove(); 
		$('div.modal-backdrop').remove();
		
		getGroup(json);
		
		if(azione=='GO') {
			showLoader();
			$( "#deskForm" ).submit();
		}
		
	}
	
	function getGroup(json) {
		
		var brand = false;
		var region = false;
		var area = false;
		var store = false;
		
		for (var i=0;i<json.length;++i)
	    {
			if(json[i].type=='CAT1') {
				brand = true;
			}
			if(json[i].type=='CAT2') {
				region = true;
			}
			if(json[i].type=='CAT3') {
				area = true;
			}
			if(json[i].type=='STORE') {
				store = true;
			}
	    }
		
		if(brand) {
			$('#group').val('1');
			return;
		}
		else if(region) {
			$('#group').val('2');
			return;
		}
		else if(area) {
			$('#group').val('3');
			return;
		}
		else if(store) {
			$('#group').val('4');
			return;
		}
		
	}
	
	function saveNodes() {
		
		showLoader();
		getNodes('');
		var idstores = $('#idstores').val();
		var group = $('#group').val();
		//alert(idstores);
		
		$.ajax({
		      url: '../ajax/savePrefUserStores.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'group='+group+'&idstores='+idstores,
		      success: function (data) {
				//alert(data);
		    	  $( "#deskForm" ).submit();
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function uncheckAll() {
		
		$('#tree').treeview('uncheckAll', { silent: true });
		$('#modal-nrstores').html('(0 Stores selected)');
		
	}
	
	function getKpi(azione) {
		
		var kpi = "";
		var num = 0;
		$('input.kpi:checkbox:checked').each(function () {
			num+=1;
		    kpi+=$(this).val()+",";
		});
		
		if(num>5) {
			alert('Select max 5 KPI.');
			return;
		}
		else {
			kpi=kpi.slice(0, -1);
			$('#kpi').val(kpi);
			$('#sel_kpi').html(num+" Selected");
			
			if(azione=='GO') {
				//alert(kpi);
				showLoader();
				submitForm();
			}
		}
		/*
		kpi=kpi.slice(0, -1);
		$('#kpi').val(kpi);
		$('#sel_kpi').html(num+" Selected");
		
		if(azione=='GO') {
			//alert(kpi);
			showLoader();
			submitForm();
		}
		*/
	}
	
	function saveKpi() {
		
		showLoader();
		getKpi('');
		var kpi = $('#kpi').val();
		
		$.ajax({
		      url: '../ajax/savePrefUserKpi.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi='+kpi,
		      success: function (data) {
				//alert(data);
				submitForm();
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function changeTab() {
		var tipo = $('#tipo').val();
		var tipoPeriod = $('#tipoPeriod').val();
		$('#tipo').val(tipoPeriod);
		if(tipoPeriod=='Y') {
			$('.nav-tabs a[href=#yearTab]').tab('show');
		}
		else if(tipoPeriod=='Q') {
			$('.nav-tabs a[href=#quarterTab]').tab('show');
		}
		else if(tipoPeriod=='M') {
			$('.nav-tabs a[href=#monthTab]').tab('show');
		}
		else if(tipoPeriod=='W') {
			$('.nav-tabs a[href=#weekTab]').tab('show');
		}
		else if(tipoPeriod=='D') {
			$('.nav-tabs a[href=#dayTab]').tab('show');
		}
		else if(tipoPeriod=='P') {
			$('.nav-tabs a[href=#prefTab]').tab('show');
		}	
	}
	
	
	function submitForm() {
		
		$( "#deskForm" ).submit();
		
	}
	
	function callReport(idstore) {
		
		$('#idstores').val(idstore);
		$('#group').val("");
		//$('#kpi').val("VIS,TRA,CON,EAS,EVI");
		showLoader();
		$( "#deskForm").attr('action', 'index-report-devices.htm');
		$( "#deskForm" ).submit();
		
	}
	
	function changeKpi(kpi, kpidesc) {
		
		<c:forEach items="${kpilist}" var="kpilist" varStatus="status">
			$( '#panel_${kpilist.xcdel}' ).removeClass( 'bg-light dk' );
			$( '#tiles_${kpilist.xcdel}' ).removeClass( 'bg-light dk' );
		</c:forEach>
		$( '#panel_'+kpi ).addClass( 'bg-light dk' );
		$( '#tiles_'+kpi ).addClass( 'bg-light dk' );
		$('.nav-tabs a[href=#'+kpi+']').tab('show');
		getHeatMap(kpi, kpidesc);
		getRadar(kpi);
	}
	
	function changeKpi2(kpi, kpidesc) {
		
		<c:forEach items="${kpilist}" var="kpilist" varStatus="status">
			$( '#panel_${kpilist.xcdel}' ).removeClass( 'bg-light dk' );
			$( '#tiles_${kpilist.xcdel}' ).removeClass( 'bg-light dk' );
		</c:forEach>
		$( '#panel_'+kpi ).addClass( 'bg-light dk' );
		$( '#tiles_'+kpi ).addClass( 'bg-light dk' );
		$('.nav-tabs a[href=#'+kpi+']').tab('show');
		//getHeatMap(kpi, kpidesc);
		getRadar(kpi);
	}
	
	var dft_chart = '${dft_chart}';
	var chart = $('#chart').val();
	
	
	if(dft_chart=='R') {
		setChart('R');
		$( "#btn_chart_radar" ).addClass( "active" );
		$( "#btn_chart_pie" ).removeClass( "active" );
		$( "#btn_chart_area" ).removeClass( "active" );
	}
	else if(dft_chart=='P') {
		setChart('P');
		$( "#btn_chart_radar" ).removeClass( "active" );
		$( "#btn_chart_pie" ).addClass( "active" );
		$( "#btn_chart_area" ).removeClass( "active" );
		
	}
	else if(dft_chart=='A') {
		$( "#btn_chart_radar" ).removeClass( "active" );
		$( "#btn_chart_pie" ).removeClass( "active" );
		$( "#btn_chart_area" ).addClass( "active" );
	}
	
	/*
	if(dft_chart!=chart) {
		$('#chart').val(dft_chart);
		getRadar('${kpi_selected}');
	}
	*/
	
	$('#chart').val(dft_chart);
	getRadar('${kpi_selected}');
	getHeatMap('${kpi_selected}','${kpi_desc_selected}');
	
	
	function getRadar(kpi) {
		
		
		var chart = $('#chart').val();
		
		var idstores = $('#idstores').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		var kpilist = $('#kpi').val();
		
		//alert(idstores+" "+kpi+" "+kpilist);
		
		myChart.dispose();
		$('#main').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		//myChart = echarts.init(document.getElementById('main'));
						
		
		if(chart=='R') {
		
			$.ajax({
			      url: '../ajax/getRadar.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: true,
			      data: 'kpi_selected='+kpi+'&kpi='+kpilist+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}',
			      success: function (data) {
			    	  //hideLoader();
					  //alert(data);
					  
					  var res = data.split('##@@');
					  
					  
					  
					  var indicator=res[0];
					  var serie1=res[1];
					  var serie2=res[2];
					  var serie3=res[3];
					  
					  //alert(maxVis+"\n"+maxTra+"\n"+maxCon+"\n"+maxEas+"\n"+maxEvi+"\n"+serie1+"\n"+serie2+"\n"+serie3);
					  
					  option = {
					    
					    title: {
					        text: kpi
					    },
					    tooltip: {
					    	
					    },
					    legend: {
					        data: ['Highest Store', 'Top 10', 'All Stores'],
					        orient: 'vertical',
					        x: 'right',
					    
					    },
					    toolbox: {
					    	show : true,
					        showTitle: false,
					        
					        feature: {
					            saveAsImage: {}
					        }
					    },
					    radar: {
					        // shape: 'circle',
					        name: {
					            textStyle: {
					                color: '#fff',
					                backgroundColor: '#999',
					                borderRadius: 3,
					                padding: [3, 5]
					           }
					        },
					        indicator: JSON.parse(indicator)
					    },
					    series: [{
					        name: 'Budget vs spending',
					        type: 'radar',
					        // areaStyle: {normal: {}},
					        data : [
					            {
					                value : serie1.split(','),
					                name : 'Highest Store',
					                
					            },
					            {
					                value : serie2.split(','),
					                name : 'Top 10',
					                
					            },
					            {
					                value : serie3.split(','),
					                name : 'All Stores',
					                
					            }
					        ]
					    }]
					  };
					  
					  //myChart.dispose();
						
					  myChart = echarts.init(document.getElementById('main'));
						
					  myChart.setOption(option);
					  
					  
					  
			      },
			      error: function () {
			      	//alert('Errore! si e\' verificato un errore.');
			      }
			});
		
		}
		else if(chart=='P') {
			
			$.ajax({
			      url: '../ajax/getPie.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: true,
			      data: 'kpi_selected='+kpi+'&kpi='+kpilist+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}',
			      success: function (data) {
			    	  //hideLoader();
					  //alert(data);
					  
					   var res = data.split('##@@');
					  
					  
					  
					  var indicator=res[0];
					  var legend=res[1];
					  
					  
					  option = {
						//color: [ '#3F4787', '#EF2027', '#F9CD2F', '#C5A848', '#595A7A', '#F7A42D', '#F47C2C', '#F25429', '#F14629', '#cc5933'],
								
					    title : {
					        text: kpi,
					        //subtext: 'Best Stores',
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    legend: {
					    	 bottom: 10,
					         left: 'center',
					         data: JSON.parse(legend),
					         type: 'scroll',
					         
					    },
					    toolbox: {
					    	show : true,
					        showTitle: false,
					        
					        feature: {
					            saveAsImage: {}
					        }
					    },
					    series : [
					        {
					            name: 'Stores',
					            type: 'pie',
					            //radius : '55%',
					            radius: ['20%', '70%'],
					            //center: ['50%', '60%'],
					            data: JSON.parse(indicator),
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                }
					            }
					        }
					    ]
					};
					  
					  //myChart.dispose();
						
					  myChart = echarts.init(document.getElementById('main'));
						
					  myChart.setOption(option);  
					 
					  
			      },
			      error: function () {
			      	//alert('Errore! si e\' verificato un errore.');
			      }
			});
			
		}
		else if(chart=='A') {
			
			var tipo = $('#tipoPeriod').val();
			
			$.ajax({
			      url: '../ajax/getArea.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: true,
			      data: 'kpi_selected='+kpi+'&kpi='+kpilist+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&tipo='+tipo+'&uid=${uid}',
			      success: function (data) {
			    	  //hideLoader();
					  //alert(data);
					  
					  var res = data.split('##@@');
					  
					  
					  
					  var indicator=res[0];
					  var serie1=res[1];
					  var serie2=res[2];
					  var serie3=res[3];
					  var label =res[4];
					  
					  
					  option = {
						    title: {
						        text: kpi
						    },
						    tooltip : {
						        trigger: 'axis',
						    },
						    legend: {
						        data:['Highest Store','Top 10','All Stores']
						    },
						    toolbox: {
						    	show : true,
						        showTitle: false,
						        
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '1%',
						        containLabel: true
						    },
						    xAxis : [
						        {
						        	axisLabel: { rotate: 60, },
						            type : 'category',
						            boundaryGap : false,
						            scale:true,
						            data : label.split(',')
						        }
						    ],
						    yAxis : [
						        {
						            type : 'value'
						        }
						    ],
						    series : [
						        {
						            name:'Highest Store',
						            type:'line',
						            //stack: 'a',
						            areaStyle: {normal: {}},
						            data: serie1.split(',')
						        },
						        {
						            name:'Top 10',
						            type:'line',
						            //stack: 'a',
						            areaStyle: {normal: {}},
						            data: serie2.split(',')
						        },
						        {
						            name:'All Stores',
						            type:'line',
						            //stack: 'a',
						            areaStyle: {normal: {}},
						            data: serie3.split(',')
						        },
						        
						    ]
						};

					  
					  //myChart.dispose();
						
					  myChart = echarts.init(document.getElementById('main'));
						
					  myChart.setOption(option);  
					    
					  
			      },
			      error: function () {
			      	//alert('Errore! si e\' verificato un errore.');
			      }
			});
			
		}
		
		hideLoader();
	}
	
	
	function getHeatMap(kpi, descr) {
		
		var idstores = $('#_idstores').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		
		myChart2.dispose();
		$('#main2').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		$.ajax({
		      url: '../ajax/getHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}&tipo=W&pag=D',
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  var res = data.split('##@@');
				  var max=res[0];
				  var heatmap=res[1];
				  var XA=res[2];
				  var YA=res[3];
				  
				  //alert(heatmap);
				  var array = JSON.parse(heatmap);
				  var xa2=XA.split(",");
				  var ya2=YA.split(",");
				  
				  option2 = {
				    		
				    tooltip: {
				        position: 'top'
				    },
				    animation: true,
				    grid: {
				        height: '70%',
				        y: '10%'
				    },
				    toolbox: {
				    	show : true,
				        showTitle: false,
				        
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        axisLabel: { rotate: 60, },
				        //data: ['00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'],
				        data: xa2,
				        splitArea: {
				            show: true
				        }
				    },
				    yAxis: {
				        type: 'category',
				        //data: ['Saturday','Friday','Thursday','Wednesday','Tuesday','Monday','Sunday'],
				        //data: ['<%=propLang.get("sab")%>','<%=propLang.get("ven")%>','<%=propLang.get("gio")%>','<%=propLang.get("mer")%>','<%=propLang.get("mar")%>','<%=propLang.get("lun")%>','<%=propLang.get("dom")%>'],
				        data: ya2,
				        splitArea: {
				            show: true
				        }
				    },
				    visualMap: {
				        min: 0,
				        max: max,
				        calculable: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        inRange: {
				        	color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
				    	}
				    },
				    series: [{
				        name: descr,
				        type: 'heatmap',
				        data: array,
				        label: {
				            normal: {
				                show: true
				            }
				        },
				        itemStyle: {
				            emphasis: {
				                shadowBlur: 10,
				                shadowColor: 'rgba(0, 0, 0, 0.5)'
				                
				            },
				            normal: {
			                    color: '#EE5154'
			                }
				        }
				    }]
				
			 		
			 	}
				
				myChart2 = echarts.init(document.getElementById('main2'));
				myChart2.setOption(option2);
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function setChart(type) {
		$('#chart').val(type);	
	}
	
	function formatDate(date) {
	    var d = new Date(date),
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear();

	    if (month.length < 2) month = '0' + month;
	    if (day.length < 2) day = '0' + day;

	    return [day, month, year].join('-');
	}
	
	function getDates(azione) {
		
		var tipo = $('#tipo').val();
		$('#tipoPeriod').val(tipo);
		
		var dtda = '';
		var dtal = '';
		
		if(tipo=='D') {
			dtda = $('#day').val();
			dtal = $('#day').val();
		}
		else if(tipo=='W') {
			
			var yearWeek = $('#yearWeek').val();
			var week = $('#week').val();
			
			var temp = week.split("#");
			dtda = temp[0];
			dtal = temp[1];
			
		}
		else if(tipo=='M') {
			
			var yearMonth = $('#yearMonth').val();
			var month = $('#month').val();
			
			//if(parseInt(month)==10||parseInt(month)==11||parseInt(month)==12)
			//	yearMonth = parseInt(yearMonth)-1;
			
			var firstDay = new Date(yearMonth, parseInt(month)-1, 1);
			var lastDay = new Date(yearMonth, parseInt(month), 0);
			
			dtda = formatDate(firstDay);
			dtal = formatDate(lastDay);
			
		}
		else if(tipo=='P') {
			
			var pref = $('#pref_period').val();
			$('#special').val(pref);
			var tipoNew = pref.substring(1,2);
			
			$('#tipo').val(tipoNew);
			$('#tipoPeriod').val(tipoNew);
			
			$.ajax({
			      url: '../ajax/getDates.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: false,
			      data: 'pref='+pref,
			      success: function (data) {
					  data = $.trim(data);
			    	  var temp = data.split('##@@');
			    	  
			    	  dtda = temp[0];
					  dtal = temp[1];
			    	  
			      },
			      error: function () {
			      	//alert('Errore! si e\' verificato un errore.');
			      }
			});
			
		}
		else if(tipo=='Y') {
			var anno = $('#year').val();
			
			// Get dtda & dtal da ajax 
			$.ajax({
			      url: '../ajax/getFinancialCalendar.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: false,
			      data: 'year='+anno,
			      success: function (data) {
					  data = $.trim(data);
			    	  var temp = data.split('##@@');
			    	  
			    	  dtda = temp[0];
					  dtal = temp[1];
			    	  
			      },
			      error: function () {
			      	//alert('Errore! si e\' verificato un errore.');
			      }
			});
			//dtda = "01-10-"+(parseInt(anno)-1);
			//dtal = "30-09-"+anno;
		}
		else if(tipo=='YW') {
			var anno = $('#year').val();
			
			// Get dtda & dtal da ajax 
			$.ajax({
			      url: '../ajax/getFinancialCalendar.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: false,
			      data: 'year='+anno,
			      success: function (data) {
					  data = $.trim(data);
			    	  var temp = data.split('##@@');
			    	  
			    	  dtda = temp[0];
					  dtal = temp[1];
			    	  
			      },
			      error: function () {
			      	//alert('Errore! si e\' verificato un errore.');
			      }
			});
			//dtda = "01-10-"+(parseInt(anno)-1);
			//dtal = "30-09-"+anno;
		}
		else if(tipo=='Q') {
			var anno = $('#yearQuarter').val();
			var quarter = $('#quarter').val();
			//var qua = quarter.replace('Q','');
			//$('#quarterForm').val(qua);
			
			
			
			var temp = quarter.split("#");
			var aada = parseInt(anno);
			var aaal = parseInt(anno);
			
			var da = temp[0];
			var al = temp[1];
			
			if(parseInt(al)<parseInt(da)) {
				aaal = aaal+1;
			}
			
			var d = new Date(aaal, ((parseInt(al))), 1);
			var n = d.toISOString();
			
			n = n.substring(0,10);
			var dd = (n.substring(8,10));
			var mm = (n.substring(5,7));
			var yy = (n.substring(0,4));
			
			//alert(dd+"-"+mm+"-"+yy);
			
			dtda = "01-"+da+"-"+aada;
			dtal = dd+"-"+mm+"-"+yy;
			
		}
		
		//alert(tipo+" "+dtda+" "+dtal);
		
		$('#dtda').val(dtda);
		$('#dtal').val(dtal);
		
		
		$('#sel_date').html(dtda+" / "+dtal);
		
		$('.modal').remove(); 
		$('div.modal-backdrop').remove();
		
		if(azione=='GO') {
			showLoader();
			$( "#deskForm" ).submit();
		}
		
	}
	
	function saveDates() {
		
		showLoader();
		
		getDates('');
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		var tipo = $('#tipo').val();
		var special = $('#special').val();
		
		//alert(idstores);
		
		$.ajax({
		      url: '../ajax/savePrefUserPeriod.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'dtda='+dtda+'&dtal='+dtal+'&tipo='+tipo+'&special='+special,
		      success: function (data) {
				//alert(data);
		    	  $( "#deskForm" ).submit();
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	
	var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		myChart.resize();
		myChart2.resize()
		//spark2();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	
	
	
	
	
	
	
//////////////////////////////////////////
  	
  	function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		 });
	}
  	
  	function getCategory() {
  		loadCategory('${idcus}');
  	}
  	
  	
	function loadCategory(idcus) {
		
		if(idcus!='') {
			console.log('idcus: '+idcus);
			console.log("../../servlet/ws/GetCustomersCategory?idcus="+idcus);
			
			$.ajax({ 
				url: "../../servlet/ws/GetCustomersCategory?idcus="+idcus,
			   	method:"POST",
			   	dataType: "json",       
			   	beforeSend: function( xhr ) {
			   	    showLoader2();
			   	},
			   	success: function(data)  
		   		{
			   		hideLoader2();
			   		$('#_catloader').hide();
		   			
			   		if(data.length>0) {
			   			$('#_cat').show();
			   			//console.log(JSON.stringify(data))
			   			var cat = data;
			   			$.each(cat, function(index, cat) {
			   				parseCategory(cat, idcus, '_catsel');
			   			});
			   			
			   		}
			   		
		   		}   
		 	});
		}
		else {
			console.log('idcus: '+idcus);
		}
	}
	
	function parseCategory(cat, idcus, _div) {
		
		var markup = '';
		var callfun = false;
		if(cat.value.length==0) {
			
			if(cat.nodes.length==0) {
				console.log("no value for "+cat.name+" in nodes");
			}
			else {
				
				console.log("aaaaa");
				markup = '<input type="hidden" name="_catt" value="'+cat.name+'"/><select id="_cat_'+cat.name+'" name="_cat_'+cat.name+'" class="ss form-control" onchange="checkNodes(this.value, \''+cat.name+'\',  \''+idcus+'\')">'
					+'<option value=""></option>';
					
				//var val = [];
				$.each(cat.nodes, function(index, node) {
					//val.push(node.name);
					markup+= '<option value="'+node.name+'">'+node.name+'</option>';
				});
				//console.log("value for "+cat.name+": "+JSON.stringify(val));
				markup+= '</select>';
				
				callfun = true;
			}
		}
		else {
			
			markup = '<input type="hidden" name="_cat" value="'+cat.name+'"/><select id="_cat_'+cat.name+'" name="_cat_'+cat.name+'" class="ss form-control" multiple onchange="selectStore(\'_cat_'+cat.name+'\',\''+cat.name+'\')">'
				//+'<option value="">-- All --</option>';
				'';
			
			$.each(cat.value, function(index, value) {
				markup+= '<option value="'+value+'">'+value+'</option>';
			});
			//console.log("value for "+cat.name+": "+JSON.stringify(val));
			markup+= '</select>';
			
			callfun = false;
			
			//console.log("value for "+cat.name+": "+JSON.stringify(cat.value));
		}
		
		markup='<div class="form-group pull-in clearfix"><div class="col-sm-12"><label>'+cat.name+'</label>'+markup+'</div></div>'
			+'<div id="_'+cat.name+'_parent"></div>';
		
		//$('#'+_div).html('');
		$('#'+_div).append(markup);
		
		
		if(callfun==true) {
			//alert("callfun");
			$("[id='_cat_"+cat.name+"']").on('change', function () {
		        var val = this.options[this.selectedIndex].value;
				//alert(val);
				
			});
		}	
		
		selpick();
		
		
		
	}
	
	function checkNodes(_category, _parent_category, _idcus) {
		
		//alert(_category+' '+_parent_category);
		
		if(_category=='') {
			
			//alert('a');
			
			$('#_'+_parent_category+'_parent').html('');
			
		}
		else {
		
		
		
		
			$.ajax({ 
				url: "../../servlet/ws/GetCustomersCategory2?idcus="+_idcus+"&category="+_category+"&parent_category="+_parent_category,
			   	method:"POST",
			   	dataType: "json",       
			   	beforeSend: function( xhr ) {
			   	    showLoader2();
			   	},
			   	success: function(data)  
		   		{
			   		hideLoader2();
		  			
			   		if(data.length>0) {
			   			$('#_cat').show();
			   			//console.log(JSON.stringify(data))
			   			var cat = data;
			   			$.each(cat, function(index, cat) {
			   				//parseCategory(cat);
			   				
			   				//alert(JSON.stringify(cat));
			   				
			   				$('#_'+_parent_category+'_parent').html('');
			   				//$('#_'+_parent_category+'_parent').append(JSON.stringify(cat));
			   				
			   				parseCategory(cat, _idcus, '_'+_parent_category+'_parent');
			   				
			   			});
			   			
			   		}
			   		
		   		}   
		 	});
			
		}
		
	}
	
	function getStoresFromCategory() {
		
		var _str = $( "#_catfrm" ).serialize();
		console.log("../../servlet/ws/GetStoreFromCategory?"+_str);
		
		$.ajax({
		      type: "POST",
		      url: "../../servlet/ws/GetStoreFromCategory",
		      data: _str,
		      dataType: "json",
		      success: function(result)
		      {
		         //alert(JSON.stringify(result));
		    	
		         uncheckAll();
		         
		         var ss = result.stores.split(',');
		         
		         if(ss[0]!='') {
		         	 $('#modal-nrstores').html('('+ss.length+' Stores selected)');
		         }
		         else {
		        	 $('#modal-nrstores').html('(0 Stores selected)');
		         }
		         
		       	 $('#idstores').val(result.stores);
				 $('#group').val('4');
					
		         
				 
				/* 
				var search = function(e) {
				  var pattern = $('#input-search').val();
		          var options = {
		            ignoreCase: true,
		            exactMatch: false,
		            revealResults: true
		          };
		          var results = $searchableTree.treeview('search', [ pattern, options ]);
		        }
				*/
			    var treeViewObject = $('#tree').data('treeview'),
			    allCollapsedNodes = treeViewObject.getCollapsed(),
			    allExpandedNodes = treeViewObject.getExpanded(),
			    allNodes = allCollapsedNodes.concat(allExpandedNodes);
				
				
				
				
				for(var s = 0; s < ss.length; s++) {
					for (var i = 0; i < allNodes.length; i++) {
				        if (allNodes[i].id != ss[s]) continue;
				        //treeViewObject.selectNode(allNodes[i].nodeId);
				        treeViewObject.checkNode(allNodes[i].nodeId);
				        
				    }
				}
				
		         
		         
				
				//$('#idstores').val(result.stores);
				//$('#group').val('4');
				//$( "#deskForm" ).submit();
				
				
				
				 
		      },
		      error: function()
		      {
		        alert("Chiamata fallita, si prega di riprovare...");
		      }
		});
	}
	
	function getNodes2() {
		getStoresFromCategory();
	}
	
	function selectStore(sel, cat) {
		
		
		var selval = $('#'+sel).val();
		
		console.log(cat+': '+$('#'+sel).val());
		
		getStoresFromCategory();
		
	}
	
	////////////////////////////
  </script> 
  
  
  
  
  
  
	
  <%@ include file="../../inc/autorized.jsp" %>
  
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
  		
  		showLoader2();
  		
  		var idcus = $('#'+id).val();
  		$('#idcus_frm').val(idcus);
  		
  		$('#chgUser').submit();
  	}
  	
  	
  	
  	
  	
  	
	
  </script>
  
</body>
</html>
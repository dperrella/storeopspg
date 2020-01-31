<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
              <%@ include file="../../inc2/menuApp12.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<div class="row" id="div" style="opacity: 0.8; filter: alpha(opacity=50);
                  		z-index: 0; width:100%; position:absolute; background:#fff;margin-left: 0px;">
	              			
		              		<div class="col-sm-4 b-b b-2x">
		                      <div class="wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/sel_step.jsp" data-toggle="ajaxModal">
		                          <span class="h4 block"><i class="icon-puzzle i-lg"></i> WHAT?</span></a>
		                          <div class="h3 m-t-xs block text-ellipsis" id="sel_kpi">${nrstep} Selected</div>
		                      </div>
		                    </div>
			                    
		              		<div class="col-sm-4 b-b b-2x">
		                      <div class=" wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/sites.jsp?nrstores=${nrstores}" data-toggle="ajaxModal">
		                        	<span class="h4 block text-ellipsis"><i class="icon-map i-lg"></i> WHERE? <%=session.getAttribute("dscus") %></span></a>
		                        	<div class="h3 m-t-xs block text-ellipsis" id="sel_stores2">${nrsites} Selected</div>
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
	                      
	                      <input type="hidden" name="idsites" id="idsites" value="${idsites}"/>
	                      <input type="hidden" name="tipoPeriod" id="tipoPeriod" value="${tipoPeriod}"/>
	                      <input type="hidden" name="tipoPeriod2" id="tipo" value="Y"/>
	                      <input type="hidden" name="dtda" id="dtda" value="${dtda}"/>
	                      <input type="hidden" name="dtal" id="dtal" value="${dtal}"/>
	                      <input type="hidden" name="step" id="step" value="${step}"/>
	                      <input type="hidden" name="quarter" id="quarterForm" value="${quarter}"/>
	                      <input type="hidden" name="special" id="special" value=""/>
	                      
	                </form>
              		<c:if test = "${nrstep==0}">
              		   	<div class="row row-sm">
							<div class="col-xs-12">
		                    	
		                    	
		                    	<div class="text-center m-b-lg">
									<h1 class="fontBig text-white animated"><%=propLang.get("nostep") %></h1>
									<h3><%=propLang.get("nostep2") %></h3>
								</div>
								
								
							</div>
						</div>
					</c:if>
					<c:if test = "${nrstep>0}">
	                    <div class="row row-sm">
								
						  	<div class="col-xs-12">
							  
							  <!-- 
							  <div class="alert alert-info">
			                    <button type="button" class="close" data-dismiss="alert">×</button>
			                    <i class="fa fa-info-sign"></i><strong>Heads up!</strong> This <a href="#" class="alert-link">alert needs your attention</a>, but it's not super important.
			                  </div>
			                  -->
			                  
							  <div class="row m-t-md m-b-md text-center">
								
								<div class="col-sm-4">
			                      <div class="bg-primary lter wrapper-md r">
			                        <a href="../popup/sel_step.jsp" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-puzzle i-lg"></i> WHAT?</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_kpi2">${nrstep} Selected</div>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-4">
			                      <div class="bg-black wrapper-md r">
			                        <a href="../popup/sites.jsp?nrsites=${nrsites}"  data-toggle="ajaxModal">
			                        	<span class="h4 block"><i class="icon-map i-lg"></i> WHERE? <%=session.getAttribute("dscus") %></span></a>
			                        	<div class="h2 m-t-xs block text-ellipsis" id="sel_stores">${nrsites} Selected</div>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-4">
			                      <div class="bg-light dker wrapper-md r">
			                        <a href="../popup/sel_period.jsp" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-calendar i-lg"></i> WHEN?</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_date">${dates}</div>
			                      </div>
			                    </div>
			                    
			                    <!-- 
			                    <div class="col-sm-3">
			                      <div class="bg-info lter wrapper-md r">
			                        <a href="../../smartrfid.apk" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-screen-smartphone i-lg"></i> MOBILE APP</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_date">Download APP</div>
			                      </div>
			                    </div>
			                    -->
			                    
			                  </div>
			                  
			                  <div id="msg">${msg}</div>
			                  
			                  
			                  <!-- Tiles -->
			                  <div class="row row-sm">
			                  
			                  	<c:forEach items="${steplist}" var="steplist" varStatus="status">
			                  		<div class="col-xs-12 col-md-6 ${col}">
			                      	    <section class="panel panel-default ">
			                            <div id="panel_${steplist.xcdel}" class="panel-body ${steplist.cmp3} text-center">
			                              
			                              <h5 class="text-ellipsis" 
			                                data-toggle="popover" data-html="true" data-placement="bottom" data-content="<div class=&quot;h3&quot;>${steplist.cmp1}</div>" data-trigger="hover" title="" data-original-title="<button type=&quot;button&quot; class=&quot;close pull-right&quot; data-dismiss=&quot;popover&quot;>&times;</button>Real Value ">${steplist.xdtab}</h5>
			                              <span id="trend_${steplist.xcdel}" class="m-t-xs pull-right"></span>
			                              <span class="h1 step"><a href="#" onclick="changeStep('${steplist.xcdel}','${steplist.xdtab}')" data-toggle="tab">${steplist.cmp1}</a></span>
			                              
			                              
			                            </div>
			                            
			                            <div class="tiles_loader ${steplist.cmp3} text-center" id="tiles_${steplist.xcdel}">
			                              	<img src="../../images/loader_xs.gif"/>
			                            </div>
			                              
			                            </section>
			                            
			                        </div>
			                  	</c:forEach>
			                  
			                  </div>
			                  
			                  <!-- Stock -->
			                  <div class="row">
			                    <div class="col-sm-12">
			                    	
			                      <section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      <span class="text-muted m-r-sm"><i class="icon-social-dropbox"></i></span>
				                      <span class="hidden-sm"><b>Stock</b></span>
				                    </header>
				                    <div class="panel-body">
				                    	
				                    	<div class="row">
				                    		<div class="col-sm-3">
				                    			<div id="stockpie" style="height:250px;">
						                    		<h3>LOADINGGGGG</h3>
						                    	</div>
				                    		</div>
				                    		<div class="col-sm-9">
				                    			<div id="stock" style="height:250px;">
						                    		<h3>LOADINGGGGG</h3>
						                    	</div>
				                    		</div>
				                    		
				                    	</div>
				                    	
				                    </div>
				                  </section>
				                  
			                    </div>
			                  </div>
			                  <!-- Table e Chart -->
			                  <div class="row">
							
			                    <div class="col-sm-6">
			                      	<section class="panel panel-default">
					                    <header class="panel-heading bg-light">
					                      
					                      <ul class="nav nav-tabs nav-justified">
					                      <c:forEach items="${steplist}" var="steplist" varStatus="status">
			                  				<li class="${steplist.cmp4}"><a href="#${steplist.xcdel}" data-toggle="tab" class="text-ellipsis"
					                        	onclick="changeStep('${steplist.xcdel}','${steplist.xdtab}')" >
					                        	<c:if test="${fn:length(steplist.xdtab) > 10}">
					                        	<span data-toggle="tooltip" data-container="body" data-placement="top" title="${steplist.xdtab}">
					                        	${fn:substring(steplist.xdtab, 0, 10)}...
					                        	</span>
					                        	</c:if>
					                        	<c:if test="${fn:length(steplist.xdtab) <= 10}">
					                        	${steplist.xdtab}
					                        	</c:if>
					                        	</a>
					                        </li>
					                      </c:forEach>          
					                      </ul>
					                    </header>
					                    <div class="tab-content">
					                      <c:forEach items="${steplist}" var="tablist" varStatus="status">
					                      	
					                      	<div class="tab-pane ${tablist.cmp4}" id="${tablist.xcdel}">
					                        	
					                        	<header class="panel-heading bg-light" style="background-color:#fff">
						                          <div class="clearfix">
						                            <div class="clear">
						                              <div class="h3 m-t-xs m-b-xs">
						                                Last 10 Docs ${tablist.xdtab} 
						                                <i class="fa fa-circle pull-right text-xs m-t-xs" style="color:${tablist.xcdtb}"></i>
						                              </div>
						                            </div>
						                          </div>
						                        </header>
					                        	
					                        	<div class="table-responsive">
									                <table class="table table-striped b-t m-b-none" id="vistable" data-ride="datatables">
									                  <thead>
														  <tr>
													        <th width="5%">No.</th>
													        <th width="5%">Status</th>
													        <th width="25%">Document</th>
													        <th width="15%">Date</th>
													        <th width="15%">Time</th>
													        <th width="10%">Exp</th>
													        <th width="10%">Read</th>
													        <th></th>
													      </tr>
													  </thead>
							      					  
							      					  <tbody>
													  	${tablist.cmp2}
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
				                    
				                    	<div id="main2" style="height:500px;"></div>
				                     
				                    </div>
				                </section>
						  	
						  	</div>
						</div>
						
						<c:if test="${num > 0}">
						
						<div class="row row-sm">
							<div class="col-xs-12">
							
								<section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      <span class="text-muted m-r-sm"><i class="icon-info"></i></span>
				                      <span class="hidden-sm"><b>Doc Analysis</b></span>
				                    </header>
				                    
				                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="example2" data-ride="datatables">
					                  <thead>
										  <tr>
								      		  <th>ID Doc</th>
								      		  <th></th>
								      		  <th>Doc Date</th>
										      <th>Doc Ref</th>
										      <th>Start</th>
										      <th>End</th>
										      <th>Elaboration Time</th>
										      <th>Pieces</th>
										      <th>Average Time</th>
								    	  </tr>
									  </thead>
			      					  
			      					  <tbody>
									  <c:forEach var="ris" items="${ris}" varStatus="status">
							            <tr>
							            	<td><span class="label bg-light">${ris.xcdtb}</span></td>
							            	<td>
							            	<c:choose>
									    		<c:when test="${ris.cmp8 == '0'}">
									    			-
									  			</c:when>
									  			<c:otherwise>
									  				<a href="doc-analysis.jsp?iddoc=${ris.xcdtb}&doc_ref=${ris.cmp2}" data-toggle="ajaxModal" class="btn btn-default"><i class="fa fa-info-circle"></i></a>
							               	</c:otherwise>
											</c:choose>
							            	<td>${ris.cmp1}</td>
							                <td><span class="label bg-light">${ris.cmp2}</span></td>
							                <td>${ris.cmp3}</td>
							                <td>${ris.cmp4}</td>
							                <td>${ris.cmp5}</td>
							                <td><b>${ris.cmp6}</b></td>
							                <td>${ris.cmp7}</td>
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
					                      <small class="text-muted inline m-t-sm m-b-sm">Last ${num} Records Show</small>
					                    </div>
					                    <div class="col-sm-4 text-right text-center-xs">                
					                      
					                    </div>
					                  </div>
					              </footer>
				  				  
				                </section>
						  	
						  	</div>
						</div>
						
						</c:if>
						
						</c:if>
						
						<c:if test="${showTransact != ''}">
						
						<div class="row row-sm">
							<div class="col-xs-12">
						
								<section class="panel panel-default">
					              <header class="panel-heading font-bold">
					                <span class="text-muted m-r-sm"><i class="fa fa-cloud-upload"></i></span>
				                    <span class="hidden-sm"><b>Last Transact Log</b></span>
				                    
				                    
					                <a href="../transact/import-log.htm" class="btn btn-default btn-xs m-l-sm pull-right" >
			                            <i class="fa fa-table"></i>
			                            Go to TransACT Import Products
			                        </a>
					              </header>
					              
					              
					              <c:if test="${tot > 0}">
					             
					              <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
					                  <thead>
										  <tr>
								      		  <th>File name</th>
										      <th>File length</th>
										      <th>Import Date</th>
										      <th>Elaboration Status</th>
										      <th>Elaboration Date</th>
										      <th>Error</th>
										      <th>Elaboration Result</th>
								    	  </tr>
									  </thead>
			      					  
			      					  <tbody>
									  <c:forEach var="log" items="${logList}" varStatus="status">
							            <tr>
							                <td><b>${log.filename}</b></td>
							                <td>${log.size}</td>
							                <td>${log.importTimestamp}</td>
							                <td><span class="label bg-light">
							                	<c:choose>
										    		<c:when test="${log.elaborated == true}">
										    			Worked
										  			</c:when>
										  			<c:otherwise>
										  				Not Worked
										  			</c:otherwise>
												</c:choose>
												</span>
											</td>
							                <td>${log.elaborationTimestamp}</td>
							                <td>
							                	<c:choose>
										    		<c:when test="${log.error == true}">
										    			In Error
										  			</c:when>
										  			<c:otherwise>
										  				No Error
										  			</c:otherwise>
												</c:choose>
							                </td>
							                <td>${log.elaborationResult}</td>
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
					                      <small class="text-muted inline m-t-sm m-b-sm">Last ${tot} Records Show</small>
					                    </div>
					                    <div class="col-sm-4 text-right text-center-xs">                
					                      
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
					                      <a href="../transact/import-log.htm" onclick="showLoader2()" class="btn btn-success btn-lg">Go to TransACT Connect</a> 
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
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
   <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  
  <!-- Sparkline Chart -->
  <script src="../../js/charts/sparkline/jquery.sparkline.min.js"></script>
  
  <!-- Treeview -->
  <script src="../../js/bootstrap-treeview/bootstrap-treeview.min.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  
  <script>
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();
		<c:if test = "${idcus!=''}">
	    <c:if test = "${nrstep>0}">
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
	
	function setSelectPicker() {
		$('.selectpicker').selectpicker();
	}
	
	var table = $('#example2').dataTable( {
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
	  } );
	
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
	
	function loadTiles() {
		
		var datastring = $("#deskForm").serialize();
		
		//alert(datastring);
		
		$.ajax({
		      url: '../ajax/getTilesChart.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
					//alert(data);
					
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
	
	function convertHex(hex,opacity){
        hex = hex.replace('#','');
        r = parseInt(hex.substring(0,2), 16);
        g = parseInt(hex.substring(2,4), 16);
        b = parseInt(hex.substring(4,6), 16);

        result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
        
        //alert(result);
        
        return result;
    }
	
	// STOCK
	// PIE
	var myChart4 = echarts.init(document.getElementById('stockpie'));
	
	// LINECHART
	var myChart3 = echarts.init(document.getElementById('stock'));
	
	
	// LINECHART
	var myChart = echarts.init(document.getElementById('main'));
	
	
	
	// HEATMAP
    var myChart2 = echarts.init(document.getElementById('main2'));
	
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
	
	function getSites() {
		
		var num = 0;
		var $searchableTree;
		
		$('#tree').html('<div align="center">Please wait...<br/><img src="../../images/loading_2.gif"/></div>');
		
		$.getJSON("../../servlet/ws/GetSitesNodes?username=${username}&idsites=${idstores}&idcus=${idcus}", function(result){
			
			$('#tree').html('');
			
			//alert(JSON.stringify(result));
			
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
			        
			        $('#modal-nrsites').html('Send request to recalculate Sites');
			    });
				
			}).on('nodeUnchecked', function (event, node){
				//alert(JSON.stringify(node));
				var childrenNodes = _getChildren(node);
				//alert(JSON.stringify(childrenNodes));
				$(childrenNodes).each(function(){
			        $('#tree').treeview('uncheckNode', [ this.nodeId, { silent: true } ]);;
			        //num-=1;
			        
			        $('#modal-nrsites').html('Send request to recalculate Sites');
			    });
			});
			
			$('#btn-uncheck').prop("disabled", false);
			
		});
		
		
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
		var idsites = '';
		for (var i=0;i<json.length;++i)
	    {
			if(json[i].type=='SITE') {
				num+=1;
	        	//$('#results').append('<div class="name">'+json[i].type+' '+json[i].id+'</div>');
	        	idsites+=json[i].id+',';
			}
	    }
		idsites=idsites.slice(0, -1);
		$('#idsites').val(idsites);
		$('#sel_sites').html(num+" Selected");
		
		$('.modal').remove(); 
		$('div.modal-backdrop').remove();
		
		if(azione=='GO') {
			showLoader();
			$( "#deskForm" ).submit();
		}
		
	}
	
	function saveNodes() {
		
		showLoader();
		getNodes('');
		var idsites = $('#idsites').val();
		
		$.ajax({
		      url: '../ajax/savePrefUserSites.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'idsites='+idsites,
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
		$('#modal-nrsites').html('(0 Stores selected)');
		
	}
	
	function getStep(azione) {
		
		var step = "";
		var num = 0;
		$('input.step:checkbox:checked').each(function () {
			num+=1;
			step+=$(this).val()+",";
		});
		
		if(num>5) {
			alert('Select max 5 STEPS.');
			return;
		}
		else {
			step=step.slice(0, -1);
			$('#step').val(step);
			$('#sel_step').html(num+" Selected");
			
			if(azione=='GO') {
				//alert(kpi);
				showLoader();
				submitForm();
			}
		}
		
	}
	
	function saveStep() {
		
		showLoader();
		getStep('');
		var step = $('#step').val();
		
		$.ajax({
		      url: '../ajax/savePrefUserStep.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'step='+step,
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
	
	function callReport(idsite) {
		
		$('#idsites').val(idsite);
		
		showLoader();
		$( "#deskForm").attr('action', 'index-report-site.htm');
		$( "#deskForm" ).submit();
		
	}
	
	function changeStep(step, stepdesc) {
		
		<c:forEach items="${steplist}" var="steplist" varStatus="status">
			$( '#panel_${steplist.xcdel}' ).removeClass( 'bg-light dk' );
			$( '#tiles_${steplist.xcdel}' ).removeClass( 'bg-light dk' );
		</c:forEach>
		$( '#panel_'+step ).addClass( 'bg-light dk' );
		$( '#tiles_'+step ).addClass( 'bg-light dk' );
		$('.nav-tabs a[href=#'+step+']').tab('show');
		getHeatMap(step, stepdesc);
		getRadar(step);
	}
	
	getRadar('${step_selected}');
	getHeatMap('${step_selected}','${step_desc_selected}');
	getStock();
	getStockpie();
	
	
	function getRadar(step) {
		
		
		
		var idsites = $('#idsites').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		var steplist = $('#step').val();
		
		//alert(idstores+" "+kpi+" "+kpilist);
		
		myChart.dispose();
		$('#main').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		//myChart = echarts.init(document.getElementById('main'));
						
		
			
		var tipo = $('#tipoPeriod').val();
		
		$.ajax({
		      url: '../ajax/getArea.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'step_selected='+step+'&step='+steplist+'&idsites='+idsites+'&dtda='+dtda+'&dtal='+dtal+'&tipo='+tipo,
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  var res = data.split('##@@');
				  
				  //alert(res);
				  
				  var serie=res[0];
				  var label=res[1];
				  var color=res[2];
				  
				  option = {
						color: [color],
					    title: {
					        text: 'Documents'
					    },
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            
					            type : 'shadow'      
					        }
					    },
					    legend: {
					        data:['Docs in '+dtda+' '+dtal+'range']
					    },
					    toolbox: {
					        show : true,
					        showTitle: false,
					        feature : {
					        	
					        	//dataView : {show: true, readOnly: false, title: 'Data View'},
					            magicType : {show: true, type: ['bar', 'line']},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    /*
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '1%',
					        containLabel: true
					    },
					    */
					    xAxis : [
					        {
					        	axisLabel: { rotate: 60, },
					            type : 'category',
					            //boundaryGap : false,
					            //scale:true,
					            data : label.split(',')
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					    	/*
					        {
					            name:'Docs in '+dtda+' '+dtal+' range',
					            type:'bar',
					            //stack: 'a',
					            areaStyle: {normal: {}},
					            data: serie.split(',')
					        }
					        */
					        {
					            name:'Docs in '+dtda+' '+dtal+' range',
					            type:'bar',
					            data:serie.split(','),
					            markLine : {
					                data : [
					                    {type : 'max', name: 'Max'}
					                ]
					            },
					            itemStyle: {
					                
					                emphasis: {
					        	        barBorderWidth: 1,
					        	        shadowBlur: 10,
					        	        shadowOffsetX: 0,
					        	        shadowOffsetY: 0,
					        	        shadowColor: 'rgba(0,0,0,0.5)'
					        	    }
					            },
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
			
		
		hideLoader();
	}
	
	function getHeatMap(step, descr) {
		
		var idsites = $('#idsites').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		
		myChart2.dispose();
		$('#main2').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		$.ajax({
		      url: '../ajax/getHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'step='+step+'&idsites='+idsites+'&dtda='+dtda+'&dtal='+dtal+'&pag=D',
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
				    xAxis: {
				        type: 'category',
				        axisLabel: { rotate: 60, },
				        data: xa2,
				        splitArea: {
				            show: true
				        }
				    },
				    yAxis: {
				        type: 'category',
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
				
				
				//alert(JSON.stringify(option2));
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
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
		myChart2.resize();
		myChart3.resize();
		myChart4.resize();
		
		
		//spark2();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	
	function viewDoc(val) {
		
		showLoader2();
		location.href="../docs/documents-dett.htm?iddoc="+val;
		
	}
	
	
	
	
	
	function getStock() {
		
		var idsites = $('#idsites').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		
		//alert(idstores+" "+kpi+" "+kpilist);
		
		myChart3.dispose();
		$('#stock').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		//myChart = echarts.init(document.getElementById('main'));
				
		$.ajax({
		      url: '../ajax/getStock.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'idsites='+idsites+'&dtda='+dtda+'&dtal='+dtal,
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  data = $.trim(data);
				  
				  var res = data.split('##@@');
				  
				  //alert(res);
				  
				  var legend=res[0].split(',');
				  var xAxis=res[1].split(',');
				  var ser=JSON.parse(res[2]);
				  
				  
				  option = {
					    color: ['#ED6F70', '#343A40', '#30C5AB', '#4B9AE5', '#FDBB44', '#7465EB', '#6C757C'],
					    title: {
					        text: 'Stock'
					    },
					    tooltip: {
					        trigger: 'axis'
					    },
					    legend: {
					        data:legend
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    toolbox: {
					        feature: {
					            saveAsImage: {}
					        }
					    },
					    xAxis: {
					        type: 'category',
					        boundaryGap: false,
					        data: xAxis,
					       
					    },
					    yAxis: {
					        type: 'value'
					    },
					    series: ser
				  };
				  
				  myChart3 = echarts.init(document.getElementById('stock'));
				  myChart3.setOption(option);  
				    
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
			
		
		hideLoader();
	}
	
	function getStockpie() {
		
		var idsites = $('#idsites').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		
		//alert(idstores+" "+kpi+" "+kpilist);
		
		myChart4.dispose();
		$('#stock').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		//myChart = echarts.init(document.getElementById('main'));
				
		$.ajax({
		      url: '../ajax/getStockPie.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'idsites='+idsites+'&dtda='+dtda+'&dtal='+dtal,
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  data = $.trim(data);
				  
				  var res = data.split('##@@');
				  
				  
				  var legend=res[0].split(',');
				  
				  
				  
				  var ser=JSON.parse(res[1]);
				  
				  option = {
				    color: ['#ED6F70', '#343A40', '#30C5AB', '#4B9AE5', '#FDBB44', '#7465EB', '#6C757C'],
				    title: {
				        text: 'Current Stock'
				    },
				    tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
				    legend: {
				        orient: 'horizontal',
				        x: 'center',
				        y: 'bottom',
				        data:legend,
				        type: 'scroll'
				    },
				    series: [
				        {
				            name:'Current Stock',
				            type:'pie',
				            radius: ['50%', '75%'],
				            avoidLabelOverlap: false,
				            label: {
				                normal: {
				                    show: false,
				                    position: 'center'
				                },
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '20',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            },
				            data:ser
				        }
				    ]
				  };
				  
				  //alert(JSON.stringify(option));
				  
				  myChart4 = echarts.init(document.getElementById('stockpie'));
				  myChart4.setOption(option);  
				    
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
			
		
		hideLoader();
	}
	
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
  		
  		var idcus = $('#'+id).val();
  		$('#idcus_frm').val(idcus);
  		
  		$('#chgUser').submit();
  	}
  	
	
  </script>
  
  </body>
</html>
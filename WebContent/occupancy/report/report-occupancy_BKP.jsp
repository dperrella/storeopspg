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
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
	.fixed {
	    position: absolute;
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
              
              <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b><%=propLang.get("pop_us_sel") %> <%=propLang.get("sm_rol_4") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                	<li class="list-group-item">
		                		<a href="report-counter-modal.jsp?idstores=${idstores}&tipo=D"
		                		class="getModal" data-toggle="modal">
		                		<i class="fa fa-sort-numeric-asc"></i> <%=propLang.get("tipo_day") %></a></li>
	                        
	                        <!-- 
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal.jsp?idstores=${idstores}&tipo=W"
		                		class="getModal" data-toggle="modal">
	                        	<i class="fa fa-list-ol"></i> <%=propLang.get("tipo_week") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal.jsp?idstores=${idstores}&tipo=M"
		                		class="getModal" data-toggle="modal">
	                        	<i class="icon-calendar"></i> <%=propLang.get("tipo_month") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal.jsp?idstores=${idstores}&tipo=P"
		                		class="getModal" data-toggle="modal">
	                        	<i class="fa fa-sort-alpha-asc"></i> <%=propLang.get("tipo_period") %></a></li>
	                        -->
	                    </ul>
                        
                      </div>
                    </section>
                    
                  </section>
                </aside>
                <!-- / side content -->
              
              <section>
                  <section class="vbox">
              
               		
	              	
                    <section class="scrollable padder-lg" id="scrollable">
              			
              			<form action="index-occupancy.htm" id="deskForm" name="deskForm" method="post">
	                      <input type="hidden" name="kpi" id="kpi" value="${kpi}"/>
	                      <input type="hidden" name="idstores" id="idstores" value="${idstores}"/>
	                      <input type="hidden" name="tipoPeriod" id="tipoPeriod" value="${tipoPeriod}"/>
	                      <input type="hidden" name="tipoPeriod2" id="tipo" value="Y"/>
	                      <input type="hidden" name="dtda" id="dtda" value="${dtda}"/>
	                      <input type="hidden" name="dtal" id="dtal" value="${dtal}"/>
	                      <input type="hidden" name="quarter" id="quarterForm" value="${quarter}"/>
	                      <input type="hidden" name="comparison" id="comparison" value="${comparison}"/>
	                      <input type="hidden" name="group" id="group" value="${group}"/>
	                      <input type="hidden" name="tipo_chart" id="tipo_chart" value="${tipo_chart}"/>
	                      <input type="hidden" name="special" id="special" value=""/>
	                      <input type="hidden" name="upd" id="upd" value=""/>
	                      <input type="hidden" name="id_advanced" id="id_advanced" value=""/>
	                      <input type="hidden" name="pag" id="pag" value="${pag}"/>
	                      <input type="hidden" name="nrPag" id="nrPag" value="${nrPag}"/>
	                      <input type="hidden" name="nrRecord" id="nrRecord" value="${nrRecord}"/>
	                      <input type="hidden" name="numRow" id="numRow" value="${numRow}"/>
	                      
	                      <input type="hidden" name="kpi_selected" id="kpi_selected" value="${kpi_selected}"/>
	                      <input type="hidden" name="uid" id="uid" value="${uid}"/>
	                      
	                      <input type="hidden" name="uid2" id="uid2" value="${uid2}"/>
	                      
	                      <input type="hidden" name="iddevice" id="iddevice" value="		"/>
	                      
	                    </form>
	                    
	                    <c:if test = "${idcus!=''}">
	                    
	                    <h2 class="font-thin m-b-none">Report Smart Occupancy ${dsstore}</h2>
                      
	                    <c:if test = "${dtda != dtal}">
	                    <small>Period: ${dtda} - ${dtal}</small>  
	                    </c:if>
	                    <c:if test = "${dtda == dtal}">
	                    <small>Date: ${dtda}</small>  
	                    </c:if>
	                    
	              		<div class="row row-sm m-t" id="corpo">
								
						  	<div class="col-xs-12">
							  
			                  <!-- Temperature Control -->
			                  <section class="panel panel-default" id="panelChart">
			                    <header class="panel-heading bg-light">
			                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
			                      <span class="hidden-sm"><b>Occupancy Chart</b></span>
			                    </header>
			                    <div class="panel-body">
			                    	
			                    	<!-- 
			                     	<div class="line line-dashed b-b line-lg pull-in"></div>
							    	-->
							    	
							    	<div id="ajaxChart2">
							    	<div class="row">
							    		
							    		<div class="col-sm-12">
							    			
							    			<div id="tc" style="height:450px;"></div>
			                    			
							    		</div>
							    		
							    	</div>
							    	</div>
							    	
							    	
							    	<div class="row">
							    		
							    		<div class="col-sm-2">
							    			
							    			<select id="select_device" name="select_device" style="margin-top:4px" class="form-control ss" onchange="changeDevice(this.value)">
						            			<option value="STORE TOTAL">Store Total</option>
						            			<c:forEach items="${dev}" var="dev" varStatus="status">
						            			<option value="${dev.xcdel}">${dev.xdtab}</option>
						            			</c:forEach>
						            		</select>
							    		</div>
							    		<div class="col-sm-10" align="right" id="exportButtonDiv">
							    			
							    			<a href="#" onclick="excelExport2()" class="btn btn-lg btn-default btn-success"><i class="fa fa-table"></i> Excel Export</a>
							    			
							    		</div>
							    	</div>
							    	
							   	</div>
							   	
							   	<div id="table_wrapper3" class="table-responsive horizontal">
							   	<table id="tableData2" class="table table-striped b-t-none b-b b-light">
				               		<thead>
				               			<tr>
				               				${th}
				               			</tr>
				               		</thead>
				               		<tbody>
				               		
				               		</tbody>
					           	</table>
					           	</div>	
							  </section>
			                  
			                  
			                  <!-- Tiles -->
				              <div class="row row-sm">
				                  
			                  	<c:forEach items="${kpilist}" var="kpilist" varStatus="status">
			                  		<div class="col-xs-12 col-md-6 ${col}">
			                      	    <section class="panel panel-default ">
			                            <div id="panel_${kpilist.xcdel}" class="panel-body ${kpilist.cmp3} text-center">
			                              
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
			                  
			                  
			                  <section class="panel panel-default" id="panelChart">
			                    <header class="panel-heading bg-light">
			                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
			                      <span class="hidden-sm"><b>Chart - ${dsstore}</b></span>
			                    </header>
			                    <div class="panel-body">
			                    	
			                    	<!-- 
			                     	<div class="line line-dashed b-b line-lg pull-in"></div>
							    	-->
							    	
							    	<div id="ajaxChart">
							    	<div class="row">
							    		
							    		<div class="col-sm-${chart_col}">
							    			
							    			<div id="main" style="height:450px;"></div>
			                    	
							    			
							    		</div>
							    		<c:if test = "${nrdev > 1}">
							    		<div class="col-sm-4">
							    			
							    			<div id="pie" style="height:450px;"></div>
							    	
							    		</div>
							    		</c:if>
							    	</div>
							    	</div>
							    	
							    	
							   	</div>
							  </section>
							  
							
							  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
			                      <span class="hidden-sm"><b>HeatMap - ${dsstore}</b></span>
			                    </header>
			                    <div class="panel-body">
			                    
			                    	<div id="main2" style="height:${heatmapHeight}px;"></div>
			                     
			                    </div>
				              </section>
						  	  
						  	  <!-- 
							  <h3 class="font-thin">${dsstore}</h3>
							  -->
							  
							  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <span class="text-muted m-r-sm"><i class="fa fa-table"></i></span>
			                      <span class="hidden-sm"><b>Data - ${dsstore}</b></span>
			                    </header>
			                    <div class="panel-body">
							  
							    	<div class="row">
							    		
							    		<div class="col-sm-2">
							    			
							    			<select id="select_kpi" name="select_kpi" style="margin-top:4px" class="form-control ss" onchange="changeKpi(this.value)">
						            			<c:forEach items="${kpilist}" var="kpilist" varStatus="status">
						            			<option value="${kpilist.xcdel}">${kpilist.xdtab}</option>
						            			</c:forEach>
						            		</select>
							    		</div>
							    		<div class="col-sm-10" align="right" id="exportButtonDiv">
							    			
							    			<!-- 
							    			<a href="#" onclick="Stampa()" class="btn btn-lg btn-default btn-primary"><i class="fa fa-print"></i> Stampa</a>
							    			-->
							    			
							    			<a href="#" onclick="excelExport()" class="btn btn-lg btn-default btn-success"><i class="fa fa-table"></i> Excel Export</a>
							    			
							    		</div>
							    	</div>
				               		
				            		
				               	</div>
				               	
				               	
				               	
				               	
				               	<div id="table_wrapper2" class="table-responsive horizontal">
				               		<table id="tableData" class="table table-striped b-t-none b-b b-light">
				               		<thead>
				               		
				               			${total}
				               			
				               			<tr>
				               				${th}
				               			</tr>
				               		</thead>
				               		<tbody>
				               		<!--  Periodo -->  
									<c:forEach items="${sto}" var="sto" varStatus="status">
										${sto.xdtab}
									</c:forEach>
					           	    </tbody>
					           	    </table>
					           	    
				           		</div>
				           		
				           	</section>
			            
			                </div>
				                
			            </div>
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
  
  <form id="chgUser" action="../main/index.htm" method="post">
  	<input type="hidden" name="idcus" id="idcus_frm"/>
  </form>
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Filter list -->
  <script src="../../js/jquery.fastLiveFilter.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  
  
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
  <!--  modal -->
  <script src="../../js/bootstrap-modal.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  
  <!-- Sparkline Chart -->
  <script src="../../js/charts/sparkline/jquery.sparkline.min.js"></script>
  
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Treeview -->
  <script src="../../js/bootstrap-treeview/bootstrap-treeview.min.js"></script>
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js?v=2"></script>
  
  <script type="text/javascript" src="xlsx/shim.min.js"></script>
  <script type="text/javascript" src="xlsx/xlsx.full.min.js"></script>
  <script type="text/javascript" src="xlsx/Blob.js"></script>
  <script type="text/javascript" src="xlsx/FileSaver.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <script>
  
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
	
	selpick();
	
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
		
		<c:if test = "${idcus!=''}">
	    <c:if test = "${nrkpi>0}">
		loadTiles();
		</c:if>
		</c:if>
		
		var iddevice = $('#select_device').val();
		
		changeDevice(iddevice);	
		
	});
      
    
	function control() {
  		
  		// Controlla che almeno uno store sia stato selezionato
  		var stores = "";
  		$('#id_store :selected').each(function(i, sel){ 
		    stores=stores+$(sel).val(); 
		});
  		
  		
  		
  		if(stores=='') {
  			$('#msg').html("<div class=\"alert alert-warning\"><strong><%=propLang.get("sel_stores")%></strong></div>");
  			
  			return false;
  		}
  		else {
  			
  			$('#__idstores').val(stores);
  			$('#__kpi').val('${kpi}');	
  			
  			showLoader2();	
  		}
 		
  		
  	}
  	
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
	
	function changeDevice(iddevice) {
		
		$('#iddevice').val(iddevice);	
		loadTable2(iddevice);
		
	}
	
	function loadTiles() {
		
		var datastring = $("#deskForm").serialize();
		
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
						
						//$('#tiles_'+$.trim(kpi)).html('<div class="row"><div class="col-sm-12"><div class="sparkline2" data-type="bar" data-height="60" data-width="100%" data-line-width="2" data-bar-color="'+col2+'" data-spot-color="'+col1+'" data-fill-color="'+col3+'" data-highlight-line-color="#fff" data-spot-radius="3" data-resize="true">'+dt+'</div></div></div></div>');
						
					}
					
					spark();
					
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function spark() {
		
		$(".sparkline2").each(function(){
			var $data = $(this).data();
			
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
	
	$('#select_kpi').val('${kpi_selected}');
	
	
	
	function loadChart(kpi, kpidesc) {
		
		showLoader2();
		
		$.ajax({
		      url: '../ajax/ReportDevicesChart.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi_selected='+kpi+'&kpi_desc_selected='+kpidesc+'&idstores=${idstores}&dtda=${dtda}&dtal=${dtal}&tipoPeriod=${tipoPeriod}&uid=${uid}',
		      success: function (data) {
		    	  
		    	  $('#ajaxChart').html('');
		    	  $('#ajaxChart').html(data);
		    	  //hideLoader2();
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function loadTable(kpi) {
		
		//showLoader2();
		
		var datastring = $("#deskForm").serialize();
		
		$.ajax({
		      url: '../ajax/ReportDevicesTable2.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
		    	  
		    	  $('#table_wrapper2').find('tbody').empty();
		    	  $('#table_wrapper2').find('tbody').append(data);
		    	  
		    	  hideLoader2();
		    	  
		    	  chgCol();
		      },
		      error: function () {
		    	  hideLoader2();
		      	  alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
	}
	
	function loadTable2(iddevice) {
		
		showLoader2();
		
		var datastring = $("#deskForm").serialize();
		
		$.ajax({
		      url: '../ajax/ReportDevicesTable_.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
		    	  
		    	  var dt = data.split('##@@');
		    	  
		    	  $('#table_wrapper3').find('tbody').empty();
		    	  $('#table_wrapper3').find('tbody').append(dt[1]);
		    	  
		    	  hideLoader2();
		    	  
		    	  chgCol();
		    	  
		    	  
		    	  
		    	  console.log(dt[0]);
		    	  
		    	  $('#ajaxChart2').html('');
		    	  $('#ajaxChart2').html(dt[0]);
		    	  
		    	  
		    	  
		      },
		      error: function () {
		    	  hideLoader2();
		      	  alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
	}
	
	
	

	function changeKpi(kpi, kpidesc) {
		
		<c:forEach items="${kpilist}" var="kpilist" varStatus="status">
			$( '#panel_${kpilist.xcdel}' ).removeClass( 'bg-light dk' );
			$( '#tiles_${kpilist.xcdel}' ).removeClass( 'bg-light dk' );
		</c:forEach>
		$( '#panel_'+kpi ).addClass( 'bg-light dk' );
		$( '#tiles_'+kpi ).addClass( 'bg-light dk' );
		$( '#select_kpi').val(kpi);
		
		$('#kpi_selected').val(kpi);
		//$('#deskForm').submit();xAxis	
		getHeatMap(kpi, kpidesc);
		loadChart(kpi, kpidesc);
		loadTable(kpi);
		
	}
	
	
	
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
		      	alert('Errore! si e\' verificato un errore.');
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
		      	alert('Errore! si e\' verificato un errore.');
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
		      	alert('Errore! si e\' verificato un errore.');
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
	
	var  $searchableTree;
	
	function getStores() {
		
		var num = 0;
		//var $searchableTree;
		
		
		//$('#tree').html('<div align="center">Please wait...<br/><img src="../../images/loading_2.gif"/></div>');
		
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
					s
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
			$('#uid').val('');
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
				  $('#uid').val('');
		    	  $( "#deskForm" ).submit();
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
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
				$('#uid').val('');
				showLoader();
				submitForm();
			}
		}
		
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
				$('#uid').val('');
				submitForm();
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
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
		
		var comparison = $('#comparison').val();
		
		if($.trim(comparison)=='C01') {
			$('#deskForm').attr('action', 'index-report.htm');
		}
		else {
			$('#deskForm').attr('action', 'index-report-comparison.htm');
		}
		
		$('div.modal-backdrop ').remove();
        $('#ajaxModal').remove();
        $("div.modal").remove();
        
		$( "#deskForm" ).submit();
		
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
			      	alert('Errore! si e\' verificato un errore.');
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
			      	alert('Errore! si e\' verificato un errore.');
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
			      	alert('Errore! si e\' verificato un errore.');
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
			$('#uid').val('');
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
				  $('#uid').val('');
		    	  $( "#deskForm" ).submit();
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	$('#select_group').val('${group}');
	function groupby(group) {
		
		$('#group').val(group);
		$('#pag').val('1');
		submitForm();
		
	}
	
	function getNode(category, group) {
		
		showLoader();
		
		var newGroup = '';
		if(group=='1')
			newGroup='2';
		else if(group=='2')
			newGroup='3';
		else if(group=='3')
			newGroup='4';
		
		$.ajax({
		      url: '../ajax/getCategoryStores.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: false,
		      data: 'group='+group+'&category='+category+'&idstores=${idstores}',
		      success: function (data) {
		    	  //alert(data);
		    	  
		    	  
		    	  var idstores = $.trim(data);
		    	  
		    	  $('#idstores').val(idstores);
		    	  $('#group').val(newGroup);
		    	  $('#uid').val('');
		    	  submitForm();
				  
		    	  //alert(data);
		    
		      },
		      error: function () {
		    	  hideLoader();
		      	  alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
	}
	
	function prev() {
		
		showLoader2();
		
		var pag = $('#pag').val();
		pag = parseInt(pag)-1;
		
		$('#pag').val(pag);
		
		//alert(pag);
		
		var datastring = $("#deskForm").serialize();
		
		
		if(parseInt(pag)==1)
			//$('#prev').prop("disabled",true);
			$('#prev').attr("disabled","disabled");
		
		$.ajax({
		      url: '../ajax/ReportPaging.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
		    	  
		    	  $('#table_wrapper').find('tbody').empty();
		    	  $('#table_wrapper').find('tbody').append(data);
		    	  
		    	  hideLoader2();
		    	  
		      },
		      error: function () {
		    	  hideLoader2();
		      	  alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
	}
	
	function chgCol() {
		
		$('#tableData td').each(function() {
		    var $this = $(this);
		    var value = $this.text();

		    if(!/^\s*\d+(\.\d+)?\s*$/.test(value)) {
		        return;
		    }

		    if(value == 0) {
		        $this.css('color', '#c0c0c0');
		    }
		    else {
		    	$this.css('color', '#50555A');
		    }
		});
	}
	chgCol();
	
	function next() {
		
		showLoader2();
		
		var pag = $('#pag').val();
		var nrPag = $('#nrPag').val();
		pag = parseInt(pag)+1;
		
		//alert(pag);
		
		//$('#prev').prop("disabled",false);
		$('#prev').removeAttr("disabled");
		
		$('#pag').val(pag);
		
		var datastring = $("#deskForm").serialize();
		
		if(parseInt(pag)==parseInt(nrPag))
			$('#next').attr("disabled","disabled");
		
		$.ajax({
		      url: '../ajax/ReportPaging.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
		    	  
		    	  
		    	  //alert(data);
		    	  
		    	  $('#table_wrapper').find('tbody').empty();
		    	  $('#table_wrapper').find('tbody').append(data);
		    	  
		    	  hideLoader2();
		    	  chgCol();
		    	  
		      },
		      error: function () {
		    	  hideLoader2();
		      	  alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
	}
	
	function getComparison(azione) {
		
		var comparison = $("input[name='chkComparison']:checked").val();
		var temp = comparison.split(",");
		var xcdel = temp[0];
		var xdtab = temp[1];
		$('#comparison').val(xcdel);
		$('#sel_comparison').html(xdtab);
		$('#pag').val('1');
		
		if(azione=='GO') {
			$('#uid').val('');
			showLoader();
			submitForm();
		}
		
	}
	
	function saveComparison() {
		
		showLoader();
		
		getComparison('');
		var comparison = $('#comparison').val();
		
		$.ajax({
		      url: '../ajax/savePrefUserComparison.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'comparison='+comparison,
		      success: function (data) {
				//alert(data);
				$('#uid').val('');
				submitForm();
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	function advancedComparison() {
		
		var href = '../popup/sel_advancedPeriod.jsp';
    	
        $('div.modal-backdrop ').remove();
        $('#ajaxModal').remove();
        $("div.modal").remove();
        
        
        var data = '';
        $.ajax({
    	  url: href,
    	  async: true,
    	  dataType: 'html',
    	  beforeSend: function () {
    		  
    	  },
    	  success: function(html) {
    	      
    		  var $modal = $('<div class="modal fade" id="ajaxModal"><div class="modal-body"></div></div>');
    	      $('body').append($modal);
    	      $modal.modal();
    	        
    		  //alert(html);
    		  $modal.append(html);
    		  
    	  },
    	  error: function (xhr, ajaxOptions, thrownError) {
   	        alert(xhr.status);
   	        alert(thrownError);
   	      }
    	});
		
	}
	
	<%@ include file="../../inc/dates.jsp" %>
	
	function addRow(table) {
	  	
		//$last = $('.idrow:last').val();
		$last = $('#'+table+' tr').length-1;
	  	$last=parseInt($last)+1;
		
	  	if(parseInt($last)<=10) {
	  	
		  	if(table=='righe1') {
		  		var $tr = '<tr>'
				    	 +'<td><input type="hidden" id="'+$last+'nrrig1" name="nrrig1" value="'+$last+'" class="idrow">'
						 +'<select id="'+$last+'year" name="year" class="form-control input-lg">'
						 +'  <%=optYearM%>'
				         +'</select> '
						 +'</td>'
						 +'<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow(\''+table+'\')"><i class="icon-plus"></i></a></td>'
						 + '<td><span onclick="$(this).parents(\'tr\').remove();" class="btn btn-danger btn-lg m-l-md"><i class="icon-close"></i></span></td>'+
						 +'</tr>';	
		  	}
		  	
		  	if(table=='righe2') {
		  		var $tr = '<tr>'
		  			+'<td><input type="hidden" id="'+$last+'nrrig2" name="nrrig2" value="'+$last+'" class="idrow">'
		  			+'<select id="'+$last+'yearQuarter" name="yearQuarter" class="form-control input-lg" onchange="cambiaQuarter2(this.value, \''+$last+'quarter\')">'
		  			+'  <%=optYearM%>'
		  			+'</select>'
		  			+'</td>'
		  			+'<td><select id="'+$last+'quarter" name="xquarter" class="form-control input-lg">'
		  			+'<option value="">-</option>'
		  			+'  <%=optQQ%>'
		  			+'</select>'
		  			+'</td>'
		  			+'<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow(\''+table+'\')"><i class="icon-plus"></i></a></td>'
		  			+'<td><span onclick="$(this).parents(\'tr\').remove();" class="btn btn-danger btn-lg m-l-md"><i class="icon-close"></i></span></td>'+
					+'</tr>';
		  	}
		  	if(table=='righe3') {
		  		var $tr = '<tr>'
	    			+'<td><input type="hidden" id="'+$last+'nrrig3" name="nrrig3" value="'+$last+'" class="idrow">'
	    			+'<select id="'+$last+'yearMonth" name="yearMonth" class="form-control input-lg" onchange="cambiaMese2(this.value, \''+$last+'month\')">'
	    			+'  <%=optYearM%>'
	    			+'</select>'
	    			+'</td>'
	    			+'<td><select id="'+$last+'month" name="month" class="form-control input-lg">'
	    			+'  <option value="">-</option>'
	    			+'  <%=optMM%>'
	    			+'</select> '
	    			+'</td>'
	    			+'<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow(\''+table+'\')"><i class="icon-plus"></i></a></td>'
	    			+'<td><span onclick="$(this).parents(\'tr\').remove();" class="btn btn-danger btn-lg m-l-md"><i class="icon-close"></i></span></td>'+
					+'</tr>';
		  		
		  		
		  	}
		  	if(table=='righe4') {
		  		var $tr = '<tr>'
	    			+'<td><input type="hidden" id="'+$last+'nrrig4" name="nrrig4" value="'+$last+'" class="idrow">'
	    			+'<select id="'+$last+'yearWeek" name="yearWeek" class="form-control input-lg" onchange="cambiaSettimana2(this.value, \''+$last+'week\')">'
	    			+'  <%=optYearW%>'
	    			+'</select>'
	    			+'</td>'
	    			+'<td><select id="'+$last+'week" name="week" class="form-control input-lg">'
	    			+'  <option value="">-</option>'
	    			+' <%=optWE%>'
	    			+'</select>'
	    			+'</td>'
	    			+'<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow(\''+table+'\')"><i class="icon-plus"></i></a></td>'
	    			+'<td><span onclick="$(this).parents(\'tr\').remove();" class="btn btn-danger btn-lg m-l-md"><i class="icon-close"></i></span></td>'+
					+'</tr>';
		  		
		  	}
		  	if(table=='righe5') {
		  		var $tr = '<tr>'
	    			+'<td><input type="hidden" id="'+$last+'nrrig5" name="nrrig5" value="'+$last+'" class="idrow">'
	    			+'<input class="datepicker-input2 form-control input-lg" name="day" id="'+$last+'day" '
	    			+'	type="text" value="" data-date-format="dd-mm-yyyy" > '
	    			+'</td>'
	    			+'<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow(\''+table+'\')"><i class="icon-plus"></i></a></td>'
	    			+'<td><span onclick="$(this).parents(\'tr\').remove();" class="btn btn-danger btn-lg m-l-md"><i class="icon-close"></i></span></td>'+
					+'</tr>';
		  	}
		  	 
		  	$("#"+table).find("tbody tr:last").after($tr);
		  	
		  	if(table=='righe5') {
		  		setCal();
		  	}
	  	}
	
	}
	
	
	
	
	function cambiaSettimana2(anno, el) {
		
		$.ajax({
		      url: '../ajax/getWeeks.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+anno,
		      success: function (data) {
				//alert(data);
		    	  $('#'+el+' option:gt(0)').remove();
		    	  $('#'+el).append(data);
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function cambiaMese2(anno, el) {
		
		$.ajax({
		      url: '../ajax/getMonths.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+anno,
		      success: function (data) {
				//alert(data);
		    	  $('#'+el+' option:gt(0)').remove();
		    	  $('#'+el).append(data);
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function cambiaQuarter2(anno, el) {
		
		$.ajax({
		      url: '../ajax/getQuarters.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+anno,
		      success: function (data) {
				//alert(data);
		    	  $('#'+el+' option:gt(0)').remove();
		    	  $('#'+el).append(data);
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function getAdvancedDates() {
		
		var tipo = $('#tipo').val();
		var dtda = '';
		var dtal = '';
		
		var idstores = $('#idstores').val();
		var kpi = $('#kpi').val();
		var group = $('#group').val();
		var l4l = "";//$('#like4like').val();
		
		
		
		var id = "";
		var ordinamento = 0;
		
		showLoader();
		
		$.ajax({
		      url: '../ajax/getAdvancedComparisonId.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: false,
		      success: function (data) {
		    	  id=$.trim(data);
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
		if(tipo=='D') {
			
			var fields = document.getElementsByName("day");
			for(var i = 0; i < fields.length; i++) {
			    
			    dtda = fields[i].value;
				dtal = fields[i].value;
				ordinamento+=1;
				// Write advanced_comparison
				$.ajax({
				      url: '../ajax/saveAdvancedComparison.jsp',
				      type: 'POST',
				      dataType: 'html',
				      async: false,
				      data: 'id='+id+'&dtda='+dtda+'&dtal='+dtal+'&stores='+idstores+'&kpi='+kpi+'&ordinamento='+ordinamento+'&type='+tipo+'&group='+group+'&l4l='+l4l,
				      success: function (data) {},
				      error: function () {
				      	alert('Errore! si e\' verificato un errore.');
				      }
				});
			}
			
		}
		
		if(tipo=='W') {
			var fields = document.getElementsByName("yearWeek");
			var fields2 = document.getElementsByName("week");
			
			for(var i = 0; i < fields.length; i++) {
				
				var yearWeek = fields[i].value;
				var week = fields2[i].value;
				
				var temp = week.split("#");
				dtda = temp[0];
				dtal = temp[1];
				ordinamento+=1;
				// Write advanced_comparison
				$.ajax({
				      url: '../ajax/saveAdvancedComparison.jsp',
				      type: 'POST',
				      dataType: 'html',
				      async: false,
				      data: 'id='+id+'&dtda='+dtda+'&dtal='+dtal+'&stores='+idstores+'&kpi='+kpi+'&ordinamento='+ordinamento+'&type='+tipo+'&group='+group+'&l4l='+l4l,
				      success: function (data) {},
				      error: function () {
				      	alert('Errore! si e\' verificato un errore.');
				      }
				});
			}
		}
		
		if(tipo=='M') {
			
			var fields = document.getElementsByName("yearMonth");
			var fields2 = document.getElementsByName("month");
			
			for(var i = 0; i < fields.length; i++) {
				
				var yearMonth = fields[i].value;
				var month = fields2[i].value;
				
				//if(parseInt(month)==10||parseInt(month)==11||parseInt(month)==12)
				//	yearMonth = parseInt(yearMonth)-1;
				
				var firstDay = new Date(yearMonth, parseInt(month)-1, 1);
				var lastDay = new Date(yearMonth, parseInt(month), 0);
				
				dtda = formatDate(firstDay);
				dtal = formatDate(lastDay);
				ordinamento+=1;
				
				// Write advanced_comparison
				
				$.ajax({
				      url: '../ajax/saveAdvancedComparison.jsp',
				      type: 'POST',
				      dataType: 'html',
				      async: false,
				      data: 'id='+id+'&dtda='+dtda+'&dtal='+dtal+'&stores='+idstores+'&kpi='+kpi+'&ordinamento='+ordinamento+'&type='+tipo+'&group='+group+'&l4l='+l4l,
				      success: function (data) {},
				      error: function () {
				      	alert('Errore! si e\' verificato un errore.');
				      }
				});
				
			}
			
		}
		
		if(tipo=='Q') {
			
			var fields = document.getElementsByName("yearQuarter");
			var fields2 = document.getElementsByName("xquarter");
			
			alert(fields.length);
			
			for(var i = 0; i < fields.length; i++) {
				
				var anno = fields[i].value;
				var quarter = fields2[i].value;
				
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
				
				ordinamento+=1;
				
				//alert(ordinamento+" "+dtda+" "+dtal);
				
				
				// Write advanced_comparison
				$.ajax({
				      url: '../ajax/saveAdvancedComparison.jsp',
				      type: 'POST',
				      dataType: 'html',
				      async: false,
				      data: 'id='+id+'&dtda='+dtda+'&dtal='+dtal+'&stores='+idstores+'&kpi='+kpi+'&ordinamento='+ordinamento+'&type='+tipo+'&group='+group+'&l4l='+l4l,
				      success: function (data) {},
				      error: function () {
				      	alert('Errore! si e\' verificato un errore.');
				      }
				});
				
			}
			
		}
		
		if(tipo=='Y') {
			
			var fields = document.getElementsByName("year");
			for(var i = 0; i < fields.length; i++) {
				
				
				
				
				
				var anno = fields[i].value;
				
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
				      	alert('Errore! si e\' verificato un errore.');
				      }
				});
				
				ordinamento+=1;
				// Write advanced_comparison
				$.ajax({
				      url: '../ajax/saveAdvancedComparison.jsp',
				      type: 'POST',
				      dataType: 'html',
				      async: false,
				      data: 'id='+id+'&dtda='+dtda+'&dtal='+dtal+'&stores='+idstores+'&kpi='+kpi+'&ordinamento='+ordinamento+'&type='+tipo,
				      success: function (data) {},
				      error: function () {
				      	alert('Errore! si e\' verificato un errore.');
				      }
				});
			}
			
		}
		
		
		$('#id_advanced').val(id);
		$('#deskForm').attr('action', 'index-report-advanced.htm');
		
		$('div.modal-backdrop ').remove();
        $('#ajaxModal').remove();
        $("div.modal").remove();
        
		$( "#deskForm" ).submit();
		
	}
	
	var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		myChart.resize();
		//myChart2.resize()
		myChart3.resize()
		myChart_x.resize()
		//spark();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	
	
	
	
	
	
	function excelExport() {
		
		/*
		var data_type = 'data:application/vnd.ms-excel';
        var table_div = document.getElementById('table_wrapper2');//table_wrapper is table id
        var table_html = table_div.outerHTML.replace(/ /g, '%20');

        var a = document.createElement('a');
        a.href = data_type + ', ' + table_html;
        a.download = 'table_name_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
        a.click();
        */
		function doit(type, fn, dl) {
			var elt = document.getElementById('table_wrapper2');
			var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
			return dl ?
				XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
				XLSX.writeFile(wb, fn || ('download.' + (type || 'xlsx')));
		}
		doit('xlsx');

        
	}
	
	function excelExport2() {
		
		/*
		var data_type = 'data:application/vnd.ms-excel';
        var table_div = document.getElementById('table_wrapper2');//table_wrapper is table id
        var table_html = table_div.outerHTML.replace(/ /g, '%20');

        var a = document.createElement('a');
        a.href = data_type + ', ' + table_html;
        a.download = 'table_name_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
        a.click();
        */
		function doit(type, fn, dl) {
			var elt = document.getElementById('table_wrapper3');
			var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
			return dl ?
				XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
				XLSX.writeFile(wb, fn || ('download.' + (type || 'xlsx')));
		}
		doit('xlsx');

        
	}
	
	
	
 	
 	
 	
 	
 	
 	
 	
	var myChart3 = echarts.init(document.getElementById('main2'));
	
	getHeatMap('${kpi_selected}','${kpi_desc_selected}');
	loadChart('${kpi_selected}','${kpi_desc_selected}');
	
	function getHeatMap(kpi, descr) {
		
		var idstores = $('#idstores').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		
		myChart3.dispose();
		$('#main2').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		$.ajax({
		      url: '../ajax/getHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}&tipo=${tipoPeriod}',
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  var res = data.split('##@@');
				  var max=res[0];
				  var heatmap=res[1];
				  var XA=res[2];
				  var YA=res[3];
				  
				  //alert(heatmap);
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
				        height: '${perc}%',
				        y: '10%',
				        bottom: '40'
				    },
				    toolbox: {
				    	show : true,
				        showTitle: false,
				        feature: {
				            saveAsImage: {show: true}
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
				
				myChart3 = echarts.init(document.getElementById('main2'));
				myChart3.setOption(option2);
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	
	
	
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
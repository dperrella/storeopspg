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
              
              <%@ include file="../../inc2/menuApp11.jsp" %>
              
              <section>
                  <section class="vbox">
              
               		<div class="row" id="div" style="opacity: 0.8; filter: alpha(opacity=50);
                  		z-index: 0; width:100%; position:absolute; background:#fff;margin-left: 0px;">
	              			
		              		<div class="col-sm-3 b-b b-2x">
		                      <div class="wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/sel_kpi.jsp" data-toggle="ajaxModal">
		                          <span class="h4 block"><i class="icon-puzzle i-lg"></i> WHAT?</span></a>
		                          <div class="h3 m-t-xs block text-ellipsis" id="sel_kpi">${nrkpi} Selected</div>
		                      </div>
		                    </div>
			                    
		              		<div class="col-sm-3 b-b b-2x">
		                      <div class=" wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/stores.jsp?nrstores=${nrstores}" data-toggle="ajaxModal">
		                        	<span class="h4 block text-ellipsis"><i class="icon-map i-lg"></i> WHERE? <%=session.getAttribute("dscus") %></span></a>
		                        	<div class="h3 m-t-xs block text-ellipsis" id="sel_stores2">${nrstores} Selected</div>
		                      </div>
		                    </div>
		                    
		                    <div class="col-sm-3 b-b b-2x">
		                      <div class=" wrapper-xs m-l" style="margin-top:5px">
		                        <a href="../popup/sel_period.jsp" data-toggle="ajaxModal">
		                          <span class="h4 block"><i class="icon-calendar i-lg"></i> WHEN?</span></a>
		                          <div class="h3 m-t-xs block text-ellipsis" id="sel_date2">${dates}</div>
		                      </div>
		                    </div>
		                    
		                    <div class="col-sm-3 b-b b-2x">
	                      <div class="wrapper-xs m-l" style="margin-top:5px">
	                        <a href="../popup/sel_comparison.jsp" data-toggle="ajaxModal">
	                          <span class="h4 block"><i class="icon-equalizer i-lg"></i> COMPARISON</span></a>
	                          <div class="h3 m-t-xs block text-ellipsis" id="sel_comparison2">No Comparison</div>
	                      </div>
	                    </div>
		          
	              	</div>
	              	
                    <section class="scrollable padder" id="scrollable">
              			
              			<form action="index-report-devices.htm" id="deskForm" name="deskForm" method="post">
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
	                      
	                    </form>
	                    
	                    <c:if test = "${idcus!=''}">
	                      
	              		<div class="row row-sm" id="corpo">
								
						  	<div class="col-xs-12">
							  
							  
							  <div class="row m-t-md m-b-md text-center">
								
								<div class="col-sm-3">
			                      <div class="bg-primary lter wrapper-md r">
			                        <a href="../popup/sel_kpi.jsp" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-puzzle i-lg"></i> WHAT?</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_kpi2">${nrkpi} Selected</div>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-3">
			                      <div class="bg-black wrapper-md r">
			                        <a href="../popup/stores.jsp?nrstores=${nrstores}"  data-toggle="ajaxModal">
			                        	<span class="h4 block"><i class="icon-map i-lg"></i> WHERE? <%=session.getAttribute("dscus") %></span></a>
			                        	<div class="h2 m-t-xs block text-ellipsis" id="sel_stores">${nrstores} Selected</div>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-3">
			                      <div class="bg-light dker wrapper-md r">
			                        <a href="../popup/sel_period.jsp" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-calendar i-lg"></i> WHEN?</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_date">${dates}</div>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-3">
			                      <div class="bg-info lter wrapper-md r">
			                        <a href="../popup/sel_comparison.jsp" data-toggle="ajaxModal">
			                          <span class="h4 block"><i class="icon-equalizer i-lg"></i> COMPARISON</span></a>
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_comparison">No Comparison</div>
			                      </div>
			                    </div>
			                    
			                  </div>
			                  
			                  <div id="msg">${msg}</div>
			                  
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
			                    
			                    	<div id="main2" style="height:450px;"></div>
			                     
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
							    			
							    			<select id="select_kpi" name="select_kpi" style="margin-top:4px" class="form-control" onchange="changeKpi(this.value)">
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
  
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Treeview -->
  <script src="../../js/bootstrap-treeview/bootstrap-treeview.min.js"></script>
  
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
		      url: '../ajax/ReportDevicesTable.jsp',
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
	
	function getStores() {
		
		var num = 0;
		var $searchableTree;
		
		$('#tree').html('<div align="center">Please wait...<br/><img src="../../images/loading_2.gif"/></div>');
		
		$.post("../../servlet/ws/GetStoresNodesHB", "username=${username}&idstores=${idstores}&idcus=${idcus}", function(result, textStatus) {
			
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
			
		}, "json");
		
		
		
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
		myChart2.resize()
		myChart3.resize()
		//spark();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	
	
	
	
	
	
	function excelExport() {
		
		var data_type = 'data:application/vnd.ms-excel';
        var table_div = document.getElementById('table_wrapper2');//table_wrapper is table id
        var table_html = table_div.outerHTML.replace(/ /g, '%20');

        var a = document.createElement('a');
        a.href = data_type + ', ' + table_html;
        a.download = 'table_name_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
        a.click();
		
	}
	
	
	
	
	/*
	// HEATMAP
    // based on prepared DOM, initialize echarts instance
    var myChart3 = echarts.init(document.getElementById('main2'));
	
    var hours = ['12a', '1a', '2a', '3a', '4a', '5a', '6a',
 		        '7a', '8a', '9a','10a','11a',
 		        '12p', '1p', '2p', '3p', '4p', '5p',
 		        '6p', '7p', '8p', '9p', '10p', '11p'];
	var days = ['Saturday', 'Friday', 'Thursday',
	        'Wednesday', 'Tuesday', 'Monday', 'Sunday'];

	var data = [[0,0,5],[0,1,1],[0,2,0],[0,3,0],[0,4,0],[0,5,0],[0,6,0],[0,7,0],[0,8,0],[0,9,0],[0,10,0],[0,11,2],[0,12,4],[0,13,1],[0,14,1],[0,15,3],[0,16,4],[0,17,6],[0,18,4],[0,19,4],[0,20,3],[0,21,3],[0,22,2],[0,23,5],[1,0,7],[1,1,0],[1,2,0],[1,3,0],[1,4,0],[1,5,0],[1,6,0],[1,7,0],[1,8,0],[1,9,0],[1,10,5],[1,11,2],[1,12,2],[1,13,6],[1,14,9],[1,15,11],[1,16,6],[1,17,7],[1,18,8],[1,19,12],[1,20,5],[1,21,5],[1,22,7],[1,23,2],[2,0,1],[2,1,1],[2,2,0],[2,3,0],[2,4,0],[2,5,0],[2,6,0],[2,7,0],[2,8,0],[2,9,0],[2,10,3],[2,11,2],[2,12,1],[2,13,9],[2,14,8],[2,15,10],[2,16,6],[2,17,5],[2,18,5],[2,19,5],[2,20,7],[2,21,4],[2,22,2],[2,23,4],[3,0,7],[3,1,3],[3,2,0],[3,3,0],[3,4,0],[3,5,0],[3,6,0],[3,7,0],[3,8,1],[3,9,0],[3,10,5],[3,11,4],[3,12,7],[3,13,14],[3,14,13],[3,15,12],[3,16,9],[3,17,5],[3,18,5],[3,19,10],[3,20,6],[3,21,4],[3,22,4],[3,23,1],[4,0,1],[4,1,3],[4,2,0],[4,3,0],[4,4,0],[4,5,1],[4,6,0],[4,7,0],[4,8,0],[4,9,2],[4,10,4],[4,11,4],[4,12,2],[4,13,4],[4,14,4],[4,15,14],[4,16,12],[4,17,1],[4,18,8],[4,19,5],[4,20,3],[4,21,7],[4,22,3],[4,23,0],[5,0,2],[5,1,1],[5,2,0],[5,3,3],[5,4,0],[5,5,0],[5,6,0],[5,7,0],[5,8,2],[5,9,0],[5,10,4],[5,11,1],[5,12,5],[5,13,10],[5,14,5],[5,15,7],[5,16,11],[5,17,6],[5,18,0],[5,19,5],[5,20,3],[5,21,4],[5,22,2],[5,23,0],[6,0,1],[6,1,0],[6,2,0],[6,3,0],[6,4,0],[6,5,0],[6,6,0],[6,7,0],[6,8,0],[6,9,0],[6,10,1],[6,11,0],[6,12,2],[6,13,1],[6,14,3],[6,15,4],[6,16,0],[6,17,0],[6,18,0],[6,19,0],[6,20,1],[6,21,2],[6,22,2],[6,23,6]];
	data = data.map(function (item) {
	    return [item[1], item[0], item[2] || '-'];
	});

	console.log("${heatmap}");
	
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
	        data: [${xAxis}],
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
    myChart3.setOption(option2);
	*/
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
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
		      data: 'kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}',
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
				        height: '70%',
				        y: '10%'
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
	
  </script> 
  
  
  
  
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  
  
	
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
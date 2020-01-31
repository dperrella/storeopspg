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
	                          <div class="h3 m-t-xs block text-ellipsis" id="sel_comparison2">${comparison_descr}</div>
	                      </div>
	                    </div>
		          
	              	</div>
	              	
                    <section class="scrollable padder" id="scrollable">
              			
              			<form action="index-report-advanced.htm" id="deskForm" name="deskForm" method="post">
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
	                      <input type="hidden" name="like4like" id="like4like" value=""/>
                      	  <input type="hidden" name="id_advanced" id="id_advanced" value="${id_advanced}"/>
                      	  <input type="hidden" name="numPeriods" id="numPeriods" value="${numPeriods}"/>
                     	  <input type="hidden" name="rowspan" id="rowspan" value="${rowspan}"/>
                      
	                      <input type="hidden" name="pag" id="pag" value="${pag}"/>
	                      <input type="hidden" name="nrPag" id="nrPag" value="${nrPag}"/>
	                      <input type="hidden" name="nrRecord" id="nrRecord" value="${nrRecord}"/>
	                      <input type="hidden" name="numRow" id="numRow" value="${numRow}"/>
	                      
	                      <input type="hidden" name="idcus" id="idcus" value="<%=session.getAttribute("idcus").toString()%>"/>
	                      <input type="hidden" name="lang" id="lang" value="<%=session.getAttribute("language").toString()%>"/>
	                    </form>
	                    
	                    <c:if test = "${idcus!=''}">
	                      
	              		<div class="row row-sm">
								
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
			                          <div class="h2 m-t-xs block text-ellipsis" id="sel_comparison">${comparison_descr}</div>
			                      </div>
			                    </div>
			                    
			                  </div>
			                  
			                  
			                  <div id="msg">${msg}</div>
			                  
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
			                      <span class="hidden-sm"><b>BarChart</b></span>
			                    </header>
			                    <div class="panel-body">
			                    	<div id="main" style="height:400px;"></div>
			                    	
			                    	<div align="center" class="m-t">
			                    		<div class="btn-group" data-toggle="buttons">
										  <label class="btn btn-default active" id="btn_atipo_chart_s" onclick="changeChart('s','')">
										    <input type="radio" id="tipo_chart_s" name="tipo_chart" value="s" disabled> Store Chart
										  </label>
										  
										  <!-- 
										  <label class="btn btn-default" id="btn_atipo_chart_t" onclick="changeChart('t','sum')">
										    <input type="radio" id="tipo_chart_t" name="tipo_chart" value="t" onclick="changeChart(this.value)" disabled> Period (Sum)
										  </label>
										  <label class="btn btn-default" id="btn_atipo_chart_m" onclick="changeChart('m','avg')">
										    <input type="radio" id="tipo_chart_m" name="tipo_chart" value="m" disabled> Period (Average)
										  </label>
										  -->
									  	</div>
			                     	</div>
			                     	
			                     	<div class="line line-dashed b-b line-lg pull-in"></div>
							    	
							    	<div class="row">
							    		
							    		<div class="col-sm-2">
							    			<select id="select_group" name="select_group" style="margin-top:4px" class="form-control" onchange="groupby(this.value)" disabled>
						            			<option value="1">Group by Customer</option>
						            			<option value="2">Group by Nation</option>
						            			<option value="3">Group by Region</option>
						            			<option value="4">Group by Store</option>
						            			<option value="5">Group by Door ID</option>
						            			<option value="6">Group by Door Type</option>
						            		</select>
							    		</div>
							    		<div class="col-sm-3">
						    			<div class="checkbox i-checks">
							              <label>
							                <input name="rep_like4like" id="rep_like4like" type="checkbox" value="S" onclick="call_like()">
							                <i></i>
							                Like for Like Comparison
							              </label>
							            </div>
							            
							    		</div>
								    		<div class="col-sm-7" align="right" id="exportButtonDiv">
								    		
								    			<a href="#" onclick="excelExport()" class="btn btn-lg btn-default btn-success"><i class="fa fa-table"></i> Excel Export</a>
							    			
								    		</div>
								    	</div>
								    	
								    	
								    
				               		
				            		
				               	</div>
				               	
				               	
				               	
				               	
				               	<div id="table_wrapper" class="table-responsive horizontal">
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
				           		<div class="wrapper">
					           		<div class="row">
							    		<div class="col-sm-6" align="left">
							    			<a href="#" id="prev" onclick="prev()" class="btn btn-default btn-lg" ${disPrev}>Prev</a>
							    		</div>
							    		<div class="col-sm-6" align="right">
							    			<a href="#" id="next" onclick="next()" class="btn btn-default btn-lg" ${disNext}>Next</a>
							    		</div>
							    	</div>
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
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Sparkline Chart -->
  <script src="../../js/charts/sparkline/jquery.sparkline.min.js"></script>
  
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
	
	// based on prepared DOM, initialize echarts instance
    var myChart = echarts.init(document.getElementById('main'));
	
	// specify chart configuration item and data
    	
	option = {
	    title : {
	        text: 'KPI per Store',
	        subtext: 'Barchart'
	    },
	    tooltip : {
	        trigger: 'axis',
	        formatter: '<b>{b0}</b><br/><br/>${chartFormatter}',
	        axisPointer : {          
	            type : 'shadow'        
	        }
	    },
	    legend: {
	    	selectedMode: 'single',
	        data:[${kpi_in}]
	    },
	    dataZoom: [{
	    	bottom: 0,
	        type: 'slider',
	        start: 50,
	        end: 70
	    }, {
	        type: 'inside',
	        start: 50,
	        end: 70
	    }],
	    toolbox: {
	        show : true,
	        showTitle: false,
	        feature : {
	            //dataView : {show: true, readOnly: false, title: 'Data View'},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            // Lista negozi
	            data : [${stores}]
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	              
			<c:forEach items="${chart}" var="chart" varStatus="status">
			
			<c:if test = "${chart.cmp11!=''}">
			{
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp11}],
	            itemStyle: {
	            	normal: {
	                    color: '${chart.cmp1}',
	                }
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp12!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp12}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',90),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp13!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp13}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',80),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp14!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp14}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',70),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp15!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp15}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',60),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp16!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp16}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',50),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp17!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp17}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',40),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp18!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp18}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',30),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp19!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp19}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',20),
	                },
	            },
	        },
	        </c:if>
	        <c:if test = "${chart.cmp20!=''}">
	        {
	            name:'${chart.xcdel}',
	            type:'bar',
	            data:[${chart.cmp20}],
	            itemStyle: {
	            	normal: {
	                    color: convertHex('${chart.cmp1}',10),
	                },
	            },
	        },
	        </c:if>
			</c:forEach>
	        
	    ]
	};
	
    function convertHex(hex,opacity){
        hex = hex.replace('#','');
        r = parseInt(hex.substring(0,2), 16);
        g = parseInt(hex.substring(2,4), 16);
        b = parseInt(hex.substring(4,6), 16);

        result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
        
        //alert(result);
        
        return result;
    }
    
    // use configuration item and data specified to show chart
    myChart.setOption(option);
	
	
	
	
	
	
	
	
	
	
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
		
		//alert(comparison);
		if($.trim(comparison)=='ADV') {
			$('#deskForm').attr('action', 'index-report-advanced.htm');
		}
		else if($.trim(comparison)=='C01') {
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
		      url: '../ajax/ReportAdvancedPaging.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
		    	  
		    	  $('#table_wrapper').find('tbody').empty();
		    	  $('#table_wrapper').find('tbody').append(data);
		    	  
		    	  spark();
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
		      url: '../ajax/ReportAdvancedPaging.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: datastring,
		      success: function (data) {
		    	  
		    	  
		    	  //alert(data);
		    	  
		    	  $('#table_wrapper').find('tbody').empty();
		    	  $('#table_wrapper').find('tbody').append(data);
		    	  
		    	  spark();
		    	  chgCol();
		    	  hideLoader2();
		    	  
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
				submitForm();
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function spark() {
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
	}
	
	function call_like() {
		if($('#rep_like4like').prop('checked')) {
			$('#like4like').val('S');
		}
		else {
			$('#like4like').val('');
		}
		showLoader();
		submitForm();
	}
	
	if('${like4like}'=='S') {
		$('#rep_like4like').prop('checked', true);
	}
	else {
		$('#rep_like4like').prop('checked', false);
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
		var l4l = $('#like4like').val();
		
		
		
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
			
			//alert(fields.length);
			
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
	
	
	
	
	
	
	function excelExport() {
		
		var formData = $('#deskForm').serialize();
		var href = '../popup/excelExport.jsp?tipo=A';
    	
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
	
	var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		myChart.resize();
		myChart2.resize()
		//spark();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	
	
  </script> 
  
  
  
  
  
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  
  
	
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
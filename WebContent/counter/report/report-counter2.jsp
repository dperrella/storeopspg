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
          <section class="hbox stretch">   
          	<!-- menu app -->
              
            <%@ include file="../../inc2/menuApp2.jsp" %>
            
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
		                		<a href="report-counter-modal2.jsp?idstores=${idstores}&tipo=D"
		                		class="getModal" data-toggle="modal">
		                		<i class="fa fa-sort-numeric-asc"></i> <%=propLang.get("tipo_day") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal2.jsp?idstores=${idstores}&tipo=W"
		                		class="getModal" data-toggle="modal">
	                        	<i class="fa fa-list-ol"></i> <%=propLang.get("tipo_week") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal2.jsp?idstores=${idstores}&tipo=M"
		                		class="getModal" data-toggle="modal">
	                        	<i class="icon-calendar"></i> <%=propLang.get("tipo_month") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal2.jsp?idstores=${idstores}&tipo=P"
		                		class="getModal" data-toggle="modal">
	                        	<i class="fa fa-sort-alpha-asc"></i> <%=propLang.get("tipo_period") %></a></li>
	                    </ul>
                        
                      </div>
                    </section>
                  </section>
                </aside>
                <!-- / side content -->
                
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      <h2 class="font-thin m-b-none">Report Smart Counter ${descrStore}</h2>
                      
                      <c:if test = "${dtda != dtal}">
                      <small>Period: ${dtda} - ${dtal}</small>  
                      </c:if>
                      <c:if test = "${dtda == dtal}">
                      <small>Date: ${dtda}</small>  
                      </c:if>
                      
                      
                      <div class="row m-t row-sm">
                        
		                <div class="col-md-12">   
	                      <section class="panel panel-default" id="panelChart">
		                    <header class="panel-heading bg-light">
		                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
		                      <span class="hidden-sm"><b>Chart</b></span>
		                    </header>
		                    <div class="panel-body">
		                    	
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
						    	<hr/>
						   		<div class="panel-body">
		                    		<div id="main2" style="height:450px;"></div>
			                    </div>
			                </div>
			                <footer class="panel-footer">
			                  <div class="row text-center no-gutter">
			                    <div class="col-xs-12">
			                    
			                    <div id="table_wrapper3" class="table-responsive horizontal">
			                    <table style="width:100%">
			                      <tr>
			                    	<c:forEach items="${sto}" var="sto" varStatus="status">
			                    	<td style="width:150px" class="wrapper-xs">
			                    		<p class="h3 font-bold m-t">${sto.cmp1}</p>
			                      		<p class="text-muted text-ellipsis">${sto.cmp2}</p>  
			                    	</td>
				           	    	</c:forEach>
				           	      </tr>
			                    </table>
			                    </div>
			                                 
			                    </div>
			                  </div>
			                </footer>
			                    
			              </section>
			              
			              <section class="panel panel-default" id="panelBarChart">
		                    <header class="panel-heading bg-light">
		                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
		                      <span class="hidden-sm"><b>BarChart</b></span>
		                    </header>
		                    <div class="panel-body">
		                    	
						    	<div id="barChart" style="height:350px;"></div>
						    	
						    </div>
						  </section>
						  
						</div>
						
					  </div>
					  
					 
					  
					   
			          <div class="row row-sm">
			          
			          	<div class="col-md-12">
			            
			            <section class="panel panel-default">
			            	<header class="panel-heading font-bold">Data</header>
			               	
			               	<div class="panel-body">
			               		<button id="btnExport" class="btn btn-default"><%=propLang.get("xls_export") %></button>
			               	</div>
			               	
			               	<div id="table_wrapper" class="table-responsive horizontal">
			               		<table class="table table-striped b-t b-light">
			               		<thead>
			               			<tr>
			               				<th>Period</th>
			              				
			               				${th}
			               			</tr>
			               		</thead>
			               		<tbody>
				               	<!--  Periodo -->  
								<c:forEach items="${period}" var="period" varStatus="status">
									<tr>
										<td>${period.xcdel}
										<input type="hidden" name="period" value="${period.cmp4}"/>
										</td>
										${period.cmp1}
									</tr>
								</c:forEach>
				           	    </tbody>
				           		<tfoot>
				           	    	<tr>
				           	    		<td></td>
					           	    	<c:forEach items="${sto}" var="sto" varStatus="status">
					           	    	<td><b>${sto.cmp1}</b></td>
					           	    	</c:forEach>
				           	    	</tr>
				           	    </tfoot>
			           			</table>
			           		</div>
			           	</section>
			           	
		            
		                </div>
			            
		             </div>
		             
		             <div class="row row-sm">
			          
			          	<div class="col-md-12">
			            
			            <section class="panel panel-default">
			            	<header class="panel-heading font-bold">Store Time & Adjustment Factor</header>
			            	<div class="panel-body">
			               		<button id="btnExport2" class="btn btn-default"><%=propLang.get("xls_export") %></button>
			               	</div>
			               	
			               	<div id="table_wrapper2" class="table-responsive horizontal">
			               		<table class="table table-striped b-t b-light">
			               		<thead>
			               			<tr>
			               				<th>Store</th>
			               				<th><%=propLang.get("lun") %></th>
			               				<th><%=propLang.get("mar") %></th>
			               				<th><%=propLang.get("mer") %></th>
			               				<th><%=propLang.get("gio") %></th>
			               				<th><%=propLang.get("ven") %></th>
			               				<th><%=propLang.get("sab") %></th>
			               				<th><%=propLang.get("dom") %></th>
			               			</tr>
			               		</thead>
			               		<tbody>
			               		<c:forEach items="${adj}" var="adj" varStatus="status">
									<tr>
										<td>${adj.xcdel}</td>
										<td>${adj.cmp1}</td>
										<td>${adj.cmp2}</td>
										<td>${adj.cmp3}</td>
										<td>${adj.cmp4}</td>
										<td>${adj.cmp5}</td>
										<td>${adj.cmp6}</td>
										<td>${adj.cmp7}</td>
									</tr>
								</c:forEach>
			               		</tbody>
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
        <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  
  
    
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
    <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  
  <script src="../../js/bootstrap-modal.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
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
	
	function loadChart(kpi, kpidesc) {
		
		showLoader2();
		
		$.ajax({
		      url: '../ajax/ReportDevicesChart.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi_selected='+kpi+'&kpi_desc_selected='+kpidesc+'&idstores=${idstores}&dtda=${dtda}&dtal=${dtal}&tipoPeriod=${tipoPeriod}&uid=${uid}&category=${category}&af=${af}',
		      success: function (data) {
		    	  
		    	  $('#ajaxChart').html('');
		    	  $('#ajaxChart').html(data);
		    	  hideLoader2();
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	function getHeatMap(kpi, descr) {
		
		var idstores = '${idstores}';
		var dtda = '${dtda}';
		var dtal = '${dtal}';
		
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
				  
				  //alert(heatmap);
				  //alert(heatmap);
				  var array = JSON.parse(heatmap);
				  var xa2=XA.split(",");
				  
				  option2 = {
				    		
				    tooltip: {
				        position: 'top'
				    },
				    animation: true,
				    grid: {
				        left: '0%',
				        top: '0%',
				        right: '10%',
				        bottom: '10%',
				        containLabel: true
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
				        data: ['<%=propLang.get("sab")%>','<%=propLang.get("ven")%>','<%=propLang.get("gio")%>','<%=propLang.get("mer")%>','<%=propLang.get("mar")%>','<%=propLang.get("lun")%>','<%=propLang.get("dom")%>'],
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
				
				//hideLoader2();
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	var myChart3 = echarts.init(document.getElementById('main2'));
	
	getHeatMap('${kpi}','VISITORS');
	loadChart('${kpi}','VISITORS');
	
	//based on prepared DOM, initialize echarts instance
	var myChart4 = echarts.init(document.getElementById('barChart'));
		
		// specify chart configuration item and data
	  option = {
			color: ['#3F4787'],
			    
		    title : {
		        text: 'Store Count',
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            
		            type : 'shadow'      
		        }
		    },
		    legend: {
		        data:[${xAxis}]
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '10%',
		        containLabel: true
		    },
		    toolbox: {
		        show : true,
		        showTitle: false,
		        feature : {
		        	
		        	//dataView : {show: true, readOnly: false, title: 'Data View'},
		            magicType : {show: true, type: ['bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            //axisLabel: { rotate: 45, },
		            // Lista negozi
		            data : [${xAxis}]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'Today',
		            type:'bar',
		            barWidth: '80%',
		            data:[${total}],
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
		        }
		    ]
		
		};

	  // use configuration item and data specified to show chart
	  myChart4.setOption(option);
	
	
	
  	
  	$("#btnExport").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	    var table_div = document.getElementById('table_wrapper');
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	});
  	
  	$("#btnExport2").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	    var table_div = document.getElementById('table_wrapper2');
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	});

  	function setCal() {
  		
  		//alert('aaa');	
  		
  		 $('.datepicker-input2').datepicker({
  		    format: 'dd-mm-yyyy',
  		    orientation: 'bottom',
  		    autoclose: true
  		});  
  		
  	}
  	
	function cambiaSettimana(anno) {
		
		//alert(anno);
		
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
	
	var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		myChart.resize();
		myChart2.resize();
		myChart3.resize();
		myChart4.resize()
		//spark();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	var nrcol = ${nrcol};
	if(nrcol==1) {
		$('#panelBarChart').hide();
	}
  	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
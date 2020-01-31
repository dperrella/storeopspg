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
          <section class="hbox stretch">    
          	
          	<!-- menu app -->
              
            <%@ include file="../../inc2/menuApp.jsp" %>
                  
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<h2 class="font-thin m-b">${description}</h2>
	
		            <div class="row row-sm">
                        
		                <div class="col-md-6">    
		                
		                	<section class="panel panel-default">
				                <header class="panel-heading font-bold" style="height:50px;padding-top:15px">Store(s) Confronto</header>
				                <div class="panel-body">
				                	<div id="flotConfronto" style="height:250px"></div>
				                </div>
				                <footer class="panel-footer bg-white">
				                  <div class="row text-center no-gutter">
				                    
				                    <div class="col-xs-6 b-r b-light">
				                      <p class="h3 font-bold m-t">${totAlarm1Confronto}</p>
				                      <p class="text-muted">${alarm1} - ${alarm1_descr}</p>
				                    </div>
				                    <div class="col-xs-6">
				                      <p class="h3 font-bold m-t">${totAlarm2Confronto}</p>
				                      <p class="text-muted">${alarm2} - ${alarm2_descr}</p>                        
				                    </div>
				                    
				                  </div>
				                </footer>
				                
				                <div class="table-responsive">
				               		<table class="table table-striped b-t b-light">
				               		<thead>
				               			<tr>
				               				<th style="width:25%">Comparison Period</th>
				               				<th style="width:25%">${alarm1} - ${alarm1_descr}</th>
				               				<th style="width:25%">${alarm2} - ${alarm2_descr}</th>
				               				<th style="width:25%">Ratio</th>
				               			</tr>
				               		</thead>
				               		<tbody>
				               			<c:forEach items="${periodConfronto}" var="periodConfronto" varStatus="status">
				               			<tr>
				               				<td class="b-r">${periodConfronto.xcdel}</td>
				               				<td class="b-r">${periodConfronto.xdtab}</td>
				               				<td class="b-r">${periodConfronto.cmp1}</td>
				               				<td><b>${periodConfronto.cmp2}</b></td>
				               			</tr>
				               			</c:forEach>
				               		</tbody>
				               		<tfoot>
				               			<tr>
				               				<td class="b-r"></td>
				               				<td class="b-r"><b>${tot1Confronto}</b></td>
				               				<td class="b-r"><b>${tot1Confronto}</b></td>
				               				<td></td>
				               			</tr>
				               		</tfoot>
				               		</table>
				               	</div>
				                
				            </section>
			            
			            </div>
			            
			            <div class="col-md-6">
			            	
			            	<section class="panel panel-default">
				                <header class="panel-heading">
				                
					                <form name="formPage" id="formPage" action="report2-4.htm" method="post">
	    	
							    	<input type="hidden" name="id_report" id="id_report" value="${id_report}"/>
							    	<input type="hidden" name="code" id="code" value="${code}"/>
							    	<input type="hidden" name="description" id="description" value="${description}"/>
							    	<input type="hidden" name="alarm1" id="alarm1" value="${alarm1}"/>
							    	<input type="hidden" name="alarm2" id="alarm2" value="${alarm2}"/>
							    	<input type="hidden" name="alarm1_descr" id="alarm1_descr" value="${alarm1_descr}"/>
							    	<input type="hidden" name="alarm2_descr" id="alarm2_descr" value="${alarm2_descr}"/>
							    	<input type="hidden" name="tipo" id="tipo" value="${tipo}"/>
							    	<input type="hidden" name="historic" id="historic" value="${historic}"/>
							    	<input type="hidden" name="benchmark" id="benchmark" value="${benchmark}"/>
							    	<input type="hidden" name="id_report" id="id_report" value="${id_report}"/>
							    	
							    	<input type="hidden" name="day" id="day" value="${day}"/>
							    	<input type="hidden" name="yearWeek" id="yearWeek" value="${yearWeek}"/>
							    	<input type="hidden" name="week" id="week" value="${week}"/>
							    	<input type="hidden" name="yearMonth" id="yearMonth" value="${yearMonth}"/>
							    	<input type="hidden" name="month" id="month" value="${month}"/>
							    	<input type="hidden" name="dtda" id="dtda" value="${dtda}"/>
							    	<input type="hidden" name="dtal" id="dtal" value="${dtal}"/>
							    	
							    	<input type="hidden" name="dayConfronto" id="dayConfronto" value="${dayConfronto}"/>
							    	<input type="hidden" name="yearWeekConfronto" id="yearWeekConfronto" value="${yearWeekConfronto}"/>
							    	<input type="hidden" name="weekConfronto" id="weekConfronto" value="${weekConfronto}"/>
							    	<input type="hidden" name="yearMonthConfronto" id="yearMonthConfronto" value="${yearMonthConfronto}"/>
							    	<input type="hidden" name="monthConfronto" id="monthConfronto" value="${monthConfronto}"/>
							    	<input type="hidden" name="dtdaConfronto" id="dtdaConfronto" value="${dtdaConfronto}"/>
							    	<input type="hidden" name="dtalConfronto" id="dtalConfronto" value="${dtalConfronto}"/>
							    	
							    	<c:forEach items="${sto}" var="sto" varStatus="status">
							        <input type="hidden" name="id_store" id="id_store" value="${sto.xcdel}"/>
							        </c:forEach>
							    	
							    	<c:forEach items="${stoConfronto}" var="stoConfronto" varStatus="status">
							        <input type="hidden" name="id_storeConfronto" id="id_storeConfronto" value="${stoConfronto.xcdel}"/>
							        </c:forEach>
							    	
							    	<select id="store" name="store" onchange="chgStore(this.value)"
						          		class="form-control parsley-validated chosen-select">
							          <c:forEach items="${sto}" var="sto" varStatus="status">
			                          <option value="${sto.xcdel}">${sto.xcdel} - ${sto.xdtab}</option>
			                          </c:forEach>
	                        	  	</select>   
	                        	  	
	                        	  	</form>
				                </header>
				                
				                <div class="panel-body">
				                	<div id="flot" style="height:250px"></div>
				                </div>
				                <footer class="panel-footer bg-white">
				                  <div class="row text-center no-gutter">
				                    <div class="col-xs-6 b-r b-light">
				                      <p class="h3 font-bold m-t">${totAlarm1}</p>
				                      <p class="text-muted">${alarm1} - ${alarm1_descr}</p>
				                    </div>
				                    <div class="col-xs-6">
				                      <p class="h3 font-bold m-t">${totAlarm2}</p>
				                      <p class="text-muted">${alarm2} - ${alarm2_descr}</p>                        
				                    </div>
				                  </div>
				                </footer>
				                
				                <div class="table-responsive">
				               		<table class="table table-striped b-t b-light">
				               		<thead>
				               			<tr>
				               				<th style="width:25%">Period</th>
				               				<th style="width:25%">${alarm1} - ${alarm1_descr}</th>
				               				<th style="width:25%">${alarm2} - ${alarm2_descr}</th>
				               				<th style="width:25%">Ratio</th>
				               			</tr>
				               		</thead>
				               		<tbody>
				               			<c:forEach items="${period}" var="period" varStatus="status">
				               			<tr>
				               				<td class="b-r">${period.xcdel}</td>
				               				<td class="b-r">${period.xdtab}</td>
				               				<td class="b-r">${period.cmp1}</td>
				               				<td><b>${period.cmp2}</b></td>
				               			</tr>
				               			</c:forEach>
				               		</tbody>
				               		<tfoot>
				               			<tr>
				               				<td class="b-r"></td>
				               				<td class="b-r"><b>${tot1}</b></td>
				               				<td class="b-r"><b>${tot2}</b></td>
				               				<td></td>
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
				                <header class="panel-heading font-bold" style="height:50px;padding-top:15px">
				                
				                  <ul class="nav nav-pills pull-right">
				                    <li>
				                      <a href="#" class="panel-toggle text-muted"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a>
				                    </li>
				                  </ul>
				                  
				                Comparison Table</header>
				                <div class="panel-body">
				                	<div id="flotBottom" style="height:250px"></div>
				                	
				                	<button id="btnExport" class="btn btn-default">Esporta tabella in Excel</button>
				               	
				                </div>
				                
				                <div id="table_wrapper" class="table-responsive">
				               		<table class="table table-striped b-t b-light">
				               		<thead>
				               			<tr>
				               				<th style="width:10%">Period</th>
				               				<th style="width:15%">Benchmark ${alarm1} - ${alarm1_descr}</th>
				               				<th style="width:15%">Store ${alarm1} - ${alarm1_descr}</th>
				               				<th style="width:15%; border-right: 2px solid">Trend</th>
				               				
				               				<th style="width:15%">Benchmark ${alarm2} - ${alarm2_descr}</th>
				               				<th style="width:15%">Store ${alarm2} - ${alarm1_descr}</th>
				               				<th style="width:15%">Trend</th>
				               			</tr>
				               		</thead>
				               		<tbody>
				               			<c:forEach items="${tbottom}" var="tbottom" varStatus="status">
				               			<tr>
				               				<td class="b-r">${tbottom.xcdtb}</td>
				               				<td class="b-r">${tbottom.xcdel}</td>
				               				<td class="b-r">${tbottom.xdtab}</td>
				               				<td class="b-r" style="border-right: 2px solid"><b>${tbottom.cmp1}</b></td>
				               				
				               				<td class="b-r">${tbottom.cmp2}</td>
				               				<td class="b-r">${tbottom.cmp3}</td>
				               				<td><b>${tbottom.cmp4}</b></td>
				               			</tr>
				               			</c:forEach>
				               		</tbody>
				               		
				               		</table>
				               	</div>
				            </section>
				                
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
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
  <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>
  
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
	
  $('#store').val('${store}');
  
  function chgStore(id_store) {
	  
	  $('#store').val(id_store);
	  $('#formPage').submit();
	  
  }
  /*
  Morris.Bar({
	  element: 'bar',
	  data: [
		<c:forEach items="${sto}" var="sto" varStatus="status">
	    { y: '${sto.xcdel}', a: ${sto.cmp1} },
	    </c:forEach>
	  ],
	  xkey: 'y',
	  ykeys: ['a'],
	  labels: ['${alarm1_descr}']
	});
  */
  </script>
  
  
    <!-- Flot -->
  <script src="js/excanvas.min.js"></script>
  <script src="js/jquery.flot.min.js"></script>
  
  <script src="../../js/charts/flot/jquery.flot.tooltip.min.js"></script>
  <script src="../../js/charts/flot/jquery.flot.resize.js"></script>
  
  <script>
  
  
  
  $(function(){
	  	
	  ${dati}
	  
		$("#flot").length && $.plot($("#flot"), 
			[
				{
					data: store,
					label: " STORE"
				},
			], 
	      {
	        series: {
	            lines: {
	                show: true,
	                lineWidth: 1,
	                fill: true,
	                fillColor: {
	                    colors: [{
	                        opacity: 0.3
	                    }, {
	                        opacity: 0.3
	                    }]
	                }
	            },
	            points: {
	                radius: 3,
	                show: true
	            },
	            grow: {
	              active: true,
	              steps: 20,
	              growings:[
	                  {
	                      stepDirection: "down"
	                  }                    
	              ]
	            },
	            shadowSize: 2
	        },
	        grid: {
	            hoverable: true,
	            clickable: true,
	            tickColor: "#f0f0f0",
	            borderWidth: 1,
	            color: '#999'
	        },
	        colors: ["#f05050"],
	        xaxis:  {
	            mode: "time", timezone: "America/Chicago", timeformat: "%d/%m/%y", minTickSize: [1, "day"]
	        },
	        yaxis: {
	            ticks: 10,
	            tickDecimals: 0,
	            min:0, max: ${maxValue}
	        },
	        tooltip: true,
	        tooltipOpts: {
	          content: "Giorno %x.1 : %y.4 ",
	          defaultTheme: false,
	          shifts: {
	            x: 0,
	            y: 20
	          }
	        }
	      }
	    );  
		
		// *****************
		
		${datiConfronto}
		  
		$("#flotConfronto").length && $.plot($("#flotConfronto"), 
			[
				{
					data: storeConfronto,
					label: " COMPARISON STORE"
				},
			], 
	      {
	        series: {
	            lines: {
	                show: true,
	                lineWidth: 1,
	                fill: true,
	                fillColor: {
	                    colors: [{
	                        opacity: 0.3
	                    }, {
	                        opacity: 0.3
	                    }]
	                }
	            },
	            points: {
	                radius: 3,
	                show: true
	            },
	            grow: {
	              active: true,
	              steps: 20,
	              growings:[
	                  {
	                      stepDirection: "down"
	                  }                    
	              ]
	            },
	            shadowSize: 2
	        },
	        grid: {
	            hoverable: true,
	            clickable: true,
	            tickColor: "#f0f0f0",
	            borderWidth: 1,
	            color: '#999'
	        },
	        colors: ["#3e4589"],
	        xaxis:  {
	            mode: "time", timezone: "America/Chicago", timeformat: "%d/%m/%y", minTickSize: [1, "day"]
	        },
	        yaxis: {
	            ticks: 10,
	            tickDecimals: 0,
	            min:0, max: ${maxValue}
	        },
	        tooltip: true,
	        tooltipOpts: {
	          content: "Giorno %x.1 : %y.4 ",
	          defaultTheme: false,
	          shifts: {
	            x: 0,
	            y: 20
	          }
	        }
	      }
	    );  
		
		// ***************
		
		$("#flotBottom").length && $.plot($("#flotBottom"), 
				[
					{
						data: storeConfronto,
						label: " COMPARISON STORE"
					},
					{
						data: store,
						label: " STORE"
					},
				], 
		      {
		        series: {
		            lines: {
		                show: true,
		                lineWidth: 1,
		                fill: true,
		                fillColor: {
		                    colors: [{
		                        opacity: 0.3
		                    }, {
		                        opacity: 0.3
		                    }]
		                }
		            },
		            points: {
		                radius: 3,
		                show: true
		            },
		            grow: {
		              active: true,
		              steps: 20,
		              growings:[
		                  {
		                      stepDirection: "down"
		                  }                    
		              ]
		            },
		            shadowSize: 2
		        },
		        grid: {
		            hoverable: true,
		            clickable: true,
		            tickColor: "#f0f0f0",
		            borderWidth: 1,
		            color: '#999'
		        },
		        colors: ["#3e4589","#f05050"],
		        xaxis:  {
		            mode: "time", timezone: "America/Chicago", timeformat: "%d/%m/%y", minTickSize: [1, "day"]
		        },
		        yaxis: {
		            ticks: 10,
		            tickDecimals: 0,
		            min:0, max: ${maxValue}
		        },
		        tooltip: true,
		        tooltipOpts: {
		          content: "Giorno %x.1 : %y.4 ",
		          defaultTheme: false,
		          shifts: {
		            x: 0,
		            y: 20
		          }
		        }
		      }
		    );  
			
		
		
			  
	});  
    
    
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
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  
  </body>
</html>
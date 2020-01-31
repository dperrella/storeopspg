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
                        
		                <div class="col-md-8">    
		                
		                <section class="panel panel-default">
			                <header class="panel-heading font-bold">Chart</header>
			                <div class="panel-body">
			                	<div id="flot" style="height:250px"></div>
			                </div>
			                <footer class="panel-footer bg-white">
			                  <div class="row text-center no-gutter">
			                    <div class="col-xs-12">
			                      <p class="h3 font-bold m-t">${totAlarm1}</p>
			                      <p class="text-muted">${alarm1_descr}</p>                        
			                    </div>
			                  </div>
			                </footer>
			            </section>
			            
			            </div>
			            
			            <div class="col-md-4">
			            	
			            <section class="panel panel-default">
			                <header class="panel-heading font-bold">Bar Chart</header>
			                <div class="panel-body">
			                	<div id="bar" style="height:250px">
			                	
			                		
			                	</div>
			                </div>
			                
			                <footer class="panel-footer bg-white">
			                  <div class="row text-center no-gutter">
			                    
			                    <div class="col-xs-12">
			                      <p class="h3 font-bold m-t">${nrstores}</p>
			                      <p class="text-muted">Stores</p>                        
			                    </div>
			                  </div>
			                </footer>
			                
			            </section>	
			            
			            </div>
			            
			          </div>
			          
			          <div class="row row-sm">
			          
			          	<div class="col-md-12">
			            
			            <section class="panel panel-default">
			            	<header class="panel-heading font-bold">Data</header>
			               	
			               	<div class="panel-body">
			               		<button id="btnExport" class="btn btn-default">Esporta tabella in Excel</button>
			               	</div>
			               	
			               	<div id="table_wrapper" class="table-responsive horizontal">
			               		<table class="table table-striped b-t b-light">
			               		<thead>
			               			<tr>
			               				<th>Periodo</th>
			              				
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
		  				  
			    </section> 
	          </section>
	        </section>
                
	      </section>
            
        </section>
        <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  
  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
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
  
  Morris.Bar({
	  element: 'bar',
	  data: [
		<c:forEach items="${sto}" var="sto" varStatus="status">
	    { y: '${sto.cmp2}', a: ${sto.cmp1} },
	    </c:forEach>
	  ],
	  xkey: 'y',
	  ykeys: ['a'],
	  labels: ['${alarm1_descr}'],
	  xLabelAngle: 60
	});
  
  </script>
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  
  
  
  
  <!-- Flot -->
  <script src="js/excanvas.min.js"></script>
  <script src="js/jquery.flot.min.js"></script>
  
  <script src="../../js/charts/flot/jquery.flot.tooltip.min.js"></script>
  <script src="../../js/charts/flot/jquery.flot.resize.js"></script>
  
  <script>
  
  
  
  $(function(){
	  	
	  <c:forEach items="${sto}" var="sto" varStatus="status">
	  ${sto.xdtab}
	  </c:forEach>
	  
	  var tipo = '${tipo}';
	  
	  if(tipo=='D') {
		
		$("#flot").length && $.plot($("#flot"), 
			[
				<c:forEach items="${sto}" var="sto" varStatus="status">
				{
					data: s${sto.xcdel},
					label: " ${sto.cmp2}"
				},
				</c:forEach>
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
	        xaxis:  {
	        	ticks: 12,
	            
	        },
	        yaxis: {
	            ticks: 10,
	            tickDecimals: 0,
	            tickFormatter: function(val, axis) { return val < axis.max ? val.toFixed(2) : "<%=propLang.get("mov_in") %>";}
	            
	        },
	        tooltip: true,
	        tooltipOpts: {
	          content: "Ore %x.1 : %y.4 ",
	          defaultTheme: false,
	          shifts: {
	            x: 0,
	            y: 20
	          }
	        }
	      }
	    );  
		
	  }
	  else {
		  
		  $("#flot").length && $.plot($("#flot"), 
				[
					<c:forEach items="${sto}" var="sto" varStatus="status">
					{
						data: s${sto.xcdel},
						label: " ${sto.cmp2}"
					},
					</c:forEach>
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
		        xaxis:  {
		            mode: "time", timezone: "America/Chicago", timeformat: "%d/%m/%y", minTickSize: [1, "day"]
		        },
		        yaxis: {
		            ticks: 10,
		            tickDecimals: 0
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
		  
	  }
			  
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
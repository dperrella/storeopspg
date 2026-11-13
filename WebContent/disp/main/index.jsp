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
              
              <%@ include file="../../inc2/menuApp5.jsp" %>
              
              
              
                <section>
                  <section class="vbox">
                  	
                    <section class="scrollable padder" id="scrollable">
                    
                      <h2 class="font-thin m-b">Desk Smart Dispatcher </h2>
                      
                      <div class="row">
							
		                    <div class="col-sm-12">
		                    	<section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <span class="text-muted m-r-sm"><i class="icon-globe"></i></span>
			                      <span class="hidden-sm"><b>manager.checkpt.com</b></span>
			                    </header>
			                    <div class="panel-body">
			                    	
			                    	<div class="row">
			                    		
			                    		<div class="col-sm-6">
			                    			<div id="main" style="height:350px;"></div>
			                    			
			                    			
			                    		</div>
			                    		<div class="col-sm-6">
			                    			<div id="main2" style="height:350px;"></div>
			                    			
			                    			
			                    		</div>
			                    		
			                    	</div>
			                    	
			                    </div>
			                  </section>
		                    </div>
			                    
			                    
			                    
			          </div>
			          
			          
			          
			          
			          
			           
			           
			                
                          
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
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  
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
	
	$('.example').dataTable( {
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
	      "sDom": "<'row'<'col-sm-12'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	      "aaSorting": [],
	      "iDisplayLength": 5
	  } );
	
	</script>
	
	<!-- Echarts -->
    <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  	<script>
	// based on prepared DOM, initialize echarts instance
    var myChart = echarts.init(document.getElementById('main'));

    option = {
    		title: {
    	        text: 'Downloaded ZIP',
    	    },
    	    //color: ['#3398DB'],
    	    tooltip : {
    	        trigger: 'axis',
    	        axisPointer : {            
    	            type : 'line'   
    	        }
    	    },
    		legend: {
    	        data:[${intDownload}]
    	    },
    	    grid: {
                bottom: '20%'
            },
    	    toolbox: {
    	        feature: {
    	            saveAsImage: {}
    	        }
    	    },
    	    xAxis: {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: [${intDownload}]
    	    },
    	    yAxis: {
    	        type: 'value'
    	    },
    	    dataZoom: [
               {
                   show: true,
                   start: 50,
                   end: 100
               },
               
            ],
    	    series: [{
    	        data: [${valDownload}],
    	        type: 'line',
    	        itemStyle: {
                    normal: {
                        color: '#D14649'
                    }
                },
    	        areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#FC5457'
                        }, {
                            offset: 1,
                            color: '#EE5154'
                        }])
                    }
                },
    	    }]
    	};
    
    myChart.setOption(option);
    
    
    
    var myChart2 = echarts.init(document.getElementById('main2'));

    option2 = {
    		title: {
    	        text: 'Elaborated JSON',
    	    },
    		//color: ['#3398DB'],
    	    tooltip : {
    	        trigger: 'axis',
    	        axisPointer : {            
    	            type : 'line'   
    	        }
    	    },
    		legend: {
    	        data:[${intJson}]
    	    },
    	    grid: {
                bottom: '20%'
            },
    	    toolbox: {
    	        feature: {
    	            saveAsImage: {}
    	        }
    	    },
    	    xAxis: {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: [${intJson}]
    	    },
    	    yAxis: {
    	        type: 'value'
    	    },
    	    dataZoom: [
               {
                   show: true,
                   start: 50,
                   end: 100
               },
               
            ],
            series: [{
   	        data: [${valJson}],
   	        type: 'line',
   	        itemStyle: {
                   normal: {
                       color: '#23A05C'
                   }
               },
   	        areaStyle: {
                   normal: {
                       color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                           offset: 0,
                           color: '#32F189'
                       }, {
                           offset: 1,
                           color: '#28B569'
                       }])
                   }
               },
   	    }]
    	};
    
    
    myChart2.setOption(option2);
 	
 	
 	
    
  </script> 
	
	
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
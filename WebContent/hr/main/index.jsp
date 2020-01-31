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
              <%@ include file="../../inc2/menuApp7.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
              		
              		
	                <!--     
                      <h2 class="font-thin m-b">Desk Smart HR <%//=propLang.get("customer") %> - <%=propIdx.get("versione") %> 2.0.0</h2>
	            	-->
	            	
			              <div class="row m-t-md row-sm">
			              	
			              	<div class="col-xs-12">
		
								<div class="row m-b text-center">
				                    <div class="col-sm-4">
				                      <div class="bg-primary lter wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="icon-users i-lg"></i> <%=propIdx.get("nrdip") %></span>
				                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrcli}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-sm-4">
				                      <div class="bg-black wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fa fa-building-o i-lg"></i> <%=propIdx.get("nrdep") %></span>
				                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrdep}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-sm-4">
				                      <div class="bg-light dker wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fa fa-calendar icon i-lg"></i> <%=propIdx.get("nrplan") %></span>
				                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrplan}</span>
				                        </a>
				                      </div>
				                    </div>
				                  </div>
							
							  </div>
							  
							</div>
							
							
							
							
							<div class="row">
								<!-- 
			                    <div class="col-sm-4">
			                      	<section class="panel panel-default">
					                    <header class="panel-heading bg-light">
					                      	<span><b><%=propIdx.get("ultplan") %></b></span>
				                      	</header>
					                    
					                      	
				                      	<div>
				                        	
				                        	<header class="panel-heading bg-light" style="background-color:#fff">
					                          <div class="clearfix">
					                            <div class="clear">
					                              <div class="h3 m-t-xs m-b-xs">
					                                10 <%=propIdx.get("ultplan") %>
					                                <i class="fa fa-circle pull-right text-xs m-t-xs text-danger"></i>
					                              </div>
					                            </div>
					                          </div>
					                        </header>
				                        	
				                        	<div class="table-responsive">
								                <table class="table table-striped b-t m-b-none" id="vistable" data-ride="datatables">
								                  <thead>
													  <tr>
												        <th width="5%">No.</th>
												        <th width="55%">Department</th>
												        <th width="40%">Week</th>
												      </tr>
												  </thead>
						      					  
						      					  <tbody>
						      					  <c:set var="count" value="0" scope="page" />
												  <c:forEach items="${plan}" var="plan" varStatus="status">
												  <c:set var="count" value="${count + 1}" scope="page"/>
					            					  <tr>
					            					  	<td>${count}.</td>
					            					  	<td><a href="../planner/planner-list-tab2.htm?tp=I&year=${plan.cmp1}&week=${plan.cmp2}&id_departments=${plan.cmp3}">${plan.cmp4}</a></td>	
					            					  	<td>${plan.cmp1} <%=propIdx.get("sett") %> ${plan.cmp2}</td>
					            					  </tr>
				            					  </c:forEach>
				       
							  					  </tbody>
							  					</table>
							  				</div>
				                        </div>
					                      	
					                    
					              </section>
			                    </div>
			                    -->
			                    <div class="col-sm-12">
								  
								  
								  
								  <section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
				                      <span class="hidden-sm"><b>HeatMap</b></span>
				                      </header>
				                    <div class="panel-body">
				                    
				                    	<div id="main2" style="height:${height}px"></div>
				                     
				                    </div>
				                  </section>
				                  
				                  
								</div>
							  </div>	
			                    
			                
		                </section> 
		              </section>
		            </section>
		            
		            
		            
		            <!-- side content -->
		            
		            <aside class="aside-md bg-dark dker" id="sidebar">
		              <section class="vbox animated fadeInRight">
		                <section class="w-f-md scrollable hover">
		                  
		                  <div class="wrapper bg-black"><h4 class="font-thin m-t-xs text-white"><%=propIdx.get("ultplan") %></h4></div>
		                  
		                  <ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
		                    <c:forEach items="${plan}" var="plan" varStatus="status">
				            <li class="list-group-item">
		                      
		                      <div class="clear">
		                        <div><a href="../planner2/planner.htm?tp=I&year=${plan.cmp1}&week=${plan.cmp2}&id_departments=${plan.cmp3}">${plan.cmp4}</a></div>
		                        <small class="text-muted smallest">${plan.cmp1} <%=propIdx.get("sett") %> ${plan.cmp2}</small>
		                      </div>
		                    </li>
		                    </c:forEach>
		                  </ul>
		                </section>
		                <footer class="footer footer-md bg-black">
		                  <form action="../../hr/planner/planification-list.htm" class="" role="search">
		                    <div class="form-group clearfix m-b-none">
		                      <div class="input-group m-t m-b">
		                        <span class="input-group-btn">
		                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
		                        </span>
		                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border" placeholder="<%=propIdx.get("cercaplan") %>">
		                      </div>
		                    </div>
		                  </form>
		                </footer>
		              </section>              
		            </aside>
		            
		            <!-- / side content -->
		           	
		  				  
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
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  
  <script>
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
	
	function setSelectPicker() {
		$('.selectpicker').selectpicker();
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
  	
  	
  	
  	
	 // HEATMAP
    // based on prepared DOM, initialize echarts instance
    var myChart2 = echarts.init(document.getElementById('main2'));
	
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
    myChart2.setOption(option2);
	
	
  </script>
  
  </body>
</html>
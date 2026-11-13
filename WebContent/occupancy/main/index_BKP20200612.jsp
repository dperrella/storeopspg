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
<html class="app">
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
		z-index: 1;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
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
              
              
              
                <!-- menu app -->
              	<%@ include file="../../inc2/menuApp16.jsp" %>
              	
              	<!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b><%=propLang.get("pop_us_sel") %> <%=propLang.get("sm_rol_5") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                	<li class="list-group-item">
		                		<a href="../report/report-counter-modal.jsp?tipo=D"
		                		class="getModal" data-toggle="modal">
		                		<i class="fa fa-sort-numeric-asc"></i> <%=propLang.get("tipo_day") %></a></li>
	                        
	                        <!-- 
	                        <li class="list-group-item">
	                        	<a href="../report/report-counter-modal.jsp?tipo=W"
		                		class="getModal" data-toggle="modal">
	                        	<i class="fa fa-list-ol"></i> <%=propLang.get("tipo_week") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="../report/report-counter-modal.jsp?tipo=M"
		                		class="getModal" data-toggle="modal">
	                        	<i class="icon-calendar"></i> <%=propLang.get("tipo_month") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="../report/report-counter-modal.jsp?tipo=P"
		                		class="getModal" data-toggle="modal">
	                        	<i class="fa fa-sort-alpha-asc"></i> <%=propLang.get("tipo_period") %></a></li>
	                        -->
	                    </ul>
	                    
	                    <div class="wrapper">
	                    	
	                    	<!-- 
		                    <label>Counter Type</label>
		                    <div class="btn-group btn-group-justified">
			                  <a href="index.htm?verso=I" onclick="showLoader()" class="btn btn-default ${chkI}">IN</a>
			                  <a href="index.htm?verso=O" onclick="showLoader()" class="btn btn-default ${chkO}">OUT</a>
			                </div>
	                    	-->
	                    </div>
                        
                      </div>
                      	
                    </section>
                    
                    
                    <footer class="footer footer-md bg-light lt">
	                  
	                  <div class="form-group clearfix m-b-none">
	                    <div>
	                     
	                     	<!-- 
	                     	<div class="btn-group btn-group-justified">
			                  <a href="#" class="btn btn-default active">Counter</a>
			                  <a href="#" class="btn btn-default" disabled>Occupancy</a>
			                </div>
			                -->
			                <form method="post" action="index.htm" class="navbar-form navbar-left input-s-lg m-b  m-l-n-xs hidden-xs" role="search">
							  <div class="form-group" style="margin-top:12px" >
							    <div class="input-group">
							      <span class="input-group-btn">
							        <button type="submit" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
							      </span>
							      <input type="text" name="q" value="${qq}" class="form-control input-sm no-border rounded" placeholder="Search Store">
							    </div>
							  </div>
							</form>
	                    </div>
	                  </div>
	                  
	                </footer>
	                
                  </section>
                  
                  
	                
                </aside>
                <!-- / side content -->
              
              	<section>
              		<section class="vbox stretch">
		                
		                <section class="vbox">
		                
		                	
		                
                    		<section class="scrollable" id="mainsection">
		                
		                		<section id="map" style="min-height:450px;">          
		                		</section>
		                		<footer class="footer bg-white" style="padding:15px">
			                    	<!-- Heatmap ${min} - ${max} -->
			                    	
			                    	<div class=" clearfix text-center">
			                    	
			                    			<!-- 
	                    					Min: <b>${min}</b>&nbsp;&nbsp;
					                    	<div class="btn-group" align="center">
							                  <button type="button" class="btn btn-default" onclick="chgMap(0)"><i class="fa fa-circle" style="color:#414883"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(1)"><i class="fa fa-circle" style="color:#676473"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(2)"><i class="fa fa-circle" style="color:#8e8165"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(3)"><i class="fa fa-circle" style="color:#a7945d"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(4)"><i class="fa fa-circle" style="color:#d5b754"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(5)"><i class="fa fa-circle" style="color:#efb754"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(6)"><i class="fa fa-circle" style="color:#e78842"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(7)"><i class="fa fa-circle" style="color:#e1643b"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(8)"><i class="fa fa-circle" style="color:#df4c37"></i></button>
							                  <button type="button" class="btn btn-default" onclick="chgMap(9)"><i class="fa fa-circle" style="color:#dc3a34"></i></button>
							                </div>
											&nbsp;&nbsp;Max: <b>${max}</b> - <a href="#" class="btn btn-default" onclick="chgMap(999)">All</a>	
											-->		
											
													
													<div class="btn-group" align="center">
													<c:forEach items="${paging}" var="paging" varStatus="status">
														<a href="index.htm?page=${paging.xcdel}" onclick="showLoader2()" class="btn btn-default ${paging.xdtab}" >${paging.xcdel}</a>
									                </c:forEach>
									                	<!-- 
									                	<select name="nr_record" class="form-control">
									                		<option value="8">8</option>
									                		<option value="16">16</option>
									                		<option value="24">24</option>
									                		<option value="32">32</option>
									                		<option value="40">40</option>
									                		<option value="48">48</option>
									                	</select>
									                	-->
									                </div>	
												
											                
							        </div>
							        
							         <form id="_formPage" name="_formPage" action="../../occupancy/report/report-occupancy.htm" 
			                 		 	method="post">
			                 		 	
			                 		 	<input type="hidden" name="tipo" id="_tipo" value="D">
			                    	 	<input type="hidden" name="id_store" id="_id_store" value=""/>
			                    	 	<input type="hidden" name="idstores" id="_idstores" value=""/>
			                    	 	<input type="hidden" name="tipoPeriod" id="_tipoPeriod" value="D"/>
			                    	 	<input type="hidden" name="kpi" id="_kpi" value="${kpi}"/>
			                 		 	<input type="hidden" name="day" id="_day" value="${oggi}"/>
			                 		 	<input type="hidden" name="_dtda" id="_dtda" value="${oggi}"/>
			                 		 	<input type="hidden" name="_dtal" id="_dtal" value="${oggi}"/>
			                 		 	<input type="hidden" name="verso" id="_verso" value="${_verso}">
			                    	 	
			                 		 </form>
			                 
							        
							    </footer>
		                		
		                		<section class="padder-lg m-t-lg">
		                		
		                			<section class="panel panel-default">
					                    <header class="panel-heading bg-light">
					                      
					                      <span class="text-muted m-r-sm"><i class="icon-bar-chart icon"></i></span>
					                      <span class="hidden-sm"><b>Smart Occupancy</b></span>
					                      
					                    </header>
					                    <div class="panel-body">
					                    	
					                    	<!-- Barchart -->
								    		<div id="bar-chart" style="height:350px"></div>
								    
					                    </div>
					                </section>
		                		
					                <div class="row row-sm m-t">
								  		
								  		<c:forEach items="${tiles}" var="tiles" varStatus="status">
								  		<div class="col-xs-12 col-md-6 col-lg-3">
								  		  
								  		  <section class="panel panel-default" id="_bg${tiles.cmp6}">
								  		  <div class="panel-body text-center">
					                      	
					                      	<div class="clearfix">
				                            <span class="pull-left thumb-sm avatar b-3x m-r" id="ico_${tiles.xcdtb}">
				                              <img src="../../images/loader_xs.gif" style="width:20px;margin-top:10px;;margin-bottom:10px"/>
				                            </span>
				                            <span class="pull-right m-l">
				                            	<a href="../popup/widgetModal.jsp?id=${tiles.xcdel}"
		                							data-toggle="ajaxModal" class="btn btn-default">
				                              		<img src="rt.png" style="width:24px"/>
				                              	</a>
				                            </span>
				                            
				                            <div class="clear">
				                              <div class="h5 m-t-xs m-b-xs text-ellipsis">
				                                ${tiles.xdtab} 
				                              </div>
				                            </div>
				                          </div>
					                      	
					                      	<div class="inline">
						                        <div id="_easypie${tiles.cmp6}" class="easypiechart" data-percent="${tiles.cmp4}" data-bar-color="${tiles.cmp2}" data-line-width="16" data-loop="false" data-size="188">
						                          <div>
						                            <span class="h2 m-l-sm step"><a href="#" onclick="callReport('${tiles.xcdel}','${tiles.cmp3}')" id="_occ${tiles.cmp6}">-</a></span>
						                            
						                            <div class="text text-sm">
						                            Occupancy
						                            </div>
						                            
						                          </div>
						                          
						                        </div>
						                        
						                    </div> 
						                    <br/>
						                    <span class="text-sm text-muted"><small id="_ts${tiles.cmp6}">Last Event: ${tiles.cmp3}</small></span>
						                    <input type="hidden" id="__ts${tiles.xcdel}" value=""/>
						                                     
						                  </div>
						                  </section>
								  		
								  		</div>
								  		</c:forEach>
								  		
								  	</div>
								  	
								</section>
							</section>
						</section>
		            </section>
		        </section>
		        
              </c:if>
              
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
  <script src="../../js/chosen/chosen.jquery.min.js?v=2"></script>
  
  <!--  modal -->
  <script src="../../js/bootstrap-modal.js"></script>
  
  <!-- EasyPie -->
  <script src="../../js/charts/easypiechart/jquery.easy-pie-chart.js"></script>
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js?v=2"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  
  <!-- The core Firebase JS SDK is always required and must be listed first -->
  <script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-app.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-database.js"></script>

  
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
  
  function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
  function openModalCus() {
  	$('#myModalCus').modal('show');
  }
  function dismissCus() {
  	var idcus = $('#idcus').val();
  	if(idcus=='') {
  		$('#chgUser').submit();
  	}
  }
  
  function selpick() {
	$('.ss').selectpicker({
	    liveSearch: true,
	    tickIcon: 'fa fa-check'
	 });
  }
  
  function selectCustomer(id) {
		
	var idcus = $('#'+id).val();
	$('#idcus_frm').val(idcus);
		
	$('#chgUser').submit();
  }
	
  function setSelectPicker() {
	$('.selectpicker').selectpicker();
  }

  var idcus = '${idcus}';
  
  if(idcus!='') {
	  //based on prepared DOM, initialize echarts instance
	  var myChart = echarts.init(document.getElementById('bar-chart'));
		
		// specify chart configuration item and data
	  option = {
			color: ['#3F4787'],
			    
		    title : {
		        text: 'Last Event Store Occupancy',
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            
		            type : 'shadow'      
		        }
		    },
		    //legend: {
		    //    data:[${kpi_in}]
		    //},
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '0%',
		        containLabel: true
		    },
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
		        	show: false,
		        	//axisLabel: { rotate: 90, width: 50},
		            type : 'category',
		            //showMaxLabel: true,
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
		            name:'Last Event',
		            type:'bar',
		            barWidth: '80%',
		            data:[${series}],
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
	  myChart.setOption(option);
	  
	  
	  
	  //myChart.on('click', 'series', function () {
	  //	  alert('series');  
	  //});
	  
	  myChart.on('click', function (params) {
	    
		  var store = params.name;
		  
		  
		  var idstore = store.split(" | ")[1].replace('ID:','');
		  var dt = store.split(" | ")[2].replace('Date:','');
		  //dt = dt.substring(8,10)+"-"+dt.substring(5,7)+"-"+dt.substring(0,4);
		  
		  console.log(idstore+" "+dt);
		  
		  showLoader();
		  callReport(idstore, dt);
		  //window.open('https://www.baidu.com/s?wd=' + encodeURIComponent(params.name));
	  });
		
  }
  
  
  
  	$('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'top',
	    autoclose: true
	});  
  
  	
	function setCal() {
  		
  		//alert('aaa');	
  		
  		 $('.datepicker-input2').datepicker({
  		    format: 'dd-mm-yyyy',
  		    orientation: 'bottom',
  		    autoclose: true
  		});  
  		
  	}
  	
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		
		//alert($( "#mainsection" ).height());
		
		var h = $( "#mainsection" ).height() - 65;
		
		$('#map').css("height",h+"px");
		
		<c:if test = "${nr_customers == 1}">
		  
		initMap();
		
		</c:if>
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
	$('#yearWeek').val('${currentYear}');
  	$('#yearMonth').val('${currentYear}');
  	
  	var $radios2 = $('input:radio[name=orario]');
    if($radios2.is(':checked') === false) {
        $radios2.filter('[value=${orario}]').prop('checked', true);
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
  			
  			showLoader();	
  		}
 		
  		
  	}
  	
  	
  	
  	
  	var map;
	function initMap() {
		
		
		
        map = new google.maps.Map(document.getElementById('map'), {
          //zoom: 3,
          //center: {lat: 0, lng: 0}
        	scrollwheel: false,
            navigationControl: true,
            mapTypeControl: true,
            scaleControl: true,
            draggable: true,
        });

        var markerBounds = new google.maps.LatLngBounds();
        
        for (i = 0; i < locations.length; i++) {
            // Generate 10 random points within North East USA
            randomPoint = new google.maps.LatLng( locations[i].lat, 
            		locations[i].lng);
            
            var pinColor = locations[i].color.replace('#','');
            var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
                new google.maps.Size(21, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(10, 34));
            
            img = pinImage;
	 		  
            var marker = new google.maps.Marker({
              position: randomPoint, 
              icon: img,
              title: locations[i].desc,
              idstore: locations[i].id,
              map: map
            });
            
            
			google.maps.event.addListener(marker, 'click', function() {
				var marker = this;
				//alert("id store:" + this.idstore);
				map.setZoom(17);
                map.setCenter(this.getPosition());
                
                var p = ''+this.getPosition();
                var temp = p.split(",");
                var lat = temp[0].replace("(","");
                var lng = temp[1].replace(")","");;
                
                moveTo(this.idstore, lat, lng, false);
                
			});
            // Extend markerBounds with each random point.
            markerBounds.extend(randomPoint);
        }
        
        map.fitBounds(markerBounds);
          
	  }
	
	  
	  
      var locations = [
    	  <c:forEach items="${sto}" var="sto" varStatus="status">
          
    	  <c:if test = "${sto.latitude != null}">
        {lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
          </c:if>
        
        </c:forEach>
      ]
      
      function moveTo(id, lat, lng, move) {
    	  //alert(id);	
    	  
    	  showLoader2();
    	  
    	  if(move) {
    		  var center = new google.maps.LatLng(lat, lng);
    		  map.setZoom(17);
    		  map.panTo(center);
    	  }
      }
  	
  	
  	
  	
  	
  	
  	
  	function callReport(id_store, dt) {
  		
  		//alert(id_store);
		$('#_id_store').val(id_store);
		$('#_idstores').val(id_store);
		
		dt = $("#__ts"+id_store).val();
		
		var dt = dt.substring(8,10)+"-"+dt.substring(5,7)+"-"+dt.substring(0,4);
		$('#_day').val(dt);
		$('#_dtda').val(dt);
		$('#_dtal').val(dt);
		
		
		$('#_formPage').submit();
  		
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
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	function selectCustomer(id) {
		
		var idcus = $('#'+id).val();
		$('#idcus_frm').val(idcus);
			
		$('#chgUser').submit();
	  }
		
	  function setSelectPicker() {
		$('.selectpicker').selectpicker();
	  }
	  
	  function selAll() {
		  
		var values="${idstores}";
	    $.each(values.split(","), function(i,e){
	        $("#id_store option[value='" + e + "']").prop("selected", true);
	    });
	    
	    $('.ss').selectpicker('refresh');
		  
	  }
	  
	function clearSelection() {
		  
		var values="${idstores}";
	    $.each(values.split(","), function(i,e){
	        $("#id_store option[value='" + e + "']").prop("selected", false);
	    });
		
	    $('.ss').selectpicker('refresh');
		
	  }
	
	function callWeather() {
		
		var count = 0;
    	
		<c:forEach items="${q}" var="q" varStatus="status">
		
		var url = 'https://api.weatherstack.com/current?access_key=5aa42b107c6e702799e61df4218098dc&query=<c:out value="${q}" />';
		console.log(url);
		
		$.ajax({
		      url: 'https://api.weatherstack.com/current',
		      //type: 'POST',
		      dataType: 'json',
		      async: true,
		      data: {
	    	    access_key: '5aa42b107c6e702799e61df4218098dc',
	    	    query: '<c:out value="${q}" />'
	    	  },
		      success: function (data) {
					
		    	console.log(JSON.stringify(data));
		    	  
		    	var ln = ''+data.length;
		    	if(ln=='undefined') {
		    		
		    		$('#ico_1').html('<img src="'+data.current.weather_icons[0]+'"/>');
		    		
		    	}
		    	else {
		    	
		        	$.each(data, function(index, data) {
		  				count+=1;
		  				
		  				$('#ico_'+count).html('<img src="'+data.current.weather_icons[0]+'"/>');
		        		
		        	});
		        	
		    	}
	        	//alert(count+' waether loaded!');
		    	  
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		</c:forEach>
		
		/*
		var url = "https://api.weatherstack.com/current?access_key=5aa42b107c6e702799e61df4218098dc&query=${query}";
		console.log(url);
		
		$.ajax({
		      url: 'https://api.weatherstack.com/current',
		      //type: 'POST',
		      dataType: 'json',
		      async: true,
		      data: {
	    	    access_key: '5aa42b107c6e702799e61df4218098dc',
	    	    query: '${query}'
	    	  },
		      success: function (data) {
					
		    	console.log(JSON.stringify(data));
		    	  
		    	var count = 0;
	        	
	        	$.each(data, function(index, data) {
	  				count+=1;
	  				
	  				$('#ico_'+count).html('<img src="'+data.current.weather_icons[0]+'"/>');
	        		
	        	});
	        	//alert(count+' waether loaded!');
		    	  
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		*/
		
	}
	
	callWeather();
	
	function chgMap(v) {
		//console.log(v);
		
		
		if(v==0) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 0 && sto.country <= 10}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==1) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 10 && sto.country <= 20}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==2) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 20 && sto.country <= 30}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==3) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 30 && sto.country <= 40}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==4) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 40 && sto.country <= 50}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==5) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 50 && sto.country <= 60}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==6) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 60 && sto.country <= 70}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==7) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 70 && sto.country <= 80}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==8) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 80 && sto.country <= 90}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else if(v==9) {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	 <c:if test="${sto.country > 90 && sto.country <= 100}">
		    	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          	</c:if>
		    	  </c:if>
	        
	        	</c:forEach>
	      	];
		}
		else  {
			locations = [
	    	  <c:forEach items="${sto}" var="sto" varStatus="status">
	          	
		    	  <c:if test = "${sto.latitude != null}">
		    	 	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
		          </c:if>
	        
	        	</c:forEach>
	      	];
		}
		
		initMap();
	}
	
	function control(frm) {
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
  			
  			
  			
  			
  			//$('.modal').remove(); $('div.modal-backdrop').remove();
  			showLoader();
  		}
	}
	
	
	
	// Your web app's Firebase configuration
	  var firebaseConfig = {
	    apiKey: "AIzaSyCMslIC8kpABVxT6aNCvFlzt4BUN70hCDU",
	    authDomain: "rnprj-a2be7.firebaseapp.com",
	    databaseURL: "https://rnprj-a2be7.firebaseio.com",
	    projectId: "rnprj-a2be7",
	    storageBucket: "rnprj-a2be7.appspot.com",
	    messagingSenderId: "845204873852",
	    appId: "1:845204873852:web:1693d4cf07826cc8a01ff9"
	  };
	  // Initialize Firebase
	  firebase.initializeApp(firebaseConfig);
	  
	  var database = firebase.database();	
	  
	  try {
	      mRef.off('value');
	  }
	  catch(err) {
	      console.log(err);
	  }
	    
	  mRef = database.ref("occupancy");
	  mRef.orderByKey().startAt("${accountCode}-").endAt('${accountCode}\uf8ff').on('value', (snapshot) => {
		  let datas = snapshot.val();
		  
	      //console.log(JSON.stringify(datas));
	      var max = 0;
	      var xaxis = '';
	      var series = '';
	      <c:forEach items="${tiles}" var="tiles" varStatus="status">
	      	
	        var cur = 0;
	      	try {
	      		cur = datas["${accountCode}-${tiles.cmp6}"].data[0].presenze;
	      		if(cur<0)
	      			cur = 0;
		      	if(cur>max)
		      		max=cur;
		      	
		      	xaxis+='${tiles.xdtab} | ID:${tiles.xcdel} | Date:'+datas["${accountCode}-${tiles.cmp6}"].tstamp+',';
		      	series+=cur+',';
	      	}
	      	catch(err) {
	      		cur = 0;
	      		xaxis+='${tiles.xdtab} | ID:${tiles.xcdel} | Date:N/D,';
	      		series+='0,';
	      	}
	      	
	      </c:forEach>
	      
	      xaxis = xaxis.slice(0, -1);
	      series = series.slice(0, -1);
	      
	      //console.log(xaxis);
	      //console.log(series);
	      
	      
	      <c:forEach items="${tiles}" var="tiles" varStatus="status">
	      	
	        var old = parseInt($('#_occ${tiles.cmp6}').text());
	        var cur = 0;	
	        try {
		      	
		      	cur = datas["${accountCode}-${tiles.cmp6}"].data[0].presenze;
		      	if(cur<0)
		      		cur = 0;
		      	
		      	$('#_easypie${tiles.cmp6}').data('easyPieChart').update((cur*100)/max);
		      	
		      	if(old!=datas["${accountCode}-${tiles.cmp6}"].data[0].presenze) {
			      	
		      		
		      		var perc = ((datas["${accountCode}-${tiles.cmp6}"].data[0].presenze/datas["${accountCode}-${tiles.cmp6}"].data[0].threshold) * 100).toFixed(0);
		      		//console.log(datas["${accountCode}-${tiles.cmp6}"].data[0].text+' '+perc);
			      	
		      		
		      		if(perc >= 100) {
		      			$('#_bg${tiles.cmp6}').addClass('bg-danger');
		      			$('#_bg${tiles.cmp6}').removeClass('bg-warning');
		      			
		      		}
		      		else if(perc >= parseInt(datas["${accountCode}-${tiles.cmp6}"].data[0].perc_warning)){
		      			$('#_bg${tiles.cmp6}').addClass('bg-warning');
		      			$('#_bg${tiles.cmp6}').removeClass('bg-danger');
		      		}
		      		else if(perc >= 0){
		      			$('#_bg${tiles.cmp6}').removeClass('bg-warning');
		      			$('#_bg${tiles.cmp6}').removeClass('bg-danger');
		      		}
		      		
		      		$('#_occ${tiles.cmp6}').html(cur);
			      	$('#_occ${tiles.cmp6}').fadeOut(200).fadeIn(200).fadeOut(200).fadeIn(200);
			      	$('#_ts${tiles.cmp6}').html(datas["${accountCode}-${tiles.cmp6}"].tstamp);
			    
			      	$('#__ts${tiles.xcdel}').val(datas["${accountCode}-${tiles.cmp6}"].tstamp);
				    
			      	
			      	//easypie();
		      	}
	      	}
	      	catch(err) {
	      		
	      		$('#_occ${tiles.cmp6}').html("-");
		      	$('#_occ${tiles.cmp6}').fadeOut(200).fadeIn(200).fadeOut(200).fadeIn(200);
		      	$('#_ts${tiles.cmp6}').html("N/D");
		    
		      	$('#__ts${tiles.xcdel}').val("N/D");
			
	      	}
	      	
	      	
	      	
	      	
	      	
	      	
	      
	      </c:forEach>
	      //parseData(datas)
	      
	      
	      option = {
	    			color: ['#3F4787'],
	    			    
	    		    title : {
	    		        text: 'Last Event Store Occupancy',
	    		    },
	    		    tooltip : {
	    		        trigger: 'axis',
	    		        axisPointer : {            
	    		            type : 'shadow'      
	    		        }
	    		    },
	    		    //legend: {
	    		    //    data:[${kpi_in}]
	    		    //},
	    		    grid: {
	    		        left: '3%',
	    		        right: '4%',
	    		        bottom: '0%',
	    		        containLabel: true
	    		    },
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
	    		        	show: false,
	    		        	//axisLabel: { rotate: 90, width: 50},
	    		            type : 'category',
	    		            //showMaxLabel: true,
	    		            //axisLabel: { rotate: 45, },
	    		            // Lista negozi
	    		            data : xaxis.split(',')
	    		        }
	    		    ],
	    		    yAxis : [
	    		        {
	    		            type : 'value'
	    		        }
	    		    ],
	    		    series : [
	    		        {
	    		            name:'Last Event',
	    		            type:'bar',
	    		            barWidth: '80%',
	    		            data:series.split(','),
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
	    	  myChart.setOption(option);
	  });	
	  
	  
	  function getCol(perc) {
			
			var col = "";
			
			if(perc<=0) 
				col = "#ffffff";
			else if(perc>0&&perc<=10)
				col = "#414883";
			else if(perc>10&&perc<=20)
				col = "#676473";
			else if(perc>20&&perc<=30)
				col = "#8e8165";
			else if(perc>30&&perc<=40)
				col = "#a7945d";
			else if(perc>40&&perc<=50)
				col = "#d5b754";
			else if(perc>50&&perc<=60)
				col = "#efb74d";
			else if(perc>60&&perc<=70)
				col = "#e78842";
			else if(perc>70&&perc<=80)
				col = "#e1643b";
			else if(perc>80&&perc<=90)
				col = "#df4c37";
			else if(perc>90&&perc<=100)
				col = "#dc3a34";
			
			return col;
			
		}
	
  </script> 
  <c:if test = "${nr_customers == 1}">
  <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCy-BidiaPvw08cS-ZytcF8MRuMTUp10Mk&callback=initMap">
  </script>
  </c:if>
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
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
              	<%@ include file="../../inc2/menuApp15.jsp" %>
              
              	<!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b><%=propLang.get("pop_us_sel") %> <%=propLang.get("sm_rol_4") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                	<li class="list-group-item active"><a href="#dayTab" data-toggle="tab" onclick="$('#tipo').val('D')">
		                		<i class="fa fa-sort-numeric-asc"></i> <%=propLang.get("tipo_day") %></a></li>
	                        <li class="list-group-item"><a href="#weekTab" data-toggle="tab" onclick="$('#tipo').val('W')">
	                        	<i class="fa fa-list-ol"></i> <%=propLang.get("tipo_week") %></a></li>
	                        <li class="list-group-item"><a href="#monthTab" data-toggle="tab" onclick="$('#tipo').val('M')">
	                        	<i class="icon-calendar"></i> <%=propLang.get("tipo_month") %></a></li>
	                        <li class="list-group-item"><a href="#periodTab" data-toggle="tab" onclick="$('#tipo').val('P')">
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
                      <h2 class="font-thin m-b">Desk Smart Counter+ <%=propLang.get("versione") %> 1.0.0</h2>
                      	
						  <div class="row row-sm">
							<div class="col-xs-12">
								
			                  <div id="msg">${msg}</div>
			                  
			                  
			                  <form id="formPage" name="formPage" action="../../counter2/report/report-counter.htm" 
			                  	method="post" onsubmit="return control()">
			                  
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="icon-bar-chart icon"></i></span>
			                      <span class="hidden-sm"><b>Smart Counter</b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                      
			                      <div class="form-group pull-in clearfix">
							        <div class="col-sm-11">
				                      <label><%=propLang.get("sel_stores") %></label>
							          <select multiple id="id_store" name="id_store" data-required="true" 
							          	class="form-control parsley-validated chosen-select">
								          <c:forEach items="${sto}" var="sto" varStatus="status">
				                          <option value="${sto.idStore}">${sto.description}</option>
				                          </c:forEach>
		                        	  </select>
		                        	</div>
		                        	<div class="col-sm-1">
		                        	<label>&nbsp;</label>
		                        	<a href="#" onclick="selAll()"
		                        		class="btn btn-default btn-block btn-sm"><%=propLang.get("sel_all") %></a></div>
		                          </div>   
	                        	  
			                      <div class="tab-content">              
			                        <div class="tab-pane active" id="dayTab">
			                        	<div class="form-group pull-in clearfix">
			                        		<div class="col-sm-12">
									          <label><%=propLang.get("sel_day") %></label>
									          <input class="datepicker-input2 form-control" name="day" id="day" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									    </div>
									</div>
			                        <div class="tab-pane" id="weekTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearWeek" name="yearWeek" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana(this.value)">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
										          <option value="2019">2019</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-10">
									          <label><%=propLang.get("sel_week") %></label>
									          <select id="week" name="week" data-required="true" class="form-control parsley-validated">
										          <option value="">-</option>
										          <c:forEach items="${weeks}" var="weeks" varStatus="status">
						                          <option value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
						                          </c:forEach>
				                        	  </select>   
									        </div>
								    	</div>
								    	
			                        </div>
			                        <div class="tab-pane" id="monthTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearMonth" name="yearMonth" data-required="true" class="form-control parsley-validated">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
										          <option value="2019">2019</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-10">
									          <label><%=propLang.get("sel_month") %></label>
									          <select id="month" name="month" data-required="true" class="form-control parsley-validated">
										          <option value="">-</option>
										          <c:forEach items="${mn}" var="mn" varStatus="status">
						                          <option value="${mn.xcdel}" ${mn.cmp1}>${mn.xcdel}: ${mn.xdtab}</option>
						                          </c:forEach>
				                        	  </select>   
									        </div>
								    	</div>
								    	
			                        </div>
			                        
			                        <div class="tab-pane" id="periodTab">
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-6">
									          <label><%=propLang.get("sel_dtda") %></label>
									          <input class="datepicker-input2 form-control" name="dtda" id="dtda" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									        
									        <div class="col-sm-6">
									          <label><%=propLang.get("sel_dtal") %></label>
									          <input class="datepicker-input2 form-control" name="dtal" id="dtal" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
									        </div>
								    	</div>
								    </div>
								    
								    <!-- Map -->
								    <div id="map" style="height:350px"></div>
								    
		            
								    <!-- Barchart -->
								    <div id="bar-chart" style="height:350px"></div>
								    
			                      </div>
			                    </div>
			                    <footer class="panel-footer text-right bg-light lter">
			                    	<input type="hidden" name="tipo" id="tipo" value="D">
			                    	
							      	<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("btn_esegui_report") %>"/>
							    </footer>
			                  </section>
			                  
			                  </form>
						
							</div>
						
					  	</div>
					  	
					  	<div class="row row-sm">
					  		
					  		<c:forEach items="${tiles}" var="tiles" varStatus="status">
					  		<div class="col-xs-12 col-md-6 col-lg-3">
					  		  
					  		  <section class="panel panel-default">
					  		  <div class="panel-body text-center">
		                      	<h5 class="text-ellipsis">${tiles.xdtab}</h5>
			                    <div class="inline">
			                        <div class="easypiechart" data-percent="${tiles.cmp4}" data-bar-color="${tiles.cmp2}" data-line-width="16" data-loop="false" data-size="188">
			                          <div>
			                            <span class="h2 m-l-sm step"><a href="#" onclick="callReport('${tiles.xcdel}')">${tiles.cmp1}</a></span>
			                            
			                            <div class="text text-sm">
			                            <c:if test = "${tiles.xcdtb!=''}">
											${tiles.xcdtb}
										</c:if>
			                            Counts
			                            </div>
			                          </div>
			                        </div>
			                    </div>                      
			                  </div>
			                  </section>
					  		
					  		</div>
					  		</c:forEach>
					  	</div>
					  	
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
  
  
  <!-- EasyPie -->
  <script src="../../js/charts/easypiechart/jquery.easy-pie-chart.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  
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
		        text: 'Today Store Count',
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            
		            type : 'shadow'      
		        }
		    },
		    legend: {
		        data:[${kpi_in}]
		    },
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
		            name:'Today',
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
		
  }
  
  
  
  $('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'top',
	    autoclose: true
	});  
  
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
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
  	
  	function selAll() {
  		$('#id_store option').prop('selected', true);
  		$('#id_store').trigger("chosen:updated");
  	}
  	
  	function reloadForm2() {
  		
  		$('#formPage').removeAttr('onsubmit')
  		
  		$('#formPage').attr('action', 'index2.htm');
  		
  		$('#formPage').submit();
  		
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
        
        var pinColor = "56b270";
        var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
            new google.maps.Size(21, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(10, 34));
        
        
            
        
        for (i = 0; i < locations.length; i++) {
            // Generate 10 random points within North East USA
            randomPoint = new google.maps.LatLng( locations[i].lat, 
            		locations[i].lng);
            
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
  	
  	
  	
  	
  	
  	
  	
  	function callReport(id_store) {
  		
		$('#id_store').val(id_store);
		$('#id_store').trigger("chosen:updated");
  		$('#formPage').submit();
  		
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
	
  </script> 
  <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCy-BidiaPvw08cS-ZytcF8MRuMTUp10Mk&callback=initMap">
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
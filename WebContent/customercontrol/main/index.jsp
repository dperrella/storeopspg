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
				   margin-left: -100px;" />
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
               
              <%@ include file="../../inc2/menuApp10.jsp" %>
              
                <aside class="aside bg-white dk" >
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
	                    
	                    <span class="list-group-item" style="background-color:#ecf0f1">
		                  <b><a href="#" onclick="initMap()"><%=propLang.get("sto_dsfun") %></a></b>
		                  <span class="pull-right">
		                  	<div class="btn-group">
			                  <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">Filters <span class="caret"></span></button>
			                  <ul class="dropdown-menu pull-right">
			                    <li><a href="#" onclick="setFilter('0-RED')"><i class="fa fa-circle text-danger"></i> In Error</a></li>
			                    <li><a href="#" onclick="setFilter('1-YELLOW')"><i class="fa fa-circle text-warning"></i> Warning</a></li>
			                    <li><a href="#" onclick="setFilter('2-GREY')"><i class="fa fa-circle text-dark"></i> No Data</a></li>
			                    <li><a href="#" onclick="setFilter('3-GREEN')"><i class="fa fa-circle text-success"></i> OK</a></li>
			                    <li><a href="#" onclick="setFilter('4-BLUE')"><i class="fa fa-circle text-info"></i> Under Construction</a></li>
			                    <li class="divider"></li>
			                    <li><a href="#" onclick="setFilter('')">All Stores</a></li>
			                  </ul>
			                </div>
		                  </span>
		                </span>
		               
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                <c:forEach items="${sto}" var="sto" varStatus="status">
		                <li class="list-group-item">
		                	
		                	<a href="#"  onclick="moveTo(${sto.idStore}, '${sto.latitude}', '${sto.longitude}', true)">
		                	
		                	<div class="row">
		                		<div class="col-sm-1">
		                			<c:choose>
		                			
		                				<c:when test="${sto.color == '0-RED'}">
							    			<span class=""><span class="label bg-danger">${sto.nrDevices}</span></span>
							    		</c:when>
							    		<c:when test="${sto.color == '1-YELLOW'}">
							    			<span class=""><span class="label bg-warning">${sto.nrDevices}</span></span>
							    		</c:when>
							    		<c:when test="${sto.color == '2-GREY'}">
							    			<span class=""><span class="label bg-light">${sto.nrDevices}</span></span>
							    		</c:when>
							    		<c:when test="${sto.color == '3-GREEN'}">
							    			<span class=""><span class="label bg-success">${sto.nrDevices}</span></span>
							    		</c:when>
							    		<c:when test="${sto.color == '4-BLUE'}">
							    			<span class=""><span class="label bg-info">${sto.nrDevices}</span></span>
							    		</c:when>
							    		<c:otherwise>
							  				<span class=""><span class="label bg-light">${sto.nrDevices}</span></span>
				                      	</c:otherwise>
									</c:choose>
		                		</div>
		                		<div class="col-sm-10 text-ellipsis">
		                			<span class="text-ellipsis">${sto.description} </span>
                            		<!-- 
                            		<small class="text-muted">${sto.country}</small>
                            		-->
                            			<c:choose>
					                		<c:when test="${sto.color == '0-RED'}">
								    			<small class="text-danger text-ellipsis"><b>${sto.eventCode}</b></small>
								    			<small class="text-danger text-ellipsis">${sto.diffDate}<span class="text-white">${sto.color}</span></small>
								    		</c:when>
								    		<c:when test="${sto.color == '1-YELLOW'}">
								    			<small class="text-warning text-ellipsis"><b>Warning</b></small>
								    			<small class="text-warning text-ellipsis"><span class="text-white">${sto.color}</span></small>
								    		</c:when>
								    		<c:when test="${sto.color == '2-GREY'}">
								    			<small class="text-muted text-ellipsis"><b>No Data</b></small>
								    			<small class="text-muted text-ellipsis"><span class="text-white">${sto.color}</span></small>
								    		</c:when>
								    		<c:when test="${sto.color == '3-GREEN'}">
								    			<small class="text-success text-ellipsis"><b>OK</b></small>
								    			<small class="text-success text-ellipsis"><span class="text-white">${sto.color}</span></small>
								    		</c:when>
								  			<c:when test="${sto.color == '4-BLUE'}">
								    			<small class="text-success text-ellipsis"><b>Under Construction</b></small>
								    			<small class="text-success text-ellipsis"><span class="text-white">${sto.color}</span></small>
								    		</c:when>
								  			<c:otherwise>
								  				<small class="text-muted text-ellipsis">${sto.country}</small>
								    			<small class="text-muted text-ellipsis"><span class="text-white">${sto.color}</span><</small>
					                      	</c:otherwise>
										</c:choose>
                            	</div>
		                		<!-- 
		                		<div class="col-sm-1">
		                			<span class=""><span class="label bg-light">${sto.nrDevices}</span></span>
                            	</div>
                            	-->
		                	</div>
		                	</a>
		                		
		                	
		                </li>
		                
		                  
			            </c:forEach>
			            </ul>
		            
	             	  </div>
	                </section>
	                
	                <footer class="footer footer-md bg-light lt">
	                  
	                  <div class="form-group clearfix m-b-none">
	                    <div class="input-group m-t m-b">
	                      <span class="input-group-btn">
	                        <a href="javascipt:void;" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></a>
	                      </span>
	                      <input type="text" id="search_input" name="search_input" value="" class="form-control input-sm bg-empty b-b b-dark no-border" placeholder="<%=propLang.get("sto_cerca") %>">
	                    </div>
	                  </div>
	                  
	                </footer>
	              </section>
	            </aside>
	            <!-- / side content -->
              
              
                <section>
                	
                	<section class="hbox stretch">
		                <section id="map" style="min-height:240px;">          
		                </section>
		            </section>
		              
	            </section>
	            
	            <aside class="aside-xxl bg-white dk" >
	              <section class="vbox animated fadeInUp">
	                <section class="scrollable hover">
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
	                    
	                    <!-- 
	                    <span class="list-group-item" style="background-color:#ecf0f1">
		                  <b>Stores Pie Chart</b>
		                </span>
		                -->
		                
		                <div class="row">
		                
		                	<!-- >
		                	<div class="col-sm-6">
		                		
		                		<div id="stodetail">
		                		
		                			<div align="center">
				                		<h4 class="fontBig text-white animated" style="padding:20px;font-size:40px"><%=propLang.get("sel_stores") %></h4>
				               		</div>
		                			
		                		</div>
		                	</div>
		                	-->
		                	
		                	<div class="col-sm-12">
		                		<div id="stodetail">
		                			<!-- 
		                			<div align="center">
				                		<h4 class="fontBig text-white animated" style="padding:20px;font-size:40px"><%=propLang.get("sel_stores") %></h4>
				               		</div>
				               		-->
		                		</div>
		                	</div>
		                	<div class="col-sm-12">
		                		<div id="pie" style="height:370px"></div>
		                	</div>
		               </div>
		                
		                
		                
		                <span class="list-group-item" style="background-color:#ecf0f1" id="storename">
		                  <b>Store Detail</b>
		                </span>
		                <div id="detail">
		                	<div align="center">
		                		<h4 class="fontBig text-white animated" style="padding:20px;font-size:40px"></h4>
		               		</div>
		                </div>
		                
		              </div>
		            </section>
		          </section>
		        </aside>
	            
	            
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
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Filter list -->
  <script src="../../js/jquery.fastLiveFilter.js"></script>
  
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
	
	$(function() {
	    $('#search_input').fastLiveFilter('#search_list');
	});
	
	
	function setFilter(value) {
		
		$('#search_input').val(value);
		
		var e = jQuery.Event("keydown");
		e.which = 50; // # Some key code value
		$("#search_input").trigger(e);
		
		
		var color = value.split("-");
		var c = color[0];
		
		if(c=='')
			c=99;
		
		
		reloadLocations(c);
		
		
		
		//$('#search_input').val('');
		
		//$('#search_input').val('');
		
		
	}
	
	var myChart = echarts.init(document.getElementById('pie'));
	
	option = {
		color: [ '#EF2027', '#f5d758', '#595A7A', '#56b270','#68b4c8'],
			
	    title : {
	        text: 'Stores Status',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	    	 bottom: 10,
	         left: 'center',
	         data: ['In Error','Warning','No Data', 'OK','Under Construction'],
	         type: 'scroll',
	         
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    grid: {
		    //    top: 110,
		    //    left: 15,
		    //    right: 15,
		    //    height: 160
		    	bottom: 80,
		},
	    series : [
	        {
	            name: 'Stores',
	            type: 'pie',
	            //radius : '55%',
	            radius: ['20%', '70%'],
	            //center: ['50%', '60%'],
	            data: [
	            	//{value:${countOk}, name:'OK'},
	            	//{value:${countErr}, name:'In Error'}
	            	
	            	{value:${count0}, name:'In Error'},
	            	{value:${count1}, name:'Warning'},
	            	{value:${count2}, name:'No Data'},
	            	{value:${count3}, name:'OK'},
	            	{value:${count4}, name:'Under Construction'}
	            ],
	        
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};

	myChart.setOption(option);
	
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
  	
  	
  	var map;
	function initMap() {
		
		
		
        map = new google.maps.Map(document.getElementById('map'), {
          //zoom: 3,
          //center: {lat: 0, lng: 0}
        });

        var markerBounds = new google.maps.LatLngBounds();
        
        //var pinColor = "595A7A";
        // 0
        var pinColor = "56b270";
        var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
            new google.maps.Size(21, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(10, 34));
        
        // 2
        var pinColor2 = "EF2027";
        var pinImage2 = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor2,
            new google.maps.Size(21, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(10, 34));
        
        // 3
        var pinColor3 = "595A7A";
        var pinImage3 = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor3,
            new google.maps.Size(21, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(10, 34));
        
        // 1
        var pinColor4 = "f5d758";
        var pinImage4 = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor4,
            new google.maps.Size(21, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(10, 34));
        
        var pinColor5 = "68b4c8";
        var pinImage5 = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor5,
                new google.maps.Size(21, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(10, 34));
            
        
        for (i = 0; i < locations.length; i++) {
            // Generate 10 random points within North East USA
            randomPoint = new google.maps.LatLng( locations[i].lat, 
            		locations[i].lng);

            // Draw a marker for each random point
            //console.log(locations[i].desc);
            
            /*
            var img = "";
            if(locations[i].err==true) {
            	img = pinImage2;
            }
            else {
            	img = pinImage;
            }
            */
            
            //locations[i].color
            
            if(locations[i].color=='0-RED') {
            	img = pinImage2;
	 		}
	 		else if(locations[i].color=='1-YELLOW') {
	 			img = pinImage4;
	 		}
			else if(locations[i].color=='2-GREY') {
				img = pinImage3;
	 		}
			else if(locations[i].color=='3-GREEN') {
				img = pinImage;
	 		}
			else if(locations[i].color=='4-BLUE') {
				img = pinImage5;
	 		}
			else {
				img = pinImage3;
			}
            
            
            
              
            var marker = new google.maps.Marker({
              position: randomPoint, 
              icon: img,
              title: locations[i].desc,
              idstore: locations[i].id,
              map: map
            });
            
            
/*
              marker.addListener('click', function() {
                map.setZoom(8);
                map.setCenter(marker.getPosition());
              });
*/
			
			google.maps.event.addListener(marker, 'click', function() {
				var marker = this;
				//alert("id store:" + this.idstore);
				map.setZoom(17);
                map.setCenter(this.getPosition());
                
                var p = ''+this.getPosition();
                var temp = p.split(",");
                var lat = temp[0].replace("(","");
                var lng = temp[1].replace(")","");;
                
                //alert(lat+' '+lng);
                
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
    	  
    	  
    	  
    	  
    	  
    	  $('#detail').html('');
    	  
    	  var ok = 0;
    	  var ko = 0;
    	  
    	  var count0 = 0;
    	  var count1 = 0;
    	  var count2 = 0;
    	  var count3 = 0;
    	  var count4 = 0;
    	  
    	  var desc = '';
    	  
    	  var hasControl = ${hasControl};
    	  
    	  $.getJSON('../../servlet/ws/GetDevices?idStore='+id, function (data) {
    		    
    		    var lista = data.listings;
    		    
    		  	var idDevice;
    		  	var l='';
    		  	
    		  	var msg = '<ul id="search_list" class="list-group no-bg auto m-t-n-xxs">';
    		  	
    		  	var css = '';
    		  	var first = true;
    		  	var devExists = true;
    		 	$.each(lista, function(index, lista) {
    				
    		 		
    		 		if(first) {
    		 			
    		 			try {
	    		 			
    		 				/*
    		 				var array = JSON.parse(lista.types);
	    		            var t="";
	    		            for(i=0;i<array.length;i++) {
	    		            	t+="- "+array[i]+"<br/>";
	    		            }
	    		            
	    		            desc = lista.storeId+" - "+lista.stodesc;
	    		            if(desc.length>50)
	    		            	desc = desc.substring(0,50)+"...";
	    		            
	    		            var m = '<div class="wrapper">'
					           +'<b><span style="font-size:20px">'+lista.stodesc+'</span></b><br/><hr/>'
				           		+'<b>Types:</b><br/>'+t;
				           		
				            //$('#stodetail').html(m);
				            $('#stodetail').html("");
				            */
				            
				            
				            if(lista.idDevice==-1) {
				            	devExists = false;
				            }
				            
				            $('#storename').html('<b>'+lista.storeId+' - '+lista.stodesc+'</b>');
				            
    		 			}
    		 			catch(err) {
    		 				
    		 				var m = '<div class="wrapper">'
 					           +'<b><span style="font-size:20px">'+lista.stodesc+'</span></b><br/><hr/>'
 				           		+'<b>Types:</b><br/>';
 				           		
 				            //$('#stodetail').html(m);
    		 				$('#stodetail').html("");
    		 			}
    		            
    		 			first = false;
    		 		}
    		 		
    		 		idDevice = lista.idDevice;
    		 		
    		 		/*
    		 		if(lista.status!=''&&lista.status!='OK') {
    		 			ko+=1;
    		 			css = "text-danger";
    		 		}
    		 		else {
    		 			ok+=1;
    		 			css = "text-dark";
    		 		}
    		 		*/
    		 		
    		 		if(lista.color=='0-RED') {
    		 			ko+=1;
    		 			count0+=1;
    		 			css = "text-danger";	
    		 		}
    		 		else if(lista.color=='1-YELLOW') {
    		 			ko+=1;
    		 			count1+=1;
    		 			css = "text-yellow";
    		 		}
					else if(lista.color=='2-GREY') {
						ok+=1;
						count2+=1;
    		 			css = "text-dark";
    		 		}
					else if(lista.color=='3-GREEN') {
						ok+=1;
						count3+=1;
    		 			css = "text-success";
    		 		}
					else if(lista.color=='4-BLUE') {
						ok+=1;
						count4+=1;
    		 			css = "text-info";
    		 		}
					else {
						ok+=1;
						count2+=1;
    		 			css = "text-dark";
					}
    		 		
    		 		
    		 		if(devExists) {
    		 			
	    		 		msg+='<li class="list-group-item"><a href="../../customercontrol/popup/slaves.jsp?iddevice='+lista.idDevice+'&name='+lista.deviceName+'" data-toggle="ajaxModal">'
	    		 		+'<span class="pull-left thumb-xs m-t-xs  m-l-xs m-r-sm" style="width:40px;height:40px;">'
	                    +'<img src="img/'+lista.deviceType+'.png?v3"  alt="'+lista.devTypeDescription+'">'
	                   	+'</span>'
	    		 		+'<span class="pull-left '+css+'">'+lista.accountId+'-'+lista.storeId+'-'+lista.doorId+'</span>'
	    		 		+'<span class="pull-right '+css+'"><b>'+lista.status+'</b></span>';
	    		 		
	    		 		
	    		 		
	    		 		msg+='<br/>'
	    		 		+'<span class="pull-left text-ellipsis '+css+'"><b>'+lista.deviceName+'</b></span>'
	    		 		+'<span class="pull-right '+css+'"><small>('+lista.statusSince+')</small></span>'
	    		 		+'<br/><small class="'+css+'">'+lista.devTypeDescription+'</small>'
		           		+'</a>';
		           		
		           		if(hasControl) {
	    		 			msg+='<span class="pull-right"><a href="../../app/anag/devices-dett.htm?tipoForm=M&id='+lista.idDevice+'" target="_blank" class="btn btn-default btn-xs"><i class="fa fa-gear"></i></a></span>';
	    		 		}
		           		
		           		msg+='</li>';
		           		
    		 		}

    			});
    		 	
    		 	msg+="</ul>";
    		 	
    		 	$('#detail').append(msg);
    		 	
    		 	/*
    		 	var myChart2 = echarts.init(document.getElementById('bar'));
    		 	
    		 	option2 = {
    		 		    xAxis: {
    		 		        type: 'category',
    		 		        data: ['OK', 'KO']
    		 		    },
    		 		    yAxis: {
    		 		        type: 'value'
    		 		    },
    		 		    series: [{
    		 		        data: [120, 200],
    		 		        type: 'bar'
    		 		    }]
    		 		};
    		 	
    		 	myChart2.setOption(option2);
    		 	*/
    		 	
    		 	
    		 	
    		 	
    		 	option = {
    		 		color: [ '#EF2027', '#f5d758', '#595A7A', '#56b270','#68b4c8'],
   		 				
   		 		    title : {
   		 		        //text: desc,
   		 		        text: '',
   		 		        x:'center'
   		 		    },
   		 		    tooltip : {
   		 		        trigger: 'item',
   		 		        formatter: "{a} <br/>{b} : {c} ({d}%)"
   		 		    },
   		 		    legend: {
   		 		    	 bottom: 10,
   		 		         left: 'center',
   		 		     	data: ['In Error','Warning','No Data', 'OK', 'Under Construction'],
   		 		         type: 'scroll',
   		 		         
   		 		    },
   		 		    toolbox: {
   		 		        feature: {
   		 		            saveAsImage: {}
   		 		        }
   		 		    },
   		 		    grid: {
   		 			    //    top: 110,
   		 			    //    left: 15,
   		 			    //    right: 15,
   		 			    //    height: 160
   		 			    	bottom: 80,
   		 			},
   		 		    series : [
   		 		        {
   		 		            name: 'Stores',
   		 		            type: 'pie',
   		 		            //radius : '55%',
   		 		            radius: ['20%', '70%'],
   		 		            //center: ['50%', '60%'],
   		 		            data: [
   		 		            	{value:count0, name:'In Error'},
	   			            	{value:count1, name:'Warning'},
	   			            	{value:count2, name:'No Data'},
	   			            	{value:count3, name:'OK'},
	   			            	{value:count4, name:'Under Construction'}
   		 		            ],
   		 		        
   		 		            itemStyle: {
   		 		                emphasis: {
   		 		                    shadowBlur: 10,
   		 		                    shadowOffsetX: 0,
   		 		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
   		 		                }
   		 		            }
   		 		        }
   		 		    ]
   		 		};

   		 		myChart.setOption(option);
   		 		
    		 	hideLoader2();
    		 	
    		});
    	  
    	  if(move) {
    		  var center = new google.maps.LatLng(lat, lng);
    		  map.setZoom(17);
    		  map.panTo(center);
    	  }
      }
      
      function reloadLocations(color) {
    	  
    	  locations = [];
    	  
    	  if(color==0) {
    		  
    		  locations = [
            	  <c:forEach items="${sto}" var="sto" varStatus="status">
                  
            	  <c:if test = "${sto.latitude != null}">
            	  <c:if test = "${sto.color == '0-RED'}">
            	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
                  </c:if>
            	  </c:if>
                
                </c:forEach>
              ]
    		  
    	  }
    	  else if(color==1) {
    		  
    		  locations = [
            	  <c:forEach items="${sto}" var="sto" varStatus="status">
                  
            	  <c:if test = "${sto.latitude != null}">
            	  <c:if test = "${sto.color == '1-YELLOW'}">
            	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
                  </c:if>
            	  </c:if>
                
                </c:forEach>
              ]
    		  
    	  }
		  else if(color==2) {
    		  
			  locations = [
            	  <c:forEach items="${sto}" var="sto" varStatus="status">
                  
            	  <c:if test = "${sto.latitude != null}">
            	  <c:if test = "${sto.color == '2-GREY'}">
            	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
                  </c:if>
            	  </c:if>
                
                </c:forEach>
              ]
			  
    	  }
		  else if(color==3) {
    		  
			  locations = [
            	  <c:forEach items="${sto}" var="sto" varStatus="status">
                  
            	  <c:if test = "${sto.latitude != null}">
            	  <c:if test = "${sto.color == '3-GREEN'}">
            	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
                  </c:if>
            	  </c:if>
                
                </c:forEach>
              ]
			  
    	  }
		  else if(color==4) {
    		  
			  locations = [
            	  <c:forEach items="${sto}" var="sto" varStatus="status">
                  
            	  <c:if test = "${sto.latitude != null}">
            	  <c:if test = "${sto.color == '4-BLUE'}">
            	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
                  </c:if>
            	  </c:if>
                
                </c:forEach>
              ]
			  
    	  }
		  else {
			  
			  locations = [
	        	  <c:forEach items="${sto}" var="sto" varStatus="status">
	              
	        	  <c:if test = "${sto.latitude != null}">
	        	  	{lat: ${sto.latitude}, lng: ${sto.longitude}, err: ${sto.inError}, desc: "${sto.description}", id: ${sto.idStore}, color: "${sto.color}"},
	              </c:if>
	            
	            </c:forEach>
	          ]
			  
		  }
    	  
    	  initMap();
    	  
      }
  </script>
    <script async defer
     
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCy-BidiaPvw08cS-ZytcF8MRuMTUp10Mk&callback=initMap">
    </script>
  </body>
</html>
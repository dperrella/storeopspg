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
      <div class="navbar-header aside bg-danger dk nav-xs">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
          <i class="icon-list"></i>
        </a>
        
        <div class="navbar-brand text-lt">
          <a href="#" class="navbar-brand text-lt" class="dropdown-toggle" data-toggle="dropdown">
            <i class="icon-settings"></i>
            <span class="caret"></span>
          </a>
            <ul class="dropdown-menu">
	            <li class="text-left"><a href="#" onclick="chgColor('')">All Colors</a></li>
	            <li class="text-left"><a href="#" onclick="chgColor('green')"><i class="fa fa-circle text-success"></i> All Green</a></li>
	            <li class="text-left"><a href="#" onclick="chgColor('red')"><i class="fa fa-circle text-danger"></i> All Red</a></li>
	            <li class="text-left"><a href="#" onclick="chgColor('yellow')"><i class="fa fa-circle text-warning"></i> All Yellow</a></li>
	            <li class="text-left"><a href="#" onclick="chgColor('blue')"><i class="fa fa-circle text-info"></i> All Blue</a></li>
	            <li class="text-left"><a href="#" onclick="chgColor('grey')"><i class="fa fa-circle text-dark"></i> All Grey</a></li>
          	</ul>
        </div>
        <!-- 
        <a href="index.htm" class="navbar-brand text-lt">
          <i class="icon-settings"></i>
        </a>
        -->
      </div>     
      
      
      
      <form id="frm-page">
      <input type="hidden" name="country" id="naz" value=""/>
	  <input type="hidden" name="continent" id="con" value=""/>
	  <input type="hidden" name="area" id="are" value=""/>
	  <input type="hidden" name="kpi" id="kpi" value="VIS"/>
	  
      
      <div class="navbar-left m-t m-l">
      	
      	<ul class="nav navbar-nav nav-user">
          <!-- 
          <li>
          	<select class="form-control input-sm no-border rounded" name="kpi" id="kpi" style="width:150px" onchange="loadStores()">
   				<option value="VIS" selected>VIS</option>
   				<option value="EAS">EAS</option>
   			</select>
          </li>
          -->
          <li>
          	<div class="btn-group" data-toggle="buttons">
			  <label class="btn btn-xl btn-primary active" onclick="$('#kpi').val('VIS');loadStores()">
			    <input type="radio" name="_kpi" id="kpi_vis" value="VIS" checked><i class="fa fa-check text-active"></i> Visitors
			  </label>
			  <label class="btn btn-xl btn-primary" onclick="$('#kpi').val('EAS');loadStores()">
			    <input type="radio" name="_kpi" id="kpi_eas" value="EAS"><i class="fa fa-check text-active"></i> EAS Alarms
			  </label>
			</div>
          </li>
        </ul>
      </div>
      
      <div class="navbar-left m-t m-l">
      	
      	<ul class="nav navbar-nav nav-user">
          <li>
          	<div class="checkbox i-checks">
              <label>
                <input id="ckpcn" type="checkbox" checked=""><i></i> Show Country
              </label>
            </div>
          </li>
        </ul>
      </div>
      
      <div class="navbar-left m-t m-l">
      	
      	<ul class="nav navbar-nav nav-user">
          <li>
          	<div class="checkbox i-checks">
              <label>
                <input id="ckphm" type="checkbox" checked=""><i></i> Show Heatmap
              </label>
            </div>
          </li>
        </ul>
      </div>
      
      <div class="navbar-right m-t m-l-n-xs">
        <ul class="nav navbar-nav m-n hidden-xs nav-user user">
          <li class="m-r">
            <select class="form-control" name="year" id="year" style="width:100px" onchange="loadStores()">
   				<option value="2020" selected>2020</option>
   				<option value="2019">2019</option>
   				<option value="2018">2018</option>
   				<option value="2017">2017</option>
   			</select>
          </li>
          <li class="m-r">
              <select class="form-control" name="month" id="month" style="width:150px" onchange="loadStores()">
   				<option value="" selected>All</option>
   				<option value="1">January</option>
   				<option value="2">February</option>
   				<option value="3">March</option>
   				<option value="4">April</option>
   				<option value="5">May</option>
   				<option value="6">June</option>
   				<option value="7">July</option>
   				<option value="8">August</option>
   				<option value="9">September</option>
   				<option value="10">October</option>
   				<option value="11">November</option>
   				<option value="12">December</option>
   			  </select>
           
          </li>
        </ul>
      </div>      
      </form>
      
    </header>
    
    
    <section>
    
      <section class="hbox stretch">
      
      	
      	<!-- .aside -->
        <aside class="bg-dark dk nav-xs aside hidden-print" id="nav">          
          <section class="vbox">
            <section class="w-f-md scrollable">
              <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">
                


                <!-- nav -->                 
                <nav class="nav-primary hidden-xs">
                  <ul class="nav bg clearfix">
                    <li class="hidden-nav-xs padder m-t m-b-sm text-xs text-muted">
                      Where
                    </li>
                    
                    <li class="bg-dark dker">
                    	<a href="#" onclick="$('#naz').val('');$('#con').val('');$('#are').val(''); loadStores()">
	                        <i class="icon-globe icon"></i>
	                        <span class="font-bold">GLOBAL</span>
                     	</a>
					</li>
					
                    <c:forEach items="${cnt}" var="cnt" varStatus="status">
	                <li>
                    	<a href="#" onclick="$('#naz').val('${cnt.idNaz}');$('#con').val('${cnt.idContinent}');$('#are').val('${cnt.idArea}'); loadStores()">
	                        <!-- 
	                        <i class="icon-globe icon"></i>
	                        -->
	                        <span class="font-bold">${cnt.area}</span>
                     	</a>
					</li>
					</c:forEach>
                      
                    
                  </ul>
                  <ul class="nav dk bg clearfix">
                  	<li class="hidden-nav-xs padder m-t m-b-sm text-xs text-muted">
                      Interface
                    </li>
                    
                    <li>
                    	<a href="#" onclick="$('#naz').val('');$('#con').val('');$('#are').val('APAC');loadStores()">
	                        <!-- 
	                        <i class="icon-globe icon"></i>
	                        -->
	                        <span class="font-bold">APAC</span>
                     	</a>
					</li>
					<li>
                    	<a href="#" onclick="$('#naz').val('');$('#con').val('');$('#are').val('EMEA');loadStores()">
	                        <!-- 
	                        <i class="icon-globe icon"></i>
	                        -->
	                        <span class="font-bold">EMEA</span>
                     	</a>
					</li>
					<li>
                    	<a href="#" onclick="$('#naz').val('');$('#con').val('');$('#are').val('LATAM');loadStores()">
	                        <!-- 
	                        <i class="icon-globe icon"></i>
	                        -->
	                        <span class="font-bold">LATAM</span>
                     	</a>
					</li>
					<li>
                    	<a href="#" onclick="$('#naz').val('');$('#con').val('');$('#are').val('NORTHAM');loadStores()">
	                        <!-- 
	                        <i class="icon-globe icon"></i>
	                        -->
	                        <span class="font-bold">NORTHAM</span>
                     	</a>
					</li>
                  </ul>
                    
                  
                </nav>
                <!-- / nav -->
              </div>
            </section>
            
          </section>
        </aside>
        <!-- /.aside -->
        
        
        
        <section id="content">
          <section class="vbox">
          
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              	
              	<aside class="aside bg-light dk" id="sidebar" >
	              <section class="vbox animated fadeInUp">
	              	<section class="w-f-md scrollable hover">
	              	  
	              	  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
	              	  <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
	              	  	<c:forEach items="${naz}" var="naz" varStatus="status">
		                <li class="list-group-item">
		                	<a style="display: inline-block" href="#" onclick="$('#naz').val('${naz.idNaz}');$('#con').val('${naz.idContinent}');$('#are').val('${naz.idArea}'); loadStores()">${naz.country}</a>
							<b class="badge bg-light pull-right">${naz.nrStores}</b>
						</li>
		                </c:forEach>
		              </ul>
	              	  </div>
	               
	                </section>
	                <footer class="footer footer-md bg-light dker">
	                  
	                  <div class="form-group clearfix m-b-none">
	                    <div class="input-group m-t m-b">
	                      <span class="input-group-btn">
	                        <a href="javascipt:void;" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></a>
	                      </span>
	                      <input type="text" id="search_input" name="search_input" value="" class="form-control input-sm bg-empty b-b b-dark no-border" placeholder="Search">
	                    </div>
	                  </div>
	                  
	                </footer>
	                
	              </section>
	            </aside>
              	
                <section>
                	
                	<section class="hbox stretch">
                		<section>
                		
                			
                			
	                		<section class="vbox flex">
	                			<section class="w-f-md stretch">
	                				
	                				<section id="map" style="min-height:240px;">          
				                	</section>
				                	
	                			</section>
		                		
		                		<footer class="footer bg-white" style="padding:0px;margin:0px;height:250px" id="hmfooter">
		                				<!-- 
		                				<div class="col-sm-4">
		                					<div id="pie" style="min-height:240px"></div>
		                				</div>
		                				-->
		                					<div id="heatmap" style="margin-left:-15px;min-height:250px"></div>
		                			
		                		</footer>
		                	</section>
		                	
                		</section>
                	
                	</section>
                	
                	
		                		
		            
		            
		              
	            </section>
	            
	            <aside class="aside-xl bg-white dk" >
	              <section class="vbox animated fadeInUp">
	                <section class="hover">
	                	<section class="wrapper bg-light" id="trend_region">
	               			<b>GLOBAL</b>
	               		</section>
		                <div class="row">
	                		<div class="col-sm-12">
	                  			<div id="pie" style="min-height:230px"></div>
	                  		</div>
	                  	</div>
	                  	
	                  	<div class="row">
	                		<div class="col-sm-12">
	                  			<div id="linechart" style="margin-left:10px;margin-top:0px;min-height:230px"></div>
	                  		</div>
	                  	</div>
	                  	
	                  	<div class="row text-center" id="trend_nrstores" style="margin-top:0px"></div>
	                  		
	                  	<div class="row text-center" id="trend"></div>
	                  	
	                </section>
	              </section>
	            </aside>
              
	            
             	  
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
		
		//loadCharts();
		
		loadStores();
		
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
	
	$(function() {
	    $('#search_input').fastLiveFilter('#search_list');
	});
	
	var map;
	
	var ccc='';
	
	function initMap() {
		
		map = new google.maps.Map(document.getElementById('map'), {
          //zoom: 3,
          //center: {lat: 0, lng: 0}
        });
	
		var markerBounds = new google.maps.LatLngBounds();
        
		map.fitBounds(markerBounds);
		
        var icon0 = {
       	    url: "../../images/red.png", // url
       	    scaledSize: new google.maps.Size(15, 15), // scaled size
       	    origin: new google.maps.Point(0,0), // origin
       	    anchor: new google.maps.Point(0, 0) // anchor
       	};
        var icon1 = {
       	    url: "../../images/yellow.png", // url
        	scaledSize: new google.maps.Size(15, 15), // scaled size
          	origin: new google.maps.Point(0,0), // origin
          	anchor: new google.maps.Point(0, 0) // anchor
        };
        var icon2 = {
       	    url: "../../images/grey.png", // url
        	scaledSize: new google.maps.Size(15, 15), // scaled size
          	origin: new google.maps.Point(0,0), // origin
          	anchor: new google.maps.Point(0, 0) // anchor
        };
            
        var icon3 = {
       	    url: "../../images/green.png", // url
        	scaledSize: new google.maps.Size(15, 15), // scaled size
          	origin: new google.maps.Point(0,0), // origin
          	anchor: new google.maps.Point(0, 0) // anchor
        };
            
        var icon4 = {
       	    url: "../../images/blue.png", // url
        	scaledSize: new google.maps.Size(15, 15), // scaled size
          	origin: new google.maps.Point(0,0), // origin
          	anchor: new google.maps.Point(0, 0) // anchor
        };
        
       
       
        
        for (i = 0; i < locations.length; i++) {
            // Generate 10 random points within North East USA
            randomPoint = new google.maps.LatLng( locations[i].lat, 
            		locations[i].lng);

            if(ccc!='') {
            	
            	if(ccc=='red') {
            		img = icon0;
            	}
            	else if(ccc=='yellow') {
            		img = icon1;
            	}
            	else if(ccc=='grey') {
            		img = icon2;
				}
            	else if(ccc=='green') {
            		img = icon3;
				}
            	else if(ccc=='blue') {
            		img = icon4;
				}
            } 
            else {
            	
	            if(locations[i].color=='0-RED') {
	            	img = icon0;
		 		}
		 		else if(locations[i].color=='1-YELLOW') {
		 			img = icon1;
		 		}
				else if(locations[i].color=='2-GREY') {
					img = icon2;
		 		}
				else if(locations[i].color=='3-GREEN') {
					img = icon3;
		 		}
				else if(locations[i].color=='4-BLUE') {
					img = icon4;
		 		}
				else {
					img = icon2;
				}
	            
            }
            
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
        
  	  <c:if test = "${sto.uid != null}">
      {lat: ${sto.lat}, lng: ${sto.lon}, id: ${sto.idStore}, color: "${sto.color}"},
      </c:if>
      </c:forEach>
    ]
	
	
	function loadStores() {
		
		showLoader();
		
		var datastring = $("#frm-page").serialize();
		
		console.log(datastring);
		
		$.ajax({
		      url: '../../nrf/GetStores',
		      type: 'POST',
		      dataType: 'json',
		      async: true,
		      data: datastring,
		      success: function (data) {
					
		    	  hideLoader();
		    	  
		    	  //alert(JSON.stringify(data))
		    	  
		    	  ////////////////
		    	  locations = [];
		    	  for (let i=0; i<data.listings.length; i++) {
		    		  
		    		  if(data.listings[i].uid!=null) {
			    		  locations.push(
			    			{lat: data.listings[i].lat, lng: data.listings[i].lon, id: data.listings[i].idStore, color: data.listings[i].color}
			    	      );
		    		  }
		    		  
		    	  }
		    	  
		    	  ///////////////
		    	  
		    	  _legend = [];
		    	  _data = [];
		    	  
		    	  for (let i=0; i<data.pie.length; i++) {
				    	_legend.push(data.pie[i].name);
				    	_data.push(
				    		{value: data.pie[i].value, name: data.pie[i].name}
				    	);
				  }
		    	  
		    	  option = {
    				color: [ '#3F4787', '#F9CD2F'],
    					
    			    title : {
    			        text: '',
    			        x:'center'
    			    },
    			    tooltip : {
    			        trigger: 'item',
    			        formatter: "{a} <br/>{b} : {c} ({d}%)"
    			    },
    			    legend: {
    			    	 orient: 'vertical',
    			         x: 'left',
    			         data: _legend,
    			         type: 'scroll',
    			         
    			    },
    			    toolbox: {
    			        feature: {
    			            saveAsImage: {}
    			        }
    			    },
    			    grid: {
    				    bottom: 0,
    				},
    			    series : [
    			        {
    			            name: 'Stores',
    			            type: 'pie',
    			            //radius : '55%',
    			            radius: ['20%', '80%'],
    			            //center: ['50%', '60%'],
    			            data: _data,
    			        
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
		    	  
		    	///////////////
		    	
		    	//alert(JSON.stringify(data.heatmap));
		    	max = data.max;
		    	
		    	option2 = {
				    		
				    tooltip: {
				        position: 'top'
				    },
				    animation: true,
				    
				    //grid: {
				    //    height: '100%',
				    //    y: '0%',
				    //    x: '0%'
				    //},
				    grid: {
				    	width: '90%',
				        height: '80%',
				        y: 5,
				        x: '10%',
				        right: '0%'
				    },
				    xAxis: {
				        type: 'category',
				        axisLabel: { rotate: 60, },
				        data: ['00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'],
				        splitArea: {
				            show: true
				        }
				    },
				    yAxis: {
				        type: 'category',
				        data: ['Saturday','Friday','Thursday','Wednesday','Tuesday','Monday','Sunday'],
				        splitArea: {
				            show: true
				        }
				    },
				    
				    
				    visualMap: {
				        min: 0,
				        max: max,
				        show: false,
				    //    calculable: true,
				    //    left: 'right',
				        inRange: {
				        	color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
				    	}
				    },
				    
				    series: [{
				        name: $('#kpi').val(),
				        type: 'heatmap',
				        data: data.heatmap,
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
		    	
				///////////////
			    
				$('#search_list').empty();
		    	//$('#search_input').fastLiveFilter('#search_list');
		    	
		    	console.log(data.country);
		    	
		    	
		    	
		    	for (var i=0; i<data.country.length; i++) {
			    	
		    		$("#search_list").append('<li class="list-group-item">'
		                	+'<a style="display: inline-block" href="#" onclick="$(\'#naz\').val(\''+data.country[i].idNaz+'\');$(\'#con\').val(\''+data.country[i].idContinent+'\');$(\'#are\').val(\''+data.country[i].idArea+'\'); loadStores()">'+data.country[i].country+'</a>'
		                	+'<b class="badge bg-info pull-right">'+data.country[i].nrStores+'</b>'
							+'</li>');
		    	}
		    	
				///////////////
			    
				console.log(data.trend);
				
				$('#trend').html('');
				for (var i=0; i<data.trend.length; i++) {
					
					if(i==0) {
						$('#trend_region').html('<b>'+data.trend[i].value+'</b>');
					}
					if(i==1) {
						$('#trend_nrstores').html('');
						$('#trend_nrstores').append('<div class="col-sm-12"><div class="wrapper r">'
								+'<a href="#">'
								+'  <span class="h4 block"><i class="fa fa-building-o icon i-lg"></i> '+data.trend[i].name+'</span>'
								+'  <span class="text-muted font-thin" style="text-align:center;font-size:40px">'+data.trend[i].value+'</span>'
								+'</a>'
								+'</div></div>')
						
					}
					
					if(i>=2) {
						
						var icon;
						if(data.trend[i].value>0) {
							icon = '<i class="fa fa-caret-up text-success icon i-lg"></i>';
						}
						else {
							icon = '<i class="fa fa-caret-down text-danger icon i-lg"></i>';
						}
						$('#trend').append('<div class="col-sm-6"><div class="wrapper r">'
								+'<a href="#">'
								+'  <span class="h4 block">'+icon+' '+data.trend[i].name+'</span>'
								+'  <span class="text-muted font-thin" style="text-align:center;font-size:40px">'+data.trend[i].value+'%</span>'
								+'</a>'
								+'</div></div>')
						}
					
				}
				
				//////////////
				
				_xAxis = [];
		    	_data1 = [];
		    	_data2 = [];
		    	  
		    	for (let i=0; i<data.linechart.length; i++) {
		    		if(data.linechart[i].serie==1) {
		    			_xAxis.push(data.linechart[i].index);
			    		_data1.push(data.linechart[i].value);
		    		}
		    		if(data.linechart[i].serie==2)
		    			_data2.push(data.linechart[i].value);
		    		
		    	}
		    	
		    	  

				
				option3 = {
					color: [ '#3F4787', '#F9CD2F'],
					grid: {
						top:0,
    				    //bottom: 0,
						//containLabel: true
    				},
    				tooltip : {
    			        trigger: 'axis',
    			        axisPointer: {
    			            type: 'cross',
    			            label: {
    			                backgroundColor: '#6a7985'
    			            }
    			        }
    			    },
	    			xAxis: {
				        type: 'category',
				        data: _xAxis
				    },
				    yAxis: {
				        type: 'value',
				        axisTick: {
				            inside: true
				        },
				        splitLine: {
				            show: true
				        },
				        axisLabel: {
				            inside: true,
				            formatter: '\n'
				        },
				        z: 10
				    },
				    series: [{
				        data: _data1,
				        type: 'line'
				    },
				    {
				        data: _data2,
				        type: 'line'
				    }]
				};
				
				pie.setOption(option);
		    	heatmap.setOption(option2);
		    	linechart.setOption(option3);
		    	
		    	initMap();
		    	  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	var pie = echarts.init(document.getElementById('pie'));
	var heatmap = echarts.init(document.getElementById('heatmap'));
	var linechart = echarts.init(document.getElementById('linechart'));
	
	
	var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		pie.resize();
		heatmap.resize()
		linechart.resize()
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	
	
	$('#ckpcn').change(function() {
        if(this.checked) {
            //var returnVal = confirm("Are you sure?");
            //$(this).prop("checked", returnVal);
            
        	$('#sidebar').show();
        	rfs = setTimeout(resize, 500);
        }
        else {
        	$('#sidebar').hide();
        	rfs = setTimeout(resize, 500);p
        }
        //$('#ckphm').val(this.checked);        
    });	

	$('#ckphm').change(function() {
        if(this.checked) {
        	$('#hmfooter').show();
        	rfs = setTimeout(resize, 500);
        }
        else {
        	$('#hmfooter').hide();
        	rfs = setTimeout(resize, 500);
        }     
    });	
	
	function chgColor(col) {
		
		ccc = col;
		initMap();
		
	}

  </script>
    
    <script async defer
     
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCy-BidiaPvw08cS-ZytcF8MRuMTUp10Mk&callback=initMap">
    </script>
  </body>
</html>
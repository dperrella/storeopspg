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
  <title><%=propLang.get("sto_dsfun") %> | SmartSuite</title>
  <meta name="description" content="SmartHR" />
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
          <section class="hbox stretch">      
          
            <!-- side content -->
            <aside class="aside bg-white dk" >
              <section class="vbox animated fadeInUp">
                <section class="w-f-md scrollable hover">
                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                    
                    <span class="list-group-item" style="background-color:#ecf0f1">
	                  <b><%=propLang.get("sto_dsfun") %></b>
	                </span>
	                
	                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
	                <c:forEach items="${sto}" var="sto" varStatus="status">
	                <li class="list-group-item" style="min-height:60px">
	                	<!-- 
	                	<a href="stores-google-dett.htm?id=${id}&googleUid=${sto.xcdel}" data-toggle="ajaxModal" onclick="moveTo(${sto.cmp2}, ${sto.cmp3})">
	                	-->
	                	<a href="#"  onclick="moveTo(${sto.cmp2}, ${sto.cmp3}, '${id}', '${sto.xcdel}')">
	                	<div>${sto.xdtab}</div>
	                	<small class="text-muted">${sto.cmp1}</small>
	                	</a>
	                </li>
	                
		            </c:forEach>
		            </ul>
	            
             	  </div>
                </section>
                
                <footer class="footer footer-md bg-light lt">
                  <form action="stores-google.htm" class="" role="search">
                  <input type="hidden" name="id" value="${id}"/>
                  <input type="hidden" name="googleUid" value="${googleUid}"/>
                  
                  <div class="form-group clearfix m-b-none">
                    <div class="input-group m-t m-b">
                      <span class="input-group-btn">
                        <a href="javascipt:void;" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></a>
                      </span>
                      <input type="text" id="query" name="query" value="${googleQuery}" class="form-control input-sm bg-empty b-b b-dark no-border" placeholder="<%=propLang.get("sto_cerca") %>">
                    </div>
                  </div>
                  </form>
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
            
            <!-- side content -->
            <aside class="aside bg-white dk" >
              <section class="vbox animated fadeInUp">
                <section class="w-f-md scrollable hover">
                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                    
                    <span class="list-group-item" style="background-color:#ecf0f1">
	                  <b><%=propLang.get("sto_dett") %></b>
	                </span>
	                
	                <div id="sto_dett">
	                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
			                <li class="list-group-item">
			                	Select Store
			                </li>
		                </ul>
		                
	                </div>
	                
             	  </div>
                </section>
                
                <footer class="footer footer-md bg-light lt">
                  <form id="uidFrm" class="" role="search">
                  <input type="hidden" id="sel_idstore" name="id" value=""/>
                  <input type="hidden" id="sel_googleUid" name="googleUid" value=""/>
                  <input type="hidden" id="sel_lat" name="lat" value=""/>
                  <input type="hidden" id="sel_lon" name="lon" value=""/>
                  <input type="hidden" id="sel_types" name="types" value=""/>
                  
                  <div class="form-group clearfix m-t m-b-none">
                      
                      <a href="#" onclick="setUid()" class="btn btn-primary btn-block">Set Google Uid</a>
                      
                    
                  </div>
                  </form>
                </footer>
                
                
              </section>
            </aside>
            <!-- / side content -->
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
  
  <!-- 
  <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
  -->
  
  
  
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
	
	var map;
	function initMap() {
		
		
		
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 3
          ,
          center: {lat: 0, lng: 0}
        });

        
        
        // Create an array of alphabetical characters used to label the markers.
        var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

        // Add some markers to the map.
        // Note: The code uses the JavaScript Array.prototype.map() method to
        // create an array of markers based on a given "locations" array.
        // The map() method here has nothing to do with the Google Maps API.
        var markers = locations.map(function(location, i) {
          return new google.maps.Marker({
            position: location,
            label: labels[i % labels.length]
          });
        });

        // Add a marker clusterer to manage the markers.
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
      }
      var locations = [
    	  <c:forEach items="${sto}" var="sto" varStatus="status">
          
        {lat: ${sto.cmp2}, lng: ${sto.cmp3}},
        
        </c:forEach>
      ]
      
      function moveTo(lat,lon, id, googleUid) {
    	  
    	  var center = new google.maps.LatLng(lat, lon);
		  map.setZoom(17);
		  map.panTo(center);
		  
		  showLoader2();
		  // ajax call store detail
		    
		  $.ajax({
			url : "stores-google-dett.htm?id="+id+"&googleUid="+googleUid,
			success : function(data) {
				
				hideLoader2();
				//alert(data);
				var temp = data.split('##@@');
				
				var name = temp[0];
				var icon = temp[1];
				var address = temp[2];
				var phone = temp[3];
				var latitude = temp[4];
				var longitude = temp[5];
				var types = temp[6];
				 
	            $('#sel_idstore').val(id);
	            $('#sel_googleUid').val(googleUid);
	            $('#sel_lat').val(latitude);
	            $('#sel_lon').val(longitude);
	            $('#sel_types').val(types);
	            
	            var array = JSON.parse(types);
	            var t="";
	            for(i=0;i<array.length;i++) {
	            	t+="- "+array[i]+"<br/>";
	            }
	            
				$('#sto_dett').html('');
				
				var msg = '<ul id="search_list" class="list-group no-bg auto m-t-n-xxs">'
				           	+'<li class="list-group-item">'
			            	+'<div align="center"><img src="'+icon+'"/></div>'
			           		+'</li>'
			           		+'<li class="list-group-item">'
			            	+'<b>Name:</b><br/>'+name
			           		+'</li>'
			           		+'<li class="list-group-item">'
			            	+'<b>Address:</b><br/>'+address
			           		+'</li>'
			           		+'<li class="list-group-item">'
			            	+'<b>Phone:</b><br/>'+phone
			           		+'</li>'
			           		+'<li class="list-group-item">'
			            	+'<b>Types:</b><br/>'+t
			           		+'</li>'
			           		+'</ul>';
			           		
			     $('#sto_dett').html(msg)
			}
		});
		    
		  
		
    	  
    	  
      }
      
      
      
      function setUid() {
			
    	  showLoader2();
			var datastring = $('#uidFrm').serialize();
			
			//alert(datastring);
			
			
			$.ajax({
				url : "../ajax/stores-google-update.jsp",
				type: 'POST',
				async: true,
			    data: datastring,
				success : function(data) {
					
					location.href="stores-list.htm?customerid=${customerid}";
					
				}
			});
			
			
			
		}
    </script>
    <script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCy-BidiaPvw08cS-ZytcF8MRuMTUp10Mk&callback=initMap">
    </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
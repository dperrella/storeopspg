<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" 
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="pkg.utility.TrippleDes"
%>
    
<%
String key = request.getParameter("key");
TrippleDes td = new TrippleDes();
String store = td.decrypt(key);
%>
<!DOCTYPE html>
<html class="app">
<head>  
  <meta charset="utf-8" />
  <title>Smart Suite Occupancy</title>
  <meta name="description" content="Occupancy" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
  <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>	
  <![endif]-->
</head>
<body id="_body" class="xxx" style="background-color:#466072;">

  <section class="vbox">
  
  	<header class="header header-md navbar navbar-fixed-top-xs">
      
      <div class="navbar-left m-t m-l-n-xs hidden-xs">
       <img src="ckplogo.png" class="m-l-lg" style="margin-left:50px;height:35px "alt="CheckPoint Italia">
      </div>
      
      <div class="navbar-right m-r-lg" style="margin-right:50px;margin-top:15px">
        	<span style="color:#fff;font-size:20px" id="time">Last Update: <span id="_ts"></span></span>
      </div>      
      
    </header>
    
	<section>
    
      <section class="hbox stretch">
    	
    	<section id="content">
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
        		
        		
        		<section>
                  <section class="vbox">
                    <section class="scrollable padder">
                		
                		<div class="row">
	                            	
                            	<div class="col-xs-12" align="center">
									<div class="wrapper" id="_panel" style="border-radius: 25px; background-color:#57788E;">
										<div id="__data">
										<div class="row">
											<div class="col-sm-4">
												<span class="h4 block" style="color:#fff;font-size: 20px">
												People in Store:<br/>
												<span id="_tot">-</span>
												</span>
											</div>
											<div class="col-sm-4">
												<span class="h3 block text-ellipsis" style="margin-bottom:20px;font-size:30px;color:#fff" id="_description"></span>
											</div>
											<div class="col-sm-4">
												<span class="h4 block" style="color:#fff;font-size: 20px">
												Max Occupancy:<br/><span id="_threshold">-</span>
												</span> 
											</div>
										
										</div>
										<div class="row" style="margin-left:20px;margin-right:20px;margin-top:20px">
											
											<div class="col-sm-5" id="_occupancy_label" style="border-radius: 25px; background-color:#72889A;">
												<span class="h4 block" style="margin-top:20px;font-size:20px;color:#fff;">Realtime Occupancy</span>
												<span class="font-thin" id="_occupancy" style="color:#FFFFFF;text-align:center;font-size:140px;margin-top:-200px">-</span>
											</div>
											<div class="col-sm-2">
											
											</div>
											<div class="col-sm-5" id="_available_label" style="border-radius: 25px; background-color:#72889A;">
												<span class="h4 block" style="margin-top:20px;font-size:20px;color:#fff;">Available Places</span>
												<span class="font-thin" id="_available" style="color:#FFFFFF;text-align:center;font-size:140px;margin-top:-200px">-</span>
											</div>
										</div>
										<div class="row" style="margin-left:20px;margin-right:20px;margin-top:20px">
											<div class="col-sm-2"></div>
											<div class="col-sm-4">
												<span class="h4 block" style="font-size:20px; color:#fff;">Today's Income</span>
				                       			<span class="font-thin" id="_in" style="color:#fff;text-align:center;font-size:40px">-</span>
											</div>
											<div class="col-sm-4">
												<span class="h4 block" style="font-size:20px; color:#fff;">Today's Outcome</span>
				                       			<span class="font-thin" id="_out" style="color:#fff;text-align:center;font-size:40px">-</span>
											</div>
											<div class="col-sm-2"></div>
										</div>
										</div>
										
									</div>
								</div>
                            </div>
                            <div class="row"><div class="col-sm-12">&nbsp;</div></div>
                            
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

<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-app.js"></script>

<!-- TODO: Add SDKs for Firebase products that you want to use
     https://firebase.google.com/docs/web/setup#available-libraries -->

<script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-database.js"></script>

<script src="../../js/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="../../js/bootstrap.js"></script>
    
<!-- App -->
<script src="../../js/app.js"></script>  
<script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="../../js/app.plugin.js"></script>
  
<script>
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
    
  mRef = database.ref("occupancy/<%=store%>");
  mRef.on('value', (snapshot) => {
	  let datas = snapshot.val();
	  
      console.log(JSON.stringify(datas));
      parseData(datas)
  });	
  
  
  var bg1 = '#466072';
  var bg2 = '#57788E';
  var bg3 = '#72889A';
  
  function sort_by_key(array, key)
	{
	 return array.reverse(function(a, b)
	 {
	  var x = a[key]; var y = b[key];
	  return ((x < y) ? -1 : ((x > y) ? 1 : 0));
	 });
	}
  
  function parseData(datas) {
	  
	  
	  var tot = 0;
	  
	  dataval = sort_by_key(datas.data, "id");
	  
	  $.each(dataval, function(index, data) {
		console.log(data.id);	 
		
		var perc = ((data.presenze/data.threshold) * 100).toFixed(0);
		var available = data.threshold-data.presenze;
		if(parseInt(available)<0)
			available = 0;
		
		console.log(available);
		
		if(perc >= 100) {
		    bg1 = '#B20202';
		    bg2 = '#EF0A0A';
		    bg3 = '#E04A3F';
		}
		else if(perc >= parseInt(data.perc_warning)){
		    bg1 = '#D6852D';
		    bg2 = '#E5C62E';
		    bg3 = '#E4CD64';
		}
		else if(perc >= 0){
		    bg1 = '#466072';
		    bg2 = '#57788E';
		    bg3 = '#72889A';
		}
		
		$('#_description').html(data.text);
		$('#_in').html(data.ingressi);
		$('#_out').html(data.uscite);
		$('#_occupancy').html(data.presenze);
		$('#_threshold').html(data.threshold);
		$('#_available').html(available);
		
		$('#_body').css('background-color',bg1);
		$('#_panel').css('background-color',bg2);
		$('#_occupancy_label').css('background-color',bg3);
		$('#_available_label').css('background-color',bg3);
		
		
		tot+=parseInt(data.presenze);
		
	  });
	  
	  $('#_tot').html(tot);
	  
	  $('#_ts').html(datas.tstamp);
	  
  }
  
</script>
</body>
</html>
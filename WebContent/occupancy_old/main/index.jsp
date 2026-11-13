<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
pageEncoding="ISO-8859-1"
%>
<%
// Set refresh, autoload time as 60 seconds
response.setIntHeader("Refresh", 60);
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <link rel="manifest" href="manifest.json"></link>
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
</head>
<body class="bg-dark dker">
  <section class="vbox">
  	
  	<section>
    
      <section class="hbox stretch">
    	
    	<section id="content">
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
        
        
        		<section>
                  <section class="vbox">
                    <section class="scrollable padder">
                      	
					  <div class="row row-sm m-t">
						<div class="col-xs-6" align="center">
							<div class="bg-dark wrapper-md r">
		                        <a href="#">
		                          <span class="h3 block" style="margin-bottom:10px"><i class="icon-users i-lg"></i> Complesso Multisala ex Cinema Metropolitan </span>
		                          <span class="h4 block" style="margin-bottom:-50px">Massimo Numero in Sala,<br/>Zona Ristorante e Galleria Pedonale e Box:<br/><span style="font-size:50px" id="maxA1"><b>2700</b></span>
		                          <br/><span style="font-size:30px">&nbsp;</span></span></span>
		                          
		                          <span class="font-thin" id="A1" style="color:#fff;text-align:center;font-size:240px">-</span>
		                        </a>
		                        <div class="row" style="margin-top:-60px">
		                       		<div class="col-sm-3"></div>
		                       		<div class="col-sm-3"><span class="h4 block" style="color:#fff;">Ingressi</span>
		                       			<span class="font-thin" id="A1E" style="color:#fff;text-align:center;font-size:50px">-</span>
		                       		</div>
		                       		<div class="col-sm-3"><span class="h4 block" style="color:#fff;">Uscite</span>
		                       			<span class="font-thin" id="A1U" style="color:#fff;text-align:center;font-size:50px">-</span></div>
		                       		<div class="col-sm-3"></div>
		                       	</div>
		                    </div>
		                </div>
		                
		                <div class="col-xs-6" align="center">
							<div class="bg-dark wrapper-md r">
		                        <!-- 
		                        <a href="#">
		                          <span class="h3 block" style="margin-bottom:10px"><i class="icon-users i-lg"></i> Old Wild West</span>
		                          <span class="h4 block" style="margin-bottom:-60px">Massimo Numero Ospiti:<br/>&nbsp;<br/><span style="font-size:50px" id="maxA2"><b>198</b></span></span>
		                          
		                          <span class="font-thin" id="A2" style="color:#fff;text-align:center;font-size:250px">-</span>
		                        </a>
		                        
		                        <div class="row" style="margin-top:-50px">
		                       		<div class="col-sm-3"></div>
		                       		<div class="col-sm-3"><span class="h4 block" style="color:#fff;">Ingressi</span>
		                       			<span class="font-thin" id="A2E" style="color:#fff;text-align:center;font-size:50px">-</span>
		                       		</div>
		                       		<div class="col-sm-3"><span class="h4 block" style="color:#fff;">Uscite</span>
		                       			<span class="font-thin" id="A2U" style="color:#fff;text-align:center;font-size:50px">-</span></div>
		                       		<div class="col-sm-3"></div>
		                       	</div>
		                       	-->
		                       	<a href="#">
		                          <span class="h3 block" style="margin-bottom:10px"><i class="icon-users i-lg"></i> Old Wild West</span>
		                          <span class="h4 block" style="margin-bottom:-60px">Massimo Numero Ospiti:<br/>&nbsp;<br/><span style="font-size:50px"><b>198</b></span>
		                          <br/><span style="font-size:30px" id="maxA2">Posti Disponibili</span></span>
		                          
		                          <span class="font-thin text-success" id="A2X" style="text-align:center;font-size:240px">-</span>
		                        </a>
		                       	<div class="row" style="margin-top:-50px">
		                       		<div class="col-sm-3"></div>
		                       		<div class="col-sm-6"><span class="h4 block text-danger">Persone Presenti</span>
		                       			<span class="font-thin text-danger" id="A2" style="text-align:center;font-size:50px">-</span>
		                       		</div>
		                       		<div class="col-sm-3"></div>
		                       	</div>
		                    </div>
		                </div>
					  </div>
					  
					  
					  <div class="row row-sm m-t">
						<div class="col-xs-4" align="left">
							<img src="logo.png" style="height:30px"/><br/>
							<span class="h2" style="margin-top:-10px">www.mcsistemi.net</span>
							
						</div>
						<div class="col-xs-4" align="center">
					  		
					  		<span class="h2" style="margin-top:-10px">Condominio Le Grotte di Napoli</span>
					  		
					  	</div>
					  	<div class="col-xs-4" align="right">
					  		<img src="logosien.png" style="height:30px"/><br/>
					  		<span class="h2" style="margin-top:-10px">www.sienimpianti.it</span>
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
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <script type="text/javascript">
  
  
  	var alarmA1 = 2700;
  	var alarmA2 = 180;
  	
  	
  	/*
    setTimeout(function(){
    	window.location.href = 'index.jsp';
	}, 60000*1);
  	*/
  	$.ajax({
		url : "area_count.jsp",
		success : function(res) {
			
			testMessage(res);
	  		
		}
	});	
  
  	function reconnect() {
  		window.location.href = 'index.jsp';
  	}
  
	var webSocket;
	
	webSocket = new WebSocket("ws://192.168.1.11:8080/HellaWS04/push");
	
	
	
	var timerID = 0; 
  	function keepAlive() { 
  	    var timeout = 5000;  
  	    if (webSocket.readyState == webSocket.OPEN) {  
  	    	console.log('keepAlive');
  	        //webSocket.send('');  
  	    }  
  	    timerId = setTimeout(keepAlive, timeout);  
  	}  
  	function cancelKeepAlive() {  
  	    if (timerId) {  
  	        clearTimeout(timerId);  
  	    }  
  	}
	/*
	function reconnect() {
		webSocket = new WebSocket("ws://suite.smartretail.it/HellaWS04/push");
  	}
	*/
	
	webSocket.onopen = function(message){ 
		wsOpen(message);
	
	};
	webSocket.onmessage = function(message){ 
		wsGetMessage(message);
	};
	webSocket.onclose = function(message){ 
		//wsClose(message);
		console.log('websocket chiuso... refresh '+message);
		webSocket.close();
		
		
		webSocket = new WebSocket("ws://192.168.1.11:8080/HellaWS04/push");
		
	};
	webSocket.onerror = function(message){ 
		wsError(message);
		
		console.log('error '+message);
	};
	
	function wsOpen(message){
		console.log("Connected ...");
		keepAlive();
	}
	function wsCloseConnection(){
		
		
		webSocket.close();
	}
	
	
	var l = '[{"id":1,"description":"Hall","ingressi":243,"uscite":212,"presenze":31},{"id":2,"description":"Old Wild West","ingressi":0,"uscite":0,"presenze":0},{"id":3,"description":"Cinema","ingressi":0,"uscite":0,"presenze":0},{"id":4,"description":"Mandara","ingressi":41,"uscite":46,"presenze":-5},{"id":5,"description":"Garage","ingressi":34,"uscite":16,"presenze":18}]';
	
	//testMessage(l);
	
	function testMessage(message){
		
		var lista = JSON.parse(message);
		var size = lista.length;
  		    
  		//alert(size);
		
  		$.each(lista, function(index, lista) {
 			
  			var presenze = lista.presenze;
			if(parseFloat(presenze)<0)
				presenze = 0;
			
			
  			if(lista.id==1) {
  				if(parseFloat(lista.presenze)>=parseFloat(alarmA1)) {
  					$("#maxA1").addClass("text-danger");
  					$("#A1").addClass("text-danger");
  				}
  				else {
  					$("#maxA1").removeClass("text-danger");
  					$("#maxA1").addClass("text-light");
  					
  					$("#A1").removeClass("text-danger");
  					$("#A1").addClass("text-light");
  				}
  			}
  			
  			if(lista.id==2) {
  				
  				
  				$("#A2X").html(198-(presenze));

  				if(parseFloat(lista.presenze)>=parseFloat(alarmA2)) {
  					$("#maxA2").addClass("text-danger");
  					$("#A2").addClass("text-danger");
  				}
  				else {
  					$("#maxA2").removeClass("text-danger");
  					$("#maxA2").addClass("text-light");
  					
  					$("#A2").removeClass("text-danger");
  					$("#A2").addClass("text-danger");
  				}
  			}
  			
  			//alert(lista.description);
  			$('#A'+lista.id).html(presenze);
  			$('#A'+lista.id+"E").html(lista.ingressi);
  			$('#A'+lista.id+"U").html(lista.uscite);
 		 	
 		});
		
	}
	
	function wsGetMessage(message){
		
		var d = JSON.stringify(message.data);
		
		console.log(new Date()+' '+JSON.stringify(message.data));
		if(d!='"Push"') {
			try { 
				var lista = JSON.parse(message.data);
				var size = lista.length;
		  		    
		  		//alert(size);
				
		  		$.each(lista, function(index, lista) {
		 			
		  			var presenze = lista.presenze;
					if(parseFloat(presenze)<0)
						presenze = 0;
					
		  			if(lista.id==1) {
		  				if(parseFloat(lista.presenze)>=parseFloat(alarmA1)) {
		  					$("#maxA1").addClass("text-danger");
		  					$("#A1").addClass("text-danger");
		  				}
		  				else {
		  					$("#maxA1").removeClass("text-danger");
		  					$("#maxA1").addClass("text-light");
		  					
		  					$("#A1").removeClass("text-danger");
		  					$("#A1").addClass("text-light");
		  				}
		  			}
		  			
		  			if(lista.id==2) {
		  				
		  				
		  				$("#A2X").html(198-(presenze));
		
		  				
		  				if(parseFloat(lista.presenze)>=parseFloat(alarmA2)) {
		  					$("#maxA2").addClass("text-danger");
		  					$("#A2").addClass("text-danger");
		  				}
		  				else {
		  					$("#maxA2").removeClass("text-danger");
		  					$("#maxA2").addClass("text-light");
		  					
		  					$("#A2").removeClass("text-danger");
		  					$("#A2").addClass("text-danger");
		  				}
		  			}
		  			
		  			//alert(lista.description);
		  			$('#A'+lista.id).html(presenze);
		  			$('#A'+lista.id+"E").html(lista.ingressi);
		  			$('#A'+lista.id+"U").html(lista.uscite);
		 		 		
		 		});
		  		
			}
			catch(error) {
				console.log('error');
			}
		}
		
	}
	function wsClose(message){
		console.log("Disconnect ...");
	}

	function wserror(message){
		alert("ERROR!");
	}
	</script>
</body>
</html>
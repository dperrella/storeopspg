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
//response.setIntHeader("Refresh", 60);
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
						<div class="col-xs-12" align="center">
							<div class="bg-dark wrapper-md r">
		                        <a href="#">
		                          <span class="h3 block" style="margin-bottom:10px"><i class="fa fa-building-o i-lg"></i> Store Decathlon </span>
		                          <span class="h4 block" style="margin-top:50px;margin-bottom:-80px">Number of People in Store</span>
			                      
		                          <span class="font-thin" id="A1" style="color:#fff;text-align:center;font-size:300px">-</span>
		                        </a>
		                        <div class="row" style="margin-top:-30px">
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
  
  	
  	
  	
	var webSocket2;
	webSocket2 = new WebSocket("ws://192.168.51.209:8080/easws");
	
	var timerId2 = 0; 
  	function keepAlive2() { 
  	    var timeout = 5000;  
  	    if (webSocket2.readyState == webSocket2.OPEN) {  
  	    	console.log('ping');
  	        webSocket2.send('ping');  
  	    }  
  	    timerId2 = setTimeout(keepAlive2, timeout);  
  	}  
  	function cancelKeepAlive2() {  
  	    if (timerId) {  
  	        clearTimeout(timerId2);  
  	    }  
  	}
	/*
	function reconnect() {
		webSocket = new WebSocket("ws://suite.smartretail.it/HellaWS04/push");
  	}
	*/
	
	webSocket2.onopen = function(message){ 
		wsOpen2(message);
	
	};
	webSocket2.onmessage = function(message){ 
		wsGetMessage2(message);
	};
	webSocket2.onclose = function(message){ 
		//wsClose(message);
		console.log('websocket chiuso... refresh');
		webSocket2.close();
		
		
		webSocket2 = new WebSocket("ws://192.168.51.209:8080/easws");
		
	};
	webSocket2.onerror = function(message){ 
		wsError(message);
		
		console.log('error '+message);
	};
	
	function wsOpen2(message){
		console.log("Connected ...");
		keepAlive2();
	}
	
	function wsGetMessage2(message) {
		alert(message);
	}
	
	
	function testMessage(message){
		
		//alert(message);
		var lista = JSON.parse(message);
		var size = lista.length;
  		    
  		//alert(size);
		
  		$.each(lista, function(index, lista) {
 			
  			var presenze = lista.presenze;
			if(parseFloat(presenze)<0)
				presenze = 0;
			
			
  			
  			
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
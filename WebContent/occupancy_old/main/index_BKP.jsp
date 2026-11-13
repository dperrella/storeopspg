<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
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
		                          <span class="h3 block" style="margin-bottom:-40px"><i class="icon-users i-lg"></i> Metropolitan Hall</span>
		                          <span class="font-thin" id="A1" style="color:#fff;text-align:center;font-size:200px">-</span>
		                        </a>
		                        <div class="row">
		                       		<div class="col-sm-3"></div>
		                       		<div class="col-sm-3"><span class="h4 block" style="color:#fff;">Ingressi</span>
		                       			<span class="font-thin" id="A1E" style="color:#fff;text-align:center;font-size:50px">-</span>
		                       		</div>
		                       		<div class="col-sm-3"><span class="h4 block" style="color:#fff;">Uscite</span>
		                       			<span class="font-thin" id="A1U" style="color:#fff;text-align:center;font-size:50px">-</span></div>
		                       		<div class="col-sm-3"><div>
		                       		 
		                        </div>
		                    </div>
		                    
						</div>
					  </div>
					  
					  <div class="row row-sm m-t">
						<div class="col-xs-3" align="center">
							
							<div class="bg-dark dk wrapper-md r">
		                        <a href="#">
		                          <span class="h3 block"><i class="icon-users i-lg"></i> Old Wild West</span>
		                          <span class="text-muted font-thin" id="A2" style="text-align:center;font-size:100px">-</span>
		                        </a>
		                    </div>
							
						</div>
						<div class="col-xs-3" align="center">
							
							<div class="bg-dark dk wrapper-md r">
		                        <a href="#">
		                          <span class="h3 block"><i class="icon-users i-lg"></i> Cinema</span>
		                          <span class="text-muted font-thin" id="A3" style="text-align:center;font-size:100px">-</span>
		                        </a>
		                    </div>
		                    
						</div>
						<div class="col-xs-3" align="center">
							
							<div class="bg-dark dk wrapper-md r">
		                        <a href="#">
		                          <span class="h3 block"><i class="icon-users i-lg"></i> Mandara</span>
		                          <span class="text-muted font-thin" id="A4" style="text-align:center;font-size:100px">-</span>
		                        </a>
		                    </div>
							
						</div>
						<div class="col-xs-3" align="center">
							
							<div class="bg-dark dk wrapper-md r">
		                        <a href="#">
		                          <span class="h3 block"><i class="icon-users i-lg"></i> Garage</span>
		                          <span class="text-muted font-thin" id="A5" style="text-align:center;font-size:100px">-</span>
		                        </a>
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
		var webSocket = new WebSocket("ws://suite.smartretail.it/HellaWS04/push");
		
		webSocket.onopen = function(message){ 
			wsOpen(message);
		};
		webSocket.onmessage = function(message){ 
			wsGetMessage(message);
		};
		webSocket.onclose = function(message){ 
			wsClose(message);
		};
		webSocket.onerror = function(message){ 
			wsError(message);
		};
		
		function wsOpen(message){
			console.log("Connected ...");
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
  				
	  			//alert(lista.description);
	  			$('#A'+lista.id).html(lista.presenze);
	  			$('#A'+lista.id+"E").html(lista.ingressi);
	  			$('#A'+lista.id+"U").html(lista.uscite);
  		 		
  			});
			
			
			
			
		}
		
		function wsGetMessage(message){
			
			var lista = JSON.parse(message.data);
			var size = lista.length;
	  		    
	  		//alert(size);
			
	  		$.each(lista, function(index, lista) {
  				
	  			//alert(lista.description);
	  			$('#A'+lista.id).html(lista.presenze);
	  			$('#A'+lista.id+"E").html(lista.ingressi);
	  			$('#A'+lista.id+"U").html(lista.uscite);
  		 		
  			});
			
			
			
			
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
<!DOCTYPE html>
<html lang="it" class="app">

<%@ page 
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="pkg.utility.Utility"
import="pkg.properties.ReadProperties"
%>
<%

ReadProperties propLang = new ReadProperties("connection.properties");

String msg = ""+request.getParameter("msg");
msg=Utility.noNull(msg);

%>
<head>  
  <meta charset="utf-8" />
  <title>Smart Suite | Password Dimenticata</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="js/jPlayer/jplayer.flat.css?2" type="text/css" />
  <link rel="stylesheet" href="css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="css/animate.css?2" type="text/css" />
  <link rel="stylesheet" href="css/font-awesome.min.css?2" type="text/css" />
  <link rel="stylesheet" href="css/simple-line-icons.css?2" type="text/css" />
  <link rel="stylesheet" href="css/font.css?2" type="text/css" />
  <link rel="stylesheet" href="css/app.css?2" type="text/css" />  
    <!--[if lt IE 9]>
    <script src="js/ie/html5shiv.js"></script>
    <script src="js/ie/respond.min.js"></script>
    <script src="js/ie/excanvas.js"></script>
  <![endif]-->
  
  <style>
	#bg {
		width: 100%; height: 100%; top: 0; left: 0;
		background: url(images/HomePageMilano_2.jpg) no-repeat center top; position: fixed; z-index: -1;
		-webkit-background-size: cover;
		-moz-background-size: cover;
		-o-background-size: cover;
		background-size: cover;
	}
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
	
  </style>     
</head>
<!-- <body class="bg-info dker">-->
<body id="bg">

	<div id="loading-image2" style="display:none">
		<img src="images/spinner.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 200px;
				   height: 200px;
				   margin-top: -100px;
				   margin-left: -100px;" />
    </div>
    <section id="content" class="m-t-lg wrapper-md animated fadeInUp">    
    
    	<div class="container aside-xl">
    		
    	      <header class="wrapper text-center">
		      <img src="images/SmartSuite_logo_NEW.png" style="width:150px"/><br/><br/>
		      </header>
		      
		      <section class="m-b-lg">
		        <header style="color:#fff" class="wrapper text-center">
		          <strong id="smartsuite">Smart Suite - Password Dimenticata</strong>
		        </header>
		        	        
		        <div id="msg">
		        <%=msg %>
		        </div>
		        
		        <form method="post">
		          <div class="form-group" style="display:none;">
		            <input type="text" id="username" name="username" placeholder="Username" class="form-control rounded input-lg text-center no-border">
		          </div>
		          <div class="form-group">
		            <input type="text" id="email" name="email" placeholder="EMail" class="form-control rounded input-lg text-center no-border">
		          </div>
		          <button type="button" class="btn btn-lg btn-danger lt b-white b-2x btn-block btn-rounded" onclick="control()">
		          	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg" id="recpassword">Recupera Password</span></button>
		          
		          
		          <div class="line line-dashed"></div>
		          <p class="text-muted text-center"><small style="color:#fff;" id="haigia">Hai gi&agrave; un account?</small></p>
		          <a href="signinV2.htm" class="btn btn-lg btn-danger btn-block rounded" id="entra">Entra</a>
		          
		        </form>
		        
		      </section>
    	
    	</div>
    
  </section>  
  <!-- footer -->
  <footer id="footer" style="display: none;">
    <div class="text-center padder" style="margin-top: 7.2%;">
      <p>
        <!--  <small>Smart Suite Web Application<br>&copy; MC Sistemi 2016</small> -->
        <small>Smart Suite Web Application<br>&copy; <i> <a href="http://it.checkpointsystems.com/" target="_blank" style="text-decoration: none;">Checkpoint</a></i> Systems Italia SpA 2017</small>
      </p>
    </div>
  </footer>
  <!-- / footer -->
  <script src="js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="js/bootstrap.js"></script>
  <!-- App -->
  <script src="js/app.js"></script>  
  <script src="js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="js/app.plugin.js"></script>
  
    <script src="js/alertify.min.js"></script>
    <link href="css/alertify.core.css" rel="stylesheet">
	<link href="css/alertify.default.css" rel="stylesheet">
  
  <script>
  	function control(frm) {
  		
  		username = $('#username').val();
  		email = $('#email').val();
  		var msg = "";
  		var err = "";
  		/*
  		if(username.length==0) {
  			msg+="- Inserire il proprio username<br/>";
  			err = "S";
  			
  		}
  		*/
  		if(email.length==0) {
  			msg+="- Inserire la propria email<br/>";
  			err = "S";
  			
  		}
  		
  		if(err=="S") {
  			$('#msg').html('<div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert">&times;</button>'+
  						   '<i class="fa fa-ban-circle"></i><strong>Si &egrave; verificato un errore!</strong><br/>'+msg+'</div>');
  			return false;
		}
  		
  		
  		showLoader2();
  		
		$.ajax({
			url : "./smrecoverypassword",
			data : {
				Username : username,
				Mail : email
			},
			success : function(responseText) {
				
				hideLoader2();
		  		
				//alert(responseText);
				resp = responseText.split("$");

				if (resp[1] == "OK"){
					alertify.log("Password Changed and Sended");
					setTimeout(function(){ location.href="signin.jsp"; }, 2000);
				}else{
					alertify.error("Error Occurred");
				}
				
			}
		}); 		
	  	
  	}
  	
  </script>
<script>
function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
var userLang = navigator.language || navigator.userLanguage;
var lang = userLang.toUpperCase();

if(lang == "ES"){
	document.getElementById("smartsuite").innerHTML = "Smart Suite - Contrase&#241;a Olvidada";
	document.getElementById("recpassword").innerHTML = "Recuperar contrase&#241;a?";
	document.getElementById("haigia").innerHTML = "¿ya tienes una cuenta?";
	document.getElementById("entra").innerHTML = "Entra";
}else if(lang == "IT"){
	document.getElementById("smartsuite").innerHTML = "Smart Suite - Password Dimenticata";
	document.getElementById("recpassword").innerHTML = "Recupera Password";
	document.getElementById("haigia").innerHTML = "hai già un account?";
	document.getElementById("entra").innerHTML = "Entra";
}else{
	document.getElementById("smartsuite").innerHTML = "Smart Suite - Recovery Password";
	document.getElementById("recpassword").innerHTML = "Password Recovery";
	document.getElementById("haigia").innerHTML = "do you already have an account?";
	document.getElementById("entra").innerHTML = "Join";
}	
</script>  
</body>
</html>
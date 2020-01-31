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

if(msg.equals("E")) {
	// Errore signon	
	msg="<div class=\"alert alert-danger\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" + 
        "<i class=\"fa fa-ban-circle\"></i><strong>Si &egrave; verificato un errore!</strong><br/>Username o password errati. Riprovare.</div>";
}
else if(msg.equals("O")) {
	// signon corretto
	msg="<div class=\"alert alert-warning\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" + 
	    "<i class=\"fa fa-ban-circle\"></i><strong>Signoff effettuato correttamente.</strong></div>";
	    
	// Pulizia sessione campi....
	session.setAttribute("username", null);
	session.setAttribute("nome", null);
	session.setAttribute("cogn", null);
	session.setAttribute("tipo", null);
	session.setAttribute("tipoLogin", null);
	session.setAttribute("foto", null);
	session.setAttribute("mail", null);
	session.setAttribute("mnu", null);
	session.setAttribute("db", null);
	session.setAttribute("style", null);
	session.setAttribute("logo", null);
	session.setAttribute("sysinf", null);
	
	session.setAttribute("arrayCustomers", null);
	session.setAttribute("arrayStores", null);
	session.setAttribute("arrayCountry", null);
	
	session.setAttribute("is_admin", null);
	
}
%>
<head>  
  <meta charset="utf-8" />
  <title>Smart Suite | Signin</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="js/jPlayer/jplayer.flat.css?2" type="text/css" />
  <link rel="stylesheet" href="css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="css/animate.css?2" type="text/css" />
  <link rel="stylesheet" href="css/font-awesome.min.css?2" type="text/css" />
  <link rel="stylesheet" href="css/simple-line-icons.css?2" type="text/css" />
  <link rel="stylesheet" href="css/font.css?2" type="text/css" />
  <link rel="stylesheet" href="css/app.css?2" type="text/css" />  
<style>
#bg {
width: 100%; height: 100%; top: 0; left: 0;
background: url(images/hard3.png) no-repeat center top; position: fixed; z-index: 999;
-webkit-background-size: cover;
-moz-background-size: cover;
-o-background-size: cover;
background-size: cover;	
}
</style>     
</head>
<!-- <body class="bg-info dker" id="bg">-->
<body id="bg">

    <section id="content" class="m-t-lg wrapper-md animated fadeInUp">    
		<div class="container aside-xl">
    
    
    	      <header class="wrapper text-center">
		      <img src="images/logo-smart-retail3.png" style="width:300px"/><br/><br/>
		      </header>
		      
		      <section class="m-b-lg">
		        <header style="color:#fff" class="wrapper text-center">
		          <strong id="userpass">Inserisci username e password</strong>
		        </header>
<!-- 		        
				<div id="msgInfo">
			        <div class="alert alert-danger">
			  			<i class="fa fa-ban-circle"></i><strong>Si comunica che nei giorni 1 e 2 Luglio 2017 </strong>
			  			<br/>per manutenzione straordianria, si potranno verificare dei disservizi sulla piattaforma.
			  		</div>
		        </div>
-->		        
		        <div id="msg">
		        <%=msg %>
		        </div>
		        
		        <form method="post" action="login-esegui.htm" onsubmit="return control(this)">
		        <input type="hidden" name="cdapp" id="cdapp" value="SAN"/>
		          <div class="form-group">
		            <input type="text" id="username" name="username" placeholder="Username" class="form-control rounded input-lg text-center no-border">
		          </div>
		          <div class="form-group">
		             <input type="password" id="password" name="password" placeholder="Password" class="form-control rounded input-lg text-center no-border">
		          </div>
		          <button type="submit" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded">
		          	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg" id="enter">Entra</span></button>
		          
		          <div class="text-center m-t m-b"><a href="password-dimenticata_MC.jsp" style="color:#fff"><b><small id="dimenticata">Password dimenticata?</small></b></a></div>
		          <div class="text-center padder" style="margin-top: -3%;"><a style="color:#fff" href="checkdevices/checkdevicepages_MC.jsp"><small><b>Check Devices</b></small></a></div>
		          
		        </form>
		        
		      </section>
    	
    	</div>
    	
    
  </section>
  <!-- footer -->
  <footer id="footer">
    <div class="text-center padder" style="margin-top: 1%;">
      <p>
        <small style="color:#fff">SmartRetail Suite Web Application<br>&copy; <i> <a href="http://www.smartretail.it/" target="_blank" style="text-decoration: none;color:#fff">MC Sistemi</a></i> 2017</small>
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
  
  <script>
  	function control(frm) {
  		
  		username = $('#username').val();
  		password = $('#password').val();
  		var msg = "";
  		var err = "";

  		if(username.length==0) {
  			msg+="- Inserire il proprio username<br/>";
  			err = "S";
  		}
  		if(password.length==0) {
  			msg+="- Inserire la propria password<br/>";
  			err = "S";  			
  		}
  		if(err=="S") {
  			$('#msg').html('<div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert">&times;</button>'+
  						   '<i class="fa fa-ban-circle"></i><strong>Si &egrave; verificato un errore!</strong><br/>'+msg+'</div>');
  			return false;
		}
	  	
  	}
  </script>
<!-- Codice per accettazione cookie - Inizio -->
<script src="js/Langs.js"></script>
<!-- Codice per accettazione cookie - Fine -->
</body>
</html>
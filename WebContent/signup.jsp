<!DOCTYPE html>
<html lang="it" class="app">

<%@ page 
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="pkg.utility.Utility"
%>
<%
String msg = ""+request.getParameter("msg");
msg=Utility.noNull(msg);
%>
<head>  
  <meta charset="utf-8" />
  <title>Smart Counter | Signup</title>
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
</head>
<body class="bg-info dker">
  <section id="content" class="m-t-lg wrapper-md animated fadeInUp">    
    <div class="container aside-xl">
      <header class="wrapper text-center">
      <span style="font-size:100px;line-height:105px" ><i class="icon-feed"></i></span><br/>
      <span style="font-size:30px;line-height:20px">Smart <b>Suite</b></span>
<!-- 
      <img src="images/signin-logo.png" style="width:200px"/>
-->
      </header>
     
      <section class="m-b-lg">
        <header class="wrapper text-center">
          <strong>Smart Suite - Signup</strong>
        </header>
        
        <div id="msg">
        <%=msg %>
        </div>
        
        <form id="myForm" method="post" action="signup-esegui.htm" onsubmit="return control(this)">
          
          <div class="form-group">
            <input type="text" id="nome" name="nome" placeholder="Nome" class="form-control rounded input-lg text-center no-border">
          </div>
          
          <div class="form-group">
            <input type="text" id="cogn" name="cogn" placeholder="Cognome" class="form-control rounded input-lg text-center no-border">
          </div>
          
          <div class="form-group">
            <input type="text" id="mail" name="mail" placeholder="Email" class="form-control rounded input-lg text-center no-border">
          </div>
          
          <div class="checkbox i-checks m-b">
            <label class="m-l">
              <input type="checkbox" checked=""><i></i> Accetto i <a href="#">Termini e la Privacy</a>
            </label>
          </div>
          
          <button type="submit" class="btn btn-lg btn-warning lter lt b-white b-2x btn-block btn-rounded">
          	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg">Registrati</span></button>
          
          <div class="line line-dashed"></div>
          <p class="text-muted text-center"><small>Hai gi&agrave; un account?</small></p>
          <a href="signin.jsp" class="btn btn-lg btn-info btn-block rounded">Entra</a>
        </form>
        
      </section>
    </div>
  </section>
  <!-- footer -->
  <footer id="footer">
    <div class="text-center padder">
      <p>
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
  <script>
  	function control(frm) {
  		nome = $('#nome').val();
  		cogn = $('#cogn').val();
  		mail = $('#mail').val();
  		var msg = "";
  		var err = "";
  		
  		if(nome.length==0) {
  			msg+="- Inserire il proprio nome<br/>";
  			err = "S";
  		}
  		if(cogn.length==0) {
  			msg+="- Inserire il proprio cognome<br/>";
  			err = "S";
  		}
  		if(mail.length==0) {
  			msg+="- Inserire la propria mail<br/>";
  			err = "S";
  		}
  		if(err=="S") {
  			$('#msg').html('<div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert">&times;</button>'+
  						   '<i class="fa fa-ban-circle"></i><strong>Si &egrave; verificato un errore!</strong><br/>'+msg+'</div>');
  			return false;
		}
	  	
  	}
  	
  </script>
</body>
</html>
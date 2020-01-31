<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it" class="app">

<head>  
  <meta charset="utf-8" />
  <title>Smart Suite | Signin</title>
  <meta name="description" content="SmartSuite - CheckPointSystems" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  
  <link rel="stylesheet" href="js/jPlayer/jplayer.flat.css?2" type="text/css" />
  <link rel="stylesheet" href="css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="css/animate.css?2" type="text/css" />
  <link rel="stylesheet" href="css/font-awesome.min.css?2" type="text/css" />
  <link rel="stylesheet" href="css/simple-line-icons.css?2" type="text/css" />
  <link rel="stylesheet" href="css/font.css?2" type="text/css" />
  <link rel="stylesheet" href="css/app.css?2" type="text/css" />  
  
  <!-- Google API Signin -->
  <script src="https://apis.google.com/js/api:client.js"></script>
  <script src="js/google/signin.js?v=${ts}"></script>
  
  <style>
	#bg { 
		width: 100%; height: 100%; top: 0; left: 0;
		background: url(images/HomePageMilano_2.jpg) no-repeat center top; position: fixed; z-index: 999;
		-webkit-background-size: cover;
		-moz-background-size: cover;
		-o-background-size: cover;
		background-size: cover;	
	}
	#loading-image2 {
		background-color:rgba(67, 81, 95, 1);
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
  </style>    
</head>

<body id="bg">

	<div id="loading-image2" style="display:none">
		<img src="images/loading.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 208px;
				   height: 13px;
				   margin-top: -8px;
				   margin-left: -104px;" />		
  </div>
  
  <section id="content" class="m-t-lg wrapper-md animated fadeInUp">    
		<div class="container aside-xl">
    
    
    	      <header class="wrapper text-center">
		      <img src="images/SmartSuite_logo_NEW.png" style="width:150px"/><br/><br/>
		      </header>
		      
		      <section class="m-b-lg">
		        <header style="color:#fff" class="wrapper text-center">
		          <strong id="userpass">Insert Username and Password</strong>
		        </header>
	        
		        <div id="msg" class="text-white">
		        ${error}
		        </div>
		        
		        <form method="post" action="login-esegui.htm" onsubmit="return control(this)">
		        <input type="hidden" name="cdapp" id="cdapp" value="SAN"/>
		          <div class="form-group">
		            <input type="text" id="username" name="username" placeholder="Username" class="form-control rounded input-lg text-center no-border">
		          </div>
		          <div class="form-group">
		             <input type="password" id="password" name="password" placeholder="Password" class="form-control rounded input-lg text-center no-border">
		          </div>
		          <button type="submit" class="btn btn-lg btn-danger lt b-white b-2x btn-block btn-rounded">
		          	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg" id="enter">Entra</span></button>
		          
		          <div class="text-center m-t m-b text-white"><b>Or Join with</b></div>
		          
				  <div class="btn-group btn-group-justified m-b">
                          <a class="btn btn-lg btn-dark lt b-2x btn-block btn-rounded">
                            <img src="ms_logo.png" style="width:24px" align="left" /><span class="m-r-n-lg_" id="sign_in_button"> Microsoft</span>
                          </a>
                          
                          <a id="customBtn" class="btn btn-lg btn-default lt b-2x btn-block btn-rounded customGPlusSignIn">
                            <img src="g_logo.png" style="width:24px" align="left"  /><span class="m-r-n-lg_" id="sign_in_button2"> Google</span>
                          </a>
                        </div>
                  <div id="name"></div>
				  
				  <div class="text-center m-t m-b"><a href="password-dimenticata.jsp" style="color:#fff"><b><small id="dimenticata">Password dimenticata?</small></b></a></div>
		          <div class="text-center padder" style="margin-top: -3%;"><a style="color:#fff" href="checkdevices/checkdevicepages.jsp"><small><b>Check Devices</b></small></a></div>
		          
		          
		        </form>
		        
		        
		        <!--
		        <p style="visibility:hidden" id="sign_in_text" class="token">Sign in with your Microsoft Account.</p>
		        -->
		        
		        <input type="hidden" id="clientid_input" value="96014a6f-3040-4e2c-ac0f-f497788b5421"/>
                <input type="hidden" id="authority_select" value="common"/>
                <input type="hidden" id="scopes_input" value="https://graph.microsoft.com/user.read,https://graph.microsoft.com/user.readbasic.all"/>
		        
		      </section>
    	
    	</div>
    	
    
  </section>
  <!-- footer -->
  <footer id="footer">
    <div class="text-center padder" style="margin-top: 1%;">
      <p>
        <small style="color:#fff">Smart Suite Web Application<br>&copy; <i> <a href="http://it.checkpointsystems.com/" target="_blank" style="text-decoration: none;color:#fff">Checkpoint</a></i> Systems Italia SpA 2017, 2019</small>
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
  	startApp();
  
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

	<!-- MSAL AZURE -->
	
	<script src="msal/js/msal.min.js"></script>
    <script src="msal/js/sign_in.js?v=${ts}"></script>
    <script src="msal/js/jwt_decoder.js?v=${ts}"></script>
   
	<!-- MSAL GOOGLE -->

	<script>
	function onSignIn(googleUser) {
	  var profile = googleUser.getBasicProfile();
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  console.log('Name: ' + profile.getName());
	  console.log('Image URL: ' + profile.getImageUrl());
	  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
	  
	  // The ID token you need to pass to your backend:
      var id_token = googleUser.getAuthResponse(true).id_token;
	  var access_token = googleUser.getAuthResponse(true).access_token;
	  
	  console.log(id_token);
	  console.log(access_token);
	  
	  
	  //console.log(JSON.stringify(googleUser));
	  
	  post('auth.htm', {name: profile.getName(), idusr: profile.getEmail(), id_token: id_token, access_token: access_token, foto: profile.getImageUrl(), origin: 'Google'});
	  
	  
	}
	
	
	
	
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
	<c:if test = "${action = 'signout'}">
	signOut();
	</c:if>
	</script>
    
</body>
</html>
   
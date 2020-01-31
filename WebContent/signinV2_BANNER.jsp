<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it" class="app" itemscope itemtype="http://schema.org/Article">
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
  
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js">
  </script>
  <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer>
  </script>
  <script>
    var auth2;
    function start() {
      gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
          client_id: '979975407473-f60m8ppi1vl2behb0pjk06lu17h1p3fn.apps.googleusercontent.com',
          // Scopes to request in addition to 'profile' and 'email'
          //scope: 'additional_scope'
          prompt: 'select_account',
        });
        attachSignin(document.getElementById('customBtn'));
      });
    }
    
    function attachSignin(element) {
        console.log(element.id);
        auth2.attachClickHandler(element, {},
            function(googleUser) {
              	onSignIn(googleUser);
              
            }, function(error) {
              alert(JSON.stringify(error, undefined, 2));
            });
      }
    
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
  	  
  	  
  	  console.log(JSON.stringify(googleUser));
  	  
  	  post('auth.htm', {name: profile.getName(), idusr: profile.getEmail(), id_token: id_token, access_token: access_token, foto: profile.getImageUrl(), origin: 'Google'});
  	  
  	  
  	}
  </script>
  
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

<section class="vbox">          
<section class="scrollable wrapper">

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
  
  
  		<div class="container" align="center">
    		<div id="msgInfo" style="width:640px" align="left">
		        
		        <!-- 
		        <div align="center">
		  				<img src="in-construction.png" style="height:100px"/>
		  		</div>
		  		-->
		  		
		        <div class="alert bg-white">
		  			
		  			<div class="row">
		  				<div class="col-sm-2">
		  					<img src="england.png"/>
		  				</div>
		  				<div class="col-sm-10">
		  					Dear user, we are working on a system upgrade. On 14th and 15th January there may be some inefficiencies and slowdowns. We will do everything possible to minimize the impact on the functionality of the portal. Please contact Checkpoint Systems Technical Support for any information.
		  				</div>
		  			</div>
		  			<hr/>
		  			<div class="row">
		  				<div class="col-sm-2">
		  					<img src="italy.png"/>
		  				</div>
		  				<div class="col-sm-10">
		  					Gentile utente, stiamo lavorando per effettuare un upgrade del sistema. Nei giorni 14/15 Gennaio potrebbero verificarsi dei disservizi e dei rallentamenti. Faremo il possibile per limitare al minimo l'impatto sulla funzionalità del portale. Per qualsiasi informazione la preghiamo di rivolgersi al servizio di assistenza tecnica di Checkpoint Systems.
						</div>
		  			</div>
		  			<hr/>
		  			<div class="row">
		  				<div class="col-sm-2">
		  					<img src="spain.png"/>
		  				</div>
		  				<div class="col-sm-10">
		  					Estimado usuario, estamos trabajando en una actualización del sistema. Los días 14 y 15 de enero puede haber algunas ineficiencias y desaceleraciones. Haremos todo lo posible para minimizar el impacto en la funcionalidad del portal. Póngase en contacto con el servicio de asistencia técnica de Checkpoint Systems para obtener más información.
						</div>
		  			</div>
		  			<hr/>
		  			<div class="row">
		  				<div class="col-sm-2">
		  					<img src="german.png"/>
		  				</div>
		  				<div class="col-sm-10">
		  					Sehr geehrter Benutzer, wir arbeiten an einem System-Upgrade. Am 14. und 15. Januar kann es zu einigen Ineffizienzen und Verlangsamungen kommen. Wir werden alles tun, um die Auswirkungen auf die Funktionalität des Portals zu minimieren. Bitte kontaktieren Sie den technischen Support von Checkpoint Systems für weitere Informationen.
						</div>
		  			</div>
		  			
		  			
		  		</div>
	        </div>
		</div>
    	
    	
		<div class="container aside-xl col-4">
    		
    		
    				<!-- 
    		        <header class="text-center">
                    <img src="images/SmartSuite_logo_NEW.png" style="width:150px"/><br/><br/>
		            </header>
                    -->
                    
                      <div id="msg" class="text-white text-center">
				        <!-- 
				        ${error}
				        <br/>
				        ${action}
				        -->
				        <b>${message}</b>
				      </div>
				      
                      <header class="wrapper text-center">
				          <strong id="userid" class="text-white">Insert User ID</strong>
				      </header>
			        
				      
				      <div id="userForm">
					      <form id="userFormX" method="post" action="#" onsubmit="return control(this)">
					          
					          <div class="form-group">
					            <input type="text" id="username" name="username" value="${userID}" placeholder="User ID" class="form-control input-lg text-center no-border rounded">
					          </div>
					          
					          <button type="submit" class="btn btn-lg btn-danger lt b-white b-2x btn-block btn-rounded">
					          	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg" id="next">Next</span></button>
					      </form>
				      </div>
				      
				      <div id="loginForm" style="display:none">
				      	  <form id="loginForm" method="post" action="login-esegui.htm" onsubmit="return controlLogin(this)">
					          <input type="hidden" name="cdapp" id="cdapp" value="SAN"/>
					          
					          <div class="form-group">
					            <input type="text" id="username2" name="username" placeholder="User ID" 
					            	class="form-control input-lg text-center no-border rounded">
					          </div>
					          <div class="form-group">
					             <input type="password" id="password" name="password" placeholder="Password" 
					             class="form-control input-lg text-center no-border rounded">
					          </div>
					          
					          <button type="submit" id="loginButton" class="btn btn-lg btn-danger lt b-white b-2x btn-block btn-rounded">
					          	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg" id="next">Sign In</span></button>
					      </form>
					      
				      </div>
				      
				      <div class="checkbox i-checks m-b">
			            <label class="m-l text-white">
			              <input type="checkbox" id="rememberMe" checked=""><i></i> <b>Remember Me</b>
			            </label>
			          </div>
			          
				      
				      <div id="loader" class="m-t text-white text-center" style="display:none">
				      	<img src="images/loading.gif" alt="Loading..."/> 
				      </div>
				      
				      <div id="join" style="display:none">
				      		<div class="text-center m-t m-b text-white"><b>Or Join with</b></div>
			          
					  		<div class="btn-group btn-group-justified m-b">
	                          <a id="customBtnMS" class="btn btn-lg btn-dark lt b-2x btn-block btn-rounded">
	                            <img src="ms_logo.png" style="width:24px" align="left" /><span class="m-r-n-lg_" id="sign_in_button"> Microsoft</span>
	                          </a>
	                          
	                          <a id="customBtn" class="btn btn-lg btn-default lt b-2x btn-block btn-rounded customGPlusSignIn">
	                            <img src="g_logo.png" style="width:24px" align="left"  /><span class="m-r-n-lg_" id="sign_in_button2"> Google</span>
	                          </a>
	                        </div>
	                  		<div id="name"></div>
	                  </div>
				      
          		 	  <div class="text-center m-t-xl m-b"><a href="password-dimenticata.jsp" style="color:#fff"><b><small id="dimenticata">Password dimenticata?</small></b></a></div>
		          	  <div class="text-center padder" style="margin-top: -3%;"><a style="color:#fff" href="checkdevices/checkdevicepages.jsp"><small><b>Check Devices</b></small></a></div>
		          				    
				    
                    
    	      
		      
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
  
</section>
</section>
  <!-- / footer -->
  <!--
  <script src="js/jquery.min.js"></script>
  -->
  <!-- Bootstrap -->
  <script src="js/bootstrap.js"></script>
  <!-- App -->
  <script src="js/app.js?v=${ts}"></script>  
  <script src="js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="js/app.plugin.js?v=${ts}"></script>
  <!-- Codice per accettazione cookie - Inizio -->
  <script src="js/Langs.js?v=${ts}"></script>
  <!-- Codice per accettazione cookie - Fine -->
 
  <script>
  	
  	var reqUsername = '${userID}';
  	
  	var username = getCookie("username");
  	$('#username').val(username);
  	
  	if(reqUsername.length>0) {
  		$('#username').val(reqUsername);
  		$('#userFormX').submit();
  	}
  	var action = '${action}';
  	if(action=='error') {
  		  $('#userForm').hide();
      	  $('#loginForm').show();
      	  $('#username2').val(username);
      	  $('#join').show();
    	  
  	}
  	//$('#loader').hide();
  	function control(frm) {
  		
  		username = $('#username').val();
  		
  		$('#msg').html('');

  		if(username.length==0) {
  			return false;
  		}
  		else {
  			
  			$('#loader').show();
  			if($('#rememberMe').is(":checked")) {
  	  			setCookie("username", username, 365);
  			}
  	  		
  			$.ajax({
	  		      url: 'users/checkUserOrigin',
	  		      type: 'POST',
	  		      dataType: 'html',
	  		      async: true,
	  		      data: 'username='+username,
	  		      success: function (data) {
	  		    	  
	  		    	//alert(data);
	  		    	$('#loader').hide();
	  		    	
	  		    	switch($.trim(data)) {
		  		      case 'Microsoft':
		  		          // redirect to secure/auth
		  		          $('#loader').show();
		  		          location.href = 'secure/auth.htm';
		  		          
		  		    	  break;
		  		      case 'Suite':
		  		      	  // check for Password
		  		      	  $('#userForm').hide();
		  		      	  $('#loginForm').show();
		  		      	  
		  		      	  $('#username2').val(username);
		  		      
		  		    	  break;
		  		      	  
		  		      case 'Google':
		  		    	  
		  		    	  // redirect to Google sign in
		  		    	  console.log('open Google!!!');
		  		    	  
		  		    		//auth2.grantOfflineAccess().then(signInCallback);
		  		        	$('#customBtn').show();
		  		    		document.getElementById('customBtn').click();
		  		    		
		  		    		  
		  		    	  break;
		  		    	  
		  		      case 'Unknown':
		  		    	  // ask for Suite / Microsoft/ Google
		  		    	 // $('#userForm').hide();
		  		      	 // $('#loginForm').show();
		  		      	  $('#loader').show();
		  		    	  location.href = 'signinV2.htm?action=error';
		  		    	  
		  		    	  break;
		  		      
		  		    }
	  		    	
	  			  },
	  		      error: function () {
	  		      	alert('Error!');
	  		      }
	  		});
	  			
  			return false;
  		}
  		
  	}
  	function controlLogin(frm) {
  		
  		username = $('#username').val();
  		password = $('#password').val();
  		
  		if($.trim(username)==''||$.trim(password)=='') {
  			return false;
  		}
  		else {
  			$('#loginButton').attr("disabled", true);
  		}
  		
  	}
  	function checkID(event) {
  		
  		username = $('#username').val();
  		password = $('#password').val();
  		
  		if($.trim(username)==''||$.trim(password)=='') {
  			$('#loginButton').attr("disabled", true);
  		}
  		else {
  			$('#loginButton').removeAttr("disabled");
  		}
  		
  	}
  	
  	
  	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
	
	$( "#customBtnMS" ).click(function() {
		location.href = 'setSession?origin=Microsoft';
	});
	
	/*
	$( "#customBtn" ).click(function() {
		
		document.getElementById('elementID').click();
		
	});
	*/
	
	
	
	
	
	</script>
    
</body>
</html>
   
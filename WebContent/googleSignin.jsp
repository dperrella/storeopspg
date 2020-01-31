<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- The top of file index.html -->
<html itemscope itemtype="http://schema.org/Article">
<head>
  <!-- BEGIN Pre-requisites -->
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js">
  </script>
  <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer>
  </script>
  <!-- END Pre-requisites -->
  <!-- Continuing the <head> section -->
  <script>
    function start() {
      gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
          client_id: '979975407473-f60m8ppi1vl2behb0pjk06lu17h1p3fn.apps.googleusercontent.com',
          // Scopes to request in addition to 'profile' and 'email'
          //scope: 'additional_scope'
          prompt: 'select_account',
        });
        
        auth2.grantOfflineAccess().then(signInCallback);
        
        
      });
    }
  </script>
</head>
<body>
  <script>
  
  function signInCallback(authResult) {
	  if (authResult['code']) {
		
		console.log(JSON.stringify(authResult));
		
		//alert("store to server...");
		
		/*
	    // Send the code to the server
	    $.ajax({
	      type: 'POST',
	      url: 'http://example.com/storeauthcode',
	      // Always include an `X-Requested-With` header in every AJAX request,
	      // to protect against CSRF attacks.
	      headers: {
	        'X-Requested-With': 'XMLHttpRequest'
	      },
	      contentType: 'application/octet-stream; charset=utf-8',
	      success: function(result) {
	        // Handle or verify the server response.
	      },
	      processData: false,
	      data: authResult['code']
	    });
	    */
	  } else {
	    // There was an error.
		  alert("error...");
	  }
  }
  </script>
</body>

</html>
  var googleUser = {};
  var auth2;
  var startApp = function() {
    gapi.load('auth2', function(){
	  // Retrieve the singleton for the GoogleAuth library and set up the client.
	  auth2 = gapi.auth2.init({
		//test
		//client_id: '745718492001-7mufnbstb25vfphtv797sd6ir6krjhq2.apps.googleusercontent.com',
		//localhost
	    client_id: '700498649326-6vfr5fo14gtl6s4m2drfnjhr37ct9i4j.apps.googleusercontent.com',
	    cookiepolicy: 'single_host_origin',
	    // Request scopes in addition to 'profile' and 'email'
	    //scope: 'additional_scope'
	    prompt: 'select_account',
	    ux_mode: 'redirect',
	  });
	  attachSignin(document.getElementById('customBtn'));
	  
    });
  };

  function attachSignin(element) {
    console.log(element.id);
    auth2.attachClickHandler(element, {},
        function(googleUser) {
          document.getElementById('name').innerText = "Signed in: " +
              googleUser.getBasicProfile().getName();
          
          	onSignIn(googleUser);
          
        }, function(error) {
          alert(JSON.stringify(error, undefined, 2));
        });
  }
  
  
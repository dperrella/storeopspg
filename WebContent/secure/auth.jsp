<!DOCTYPE html>
<html lang="it" class="app">

<head>  
  <meta charset="utf-8" />
  <title>Smart Suite | Microsoft Auth</title>
  <meta name="description" content="Smart Suite - CheckPoint Systems" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../js/jPlayer/jplayer.flat.css?2" type="text/css" />
  <link rel="stylesheet" href="../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../css/animate.css?2" type="text/css" />
  <link rel="stylesheet" href="../css/font-awesome.min.css?2" type="text/css" />
  <link rel="stylesheet" href="../css/simple-line-icons.css?2" type="text/css" />
  <link rel="stylesheet" href="../css/font.css?2" type="text/css" />
  <link rel="stylesheet" href="../css/app.css?2" type="text/css" />  
    <!--[if lt IE 9]>
    <script src="../js/ie/html5shiv.js"></script>
    <script src="../js/ie/respond.min.js"></script>
    <script src="../js/ie/excanvas.js"></script>
  <![endif]-->
</head>
<body class="bg-dark dker">
  <section id="content" class="m-t-lg wrapper-md animated fadeInUp">    
    <div class="container">
      
      <header class="wrapper text-center"><img src="../images/gif-load.gif" /></header>
      
      <section class="m-b-lg">
        <header class="wrapper text-center">
          <strong>Please Wait... Loading Smart Suite</strong>
          
          <!-- 
          <br/><br/>
          
          <p>${name}</p>
          <p>${idusr}</p>
          <p>${id_token}</p>
          <p>${access_token}</p>
          <p>${foto}</p>
          <p>${origin}</p>
          -->
          
          
        </header>
      </section>
    </div>
  </section>
  
  <script src="../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../js/app.js"></script>  
  <script src="../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../js/app.plugin.js"></script>
  
  <script>
  	  
  	post('../login-esegui-redirect.htm', {username: '<%=session.getAttribute("idusr")%>', fromAuth: 'Y'});
  	
  	function post(path, params, method='post') {

	  // The rest of this code assumes you are not using a library.
	  // It can be made less wordy if you use one.
	  const form = document.createElement('form');
	  form.method = method;
	  form.action = path;

	  for (const key in params) {
	    if (params.hasOwnProperty(key)) {
	      const hiddenField = document.createElement('input');
	      hiddenField.type = 'hidden';
	      hiddenField.name = key;
	      hiddenField.value = params[key];

	      form.appendChild(hiddenField);
	    }
	  }

	  document.body.appendChild(form);
	  form.submit();
	}
  	
  	
  	
  	
  </script>
  
</body>
</html>
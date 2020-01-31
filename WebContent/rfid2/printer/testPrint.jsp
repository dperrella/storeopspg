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
</head>
<body>

	      <section class="scrollable padder-lg " id="bjax-target">
	      
	      <div class="row row-sm">
			<h1>ZPL Test</h1>
			
			<form id="frm" method="post" style="width:650px">
			
			<label>Ip Printer</label>
			<br/>
			<input style="width:100%;" type="text" name="ip" id="ip"value="" class="form-control" placeholder="ip"/>
			<hr/>
			<label>Port Printer</label>
			<br/>
			<input style="width:100%;" type="text" name="port" id="port" value="" class="form-control"placeholder="port"/>
			<hr/>
			<label>Zpl</label>
			<br/>
			<textarea style="height:300px; width:100%" name="zpl" class="form-control"id="zpl"></textarea>
			<hr/>
			<a href="#" class="btn btn-primary btn-lg" onclick="openSocket()">Open Socket</a>
			</form>
		  
		  </div>
		  </section>
		

<script>

</script>
</body>
</html>
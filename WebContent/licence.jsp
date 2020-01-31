<!DOCTYPE html>
<html lang="it" class="app">

<%@ page 
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="pkg.utility.Utility"
import="pkg.utility.QRCodeGenerator"
import="java.util.Date"
import="pkg.utility.TrippleDes"
import="pkg.properties.ReadProperties"
%>

<%
long ts = new Date().getTime();
%>

<head>  
  <meta charset="utf-8" />
  <title>Smart Suite | CKP RFID-QC Licence</title>
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
  <script src="js/google/signin.js?v=<%=ts%>"></script>
<%
String fileName = "";
String QR_CODE_IMAGE_PATH = getServletContext().getRealPath("MyQRCode.png");
System.out.println(QR_CODE_IMAGE_PATH);

ReadProperties propLang = new ReadProperties("connection.properties");
String mac = ""+request.getParameter("mac");
mac = Utility.noNull(mac);

String qrcode = "";
if(mac.length()>0) {
	
	String licence = "";
	try {
        TrippleDes td = new TrippleDes();
        licence = td.encrypt(mac.toUpperCase());
    }
    catch(Exception e) {
        e.printStackTrace();
    }
	System.out.println(licence);
	QRCodeGenerator.generateQRCodeImage(licence, 350, 350, QR_CODE_IMAGE_PATH);
	
	qrcode = "<div class=\"text-center m-t m-b\">Use the Camera to Activate Licence</div>"+
	         "<img src=\"MyQRCode.png\" style=\"width:350px;height:350px\"/>";
	
}
%> 
  
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
<!-- <body class="bg-info dker" id="bg">-->
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
		          <strong id="userpass">Insert MAC Address</strong>
		        </header>
	        
		        <div id="msg">
		        </div>
		        
		        <form method="post" action="licence.jsp">
		          <div class="form-group">
		            <input type="text" id="mac" name="mac" placeholder="MAC Address" value="<%=mac%>" class="form-control rounded input-lg text-center no-border">
		          </div>
		          <button type="submit" class="btn btn-lg btn-danger lt b-white b-2x btn-block btn-rounded">
		          	<i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg" id="enter">Generate Licence</span></button>
		          
		          <div class="text-center m-t m-b text-white"><b><%=qrcode %></b></div>
		        </form>
		       
		      </section>
    	
    	</div>
    	
    
  </section>
  <!-- footer -->
  <footer id="footer">
    <div class="text-center padder" style="margin-top: 1%;">
      <p>
        <small style="color:#fff">Smart Suite Web Application<br>&copy; <i> <a href="http://it.checkpointsystems.com/" target="_blank" style="text-decoration: none;color:#fff">Checkpoint</a></i> Systems Italia SpA 2017</small>
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
	
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
  </script>
    
</body>
</html>
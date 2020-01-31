<!DOCTYPE html>
<html lang="it" class="app">

<%@ page 
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="pkg.utility.Utility"
import="java.sql.*"
import="pkg.properties.ReadProperties"
%>
<%
String sql = "";
String routertype = "";

//Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;

conn=Utility.getConnControl();

try{
st=conn.createStatement();

}catch (SQLException e) {
	e.printStackTrace();
}
sql = "select * from routertype ";
System.out.println("sql = " + sql);
//routertype = "<option value=''> -- Choise -- </option>";
rs = st.executeQuery(sql);
while(rs.next()) {
	routertype += "<option value='"+rs.getString("idrouter")+"'>"+rs.getString("description")+"</option>";
}
rs.close();

ReadProperties propLang = new ReadProperties("connection.properties");

%>
       
<head>  
  <meta charset="utf-8" />
  <title>Smart Suite | Check Devices</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../js/jPlayer/jplayer.flat.css" type="text/css" />
  <link rel="stylesheet" href="../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../css/app.css?2" type="text/css" />  
    
  <!--[if lt IE 9]>
    <script src="js/ie/html5shiv.js"></script>
    <script src="js/ie/respond.min.js"></script>
    <script src="js/ie/excanvas.js"></script>
  <![endif]-->
  
    <style>
	.vertical-text {
	/*
		transform: rotate(90deg);
		transform-origin: left top 0;
    */		
		color: #173553; 
		text-align: center; 
		font-weight: bold; 
		/*margin-left: 60px;*/	
	}    
	
	input[required]::placeholder {
		    color: #f05050;
		    opacity: 1; /* Firefox */

	}
	
	#bg {
		width: 100%; height: 100%; top: 0; left: 0;
		background: url(../images/hard3.png) no-repeat center top; position: fixed; z-index: -1;
		-webkit-background-size: cover;
		-moz-background-size: cover;
		-o-background-size: cover;
		background-size: cover;
	}
	
.nav-tabs > li.active > a, .nav-tabs > li.active > a:hover, .nav-tabs > li.active > a:focus {
    color: #173553;
    cursor: default;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-color: transparent;
}
a {
    color: #fff;
    text-decoration: none;
}

     #loading-image {
		background-color: #333;
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 99999999;
	}	
  </style>
  
</head>

<!-- <body class="bg-info dker" style="overflow: auto !important;">-->
<body id="bg" style="overflow: auto !important;">
  <section id="content" class="m-t-lg wrapper-md animated fadeInUp">    
	<div class="container" style="width: 50% !important;">
      <!-- 
      <header class="wrapper text-center">
      <img src="../images/SmartSuite_logo_NEW.png" style="width:150px"/><br/><br/>
      <span style="font-size:30px;line-height:20px; color:#fff;">Check <b>DEVICES</b></span>
      </header>
      -->
      
      <section class="m-b-lg" style="width: 100%;">
        <header class="text-center">
          <strong id="userpass"></strong>
        </header>
        
        <!--  <div id="msg" style="overflow: auto !important; max-height: 300px;"> -->
        <!-- pietro
        <div id="msg">
        </div>
        -->
        
        <!--  -->
<div>
  <div id="loading-image">
		<img src="../images/loading.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 208px;
				   height: 13px;
				   margin-top: -8px;
				   margin-left: -104px;" />
  </div>
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#ftpip" onclick="Msg('F')">Test Ftp Data</a></li>
    <li><a data-toggle="tab" href="#ftpweb" onclick="Msg('W')">Test Webservice</a></li>
    <li><a data-toggle="tab" href="#control" onclick="Msg('C')">Test Remote Control</a></li>
    <li><a data-toggle="tab" href="#router" onclick="Msg('R')">New Router</a></li>
  </ul>

  <div class="tab-content">
    <div id="ftpip" class="tab-pane fade in active">
      <h3 style="text-align: center; color:#fff;">Test Ftp Data</h3>
              <form method="post" action="login-esegui.htm">
          <div align="center">    
	          <div class="form-group">
	            <input type="text" id="accountid" name="accountid" style="width: 50%;" placeholder="Account Id" class="form-control rounded input-lg text-center no-border">
	          </div>
	          <div class="form-group">
	             <input type="text" id="storeid" name="storeid" style="width: 50%;" placeholder="Store Id" class="form-control rounded input-lg text-center no-border">
	          </div>
	          <div class="form-group">
	             <input type="text" id="doorid" name="doorid" style="width: 50%;" placeholder="Door Id" class="form-control rounded input-lg text-center no-border">
	          </div>
          </div>
          <div align="center">
	          <button type="button" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 50%;" onclick="return control(this)">CHECK</button>
	          <a href="../signin_MC.jsp" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 50%;">BACK TO LOGIN</a>
          </div>
          
        </form>
    </div>
    <div id="ftpweb" class="tab-pane fade">
      <h3 style="text-align: center; color:#fff;">Test Webservice</h3>
          <form method="post" action="login-esegui.htm">
          <div align="center">
	          <div class="form-group">
	            <input type="text" id="accountidw" name="accountidw" style="width: 50%;" placeholder="Account Id" class="form-control rounded input-lg text-center no-border">
	          </div>
	          <div class="form-group">
	             <input type="text" id="storeidw" name="storeidw" style="width: 50%;" placeholder="Store Id" class="form-control rounded input-lg text-center no-border">
	          </div>
	          <div class="form-group">
	             <input type="text" id="dooridw" name="dooridw" style="width: 50%;" placeholder="Door Id" class="form-control rounded input-lg text-center no-border">
	          </div>
          </div>
          <div align="center">
	          <button type="button" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 50%;" onclick="return controlweb(this)">CHECK</button>
	          <a href="../signin_MC.jsp" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 50%;">BACK TO LOGIN</a>
          </div>
        </form>
    </div>    
    <div id="control" class="tab-pane fade">
      <h3 style="text-align: center; color:#fff;">Test Remote Control</h3>
      <!-- <h3 style="text-align: center; visibility: hidden;">CONTROL</h3>-->
              <form method="post" action="login-esegui.htm">
			<div align="center">              
	          <div class="form-group">
	            <input type="text" id="ip" name="ip" placeholder="IP" style="width: 50%;" class="form-control rounded input-lg text-center no-border">
	          </div>
	          <div class="form-group">
	             <input type="text" id="port" name="port" placeholder="Port" style="width: 50%;" class="form-control rounded input-lg text-center no-border">
	          </div>
		          <div class="form-group" align="center" style="">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default active">
							<input name="chkradio" value="EV" type="radio" checked="checked">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EVOLVE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
						</label>
						<label class="btn btn-default">
							<input name="chkradio" value="EVS" type="radio">EVOLVESTORE
						</label>
						<label class="btn btn-default">
							<input name="chkradio" value="NEO" type="radio">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NEO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</label>						
			        </div>
		          </div>
			</div>
			
			<div id="loader" align="center"></div>
          
          <div align="center">
	          <button type="button" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 50%;" onclick="return controlEV(this)">CHECK</button>
			  <a href="../signin_MC.jsp" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 50%;">BACK TO LOGIN</a>
		  </div>          
        </form>
    </div>
    
<!--  -->
    <!-- <div id="router" class="tab-pane fade" style="max-height: 580px; overflow-y: auto; overflow-x: hidden;"> -->
    <div id="router" class="tab-pane fade">
      <h3 style="text-align: center; color:#fff;">New Router</h3>
      <!-- <h3 style="text-align: center; visibility: hidden;">CONTROL</h3>-->
              <form method="post" action="login-esegui.htm">
              
              <div align="center" class="row">
	             <div class="form-group">
		            <!-- <input type="text" id="ip" name="ip" placeholder="IP" style="width: 50%;" class="form-control rounded input-lg text-center no-border"> -->
		            <select id="routertype" name="routertype" style="width: 70%;" class="form-control rounded input-lg text-center no-border" onchange="SelRouter(this)">
		            	
		            </select>
		         </div>    
              </div>
              
			<div align="center" class="row" style="margin-top: 30px;">
			<!-- <div class="vertical-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SECTION CUSTOMER INFO</div>-->
			<div class="vertical-text">SECTION CUSTOMER INFO</div>
	          <div class="form-group">
	            <input type="text" id="customerid" name="customerid" placeholder="Customer Id" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
	          </div>
	          <div class="form-group">
	             <input type="text" id="customerdescription" name="customerdescription" placeholder="Customer Description" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
	          </div>
	          <!-- 
	          <div class="form-group">
	             <input type="text" id="accountidr" name="accountidr" placeholder="Account Id" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
	          </div>
	          -->	
	          <input type="hidden" id="accountidr" name="accountidr" placeholder="Account Id" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
	                    
	          <div class="form-group">
	             <input type="text" id="storeidr" name="storeidr" placeholder="Store Id" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
	          </div>	          
	          <div class="form-group">
	             <input type="text" id="storedescription" name="storedescription" placeholder="Store Description" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
	          </div>
	          <div class="form-group">
	             <input type="text" id="brepbins" name="brepbins" placeholder="Brep/Bins" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
	          </div>	          
		          <div class="form-group">
	             <input type="text" id="storesapcode" name="storesapcode" placeholder="Store Sap Code" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
	          </div>          
			</div>
			
			
			<div align="center" class="row" style="margin-top: 30px;">
			<!-- <div class="vertical-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SECTION CONNECTION</div>-->
			<div class="vertical-text">SECTION CONNECTION</div>
				  <div id="OpenVPN">
					  <div class="form-group">
			            <input type="text" id="clientopenvpn" name="clientopenvpn" placeholder="Client Open VPN" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
			          </div>
			          <div class="form-group">
			             <input type="text" id="ipaddressroutero" name="ipaddressroutero" placeholder="Ip Address Router" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
			          </div>
			          <div class="form-group">
			             <input type="text" id="sim" name="sim" placeholder="Sim YES/NO" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
			          </div>	          
		          </div>
				  <div id="WIND" style="display: none;">
					  <div class="form-group">
			            <input type="text" id="routercode" name="routercode" placeholder="Router Code" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
			          </div>
			          <div class="form-group">
			             <input type="text" id="ipaddressrouterw" name="ipaddressrouterw" placeholder="Ip Address Router" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
			          </div>        
		          </div>
				  <div id="EvolveNet Wireless Router" style="display: none;">
					  <div class="form-group">
			            <input type="text" id="ipaddressroutere" name="ipaddressroutere" placeholder="Ip Address Router" style="width: 70%;" class="form-control rounded input-lg text-center no-border" required>
			          </div>
			          <div class="form-group">
			             <input type="text" id="routerserialnumber" name="routerserialnumber" placeholder="Router Serial Number" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
			          </div>
			          <div class="form-group">
			             <input type="text" id="imsinumber" name="imsinumber" placeholder="IMSI Number" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
			          </div>	          
		          </div>		          
			</div>
			
			<div align="center" class="row" style="margin-top: 30px;">
			<!-- <div class="vertical-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SECTION HW INSTALLED</div>-->
			<div class="vertical-text">SECTION HW INSTALLED</div>
	          <div class="form-group">
	             <input type="text" id="masterantenna" name="masterantenna" placeholder="Master Antenna" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
	          </div>	          
	          <div class="form-group">
	             <input type="text" id="slaveantenna" name="slaveantenna" placeholder="Slave Antenna" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
	          </div>	          
	          <div class="form-group">
	             <input type="text" id="notes" name="notes" placeholder="Notes" style="width: 70%;" class="form-control rounded input-lg text-center no-border">
	          </div>        
<!--  -->
	          <div class="form-group">
	             <button id="btnPing" type="button" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 30%;" onclick="return pings()">&nbsp;&nbsp;Ping&nbsp; </button><button  type="button" id="btnTel" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded"  style="width: 30%;" onclick="return TelNets();">TelNet </button>
	          </div> 
<!--  -->			
			</div>			
          
          
          <div align="center">
	          <button type="button" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 80%;" onclick="return Registration(this)">REGISTRATION</button>
			  <a href="../signin_MC.jsp" class="btn btn-lg btn-success lt b-white b-2x btn-block btn-rounded" style="width: 80%;">BACK TO LOGIN</a>
		  </div>          
        </form>
    </div>
<!--  -->    
  </div>
</div>
        <!--  -->        
        
        <!-- <div class="text-center m-t m-b"><a href="password-dimenticata.jsp"><small id="dimenticata"></small></a></div>-->
        
          <div class="line line-dashed" style="visibility: hidden;"></div>
          <p class="text-muted text-center" style="visibility: hidden;"><small>Non hai ancora un account?</small></p>
          <a href="signup.jsp" class="btn btn-lg btn-info btn-block rounded" style="visibility: hidden;">Registrati</a>
        
      </section>
    </div>
  </section>

  <!-- footer -->
  <footer id="footer" style="display: hidden;">

  </footer>
  <!-- / footer -->
  <script src="../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../js/app.js"></script>  
  <script src="../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../js/app.plugin.js"></script>
  
    <script src="../js/alertify.min.js"></script>
    <link href="../css/alertify.core.css" rel="stylesheet">
	<link href="../css/alertify.default.css" rel="stylesheet">
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Test Devices</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="divTab" style="overflow: auto; max-height: 500px;">
        <table id="contegg" class="table table-striped table-bordered">
        	<tr>
        		<td width="100%" align="center"><img src="../images/loading.gif"/></td>
        	</tr>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
        <!-- <button type="button" class="btn btn-primary">Save changes</button>-->
      </div>
    </div>
  </div>
</div>
<!--  -->  
  <script>
  
  
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
	});
  
 
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
  
  
  function control() {
	  
	  

  		var msg = "";
  		var err = "";
  		var tabs = "<br><table id='maintable3' width='100%' style='align:center; width:100%;'><thead id='dinInt'></thead><tbody id='divTab'></tbody></table>";
  		
  		if	(document.getElementById("accountid").value == ""){
  			alertify.error("Account Id Required");
  			document.getElementById("accountid").focus();
  			return;	
  		}

  		if	(document.getElementById("storeid").value == ""){
  			alertify.error("Store Id Required");
  			document.getElementById("storeid").focus();
  			return;	
  		}

  		if	(document.getElementById("doorid").value == ""){
  			alertify.error("Door Id Required");
  			document.getElementById("doorid").focus();
  			return;	
  		}
		
  		
  		$('#exampleModal').modal('show');
  		
  		
  		$('#contegg').html("<tr><td width='100%' align='center'><image src='../images/loading.gif'></td></tr>");
  		
  		$('#BodyPage').html('<i class="fa fa-spinner fa-pulse fa-fw"></i>');
  		
  		
  		 		
  		$.ajax({
  			url : "../smcheckdevices",
  			//url: "http://pc.smart-control.it/SmartSuite/smcheckdevices",
  			data : {
  				account : document.getElementById("accountid").value,
  				store : document.getElementById("storeid").value,
  				door : document.getElementById("doorid").value
  			},
  			success : function(responseText) {
  				ret = responseText.split("$");
  				var msg = ret[0];
  				var nam = ret[1];
  				var tab = ret[2];  				
  				var tabint = ret[3];

  		  		$('#msg').html('<div class="alert alert-info"><button type="button" class="close" data-dismiss="alert">&times;</button>'+
  		  						   '<i class="fa fa-ban-circle"></i><strong>Configured on the Server</strong> '+msg+tabs+'</div>');
  				
  		  		
  		  		//$('#divTab').html(tab);
  		  		//alert("tabint = " + tabint)
  		  		//alert("tab = " + tab)
  		  		$('#contegg').html(tabint+tab);
  		  		$('#dinInt').html(tabint);
  		  		document.getElementById("msg").style.display = "inline";
  		  		$('#BodyPage').html('<i class="fa fa-desktop"></i>');
  		  		
  		  		
  		  		
  			}
  		});	
  		

  		
  	}
  	
  function controlweb() {
	  
		var msg = "";
		var err = "";
		var tabs = "<br><table id='maintable3' width='100%' style='align:center; width:100%;'><thead id='dinInt'></thead><tbody id='divTab'></tbody></table>";
		
		if	(document.getElementById("accountidw").value == ""){
			alertify.error("Account Id Required");
			document.getElementById("accountidw").focus();
			return;	
		}

		if	(document.getElementById("storeidw").value == ""){
			alertify.error("Store Id Required");
			document.getElementById("storeidw").focus();
			return;	
		}

		if	(document.getElementById("dooridw").value == ""){
			alertify.error("Door Id Required");
			document.getElementById("dooridw").focus();
			return;	
		}
		
		$('#exampleModal').modal('show');
		
		$('#contegg').html("<tr><td width='100%' align='center'><image src='../images/loading.gif'></td></tr>");
		
		$('#BodyPage').html('<i class="fa fa-spinner fa-pulse fa-fw"></i>');
		 		
		$.ajax({
			url : "../smcheckdevices",
			//url: "http://pc.smart-control.it/SmartSuite/smcheckdevices",
			data : {
				account : document.getElementById("accountidw").value,
				store : document.getElementById("storeidw").value,
				door : document.getElementById("dooridw").value
			},
			success : function(responseText) {
				ret = responseText.split("$");
				var msg = ret[0];
				var nam = ret[1];
				var tab = ret[2];  				
				var tabint = ret[3];

		  		$('#msg').html('<div class="alert alert-info"><button type="button" class="close" data-dismiss="alert">&times;</button>'+
		  						   '<i class="fa fa-ban-circle"></i><strong>Configured on the Server</strong> '+msg+tabs+'</div>');
				
		  		
		  		//$('#divTab').html(tab);
		  		//alert("tabint = " + tabint)
		  		//alert("tab = " + tab)
		  		$('#contegg').html(tabint+tab);
		  		$('#dinInt').html(tabint);
		  		document.getElementById("msg").style.display = "inline";
		  		$('#BodyPage').html('<i class="fa fa-desktop"></i>');
		  		
			}
		});	
		

		
	}
  
  	function PreReg() {  		

  		if	(document.getElementById("storename").value == ""){
  			alertify.error("StoreName Required");
  			document.getElementById("storename").focus();
  			return;	
  		}

  		if	(document.getElementById("storenotes").value == ""){
  			alertify.error("StoreNotes Required");
  			document.getElementById("storenotes").focus();
  			return;	
  		}
  		
  		if	(document.getElementById("accountid").value == ""){
  			alertify.error("Account ID Required");
  			document.getElementById("accountid").focus();
  			return;	
  		}  		
  		
  		if	(document.getElementById("storeid").value == ""){
  			alertify.error("Store ID Required");
  			document.getElementById("storeid").focus();
  			return;	
  		}  		
		

  		 		
  		$.ajax({
  			url : "../smcheckdevicespreregistration",
  			data : {
  				account : document.getElementById("accountid").value,
  				store : document.getElementById("storeid").value,
  				door : document.getElementById("doorid").value,
  				sname : document.getElementById("storename").value,
  				snote : document.getElementById("storenotes").value
  				
  			},
  			success : function(responseText) {
				
  				if (responseText == "OK"){
  					cla = "alert-success";
  					risp = "Pre-Recording Performed";
  				}else{
  					cla = "alert-danger";
  					risp = "Error in Pre-Recording";  					
  				}
  				/*
  		  		$('#msg').html('<div class="alert '+cla+'"><button type="button" class="close" data-dismiss="alert">&times;</button>'+
  		  						   '<i class="fa fa-ban-circle"></i><strong>'+risp+'</strong></div>');

  		  		document.getElementById("msg").style.display = "inline";
  		  		*/
  				$('#contegg').html("<tr><td align='center'>"+risp+"</td></tr>");
  		  		
  			}
  		});	
  		

  		
  	}
  	
/**/
  	
  	$("input[name=chkradio]").change(function() {
  		
  		var tipo = $('input[name=chkradio]:checked').val();
  		if(tipo=='EV') {
  			$('#port').val('2101');
  		}
  		else if(tipo=='EVS') {
  			$('#port').val('8081');
  		}
  		else {
  			$('#port').val('');
  		}
  		
  	});
  			
  	function controlEV() {

  		var msg = "";
  		var err = "";
  		var tabs = "<br><table id='maintable3' width='100%' style='align:center; width:100%;'><thead id='dinInt'></thead><tbody id='divTab'></tbody></table>";
  		
  		if	(document.getElementById("ip").value == ""){
  			alertify.error("IP Required");
  			document.getElementById("ip").focus();
  			return;	
  		}

  		if	(document.getElementById("port").value == ""){
  			alertify.error("Port Required");
  			document.getElementById("port").focus();
  			return;	
  		}
		
  		//$('#BodyPage').html('<i class="fa fa-spinner fa-pulse fa-fw"></i>');
  		
  		
  		$.ajax({
  			//url : "../smcheckdevicesev",
  			url: "http://cusago.smart-control.it:10080/SmartSuite/smcheckdevicesev",
  			//async: false,
  			data : {
  				ip : document.getElementById("ip").value,
  				port : document.getElementById("port").value,
  				chk : $('input[name=chkradio]:checked').val()
  			},
  			beforeSend: function(){
  				$('#loader').html("<p style=\"color:#fff\"><b>Loading...</b></p>");
  			},
  			success : function(responseText) {

  				$('#loader').html("");
  				
  				ret = responseText.split("$");
  				
  		  		if (ret[0] == "KO"){
  		  			alertify.alert("<b>Device Not found or DeviceType is Incorrect</b>")
  		  			return;
  		  		}
  		  		
  		  		// 0 --> OK / 1 --> ERR
  		  		if (ret[1] == "0"){
  		  			//alertify.alert(ret[2])
  		  			alertify.alert("<b>Device Found</b>")
  		  		}else{
  		  			alertify.alert("<b>Error</b>")
  		  		}
  		  		
  			}
  		});	
  		
  		

  		
  	}
/**/ 

function  Msg(str){
	
	if (str == "F"){
		document.getElementById("accountid").value = "";
		document.getElementById("storeid").value = "";
		document.getElementById("doorid").value = "";
	}else if(str == "W"){
		document.getElementById("accountidw").value = "";
		document.getElementById("storeidw").value = "";
		document.getElementById("dooridw").value = "";
	}else if(str == "C"){
		document.getElementById("ip").value = "";
		document.getElementById("port").value = "2101";
	}else{
		document.getElementById("customerid").value = "";
		document.getElementById("customerdescription").value = "";
	    document.getElementById("accountidr").value = "";
		document.getElementById("storeidr").value = "";
		document.getElementById("storedescription").value = "";
		document.getElementById("brepbins").value = "";
		document.getElementById("storesapcode").value = "";
		document.getElementById("clientopenvpn").value = "";
		document.getElementById("ipaddressroutero").value = "";
		document.getElementById("ipaddressroutere").value = "";
		document.getElementById("ipaddressrouterw").value = "";
		document.getElementById("sim").value = "";
		document.getElementById("routercode").value = "";
		document.getElementById("routerserialnumber").value = "";
		document.getElementById("imsinumber").value = "";
		document.getElementById("masterantenna").value = "";
		document.getElementById("slaveantenna").value = "";
		document.getElementById("notes").value = "";
	}
	
	document.getElementById("msg").style.display = "none"; 
}

var suffix = "o";
function SelRouter(str){
	
	
	
	var name = $("#routertype option[value='"+str.value+"']").text();
	
	switch(name) {
    case "OpenVPN":
    	document.getElementById(name).style.display = "inline";
    	document.getElementById("WIND").style.display = "none";
    	document.getElementById("EvolveNet Wireless Router").style.display = "none";
    	suffix = "o";
        break;
    case "EvolveNet Wireless Router":
    	document.getElementById(name).style.display = "inline";
    	document.getElementById("WIND").style.display = "none";
    	document.getElementById("OpenVPN").style.display = "none";
    	suffix = "e";
        break;
    case "WIND":
    	document.getElementById(name).style.display = "inline";
    	document.getElementById("OpenVPN").style.display = "none";
    	document.getElementById("EvolveNet Wireless Router").style.display = "none";
    	suffix = "w";
        break;        
	}
	
	
}

function Registration(obj){

	var ipaddrout = "";
	
		if	(document.getElementById("customerid").value == ""){
  			alertify.error("Customer Id Required");
  			document.getElementById("customerid").focus();
  			return;	
  		}
			
		if	(document.getElementById("customerdescription").value == ""){
  			alertify.error("Customer Description Required");
  			document.getElementById("customerdescription").focus();
  			return;	
  		}		

		if	(document.getElementById("storeidr").value == ""){
  			alertify.error("Store Id Required");
  			document.getElementById("storeidr").focus();
  			return;	
  		}		
		
		if	(document.getElementById("storedescription").value == ""){
  			alertify.error("Store Description Required");
  			document.getElementById("storedescription").focus();
  			return;	
  		}
		
		if	(document.getElementById("brepbins").value == ""){
  			alertify.error("Brep/Bins Required");
  			document.getElementById("brepbins").focus();
  			return;	
  		}		
	
		//brepbins
	
	
	if (suffix == "e"){
		ipaddrout = $("#ipaddressroutere").val();
		
		if	(document.getElementById("ipaddressroutere").value == ""){
  			alertify.error("IP Address Router Required");
  			document.getElementById("ipaddressroutere").focus();
  			return;	
  		}

	}else if(suffix == "o"){
		
		ipaddrout = $("#ipaddressroutero").val();
		
		if	(document.getElementById("clientopenvpn").value == ""){
  			alertify.error("Client OpenVpn Required");
  			document.getElementById("clientopenvpn").focus();
  			return;	
  		}

		if	(document.getElementById("ipaddressroutero").value == ""){
  			alertify.error("IP Address Router Required");
  			document.getElementById("ipaddressroutero").focus();
  			return;	
  		}
		
		if	(document.getElementById("sim").value == ""){
  			alertify.error("SIM Required");
  			document.getElementById("sim").focus();
  			return;	
  		}
		
	}else{
		
		ipaddrout = $("#ipaddressrouterw").val();
		
		if	(document.getElementById("routercode").value == ""){
  			alertify.error("Router Code Required");
  			document.getElementById("routercode").focus();
  			return;	
  		}

		if	(document.getElementById("ipaddressrouterw").value == ""){
  			alertify.error("IP Address Router Required");
  			document.getElementById("ipaddressrouterw").focus();
  			return;	
  		}

	}
	

	
		$.ajax({
  			url : "../SetRouters",
  			data : {
  				routertype : document.getElementById("routertype").value,
  				customerid : document.getElementById("customerid").value,
  				customerdescription : document.getElementById("customerdescription").value,
  				accountid : document.getElementById("accountidr").value,
 				storeid : document.getElementById("storeidr").value,  
 				storedescription : document.getElementById("storedescription").value,
 				brepbins : document.getElementById("brepbins").value,  
 				storesapcode : document.getElementById("storesapcode").value,
 				clientopenvpn : document.getElementById("clientopenvpn").value,
 				ipaddressrouter : ipaddrout,
 				sim : document.getElementById("sim").value,
 				routercode : document.getElementById("routercode").value,
 				routerserialnumber : document.getElementById("routerserialnumber").value,
 				imsinumber : document.getElementById("imsinumber").value,
 				masterantenna : document.getElementById("masterantenna").value,
 				slaveantenna : document.getElementById("slaveantenna").value,
 				notes : document.getElementById("notes").value
  			},
  			beforeSend: function( xhr ) {
  				
  				alertify.set({delay:1000});
  				alertify.log('Registering Device, please wait for confirmation');
  				
  			},
  			success : function(responseText) {
  				alertify.alert("<b>New Router Added</b>");
  			}
  		});	
	
}


document.getElementById("routertype").innerHTML = "<%=routertype%>";

function pings() {

if (suffix == "o"){
	
	if (document.getElementById("ipaddressroutero").value == ""){
		alertify.log("Ip Address Router Required");
		document.getElementById("ipaddressroutero").focus();
		return;
	}
	
	ip = document.getElementById("ipaddressroutero").value;
}else if (suffix == "w"){
	
	if (document.getElementById("ipaddressrouterw").value == ""){
		alertify.log("Ip Address Router Required");
		document.getElementById("ipaddressrouterw").focus();
		return;
	}
	
	ip = document.getElementById("ipaddressrouterw").value;
}else{
	
	if (document.getElementById("ipaddressroutere").value == ""){
		alertify.log("Ip Address Router Required");
		document.getElementById("ipaddressroutere").focus();
		return;
	}
	
	ip = document.getElementById("ipaddressroutere").value;
}
	
	
$.ajax({
	url : "../GetPing",
	data : {
		command : "" + ip
	},
	beforeSend: function( xhr ) {
		alertify.set({delay:1000});
		alertify.log('Running test, please wait for confirmation');
	},
	success : function(responseText) {
		//hideLoader();
		if (responseText == 0){
			alertify.alert("<b>Device Found</span></b>");
		}else{
			alertify.alert("<b><span style='color:#f00'>Device Not Found</span></b>");
		}
	}
});
	
	
}

function TelNets() {

	if (suffix == "o"){
		
		if (document.getElementById("ipaddressroutero").value == ""){
			alertify.log("Ip Address Router Required");
			document.getElementById("ipaddressroutero").focus();
			return;
		}
		
		ip = document.getElementById("ipaddressroutero").value;
	}else if (suffix == "w"){
		
		if (document.getElementById("ipaddressrouterw").value == ""){
			alertify.log("Ip Address Router Required");
			document.getElementById("ipaddressrouterw").focus();
			return;
		}
		
		ip = document.getElementById("ipaddressrouterw").value;
	}else{
		
		if (document.getElementById("ipaddressroutere").value == ""){
			alertify.log("Ip Address Router Required");
			document.getElementById("ipaddressroutere").focus();
			return;
		}
		
		ip = document.getElementById("ipaddressroutere").value;
	}
		
		
	$.ajax({
		url : "../GetTelnet",
		data : {
			command : ip
		},
		async: false,
		beforeSend: function( xhr ) {
			alertify.set({delay:1000});
			alertify.log('Running test, please wait for confirmation');
		},
		success : function(responseText) {
			if (responseText == 0){
				alertify.alert("<b>Device Found</span></b>");
			}else{
				alertify.alert("<b><span style='color:#f00'>Device Not Found</b></span>");
			}
		}
	});
		
		
	}
  </script>
</body>
</html>
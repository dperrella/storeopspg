<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
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
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
  <style>
     #loading-image {
		background-color: #333;
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 1;
	}	
  </style>
</head>
<div id="loading-image">
		<img src="../../images/loading.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 208px;
				   height: 13px;
				   margin-top: -8px;
				   margin-left: -104px;" />
  </div>
  <section class="vbox">
    <header class="bg-white-only header header-md navbar navbar-fixed-top-xs">
      
      <%@ include file="../../inc2/top.jsp" %>
      
      <div class="navbar-right ">
        <ul class="nav navbar-nav m-n hidden-xs nav-user user">
          
          <%@ include file="../../inc2/topMenu.jsp" %>
          
          <%@ include file="../../inc2/top2.jsp" %>
        </ul>
      </div>      
    </header>
    <section>
    
      <section class="hbox stretch">
        <!-- .aside -->
        <aside class="bg-black dk nav-xs aside hidden-print" id="nav">           
          <section class="vbox">
            <section class="w-f-md scrollable">
              <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">

                <!-- nav -->                 
                
                <%@ include file="../../inc/menu-left.jsp" %>
                
                <!-- / nav -->
                
                
              </div>
            </section>
            
			<%@ include file="../../inc2/leftFooter.jsp" %>
            
          </section>
        </aside>
        <!-- /.aside -->
        
        
        <section id="content">
          <section class="vbox">
          
            <section id="bjax-target">
              <section class="hbox stretch">
            	
            	<!-- menu app -->
              
            	<%@ include file="../../inc2/menuApp3.jsp" %>
            
            	
                <aside class="col-sm-9 padder " >
	              <section class="vbox">
	                <section class="scrollable hover">
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("rot_dett") %></h2>
		                
		                 
		                <form id="form1" data-validate="parsley" method="post" action="router-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		<input type="hidden" id="devtypeName" name="devtypeName">
  						<input type="hidden" id="Closed" name="Closed">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("rot_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="router-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("rou_id") %></label>
						          <input type="text" id="id" name="id" class="form-control" value="${Id}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_type") %></label>
						        <select class="form-control" name="devtype" id="devtype" ${btnActive} onclick="SelRouter(this)">    
										${Devtype}
						        </select>                         
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>						    
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_cus_id") %></label>
						        <input type="text" id="customer" name="customer" class="form-control" value="${Customer}" data-minlength="3" data-maxlength="250" ${btnActive}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_desc_cust") %></label>
								<input type="text" id="customer_description" name="customer_description" class="form-control" value="${Customer_description}" data-minlength="3" data-maxlength="250" ${btnActive}>               
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <!-- 
						    <div class="form-group">
						        <label><%=propLang.get("rou_acc_id") %></label>
								<input type="text" id="account_id" name="account_id" class="form-control" value="${Account_id}" data-minlength="3" data-maxlength="250" ${btnActive}>                  
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    -->
						    <input type="hidden" id="account_id" name="account_id" class="form-control" value="${Account_id}" data-minlength="3" data-maxlength="250" ${btnActive}>                  
						    
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_store_id") %></label>
								<input type="text" id="store_id" name="store_id" class="form-control" value="${Store_id}" data-minlength="3" data-maxlength="250" ${btnActive}>               
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_desc_store") %></label>
								<input type="text" id="store_description" name="store_description" class="form-control" value="${Store_description}" data-minlength="3" data-maxlength="250" ${btnActive}>               
						    </div>						    
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_brepbins") %></label>
								<input type="text" id="brepbins" name="brepbins" class="form-control" value="${Brepbip}" data-minlength="3" data-maxlength="250" ${btnActive}>                  
						    </div>						    
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_sap_code") %></label>
								<input type="text" id="store_sap_code" name="store_sap_code" class="form-control" value="${Store_sap_code}" data-minlength="3" data-maxlength="250" ${btnActive}>               
						    </div>							    
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
<!--  -->
				  <div id="OpenVPN">
					  <div class="form-group">
					   <label><%=propLang.get("rou_client") %></label>
			            <input type="text" id="clientopenvpn" name="clientopenvpn" class="form-control" value="${Client_open_vpn}"  data-minlength="3" data-maxlength="250" ${btnActive}>
			          </div>
			          <div class="line line-dashed b-b line-lg pull-in"></div>
			          <div class="form-group">
			          <label><%=propLang.get("ip_address_router") %></label>
			             <input type="text" id="ipaddressroutero" name="ipaddressroutero" class="form-control" data-minlength="3" data-maxlength="250" ${btnActive}>
			          </div>
			          <div class="line line-dashed b-b line-lg pull-in"></div>
			          <div class="form-group">
			          <label><%=propLang.get("rou_sim") %></label>
			             <input type="text" id="sim" name="sim" class="form-control" value="${Sim}" data-minlength="3" data-maxlength="250" ${btnActive}>
			          </div>	          
		          </div>
				  <div id="WIND" style="display: none;">
					  <div class="form-group">
					  <label><%=propLang.get("rou_code") %></label>
			            <input type="text" id="routercode" name="routercode" class="form-control" value="${Routercode}" data-minlength="3" data-maxlength="250" ${btnActive}>
			          </div>
			          <div class="line line-dashed b-b line-lg pull-in"></div>
			          <div class="form-group">
			          <label><%=propLang.get("ip_address_router") %></label>
			             <input type="text" id="ipaddressrouterw" name="ipaddressrouterw" class="form-control"  data-minlength="3" data-maxlength="250" ${btnActive}>
			          </div>        
		          </div>
				  <div id="EvolveNet Wireless Router" style="display: none;">
					  <div class="form-group">
					  <label><%=propLang.get("ip_address_router") %></label>
			            <input type="text" id="ipaddressroutere" name="ipaddressroutere" class="form-control" data-minlength="3" data-maxlength="250" ${btnActive}>
			          </div>
			          <div class="line line-dashed b-b line-lg pull-in"></div>
			          <div class="form-group">
			          <label><%=propLang.get("rou_sn") %></label>
			             <input type="text" id="routerserialnumber" name="routerserialnumber" value="${Routerserialnumber}" class="form-control"  data-minlength="3" data-maxlength="250" ${btnActive}>
			          </div>
			          <div class="line line-dashed b-b line-lg pull-in"></div>
			          <div class="form-group">
			          <label><%=propLang.get("rou_imsi") %></label>
			             <input type="text" id="imsinumber" name="imsinumber"  class="form-control" value="${Imsinumber}" data-minlength="3" data-maxlength="250"${btnActive}>
			          </div>	          
		          </div>					    							    						    				    
<!--  -->

						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_master") %></label>
								<input type="text" id="master_antenna" name="master_antenna" class="form-control" value="${Master_antenna}" data-minlength="1" data-maxlength="250" ${btnActive}>
						    </div>						    							    						    				    
					
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_slave") %></label>
								<input type="text" id="slave_antenna" name="slave_antenna" class="form-control" value="${Slave_antenna}" data-minlength="1" data-maxlength="250" ${btnActive}>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_note") %></label>
								<input type="text" id="note" name="note" class="form-control" value="${Note}" data-minlength="1" data-maxlength="250" ${btnActive}>
						    </div>							    						    						    				    

						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("rou_close") %></label>
						        <div id="group1">
									<input type="radio" id="closeY" name="close" value="Y" ${btnActive}>YES
									&nbsp;&nbsp;&nbsp;<input type="radio" id="closeN" name="close" value="N" checked ${btnActive}>NO
								</div>
						    </div>											    						    							    						    						    				    
						    
						    
			<!--  -->
				          <div class="form-group">
				             <button id="btnPing" type="button" class="btn btn-primary btn-lg" style="width: 30%;" onclick="return pings()">&nbsp;&nbsp;Ping&nbsp; </button>&nbsp;&nbsp;&nbsp;<button  type="button" id="btnTel" class="btn btn-primary btn-lg"  style="width: 30%;">TelNet </button>
				          </div> 
			<!--  -->			
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive} onclick="return ChangeRadio();"><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            
			            
						 
						 
						</form>
			            
			            
	                
	                </section>
	              </section>
	            </aside>
<!--	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%//=propLang.get("dev_doc") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${doc}" var="doc" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="clear">
			                    	<div>${doc.id}</div> 
		                        </div> 
		                      </li>
	                        </c:forEach>
	                          
	                        </ul>
                        </div>
                    </section>
                  </section>
                </section>
 -->               
                 <!-- side content -->
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="router-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-building-o"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("rou_rde") %></span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="router-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("dev_cerca") %>">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            <!-- / side content -->
        
                 
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
    <script src="../../js/alertify.min.js"></script>
    <link href="../../css/alertify.core.css" rel="stylesheet">
	<link href="../../css/alertify.default.css" rel="stylesheet">
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


var name = "";
var suffix = "o";
function SelRouter(str){
	
	name = $("#devtype option[value='"+str.value+"']").text();
	
	document.getElementById("devtypeName").value = name;
	
	switch(name) {
    case "OpenVPN":
    	document.getElementById(name).style.display = "inline";
    	document.getElementById("WIND").style.display = "none";
    	document.getElementById("EvolveNet Wireless Router").style.display = "none";
    	document.getElementById("routercode").value = "";
    	document.getElementById("ipaddressrouterw").value = "";
    	document.getElementById("ipaddressroutere").value = "";
    	document.getElementById("routerserialnumber").value = "";
    	document.getElementById("imsinumber").value = "";
    	suffix = "o";
        break;
    case "EvolveNet Wireless Router":
    	document.getElementById(name).style.display = "inline";
    	document.getElementById("WIND").style.display = "none";
    	document.getElementById("OpenVPN").style.display = "none";
    	document.getElementById("routercode").value = "";
    	document.getElementById("ipaddressrouterw").value = "";
    	document.getElementById("clientopenvpn").value = "";
    	document.getElementById("ipaddressroutero").value = "";
    	document.getElementById("sim").value = "";
    	suffix = "e";
        break;
    case "WIND":
    	document.getElementById(name).style.display = "inline";
    	document.getElementById("OpenVPN").style.display = "none";
    	document.getElementById("EvolveNet Wireless Router").style.display = "none";
    	document.getElementById("ipaddressroutere").value = "";
    	document.getElementById("routerserialnumber").value = "";
    	document.getElementById("imsinumber").value = "";
    	document.getElementById("clientopenvpn").value = "";
    	document.getElementById("ipaddressroutero").value = "";
    	document.getElementById("sim").value = "";    	
    	suffix = "w";
        break;        
	}
	
	
}

function ChangeRadio(){
	
	
	if (document.getElementById("tipoForm").value != "E"){
	
	document.getElementById("Closed").value = $('input[name=close]:checked', '#form1').val();
	
	if (document.getElementById("devtypeName").value == ""){
		document.getElementById("devtypeName").value = "OpenVPN";
	}
	
	/**/
	if (document.getElementById("customer").value == ""){
		alertify.log("Customer Required");
		document.getElementById("customer").focus();
		return false;
	}
	
	if (document.getElementById("customer_description").value == ""){
		alertify.log("Customer Description Required");
		document.getElementById("customer_description").focus();
		return false;
	}	

	if (document.getElementById("store_description").value == ""){
		alertify.log("Store Description Required");
		document.getElementById("store_description").focus();
		return false;
	}	
	
	if (document.getElementById("brepbins").value == ""){
		alertify.log("BrepBins Required");
		document.getElementById("brepbins").focus();
		return false;
	}		


/**/
 
if (suffix == "e"){
		ipaddrout = $("#ipaddressroutere").val();
		
		if	(document.getElementById("ipaddressroutere").value == ""){
  			alertify.log("IP Address Router Required");
  			document.getElementById("ipaddressroutere").focus();
  			return false;	
  		}

	}else if(suffix == "o"){

		ipaddrout = $("#ipaddressroutero").val();
		
		if	(document.getElementById("clientopenvpn").value == ""){
  			alertify.log("Client OpenVpn Required");
  			document.getElementById("clientopenvpn").focus();
  			return false;
  		}

		if	(document.getElementById("ipaddressroutero").value == ""){
  			alertify.log("IP Address Router Required");
  			document.getElementById("ipaddressroutero").focus();
  			return false;
  		}
		
		if	(document.getElementById("sim").value == ""){
  			alertify.log("SIM Required");
  			document.getElementById("sim").focus();
  			return false;
  		}
		
	}else{
		
		ipaddrout = $("#ipaddressrouterw").val();
		
		if	(document.getElementById("routercode").value == ""){
  			alertify.log("Router Code Required");
  			document.getElementById("routercode").focus();
  			return false;
  		}

		if	(document.getElementById("ipaddressrouterw").value == ""){
  			alertify.log("IP Address Router Required");
  			document.getElementById("ipaddressrouterw").focus();
  			return false;
  		}

	}
/**/
	
	/**/
	}
}


$('#devtype').val('${Routertype}');

var clo = '${Close}';

if (clo == "" || clo == "f" || clo == false){
	document.getElementById("closeN").checked = true;
	document.getElementById("closeY").checked = false;
}else{
	document.getElementById("closeN").checked = false;
	document.getElementById("closeY").checked = true;	
}

var devname = $("#devtype option[value='"+document.getElementById("devtype").value+"']").text();

if (devname == "OpenVPN"){
	document.getElementById("ipaddressroutero").value = "${Ip_address_router}";
	document.getElementById(devname).style.display = "inline";
	document.getElementById("WIND").style.display = "none";
	document.getElementById("EvolveNet Wireless Router").style.display = "none";
	document.getElementById("devtypeName").value = devname;
	suffix = "o";
}else if(devname == "WIND"){
	document.getElementById("ipaddressrouterw").value = "${Ip_address_router}";
	document.getElementById(devname).style.display = "inline";
	document.getElementById("OpenVPN").style.display = "none";
	document.getElementById("EvolveNet Wireless Router").style.display = "none";
	document.getElementById("devtypeName").value = devname;
	suffix = "w";
}else{
	document.getElementById("ipaddressroutere").value = "${Ip_address_router}";
	document.getElementById(devname).style.display = "inline";
	document.getElementById("WIND").style.display = "none";
	document.getElementById("OpenVPN").style.display = "none";
	document.getElementById("devtypeName").value = devname;
	suffix = "e";
}
/**/
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
	url : "../../GetPing",
	data : {
		command : "ping " + ip
	},
	success : function(responseText) {
		if (responseText == 0){
			alertify.success("Device Found");
		}else{
			alertify.error("Device Not Found");
		}
	}
});
	
	
}
</script>
  <%//@ include file="../../inc/autorized.jsp" %>
</body>
</html>
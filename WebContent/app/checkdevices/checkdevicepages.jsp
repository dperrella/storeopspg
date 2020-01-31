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

//routertype = "<option value=''> -- Choise -- </option>";
rs = st.executeQuery(sql);
while(rs.next()) {
	routertype += "<option value='"+rs.getString("idrouter")+"'>"+rs.getString("description")+"</option>";
}
rs.close();
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang2 = new ReadProperties("connection.properties");

%>
       
<head>  
  <meta charset="utf-8" />
  <title>Smart Suite | Check Devices</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../js/jPlayer/jplayer.flat.css" type="text/css" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
    
  <!--[if lt IE 9]>
    <script src="js/ie/html5shiv.js"></script>
    <script src="js/ie/respond.min.js"></script>
    <script src="js/ie/excanvas.js"></script>
  <![endif]-->
  


<style>
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
<body id="bg">

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
          <!--  -->
          <%//@ include file="../../inc2/topMenu.jsp" %>
          
          <!--  -->
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
          <section class="hbox stretch">          
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                      
		                <h2 class="font-thin m-b"><%="CheckDevice Page" %></h2>

			                      	<section class="panel panel-default">
					                    <header class="panel-heading bg-light">
					                      <ul class="nav nav-tabs nav-justified">
			                  				<li class="active">
			                  				<a href="#FTP" data-toggle="tab" class="text-ellipsis" onclick="Msg('F')">Test Ftp Data</a>
					                        </li>
			                  				<li class="">
			                  				<a href="#WEB" data-toggle="tab" class="text-ellipsis" onclick="Msg('W')">Test Webservice</a>
					                        </li>
			                  				<li class="">
			                  				<a href="#REM" data-toggle="tab" class="text-ellipsis" onclick="Msg('C')">Test Remote Control</a>
					                        </li>
			                  				<li class="">
			                  				<a href="#ROU" data-toggle="tab" class="text-ellipsis" onclick="Msg('R')">New Router</a>
					                        </li>
					                      </ul>
					                    </header>
					                    <div class="tab-content">
					                      
					                      	
					                      	<div class="tab-pane active" id="FTP" style="margin-left: 15px;">
					                        	
					                        	<header class="panel-heading bg-light" style="background-color:#fff">
						                          <div class="clearfix">
						                            <div class="clear">
						                              <div class="h3 m-t-xs m-b-xs">
						                                Test Ftp Data 
						                              </div>
						                            </div>
						                          </div>
						                        </header>
					                        	
					                        	<div class="table-responsive">
												          <form method="post" action="login-esegui.htm">
												          <div>    
													          <div class="form-group pull-in clearfix">
													          	<div class="col-sm-6">
						          								<label>Account Id</label>
													            <input type="text" id="accountid" name="accountid" placeholder="Account Id" class="form-control">
													            </div>
													          </div>
													          
													          <div class="line line-dashed b-b line-lg pull-in"></div>
													          
													          <div class="form-group pull-in clearfix">
													          	<div class="col-sm-6">
						          								<label>Store Id</label>
													             <input type="text" id="storeid" name="storeid"placeholder="Store Id" class="form-control">
													             </div>
													          </div>
													          
													          <div class="line line-dashed b-b line-lg pull-in"></div>
													          
													          <div class="form-group pull-in clearfix">
													          	<div class="col-sm-6">
						          								<label>Door Id</label>
													             <input type="text" id="doorid" name="doorid" placeholder="Door Id" class="form-control">
													             </div>
													          </div>
												          </div>
												          <div style="margin-top: 30px; margin-bottom: 30px;">
													          <!-- <a href="../../signin.jsp" class="btn btn-default">BACK TO LOGIN</a>-->
													          <button type="button" class="btn btn-default btn-danger" onclick="return control(this)">CHECK</button>
												          </div>
												        </form>
								  				</div>
					                        </div>
					                      	
					                      
					                      	
					                      	<div class="tab-pane " id="WEB" style="margin-left: 15px;">
					                        	
					                        	<header class="panel-heading bg-light" style="background-color:#fff">
						                          <div class="clearfix">
						                            <div class="clear">
						                              <div class="h3 m-t-xs m-b-xs">
						                                Test Webservice 
						                              </div>
						                            </div>
						                          </div>
						                        </header>
					                        	
					                        	<div class="table-responsive">
										          <form method="post" action="login-esegui.htm">
										          <div>
											          <div class="form-group pull-in clearfix">
											          	<div class="col-sm-6">
				          								<label>Account Id</label>
											            <input type="text" id="accountidw" name="accountidw" placeholder="Account Id" class="form-control">
											            </div>
											          </div>
											          
											          <div class="line line-dashed b-b line-lg pull-in"></div>
											          
											          <div class="form-group pull-in clearfix">
											          	<div class="col-sm-6">
				          								 <label>Store Id</label>
											             <input type="text" id="storeidw" name="storeidw" placeholder="Store Id" class="form-control">
											            </div>
											          </div>
											          
											          <div class="line line-dashed b-b line-lg pull-in"></div>
											          
											          <div class="form-group pull-in clearfix">
											          	<div class="col-sm-6">
				          								 <label>Door Id</label>
											             <input type="text" id="dooridw" name="dooridw" placeholder="Door Id" class="form-control">
											             </div>
											          </div>
										          </div>
										          <div style="margin-top: 30px; margin-bottom: 30px;">
											          <button type="button" class="btn btn-default btn-danger" onclick="return controlweb(this)">CHECK</button>
											          <!-- <a href="../../signin.jsp" class="btn btn-default btn-danger">BACK TO LOGIN</a> -->
										          </div>
										        </form>
								  				</div>
					                        </div>
					                      	
					                      
					                      	
					                      	<div class="tab-pane " id="REM" style="margin-left: 15px;">
					                        	
					                        	<header class="panel-heading bg-light" style="background-color:#fff">
						                          <div class="clearfix">
						                            <div class="clear">
						                              <div class="h3 m-t-xs m-b-xs">
						                                Test Remote Control 
						                              </div>
						                            </div>
						                          </div>
						                        </header>
					                        	
					                        	<div class="table-responsive">
									              <form method="post" action="login-esegui.htm">
													<div>              
											          <div class="form-group pull-in clearfix">
											          	<div class="col-sm-6">
				          								<label>IP</label>
											            <input type="text" id="ip" name="ip" placeholder="IP" class="form-control">
											            </div>
											          </div>
											          
											          <div class="line line-dashed b-b line-lg pull-in"></div>
											          
											          <div class="form-group pull-in clearfix">
											          	<div class="col-sm-6">
				          								<label>Port</label>
											             <input type="text" id="port" name="port" placeholder="Port" class="form-control">
											             </div>
											          </div>
											          
											          <div class="line line-dashed b-b line-lg pull-in"></div>
											          
												          <div class="form-group">
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
										          <div style="margin-top: 30px; margin-bottom: 30px;">
											          <button type="button" class="btn btn-default btn-danger" onclick="return controlEV(this)">CHECK</button>
													  <!-- <a href="../../signin.jsp" class="btn btn-default btn-danger">BACK TO LOGIN</a> -->
												  </div>          
										        </form>
								  				</div>
					                        </div>
					                      	
					                      	<div class="tab-pane " id="ROU" style="margin-left: 15px;">	
					                        	<header class="panel-heading bg-light" style="background-color:#fff">
						                          <div class="clearfix">
						                            <div class="clear">
						                              <div class="h3 m-t-xs m-b-xs">
						                                New Routers 
						                              </div>
						                            </div>
						                          </div>
						                        </header>
					                        	
					                        	<div class="table-responsive">
									              <form method="post" action="login-esegui.htm">
									              <div>
										            <div class="form-group pull-in clearfix">
											          	<div class="col-sm-6">
				          								<label>Router Type</label>
											            <select id="routertype" name="routertype" class="form-control" onchange="SelRouter(this)">
											            	
											            </select>
											            </div>
											         </div>    
									              </div>
									              
												
											  	<div class="panel-footer bg-light">
							                      <div class="padder">
						                            <span class="h4 block">SECTION CUSTOMER INFO</span>
						                          </div>
							                    </div>
										        <br/>
												
 									              <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Customer Id</label>
										            <input type="text" id="customerid" name="customerid" placeholder="Customer Id" class="form-control" required>
										            </div>
										          </div>
										          
										          <div class="line line-dashed b-b line-lg pull-in"></div>
										          
   									              <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Customer Description</label>
										             <input type="text" id="customerdescription" name="customerdescription" placeholder="Customer Description" class="form-control" required>
										             </div>
										          </div>
										          <input type="hidden" id="accountidr" name="accountidr" placeholder="Account Id" class="form-control">
										                    
										          <div class="line line-dashed b-b line-lg pull-in"></div>          
										                    
									              <div class="form-group pull-in clearfix">
										          	 <div class="col-sm-6">
			          								 <label>Store Id</label>
										             <input type="text" id="storeidr" name="storeidr" placeholder="Store Id" class="form-control">
										             </div>
										          </div>	         
										          
										          <div class="line line-dashed b-b line-lg pull-in"></div> 
										          
										          <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Store Description</label>
										             <input type="text" id="storedescription" name="storedescription" placeholder="Store Description" class="form-control" required>
										             </div>
										          </div>
										          
										          <div class="line line-dashed b-b line-lg pull-in"></div>
										          
									              <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Brep/Bins</label>
										             <input type="text" id="brepbins" name="brepbins" placeholder="Brep/Bins" class="form-control" required>
										             </div>
										          </div>	   
										          
										          <div class="line line-dashed b-b line-lg pull-in"></div>    
										             
									              <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Store Sap Code</label>
										             <input type="text" id="storesapcode" name="storesapcode" placeholder="Store Sap Code" class="form-control">
										             </div>
										          </div>          
												
												
											  	<div class="panel-footer bg-light">
							                      <div class="padder">
						                            <span class="h4 block">SECTION CONNECTION</span>
						                          </div>
							                    </div>
										        <br/>
													  <div id="OpenVPN">
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>Client Open VPN</label>
												            <input type="text" id="clientopenvpn" name="clientopenvpn" placeholder="Client Open VPN" class="form-control" required>
												            </div>
												          </div>
												          
												          <div class="line line-dashed b-b line-lg pull-in"></div>
												          
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>Ip Address Router</label>
												             <input type="text" id="ipaddressroutero" name="ipaddressroutero" placeholder="Ip Address Router" class="form-control" required>
												             </div>
												          </div>
												          
												          <div class="line line-dashed b-b line-lg pull-in"></div>
												          
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>Sim YES/NO</label>
												             <input type="text" id="sim" name="sim" placeholder="Sim YES/NO" class="form-control" required>
												             </div>
												          </div>	          
											          </div>
											          
													  <div id="WIND" style="display: none;">
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>Router Code</label>
												            <input type="text" id="routercode" name="routercode" placeholder="Router Code" class="form-control" required>
												            </div>
												          </div>
												          
												          <div class="line line-dashed b-b line-lg pull-in"></div>
												          
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>Ip Address Router</label>
												             <input type="text" id="ipaddressrouterw" name="ipaddressrouterw" placeholder="Ip Address Router" class="form-control" required>
												             </div>
												          </div>        
											          </div>
											          
													  <div id="EvolveNet Wireless Router" style="display: none;">
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>Ip Address Router</label>
												            <input type="text" id="ipaddressroutere" name="ipaddressroutere" placeholder="Ip Address Router" class="form-control" required>
												            </div>
												          </div>
												          
												          <div class="line line-dashed b-b line-lg pull-in"></div>
												          
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>Router Serial Number</label>
												             <input type="text" id="routerserialnumber" name="routerserialnumber" placeholder="Router Serial Number" class="form-control">
												             </div>
												          </div>
												          
												          <div class="line line-dashed b-b line-lg pull-in"></div>
												          
											              <div class="form-group pull-in clearfix">
												          	<div class="col-sm-6">
					          								<label>IMSI Number</label>
												             <input type="text" id="imsinumber" name="imsinumber" placeholder="IMSI Number" class="form-control">
												             </div>
												          </div>	          
											          </div>		          
												
											  	<div class="panel-footer bg-light">
							                      <div class="padder">
						                            <span class="h4 block">SECTION HW INSTALLED</span>
						                          </div>
							                    </div>
										        <br/>
									              <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Master Antenna</label>
										             <input type="text" id="masterantenna" name="masterantenna" placeholder="Master Antenna" class="form-control">
										             </div>
										          </div>	          
										          
										          <div class="line line-dashed b-b line-lg pull-in"></div>
										          
									              <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Slave Antenna</label>
										             <input type="text" id="slaveantenna" name="slaveantenna" placeholder="Slave Antenna" class="form-control">
										             </div>
										          </div>	          
										          
										          <div class="line line-dashed b-b line-lg pull-in"></div>
										          
									              <div class="form-group pull-in clearfix">
										          	<div class="col-sm-6">
			          								<label>Notes</label>
										             <input type="text" id="notes" name="notes" placeholder="Notes" class="form-control">
										             </div>
										          </div>        
										          
										          <div class="form-group" style="margin-top: 30px;">
										             <button id="btnPing" type="button" class="btn btn-default btn-danger" onclick="return pings()">&nbsp;&nbsp;Ping&nbsp; </button>
										             <button  type="button" id="btnTel" class="btn btn-default btn-danger" onclick="return TelNets();">TelNet </button>
										             <button type="button" class="btn btn-default btn-danger" onclick="return Registration(this)">REGISTRATION</button>
										          </div> 
	
									          <div style="margin-top: 30px; margin-bottom: 30px; display: none;">
										          <button type="button" class="btn btn-default btn-danger" onclick="return Registration(this)">REGISTRATION</button>
												  <a href="../../signin.jsp" class="btn btn-default btn-danger" >BACK TO LOGIN</a>
											  </div>          
									        </form>
								  				</div>
					                        </div>
					                      	
					                      
					                    </div>
					                    
					              </section>

		  				
			        </section> 
	              </section>
	            </section>
                
                <!-- side content 
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="customers-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-user icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang2.get("cus_cus") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="customers-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang2.get("cus_cerca") %>">
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
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
    <script src="../../js/alertify.min.js"></script>
    <link href="../../css/alertify.core.css" rel="stylesheet">
	<link href="../../css/alertify.default.css" rel="stylesheet">
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Test Devices</h5>
        <!-- 
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        -->
      </div>
      <div class="modal-body" id="divTab" style="overflow: auto; max-height: 500px;">
        <table width="100%" id="contegg" class="table-striped table-bordered">
        	<tr>
        		<td width="100%" align="center"><image src="../../images/loading.gif"></td>
        	</tr>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
  		
  		
  		$('#contegg').html("<tr><td width='100%' align='center'><image src='../../images/loading.gif'></td></tr>");
  		

  		 		
  		$.ajax({
  			url : "../../smcheckdevices",
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
		
		$('#contegg').html("<tr><td width='100%' align='center'><image src='../../images/loading.gif'></td></tr>");
		
		$('#BodyPage').html('<i class="fa fa-spinner fa-pulse fa-fw"></i>');
		 		
		$.ajax({
			url : "../../smcheckdevices",
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
  			url : "../../smcheckdevicespreregistration",
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
		
  		$('#BodyPage').html('<i class="fa fa-spinner fa-pulse fa-fw"></i>');
  		
  		$.ajax({
  			url : "../../smcheckdevicesev",
  			data : {
  				ip : document.getElementById("ip").value,
  				port : document.getElementById("port").value,
  				chk : $('input[name=chkradio]:checked').val()
  			},
  			success : function(responseText) {

  				ret = responseText.split("$");
  				/*
  				alert("flag="+ret[0])
  				alert("exit-value="+ret[1])
  				alert("string="+ret[2])
  				*/
  				/*
  				var msg = ret[0];
  				var nam = ret[1];
  				var tab = ret[2];
  				var tabint = ret[3];
				  		  		
  		  		$('#msg').html('<div class="alert alert-info"><button type="button" class="close" data-dismiss="alert">&times;</button>'+
  		  						   '<i class="fa fa-ban-circle"></i><strong>Configured on the server</strong> '+msg+tabs+'</div>');
  				
  		  	    $('#divTab').html(tab);
  		  		$('#dinInt').html(tabint);
  		  		document.getElementById("msg").style.display = "inline";
  		  		$('#BodyPage').html('<i class="fa fa-desktop"></i>');
  		  		*/
  		  		
  		  		if (ret[0] == "KO"){
  		  			alertify.alert("<b>Device Not found or DeviceType is Incorrect</b>")
  		  			return;
  		  		}
  		  		
  		  		alert(ret[1]);
  		  		
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
  			url : "../../SetRouters",
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
	url : "../../GetPing",
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
		url : "../../GetTelnet",
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
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
  <title>Customer Card Data Dett | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
  
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />

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
<body>
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
            <section class="" id="bjax-target">
              <section class="hbox stretch">

              <!-- menu app (in questo caso non hanno menu app)-->
              
              <%//@ include file="../../inc2/menuApp5.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
	                
	                	<h2 class="font-thin m-b"><%="Customer Card Data Detail" %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="customer_card_data-dett.htm" onsubmit="return contrName();">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%="Customer Card Data Detail" %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="customer_card_data.htm" class="btn btn-default"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%="Payment Code" %></label>
						          	<select class="form-control" name="payment_code" id="payment_code">
										${Paymenttype}
							        </select>
						        </div>
						        <div class="col-sm-4">
							        <label><%="Customers" %></label>
							        <select class="form-control" name="idcustomer" id="idcustomer">
										${Customers}
							        </select>  
							    </div>
							    
							    <div class="col-sm-4">
							        <label><%="Country" %></label>
							        <select class="form-control" name="country" id="country">
										${Countrys}
							        </select>                     
							    </div>	
						    </div> 
						    
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>

						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%="Card Code" %></label>
						          	<select class="form-control" name="card_code" id="card_code">
										${Cardcode}
							        </select>
						        </div>
						        <div class="col-sm-4">
							        <label><%="Card Number" %></label>
									<input type="text" id="card_number" name="card_number" class="form-control" value="${Cardn}" />
							    </div>							    
							    <div class="col-sm-2">
							        <label><%="Month Deadline" %></label>
							        <select class="form-control" name="month_deadline" id="month_deadline">
								        <option value="01">January</option>
								        <option value="02">February</option>
								        <option value="03">March</option>
										<option value="04">April</option>
										<option value="05">May</option>
										<option value="06">June</option>	
								        <option value="07">July</option>
								        <option value="08">August</option>
								        <option value="09">September</option>
										<option value="10">October</option>
										<option value="11">November</option>
										<option value="12">December</option>																														
							        </select>                     
							    </div>
							    
							    <div class="col-sm-2">
							        <label><%="Year Deadline" %></label>
							        <select class="form-control" name="year_deadline" id="year_deadline">
										${Years_}																														
							        </select>                     
							    </div>	
							    	
						    </div> 
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>

						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-2">
						          <label><%="CVV" %></label>
								  <input type="text" id="cvv" name="cvv" class="form-control" maxlength="3" value="${Cvv}" />
						        </div>
						        <div class="col-sm-5">
							        <label><%="Name" %></label>
									<input type="text" id="name" name="name" class="form-control" value="${Name}" />
							    </div>							    
							    <div class="col-sm-5">
							        <label><%="Surname" %></label>
									<input type="text" id="surname" name="surname" class="form-control" value="${Surname}" />                   
							    </div>	
						    </div> 						    
						    
						  </div>
						  

						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            

			            
			              <input type="hidden" id="idcard" name="idcard" value="<%=request.getParameter("idcard") %>">
			               
						</form>
			              
			        </section> 
	              </section>
	            </section>
                
                <!-- side content 
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="import-file-type.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-download"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%="Import File-Type" %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="import-file-type.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("rol_cerca") %>">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            <!-- / side content -->
                
               </section>
                
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  
  
  <script src="../../js/color/jscolor.js"></script>
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/alertify.min.js"></script>
  <link href="../../css/alertify.core.css" rel="stylesheet">
  <link href="../../css/alertify.default.css" rel="stylesheet">   

  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
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
	
	function contrName(){

		if ($("#payment_code").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Payment Code Required</div>");
			$("#payment_code").focus();
			return false;
		}
  		
  		if ($("#card_code").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Card Code Required</div>");
			$("#card_code").focus();
			return false;
		}
  		
  		if ($("#card_number").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Card Number Required</div>");
			$("#card_number").focus();
			return false;
		}
  		
		if ($("#cvv").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">CVV Required</div>");
			$("#cvv").focus();
			return false;
		}
  		
  		if ($("#name").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Name Required</div>");
			$("#name").focus();
			return false;
		}
  		
  		if ($("#surname").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Surname Required</div>");
			$("#surname").focus();
			return false;
		}
  		
		
	}

	
	var act = "${Paymentcode}";
	if (act != ""){
		$("#payment_code").val(act);
	}
	
	var pur = "${Idcustomer}";
	if (pur != ""){
		$("#idcustomer").val(pur);
	}
	
	var sel = "${Country}";
	if (sel != ""){
		$("#country").val(sel);
	}	
	
	var bil = "${Cardc}";
	if (bil != ""){
		$("#card_code").val(bil);
	}
	
	var mont = "${Months}";
	if (mont != ""){
		$("#month_deadline").val(mont);
	}
	
	var yea = "${Year}";
	if (yea != ""){
		$("#year_deadline").val(yea);
	}
	
	if ($("#tipoForm").val() == "M"){
		$('#code').prop('readonly', true);
	}	

  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
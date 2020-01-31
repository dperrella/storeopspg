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
  <title>Import File-Type Dett | Smart Suite</title>
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

              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp5.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
	                
	                	<h2 class="font-thin m-b"><%="Import File-Type Detail" %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="import-file-type-dett.htm" onsubmit="return contrName();">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%="Import File-Type Detail" %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="import-file-type.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%="ID" %></label>
						          <select id="driver" name="driver" class="form-control">
						          	<option value=""> -- Seleziona -- </option>
						          	${OptD}
						          </select>   
						        </div>
						        <div class="col-sm-8">
							        <label><%="Description" %></label>
							        <input type="text" class="form-control" name="description" id="description" />
							    </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label><%="Separator" %></label>
										<input type="text" class="form-control" name="separator" id="separator" />
							    </div>
							    <div class="col-sm-4">
							        <label><%="Account Position" %></label>
							        <select class="form-control" name="accountposition" id="accountposition">
							        <option value=""> -- Seleziona -- </option>
							        <option value="1">Position 1</option>
							        <option value="2">Position 2</option>
							        <option value="3">Position 3</option>
							        <option value="4">Position 4</option>
							        <option value="5">Position 5</option>
							        <option value="6">Position 6</option>
							        <option value="7">Position 7</option>
							        <option value="8">Position 8</option>
							        <option value="9">Position 9</option>
							        <option value="10">Position 10</option>
							        </select>                     
							    </div>
							    <div class="col-sm-4">
							        <label><%="Store Position" %></label>
							        <select class="form-control" name="storeposition" id="storeposition">
							        <option value=""> -- Seleziona -- </option>
							        <option value="1">Position 1</option>
							        <option value="2">Position 2</option>
							        <option value="3">Position 3</option>
							        <option value="4">Position 4</option>
							        <option value="5">Position 5</option>
							        <option value="6">Position 6</option>
							        <option value="7">Position 7</option>
							        <option value="8">Position 8</option>
							        <option value="9">Position 9</option>
							        <option value="10">Position 10</option>
							        </select>                     
							    </div>							    
						    </div>						
						    
							<div class="line line-dashed b-b line-lg pull-in"></div>						    
						    
						    <div class="form-group pull-in clearfix">
							    <div class="col-sm-12">
							        <label><%="like String" %></label>
										<input type="text" class="form-control" name="likestring" id="likestring" />
							    </div>
						    </div>
						        
						    <!--  
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
							    <div class="col-sm-4">
							        <label><%=propLang.get("aki_symb") %></label>
									<select id="symbol" name="symbol" class="form-control">
										<option value="" selected> -- Select -- </option>
										<option value="&euro;">&euro;</option>
										<option value="%">%</option>
										<option value="$">$</option>
									</select>   							                             
							    </div>
							    <div class="col-sm-4">
							        <label><%=propLang.get("aki_calc") %></label>
									<select id="calculate" name="calculate" class="form-control" onchange="Mod(this)">
										<option value="f" selected>False</option>										
										<option value="t">True</option>
									</select>                   
							    </div>
							    
							    <div class="col-sm-4">
							        <label><%=propLang.get("aki_color") %></label>
							        <input class="form-control jscolor" name="color" id="color" type="text" value="${Col}">                     
							    </div>							    
						    </div>
						    -->						    

						    
						  </div>
						  

						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            

			            
			              <input type="hidden" id="idimport" name="idimport" value="<%=request.getParameter("idimport") %>">
			               
						</form>
			              
			        </section> 
	              </section>
	            </section>
                
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
  
  function CustomerChange(str){
	  
		$.ajax({
			url: '../../GetDispStores',
			async: false,
			data : {
				id_customer : str.value
			},
			success : function(responseText) {

				$("#idstore").html(responseText);
				//$('.chosen-select').chosen('refresh');
				$("#idstore").trigger("chosen:updated");
			}
		
		});
	  
  }
  
  
  
  var flag = "T";
  var tipoFormPre = "";
  
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

		
		if ($("#driver").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">ID Required</div>");
			$("#driver").focus();
			return false;
		}
		
  		if ($("#separator").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Separator Required</div>");
			$("#separator").focus();
			return false;
		}
  		
  		if ($("#accountposition").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Account position Required</div>");
			$("#accountposition").focus();
			return false;
		}
  		
  		if ($("#storeposition").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Store position Required</div>");
			$("#storeposition").focus();
			return false;
		}  		  				

  		
  		if ($("#accountposition").val() == $("#storeposition").val()){
			$("#msg").html("<div class=\"alert alert-danger\">Account position and Store position they can not be the same</div>");
			return false;  			
  		}
		
	}
	
	
	var Idimp = "${Idimp}";
	if (Idimp != ""){
		$("#idimport").val(Idimp);
	}

	var Id = "${Id}";
	if (Id != ""){
		$("#driver").val(Id);
	}
	
	var Description = "${Description}";
	if (Description != ""){
		$("#description").val(Description);
	}
	
	var Separator = "${Separator}";
	if (Separator != ""){
		$("#separator").val(Separator);
	}
	
	var Accountposition = "${Accountposition}";
	if (Accountposition != ""){
		$("#accountposition").val(Accountposition);
	}
	
	var Storeposition = "${Storeposition}";
	if (Storeposition != ""){
		$("#storeposition").val(Storeposition);
	}
	
	var Likestring = "${Likestring}";
	if (Likestring != ""){
		$("#likestring").val(Likestring);
	}

  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
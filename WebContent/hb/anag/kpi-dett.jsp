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
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
    <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
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
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
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
  <div id="loading-image2" style="display:none">
		<img src="../../images/spinner.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 200px;
				   height: 200px;
				   margin-top: -100px;
				   margin-left: -50px;" />
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
              
              <%@ include file="../../inc2/menuApp11.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("aki_defun") %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="kpi-dett.htm" onsubmit="return contrName();">
                  		<input type="hidden" id="azione" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
                  		<input type="hidden" name="codeold" id="codeold" value="<%=request.getParameter("codeold") %>">
			            <input type="hidden" name="hid_grouped" id="hid_grouped" value="">
			            <input type="hidden" name="hid_door_type" id="hid_door_type" value="">
			              
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("aki_defun") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="kpi-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("aki_code") %></label>
						          <input type="text" id="code" name="code" maxlength="3" class="form-control" value="${Xcdel}" onkeyup="Uppers(this)">   
						        </div>
						        <div class="col-sm-8">
						        <label><%=propLang.get("aki_name") %></label>
						        <input class="form-control" name="name" id="name" type="text"  value="${Xctab}">                     
						    	</div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
							    <div class="col-sm-12">
							        <label><%=propLang.get("aki_desc") %></label>
							        <input class="form-control" name="description" id="description" type="text" value="${Xdesc}">                     
							    </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
							    <div class="col-sm-3">
							        <label><%=propLang.get("aki_symb") %></label>
									<select id="symbol" name="symbol" class="form-control">
										<option value="" selected> -- Select -- </option>
										<option value="&euro;">&euro;</option>
										<option value="%">%</option>
										<option value="$">$</option>
									</select>   							                             
							    </div>
							    <div class="col-sm-3" style="display: none;">
							        <label><%=propLang.get("aki_calc") %></label>
									<select id="calculate" name="calculate" class="form-control" onchange="Mod(this)">
										<option value="t" selected>True</option>									
										<option value="f">False</option>										
									</select>                   
							    </div>
								
							    <div class="col-sm-3">
							    	<div id="aaa">
							        <label><%=propLang.get("aki_calc_type") %></label>
									<select id="calculate_type" name="calculate_type" class="form-control" onchange="Mod(this)">
										<option value="1" selected>Calculated</option>									
										<option value="2">Grouped</option>										
									</select>         
									</div>          
							    </div>

							    
							    <div class="col-sm-3">
							        <label><%=propLang.get("aki_color") %></label>
							        <input class="form-control jscolor" name="color" id="color" type="text" value="${Col}">                     
							    </div>	
							    
							    <div class="col-sm-3">
							        <label>Order</label>
							        <input class="form-control" name="order" id="order" type="text" value="${order}">                     
							    </div>							    
						    </div>						    
						    
						  
						    
						<!--  -->

			            <!--  -->
						    
						  </div>
						  
						  <div class="form-group pull-in clearfix" id="kpicalculated" style="display: inline;">
						  
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						  
							        <div class="col-sm-4">
								        <label><%=propLang.get("aki_aky") %></label>
										<select id="kpi1" name="kpi1" class="form-control">
												${KpiSel}
										</select> 
							        </div>
							        <div class="col-sm-4">
								        <label><%=propLang.get("aki_symb") %></label>
										<select id="kpisymb" name="kpisymb" class="form-control">
											<!-- 
											<option value="+">+</option>
											-->
											<option value="-">-</option>
											<option value="*">*</option>
											<option value="/">/</option>
										</select> 
							
							        </div>
							        <div class="col-sm-4">
								        <label><%=propLang.get("aki_aky") %></label>
										<select id="kpi2" name="kpi2" class="form-control">
											${KpiSel}
										</select> 
							        </div>						    
						    </div>
						    
						  <div class="form-group pull-in clearfix" id="kpicalculated_grouped" style="display: none;">
						  
						    		<div class="line line-dashed b-b line-lg pull-in"></div>
						  
							        <div class="col-sm-12">
								        <label><%=propLang.get("aki_aky") %></label>
								        <select class="selectpicker" multiple name="kpi_group" id="kpi_group">
								        	${KpiSel}	
								        </select>                     
							        </div>
						    </div>						    	
						  
						  
						  						    	
						  
						  
						  
						    
						  <div class="form-group pull-in clearfix padder-lg" id="door_type_div">
						  
						  <div class="line line-dashed b-b line-lg pull-in"></div>
						  
						  
						  	<div class="form-group pull-in clearfix">
							        <div class="col-sm-12">
								        <label>Door Type Filter (Nothing = ALL)</label>
								        <select class="selectpicker" multiple name="door_type" id="door_type">
								        	${dooropt}	
								        </select>                     
							        </div>
						    </div>
						  
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            

			              <input type="hidden" id="HidCal" name="HidCal" value="${Cal}">
						  <input type="hidden" id="HidSym" value="${Sym}">
						  
						  <input type="hidden" id="HidSign" value="${Sign}">
						  <input type="hidden" id="HidKpi1" value="${Kpi1}">
						  <input type="hidden" id="HidKpi2" value="${Kpi2}">
			              
			             
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
				        
                        <a href="kpi-dett.htm" id="retlink" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-puzzle icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("aki_dsfun") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="kpi-dett.htm" class="" role="search">
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
  
  
  <!-- Chosen -->
  <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/i18n/defaults-*.min.js"></script>
<!--  -->
  <script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet">   


  <script>
  var flag = "T";
  var tipoFormPre = "";
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();

	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
	function setSelectPicker() {
		$('.selectpicker').selectpicker();
	}
	
	/*
	if ($("#HidCal").val() != ""){
		var fl = "";
		
		if ($("#HidCal").val() == "false" || $("#HidCal").val() == "f" || $("#HidCal").val() == false){
			fl = "f";
			document.getElementById("kpicalculated").style.display="inline";
		}else{
			fl = "t";
			document.getElementById("kpicalculated").style.display="inline";
		}
		
		//$("#calculate").val(fl);		
	}
	*/

	if ($("#HidSym").val() != ""){
		$("#symbol").val($("#HidSym").val());
	}
	
	if ($("#HidSign").val() != ""){
		$("#kpisymb").val($("#HidSign").val());
	}
	
	if ($("#HidKpi1").val() != ""){
		$("#kpi1").val($("#HidKpi1").val());
	}
	
	if ($("#HidKpi2").val() != ""){
		$("#kpi2").val($("#HidKpi2").val());
	}	
/**/
	function Uppers(str){
		var val = str.value;
		val = val.toUpperCase();
		$("#code").val(val);
	}
	
	
	function contrName(){
		
		if ($("#code").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Code Required</div>");
			$("#code").focus();
			return false;
		}
		
		if ($("#name").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Name Required</div>");
			$("#name").focus();
			return false;
		}
		
		if ($("#calculate_type").val() == "2"){
			if ($("#kpi_group").val() == "" || $("#kpi_group").val() == null){
				$("#msg").html("<div class=\"alert alert-danger\">Kpi Required</div>");
				$("#kpi_group").focus();
				return false;
			}
		}		
		
		var ssp = $("#kpi_group").val();
		$("#hid_grouped").val(ssp);
		
		var ssp2 = $("#door_type").val();
		$("#hid_door_type").val(ssp2);
		var app_resp = "";
		
		if ($("#tipoForm").val() == "I"){
		
			$.ajax({
				url: '../../GetNameController',
				async: false,
				data : {
					xcdel : $("#code").val()
				},
				success : function(responseText) {
		
					if (responseText == "KO"){
						$("#msg").html("<div class=\"alert alert-danger\">Code Already Exist</div>");	
						flag = "F";
					}else{
						$("#msg").html("");	
						flag = "T";				
					}
					
				}
			
			});
			
			/*Control Kpi valori*/
			$.ajax({
				url: '../../GetNameControllerValues',
				async: false,
				data : {
					code : $("#code").val(),
					kpi1 : $("#kpi1").val(),
					kpisymb : $("#kpisymb").val(),
					kpi2 : $("#kpi2").val(),
					ctype : $("#calculate_type").val(),
					kpi_group : $("#hid_grouped").val()
				},
				success : function(responseText) {
					app_resp = responseText.split(";"); 
				}
			
			});
			
			if (app_resp[0] == "KO"){
				alertify.confirm("I valore assegnati, sono già presenti nel Kpi <b>"+app_resp[1]+"</b> continuare?", function (e) {
				    if (e) {
				    	flag = "T";	
				    	_Submits($("#tipoForm").val());
				    } else {
				    	flag = "F";	
				    }
				});
				return false;
			}
		
		}else if($("#tipoForm").val() == "M" || $("#tipoForm").val() == "E"){
			
			var rep = "";
			
			$.ajax({
				url: '../../GetKpiCalculateName',
				async: false,
				data : {
					xcdel : $("#code").val()
				},
				success : function(responseText) {
		
						rep = responseText;
					
				}
			
			});
			
			// confirm dialog
			if (rep != "0"){
				alertify.confirm("Il Kpi è collegato a <b>"+rep+"</b> User continuare con le modifiche?", function (e) {
				    if (e) {
				    	flag = "T";	
				    	_Submits($("#tipoForm").val());
				    } else {
				    	
				    	flag = "F";	
				    }
				});
				return false;
			}
		}
		
		if($("#tipoForm").val() == "Y"){
			$("#tipoForm").val(tipoFormPre);
		}
		
		if (flag == "F"){
			return false;
		}
		
	}
	
	function Mod(flg){
		
		if (flg.value == "1"){
			document.getElementById("kpicalculated").style.display="inline";
			document.getElementById("kpicalculated_grouped").style.display="none";
		}else{
			document.getElementById("kpicalculated_grouped").style.display="inline";
			document.getElementById("kpicalculated").style.display="none";
			
			$("#kpi_group").trigger("chosen:updated");
			$('#kpi_group').chosen('refresh');
		}
	}
	
	function _Submits(pre){
		flag = "T";
		tipoFormPre = pre;
		$("#tipoForm").val("Y");
		$("#submit").click();
	}


if($("#tipoForm").val() != "I"){
	document.getElementById("code").readOnly = true;
}else{
	document.getElementById("code").readOnly = false;
}	


var ctype = "${Ctype}";


if (ctype != ""){
	
	$("#calculate_type").val(ctype);
	
	if (ctype == 1){
		document.getElementById("kpicalculated").style.display="inline";
		document.getElementById("kpicalculated_grouped").style.display="none";
	}else{
		document.getElementById("kpicalculated_grouped").style.display="inline";
		document.getElementById("kpicalculated").style.display="none";
		
		var vals = "${Kpiout}";
		
		if (vals != ""){			
			$('#kpi_group').selectpicker('val', [${Kpiout}]);
		}
	}
	
	// Mostra gli isle type da includere
	$('#door_type_div').show();
	
	var vals2 = "${doorout}";
	
	
	if (vals2 != ""){			
		$('#door_type').selectpicker('val', [${doorout}]);
	}
	
}
else {
	if($("#tipoForm").val() != "I"){
		$("#calculate_type").val(ctype);
		$('#aaa').hide();
		$('#kpicalculated').hide();
		$('#kpicalculated_grouped').hide();
		$('#door_type_div').hide();
	}
}

var strIn  = window.location.href;
index  = strIn.indexOf('?');
strOut = strIn.substr(index);
var links = $("#retlink").attr('href');
links = links + strOut;
$("#retlink").attr('href', links);

  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  
<!--   
  <%//if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <% //} %>
-->
  </body>
</html>
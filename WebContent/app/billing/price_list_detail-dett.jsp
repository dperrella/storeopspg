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
  <title>Price List Detail Dett | Smart Suite</title>
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
	                
	                	<h2 class="font-thin m-b"><%="Price List Detail" %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="price_list_detail-dett.htm" onsubmit="return contrName();">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%="Price List Detail" %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="price_list_detail.htm?idcard=<%=session.getAttribute("IdRet") %>" class="btn btn-default"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%="List Code" %></label>
						          	<select class="form-control" name="list_code" id="list_code">
										${ListCode}
							        </select>   
						        </div>
						        
						        <div class="col-sm-4">
							        <label><%="Product" %></label>
						          	<select class="form-control" name="product_code" id="product_code">
										${ProductsCode}
							        </select>
							    </div>
							   
							    <div class="col-sm-2">
							        <label><%="Active" %></label>
							        <select class="form-control" name="active" id="active">
								        <option value="true" selected>True</option>
								        <option value="false">False</option>
							        </select>                     
							    </div>								    
							    
						        <div class="col-sm-2">
							        <label><%="Price" %></label><br />
									<button type="button" id="plus" class="btn btn-danger" data-toggle="modal" data-target="#myModal" style="margin-top: 0px;"><i class="fa fa-plus" aria-hidden="true"></i></button>
							    </div>							    
							    
						    </div> 

						    
						  </div>
						  

						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            

			            
			              <input type="hidden" id="idcard" name="idcard" value="<%=request.getParameter("idcard") %>">
			              <input type="hidden" id="idret" name="idret" value="<%=request.getParameter("idret") %>">
			              <input type="hidden" id="hidjsons" name="hidjsons">
			               
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
  
  
  <!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog" style="width: 800px;">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Product Price JSON</h4>
      </div>
      <div class="modal-body" id="bodys" style="min-height: 200px;">
      <div id="div_0" style="min-height: 70px;">
			<div class="col-xs-12 col-md-6 col-lg-2-4">
		        <label><%="Price" %></label>
				<input type="text" id="price_0" name="price_0" class="form-control" style="width: 100%" />
		    </div>

			<div class="col-xs-12 col-md-6 col-lg-2-4">
		        <label><%="Discount" %></label>
				<input type="text" id="discount_0" name="discount_0" class="form-control" style="width: 100%" />
		    </div>	

			<div class="col-xs-12 col-md-6 col-lg-2-4">
		        <label><%="Init Echelon" %></label>
				<input type="text" id="init_0" name="init_0" class="form-control" style="width: 100%" />
		    </div>	

			<div class="col-xs-12 col-md-6 col-lg-2-4">
		        <label><%="End Echelon" %></label>
				<input type="text" id="end_0" name="end_0" class="form-control" style="width: 100%" />
		    </div>	

			<div class="col-xs-12 col-md-6 col-lg-2-4">
		        <label id="lbl_0"><%="ADD" %></label><br />
				<button type="button" id="add_0" class="btn btn-danger" onclick="ADDS()"><i class="fa fa-plus" aria-hidden="true"></i></button>
		    </div>			    
	  </div>    
	  </div>	
	
      <div class="modal-footer">
        <button type="button" class="btn btn-success btn-lg" onclick="Saves()" data-dismiss="modal">Save</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
  
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
  var count = 0;
  function ADDS(){
	  
	count++;
	
	var addedd ="<div id='div_"+count+"' style='min-height: 70px;'>"+ 
		"<div class='col-xs-12 col-md-6 col-lg-2-4'> "+
        "<label>Price</label>"+
		"<input type='text' id='price_"+count+"' name='price_"+count+"' class='form-control' />"+
    	"</div> "+
		"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
        "<label>Discount</label>"+
		"<input type='text' id='discount_"+count+"' name='discount_"+count+"' class='form-control' />"+
    	"</div>"+	
		"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
        "<label>Init Echelon</label>"+
		"<input type='text' id='init_"+count+"' name='init_"+count+"' class='form-control' />"+
    	"</div>"+	
		"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
        "<label>End Echelon</label>"+
		"<input type='text' id='end_"+count+"' name='end_"+count+"' class='form-control' />"+
    	"</div>"+	
		"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
        "<label id='lbl_"+count+"'>ADD/REMOVE</label><br />"+
		"<button type='button' id='add_"+count+"' class='btn btn-danger' onclick='ADDS()'><i class='fa fa-plus'></i></button>&nbsp;"+
		"<button type='button' id='min_"+count+"' class='btn btn-danger' onclick='MINUS("+count+")'><i class='fa fa-minus'></i></button>"+
    	"</div>"+
    	"</div>";
	  
    	$("#bodys").append(addedd);
    	
    	
    	
    	if (count>1){
    		var ints = count-1;
    		$("#lbl_"+ints).hide();
    		$("#add_"+ints).hide();
    		$("#min_"+ints).hide();
    	}else{
    		$("#lbl_0").hide();
    		$("#add_0").hide();
    	}
	  
  }
  
  function MINUS(str){
	 
	  
	  $("#div_"+str).remove();
	  	
	  if (str>1){
			var ints = str-1;
			$("#lbl_"+ints).show();
			$("#add_"+ints).show();
			$("#min_"+ints).show();
		}else{
			$("#lbl_0").show();
			$("#add_0").show();
		}
	  
	  count--;
	  
  }
  
  function Saves(){
	  
	  var counts = count;
	  
	  var jsons_ini = "{\"listings\":[";
	  var jsons_bod = "";
	  var jsons_fin = "]}";
	  
	  for (i=0; i<=counts;i++){
		if (jsons_bod == ""){  
	  		jsons_bod = "{\"price\":"+$("#price_"+i).val()+",\"discount\":"+$("#discount_"+i).val()+",\"initechelon\":"+$("#init_"+i).val()+",\"endechelon\":"+$("#end_"+i).val()+"}";
		}else{
			jsons_bod = jsons_bod + ",{\"price\":"+$("#price_"+i).val()+",\"discount\":"+$("#discount_"+i).val()+",\"initechelon\":"+$("#init_"+i).val()+",\"endechelon\":"+$("#end_"+i).val()+"}";
		}
	  }
	  
	  jsons = jsons_ini + jsons_bod + jsons_fin;
	  _json = jsons;
	  $("#hidjsons").val(_json);
	  

  }
  </script>
  
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
	 
		var resps = "";
	
		if ($("#list_code").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">List Code Required</div>");
			$("#list_code").focus();
			return false;
		}
  		
  		if ($("#product_code").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Product Code Required</div>");
			$("#product_code").focus();
			return false;
		}
  		
  		if ($("#active").val() == ""){
			$("#msg").html("<div class=\"alert alert-danger\">Active Required</div>");
			$("#active").focus();
			return false;
		}  		
  		
		 if ($("#tipoForm").val() != "E"){ 		
		  		if ($("#hidjsons").val() == ""){
					$("#msg").html("<div class=\"alert alert-danger\">Price JSON Required</div>");
					$("#plus").focus();
					return false;
				}  		  		
		 }  		
		 
		if ($("#tipoForm").val() == "I"){ 
			$.ajax({
				url: '../../GetPriceListDetailKey',
				async: false,
				data : {
					idprice : $("#list_code").val(),
					idproduct : $("#product_code").val()
				},
				success : function(responseText) {
					resps = responseText;
				}
			
			});
		 
			if(resps != ""){
				$("#msg").html("<div class=\"alert alert-danger\">List Code and Product Code Already Present</div>");
				$("#product_code").focus();
				return false;
			}		
		}
}

	
	var lis = "${Listcode}";
	if (lis != ""){
		$("#list_code").val(lis);
	}	

	var pro = "${Productlist}";
	if (pro != ""){
		$("#product_code").val(pro);
	}	
	
	var act = "${Active}";
	if (act != ""){
		if (act == "f"){
			act = "false";
		}else{
			act = "true";
		}
		$("#active").val(act);
	}	
	
	if ($("#tipoForm").val() == "M"){
		$('#list_code').prop('readonly', true);
	}	
	
	var pri = '${Productprice}';
	if (pri != ""){
		
			$("#hidjsons").val(pri);
		
/**/		tes = JSON.parse(pri);
			var lista = tes.listings;
		  	var price = "";
		  	var discount = "";
		  	var init_ = "";
		  	var end_ = "";
			var cunt = 0;
			
		 	$.each(lista, function(index, lista) {
		 		
		 		price = lista.price;
		 		discount = lista.discount;
		 		init_ = lista.initechelon;
		 		end_ = lista.endechelon;
		 		
		 		if (cunt < 1){
					
					$("#price_0").val(price);
					$("#discount_0").val(discount);
					$("#init_0").val(init_);
					$("#end_0").val(end_);
					
				}else{
					
					
					var addedd_ ="<div id='div_"+cunt+"' style='min-height: 70px;'>"+ 
					"<div class='col-xs-12 col-md-6 col-lg-2-4'> "+
			        "<label>Price</label>"+
					"<input type='text' id='price_"+cunt+"' name='price_"+cunt+"' class='form-control' />"+
			    	"</div> "+
					"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
			        "<label>Discount</label>"+
					"<input type='text' id='discount_"+cunt+"' name='discount_"+cunt+"' class='form-control' />"+
			    	"</div>"+	
					"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
			        "<label>Init Echelon</label>"+
					"<input type='text' id='init_"+cunt+"' name='init_"+cunt+"' class='form-control' />"+
			    	"</div>"+	
					"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
			        "<label>End Echelon</label>"+
					"<input type='text' id='end_"+cunt+"' name='end_"+cunt+"' class='form-control' />"+
			    	"</div>"+	
					"<div class='col-xs-12 col-md-6 col-lg-2-4'>"+
			        "<label id='lbl_"+cunt+"'>ADD/REMOVE</label><br />"+
					"<button type='button' id='add_"+cunt+"' class='btn btn-danger' onclick='ADDS()'><i class='fa fa-plus'></i></button>&nbsp;"+
					"<button type='button' id='min_"+cunt+"' class='btn btn-danger' onclick='MINUS("+cunt+")'><i class='fa fa-minus'></i></button>"+
			    	"</div>"+
			    	"</div>";
			    	
					$("#bodys").append(addedd_);
					
					$("#price_"+cunt).val(price);
					$("#discount_"+cunt).val(discount);
					$("#init_"+cunt).val(init_);
					$("#end_"+cunt).val(end_);

				}
		 		
		 		cunt++;
		 		
		    	if (cunt>1){
		    		var ints = cunt-1;
		    		$("#lbl_"+ints).hide();
		    		$("#add_"+ints).hide();
		    		$("#min_"+ints).hide();
		    	}else{
		    		$("#lbl_0").hide();
		    		$("#add_0").hide();
		    	}
				
		 		
			});
		 	
		 	

		 	count = cunt-1;

		 	$("#lbl_"+count).show();
    		$("#add_"+count).show();
    		$("#min_"+count).show();

/**/		
	}	

  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
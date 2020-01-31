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
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
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
              
            	<%@ include file="../../inc2/menuApp.jsp" %>
            	
                <aside class="col-sm-9 padder " >
	              <section class="vbox">
	                <section class="scrollable hover">
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("aar_dett") %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="aree-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("aar_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="aree-stores-list.htm"><%=propLang.get("aar_back_stores") %></a></li>
									<li><a href="aree-list.htm?id_store=${id_store}"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("aar_id_store") %></label>
						          <select id="id_store" name="id_store" data-required="true" 
						          	class="form-control parsley-validated" ${key} ${btnActive} readonly>
							          <c:forEach items="${sto}" var="sto" varStatus="status">
			                          <option value="${sto.id}">${sto.id} - ${sto.description} [${sto.cmp1}]</option>
			                          </c:forEach>
	                        	  </select>    
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("aar_id_area") %></label>
						        <input type="text" id="id_area" name="id_area" class="form-control" value="${id_area}" 
						        	data-type="number" class="form-control parsley-validated" data-required="true" ${key} ${btnActive}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("aar_description") %></label>
						        <input type="text" id="description" name="description" class="form-control" value="${description}" data-minlength="3" data-maxlength="250" data-required="true" ${btnActive}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("aar_codice") %></label>
						        <input type="text" id="codice" name="codice" class="form-control" value="${codice}" data-minlength="2" data-maxlength="50" data-required="true" ${btnActive}>                        
						    </div>
						    
					        <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						      <label><%=propLang.get("aar_esterna") %></label>
					          <select id="esterna" name="esterna" data-required="true" 
					          	class="form-control parsley-validated" ${btnActive}>
						          <option value="t"><%=propLang.get("aar_S") %></option>
						          <option value="f"><%=propLang.get("aar_N") %></option>
		                      </select>    
					        </div>
					        
					        <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						      <label><%=propLang.get("aar_gruppo") %></label>
					          <select id="gruppo" name="gruppo" data-required="true" 
					          	class="form-control parsley-validated" ${btnActive}>
						          <c:forEach items="${tp}" var="tp" varStatus="status">
		                          <option value="${tp.xcdel}">${tp.xcdel} - ${tp.xdtab}</option>
		                          </c:forEach>
		                      </select>    
					        </div>
					        
					        <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						      <label><%=propLang.get("aar_tipo") %></label>
					          <select id="tipo" name="tipo" data-required="true" 
					          	class="form-control parsley-validated" onchange="hideShow(this.value)" ${btnActive}>
						          <c:forEach items="${tp2}" var="tp2" varStatus="status">
		                          <option value="${tp2.xcdel}">${tp2.xcdel} - ${tp2.xdtab}</option>
		                          </c:forEach>
		                      </select>    
					        </div>
					        
					        <div id="aree-div">
					        	
					        	<div class="line line-dashed b-b line-lg pull-in"></div>
					        	
					        	<div class="form-group">
							      <label><%=propLang.get("add_id_area_from") %></label>
						          <select id="id_area_from" name="id_area_from" data-required="true" 
						          	class="form-control parsley-validated" ${btnActive}>
							          <c:forEach items="${aree}" var="aree" varStatus="status">
			                          <option value="${aree.id_area}">${aree.id_area} - ${aree.description}</option>
			                          </c:forEach>
			                      </select>    
						        </div>
						        
						        <div class="line line-dashed b-b line-lg pull-in"></div>
					        	
					        	<div class="form-group">
							      <label><%=propLang.get("add_id_area_to") %></label>
						          <select id="id_area_to" name="id_area_to" data-required="true" 
						          	class="form-control parsley-validated" ${btnActive}>
							          <c:forEach items="${aree}" var="aree" varStatus="status">
			                          <option value="${aree.id_area}">${aree.id_area} - ${aree.description}</option>
			                          </c:forEach>
			                      </select>    
						        </div>
					        </div>
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            
			            
						 
						 
						</form>
			            
			            
	                
	                </section>
	              </section>
	            </aside>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%=propLang.get("aar_stores") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	  
                        	  <li class="list-group-item">
		                        <div class="clear">
		                        	Brand:<br/>
			                    	<b>${brandlogo} </b>
		                        </div> 
		                      </li>
	                          <li class="list-group-item">
			                    <div class="clear">
			                    	Address:<br/>
			                    	<b>${address} </b>
		                        </div> 
		                      </li>
		                      <li class="list-group-item">
		                        <div class="clear">
			                    	City:<br/>
			                    	<b>${city} </b>
		                        </div> 
		                      </li>
		                      <li class="list-group-item">
		                        <div class="clear">
		                        	PostalCode:<br/>
			                    	<b>${postalcode} </b>
		                        </div> 
		                      </li>
		                      
		                         
	                        </ul>
                        </div>
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
				        
                        <a href="aree-stores-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-map icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("sto_sto") %></span>
                        </a>

                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="aree-stores-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("sto_cerca") %>">
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
	
  $('#id_store').val('${id_store}');
  $('#esterna').val('${esterna}');
  $('#gruppo').val('${gruppo}');
  $('#tipo').val('${tipo}');
  
  if('${tipo}'=='FITT') {
	  
	  $('#aree-div').show();
	  
	  $('#id_area_from').val('${id_area_from}');
	  $('#id_area_to').val('${id_area_to}');
	  
  }
  else {
	  $('#aree-div').hide();
  }
  
  function hideShow(tipo) {
	  
	  if(tipo=='FITT') {
		  $('#aree-div').show();
	  }
	  else {
		  $('#aree-div').hide();
	  }
	  
  }
  
  
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
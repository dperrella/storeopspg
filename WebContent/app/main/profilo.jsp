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
            <section id="bjax-target">
              <section class="hbox stretch">
            	
            	
                <aside class="col-sm-9 padder " >
	              <section class="vbox">
	                <section class="scrollable hover">
	                
	                	<h2 class="font-thin m-b">${dsfun}</h2>
	                	
	                	<form action="profilo-esegui.jsp" data-validate="parsley" class="form-horizontal" method="post">
			            <input type="hidden" name="azione" value="S">
			              
	                		<section class="panel panel-default">
				              <header class="panel-heading font-bold">
				                ${dsfun} 
				              </header>
				              
				              <div class="panel-body">
								
							    <div id="msg">${msg}</div>
							    
							    <div class="text-center m-b m-t">
		                          <span class="thumb-lg">
		                            <img src="../../images/${foto}" class="img-circle">
		                          </span>
		                          <div>
		                            <div class="h3 m-t-xs m-b-xs">${username}</div>
		                            <small class="text-muted">${nome} ${cogn}</small>
		                          </div>                
		                        </div>
							    
							    <div class="line line-dashed b-b line-lg pull-in"></div>
			                   
			                   
			                    <div class="form-group">
			                      <label class="col-sm-2 control-label"><%=propLang.get("prof_username") %></label>
			                      <div class="col-sm-10">
				                      <input type="text" name="username" id="username" value="${idusr}" class="form-control" readonly>     
				                  </div>
				                </div>
				                
				                <div class="alert alert-warning">
				                	<%=propLang.get("prof_warning") %>
				                </div>
				                
				                <div class="form-group">
			                      <label class="col-sm-2 control-label"><%=propLang.get("prof_password") %></label>
			                      <div class="col-sm-10">
				                      <input type="password" name="password" id="password" class="form-control parsley-validated" value="" class="form-control">     
				                  </div>
				                </div>
				                
				                <div class="line line-dashed b-b line-lg pull-in"></div>
			                   
				                <div class="form-group">
			                      <label class="col-sm-2 control-label"><%=propLang.get("prof_password2") %></label>
			                      <div class="col-sm-10">
				                      <input type="password" name="password2" id="password2" class="form-control parsley-validated" data-equalto="#password" value="" class="form-control">     
				                  </div>
				                </div>
				                
			                    <div class="line line-dashed b-b line-lg pull-in"></div>
			                   
			                    <div class="form-group">
			                      <label class="col-sm-2 control-label"><%=propLang.get("prof_nome") %></label>
			                      <div class="col-sm-10">
				                      <input type="text" name="nome" id="nome" value="${nome}" data-required="true" class="form-control">     
				                  </div>
				                </div>
				                
				                <div class="line line-dashed b-b line-lg pull-in"></div>
			                   
			                    <div class="form-group">
			                      <label class="col-sm-2 control-label"><%=propLang.get("prof_cognome") %></label>
			                      <div class="col-sm-10">
				                      <input type="text" name="cogn" id="cogn" value="${cogn}" data-required="true" class="form-control">     
				                  </div>
				                </div>
				                
				                <div class="line line-dashed b-b line-lg pull-in"></div>
			                   
				                <div class="form-group">
			                      <label class="col-sm-2 control-label"><%=propLang.get("prof_mail") %></label>
			                      <div class="col-sm-10">
				                      <input type="text" name="mail" id="mail" value="${mail}" data-type="email" data-required="true" class="form-control">     
				                  </div>
				                </div>
				               
				                <!--  
				                <div class="line line-dashed b-b line-lg pull-in"></div>
			                   
				                <div class="form-group">
			                      <label class="col-sm-2 control-label"><%=propLang.get("prof_avatar") %></label>
			                      <div class="col-sm-10">
				                	  <input name="myFile" id="myFile" type="file" class="form-control" />
				                  </div>
				                </div>
				                -->
							  </div>
							  
							  <footer class="panel-footer text-right bg-light lter">
						      <button type="submit" id="submit" class="btn btn-success btn-s-xs" ${btnActive}><%=propLang.get("prof_button") %></button>
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
	                     	<p class="h4 font-thin"><%=propLang.get("usr_aut") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${fun}" var="fun" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="pull-left m-l-xs m-r-sm">
									
									<input type="hidden" name="nrRiga" value="${fun.cmp2}" />
									<input type="hidden" name="cdfun" value="${fun.cdfun}" />
									<div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" name="post${fun.cmp2}" id="post${fun.cmp2}" data-required="true" ${fun.cmp1} disabled>
			                            <i></i>
			                          </label>
			                        </div>

		                        </div> 
		                        <div class="clear">
		                        	<div><a href="../popup/function.jsp?cdapp=${fun.cdfun}"
		                       			class="getModal" data-toggle="modal">${fun.cdfun} - ${fun.tipo}</a></div> 
		                       		<small class="text-muted">${fun.descr}</small> 
		                       	</div> 
		                        
			                  </li>
	                        </c:forEach>
	                          
	                        </ul>
                        </div>
                    </section>
                  </section>
                </section>
                
                <!-- side content -->
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="profilo.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-user"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("profilo") %></span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="../config/users-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("usr_cerca") %>">
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
  
  <script src="../../js/bootstrap-modal.js"></script>
  
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
	
	//MODAL
	$('.getModal').click(function(e) {
	
		e.preventDefault();
		var href = $(e.target).attr('href');

		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
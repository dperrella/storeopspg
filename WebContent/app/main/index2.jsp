<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css?2" type="text/css" />
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
          <section class="hbox stretch">          
            <section>
              
              <section class="vbox">
                <section class="scrollable" id="bjax-target">
                <header class="header bg-light dk">
	              <p class="h2 font-thin m-b">Desk Smart Suite <%=propIdx.get("versione") %> 2.0.0</p>
	            </header>
	            <section class="">
	              
	              
	              
	                    
	                    <div class="row row-sm">
	                    	
	                    	<div class="col-sm-12">
	                    		
	                    		<!-- <section class="panel panel-default">-->
                    				
                    				<section class="hbox">
	                    		
		                    			<aside class="aside-lg">
		                    				
		                    				<section class="panel panel-default no-border">
						                        <header class="panel-heading bg-light">
						                          <div class="clearfix">
						                            <a href="#" class="pull-left thumb-md avatar b-3x m-r">
						                              <img src="../../images/SCN.png" alt="...">
						                            </a>
						                            <div class="clear">
						                              <div class="h3 m-t-xs m-b-xs">
						                                SmartControl
						                                <i class="fa fa-circle text-danger pull-right text-xs m-t-sm"></i>
						                              </div>
						                              <small class="text-muted">Devices Control and Management</small>
						                            </div>
						                          </div>
						                        </header>
						                        <div class="list-group no-radius alt">
						                          <a class="list-group-item" href="#">
						                            <span class="badge bg-success">25</span>
						                            <i class="fa fa-list-alt icon-muted"></i> 
						                            Log List
						                          </a>
						                          <a class="list-group-item" href="#">
						                            <span class="badge bg-info">16</span>
						                            <i class="fa fa-desktop icon-muted"></i> 
						                            Device Not Paired
						                          </a>
						                          <a class="list-group-item" href="#">
						                            <span class="badge bg-light">5</span>
						                            <i class="fa fa-users icon-muted"></i> 
						                            People Counter Not Update
						                          </a>
						                          <a class="list-group-item" href="#">
						                            <span class="badge bg-light">5</span>
						                            <i class="icon-bar-chart icon-muted"></i> 
						                            Analytics Last Update
						                          </a>
						                          <a class="list-group-item" href="#">
						                            <span class="badge bg-light">5</span>
						                            <i class="fa fa-bullhorn icon-muted"></i> 
						                            Analytics Active Alarms
						                          </a>
						                          <a class="list-group-item" href="#">
						                            <span class="badge bg-light">5</span>
						                            <i class="fa fa-list icon-muted"></i> 
						                            Updating List
						                          </a>
						                          <a class="list-group-item" href="#">
						                            <span class="badge bg-light">5</span>
						                            <i class="fa fa-ban icon-muted"></i> 
						                            Device Not Enabled
						                          </a>
						                        </div>
						                        
						                    </section>	
		                    			
		                    			
		                    			</aside>
		                    			<section class="bg-white wrapper">
		                    			a
		                    			</section>
		                    			
		                    		</section>
		                    	
                    				
                    				
                    			<!-- </section> -->
	                    		
	                    		
	                    		
	                    		
                      
	                    		
	                    	</div>
	                    	
	                    	
	                    	
	                    </div>
	                    
	                    
	                  </section>
	
             	  
             	  
                </section> 
              </section>
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
  <!-- 
  <script src="../../js/charts/easypiechart/jquery.easy-pie-chart.js"></script>
  -->

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
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
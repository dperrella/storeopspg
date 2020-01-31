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
                <section class="scrollable padder-lg " id="bjax-target">
	              <h2 class="font-thin m-b">Desk Smart Suite <%=propIdx.get("versione") %> 2.0.0</h2>
	              
	              
	              
	              <div class="row row-sm">
	              	
<%
if(mnu01!=null) {
	if(mnu01.length==1) {
 %>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-3">
	                  
	                  <section class="panel panel-default">
	                    <div class="panel-body">
	                      <div class="clearfix text-center m-t">
	                        <div class="inline">
	                            <div class="thumb-lg">
	                            <a href="../../<%=mnu01[0].substring(228, mnu01[0].length()) %>">
	                              <img src="../../images/<%=mnu01[0].substring(0,3) %>.png?v7" class="img-circles" style="height: 134px;" alt="...">
	                            </a>
	                            </div>
	                        </div>                      
	                      </div>
	                    </div>
	                    <footer class="panel-footer bg-dark text-center">
	                      <div class="row pull-out">
	                        <div class="col-xs-12">
	                          <div class="padder-v">
	                            <span class="m-b-xs h3 block text-white text-ellipsis"><%=mnu01[0].substring(3,28) %></span>
	                            <small class="text-muted text-ellipsis"><%=mnu01[0].substring(28,128) %></small>
	                          </div>
	                        </div>
	                      </div>
	                    </footer>
	                  </section>
	                  
	                </div>
	               
<%	
	}
	else {
		String puntini = "../../";
		for(m=0; m < mnu01.length ; m++)
		{
			if(mnu01[m].substring(228, mnu01[m].length()).contains("http")) {
				puntini = "";
			}
			else {
				puntini = "../../";
			}
%>	

					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-3">
	                  
	                  <section class="panel panel-default">
	                    <div class="panel-body">
	                      <div class="clearfix text-center m-t">
	                        <div class="inline">
	                            <div class="thumb-lg">
	                            <a href="<%=puntini %><%=mnu01[m].substring(228, mnu01[m].length()) %>">
	                              <img src="../../images/<%=mnu01[m].substring(0,3) %>.png?v7" class="img-circles" style="height: 134px;" alt="...">
	                            </a>
	                            </div>
	                        </div>                      
	                      </div>
	                    </div>
	                    <footer class="panel-footer bg-dark text-center">
	                      <div class="row pull-out">
	                        <div class="col-xs-12">
	                          <div class="padder-v">
	                            <span class="m-b-xs h3 block text-white text-ellipsis"><%=mnu01[m].substring(3,28) %></span>
	                            <small class="text-muted text-ellipsis"><%=mnu01[m].substring(28,128) %></small>
	                          </div>
	                        </div>
	                      </div>
	                    </footer>
	                  </section>
	                  
	                </div>
<%
		}
	}
}
%>	
	 
	
             	  </div>
             	  <!--  
             	  <div class="row row-sm">
             	  	<div class="col-sm-12">
             	  		
             	  		<a href="index2.htm">Link a Test Dashboard...</a>
             	  		
             	  	</div>
             	  </div>
             	  -->
             	  
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
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
            <section class="" id="bjax-target">
              <section class="hbox stretch bg-black dker">
              
              <!-- side content -->
                <aside class="col-sm-3 no-padder aside" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover" style="margin-bottom:40px">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        <!-- 
                        <span class="list-group-item">
		                  <b><%=propLang.get("sel_report") %></b>
		                </span>
		                -->
		                <div class="m-t-n-xxs item pos-rlt">
	                      <div class="top text-right">
	                        <span class="musicbar bg-success bg-empty inline m-r-lg m-t" style="width:25px;height:30px">
	                          <span class="bar1 a3 lter"></span>
	                          <span class="bar2 a5 lt"></span>
	                          <span class="bar3 a1 bg"></span>
	                          <span class="bar4 a4 dk"></span>
	                          <span class="bar5 a2 dker"></span>
	                        </span>
	                      </div>
	                      <div class="bottom gd bg-info wrapper-lg">
	                        <span class="h2 font-thin">Suite Apps</span>
	                      </div>
	                      <img class="img-full" src="../../images/HomePageMilano_2.jpg" alt="...">
	                    </div>
		                
		                <ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
		                <c:forEach items="${apps}" var="apps" varStatus="status">
		                <li class="list-group-item ${apps.cmp1}">
		                	<a href="notification-list.htm?app=${apps.xcdel}">
		                	<i class="${apps.cmp2}"></i>${apps.cmp3} <span class="m-l">${apps.xdtab}</span>
		                	</a>
		                </li>
			            </c:forEach>
			            </ul>
                        
                      </div>
                    </section>
                    <!-- 
                    <footer class="footer footer-md bg-light dker">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <a href="javascipt:void;" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></a>
	                        </span>
	                        <input type="text" id="search_input" class="form-control input-sm bg-empty b-b b-dark no-border" placeholder="<%=propLang.get("rep_cerca") %>">
	                      </div>
	                    </div>
                    </footer>
                    -->
                  </section>
                </aside>
                <!-- / side content -->
                
                <!-- side content -->
	            <section class="col-sm-2 no-padder bg">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto bg clearfix">
                        <span class="text-muted list-group-item">
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Status</span>
				        </span>
				        
                        <a href="notification-list.htm?app=${app}" class="list-group-item ${all_active}">
                          <span class="hidden-nav-xs m-l-sm">All</span>
                        </a>
                        
                        <c:forEach items="${sts}" var="sts" varStatus="status">
		                <a href="notification-list.htm?app=${app}&sts=${sts.xcdel}" class="list-group-item ${sts.cmp1}">
                          <span class="hidden-nav-xs m-l-sm">${sts.xdtab}</span>
                        </a>
                        
		                </c:forEach>
						
                      </div>
	                </section>
	                
	              </section>              
	            </section>
	            <!-- / side content -->
	            
	            <section class="col-sm-7 no-padder ">
	                <section class="vbox">
	                  <section class="scrollable hover">
	                    <ul id="search_list" class="list-group list-group-lg no-bg auto m-b-none m-t-n-xxs">
	                    <c:forEach items="${notif}" var="notif" varStatus="status">
								
	                      <li class="list-group-item clearfix">
	                        <span class="pull-right m-t-sm m-l text-md">
	                          ${notif.stato}
	                        </span>
	                        
	                        <span class="pull-right m-t-sm m-l text-md">
	                          <small class="text-muted">${notif.dtnot}</small>
	                        </span>
	                        <a class="clear" href="${notif.link}">
	                          <span class="block text-ellipsis">${notif.titolo}</span>
	                          <small class="text-muted">${notif.nota}</small>
	                        </a>
	                      </li>
	                    </c:forEach>
	                    </ul>
	                  </section>
	                  <footer class="footer footer-md bg-black dk">
	                  <form action="notification-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <a href="javascipt:void;" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></a>
	                        </span>
	                        <input id="search_input" type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("not_cercaNot") %>">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
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
  <!-- Filter list -->
  <script src="../../js/jquery.fastLiveFilter.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <script>
  $('#example').dataTable( {
        "language": {
            "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
            "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
            "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_",
            "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
            "infoFiltered": "(Filtrati da _MAX_ records totali)",
            "decimal": ",",
            "thousands": ".",
            search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
        },
        "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "aaSorting": [],
        "iDisplayLength": 50
    } );
  
  	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
	$(function() {
	    $('#search_input').fastLiveFilter('#search_list');
	});
  	
	
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
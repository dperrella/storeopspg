<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propGen = new ReadProperties("connection.properties");

%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>Desk | Smart HR</title>
  <meta name="description" content="Desk SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
</head>
<body class="">
  <section class="vbox">
    <header class="bg-white-only header header-md navbar navbar-fixed-top-xs">
      <div class="navbar-header aside <%=session.getAttribute("style") %> nav-xs">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
          <i class="icon-list"></i>
        </a>
        <a href="../../app/main/index.htm" class="navbar-brand text-lt">
          <img src="../../images/<%=session.getAttribute("logo") %>" alt="<%=session.getAttribute("sysinf") %>">
          <span class="hidden-nav-xs m-l-sm"><%=session.getAttribute("sysinf") %></span>
        </a>
        <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".user">
          <i class="icon-settings"></i>
        </a>
      </div>      
      
      <ul class="nav navbar-nav hidden-xs">
        <li>
          <a href="#nav,.navbar-header" data-toggle="class:nav-xs,nav-xs" class="text-muted">
            <i class="fa fa-indent text"></i>
            <i class="fa fa-dedent text-active"></i>
          </a>
        </li>
      </ul>
      
      <form method="post" action="../../app/main/ricerca.htm" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">
        <div class="form-group"  style="visibility: hidden;">
          <div class="input-group">
            <span class="input-group-btn">
              <button type="submit" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
            </span>
            <input type="text"  name="q" class="form-control input-sm no-border rounded" placeholder="<%=propIdx.get("cerca") %>">
          </div>
        </div>
      </form>
      
      <div class="navbar-right ">
        <ul class="nav navbar-nav m-n hidden-xs nav-user user">
          
          <%=session.getAttribute("notif") %>
          
          <li class="dropdown">
            <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
              <span class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
                <img src="../../images/${foto}" alt="...">
              </span>
              ${username} <b class="caret"></b>
            
            </a>
            <ul class="dropdown-menu animated fadeInRight">            
              <li>
                <a href="../../app/main/profilo.htm"><%=propIdx.get("profilo") %></a>
              </li>
              <li>
                <a href="../main/notifiche.htm">
                  <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                  <%=propIdx.get("notifiche") %>
                </a>
              </li>
              <li>
                <a href="../../app/main/docs.html"><%=propIdx.get("guida") %></a>
              </li>
              <li class="divider"></li>
              <li>
                <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal"><%=propIdx.get("esci") %></a>
              </li>
            </ul>
          </li>
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
                
                <%@ include file="../../inc/menu-left-hr.jsp" %>
                
                <!-- / nav -->
                
                
              </div>
            </section>
            
			
            <footer class="footer hidden-xs no-padder text-center-nav-xs">
              <div class="bg hidden-xs ">
                  <div class="dropdown dropup wrapper-sm clearfix">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                      <span class="thumb-sm avatar pull-left m-l-xs">                        
                        <img src="../../images/${foto}" class="dker" alt="...">
                        <i class="on b-black"></i>
                      </span>
                      <span class="hidden-nav-xs clear">
                        <span class="block m-l">
                          <strong class="font-bold text-lt">${username}</strong> 
                          <b class="caret"></b>
                        </span>
                        <span class="text-muted text-xs block m-l">${desc}</span>
                      </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight aside text-left">                      
                      <li>
                        <a href="../../app/main/profilo.htm"><%=propIdx.get("profilo") %></a>
                      </li>
                      <li>
                        <a href="../main/notifiche.htm">
                          <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                          <%=propIdx.get("notifiche") %>
                        </a>
                      </li>
                      <li>
                        <a href="../../app/main/docs.html"><%=propIdx.get("guida") %></a>
                      </li>
                      <li class="divider"></li>
                      <li>
                        <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" ><%=propIdx.get("esci") %></a>
                      </li>
                    </ul>
                  </div>
                </div>            
			</footer>
			
          </section>
        </aside>
        <!-- /.aside -->
        <section id="content">
          <section class="hbox stretch">          
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
	              <h2 class="font-thin m-b">Desk Smart HR <%=propGen.get("customer") %> - <%=propIdx.get("versione") %> <%=propGen.get("versione") %></h2>
	            
	              <div class="row row-sm">
	              	
	              	<div class="col-xs-12">

						<div class="row m-b-lg text-center">
		                    <div class="col-sm-4">
		                      <div class="bg-primary wrapper-md r">
		                        <a href="#">
		                          <span class="h4 block"><i class="icon-users i-lg"></i> <%=propIdx.get("nrdip") %></span>
		                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrcli}</span>
		                        </a>
		                      </div>
		                    </div>
		                    <div class="col-sm-4">
		                      <div class="bg-black wrapper-md r">
		                        <a href="#">
		                          <span class="h4 block"><i class="fa fa-building-o i-lg"></i> <%=propIdx.get("nrdep") %></span>
		                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrdep}</span>
		                        </a>
		                      </div>
		                    </div>
		                    <div class="col-sm-4">
		                      <div class="bg-light dker wrapper-md r">
		                        <a href="#">
		                          <span class="h4 block"><i class="fa fa-calendar icon i-lg"></i> <%=propIdx.get("nrplan") %></span>
		                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrplan}</span>
		                        </a>
		                      </div>
		                    </div>
		                  </div>
					
					  </div>
					
					
					
<%
if(mnu01!=null) {
	if(mnu01.length==1) {
 %>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu01[0].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu01[0].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu01[0].substring(55,135) %>"><img src="../../images/<%=mnu01[0].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu01[0].substring(55,135) %>" class="text-ellipsis"><%=mnu01[0].substring(3,53) %></a>
	                      <a href="<%=mnu01[0].substring(55,135) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("anagrafici") %></a>
	                    </div>
	                  </div>
	                </div>
<%	
	}
	else {
		for(m=0; m < mnu01.length ; m++)
		{
%>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu01[m].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu01[m].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu01[m].substring(55,105) %>"><img src="../../images/<%=mnu01[m].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu01[m].substring(55,105) %>" class="text-ellipsis"><%=mnu01[m].substring(3,53) %></a>
	                      <a href="<%=mnu01[m].substring(55,105) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("anagrafici") %></a>
	                    </div>
	                  </div>
	                </div>
<%
		}
	}
}
%>	

<%
if(mnu02!=null) {
	if(mnu02.length==1) {
 %>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu02[0].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu02[0].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu02[0].substring(55,135) %>"><img src="../../images/<%=mnu02[0].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu02[0].substring(55,135) %>" class="text-ellipsis"><%=mnu02[0].substring(3,53) %></a>
	                      <a href="<%=mnu02[0].substring(55,135) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("relazioni") %></a>
	                    </div>
	                  </div>
	                </div>
<%	
	}
	else {
		for(m=0; m < mnu02.length ; m++)
		{
%>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu02[m].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu02[m].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu02[m].substring(55,105) %>"><img src="../../images/<%=mnu02[m].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu02[m].substring(55,105) %>" class="text-ellipsis"><%=mnu02[m].substring(3,53) %></a>
	                      <a href="<%=mnu02[m].substring(55,105) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("relazioni") %></a>
	                    </div>
	                  </div>
	                </div>
<%
		}
	}
}
%>	
                   
<%
if(mnu03!=null) {
	if(mnu03.length==1) {
 %>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu03[0].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu03[0].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu03[0].substring(55,135) %>"><img src="../../images/<%=mnu03[0].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu03[0].substring(55,135) %>" class="text-ellipsis"><%=mnu03[0].substring(3,53) %></a>
	                      <a href="<%=mnu03[0].substring(55,135) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("pianificazione") %></a>
	                    </div>
	                  </div>
	                </div>
<%	
	}
	else {
		for(m=0; m < mnu03.length ; m++)
		{
%>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu03[m].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu03[m].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu03[m].substring(55,105) %>"><img src="../../images/<%=mnu03[m].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu03[m].substring(55,105) %>" class="text-ellipsis"><%=mnu03[m].substring(3,53) %></a>
	                      <a href="<%=mnu03[m].substring(55,105) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("pianificazione") %></a>
	                    </div>
	                  </div>
	                </div>
<%
		}
	}
}
%>	 

<%
if(mnu04!=null) {
	if(mnu04.length==1) {
 %>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu02[0].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu04[0].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu04[0].substring(55,135) %>"><img src="../../images/<%=mnu04[0].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu04[0].substring(55,135) %>" class="text-ellipsis"><%=mnu04[0].substring(3,53) %></a>
	                      <a href="<%=mnu04[0].substring(55,135) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("controlli") %></a>
	                    </div>
	                  </div>
	                </div>
<%	
	}
	else {
		for(m=0; m < mnu04.length ; m++)
		{
%>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
	                  <div class="item">
	                    <div class="pos-rlt">
	                      <div class="bottom">
	                        <span class="badge bg-info m-l-sm m-b-sm"><%=mnu04[m].substring(0,3) %></span>
	                      </div>
	                      <div class="item-overlay opacity r r-2x bg-black">
	                        <div class="center text-center m-t-n">
	                          <a href="<%=mnu04[m].substring(55,105) %>"><i class="fa fa-gears i-2x"></i></a>
	                        </div>
	                      </div>
	                      <a href="<%=mnu04[m].substring(55,105) %>"><img src="../../images/<%=mnu04[m].substring(0,3) %>.jpg" alt="" class="r r-2x img-full"></a>
	                    </div>
	                    <div class="padder-v">
	                      <a href="<%=mnu04[m].substring(55,105) %>" class="text-ellipsis"><%=mnu04[m].substring(3,53) %></a>
	                      <a href="<%=mnu04[m].substring(55,105) %>" class="text-ellipsis text-xs text-muted"><%=propIdx.get("controlli") %></a>
	                    </div>
	                  </div>
	                </div>
<%
		}
	}
}
%>	 
             	  </div>
             	  
             	  
                </section> 
              </section>
            </section>
            
            <!-- side content -->
            <aside class="aside-md bg-dark dker" id="sidebar">
              <section class="vbox animated fadeInRight">
                <section class="w-f-md scrollable hover">
                  
                  <div class="wrapper bg-black"><h4 class="font-thin m-t-xs text-white"><%=propIdx.get("ultplan") %></h4></div>
                  
                  <ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                    <c:forEach items="${plan}" var="plan" varStatus="status">
		            <li class="list-group-item">
                      
                      <div class="clear">
                        <div><a href="../planner/planner-list2.htm?tp=I&year=${plan.cmp1}&week=${plan.cmp2}&id_departments=${plan.cmp3}">${plan.cmp4}</a></div>
                        <small class="text-muted smallest">${plan.cmp1} <%=propIdx.get("sett") %> ${plan.cmp2}</small>
                      </div>
                    </li>
                    </c:forEach>
                  </ul>
                </section>
                <footer class="footer footer-md bg-black">
                  <form action="../../app/planner/planification-list.htm" class="" role="search">
                    <div class="form-group clearfix m-b-none">
                      <div class="input-group m-t m-b">
                        <span class="input-group-btn">
                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
                        </span>
                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border" placeholder="<%=propIdx.get("cercaplan") %>">
                      </div>
                    </div>
                  </form>
                </footer>
              </section>              
            </aside>
            <!-- / side content -->
            
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
  
</body>
</html>
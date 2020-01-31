<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>${dsfun} | SmartHR</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
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
	
	
	
	.WRK {
	  background: #ffffff;
	}			
	.ROL {
	  background: #F2DEDE;
	  color: #BA6866;
	}
	.FER {
	  background: #D9EDF7;
	  color: #31708F;
	}
	.MAL {
	  background: #FCF8E3;
	  color: #8A6D3B;
	  
	}
	.MON {
	  background: #F0FAFF;
	}
	.104 {
	  background: #F3F9FB;
	}
	.ASP {
	  background: #678296;
	  color:fff;
	}
	.PLN {
	  background: #DFF0D8;
	  color: #629361;
	}
	.DIF {
	  background: #F2F4F8;
	}
	
	
	
	
	
	
	
	
	
  </style>
</head>
<body class="">
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
        <div class="form-group">
          <div class="input-group">
            <span class="input-group-btn">
              <button type="submit" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
            </span>
            <input type="text"  name="q" class="form-control input-sm no-border rounded" placeholder="<%=propLang.get("cerca") %>...">
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
                <a href="../../app/main/profilo.htm"><%=propLang.get("profilo") %></a>
              </li>
              <li>
                <a href="../main/notifiche.htm">
                  <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                  <%=propLang.get("notifiche") %>
                </a>
              </li>
              <li>
                <a href="../../app/main/docs.html"><%=propLang.get("guida") %></a>
              </li>
              <li class="divider"></li>
              <li>
                <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal"><%=propLang.get("esci") %></a>
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
                        <a href="../../app/main/profilo.htm"><%=propLang.get("profilo") %></a>
                      </li>
                      <li>
                        <a href="../main/notifiche.htm">
                          <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                          <%=propLang.get("notifiche") %>
                        </a>
                      </li>
                      <li>
                        <a href="../../app/main/docs.html"><%=propLang.get("guida") %></a>
                      </li>
                      <li class="divider"></li>
                      <li>
                        <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" ><%=propLang.get("esci") %></a>
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
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b">${dsfun}</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                ${dsfun} 
			              </header>
			              
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="control-ric.htm?tp=${tp}"><%=propLang.get("tornaRic") %></a></li>
								</ul>
							</div>
						  </section>
			              
			              <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<form data-validate="parsley" action="control-list.htm" method="post">
		                
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-6">
						          <label><%=propLang.get("pla_seldip") %></label>
						          <select id="id_departments" name="id_departments" data-required="true" 
						          	onchange="cambiaSettimana()" class="form-control parsley-validated" disabled >
							          <option value="">-</option>
							          <c:forEach items="${dep}" var="dep" varStatus="status">
			                          <option value="${dep.id}">${dep.description}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						        
						        <div class="col-sm-2">
						          <label><%=propLang.get("pla_year") %></label>
						          <select id="year" name="year" data-required="true" class="form-control parsley-validated" disabled>
							          <option value="2015">2015</option>
							          <option value="2016">2016</option>
							          <option value="2017">2017</option>
			                      </select>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label><%=propLang.get("pla_week") %></label>
						          <select id="week" name="week" data-required="true" class="form-control parsley-validated" disabled>
							          <option value="">-</option>
							          <c:forEach items="${weeks}" var="weeks" varStatus="status">
			                          <option style="background-color: ${weeks.cmp2}" value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						        
						    </div>
						    </form>
						    						    
						  </div>
						  
			            </section>
                    	
		                
		                
		                
		                
	                 	
			            <!--  Dipendente -->  
						<c:forEach items="${emp}" var="emp" varStatus="status">
						  
						<section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span>${emp.name} ${emp.surname} ${emp.cmp4}</span>
			              </header>
						  
						  <div class="table-responsive">
			               <table class="table b-t b-light">
			                
			                <tr>
			                    <th class="b-r" style="width:15%"><%=propLang.get("cnt_data") %></th>
			                    <th class="b-r" style="width:10%"><%=propLang.get("cnt_tipo") %></th>
			                    <th class="b-r" style="width:15%"><%=propLang.get("cnt_in") %></th>
			                    <th class="b-r" style="width:15%"><%=propLang.get("cnt_pausaIni") %></th>
			                    <th class="b-r" style="width:15%"><%=propLang.get("cnt_pausaFin") %></th>
			                    <th class="b-r" style="width:15%"><%=propLang.get("cnt_out") %></th>
			                    <th style="width:15%"><%=propLang.get("cnt_totale") %></th>
			                </tr>
			                    
			                 
						    <tbody id="body-table">
						    ${emp.cmp3}
						    </tbody>
						   </table>
						  </div>
						  <footer class="panel-footer text-right bg-light lter">
							
							<div class="row wrapper-xs">
								<div class="col-sm-6"></div>
								<div class="col-sm-2"><b>Tot Planning:</b></div>
								<div class="col-sm-1"><b>${emp.locality}</b></div>
								<div class="col-sm-2"><b>Tot Worked:</b></div>
								<div class="col-sm-1"><b>${emp.address}</b></div>
							</div>
							<!-- 
							<div class="row wrapper-xs">
								<div class="col-sm-6"></div>
								<div class="col-sm-2"></div>
								<div class="col-sm-1"></div>
								<div class="col-sm-2">Tot Notturno:</div>
								<div class="col-sm-1"><b>0</b></div>
							</div>
							
							<div class="row wrapper-xs">
								<div class="col-sm-6"></div>
								<div class="col-sm-2"></div>
								<div class="col-sm-1"></div>
								<div class="col-sm-2">Tot Monteore:</div>
								<div class="col-sm-1"> <b>0</b></div>
							</div>
							-->
							
						  </footer>
			              
						</section>
						
			            </c:forEach>
			            
			            <!-- Totale Ore Store per settimana -->
			            <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span><%=propLang.get("cnt_totWeek") %></span>
			              </header>
			              <div class="table-responsive">
			              	<table class="table table-striped b-t b-light">
			                	<tr>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_store") %></th>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_periodo") %></th>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_planned") %></th>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_worked") %></th>
			                    </tr>
			                    <tbody>
			                    <tr>
			                    	<td><div class="department"></div></td>
			                    	<td><div class="empperiod"></div></td>
			                    	<td><b>${totPlanned}</b></td>
			                    	<td><b>${totWorked}</b></td>
			                    </tr>
			                    </tbody>
			                </table>
			              </div>
			            </section>
			            
			            <!-- Totale per dipendente / settimana -->
			            <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span><%=propLang.get("cnt_totDip") %></span>
			              </header>
			              <div class="table-responsive">
			              	<table class="table table-striped b-t b-light">
			                	<tr>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_dip") %></th>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_periodo") %></th>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_planned") %></th>
			                    	<th width="25%" class="b-r"><%=propLang.get("cnt_worked") %></th>
			                    </tr>
			                    <tbody>
			                    <c:forEach items="${emp}" var="emp" varStatus="status">
			                    <tr>
			                    	<td>${emp.name} ${emp.surname}</td>
			                    	<td><div class="empperiod"></div></td>
			                    	<td><b>${emp.locality}</b></td>
			                    	<td><b>${emp.address}</b></td>
			                    </tr>
			                    </c:forEach>
			                    </tbody>
			                </table>
			              
			              </div>
		  				
		  				</section>
		  				
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
				        
                        <a href="control-ric.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-magnifier"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("cnt_cerca") %></span>
                        </a>
                        
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="control-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("cnt_cerca") %>">
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
	
  	$('#year').val('${year}');
  	$('#week').val('${week}');
  	$('#id_departments').val('${id_departments}');
  	
  	
  	function cambiaSettimana() {
  		
  		showLoader();
  		
  		var year = $('#year').val();
  		var id_departments = $('#id_departments').val();
  		
		javascript:window.location='clocking-ric.htm?year='+year+'&id_departments='+id_departments;
	  	window.location.href='clocking-ric.htm?year='+year+'&id_departments='+id_departments;
	}
  	
  	var iddep = $('#id_departments').val();
  	var idweek = $('#week').val();
  	
  	var periodo = $("#week option[value='"+idweek+"']").text();
  	$('.empperiod').html(periodo);
  	
  	var title = '';
 	title = $("#id_departments option[value='"+iddep+"']").text(); 	
 	$('.department').html(title);
 	
  </script>
  </body>
</html>
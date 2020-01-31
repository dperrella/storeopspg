<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"  %>
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
  <!--
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/themes/sunny/jquery-ui.css" rel="stylesheet" type="text/css"/>
  -->
  <link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.11.4/themes/black-tie/jquery-ui.css" />
  <link rel="stylesheet" href="../../js/multiselect/css/bootstrap-multiselect.css" type="text/css"/>
  
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
	.ruler {
	    position: absolute;
	    width: 20px;
	    margin-left: -10px;
	    text-align: center;
	    /*
	    margin-top: 20px;
	    */
	    color: #c0c0c0;
	    font-size:12px;
	}
	.turno {
		background:	#1ab667;
		/*box-shadow:	0 0 1em #1ab667;*/				
	}			
	.rol {
	  background: #f05050;
	  /*box-shadow:	0 0 1em #f05050;*/
	}
	.ferie {
	  background: #545ca6;
	  /*box-shadow:	0 0 1em #545ca6;*/
	}
	.malattia {
	  background: #4cb6cb;
	  /*box-shadow:	0 0 1em #4cb6cb;*/ 
	}
	.monte {
	  background: #FAD733;
	  /*box-shadow:	0 0 1em #FAD733;*/ 
	}
	.l104 {
	  background: #E8EFF0;
	  /*box-shadow:	0 0 1em #E8EFF0;*/ 
	}
	.asp {
	  background: #232C32;
	  /*box-shadow:	0 0 1em #FAD733;*/ 
	}
	.ui-slider-horizontal {
		height: 30px;
	}
	.ui-slider .ui-slider-handle { width: 10px; margin-left: -5px; height: 38px; }
  </style>
  
  <script>
  if('${richiestaApprovazione}'!='') {
	  
	  // Stampa
	  if('${richiestaApprovazione}'=='S') {
		  javascript:window.location='../planner/planner-stampa.htm?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}';
	  	  window.location.href='../planner/planner-stampa.htm?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}';   
	  }
	  // Richiesta / Approvazione / Diniego / Riapertura
	  else if('${richiestaApprovazione}'=='R' || '${richiestaApprovazione}'=='A' || '${richiestaApprovazione}'=='X'){
		  javascript:window.location='planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=${richiestaApprovazione}&tp=${tp}';
		  window.location.href='planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=${richiestaApprovazione}&tp=${tp}';
	  }
	  else {
		  javascript:window.location='cancellaTurno.jsp?id=${richiestaApprovazione}&tp=${tp}&id_departments=${id_departments}&year=${plan_year}&week=${plan_week}';
		  window.location.href='cancellaTurno.jsp?id=${richiestaApprovazione}&tp=${tp}&id_departments=${id_departments}&year=${plan_year}&week=${plan_week}';
	  }
  }
  </script>
  
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
          <header id="task_flyout" class="header bg-light dk">
              <p>Components</p>
          </header>     
          <section class="hbox stretch">     
          	
            <section>
              
           		
           		<c:if test="${nrdip>0}">
			              
	            <section class="vbox">
          		  <section class="scrollable padder-lg " id="bjax-target">
                    <div class="row row-sm">
                      
		                <h2 class="font-thin m-b">${dsfun}</h2>
		                
		                <form id="frmPlanner" name="frmPlanner" action="planner-list2.htm" method="post">
		                <input type="hidden" name="tp" value="${tp}">
		                <input type="hidden" name="azione" value="S">
		                <input type="hidden" name="tipoForm" value="I">
		                
		                <input type="hidden" id="id_departments2" name="id_departments" value=""/>
					  	<input type="hidden" id="year2" name="year" value=""/>
					  	<input type="hidden" id="week2" name="week" value=""/>
		                
		                <input type="hidden" id="richiestaApprovazione" name="richiestaApprovazione" value=""/>
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <div id="title">${dsfun}</div>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="planner-ric.htm?tp=${tp}"><%=propLang.get("tornaRic") %></a></li>
								</ul>
							</div>
						  </section>
			              
			              <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-6">
						          <label><%=propLang.get("pla_seldip") %></label>
						          <select id="id_departments" name="id_departments_vid" data-required="true" class="form-control m-t parsley-validated" disabled>
							          <option value="">-</option>
							          <c:forEach items="${dep}" var="dep" varStatus="status">
			                          <option value="${dep.id}">${dep.description}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						        
						        <div class="col-sm-2">
						          <label><%=propLang.get("pla_year") %></label>
						          <select id="year" name="year_vid" data-required="true" class="form-control m-t parsley-validated" disabled>
							          <option value="2015">2015</option>
							          <option value="2016">2016</option>
							          <option value="2017">2017</option>
			                      </select>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label><%=propLang.get("pla_week") %></label>
						          <select id="week" name="week_vid" data-required="true" class="form-control m-t parsley-validated" disabled>
							          <option value="">-</option>
							          <c:forEach items="${weeks}" var="weeks" varStatus="status">
			                          <option value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						    </div>
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
						  	
						  	<a href="../../app/popup/addPlanification.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&tp=${tp}" 
										class="getModal btn btn-default btn-lg" style="width:200px" ${dis}><%=propLang.get("btnTurni") %></a>
										
						  	
						  	<c:if test="${tp=='A'}">
						  		<c:if test="${tpApp=='0001'}">
						  			<!-- 
							  		<a href="planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=R&tp=${tp}" 
							  			style="width:200px" class="btn btn-danger btn-lg" onclick="showLoader();"><%=propLang.get("btnRigetta") %></a>
							  		<a href="planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=A&tp=${tp}" 
							  			style="width:200px" class="btn btn-info btn-lg" onclick="showLoader();"><%=propLang.get("btnApprova") %></a>
							  		-->
							  		<button type="submit" id="btnRigetta" style="width:200px" class="btn btn-danger btn-lg" ${dis} 
					      				onclick="$('#richiestaApprovazione').val('R');showLoader();" disabled><%=propLang.get("btnRigetta") %></button>	
					      			<button type="submit" id="btnApprova" style="width:200px" class="btn btn-info btn-lg" ${dis} 
					      				onclick="$('#richiestaApprovazione').val('A');showLoader();" disabled><%=propLang.get("btnApprova") %></button>
							  			
							  			
							    </c:if>
						  		<!-- 
						  		<a href="../planner/planner-stampa.htm?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}"
						  			class="btn btn-default btn-primary btn-lg" style="width:200px" target="_blank"><%=propLang.get("btnStampa") %></a>
						  		-->
						  		<button type="submit" id="btnStampa" style="width:200px" class="btn btn-primary btn-lg" ${dis} 
					      			onclick="$('#richiestaApprovazione').val('S');showLoader();" disabled><%=propLang.get("btnStampa") %></button>
					      		
						  		<button type="submit" id="btnSubmit" style="width:200px" class="btn btn-success btn-lg" 
						  			onclick="showLoader();" disabled><%=propLang.get("btnSalva") %></button>
						  	</c:if>
						  	<c:if test="${tp=='I'}">
						  	
						  		<!-- 
						  		<a href="planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=X&tp=${tp}" 
							  		style="width:200px" class="btn btn-info btn-lg" ${dis} onclick="showLoader();"><%=propLang.get("btnRichiedi") %></a>
						  		-->
						  		<button type="submit" id="btnApprova" style="width:200px" class="btn btn-info btn-lg" ${dis} 
					      			onclick="$('#richiestaApprovazione').val('X');showLoader();" disabled><%=propLang.get("btnRichiedi") %></button>
					      			
						  		<%
						  		if(session.getAttribute("tipo").toString().equals("A")) {	  			
						  		%>
						  		<c:if test="${dis=='disabled'}">
						  		<a href="planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=Y&tp=${tp}" 
							  		style="width:200px" class="btn btn-dark btn-lg" onclick="showLoader();"><%=propLang.get("btnRiapri") %></a>
						  		</c:if>
						  		<%
						  		}
						  		%>
						  		<!-- 
						  		<a href="../planner/planner-stampa.htm?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}"
						  			class="btn btn-default btn-primary btn-lg" style="width:200px" target="_blank"><%=propLang.get("btnStampa") %></a>
						  		-->
						  		<button type="submit" id="btnStampa" style="width:200px" class="btn btn-primary btn-lg" ${dis} 
					      			onclick="$('#richiestaApprovazione').val('S');showLoader();" disabled><%=propLang.get("btnStampa") %></button>
						  		
						  		<button type="submit" id="btnSubmit" style="width:200px" class="btn btn-success btn-lg" ${dis} 
					      			onclick="showLoader();" disabled><%=propLang.get("btnSalva") %></button>
					      	</c:if>
					      </footer>
					      
						</section>
						  
						<!--  Giorno -->  
						<c:forEach items="${day}" var="day" varStatus="status">
						  
						<section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span class="h3 font-thin">${day.xdtab}</span>
			                <input type="hidden" name="day" value="${day.xcdel}">
			                <input type="hidden" id="data${day.xcdel}" name="data" value="${day.cmp1}">
			              </header>
						  
						  
						  <div class="table-responsive">
			               <table class="table table-striped b-t b-light">
			                 
						    <tbody id="body-table">
						    
						    <c:forEach items="${emp}" var="emp" varStatus="status">
							  <tr>
							    
								<td class="b-r" style="width:120px; border-bottom:2px solid #77848F" rowspan="2">
									<div style="width:120px;">${emp.name}<br/>${emp.surname}<br/><b>${emp.cmp4}</b></div>
									
									<input type="hidden" name="id_employee${day.xcdel}" value="${emp.id}"/>
									<input type="hidden" id="id_departments${day.xcdel}${emp.id}" name="id_departments${day.xcdel}${emp.id}" value="${emp.cmp2}"/>
									<input type="text" id="ds_departments${day.xcdel}${emp.id}" name="ds_departments${day.xcdel}${emp.id}" 
										style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="${emp.cmp3}" disabled/>
									
									<div id="status${day.xcdel}${emp.id}"></div>
									
									<div id="periods${day.xcdel}${emp.id}"></div>
									
								</td>
								
								<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
									<a href="../../app/popup/departments_planner.jsp?iddest=id_departments${day.xcdel}${emp.id}&dsdest=ds_departments${day.xcdel}${emp.id}" 
										class="getModal btn btn-default btn-block" data-toggle="tooltip"
										data-placement="top" title="" data-original-title="<%=propLang.get("cambiaDip") %>" ${dis}>
							      		T.</a>
								</td>
								
								<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
									<a href="#" class="btn btn-default btn-block" data-toggle="tooltip" 
										onclick="confirmAddSliderLeft('${day.xcdel}', '${emp.id}', 'L')"
										data-placement="top" title="" data-original-title="<%=propLang.get("btnAddL") %>" ${dis}>
										<i class="icon-arrow-left"></i>
									</a>
									<a href="#" class="btn btn-default btn-block" data-toggle="tooltip" 
										onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
										data-placement="top" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
										<i class="icon-close"></i>
									</a>
								</td>
								
								<td colspan="18" class="b-r" style="background:#fff; height:40px">
									
									<div class="${day.xcdel}${emp.id}tempi"></div>
								    
									<div class="${day.xcdel}${emp.id}ruler ui-slider"></div>
								    
								</td>
								
								<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
									<a href="#" class="btn btn-default btn-block" data-toggle="tooltip" 
										onclick="confirmAddSliderRight('${day.xcdel}', '${emp.id}', 'R')"
										data-placement="top" title="" data-original-title="<%=propLang.get("btnAddR") %>" ${dis}>
										<i class="icon-arrow-right"></i>
									</a>
									<a href="#" class="btn btn-default btn-block" data-toggle="tooltip" 
										onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
										data-placement="top" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
										<i class="icon-close"></i>
									</a>
								</td>
								
								<td class="b-l" style="width:60px; border-bottom:2px solid #77848F" rowspan="2">
									<input type="text" name="totDip${day.xcdel}${emp.id}" id="totDip${day.xcdel}${emp.id}" 
										value="0" style="width:50px;text-align:right; background: transparent; 
										border:0px;font-weight:bold;color:#000" readonly />
								</td>
								
							  </tr>
							  <tr>
							  	<td colspan="18" class="b-r" style="height:40px; border-bottom:2px solid #77848F">
							  		<div class="${day.xcdel}${emp.id}"></div>
								</td>
							  </tr>
							  
							</c:forEach>
						    
						    </tbody>
						    <tfoot>
						      <!-- Totale x Fascia -->
						      <tr>
						    	<td colspan="3"><b><%=propLang.get("tot1") %></b></td>
						    	<td colspan="18">
						    		
						    		<div style="height:30px" class="${day.xcdel}tot1 ui-slider"></div>
						    		
						    	</td>
						    	<td></td>
						    	<td><input type="text" name="${day.xcdel}totSett1" id="${day.xcdel}totSett1" 
										value="0" style="width:50px;text-align:right; background: transparent; 
										border:0px;font-weight:bold;color:#000" readonly /></td>
						      </tr>
						      <!-- Totale x Risorsa -->
						      <tr>
						    	<td colspan="3"><b><%=propLang.get("tot2") %></b></td>
						    	<td colspan="18">
						    		
						    		<div style="height:30px" class="${day.xcdel}tot2 ui-slider"></div>
						    		
						    	</td>
						    	<td></td>
						    	<td><input type="text" name="${day.xcdel}totSett2" id="${day.xcdel}totSett2" 
										value="0" style="width:50px;text-align:right;background:transparent; 
										border:0px;font-weight:bold;color:#000" readonly /></td>
						      </tr>
						      <!-- Nomero tabella (da creare) -->
						      <tr>
						    	<td colspan="3"><b><%=propLang.get("tot3") %></b></td>
						    	<td colspan="18">
						    		
						    		<div style="height:30px" class="${day.xcdel}tot3 ui-slider"></div>
						    		
						    	</td>
						    	<td></td>
						    	<td><input type="text" name="${day.xcdel}totSett3" id="${day.xcdel}totSett3" 
										value="0" style="width:50px;text-align:right; background: transparent; 
										border:0px;font-weight:bold;color:#000" readonly /></td>
						      </tr>
						    </tfoot>
						  </table>
						  
						</div>
						
					  </section>
					  
					  </c:forEach>
					  <!--  fine Schiera giorni -->
					  
					  <div id="day1"></div>
					  <div id="day2"></div>
					  <div id="day3"></div>
					  <div id="day4"></div>
					  <div id="day5"></div>
					  <div id="day6"></div>
					  
					  <div align="center" id="dayLoader">
					  	<img src="loader.gif">
					  </div>
					  
					  
					  
					  <!--  Totale Settimana -->
					  <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span class="h3 font-thin"><%=propLang.get("tot") %></span>
			              </header>
			              <div class="table-responsive">
			               <table class="table table-striped b-t b-light">
			                 <tfoot>
						      <tr>
						      <!-- 
						    	<td style="width:60px"></td>
						      --> 	
						    	<td style="width:150px"><b><%=propLang.get("tot") %></b></td>
						    	<td style="width:50px"></td>
						    	<td style="width:50px"></td>
						    	<td colspan="18">
						    		
						    		<div style="height:30px" class="totSett ui-slider"></div>
						    		
						    	</td>
						    	<td style="width:60px"></td>
						    	<td style="width:60px"><input type="text" name="totSett" id="totSett" 
										value="0" style="width:50px;text-align:right; background: transparent; 
										border:0px;font-weight:bold;color:#000" readonly /></td>
						      </tr>
						     </tfoot>
			               </table>
			              </div>
					  </section>
					  
					  
					  <!--  Totale diviso x giorni -->
					  <!--  Totale Settimana -->
					  <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span class="h3 font-thin"><%=propLang.get("totEmp") %></span>
			              </header>
			              <div class="table-responsive">
			               <table class="table table-striped b-t b-light">
			                 <tr>
						    	<th colspan="2"><%=propLang.get("emp") %></th>
						    	<th><%=propLang.get("lun") %></th>
						    	<th><%=propLang.get("mar") %></th>
						    	<th><%=propLang.get("mer") %></th>
						    	<th><%=propLang.get("gio") %></th>
						    	<th><%=propLang.get("ven") %></th>
						    	<th><%=propLang.get("sab") %></th>
						    	<th><%=propLang.get("dom") %></th>
						    	<th><%=propLang.get("totali") %></th>
						     </tr>
						     <tbody>
			                  <c:forEach items="${emp}" var="emp" varStatus="status">
						      	<tr>
						      		<td class="b-r" style="width:50px">
							    	<span class="thumb-sm avatar pull-left m-l-xs">                        
				                        <img src="../../images/default-icon-user.png" class="dker" alt="...">
					                    </span>
								    </td>
									<td class="b-r" style="width:120px">
										<div style="width:120px;">${emp.name}<br/>${emp.surname}<br/><b>${emp.cmp4}</b></div></td>
									
									<td class="b-r">
										<input type="text" name="tot0" id="tot0${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tot1" id="tot1${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tot2" id="tot2${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tot3" id="tot3${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tot4" id="tot4${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tot5" id="tot5${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td>
										<input type="text" name="tot6" id="tot6${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td>
										<input type="text" name="totDef" id="totDef${emp.id}" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
								</tr>
							  </c:forEach>
							    <tr>
							    	<td class="b-r" colspan="2" >
										<%=propLang.get("totali") %>
									<td class="b-r">
										<input type="text" name="tottot0" id="tottot0" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tottot1" id="tottot1" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tottot2" id="tottot2" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tottot3" id="tottot3" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tottot4" id="tottot4" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td class="b-r">
										<input type="text" name="tottot5" id="tottot5" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td>
										<input type="text" name="tottot6" id="tottot6" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
									<td>
										<input type="text" name="tottotDef" id="tottotDef" value="0" style="text-align:right" class="form-control" readonly/>
									</td>
							    </tr>
						     </tbody>
			               </table>
			              </div>
					  </section>
					  
					  
					  
					  
                      </form>
                      
                      
                      </div>
                      </section>
		  				
		  			</section>
		  				
			        
                </c:if>
                
                <c:if test="${nrdip==0}">
                
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
									<li><a href="planner-ric.htm?tp=${tp}"><%=propLang.get("tornaRic") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper text-center">
						  	<h3><%=propLang.get("msgNoPlan") %></h3>
						  </div>
						  
						</section>
						
					    </div>
		              </section>
		            </section>
                
                </c:if>
                
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
				        
                        <a href="planner-ric.htm?tp=${tp}" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-settings"></i> 
                          <span class="hidden-nav-xs m-l-sm">${dsfun}</span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="planner-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("pla_cerca") %>">
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
  
  <!-- Modal "deletePeriod" -->
	<div id="deletePeriodModal" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title"><%=propLang.get("btnDel") %></h4>
	      </div>
	      <div class="modal-body">
	        <div id="contenutoDeleteModal">
	        	
	        </div>
	      </div>
	      <div class="modal-footer">
	        <div id="confermaDelete" style="float:left"></div>
	        <button type="button" class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ap_chiudi") %></button>
	      </div>
	    </div>
	
	  </div>
	</div>
  <!-- Fine Modal -->
  
  
  <!-- Modal "insertPeriod" -->
	<div id="insertPeriodModal" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title"><%=propLang.get("addPeriod") %></h4>
	      </div>
	      <div class="modal-body">
	        <div id="contenutoInsertModal">
	        	
	        </div>
	      </div>
	      <div class="modal-footer">
	        <div id="confermaInsert" style="float:left"></div>
	        <button type="button" class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ap_chiudi") %></button>
	      </div>
	    </div>
	
	  </div>
	</div>
  <!-- Fine Modal -->
  
  <script src="https://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript"></script>
  <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js" type="text/javascript" ></script>
  <script src="jquery.ui.touch-punch.min.js"></script>
  <script src="jquery.limitslider.js"></script>
  
  
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  
  <!-- Multiselect -->
  <script type="text/javascript" src="../../js/multiselect/js/bootstrap-multiselect.js"></script>

  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  <script src="../../js/bootstrap-modal.js"></script>
  
  
  
  <script>	
  	
    $(window).scroll(function(){
      if ($(this).scrollTop() > 135) {
          $('#task_flyout').addClass('fixed');
      } else {
          $('#task_flyout').removeClass('fixed');
      }
    });
  
  
  	function setMulti() {
  		$('#addPlan_day').multiselect();
  	}
  	
	//MODAL
	$('.getModal').click(function(e) {
		
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		//alert(href);
		
		/*
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		// Dipartimento
		if(href=="../../app/popup/departments.jsp") {
			href+="?roles_description=";
		}
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal mia" >' + data + '</div>').modal();
			});
		}
		*/
		
		$.get(href, function(data) {
			$('<div class="modal mia" >' + data + '</div>').modal();
		});
		
	});
	
  
  
  	
  	function loadDay(day) {
	  		
  		$.ajax({
	        url: 'planner-list2Day.htm',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'year=${plan_year}&week=${plan_week}&day='+day+'&id_departments=${id_departments}',
	        beforeSend: function () {
	        	$('#dayLoader').show();
	        },
	        success: function (data) {
	        	$('#dayLoader').hide();
	        	$("#day"+day).append(data);
	        	
	        	render(day);
	        	
	        	if(parseInt(day)<6) {
	        		loadDay(""+(parseInt(day)+1)+"");
	        	}
	        	
	        	// Dopo l'ultimo giorno abilita il tasto submit
	        	if(parseInt(day)==6) {
	        		
	        		if('${dis}'=='disabled') {
	        			$( "#btnSubmit" ).prop( "disabled", true );
	        			$( "#btnApprova" ).prop( "disabled", true );
	        			$( "#btnStampa" ).prop( "disabled", true );
	        			$( "#btnRigetta" ).prop( "disabled", true );
	        			$( "#btnApprova" ).prop( "disabled", true );
	        			
	        		}
	        		else {
	        			$( "#btnSubmit" ).prop( "disabled", false );
	        			$( "#btnApprova" ).prop( "disabled", false );
	        			$( "#btnStampa" ).prop( "disabled", false );
	        			$( "#btnRigetta" ).prop( "disabled", false );
	        			$( "#btnApprova" ).prop( "disabled", false );
	        			
	        		}
	        		
	        		/*
	        		$( "#btnSubmit" ).prop( "disabled", false );
        			$( "#btnApprova" ).prop( "disabled", false );
        			$( "#btnStampa" ).prop( "disabled", false );
        			$( "#btnRigetta" ).prop( "disabled", false );
        			$( "#btnApprova" ).prop( "disabled", false );
        			*/
	        	}
	        	
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            //alert(xhr.status);
	            //alert(thrownError);
	        }
	    });
	  	
  	}
  	
  	function render(day) {
  		
  		// =====================
  		// INIZIO GIORNO
  	  	<c:forEach items="${emp}" var="emp" varStatus="status">
  	  		
  		 	// Contenitore input per insert
  	  		var scntDiv = $("#periods"+day+"${emp.id}");
  	  		// Pulisco il contenitore
  			scntDiv.empty();
  	  		
  		    // Intervalli
  		    if(day=='0') {
  		    	${emp.lun}
  		    }
  		    if(day=='1') {
  				${emp.mar}
  		    }
  		  	if(day=='2') {
  				${emp.mer}
  		  	}
  		  	if(day=='3') {
  				${emp.gio}
  		  	}
  		  	if(day=='4') {
  				${emp.ven}
  		  	}
  		  	if(day=='5') {
  				${emp.sab}
  		  	}
  		  	if(day=='6') {
  				${emp.dom}
  		  	}
  		    
  	  		// Righello + input ore
  	  		var id='';
  	  	 	for (var i = 0; i <= 17; i+=0.5) {
  	  	      	
  	  	 		id = ""+((i+6)+1);
  	  	 		id = id.replace(/\./g,"");
  	  	 		
  	  	    	if(isInt(i)) {
  	  	    		var el = $('<label class="ruler'+day+'${emp.id}'+id+' ruler">'+((i+6)+1)+'<br/>|</label>').css('left',(i/17*100)+'%');
  	  	    		var inp = $('<input class="'+day+'${emp.id}'+id+'" type="hidden" id="'+day+'${emp.id}'+id+'" name="day'+day+''+id+'" value="0">');
  	  	    		var inp2 = $('<input class="'+day+'${emp.id}_X'+id+'" type="hidden" id="'+day+'${emp.id}_X'+id+'" name="day'+day+'_X'+id+'" value="0">');
  	  	    	}
  	  	    	else {
  	  	    		var el = $('<label class="ruler'+day+'${emp.id}'+id+' ruler">&nbsp;<br/><span style="font-size:8px">&times;</span></label>').css('left',(i/17*100)+'%');
  	  	    		var inp2 = $('<input class="'+day+'${emp.id}_X'+id+'" type="hidden" id="'+day+'${emp.id}_X'+id+'" name="day'+day+'_X'+id+'" value="0">');
  	  	  	    	
  	  	    	}
  	  	    	$( "."+day+"${emp.id}ruler" ).append(el);
  	  	    	
  	  	    	$( "."+day+"${emp.id}tempi" ).append(inp);
  	  	    	$( "."+day+"${emp.id}tempi" ).append(inp2);
  	  	    	
  	  	    }
  	  	    
  	  		// modifica il totale
  			setTot(''+day+'' , '${emp.id}');
  	  	</c:forEach>
  	  		
  	 	// Righello
  	 	for (var i = 0; i <= 17; i+=0.5) {
  	      	
  	    	if(isInt(i)) {
  	    		// Totali
  	    		var el2 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
  	    		var el3 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
  	    		var el4 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
  	    	}
  	    	else {
  	    		var el2 = $('<label id="'+day+'tot1'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
  	    		var el3 = $('<label id="'+day+'tot2'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
  	    		var el4 = $('<label id="'+day+'tot3'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
  	    		
  	    	}
  	    	$( "."+day+"tot1" ).append(el2);
  	    	$( "."+day+"tot2" ).append(el3);
  	    	$( "."+day+"tot3" ).append(el4);
  	    }
  	  	
  	 	var tot3 = $('#tot3'+day+'hidden').val();
  	 	
  	 	var res = tot3.split(",");
  	 	
  	 	for ( var i = 0, l = res.length; i < l; i++ ) {
  			
  	 		$('#'+day+'tot3'+(i+7)).html(''+res[i]+'');
  	 		
  		}
  	 	
  	 	setTot1(''+day+'');
  	 	// FINE GIORNO
  	  	// ========================
  	  		
  	 	$('.getModal'+day).click(function(e) {
  			
  			e.preventDefault();
  			var href = $(e.target).attr('href');
  			
  			$.get(href, function(data) {
  				$('<div class="modal mia" >' + data + '</div>').modal();
  			});
  			
  		});
  	  	
  	}
  	
  	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		
		loadDay("1");
		
	});
  	
  	
  	
  	
  	
  	
  	
  	
  	// Fine NUOVA GESTIONE AJAX
  	// ====================
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
  	
	
	// Totale Settimana
 	for (var i = 0; i <= 17; i+=0.5) {
      	
    	if(isInt(i)) {
    		// Totali
    		var el5 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
    		
    	}
    	else {
    		var el5 = $('<label id="totSett'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
    	}
    	$( ".totSett" ).append(el5);
    }
  	
	// =====================
	// INIZIO GIORNO
  	<c:forEach items="${day}" var="day" varStatus="status">
  	<c:forEach items="${emp}" var="emp" varStatus="status">
  		
	 	// Contenitore input per insert
  		var scntDiv = $("#periods${day.xcdel}${emp.id}");
  		// Pulisco il contenitore
		scntDiv.empty();
  		
	    // Intervalli
	    <c:if test="${day.xcdel=='0'}">
	    	${emp.lun}
	    </c:if>
		<c:if test="${day.xcdel=='1'}">
			${emp.mar}
	    </c:if>
		<c:if test="${day.xcdel=='2'}">
			${emp.mer}
	    </c:if>
		<c:if test="${day.xcdel=='3'}">
			${emp.gio}
	    </c:if>
		<c:if test="${day.xcdel=='4'}">
			${emp.ven}
	    </c:if>
		<c:if test="${day.xcdel=='5'}">
			${emp.sab}
	    </c:if>
		<c:if test="${day.xcdel=='6'}">
			${emp.dom}
	    </c:if>
	    
  		// Righello + input ore
  		var id='';
  	 	for (var i = 0; i <= 17; i+=0.5) {
  	      	
  	 		id = ""+((i+6)+1);
  	 		id = id.replace(/\./g,"");
  	 		
  	    	if(isInt(i)) {
  	    		var el = $('<label class="ruler${day.xcdel}${emp.id}'+id+' ruler">'+((i+6)+1)+'<br/>|</label>').css('left',(i/17*100)+'%');
  	    		var inp = $('<input class="${day.xcdel}${emp.id}'+id+'" type="hidden" id="${day.xcdel}${emp.id}'+id+'" name="day${day.xcdel}'+id+'" value="0">');
  	    		var inp2 = $('<input class="${day.xcdel}${emp.id}_X'+id+'" type="hidden" id="${day.xcdel}${emp.id}_X'+id+'" name="day${day.xcdel}_X'+id+'" value="0">');
  	    	}
  	    	else {
  	    		var el = $('<label class="ruler${day.xcdel}${emp.id}'+id+' ruler">&nbsp;<br/><span style="font-size:8px">&times;</span></label>').css('left',(i/17*100)+'%');
  	    		
  	    		//var inp = $('<input class="${day.xcdel}${emp.id}'+id+'" type="text" id="${day.xcdel}${emp.id}'+id+'" name="${day.xcdel}${emp.id}'+id+'" value="0">');
  	    		var inp2 = $('<input class="${day.xcdel}${emp.id}_X'+id+'" type="hidden" id="${day.xcdel}${emp.id}_X'+id+'" name="day${day.xcdel}_X'+id+'" value="0">');
  	  	    	
  	    	}
  	    	$( ".${day.xcdel}${emp.id}ruler" ).append(el);
  	    	
  	    	$( ".${day.xcdel}${emp.id}tempi" ).append(inp);
  	    	$( ".${day.xcdel}${emp.id}tempi" ).append(inp2);
  	    	
  	    }
  	    
  		// modifica il totale
		setTot('${day.xcdel}' , '${emp.id}');
  	</c:forEach>
  		
 	// Righello
 	for (var i = 0; i <= 17; i+=0.5) {
      	
    	if(isInt(i)) {
    		// Totali
    		var el2 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
    		var el3 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
    		var el4 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
    	}
    	else {
    		var el2 = $('<label id="${day.xcdel}tot1'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
    		var el3 = $('<label id="${day.xcdel}tot2'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
    		var el4 = $('<label id="${day.xcdel}tot3'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
    		
    	}
    	$( ".${day.xcdel}tot1" ).append(el2);
    	$( ".${day.xcdel}tot2" ).append(el3);
    	$( ".${day.xcdel}tot3" ).append(el4);
    }
  	
 	
 	var tot3 = "${day.cmp2}";
 	var res = tot3.split(",");
 	
 	for ( var i = 0, l = res.length; i < l; i++ ) {
		
 		$('#${day.xcdel}tot3'+(i+7)).html(''+res[i]+'');
 		
	}
 	
 	setTot1('${day.xcdel}');
 	
  	</c:forEach>
  	// FINE GIORNO
  	// ========================
  		
 	
  		
  		
  	
  		
  		
  		
  		
  	
 	// ========================
  	// Aggiunta Slider (Sinistra)
  	function confirmAddSliderLeft(day , idemployee, LR) {
  		
 		var sValues = '';
 		try {
 			sValues = $("."+day+""+idemployee).limitslider("values") ;
 		}
 		catch(err) {
 			//alert(err.message);
 		}
		
 		//Se sValues.length>0 Aggiunge il periodo
 		if(sValues!='') {
	  		
	  		// Ultimo valore schiera
	  		var lastValues = '';
	  		for ( var i = 0, l = sValues.length; i < l; i+=2 ) {
	  			if(i==0) {
	  		    	lastValues = sValues[ i ]+','+sValues[ i+1 ];
	  			}
	  		}
			
	  		var val = lastValues.split(',');
	  		// Ultima ora turni
	  		var ini = val[0];
	  		// Sottraggo 1 ora all'inizio
	  		var newIni = parseFloat(ini)-2;
	  		var newFin = parseFloat(newIni)+1;
	  		
	  		// Se la nuova ora inizio supera 24 da errore
	  		if(parseFloat(newIni)<7) {
	  			alert('<%=propLang.get("errAddPeriod2") %> '+ini);
	  		}
	  		else {
	  			
	  			var lista = newIni+','+newFin+','+sValues.toString();
	  			var temp = new Array();
	  			temp = lista.split(",");
		  		for (a in temp ) {
	  			    temp[a] = parseFloat(temp[a]);
	  			}
	  			
		  		// Distrugge lo slider
	  			$( "."+day+""+idemployee ).limitslider( "destroy" );
	  			
	  			// Ricrea lo slider con nuovo valore
	  			$(function() {
		  	  		$( "."+day+""+idemployee ).limitslider({
		  				values:		temp ,
		  				showRanges: true,
		  				step:		0.5,
		  				gap:		0,
		  				min:		7,
		  				max:		24,
		  				label:		false,
		  				change: function( event, ui ) {
		  					reloadInputs(day , idemployee);
						},
		  				ranges:		[false, { styleClass: getCss(day , idemployee, '1') }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false]
		  		   	});
	  			});
	  			
	  			// Ricarica gli inputs
	  			reloadInputs(day , idemployee);
	  			
	  		}
	  		
 		}
 		// Altrimenti crea lo slider con valori di default
 		else {
 			
 			// Ricrea lo slider con nuovo valore
  			$(function() {
	  	  		$( "."+day+""+idemployee ).limitslider({
	  				values:		[7 , 8],
	  				showRanges: true,
	  				step:		0.5,
	  				gap:		0,
	  				min:		7,
	  				max:		24,
	  				label:		false,
	  				change: function( event, ui ) {
	  					reloadInputs(day , idemployee);
					},
	  				ranges:		[false, { styleClass: getCss(day , idemployee, '1') }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false]
	  		   	});
  			});
  			
  			// Ricarica gli inputs
  			reloadInputs(day , idemployee);
 			
 		}
		
  	}
 	
 	// ========================
  	// Aggiunta Slider (Destra)
  	function confirmAddSliderRight(day , idemployee, LR) {
  		
 		var sValues = '';
 		try {
 			sValues = $("."+day+""+idemployee).limitslider("values") ;
 		}
 		catch(err) {
 			//alert(err.message);
 		}
		
 		//Se sValues.length>0 Aggiunge il periodo
 		if(sValues!='') {
	  		
	  		// Ultimo valore schiera
	  		var lastValues = '';
	  		for ( var i = 0, l = sValues.length; i < l; i+=2 ) {
	  		    lastValues = sValues[ i ]+','+sValues[ i+1 ];
	  		}
			
	  		var val = lastValues.split(',');
	  		// Ultima ora turni
	  		var fin = val[1];
	  		// Aggiungo 1 ora
	  		var newIni = parseFloat(fin)+1;
	  		var newFin = parseFloat(newIni)+1;
	  		
	  		// Se la nuova ora inizio supera 24 da errore
	  		if(parseFloat(newFin)>24) {
	  			alert('<%=propLang.get("errAddPeriod1") %> '+fin);
	  		}
	  		else {
	  			
	  			var lista = sValues.toString()+','+newIni+','+newFin;
	  			var temp = new Array();
	  			temp = lista.split(",");
		  		for (a in temp ) {
	  			    temp[a] = parseFloat(temp[a]);
	  			}
	  			
		  		// Distrugge lo slider
	  			$( "."+day+""+idemployee ).limitslider( "destroy" );
	  			
	  			// Ricrea lo slider con nuovo valore
	  			$(function() {
		  	  		$( "."+day+""+idemployee ).limitslider({
		  				values:		temp ,
		  				showRanges: true,
		  				step:		0.5,
		  				gap:		0,
		  				min:		7,
		  				max:		24,
		  				label:		false,
		  				change: function( event, ui ) {
		  					reloadInputs(day , idemployee);
						},
		  				ranges:		[false, { styleClass: getCss(day , idemployee, '1') }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false]
		  		   	});
	  			});
	  			
	  			// Ricarica gli inputs
	  			reloadInputs(day , idemployee);
	  			
	  		}
	  		
 		}
 		// Altrimenti crea lo slider con valori di default
 		else {
 			
 			// Ricrea lo slider con nuovo valore
  			$(function() {
	  	  		$( "."+day+""+idemployee ).limitslider({
	  				values:		[7 , 8],
	  				showRanges: true,
	  				step:		0.5,
	  				gap:		0,
	  				min:		7,
	  				max:		24,
	  				label:		false,
	  				change: function( event, ui ) {
	  					reloadInputs(day , idemployee);
					},
	  				ranges:		[false, { styleClass: getCss(day , idemployee, '1') }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false]
	  		   	});
  			});
  			
  			// Ricarica gli inputs
  			reloadInputs(day , idemployee);
 			
 		}
		
  	}
 	
 	function getCss(day , idemployee, nrturno) {
 		
 		var turno = '';
 		
 		var year = $('#year').val();
		var week = $('#week').val();
		var dataIta = $('#data'+day).val();
		
  		// Controlla che il turno non sia un ROL, una FERIE o una MALATTIA
		$.ajax({
	        url: 'chkTurnoCss.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: false,
	        data: 'year='+year+'&week='+week+'&day='+day+'&id_employee='+idemployee+'&req_data='+dataIta+'&nr='+nrturno,
	        success: function (data) {
	        	turno = data;
	        },
	        error: function () {
	        	alert('Errore! si e\' verificato un errore.');
	        }
	    });
 		
 		return turno;
 		
 	}
 	
  	// ========================
  	// Apertura Modal Rimozione Slider
	function confirmRemoveSlider(day , idemployee) {
  		
		var year = $('#year').val();
		var week = $('#week').val();
		var dataIta = $('#data'+day).val();
		
  		var sValues = $("."+day+""+idemployee).limitslider("values") ;
  		
  		$('#contenutoDeleteModal').empty();
  		$('#contenutoDeleteModal').append('<p><%=propLang.get("selPeriodDel") %>:</p>');
  		 
  		for ( var i = 0, l = sValues.length; i < l; i+=2 ) {
  		    
  			// Controlla che il turno non sia un ROL, una FERIE o una MALATTIA
  			$.ajax({
		        url: 'chkTurnoEdit.jsp',
		        type: 'POST',
		        dataType: 'html',
		        async: false,
		        data: 'year='+year+'&week='+week+'&day='+day+'&id_employee='+idemployee+'&req_data='+dataIta+'&ini='+sValues[ i ]+'&fin='+sValues[ i+1 ],
				beforeSend: function () {
		        	//showLoader();
		        },
		        success: function (data) {
		        	//hideLoader();
		        	// E' un turno
		        	if($.trim(data)=='') {
			        	// Costrusco il contenuto della Modal
			  		    $('#contenutoDeleteModal').append('<div class="radio i-checks"><label><input type="radio" name="turno" value="'+sValues[ i ]+','+sValues[ i+1 ]+'"><i></i> Turno ' + sValues[ i ] + ' - ' + sValues[ i+1 ]+'</label></div>');
		        	}
		        	// Non è un turno
		        	else {
		        	    $('#contenutoDeleteModal').append('<div class="radio i-checks"><label>&nbsp; Periodo (NO TURNO) ' + sValues[ i ] + ' - ' + sValues[ i+1 ]+' &nbsp;&nbsp;&nbsp;<a href="#" onclick="cancellaPeriodo(\''+$.trim(data)+'\')" class="btn btn-danger btn-xs">Cancella</a></label></div>');
			        }
		        },
		        error: function () {
		        	hideLoader();
		        	alert('Errore! si e\' verificato un errore.');
		        }
		    });
  			 
  		}
  		
  		$('#confermaDelete').empty();
  		$('#confermaDelete').append('<a href="#" onclick="removeSlider(\''+day+'\', \''+idemployee+'\')" class="btn btn-default btn-success btn-lg" ><%=propLang.get("btnConferma") %></a>');
  		
  		// quale periodo vuoi eliminare?
  		$('#deletePeriodModal').modal('show');
  		
  	}
	
  	// ========================
  	// Rimozione Slider
	function removeSlider(day, idemployee) {		
		//alert(day+' '+idemployee);
		
		var sLista = '';
		var nr = 0;
		$("input[name='turno']").each(function () {
			
			// Costruisce la lista
			if($(this).is(':checked')) {
				// Scarta il record
			}
			else {
				nr+=1;
				
				sLista+=""+$(this).val()+",";
			}
			
		});
		
		// Se la lista contiene almeno un elemento va avanti
		if(nr>=1) {
			
			// Rimuove ultimo carattere
			sLista = sLista.substring(0, sLista.length - 1);
			// Trasforma la lista in array
			var temp = new Array();
  			temp = sLista.split(",");
	  		for (a in temp ) {
  			    temp[a] = parseFloat(temp[a]);
  			}
			// Distrugge lo slider
			$( "."+day+""+idemployee ).limitslider( "destroy" );
			// Ricrea lo slider con nuovo valore
			$(function() {
		  		$( "."+day+""+idemployee ).limitslider({
					values:		temp,
					showRanges: true,
					step:		0.5,
					gap:		0,
					min:		7,
					max:		24,
					label:		false,
					change: function( event, ui ) {
	  					reloadInputs(day , idemployee);
					},
					ranges:		[false, { styleClass: getCss(day , idemployee, '1') }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false]
			   	});
			});
			
			// Ricarica gli inputs
  			reloadInputs(day , idemployee);
			
		}
		else {
			// Distrugge lo slider
			$( "."+day+""+idemployee ).limitslider( "destroy" );
			$( "."+day+""+idemployee ).empty();
			$('input[name=p_dati'+day+''+idemployee+']').remove();
			
			setTot(''+day+'' , ''+idemployee+'');
		}
		
		// Chiude la modal
  		$('#deletePeriodModal').modal('hide');
		
	}
  	
  	function reloadInputs(day, idemployee) {
  		
  		var year = $('#year').val();
		var week = $('#week').val();
		var dataIta = $('#data'+day).val();
		
  		var lista = $("."+day+""+idemployee).limitslider("values");
  		
  		// Contenitore input per insert
	 	var scntDiv = $("#periods"+day+""+idemployee);
	 	// Pulisco il contenitore
		scntDiv.empty();
	 	var periodid = 0;
		for ( var i = 0, l = lista.length; i < l; i+=2 ) {
			periodid+=1;
			
			// Bisogna scartare i ROL, le MALATTIE e le FERIE// Controlla che il turno non sia un ROL, una FERIE o una MALATTIA
  			$.ajax({
		        url: 'chkTurnoEdit.jsp',
		        type: 'POST',
		        dataType: 'html',
		        async: false,
		        data: 'year='+year+'&week='+week+'&day='+day+'&id_employee='+idemployee+'&req_data='+dataIta+'&ini='+lista[ i ]+'&fin='+lista[ i+1 ],
				beforeSend: function () {
		        	//showLoader();
		        },
		        success: function (data) {
		        	//hideLoader();
		        	if($.trim(data)=='') {
			        	
		        		$('<input type="hidden" id="p_dati'+day+''+idemployee+'_'+periodid+'" name="p_dati'+day+''+idemployee+'" value="'+lista[ i ]+','+lista[ i+1 ]+'" />').appendTo(scntDiv);
			  		    
		        	}
		        	
		        },
		        error: function () {
		        	hideLoader();
		        	alert('Errore! si e\' verificato un errore.');
		        }
		    });
			
		}
		
		// modifica il totale
		setTot(''+day+'' , ''+idemployee+'');
  		
  	}
  	
	function setTot(day , idemployee) {
  		
  		var name="p_dati"+day+idemployee;
  		var tot = 0.0;
  		
  		// svuota gli input
  		for(var i=7; i<24; i+=0.5) {
				
			id=""+i;
			id=id.replace(/\./g,"");
			
			// Totale Normale
			$('#'+day+''+idemployee+''+id).val('0');
			
			// Totale "Strano"
			$('#'+day+''+idemployee+'_X'+id).val('0');
			
			// Ruler
			$('.ruler'+day+''+idemployee+''+id).css("background-color", "transparent");
			$('.ruler'+day+''+idemployee+''+id).css("font-weight", "normal");
			$('.ruler'+day+''+idemployee+''+id).css("color", "#AFBDC8");
				
		}
  		
  		$('.ruler'+day+''+idemployee+'24').css("background-color", "transparent");
		$('.ruler'+day+''+idemployee+'24').css("font-weight", "normal");
		$('.ruler'+day+''+idemployee+'24').css("color", "#AFBDC8");
  		
  		$("input[name="+name+"]").each(function(){
  		    
  			var res = this.value.split(",");	
  			tot+= parseFloat(res[1])-parseFloat(res[0]);
  			
  			// Per ogni intervallo popola gli input
  			var id = "";
  			var sI = "";
  			var sI2 = "";
  			
  			var val = 0;
  			var val2 = 0;
  			
  			for(var i=parseFloat(res[0]); i<parseFloat(res[1]); i+=0.5) {
  				
  				sI = ""+i;
  				sI = sI.replace(/\./g,",");
  				sI = sI.split(",");
  				sI = sI[0];
  				
  				sI2 = ""+i;
  				sI2 = sI2.replace(/\./g,"");
  				
  				val = parseFloat($('#'+day+''+idemployee+''+sI).val());
  				val = parseFloat(val)+0.5;
  				$('#'+day+''+idemployee+''+sI).val(val);
  				
  				// -----------
  				
  				$('#'+day+''+idemployee+'_X'+sI2).val('0.5');
  				
  				// -----------
  				
  				//$('.ruler'+day+''+idemployee+''+sI2).css("background-color", "#8aeaba");
  				$('.ruler'+day+''+idemployee+''+sI2).css("font-weight", "bold");
  				$('.ruler'+day+''+idemployee+''+sI2).css("color", "#000");
  				
  				
  				// Aggiunge l'ultimo record al righello
  				var ultrec = parseFloat(i)+0.5;
  				var ultrec2 = ""+ultrec;
  				ultrec2 = ultrec2.replace(/\./g,"");
  				
  				//alert(ultrec2);
  				
  				//$('.ruler'+day+''+idemployee+''+ultrec2).css("background-color", "#8aeaba");
  				$('.ruler'+day+''+idemployee+''+ultrec2).css("font-weight", "bold");
  				$('.ruler'+day+''+idemployee+''+ultrec2).css("color", "#000");
  			}
  			
  			
	  		
		
  		});
  		
  		// ====================
  		// Totali Giorno
  		var tot1 = 0;
  		for(var i=7; i<24; i++) {
			
  			var valore = 0;
  			
  			$("input[name=day"+day+''+i+"]").each(function(){
  				
  				valore=valore+parseFloat(this.value);
  				tot1=tot1+parseFloat(this.value);
  				
  			});
  			
  			$('#'+day+'tot1'+i).html(''+valore+'');
			
		}
  		
  		$('#'+day+'totSett1').val(tot1);
  		
  		
  		
  		// ====================
  		// Totali Strani
  		var tot2 = 0;
  		
		for(var i=7; i<24; i+=1) {
  			
  			var valore = 0;
  			
  			valore = parseFloat($('#'+day+'tot1'+i).html());
  			
  			valore = Math.ceil(valore);
  			tot2=tot2+parseFloat(valore);
  			
  			$('#'+day+'tot2'+i).html(''+valore+'');
  			
  		}
  		
		$('#'+day+'totSett2').val(tot2);
		
		// ====================
		// Differenze con tot3
		var val1 = 0;
		var val2 = 0;
		
		for(var i=7; i<24; i+=1) {
			
			val1 = parseFloat($('#'+day+'tot2'+i).html());
			val2 = parseFloat($('#'+day+'tot3'+i).html());
			
			if(parseFloat(val1)!=parseFloat(val2)) {
				$('#'+day+'tot3'+i).css({"color": "#f05050", "font-weight": "bold"});
			}
			
		}
		// ====================
  		
  		
  		/*
  		// ====================
  		// Totali Strani
  		var sI2 = 0;
  		var tot2 = 0;

  		for(var i=7; i<24; i+=1) {
			
  			sI2 = ""+i;
			sI2 = sI2.replace(/\./g,"");
				
  			var valore = 0;
  			
  			$("input[name=day"+day+'_X'+sI2+"]").each(function(){
  				
  				if(parseFloat(this.value)>0) {
  					valore=parseFloat(valore)+parseFloat(this.value)+0.5;
  					tot2=tot2+parseFloat(this.value)+0.5;
  				}
  				else {
  					valore=parseFloat(valore)+parseFloat(this.value);
  					tot2=tot2+parseFloat(this.value);
  				}
  				
  			});
  			
  			// mezz'ora
  			if(parseFloat(valore)==0) {
				$("input[name=day"+day+'_X'+sI2+"5]").each(function(){
	  				
	  				if(parseFloat(this.value)>0) {
	  					valore=parseFloat(valore)+parseFloat(this.value)+0.5;
	  					tot2=tot2+parseFloat(this.value)+0.5;
	  				}
	  				else {
	  					valore=parseFloat(valore)+parseFloat(this.value);
	  					tot2=tot2+parseFloat(this.value);
	  				}
	  				
	  			});
  			}
			
  			$('#'+day+'tot2'+i).html(''+valore+'');
  			
  			// blank valore
  			valore = 0;
		}
  		$('#'+day+'totSett2').val(tot2);
  		// ====================
  		*/
  		
  		
  		
  		$('#tottot'+day+'').val(tot1);
  		
  		// ======================
  			
  		// Totali Settimana
  		var totSett = 0;
		
		// Fascia oraria 
		var totSett7 = 0;
		var totSett8 = 0;
		var totSett9 = 0;
		var totSett10 = 0;
		var totSett11 = 0;
		var totSett12 = 0;
		var totSett13 = 0;
		var totSett14 = 0;
		var totSett15 = 0;
		var totSett16 = 0;
		var totSett17 = 0;
		var totSett18 = 0;
		var totSett19 = 0;
		var totSett20 = 0;
		var totSett21 = 0;
		var totSett22 = 0;
		var totSett23 = 0;
		
  		// Giorno
  		for(var t=0; t<7; t++) {
  			// Ora
  			for(var i=7; i<24; i++) {
  				
  				var valore = 0;
  				
  				$("input[name=day"+t+''+i+"]").each(function(){
  	  				
  	  				valore=valore+parseFloat(this.value);
  	  				totSett=totSett+parseFloat(this.value);
  	  				
  	  				if(i==7) {
  	  					totSett7=totSett7+parseFloat(this.value);
  	  				}
  	  				if(i==8) {
  	  					totSett8=totSett8+parseFloat(this.value);
	  				}
  	  				if(i==9) {
  	  					totSett9=totSett9+parseFloat(this.value);
	  				}
  	  				if(i==10) {
  	  					totSett10=totSett10+parseFloat(this.value);
	  				}
  	  				if(i==11) {
  	  					totSett11=totSett11+parseFloat(this.value);
	  				}
  	  				if(i==12) {
  	  					totSett12=totSett12+parseFloat(this.value);
	  				}
	  	  			if(i==13) {
	  	  				totSett13=totSett13+parseFloat(this.value);
	  				}
		  	  		if(i==14) {
		  	  			totSett14=totSett14+parseFloat(this.value);
	  				}
			  	  	if(i==15) {
			  	  		totSett15=totSett15+parseFloat(this.value);
	  				}
		  			if(i==16) {
		  				totSett16=totSett16+parseFloat(this.value);
	  				}
	  				if(i==17) {
	  					totSett17=totSett17+parseFloat(this.value);
	  				}
	  	  			if(i==18) {
	  	  				totSett18=totSett18+parseFloat(this.value);
	  				}
		  	  		if(i==19) {
		  	  			totSett19=totSett19+parseFloat(this.value);
	  				}
			  	  	if(i==20) {
			  	  		totSett20=totSett20+parseFloat(this.value);
	  				}
			  	  	if(i==21) {
			  	  		totSett21=totSett21+parseFloat(this.value);
	  				}
			  	 	if(i==22) {
			  	 		totSett22=totSett22+parseFloat(this.value);
	  				}
					if(i==23) {
						totSett23=totSett23+parseFloat(this.value);
	  				}
					if(i==24) {
						totSett24=totSett24+parseFloat(this.value);
	  				}
  	  				
  	  			});
  				
  			}
  			
  		}
  		
  		$('#totSett7').html(''+totSett7+'');
  		$('#totSett8').html(''+totSett8+'');
  		$('#totSett9').html(''+totSett9+'');
  		$('#totSett10').html(''+totSett10+'');
  		$('#totSett11').html(''+totSett11+'');
  		$('#totSett12').html(''+totSett12+'');
  		$('#totSett13').html(''+totSett13+'');
  		$('#totSett14').html(''+totSett14+'');
  		$('#totSett15').html(''+totSett15+'');
  		$('#totSett16').html(''+totSett16+'');
  		$('#totSett17').html(''+totSett17+'');
  		$('#totSett18').html(''+totSett18+'');
  		$('#totSett19').html(''+totSett19+'');
  		$('#totSett20').html(''+totSett20+'');
  		$('#totSett21').html(''+totSett21+'');
  		$('#totSett22').html(''+totSett22+'');
  		$('#totSett23').html(''+totSett23+'');
  		
  		$('#totSett').val(totSett);
  		
  		var valday = 0;
  		
  		$('#totDip'+day+''+idemployee+'').val(tot);
  		
  		valday = $('#totDip'+day+idemployee).val();
  		$('#tot'+day+idemployee).val(valday);
  		
  		
  		valTot = parseFloat($('#tot0'+idemployee).val()) + 
  				 parseFloat($('#tot1'+idemployee).val()) + 
  				 parseFloat($('#tot2'+idemployee).val()) + 
  				 parseFloat($('#tot3'+idemployee).val()) + 
  				 parseFloat($('#tot4'+idemployee).val()) + 
  				 parseFloat($('#tot5'+idemployee).val()) + 
  				 parseFloat($('#tot6'+idemployee).val());
  		
  		$('#totDef'+idemployee).val(valTot);
  		
  		
  		
  		
  		valTotTot = parseFloat($('#tottot0').val()) + 
			 parseFloat($('#tottot1').val()) + 
			 parseFloat($('#tottot2').val()) + 
			 parseFloat($('#tottot3').val()) + 
			 parseFloat($('#tottot4').val()) + 
			 parseFloat($('#tottot5').val()) + 
			 parseFloat($('#tottot6').val());
	
		$('#tottotDef').val(valTotTot);
  	
	}
	
	function setTot1(day) {
		
		var tot1 = 0;
		for(var i=7; i<24; i++) {
			
  			var valore = 0;
  			
  			$("input[name=day"+day+''+i+"]").each(function(){
  				
  				valore=valore+parseFloat(this.value);
  				tot1=tot1+parseFloat(this.value);
  					
  			});
  			
  			$('#'+day+'tot1'+i).html(''+valore+'');
  			$('#'+day+'totSett1').val(tot1);
			
		}
  		
		
		
		// ====================
  		// Totali Strani
  		var tot2 = 0;
  		
		for(var i=7; i<24; i+=1) {
  			
  			var valore = 0;
  			
  			valore = parseFloat($('#'+day+'tot1'+i).html());
  			
  			valore = Math.ceil(valore);
  			tot2=tot2+parseFloat(valore);
  			
  			$('#'+day+'tot2'+i).html(''+valore+'');
  			
  		}
  		
		$('#'+day+'totSett2').val(tot2);
		
		// ====================
		// Differenze con tot3
		var val1 = 0;
		var val2 = 0;
		
		for(var i=7; i<24; i+=1) {
			
			val1 = parseFloat($('#'+day+'tot2'+i).html());
			val2 = parseFloat($('#'+day+'tot3'+i).html());
			
			if(parseFloat(val1)!=parseFloat(val2)) {
				$('#'+day+'tot3'+i).css({"color": "#f05050", "font-weight": "bold"});
			}
			
		}
		// ====================
		
		
		/*
		// ====================
  		// Totali Strani
  		var sI2 = 0;
  		var tot2 = 0;

  		for(var i=7; i<24; i+=1) {
			
  			sI2 = ""+i;
			sI2 = sI2.replace(/\./g,"");
				
  			var valore = 0;
  			
  			$("input[name=day"+day+'_X'+sI2+"]").each(function(){
  				
  				if(parseFloat(this.value)>0) {
  					valore=parseFloat(valore)+parseFloat(this.value)+0.5;
  					tot2=tot2+parseFloat(this.value)+0.5;
  				}
  				else {
  					valore=parseFloat(valore)+parseFloat(this.value);
  					tot2=tot2+parseFloat(this.value);
  				}
  				
  			});
  			
  			// mezz'ora
			$("input[name=day"+day+'_X'+sI2+"5]").each(function(){
  				
  				if(parseFloat(this.value)>0) {
  					valore=parseFloat(valore)+parseFloat(this.value)+0.5;
  					tot2=tot2+parseFloat(this.value)+0.5;
  				}
  				else {
  					valore=parseFloat(valore)+parseFloat(this.value);
  					tot2=tot2+parseFloat(this.value);
  				}
  				
  			});
			
  			$('#'+day+'tot2'+i).html(''+valore+'');
  			
  			// blank valore
  			valore = 0;
		}
  		$('#'+day+'totSett2').val(tot2);
  		// ====================
  		*/
		
		
		
		
		
		
		
		var tottot1 = 0;
		for(var i=7; i<24; i++) {
			
  			tottot1 = $('#'+day+'totSett1').val();
  			
  			$('#tottot'+day).val(tottot1);
			
		}
		
		
		// Totali Settimana
  		var totSett = 0;
		
		// Fascia oraria 
		var totSett7 = 0;
		var totSett8 = 0;
		var totSett9 = 0;
		var totSett10 = 0;
		var totSett11 = 0;
		var totSett12 = 0;
		var totSett13 = 0;
		var totSett14 = 0;
		var totSett15 = 0;
		var totSett16 = 0;
		var totSett17 = 0;
		var totSett18 = 0;
		var totSett19 = 0;
		var totSett20 = 0;
		var totSett21 = 0;
		var totSett22 = 0;
		var totSett23 = 0;
		
  		// Giorno
  		for(var t=0; t<7; t++) {
  			// Ora
  			for(var i=7; i<24; i++) {
  				
  				var valore = 0;
  				
  				$("input[name=day"+t+''+i+"]").each(function(){
  	  				
  	  				valore=valore+parseFloat(this.value);
  	  				totSett=totSett+parseFloat(this.value);
  	  				
  	  				if(i==7) {
  	  					totSett7=totSett7+parseFloat(this.value);
  	  				}
  	  				if(i==8) {
  	  					totSett8=totSett8+parseFloat(this.value);
	  				}
  	  				if(i==9) {
  	  					totSett9=totSett9+parseFloat(this.value);
	  				}
  	  				if(i==10) {
  	  					totSett10=totSett10+parseFloat(this.value);
	  				}
  	  				if(i==11) {
  	  					totSett11=totSett11+parseFloat(this.value);
	  				}
  	  				if(i==12) {
  	  					totSett12=totSett12+parseFloat(this.value);
	  				}
	  	  			if(i==13) {
	  	  				totSett13=totSett13+parseFloat(this.value);
	  				}
		  	  		if(i==14) {
		  	  			totSett14=totSett14+parseFloat(this.value);
	  				}
			  	  	if(i==15) {
			  	  		totSett15=totSett15+parseFloat(this.value);
	  				}
		  			if(i==16) {
		  				totSett16=totSett16+parseFloat(this.value);
	  				}
	  				if(i==17) {
	  					totSett17=totSett17+parseFloat(this.value);
	  				}
	  	  			if(i==18) {
	  	  				totSett18=totSett18+parseFloat(this.value);
	  				}
		  	  		if(i==19) {
		  	  			totSett19=totSett19+parseFloat(this.value);
	  				}
			  	  	if(i==20) {
			  	  		totSett20=totSett20+parseFloat(this.value);
	  				}
			  	  	if(i==21) {
			  	  		totSett21=totSett21+parseFloat(this.value);
	  				}
			  	 	if(i==22) {
			  	 		totSett22=totSett22+parseFloat(this.value);
	  				}
					if(i==23) {
						totSett23=totSett23+parseFloat(this.value);
	  				}
					if(i==24) {
						totSett24=totSett24+parseFloat(this.value);
	  				}
  	  				
  	  			});
  				
  			}
  			
  		}
  		
  		$('#totSett7').html(''+totSett7+'');
  		$('#totSett8').html(''+totSett8+'');
  		$('#totSett9').html(''+totSett9+'');
  		$('#totSett10').html(''+totSett10+'');
  		$('#totSett11').html(''+totSett11+'');
  		$('#totSett12').html(''+totSett12+'');
  		$('#totSett13').html(''+totSett13+'');
  		$('#totSett14').html(''+totSett14+'');
  		$('#totSett15').html(''+totSett15+'');
  		$('#totSett16').html(''+totSett16+'');
  		$('#totSett17').html(''+totSett17+'');
  		$('#totSett18').html(''+totSett18+'');
  		$('#totSett19').html(''+totSett19+'');
  		$('#totSett20').html(''+totSett20+'');
  		$('#totSett21').html(''+totSett21+'');
  		$('#totSett22').html(''+totSett22+'');
  		$('#totSett23').html(''+totSett23+'');
  		
  		
  		$('#totSett').val(totSett);
  		
  		
  		var valday = 0;
  		var valTot = 0;
  		<c:forEach items="${emp}" var="emp" varStatus="status">
  		<c:forEach items="${day}" var="day" varStatus="status">
  		
  		valday = $('#totDip${day.xcdel}${emp.id}').val();
  		$('#tot${day.xcdel}${emp.id}').val(valday);
  		
  		</c:forEach>
  		
  		valTot = parseFloat($('#tot0${emp.id}').val()) + 
				 parseFloat($('#tot1${emp.id}').val()) + 
				 parseFloat($('#tot2${emp.id}').val()) + 
				 parseFloat($('#tot3${emp.id}').val()) + 
				 parseFloat($('#tot4${emp.id}').val()) + 
				 parseFloat($('#tot5${emp.id}').val()) + 
				 parseFloat($('#tot6${emp.id}').val());
		$('#totDef${emp.id}').val(valTot);
		
  		</c:forEach>
  		
  		
  		
  		valTotTot = parseFloat($('#tottot0').val()) + 
			parseFloat($('#tottot1').val()) + 
			parseFloat($('#tottot2').val()) + 
			parseFloat($('#tottot3').val()) + 
			parseFloat($('#tottot4').val()) + 
			parseFloat($('#tottot5').val()) + 
			parseFloat($('#tottot6').val());
	
		$('#tottotDef').val(valTotTot);
  		
	}
	
  	function isInt(n){
  	    return Number(n) === n && n % 1 === 0;
  	}

  	function isFloat(n){
  	    return Number(n) === n && n % 1 !== 0;
  	}
  	
  	// Aggiungi slider
  	function addSlider(day , idemployee) {
  		
  	}
  	
  	$('#id_departments').val('${id_departments}');
  	$('#year').val('${plan_year}');
  	$('#week').val('${plan_week}');
  	
  	$('#id_departments2').val('${id_departments}');
  	$('#year2').val('${plan_year}');
  	$('#week2').val('${plan_week}');
  	
  	// imposta il dipartimento 'diverso'
	function setDepartment(day, idemployee, iddepartment, description) {
  		
  		$('#id_departments'+day+''+idemployee+'').val(iddepartment);
  		$('#ds_departments'+day+''+idemployee+'').val(description);
		
  	}
  	
  	function setStatus(day, idemployee, status) {
  		$('#status'+day+''+idemployee+'').html(status);
  	}
  	
  	${newDepart}
  	
  	// aggiunge pianificazione da modal
  	function addPlanification() {
  		
  		var id_departments = $('#id_departments').val();
  		var year = $('#year').val();
		var week = $('#week').val();
  		var day = $('#addPlan_day').val();
  		var ora_ini = $('#addPlan_ora_ini').val();
  		var ora_fin = $('#addPlan_ora_fin').val();
  		var id_planification_type = $('#addPlan_id_planification_type').val();
  		var id_employee = $('#addPlan_id_employee').val();
  		
  		if(day==null)
  			day = "";
  		
  		$("input[name='addPlan_day']").each(function () {
  			day=day+""+this.value+",";
  		});
  		
  		var chkErr = "";
  		var msgErr = "";
  		if(day=='') {
  			chkErr=chkErr+"S";
  			msgErr=msgErr+"- <%=propLang.get("msg1") %>\n";
  		}
  		if(ora_ini=='') {
  			chkErr=chkErr+"S";
  			msgErr=msgErr+"- <%=propLang.get("msg2") %>\n";
  		}
  		if(ora_fin=='') {
  			chkErr=chkErr+"S";
  			msgErr=msgErr+"- <%=propLang.get("msg3") %>\n";
  		}
  		if(id_planification_type=='') {
  			chkErr=chkErr+"S";
  			msgErr=msgErr+"- <%=propLang.get("msg4") %>\n";
  		}
  		if(id_employee=='') {
  			chkErr=chkErr+"S";
  			msgErr=msgErr+"- <%=propLang.get("msg5") %>\n";
  		}
  		
  		if(chkErr=='') {
  			
  			$.ajax({
  		        url: 'addTurno.jsp',
  		        type: 'POST',
  		        dataType: 'html',
  		        async: false,
  		        data: 'id_departments='+id_departments+'&year='+year+'&week='+week+'&day='+day+'&id_employee='+id_employee+'&id_planification_type='+id_planification_type+'&ora_ini='+ora_ini+'&ora_fin='+ora_fin,
  				beforeSend: function () {
  		        	showLoader();
  		        },
  		        success: function (data) {
  		        	
  		        	showLoader();
  		        	// Reload Page
  		        	//javascript:window.location='planner-list2.htm?tp=${tp}&year=${plan_year}&week=${plan_week}&id_departments=${id_departments}';
	  				//window.location.href='planner-list2.htm?tp=${tp}&year=${plan_year}&week=${plan_week}&id_departments=${id_departments}';
  		        	
	  				document.getElementById("frmPlanner").submit();
	  				document.forms["frmPlanner"].submit();
	  				document.frmPlanner.submit();
	  				
  		        	$( ".modal-backdrop").remove();
  		  			$( ".modal.in" ).remove();
  		        },
  		        error: function () {
  		        	hideLoader();
  		        	alert('Errore! si e\' verificato un errore.');
  		        }
  		    });

  		}
  		else {
  			alert("Si e' verificato un errore:\n\n"+msgErr);
  		}
  			
  	}
  	
  	function cancellaPeriodo(id) {
  		
  		$('#richiestaApprovazione').val(id)
  		
  		document.getElementById("frmPlanner").submit();
		document.forms["frmPlanner"].submit();
		document.frmPlanner.submit();
  	}
  	
  	
  	function controllaTipo(tipo) {
  		
  		if(tipo=="575ed403-8924-4345-bf86-d2bf0534afb6" || tipo=="afc1e7c2-d84a-461b-a567-ce8771b8c6aa") {
  			$('#addPlan_ora_ini').val('07:00');
  			$('#addPlan_ora_fin').val('24:00');
  			
  			$('#addPlan_ora_ini option:not(:selected)').attr('disabled', true);
  			$('#addPlan_ora_fin option:not(:selected)').attr('disabled', true);
  			
  		}else {
  			$('#addPlan_ora_ini').val('');
  			$('#addPlan_ora_fin').val('');
  			
  			$('#addPlan_ora_ini option:not(:selected)').attr('disabled', false);
  			$('#addPlan_ora_fin option:not(:selected)').attr('disabled', false);
  		}
  			
  	}
  	
  	var iddep = $('#id_departments').val();
  	var idweek = $('#week').val();
  	var title = '';
 	title = $("#id_departments option[value='"+iddep+"']").text()+" "+$('#year').val()+" "+$("#week option[value='"+idweek+"']").text(); 	
  	
  	$('#title').append(" - "+title);
  	
  </script>
  
  </body>
</html>
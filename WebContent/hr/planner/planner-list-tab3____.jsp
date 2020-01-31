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
  	 
  	 .marBig {
  	 	margin-bottom: 250px;
  	 }
  	 
  	 .mar50 {
  	 	margin-bottom: 50px;
  	 }
  	 
  	 .mar0 {
  	 	margin-bottom: 0px;
  	 }
  	 
  	 .tfootBlock {
  	 	position: fixed;
	    bottom: 0;
	    width:85%;
	    background: #fff;
  	 }
  	 
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
	    margin-top: 0px;
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
	.rc {
	  background: #E0EAEC;
	  /*box-shadow:	0 0 1em #FAD733;*/ 
	}
	.mat {
	  background: #E0EAEC;
	  /*box-shadow:	0 0 1em #FAD733;*/ 
	}
	
       .ui-slider-horizontal {
		height: 20px;
	}
	.ui-slider .ui-slider-handle { width: 10px; margin-left: -5px; height: 28px; }
	
	.panel .table td,
	.panel .table th {
	  /*padding: 8px 15px;*/
	  padding: 5px 5px 5px 5px;
	  
	  border-top: 1px solid #eaeef1;
	}
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
              		  	  <input type="hidden" id="id_departments" name="id_departments_vid">
					  	  <input type="hidden" id="year" name="year_vid" >
					      <input type="hidden" id="week" name="week_vid">
					    
						  <div class="text-center" style="padding-top:10px">
						  	
						  	<a href="planner-ric.htm?tp=${tp}" class="btn btn-default m-b-sm"><%=propLang.get("btnSearch") %></a>
						  	
						  	<!-- 
						  	<a href="planner-list-tab2.htm?tp=${tp}&id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&vis=3&eye=${eye}" onclick="showLoader()" class="btn btn-default m-b-sm"><i class="fa ${eyeIcon}"></i></a>
						  	-->
						  	<a href="#" onclick="setEye()" style="width:40px" data-toggle="class:nav-xs,nav-xs" class="btn btn-default m-b-sm">
						  		<i class="fa fa-eye text-active"></i>
				           		<i class="fa fa-eye-slash text"></i></a>
						  	
						  	<a href="../../app/popup/addPlanification.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&tp=${tp}" 
									id="btnTurni" class="getModal btn btn-default  m-b-sm" style="width:150px" ${dis}><%=propLang.get("btnTurni") %></a>
							
						  	<c:if test="${tp=='A'}">
						  		<c:if test="${tpApp=='0001'}">
						  			<button type="submit" id="btnRigetta" style="width:70px" class="btn btn-danger  m-b-sm" ${dis} 
					      				onclick="$('#richiestaApprovazione').val('R');showLoader();elaboraForm();" ${dis}><%=propLang.get("btnRigetta") %></button>	
					      			<button type="submit" id="btnApprova" style="width:70px" class="btn btn-info  m-b-sm" ${dis} 
					      				onclick="$('#richiestaApprovazione').val('A');showLoader();elaboraForm();" ${dis}><%=propLang.get("btnApprova") %></button>
							  	</c:if>
						  		<button type="submit" id="btnStampa" style="width:70px" class="btn btn-primary  m-b-sm" 
					      			onclick="$('#richiestaApprovazione').val('S');showLoader();elaboraForm();"><%=propLang.get("btnStampa") %></button>
					      		
						  		<button type="submit" id="btnSubmit" style="width:70px" class="btn btn-success  m-b-sm" 
						  			onclick="showLoader();elaboraForm();" ${dis} disabled><%=propLang.get("btnSalva") %></button>
						  	</c:if>
						  	<c:if test="${tp=='I'}">
						  		<button type="submit" id="btnApprova" style="width:150px" class="btn btn-info  m-b-sm" ${dis} 
					      			onclick="$('#richiestaApprovazione').val('X');showLoader();elaboraFormRic();"><%=propLang.get("btnRichiedi") %></button>
					      			
						  		<%
						  		if(session.getAttribute("tipo").toString().equals("A") || session.getAttribute("tipo").toString().equals("M") || session.getAttribute("tipo").toString().equals("H")) {	  			
						  		%>
						  		<c:if test="${dis=='disabled'}">
						  		<a href="../../app/popup/planner-note.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=Y&tp=${tp}" 
							  		style="width:70px" class="getModal btn btn-dark  m-b-sm"><%=propLang.get("btnRiapri") %></a>
						  		</c:if>
						  		<%
						  		}
						  		%>
						  		<button type="submit" id="btnStampa" style="width:70px" class="btn btn-primary  m-b-sm" 
					      			onclick="$('#richiestaApprovazione').val('S');showLoader();elaboraForm();" ${dis}><%=propLang.get("btnStampa") %></button>
						  		
						  		<button type="submit" id="btnSubmit" style="width:70px" class="btn btn-success  m-b-sm" ${dis} 
					      			onclick="showLoader();elaboraForm();" ${dis}><%=propLang.get("btnSalva") %></button>
					      	</c:if>
					      	
					      	<%
					  		if(session.getAttribute("tipo").toString().equals("A") || session.getAttribute("tipo").toString().equals("M")) {	  			
					  		%>
					      	<a href="../../app/popup/planification_note.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&tp=${tp}" 
									id="btnNote" class="getModal btn btn-default  m-b-sm">Note</a>
							<%
					  		}
							%>
					      	<span class="btn btn-default  m-b-sm"
					      		data-toggle="popover" data-html="true" data-placement="bottom" 
					      		data-content="<div class='scrollable'>
					      		<span class='label bg-success'>Turno</span>
					      		<span class='label bg-danger'>ROL - Permesso</span>
					      		<span class='label bg-info'>Malattia</span>
					      		<span class='label bg-primary'>Ferie</span>
					      		<span class='label bg-warning'>Monte Ore</span>
					      		<span class='label bg-light'>Legge 104</span>
					      		<span class='label bg-black'>Aspettativa</span>
					      		
					      		</div>" 
					      		title="" data-original-title="<button type=&quot;button&quot; class=&quot;close pull-right&quot; data-dismiss=&quot;popover&quot;>&times;</button>Legenda">
					      		<img src="info.png" style="width:18px;height:18px"></span>
							
					      </div>
				
          		</header>  
			  
          
          <section class="hbox stretch">     
          	
            <section>
              
           		<c:if test="${nrdip>0}">
			              
	            <section class="vbox">
	              
          		  <section class="scrollable padder-md wrapper-sm mar50 " id="bjax-target">
          		  
                    <div class="row row-sm">
                    	
                    	<form id="frmPlanner" name="frmPlanner" action="planner-list-tab3.htm" method="post">
		                <input type="hidden" id="tp" name="tp" value="${tp}">
		                <input type="hidden" name="azione" value="S">
		                <input type="hidden" name="tipoForm" value="I">
		                <input type="hidden" name="vis" value="${vis}">
		                <input type="hidden" name="tpvis" value="${tpvis}">
		               
		                <input type="hidden" id="id_departments2" name="id_departments" value=""/>
				  		<input type="hidden" id="year2" name="year" value=""/>
				  	    <input type="hidden" id="week2" name="week" value=""/>
		               
		                <input type="hidden" id="richiestaApprovazione" name="richiestaApprovazione" value=""/>
		               
		                <input type="hidden" id="eye" name="eye" value="${eye}"/>
		               
						<!--  Giorno -->  
						<c:forEach items="${day}" var="day" varStatus="status">
						
						<section class="panel panel-default">
		                    <header class="panel-heading bg-light">
		                      <ul class="nav nav-tabs nav-justified">
		                        
		                        <c:forEach items="${giorni}" var="giorni" varStatus="status">
		                        <li ${giorni.cmp2}><a href="#day${giorni.xcdel}" ${giorni.cmp3} data-toggle="tab">
		                        	<span style="font-size:10px">${giorni.xdtab} <b>${giorni.cmp1}</b></span></a></li>
		                        </c:forEach>
		                        
		                        <li><a href="#dayTotal" data-toggle="tab"><%=propLang.get("totali") %></a></li>
		                      </ul>
		                    </header>
		                    <div class="panel-body" style="padding:0px;margin:0px">
		                      <div class="tab-content">
		                        <div class="tab-pane active" id="day0">
		                        	
		                        	<input type="hidden" name="day" value="${day.xcdel}">
			                		<input type="hidden" id="data${day.xcdel}" name="data" value="${day.cmp1}">
		                        	
		                        	<div class="table-responsive">
						               <table class="table b-t b-light" style="padding:2px;">
						                 
									    <tbody id="body-table">
									    
									    <c:forEach items="${plan}" var="emp" varStatus="status">
										  <tr id="row${day.xcdel}${emp.id_employee}" class="${emp.eyeClass}">
										    
										    <td class="b-r" style="width:40px;border-bottom:2px solid #77848F">
										    	<div id="status${day.xcdel}${emp.id_employee}"></div>
										    </td>
											<td class="b-r" style="width:120px; border-bottom:2px solid #77848F">
												<div style="width:120px;" class="text-ellipsis">${emp.name} ${emp.surname}</div>
												
												<input type="hidden" name="id_employee${day.xcdel}" value="${emp.id_employee}"/>
												<input type="hidden" id="id_departments${day.xcdel}${emp.id_employee}" name="id_departments${day.xcdel}${emp.id_employee}" value="${emp.id_dep}"/>
												<input type="text" id="ds_departments${day.xcdel}${emp.id_employee}" name="ds_departments${day.xcdel}${emp.id_employee}" 
													style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="${emp.ds_dep}" disabled/>
												
												<div id="periods${day.xcdel}${emp.id_employee}"></div>
												
											</td>
											
											<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F">
												<a href="../../app/popup/departments_planner.jsp?iddest=id_departments${day.xcdel}${emp.id_employee}&dsdest=ds_departments${day.xcdel}${emp.id_employee}" 
													class="getModal btn btn-default btn-xs" data-toggle="tooltip"
													data-placement="top" title="" data-original-title="<%=propLang.get("cambiaDip") %>" ${dis}>
										      		T.</a>
											</td>
											
											<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F"">
												<div class="btn-group-vertical">
												<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
													onclick="confirmAddSliderLeft('${day.xcdel}', '${emp.id_employee}', 'L')"
													data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnAddL") %>" ${dis}><i class="icon-arrow-left" style="margin-top:6px;margin-left:-6px"></i>
												</a>
												<a href="#" class="btn btn-default btn-block btn-xs"  data-toggle="tooltip" 
													onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id_employee}')"
													data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
													<i class="icon-close" style="margin-top:6px;margin-left:-6px"></i>
												</a>
												</div>
											</td>
											
											
											<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px; border-bottom:2px solid #77848F"">
												
												<div class="${day.xcdel}${emp.id_employee}tempi"></div>
											    
												<div class="${day.xcdel}${emp.id_employee}ruler ui-slider"></div>
												<br/>
												<div class="${day.xcdel}${emp.id_employee}"></div>
											    
											</td>
											
											<td class="b-r" style="width:30px;border-bottom:2px solid #77848F"">
												<div class="btn-group-vertical">
												<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
													onclick="confirmAddSliderRight('${day.xcdel}', '${emp.id_employee}', 'R')"
													data-placement="top" style="width:20px;" title="" data-original-title="<%=propLang.get("btnAddR") %>" ${dis}>
													<i class="icon-arrow-right" style="margin-top:6px;margin-left:-6px"></i>
												</a>
												<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
													onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id_employee}')"
													data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
													<i class="icon-close" style="margin-top:6px;margin-left:-6px"></i>
												</a>
												</div>
											</td>
											
											<td class="b-l" style="width:60px; border-bottom:2px solid #77848F">
												<input type="text" name="totDip${day.xcdel}${emp.id_employee}" id="totDip${day.xcdel}${emp.id_employee}" 
													value="0" style="width:50px;text-align:right; background: transparent; 
													border:0px;font-weight:bold;color:#000" readonly />
											</td>
											
										  </tr>
										  
										</c:forEach>
										
										<!-- Trasferimenti -->
										<c:forEach items="${trasf0}" var="trasf0" varStatus="status">
											
										  <tr id="row${day.xcdel}${trasf0.xcdtb}">
										    
										    <td class="b-r" style="width:40px; border-bottom:2px solid #77848F">
										    	<div id="status${day.xcdel}${trasf0.xcdtb}"></div>
										    </td>
										    
											<td class="b-r" style="width:120px; border-bottom:2px solid #77848F">
												<div style="width:120px;" class="text-ellipsis">${trasf0.xcdel} ${trasf0.xdtab}</div>
												
												<input type="hidden" name="id_employee${day.xcdel}" value="${trasf0.xcdtb}"/>
												<input type="hidden" id="id_departments${day.xcdel}${trasf0.xcdtb}" name="id_departments${day.xcdel}${trasf0.xcdtb}" value=""/>
												<input type="text" id="ds_departments${day.xcdel}${trasf0.xcdtb}" name="ds_departments${day.xcdel}${trasf0.xcdtb}" 
													style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="" disabled/>
												
												<div id="periods${day.xcdel}${trasf0.xcdtb}"></div>
												
											</td>
											
											<td class="b-r" style="width:30px; border-bottom:2px solid #77848F">
												
											</td>
											
											<td class="b-r" style="width:30px; border-bottom:2px solid #77848F">
												
											</td>
											
											<td colspan="18" class="b-r" style="background:#fff;border-bottom:2px solid #77848F; height:${rowheight}px">
												
												<div class="${day.xcdel}${trasf0.xcdtb}tempi"></div>
											    
												<div class="${day.xcdel}${trasf0.xcdtb}ruler ui-slider"></div>
												<br/>
												<div class="${day.xcdel}${trasf0.xcdtb}"></div>
											    
											</td>
											
											<td class="b-r" style="width:30px; border-bottom:2px solid #77848F" rowspan="2">
												-
											</td>
											
											<td class="b-l" style="width:60px; border-bottom:2px solid #77848F" rowspan="2">
												<input type="text" name="totDip${day.xcdel}${trasf0.xcdtb}" id="totDip${day.xcdel}${trasf0.xcdtb}" 
													value="0" style="width:50px;text-align:right; background: transparent; 
													border:0px;font-weight:bold;color:#000" readonly />
											</td>
											
										  </tr>
										  
										</c:forEach>
										
									    </tbody>
									    
									  </table>
									  
								  </div>
								  
								  
								  
								  
								  
								  
								  
								  <!--  Totale Settimana -->
								      <div class="totaliGiorno">
						               <table class="table b-t b-light">
						                 <tfoot>
									      
									      <!-- Intestazioni -->
									      <tr>
									    	<td style="width:240px"></td>
									    	<td class="barra">
									    		<div style="height:13px;margin-top:-5px" class="${day.xcdel}intestazioni ui-slider"></div>
									    	</td>
									    	<td style="width:90px"></td>
									      </tr>
									      
									      <!-- Totale x Fascia -->
									      <tr>
									    	<td style="width:240px"><b><%=propLang.get("tot1") %></b></td>
									    	<td class="barra">
									    		<div style="height:20px" class="${day.xcdel}tot1 ui-slider"></div>
									    	</td>
									    	<td style="width:90px"><input type="text" name="${day.xcdel}totSett1" id="${day.xcdel}totSett1" 
													value="0" style="width:87px;text-align:right; background: transparent; 
													border:0px;font-weight:bold;color:#000" readonly /></td>
									      </tr>
									      <!-- Totale x Risorsa -->
									      <tr>
									    	<td style="width:240px"><b><%=propLang.get("tot2") %></b></td>
									    	<td class="barra">
									    		<div style="height:20px" class="${day.xcdel}tot2 ui-slider"></div>
									    	</td>
									    	<td style="width:90px"><input type="text" name="${day.xcdel}totSett2" id="${day.xcdel}totSett2" 
													value="0" style="width:87px;text-align:right;background:transparent; 
													border:0px;font-weight:bold;color:#000" readonly /></td>
									      </tr>
									      <!-- Nomero tabella (da creare) -->
									      <tr>
									    	<td style="width:240px"><b><%=propLang.get("tot3") %></b></td>
									    	<td class="barra">
									    		<div style="height:20px" class="${day.xcdel}tot3 ui-slider"></div>
									    	</td>
									    	<td style="width:90px"><input type="text" name="${day.xcdel}totSett3" id="${day.xcdel}totSett3" 
													value="0" style="width:87px;text-align:right; background: transparent; 
													border:0px;font-weight:bold;color:#000" readonly /></td>
									      </tr>
									     </tfoot>
						               </table>
						              </div>
						              <div class="spacer"></div>
								  
								  
								  
		                        	
		                        </div>
		                        <div class="tab-pane" id="day1">
		                        	<div align="center" id="dayLoader1">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay1" name="caricoDay1" value="N"/>
									  	
									  	<c:forEach items="${emp}" var="emp" varStatus="status">
									  		${emp.mar}	
									  		<input type="hidden" name="totDip1${emp.id}" id="totDip1${emp.id}" value="0"/>
									  	</c:forEach>
									  	
									</div>
		                        </div>
		                        <div class="tab-pane" id="day2">
		                        	<div align="center" id="dayLoader2">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay2" name="caricoDay2" value="N"/>
									  	
									  	<c:forEach items="${emp}" var="emp" varStatus="status">
									  		${emp.mer}
									  		<input type="hidden" name="totDip2${emp.id}" id="totDip2${emp.id}" value="0"/>
									  	</c:forEach>
									</div>
		                        </div>
		                        <div class="tab-pane" id="day3">
		                        	<div align="center" id="dayLoader3">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay3" name="caricoDay3" value="N"/>
									  	
									  	<c:forEach items="${emp}" var="emp" varStatus="status">
									  		${emp.gio}
									  		<input type="hidden" name="totDip3${emp.id}" id="totDip3${emp.id}" value="0"/>
									  	</c:forEach>
									</div>
		                        </div>
		                        <div class="tab-pane" id="day4">
		                        	<div align="center" id="dayLoader4">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay4" name="caricoDay4" value="N"/>
									  	
									  	<c:forEach items="${emp}" var="emp" varStatus="status">
									  		${emp.ven}
									  		<input type="hidden" name="totDip4${emp.id}" id="totDip4${emp.id}" value="0"/>
									  	</c:forEach>
									</div>
		                        </div>
		                        <div class="tab-pane" id="day5">
									<div align="center" id="dayLoader5">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay5" name="caricoDay5" value="N"/>
									  	
									  	<c:forEach items="${emp}" var="emp" varStatus="status">
									  		${emp.sab}
									  		<input type="hidden" name="totDip5${emp.id}" id="totDip5${emp.id}" value="0"/>
									  	</c:forEach>
									</div>
								</div>
		                        <div class="tab-pane" id="day6">
		                        	<div align="center" id="dayLoader6">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay6" name="caricoDay6" value="N"/>
									  	
									  	<c:forEach items="${emp}" var="emp" varStatus="status">
									  		${emp.dom}
									  		<input type="hidden" name="totDip6${emp.id}" id="totDip6${emp.id}" value="0"/>
									  	</c:forEach>
									</div>
		                        </div>
		                        <div class="tab-pane" id="dayTotal">
		                        	
		                        	  <div class="table-responsive">
						               <table class="table b-t b-light">
						                 <!-- Totale Settimana -->
						                 <!-- 
									     <tr>
									        <td><b><%=propLang.get("tot") %></b></td>
									    	<td colspan="7">
									    		
									    		<div style="height:20px" class="totSett ui-slider"></div>
									    		
									    	</td>
									    	<td><input type="text" name="totSett" id="totSett" 
													value="0" style="width:50px;text-align:right; background: transparent; 
													border:0px;font-weight:bold;color:#000" readonly /></td>
									     </tr>
									     -->
						                 <tr>
									    	<th><%=propLang.get("emp") %></th>
									    	<th style="width:10%"><%=propLang.get("lun") %></th>
									    	<th style="width:10%"><%=propLang.get("mar") %></th>
									    	<th style="width:10%"><%=propLang.get("mer") %></th>
									    	<th style="width:10%"><%=propLang.get("gio") %></th>
									    	<th style="width:10%"><%=propLang.get("ven") %></th>
									    	<th style="width:10%"><%=propLang.get("sab") %></th>
									    	<th style="width:10%"><%=propLang.get("dom") %></th>
									    	<th style="width:10%"><%=propLang.get("totali") %></th>
									     </tr>
									     <tbody>
						                  <c:forEach items="${empAll}" var="empAll" varStatus="status">
									      	<tr>
									      		<td class="b-r">
													<div style="width:120px;" class="text-ellipsis">${empAll.name} ${empAll.surname}</div></td>
												<td class="b-r">
													<input type="text" name="tot0" id="tot0${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tot1" id="tot1${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tot2" id="tot2${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tot3" id="tot3${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tot4" id="tot4${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tot5" id="tot5${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tot6" id="tot6${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td>
													<input type="text" name="totDef" id="totDef${empAll.id}" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
											</tr>
										  </c:forEach>
										    <tr>
										    	<td class="b-r">
													<%=propLang.get("totali") %>
												<td class="b-r">
													<input type="text" name="tottot0" id="tottot0" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tottot1" id="tottot1" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tottot2" id="tottot2" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tottot3" id="tottot3" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tottot4" id="tottot4" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tottot5" id="tottot5" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td class="b-r">
													<input type="text" name="tottot6" id="tottot6" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
												<td>
													<input type="text" name="tottotDef" id="tottotDef" value="0" style="width:50px;text-align:right;background: transparent; 
													border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
												</td>
										    </tr>
									     </tbody>
						               </table>
						              
		                        </div>
		                      </div>
		                    </div>
		                    
		                    </div>
		                  </section>
		                  
		                  
						
					  </c:forEach>
					  <!--  fine Schiera giorni -->
					  
					  
					  
					  <div class="row row-sm">
							<div class="col-xs-12">
							
								<section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
				                      <span class="hidden-sm"><b>HeatMap</b></span>
				                    </header>
				                    <div class="panel-body">
				                    
				                    	<div id="main2" style="height:${height}px;"></div>
				                     
				                    </div>
				                </section>
						  	
						  	</div>
						</div>
					  
					  
					  
					  
					  
					  <!--  Totale diviso x giorni -->
					  <!--  Totale Settimana -->
					  <div style="display: none">
					  
					  </div>
					  
					  
					  
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
                        
                        <a href="#" onclick="mostraNascondiTop()" style="padding-left: 30px" class="list-group-item"
                        	data-toggle="tooltip"
							data-placement="left" title="" data-original-title="Mostra / Nascondi Menu'">
					  		<i class="icon-equalizer"></i>
			           		<span class="hidden-nav-xs m-l-sm">Hide/Show</span>
			           		</a>
			           		
			           	<a href="#" onclick="mostraNascondiBottom()" style="padding-left: 30px" 
			           		class="list-group-item" data-toggle="tooltip"
								data-placement="left" title="" data-original-title="Blocca / Nascondi Totali">
					  		<i class="icon-list"></i>
			           		<span class="hidden-nav-xs m-l-sm">Hide/Show</span>
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
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <script>
  	
  var myChart2 = echarts.init(document.getElementById('main2'));
	
  	option2 = {
  		
	    tooltip: {
	        position: 'top'
	    },
	    animation: true,
	    grid: {
	        height: '70%',
	        y: '10%'
	    },
	    xAxis: {
	        type: 'category',
	        data: [${xAxis}],
	        splitArea: {
	            show: true
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: [${yAxis}],
	        splitArea: {
	            show: true
	        }
	    },
	    visualMap: {
	        min: 0,
	        max: ${max},
	        calculable: true,
	        orient: 'vertical',
	        left: 'right',
	        top: 'center',
	        inRange: {
	            
	        	//color: [ '#3F4787',  '#F9CD2F',  '#EF8384', '#EE6B6D', '#EE5254', '#EE393D',    '#EF2027']
	        	color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
	        }
	    },
	    series: [{
	        name: 'Employee',
	        type: 'heatmap',
	        data: [${heatmap}],
	        label: {
	            normal: {
	                show: true
	            }
	        },
	        itemStyle: {
	            emphasis: {
	                shadowBlur: 10,
	                shadowColor: 'rgba(0, 0, 0, 0.5)'
	                
	            },
	            normal: {
                    color: '#EE5154'
                }
	        }
	    }]
	
 		
 	}
    // use configuration item and data specified to show chart
    myChart2.setOption(option2);
	
  
  </script>
  
  <script>	
  	
  $('#frmPlanner button').on("click",function(e){
	    e.preventDefault;
  });
  
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
		
		$( 'div.modal-backdrop').remove();$( '.mia' ).remove();
		console.log('rim1');
		
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		$.get(href, function(data) {
			$('<div class="modal mia" >' + data + '</div>').modal();
		});
		
	});
	
	
  	function loadDay(day) {
  		
	  	var caricoDay = $('#caricoDay'+day+'').val();
	  	
	  	//alert(caricoDay);
	  	
	  	if(caricoDay=='N') {
	  		$.ajax({
		        url: 'planner-list-tab2Day.htm',
		        type: 'POST',
		        dataType: 'html',
		        async: true,
		        data: 'year=${plan_year}&week=${plan_week}&day='+day+'&id_departments=${id_departments}&dis=${dis}&vis=${vis}&tpvis=${tpvis}&eye=${eye}',
		        beforeSend: function () {
		        	$('#dayLoader').show();
		        },
		        success: function (data) {
		        	$('#dayLoader'+day+'').remove();
		        	$("#day"+day).append(data);
		        	
		        	var eye = $('#eye').val();
		        	if(eye=='P') {
		        		$('.eye').hide();
		        	}
		        	
		        	//render(day);
		        	
		        },
		        error: function (xhr, ajaxOptions, thrownError) {
		            //alert(xhr.status);
		            //alert(thrownError);
		        }
		    });
	  	}
	  	
  	}
  	
  	
  	
  	function render(day) {
  		
  		// =====================
  		// INIZIO GIORNO
  	  	<c:forEach items="${plan}" var="emp" varStatus="status">
  	  		
  		 	// Contenitore input per insert
  	  		var scntDiv = $("#periods"+day+"${emp.id_employee}");
  	  		// Pulisco il contenitore
  			scntDiv.empty();
  	  		
  		    // Intervalli
  		    
  		    
  		    if(day=='0') {
  		    	//$ ... {  emp.lun  }
  		    }
  		    
  		    //ZZZZZZZZZ 1 ZZZZZZZZZ
  	  		renderRighello(day , '${emp.id_employee}');
  	  		
  	  		// modifica il totale
  			setTot(''+day+'' , '${emp.id_employee}');
  	  		
  	  	</c:forEach>
  	  	
  	 	// Righello
  	 	for (var i = 0; i <= 18; i+=0.5) {
  	      	
  	    	if(isInt(i)) {
  	    		// Totali
  	    		var el2 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
  	    		var el3 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
  	    		var el4 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
  	    	}
  	    	else {
  	    		var el2 = $('<label id="'+day+'tot1'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
  	    		var el3 = $('<label id="'+day+'tot2'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
  	    		var el4 = $('<label id="'+day+'tot3'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
  	    		
  	    	}
  	    	$( "."+day+"tot1" ).append(el2);
  	    	$( "."+day+"tot2" ).append(el3);
  	    	$( "."+day+"tot3" ).append(el4);
  	    }
  	  	
  	 	var tot3 = ""+$('#tot3'+day+'hidden').val();
  	 	if(tot3=="undefined") {
  	 		tot3 = "0";
  	 	}
  	 	console.log('tot3:' + tot3);
  	 	
  	 	var res = tot3.split(",");
  	 	
  	 	for ( var i = 0, l = res.length; i < l; i++ ) {
  			
  	 		$('#'+day+'tot3'+(i+7)).html(''+res[i]+'');
  	 		
  		}
  	 	
  	 	setTot1(''+day+'');
  	 	// FINE GIORNO
  	  	// ========================
  	  	
  	}
  	
  	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		
		<c:forEach items="${trasf0}" var="trasf0" varStatus="status">
  	  	${trasf0.cmp1}
  	  	
  		// Righello + input ore
  		var id='';
  	 	for (var i = 0; i <= 18; i+=0.5) {
  	      	
  	 		id = ""+((i+5)+1);
  	 		id = id.replace(/\./g,"");
  	 		
  	    	if(isInt(i)) {
  	    		var el = $('<label class="ruler0${trasf0.xcdtb}'+id+' ruler">'+((i+5)+1)+'</label>').css('left',(i/18*100)+'%');
  	    	}
  	    	else {
  	    		var el = $('<label class="ruler0${trasf0.xcdtb}'+id+' ruler"><span style="font-size:8px">&times;</span></label>').css('left',(i/18*100)+'%');
  	    	}
  	    	$( ".0${trasf0.xcdtb}ruler" ).append(el);
  	    	
  	    }
  	    </c:forEach>
		
		//loadDay("1");
		
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
 	for (var i = 0; i <= 18; i+=0.5) {
      	
    	if(isInt(i)) {
    		// Totali
    		var el5 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
    		
    	}
    	else {
    		var el5 = $('<label id="totSett'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
    	}
    	$( ".totSett" ).append(el5);
    }
  	
	// =====================
	// INIZIO GIORNO
  	<c:forEach items="${day}" var="day" varStatus="status">
  	console.log('${day.xcdel}');
  	<c:forEach items="${plan}" var="emp" varStatus="status">
  		
  		
  		
	 	// Contenitore input per insert
  		var scntDiv = $("#periods${day.xcdel}${emp.id_employee}");
  		// Pulisco il contenitore
		scntDiv.empty();
  		
	    // Intervalli
	    <c:if test="${day.xcdel=='0'}">
	    	//$....{emp.lun}
	    </c:if>
	    
	  	//ZZZZZZZZZ 1 ZZZZZZZZZ
  	 	
  	 	setRighello('${day.xcdel}' , '${emp.id_employee}');
  	    
  		// modifica il totale
		setTot('${day.xcdel}' , '${emp.id_employee}');
  		
		// ------------------
  		setTot('1' , '${emp.id_employee}');
  		setTot('2' , '${emp.id_employee}');
  		setTot('3' , '${emp.id_employee}');
		setTot('4' , '${emp.id_employee}');
		setTot('5' , '${emp.id_employee}');
		setTot('6' , '${emp.id_employee}');
		
  	</c:forEach>
  		
 	// Righello
 	for (var i = 0; i <= 18; i+=0.5) {
      	
    	if(isInt(i)) {
    		// Totali
    		var intestazioni = $('<label class="ruler">'+((i+5)+1)+'</label>').css('left',(i/18*100)+'%');
    		
    		
    		var el2 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
    		var el3 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
    		var el4 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
    	}
    	else {
    		
    		var intestazioni = $('<label id="${day.xcdel}intestazioni'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">&nbsp;</label>').css('left',(i/18*100)+'%');
    		
    		
    		var el2 = $('<label id="${day.xcdel}tot1'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
    		var el3 = $('<label id="${day.xcdel}tot2'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
    		var el4 = $('<label id="${day.xcdel}tot3'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
    		
    	}
    	$( ".${day.xcdel}intestazioni" ).append(intestazioni);
    	$( ".${day.xcdel}tot1" ).append(el2);
    	$( ".${day.xcdel}tot2" ).append(el3);
    	$( ".${day.xcdel}tot3" ).append(el4);
    }
  	
 	
 	var tot3 = "${day.cmp2}";
 	var res = tot3.split(",");
 	
 	for ( var i = 0, l = res.length; i < l; i++ ) {
		
 		$('#${day.xcdel}tot3'+(i+6)).html(''+res[i]+'');
 		
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
		  				min:		6,
		  				max:		24,
		  				label:		false,
		  				change: function( event, ui ) {
		  					reloadInputs(day , idemployee);
						},
		  				ranges:		[false, { styleClass: 'turno' }, false, { styleClass: getCss(day , idemployee, '1') }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false]
		  		   	});
	  			});
	  			
	  			// Ricarica gli inputs
	  			reloadInputs(day , idemployee);
	  			
	  			// rimuovi classe .eye
	  			$( "#row"+day+""+idemployee ).removeClass( "eye" );
	  			
	  		}
	  		
 		}
 		// Altrimenti crea lo slider con valori di default
 		else {
 			
 			// Ricrea lo slider con nuovo valore
  			$(function() {
	  	  		$( "."+day+""+idemployee ).limitslider({
	  				values:		[6 , 7],
	  				showRanges: true,
	  				step:		0.5,
	  				gap:		0,
	  				min:		6,
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
  			
  			// rimuovi classe .eye
  			$( "#row"+day+""+idemployee ).removeClass( "eye" );
  			
 			
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
		  				min:		6,
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
	  			
	  			// rimuovi classe .eye
	  			$( "#row"+day+""+idemployee ).removeClass( "eye" );
	  			
	  			
	  		}
	  		
 		}
 		// Altrimenti crea lo slider con valori di default
 		else {
 			
 			// Ricrea lo slider con nuovo valore
  			$(function() {
	  	  		$( "."+day+""+idemployee ).limitslider({
	  				values:		[6 , 7],
	  				showRanges: true,
	  				step:		0.5,
	  				gap:		0,
	  				min:		6,
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
  			
  			// rimuovi classe .eye
  			$( "#row"+day+""+idemployee ).removeClass( "eye" );
  			
 			
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
  		    
  			//alert('chkTurnoEdit.jsp?year='+year+'&week='+week+'&day='+day+'&id_employee='+idemployee+'&req_data='+dataIta+'&ini='+sValues[ i ]+'&fin='+sValues[ i+1 ]);
  			
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
		        	    $('#contenutoDeleteModal').append('<div class="radio i-checks"><label>&nbsp; Periodo (NO TURNO) ' + sValues[ i ] + ' - ' + sValues[ i+1 ]+' &nbsp;&nbsp;&nbsp;<a href="#" onclick="cancellaPeriodo(\''+$.trim(data)+'\', \''+day+'\', \''+idemployee+'\')" class="btn btn-danger btn-xs">Cancella</a></label></div>');
		        	    //$('#contenutoDeleteModal').append('<div class="radio i-checks"><label><input type="radio" name="turno" value="'+sValues[ i ]+','+sValues[ i+1 ]+'"><i></i> Periodo (NO TURNO) ' + sValues[ i ] + ' - ' + sValues[ i+1 ]+'</label></div>');
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
					min:		6,
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
		
  		// aggiunge classe .eye
		$( "#row"+day+""+idemployee ).addClass( "eye" );
			
		
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
			
			//alert('chkTurnoEdit.jsp?year='+year+'&week='+week+'&day='+day+'&id_employee='+idemployee+'&req_data='+dataIta+'&ini='+lista[ i ]+'&fin='+lista[ i+1 ]);
  			
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
  		for(var i=6; i<24; i+=0.5) {
				
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
  		for(var i=6; i<24; i++) {
			
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
  		
		for(var i=6; i<24; i+=1) {
  			
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
		
		for(var i=6; i<24; i+=1) {
			
			val1 = parseFloat($('#'+day+'tot2'+i).html());
			val2 = parseFloat($('#'+day+'tot3'+i).html());
			
			if(parseFloat(val1)!=parseFloat(val2)) {
				$('#'+day+'tot3'+i).css({"color": "#f05050", "font-weight": "bold"});
			}
			else {
				$('#'+day+'tot3'+i).css({"color": "#000", "font-weight": "bold"});
			}
			
		}
		// ====================
  		
  		
  		
  		
  		
  		$('#tottot'+day+'').val(tot1);
  		
  		// ======================
  			
  		// Totali Settimana
  		var totSett = 0;
		
		// Fascia oraria 
		var totSett6 = 0;
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
  			for(var i=6; i<24; i++) {
  				
  				var valore = 0;
  				
  				$("input[name=day"+t+''+i+"]").each(function(){
  	  				
  	  				valore=valore+parseFloat(this.value);
  	  				totSett=totSett+parseFloat(this.value);
  	  				
  	  				if(i==6) {
	  					totSett6=totSett6+parseFloat(this.value);
	  				}
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
  		
  		$('#totSett6').html(''+totSett6+'');
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
		for(var i=6; i<24; i++) {
			
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
  		
		for(var i=6; i<24; i+=1) {
  			
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
		
		for(var i=6; i<24; i+=1) {
			
			val1 = parseFloat($('#'+day+'tot2'+i).html());
			val2 = parseFloat($('#'+day+'tot3'+i).html());
			
			if(parseFloat(val1)!=parseFloat(val2)) {
				$('#'+day+'tot3'+i).css({"color": "#f05050", "font-weight": "bold"});
			}
			
		}
		// ====================
		
		
		
		
		
		
		var tottot1 = 0;
		for(var i=6; i<24; i++) {
			
  			tottot1 = $('#'+day+'totSett1').val();
  			
  			$('#tottot'+day).val(tottot1);
			
		}
		
		
		// Totali Settimana
  		var totSett = 0;
		
		// Fascia oraria 
		var totSett6 = 0;
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
  			for(var i=6; i<24; i++) {
  				
  				var valore = 0;
  				
  				$("input[name=day"+t+''+i+"]").each(function(){
  	  				
  	  				valore=valore+parseFloat(this.value);
  	  				totSett=totSett+parseFloat(this.value);
  	  				
  	  				if(i==6) {
	  					totSett7=totSett7+parseFloat(this.value);
	  				}
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
  		
  		$('#totSett6').html(''+totSett6+'');
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
  		
  		//alert(day+" "+idemployee+" "+iddepartment+" "+description);
  		
  		$('#id_departments'+day+''+idemployee+'').val(iddepartment);
  		$('#ds_departments'+day+''+idemployee+'').val(description);
		
  	}
  	
  	function setStatus(day, idemployee, status) {
  		$('#status'+day+''+idemployee+'').html(status);
  	}
  	
  	${newDepart}
  	
  	function setNote() {
  		
  		var note = $('#note').val();
  		
  		if($.trim(note)=='') {
  			alert("Inserire le note.");
  			return false;
  		}
  		else if($.trim(note).length<10) {
  			alert("Inserire almeno 10 caratteri.");
  		}
  		else {
  			javascript:window.location='planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=Y&tp=${tp}&note='+note;
  			window.location.href='planner-approve.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=Y&tp=${tp}&note='+note;
  		}
  		
  		
  	}
  	
  	// aggiunge pianificazione da modal
  	function addPlanification() {
  		
  		var isDisabled = $("#btnSubmit").is(':disabled');
  		
  		// Deve ancora terminare di caricare
  		if (isDisabled) {
  			alert("Attendere il completo caricamento della pagina.");
  		}
  		// Ha terminato il caricamento
	  	else {
	  	
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
	  			
	  			// NEW - Da verificare
	  			
	  			var dow = "";
	  			
	  			var dayIso = day.toString().substring(6, 10)+"-"+day.toString().substring(3,5)+"-"+day.toString().substring(0,2);
	  			
	  			var d = new Date(dayIso);
	  		    var n = d.getDay();
	  		    n=n-1;
	  			
	  		    var dati = "";
	  		  	dati = $("input[name='p_dati"+n+""+id_employee+"']").val();
	  		  	
	  		  	if(dati!=undefined) {
	  		  		
	  		  		var sValues = $("."+n+""+id_employee).limitslider("values") ;
	  		  	
	  		  		var periodid=0;
	  		  		var confirmSubmit=false;
	  		  		
		  	  		for ( var i = 0, l = sValues.length; i < l; i+=2 ) {
		  	  			
		  	  			periodid+=1;
		  	  		
		  	  			var hhini = ora_ini.toString().replace(":30", ".5");
		  	  			hhini = parseFloat(hhini);
		  	  			var hhfin = ora_fin.toString().replace(":30", ".5");
	  	  				hhfin = parseFloat(hhfin);
	  	  				
	  	  				
	  	  				
	  	  				if((parseFloat(hhini)>=parseFloat(sValues[ i ])&&parseFloat(hhini)<=parseFloat(sValues[ i+1 ])) 
	  	  				   ||
	  	  				   (parseFloat(hhfin)>=parseFloat(sValues[ i ])&&parseFloat(hhfin)<=parseFloat(sValues[ i+1 ]))
	  	  				   ||
	  	  				   (parseFloat(hhini)<parseFloat(sValues[ i ])&&parseFloat(hhfin)>parseFloat(sValues[ i+1 ]))
	  	  				   /*
	  	  				   ||
	  	  				   (parseFloat(hhini)==parseFloat(sValues[ i ]))
	  	  				   ||
	  	  				   (parseFloat(hhfin)==parseFloat(sValues[ i+1 ]))
	  	  				   */
	  	  				   ) {
	  	  						
	  	  					if(confirm("Il turno si sovrappone ad uno gia' esistente.\nConfermando cancellerai il turno esistente.")) {
	  	  						
	  	  						confirmSubmit = true;
	  	  						
	  	  						// Formattazione valori di input
	  	  						var val1 = sValues[ i ];
	  	  						var val2 = sValues[ i+1 ];
	  	  						
		  	  					if(parseFloat(val1) % 1 === 0){
		  	  				      val1 = val1+".0";
		  	  				    }
		  	  					if(parseFloat(val2) % 1 === 0){
		  	  				      val2 = val2+".0";
		  	  				    }
		  	  					
		  	  					console.log('#p_dati'+n+''+id_employee+'_'+periodid+'');
		  	  					
		  	  					try {
		  	  						$( "."+n+""+id_employee ).limitslider( "destroy" );
	  	  						}
		  	  					catch(Exception) {
		  	  						
		  	  					}
		  	  					// Rimuovo input
		  	  					$('#p_dati'+n+''+id_employee+'_'+periodid+'').remove();
		  	  					
	  	  					}
	  	  					
	  	  				}
	  	  				else {
	  	  					confirmSubmit = true;
	  	  				}
	  	  				
		  	  		}
		  	  		
		  	  		if(confirmSubmit==true) {
		  	  				
		  	  			console.log('confirmSubmit=true');
		  	  			
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
	  	  	  		        	
	  	  	  		        	hideLoader();
	  	  	  		        	
	  	  	  		        	
	  	  		  				showLoader();
	  	  	  		        	
	  	  	  		        	document.getElementById("frmPlanner").submit();
	  	  		  				document.forms["frmPlanner"].submit();
	  	  		  				document.frmPlanner.submit();
	  	  		  				
	  	  		  				
	  	  		  				/*
	  	  		  				var hhini2 = ora_ini.toString().replace(":30", ".5");
	  			  	  			hhini2 = parseFloat(hhini2);
	  			  	  			var hhfin2 = ora_fin.toString().replace(":30", ".5");
	  		  	  				hhfin2 = parseFloat(hhfin2);
	  	  		  				var tipo = '';
	  	  		  				if(id_planification_type=='3694f2cb-1d27-4ec1-9cd2-dd2bc1bf73b4') {
	  	  		  					tipo = 'rol';
	  	  		  				}
	  	  		  				else if(id_planification_type=='575ed403-8924-4345-bf86-d2bf0534afb6') {
	  	  		  					tipo = 'ferie';
	  	  		  				}
	  	  		  				else if(id_planification_type=='afc1e7c2-d84a-461b-a567-ce8771b8c6aa') {
	  	  		  					tipo = 'malattia';
	  	  		  				}
		  	  		  			else if(id_planification_type=='da24f14a-1436-4295-8f99-8b1a450b6277') {
	  	  		  					tipo = 'turno';
	  	  		  				}
			  	  		  		else if(id_planification_type=='5d2cd2c5-1942-4138-863e-b5ca14e3fbf2') {
	  	  		  					tipo = 'monte';
	  	  		  				}
				  	  		  	else if(id_planification_type=='33b31123-b8df-40dd-a4e5-f0458349e220') {
	  	  		  					tipo = 'l104';
	  	  		  				}
					  	  		else if(id_planification_type=='c08eba66-867c-4dff-970f-8332a3cc7785') {
	  	  		  					tipo = 'asp';
	  	  		  				}
	  	  		  				
	  	  		  				// Distrugge lo slider
			  	  				//$( "."+n+""+id_employee ).limitslider( "destroy" );
			  	  				
	  	  		  				//alert(hhini2+" - "+hhfin2);
	  	  		  				confirmAddSliderGeneral(day , id_employee, hhini2, hhfin2, tipo);
	  	  		  				
	  	  		  				$('.mia').remove(); $('div.modal-backdrop').remove();
	  	  		  				*/
	  	  		  				
	  	  	  		        },
	  	  	  		        error: function () {
	  	  	  		        	hideLoader();
	  	  	  		        	alert('Errore! si e\' verificato un errore.');
	  	  	  		        }
	  	  	  		    });
		  	  			
		  	  		}
		  	  	
	  		  	}
	  		  	else {
	  		  		
	  		  		console.log('confirmSubmit=false');
	  		  		
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
	  	  		        	
	  	  		        	hideLoader();
	  	  		        	
	  	  		        	// Reload Page
	  	  		        	
	  	  		        	showLoader();
	  		  				
	  	  		        	document.getElementById("frmPlanner").submit();
	  		  				document.forms["frmPlanner"].submit();
	  		  				document.frmPlanner.submit();
	  		  				
	  		  				/*
	  		  				var hhini2 = ora_ini.toString().replace(":30", ".5");
  			  	  			hhini2 = parseFloat(hhini2);
  			  	  			var hhfin2 = ora_fin.toString().replace(":30", ".5");
  		  	  				hhfin2 = parseFloat(hhfin2);
  	  		  				var tipo = '';
  	  		  				if(id_planification_type=='3694f2cb-1d27-4ec1-9cd2-dd2bc1bf73b4') {
  	  		  					tipo = 'rol';
  	  		  				}
  	  		  				else if(id_planification_type=='575ed403-8924-4345-bf86-d2bf0534afb6') {
  	  		  					tipo = 'ferie';
  	  		  				}
  	  		  				else if(id_planification_type=='afc1e7c2-d84a-461b-a567-ce8771b8c6aa') {
  	  		  					tipo = 'malattia';
  	  		  				}
	  	  		  			else if(id_planification_type=='da24f14a-1436-4295-8f99-8b1a450b6277') {
  	  		  					tipo = 'turno';
  	  		  				}
		  	  		  		else if(id_planification_type=='5d2cd2c5-1942-4138-863e-b5ca14e3fbf2') {
  	  		  					tipo = 'monte';
  	  		  				}
			  	  		  	else if(id_planification_type=='33b31123-b8df-40dd-a4e5-f0458349e220') {
  	  		  					tipo = 'l104';
  	  		  				}
				  	  		else if(id_planification_type=='c08eba66-867c-4dff-970f-8332a3cc7785') {
  	  		  					tipo = 'asp';
  	  		  				}
  	  		  				
  	  		  				//alert(hhini2+" - "+hhfin2);
  	  		  				
  	  		  				confirmAddSliderGeneral(day , id_employee, hhini2, hhfin2, tipo);
	  		  				
  	  		  				$('.mia').remove(); $('div.modal-backdrop').remove();
  	  		  				*/
  	  		  				
	  	  		        },
	  	  		        error: function () {
	  	  		        	hideLoader();
	  	  		        	alert('Errore! si e\' verificato un errore.');
	  	  		        }
	  	  		    });
	  		  		
	  		  	}
	  	  		
	  		}
	  		else {
	  			alert("Si e' verificato un errore:\n\n"+msgErr);
	  		}
	  	
	  	}
  			
  	}
  	
  	function cancellaPeriodo(id, day, idemployee) {
  		
  		
  		/*
  		$('#richiestaApprovazione').val(id)
  		
  		document.getElementById("frmPlanner").submit();
		document.forms["frmPlanner"].submit();
		document.frmPlanner.submit();
		*/
		
  		$.ajax({
	        url: 'cancellaTurnoAjax.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'id='+id,
			beforeSend: function () {
	        	showLoader();
	        },
	        success: function (data) {
	        	
	        	hideLoader();
	        	
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
	    		
	    		console.log(nr);
	    		
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
	    			try {
	    				$( "."+day+""+idemployee ).limitslider( "destroy" );
	    			}
	    			catch(err) {
	    				
	    			}
	    			// Ricrea lo slider con nuovo valore
	    			$(function() {
	    		  		$( "."+day+""+idemployee ).limitslider({
	    					values:		temp,
	    					showRanges: true,
	    					step:		0.5,
	    					gap:		0,
	    					min:		6,
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
	    			try {
	    				$( "."+day+""+idemployee ).limitslider( "destroy" );
	    			}
	    			catch(err) {
	    				
	    			}
	    			
	    			$( "."+day+""+idemployee ).empty();
	    			$('input[name=p_dati'+day+''+idemployee+']').remove();
	    			
	    			setTot(''+day+'' , ''+idemployee+'');
	    		}
	    		
	    		// Chiude la modal
	      		$('#deletePeriodModal').modal('hide');
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        },
	        error: function () {
	        	hideLoader();
	        	alert('Errore! si e\' verificato un errore.');
	        }
	    });
  		
  		//reloadSliderGeneral(day , idemployee);
		
  	}
  	
  	function elaboraForm() {
  		
  		document.getElementById("frmPlanner").submit();
		document.forms["frmPlanner"].submit();
		document.frmPlanner.submit();
  		
  	}
  	
	function elaboraFormRic() {
  		
		var id_departments = $('#id_departments').val();
	  	var year = $('#year').val();
	  	var week = $('#week').val();
	  	
	  	
		$.ajax({
	        url: 'elaboraForm.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: false,
	        data: 'id_departments='+id_departments+'&year='+year+'&week='+week,
			beforeSend: function () {
	        	showLoader();
	        },
	        success: function (data) {
	        	
	        	//alert(data);
	        	
	        	if($.trim(data)=='') {
	        		document.getElementById("frmPlanner").submit();
	  				document.forms["frmPlanner"].submit();
	  				document.frmPlanner.submit();
	        	}
	        	else {
	        		
	        		hideLoader();
	        		
	        		if(confirm(data)) {
	        			
	        			showLoader();
	        			
	        			document.getElementById("frmPlanner").submit();
		  				document.forms["frmPlanner"].submit();
		  				document.frmPlanner.submit();
	        			
	        		}
	        		else {
	        			// do nothing
	        		}
	        		//hideLoader();
	        		//alert(data);
	        	}
	        	
	        },
	        error: function () {
	        	hideLoader();
	        	alert('Errore! si e\' verificato un errore.');
	        }
	    });
		
  	}
  	
  	
  	function controllaTipo(tipo) {
  		
  		
  		//if(tipo=="575ed403-8924-4345-bf86-d2bf0534afb6" || tipo=="afc1e7c2-d84a-461b-a567-ce8771b8c6aa") {
  		/*
  		if(tipo=="575ed403-8924-4345-bf86-d2bf0534afb6") {
  		
  			$('#addPlan_ora_ini').val('07:00');
  			$('#addPlan_ora_fin').val('23:00');
  			
  			$('#addPlan_ora_ini option:not(:selected)').attr('disabled', true);
  			$('#addPlan_ora_fin option:not(:selected)').attr('disabled', true);
  			
  		}else {
  			$('#addPlan_ora_ini').val('');
  			$('#addPlan_ora_fin').val('');
  			
  			$('#addPlan_ora_ini option:not(:selected)').attr('disabled', false);
  			$('#addPlan_ora_fin option:not(:selected)').attr('disabled', false);
  		}
  		*/
  	}
  	
  	var iddep = $('#id_departments').val();
  	var idweek = $('#week').val();
  	var title = '';
 	title = $("#id_departments option[value='"+iddep+"']").text()+" "+$('#year').val()+" "+$("#week option[value='"+idweek+"']").text(); 	
  	
  	$('#title').append(" - "+title);
  	
  	
  	
  	
  	
 	// ========================
  	// Aggiunta Slider (con orario)
  	function confirmAddSliderGeneral(day , idemployee, oraIni, oraFin, tipo) {
 		
 		var sDay = ""+day;
 		
  		var myarray = sDay.split(',');
  		for(var i = 0; i < myarray.length; i++)
  		{
	 		//alert(myarray[i]);
	 		
	 		var dt = ""+myarray[i];
	 		
	 		var dateParts = dt.split("/");
	 		var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
	
	 		var n = dateObject.getDay()-1;
	  	    
	  	    day = n;
	  	    
	 		var sValues = '';
	 		try {
	 			
	 			console.log("svalues: ."+day+""+idemployee);
	 			
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
		  		var newIni = parseFloat(oraIni);
		  		var newFin = parseFloat(oraFin);
		  		
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
			  		try{
		  				$( "."+day+""+idemployee ).limitslider( "destroy" );
			  		}
			  		catch(err) {
			  			console.log(err);
			  		}
		  			// Ricrea lo slider con nuovo valore
		  			$(function() {
			  	  		$( "."+day+""+idemployee ).limitslider({
			  				values:		temp ,
			  				showRanges: true,
			  				step:		0.5,
			  				gap:		0,
			  				min:		6,
			  				max:		24,
			  				label:		false,
			  				change: function( event, ui ) {
			  					reloadInputs(day , idemployee);
							},
			  				ranges:		[false, { styleClass: ''+tipo+'' }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false, { styleClass: getCss(day , idemployee, '5') }, false]
			  		   	});
		  			});
		  			
		  			// Ricarica gli inputs
		  			reloadInputs(day , idemployee);
		  			
		  		}
		  		
	 		}
	 		// Altrimenti crea lo slider con valori di default
	 		else {
	 			
	 			//alert(oraIni+" "+oraFin);
	 			
	 			var lista = oraIni+','+oraFin;
	  			var temp = new Array();
	  			temp = lista.split(",");
		  		for (a in temp ) {
	  			    temp[a] = parseFloat(temp[a]);
	  			}
	 			
	 			// Ricrea lo slider con nuovo valore
	 			// Distrugge lo slider
		  		try{
	  				$( "."+day+""+idemployee ).limitslider( "destroy" );
		  		}
		  		catch(err) {
		  			console.log(err);
		  		}
	 			$(function() {
		  	  		$( "."+day+""+idemployee ).limitslider({
		  				values:		temp,
		  				showRanges: true,
		  				step:		0.5,
		  				gap:		0,
		  				min:		6,
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
  	}
  	
 	// Cancella con Ajax
 	// ========================
  	function reloadSliderGeneral(day , idemployee) {
 		
  		$( "."+day+""+idemployee ).limitslider( "destroy" );
		$( "."+day+""+idemployee ).empty();
		$('input[name=p_dati'+day+''+idemployee+']').remove();
		
 		
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
	  			
  			var lista = sValues.toString();
  			var temp = new Array();
  			temp = lista.split(",");
	  		for (a in temp ) {
  			    temp[a] = parseFloat(temp[a]);
  			}
  			
	  		// Distrugge lo slider
	  		try {
  				$( "."+day+""+idemployee ).limitslider( "destroy" );
	  		}
	  		catch(err) {}
	  		
  			// Ricrea lo slider con nuovo valore
  			$(function() {
	  	  		$( "."+day+""+idemployee ).limitslider({
	  				values:		temp ,
	  				showRanges: true,
	  				step:		0.5,
	  				gap:		0,
	  				min:		6,
	  				max:		24,
	  				label:		false,
	  				change: function( event, ui ) {
	  					reloadInputs(day , idemployee);
					},
	  				ranges:		[false, { styleClass: getCss(day , idemployee, '0') }, false, { styleClass: getCss(day , idemployee, '1') }, false, { styleClass: getCss(day , idemployee, '2') }, false, { styleClass: getCss(day , idemployee, '3') }, false, { styleClass: getCss(day , idemployee, '4') }, false]
	  		   	});
  			});
  			
  			// Ricarica gli inputs
  			reloadInputs(day , idemployee);
	  		
 		}
 		// Altrimenti distrugge lo slider
 		else {
 		
 			$( "."+day+""+idemployee ).limitslider( "destroy" );
 			
 		}
		
  	}
 	
 	function setEye() {
 		var eye = $('#eye').val();
 		
 		// Ribalto eye
 		if(eye=='') {
 			 $('#eye').val('P');
 		}
 		else {
 			 $('#eye').val('');
 		}
 		
 		$('.eye').toggle();
 		
 	}
 	
 	function mostraNascondiTop() {
 		//$('#task_flyout').toggle();
 		
 		$('#task_flyout').toggle(function() {
 		    if($(this).is(':hidden')) { 
 		        $('#bjax-target').removeClass('mar50');
 		    }
 		    else {
 		    	$('#bjax-target').addClass('mar50');
 		    }
 		}); 
 	}
 	
 	if('${tp}'=='A') {
 		
 		$('#eye').val('P');
 		$('.eye').hide();
 	}
 	
 	function elementOrParentIsFixed(element) {
 	    var $element = $(element);
 	    var $checkElements = $element.add($element.parents());
 	    var isFixed = false;
 	    $checkElements.each(function(){
 	        if ($(this).css("position") === "fixed") {
 	            isFixed = true;
 	            return false;
 	        }
 	    });
 	    return isFixed;
 	}
 	
 	function mostraNascondiBottom() {
 		var el = $( ".totaliGiorno:first" );
 		var isFixed = elementOrParentIsFixed(el);
 		var largh = ($( window ).width()-162);
 		var largh2 = $('.totaliGiorno:first').width();
 		//alert(largh2);
 		
 		$('.totaliGiorno').css("width", largh2);
 		
 		if (isFixed) {
 			//$('.totaliGiorno').removeAttr( 'style' );
 			$('.totaliGiorno').css("position", "").css("bottom", "").css("width:", "").css("background", "").css("z-index", ""); 
 			//$('#bjax-target').css('margin-bottom', '50px');
 			$('.spacer').css('height', '0px');
 			$('.barra').css('width', '');
 		}
 		else {
 			$('.totaliGiorno').css("position", "fixed").css("bottom", "0").css("width:", largh2).css("background", "#fff").css("z-index", "100"); 
 			//$('#bjax-target').css('margin-bottom', '160px');
 			
 			$('.spacer').css('height', '100px');
 			$('.barra').css('width', ''+(largh2-330)+'');
 	 		
 		}
 		
 		//$('.totaliGiorno').css("position", "fixed").css("bottom", "0").css("width:", "100%").css("background", "#fff"); 
 		
 		
 	}
 	
 	function renderRighello(day , idemployee) {
 		
 		// Righello + input ore
  		var id='';
  	 	for (var i = 0; i <= 18; i+=0.5) {
  	      	
  	 		id = ""+((i+5)+1);
  	 		id = id.replace(/\./g,"");
  	 		
  	    	if(isInt(i)) {
  	    		var el = $('<label class="ruler'+day+''+idemployee+''+id+' ruler">'+((i+5)+1)+'</label>').css('left',(i/18*100)+'%');
  	    		var inp = $('<input class="'+day+''+idemployee+''+id+'" type="hidden" id="'+day+''+idemployee+''+id+'" name="day'+day+''+id+'" value="0">');
  	    		var inp2 = $('<input class="'+day+''+idemployee+'_X'+id+'" type="hidden" id="'+day+''+idemployee+'_X'+id+'" name="day'+day+'_X'+id+'" value="0">');
  	    	}
  	    	else {
  	    		var el = $('<label class="ruler'+day+''+idemployee+''+id+' ruler"><span style="font-size:8px">&times;</span></label>').css('left',(i/18*100)+'%');
  	    		var inp2 = $('<input class="'+day+''+idemployee+'_X'+id+'" type="hidden" id="'+day+''+idemployee+'_X'+id+'" name="day'+day+'_X'+id+'" value="0">');
  	  	    	
  	    	}
  	    	$( "."+day+""+idemployee+"ruler" ).append(el);
  	    	
  	    	$( "."+day+""+idemployee+"tempi" ).append(inp);
  	    	$( "."+day+""+idemployee+"tempi" ).append(inp2);
  	    	
  	    }
  	    
 	}
 	
 	function setRighello(day , idemployee) {
 		
 		// Righello + input ore
  		var id='';
  	 	for (var i = 0; i <= 18; i+=0.5) {
  	      	
  	 		id = ""+((i+5)+1);
  	 		id = id.replace(/\./g,"");
  	 		
  	    	if(isInt(i)) {
  	    		var el = $('<label class="ruler'+day+''+idemployee+''+id+' ruler">'+((i+5)+1)+'</label>').css('left',(i/18*100)+'%');
  	    		var inp = $('<input class="'+day+''+idemployee+''+id+'" type="hidden" id="'+day+''+idemployee+''+id+'" name="day'+day+''+id+'" value="0">');
  	    		var inp2 = $('<input class="'+day+''+idemployee+'_X'+id+'" type="hidden" id="'+day+''+idemployee+'_X'+id+'" name="day'+day+'_X'+id+'" value="0">');
  	    	}
  	    	else {
  	    		var el = $('<label class="ruler'+day+''+idemployee+''+id+' ruler"><span style="font-size:8px">&times;</span></label>').css('left',(i/18*100)+'%');
  	    		var inp2 = $('<input class="'+day+''+idemployee+'_X'+id+'" type="hidden" id="'+day+''+idemployee+'_X'+id+'" name="day'+day+'_X'+id+'" value="0">');
  	  	    }
  	    	$( "."+day+""+idemployee+"ruler" ).append(el);
  	    	
  	    	$( "."+day+""+idemployee+"tempi" ).append(inp);
  	    	$( "."+day+""+idemployee+"tempi" ).append(inp2);
  	    	
  	    }
 		
 	}
  </script>
  
  </body>
</html>
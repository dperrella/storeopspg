<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"  %>
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
  <link rel="stylesheet" href="../../js/dist/css/bootstrap-select.css">
  
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
	    margin-top: 0px;
	    color: #c0c0c0;
	    font-size:12px;
	}
	.ruler2 {
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
	    heigth: 50px;
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
        <%@ include file="../../inc2/menuApp7.jsp" %>
        <section id="content">
          
          	  	<header id="task_flyout" class="header bg-light dk">
              		  	  <input type="hidden" id="id_departments" name="id_departments_vid">
					  	  <input type="hidden" id="year" name="year_vid" >
					      <input type="hidden" id="week" name="week_vid">
					    
						  <div class="text-center" style="padding-top:10px">
						  	
						  	<a href="planner-ric.htm?tp=${tp}" class="btn btn-default m-b-sm"><%=propLang.get("btnSearch") %></a>
						  	
						  	<a href="#" onclick="setEye()" style="width:40px" data-toggle="class:nav-xs,nav-xs" class="btn btn-default m-b-sm">
						  		<i class="fa fa-eye text-active"></i>
				           		<i class="fa fa-eye-slash text"></i></a>
						  	
						  	<a href="../../hr/popup/addPlanification.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&tp=${tp}" 
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
						  		<a href="../../hr/popup/planner-note.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&status=Y&tp=${tp}" 
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
					      	<a href="../../hr/popup/planification_note.jsp?id_departments=${id_departments}&year=${plan_year}&week=${plan_week}&tp=${tp}" 
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
		               
						
						<section class="panel panel-default">
		                    <header class="panel-heading bg-light">
		                      <ul class="nav nav-tabs nav-justified">
		                        
		                        <c:forEach items="${giorni}" var="giorni" varStatus="status">
		                        <li ${giorni.cmp2}><a href="#day${giorni.xcdel}" ${giorni.cmp3} data-toggle="tab">
		                        	<span style="font-size:10px">${giorni.xdtab} <b>${giorni.cmp1}</b></span></a></li>
		                        </c:forEach>
		                        
		                        <li><a href="#dayTotal" data-toggle="tab" onclick="loadTot()"><%=propLang.get("totali") %></a></li>
		                      </ul>
		                    </header>
		                    <div class="panel-body" style="padding:0px;margin:0px">
		                      <div class="tab-content">
		                        <div class="tab-pane active" id="day0">
		                        	
		                        	<div align="center" id="dayLoader0">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay0" name="caricoDay0" value="N"/>
									</div>
									<div id="dayy0">
		                        	<div class="table-responsive">
						               	<table class="table b-t b-light" style="padding:2px;">
						                 
									    	<tbody id="body-table">
									    
									    		<c:forEach items="${plan2}" var="emp" varStatus="status">
									    		
									    			<tr id="row0${emp.id_employee}" class="${emp.eyeClass}">
									    				
									    				<td class="b-r" style="width:40px;border-bottom:2px solid #77848F">
													    	<div id="status0${emp.id_employee}">${s}</div>
													    </td>
													    <td class="b-r" style="width:120px; border-bottom:2px solid #77848F">
															<div style="width:120px;" class="text-ellipsis">${emp.name} ${emp.surname}</div>
															
															<input type="hidden" name="id_employee0" value="${emp.id_employee}"/>
															<input type="hidden" id="id_departments0${emp.id_employee}" name="id_departments0${emp.id_employee}" value="${emp.id_dep}"/>
															<input type="text" id="ds_departments0${emp.id_employee}" name="ds_departments0${emp.id_employee}" 
																style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="${emp.ds_dep}" disabled/>
															
															<div id="periods0${emp.id_employee}">${emp.inputs}</div>
															
														</td>
														<!-- 
														<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F">
															<a href="../../app/popup/departments_planner.jsp?iddest=id_departments0${emp.id_employee}&dsdest=ds_departments0${emp.id_employee}" 
																class="getModal btn btn-default btn-xs" data-toggle="tooltip"
																data-placement="top" title="" data-original-title="<%=propLang.get("cambiaDip") %>" ${dis}>
													      		T.</a>
														</td>
														-->
														<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F">
															<div class="btn-group-vertical">
															
															<!-- 
															<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
																onclick="confirmAddSlider('0', '${emp.id_employee}')"
																data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnAddL") %>" ${dis}><i class="icon-plus" style="margin-top:6px;margin-left:-6px"></i>
															</a>
															-->
															<a href="#" class="btn btn-default btn-block btn-xs"  data-toggle="tooltip" 
																onclick="confirmRemoveSlider('0', '${emp.id_employee}')"
																data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
																<i class="icon-close" style="margin-top:8px;margin-left:-6px"></i>
															</a>
															</div>
														</td>
														<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px; border-bottom:2px solid #77848F"">
															
															<div class="0${emp.id_employee}ruler ui-slider">${emp.ruler}</div>
															<br/>
															<div class="0${emp.id_employee}ruler2 ui-slider">${emp.day0}</div>
														    
														</td>
														
														<td class="b-l" style="width:60px; border-bottom:2px solid #77848F">
															<input type="text" name="totDip0${emp.id_employee}" id="totDip0${emp.id_employee}" 
																value="${emp.tot_day_emp}" style="width:50px;text-align:right; background: transparent; 
																border:0px;font-weight:bold;color:#000" readonly />
														</td>
											
														
									    			</tr>
									    		
									    		</c:forEach>
										
									    	</tbody>
									    
									  	</table>
									  
								  	</div>
								  	
								  	<div class="totaliGiorno">
						               <table class="table b-t b-light">
						                 <tfoot>
									      
									      <!-- Intestazioni -->
									      <tr>
									    	<td style="width:205px"></td>
									    	<td class="barra">
									    		<div style="height:13px;margin-top:-5px" class="0intestazioni ui-slider">${rulertot}</div>
									    	</td>
									    	<td style="width:60px"></td>
									      </tr>
									      
									      <!-- Totale x Fascia -->
									      <tr>
									    	<td style="width:205px"><b><%=propLang.get("tot1") %></b></td>
									    	<td class="barra">
									    		<div style="height:20px" class="0tot1 ui-slider">${tot1}</div>
									    	</td>
									    	<td style="width:60px"><input type="text" name="0totSett1" id="0totSett1" 
													value="${ttt1}" style="width:50px;text-align:right; background: transparent; 
													border:0px;font-weight:bold;color:#000" readonly /></td>
									      </tr>
									      <!-- Totale x Risorsa -->
									      <tr>
									    	<td style="width:205px"><b><%=propLang.get("tot2") %></b></td>
									    	<td class="barra">
									    		<div style="height:20px" class="0tot2 ui-slider">${tot2}</div>
									    	</td>
									    	<td style="width:60px"><input type="text" name="0totSett2" id="0totSett2" 
													value="${ttt2}" style="width:50px;text-align:right;background:transparent; 
													border:0px;font-weight:bold;color:#000" readonly /></td>
									      </tr>
									      <!-- Nomero tabella (da creare) -->
									      <tr>
									    	<td style="width:205px"><b><%=propLang.get("tot3") %></b></td>
									    	<td class="barra">
									    		<div style="height:20px" class="0tot3 ui-slider">${tot3}</div>
									    	</td>
									    	<td style="width:60px"><input type="text" name="totSett3" id="0totSett3" 
													value="${ttt3}" style="width:50px;text-align:right; background: transparent; 
													border:0px;font-weight:bold;color:#000" readonly /></td>
									      </tr>
									     </tfoot>
						               </table>
						            </div>
						            <div class="spacer"></div>
								    </div>
		                        </div>
		                        
		                        <div class="tab-pane" id="day1">
		                        	<div align="center" id="dayLoader1">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay1" name="caricoDay1" value="N"/>
									</div>
									<div id="dayy1"></div>
		                        </div>
		                        <div class="tab-pane" id="day2">
		                        	<div align="center" id="dayLoader2">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay2" name="caricoDay2" value="N"/>
									</div>
									<div id="dayy2"></div>
		                        </div>
		                        <div class="tab-pane" id="day3">
		                        	<div align="center" id="dayLoader3">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay3" name="caricoDay3" value="N"/>
									</div>
									<div id="dayy3"></div>
		                        </div>
		                        <div class="tab-pane" id="day4">
		                        	<div align="center" id="dayLoader4">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay4" name="caricoDay4" value="N"/>
									</div>
									<div id="dayy4"></div>
		                        </div>
		                        <div class="tab-pane" id="day5">
									<div align="center" id="dayLoader5">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay5" name="caricoDay5" value="N"/>
									</div>
									<div id="dayy5"></div>
								</div>
		                        <div class="tab-pane" id="day6">
		                        	<div align="center" id="dayLoader6">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDay6" name="caricoDay6" value="N"/>
									</div>
									<div id="dayy6"></div>
		                        </div>
		                        <div class="tab-pane" id="dayTotal">
		                        	
		                        	<div align="center" id="daytot">
									  	<img src="loader.gif">
									  	<input type="hidden" id="caricoDaytot" name="caricoDaytot" value="N"/>
									</div>
									
									<div id="tabletotal"></div>
						              
		                        </div>
		                      </div>
		                    </div>
		              	</section>
						
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
               
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            	  
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
						<a href="planner-ric.htm?tp=${tp}" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-settings"></i> 
                          <span class="hidden-nav-xs m-l-sm">${dsfun}</span>
                        </a>
                        
                        <a href="#" onclick="mostraNascondiTop()" style="padding-left: 30px" class="list-group-item">
					  		<i class="icon-equalizer"></i>
			           		<span class="hidden-nav-xs m-l-sm">Hide/Show</span>
			           		</a>
			           		
			           	<a href="#" onclick="mostraNascondiBottom()" style="padding-left: 30px" 
			           		class="list-group-item">
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
  
  <!-- Bootstrap-select -->
  <script src="../../js/dist/js/bootstrap-select.js"></script>
  
  
  <script>
  
  //alert("${heatmap}")
  
  $('#dayLoader0').hide();
  
  var ruler = '${ruler}';
  
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
	
  function getHeatMap() {
		
		var id_departments = $('#id_departments').val();
		var year = $('#year').val();
		var week = $('#week').val();
		
		$.ajax({
		      url: '../ajax/getHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'id_departments='+id_departments+'&year='+year+'&week='+week,
		      success: function (data) {
		    	  
				  var res = data.split('##@@');
				  var max=res[0];
				  var heatmap=res[1];
				  
				  console.log(heatmap);
				  				  
				  //alert(heatmap);
				  var array = JSON.parse(heatmap);
				  
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
				        max: max,
				        calculable: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        inRange: {
				            color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
				        }
				    },
				    series: [{
				        name: 'Employee',
				        type: 'heatmap',
				        data: array,
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
				  
				myChart2.setOption(option2);
				  
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
  
  	
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
  		
  		$('#addPlan_dep').selectpicker();

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
  		$.ajax({
	        url: 'planner-list-tab3Day.htm',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'year=${plan_year}&week=${plan_week}&day='+day+'&id_departments=${id_departments}&dis=${dis}&vis=${vis}&tpvis=${tpvis}&eye=${eye}&sts=${sts}',
	        beforeSend: function () {
	        	//$('#dayLoader'+day+'').show();
	        	//$("#dayy"+day).html('');
	        },
	        success: function (data) {
	        	$("#dayy"+day).html('');
	        	$('#dayLoader'+day+'').hide();
	        	$("#dayy"+day).html(data);
	        	
	        	var eye = $('#eye').val();
	        	if(eye=='P') {
	        		$('.eye').hide();
	        	}
	        	
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            //alert(xhr.status);
	            //alert(thrownError);
	        }
	    });
  	
  	}
  	
	function loadTot() {
  		
	  	$.ajax({
	        url: 'planner-list-tab3Tot.htm',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'year=${plan_year}&week=${plan_week}&id_departments=${id_departments}&dis=${dis}&vis=${vis}&tpvis=${tpvis}&eye=${eye}',
	        beforeSend: function () {
	        	$('#daytot').show();
	        	$('#tabletotal').html('');
	        },
	        success: function (data) {
	        	$('#daytot').hide();
	        	$("#tabletotal").html(data);
	        	
	        	var eye = $('#eye').val();
	        	if(eye=='P') {
	        		$('.eye').hide();
	        	}
	        	
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            //alert(xhr.status);
	            //alert(thrownError);
	        }
	    });
	  	
  	}
  	
  	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
	});
  	
  	// Fine NUOVA GESTIONE AJAX
  	// ====================
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
  	
  	// ========================
  	// Apertura Modal Rimozione Slider
	function confirmRemoveSlider(day , idemployee) {
  		
		var year = $('#year').val();
		var week = $('#week').val();
		var dataIta = $('#data'+day).val();
		
  		if(confirm("Confermi la rimozione dei turni della giornata per il dipendente?")) {
  			
  			$.ajax({
		        url: 'cancellaTurno2.jsp',
		        type: 'POST',
		        dataType: 'html',
		        async: false,
		        data: 'year='+year+'&week='+week+'&day='+day+'&id_employee='+idemployee,
				success: function (data) {
		        	// Caricamento turni ajax
  		      		loadDay(0);
  		      		getHeatMap();
		        },
		        error: function () {
		        	hideLoader();
		        	alert('Errore! si e\' verificato un errore.');
		        }
		    });
  			
  		}
  		
  	}
	
  	function isInt(n){
  	    return Number(n) === n && n % 1 === 0;
  	}

  	function isFloat(n){
  	    return Number(n) === n && n % 1 !== 0;
  	}
  	
  	$('#id_departments').val('${id_departments}');
  	$('#year').val('${plan_year}');
  	$('#week').val('${plan_week}');
  	
  	$('#id_departments2').val('${id_departments}');
  	$('#year2').val('${plan_year}');
  	$('#week2').val('${plan_week}');
  	
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
  		
  		var id_departments = $('#id_departments').val();
  		var year = $('#year').val();
		var week = $('#week').val();
  		var day = $('#addPlan_day').val();
  		var ora_ini = $('#addPlan_ora_ini').val();
  		var ora_fin = $('#addPlan_ora_fin').val();
  		var id_planification_type = $('#addPlan_id_planification_type').val();
  		var id_employee = $('#addPlan_id_employee').val();
  		var addPlan_dep = $('#addPlan_dep').val();
  		
  		if(addPlan_dep!='') {
  			id_departments = addPlan_dep;
  		}
  		
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
  		    
  		    var hhini = ora_ini.toString().replace(":30", ".5");
  			hhini = parseFloat(hhini);
  			var hhfin = ora_fin.toString().replace(":30", ".5");
			hhfin = parseFloat(hhfin);
			
			// Controlla se per il dipendente / turno ci sono sovrapposizioni
			var sovrappone = false;
			
			$.ajax({
  		        url: 'controllaTurno.jsp',
  		        type: 'POST',
  		        dataType: 'html',
  		        async: false,
  		        data: 'id_departments='+id_departments+'&year='+year+'&week='+week+'&day='+day+'&id_employee='+id_employee+'&id_planification_type='+id_planification_type+'&ora_ini='+ora_ini+'&ora_fin='+ora_fin,
  				success: function (data) {
  		        	
  		        	if(parseFloat($.trim(data))>0) {
  		        		sovrappone = true;
  		        	}
  		        	
  		        },
  		        error: function () {
  		        	hideLoader();
  		        	alert('Errore! si e\' verificato un errore.');
  		        }
  		    });
			
			var confirmSubmit = true;
			if(sovrappone) {
				
				if(confirm("Il turno si sovrappone ad uno gia' esistente.\nConfermando cancellerai il turno esistente.")) {
	  				confirmSubmit = true;
				}
				else {
					confirmSubmit = false;
				}
				
			}
			
			if(confirmSubmit) {
				
				$.ajax({
  	  		        url: 'addTurno.jsp',
  	  		        type: 'POST',
  	  		        dataType: 'html',
  	  		        async: true,
  	  		        data: 'id_departments='+id_departments+'&year='+year+'&week='+week+'&day='+day+'&id_employee='+id_employee+'&id_planification_type='+id_planification_type+'&ora_ini='+ora_ini+'&ora_fin='+ora_fin,
  	  				success: function (data) {
  	  		        	
  	  		        	$('.mia').remove(); $('div.modal-backdrop').remove();
  	  		        	
  	  		      		// Caricamento turni ajax
  	  		      		loadDay(0);
  	  		      		getHeatMap();
  	  		      		
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
  	
  	var iddep = $('#id_departments').val();
  	var idweek = $('#week').val();
  	var title = '';
 	title = $("#id_departments option[value='"+iddep+"']").text()+" "+$('#year').val()+" "+$("#week option[value='"+idweek+"']").text(); 	
  	
  	$('#title').append(" - "+title);
  	
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
 	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
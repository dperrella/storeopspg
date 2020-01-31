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
	  color:#fff;
	}
	.PLN {
	  background: #DFF0D8;
	  color: #629361;
	}
	.DIF {
	  background: #F2F4F8;
	}
	
	.TRA {
	  background: #f7ffe6;
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
            <%@ include file="../../inc2/menuApp7.jsp" %>
             
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
							          <option value="2018">2018</option>
							          <option value="2019">2019</option>
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
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						        
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("clo_seldip") %></label>
						          <input type="text" id="filter" class="form-control" placeholder="Filtra i risultati">
						          
						        </div>
						    </div>
						        						    
						  </div>
						  
			            </section>
                    	
		                
		                
		                
		                
	                 	
			            <!--  Dipendente -->  
						<c:forEach items="${emp}" var="emp" varStatus="status">
						  
						<section class="panel panel-default jcorgFilterTextParent">
			              <header class="panel-heading font-bold jcorgFilterTextChild">
			                <span>${emp.name} ${emp.surname} ${emp.cmp4}</span>
			              </header>
						  
						  <div class="table-responsive">
			               <table class="table b-t b-light">
			                
			                <tr>
			                    <th class="b-r" style="width:10%"><%=propLang.get("cnt_data") %></th>
			                    <th class="b-r" style="width:15%"><%=propLang.get("cnt_tipo") %></th>
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
							
							<!-- Totali -->
							${emp.province}
							
						  </footer>
			              
						</section>
						
			            </c:forEach>
			            
			            <!-- Totale Ore Store per settimana -->
			            <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			              <c:if test="${tp=='M'}">
			                <span><%=propLang.get("cnt_totMonth") %></span>
			              </c:if>
			              <c:if test="${tp=='D'}">
			              	<span><%=propLang.get("cnt_totWeek") %></span>
			              </c:if>
			              <c:if test="${tp==''}">
			                <span><%=propLang.get("cnt_totWeek") %></span>
			              </c:if>
			              
			              </header>
			              <div class="table-responsive">
			              	<table class="table table-striped b-t b-light">
			                	<tr>
			                    	<th class="b-r"><%=propLang.get("cnt_store") %></th>
			                    	<th class="b-r"><%=propLang.get("cnt_periodo") %></th>
			                    	
			                    	<th class="b-r">PLANNED</th>
			                    	<th class="b-r">WORKED</th>
			                    	<th class="b-r">BANCA ORE</th>
			                    	<th class="b-r">ROL</th>
			                    	<th class="b-r">MALATTIA</th>
			                    	<th class="b-r">FERIE</th>
			                    	<th class="b-r">LEGGE 104</th>
			                    	<th class="b-r">ASPETTATIVA</th>
			                    	<th class="b-r">NOTTURNO</th>
			                    	<th class="b-r">MATERNITA'</th>
			                    	<th class="b-r">RIPOSO COMPENSATIVO</th>
			                    	<th class="b-r">MALATTIA BIMBO < 3</th>
			                    	<th class="b-r">TRASFERTA</th>
			                    	<th class="b-r">MALATTIA BIMBO > 3</th>
			                    	<th class="b-r">CONGEDO MATRIMONIALE</th>
			                    	<th class="b-r">PERMESSO STUDIO</th>
			                    	<th class="b-r">DIFFERENCE</th>
			                    	
			                    </tr>
			                    <tbody>
			                    <tr>
			                    	<td><div class="department"></div></td>
			                    	<td><div class="empperiod"></div></td>
			                    	
			                    	${totaliDep}
			                    	
			                    </tr>
			                    </tbody>
			                </table>
			              </div>
			            </section>
			            
			            <!-- Totale per dipendente / settimana -->
			            <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <c:if test="${tp=='M'}">
				                <span><%=propLang.get("cnt_totDipMonth") %></span>
				            </c:if>
				            <c:if test="${tp=='D'}">
				              	<span><%=propLang.get("cnt_totDip") %></span>
				            </c:if>
				            <c:if test="${tp==''}">
				                <span><%=propLang.get("cnt_totDip") %></span>
				            </c:if>
			              </header>
			              <div class="table-responsive">
			              	<table class="table table-striped b-t b-light">
			                	<tr>
			                    	<th class="b-r"><%=propLang.get("cnt_dip") %></th>
			                    	<th class="b-r"><%=propLang.get("cnt_periodo") %></th>
			                    	
			                    	<th class="b-r">PLANNED</th>
			                    	<th class="b-r">WORKED</th>
			                    	<th class="b-r">BANCA ORE</th>
			                    	<th class="b-r">ROL</th>
			                    	<th class="b-r">MALATTIA</th>
			                    	<th class="b-r">FERIE</th>
			                    	<th class="b-r">LEGGE 104</th>
			                    	<th class="b-r">ASPETTATIVA</th>
			                    	<th class="b-r">NOTTURNO</th>
			                    	<th class="b-r">MATERNITA'</th>
			                    	<th class="b-r">RIPOSO COMPENSATIVO</th>
			                    	<th class="b-r">MALATTIA BIMBO < 3</th>
			                    	<th class="b-r">TRASFERTA</th>
			                    	<th class="b-r">MALATTIA BIMBO > 3</th>
			                    	<th class="b-r">CONGEDO MATRIMONIALE</th>
			                    	<th class="b-r">PERMESSO STUDIO</th>
			                    	<th class="b-r">DIFFERENCE</th>
			                    	
			                    	
			                    </tr>
			                    <tbody>
			                    <c:forEach items="${emp}" var="emp" varStatus="status">
			                    <tr>
			                    	<td>${emp.name} ${emp.surname}</td>
			                    	<td><div class="empperiod"></div></td>
			                    	
			                    	${emp.birthdate}
			                    	
			                    </tr>
			                    </c:forEach>
			                    </tbody>
			                </table>
			              
			              </div>
		  				
		  				</section>
		  				
		  				<c:if test="${tp=='M'}">
		  				<section class="panel panel-default">
			              <header class="panel-heading font-bold">
		  					<span><%=propLang.get("cnt_totWeek") %></span>
		  				  </header>
		  				  <div class="table-responsive">
		  				  	  <table class="table table-striped b-t b-light">
			                	<tr>
			                    	<th class="b-r">Week</th>
			                    	<th class="b-r">From</th>
			                    	<th class="b-r">To</th>
			                    	
			                    	<th class="b-r">PLANNED</th>
			                    	<th class="b-r">WORKED</th>
			                    	<th class="b-r">DIFFERENCE</th>
			                    	
			                    </tr>
			                    <tbody>
			                    <c:forEach items="${sett}" var="sett" varStatus="status">
			                    <tr>
			                    	<td>${sett.xcdtb}</td>
		  							<td>${sett.xcdel}</td>
		  							<td>${sett.xdtab}</td>
		  							
		  							${sett.cmp1}
		  							
		  						</tr>
		  						</c:forEach>
		  						</tbody>
		  					  </table>
		  				  </div>
		  				</section>
		  				</c:if>
		  				
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
 
  <script type="text/javascript" src="jcfilter.min.js"></script>
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
 	
 	
 	jQuery(document).ready(function(){
       jQuery("#filter").jcOnPageFilter({animateHideNShow: true,
                 focusOnLoad:true,
                 highlightColor:'yellow',
                 textColorForHighlights:'#000000',
                 caseSensitive:false,
                 hideNegatives:true,
                 parentLookupClass:'jcorgFilterTextParent',
                 childBlockClass:'jcorgFilterTextChild'});
    });
 	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
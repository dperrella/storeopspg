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
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
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
		z-index: 10000;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
	#ajaxModal {
		z-index: 10000;
	}
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
  </style>
</head>
<div id="loading-image" style="display:none">
		<img src="../../images/loading.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 208px;
				   height: 13px;
				   margin-top: -8px;
				   margin-left: -104px;" />
  </div>
  <div id="loading-image2" style="display:none">
		<img src="../../images/spinner.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 200px;
				   height: 200px;
				   margin-top: -100px;
				   margin-left: -50px;" />
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
		                
		                <!-- 
		                <form data-validate="parsley" id="planner-form" action="planner-list2.htm" method="post">
		                -->
		                
		                <form data-validate="parsley" id="planner-form" action="planner-list-tab2.htm" method="post">
		                
		                
		                <!-- 
		                <form data-validate="parsley" id="planner-form" action="planner-list-tab3.htm" method="post">
		                -->
		                <input type="hidden" name="tp" value="${tp}">
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                ${dsfun} 
			              </header>
			              
			              
			              <section class="panel wrapper" style="display: none;">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="planification-list.htm?q="><%=propLang.get("pla_cerca") %></a></li>
									<!-- <li><a href="planification-ric.htm"><%//=propLang.get("pla_cerca") %></a></li> -->
								</ul>
							</div>
						  </section>
			              
			              <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("pla_seldip") %></label>
						          <select id="id_departments" name="id_departments" data-required="true" 
						          	onchange="cambiaSettimana()" class="form-control m-t parsley-validated chosen-select" >
							          <option value="">-</option>
							          <c:forEach items="${dep}" var="dep" varStatus="status">
			                          <option value="${dep.id}">${dep.description}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-2">
						          <label><%=propLang.get("pla_year") %></label>
						          <select id="year" name="year" data-required="true" class="form-control parsley-validated" 
						          	onchange="cambiaSettimana()">
							          <option value="2015">2015</option>
							          <option value="2016">2016</option>
							          <option value="2017">2017</option>
							          <option value="2018">2018</option>
							          <option value="2019">2019</option>
							          <option value="2020">2020</option>
							          
			                      </select>   
						        </div>
						        
						        <div class="col-sm-10">
						          <label><%=propLang.get("pla_week") %></label>
						          <br/>
						          
						          <!-- 
						          <select id="week" name="week" data-required="true" class="form-control parsley-validated">
							          <option value="">-</option>
							          <c:forEach items="${weeks}" var="weeks" varStatus="status">
			                          <option style="background-color: ${weeks.cmp2}" value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
			                          </c:forEach>
	                        	  </select>   
	                        	  -->
	                        	  
	                        	  <c:if test = "${tp == 'A'}">
	                        	  <input type="hidden" id="week" name="week" value=""/>
	                        	  </c:if>
	                        	  
	                        	  <c:if test = "${tp == 'I'}">
	                        	  <input type="hidden" id="week" name="week" value="${currentWeek}"/>
	                        	  </c:if>
	                        	  
	                        	  
	                        	  
	                        	  
	                        	  
	                        	  <a href="#" class="btn btn-default btn-block" onclick="selWeek()" id="selweek"><%=propLang.get("pla_week") %></a>
						        </div>
						        
						    </div>
						    
						    <input type="hidden" name="vis" value="3"/>

						    <!-- 
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
		                        	<label><%=propLang.get("vis_week") %></label>
			                        <div class="radio i-checks">
				                      <label class="i-checks">
				                        <input type="radio" onclick="chgAction(this.value)"
				                        	id="vis1" name="vis" value="1"><i></i> <%=propLang.get("vis1") %>
				                      </label>
				                    </div>
				                    
				                    <div class="radio i-checks">
				                      <label class="i-checks">
				                        <input type="radio" onclick="chgAction(this.value)"
				                        	id="vis2" name="vis" value="2"><i></i> <%=propLang.get("vis2") %>
				                      </label>
				                    </div>
				                    
				                    <div class="radio i-checks">
				                      <label class="i-checks">
				                        <input type="radio" onclick="chgAction(this.value)"
				                        	id="vis3" name="vis" value="3"><i></i> <%=propLang.get("vis3") %>
				                      </label>
				                    </div>
				                </div>
				                
				                <div class="col-sm-4" id="div-tpvis">
		                        	<label><%=propLang.get("tpvis_week") %></label>
			                        <div class="radio i-checks">
				                      <label class="i-checks">
				                        <input type="radio"
				                        	id="tpvis1" name="tpvis" value="1"><i></i> <%=propLang.get("tpvis1") %>
				                      </label>
				                    </div>
				                    
				                    <div class="radio i-checks">
				                      <label class="i-checks">
				                        <input type="radio"
				                        	id="tpvis2" name="tpvis" value="2"><i></i> <%=propLang.get("tpvis2") %>
				                      </label>
				                    </div>
				                </div>
				            </div>
						    -->
						    						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<!-- 
					      	<button type="submit" id="submit2" style="width:200px" class="btn btn-success btn-lg"
					      		onsubmit="showLoader();"><%=propLang.get("btnConferma") %></button>
					      	-->
					      	<a href="#" onclick="newPlanner()" style="width:200px" class="btn btn-info btn-lg"><%=propLang.get("btnConferma") %></a>
					      </footer>
			             
			            </section>
                    	
                    	</form>
                    	
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
	                          <!--  --><button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
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
  
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
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
	function showLoader2() {
  	  jQuery('#loading-image2').show();
  	}
  	function hideLoader2() {
  	  jQuery('#loading-image2').hide();
  	}
  	
  	$('#year').val('${year}');
  	$('#id_departments').val('${id_departments}');
  	
  	function newPlanner() {
  		
  		$("#planner-form").attr("action", "../planner2/planner.htm");
  		
  		if($('#week').val()=="") {
  			alert("Selezionare una settimana");
  		}
  		else {
  			$("#planner-form").submit();
  		}
  		
  	}
  	
  	function cambiaSettimana() {
  		
  		
  		/*
  		showLoader();
  		
  		var anno = $('#year').val();
  		var id_departments = $('#id_departments').val();
  		
		javascript:window.location='planner-ric.htm?tp=${tp}&year='+anno+'&id_departments='+id_departments;
	  	window.location.href='planner-ric.htm?tp=${tp}&year='+anno+'&id_departments='+id_departments;
	  	*/
	  	
	}
  	
  	
  	function selWeek() {
  		
  		showLoader2();
  		var anno = $('#year').val();
  		var id_departments = $('#id_departments').val();
  		
  		href = '../../hr/popup/week.jsp?tp=${tp}&year='+anno+'&id_departments='+id_departments;
  		
  		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
				hideLoader2();
			});
		}
  	}
  	
  	function weeksel(msg, color) {
  		$('#selweek').text(msg);
  	}
  	
  	
  	var tp = '${tp}';
  	if(tp=='I') {
	  	var msg = '${currentWeek} - ${currentWeekDesc}';
	  	
	  	$('#selweek').text(msg);
  	}
  	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
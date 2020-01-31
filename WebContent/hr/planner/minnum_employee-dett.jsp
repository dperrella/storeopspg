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
  <title><%=propLang.get("min_dett") %> | SmartHR</title>
  <meta name="description" content="Desk Microtech" />
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
</head>
<body class="">
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
            <section>
              <section class="hbox stretch">
            	
            	<%@ include file="../../inc2/menuApp7.jsp" %>
                <section>
	              <section class="vbox">
	                <section class="scrollable padder-lg" id="bjax-target">
	                
	                
	                <div class="row row-sm">
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("min_dett") %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="minnum_employee-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("min_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="minnum_employee-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						    	<!-- 
						    	<div class="col-sm-4">
							        <label><%=propLang.get("min_iddep") %></label>
							        <input type="text" id="id_department" name="id_departments" class="form-control" value="${id_departments}" 
							        	readonly data-required="true">                        
						    	</div>
						    	-->
						    	
						    	<div class="col-sm-10">
						          <label><%=propLang.get("min_dsdep") %></label>
						          <input type="hidden" id="id_department" name="id_departments" class="form-control" value="${id_departments}" 
							        	readonly data-required="true">    
						          <input type="text" id="department_description" name="department_description" class="form-control" value="${description}" ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						        <label><br/></label>
			                      	<a href="../../hr/popup/departments.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
							      		<i class="fa fa-search"></i> Cerca</a>
			                    </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label><%=propLang.get("min_year") %></label>
							        <select id="year" name="year" data-required="true" class="form-control m-t parsley-validated" 
						          		onchange="cambiaSettimana()">
							          <option value="2015">2015</option>
							          <option value="2016">2016</option>
							          <option value="2017">2017</option>
							          <option value="2018">2018</option>
							          <option value="2019" selected>2019</option>
			                      </select>              
						    	</div>
						    	
						    	<div class="col-sm-4">
						          	<label><%=propLang.get("min_week") %></label>
						          	<select id="week" name="week" class="form-control m-t parsley-validated">
							          <option value="">Tutte (Default)</option>
							          <c:forEach items="${weeks}" var="weeks" varStatus="status">
			                          <option value="${weeks.xcdel}" ${weeks.cmp1}>Settimana ${weeks.xcdel}: ${weeks.xdtab}</option>
			                          </c:forEach>
		                        	</select>   
						        </div>
						          
						        <div class="col-sm-4">
						        	<label><%=propLang.get("min_day") %></label>
						        	<select id="day" name="day" class="form-control m-t parsley-validated">
						        		<option value="">Tutti (Default)</option>
						        		<option value="0"><%=propLang.get("lun") %></option>
						        		<option value="1"><%=propLang.get("mar") %></option>
						        		<option value="2"><%=propLang.get("mer") %></option>
						        		<option value="3"><%=propLang.get("gio") %></option>
						        		<option value="4"><%=propLang.get("ven") %></option>
						        		<option value="5"><%=propLang.get("sab") %></option>
						        		<option value="6"><%=propLang.get("dom") %></option>
						        	</select>
			                      	
			                    </div>
						    </div>
						  	
						  	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <c:forEach items="${minnum}" var="minnum" varStatus="status">
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-2">
							        <label><%=propLang.get("min_fascia") %></label>
							        <input type="text" name="plan_hour" class="form-control" value="${minnum.plan_hour}" readonly/>                 
						    	</div>
						    	
						    	<div class="col-sm-2">
						          	<label><%=propLang.get("min_valore") %></label>
						          	<input type="text" name="value" class="form-control" value="${minnum.value}" style="text-align:right"/>
						        </div>
						        
						        
						    </div>
						    
						    </c:forEach>
						  	
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            
			            
						 
						 
						</form>
			            
			            </div>
	                
	                </section>
	              </section>
	            </section>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%=propLang.get("min_dip_plan") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${emp}" var="emp" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="clear">
		                        	<div><a href="../employee/emp-dett.htm?id=${emp.id}&tipoForm=M">${emp.name} ${emp.surname}</a></div> 
		                        </div> 
		                      </li>
	                        </c:forEach>
	                          
	                        </ul>
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
				        
                        <a href="minnum_employee-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-user-following"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("min_min") %></span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="minnum_employee-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("min_cerca") %>">
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
  
  <script src="../../js/bootstrap-modal.js"></script>
  
  
  <script>
  	
	//MODAL
	$('.getModal').click(function(e) {
	
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		// Dipartimento
		if(href=="../../hr/popup/departments.jsp") {
			//href+="?department_description="+document.getElementById("department_description").value;
			href+="?department_description=";
		}
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
	
	$('#id_departments').val('${$id_departments}');
  	$('#year').val('${plan_year}');
  	$('#week').val('${plan_week}');
  	$('#day').val('${plan_day}');
  	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
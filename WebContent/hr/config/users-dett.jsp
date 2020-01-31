<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java" 
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
  <title><%=propLang.get("usr_dett") %> | SmartHR</title>
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
            <section id="bjax-target">
              <section class="hbox stretch">
            	
            	<%@ include file="../../inc2/menuApp7.jsp" %>
            	
                <aside class="col-sm-9 padder " >
	              <section class="vbox">
	                <section class="scrollable hover">
	                
	                <form data-validate="parsley" method="post" action="users-dett.htm">
        
	                	<h2 class="font-thin m-b"><%=propLang.get("usr_dett") %></h2>
		                
		                 
		                <input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("usr_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="users-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("usr_id") %></label>
						          <input type="text" id="idusr" name="idusr" class="form-control" 
						          	data-minlength="3" data-maxlength="25" data-required="true" value="${idusr}" ${readonly}>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_passwordDett") %></label>
						        <input type="password" id="pwd" name="pwd" class="form-control" value="${pwd}" data-minlength="3" data-required="true" ${readonly}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_nomeDett") %></label>
						        <input type="text" id="nome" name="nome" class="form-control" value="${nome}" data-minlength="3" data-maxlength="25" data-required="true">                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_cognDett") %></label>
						        <input type="text" id="cogn" name="cogn" class="form-control" value="${cogn}" data-minlength="3" data-maxlength="25" data-required="true">                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_tipoDett") %></label>
						        <select id="tipo" name="tipo" class="form-control" data-required="true">
						        	<option value="">-</option>
						        	<option value="D">Dipendente</option>
						        	<option value="S">Store Manager</option>
						        	<option value="M">Area Manager</option>
						        	<option value="H">HRM Manager</option>
						        	<option value="A">Administrator</option>
						        </select>                      
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_mailDett") %></label>
						        <input type="text" id="mail" name="mail" class="form-control" value="${mail}" data-type="email" data-maxlength="50" data-required="true">                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_lingua") %></label>
						        <select id="language" name="language" class="form-control" data-required="true">
						        	<option value="">-</option>
						        	<c:forEach items="${lang}" var="lang" varStatus="status">
						        	<option value="${lang.xcdel}">${lang.xcdel} - ${lang.xdtab}</option>
						        	</c:forEach>
						        </select>                      
						    </div>
						    
						    <c:if test="${tipoForm=='I'}">
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("usr_rel") %></span>
	                          </div>
		                    </div>
					        <br/> 
					        
					        <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label><%=propLang.get("usr_iddip") %></label>
							        <input type="text" id="id_employee" name="id_employee" class="form-control" value="" 
							        	readonly data-required="true">                        
						    	</div>
						    	
						    	<div class="col-sm-7">
						          <label><%=propLang.get("usr_desdip") %></label>
						          <input type="text" id="emp_description" name="emp_description" class="form-control" value="">   
						        </div>
						        
						        <div class="col-sm-1">
						        <label><br/></label>
			                      	<a href="../../hr/popup/employee.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
							      		<i class="fa fa-search"></i></a>
			                    </div>
						    </div>
						    
						    </c:if>
						    
						  </div>
						  
						  
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            
			            
						 
						 
						
			            
	                
	                </section>
	              </section>
	            </aside>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%=propLang.get("usr_aut") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${fun}" var="fun" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="pull-left m-l-xs m-r-sm">
									
									<input type="hidden" name="nrRiga" value="${fun.cmp2}" />
									<input type="hidden" name="cdfun" value="${fun.cdfun}" />
									<div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" name="post${fun.cmp2}" id="post${fun.cmp2}" data-required="true" ${fun.cmp1}>
			                            <i></i>
			                          </label>
			                        </div>

		                        </div> 
		                        <div class="clear">
		                        	<div><a href="#">${fun.cdfun}</a></div> 
		                       		<small class="text-muted">${fun.descr}</small> 
		                        </div> 
		                        
			                  </li>
	                        </c:forEach>
	                          
	                        </ul>
                        </div>
                    </section>
                  </section>
                </section>
                
                </form>
			            
                
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
				        
                        <a href="users-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-users"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("usr_utenti") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="users-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("usr_cerca") %>">
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
			href+="?department_description="+document.getElementById("department_description").value;
		}
		
		// Employee
		if(href=="../../hr/popup/employee.jsp") {
			href+="?emp_description="+document.getElementById("emp_description").value;
		}
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
	
  
  $('#tipo').val('${tipo}');
  $('#language').val('${language}');
  
  
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
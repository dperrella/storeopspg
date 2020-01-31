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
  <title><%=propLang.get("reb_dett") %> | SmartHR</title>
  <meta name="description" content="Desk Microtech" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/datepicker.css" type="text/css" />
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
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("reb_dett") %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="relempbad-dett.htm" 
		                	onsubmit="return control(this)">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("reb_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="relempbad-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("emp_codice") %></label>
						          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label><%=propLang.get("dtfin") %></label>
							        <input class="datepicker-input form-control" name="date_end" id="date_end" 
							        	type="text" value="${date_end}" data-date-format="dd-mm-yyyy" ${btnActive}>                   
						    	</div>						   
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<!-- 
						    	<div class="col-sm-4">
							        <label><%=propLang.get("dep_iddip") %></label>
							        <input type="text" id="id_employee" name="id_employee" class="form-control" value="${id_employee}" readonly data-required="true">                        
						    	</div>
						    	-->
						    	
						    	<div class="col-sm-10">
						          <label><%=propLang.get("pla_dip") %></label>
						          <input type="hidden" id="id_employee" name="id_employee" class="form-control" value="${id_employee}" readonly data-required="true">                        
						    	  <input type="text" id="emp_description" name="emp_description" class="form-control" value="${emp_description}" readonly ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						        <label><br/></label>
			                      	<a href="../../hr/popup/employee.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
							      		<i class="fa fa-search"></i> Cerca</a>
			                    </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<!-- 
						    	<div class="col-sm-4">
							        <label><%=propLang.get("emp_idbadge") %></label>
							        <input type="text" id="id_badge" name="id_badge" class="form-control" value="${id_badge}" readonly data-required="true">                        
						    	</div>
						    	-->
						    	<div class="col-sm-4">
							        <label><%=propLang.get("emp_idbadge") %></label>
							        <input type="hidden" id="id_badge" name="id_badge" class="form-control" value="${id_badge}" readonly data-required="true"> 
							        <input type="text" id="nr_badge" name="nr_badge" class="form-control" value="${badge_number}" readonly data-required="true">                        
						    	</div>
						    	
						        <div class="col-sm-2">
						        <label><br/></label>
			                      	<a href="../../hr/popup/badges.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
							      		<i class="fa fa-search"></i> Cerca</a>
			                    </div>
						    </div>
						    
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
	                     	<p class="h4 font-thin"><%=propLang.get("emp_dsfun") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${emp}" var="emp" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="clear">
		                        	<div><a href="../employee/emp-dett.htm?id=${emp.id}&tipoForm=V">${emp.name} ${emp.surname}</a></div> 
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
				        
                        <a href="relempbad-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-share"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("reb_reb") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="relempbad-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("reb_cerca") %>">
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
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
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
		
		// Employee
		if(href=="../../hr/popup/employee.jsp") {
			//href+="?emp_description="+document.getElementById("emp_description").value;
			href+="?emp_description=";
		}
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
	
	function control(frm) {
		
		var id_employee = $('#id_employee').val();
		var id_badge = $('#id_badge').val();
		
		// Controlla se il badge è già associato
		var err = "";
		
		$.ajax({
	        url: 'chkBadge.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: false,
	        data: 'id_employee='+id_employee+'&id_badge='+id_badge,
	        beforeSend: function () {
	        	
	        },
	        success: function (data) {

				err = data;
	        	
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            alert(xhr.status);
	            alert(thrownError);
	        }
	    });
		
		/*
		if(err!='') {
			
			
			if(confirm(err)) {
				return true;
			}
			else {
				return false;
			}
			
		}
		else {
			return true;
		}
		
		return false;
		*/
		
		return true;
	}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
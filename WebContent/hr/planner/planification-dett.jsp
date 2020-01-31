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
  <title><%=propLang.get("pla_dett") %> | SmartHR</title>
  <meta name="description" content="Desk Microtech" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
  <link type="text/css" href="bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
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
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("pla_dett") %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="planification-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("pla_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="planification-ric.htm"><%=propLang.get("pla_cerca") %></a></li>
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
							        <label><%=propLang.get("pla_data") %></label>
							        <input class="datepicker-input form-control" name="day" id="day" 
							        	type="text" value="${day}" data-date-format="dd-mm-yyyy" data-required="true" ${btnActive}>                   
						    	</div>
						    	
						    	<div class="col-sm-2">
						          <label><%=propLang.get("pla_orada") %></label>
						          <select id="ora_ini" name="ora_ini" class="form-control" data-required="true" ${btnActive}>
						          	<option value="">-</option>
						          	<option value="07:00">07:00</option>
						          	<option value="07:30">07:30</option>
						          	<option value="08:00">08:00</option>
						          	<option value="08:30">08:30</option>
						          	<option value="09:00">09:00</option>
						          	<option value="09:30">09:30</option>
						          	<option value="10:00">10:00</option>
						          	<option value="10:30">10:30</option>
						          	<option value="11:00">11:00</option>
						          	<option value="11:30">11:30</option>
						          	<option value="12:00">12:00</option>
						          	<option value="12:30">12:30</option>
						          	<option value="13:00">13:00</option>
						          	<option value="13:30">13:30</option>
						          	<option value="14:00">14:00</option>
						          	<option value="14:30">14:30</option>
						          	<option value="15:00">15:00</option>
						          	<option value="15:30">15:30</option>
						          	<option value="16:00">16:00</option>
						          	<option value="16:30">16:30</option>
						          	<option value="17:00">17:00</option>
						          	<option value="17:30">17:30</option>
						          	<option value="18:00">18:00</option>
						          	<option value="18:30">18:30</option>
						          	<option value="19:00">19:00</option>
						          	<option value="19:30">19:30</option>
						          	<option value="20:00">20:00</option>
						          	<option value="20:30">20:30</option>
						          	<option value="21:00">21:00</option>
						          	<option value="21:30">21:30</option>
						          	<option value="22:00">22:00</option>
						          	<option value="22:30">22:30</option>
						          	<option value="23:00">23:00</option>
						          	<option value="23:30">23:30</option>
						          	<option value="24:00">24:00</option>
						          </select>
						        </div>
						        
						        <div class="col-sm-2">
						          <label><%=propLang.get("pla_oraal") %></label>
						          <select id="ora_fin" name="ora_fin" class="form-control" data-required="true" ${btnActive} >
						          	<option value="">-</option>
						          	<option value="07:00">07:00</option>
						          	<option value="07:30">07:30</option>
						          	<option value="08:00">08:00</option>
						          	<option value="08:30">08:30</option>
						          	<option value="09:00">09:00</option>
						          	<option value="09:30">09:30</option>
						          	<option value="10:00">10:00</option>
						          	<option value="10:30">10:30</option>
						          	<option value="11:00">11:00</option>
						          	<option value="11:30">11:30</option>
						          	<option value="12:00">12:00</option>
						          	<option value="12:30">12:30</option>
						          	<option value="13:00">13:00</option>
						          	<option value="13:30">13:30</option>
						          	<option value="14:00">14:00</option>
						          	<option value="14:30">14:30</option>
						          	<option value="15:00">15:00</option>
						          	<option value="15:30">15:30</option>
						          	<option value="16:00">16:00</option>
						          	<option value="16:30">16:30</option>
						          	<option value="17:00">17:00</option>
						          	<option value="17:30">17:30</option>
						          	<option value="18:00">18:00</option>
						          	<option value="18:30">18:30</option>
						          	<option value="19:00">19:00</option>
						          	<option value="19:30">19:30</option>
						          	<option value="20:00">20:00</option>
						          	<option value="20:30">20:30</option>
						          	<option value="21:00">21:00</option>
						          	<option value="21:30">21:30</option>
						          	<option value="22:00">22:00</option>
						          	<option value="22:30">22:30</option>
						          	<option value="23:00">23:00</option>
						          	<option value="23:30">23:30</option>
						          	<option value="24:00">24:00</option>
						          </select>
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("pla_tipo") %></label>
						          
						          <select id="id_planification_type" name="id_planification" 
						          	class="form-control m-b chosen-select" onchange="controllaTipo(this.value)" data-required="true" ${btnActive}>
							        <option value="">-</option>
							        <c:forEach items="${plantype}" var="plantype" varStatus="status">
			                        <option value="${plantype.id}">${plantype.description}</option>
			                        </c:forEach>
	                        	  </select>  
	                        	    
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<!-- 
						    	<div class="col-sm-4">
							        <label><%=propLang.get("emp_iddep") %></label>
							        <input type="text" id="id_department" name="id_departments" class="form-control" value="${id_departments}" readonly data-required="true">                        
						    	</div>
						    	-->
						    	
						    	<div class="col-sm-10">
						          <label><%=propLang.get("emp_dsdep") %></label>
						          <input type="hidden" id="id_department" name="id_departments" class="form-control" value="${id_departments}" readonly data-required="true">                        
						    	  <input type="text" id="department_description" name="department_description" class="form-control" value="${department_description}" ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						        <label><br/></label>
			                      	<a href="../../hr/popup/departments.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
							      		<i class="fa fa-search"></i> Cerca</a>
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
						          <label><%=propLang.get("dep_dsdip") %></label>
						          <input type="hidden" id="id_employee" name="id_employee" class="form-control" value="${id_employee}" readonly data-required="true">                        
						    	  <input type="text" id="emp_description" name="emp_description" class="form-control" value="${name} ${surname}" ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						        <label><br/></label>
			                      	<a href="../../hr/popup/employee.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
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
	                     	<p class="h4 font-thin"><%=propLang.get("min_dip_plan") %></p>
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
				        
                        <a href="planification-ric.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-magnifier"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("pla_cerca") %></span>
                        </a>
                        <a href="planification-dett.htm?tipoForm=I" style="padding-left: 30px" class="list-group-item">
                          <i class="icon-note"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("pla_new") %></span>
                        </a>						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="planification-list.htm" class="" role="search">
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
	
	//Setta il valore dei campi
	$('#id_planification_type').val('${id_planification_type}');
	$('#ora_ini').val('${ora_ini}');
	$('#ora_fin').val('${ora_fin}');
	
  </script>
  
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  <!--  Timepicker -->
  <script type="text/javascript" src="bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
  
  <script type="text/javascript">
  $(function() {
    $('#div_ini').datetimepicker({
      pickDate: false,
      pickSeconds: false
    });
    
    $('#div_fin').datetimepicker({
        pickDate: false,
        pickSeconds: false
      });
  });
  
  function controllaTipo(tipo) {
		
		if(tipo=="575ed403-8924-4345-bf86-d2bf0534afb6" || tipo=="afc1e7c2-d84a-461b-a567-ce8771b8c6aa") {
			$('#ora_ini').val('07:00');
			$('#ora_fin').val('23:00');
			
			$('#ora_ini option:not(:selected)').attr('disabled', true);
			$('#ora_fin option:not(:selected)').attr('disabled', true);
			
		}else {
			$('#ora_ini').val('');
			$('#ora_fin').val('');
			
			$('#ora_ini option:not(:selected)').attr('disabled', false);
			$('#ora_fin option:not(:selected)').attr('disabled', false);
		}
			
	}
</script>
<%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
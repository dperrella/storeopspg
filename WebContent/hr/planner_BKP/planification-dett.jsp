<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>Dettaglio Pianificazione | SmartHR</title>
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
            <input type="text"  name="q" class="form-control input-sm no-border rounded" placeholder="Cerca in SmartHR...">
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
                <a href="../../app/main/profilo.htm">Modifica Profilo</a>
              </li>
              <li>
                <a href="../main/notifiche.htm">
                  <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                  Notifiche
                </a>
              </li>
              <li>
                <a href="../../app/main/docs.html">Guida</a>
              </li>
              <li class="divider"></li>
              <li>
                <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal">Esci</a>
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
                
                <%@ include file="../../inc/menu-left.jsp" %>
                
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
                        <a href="../../app/main/profilo.htm">Visualizza Profilo</a>
                      </li>
                      <li>
                        <a href="../main/notifiche.htm">
                          <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                          Notifiche
                        </a>
                      </li>
                      <li>
                        <a href="../../app/main/docs.html">Giuda</a>
                      </li>
                      <li class="divider"></li>
                      <li>
                        <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" >Esci</a>
                      </li>
                    </ul>
                  </div>
                </div>            
			</footer>
			
          </section>
        </aside>
        <!-- /.aside -->
        
        
        
        <section id="content">
          <section class="vbox">          
                  		     		
            <section class="hbox stretch" id="bjax-target">
            	
            	
                <aside class="col-sm-9 padder-lg scrollable" >
	              <section class="vbox">
	                <section>
	                
	                	<h2 class="font-thin m-b">${dsfun}</h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="planification-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                ${dsfun} 
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="planification-list.htm?id_departments=${req_id_departments}&week=${req_week}&year=${req_year}">Torna alla lista</a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Codice</label>
						          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label>Data</label>
							        <input class="datepicker-input form-control" name="day" id="day" 
							        	type="text" value="${day}" data-date-format="dd-mm-yyyy" data-required="true">                   
						    	</div>
						    	
						    	<div class="col-sm-2">
						          <label>Ora Inizio</label>
						          
						          <div id="div_ini" class="input-group input-append">
						          	<input id="ora_ini" name="ora_ini" data-format="hh:mm" class="form-control" type="text" value="${ora_ini}"></input>
								    <span class="input-group-addon add-on">
								      <i data-time-icon="icon-clock" data-date-icon="icon-calendar">
								      </i>
								    </span>
								  </div>
						          
						        </div>
						        
						        <div class="col-sm-2">
						          <label>Ora Fine</label>
						          <div id="div_fin" class="input-group input-append">
						          	<input id="ora_fin" name="ora_fin" data-format="hh:mm" class="form-control" type="text" value="${ora_fin}"></input>
								    <span class="input-group-addon add-on">
								      <i data-time-icon="icon-clock" data-date-icon="icon-calendar">
								      </i>
								    </span>
								  </div>  
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Tipo pianificazione</label>
						          
						          <select id="id_planification_type" name="id_planification" 
						          	class="form-control m-b chosen-select" data-required="true">
							        <option value="">-</option>
							        <c:forEach items="${plantype}" var="plantype" varStatus="status">
			                        <option value="${plantype.id}">${plantype.description}</option>
			                        </c:forEach>
	                        	  </select>  
	                        	    
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label>Id Dipartimento</label>
							        <input type="text" id="id_department" name="id_departments" class="form-control" value="${id_departments}" readonly data-required="true">                        
						    	</div>
						    	
						    	<div class="col-sm-7">
						          <label>Descrizione</label>
						          <input type="text" id="department_description" name="department_description" class="form-control" value="${department_description}">   
						        </div>
						        
						        <div class="col-sm-1">
						        <label><br/></label>
			                      	<a href="../../app/popup/departments.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
							      		<i class="fa fa-search"></i></a>
			                    </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label>Id Dipendente</label>
							        <input type="text" id="id_employee" name="id_employee" class="form-control" value="${id_employee}" readonly data-required="true">                        
						    	</div>
						    	
						    	<div class="col-sm-7">
						          <label>Nome / Cognome</label>
						          <input type="text" id="emp_description" name="emp_description" class="form-control" value="${name} ${surname}">   
						        </div>
						        
						        <div class="col-sm-1">
						        <label><br/></label>
			                      	<a href="../../app/popup/employee.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
							      		<i class="fa fa-search"></i></a>
			                    </div>
						    </div>
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}>Conferma</button>
					      </footer>
						  
			            </section>
			            
			            
						 
						 
						</form>
			            
			            
	                
	                </section>
	              </section>
	            </aside>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin">Dipendenti Pianificazione</p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${emp}" var="emp" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="clear">
		                        	<div><a href="../employee/emp-dett.htm?id=${emp.id}&tipoForm=M">${emp.id}</a></div> 
		                       		<small class="text-muted">${emp.name} ${emp.surname}</small> 
		                        </div> 
		                      </li>
	                        </c:forEach>
	                          
	                        </ul>
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
				        
                        <a href="planification-ric.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-magnifier"></i> 
                          <span class="hidden-nav-xs m-l-sm">Ricerca</span>
                        </a>
                        <a href="planification-dett.htm?tipoForm=I" style="padding-left: 30px" class="list-group-item">
                          <i class="icon-note"></i> 
                          <span class="hidden-nav-xs m-l-sm">Nuova Pianificazione</span>
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
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="Cerca Pianificazione">
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
		if(href=="../../app/popup/departments.jsp") {
			href+="?roles_description="+document.getElementById("department_description").value;
		}
		
		// Employee
		if(href=="../../app/popup/eployee.jsp") {
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
</script>
  </body>
</html>
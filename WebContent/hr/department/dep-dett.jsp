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
  <title><%=propLang.get("dep_dett") %> | SmartHR</title>
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
	              <section class="vbox" id="bjax-target">
	                <section class="scrollable padder-lg">
	                
	                <div class="row row-sm">
	                	<h2 class="font-thin m-b"><%=propLang.get("dep_dett") %></h2>
		                
		                <form data-validate="parsley" method="post" action="dep-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("dep_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="dep-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("dep_codice") %></label>
						          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
						        </div>
						    </div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("dep_anag") %></span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("dep_cddip") %></label>
						          <input type="text" id="department_code" name="department_code" class="form-control" value="${department_code}" ${btnActive}>   
						        </div>
						        <div class="col-sm-4">
						          <label><%=propLang.get("company_code") %></label>
						          <input type="text" id="company_code" name="company_code" class="form-control" value="${company_code}" ${btnActive}>   
						        </div>
						        <div class="col-sm-4">
						          <label><%=propLang.get("agency_code") %></label>
						          <input type="text" id="agency_code" name="agency_code" class="form-control" value="${agency_code}" ${btnActive}>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("dep_descrizione") %></label>
						        <input type="text" id="description" name="description" class="form-control" value="${description}" data-minlength="3" data-maxlength="50" data-required="true" ${btnActive}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("dep_indirizzo") %></label>
						        <input type="text" id="address" name="address" class="form-control" value="${address}" data-minlength="3" data-maxlength="50" ${btnActive}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-8">
							        <label><%=propLang.get("dep_localita") %></label>
							        <input type="text" id="locality" name="locality" class="form-control" value="${locality}" data-minlength="3" data-maxlength="50" ${btnActive}>                        
						    	</div>
						    	
						    	<div class="col-sm-2">
						          <label><%=propLang.get("dep_prov") %></label>
						          <input type="text" id="province" name="province" class="form-control" value="${province}" data-minlength="2" data-maxlength="2" ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						          <label><%=propLang.get("dep_cap") %></label>
						          <input type="text" id="zipcode" name="zipcode" class="form-control" value="${zipcode}" data-minlength="5" data-maxlength="5" ${btnActive}>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						     
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-6">
							        <label><%=propLang.get("dep_tel") %></label>
							        <input type="text" id="phone" name="phone" class="form-control" value="${phone}" data-minlength="3" data-maxlength="25" ${btnActive}>                        
						    	</div>
						    	
						    	<div class="col-sm-6">
						          <label><%=propLang.get("dep_mail") %></label>
						          <input type="text" id="email" name="email" class="form-control" value="${email}" data-type="email" data-maxlength="50" ${btnActive}>   
						        </div>
						    </div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">Suite Relationship</span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <div class="form-group pull-in clearfix">
						    	<div class="col-sm-6">
							        <label>Department Type</label>
							    	<select class="form-control" id="department_type" name="department_type">
							    	<option value=""></option>
							    	<c:forEach items="${deptype}" var="deptype" varStatus="status">
							    	<option value="${deptype.xcdel}">${deptype.xdtab}</option>
							    	</c:forEach>
							    	</select>
							    </div>
						    	
						    	<div class="col-sm-6">
						        	<label>Store</label>
							    	<select class="form-control" id="id_store" name="id_store">
							    	<option value=""></option>
							    	<c:forEach items="${sto}" var="sto" varStatus="status">
							    	<option value="${sto.xcdel}">${sto.xdtab}</option>
							    	</c:forEach>
							    	</select>
						        </div>
						    </div>
					        
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("dep_parentid") %></span>
	                          </div>
		                    </div>
					        <br/>
					        
						    <div class="form-group pull-in clearfix">
						    	<!-- 
						    	<div class="col-sm-4">
							        <label><%=propLang.get("dep_iddep") %></label>
							        <input type="text" id="id_department" name="id_department" class="form-control" value="${id_department}" 
							        	readonly>                        
						    	</div>
						    	-->
						    	
						    	<div class="col-sm-10">
						          <label><%=propLang.get("dep_descrizione") %></label>
						          <input type="hidden" id="id_department" name="id_department" class="form-control" value="${id_department}" readonly>
						          <input type="text" id="department_description" name="department_description" class="form-control" value="${dep_description}" ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						        <label><br/></label>
			                      	<a href="../../hr/popup/departments.jsp" class="getModal btn btn-default btn-block" data-toggle="modal"  ${btnActive}>
							      		<i class="fa fa-search"></i> Cerca</a>
			                    </div>
						    </div>
						    
						    <!-- 
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("dep_responsabile") %></span>
	                          </div>
		                    </div>
					        <br/> 
					        
					        <div class="form-group pull-in clearfix">
						    	
						    	<div class="col-sm-10">
						          <label><%=propLang.get("dep_dsdip") %></label>
						          <input type="hidden" id="id_employee" name="id_employee" class="form-control" value="${id_employee}" readonly>  
						          <input type="text" id="emp_description" name="emp_description" class="form-control" value="${emp_description}" ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						        <label><br/></label>
			                      	<a href="../../hr/popup/employee.jsp" class="getModal btn btn-default btn-block" data-toggle="modal" ${btnActive}>
							      		<i class="fa fa-search"></i> Cerca</a>
			                    </div>
						    </div>
						    <input type="hidden" id="id_employee" name="id_employee" class="form-control" value="${id_employee}" readonly>  
						    -->
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("dep_plan") %></span>
	                          </div>
		                    </div>
					        <br/> 
					        
					              
					        
					        <div class="form-group pull-in clearfix">
						    	<div class="col-sm-4">
							        <label><%=propLang.get("dep_maxperiod") %></label>
							        <input type="text" id="minnum_intervals" name="minnum_intervals" 
							        	data-type="number" class="form-control" value="${minnum_intervals}"  ${btnActive}>                        
						    	</div>
						    	
						    	<div class="col-sm-4">
							        <label><%=propLang.get("dep_tolleranza") %></label>
							        <input type="text" id="tollerance" name="tollerance" 
							        	data-type="number" class="form-control" value="${tollerance}"  ${btnActive}>                        
						    	</div>
						    	
						    	<div class="col-sm-4">
							        <label><%=propLang.get("dep_inizionotturno") %></label>
							        <input type="text" id="nocturne" name="nocturne" 
							        	data-type="number" class="form-control" value="${nocturne}"  ${btnActive}>                        
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
	                     	<p class="h4 font-thin"><%=propLang.get("dep_dip_dep") %></p>
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
				        
                        <a href="dep-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-building-o"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("dep_dipartimenti") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="dep-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("dep_cerca") %>">
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
  	
  	$('#department_type').val('${department_type}');
  	$('#id_store').val('${id_store}');
  	
  	
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
	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
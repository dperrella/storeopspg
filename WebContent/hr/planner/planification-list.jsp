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
  <title><%=propLang.get("pla_list") %> | SmartHR</title>
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
          <section class="hbox stretch">  
          
          <%@ include file="../../inc2/menuApp7.jsp" %>
                  
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("pla_list") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("pla_list") %> 
			              </header>
			              
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<!-- <li><a href="planification-ric.htm"><%//=propLang.get("pla_cerca") %></a></li> -->
									<li><a href="planification-dett.htm?tipoForm=I"><%=propLang.get("pla_new") %></a></li>
								</ul>
							</div>
						  </section>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								  <!-- 
							        <th width="15%">Id</th>
							      -->
							        <th width="20%"><%=propLang.get("pla_dep") %></th>
							        <th width="5%"></th>
							        <th width="20%"><%=propLang.get("pla_dip") %></th>
							        <th width="5%"><%=propLang.get("pla_aa") %></th>
							        <th width="5%"><%=propLang.get("pla_se") %></th>
							        <th width="10%"><%=propLang.get("pla_gg") %></th>
							        <th width="10%"><%=propLang.get("pla_tipo") %></th>
							        <th width="10%"><%=propLang.get("pla_orada") %></th>
							        <th width="10%"><%=propLang.get("pla_oraal") %></th>
							        <th></th>
							        <th></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${plan}" var="plan" varStatus="status">
								<tr>
								  <td>${plan.department_description}</td>
								  <td><span class="thumb-sm avatar pull-left m-l-xs">                        
				                        <img src="../../images/default-icon-user.png" class="dker" alt="...">
				                    </span></td>
								  <td>${plan.name} ${plan.surname}</td>
								  <td>${plan.plan_year}</td>
								  <td>${plan.plan_week}</td>
								  <td>${plan.cmp1}</td>
								  <td><span class="label ${plan.cmp2}">${plan.planification_type_description}</span></td>
								  <td>${plan.datetime_start}</td>
								  <td>${plan.datetime_end}</td>
								  
					              <td><a href="planification-dett.htm?tipoForm=M&id=${plan.id}&id_departments=${plan.id_departments}&week=${plan.plan_week}&year=${plan.plan_year}" class="btn btn-default"><i class="fa fa-edit"></i></a></td>
					              <td><a href="planification-dett.htm?tipoForm=E&id=${plan.id}&id_departments=${plan.id_departments}&week=${plan.plan_week}&year=${plan.plan_year}" class="btn btn-danger"><i class="fa fa-times"></i></a></td>
					              
					              
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
		  				  </div>
		  				
		  				</section>
		  				
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
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <script>
  $('#example').dataTable( {
      "language": {
          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_",
          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
          "infoFiltered": "(Filtrati da _MAX_ records totali)",
          "decimal": ",",
          "thousands": ".",
          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
      },
      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
      "aaSorting": [],
      "iDisplayLength": 50
  } );
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
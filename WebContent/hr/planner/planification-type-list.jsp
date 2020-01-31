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
  <title><%=propLang.get("plt_dsfun") %> | SmartHR</title>
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
	.all {
	  background: #cc99ff;
	  /*box-shadow:	0 0 1em #FAD733;*/ 
	}	
	.mat {
	  background: #ffcccc;
	  /*box-shadow:	0 0 1em #FAD733;*/ 
	}	
	.rc {
	  background: brown;
	  /*box-shadow:	0 0 1em #FAD733;*/ 
	}
	.lutto {
	  background: #c0c0c0;
	  /*box-shadow:	0 0 1em #f05050;*/
	}
	.cong {
	  background: #432;
	  /*box-shadow:	0 0 1em #f05050;*/
	}
	.mal1 {
	  background: #162955;
	  /*box-shadow:	0 0 1em #f05050;*/
	}
	.mal2 {
	  background: #2E4172;
	  /*box-shadow:	0 0 1em #f05050;*/
	}
	.com {
	  background: #196811;
	  /*box-shadow:	0 0 1em #f05050;*/
	}
	.pes {
	  background: #FFB800;
	  /*box-shadow:	0 0 1em #f05050;*/
	}
  </style>
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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("plt_dsfun") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("plt_dsfun") %> 
			              </header>
			              
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="planification-type-dett.htm?tipoForm=I"><%=propLang.get("plt_new") %></a></li>
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
							        <th width="45%"><%=propLang.get("plt_descrizione") %></th>
							        <th width="10%"><%=propLang.get("plt_min") %></th>
							        <th width="10%"><%=propLang.get("plt_max") %></th>
							        <!-- 
							        <th width="10%"><%=propLang.get("plt_direzione") %></th>
							        -->
							        <th width="10%"><%=propLang.get("plt_approvazione") %></th>
							        <th width="10%"><%=propLang.get("plt_colore") %></th>
							        <th></th>
							        <th></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${plt}" var="plt" varStatus="status">
								<tr>
								<!-- 
								  <td><a href="planification-type-dett.htm?tipoForm=V&id=${plt.id}" class="btn btn-default" style="width:270px">${plt.id}</a>
								-->  
								  <td>${plt.description}</td>
								  <td>${plt.min_time}</td>
								  <td>${plt.max_time}</td>
								  <!-- 
								  <td>${plt.direction}</td>
								  -->
								  <td>${plt.approvation}</td>
								  <td><span class="label ${plt.color}">${plt.color}</span></td>
								  
					              <td><a href="planification-type-dett.htm?tipoForm=M&id=${plt.id}" class="btn btn-default"><i class="fa fa-edit"></i></a></td>
					              <td><a href="planification-type-dett.htm?tipoForm=E&id=${plt.id}" class="btn btn-danger"><i class="fa fa-times"></i></a></td>
					              
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
				        
                        <a href="planification-type-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-calendar"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("plt_plt") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="planification-type-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("plt_cerca") %>">
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
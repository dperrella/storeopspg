<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  
  <title><%=propLang.get("sm_dsfuncontr") %> | SmartControl</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
  <link rel="stylesheet" href="../../css/bootstrap.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>

  <script src="../../js/jquery.min.js"></script>
  <script src="../../js/bootstrap.js"></script>
  
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>

    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->

</head>
<body class="">
<form id="form1" method="post" action="smartcontrol.htm" role="Control"></form>
  <section class="vbox">
    <header class="bg-white-only header header-md navbar navbar-fixed-top-xs">
      <div class="navbar-header aside <%=session.getAttribute("style") %> nav-xs">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
          <i class="icon-list"></i>
        </a>
        <a href="../../app/main/index.htm" class="navbar-brand text-lt">
          <i class="icon-feed"></i>
          <span class="hidden-nav-xs m-l-sm">Control</span>
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
      
      <!-- <form method="post" action="../../app/main/ricerca.htm" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">-->
      <form method="post" action="#" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">
        <div class="form-group">
          <div class="input-group">
            <span class="input-group-btn">
              <button type="button" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
            </span>
            <input type="text" name="myInput" id="myInput" class="form-control input-sm no-border rounded" style="width: 300%" placeholder="<%=propLang.get("cerca_smc") %>...">
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
              </span>${username}<b class="caret"></b>&nbsp;&nbsp;&nbsp;&nbsp;
            
            </a>
            <ul class="dropdown-menu animated fadeInRight" style="margin-right: 15px;">            
              <li>
                <a href="../../app/main/profilo.htm"><%=propLang.get("profilo") %></a>
              </li>
              <li>
                <a href="../main/notifiche.htm">
                  <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                  <%=propLang.get("notifiche") %>
                </a>
              </li>
              <li>
                <a href="../../app/main/docs.html"><%=propLang.get("guida") %></a>
              </li>
              <li class="divider"></li>
              <li>
                <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal"><%=propLang.get("esci") %></a>
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
                        <a href="../../app/main/profilo.htm"><%=propLang.get("profilo") %></a>
                      </li>
                      <li>
                        <a href="../main/notifiche.htm">
                          <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                          <%=propLang.get("notifiche") %>
                        </a>
                      </li>
                      <li>
                        <a href="../../app/main/docs.html"><%=propLang.get("guida") %></a>
                      </li>
                      <li class="divider"></li>
                      <li>
                        <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" ><%=propLang.get("esci") %></a>
                      </li>
                    </ul>
                  </div>
                </div>            
			</footer>
			
          </section>
        </aside>
 <!-- Aggiunto Pietro -->              

                <aside class="aside-md bg-dark nav-xs" id="sidebar2">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover" style="margin-bottom:40px">
                    
						<%@ include file="../../inc/smartcontrol-menu-left.jsp" %>

                    </section>
                  </section>
                </aside>
                <!--  -->        
        <!-- /.aside -->
        <section id="content">
          <section class="hbox stretch">          
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("sm_sch_neg") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold" style="text-align: center;">
			                <span id="Testata_" style="text-align: center; font-weight: bold;"></span>
			              </header>

<!-- inizio panel red -->
			              <section class="panel wrapper"  id="subContent">
			              	<div class="subnav" >
		              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-list-alt" aria-hidden="true"></i> <%=propLang.get("sm_sch_neg") %>  
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">
						  <% if (session.getAttribute("RIDC").equals("super") || session.getAttribute("RIDC").equals("countryadmin") || session.getAttribute("RIDC").equals("list-admin")) {%>
						 <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="smartcontrol-stores-dett-add.htm" class="btn btn-default"><%=propLang.get("sm_sch_new_neg") %></a></li>
								</ul>
							</div>
						  </section>
						  <% } %>
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
							        <th width="10%">ID</th>
							        <th width="40%">Store</th>
							        <th width="10%">N° Board</th>
							        <th width="40%">Customer</th>
							        <th></th>
							      </tr>
							  </thead>	      					  
	      					  <tbody>
							  <c:forEach items="${Liststo}" var="Liststo" varStatus="status">
								<tr>
								  <td><span class="label bg-light">${Liststo.ids}</span></td>
								  <td>${Liststo.sto}</td>
			                      <td>${Liststo.boa}</td>
								  <td>${Liststo.cus}</td>
								  <td>
								  <% if (session.getAttribute("RIDC").equals("super") || session.getAttribute("RIDC").equals("countryadmin") || session.getAttribute("RIDC").equals("list-admin")) {%>
								  <div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu" style="margin-left: -100px;">
					                    <!-- <li><a onclick="oper('Sto','M',${Liststo.ids})" style="cursor:pointer;"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li> -->
					                    <li><a href="smartcontrol-stores-dett.htm?id=${Liststo.ids}" style="cursor:pointer;"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li>
					                    <li><a onclick="oper('Sto','E',${Liststo.ids})" style="cursor:pointer;"><i class="fa fa-times"></i> <%=propLang.get("eli") %></a></li>
					                  </ul>
					              </div>
					              <% }else{ %>
								  <div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu" style="margin-left: -100px;">
					                    <li><a onclick="oper('Sto','M',${Liststo.ids})" style="cursor:pointer;"><i class="fa fa-edit"></i> <%=propLang.get("sm_vis") %></a></li>
					                  </ul>
					              </div>					              
					              <% } %>
					              </td> 		                      			                      
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
		  				  </div>
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>
					
			              		
							</div>
						  </section>

		  				
		  				</section>
		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>
                
                <!-- side content -->

	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	            
						<%@ include file="../../inc/smartcontrol-menu-right.jsp" %>
				
	                </section>
	                
	                <footer class="footer footer-md bg-black">
	                  <form action="#" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                        </span> 
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
<script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet"> 
  <!-- fine pietro -->
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
      "iDisplayLength": 10
  } );
  </script>
    <script>
function oper(tab,flag,id) {
	$.ajax({
		url : "../../smcontrol",
		data : {
			tab : tab,
			flag : flag,
			id : id
		},
		success : function(responseText) {
			alertify.success(responseText);
			setTimeout(function(){ location.href="smartcontrol-stores.htm"; }, 2000);
			//alertify.success(responseText);
		}
	});
}
</script>
<script>  
var err = "<%=propLang.get("sm_notautorized") %>";
</script>
<script src="../../js/autorized.js"></script>
  </body>
</html>
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
  <title><%=propLang.get("tab_dsfun") %> | SmartHR</title>
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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("tab_dsfun") %></h2>
		                
		                <form action="tables-list.htm" method="post">
			            <input type="hidden" id="tipoForm" name="tipoForm" value="">
			            <input type="hidden" id="azione" name="azione" value="S">
			            <input type="hidden" id="xcdtb" name="xcdtb" value="${xcdtb}">
			              
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("tab_dsfun") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<c:if test="${tipoForm=='M'}">
								  		<li><a href="tables-list.htm?xcdtb=TAB"><%=propLang.get("tab_torna") %></a></li>
								  		<li><a href="javascript:void;" onclick="addRow('righe', '${xcdtb}')"><%=propLang.get("tab_newtab") %></a></li>
								  	</c:if>
								  	<c:if test="${tipoForm!='M'}">
								  		<li><a href="../../hr/popup/newTables.jsp" class="getModal" data-toggle="modal">
							      			<%=propLang.get("tab_newtab") %></a>
								  		</li>
								  	</c:if>
								  
								</ul>
							</div>
							
							<div id="msg">${msg}</div>
						  
						  </section>
			              
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="righe" data-ride="datatables">
			                  <thead>
								  <tr>
								  <c:if test="${tipoForm=='M'}">
								  	<th></th>
								  </c:if>
							        <th width="10%"><%=propLang.get("tab_xcdtb") %></th>
							        <th width="10%"><%=propLang.get("tab_xcdel") %></th>
							        <th width="70%"><%=propLang.get("tab_xdtab") %></th>
							        <th></th>
							        <th></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${tab}" var="tab" varStatus="status">
								<tr>
								  <c:if test="${tipoForm=='M'}">
								  	<td><label class="checkbox m-n i-checks"><input type="checkbox" name="post${tab.cmp1}"><i></i></label></td>
								  </c:if>
								  <td><span class="label bg-light">${tab.xcdtb}</span>
								      <input type="hidden" id="${tab.cmp1}nrrig" name="nrrig" value="${tab.cmp1}" class="idrow">
								  	  <input type="hidden" id="${tab.cmp1}xcdtb" name="xcdtb" value="${tab.xcdtb}"></td>
								  <td><input type="text" class="form-control" id="${tab.cmp1}xcdel" name="xcdel" value="${tab.xcdel}" ${tab.cmp2}></td>
								  <td><input type="text" class="form-control" id="${tab.cmp1}xdttb" name="xdtab" value="${tab.xdtab}" ${readonly}></td>
								  <c:if test="${tipoForm!='M'}">
								  	<td><a href="tables-list.htm?xcdtb=${tab.xcdel}&tipoForm=M" class="btn btn-default">
								  	<i class="fa fa-search"></i> <%=propLang.get("tab_dettagli") %></a></td>
								  	<td></td>
								  </c:if>
								  <c:if test="${tipoForm=='M'}">
								  	<td><a href="javascript:void;" class="btn btn-default" onclick="addRow('righe', '${tab.xcdtb}')"><i class="icon-plus"></i></a></td>
								  	<td></td>
								  </c:if>
								  
								  
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
		  					<c:if test="${tipoForm=='M'}">
							<footer class="panel-footer text-right bg-light lter">
					        <button type="submit" id="submit" class="btn btn-danger btn-lg" style="width:130px" onclick="$('#tipoForm').val('E')"><i class="fa fa-times"></i> <%=propLang.get("tab_cancella") %></button>
					        <button type="submit" id="submit" class="btn btn-success btn-lg" style="width:130px" onclick="$('#tipoForm').val('M')"><i class="fa fa-edit"></i> <%=propLang.get("tab_modifica") %></button>
					      	</footer>
		  					</c:if>
		  					
		  				  </div>
		  				  
		  				  
                    	
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
				        
                        <a href="tables-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-list"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("tab_tabelle") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="tables-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("tab_cercaTab") %>">
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
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
	
  </script>
	
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <script>
  function addRow(table, xcdtb) {
	  	
		$last = $('.idrow:last').val();
	  	$last=parseInt($last)+1;
				
		var $tr = '<tr>'
			 	+ '<td><label class="checkbox m-n i-checks"><input type="checkbox" name="post'+$last+'"><i></i></label></td>'
			 	+ '<td><span class="label bg-light">'+xcdtb+'</span>'
				+ '<input type="hidden" id="'+$last+'nrrig" name="nrrig" value="'+$last+'" class="idrow">'
				+ '<input type="hidden" id="'+$last+'xcdtb" name="xcdtb" value="'+xcdtb+'"></td>'
				+ '<td><input type="text" class="form-control" id="'+$last+'xcdel" name="xcdel" value=""></td>'
				+ '<td><input type="text" class="form-control" id="'+$last+'xdttb" name="xdtab" value=""></td>'
				+ '<td><a href="javascript:void;" class="btn btn-default" onclick="addRow(\'righe\', \''+xcdtb+'\')"><i class="icon-plus"></i></a></td>'
				+ '<td><span onclick="$(this).parents(\'tr\').remove();" class="btn btn-danger"><i class="icon-close"></i></span></td>'
				+ '</tr>';
	  	 
	  	 $("#"+table).find("tbody tr:last").after($tr);
	
  }
	  
  
  $('#righe').dataTable( {
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
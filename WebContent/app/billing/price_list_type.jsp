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
  <title>Price Type | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  
  <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
  <style>
    	
	#loading-image {
		background-color: #333;
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}	
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
	.fixed {
	    position: absolute;
	}
	#ajaxModal {
		z-index: 10000;
	}
  </style>
</head>
<div id="loading-image">
		<img src="../../images/loading.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 208px;
				   height: 13px;
				   margin-top: -8px;
				   margin-left: -104px;" />
  </div>
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
          
          	<!-- menu app (In questo caso non hanno menu app)-->
              
            <%//@ include file="../../inc2/menuApp5.jsp" %>
                   
            <section>
              <section class="vbox">
              
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b">Price Type</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Price Type
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="price_dett_type.htm?tipoForm=I" class="btn btn-default"><%="New Price Type" %></a></li>
								</ul>
							</div>
						  </section>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								  <th style="width:23%">Code</th>  
								  <th style="width:65%">Description</th>
								  <th style="width:7%">Active</th>  
								  <th style="width:5%">&nbsp;</th>
								</tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${tab}" var="tab" varStatus="status">
								<tr>
								  <td>${tab.xcdtb}</td>
								  <td>${tab.xcdel}</td>
								  <td>${tab.xdtab}</td>
								  <td>
								  <div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu">
					                    <li><a href="price_dett_type.htm?tipoForm=V&idcard=${tab.xcdtb}"><i class="fa fa-search"></i> <%=propLang.get("dett") %></a></li>
					                    <li><a href="price_dett_type.htm?tipoForm=M&idcard=${tab.xcdtb}"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li>
					                    <li><a href="price_dett_type.htm?tipoForm=E&idcard=${tab.xcdtb}"><i class="fa fa-times"></i> <%=propLang.get("eli") %></a></li>
					                  </ul>
					                </div>
								  </td>
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
                
                <!-- side content 
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="payment_type-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-download"></i> 
                          <span class="hidden-nav-xs m-l-sm">Payment Type</span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="payment_type-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="Search Import-List">
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
  
    <script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet"> 
  
  <script>
  
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
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
	<script>
	function Del(c,s,d){

		
		alertify.set({ labels: {
		    ok     : "CONFIRM",
		    cancel : "CANCEL"
		} });
		
		alertify.confirm("Perform Action?", function (e) {
			
		    if (e) {		
					
					$.ajax({
						url: '../../SetDispDelStores',
						async: false,
						data : {
							flag: "W",
							idcustomer : c,
							idstore: s,
							iddrive: d
						},
						success : function(responseText) {
							alertify.success("Cancellation Occurred");
							location.href="import-file-type.htm";
						}
					
					});
		
		    } else {
		    	alertify.log("Action Cancelled");
		    }
		});		
		
	}
	</script>
	
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
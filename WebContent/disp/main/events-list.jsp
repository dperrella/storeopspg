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
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  <link href="dist/css/bootstrap-colorpicker.min.css" rel="stylesheet">
  
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
	#loading-image2 {
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
  <div id="loading-image2" style="display:none">
		<img src="../../images/spinner.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 200px;
				   height: 200px;
				   margin-top: -100px;
				   margin-left: -50px;" />
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
          
          	<!-- menu app -->
              
            <%@ include file="../../inc2/menuApp5.jsp" %>
                   
            <section>
              <section class="vbox">
              
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b">Events List</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Events List
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="events-dett.htm?tipoForm=I"><%="New Events" %></a></li>
									
									<li><a href="../popup/customers-events.jsp" data-toggle="ajaxModal">Events Received</a></li>
								</ul>
							</div>
						  </section>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								  <th style="width:10%">Events</th>  
								  <th style="width:52%">Description</th>  
								  <th style="width:10%">Driver</th>  
								  <th style="width:10%">Imported</th>  
								  <th style="width:8%">KPI</th>  
								  <th style="width:10%">&nbsp;</th>
								</tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${tab}" var="tab" varStatus="status">
								<tr>
								  <td>${tab.xcdel}</td>
								  <td>${tab.xdtab}</td>
								  <td>${tab.cmp1}</td>
								  <td>${tab.cmp2}<td>
								  <c:choose>
									  <c:when test="${tab.cmp3==''}">
									    <a href="selKpi.jsp?code=${tab.xcdel}&drv=${tab.cmp1}" class="getModal btn-block btn btn-default" data-toggle="modal">Sel KPI</a>
									  </c:when>
									  <c:otherwise>
									  	<a href="selKpi.jsp?code=${tab.xcdel}&drv=${tab.cmp1}&kpi=${tab.cmp3}" class="getModal btn-block btn btn-info" data-toggle="modal">${tab.cmp3}</a>
									  </c:otherwise>
									</c:choose>
									</td>
								  <td>
								  <div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu">
					                    <li><a href="events-dett.htm?tipoForm=V&events=${tab.xcdel}&drv=${tab.cmp1}"><i class="fa fa-search"></i> <%=propLang.get("dett") %></a></li>
					                    
					                    <c:if test = "${tab.cmp4=='f'}">
					                    <li><a href="events-dett.htm?tipoForm=M&events=${tab.xcdel}&drv=${tab.cmp1}"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li>
					                    <li><a href="events-dett.htm?tipoForm=E&events=${tab.xcdel}&drv=${tab.cmp1}"><i class="fa fa-times"></i> <%=propLang.get("eli") %></a></li>
					                    </c:if>
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
				        
                        <a href="events-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-tasks"></i> 
                          <span class="hidden-nav-xs m-l-sm">Events List</span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="visit-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="Search JSON">
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
  
  <script src="dist/js/bootstrap-colorpicker.js"></script>
  
  <script src="../../js/bootstrap-modal.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  
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
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
	function callColor() {
		
		$('#cp2').colorpicker();
		
	}
	
	//MODAL
	$('.getModal').click(function(e) {
		
		showLoader2();
		
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
				hideLoader2();
			});
		}
	});
	
	function valutateModal() {
		
		var kpi = $('#kpi_sel').val();
		if(kpi=='') {
			
			$("#kpi_code").prop('disabled', false);
			$("#kpi_name").prop('disabled', false);
			$("#kpi_desc").prop('disabled', false);
			$("#kpi_symbol").prop('disabled', false);
			$("#kpi_color").prop('disabled', false);
			$("#kpi_btn").show();
			$("#kpi_rel").hide();
			
		}
		else {
			
			$("#kpi_code").prop('disabled', true);
			$("#kpi_name").prop('disabled', true);
			$("#kpi_desc").prop('disabled', true);
			$("#kpi_symbol").prop('disabled', true);
			$("#kpi_color").prop('disabled', true);
			$("#kpi_btn").hide();
			$("#kpi_rel").show();
		}
		
	}
	
	function relKpi() {
		
		var code = $('#code').val();
		var drv = $('#drv').val();
		var kpi = $('#kpi_sel').val();
		var kpi_old = $('#kpi_old').val();
		
		$('.modal').remove(); $('div.modal-backdrop').remove();
		showLoader2();
		
		$.ajax({
		      url: 'relKpi.jsp',
		      type: 'POST',
		      dataType: 'html',
		      data: 'code='+code+'&drv='+drv+'&kpi='+kpi+'&tp=R&kpi_old='+kpi_old,
		      success: function (data) {
					
				hideLoader2();
				location.reload();
				
		      },
		      error: function () {
		      	
		      }
		});
		
	}
	
	function writeKpi() {
			
		var code = $('#code').val();
		var drv = $('#drv').val();
		
		var kpi = $("#kpi_code").val();
		var name = $("#kpi_name").val();
		var desc = $("#kpi_desc").val();
		var symbol = $("#kpi_symbol").val();
		var color = $("#kpi_color").val();
		var kpi_old = $('#kpi_old').val();
		
		$('.modal').remove(); $('div.modal-backdrop').remove();
		showLoader2();
		
		$.ajax({
		      url: 'relKpi.jsp',
		      type: 'POST',
		      dataType: 'html',
		      data: 'code='+code+'&drv='+drv+'&kpi='+kpi+'&tp=I&name='+name+'&desc='+desc+'&symbol='+symbol+'&color='+color+'&kpi_old='+kpi_old,
		      success: function (data) {
					
				hideLoader2();
				location.reload();
				
		      },
		      error: function () {
		      	
		      }
		});
		
	}
	
	function modForm(val) {
		
		if(val=='') {
			
			$("#kpi_code").prop('disabled', false);
			$("#kpi_name").prop('disabled', false);
			$("#kpi_desc").prop('disabled', false);
			$("#kpi_symbol").prop('disabled', false);
			$("#kpi_color").prop('disabled', false);
			$("#kpi_btn").show();
			$("#kpi_rel").hide();
			
		}
		else {

			$("#kpi_code").prop('disabled', true);
			$("#kpi_name").prop('disabled', true);
			$("#kpi_desc").prop('disabled', true);
			$("#kpi_symbol").prop('disabled', true);
			$("#kpi_color").prop('disabled', true);
			$("#kpi_btn").hide();
			$("#kpi_rel").show();
			
			
		}
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
	
	  function viewCustomerEvents(account_code) {
		  
		  $('.modal').remove(); 
		  $('div.modal-backdrop').remove();
		  
		  //alert(account_code);
		  showLoader2();
		  
		  javascript:window.location='events-list.htm?account_code='+account_code;
		  window.location.href='events-list.htm?account_code='+account_code; 
		  
		  
		  
	  }
	
	</script>
	
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
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
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
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
		z-index: 1;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
  </style>
</head>
<body>
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
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp6.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
              		
                      
		                <h2 class="font-thin m-b"><%=propLang.get("fin_dsfun") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("fin_dsfun") %>
			              </header>
			              
			              
			              <section class="panel wrapper">
			              
			              	<label><%=propLang.get("cus_dsfun") %></label>
					        <select id="idcus" name="idcus" data-required="true" 
					          	onchange="reloadList()" class="form-control parsley-validated chosen-select">
						          <option value="">-</option>
						          <c:forEach items="${cus}" var="cus" varStatus="status">
		                          <option value="${cus.id}">${cus.account_code} - ${cus.brandname} ${cus.description}</option>
		                          </c:forEach>
	                       	</select>   
				            
				            <c:if test = "${idcus > 0}">
				            <div class="line line-dashed b-b line-lg pull-in"></div>
				               
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="../popup/newCalendar_month.jsp?idcus=${idcus}" data-toggle="ajaxModal"><%=propLang.get("fin_new") %> <b><%=propLang.get("sm_rol_3") %></b></a></li>
									<li><a href="../popup/newCalendar_week.jsp?idcus=${idcus}" data-toggle="ajaxModal"><%=propLang.get("fin_new") %> <b><%=propLang.get("sm_rol_2") %></b></a></li>
								</ul>
							</div>
							</c:if>
							
						  </section>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
							        <th width="10%"><%=propLang.get("usr_tipoDett") %></th>
							        <th width="20%"><%=propLang.get("fin_year") %></th>
							        <th width="30%"><%=propLang.get("fin_from") %></th>
							        <th width="30%"><%=propLang.get("fin_to") %></th>
							        <th></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${fc}" var="fc" varStatus="status">
								<tr>
								  <td><span class="label bg-light">${fc.caltype}</span></td>
								  <td><span>${fc.year}</span></td>
								  <td>${fc.fromdate}</td>
			                      <td>${fc.todate}</td>
			                      <td><div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu">
					                  <c:if test = "${fc.caltype == 'M'}">				           
					                    <li><a href="financial-calendar-month.htm?tipoForm=V&year=${fc.year}&idcus=${idcus}&caltype=${fc.caltype}"><i class="fa fa-search"></i> <%=propLang.get("dett") %></a></li>
					                    <li><a href="financial-calendar-month.htm?tipoForm=M&year=${fc.year}&idcus=${idcus}&caltype=${fc.caltype}"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li>
					                    <li><a href="financial-calendar-month.htm?tipoForm=E&year=${fc.year}&idcus=${idcus}&caltype=${fc.caltype}"><i class="fa fa-times"></i> <%=propLang.get("eli") %></a></li>          
					                  </c:if>
					                  <c:if test = "${fc.caltype == 'W'}">				           
					                    <li><a href="financial-calendar-week.htm?tipoForm=V&year=${fc.year}&idcus=${idcus}&caltype=${fc.caltype}"><i class="fa fa-search"></i> <%=propLang.get("dett") %></a></li>
					                    <li><a href="financial-calendar-week.htm?tipoForm=M&year=${fc.year}&idcus=${idcus}&caltype=${fc.caltype}"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li>
					                    <li><a href="financial-calendar-week.htm?tipoForm=E&year=${fc.year}&idcus=${idcus}&caltype=${fc.caltype}"><i class="fa fa-times"></i> <%=propLang.get("eli") %></a></li>          
					                  </c:if>
					                  
					                  </ul>
					                </div></td>
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
				        
                        <a href="financial-calendar.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-calendar"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("fin_dsfun") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="financial-calendar.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("rol_cerca") %>">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            <!-- / side content -->
                
               
              </section>
              
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
  
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <script>
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();
		
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
	
	function setSelectPicker() {
		$('.selectpicker').selectpicker();
	}
	
	$('#idcus').val('${idcus}');
	  
	function reloadList() {
		  var idcus = $('#idcus').val();
			
		  javascript:window.location='financial-calendar.htm?idcus='+idcus;
		  window.location.href='financial-calendar.htm?idcus='+idcus; 
	}
	
	function newCalendar() {
		
		  var idcus = $('#idcus').val();
		
		  javascript:window.location='financial-calendar-dett.htm?tipoForm=I&idcus='+idcus;
		  window.location.href='financial-calendar-dett.htm?tipoForm=I&idcus='+idcus;
		  
	}
	
	function setCal() {
  		
  		//alert('aaa');	
  		var year = $('#year').val();
  		var day = $('weekstart').val();
  		
  		var dis = [1,2,3,4,5,6];
  		var hl = [0];
  		if(day=='M') {
  			dis = [1,2,3,4,5,6];
  			hl=[0];
  		}
  		else {
  			dis = [0,2,3,4,5,6];
  			hl=[1];
  		}
  		
  		 $('.datepicker-input2').datepicker({
  		    format: 'yyyy-mm-dd',
  		    orientation: 'top',
  		    autoclose: true,
  		  	daysOfWeekDisabled: dis,
  		  	calendarWeeks: true,
  		  daysOfWeekHighlighted: hl
  		});  
  		
  		//var year = $('#year').val();
  		//$(".datepicker-input2").datepicker("update", ''+year+'-01-01');
  	}
  	
	function chgYear(year) {
		//$(".datepicker-input2").datepicker("update", ''+year+'-01-01');
	}
	  
	function chgDay(day) {
		var hl = [0];
		if(day=='M') {
  			dis = [0,2,3,4,5,6];
  			hl = [1];
  		}
  		else {
  			dis = [1,2,3,4,5,6];
  			hl = [0];
  		}
		
		$('.datepicker-input2').datepicker('remove');
		$('.datepicker-input2').datepicker({
  		    format: 'yyyy-mm-dd',
  		    orientation: 'top',
  		    autoclose: true,
  		  	daysOfWeekDisabled: dis,
  		  calendarWeeks: true,
  		daysOfWeekHighlighted: hl
  		});  
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
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  </body>
</html>
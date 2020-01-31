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
  <title><%=propLang.get("clo_list") %> | SmartHR</title>
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
     #loading-image {
		background-color: #333;
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 1;
	}	
  </style>
</head>
<body class="">
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
          <%@ include file="../../inc2/menuApp7.jsp" %>    
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("clo_list") %></h2>
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("clo_list") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="clocking-ric.htm"><%=propLang.get("tornaRic") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								    <th width="8%"><%=propLang.get("clo_data") %></th>
							        <th width="25%"><%=propLang.get("clo_dip") %></th>
							        <th width="25%"><%=propLang.get("clo_dep") %></th>
							        <th width="10%"><%=propLang.get("clo_ragione") %></th>
							        <th width="5%"><%=propLang.get("clo_ora") %></th>
							        <th width="20%"><%=propLang.get("clo_noj") %></th>
							        <th width="7%"><%=propLang.get("clo_app") %></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${clk}" var="clk" varStatus="status">
								<tr>
								  <td>${clk.cmp9}<input type="hidden" id="hid_date" value="${clk.cmp9}"></td>
								  <td>${clk.xdtab}<input type="hidden" id="hid_empl" value="${clk.xdtab}"></td>
								  <td>${clk.cmp2}</td>
								  <td>${clk.cmp3}</td>
								  <td>${clk.cmp4}</td>
								  <td>${clk.cmp5}</td>
								  <td>${clk.cmp10}</td>
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
				        
                        <a href="clocking-ric.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-magnifier"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("clo_dsfun") %></span>
                        </a>
                        
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="clocking-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("clo_dsfun") %>">
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
  
<script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet">   
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
	
  	$('#year').val('${year}');
  	$('#week').val('${currentWeek}');
  	$('#id_departments').val('${id_departments}');
  	
  	
  	function cambiaSettimana() {
  		
  		showLoader();
  		
  		var year = $('#year').val();
  		var id_departments = $('#id_departments').val();
  		
		javascript:window.location='clocking-ric.htm?year='+year+'&id_departments='+id_departments;
	  	window.location.href='clocking-ric.htm?year='+year+'&id_departments='+id_departments;
	}
  	
  	function approva(nam,ids,dat){

  		var ckbox = $('#'+nam.id);
  		
  		if (ckbox.is(':checked')){
		alertify.confirm("Stai per approvare la giusificazione di: <b>"+$("#hid_empl").val()+"</b> del: <b>"+$("#hid_date").val()+"</b> continuare?", function (e) {
		    if (e) {

		  		$.ajax({
		  			url: '../../SetApprovated',
		  			data : {
		  				ids : ids,
		  				dat : dat
		  			},
		  			success : function(responseText) {
		  				
		  				location.reload();
		  				
		  			}
		  		
		  		});

		    	
		    } else {
		    	
		    	
		    }
		});
  		
  		}
  		
  		
  		
  	}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
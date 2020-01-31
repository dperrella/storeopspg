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
  <title>${dsfun} | SmartHR</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
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
                      
		                <h2 class="font-thin m-b">${dsfun}</h2>
		                
		                <form name="myForm" id="myForm" action="control-list.htm" method="post">
		                <input type="hidden" name="tp" id="tp" value="${tp}"/>
		                
		                <!-- 
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                ${dsfun} 
			              </header>
			            -->
			               
			            <section class="panel panel-default">
		                    <header class="panel-heading bg-light">
		                      <ul class="nav nav-tabs pull-right">
		                        <li class="active"><a href="#weekTab" data-toggle="tab" onclick="chgAction('W')">${dsfun} <%=propLang.get("tipo_week") %></a></li>
		                        <li><a href="#dayTab" data-toggle="tab" onclick="chgAction('D')">${dsfun} <%=propLang.get("tipo_day") %></a></li>
		                      </ul>
		                      <span class="text-muted m-r-sm"><i class="icon-calendar"></i></span>
			                  <span class="hidden-sm"><b><!-- ${code} - -->${dsfun}</b></span>
			                      
		                    </header>
			              
			              <div class="wrapper">
						  
						    
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="tab-content">  
							  	<div class="tab-pane active" id="weekTab">
							  	<div class="form-group pull-in clearfix">
							        <div class="col-sm-12">
							          <label><%=propLang.get("pla_seldip") %></label>
							          <select id="id_departments" name="id_departments" 
							          	 class="form-control chosen-select" onchange="cambiaSettimana()">
								          <option value="">-</option>
								          <c:forEach items="${dep}" var="dep" varStatus="status">
				                          <option value="${dep.id}">${dep.description}</option>
				                          </c:forEach>
		                        	  </select>   
							        </div>
							    </div>
							    
						        <div class="line line-dashed b-b line-lg pull-in"></div>
						        
						        <div class="form-group pull-in clearfix">
							        <div class="col-sm-2">
							          <label><%=propLang.get("pla_year") %></label>
							          <select id="year" name="year" class="form-control" onchange="cambiaSettimana()">
								          <option value="2015">2015</option>
								          <option value="2016">2016</option>
								          <option value="2017">2017</option>
								          <option value="2018">2018</option>
								          <option value="2019">2019</option>
				                      </select>   
							        </div>
							        
							        <div class="col-sm-10">
							          <label>${selPeriod}</label>
							          <select id="week" name="week" class="form-control">
								          <option value="">-</option>
								          <c:forEach items="${weeks}" var="weeks" varStatus="status">
				                          <option style="background-color: ${weeks.cmp2}" value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
				                          </c:forEach>
		                        	  </select>   
							        </div>
							    </div>
							    </div>
							
							    <div class="tab-pane" id="dayTab">
		                        	
		                        	<div class="form-group pull-in clearfix">
		                        		<div class="col-sm-12">
								          <label><%=propLang.get("sel_day") %></label>
								          <input class="datepicker-input2 form-control" name="day" id="day" 
								          	type="text" value="${oggi}" data-date-format="dd/mm/yyyy" > 
								        </div>
								    </div>
		                        	
		                        </div>
			                </div>
						    
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" style="width:200px" class="btn btn-success btn-lg" onclick="showLoader()"><%=propLang.get("btnSearch") %></button>
					      </footer>
			             
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
				        
                        <a href="control-ric.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-magnifier"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("cnt_cerca") %></span>
                        </a>
                        
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="control-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("cnt_cerca") %>">
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
  
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
 
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
  	
  	$('.datepicker-input2').datepicker({
	    format: 'dd/mm/yyyy',
	    orientation: 'bottom',
	    autoclose: true
	});  
  
  	function chgAction(tp) {
  		
  		if(tp=='W') {
  			
  			//$('#myForm').attr('action', 'control-list.htm');
  			document.myForm.action = "control-list.htm";
  		}
  		else {
  			
  			//$('#myForm').attr('action', 'control-day-list.htm');
  			document.myForm.action = "control-day-list.htm";
  		}
  		
  		
  	}
  	
  	function cambiaSettimana() {
  		
  		showLoader();
  		
  		var year = $('#year').val();
  		var id_departments = $('#id_departments').val();
  		var tp = $('#tp').val();
  		  		
		javascript:window.location='control-ric.htm?year='+year+'&id_departments='+id_departments+'&tp='+tp;
	  	window.location.href='control-ric.htm?year='+year+'&id_departments='+id_departments+'&tp='+tp;
	}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
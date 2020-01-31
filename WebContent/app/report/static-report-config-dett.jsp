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
  <title><%=propLang.get("src_dsfun") %> | SmartAnalytics</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
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
          
          	<!-- menu app -->
            <%@ include file="../../inc2/menuApp.jsp" %>
              
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("src_dsfun") %> - ${description}</h2>
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("src_dsfun") %> - ${description}
			              </header>
			              
			              <section class="wrapper">
			              	  
			              	  <div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="static-report-config-list.htm?id_store=${id_store}"><%=propLang.get("torna") %></a></li>
								</ul>
							  </div>
							    
                        	  <div id="msg">${msg}</div>
					       	  
					       	  <div class="line line-dashed b-b line-lg pull-in"></div>
					       	  
					       	  <form data-validate="parsley" id="ac-form" action="static-report-config-dett.htm" method="post">
				              <input type="hidden" id="id_store" name="id_store" value="${id_store}"/>
				              <input type="hidden" id="id_flow" name="id_flow" value="${id_flow}"/>
				              <input type="hidden" id="tipoForm" name="tipoForm" value="I"/>
				              <input type="hidden" id="azione" name="azione" value="S"/>
				              
					       	  <div class="form-group pull-in clearfix">
							  	<div class="col-sm-5">
							  		<label><%=propLang.get("fun_descrizione") %></label>
							  		<input type="text" class="form-control" name="description" id="description" value="${description}" readonly/>
							  	</div>
							  	<div class="col-sm-3">
							  		<label><%=propLang.get("ara_calc_device") %></label>
							  		<select id="id_device" name="id_device" data-required="true" 
							          	onchange="reloadPage()" class="form-control parsley-validated chosen-select">
								          <c:forEach items="${dev}" var="dev" varStatus="status">
				                          <option value="${dev.id}">${dev.doorid} - ${dev.devicename}</option>
				                          </c:forEach>
		                        	  </select>   
		                        	  
							  	</div>
							  	<div class="col-sm-3">
							  		<label><%=propLang.get("ara_calc_line") %></label>
							  		<select name="id_line" id="id_line" data-required="true" 
					          			class="form-control parsley-validated chosen-select">
							  			  <c:forEach items="${dd}" var="dd" varStatus="status">
				                          <option value="${dd.id_line}">${dd.id_line} - ${dd.note}</option>
				                          </c:forEach>
							  		</select>
							  	</div>
							  	<div class="col-sm-1">
							  		<label>&nbsp;</label>
							  		<a href="#" class="btn btn-default btn-block" onclick="submitForm()">
							  			<i class="icon-plus"></i></a>
							  	</div>
							  </div>
							  </form>
							  	
					       	  
					      </section>
					      
			              <div class="table-responsive">
			                <table class="table table-striped b-t m-b-none">
			                  <thead>
								  <tr>
								    <th style="width:5%"></th>
							        <th style="width:45%"><%=propLang.get("ara_calc_device") %></th>
							        <th style="width:45%"><%=propLang.get("ara_calc_line") %></th>
							        <th style="width:5%"></th>
							      </tr>
							  </thead>
	      					  <tbody>
	      					  <c:forEach items="${sr}" var="sr" varStatus="status">
	      					  	  <tr>
	      					  	  	<td>${sr.cmp1}</td>
	      					  	  	<td>${sr.cmp5} - ${sr.cmp6}</td>
	      					  	  	<td>${sr.cmp3} - ${sr.cmp4}</td>
	      					  	  	<td><a href="static-report-config-dett.htm?azione=S&tipoForm=E&id_store=${id_store}&id_flow=${id_flow}&description=${description}&id_device=${sr.cmp2}&id_line=${sr.cmp3}"
	      					  	  		class="btn btn-danger"><i class="fa fa-times"></i></a></td>
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
				        
                        <a href="aree-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-directions"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("aar_dsfun") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="aree-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("aar_cerca") %>">
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
  
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
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
	
  $('#id_device').val('${id_device}');
  $('#id_line').val('${id_line}');
  
  function reloadPage() {
	  
	  var id_store = $('#id_store').val();
	  var id_flow = $('#id_flow').val();
	  var description = $('#description').val();
	  var id_device = $('#id_device').val();
	  var id_line = $('#id_line').val();
	  
	  javascript:window.location='static-report-config-dett.htm?id_store='+id_store+'&id_flow='+id_flow+'&description='+description+'&id_device='+id_device+'&id_line='+id_line;
	  window.location.href='static-report-config-dett.htm?id_store='+id_store+'&id_flow='+id_flow+'&description='+description+'&id_device='+id_device+'&id_line='+id_line;
  }
  
  function submitForm() {
	  $( "#ac-form" ).submit();
  }
  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
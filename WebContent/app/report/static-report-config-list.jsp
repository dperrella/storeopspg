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
  <link rel="stylesheet" href="../../js/nestable/nestable.css" type="text/css" />
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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("src_dsfun") %></h2>
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("src_dsfun") %>
			              </header>
			              
			              <section class="wrapper">
			              	  
			              	  <div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="static-report-config.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							  </div>
							    
                        	  <div id="msg">${msg}</div>
					       	  
					       	  <div class="line line-dashed b-b line-lg pull-in"></div>
					       	  
					       	  <form data-validate="parsley" id="sr-form" action="static-report-config-list.htm" method="post">
				              <input type="hidden" id="id_store" name="id_store" value="${id_store}"/>
				              <input type="hidden" id="tipoForm" name="tipoForm" value="I"/>
				              <input type="hidden" id="azione" name="azione" value="S"/>
				              
					       	  <div class="form-group pull-in clearfix">
							  	<div class="col-sm-6">
							  		<label><%=propLang.get("uc_description") %></label>
							  		<input type="text" class="form-control parsley-validated" name="descrizione" id="descrizione" 
							  			value="" data-required="true">
							  	</div>
							  	
							  	<div class="col-sm-1">
							  		<label><%=propLang.get("ordine") %></label>
							  		<select name="ordine" id="ordine" data-required="true" 
					          			class="form-control parsley-validated chosen-select">
							  			  <option value="0">0</option>
				                          <option value="1">1</option>
				                          <option value="2">2</option>
				                          <option value="3">3</option>
				                          <option value="4">4</option>
				                          <option value="5">5</option>
				                          <option value="6">6</option>
				                          <option value="7">7</option>
				                          <option value="8">8</option>
				                          <option value="9">9</option>
				                          <option value="10">10</option>
				                    </select>
							  	</div>
							  	<div class="col-sm-2">
							  		<label>Line</label>
							  		<select name="id_linea" id="id_linea"
					          			class="form-control chosen-select">
							  			  <option value="">Nuova Linea</option>
							  			  <c:forEach items="${rcLine}" var="rcLine" varStatus="status">
				                          <option value="${rcLine.cmp1}">Line ${rcLine.cmp1}</option>
				                          </c:forEach>
							  		</select>
							  	</div>
							  	<div class="col-sm-2">
							  		<label>Parent Id</label>
							  		<select name="parent_id" id="parent_id"
					          			class="form-control chosen-select">
							  			  <c:forEach items="${rc}" var="rc" varStatus="status">
				                          <option value="${rc.cmp2}">Id: ${rc.cmp2} - Line ${rc.cmp3}</option>
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
							  
							  <!-- 
							  <c:if test="${count > 0}">
							  <div class="line line-dashed b-b line-lg pull-in"></div>
							  <h3 class="font-thin"><%=propLang.get("mod_order") %></h3>
							  </c:if>
							  
							  <form id="row-form" name="row-form" method="post" action="static-report-config-list.htm">
							  <input type="hidden" name="tipoForm" id="rowTipoForm" value=""/>
							  <input type="hidden" name="azione" id="rowAzione" value="S"/>
							  
							  <ul class="list-group gutter list-group-lg list-group-sp sortable">
							  	<c:forEach items="${rc}" var="rc" varStatus="status">
							  	<li class="list-group-item" style="height:70px">
				                 	
				                 	<span class="pull-right" style="width:200px">
				                      <div class="row">
				                      	<div class="col-sm-6">
				                      	  <b><%=propLang.get("ordine") %>:</b><br/>
				                     	  ${rc.cmp4}	
				                      	</div>
				                      	<div class="col-sm-6">
				                      	  <a href="static-report-config-dett.htm?id_store=${id_store}&id_flow=${rc.cmp2}&description=${rc.xdtab}"
				                      	  	class="btn btn-default"><i class="fa fa-search"></i> <%=propLang.get("dett") %></a>
				                      	</div>
				                      </div>
				                    </span>
				                 	<span class="pull-left m-r-md"><i class="fa fa-sort text-muted fa m-r-sm"></i> ${rc.cmp3}</span>
				                    <span class="pull-left m-r-md" style="margin-top:-10px">
				                    	<div class="checkbox i-checks">
						                  <label>
						                    <input name="chk${rc.cmp2}" type="checkbox" value="S">
						                    <i></i>
						                  </label>
						                </div>
				                    </span>
				                    <span class="pull-left m-r-md"><b>Parent Id:</b><br/>
				                    ${rc.cmp5}</span>
				                    <div class="clear">
				                      <b><%=propLang.get("uc_description") %>:</b><br/>
				                      ${rc.xdtab}
				                      <input type="hidden" name="id" value="${rc.cmp2}"/>
				               	      <input type="hidden" name="id_linea" value="${rc.cmp3}"/>
				                      <input type="hidden" name="descrizione" value="${rc.xdtab}"/>
				                      <input type="hidden" name="ordine" value="${rc.cmp4}"/>
				                      <input type="hidden" name="parent_id" value="${rc.cmp5}"/>
				                      <input type="hidden" name="id_store" value="${id_store}"/>
				                    </div>
					          	</li>
					          	</c:forEach>
							  </ul>
							  </form>
							  -->
						  </section>
						  
						  <form id="row-form" name="row-form" method="post" action="static-report-config-list.htm">
						  <input type="hidden" name="tipoForm" id="rowTipoForm" value=""/>
						  <input type="hidden" name="azione" id="rowAzione" value="S"/>
						  
						  <div class="table-responsive">
			                <table class="table table-striped b-t m-b-none">
			                  <thead>
								  <tr>
								  	<th style="width:2%"></th>
								  	<th style="width:5%">Id</th>
								  	<th style="width:5%">Line</th>
								  	<th style="width:5%">Parent Id</th>
								  	<th><%=propLang.get("uc_description") %></th>
								  	<th style="width:5%"><%=propLang.get("ordine") %></th>
								  	<th style="width:10%"></th>
								  </tr>
							  </thead>
							  <tbody>
							  <c:forEach items="${rc}" var="rc" varStatus="status">
							  	  <tr>
							  	  	<td><div class="checkbox i-checks">
						                  <label>
						                    <input name="chk${rc.cmp2}" type="checkbox" value="S">
						                    <i></i>
						                  </label>
						                </div>
						                <input type="hidden" name="id" value="${rc.cmp2}"/>
					               	    <input type="hidden" name="id_linea" value="${rc.cmp3}"/>
					                    <input type="hidden" name="descrizione" value="${rc.xdtab}"/>
					                    <input type="hidden" name="ordine" value="${rc.cmp4}"/>
					                    <input type="hidden" name="parent_id" value="${rc.cmp5}"/>
					                    <input type="hidden" name="id_store" value="${id_store}"/>
						            </td>
						            <td><span class="label bg-light">${rc.cmp2}</span></td>
						            <td>${rc.cmp3}</td>
						            <td>${rc.cmp5}</td>
						            <td>${rc.xdtab}</td>
						            <td>${rc.cmp4}</td>
						            <td><a href="static-report-config-dett.htm?id_store=${id_store}&id_flow=${rc.cmp2}&description=${rc.xdtab}"
				                      	  	class="btn btn-default"><i class="fa fa-search"></i> <%=propLang.get("dett") %></a></td>
							  	  </tr>
							  </c:forEach>
							  </tbody>
							</table>
						  </div>
						  </form>
						  
						  <c:if test="${count > 0}">
							  
						  <footer class="panel-footer text-right bg-light lter">
					      	<!-- 
					      	<a href="#" onclick="rowFormSubmit('M')" class="btn btn-success btn-lg"><%=propLang.get("mod_sel") %></a>
					      	-->
					      	<a href="#" onclick="rowFormSubmit('E')" class="btn btn-danger btn-lg"><%=propLang.get("eli_sel") %></a>
					      </footer>
					      
					      </c:if>
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
  
  <script src="../../js/sortable/jquery.sortable.js"></script>
  <script src="../../js/nestable/jquery.nestable.js"></script>
  <script src="../../js/nestable/demo.js"></script>

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
	
  $('#descrizione').val('${descrizione}');
  $('#parent_id').val('${parent_id}');
  
  function reloadPage() {
	  
	  var id_store = $('#id_store').val();
	  var descrizione = $('#descrizione').val();
	  var parent_id = $('#parent_id').val();
	  
	  javascript:window.location='static-report-config-list.htm?id_store='+id_store+'&descrizione='+descrizione+'&parent_id='+parent_id;
	  window.location.href='static-report-config-list.htm?id_store='+id_store+'&descrizione='+descrizione+'&parent_id='+parent_id;
  }
  
  function submitForm() {
	  $( "#sr-form" ).submit();
  }
  
  function rowFormSubmit(tipoForm) {
	  
	  $('#rowTipoForm').val(tipoForm);
	  $( "#row-form" ).submit();
  }
  
  </script>
  
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
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
  <title><%=propLang.get("plt_dett") %> | SmartHR</title>
  <meta name="description" content="Desk Microtech" />
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
          <section class="vbox">
            <section>
              <section class="hbox stretch">
            	
            	<%@ include file="../../inc2/menuApp7.jsp" %>
                <section>
	              <section class="vbox">
	                <section class="scrollable padder-lg" id="bjax-target">
	                
	                <div class="row row-sm">
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("plt_dett") %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="planification-type-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("plt_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="planification-type-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("emp_codice") %></label>
						          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("plt_descrizione") %> (<%=propLang.get("generico") %>)</label>
						        <input type="text" id="description" name="description" class="form-control" value="${description}" data-minlength="3" data-maxlength="250" data-required="true" ${btnActive}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-2">
						        	<label><%=propLang.get("plt_min") %></label>
						        	<input type="text" id="min_time" name="min_time" class="form-control" value="${min_time}" data-maxlength="4" data-type="number" data-required="true" ${btnActive}>   
						        </div>
						        
						        <div class="col-sm-2">
						        	<label><%=propLang.get("plt_max") %></label>
						        	<input type="text" id="max_time" name="max_time" class="form-control" value="${max_time}" data-maxlength="4" data-type="number" data-required="true" ${btnActive}>   
						        </div>
						    </div>
						    
						    <!-- 
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						        	<label><%=propLang.get("plt_direzione") %></label>
						        	<select class="form-control" name="direction" id="direction" ${btnActive}>
						        		<option value="">???????</option> 
						        	</select>  
						        </div>
						    </div>
						    -->
						    <input type="hidden" name="direction" value="">
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						        	<label><%=propLang.get("plt_approvazione") %></label>
						        	<select class="form-control" name="approvation" id="approvation" ${btnActive}>
						        		<option value="N"><%=propLang.get("plt_noNeedApp") %></option> 
						        		<option value="S"><%=propLang.get("plt_needApp") %></option> 
						        	</select>  
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						        	<label><%=propLang.get("plt_colore") %></label>
						        	<input type="text" name="color" id="color" value="${color}" class="form-control" ${btnActive}/>
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("datiagg") %></span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <div class="form-group">
						        <label><%=propLang.get("plt_descrizione") %></label>
						        <input type="text" id="cus_description" name="cus_description" class="form-control" value="${cus_description}" ${btnActive}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						        	<label><%=propLang.get("expcode") %></label>
						        	<input type="text" name="expcode" id="expcode" value="${expcode}" class="form-control" ${btnActive}/>
						        </div>
						    </div>
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            
			            
						 
						 
						</form>
			            
			            </div>
	                
	                </section>
	              </section>
	            </section>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%=propLang.get("ultplan") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${plan}" var="plan" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="clear">
			                        <div><a href="../planner/planner-list2.htm?tp=I&year=${plan.cmp1}&week=${plan.cmp2}&id_departments=${plan.cmp3}">${plan.cmp4}</a></div>
			                        <small class="text-muted smallest">${plan.cmp1} Sett. ${plan.cmp2}</small>
			                    </div>
		                      </li>
	                        </c:forEach>
	                          
	                        </ul>
                        </div>
                    </section>
                  </section>
                </section>
                
                <!-- side content -->
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
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
  
  <script>
  	$('#direction').val('${direction}');
  	$('#approvation').val('${approvation}');
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
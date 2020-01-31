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
  <title><%=propLang.get("cus_dett") %> | SmartSuite</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
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
          <section class="vbox">
            <section id="bjax-target">
              <section class="hbox stretch">
            	
            	
                <aside class="col-sm-9 padder " >
	              <section class="vbox">
	                <section class="scrollable hover">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("cus_dett") %></h2>
		                
		                <form data-validate="parsley" method="post" id="myform" action="customers-dett.htm" onsubmit="return Controlla();"">
		                <input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("cus_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="customers-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
			              <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">General Information</span>
	                          </div>
		                    </div>
					        <br/>
						  
						  
						     
						    <!-- 
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-2">
						          <label>Id Customers</label>
						          <input type="text" id="id" name="id" class="form-control" 
						          	value="${id_customers}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    -->
						    <input type="hidden" id="id" name="id" value="${id_customers}">  
						    
						    <!-- 
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Contract</label><br/>
						          <div class="btn-group" data-toggle="buttons">
									  <label class="btn btn-default" id="btn_agreement_si">
									    <input type="radio" id="agreement_si" name="agreement" value="true"> SI&nbsp;&nbsp;
									  </label>
									  <label class="btn btn-default active" id="btn_agreement_no">
									    <input type="radio" id="agreement_no" name="agreement" value="false"> NO
									  </label>
								  </div>
						        </div>
						    </div>
						    -->
						    
						    <input type="hidden" id="agreement" name="agreement" value="">
						    
						    <!-- 
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Export to Analitycs</label><br/>
						          <div class="btn-group" data-toggle="buttons">
									  <label class="btn btn-default" id="btn_analytics_export_si">
									    <input type="radio" id="analytics_export_si" name="analytics_export" value="t"> SI&nbsp;&nbsp;
									  </label>
									  <label class="btn btn-default active" id="btn_analytics_export_no">
									    <input type="radio" id="analytics_export_no" name="analytics_export" value="f"> NO
									  </label>
								  </div>
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    -->
						    
						    <input type="hidden" id="analytics_export" name="analytics_export" value="">
						    
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Company Name</label>
						          <input type="text" id="companyname" name="companyname" class="form-control" 
						          	data-minlength="3" data-required="true" value="${companyname}" ${readonly}>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-6">
						          <label>Description</label>
						          <input type="text" id="description" name="description" class="form-control" 
						          	data-minlength="3" data-required="true" value="${description}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-3">
						          <label>Account Id</label>
						          <input type="text" id="account_code" name="account_code" class="form-control" 
						          	data-required="true" value="${account_code}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-3">
						          <label> Evolve Store Account Id</label>
						          <input type="text" id="account_code_evolve" name="account_code_evolve" class="form-control" 
						          	value="${account_code_evolve}" ${readonly}>   
						        </div>						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Brand Name</label>
						          <input type="text" id="brandname" name="brandname" class="form-control" 
						          	value="${brandname}" ${readonly}>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Brand Logo</label>
						          <input type="text" id="brandlogo" name="brandlogo" class="form-control" 
						          	value="${brandlogo}" ${readonly}>   
						        </div>
						        
						        <!-- 
						        <div class="col-sm-2">
						          <label>Template</label>
						          <select name="id_optempl" id="id_optempl" class="form-control">
						          	<option value="">-</option>
						          	<c:forEach items="${tmp}" var="tmp" varStatus="status">
						          	<option value="${tmp.xcdel}">${tmp.xdtab}</option>
						         	</c:forEach>
						          </select>
						        </div>
						        -->
						        <input type="hidden" name="id_optempl" id="id_optempl" value=""/>
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Country</label>
						          <select name="customer_country_id" id="customer_country_id" class="form-control"
						          	data-required="true" >
						          	<option value="">-</option>
						          	<c:forEach items="${naz}" var="naz" varStatus="status">
						          	<option value="${naz.xcdel}">${naz.xdtab}</option>
						         	</c:forEach>
						          </select>
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">Contact Information</span>
	                          </div>
		                    </div>
					        <br/>
					        
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Contact</label>
						          <input type="text" id="contact" name="contact" class="form-control" 
						          	value="${contact}" ${readonly}>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Phone</label>
						          <input type="text" id="phone" name="phone" class="form-control" 
						          	value="${phone}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Mobile</label>
						          <input type="text" id="mobile" name="mobile" class="form-control" 
						          	value="${mobile}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Mail</label>
						          <input type="text" id="mail" name="mail" class="form-control" 
						          	value="${mail}" ${readonly}>   
						        </div>
						    </div>
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<input type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive} value="<%=propLang.get("btnConferma") %>"/>
					      </footer>  
		  				
		  				</section>
		  				</section>
	              </section>
	            </aside>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk w-f-md">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%=propLang.get("sto_dsfun") %></p>
                        </div>
                        <div id="stores">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${sto}" var="sto" varStatus="status">
	                          <li class="list-group-item">
	                          	<div class="pull-left m-l-xs m-r-sm">
									<a href="../anag/stores-dett.htm?tipoForm=E&id=${sto.id}" class="btn btn-default btn-xs btn-danger m-r"><i class="fa fa-times-circle"></i></a>	                          	
	                          	 	<a href="../anag/stores-dett.htm?tipoForm=M&id=${sto.id}" class="btn btn-default btn-xs">
	                          	 	 	${sto.cmp3}</a>
	                          	</div>
			                    <div class="pull-right m-l-xs m-r-sm">
	                          	 	<span class="label bg-light">${sto.cmp2} Board</span> 
	                          	 </div>
	                          	 <div class="clear text-ellipsis">
		                        	${sto.description}
		                        </div> 
		                      </li>
	                        </c:forEach>
	                        </ul>
                        </div>
                    </section>
                    <footer class="footer footer-md bg-light dker">
	                    
	                    <c:if test="${tipoForm!='I'}">
	                    <a href="../anag/stores-dett.htm?tipoForm=I&id_customer=${id_customers}"
	                    	class="btn btn-default btn-block">New Store</a> 
	                    </c:if>
	                    
	                </footer>
                  </section>
                </section>
                
                </form>
                
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
				        
                        <a href="customers-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-user icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("cus_cus") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="customers-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("cus_cerca") %>">
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
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
  	
	var agreement = '${agreement}';
	
	if(agreement=='t') {
		//$("#agreement_si").attr('checked', 'checked');
		//$("#btn_agreement_si").addClass("active");
		//$("#btn_agreement_no").removeClass("active");
		$("#agreement").val('true');	
	}
	else {
		//$("#agreement_no").attr('checked', 'checked');
		//$("#btn_agreement_no").addClass("active");
		////$("#btn_agreement_si").removeClass("active");
		$("#agreement").val('false');
	}
	
	var analytics_export = '${analytics_export}';
	if(analytics_export=='t') {
		//$("#analytics_export_si").attr('checked', 'checked');
		//$("#btn_analytics_export_si").addClass("active");
		//$("#btn_analytics_export_no").removeClass("active");
		$("#analytics_export").val('t');
	}
	else {
		//$("#analytics_export_no").attr('checked', 'checked');
		//$("#btn_analytics_export_no").addClass("active");
		//$("#btn_analytics_export_si").removeClass("active");
		$("#analytics_export").val('f');
	}
	
	$('#customer_country_id').val('${customer_country_id}');
	$('#id_optempl').val('${id_optempl}');
	
	
	
	
	
	function Controlla(){ 
	
		/*
		var tipoForm = $('#tipoForm').val();
		var resp = "OK";
		if (tipoForm == "I"){
		
			
		
		    	$.ajax({
		    		url : "../../GetUnique",
		    		async: false,
		    		data : {
		    			tabs: 'C',
		    			accid : document.getElementById("account_code").value
		    		},
		    		success : function(responseText) {
		    			if ($.trim(responseText) == "KO"){
		    				resp = "KO";
		    				alertify.error("account_code Already Present");
		    			}
		    		}
		    	});	 
		
		
		}	 
		
		alert(resp);
		  
		if (resp == "KO"){
			return false;
		}	
		else {
			
		}
		*/
		
	}
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
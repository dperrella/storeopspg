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
  <title><%=propLang.get("sto_dett") %> | SmartSuite</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
    <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
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
				   margin-left: -100px;" />
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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("sto_dett") %></h2>
		                
		                <form data-validate="parsley" method="post" action="stores-dett.htm">
		                <input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("sto_dett") %> ${description}
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<!-- <li><a href="stores-list.htm"><%//=propLang.get("torna") %></a></li> -->
									<!-- QUI MOD -->
									<li><a href="stores-list.htm?customerid=${id_customer}"><%=propLang.get("torna") %></a></li>
									<li><a href="customers-dett.htm?tipoForm=${tipoForm}&id=${id_customer}&customerid=${id_customer}">Customer Detail</a></li>
									<li>
									<a href="stores-google.htm?id=${storeid}&google_uid=${googleUid}&customerid=${id_customer}" >
									<c:choose>
									  <c:when test="${google_uid==''}">
										<i class="fa fa-circle text-danger"></i>&nbsp;    
									  </c:when>
									  <c:otherwise>
									  	<i class="fa fa-circle text-success"></i>&nbsp;
									  </c:otherwise>
									</c:choose>
									Google Places Connect</a>
				                    </li>  	
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
						          <label>Id Stores</label>
						          <input type="text" id="id" name="id" class="form-control" 
						          	value="${storeid}" readonly>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    -->
						    <input type="hidden" id="id" name="id" value="${storeid}">   
						    
						    <!-- 
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Export to Analitycs</label><br/>
						          <div class="btn-group" data-toggle="buttons">
									  <label class="btn btn-default" id="btn_analytics_export_si">
									    <input type="radio" id="analytics_export_si" name="flag_analytics" value="t"> SI&nbsp;&nbsp;
									  </label>
									  <label class="btn btn-default active" id="btn_analytics_export_no">
									    <input type="radio" id="analytics_export_no" name="flag_analytics" value="f"> NO
									  </label>
								  </div>
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    -->
						    <input type="hidden" id="flag_analytics" name="flag_analytics" value="f">
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Description</label>
						          <input type="text" id="description" name="description" class="form-control" 
						          	data-minlength="3" data-required="true" value="${description}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Customer</label>
						          
						          <c:if test = "${tipoForm=='I'}">
						          <select id="id_customer" name="id_customer" class="form-control ss" ${readonly} onchange="SelCodeCustomers(this); loadCategory(this.value)">
						          	<option value="">-</option>
						          	<c:forEach items="${cus}" var="cus" varStatus="status">
						          	<option value="${cus.xcdel}">${cus.xdtab} - ${cus.cmp1}</option>
						         	</c:forEach>
						          </select> 
						          </c:if>
						          
						          <c:if test = "${tipoForm=='M'}">
						          <select id="id_customer_vid" name="id_customer_vid" class="form-control ss" ${readonly} onchange="SelCodeCustomers(this);loadCategory(this.value)">
						          	<option value="">-</option>
						          	<c:forEach items="${cus}" var="cus" varStatus="status">
						          	<option value="${cus.xcdel}">${cus.xdtab} - ${cus.cmp1}</option>
						         	</c:forEach>
						          </select> 
						          <input type="hidden" name="id_customer" value="${id_customer}"/>
						          </c:if>
						          
						          <c:if test = "${tipoForm=='V'}">
						          <select id="id_customer_vid" name="id_customer_vid" class="form-control ss" ${readonly} onchange="SelCodeCustomers(this);loadCategory(this.value)">
						          	<option value="">-</option>
						          	<c:forEach items="${cus}" var="cus" varStatus="status">
						          	<option value="${cus.xcdel}">${cus.xdtab} - ${cus.cmp1}</option>
						         	</c:forEach>
						          </select> 
						          <input type="hidden" name="id_customer" value="${id_customer}"/>
						          </c:if>
						          
						          <c:if test = "${tipoForm=='E'}">
						          <select id="id_customer_vid" name="id_customer_vid" class="form-control ss" ${readonly} onchange="SelCodeCustomers(this);loadCategory(this.value)">
						          	<option value="">-</option>
						          	<c:forEach items="${cus}" var="cus" varStatus="status">
						          	<option value="${cus.xcdel}">${cus.xdtab} - ${cus.cmp1}</option>
						         	</c:forEach>
						          </select> 
						          <input type="hidden" name="id_customer" value="${id_customer}"/>
						          </c:if>
						          
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Store ID</label>
						          <input type="text" id="store_code" name="store_code" class="form-control" 
						          	data-required="true" value="${store_code}" ${readonly}>   
						        </div>
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Country</label>
						          <select id="country" name="country" class="form-control ss" onchange="chgNaz(this.value)" ${readonly}>
						          	<option value="">-</option>
						          	<c:forEach items="${naz}" var="naz" varStatus="status">
						          	<option value="${naz.xcdel}">${naz.xdtab}</option>
						         	</c:forEach>
						          </select> 
						        </div>
						        
						        <div id="regdiv">
							        <div class="col-sm-4">
							          <label id="region_label">Region</label>
							          <select id="region" name="region" class="form-control ss"  onchange="chgReg(this.value)" ${readonly}>
							          	<option value="">-</option>
							          	<c:forEach items="${reg}" var="reg" varStatus="status">
							          	<option value="${reg.xcdel}">${reg.xdtab}</option>
							         	</c:forEach>
							          </select> 
							        </div>
						        </div>
						        
						        <div id="prodiv">
							        <div class="col-sm-4">
							          <label id="province_label">Province</label>
							          <select id="province" name="province" class="form-control ss" onchange="chgPro(this.value)" ${readonly}>
							          	<option value="">-</option>
							          	<c:forEach items="${pro}" var="pro" varStatus="status">
							          	<option value="${pro.xcdel}">${pro.xdtab}</option>
							         	</c:forEach>
							          </select>  
							        </div>
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-8">
						          <label>City</label>
						          <select id="city" name="city" class="form-control ss"  onchange="chgCit(this.value)" ${readonly}>
						          	<option value="">-</option>
						          	<c:forEach items="${cit}" var="cit" varStatus="status">
						          	<option value="${cit.xcdel}">${cit.xdtab}</option>
						         	</c:forEach>
						          </select> 
						        </div>
						   
						        <div class="col-sm-4">
						          <label>Timezone</label>
						          <select id="timezone" name="timezone" class="form-control ss" data-required="true" ${readonly}>
						          	<option value="">-</option>
						          	<c:forEach items="${tmz}" var="tmz" varStatus="status">
						          	<option value="${tmz.xcdel}">${tmz.cmp1} - ${tmz.xdtab}</option>
						         	</c:forEach>
						          </select> 
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-8">
						          <label>Address</label>
						          <input type="text" id="address" name="address" class="form-control" 
						          	data-minlength="3" value="${address}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Postal Code</label>
						          <input type="text" id="postalcode" name="postalcode" class="form-control" 
						          	data-minlength="5" value="${postalcode}" ${readonly}>  
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-8">
						          <label>Phone</label>
						          <input type="text" id="sphone" name="sphone" class="form-control" 
						          	data-minlength="3"  value="${sphone}" ${readonly}>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Mail Address</label>
						          <input type="text" id="smail" name="smail" class="form-control" 
						          	data-type="email" value="${smail}" ${readonly}>  
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<!-- 
						        <div class="col-sm-4">
						          <label>Closing Time Default Template</label>
						          <select id="id_optempl" name="id_optempl" class="form-control" ${readonly}>
						          	<option value="">-</option>
						          	<c:forEach items="${tmp}" var="tmp" varStatus="status">
						          	<option value="${tmp.xcdel}">${tmp.xdtab}</option>
						         	</c:forEach>
						          </select> 
						        </div>
						        -->
						        
						        <input type="hidden" id="id_optempl" name="id_optempl" value=""/>
						        
						        <div class="col-sm-6">
						          <label>Account Number (SAP ID)</label>
						          <input type="text" id="sapid" name="sapid" class="form-control" 
						          	value="${sapid}" ${readonly}>  
						        </div>
						        
						        <div class="col-sm-6">
						          <label>Customer Id Store</label>
						          <input type="text" id="customeridstore" name="customeridstore" class="form-control" 
						          	value="${customeridstore}" ${readonly}>  
						        </div>
						    </div>
						    
						    
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        
						        <div class="col-sm-4">
						          <label>Customer Id</label>
						          <input type="text" id="customerid" name="customerid" class="form-control" 
						          	value="" ${readonly} readonly>  
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Brand Name</label>
						          <input type="text" id="brandname" name="brandname" class="form-control" 
						          	 value="${brandname}" ${readonly}>  
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Brand Logo</label>
						          <input name="brandlogo" id="brandlogo" type="file" class="form-control" ${readonly}/>
						        </div>
						        
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	
						    	<div class="col-sm-4">
						        	<label>Store Close</label>
						        	<div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" value="true" name="flagclose" id="flagclose" ${readonly}>
			                            <i></i>
			                          </label>
			                        </div>
			                    </div>
			                    
			                    <div class="col-sm-4">
						        	<label>Store Under Construction</label>
						        	<div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" value="true" name="under_construction" id="under_construction" ${readonly}>
			                            <i></i>
			                          </label>
			                        </div>
			                    </div>
			                    
						    </div>
						    
						    <div id="_cat">
						    	
						    	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
							    <div class="panel-footer bg-light">
			                      <div class="padder">
		                            <span class="h4 block">Category</span>
		                          </div>
			                    </div>
						        <br/>
						        
						        <div id="_catsel"></div>
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">Adjustment Factor</span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <div class="form-group pull-in clearfix">
						    
						        <div class="col-sm-12">
						          <label>Adjustment Factor</label>
						          <select id="adjustment_factor" name="adjustment_factor" class="form-control ss" ${readonly}>
						          	<option value="">-</option>
						          	<%
						          	for(int i=-100;i<=100;i++) {
						          	%>
						          	<option value="<%=i%>"><%=i%></option>
						         	<%
						          	}
						         	%>
						          </select> 
						        </div>
						        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">Notes</span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Notes</label>
						          <textarea id="notes" name="notes" class="form-control" style="height:100px"
						            ${readonly}>${notes}</textarea>  
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">Store Rappresentative</span>
	                          </div>
		                    </div>
					        <br/>
					        
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label>Contact Person</label>
						          <input type="text" id="contact" name="contact" class="form-control" 
						          	data-minlength="3" value="${contact}" ${readonly}>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Phone</label>
						          <input type="text" id="cphone" name="cphone" class="form-control" 
						          	data-minlength="3" value="${cphone}" ${readonly}>   
						        </div>
						        <div class="col-sm-4">
						          <label>Mobile</label>
						          <input type="text" id="cmobile" name="cmobile" class="form-control" 
						          	data-minlength="3" value="${cmobile}" ${readonly}>   
						        </div>
						        <div class="col-sm-4">
						          <label>Mail</label>
						          <input type="text" id="cmail" name="cmail" class="form-control" 
						          	data-type="email" value="${cmail}" ${readonly}>   
						        </div>
						    </div>
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>  
		  				
		  				</section>
		  				</section>
	              </section>
	            </aside>
	            
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk w-f-md">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%=propLang.get("dev_dsfun") %></p>
                        </div>
                        <div id="stores">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${dev}" var="dev" varStatus="status">
	                          <li class="list-group-item">
	                          	<div class="pull-left m-l-xs m-r-sm">
	                          	<a href="../anag/devices-dett.htm?tipoForm=E&id=${dev.xcdel}&ids=${storeid}" class="btn btn-default btn-xs btn-danger  m-r"><i class="fa fa-times-circle"></i></a>
	                          	 	<a href="../anag/devices-dett.htm?tipoForm=M&id=${dev.xcdel}&ids=${storeid}" class="btn btn-default btn-xs">
	                          	 	 	${dev.cmp3}</a>
	                          	</div>
	                          	${dev.cmp4}
			                    <div class="clear text-ellipsis">
		                        	${dev.xdtab}
		                        </div> 
		                      </li>
	                        </c:forEach>
	                        </ul>
                        </div>
                    </section>
                    <footer class="footer footer-md bg-light dker">
	                    
	                    <c:if test="${tipoForm!='I'}">
	                    <a href="../anag/devices-dett.htm?tipoForm=I&id_store=${storeid}&ids=${storeid}"
	                    	class="btn btn-default btn-block">New Device</a> 
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
				        
                        <a href="stores-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-user icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("sto_sto") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="stores-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("sto_cerca") %>">
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
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js?v=2"></script>
  
  
  
  <script>
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		selpick();
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
	
  	
	function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		 });
	}
	
	if('${country}'!='3'&&'${country}'!='230') {
		$('#regdiv').hide();
		$('#prodiv').hide();
		$('#region').val('');
		$('#city').val('');	
		
		
		
		//$('.ss').selectpicker('refresh');
	}
	
	$('#id_customer').val('${id_customer}');
	$('#id_customer_vid').val('${id_customer}');
	
	$('#country').val('${country}');
	$('#region').val('${region}');
	$('#province').val('${province}');
	$('#city').val('${city}');
	$('#id_optempl').val('${id_optempl}');
	$('#adjustment_factor').val('${adjustment_factor}');
	
	$('#timezone').val('${timezone}');
	
	if('${country}'=='230') {
		$('#region_label').html('State');
		$('#province_label').html('County');
	}
	else {
		$('#region_label').html('Region');
		$('#province_label').html('Province');
	}
	
	
	/*
	var analytics_export = '${flag_analytics}';
	if(analytics_export=='t') {
		$("#analytics_export_si").attr('checked', 'checked');
		$("#btn_analytics_export_si").addClass("active");
		$("#btn_analytics_export_no").removeClass("active");
	}
	else {
		$("#analytics_export_no").attr('checked', 'checked');
		$("#btn_analytics_export_no").addClass("active");
		$("#btn_analytics_export_si").removeClass("active");
	}
	*/
	
	var flagclose = '${flagclose}';
	if(flagclose=='t') {
		$('#flagclose').attr('checked', true);
	}
	
	var under_construction = '${under_construction}';
	if(under_construction=='t') {
		$('#under_construction').attr('checked', true);
	}
	
	
	
	function chgNaz(idnaz) {
		
		if(idnaz!='3'&&idnaz!='230') {
			$('#regdiv').hide();
			$('#prodiv').hide();
			
			// Call ws GetComuni
			$('#region').empty();
			$('#province').empty();
			$('#city').empty();
			
			$.getJSON('../../servlet/ws/GetComuniEst?id_naz='+idnaz, function (data) {
				
				// Legge json di risposta...
		    	var lista = data.listings;
		    	var id = "";
		    	var name = "";
		    	$.each(lista, function(index, lista) {
					id = lista.id;
		    		name = lista.name;
		    		
		    		$('#city').append($('<option>', {
		    		    value: id,
		    		    text: name
		    		}));
		    	});
		    	
		    	$('.ss').selectpicker('refresh');
				
			});
			
		
		}
		else {
			
			if(idnaz=='230') {
				$('#region_label').html('State');
				$('#province_label').html('County');
			}
			else {
				$('#region_label').html('Region');
				$('#province_label').html('Province');
			}
			$('#regdiv').show();
			$('#prodiv').show();
			$('#region').empty();
			$('#province').empty();
			$('#city').empty();
			
			// Call ws GetRegioni
			$.getJSON('../../servlet/ws/GetRegioni?id_naz='+idnaz, function (data) {
				
				// Legge json di risposta...
		    	var lista = data.listings;
		    	var id = "";
		    	var name = "";
		    	$.each(lista, function(index, lista) {
					id = lista.id;
		    		name = lista.name;
		    		
		    		$('#region').append($('<option>', {
		    		    value: id,
		    		    text: name
		    		}));
		    	});
		    	
		    	$('.ss').selectpicker('refresh');
		    	
			});
			
			
			
		}
			
	}
	
	function chgReg(idreg) {
		
		// Call ws GetProvince
		$('#province').empty();
		$('#city').empty();
		
		// Call ws GetProvince
		$.getJSON('../../servlet/ws/GetProvince?id_reg='+idreg, function (data) {
			
			// Legge json di risposta...
	    	var lista = data.listings;
	    	var id = "";
	    	var name = "";
	    	$.each(lista, function(index, lista) {
				id = lista.id;
	    		name = lista.name;
	    		
	    		$('#province').append($('<option>', {
	    		    value: id,
	    		    text: name
	    		}));
	    	});
	    	
			$('.ss').selectpicker('refresh');
			
		});
		
		
	}
	
	function chgPro(idpro) {
		
		// Call ws GetComuni
		$('#city').empty();
		
		// Call ws GetProvince
		$.getJSON('../../servlet/ws/GetComuni?id_pro='+idpro, function (data) {
			
			// Legge json di risposta...
	    	var lista = data.listings;
	    	var id = "";
	    	var name = "";
	    	$.each(lista, function(index, lista) {
				id = lista.id;
	    		name = lista.name;
	    		
	    		$('#city').append($('<option>', {
	    		    value: id,
	    		    text: name
	    		}));
	    	});
	    	

			$('.ss').selectpicker('refresh');
	    	
		});
		
		
		
	}
	
	
	function Controlla(){ 
		
		var resp = "OK";
		if (document.getElementById("tipoForm").value == "I"){
		
		/**/
		    	$.ajax({
		    		url : "../../GetUnique",
		    		async: false,
		    		data : {
		    			tabs: 'S',
		    			accid : document.getElementById("id_customer").value,
		    			stoid : document.getElementById("store_code").value
		    		},
		    		success : function(responseText) {
		    			if (responseText == "KO"){
		    				resp = "KO";
		    				alertify.error("account_code,store_code Already Present");
		    			}
		    		}
		    	});	 
		/**/
		
		}	 
		  
		if (resp == "KO"){
			return false;
		}	
		
	}

	function SelCodeCustomers(str){
		
/**/
    	$.ajax({
    		url : "../../GetAccountsCodes",
    		async: false,
    		data : {
    			idcust: str.value
    		},
    		success : function(responseText) {
    			$("#customerid").val(responseText);
    		}
    	});	 
/**/
		
	}
	
	
	
	var accountCode = "${AccountCode}";
	if (accountCode != ""){
		$("#customerid").val(accountCode); 
	}
	
	if ($("#tipoForm").val() == "M" || $("#tipoForm").val() == "V" || $("#tipoForm").val() == "E" ){
		$("#id_customer_vid").prop("disabled", true); 
	}	
	
	
	
	
	
	
	$('#_cat').hide();
	
	$(document).ready(function(){
		
		loadCategory('${id_customer}')
	 
	});
	
	function loadCategory(idcus) {
		
		if(idcus!='') {
			console.log("../../servlet/ws/GetCustomersCategory?idcus="+idcus);
			
			$.ajax({ 
				url: "../../servlet/ws/GetCustomersCategory?idcus="+idcus,
			   	method:"POST",
			   	dataType: "json",       
			   	beforeSend: function( xhr ) {
			   	    showLoader2();
			   	},
			   	success: function(data)  
		   		{
			   		hideLoader2();
		  			
			   		if(data.length>0) {
			   			$('#_cat').show();
			   			//console.log(JSON.stringify(data))
			   			var cat = data;
			   			$.each(cat, function(index, cat) {
			   				parseCategory(cat, idcus, '_catsel');
			   			});
			   			
			   		}
			   		
		   		}   
		 	});
		}
	}
	
	function parseCategory(cat, idcus, _div) {
		
		var markup = '';
		var callfun = false;
		if(cat.value.length==0) {
			
			if(cat.nodes.length==0) {
				console.log("no value for "+cat.name+" in nodes");
			}
			else {
				
				markup = '<input type="hidden" name="_cat" value="'+cat.name+'"/><select id="_cat_'+cat.name+'" name="_cat_'+cat.name+'" class="ss form-control" onchange="checkNodes(this.value, \''+cat.name+'\',  \''+idcus+'\')">'
					+'<option value=""></option>';
				
				//var val = [];
				$.each(cat.nodes, function(index, node) {
					//val.push(node.name);
					markup+= '<option value="'+node.name+'">'+node.name+'</option>';
				});
				//console.log("value for "+cat.name+": "+JSON.stringify(val));
				markup+= '</select>';
				
				callfun = true;
			}
		}
		else {
			
			markup = '<input type="hidden" name="_cat" value="'+cat.name+'"/><select id="_cat_'+cat.name+'" name="_cat_'+cat.name+'" class="ss form-control">'
				+'<option value=""></option>';
			
			$.each(cat.value, function(index, value) {
				markup+= '<option value="'+value+'">'+value+'</option>';
			});
			//console.log("value for "+cat.name+": "+JSON.stringify(val));
			markup+= '</select>';
			
			callfun = false;
			
			//console.log("value for "+cat.name+": "+JSON.stringify(cat.value));
		}
		
		markup='<div class="form-group pull-in clearfix"><div class="col-sm-12"><label>'+cat.name+'</label>'+markup+'</div></div>'
			+'<div id="_'+cat.name+'_parent"></div>';
		
		//$('#'+_div).html('');
		$('#'+_div).append(markup);
		
		
		if(callfun==true) {
			//alert("callfun");
			$("[id='_cat_"+cat.name+"']").on('change', function () {
		        var val = this.options[this.selectedIndex].value;
				//alert(val);
				
			});
		}	
		
		
		
		
		
		<c:forEach items="${cat}" var="cat" varStatus="status">
	  	
			if(cat.name=='${cat.xcdel}') {
				console.log('*** ${cat.xcdel}:${cat.xdtab}');
				$("[id='_cat_${cat.xcdel}']").val('${cat.xdtab}').trigger("change");
				//$('#_cat_${cat.xcdel} option[value="${cat.xdtab}"]').attr('selected','selected');
			}
		
		</c:forEach>
		selpick();
		
		
		
	}
	
	function checkNodes(_category, _parent_category, _idcus) {
		//alert(_category+' '+_parent_category);
		
		$.ajax({ 
			url: "../../servlet/ws/GetCustomersCategory2?idcus="+_idcus+"&category="+_category+"&parent_category="+_parent_category,
		   	method:"POST",
		   	dataType: "json",       
		   	beforeSend: function( xhr ) {
		   	    showLoader2();
		   	},
		   	success: function(data)  
	   		{
		   		hideLoader2();
	  			
		   		if(data.length>0) {
		   			$('#_cat').show();
		   			//console.log(JSON.stringify(data))
		   			var cat = data;
		   			$.each(cat, function(index, cat) {
		   				//parseCategory(cat);
		   				
		   				//alert(JSON.stringify(cat));
		   				
		   				$('#_'+_parent_category+'_parent').html('');
		   				//$('#_'+_parent_category+'_parent').append(JSON.stringify(cat));
		   				
		   				parseCategory(cat, _idcus, '_'+_parent_category+'_parent');
		   				
		   			});
		   			
		   		}
		   		
	   		}   
	 	});
	}
	
	
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
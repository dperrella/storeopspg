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
          <section class="vbox">
            <section id="bjax-target">
              <section class="hbox stretch">
            	
            	
                <aside class="col-sm-9 padder " >
	              <section class="vbox">
	                <section class="scrollable hover">
	                
	                <form id="my-form" data-validate="parsley" method="post" action="users-dett.htm">
        
	                	<h2 class="font-thin m-b"><%=propLang.get("usr_dett") %></h2>
		                
		                 
		                <input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("usr_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="users-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("usr_id") %></label>
						          
						          <c:choose>
								    <c:when test="${tipoForm == 'I'}">
								        <input type="text" id="idusr" name="idusr" class="form-control" 
								          	data-minlength="3" data-maxlength="100" 
								          	data-type="email" 
								          	data-required="true" value="${idusr}" ${readonly}>   
								    </c:when>    
								    <c:otherwise>
								        <input type="text" id="idusr" name="idusr" class="form-control" 
								          	data-minlength="3" data-maxlength="100" 
								          	data-required="true" value="${idusr}" ${readonly}>   
								    </c:otherwise>
								  </c:choose>

						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_passwordDett") %></label>
						        <input type="password" id="pwd" name="pwd" class="form-control" value="${pwd}" data-minlength="3" data-required="true" ${readonly}>                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_nomeDett") %></label>
						        <input type="text" id="nome" name="nome" class="form-control" value="${nome}" data-minlength="3" data-maxlength="25" data-required="true">                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_cognDett") %></label>
						        <input type="text" id="cogn" name="cogn" class="form-control" value="${cogn}" data-minlength="3" data-maxlength="25" data-required="true">                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_tipoDett") %></label>
						        <select id="tipo" name="tipo" class="form-control" data-required="true" onchange="setAut(this.value, 'S')">
						        	<option value="">-</option>
						        	<c:forEach items="${rol}" var="rol" varStatus="status">
						        	<option value="${rol.id}">${rol.id} - ${rol.name}</option>
						        	</c:forEach>
						        </select>                      
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_mailDett") %></label>
						        <input type="text" id="mail" name="mail" class="form-control" value="${mail}" data-type="email" data-maxlength="50">                        
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label><%=propLang.get("usr_lingua") %></label>
						        <select id="language" name="language" class="form-control" data-required="true">
						        	<option value="">-</option>
						        	<c:forEach items="${lang}" var="lang" varStatus="status">
						        	<option value="${lang.xcdel}">${lang.xcdel} - ${lang.xdtab}</option>
						        	</c:forEach>
						        </select>                      
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group">
						        <label>Token</label>
						        <div class="input-group">
		                          <input id="token" name="token" type="text" value="${token}" class="form-control">
		                          <span class="input-group-btn">
		                            <button class="btn btn-default" onclick="generaToken()" type="button">Generate</button>
		                          </span>
		                        </div>                
						    </div>
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("usr_aut") %></span>
	                          </div>
		                    </div>
					        
							<c:forEach items="${app}" var="app" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="pull-left m-l-xs m-r-sm">
									
									<input type="hidden" name="nrRigaApp" value="${app.cmp2}" />
									<input type="hidden" name="cdfunApp" value="${app.cdfun}" />
									<div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" name="postApp${app.cmp2}" id="postApp${app.cmp2}" ${app.cmp1} onclick="checkApp('${app.cmp2}','${app.cdfun}')">
			                            <i></i>
			                          </label>
			                        </div>

		                        </div> 
		                        <div class="clear">
		                        	<div><a href="#">${app.cdfun} - ${app.tipo}</a></div> 
		                       		<small class="text-muted">${app.descr}</small> 
		                       		
			                       	<div class="pull-right text-success" style="margin-top:-10px">
			                            <a href="../popup/funzioni-app.jsp?cdapp=${app.cdfun}&idusr=${idusr}&tipoForm=${tipoForm}" 
			                            	class="getModal btn btn-default btn-xs" data-toggle="modal">APP functions Authorization</a>
			                        </div>
		                       	</div> 
		                       	
		                       	<input type="hidden" name="zutefunz_${app.cdfun}" id="zutefunz_${app.cdfun}"
						    	    value="${app.cmp4}"/>
			                  </li>
	                        </c:forEach>
								
					        
					        
					        <div id="aut">
						    
							    <div class="panel-footer bg-light">
			                      <div class="padder">
		                            <span class="h4 block"><%=propLang.get("usr_rel") %></span>
		                          </div>
			                    </div>
					        	<br/> 
					        	
					        	<div id="divcus">
								    <div class="form-group">
								        <label>Customer</label>
								        <a href="../popup/rel_user_customers.jsp" class="getModal btn btn-default btn-lg btn-block" data-toggle="modal">
								        	User / Customers relation</a>                    
							    	    
							    	    <input type="hidden" name="rel_user_customers" id="rel_user_customers"
							    	    	value="${rel_user_customers}"/>
							    	    
							    	    <input type="hidden" name="_chkwr" id="_chkwr"
							    	    	value="${_chkwr}"/>
							    	    <input type="hidden" name="_chkdr" id="_chkdr"
							    	    	value="${_chkdr}"/>
						    	    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
						    	</div>
						    	
						    	<div id="divcou">
								    <div class="form-group">
								        <label>Country</label>
								        
								        <div id="coulink">
								        <a href="../popup/rel_user_country.jsp" class="getModal btn btn-default btn-lg btn-block" data-toggle="modal">
								        	User / Country relation</a>                    
							    	    </div>
							    	    
							    	    <input type="hidden" name="rel_user_country" id="rel_user_country"
							    	    	value="${rel_user_country}"/>                    
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
						    	</div>
						    	
						    	<div id="divcou2">
								    <div class="form-group">
								        <label>Country</label>
								        
								        <div id="coulink">
								        <a href="../popup/rel_user_country2.jsp" class="getModal btn btn-default btn-lg btn-block" data-toggle="modal">
								        	User / Country relation</a>                    
							    	    </div>
							    	    
							    	    <input type="hidden" name="rel_user_country2" id="rel_user_country2"
							    	    	value="${rel_user_country2}"/>                    
								    </div>
								    
								    <div class="line line-dashed b-b line-lg pull-in"></div>
						    	</div>
						    	
						    	<div id="divsto">
								    <div class="form-group">
								        
								        <label><%=propLang.get("usr_relusrsto") %></label>
								        <a href="../popup/rel_user_stores.jsp" class="getModal btn btn-default btn-lg btn-block" data-toggle="modal">
								        	User / Stores relation</a>      
								        	
								        	
							    	    <input type="hidden" name="rel_user_stores" id="rel_user_stores"
							    	    	value="${rel_user_stores}"/>                         
								    </div>
								    
						    	</div>
						    	
						    
						    </div>
						    
						    
						    <div id="aut2">
						    
						    	<div class="panel-footer bg-light">
			                      <div class="padder">
		                            <span class="h4 block"><%=propLang.get("usr_tipoDett") %> HR</span>
		                          </div>
			                    </div>
					        	<br/> 
					        	
						    
						    	<div id="divhr">
						    		<div class="form-group">
						    			<label><%=propLang.get("usr_tipoDett") %> HR</label>
						    			<select id="tipo_hr" name="tipo_hr" class="form-control" data-required="true">
								        	<option value="">-</option>
								        	<option value="D">Dipendente</option>
								        	<option value="S">Store Manager</option>
								        	<option value="M">Area Manager</option>
								        	<option value="H">HRM Manager</option>
								        	<option value="A">Administrator</option>
								        </select>   
						    		</div>
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
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%=propLang.get("usr_fun") %></p>
                        </div>
                        <div id="aree">
                        <%
                        String dis = "";
                        if(session.getAttribute("tipo").toString().trim().equals("3")||session.getAttribute("tipo").toString().trim().equals("7")) {
                        	dis = "disabled";
                        }
                        %>
                        
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${fun}" var="fun" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="pull-left m-l-xs m-r-sm">
									
									<input type="hidden" name="nrRiga" value="${fun.cmp2}" <%=dis %>/>
									<input type="hidden" name="cdfun" value="${fun.cdfun}" <%=dis %>/>
									<div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" name="post${fun.cmp2}" id="post${fun.cmp2}" data-required="true" ${fun.cmp1} <%=dis %>>
			                            <i></i>
			                          </label>
			                        </div>

		                        </div> 
		                        <div class="clear">
		                        	<div><a href="#">${fun.cdfun}</a></div> 
		                       		<small class="text-muted">${fun.descr}</small> 
		                        </div> 
		                        
			                  </li>
	                        </c:forEach>
	                          
	                        </ul>
                        </div>
                    </section>
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
				        
                        <a href="users-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-users"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("usr_utenti") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="users-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("usr_cerca") %>">
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
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/bootstrap-modal.js"></script>
  
  <script src="../../js/md5.js"></script>
  
  
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
	
	var idrole="${tipo}";
	setAut(idrole,'');
	
	$('#aut2').hide();
	$('#tipo_hr').prop('disabled', true);
	
	<c:forEach items="${app}" var="app" varStatus="status">
	checkApp('${app.cmp2}','${app.cdfun}');
  	</c:forEach>
		
	
	function setAut(idrole,blank) {
		
		idrole = $.trim(idrole);
		
		var tipoForm = '${tipoForm}';
		
		if(idrole==""||idrole=="2"||idrole=="8") {
			$('#aut').hide();
		}
		else {
			$('#aut').show();
			if(idrole=="3") {
				
				$('#divcus').show();
				$('#divcou').show();
				$('#divsto').show();
				$('#divcou2').hide();
				
				
				if(blank=='S') {
					$('#rel_user_customers').val('');
					$('#rel_user_country').val('');
					$('#rel_user_country2').val('');
					$('#rel_user_stores').val('');
				}
				
				
			}
			else if(idrole=="4") {
				
				$('#divcus').show();
				$('#divcou').hide();
				$('#divsto').hide();
				$('#divcou2').hide();
				
				
				if(blank=='S') {
					$('#rel_user_customers').val('');
					$('#rel_user_country').val('');
					$('#rel_user_country2').val('');
					$('#rel_user_stores').val('');
				}
				
				
			}
			else if(idrole=="5"||idrole=="7") {
				
				$('#divcus').hide();
				$('#divcou').hide();
				$('#divsto').hide();
				$('#divcou2').show();
				
				if(blank=='S') {
					$('#rel_user_customers').val('');
					$('#rel_user_country').val('');
					$('#rel_user_country2').val('');
					$('#rel_user_stores').val('');
				}
				
			}
			else if(idrole=="6") {
				
				$('#divcus').show();
				$('#divcou').show();
				$('#divsto').hide();
				$('#divcou2').hide();
				
				if(blank=='S') {
					$('#rel_user_customers').val('');
					$('#rel_user_country').val('');
					$('#rel_user_country2').val('');
					$('#rel_user_stores').val('');
				}
				
			}
				
		}
	}
	
	
	//MODAL
	$('.getModal').click(function(e) {
		
		showLoader();
		
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		// Utenti / Clienti
		if(href=="../popup/rel_user_customers.jsp") {
			href+="?tipoForm="+document.getElementById("tipoForm").value+"&idusr="+document.getElementById("idusr").value;
		}
		
		// Utenti / Negozi
		if(href=="../popup/rel_user_stores.jsp") {
			href+="?tipoForm="+document.getElementById("tipoForm").value+"&idusr="+document.getElementById("idusr").value+"&customers="+document.getElementById("rel_user_customers").value;
		}
		
		// Utenti / Nazioni
		if(href=="../popup/rel_user_country.jsp") {
			href+="?tipoForm="+document.getElementById("tipoForm").value+"&idusr="+document.getElementById("idusr").value+"&customers="+document.getElementById("rel_user_customers").value;
		}
		
		if(href=="../popup/rel_user_country2.jsp") {
			href+="?tipoForm="+document.getElementById("tipoForm").value+"&idusr="+document.getElementById("idusr").value+"&customers="+document.getElementById("rel_user_customers").value;
		}
		
		/*
		// Utenti / Funzioni app
		if(href=="../popup/funzioni-app.jsp") {
			href+="?tipoForm="+document.getElementById("tipoForm").value+"&idusr="+document.getElementById("idusr").value;
		}
		*/
	
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
				hideLoader();
			});
		}
	});
	
  
  $('#tipo').val('${tipo}');
  $('#tipo_hr').val('${tipo_hr}');
  $('#language').val('${language}');
  
  
  function setRelUserCustomers() {
	  
	  //alert('a');
	  
	  var chk = "";
	  var chkwr = "";
	  var chkdr = "";
	  $('input[name="chkCustomer"]:checked').each(function() {
		  chk=chk+this.value+","; 
		  
		  if($('#_'+this.value+'wr').is(":checked")) {
			  chkwr=chkwr+"t,"; 
		  }
		  else {
			  chkwr=chkwr+"f,"; 
		  }
		  
		  if($('#_'+this.value+'dr').is(":checked")) {
			  chkdr=chkdr+"t,"; 
		  }
		  else {
			  chkdr=chkdr+"f,"; 
		  }
		  
	  });
	  
	  chk = chk.substring(0, chk.length - 1);
	  chkwr = chkwr.substring(0, chkwr.length - 1);
	  chkdr = chkdr.substring(0, chkdr.length - 1);
	  
	  
	  //alert(chk);
	  
	  $('#rel_user_customers').val(chk);
	  $('#_chkwr').val(chkwr);
	  $('#_chkdr').val(chkdr);
	  
	  
  }
  
  function setRelUserCountry() {
	  
	  var chk = "";
	  $('input[name="chkCountry"]:checked').each(function() {
		  chk=chk+this.value+","; 
	  });
	  
	  chk = chk.substring(0, chk.length - 1);
	  
	  $('#rel_user_country').val(chk);
	  
  }
  
  function setRelUserCountry2() {
	  
	  var chk = "";
	  $('input[name="chkCountry"]:checked').each(function() {
		  chk=chk+this.value+","; 
	  });
	  
	  chk = chk.substring(0, chk.length - 1);
	  
	  $('#rel_user_country2').val(chk);
	  
  }
  
	function setFunzioni(cdapp) {
	  
	  var chk = "";
	  $('input[name="chkFun"]:checked').each(function() {
		  chk=chk+this.value+","; 
	  });
	  
	  chk = chk.substring(0, chk.length - 1);
	  
	  $('#zutefunz_'+cdapp).val(chk);
	  
  }
  
  
  
	function setRelUserStores() {
	  
	  var chk = "";
	  $('input[name="chkStore"]:checked').each(function() {
		  chk=chk+this.value+","; 
	  });
	  
	  chk = chk.substring(0, chk.length - 1);
	  
	  $('#rel_user_stores').val(chk);
	  
  }
	
	function generaToken() {
		
		var token = $('#token').val();
		
		if(token.length==0) {
			
			token = Math.random().toString(36).replace(/[^a-z]+/g, '').substr(0, 5);
			
		}
		
		var md5 = CryptoJS.MD5(token);
		
		$('#token').val(md5);
	}
  
	function checkApp(row, app) {
		
		if(app=='SHR') {
		
			if($('#postApp'+row).is(':checked')) {
				
				$('#aut2').show();
				$('#tipo_hr').prop('disabled', false);
				$('#my-form').parsley().destroy();
				$('#my-form').parsley();
				
			}
			else {
				
				$('#aut2').hide();
				$('#tipo_hr').prop('disabled', true);
				$('#my-form').parsley().destroy();
		        $('#my-form').parsley();
				
			}
			
		}
		
	}
	
	function pwdRequired(required) {
		
		if(required) {
			
			//destroy parsley
			$('#my-form').parsley().destroy();
		    
			//set required attribute on input to false
			$('#pwd').attr('data-required', 'true');
			$('#pwd').prop('disabled', false);
			
			//reinitialize parsley
			$('#my-form').parsley();
		}
		else {
			
			//destroy parsley
			$('#my-form').parsley().destroy();

			//set required attribute on input to false
			$('#pwd').attr('data-required', 'false');
			$('#pwd').prop('disabled', true);
			

			//reinitialize parsley
			$('#my-form').parsley();
			
		}
		
	}
	
	if('${tipoForm}'=='M' || '${tipoForm}'=='E') {
		
		pwdRequired(false);
		
	}
	
	function _enable(idcus) {
		if($('#_'+idcus).is(":checked")) {
			
			
			$('#_'+idcus+'wr').prop("disabled", false);
			$('#_'+idcus+'dr').prop("disabled", false);
			
		}
		else {
			$('#_'+idcus+'wr').prop("checked", false);
			$('#_'+idcus+'dr').prop("checked", false);
			
			$('#_'+idcus+'wr').prop("disabled", true);
			$('#_'+idcus+'dr').prop("disabled", true);
		}
		
	}
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
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
  <title><%=propLang.get("anr-create_dsfun") %> | SmartAnalytics</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />    
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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("anr-create_dsfun") %></h2>
		                
		                <form action="report-create.htm" method="post" id="report-form">
		                <input type="hidden" name="azione" value="S"/>
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("anr-create_dsfun") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="report-list.htm"><%=propLang.get("anr_dsfun") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("anr_report_type") %></label>
						          <select id="report_type" name="report_type" data-required="true" 
						          	onchange="loadSubReport()" class="form-control parsley-validated chosen-select">
							          <option value="">-</option>
							          <c:forEach items="${rep_type}" var="rep_type" varStatus="status">
			                          <option value="${rep_type.xcdel}">${rep_type.xcdel} - ${rep_type.xdtab}</option>
			                          </c:forEach>
	                        	  </select>   
	                        	 </div>
						    </div>
						    
						    <div id="subreport"></div>
						    
						  	
						  	<div id="report-dati" style="display: none;">
						  	
								<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block "><%=propLang.get("anr_dati") %></span>
			                          </div>
				                </div>
						      	<br/> 
							    
							    <div class="form-group pull-in clearfix">
							        <div class="col-sm-4">
							          <label><%=propLang.get("anr_code") %></label>
							          <input type="text" id="code" name="code" class="form-control" value="${code}">   
							        </div>
							    </div>
							    
							    <div class="line line-dashed b-b line-lg pull-in"></div>
							    
							    <div class="form-group pull-in clearfix">
							        <div class="col-sm-6">
							          <label><%=propLang.get("anr_descrizione") %> [IT]</label>
							          <input type="text" id="description_it" name="description_it" class="form-control" value="${description}">   
							        </div>
							        
							        <div class="col-sm-6">
							          <label><%=propLang.get("anr_descrizione") %> [EN]</label>
							          <input type="text" id="description_en" name="description_en" class="form-control" value="${description}">   
							        </div>
							    </div>
							    
							    <div class="line line-dashed b-b line-lg pull-in"></div>
							    
							    <div class="form-group pull-in clearfix">
							        <div class="col-sm-10">
							          <label><%=propLang.get("anr_icon") %></label>
							          <input type="text" id="icon" name="icon" class="form-control" value="${icon}">   
							        </div>
							        <div class="col-sm-2">
								        <label><br/></label>
					                      	<a href="../../app/popup/icons.jsp" class="getModal btn btn-default btn-block" data-toggle="modal">
									      		<i class="fa fa-search"></i> <%=propLang.get("btnSearch") %></a>
					                </div>
							    </div>
							    
							    <div class="line line-dashed b-b line-lg pull-in"></div>
							    
							    <div class="form-group pull-in clearfix">
							        <div class="col-sm-12">
							          <label><%=propLang.get("anr_alarm1") %></label>
							          <select id="alarm1" name="alarm1" data-required="true" 
							          	class="form-control parsley-validated chosen-select2">
									    <option value="">-</option>
									    <c:forEach items="${ec}" var="ec" varStatus="status">
									    <option value="${ec.code}">${ec.code} - ${ec.description}</option>
									    </c:forEach>
							   		   </select>   
		                        	</div>
							    </div>
							    
							    <div id="div-alarm2" style="display: none;">
						  			
						  			<div class="line line-dashed b-b line-lg pull-in"></div>
						  			
						  			<div class="form-group pull-in clearfix">
								        <div class="col-sm-12">
								          <label><%=propLang.get("anr_alarm2") %></label>
								          <select id="alarm2" name="alarm2" data-required="true" 
								          	class="form-control parsley-validated chosen-select3">
										    <option value="">-</option>
										    <c:forEach items="${ec}" var="ec" varStatus="status">
										    <option value="${ec.code}">${ec.code} - ${ec.description}</option>
										    </c:forEach>
								   		   </select>   
			                        	</div>
								    </div>
						  		
						  		</div>
						  		
						  		<div id="div-tipo3" style="display: none;">
						  			
						  			<div class="line line-dashed b-b line-lg pull-in"></div>
						  			
						  			<div class="form-group pull-in clearfix">
								        <div class="col-sm-12">
								          <label><%=propLang.get("anr_confronto") %></label>
								  			<div class="radio i-checks">
					                          <label>
					                            <input type="radio" id="confronto_historic" name="confronto" value="H">
					                            <i></i>
					                            <%=propLang.get("anr_historic") %>
					                          </label>
					                        </div>
					                        
					                        <div class="radio i-checks">
					                          <label>
					                            <input type="radio" id="confronto_benchmark" name="confronto" value="B">
					                            <i></i>
					                            <%=propLang.get("anr_benchmark") %>
					                          </label>
					                        </div>
					                    </div>
					                </div>
						  		
						  		</div>
						  		
						  		
						    	
						  	</div>
						  	
						  	
						  	
						  </div>
						  
						  <div id="report-footer"></div>
						  
			              
			              
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
				        
                        <a href="report-create.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-edit"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("anr_anr") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="report-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("anr_cerca") %>">
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
  
  <script src="../../js/bootstrap-modal.js"></script>
  
  
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
  
	//MODAL
	$('.getModal').click(function(e) {
	
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
 	
  	function loadSubReport() {
		
  		$('#report-footer').html("");
  		$('#report-dati').hide();
  		$('#div-alarm2').hide();
  		$('#div-tipo3').hide();
  		
  		var report_type = $('#report_type').val();
  			
  		$.ajax({
	      url: '../ajax/loadSubReport.jsp',
	      type: 'POST',
	      dataType: 'html',
	      async: false,
	      data: 'report_type='+report_type,
	      success: function (data) {

	    	  $('#subreport').html(data);
	    	  
	    	  $(".chosen-select").chosen();
	    	  
	      },
	      error: function () {
	      	alert('Errore! si e\' verificato un errore.');
	      	
	   		  // Mostra div
	    	  $('#report-dati').hide();
	    	  
	      }
	    });
 		
 		
  	}
  	
  	function showDatiReport() {
  		
  		var report_type = $('#report_type').val();
  		var subreport_type = $('#subreport_type').val(); 
  		
  		if(subreport_type=='') {
  			$('#report-dati').hide();
  		}
  		else {
  			$('#report-dati').show();
  			
  			$(".chosen-select2").chosen();
  			
  			// Casi:
  			//alert(report_type+" "+subreport_type);
  			if(report_type=='1'&&subreport_type=='1') {

  				$('#div-alarm2').hide();
  				$('#div-tipo3').hide();
  				
  				$('#report-footer').html('<footer class="panel-footer text-right bg-light lter">'+
  					'<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("anr_salva") %>" onclick="return controlForm()"></footer>');
  			
  			}
  			
  			if(report_type=='1'&&subreport_type=='3') {
  	  			
  				
  				$('#div-alarm2').show();
  				$('#div-tipo3').hide();
  				
  				$(".chosen-select3").chosen();
  				
  				$('#report-footer').html('<footer class="panel-footer text-right bg-light lter">'+
					'<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("anr_salva") %>" onclick="return controlForm()"></footer>');
  				
  			}
  			
			if(report_type=='2'&&subreport_type=='2') {
  	  			
  				
  				$('#div-alarm2').hide();
  				$('#div-tipo3').show();
  				
  				$('#report-footer').html('<footer class="panel-footer text-right bg-light lter">'+
					'<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("anr_salva") %>" onclick="return controlForm()"></footer>');
  				
  			}
			
			if(report_type=='2'&&subreport_type=='4') {
  	  			
  				
				$('#div-alarm2').show();
  				$('#div-tipo3').show();
  				
  				$(".chosen-select3").chosen();
  				
  				$('#report-footer').html('<footer class="panel-footer text-right bg-light lter">'+
					'<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("anr_salva") %>" onclick="return controlForm()"></footer>');
  				
  			}
  		}
  		
  	}
  	
  	
  	
  	function controlForm() {
  		
  		var report_type = $('#report_type').val();
  		var subreport_type = $('#subreport_type').val();
  		var code = $('#code').val();
  		var description_it = $('#description_it').val();
  		var description_en = $('#description_en').val();
  		var alarm1 = $('#alarm1').val();
  		var alarm2 = $('#alarm2').val();
  		var confronto = $("input[name='confronto']:checked").val();
  		confronto = ""+confronto;
  		confronto = confronto.replace("undefined", "");
  		
  		var icon = $('#icon').val(); 
  		
  		// Gestione Errori
  		var msgErr = "";
  		if(code=='') {
  			msgErr+=" - <%=propLang.get("anr_chkcode") %><br/>";
  		}
  		if(description_it==''&&description_en=='') {
  			msgErr+=" - <%=propLang.get("anr_chkdescr") %><br/>";
  		}
  		if(alarm1=='') {
  			msgErr+=" - <%=propLang.get("anr_chkalarm1") %><br/>";
  		}
  		if(report_type=='') {
  			msgErr+=" - <%=propLang.get("anr_chktipoRep") %><br/>";
  		}
  		if(subreport_type=='') {
  			msgErr+=" - <%=propLang.get("anr_chktiposubrep") %><br/>";
  		}
  		if((report_type=='1'&&subreport_type=='3')||(report_type=='2'&&subreport_type=='4')) {
  			if(alarm2=='') {
  				msgErr+=" - <%=propLang.get("anr_chkalarm2") %><br/>";
  	  		}
  		}
  		if(report_type=='2') {
	  		if(confronto=='') {
	  			msgErr+=" - <%=propLang.get("anr_chkconfronto") %><br/>";
	  		}
	  		
  		}
  		if(msgErr!='') {
  			
  			$('#msg').html("<div class=\"alert alert-danger\"><strong>Si &egrave; verificato un errore:</strong><br/>"+msgErr+"</div>"+
  					"<div class=\"line line-dashed b-b line-lg pull-in\"></div>");
  			  
  			document.getElementById('msg').scrollIntoView();
  			
  			return false;
  		}
  		else {
  			
  			// Formatto i campi...
  			if((report_type=='1'&&subreport_type=='1')||(report_type=='2'&&subreport_type=='2')) {
  				alerm2 = "";
  			}
  			
  			/*
  			javascript:window.location='report-create.htm?azione=S&report_type='+report_type+
  					'&subreport_type='+subreport_type+'&code='+code+'&description_it='+description_it+
  					'&description_en='+description_en+'&alarm1='+alarm1+'&alarm2='+alarm2+'&confronto='+confronto+
  					'&icon='+icon;
  			/*
  		  	/*
  			window.location.href='report-create.htm?azione=S&report_type='+report_type+
				'&subreport_type='+subreport_type+'&code='+code+'&description_it='+description_it+
					'&description_en='+description_en+'&alarm1='+alarm1+'&alarm2='+alarm2+'&confronto='+confronto+
					'&icon='+icon;
  		  	*/
  		  location.href='report-create.htm?azione=S&report_type='+report_type+
			'&subreport_type='+subreport_type+'&code='+code+'&description_it='+description_it+
			'&description_en='+description_en+'&alarm1='+alarm1+'&alarm2='+alarm2+'&confronto='+confronto+
			'&icon='+icon;
  		  //alert("1:"+report_type+"\n2:"+subreport_type+"\n3:"+code+"\n4:"+description_it+"\n5:"+description_en+"\n6:"+alarm1+"\n7:"+alarm2+"\n8:"+confronto+"\n9:"+stores);
  	  		
  		}
  		
  		
  	}
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
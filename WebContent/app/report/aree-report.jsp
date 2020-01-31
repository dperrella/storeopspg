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
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              	
              	<!-- menu app -->
            	<%@ include file="../../inc2/menuApp.jsp" %>
            
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      <h2 class="font-thin m-b"><%=propLang.get("ara_dsfun") %></h2>
                      
                      <div class="row row-sm">
	              	
		              	<div class="col-xs-12">
	
							
			                  
			                  
			                  <form action="../report/aree-report-esegui.htm" 
			                  	method="post" onsubmit="return control()" id="formPage">
			                  <input type="hidden" name="id_store" value="${id_store}"/>
			                  
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <ul class="nav nav-tabs pull-right">
			                        <li class="active"><a href="#dayTab" data-toggle="tab" onclick="$('#tipo').val('D')"><%=propLang.get("tipo_day") %></a></li>
			                        <li><a href="#weekTab" data-toggle="tab" onclick="$('#tipo').val('W')"><%=propLang.get("tipo_week") %></a></li>
			                        <li><a href="#monthTab" data-toggle="tab" onclick="$('#tipo').val('M')"><%=propLang.get("tipo_month") %></a></li>
			                        <li><a href="#periodTab" data-toggle="tab" onclick="$('#tipo').val('P')"><%=propLang.get("tipo_period") %></a></li>
			                      </ul>
			                      
			                      <span class="text-muted m-r-sm"><i class="icon-size-fullscreen"></i></span>
			                      <span class="hidden-sm"><b>Aree Report</b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                    			            
			                      <div class="row m-b-lg">
				                    <div class="col-sm-12">
				                      <div class="bg-danger lter wrapper-md r">
				                        <a href="#">
				                          <span class="h4 m-b-xs block"><i class="fa fa-info-circle i-lg"></i> <%=propLang.get("ara_dsfun") %></span>
				                        </a>
				                      </div>
				                    </div>
				                  </div> 			         
				                  
				                  <div id="msg">${msg}</div>
			                      
			                      <label><%=propLang.get("sel_stores") %></label>
					              <select id="id_store" name="id_store" data-required="true" 
						          	class="form-control parsley-validated chosen-select" onchange="reloadForm(this.value)">
					                <c:forEach items="${sto}" var="sto" varStatus="status">
					                <option value="${sto.id}">${sto.id} - ${sto.description}</option>
						            </c:forEach>
						          </select>
			                      
			                      <c:if test="${rep < 3}">
			                      <div class="line line-dashed b-b line-lg pull-in"></div>
			                      
			                      <div class="form-group pull-in clearfix">
							        <div class="col-sm-11">
				                      <label><%=propLang.get("sel_aree") %></label>
							          <select multiple id="id_area" name="id_area" data-required="true" 
							          	class="form-control parsley-validated chosen-select">
								          <c:forEach items="${aree}" var="aree" varStatus="status">
				                          <option value="${aree.id_area}">${aree.id_area} - ${aree.description}</option>
				                          </c:forEach>
		                        	  </select>
		                        	</div>
		                        	<div class="col-sm-1">
		                        	<label>&nbsp;</label>
		                        	<a href="#" onclick="selAll()"
		                        		class="btn btn-default btn-block btn-sm"><%=propLang.get("sel_all") %></a></div>
		                       	  </div>   
		                       	  </c:if>
	                        	  
			                      <div class="line line-dashed b-b line-lg pull-in"></div>
			                      
			                      <div class="tab-content">              
			                        <div class="tab-pane active" id="dayTab">
			                        	<div class="form-group pull-in clearfix">
			                        		<div class="col-sm-12">
									          <label><%=propLang.get("sel_day") %></label>
									          <input class="datepicker-input form-control" name="day" id="day" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									    </div>
									</div>
			                        
			                        <div class="tab-pane" id="weekTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearWeek" name="yearWeek" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana()">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-10">
									          <label><%=propLang.get("sel_week") %></label>
									          <select id="week" name="week" data-required="true" class="form-control parsley-validated">
										          <option value="">-</option>
										          <c:forEach items="${weeks}" var="weeks" varStatus="status">
						                          <option value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
						                          </c:forEach>
				                        	  </select>   
									        </div>
								    	</div>
								    </div>
			                        <div class="tab-pane" id="monthTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearMonth" name="yearMonth" data-required="true" class="form-control parsley-validated">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-10">
									          <label><%=propLang.get("sel_month") %></label>
									          <select id="month" name="month" data-required="true" class="form-control parsley-validated">
										          <option value="">-</option>
										          <c:forEach items="${mn}" var="mn" varStatus="status">
						                          <option value="${mn.xcdel}" ${mn.cmp1}>${mn.xcdel}: ${mn.xdtab}</option>
						                          </c:forEach>
				                        	  </select>   
									        </div>
								    	</div>
								    	
								    	
			                        	
			                        </div>
			                        <div class="tab-pane" id="periodTab">
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-6">
									          <label><%=propLang.get("sel_dtda") %></label>
									          <input class="datepicker-input form-control" name="dtda" id="dtda" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									        
									        <div class="col-sm-6">
									          <label><%=propLang.get("sel_dtal") %></label>
									          <input class="datepicker-input form-control" name="dtal" id="dtal" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
									        </div>
								    	</div>
								    	
			                        </div>
			                        
			                      </div>
			                      
			                      <div class="line line-dashed b-b line-lg pull-in"></div>
			                        <div class="form-group pull-in clearfix">
							        	<div class="col-sm-6">
				                        	<label>Report</label>
					                        <div class="radio i-checks">
						                      <label class="i-checks">
						                        <input type="radio" 
						                        	id="rep1" name="rep" value="1" onclick="reloadForm2(this.value)"><i></i> <%=propLang.get("ara_count") %>
						                      </label>
						                    </div>
						                    
						                    <div class="radio i-checks">
						                      <label class="i-checks">
						                        <input type="radio" 
						                        	id="rep2" name="rep" value="2" onclick="reloadForm2(this.value)"><i></i> <%=propLang.get("ara_stream") %>
						                      </label>
						                    </div>
						                    <div class="radio i-checks">
						                      <label class="i-checks">
						                        <input type="radio" 
						                        	id="rep3" name="rep" value="3" onclick="reloadForm2(this.value)"><i></i> <%=propLang.get("ara_static") %>
						                      </label>
						                    </div>
						                </div>
						                <div class="col-sm-6">
						                	
						                	<label><%=propLang.get("ara_orario") %></label>
					                        <div class="radio i-checks">
						                      <label class="i-checks">
						                        <input type="radio" 
						                        	id="orario1" name="orario" value="S"><i></i> <%=propLang.get("aar_S") %>
						                      </label>
						                    </div>
						                    
						                    <div class="radio i-checks">
						                      <label class="i-checks">
						                        <input type="radio" 
						                        	id="orario2" name="orario" value="N"><i></i> <%=propLang.get("aar_N") %>
						                      </label>
						                    </div>
						                    
						                </div>
						            </div>
			                    </div>
			                    <footer class="panel-footer text-right bg-light lter">
			                    	<input type="hidden" name="tipo" id="tipo" value="D">
			                    	
							      	<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("btn_esegui_report") %>"/>
							    </footer>
			                  </section>
			                  
			                  </form>
			                  
							</div>
						
					  	</div>
					  	
                    </section>
                  </section>
                </section>
              
              
              
              
              </section>
            </section>
          </section>
        </section>
        
        
      </section>
    </section>    
  </section>
  <script src="../../js/jquery.min.js"></script>
  <!-- Filter list -->
  <script src="../../js/jquery.fastLiveFilter.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
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
	
	$('#yearWeek').val('${currentYear}');
  	$('#yearMonth').val('${currentYear}');
  	$('#id_store').val('${id_store}');
  	
  	var $radios = $('input:radio[name=rep]');
    if($radios.is(':checked') === false) {
        $radios.filter('[value=${rep}]').prop('checked', true);
    }
    
    var $radios2 = $('input:radio[name=orario]');
    if($radios2.is(':checked') === false) {
        $radios2.filter('[value=${orario}]').prop('checked', true);
    }
  	
  	function reloadForm(id_store) {
  		
  		showLoader();
  		
  		var rep = $("#formPage input[type='radio']:checked").val();
  		location.href='aree-report.htm?id_store='+id_store+'&rep='+rep;
  		
  	}
  	
	function reloadForm2(rep) {
  		
  		showLoader();
  		
  		var id_store = $('#id_store').val();
  		var orario = $("#formPage input[name='orario']:checked").val();
  		
  		location.href='aree-report.htm?id_store='+id_store+'&rep='+rep+'&orario='+orario;
  	}
  	
	function reloadForm3(orario) {
  		
  		showLoader();
  		
  		var id_store = $('#id_store').val();
  		var rep = $("#formPage input[name='rep']:checked").val();
  		
  		location.href='aree-report.htm?id_store='+id_store+'&rep='+rep+'&orario='+orario;
  	}
  	
  	function control() {
  		
  		var rep = $("#formPage input[name='rep']:checked").val();
  		if(rep=='1') {
  			$('#formPage').attr('action', 'aree-report-esegui.htm');
  		}
  		if(rep=='2') {
  			$('#formPage').attr('action', 'aree-report-esegui2.htm');
  		}
  		if(rep=='3') {
  			$('#formPage').attr('action', 'aree-report-esegui3.htm');
  		}
  		
  		<c:choose>
	  	    <c:when test="${rep < 3}">
		  		// Controlla che almeno un area sia stato selezionato
		  		var aree = "";
		  		$('#id_area :selected').each(function(i, sel){ 
				    aree=aree+$(sel).val(); 
				});
		  		
		  		if(aree=='') {
		  			$('#msg').html("<div class=\"alert alert-warning\"><strong><%=propLang.get("sel_aree")%></strong></div>");
		  			
		  			return false;
		  		}
		  		else {
		  			showLoader();	
		  		}
	  	    </c:when>    
	  	    <c:otherwise>
	  	    	var id_store = $('#id_store').val();
	  	  		if('${id_store}'=='') {
		  			$('#msg').html("<div class=\"alert alert-warning\"><strong><%=propLang.get("sel_stores")%></strong></div>");
		  			
		  			return false;
		  		}
		  		else {
		  			showLoader();	
		  		}
	  	    </c:otherwise>
	  	</c:choose>
  	
  		
  		
  	}
  	
  	function selAll() {
  		$('#id_area option').prop('selected', true);
  		$('#id_area').trigger("chosen:updated");
  	}
  	
  </script> 
  
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
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
  <title><%=propLang.get("clo_dsfun") %> | SmartHR</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
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
		z-index: 10000;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
	#ajaxModal {
		z-index: 10000;
	}
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
  </style>
</head>
<div id="loading-image" style="display:none">
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
				   margin-left: -50px;" />
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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("clo_dsfun") %></h2>
		                
		                <form data-validate="parsley" action="clocking-list.htm" method="post">
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("clo_dsfun") %>
			              </header>
			              
			               <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a style="cursor: pointer;" data-toggle="modal" data-target="#myModal" onclick="copys()"><%=propLang.get("clo_new") %></a></li>
									<li><a style="cursor: pointer;" href="clocking-daapp-ric.htm"><%=propLang.get("clo_dsfun") %> Da approvare</a></li>
								</ul>
							</div>
						  </section>
			              
			              
			              <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("clo_seldep") %></label>
						          <select id="id_departments" name="id_departments"
						          	 class="form-control m-t parsley-validated chosen-select" onchange="cambiaSettimana()">
							          <option value="">-</option>
							          <c:forEach items="${dep}" var="dep" varStatus="status">
							          <option value="${dep.id}">${dep.description}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						    </div>
						        
					        <div class="line line-dashed b-b line-lg pull-in"></div>
					        
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("clo_seldip") %></label>
						          <select id="id_employee" name="id_employee" data-required="true" class="form-control parsley-validated chosen-select">
							          <option value="">-</option>
							          <c:forEach items="${emp}" var="emp" varStatus="status">
			                          <option value="${emp.id}">${emp.name} ${emp.surname}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
					        </div>
					        
					        <div class="line line-dashed b-b line-lg pull-in"></div>
					        
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-2">
						          <label><%=propLang.get("pla_year") %></label>
						          <select id="year" name="year" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana()">
							          <option value="2015">2015</option>
							          <option value="2016">2016</option>
							          <option value="2017">2017</option>
							          <option value="2018">2018</option>
							          <option value="2019">2019</option>
							          <option value="2020">2020</option>
							          <option value="2021">2021</option>
			                      </select>   
						        </div>
						        
						        <div class="col-sm-10">
						          <label><%=propLang.get("pla_week") %></label>
						          <select id="week" name="week" data-required="true" class="form-control parsley-validated">
							          <option value="">-</option>
							          <c:forEach items="${weeks}" var="weeks" varStatus="status">
			                          <option value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						    </div>
						    
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" style="width:200px" class="btn btn-success btn-lg"><%=propLang.get("btnSearch") %></button>
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
				        
                        <a href="clocking-ric.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-clock icon"></i> 
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
  
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <!-- Modal -->
 <div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title"><%=propLang.get("clo_new") %></h4>
      </div>
      <div class="modal-body">
		  	<div class="form-group pull-in clearfix">
		        <div class="col-sm-12">
		          <label><%=propLang.get("clo_seldep") %></label>
		          <select id="id_departments_ins" name="id_departments_ins" class="form-control" onchange="cambiaDep()">

                  </select>   
		        </div>
		    </div>
		        
	        <div class="line line-dashed b-b line-lg pull-in"></div>
	        
	       <div class="form-group pull-in clearfix">
		        <div class="col-sm-12">
		          <label><%=propLang.get("clo_seldip") %></label>
		          <select id="id_employee_ins" name="id_employee_ins" class="form-control">

                  </select>   
		        </div>
		    </div>
		        
	        <div class="line line-dashed b-b line-lg pull-in"></div>
	        
	       <div class="form-group pull-in clearfix">
		        <div class="col-sm-4">
		          <label><%=propLang.get("clo_data") %></label>
		          <input type="text" id="date_ins" name="date_ins" class="form-control" readonly>
		        </div>
		        <div class="col-sm-4">
		          <label><%=propLang.get("clo_ora") %></label>
		          <input type="time" id="hour_ins" name="hour_ins" class="form-control" />
		        </div>
		        <div class="col-sm-4">
		          <label><%=propLang.get("clo_ragione") %></label>
		          <select id="ragione_ins" name="ragione_ins" class="form-control">
		          	<option value="ENTRATA">ENTRATA</option>
		          	<option value="USCITA">USCITA</option>
		          </select>
		        </div>		        
		    </div>
		    
		    <div class="line line-dashed b-b line-lg pull-in"></div>	
		    
	       <div class="form-group pull-in clearfix">
		        <div class="col-sm-9">
		          <label><%=propLang.get("clo_noj") %></label>
		          <input type="text" id="note_justificative_ins" name="note_justificative_ins" class="form-control">
		        </div>
		        <div class="col-sm-3">
		          <label><%=propLang.get("clo_app") %></label><br>
		          <input type="checkbox" id="approvated_ins" name="approvated_ins" />
		        </div>		        
		    </div>		     
 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-success btn-lg" data-dismiss="modal" onclick="SaveIns()">Save</button>
      </div>
    </div>

  </div>
</div>
 <!-- Fine Modal -->
  <script>
  options = {
			changeMonth: true,
			changeYear: true,
			yearRange: '1950:2100',
			closeText: 'Chiudi',
			prevText: 'Prec',
			nextText: 'Succ',
			currentText: 'Oggi',
			monthNames: ['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
			monthNamesShort: ['Gen','Feb','Mar','Apr','Mag','Giu', 'Lug','Ago','Set','Ott','Nov','Dic'],
			dayNames: ['Domenica','Lunedì','Martedì','Mercoledì','Giovedì','Venerdì','Sabato'],
			dayNamesShort: ['Dom','Lun','Mar','Mer','Gio','Ven','Sab'],
			dayNamesMin: ['Do','Lu','Ma','Me','Gio','Ve','Sa'],
			dateFormat: 'yy-mm-dd', 
			firstDay: 1,
			isRTL: false	
	}
		
	$( function() {
	    $("#date_ins").datepicker(options);
	} ); 
  
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
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
	
  	$('#year').val('${year}');
  	$('#week').val('${currentWeek}');
  	$('#id_departments').val('${id_departments}');
  	
  	function cambiaSettimana() {
  		
  		showLoader2();
  		
  		var year = $('#year').val();
  		var id_departments = $('#id_departments').val();
  		
		javascript:window.location='clocking-ric.htm?year='+year+'&id_departments='+id_departments;
	  	window.location.href='clocking-ric.htm?year='+year+'&id_departments='+id_departments;
	}
  	
  	function copys(){
  		$.ajax({
  			url: '../../GetHRDepartment',
  			async: false,
  			data : {},
  			success : function(responseText) {
  				$('#id_departments_ins').html(responseText);
  			}
  		
  		});
  	}
  	
  	function cambiaDep(){
  		$.ajax({
  			url: '../../GetHREmployeeInDepartment',
  			async: false,
  			data : {
  				dep: $('#id_departments_ins').val()
  			},
  			success : function(responseText) {
  				$('#id_employee_ins').html(responseText);
  			}
  		
  		});
  	}
  	
  	function SaveIns(){
  		
  		if ($('#id_departments_ins').val() == ""){
  			$('#msg').html('<div class="alert alert-danger"><strong>Campo Dipartimento Obbligatorio.</div>');
  			$('#id_departments_ins').focus();
  			return;
  		}

  		if ($('#id_employee_ins').val() == ""){
  			$('#msg').html('<div class="alert alert-danger"><strong>Campo Dipendente Obbligatorio.</div>');
  			$('#id_employee_ins').focus();
  			return;
  		}
  		
  		if ($('#date_ins').val() == ""){
  			$('#msg').html('<div class="alert alert-danger"><strong>Campo Data Obbligatorio.</div>');
  			$('#date_ins').focus();
  			return;
  		}
  		
  		if ($('#hour_ins').val() == ""){
  			$('#msg').html('<div class="alert alert-danger"><strong>Campo Ora Obbligatorio.</div>');
  			$('#hour_ins').focus();
  			return;
  		}  		

  		var approved = 0;
  		
  		if ($('#approvated_ins').is(':checked')){
  			approved = 1;
  		}else{
  			approved = 0;
  		}
  		
  		$.ajax({
  			url: '../../SetInsertClockings',
  			async: false,
  			data : {
  				dep: $('#id_departments_ins').val(),
  				dip: $('#id_employee_ins').val(),
  				dat: $('#date_ins').val(),
  				hou: $('#hour_ins').val(),
  				rag: $('#ragione_ins').val(),
  				not: $('#note_justificative_ins').val(),
  				app: approved
  			},
  			success : function(responseText) {
  				if (responseText == "OK"){
  					alertify.success("Operazione avvenuta con successo");
  				}else{
  					alertify.error("Errore");
  				}
  			}
  		
  		});  		
  	}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
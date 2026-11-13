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
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
          
          <%@ include file="../../inc2/menuApp7.jsp" %>    
          
          <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b><%=propLang.get("pop_us_sel") %> <%=propLang.get("sm_rol_4") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                	<li class="list-group-item active"><a href="#dayTab" data-toggle="tab" 
		                		onclick="$('#tp').val('D');$('#id_departments').prop('disabled', false).trigger('chosen:updated');">
		                		<i class="fa fa-sort-numeric-asc"></i> <%=propLang.get("tipo_day") %></a></li>
	                        <li class="list-group-item"><a href="#weekTab" data-toggle="tab" 
	                        	onclick="$('#tp').val('W');$('#id_departments').prop('disabled', false).trigger('chosen:updated');">
	                        	<i class="fa fa-list-ol"></i> <%=propLang.get("tipo_week") %></a></li>
	                        <li class="list-group-item"><a href="#monthTab" data-toggle="tab" 
	                        	onclick="$('#tp').val('M');$('#id_departments').prop('disabled', false).trigger('chosen:updated');">
	                        	<i class="icon-calendar"></i> <%=propLang.get("tipo_month") %></a></li>
	                        <!-- 
	                        <li class="list-group-item"><a href="#periodTab" data-toggle="tab" onclick="$('#tp').val('P')">
	                        	<i class="fa fa-sort-alpha-asc"></i> <%=propLang.get("tipo_period") %></a></li>
	                       	-->
	                    </ul>
                        
                      </div>
                    </section>
                  </section>
                </aside>
                <!-- / side content -->
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      <h2 class="font-thin m-b">${dsfun}</h2>
                      	
						  <div class="row row-sm">
							<div class="col-xs-12">
								
			                  <div id="msg">${msg}</div>
			                  
			                  
			                  <form name="myForm" id="myForm" action="#" 
			                  	method="post" onsubmit="return control()">
			                  <input type="hidden" name="tp" id="tp" value="D"/>
			                  <input type="hidden" name="year" id="year" value="${currentYear}"/>
		                      
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="icon-settings icon"></i></span>
			                      <span class="hidden-sm"><b>${dsfun}</b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                      
			                      <div class="form-group pull-in clearfix">
							        <div class="col-sm-12">
				                      <label><%=propLang.get("emp_nome") %> <%=propLang.get("emp_cognome") %></label>
							          <select id="id_employee" name="id_employee" 
							          	 class="form-control chosen-select">
								          <option value="">-</option>
								          <c:forEach items="${emp}" var="emp" varStatus="status">
				                          <option value="${emp.xcdel}">${emp.xdtab}</option>
				                          </c:forEach>
		                        	  </select> 
		                        	</div>
		                        	
		                          </div>   
	                        	  
	                        	  <div class="line line-dashed b-b line-lg pull-in"></div>
						        
						          <div class="tab-content">              
			                        <div class="tab-pane active" id="dayTab">
			                        	<div class="form-group pull-in clearfix">
			                        		<div class="col-sm-12">
									          <label><%=propLang.get("sel_day") %></label>
									          <input class="datepicker-input2 form-control" name="day" id="day" 
									          	autocomplete="off" type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									    </div>
									</div>
			                        <div class="tab-pane" id="weekTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearWeek" name="yearWeek" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana(this.value);$('#year').val(this.value);$('#yearMonth').val(this.value);">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
										          <option value="2019">2019</option>
										          <option value="2020">2020</option>
										          <option value="2021">2021</option> 
										          <option value="2022">2022</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-10">
									          <label><%=propLang.get("sel_week") %></label>
									          <select id="week" name="week" data-required="true" class="form-control parsley-validated">
										          <option value="">-</option>
										          <c:forEach items="${weeks}" var="weeks" varStatus="status">
						                          <option value="${weeks.xcdel}" value2="${weeks.xdtab}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
						                          </c:forEach>
				                        	  </select>   
									        </div>
								    	</div>
								    	
								    	
			                        </div>
			                        <div class="tab-pane" id="monthTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearMonth" name="yearMonth" data-required="true" onchange="$('#year').val(this.value);$('#yearWeek').val(this.value);cambiaSettimana(this.value);" class="form-control parsley-validated">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
										          <option value="2019">2019</option>
										          <option value="2020">2020</option>
										          <option value="2021">2021</option>
										          <option value="2022">2022</option>
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
			                        
			                        <!-- 
			                        <div class="tab-pane" id="periodTab">
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-6">
									          <label><%=propLang.get("sel_dtda") %></label>
									          <input class="datepicker-input2 form-control" name="dtda" id="dtda" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									        
									        <div class="col-sm-6">
									          <label><%=propLang.get("sel_dtal") %></label>
									          <input class="datepicker-input2 form-control" name="dtal" id="dtal" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
									        </div>
								    	</div>
								    </div>
								    -->
								    
			                      </div>
			                    </div>
			                    <footer class="panel-footer text-right bg-light lter">
			                    	<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("btn_esegui_report") %>"/>
							    </footer>
			                  </section>
			                  
			                  </form>
						
							</div>
						
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
          </section>
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
	function showLoader2() {
  	  jQuery('#loading-image2').show();
  	}
  	function hideLoader2() {
  	  jQuery('#loading-image2').hide();
  	}
	
	var tp = $('#tp').val();
	
	$('#yearWeek').val('${currentYear}');
  	$('#yearMonth').val('${currentYear}');
  	$('#week').val('${currentWeek}');
  	$('#id_departments').val('${id_departments}');
  	
  	var $radios = $('input:radio[name=det1]');
    if($radios.is(':checked') === false) {
        $radios.filter('[value=${dett}]').prop('checked', true);
    }
    
    
    function control() {
  		
    	var tp = $('#tp').val();
    	var dtda = "";
    	var dtal = "";
    	
    	//alert(tp);
    	
    	
    	
    	if(tp=='D') {
    		
    		var day = $('#day').val();
    		dtda = day;
    		dtal = day;
    		
    		
    	}
    	else if(tp=='W') {
    		
    		var dates = $("#week option:selected").attr("value2").split(" - ");
    		
    		//alert(dates);
    		dtda = dates[0];
    		dtal = dates[1];
    		
    	}
		else if(tp=='M') {
    		
			var month = $('#month').val();
			var year = $('#yearMonth').val();
			
			if(month=='01') {
				dtda = '01/01/'+year;
				dtal = '31/01/'+year;
			}
			else if(month=='02') {
				dtda = '01/02/'+year;
				dtal = '28/02/'+year;
			}
			else if(month=='03') {
				dtda = '01/03/'+year;
				dtal = '31/03/'+year;
			}
			else if(month=='04') {
				dtda = '01/04/'+year;
				dtal = '30/04/'+year;
			}
			else if(month=='05') {
				dtda = '01/05/'+year;
				dtal = '31/05/'+year;
			}
			else if(month=='06') {
				dtda = '01/06/'+year;
				dtal = '30/06/'+year;
			}
			else if(month=='07') {
				dtda = '01/07/'+year;
				dtal = '31/07/'+year;
			}
			else if(month=='08') {
				dtda = '01/08/'+year;
				dtal = '31/08/'+year;
			}
			else if(month=='09') {
				dtda = '01/09/'+year;
				dtal = '30/09/'+year;
			}
			else if(month=='10') {
				dtda = '01/10/'+year;
				dtal = '30/10/'+year;
			}
			else if(month=='11') {
				dtda = '01/11/'+year;
				dtal = '30/11/'+year;
			}
			else if(month=='12') {
				dtda = '01/12/'+year;
				dtal = '31/12/'+year;
			}
			
    		
    	}
    	
    	//alert(dtda+' '+dtal);
    	
    	
    	var idemployee = "";
    	$('#id_employee :selected').each(function(i, sel){ 
    		idemployee=idemployee+$(sel).val(); 
		});
    	
    	if(dtda==''&&dtal=='') {
			$('#msg').html("<div class=\"alert alert-warning\"><strong><%=propLang.get("sel_day")%></strong></div>");
  			return false;
		}
		else if(idemployee=='') {
			$('#msg').html("<div class=\"alert alert-warning\"><strong>Select Employee</div>");
			return false;
		}
		else {
			showLoader2();
			
			$.ajax({
			      url: '../ajax/callFunctionAntress.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: true,
			      data: 'idemployee='+idemployee+'&dtda='+dtda+'&dtal='+dtal,
			      success: function (data) {
			    	  
			    	  
			    	  
			    	  hideLoader2();
			    	  alert(data);
			    	  if($.trim(data)!='') {
			    	  	location.href="../../servlet/ws/DownloadAntress?fileName="+$.trim(data);
			    	  }
			    	  
			      },
			      error: function () {
			      	alert('Errore! si e\' verificato un errore.');
			      	hideLoader2();
			      }
			});
			
			
			return false;
		}
    	
		
  		
  	}
  	
  	$('.datepicker-input2').datepicker({
	    format: 'dd/mm/yyyy',
	    orientation: 'bottom',
	    autoclose: true
	});  
  
  	
	function cambiaSettimana(anno) {
		
		//alert(anno);
		
		$.ajax({
		      url: '../ajax/getWeeks.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+anno,
		      success: function (data) {
				//alert(data);
		    	  $('#week option:gt(0)').remove();
		    	  $('#week').append(data);
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
	
	}
	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
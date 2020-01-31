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
  <title><%=propLang.get("ara_count") %> | Smart Analytics</title>
  <meta name="description" content="Desk SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <!-- 
  <link rel="stylesheet" href="../../js/datepicker/datepicker.css" type="text/css" />
  -->
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  <link rel="styleshett" href="//cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css" type="text/css" />
  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
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
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
  </style>
</head>
<body class="">
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
                      <h2 class="font-thin m-b"><%=propLang.get("ara_count") %></h2>
                      
                      <div class="row row-sm">
	              	
		              	<div class="col-xs-12">
			                  
			                  <div id="msg">${msg}</div>
			                  
			                  <form action="../report/aree-report-esegui.htm" id="formPage"
			                  	method="post" onsubmit="return control()">
			                  <input type="hidden" name="id_store" value="${id_store}"/>
			                  <input type="hidden" name="tipo" id="tipo" value="${tipo}">
			                  
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <ul class="nav nav-tabs pull-right">
			                        <li id="T1"><a href="#dayTab" data-toggle="tab" onclick="$('#tipo').val('D')"><%=propLang.get("tipo_day") %></a></li>
			                        <li id="T2"><a href="#weekTab" data-toggle="tab" onclick="$('#tipo').val('W')"><%=propLang.get("tipo_week") %></a></li>
			                        <li id="T3"><a href="#monthTab" data-toggle="tab" onclick="$('#tipo').val('M')"><%=propLang.get("tipo_month") %></a></li>
			                        <li id="T4"><a href="#periodTab" data-toggle="tab" onclick="$('#tipo').val('P')"><%=propLang.get("tipo_period") %></a></li>
			                      </ul>
			                      
			                      <span class="hidden-sm"><b>${description_store}</b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                      
			                      <div class="form-group pull-in clearfix">
				                      		        
				                      <div class="col-sm-12">            
					                      <label><%=propLang.get("sel_aree") %></label>
								          <select multiple id="id_area" name="id_area" data-required="true" 
								          	class="form-control parsley-validated chosen-select" onchange="reloadForm()">
									          <option value="">-</option>
									          <c:forEach items="${aree}" var="aree" varStatus="status">
					                          <option value="${aree.id_area}" ${aree.cmp1}>${aree.id_area} - ${aree.description}</option>
					                          </c:forEach>
			                        	  </select>   
		                        	  </div>
	                        	  </div>
	                        	  
	                        	  <div id="msg">${msg}</div>
	                        	  
			                      <div class="line line-dashed b-b line-lg pull-in"></div>
			                      
			                      <div class="tab-content">              
			                        <div class="tab-pane" id="dayTab">
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
			                  
			                  <div class="row">
                        
				                <div class="col-md-6">    
				                
				                <section class="panel panel-default">
					                <header class="panel-heading font-bold">Chart</header>
					                <div class="panel-body">
					                	<div id="flot" style="height:300px"></div>
					                </div>
					                <footer class="panel-footer bg-white">
					                  <div class="row text-center no-gutter">
					                    <div class="col-xs-12">
					                      <p class="h3 font-bold m-t">${totIngressi}</p>
					                      <p class="text-muted"><%=propLang.get("somma_aree") %></p>                        
					                    </div>
					                  </div>
					                </footer>
					            </section>
					            
					            </div>
					            
					            <div class="col-md-6">
					            	
					            <section class="panel panel-default">
					                <header class="panel-heading font-bold">Bar Chart</header>
					                <div class="panel-body">
					                	<div id="bar" style="height:300px">
					                	
					                		
					                	</div>
					                </div>
					                
					                <footer class="panel-footer bg-white">
					                  <div class="row text-center no-gutter">
					                    
					                    <div class="col-xs-12">
					                      <p class="h3 font-bold m-t">${nraree}</p>
					                      <p class="text-muted"><%=propLang.get("tot_aree") %></p>                        
					                    </div>
					                  </div>
					                </footer>
					                
					            </section>	
					            
					            </div>
					            
					          </div>
			                  
			                  <div class="row">
			                    <div class="col-sm-12">
			                  	<section class="panel panel-default">
				            		<header class="panel-heading font-bold">Data Tables</header>
				            		<div class="table-responsive horizontal">
				               		<table class="table table-striped m-b-none example" data-ride="datatables">
				               		<thead>
				               		  ${th}
				               		</thead>
				               		<tbody>
				               		  ${table}
				               		</tbody>
				               		<tfoot>
				               		<tr>
				               		  <td colspan="2">Tot.</td>
				               		  <c:forEach items="${data}" var="data" varStatus="status">
									  <td style="text-align:right"><b>${data.cmp1}</b></td>
									  </c:forEach>
				               		</tr>
				               		</tfoot>
				               		</table>
				               		</div>
				               	</section>
				               	</div>
				              </div>
			                    
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
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  
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
  
  <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>
  
  <script>
  
  Morris.Bar({
	  element: 'bar',
	  data: [
		<c:forEach items="${data}" var="data" varStatus="status">
	    { y: '${data.cmp2}', a: ${data.cmp1} },
	    </c:forEach>
	  ],
	  xkey: 'y',
	  ykeys: ['a'],
	  labels: ['${description_store}'],
	  xLabelAngle: 60
	});
  
  </script>
  
  <script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
  <script src="https://cdn.datatables.net/buttons/1.2.4/js/dataTables.buttons.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
  <script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
  <script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
  <script src="https://cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js"></script>
  
  <script>
  	  /*
	  $('.example').dataTable( {
	      "language": {
	          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
	          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
	          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_",
	          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
	          "infoFiltered": "(Filtrati da _MAX_ records totali)",
	          "decimal": ",",
	          "thousands": ".",
	          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
	      },
	      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	      dom: 'Bfrtip',
	      buttons: [
                'copy', 'csv', 'excel', 'pdf'
          ],
	      "aaSorting": [],
	      "iDisplayLength": 10
	  } );
  	  */
  	
  	
      
    $('.example').DataTable( {
  		 dom: 'Bfrtip',
         buttons: [
             'excelHtml5',
             'csvHtml5',
             'pdfHtml5'
         ],
         "iDisplayLength": 25
    } );
	
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		
		$('.dt-buttons').addClass('wrapper');
		$('.buttons-html5').addClass('btn btn-default');
		$('.dt-buttons').append( "<div class=\"line line-dashed b-b line-lg pull-in\"></div>" );
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
  	
  	$('#day').val('${day}');
  	$('#yearWeek').val('${yearWeek}');
  	$('#week').val('${week}');
  	$('#yearMonth').val('${yearMonth}');
  	$('#month').val('${month}');
  	$('#dtda').val('${dtda}');
  	$('#dtal').val('${dtal}');
  	
  	var $radios2 = $('input:radio[name=orario]');
    if($radios2.is(':checked') === false) {
        $radios2.filter('[value=${orario}]').prop('checked', true);
    }
  	
  	var tipo = '${tipo}';
  	if(tipo=='D') {
  		$("#T1" ).addClass("active");	
  		$('#dayTab').addClass("active");
  	}
  	if(tipo=='W') {
  		$("#T2" ).addClass("active");	
  		$('#weekTab').addClass("active");
  	}
  	if(tipo=='M') {
  		$("#T3" ).addClass("active");	
  		$('#monthTab').addClass("active");
  	}
  	if(tipo=='P') {
  		$("#T4" ).addClass("active");	
  		$('#periodTab').addClass("active");
  	}
  	
  	
  	
  	
  	
  	function reloadForm() {
  		
  		showLoader();
  		$('#formPage').submit();
  		
  	}
  	
  	function control() {
  		
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
  		
  	}
  </script> 
  
  
  <!-- Flot -->
  <script src="js/excanvas.min.js"></script>
  <script src="js/jquery.flot.min.js"></script>
  
  <script src="../../js/charts/flot/jquery.flot.tooltip.min.js"></script>
  <script src="../../js/charts/flot/jquery.flot.resize.js"></script>
  
  <script>
  
  
  
  $(function(){
	  	
	  <c:forEach items="${data}" var="data" varStatus="status">
	  ${data.xdtab}
	  </c:forEach>
	  
	  if(tipo=='D') {
		  
		// Giorno
		  $("#flot").length && $.plot($("#flot"), 
			[
				<c:forEach items="${data}" var="data" varStatus="status">
				{
					data: a${data.xcdel},
					label: " ${data.cmp2}"
				},
				</c:forEach>
			], 
	      {
	        series: {
	            lines: {
	                show: true,
	                lineWidth: 1,
	                fill: true,
	                fillColor: {
	                    colors: [{
	                        opacity: 0.3
	                    }, {
	                        opacity: 0.3
	                    }]
	                }
	            },
	            points: {
	                radius: 3,
	                show: true
	            },
	            grow: {
	              active: true,
	              steps: 20,
	              growings:[
	                  {
	                      stepDirection: "down"
	                  }                    
	              ]
	            },
	            shadowSize: 2
	        },
	        grid: {
	            hoverable: true,
	            clickable: true,
	            tickColor: "#f0f0f0",
	            borderWidth: 1,
	            color: '#999'
	        },
	        xaxis:  {
	        	ticks: 12,
	            
	        },
	        yaxis: {
	            ticks: 10,
	            tickDecimals: 0,
	            tickFormatter: function(val, axis) { return val < axis.max ? val.toFixed(2) : "<%=propLang.get("mov_in") %>";}
	            
	        },
	        tooltip: true,
	        tooltipOpts: {
	          content: "Ore %x.1 : %y.4 ",
	          defaultTheme: false,
	          shifts: {
	            x: 0,
	            y: 20
	          }
	        }
	      }
	    );
		  
	  }
	  else {
		  
		// Settimana / mese / periodo
		$("#flot").length && $.plot($("#flot"), 
			[
				<c:forEach items="${data}" var="data" varStatus="status">
				{
					data: a${data.xcdel},
					label: " ${data.cmp2}"
				},
				</c:forEach>
			], 
	      {
	        series: {
	            lines: {
	                show: true,
	                lineWidth: 1,
	                fill: true,
	                fillColor: {
	                    colors: [{
	                        opacity: 0.3
	                    }, {
	                        opacity: 0.3
	                    }]
	                }
	            },
	            points: {
	                radius: 3,
	                show: true
	            },
	            grow: {
	              active: true,
	              steps: 20,
	              growings:[
	                  {
	                      stepDirection: "down"
	                  }                    
	              ]
	            },
	            shadowSize: 2
	        },
	        grid: {
	            hoverable: true,
	            clickable: true,
	            tickColor: "#f0f0f0",
	            borderWidth: 1,
	            color: '#999'
	        },
	        xaxis:  {
	            mode: "time", timezone: "America/Chicago", timeformat: "%d/%m/%y", minTickSize: [1, "day"]
	        },
	        yaxis: {
	            ticks: 10,
	            tickDecimals: 0,
	            tickFormatter: function(val, axis) { return val < axis.max ? val.toFixed(2) : "<%=propLang.get("mov_in") %>";}
	            
	        },
	        tooltip: true,
	        tooltipOpts: {
	          content: "Giorno %x.1 : %y.4 ",
	          defaultTheme: false,
	          shifts: {
	            x: 0,
	            y: 20
	          }
	        }
	      }
	    );  
		
	  }
			  
	});  
  
  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
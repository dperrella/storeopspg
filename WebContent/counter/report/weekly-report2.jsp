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
              
              <%@ include file="../../inc2/menuApp2.jsp" %>
              
              	<!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b><%=propLang.get("pop_us_sel") %> <%=propLang.get("sm_rol_4") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                <!-- 
		                	<li class="list-group-item active"><a href="#dayTab" data-toggle="tab" onclick="$('#tipo').val('D')">
		                		<i class="fa fa-sort-numeric-asc"></i> <%=propLang.get("tipo_day") %></a></li>
	                    -->    
	                        <li class="list-group-item active"><a href="#weekTab" data-toggle="tab" onclick="$('#tipo').val('W')">
	                        	<i class="fa fa-list-ol"></i> <%=propLang.get("tipo_week") %></a></li>
	                    <!--     
	                        <li class="list-group-item"><a href="#monthTab" data-toggle="tab" onclick="$('#tipo').val('M')">
	                        	<i class="icon-calendar"></i> <%=propLang.get("tipo_month") %></a></li>
	                        <li class="list-group-item"><a href="#periodTab" data-toggle="tab" onclick="$('#tipo').val('P')">
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
                      <h2 class="font-thin m-b">Weekly Report <%=propLang.get("versione") %> 2.0.0</h2>
                      	
                      	  <!-- 
	                      <div class="row row-sm">
		              		<div class="col-xs-12">
					  			<section class="panel panel-default">
						  		  <div class="panel-body text-center">
						  		  	<div id="bar-chart" style="height:300px"></div>
						  		  </div>
					  			</section>
					  		</div>
		              	  </div>
		              	  -->
	
						  <div class="row row-sm">
							<div class="col-xs-12">
								
			                  <div id="msg">${msg}</div>
			                  
			                  
			                  <form id="formPage" name="formPage" action="../../counter/report/weekly-report-counter2.htm" 
			                  	method="post" onsubmit="return control()">
			                  
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="icon-bar-chart icon"></i></span>
			                      <span class="hidden-sm"><b>Smart Counter Weekly Repart [${dtda} / ${dtal}]</b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                      
			                      <div class="form-group pull-in clearfix">
							        <div class="col-sm-11">
				                      <label><%=propLang.get("sel_stores") %></label>
							          <select multiple id="id_store" name="id_store" data-required="true" 
							          	class="form-control parsley-validated chosen-select">
								          <c:forEach items="${sto}" var="sto" varStatus="status">
				                          <option value="${sto.id}">${sto.cmp1} - ${sto.description}</option>
				                          </c:forEach>
		                        	  </select>
		                        	</div>
		                        	<div class="col-sm-1">
		                        	<label>&nbsp;</label>
		                        	<a href="#" onclick="selAll()"
		                        		class="btn btn-default btn-block btn-sm"><%=propLang.get("sel_all") %></a></div>
		                          </div>   
	                        	  
			                      <div class="tab-content">      
			                      
			                        <div class="tab-pane active" id="weekTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearWeek" name="yearWeek" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana(this.value)">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-2">
									          <label><%=propLang.get("week_start") %></label>
									          <select id="weekstart" name="weekstart" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana(this.value)">
										          <option value="M" selected><%=propLang.get("lun") %></option>
										          <option value="S"><%=propLang.get("dom") %></option>
										      </select>   
									        </div>
									        
									        <div class="col-sm-8">
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
			                        
								    <div id="bar-chart" style="height:350px"></div>
								    
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
					  	
					  	<div class="row row-sm">
					  		
					  		<c:forEach items="${tiles}" var="tiles" varStatus="status">
					  		<div class="col-xs-12 col-md-6 col-lg-3">
					  		  
					  		  <section class="panel panel-default">
					  		  <div class="panel-body text-center">
		                      	<h5 class="text-ellipsis">${tiles.xdtab}</h5>
			                    <div class="inline">
			                        <div class="easypiechart" data-percent="${tiles.cmp4}" data-bar-color="${tiles.cmp2}" data-line-width="16" data-loop="false" data-size="188">
			                          <div>
			                            <span class="h2 m-l-sm step"><a href="#" onclick="callReport('${tiles.xcdel}')">${tiles.cmp1}</a></span>
			                            
			                            <div class="text text-sm">
			                            <c:if test = "${tiles.xcdtb!=''}">
											${tiles.xcdtb}
										</c:if>
			                            Counts
			                            </div>
			                          </div>
			                        </div>
			                    </div>                      
			                  </div>
			                  </section>
					  		
					  		</div>
					  		</c:forEach>
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
  
  
  <!-- EasyPie -->
  <script src="../../js/charts/easypiechart/jquery.easy-pie-chart.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  
  <script>
  
  $('#id_customer').val('${id_customer}');
  
  
//based on prepared DOM, initialize echarts instance
  var myChart = echarts.init(document.getElementById('bar-chart'));
	
	// specify chart configuration item and data
  option = {
		color: ['#FF6859'],
		    
	    title : {
	        text: 'Today Store Count',
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            
	            type : 'shadow'      
	        }
	    },
	    legend: {
	        data:[${kpi_in}]
	    },
	    grid: {
	        left: '0%',
	        right: '4%',
	        bottom: '10%',
	        containLabel: true
	    },
	    dataZoom: [{
	    	bottom: 0,
	        type: 'slider',
	        start: 0,
	        end: 20	
	    }, {
	        type: 'inside',
	        start: 0,
	        end: 20
	    }],
	    toolbox: {
	        show : true,
	        showTitle: false,
	        feature : {
	        	
	        	//dataView : {show: true, readOnly: false, title: 'Data View'},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            //axisLabel: { rotate: 45, },
	            // Lista negozi
	            data : [${xAxis}]
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'Today',
	            type:'bar',
	            barWidth: '60%',
	            data:[${series}],
	            markLine : {
	                data : [
	                    {type : 'max', name: 'Max'}
	                ]
	            },
	            itemStyle: {
	                normal: {
	                    color: '${chart.cmp1}',
	                },
	                emphasis: {
	        	        barBorderWidth: 1,
	        	        shadowBlur: 10,
	        	        shadowOffsetX: 0,
	        	        shadowOffsetY: 0,
	        	        shadowColor: 'rgba(0,0,0,0.5)'
	        	    }
	            },
	        }
	    ]
	
	};

  // use configuration item and data specified to show chart
  myChart.setOption(option);
	
  
  $('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'top',
	    autoclose: true
	});  
  
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
  	
  	var $radios2 = $('input:radio[name=orario]');
    if($radios2.is(':checked') === false) {
        $radios2.filter('[value=${orario}]').prop('checked', true);
    }
  	
    function control() {
  		
  		// Controlla che almeno uno store sia stato selezionato
  		var stores = "";
  		$('#id_store :selected').each(function(i, sel){ 
		    stores=stores+$(sel).val(); 
		});
  		
  		var week = $('#week').val();
  		
  		var err = false;
  		
  		if(stores=='') {
  			err = true;
  			$('#msg').html("<div class=\"alert alert-warning\"><strong><%=propLang.get("sel_stores")%></strong></div>");
  			return false;
  		}
  		else if(week=='') {
  			err = true;
  			$('#msg').html("<div class=\"alert alert-warning\"><strong><%=propLang.get("sel_week")%></strong></div>");
  			return false;
  		}
  		
  		if(err==false) {
  			showLoader();	
  		}
 		
  		
  	}
  	
  	function selAll() {
  		$('#id_store option').prop('selected', true);
  		$('#id_store').trigger("chosen:updated");
  	}
  	
  	function reloadForm2() {
  		
  		$('#formPage').removeAttr('onsubmit')
  		
  		$('#formPage').attr('action', 'weekly-report2.htm');
  		
  		$('#formPage').submit();
  		
  	}
  	
  	function callReport(id_store) {
  		
		$('#id_store').val(id_store);
		$('#id_store').trigger("chosen:updated");
  		$('#formPage').submit();
  		
  	}
  	
	function cambiaSettimana(anno) {
		
		//alert(anno);
		var yearWeek = $('#yearWeek').val();
		var weekstart = $('#weekstart').val();
		
		$.ajax({
		      url: '../ajax/getWeeks_new.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'anno='+yearWeek+'&weekstart='+weekstart,
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
  	
	var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		myChart.resize();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
  </script> 
  
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
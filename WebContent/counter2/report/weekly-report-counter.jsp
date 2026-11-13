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
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
	
	
	.treegrid-indent {
	    width: 0px;
	    height: 16px;
	    display: inline-block;
	    position: relative;
	}
	.treegrid-expander {
	    width: 0px;
	    height: 16px;
	    display: inline-block;
	    position: relative;
	    left:-17px;
	    cursor: pointer;
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
              
            <%@ include file="../../inc2/menuApp9.jsp" %>
            
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
		                	<li class="list-group-item">
		                		<a href="report-counter-modal.jsp?idstores=${idstores}&tipo=D"
		                		class="getModal" data-toggle="modal">
		                		<i class="fa fa-sort-numeric-asc"></i> <%=propLang.get("tipo_day") %></a></li>
	                        -->
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal-new.jsp?weekstart=${weekstart}&idstores=${idstores}&tipo=W"
		                		class="getModal" data-toggle="modal">
	                        	<i class="fa fa-list-ol"></i> <%=propLang.get("tipo_week") %></a></li>
	                        <!-- 
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal.jsp?idstores=${idstores}&tipo=M"
		                		class="getModal" data-toggle="modal">
	                        	<i class="icon-calendar"></i> <%=propLang.get("tipo_month") %></a></li>
	                        <li class="list-group-item">
	                        	<a href="report-counter-modal.jsp?idstores=${idstores}&tipo=P"
		                		class="getModal" data-toggle="modal">
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
                      <h2 class="m-b-none font-thin">Weekly Report Smart Counter ${descrStore}</h2>
                      <small>Period: ${dtda} - ${dtal}</small>  
                        
                      <div class="row m-t row-sm">
							<div class="col-xs-12">
							
								<section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      <span class="text-muted m-r-sm"><i class="fa fa-bar-chart-o"></i></span>
				                      <span class="hidden-sm"><b>HeatMap</b></span>
				                    </header>
				                    <div class="panel-body">
				                    
				                    	<div id="main2" style="height:400px;"></div>
				                     
				                    </div>
				                    <footer class="panel-footer">
					                  <div class="row text-center no-gutter">
					                    <div class="col-xs-12">
					                    
					                    <div id="table_wrapper3" class="table-responsive horizontal">
					                    <table style="width:100%">
					                      <tr>
					                    	<c:forEach items="${sto}" var="sto" varStatus="status">
					                    	<td style="width:150px" class="wrapper-xs">
					                    		<p class="h3 font-bold m-t">${sto.cmp1}</p>
					                      		<p class="text-muted text-ellipsis">${sto.cmp2}</p>  
					                    	</td>
						           	    	</c:forEach>
						           	      </tr>
					                    </table>
					                    </div>
					                                 
					                    </div>
					                  </div>
					                </footer>
				    
				    		  </section>
						  	
						  	</div>
					  
					  
					  </div>
			          <div class="row row-sm">
			          
			          	<div class="col-md-12">
			          	
			            <section class="panel panel-default">
			            	<header class="panel-heading font-bold">Data</header>
			          
			               	<div class="panel-body">
			               		<button id="btnExport" class="btn btn-default"><%=propLang.get("xls_export") %></button>
			               		<button id="btnXXX" class="btn btn-default" onclick="showAll()">Show All</button>
			               		<button id="btnYYY" class="btn btn-default" onclick="hideAll()">Hide All</button>
			               	</div>
			               	
			               	<div id="table_wrapper" class="table-responsive horizontal">
			               		<table id="tree-table" class="table table-striped b-t b-light">
			               		<thead>
			               			<tr>
			               				<th>Date</th>
			              				<!-- <th>Time</th>-->
			              				
			               				${th}
			               			</tr>
			               		</thead>
			               		<tbody>
				               	<!--  Periodo -->  
								<c:forEach items="${period}" var="period" varStatus="status">
									
									${period.cmp5}
									<tr data-parent="${period.cmp7}" data-level="2">
										<td ${period.cmp2} data-column="name">${period.cmp3}</td>
										${period.cmp1}
									</tr>
								</c:forEach>
				           	    </tbody>
				           		<tfoot>
				           	    	<tr>
				           	    		<td></td>
					           	    	<!-- <td></td>-->
					           	    	<c:forEach items="${sto}" var="sto" varStatus="status">
					           	    	<td><b>${sto.cmp1}</b></td>
					           	    	</c:forEach>
				           	    	</tr>
				           	    </tfoot>
			           			</table>
			           		</div>
			           	</section>
			        
			            </div>
			            
		             </div>
		            
			    </section> 
	          </section>
	        </section>
                
	      </section>
            
        </section>
        <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
    <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  
  <script src="../../js/bootstrap-modal.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <script>
  	
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		//$('.legend').insertAfter('.newLegend');
		//$('.legend').remove();
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
  
	function getHeatMap(kpi, descr) {
		
		var idstores = '${idstores}';
		var dtda = '${dtda}';
		var dtal = '${dtal}';
		
		myChart3.dispose();
		$('#main2').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		$.ajax({
		      url: '../ajax/getHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}&weekstart=${weekstart}',
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  var res = data.split('##@@');
				  var max=res[0];
				  var heatmap=res[1];
				  var XA=res[2];
				  var YA=res[3];
				  
				  //alert(heatmap);
				  //alert(heatmap);
				  var array = JSON.parse(heatmap);
				  var xa2=XA.split(",");
				  var ya2=YA.split(",");
				  
				  option2 = {
				    		
				    title: {
			   			text: 'HeatMap ${dtda} - ${dtal}',
			   	        left: 'center',
			         	top: 'top'
			   	    },
				    tooltip: {
				        position: 'top'
				    },
				    animation: true,
				    grid: {
				        height: '80%',
				        y: '10%'
				    },
				    toolbox: {
				        feature: {
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        //axisLabel: { rotate: 60, },
				        axisLabel: { rotate: 90, },
				        data: xa2,
				        splitArea: {
				            show: true
				        }
				    },
				    yAxis: {
				        type: 'category',
				        //data: ['<%=propLang.get("sab")%>','<%=propLang.get("ven")%>','<%=propLang.get("gio")%>','<%=propLang.get("mer")%>','<%=propLang.get("mar")%>','<%=propLang.get("lun")%>','<%=propLang.get("dom")%>'],
				        data: ya2,
				        splitArea: {
				            show: true
				        }
				    },
				    visualMap: {
				        min: 0,
				        max: max,
				        calculable: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        inRange: {
				        	color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
				    	}
				    },
				    series: [{
				        name: descr,
				        type: 'heatmap',
				        data: array,
				        label: {
				            normal: {
				                show: true
				            }
				        },
				        itemStyle: {
				            emphasis: {
				                shadowBlur: 10,
				                shadowColor: 'rgba(0, 0, 0, 0.5)'
				                
				            },
				            normal: {
			                    color: '#EE5154'
			                }
				        }
				    }]
				
			 		
			 	}
				
				myChart3 = echarts.init(document.getElementById('main2'));
				myChart3.setOption(option2);
				
				//hideLoader2();
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	var myChart3 = echarts.init(document.getElementById('main2'));
	getHeatMap('${kpi}','VISITORS');
	
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
  	
  	$("#btnExport").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	    var table_div = document.getElementById('table_wrapper');
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	});
  	
  	function setCal() {
  		
  		//alert('aaa');	
  		
  		 $('.datepicker-input2').datepicker({
  		    format: 'dd-mm-yyyy',
  		    orientation: 'bottom',
  		    autoclose: true
  		});  
  		
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
  	
	
	
	
	
	
	
	
	
	$(function () {

	    var
	        $table = $('#tree-table'),
	        rows = $table.find('tr');

	    rows.each(function (index, row) {
	        var
	            $row = $(row),
	            level = $row.data('level'),
	            id = $row.data('id'),
	            $columnName = $row.find('td[data-column="name"]'),
	            children = $table.find('tr[data-parent="' + id + '"]');

	        if (children.length) {
	            var expander = $columnName.prepend('' +
	                '<span class="treegrid-expander fa fa-chevron-right"></span>' +
	                '');

	            children.hide();

	            expander.on('click', function (e) {
	            	
	            	var $target = $(e.target);
	                
	                if ($target.hasClass('fa fa-chevron-right')) {
	                    $target
	                        .removeClass('fa fa-chevron-right')
	                        .addClass('fa fa-chevron-down');

	                    children.show();
	                } else {
	                    $target
	                        .removeClass('fa fa-chevron-down')
	                        .addClass('fa fa-chevron-right');

	                    reverseHide($table, $row);
	                }
	            });
	        }

	        $columnName.prepend('' +
	            '<span class="treegrid-indent" style="width:' + 15 * level + 'px"></span>' +
	            '');
	    });

	    // Reverse hide all elements
	    reverseHide = function (table, element) {
	        var
	            $element = $(element),
	            id = $element.data('id'),
	            children = table.find('tr[data-parent="' + id + '"]');

	        if (children.length) {
	            children.each(function (i, e) {
	                reverseHide(table, e);
	            });

	            $element
	                .find('.fa fa-chevron-down')
	                .removeClass('fa fa-chevron-down')
	                .addClass('fa fa-chevron-right');

	            children.hide();
	        }
	    };
	
	});

	
	var dd = '${dd}';
	var str = '${str}';
	
	arrdd = dd.split(",");
	arrstr = str.split(",");
	
	for(d=0;d<arrdd.length;d++) {
		
		console.log(arrdd[d]);
		
		for(s=0;s<arrstr.length;s++) {
			
			console.log(' > '+arrdd[d]+'_'+arrstr[s]);
			var tot = 0
			$('input[name^="'+arrdd[d]+'_'+arrstr[s]+'"]').each(function() {
			    //alert($(this).val());
			    tot+=parseInt($(this).val());
			});
			console.log(' > TOT: '+tot);
			
			$('#'+arrdd[d]+'_'+arrstr[s]+'').html('<b>'+tot+'</b>');
			
		}
		
	}
	
	
	function hideAll() {
		
	    var
	        $table = $('#tree-table'),
	        rows = $table.find('tr');

	    rows.each(function (index, row) {
	        var
	            $row = $(row),
	            level = $row.data('level'),
	            id = $row.data('id'),
	            $columnName = $row.find('td[data-column="name"]'),
	            children = $table.find('tr[data-parent="' + id + '"]');
			
				
	        	reverseHide3($table, $row);
	        
	    });
	
	
	}
	
	function reverseHide3 (table, element) {
        var
            $element = $(element),
            id = $element.data('id'),
            children = table.find('tr[data-parent="' + id + '"]');
        
        if (children.length) {
            children.each(function (i, e) {
                reverseHide3(table, e);
            });

            $element
                .find('.fa fa-chevron-down')
                .removeClass('fa fa-chevron-down')
                .addClass('fa fa-chevron-right');
			
			
			
			/*
			$element.children()
				.find('.fa fa-chevron-down')
            	.removeClass('fa fa-chevron-down')
            	.addClass('fa fa-chevron-right');
			*/
            children.hide();
        }
        
    };
	
	function showAll() {
		
		    var
		        $table = $('#tree-table'),
		        rows = $table.find('tr');

		    rows.each(function (index, row) {
		        var
		            $row = $(row),
		            level = $row.data('level'),
		            id = $row.data('id'),
		            $columnName = $row.find('td[data-column="name"]'),
		            children = $table.find('tr[data-parent="' + id + '"]');
				
					
		        /*
		        	$row
		                .find('.fa fa-chevron-right')
		                .removeClass('fa fa-chevron-right')
		                .addClass('fa fa-chevron-down');
		        	
		            children.show();
		        */
		        reverseHide2($table, $row);
		        
		    });
		    
		    
		
		
	}
	
	
	function reverseHide2 (table, element) {
        var
            $element = $(element),
            id = $element.data('id'),
            children = table.find('tr[data-parent="' + id + '"]');

        if (children.length) {
            children.each(function (i, e) {
                reverseHide2(table, e);
            });

            $element
                .find('.fa fa-chevron-right')
                .removeClass('fa fa-chevron-right')
                .addClass('fa fa-chevron-down');

            children.show();
        }
        
    };
    
    var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		myChart3.resize();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	function selectCustomer(id) {
		
		var idcus = $('#'+id).val();
		$('#idcus_frm').val(idcus);
			
		$('#chgUser').submit();
	  }
		
	  function setSelectPicker() {
		$('.selectpicker').selectpicker();
	  }
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
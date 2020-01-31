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
ReadProperties propSec = new ReadProperties("connection.properties");
int sec = Integer.parseInt(propSec.get("sec"));
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>${dsfun} | SmartHR</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <!-- 
  <link rel="stylesheet" href="common/bootstrap.min.css" type="text/css" />
  -->
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  
  
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
  
  <link rel="stylesheet" href="../../js/slider/slider.css" type="text/css" />
  <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
  
  <link rel="stylesheet" type="text/css" href="../../js/tooltipster/css/tooltipster.bundle.min.css" />
  
  <link rel="stylesheet" type="text/css" href="datepair/jquery.timepicker.css" />
  
  <link rel="stylesheet" href="common/jquery.skedTape.css?v=4"/>
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
	.ruler {
	    position: absolute;
	    /*width: 20px;*/
	    margin-left: -4px;
	    /*margin-right: -50px;*/
	    text-align: center;
	    
	    margin-top: 10px;
	    color: #c0c0c0;
	    font-size:12px;
	}
	.mar50 {
  	 	margin-bottom: 50px;
  	}
  	.bg-emp {
  		background-color: #efefef;
  		border-bottom: 1px solid #fff;	
  	}
	
	<c:forEach items="${css}" var="css" varStatus="status">
		
		.${css.xcdtb} {
			background:	${css.xdtab};
			border: 1px solid ${css.xcdel};
		}
		.${css.xcdtb}:hover {
		  background-color: ${css.cmp1};
		}
		
	</c:forEach>
	
	.trasf {
		background:	${trasf2};
		border: 1px solid ${trasf};
	}
	.trasf:hover {
	  background-color: ${trasf3};
	}
	
	.bold {
		font-weight: bold;
	}
	
	.modal-backdrop
	{
	    opacity:0.5 !important;
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
        
        <%@ include file="../../inc2/menuApp7.jsp" %>
            
        <section id="content">
        
          <header id="task_flyout" class="header bg-light dk">
           	  <input type="hidden" id="id_departments" name="id_departments_vid">
		  	  <input type="hidden" id="year" name="year_vid" >
		      <input type="hidden" id="week" name="week_vid">
		    
		    <div class="row">
			  
			  <div class="col-sm-8 text-left" style="padding-top:10px">
			  	
			  	<a href="../planner/planner-ric.htm?tp=${tp}" class="btn btn-default m-b-sm"><%=propLang.get("btnSearch") %></a>
			  	
			  	<a href="#" onclick="setEye()" style="width:40px" data-toggle="class:nav-xs,nav-xs" 
			  		class="btn btn-default m-b-sm aaa" title="Mostra/Nascondi non pianificati" data-container="body" >
			  		<i class="fa fa-eye text-active"></i>
	           		<i class="fa fa-eye-slash text"></i></a>
			  	
			  	<a href="#" onclick="setWorkshift()" style="width:40px" data-toggle="class:nav-xs,nav-xs" 
			  		class="btn btn-default m-b-sm aaa" title="Aggiungi Turno" ${dis}>
			  		<i class="fa fa-plus-square text-active"></i>
	           		<i class="fa fa-plus-square-o text"></i></a>
			  	
			  	<c:if test="${tp=='A'}">
		  			<a href="../../hr/popup/planner-note2.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status=R&tp=${tp}" 
			  			style="width:70px" class="getModal btn btn-dark  m-b-sm"><%=propLang.get("btnRigetta") %></a>
		  			
			  		<a href="../planner/planner-approve.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status=A&tp=${tp}" id="btnApprova" 
			  			style="width:70px" class="btn btn-info  m-b-sm" ${dis} 
	      				onclick="showLoader2();" ${dis}><%=propLang.get("btnApprova") %></a>
			  		
			  		<!-- 
				  	<a href="../planner/planner-stampa.htm?id_departments=${id_departments}&year=${year}&week=${week}" id="btnStampa" 
				  		style="width:70px" class="btn btn-primary  m-b-sm" 
		      			onclick="showLoader2();"><%=propLang.get("btnStampa") %></a>
		      		-->	
		      		<!-- 
		      		<a href="../planner2/planner-stampa.htm?id_departments=${id_departments}&year=${year}&week=${week}" id="btnStampa" 
				  		style="width:70px" class="btn btn-primary  m-b-sm" target="_blank"
		      			><%=propLang.get("btnStampa") %></a>
		      		-->
		      		<a href="#" onclick="openStampa('${tp}, '${id_departments}', '${year}', '${week}', '0')" id="btnStampa" 
				  		style="width:70px" class="btn btn-primary  m-b-sm"
		      			><%=propLang.get("btnStampa") %></a>
		      	</c:if>
			  	<c:if test="${tp=='I'}">
			  		
		      		<a href="../planner/planner-approve.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status=X&tp=${tp}" id="btnApprova" 
		      			style="width:150px" class="btn btn-info  m-b-sm" ${dis}
		      			onclick="showLoader2();"><%=propLang.get("btnRichiedi") %></a>
			  		<%
			  		if(session.getAttribute("tipo_hr").toString().equals("A") || session.getAttribute("tipo_hr").toString().equals("M") || session.getAttribute("tipo_hr").toString().equals("H") || session.getAttribute("tipo_hr").toString().equals("S")) {	  			
			  		%>
			  		<c:if test="${dis=='disabled'}">
			  		<a href="../../hr/popup/planner-note.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status=Y&tp=${tp}" 
				  		style="width:70px" class="getModal btn btn-dark  m-b-sm"><%=propLang.get("btnRiapri") %></a>
			  		</c:if>
			  		<%
			  		}
			  		%>
			  		<!-- 
			  		<a href="../planner/planner-stampa.htm?id_departments=${id_departments}&year=${year}&week=${week}" id="btnStampa" 
				  		style="width:70px" class="btn btn-primary  m-b-sm" 
		      			onclick="showLoader2();"><%=propLang.get("btnStampa") %></a>
		      		-->
		      		<!-- 
		      		<a href="../planner2/planner-stampa.htm?id_departments=${id_departments}&year=${year}&week=${week}" id="btnStampa" 
				  		style="width:70px" class="btn btn-primary  m-b-sm" target="_blank"
		      			"><%=propLang.get("btnStampa") %></a>
		      		-->
		      		<a href="#" onclick="openStampa('${tp}', '${id_departments}', '${year}', '${week}', '0')" id="btnStampa" 
				  		style="width:70px" class="btn btn-primary  m-b-sm"
		      			><%=propLang.get("btnStampa") %></a>
		      	</c:if>
		      	
		      	<a href="../../hr/popup/planification_note.jsp?id_departments=${id_departments}&year=${year}&week=${week}&tp=${tp}" 
						id="btnNote" class="getModal btn btn-default  m-b-sm">Note</a>
				
				<a href="../../hr/popup/planification_type.jsp" 
						id="btnLegenda" class="getModal btn btn-default  m-b-sm">Legenda</a>
				</div>
				<div class="col-sm-4 text-right" style="padding-top:10px">
				    <span class="h3 text-ellipsis" style="margin-top:5px">${dsdep}</span>
				</div>
			</div>
				
       	  </header>  
          <section class="hbox stretch">   
          
                 
            <section>
              <section class="vbox">
                <section class="scrollable padder-md  mar50 " id="bjax-target">
                    
                      
                	<div class="row row-sm" style="margin-top:10px">
                      	
		                <section class="panel panel-default">
		                    <header class="panel-heading bg-light">
		                      <ul class="nav nav-tabs nav-justified">
		                        
		                        <c:forEach items="${giorni}" var="giorni" varStatus="status">
		                        <li ${giorni.cmp2} id="li${giorni.xcdel}"><a href="#day${giorni.xcdel}" ${giorni.cmp3} data-toggle="tab">
		                        	<span style="font-size:10px">${giorni.xdtab} <b>${giorni.cmp1}</b></span></a></li>
		                        </c:forEach>
		                        
		                        <li id="litot" onclick="loadTot()"><a href="#dayTotal" data-toggle="tab"><%=propLang.get("totali") %></a></li>
		                      </ul>
		                  </header>
			              
			              <div class="panel-body">
		                      
		                      
		                      
		                      <div class="tab-content">
		                      			             
		                      	<form id="pp" name="pp" method="post">
		                      		<input type="hidden" id="eye" name="eye" value="${eye}"/>
		                      		<input type="hidden" name="planDow" id="planDow" value=""/>
				                    <input type="hidden" id="workshift" name="workshift" value="${workshift}"/>
		                      		  		
		                      		<div class="form-group pull-in clearfix">
		                      		
		                      			<div class="">
			                      		<div class="col-sm-2">
			                      			<label><%=propLang.get("tipo_turno") %></label>
				                      		
				                      		<select name="planification_type" id="planification_type" 
					                      		class="form-control col-sm-2 ss">
					                      		<option value="">-</option>
					                      		
					                      		<c:forEach items="${css}" var="css" varStatus="status">
												<option value="${css.cmp3}" ${css.cmp2}
													data-description="${css.cmp4}" 
													data-css="${css.xcdtb}">${css.cmp4}</option>
												</c:forEach>
					                      	</select>
				                      	</div>
				                      	<div class="col-sm-10">
				                      		
				                      		<label><%=propLang.get("duration") %></label>
				                      		<input id="duration" name="duration" class="slider slider-horizontal form-control" type="text" value="4" style="width:100%" data-slider-min="0" data-slider-max="18" 
				                      			data-slider-step="0.25" data-slider-value="4" data-slider-orientation="horizontal" >
				                      		
				                      	</div>
				                      	</div>
				                    </div>
			                      	
			                    	<div class="line line-dashed b-b line-lg pull-in"></div>
						     
		                      	</form>      
		                      	
		                      	<!--
		                      	<div id="main2" style="height:60px"></div>
                              	-->
                              	
		                        <div class="tab-pane active" id="day0">
		                        	
		                        	<div align="center" id="dayLoader0">
									  	<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay0" name="caricoDay0" value="N"/>
									</div>
			              			
			              		</div>
			              
			              		<div class="tab-pane" id="day1">
		                        	<div align="center" id="dayLoader1">
									  	<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay1" name="caricoDay1" value="N"/>
									  	
									</div>
		                        </div>
		                        <div class="tab-pane" id="day2">
		                        	<div align="center" id="dayLoader2">
									  	<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay2" name="caricoDay2" value="N"/>
									  	
									</div>
		                        </div>
		                        <div class="tab-pane" id="day3">
		                        	<div align="center" id="dayLoader3">
									  	<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay3" name="caricoDay3" value="N"/>
									  	
									</div>
		                        </div>
		                        <div class="tab-pane" id="day4">
		                        	<div align="center" id="dayLoader4">
									  	<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay4" name="caricoDay4" value="N"/>
									  	
									</div>
		                        </div>
		                        <div class="tab-pane" id="day5">
									<div align="center" id="dayLoader5">
									  	<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay5" name="caricoDay5" value="N"/>
									  	
									</div>
								</div>
		                        <div class="tab-pane" id="day6">
		                        	<div align="center" id="dayLoader6">
									  	<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay6" name="caricoDay6" value="N"/>
									  	
									 </div>
		                        </div>
		                        <div class="tab-pane" id="dayTotal">
		                        	<div align="center" id="totLoader">
									  	<img src="common/loader.gif">
									</div>
									<div id="tt"></div>
		                        </div>
		                        
		                    </div>
		                </div>
						  
			            </section>
                    	
                    </div>
                    	
                </section>
              </section>
            </section>
                
	        </section>
            
            
        	<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  
  
  <div class="modal-dialog" id="planModal">
	  <div class="modal-content">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">&times;</a>
			<h3 class="font-thin"><%=propLang.get("pop_ap_title") %></h3>
		</div>
		<div class="modal-body">
		
		</div>
		<div class="modal-footer">
			<a class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ba_chiudi") %></a>
		</div>
	  </div>
  </div>
  
  
  <script src="common/jquery.min.js"></script>
  <script src="common/popper.min.js"></script>
  <!-- Bootstrap -->
  <!-- 
  <script src="../../js/bootstrap.js"></script>
  -->
  <script src="common/bootstrap.min.js"></script>
  <script src="common/jquery.skedTape.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  
  <!-- Sparkline Chart -->
  <script src="../../js/charts/sparkline/jquery.sparkline.min.js"></script>
  
  <script src="../../js/app.plugin.js"></script>
  
  <script type="text/javascript" src="../../js/tooltipster/js/tooltipster.bundle.min.js"></script>
  
  <!-- slider -->
  <script src="../../js/slider/bootstrap-slider.js"></script>
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <!-- Datepair -->
  <script type="text/javascript" src="datepair/jquery.timepicker.js"></script>
  <script src="datepair/datepair.js"></script>
  <script src="datepair/jquery.datepair.js"></script>
  
  <script>
  
  	var vistime = false;
  	
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		$('#pp').hide();
		renderRighello();
		loadDay(0);
		$('.aaa').tooltipster();
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
  	
  	function typeVis() {
  		
  		if(vistime) {
  			$('#chktime').prop('checked', true);
  		}
  		else {
  			$('#chktime').prop('checked', false);
  		}
  		showHide2();
  	}
  	
  	function showHide2() {
  		var time;
  		
  		if($('#chktime').prop('checked')) {
  			time=true;
  			vistime=true;
  			$('#divTime2').hide()
  			$('#divTime').show()
  		}
  		else {
  			time = false;
  			vistime=false;
  			$('#divTime2').show()
  			$('#divTime').hide()
  		}
  	}
  	
  	function showHide() {
  		var trasf;
  		
  		if($('#insTrasf').prop('checked')) {
  			trasf=true;
  			$('#divTrasf').show()
  			$('#divDurata').hide()
  			
  		}
  		else {
  			trasf = false;
  			$('#divTrasf').hide()
  			$('#divDurata').show()
  			
  		}
  		//alert(trasf)
  	}
  	
  	 $('.ss').selectpicker({
	    liveSearch: true
	 });
  	 
  	function selpick() {
  		$('.ss').selectpicker({
 		    liveSearch: true
 		 });
  	}
  	
  	function setDatePair() {
  		$('#datepairExample .time').timepicker({
			'showDuration': true,
			'timeFormat': 'H:i',
			'step': 15,
			'minTime': '6:00am',
			'maxTime': '24:00pm',
		});

		
		$('#datepairExample').datepair();
  	}
  	
  //--------------------------- Data --------------------------------
  	var locations = [
  	    ${locations}
  	];
  	var events = [
  		${events}
  	];


  	function setSked(a , m , d, locations, events, employee, lun, mar, mer, gio, ven, sab, dom) {
  		
  				
  		
  		$('#sked').unbind().removeData();
    	$('#sked').empty();
    	
  		
  		var $sked = $('#sked').skedTape({
  		    caption: '<%=propLang.get("emp") %>',
  		    start: new Date(Date.UTC(a, m-1, d, 6, 0)),
  		    end: new Date(Date.UTC(a, m-1, d, 24, 0)),
  		    showEventTime: true,
  		    showEventDuration: true,
  		    scrollWithYWheel: false,
  		    locations: locations,
  		    events: events,
  		    maxTimeGapHi: 60 * 1000, // 1 minute
  		    minGapTimeBetween: 0 * 60 * 1000,
  		    snapToMins: 15,
  		    zoom:1,
  		    editMode: false,
  		    formatters: {
  		        date: function (date) {
  		            return $.fn.skedTape.format.date(date, 'l', '.');
  		        },
  		        duration: function (ms, opts) {
  		            return $.fn.skedTape.format.duration(ms, {
  		                hrs: 'h.',
  		                min: 'min.'
  		            });
  		        },
  		    },
  		    /*
  		    postRenderLocation: function($el, location, canAdd) {
  		        this.constructor.prototype.postRenderLocation($el, location, canAdd);
  		       
  		    }
  		    */
	  		postRenderLocation: function($el, location, canAdd) {
	  	        
	  	    	
	  	    	this.constructor.prototype.postRenderLocation($el, location, canAdd);
	  	        
	  	        
	  	        $el.prepend('<input id="www'+location.id+'" value="-" style="width:30px;text-align:right; margin-right:5px; background: transparent; border:0px;font-weight:bold;color:#000" readonly/>')
	  	    
	  	    }
  		});
  		
  		var title = $('#sked').find('.sked-tape__date');
  		
  		$(title).prop('title', employee);
  		
  		
  		$('#www0').val(lun);
  		$('#www1').val(mar);
  		$('#www2').val(mer);
  		$('#www3').val(gio);
  		$('#www4').val(ven);
  		$('#www5').val(sab);
  		$('#www6').val(dom);
  		

  		
  	}
  	
  	//MODAL
	$('.getModal').click(function(e) {
		
		
		
		$( 'div.modal-backdrop').remove();$( '.mia' ).remove();
		console.log('rim1');
		
		e.preventDefault();
		var href = $(e.target).attr('href');
		
		
		$.get(href, function(data) {
			$('<div class="modal mia" >' + data + '</div>').modal();
		});
		
	});
  	
  	function openPlan(id_employee, id_departments, year, week, day) {
  		
  		var href = 'plannerEmployee.htm?id_employee='+id_employee+'&id_departments='+id_departments+'&year='+year+'&week='+week+'&day='+day;
  		
  		$( 'div.modal-backdrop').remove();$( '.mia' ).remove();
		
		$.get(href, function(data) {
			$('<div class="modal mia" >' + data + '</div>').modal();
		});
		
  		
  	}
  	
    function convertHex(hex,opacity){
        hex = hex.replace('#','');
        r = parseInt(hex.substring(0,2), 16);
        g = parseInt(hex.substring(2,4), 16);
        b = parseInt(hex.substring(4,6), 16);
        
        result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
        return result;
    }
 	
    function loadTot() {
    	for(i=0;i<7;i++) {
	  		$("#li"+i).removeClass("active");
	  	}
	  	$("#litot").addClass("active");
	  	
	  	$.ajax({
	        url: 'plannerTot.htm',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'year=${year}&week=${week}&id_departments=${id_departments}',
	        beforeSend: function () {
	        	
	        	$('#tt').empty();
	        	
	        	$('#totLoader').show();
	        	
	        	
	        },
	        success: function (data) {
	        	$('#totLoader').hide();
	        	$("#tt").append(data);
	        	
	        	spark();
	        	
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            alert(xhr.status);
	            alert(thrownError);
	        }
	    });
	  	
    }
    
	function loadDay(day) {
  		
		$('#planDow').val(day);
		var eye = $('#eye').val();
		
	  	var caricoDay = $('#caricoDay'+day+'').val();
	  	for(i=0;i<7;i++) {
	  		$("#li"+i).removeClass("active");
	  	}
	  	$("#litot").removeClass("active");
	  	$("#li"+day).addClass("active");
	  	
	  	if(caricoDay=='N') {
	  		$.ajax({
		        url: 'plannerDay.htm',
		        type: 'POST',
		        dataType: 'html',
		        async: true,
		        data: 'year=${year}&week=${week}&day='+day+'&id_departments=${id_departments}&eye='+eye,
		        beforeSend: function () {
		        	
		        	$('#totali'+day).remove();
		        	$('#mainhr'+day).remove();
		        	$('#main'+day).remove();
		        	
		        	$('#sked'+day).unbind().removeData();
		        	$('#sked'+day).empty();
		        	$('#dayLoader'+day+'').show();
		        	
		        	
		        },
		        success: function (data) {
		        	$('#caricoDay'+day+'').val("");
		        	$('#dayLoader'+day+'').hide();
		        	$("#day"+day).append(data);
		        	
		        	
		        	getTotDayEmployee(day);
		        },
		        error: function (xhr, ajaxOptions, thrownError) {
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
	  	}
	
  	}
	
	var pln = '';
	
	function deletePlan() {
		
		var dis = '${dis}';
		
		if(dis!='disabled') { 
			
			var id = pln.id;
			var id_employee = pln.location;
			var start = pln.start;
			var end = pln.end;
			var idtype = pln.userData.idtype;
			var datetime_start = pln.userData.datetime_start;
			var datetime_end = pln.userData.datetime_end;
			var plan_day = pln.userData.plan_day;
			
			var iddep = pln.userData.iddep;
			
			if(datetime_start==undefined) {
				datetime_start = (start.getTime()/1000)-<%=sec%>;
				datetime_end = (end.getTime()/1000)-<%=sec%>;
			}
			
			planRemove(plan_day, id);
			
			
						
			$.ajax({
		        url: 'ajax/delPlan.jsp',
		        type: 'POST',
		        dataType: 'html',
		        async: true,
		        data: 'year=${year}&week=${week}&day='+plan_day+'&id_departments='+iddep+'&id_employee='+id_employee+"&datetime_start="+datetime_start+"&datetime_end="+datetime_end+"&id_planification_type="+idtype,
		        beforeSend: function () {
		        	
		        },
		        success: function (data) {
		        	//alert(data);
		        	
		        	
		        	
		        	getTotDayEmployee(plan_day);
		        	
		        	pln = '';
		        	
		        },
		        error: function (xhr, ajaxOptions, thrownError) {
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
			
		}
		
	}
	
	function planInsertTrasf(plan_day, pln2, iddep, dsdep) {
		
		switch(plan_day) {
		  case 0:
			  planInsertTrasf0(pln2, iddep, dsdep);
			  break;
		  case 1:
			  planInsertTrasf1(pln2, iddep, dsdep);
			  break;
		  case 2:
			  planInsertTrasf2(pln2, iddep, dsdep);
			  break;
		  case 3:
			  planInsertTrasf3(pln2, iddep, dsdep);
		  break;
		  case 4:
			  planInsertTrasf4(pln2, iddep, dsdep);
			  break;
		  case 5:
			  planInsertTrasf5(pln2, iddep, dsdep);
			  break;
		  case 6:
			  planInsertTrasf6(pln2, iddep, dsdep);
			  break;	  
		}
	}	
	
	function planRemove(plan_day, id) {
		switch(plan_day) {
		  case 0:
			  planRemove0(id);
			  break;
		  case 1:
			  planRemove1(id);
			  break;
		  case 2:
			  planRemove2(id);
			  break;
		  case 3:
			  planRemove3(id);
		  break;
		  case 4:
			  planRemove4(id);
			  break;
		  case 5:
			  planRemove5(id);
			  break;
		  case 6:
			  planRemove6(id);
			  break;	  
		}
	}	
	
	function modificaPlan(iddep_trasf, dsdep_trasf, idtype) {
		
		var insTrasf;
		if($('#insTrasf').prop('checked')) {
			insTrasf=true;
		}
		else {
			insTrasf=false;
		}
		
		//alert(insTrasf);
		
		$('#editModal .cls').click()
		//alert(insTrasf);
		
		var plan_day = pln.userData.plan_day;
		var id = pln.id;
		var id_departments = '${id_departments}';
		
		var pln2 = pln;
		
		
		
		if(id_departments!=iddep_trasf) {
			
			if(insTrasf) {
				planRemove(plan_day, id);
				planInsertTrasf(plan_day, pln2, iddep_trasf, dsdep_trasf);
			}
			
		}
		else {
			//alert('cosa???');
			
			
			
			
			
			
			
			
			
			
			
			$('#planification_type').val(idtype);
			
			/*
			var id_planification_type = $('#planification_type').val();
			var ds_planification_type = $('#planification_type').find(':selected').data('description');
			var css_planification_type = $('#planification_type').find(':selected').data('css');
			*/
			var id_planification_type = $('#planification_type2').val();
			var ds_planification_type = $('#planification_type2').find(':selected').data('description');
			var css_planification_type = $('#planification_type2').find(':selected').data('css');
			var durata=$('#duration2').val();
			var iddep = '${id_departments}';
			
			var time;
	  		if($('#chktime').prop('checked')) {
	  			vistime=true;
	  			time=true;
	  		}
	  		else {
	  			vistime=false;
	  			time = false;
	  		}
			
			//alert($('#planification_type').find(':selected').data('description'));
			
			// Intervallo orario
			if(time) {
				
				
				var fromhour = $('#fromhour').val();
				var tohour = $('#tohour').val();
				
				if(fromhour==''||tohour=='') {
					alert("Inserire ora inizio e ora fine turno");
				}
				else {
					
					
					//alert(JSON.stringify(pln));
					var location = pln.location;
					var id = pln.id;
					
					var start = new Date(pln.start);
					
					var monthstart = start.getUTCMonth() + 0; //months from 1-12
					var daystart = start.getUTCDate();
					var yearstart = start.getUTCFullYear();
					
					var timestart = fromhour.split(":");
					var hhstart = timestart[0];
					var mmstart = timestart[1];
					
					var end = new Date(pln.end);
					
					var monthend = end.getUTCMonth() + 0; //months from 1-12
					var dayend = end.getUTCDate();
					var yearend = end.getUTCFullYear();
					
					var timeend = tohour.split(":");
					var hhend = timeend[0];
					var mmend = timeend[1];
					
					start = new Date(yearstart, monthstart, daystart, hhstart, mmstart, 0, 0);
					end = new Date(yearend, monthend, dayend, hhend, mmend, 0, 0);
					
					
					var datetime_start = (start.getTime()/1000)-<%=sec%>;
					var datetime_end = (end.getTime()/1000)-<%=sec%>;
					
					//alert(datetime_start+' '+datetime_end);
					
					
					var start2 = (((parseInt(start.getTime())/1000))*1000);
					var end2 = (((parseInt(end.getTime())/1000))*1000);
					
					start2 = parseInt(start2)+(<%=sec%>*1000);
					end2 = parseInt(end2)+(<%=sec%>*1000);
					
					//alert(start2+' '+start.getTime());
					
					planRemove(plan_day, id);
						
					var go = true;
					try {
						switch(plan_day) {
						  	case 0:
						  		($sked0).skedTape('addEvent', {
						            name: ds_planification_type,
						            location: location, 
						            start: start2,
						            end: end2,
						            className: css_planification_type,
						            userData: {idtype: id_planification_type, plan_day: 0, iddep: iddep}
						        });
							  	break;
							case 1:
								($sked1).skedTape('addEvent', {
						            name: ds_planification_type,
						            location: location, 
						            start: start2,
						            end: end2,
						            className: css_planification_type,
						            userData: {idtype: id_planification_type, plan_day: 1, iddep: iddep}
						        });
							  	break;
							case 2:
								($sked2).skedTape('addEvent', {
						            name: ds_planification_type,
						            location: location, 
						            start: start2,
						            end: end2,
						            className: css_planification_type,
						            userData: {idtype: id_planification_type, plan_day: 2, iddep: iddep}
						        });
								break;
							case 3:
								($sked3).skedTape('addEvent', {
						            name: ds_planification_type,
						            location: location, 
						            start: start2,
						            end: end2,
						            className: css_planification_type,
						            userData: {idtype: id_planification_type, plan_day: 3, iddep: iddep}
						        });
								break;
							case 4:
								($sked4).skedTape('addEvent', {
						            name: ds_planification_type,
						            location: location, 
						            start: start2,
						            end: end2,
						            className: css_planification_type,
						            userData: {idtype: id_planification_type, plan_day: 4, iddep: iddep}
						        });
								break;
							case 5:
								($sked5).skedTape('addEvent', {
						            name: ds_planification_type,
						            location: location, 
						            start: start2,
						            end: end2,
						            className: css_planification_type,
						            userData: {idtype: id_planification_type, plan_day: 5, iddep: iddep}
						        });
								break;
							case 6:
								($sked6).skedTape('addEvent', {
						            name: ds_planification_type,
						            location: location, 
						            start: start2,
						            end: end2,
						            className: css_planification_type,
						            userData: {idtype: id_planification_type, plan_day: 6, iddep: iddep}
						        });
								break;
								  
						}
					}
					catch(err) {
						go = false;
						alert("Il turno si sovrappone ad uno esistente.\nCancella prima il turno esistente.");
					}
					//deletePlan();
					
					
					var id = pln.id;
					var id_employee = pln.location;
					var start = pln.start;
					var end = pln.end;
					var idtype = pln.userData.idtype;
					var datetime_start = pln.userData.datetime_start;
					var datetime_end = pln.userData.datetime_end;
					var plan_day = pln.userData.plan_day;
					var className = pln.className;
					var name = pln.name;
					
					var iddep = pln.userData.iddep;
					
					
					
					/*
					
					var start = new Date(pln.start);
					
					var monthstart = start.getUTCMonth() + 0; //months from 1-12
					var daystart = start.getUTCDate();
					var yearstart = start.getUTCFullYear();
					
					var timestart = fromhour.split(":");
					var hhstart = timestart[0];
					var mmstart = timestart[1];
					
					var end = new Date(pln.end);
					
					var monthend = end.getUTCMonth() + 0; //months from 1-12
					var dayend = end.getUTCDate();
					var yearend = end.getUTCFullYear();
					
					var timeend = tohour.split(":");
					var hhend = timeend[0];
					var mmend = timeend[1];
					
					start = new Date(yearstart, monthstart, daystart, hhstart, mmstart, 0, 0);
					end = new Date(yearend, monthend, dayend, hhend, mmend, 0, 0);
					
					
					var datetime_start = (start.getTime()/1000)-<%=sec%>;
					var datetime_end = (end.getTime()/1000)-<%=sec%>;
					
					//alert(datetime_start+' '+datetime_end);
					
					
					var start2 = (((parseInt(start.getTime())/1000))*1000);
					var end2 = (((parseInt(end.getTime())/1000))*1000);
					
					start2 = parseInt(start2)+(<%=sec%>*1000);
					end2 = parseInt(end2)+(<%=sec%>*1000);
					
					*/
					
					if(go) {
						
						if(datetime_start==undefined) {
							datetime_start = (start.getTime()/1000)-<%=sec%>;
							datetime_end = (end.getTime()/1000)-<%=sec%>;
						}
						
						planRemove(plan_day, id);
									
						$.ajax({
					        url: 'ajax/delPlan.jsp',
					        type: 'POST',
					        dataType: 'html',
					        async: true,
					        data: 'year=${year}&week=${week}&day='+plan_day+'&id_departments='+iddep+'&id_employee='+id_employee+"&datetime_start="+datetime_start+"&datetime_end="+datetime_end+"&id_planification_type="+idtype,
					        beforeSend: function () {
					        	
					        },
					        success: function (data) {
					        	//alert(data);
					        	
					        	//getTotDayEmployee(plan_day);
					        	
					        	pln = '';
					        	
					        },
					        error: function (xhr, ajaxOptions, thrownError) {
					            alert(xhr.status);
					            alert(thrownError);
					        }
					    });
						
						
						
						
						//alert('year=${year}&week=${week}&day='+plan_day+'&id_departments='+iddep+'&id_employee='+location+'&datetime_start='+(start2/1000)+'&datetime_end='+(end2/1000)+'&id_planification_type='+id_planification_type+'&id_departments_trasf='+iddep);
				        
				        $.ajax({
					        url: 'ajax/addPlan.jsp',
					        type: 'POST',
					        dataType: 'html',
					        async: true,
					        data: 'year=${year}&week=${week}&day='+plan_day+'&id_departments=${id_departments}&id_employee='+location+"&datetime_start="+((start2/1000)-<%=sec%>)+"&datetime_end="+((end2/1000)-<%=sec%>)+"&id_planification_type="+id_planification_type,
					        beforeSend: function () {
					        	
					        },
					        success: function (data) {
					        	//alert(data);
					        	
					        	getTotDayEmployee(plan_day);
					        	
					        },
					        error: function (xhr, ajaxOptions, thrownError) {
					            alert(xhr.status);
					            alert(thrownError);
					        }
					    });
				        
					}
					else {
						
						//alert(JSON.stringify(pln));
						//$('#caricoDay'+plan_day).val('N');
						//loadDay(plan_day);
						
						
						
						switch(plan_day) {
						  	case 0:
						  		($sked0).skedTape('addEvent', {
						            name: name,
						            location: id_employee, 
						            start: start,
						            end: end,
						            className: className,
						            userData: {idtype: idtype, plan_day: 0, iddep: iddep}
						        });
							  	break;
							case 1:
								($sked1).skedTape('addEvent', {
						            name: name,
						            location: id_employee, 
						            start: start,
						            end: end,
						            className: className,
						            userData: {idtype: idtype, plan_day: 1, iddep: iddep}
						        });
							  	break;
							case 2:
								($sked2).skedTape('addEvent', {
						            name: name,
						            location: id_employee, 
						            start: start,
						            end: end,
						            className: className,
						            userData: {idtype: idtype, plan_day: 2, iddep: iddep}
						        });
								break;
							case 3:
								($sked3).skedTape('addEvent', {
						            name: name,
						            location: id_employee, 
						            start: start,
						            end: end,
						            className: className,
						            userData: {idtype: idtype, plan_day: 3, iddep: iddep}
						        });
								break;
							case 4:
								($sked4).skedTape('addEvent', {
						            name: name,
						            location: id_employee, 
						            start: start,
						            end: end,
						            className: className,
						            userData: {idtype: idtype, plan_day: 4, iddep: iddep}
						        });
								break;
							case 5:
								($sked5).skedTape('addEvent', {
						            name: name,
						            location: id_employee, 
						            start: start,
						            end: end,
						            className: className,
						            userData: {idtype: idtype, plan_day: 5, iddep: iddep}
						        });
								break;
							case 6:
								($sked6).skedTape('addEvent', {
						            name: name,
						            location: id_employee, 
						            start: start,
						            end: end,
						            className: className,
						            userData: {idtype: idtype, plan_day: 6, iddep: iddep}
						        });
								break;
								  
						}
					}
					
					
				}
				//alert('time: '+fromhour+' '+tohour);
				
				
			}
			// durata e posizionamento
			else {
				
				deletePlan();
				
				try {
					switch(plan_day) {
					  case 0:
						  ($sked0).skedTape('startAdding', {
					            name: ds_planification_type,
					            duration: durata * 60 * 60 * 1000,
					            className: css_planification_type,
					            userData: {idtype: id_planification_type, plan_day: 0, iddep: iddep}
					        });
					        
					      addTot(0);
						  break;
					  case 1:
						  ($sked1).skedTape('startAdding', {
					            name: ds_planification_type,
					            duration: durata * 60 * 60 * 1000,
					            className: css_planification_type,
					            userData: {idtype: id_planification_type, plan_day: 1, iddep: iddep}
					        });
						  break;
					  case 2:
						  ($sked2).skedTape('startAdding', {
					            name: ds_planification_type,
					            duration: durata * 60 * 60 * 1000,
					            className: css_planification_type,
					            userData: {idtype: id_planification_type, plan_day: 2, iddep: iddep}
					        });
						  break;
					  case 3:
						  ($sked3).skedTape('startAdding', {
					            name: ds_planification_type,
					            duration: durata * 60 * 60 * 1000,
					            className: css_planification_type,
					            userData: {idtype: id_planification_type, plan_day: 3, iddep: iddep}
					        });
					  break;
					  case 4:
						  ($sked4).skedTape('startAdding', {
					            name: ds_planification_type,
					            duration: durata * 60 * 60 * 1000,
					            className: css_planification_type,
					            userData: {idtype: id_planification_type, plan_day: 4, iddep: iddep}
					        });
						  break;
					  case 5:
						  ($sked5).skedTape('startAdding', {
					            name: ds_planification_type,
					            duration: durata * 60 * 60 * 1000,
					            className: css_planification_type,
					            userData: {idtype: id_planification_type, plan_day: 5, iddep: iddep}
					        });
						  break;
					  case 6:
						  ($sked6).skedTape('startAdding', {
					            name: ds_planification_type,
					            duration: durata * 60 * 60 * 1000,
					            className: css_planification_type,
					            userData: {idtype: id_planification_type, plan_day: 6, iddep: iddep}
					        });
						  break;	  
					}
			        
			        
			        $('#planification_type').val('da24f14a-1436-4295-8f99-8b1a450b6277');
			        $('.ss').selectpicker('refresh');
			        
			    }
			    catch (e) {
			        if (e.name !== 'SkedTape.CollisionError') throw e;
			        //alert('Already exists');
			    }
			    
			}
			
		}
		
		pln2 = '';
	
		
		
	}
	
	function planEdit(plan) {
		
		var dis = '${dis}';
		
		var disabled = plan.disabled;
		
		
		//alert(JSON.stringify(plan));
		
		if(dis!='disabled') { 
			
			if(disabled==false) {
				pln = plan;
				
				$( 'div.modal-backdrop').remove();$( '.mia' ).remove();
				var href = 'editPlan.jsp?id_employee='+plan.location+'&idtype='+plan.userData.idtype+'&id_departments_sel='+plan.userData.iddep+'&id_departments=${id_departments}';
				
				$.get(href, function(data) {
					$('<div class="modal mia" >' + data + '</div>').modal();
				});
			}
			
		}
		
	}
	
	<%
	for(int i=0;i<7;i++) {
	%>
	
	function planRemove<%=i%>(id) {
		
		$sked<%=i%>.skedTape('removeEvent', id);
		
		addTot(<%=i%>);

		
	}
	
	function planInsertDB<%=i%>(plan, locationsDis) {
		
		//alert(JSON.stringify(plan));
		// check se è un trasfertista
		var ins = true;
		
		for(i=0;i<locationsDis.length;i++) {
			
			//alert(plan.location+" "+locationsDis[i])
			if(locationsDis[i]==plan.location) {
				ins = false;
			}
		}
		
		//alert(ins);
		
		
		if(ins) {
			var id = plan.id;
			var id_employee = plan.location;
			var start = plan.start;
			var end = plan.end;
			var idtype = plan.userData.idtype;
			var plan_day = plan.userData.plan_day;
			
			var datetime_start = (start.getTime()/1000)-<%=sec%>;
			var datetime_end = (end.getTime()/1000)-<%=sec%>;
			
	
	    	//addTot(plan_day);
	    	
			$.ajax({
		        url: 'ajax/addPlan.jsp',
		        type: 'POST',
		        dataType: 'html',
		        async: true,
		        data: 'year=${year}&week=${week}&day='+plan_day+'&id_departments=${id_departments}&id_employee='+id_employee+"&datetime_start="+datetime_start+"&datetime_end="+datetime_end+"&id_planification_type="+idtype,
		        beforeSend: function () {
		        	
		        },
		        success: function (data) {
		        	//alert(data);
		        	
		        	getTotDayEmployee(plan_day);
		        	
		        },
		        error: function (xhr, ajaxOptions, thrownError) {
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
		}
		else {
			
			//$sked<%=i%>.skedTape('removeEvent', plan.id);
			alert('Non puoi inserire turno a trasfertista ');
			
			$('#sked<%=i%>').unbind().removeData();
        	$('#sked<%=i%>').empty();
        	
			$('#caricoDay<%=i%>').val('N');
			loadDay(<%=i%>);
			
		}
		
	}
	
	function planInsertTrasf<%=i%>(pln2, iddep, dsdep) {
		
		
		//alert(iddep);
		
		
		var plan_day = pln2.userData.plan_day;
		var location = pln2.location;
		var start = new Date(pln2.start);
		start = ((start.getTime()/1000))*1000;
		
		var end = new Date(pln2.end);
		end = ((end.getTime()/1000))*1000;
		
		var className = pln2.className;
		var idtype = pln2.userData.idtype;
		var id_departments = pln2.userData.iddep;
		
		
		try {
	        ($sked<%=i%>).skedTape('addEvent', {
	            name: 'TRASF. '+dsdep+'',
	            location: location, 
	            start: start,
	            end: end,
	            className: 'trasf bold',
	            userData: {idtype: idtype, plan_day: <%=i%>, iddep: iddep}
	        });
	        
	        
	        
	        
	        $.ajax({
		        url: 'ajax/edtPlan.jsp',
		        type: 'POST',
		        dataType: 'html',
		        async: true,
		        data: 'year=${year}&week=${week}&day='+plan_day+'&id_departments='+id_departments+'&id_employee='+location+"&datetime_start="+((start/1000)-<%=sec%>)+"&datetime_end="+((end/1000)-<%=sec%>)+"&id_planification_type="+idtype+'&id_departments_trasf='+iddep,
		        beforeSend: function () {
		        	
		        },
		        success: function (data) {
		        	//alert(data);
		        	
		        	getTotDayEmployee(plan_day);
		        	
		        },
		        error: function (xhr, ajaxOptions, thrownError) {
		            alert(xhr.status);
		            alert(thrownError);
		        }
		    });
	        
	    }
	    catch (e) {
	        if (e.name !== 'SkedTape.CollisionError') throw e;
	        //alert('Already exists');
	    }
		
	}
	
	function planInsert<%=i%>() {
		
		//alert(JSON.stringify(locationsDis));
		
		
		
		var workshift = $('#workshift').val();
		if($.trim(workshift)!='') {
			var id_planification_type = $('#planification_type').val();
			var ds_planification_type = $('#planification_type').find(':selected').data('description');
			var css_planification_type = $('#planification_type').find(':selected').data('css');
			var durata=$('#duration').val();
			var iddep = '${id_departments}';
			
			//alert($('#planification_type').find(':selected').data('description'));
			
			try {
		        ($sked<%=i%>).skedTape('startAdding', {
		            name: ds_planification_type,
		            duration: durata * 60 * 60 * 1000,
		            className: css_planification_type,
		            userData: {idtype: id_planification_type, plan_day: <%=i%>, iddep: iddep}
		        });
		        
		        addTot(<%=i%>);
		        
		        $('#planification_type').val('da24f14a-1436-4295-8f99-8b1a450b6277');
		        $('.ss').selectpicker('refresh');
		    }
		    catch (e) {
		        if (e.name !== 'SkedTape.CollisionError') throw e;
		        //alert('Already exists');
		    }
		}
		else {
			//alert("Clicca sul tasto 'plus'");
		}
		
		
	}
	<%
	}
	%>
	
	function setEye() {
		
 		var eye = $('#eye').val();
 		
 		// Ribalto eye
 		if(eye=='') {
 			 $('#eye').val('P');
 		}
 		else {
 			 $('#eye').val('');
 		}
 		
 		
 		
 		var day = $('#planDow').val();
		
 		<%
 		for(int i=0;i<7;i++) {
 		%>
 		$('#caricoDay<%=i%>').val('N');
 		<%
 		}
 		%>
 		
		loadDay(day);
		
 		
 	}
	
	function setWorkshift() {
		
 		var workshift = $('#workshift').val();
 		
 		// Ribalto eye
 		if(workshift=='') {
 			 $('#workshift').val('P');
 			$('#pp').show();
 		}
 		else {
 			 $('#workshift').val('');
 			 $('#pp').hide();
 		}
 		
 		
 		//alert(workshift);
 		
 	}
	
	function isInt(n){
  	    return Number(n) === n && n % 1 === 0;
  	}

  	function isFloat(n){
  	    return Number(n) === n && n % 1 !== 0;
  	}
  	
  	function renderRighello() {
 		
 		// Righello + input ore
  		var id='';
  	 	for (var i = 0; i <= 18; i+=0.25) {
  	      	
  	 		id = ""+((i));
  	 		id = id.replace(/\./g,"");
  	 		
  	    	if(isInt(i)) {
  	    		var el = $('<label class="ruler">'+((i))+'</label>').css('left',(i/18*100)+'%');
  	    	}
  	    	else {
  	    		var el = $('<label class="ruler"><span style="font-size:8px">&times;</span></label>').css('left',(i/18*100)+'%');
  	    		
  	    	}
  	    	
  	    	$( ".slider-track" ).append(el);
  	    }
  	    
 	}
  	
	function setNote(status) {
		
		
  		var note = $('#note').val();
  		
  		if($.trim(note)=='') {
  			alert("Inserire le note.");
  			return false;
  		}
  		else if($.trim(note).length<10) {
  			alert("Inserire almeno 10 caratteri.");
  		}
  		else {
  			javascript:window.location='../planner/planner-approve.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status='+status+'&tp=${tp}&note='+note;
  			window.location.href='../planner/planner-approve.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status='+status+'&tp=${tp}&note='+note;
  		}
  		
  		
  	}
  	
	function setNote2(status) {
  		
		alert('a');
  		
  		var note = $('#note').val();
  		
  		if($.trim(note)=='') {
  			alert("Inserire le note.");
  			return false;
  		}
  		else if($.trim(note).length<10) {
  			alert("Inserire almeno 10 caratteri.");
  		}
  		else {
  			
  			$.ajax({
		        url: '../ajax/setNote.jsp',
		        type: 'POST',
		        dataType: 'html',
		        async: false,
		        data: 'id_departments=${id_departments}&year=${year}&week=${week}&status='+status+'&tp=${tp}&note='+note,
				beforeSend: function () {
		        	//showLoader();
		        },
		        success: function (data) {
		        	javascript:window.location='../planner/planner-approve.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status='+status+'&tp=${tp}&note='+note;
	  				window.location.href='../planner/planner-approve.jsp?id_departments=${id_departments}&year=${year}&week=${week}&status='+status+'&tp=${tp}&note='+note;
	  		    },
		        error: function () {
		        	hideLoader();
		        	alert('Errore! si e\' verificato un errore.');
		        }
		    });
			
		}
  		
  		
  	}
	
	function getTotDayEmployee(day) {
		
		showLoader2();
		
		$.getJSON('ajax/totDayEmployee.jsp?id_departments=${id_departments}&year=${year}&week=${week}&day='+day, function (data) {
  		    
			console.log(data);
			
			var lista = data.employee;
  		    
  		    
  		    
  		  	var id_employee = "";
  		  	var totempday = 0;
  		  	
  		 	$.each(lista, function(index, lista) {
  				
  		 		id_employee = lista.id_employee;
  		 		totempday = lista.totempday;
  		 		
  		 		$('#'+day+''+id_employee).val(totempday);
  		 		
  		 	});
  		 	
			var lista2 = data.monte;
  		    
  		    
  		    
  		  	id_employee = "";
  		  	totempday = 0;
  		  	monte = 0;
  		  	
  		 	$.each(lista2, function(index, lista2) {
  				
  		 		id_employee = lista2.id_employee;
  		 		totempday = lista2.totempday;
  		 		monte = lista2.monteore;
  		 		
  		 		console.log((parseFloat(monte)-parseFloat(totempday)));
  		 		
  		 		
  		 		$('#monte'+day+''+id_employee).val((parseFloat(monte)-parseFloat(totempday)));
  		 		
  		 	});
  		 	
  		 	
  		 	
  		 	addTot(day);
  		 	
  		 	$('#'+day+'v6').html((parseFloat(data.v6) >0) ? '<b style="color:#333">'+data.v6+'</b>' : '0');
  		 	$('#'+day+'v7').html((parseFloat(data.v7) >0) ? '<b style="color:#333">'+data.v7+'</b>' : '0');
  		 	$('#'+day+'v8').html((parseFloat(data.v8) >0) ? '<b style="color:#333">'+data.v8+'</b>' : '0');
  		 	$('#'+day+'v9').html((parseFloat(data.v9) >0) ? '<b style="color:#333">'+data.v9+'</b>' : '0');
  		 	$('#'+day+'v10').html((parseFloat(data.v10) >0) ? '<b style="color:#333">'+data.v10+'</b>' : '0');
  		 	$('#'+day+'v11').html((parseFloat(data.v11) >0) ? '<b style="color:#333">'+data.v11+'</b>' : '0');
  		 	$('#'+day+'v12').html((parseFloat(data.v12) >0) ? '<b style="color:#333">'+data.v12+'</b>' : '0');
  		 	$('#'+day+'v13').html((parseFloat(data.v13) >0) ? '<b style="color:#333">'+data.v13+'</b>' : '0');
  		 	$('#'+day+'v14').html((parseFloat(data.v14) >0) ? '<b style="color:#333">'+data.v14+'</b>' : '0');
  		 	$('#'+day+'v15').html((parseFloat(data.v15) >0) ? '<b style="color:#333">'+data.v15+'</b>' : '0');
  		 	$('#'+day+'v16').html((parseFloat(data.v16) >0) ? '<b style="color:#333">'+data.v16+'</b>' : '0');
  		 	$('#'+day+'v17').html((parseFloat(data.v17) >0) ? '<b style="color:#333">'+data.v17+'</b>' : '0');
  		 	$('#'+day+'v18').html((parseFloat(data.v18) >0) ? '<b style="color:#333">'+data.v18+'</b>' : '0');
  		 	$('#'+day+'v19').html((parseFloat(data.v19) >0) ? '<b style="color:#333">'+data.v19+'</b>' : '0');
  		 	$('#'+day+'v20').html((parseFloat(data.v20) >0) ? '<b style="color:#333">'+data.v20+'</b>' : '0');
  		 	$('#'+day+'v21').html((parseFloat(data.v21) >0) ? '<b style="color:#333">'+data.v21+'</b>' : '0');
  		 	$('#'+day+'v22').html((parseFloat(data.v22) >0) ? '<b style="color:#333">'+data.v22+'</b>' : '0');
  		 	$('#'+day+'v23').html((parseFloat(data.v23) >0) ? '<b style="color:#333">'+data.v23+'</b>' : '0');
  		 	
  		 	
  		 	
  		 	$('#'+day+'t6').html((parseFloat(data.v6) >0) ? '<b style="color:#333">'+Math.ceil(data.v6)+'</b>' : '0');
  		 	$('#'+day+'t7').html((parseFloat(data.v7) >0) ? '<b style="color:#333">'+Math.ceil(data.v7)+'</b>' : '0');
  		 	$('#'+day+'t8').html((parseFloat(data.v8) >0) ? '<b style="color:#333">'+Math.ceil(data.v8)+'</b>' : '0');
  		 	$('#'+day+'t9').html((parseFloat(data.v9) >0) ? '<b style="color:#333">'+Math.ceil(data.v9)+'</b>' : '0');
  		 	$('#'+day+'t10').html((parseFloat(data.v10) >0) ? '<b style="color:#333">'+Math.ceil(data.v10)+'</b>' : '0');
  		 	$('#'+day+'t11').html((parseFloat(data.v11) >0) ? '<b style="color:#333">'+Math.ceil(data.v11)+'</b>' : '0');
  		 	$('#'+day+'t12').html((parseFloat(data.v12) >0) ? '<b style="color:#333">'+Math.ceil(data.v12)+'</b>' : '0');
  		 	$('#'+day+'t13').html((parseFloat(data.v13) >0) ? '<b style="color:#333">'+Math.ceil(data.v13)+'</b>' : '0');
  		 	$('#'+day+'t14').html((parseFloat(data.v14) >0) ? '<b style="color:#333">'+Math.ceil(data.v14)+'</b>' : '0');
  		 	$('#'+day+'t15').html((parseFloat(data.v15) >0) ? '<b style="color:#333">'+Math.ceil(data.v15)+'</b>' : '0');
  		 	$('#'+day+'t16').html((parseFloat(data.v16) >0) ? '<b style="color:#333">'+Math.ceil(data.v16)+'</b>' : '0');
  		 	$('#'+day+'t17').html((parseFloat(data.v17) >0) ? '<b style="color:#333">'+Math.ceil(data.v17)+'</b>' : '0');
  		 	$('#'+day+'t18').html((parseFloat(data.v18) >0) ? '<b style="color:#333">'+Math.ceil(data.v18)+'</b>' : '0');
  		 	$('#'+day+'t19').html((parseFloat(data.v19) >0) ? '<b style="color:#333">'+Math.ceil(data.v19)+'</b>' : '0');
  		 	$('#'+day+'t20').html((parseFloat(data.v20) >0) ? '<b style="color:#333">'+Math.ceil(data.v20)+'</b>' : '0');
  		 	$('#'+day+'t21').html((parseFloat(data.v21) >0) ? '<b style="color:#333">'+Math.ceil(data.v21)+'</b>' : '0');
  		 	$('#'+day+'t22').html((parseFloat(data.v22) >0) ? '<b style="color:#333">'+Math.ceil(data.v22)+'</b>' : '0');
  		 	$('#'+day+'t23').html((parseFloat(data.v23) >0) ? '<b style="color:#333">'+Math.ceil(data.v23)+'</b>' : '0');
  		 	
  		 	
  		 	for(i=6;i<24;i++) {
  		 		var min = $('#'+day+'m'+i).text();
  		 		var datav = Math.ceil(data['v'+i]);
  		 		//alert(datav);
  		 		
  		 		if(datav>0&&min!=datav) {
  		 			$('#'+day+'m'+i).html('<b style="color:#f05050">'+min+'</b>');
  		  		}
  		 		else {
  		 			if(parseFloat(datav)>0)
  		 				$('#'+day+'m'+i).html('<b style="color:#1ab667">'+min+'</b>');
  		 		}
  		 	}
  		 	
  		 	
  		 	
  		 	
  		 	
  		 	$('.sked-tape__caption').html('${status} <%=propLang.get("emp") %>');
        	
		});
		
		hideLoader2();
		
	}
	
	function addTot(day) {
		
	}
	
	/*
	function getHeatMap(kpi, descr, dtda, dtal, idstores) {
		
		myChart3.dispose();
		$('#main2').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		//alert('../ajax/getHeatMap.jsp?kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}&tipo=${tipo}');
		console.log('HEATMAP: ../ajax/getHeatMap.jsp?kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}&tipo=${tipo}');
		
		$.ajax({
		      url: '../../counter2/ajax/getHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=XXX&tipo=D',
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  var res = data.split('##@@');
				  var max=res[0];
				  var heatmap=res[1];
				  var XA=res[2];
				  var YA=res[3];
				  //alert(heatmap);
				  //alert(YA);
				  var array = JSON.parse(heatmap);
				  var xa2=XA.split(",");
				  var ya2=YA.split(",");
				  
				  option2 = {
				    		
				    tooltip: {
				        position: 'top'
				    },
				    animation: true,
				    grid: {
				        left: '1%',
				        top: '${top}',
				        right: '10%',
				        bottom: '10%',
				        containLabel: true
				    },
				    xAxis: {
				        type: 'category',
				        //axisLabel: { rotate: 60, },
				        axisLabel: { rotate: 90, },
				        //data: ['00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'],
				        data: xa2,
				        splitArea: {
				            show: true
				        }
				    },
				    yAxis: {
				        type: 'category',
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
	
	*/
	
	function openStampa(tp, id_departments, year, week, day) {
  		
  		var href = '../popup/planner-stampa.jsp?tp='+tp+'&id_departments='+id_departments+'&year='+year+'&week='+week+'&day='+day;
  		
  		$( 'div.modal-backdrop').remove();$( '.mia' ).remove();
		
		$.get(href, function(data) {
			$('<div class="modal mia" >' + data + '</div>').modal();
		});
		
  		
  	}
  	
	
	function spark() {
		
		$(".sparkline2").each(function(){
			var $data = $(this).data();
			
			console.log($data);
			($data.type == 'bar') && $data.stackedBarColor && ($data.stackedBarColor = eval($data.stackedBarColor));
			$data.valueSpots = {'0:': $data.spotColor};
			$(this).sparkline('html', $data);
		});
	
	}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
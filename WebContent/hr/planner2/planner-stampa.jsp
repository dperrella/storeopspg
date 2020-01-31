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
  
  <!-- 
  <link rel="stylesheet" href="../../js/slider/slider.css" type="text/css" />
  <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
  
  <link rel="stylesheet" type="text/css" href="../../js/tooltipster/css/tooltipster.bundle.min.css" />
  
  <link rel="stylesheet" type="text/css" href="datepair/jquery.timepicker.css" />
  -->
  <link rel="stylesheet" href="common/jquery.skedTape.css?v=4" media="screen, print"/>
    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
  <style>
  	/*
  	@media all {
	    .page-break { display: none; }
	}
	
	@media print {
	    .page-break.visible { display: block; page-break-after: always; }
	}
	*/
	
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
  		background-color: #efefef !important;
  		border-bottom: 1px solid #fff;	
  	}
	
	<c:forEach items="${css}" var="css" varStatus="status">
		
		.${css.xcdtb} {
			background:	${css.xdtab} !important;
			border: 1px solid ${css.xcdel};
		}
		.${css.xcdtb}:hover {
		  background-color: ${css.cmp1} !important;
		}
		
	</c:forEach>
	
	.trasf {
		background:	${trasf2} !important;
		border: 1px solid ${trasf};
	}
	.trasf:hover {
	  background-color: ${trasf3} !important;
	}
	
	.bold {
		font-weight: bold;
	}
	
	p {
		font-weight: bold;
	}
	
	.modal-backdrop
	{
	    opacity:0.5 !important;
	}
	
	
	@media print {
	  body {-webkit-print-color-adjust: exact;}
	  
	  /* ==========================================================================
 * Default settings
 * ========================================================================== */
/* ==========================================================================
 * SkedTape
 * ========================================================================== */
.sked-tape {
  display: flex;
  align-items: stretch;
  width: 100%;
  max-width: 100%;
  box-sizing: border-box;
}

.sked-tape * {
  box-sizing: inherit;
}

.sked-tape__aside {
  flex: none;
  max-width: 30%;
  min-width: 100px;
  padding: 0 !important;
  border-right: 5px solid transparent;
  position: relative;
}

.sked-tape__locations {
  margin: 0;
  padding: 0;
  list-style: none;
}

.sked-tape__location {
  position: relative;
  padding: 0 15px;
  background-color: #EFEFEF !important;
  line-height: 54px;
  height: 54px;
}

.sked-tape__location:not(:last-child) {
  border-bottom: 1px solid white;
}

.sked-tape__location--forbidden {
  background-image: repeating-linear-gradient(-45deg, rgba(236, 106, 94, 0.5), rgba(236, 106, 94, 0.5) 10px, rgba(255, 0, 0, 0) 10px, rgba(255, 0, 0, 0) 20px);
}

.sked-tape__location-text {
  display: inline-block;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  word-wrap: normal;
}

.sked-tape__caption {
  display: block;
  background-color: white;
  height: 24px;
  position: relative;
  top: 0;
  text-align: center;
}

.sked-tape--has-dates .sked-tape__caption {
  height: 48px;
  line-height: 48px;
}

.sked-tape__time-wrap {
  flex: 3 0 auto;
  width: 0;
  padding: 0 !important;
}

.sked-tape__time-frame {
	
	width: 100%;
	/*
  display: block;
  width: 100%;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
  */
}

.sked-tape__time-frame:focus {
  outline: none;
}

.sked-tape__time-canvas {
  position: relative;
}

.sked-tape__hours {
  background: white;
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
}

.sked-tape__hours > ul {
  margin: 0;
  padding: 0;
  list-style: none;
  display: flex;
  height: 24px;
  align-items: center;
}

.sked-tape__hours > ul > li {
  flex: none;
  display: block;
  position: relative;
  /*
  min-width: 96px;
  width: 96px;
  height: 24px;
  line-height: 24px;
  */
  
  height: 18px;
  line-height: 18px;
  
}

.sked-tape__hours > ul > li > time {
  display: block;
  position: absolute;
  left: 0;
  font-size: 10px;
  white-space: nowrap;
}

.sked-tape__hours > ul > li:not(:first-child) > time {
  -webkit-transform: translateX(-50%);
          transform: translateX(-50%);
}

.sked-tape__hours > ul > li:last-child {
  width: 0;
  min-width: 0;
}

.sked-tape__hours > ul > li:last-child > time {
  -webkit-transform: translateX(-100%);
          transform: translateX(-100%);
}

.sked-tape__timeline-wrap {
  position: relative;
}

.sked-tape__timeline {
  list-style: none;
  margin: 0;
  padding: 0;
  width: 100%;
  border-left: none;
  display: flex;
  flex-direction: column;
  align-items: stretch;
}

.sked-tape__event-row {
  position: relative;
  background-color: #F4F4F4 !important;
  height: 54px;
}

.sked-tape__event-row:nth-child(odd) {
  background-color: #FDFDFD !important;
}

.sked-tape__event-row:first-child {
  border-top: 1px solid #E5E5E5 !important;
}

.sked-tape__event-row:last-child {
  border-bottom: 1px solid #E5E5E5 !important;
}

.sked-tape__event-row:not(:last-child) {
  border-bottom: 0;
}

.sked-tape__gap {
  display: block;
  height: 53px;
  line-height: 1;
  position: absolute;
  z-index: 4;
  white-space: nowrap;
  font-size: 12px;
  pointer-events: none;
}

.sked-tape__gap-text {
  display: block;
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
          transform: translate(-50%, -50%);
  background: white;
  border-radius: 0.2em;
  padding: 2px;
}

.sked-tape__intersection {
  background: transparent;
  top: 1px;
  bottom: 0;
  display: block;
  position: absolute;
  z-index: 4;
  border: 2px dashed rgba(255, 62, 44, 0.5);
}

.sked-tape__center {
  position: relative;
  top: 50%;
  -webkit-transform: translateY(-50%);
          transform: translateY(-50%);
  display: block;
  padding: 0 10px;
}

.sked-tape__grid {
  display: flex;
  align-items: stretch;
  list-style: none;
  margin: 0;
  padding: 0;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}

.sked-tape__grid > li {
  display: block;
  margin: 0;
  padding: 0;
  background-image: linear-gradient(to right, #E5E5E5 1px, #E5E5E5 1px), linear-gradient(to right, #E5E5E5 1px, #E5E5E5 1px), linear-gradient(to right, #E5E5E5 1px, #E5E5E5 1px), linear-gradient(to right, #E5E5E5 1px, #E5E5E5 1px), linear-gradient(to right, #E5E5E5 1px, #E5E5E5 1px) !important;
  background-size: 1px 100%, 1px 100%, 1px 100%, 1px 100%, 1px 100% !important;
  background-repeat: no-repeat !important;
  background-position: 0 0, 100% 0, 25% 0, 50% 0, 75% 0 !important;
  /*
  min-width: 96px;
  width: 96px;
  */
}

.sked-tape__indicator {
  position: absolute;
  z-index: 4;
  top: 0;
  bottom: 0;
  border-left: 0px solid #e43828;
}

.sked-tape__dates {
  display: flex;
  align-items: stretch;
  width: 100%;
  list-style: none;
  margin: 0;
  padding: 0;
}

.sked-tape__date {
  display: block;
  margin: 0;
  padding: 0;
  height: 24px;
  line-height: 24px;
  font-size: 13px;
  font-weight: bold;
  text-align: center;
  overflow: hidden;
  white-space: nowrap;
  background: #e0e0e0;
}

.sked-tape__date:nth-child(odd) {
  background: #eee;
}

.sked-tape__date:before, .sked-tape__date:after {
  content: attr(title);
  display: block;
  width: calc(50% - 20px);
  height: inherit;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sked-tape__date:before {
  float: left;
  margin-left: 15px;
  text-align: left;
}

.sked-tape__date:after {
  float: right;
  margin-right: 15px;
  text-align: right;
}

.sked-tape__date--short:before {
  float: none;
  width: auto;
  margin: 0 5px;
  text-align: center;
}

.sked-tape__date--short:after {
  display: none;
}
	}
	@page {
	    size:A4 po;
	    margin-left: 0px;
	    margin-right: 0px;
	    margin-top: 0px;
	    margin-bottom: 0px;
	    margin: 0;
	    -webkit-print-color-adjust: exact;
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
  			<section>
              <section class="vbox">
                <section class="scrollable padder-md  mar50 " id="bjax-target">
                
                	<div class="row row-sm" style="margin-top:10px;width:740px">
                      	
		                <section class="panel panel-default">
		                    <!-- 
		                    <header class="panel-heading bg-light">
		                      <ul class="nav nav-tabs nav-justified">
		                        
		                        <c:forEach items="${giorni}" var="giorni" varStatus="status">
		                        <li ${giorni.cmp2} id="li${giorni.xcdel}"><a href="#day${giorni.xcdel}" ${giorni.cmp3} data-toggle="tab">
		                        	<span style="font-size:10px">${giorni.xdtab} <b>${giorni.cmp1}</b></span></a></li>
		                        </c:forEach>
		                        
		                        <li id="litot" onclick="loadTot()"><a href="#dayTotal" data-toggle="tab"><%=propLang.get("totali") %></a></li>
		                      </ul>
		                  </header>
		                  -->
			              
			              <div class="panel-body">
		                      
									  	
		                        <div class="" style="page-break-after:always" id="day0">
		                        	
									<p><%=propLang.get("lun") %></p>
		                        	<div align="center" id="dayLoader0">
		                        		<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay0" name="caricoDay0" value="N"/>
									</div>
			              			
			              		</div>
			              
			              		
									  	
			              		<div class="" style="page-break-after:always" id="day1">
			              			<hr/>
			              			<p><%=propLang.get("mar") %></p>
		                        	<div align="center" id="dayLoader1">
		                        		<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay1" name="caricoDay1" value="N"/>
									  	
									</div>
		                        </div>
		                        
		                        
									  	
		                        <div class="" style="page-break-after:always" id="day2">
		                        	<hr/>
		                       		<p><%=propLang.get("mer") %></p>
		                        	<div align="center" id="dayLoader2">
		                        		<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay2" name="caricoDay2" value="N"/>
									  	
									</div>
		                        </div>
		                        
		                        
									  	
		                        <div class="" style="page-break-after:always" id="day3">
		                        	<hr/>
		                        	<p><%=propLang.get("gio") %></p>
		                        	<div align="center" id="dayLoader3">
		                        		<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay3" name="caricoDay3" value="N"/>
									  	
									</div>
		                        </div>
		                        
									  	
		                        <div class="" style="page-break-after:always" id="day4">
		                        	<hr/>
		                        	<p><%=propLang.get("ven") %></p>
		                        	<div align="center" id="dayLoader4">
		                        		<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay4" name="caricoDay4" value="N"/>
									  	
									</div>
		                        </div>
		                        
								<div class="" style="page-break-after:always" id="day5">
									<hr/>
		                        	<p><%=propLang.get("sab") %></p>
									<div align="center" id="dayLoader5">
										<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay5" name="caricoDay5" value="N"/>
									  	
									</div>
								</div>
								
								<div class="" style="page-break-after:always" id="day6">
									<hr/>
									<p><%=propLang.get("dom") %></p>
		                        	<div align="center" id="dayLoader6">
		                        		<img src="common/loader.gif">
									  	<input type="hidden" id="caricoDay6" name="caricoDay6" value="N"/>
									  	
									 </div>
		                        </div>
		                        
		                        <div class="" style="page-break-after:always" id="dayTotal">
		                        	<hr/>
		                        	<p><%=propLang.get("totali") %></p>
		                        	
		                        	<div align="center" id="totLoader">
									  	<img src="common/loader.gif">
									</div>
									<div id="tt"></div>
		                        </div>
		                        
		                	</div>
						  
			            </section>
                    	
                    </div>
                </section>
              </section>
            </section>
                    
  
  
  
  
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
		loadDay(1);
		loadDay(2);
		loadDay(3);
		loadDay(4);
		loadDay(5);
		loadDay(6);
		loadTot();
		
		$('.aaa').tooltipster();
		
		setTimeout(function(){
			window.print();
		}, 1000);
		
		//window.close();
		
		
		
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


  	function setSked(a , m , d, locations, events, employee) {
  		
  				
  		
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
  		    postRenderLocation: function($el, location, canAdd) {
  		        this.constructor.prototype.postRenderLocation($el, location, canAdd);
  		       
  		    }
  		});
  		
  		var title = $('#sked').find('.sked-tape__date');
  		
  		$(title).prop('title', employee);

  		
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
	        url: 'plannerTotStampa.htm',
	        type: 'POST',
	        dataType: 'html',
	        async: false,
	        data: 'year=${year}&week=${week}&id_departments=${id_departments}',
	        beforeSend: function () {
	        	
	        	$('#tt').empty();
	        	
	        	$('#totLoader').show();
	        	
	        	
	        },
	        success: function (data) {
	        	$('#totLoader').hide();
	        	$("#tt").append(data);
	        	
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
		        async: false,
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
	
	</script>
	
  	
  
</html>
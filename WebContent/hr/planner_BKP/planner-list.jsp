<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"  %>

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
  <link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.11.4/themes/black-tie/jquery-ui.css" />
  
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
  	
	.ruler {
	    position: absolute;
	    width: 20px;
	    margin-left: -10px;
	    text-align: center;
	    margin-top: 20px;
	    color: #c0c0c0;
	}
  	
  	.ui-slider-horizontal .ui-slider-range {
	  background-color: #97ce00;
	}
	
	.ui-slider-control-plus,
	.ui-slider-control-minus {
	  width: 26px;
	  height: 26px;
	  position: absolute;
	  z-index: 99;
	  margin: -7px 0 0 -13px;
	  border: 1px solid #d3d3d3;
	  -moz-border-radius: 5px;
	  -webkit-border-radius: 5px;
	  border-radius: 5px;
	  background: #97ce00;
	  line-height: 26px;
	}
	
	.ui-slider-control-plus:after,
	.ui-slider-control-minus:after {
	  position: absolute;
	  width: 26px;
	  height: 26px;
	  color: #fff;
	  text-align: center;
	  right: 1px;
	  top: -2px;
	  line-height: 26px;
	  font-size: 26px;
	}
	
	.ui-slider-control-plus:after {
	  content: "+";
	}
	
	.ui-slider-control-minus:after {
	  content: "-";
	}
	
	.ui-slider-control-minus,
	.ui-slider-control-plus,
	.ui-slider-handle {
	  cursor: pointer;
	}
	
	.ui-slider-handle.arrow-left,
	.ui-slider-handle.arrow-right {
	  width: 0;
	  height: 0;
	  border: none;
	  background: none;
	  outline: none;
	  cursor: pointer;
	}
	
	.ui-slider-handle.arrow-left {
	  margin-left: -16px;
	  border-top: 0.8em solid transparent;
	  border-bottom: 0.8em solid transparent;
	  border-right: 0.8em solid #e6e6e6;
	}
	
	.ui-slider-handle.arrow-right {
	  margin-left: 0px;
	  border-top: 0.8em solid transparent;
	  border-bottom: 0.8em solid transparent;
	  border-left: 0.8em solid #e6e6e6;
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
      <div class="navbar-header aside <%=session.getAttribute("style") %> nav-xs">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
          <i class="icon-list"></i>
        </a>
        <a href="../../app/main/index.htm" class="navbar-brand text-lt">
          <img src="../../images/<%=session.getAttribute("logo") %>" alt="<%=session.getAttribute("sysinf") %>">
          <span class="hidden-nav-xs m-l-sm"><%=session.getAttribute("sysinf") %></span>
        </a>
        <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".user">
          <i class="icon-settings"></i>
        </a>
      </div>        
      
      <ul class="nav navbar-nav hidden-xs">
        <li>
          <a href="#nav,.navbar-header" data-toggle="class:nav-xs,nav-xs" class="text-muted">
            <i class="fa fa-indent text"></i>
            <i class="fa fa-dedent text-active"></i>
          </a>
        </li>
      </ul>
      
      <form method="post" action="../../app/main/ricerca.htm" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">
        <div class="form-group">
          <div class="input-group">
            <span class="input-group-btn">
              <button type="submit" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
            </span>
            <input type="text"  name="q" class="form-control input-sm no-border rounded" placeholder="Cerca in SmartHR...">
          </div>
        </div>
      </form>
      
      <div class="navbar-right ">
        <ul class="nav navbar-nav m-n hidden-xs nav-user user">
          
          <%=session.getAttribute("notif") %>
          
          <li class="dropdown">
            <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
              <span class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
                <img src="../../images/${foto}" alt="...">
              </span>
              ${username} <b class="caret"></b>
            
            </a>
            <ul class="dropdown-menu animated fadeInRight">            
              <li>
                <a href="../../app/main/profilo.htm">Modifica Profilo</a>
              </li>
              <li>
                <a href="../main/notifiche.htm">
                  <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                  Notifiche
                </a>
              </li>
              <li>
                <a href="../../app/main/docs.html">Guida</a>
              </li>
              <li class="divider"></li>
              <li>
                <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal">Esci</a>
              </li>
            </ul>
          </li>
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
            
			
            <footer class="footer hidden-xs no-padder text-center-nav-xs">
              <div class="bg hidden-xs ">
                  <div class="dropdown dropup wrapper-sm clearfix">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                      <span class="thumb-sm avatar pull-left m-l-xs">                        
                        <img src="../../images/${foto}" class="dker" alt="...">
                        <i class="on b-black"></i>
                      </span>
                      <span class="hidden-nav-xs clear">
                        <span class="block m-l">
                          <strong class="font-bold text-lt">${username}</strong> 
                          <b class="caret"></b>
                        </span>
                        <span class="text-muted text-xs block m-l">${desc}</span>
                      </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight aside text-left">                      
                      <li>
                        <a href="../../app/main/profilo.htm">Visualizza Profilo</a>
                      </li>
                      <li>
                        <a href="../main/notifiche.htm">
                          <span class="badge bg-danger pull-right"><%=session.getAttribute("numNotif") %></span>
                          Notifiche
                        </a>
                      </li>
                      <li>
                        <a href="../../app/main/docs.html">Giuda</a>
                      </li>
                      <li class="divider"></li>
                      <li>
                        <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" >Esci</a>
                      </li>
                    </ul>
                  </div>
                </div>            
			</footer>
			
          </section>
        </aside>
        <!-- /.aside -->
        <section id="content">
          <section class="vbox">          
             
            <section class="hbox stretch" id="bjax-target">
           
	            <section>
                  
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      
		                <h2 class="font-thin m-b">${dsfun}</h2>
		                
		                <form action="planner-list.htm" method="post">
		                <input type="hidden" name="tp" value="${tp}">
		                <input type="hidden" name="azione" value="S">
		                <input type="hidden" name="tipoForm" value="I">
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                ${dsfun} 
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="planner-ric.htm?tp=${tp}">Torna alla ricerca</a></li>
								</ul>
							</div>
						  </section>
			              
			              <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-6">
						          <label>Seleziona Dipartimento</label>
						          <select id="id_departments" name="id_departments" data-required="true" class="form-control m-t parsley-validated">
							          <option value="">-</option>
							          <c:forEach items="${dep}" var="dep" varStatus="status">
			                          <option value="${dep.id}">${dep.description}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label>Seleziona Settimana</label>
						          <select id="week" name="week" data-required="true" class="form-control m-t parsley-validated">
							          <option value="">-</option>
							          <c:forEach items="${weeks}" var="weeks" varStatus="status">
			                          <option value="${weeks.xcdel}" ${weeks.cmp1}>Settimana ${weeks.xcdel}: ${weeks.xdtab}</option>
			                          </c:forEach>
	                        	  </select>   
						        </div>
						        
						        <div class="col-sm-2">
						          <label>Seleziona Anno</label>
						          <select id="year" name="year" data-required="true" class="form-control m-t parsley-validated">
							          <option value="2016">2016</option>
			                      </select>   
						        </div>
						        
						    </div>
						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg">Conferma</button>
					      </footer>
					      
						</section>
						
						
						
						
						  
						<!--  Giorno -->  
						<c:forEach items="${day}" var="day" varStatus="status">
						  
						  
						<section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span class="h3 font-thin">${day.xdtab}</span>
			                <input type="hidden" name="day" value="${day.xcdel}">
			                <input type="hidden" name="data" value="${day.cmp1}">
			                
			              </header>
						  
						  
						  <div class="table-responsive">
			               <table class="table table-striped b-t b-light">
			                 
						    <tbody id="body-table">
						    
						    <c:forEach items="${emp}" var="emp" varStatus="status">
							  <tr>
							    <td class="b-r" style="width:50px">
							    	<span class="thumb-sm avatar pull-left m-l-xs">                        
				                        <img src="../../images/default-icon-user.png" class="dker" alt="...">
				                    </span>
							    </td>
								<td class="b-r" style="width:150px">
									<div style="height:35px">${emp.name} ${emp.surname}</div>
									
									<input type="hidden" name="id_employee${day.xcdel}" value="${emp.id}"/>
									<input type="hidden" id="id_departments${day.xcdel}${emp.id}" name="id_departments${day.xcdel}${emp.id}" value="${emp.cmp2}"/>
									<input type="text" id="ds_departments${day.xcdel}${emp.id}" name="ds_departments${day.xcdel}${emp.id}" 
										style="padding:0px;margin:0px;color:#000;border:0px; background:none;font-weight:bold;" value="${emp.cmp3}" disabled/>
									
									<div id="periods${day.xcdel}${emp.id}"></div>
									
								</td>
								
								<td class="b-r" style="width:50px">
									<a href="../../app/popup/departments_planner.jsp?iddest=id_departments${day.xcdel}${emp.id}&dsdest=ds_departments${day.xcdel}${emp.id}" 
										class="getModal btn btn-default btn-block" data-toggle="tooltip"
										data-placement="top" title="" data-original-title="Cambia Dipartimento per questa Pianificazione">
							      		<i class="icon-shuffle"></i></a>
								</td>
								
								<td colspan="18">
									
									<div class="${day.xcdel}${emp.id}"></div>
								    
								</td>
								
								<td class="b-l" style="width:50px">
									<input type="text" name="totDip${day.xcdel}${emp.id}" id="totDip${day.xcdel}${emp.id}" 
										class="form-control" value="0" style="text-align:right" readonly />
								</td>
							  </tr>
							</c:forEach>
						    
						    </tbody>
						  </table>
						</div>
						
					  </section>
						  
					  </c:forEach>
					  <!--  fine Schiera giorni -->
					  
					  
                    	
                    	
						  
						  
			             
			            
                    	
                      </form>
                    	
                    </section>
                  </section>
                </section>
                
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
				        
                        <a href="planner-ric.htm?tp=${tp}" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-settings"></i> 
                          <span class="hidden-nav-xs m-l-sm">${dsfun}</span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="planner-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="Cerca Pianificazioni">
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
  
  <script src="https://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript" language="javascript"></script>
  <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js" type="text/javascript" language="javascript"></script>
  <script src="jquery.ui.touch-punch.min.js"></script>
  <script src="jquery-ui-intervals.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
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
		
		// Dipartimento
		if(href=="../../app/popup/departments.jsp") {
			href+="?roles_description=";
		}
		
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
  	
  	<c:forEach items="${day}" var="day" varStatus="status">
  	<c:forEach items="${emp}" var="emp" varStatus="status">
  
  	$(function () {
	    var intervals = new Intervals(".${day.xcdel}${emp.id}");
	    //intervals.addPeriod(8,4);
	    
	    <s:eval expression='T(pkg.utility.Utility).getEmployeePlanning("${plan_year}" , "${plan_week}" , "${day.xcdel}" , "${emp.id}")' var="test" />
	    
	    // Intervalli
	    ${test}
	    
	    intervals.setAddPeriodConfirmCallback(function (period, callback) {
	        callback(function () {
	                    	
	        	//return confirm('Aggiungi periodo tra ' + period.getAbscissas()[0] + ' e ' + period.getAbscissas()[1]);
	        	
	        	var periodid = JSON.stringify(period.getId());
	        	
	        	var scntDiv = $('#periods${day.xcdel}${emp.id}');
	        	
	        	$('<input type="text" id="p_dati${day.xcdel}${emp.id}_'+periodid+'" name="p_dati${day.xcdel}${emp.id}" value="'+period.getAbscissas()[0]+','+period.getAbscissas()[1]+'" />').appendTo(scntDiv);
                
	        	// Modifica il totale
	        	setTot('${day.xcdel}' , '${emp.id}');
	        	
	        	return true;
	        	
	        }());
	        
	    });

	    intervals.setDeletePeriodConfirmCallback(function (period, callback) {
	        callback(function () {
	        	if(confirm('Cancella periodo tra ' + period.getAbscissas()[0] + ' e ' + period.getAbscissas()[1])) {
	        		
	        		var periodid = JSON.stringify(period.getId());
	        		
	        		
	        		$('#p_dati${day.xcdel}${emp.id}_'+periodid+'').remove();
	        		
	        		// modifica il totale
	        		setTot('${day.xcdel}' , '${emp.id}');
	        		
	        		//alert(JSON.stringify(period.getId()));
	        		
	        		return true;
	        		
	        	}
	            //return confirm('Cancella periodo tra ' + period.getAbscissas()[0] + ' e ' + period.getAbscissas()[1]);
	        }());
	    });
	    
	    
	    
	    intervals.setOnHandleSlideCallback(function (context, period, edgeIndex) {
	        var handlePosition = context.offset().left;
	        var periodId = period.getId();
	        var handleAbscissa = period.getAbscissas()[edgeIndex];
	        //$("#onhandleslide_info").html("Last OnHandleSlide data:" + "<br>" + " --- x-position: " + handlePosition + " px<br>" + " --- slider value (abscissa): " + handleAbscissa + "<br>" + " --- orientation: " + (edgeIndex === 1 ? "right" : "left") + " handle<br>" + "Period id: " + periodId + "<br>");
			
	        var periodid = JSON.stringify(period.getId());
	        $('#p_dati${day.xcdel}${emp.id}_'+periodid+'').val(period.getAbscissas()[0]+','+period.getAbscissas()[1]);
	        
	     	// modifica il totale
    		setTot('${day.xcdel}' , '${emp.id}');
    		
	        return false;
	    });
	    
	    intervals.setOnBeforeHandleSlideCallback(function (context, period, edgeIndex) {
	        //return false;
	    });

	});
  	
 	// Space out values
    for (var i = 0; i <= 17; i+=0.5) {
      	
    	if(isInt(i)) {
    		var el = $('<label class="ruler">|<br/>'+((i+6)+1)+'</label>').css('left',(i/17*100)+'%');
    	}
    	else {
    		var el = $('<label class="ruler" style="font-size:8px">|</label>').css('left',(i/17*100)+'%');
    	}
    	
      	$( ".${day.xcdel}${emp.id}" ).append(el);
      
    }
  	
  	
  	</c:forEach>
  	</c:forEach>
  	
  	
  	function setInputs(day, id_employee, ini, fin, periodid) {
  		
  		//alert(day+" "+id_employee+" "+ini+" "+fin+" "+periodid);
  		
  		var scntDiv = $('#periods'+day+''+id_employee+'');
    	
    	$('<input type="hidden" id="p_dati'+day+''+id_employee+'_'+periodid+'" name="p_dati'+day+''+id_employee+'" value="'+ini+','+fin+'" />').appendTo(scntDiv);
        
    	// modifica il totale
		setTot(''+day+'' , ''+id_employee+'');
  	}
  	
	function setColor(day, id_employee, ini, fin, periodid, color) {
  		
  		//alert(day+" "+id_employee+" "+ini+" "+fin+" "+periodid);
  		
  		$('.ui-slider-control-'+periodid).remove();
  		//$('. ui-slider-range-'+periodid).
  		
  	}
  	
  	
  	
  	function setTot(day , idemployee) {
  		
  		//alert(day+" "+idemployee);
  		
  		var name="p_dati"+day+idemployee;
  		var tot = 0.0;
  		
  		$("input[name="+name+"]").each(function(){
  		    
  			// split string ",";
  			var res = this.value.split(",");
  			
  			tot+= parseFloat(res[1])-parseFloat(res[0]);
  			
  		});
  		
  		$('#totDip'+day+''+idemployee+'').val(tot);
  		//alert(tot);
  	}
  	
  	
  	function isInt(n){
  	    return Number(n) === n && n % 1 === 0;
  	}

  	function isFloat(n){
  	    return Number(n) === n && n % 1 !== 0;
  	}
  	$('#id_departments').val('${id_departments}');
  
  </script>
  
  </body>
</html>
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
	
	
	
	.WRK {
	  background: #ffffff;
	}			
	.ROL {
	  background: #F2DEDE;
	  color: #BA6866;
	}
	.FER {
	  background: #D9EDF7;
	  color: #31708F;
	}
	.MAL {
	  background: #FCF8E3;
	  color: #8A6D3B;
	  
	}
	.MON {
	  background: #F0FAFF;
	}
	.104 {
	  background: #F3F9FB;
	}
	.ASP {
	  background: #678296;
	  color:#fff;
	}
	.PLN {
	  background: #DFF0D8;
	  color: #629361;
	}
	.DIF {
	  background: #F2F4F8;
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
          <section class="hbox stretch">          
            <%@ include file="../../inc2/menuApp7.jsp" %>
             
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b">${dsfun} ${day}</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                ${dsfun} ${day}
			              </header>
			              
			              <section class="wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="control-ric.htm?tp=${tp}"><%=propLang.get("tornaRic") %></a></li>
								</ul>
							</div>
							
							<div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <form action="control-day-list.htm" id="myForm" method="post">
						    <input type="hidden" name="day" id="day" value="${day}"/>
						    </form>
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("pop_dp_filtra") %></label>
						          <input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_dp_filtra") %>">
		 
						        </div>
						    </div>
						  </section>
			              
			              
			              <table class="table b-t b-light">
			                <tbody id="fbody">
						    ${table}
						    </tbody>
			              </table>
			              
			            </section>
                    	
		                
		                
		                
		                
	                 	
			            
			            
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
 
  <script type="text/javascript" src="jcfilter.min.js"></script>
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
	
 	jQuery(document).ready(function(){
 		
 		jqueryRic();
    });
 	
 		
	function addPlanification() {
  		
  		
	  		var id_departments = $('#id_departments').val();
	  		var year = $('#year').val();
			var week = $('#week').val();
	  		var day = $('#day').val();
	  		var ora_ini = $('#addPlan_ora_ini').val();
	  		var ora_fin = $('#addPlan_ora_fin').val();
	  		var id_planification_type = $('#addPlan_id_planification_type').val();
	  		var id_employee = $('#id_employee').val();
	  		
	  		if(day==null)
	  			day = "";
	  		
	  		$("input[name='addPlan_day']").each(function () {
	  			day=day+""+this.value+",";
	  		});
	  		
	  		
	  		var chkErr = "";
	  		var msgErr = "";
	  		if(day=='') {
	  			chkErr=chkErr+"S";
	  			msgErr=msgErr+"- <%=propLang.get("msg1") %>\n";
	  		}
	  		if(ora_ini=='') {
	  			chkErr=chkErr+"S";
	  			msgErr=msgErr+"- <%=propLang.get("msg2") %>\n";
	  		}
	  		if(ora_fin=='') {
	  			chkErr=chkErr+"S";
	  			msgErr=msgErr+"- <%=propLang.get("msg3") %>\n";
	  		}
	  		if(id_planification_type=='') {
	  			chkErr=chkErr+"S";
	  			msgErr=msgErr+"- <%=propLang.get("msg4") %>\n";
	  		}
	  		if(id_employee=='') {
	  			chkErr=chkErr+"S";
	  			msgErr=msgErr+"- <%=propLang.get("msg5") %>\n";
	  		}
	  		
	  		if(chkErr=='') {
	  			
	  		  		
  		  		console.log('confirmSubmit=false');
  		  		
  		  		$.ajax({
  	  		        url: '../planner/addTurno.jsp',
  	  		        type: 'POST',
  	  		        dataType: 'html',
  	  		        async: true,
  	  		        data: 'id_departments='+id_departments+'&year='+year+'&week='+week+'&day='+day+'&id_employee='+id_employee+'&id_planification_type='+id_planification_type+'&ora_ini='+ora_ini+'&ora_fin='+ora_fin,
  	  				beforeSend: function () {
  	  		        	showLoader();
  	  		        },
  	  		        success: function (data) {
  	  		        	$('#myForm').submit();
  	  		        },
  	  		        error: function () {
  	  		        	hideLoader();
  	  		        	alert('Errore! si e\' verificato un errore.');
  	  		        }
  	  		    });
  		  		
  		  	}
	  	  	
	  	
  			
  	
 		
 		
 	}
 	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
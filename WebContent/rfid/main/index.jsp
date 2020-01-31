<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
          <section class="hbox stretch">   
          
          	<!-- menu app -->
            <%@ include file="../../inc2/menuApp4.jsp" %>
                   
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b">Device / Flow / Steps</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Flow / Steps
			              </header>
			              
			              <form method="post">
			              
			              <section class="wrapper">
			              	  
			              	  <!-- 
			              	  <label><%=propLang.get("sel_sel_dev") %></label>
					          <select id="iddevice" name="iddevice" onchange="reloadList('FLOW')" class="form-control">
						          <option value="">-</option>
						          <c:forEach items="${dev}" var="dev" varStatus="status">
		                          <option value="${dev.iddevice}">${dev.iddevice} - ${dev.name}</option>
		                          </c:forEach>
                        	  </select>   
                        	  
			              	  <div class="line line-dashed b-b line-lg pull-in"></div>
			              	  -->
			              	  
			              	  <div id="msg">${msg}</div>
                        	  
			              	  
			              	  <label><%=propLang.get("flo_dsfun") %></label>
			              	  <input type="hidden" id="iddevice" name="iddevice" value="5"/>
					          <select id="idflow" name="idflow" onchange="reloadList('STEP')" class="form-control">
						          <option value="">-</option>
						          <!--
						          <c:forEach items="${flow}" var="flow" varStatus="status">
		                          <option value="${flow.idflow}">${flow.idflow} - ${flow.description}</option>
		                          </c:forEach>
		                          -->
                        	  </select>   
                        	  
                        	  
			              	  <div class="line line-dashed b-b line-lg pull-in"></div>
			              	  
			              	  <label><%=propLang.get("sel_ste_dsfun") %></label>
					          <select id="idstep" name="idstep" onchange="" class="form-control">
						          <option value="">-</option>
						      </select>                           	  
                        	  
					      </section>
						  <footer class="panel-footer bg-light lter" style="height:70px">
						  
						  	<button type="button" onclick="confirmDelete();" class="pull-left btn btn-danger btn-lg"
						  		style="width:260px">
		  					  	  		<b>[DEMO MODE]</b> - Delete all Docs </button>
		  					
					      	<button type="button" onclick="setRelDevStep();" class="pull-right btn btn-success btn-lg"
					      		style="width:260px">
		  					  	  		View FLow/Step Docs</button>
					      </footer>
					      				      
		  				  </form>
		  				  
		  					
		  				</section>
		  				
		  				
		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>
                
                <!-- side content
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="rel_dev_step.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-share"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("aru_dsfun") %></span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="../../rfid/anag/devices-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("dev_cerca") %>">
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
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  
  <script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet"> 

  <script>
  
  	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		
		reloadList('FLOW');
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
/*  	
    $('#iddevice').val('${iddevice}');
    $('#idflow').val('${idflow}');
*/    
function reloadList(act) {
		
var param = "";

		if (act == "FLOW"){
    		param = $("#iddevice").val();
		}else{
			param = $("#idflow").val()+"-"+$("#iddevice").val();
			//param = $("#idflow").val()+"-0";
		}

    	$.ajax({
    		url: '../../GetFlowStep',
    		async: false,
    		data : {
    			action : act,
    			param : param
    		},
    		success : function(responseText) {

    			if (act == "FLOW"){
    	    		$("#idflow").html(responseText);
    			}else{
    				$("#idstep").html(responseText);
    			}    			
    			
    		}
    	
    	});
    	
}

function setRelDevStep(){
	
	if ($("#iddevice").val() == ""){
		alertify.error("Device Requered");
		$("#iddevice").focus()
		return;
	}
	
	if ($("#idflow").val() == ""){
		alertify.error("Flow Requered");
		$("#idflow").focus()
		return;
	}
	
	if ($("#idstep").val() == ""){
		alertify.error("Step Requered");
		$("#idstep").focus()
		return;
	}
	
	
	location.href = "document-list.htm?iddevice="+$("#iddevice").val()+"&flow="+$("#idflow").val()+"&step="+$("#idstep").val();
	
}

function confirmDelete() {
	
	var r = confirm("Confirm delete ALL DOCS (Cannot be undone)? ");
    if (r == true) {
        
    	$.ajax({
		      url: 'deleteDocs.jsp',
		      type: 'POST',
		      dataType: 'html',
		      success: function (data) {
					
		    	  alert(data);
					
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
    	
    } 
	
}
  	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
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
  <title><%=propLang.get("con_dett") %> | SmartHR</title>
  <meta name="description" content="Desk Microtech" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
</head>
<body class="">
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
	                <section class="scrollable padder-lg"  id="bjax-target">
		                
		                <div class="row row-sm">
		                
		                <h2 class="font-thin m-b"><%=propLang.get("con_dett") %></h2>
		                
		                <form data-validate="parsley" method="post" action="contract-dett.htm" onsubmit="return contr();">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("con_dett") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="contract-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
					        
					        <div class="form-group pull-in clearfix">
						        <div class="col-sm-12">
						          <label><%=propLang.get("con_emp") %></label>
						          <select id="id_employee" name="id_employee" class="form-control chosen-select">
						          	${Employees}
						          </select>   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group  pull-in clearfix">
						    	<div class="col-sm-4">
							        <label><%=propLang.get("con_dtda") %></label>
							        <input type="text" id="datada" name="datada" class="form-control" data-maxlength="50" ${btnActive}>
						        </div>
						        
						        <div class="col-sm-4">
							        <label><%=propLang.get("con_dtal") %></label>
							        <input type="text" id="dataal" name="dataal" class="form-control" data-maxlength="50" ${btnActive}>                        
						        </div>                        
						                                
						        <div class="col-sm-4">
							        <label><%=propLang.get("con_more") %></label>
							        <input type="text" id="monteore" name="monteore" class="form-control" data-maxlength="50" ${btnActive}>                        
						        </div>                        
						    </div>


						    <!-- 
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block"><%=propLang.get("dep_plan") %></span>
	                          </div>
		                    </div>
					        <br/> 
					        -->

						    
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" onclick="contr()" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			            </section>
			            
			            <input type="hidden" id="id_contract" name="id_contract" value="${Contract}">
			             
						</form>
			            
			            </div>
	                
	                </section>
	              </section>
	            </section>
	            
	            <!-- 
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%//=propLang.get("dep_dip_dep") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${emp}" var="emp" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="clear">
		                        	<div><a href="../employee/emp-dett.htm?id=${emp.id}&tipoForm=M">${emp.name} ${emp.surname}</a></div> 
		                       	</div> 
		                      </li>
	                        </c:forEach>
	                        </ul>
                        </div>
                    </section>
                  </section>
                </section>
                -->
                
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
				        
                        <a href="contract-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-file-text"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("con_dsfun") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="dep-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("dep_cerca") %>">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            <!-- / side content -->
        
                 
	        </section>
            

          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        
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
  
  <script src="../../js/bootstrap-modal.js"></script>
  
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
	    $("#datada").datepicker(options);
	    $("#dataal").datepicker(options);
	} );
	
  function contr(){
	  
	  if($("#id_employee").val() == ""){
		  $("#msg").html("<div class=\"alert alert-danger\">Employee Obbligatorio</div>");
		  $("#id_employee").focus();
		  return false;
	  }
	  
	  if($("#datada").val() == ""){
		  $("#msg").html("<div class=\"alert alert-danger\">Data Da Obbligatorio</div>");
		  $("#datada").focus();
		  return false;
	  }

	  /*
	  if($("#dataal").val() == ""){
		  $("#msg").html("<div class=\"alert alert-danger\">Data Al Obbligatorio</div>");
		  $("#dataal").focus();
		  return false;
	  }
	  */
	  
	  dat1 = $("#datada").val();
	  dat2 = $("#dataal").val();
	  
	  dat_1 = dat1.replace(/-/g, '');
	  dat_2 = dat2.replace(/-/g, '');

	  if ($("#dataal").val() != ""){
		if (dat_1 > dat_2){
			  $("#msg").html("<div class=\"alert alert-danger\">Controllare le date</div>");
			  $("#dataal").focus();
			  return false;
		}
	  }
  }
  
  var idemp = "${Id_employee}";
  var dtda = "${Datada}";
  var dtal = "${Dataal}";
  var mont = "${montre}";
  
  if (idemp != ""){
	  $("#id_employee").val(idemp);
  }
  
  if (dtda != ""){
	  $("#datada").val(dtda);
  }
  
  if (dtal != ""){
	  $("#dataal").val(dtal);
  }
  
  if (mont != ""){
	  $("#monteore").val(mont);
  }
  
  $('#id_employee').trigger("chosen:updated");
  
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
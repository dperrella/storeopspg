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
                      
		                <h2 class="font-thin m-b">Device / Steps</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Device / Steps
			              </header>
			              
			              <form data-validate="parsley" id="dd-form" action="rel_dev_step.htm" method="post">
			              
			              <section class="panel wrapper">
			              	  
			              	  <label><%=propLang.get("sel_dev") %></label>
					          <select id="id_devicetype" name="id_devicetype" data-required="true" 
					          	onchange="reloadList()" class="form-control parsley-validated chosen-select">
						          <option value="">-</option>
						          <c:forEach items="${dev}" var="dev" varStatus="status">
		                          <option value="${dev.iddevicetype}">${dev.iddevicetype} - ${dev.description}</option>
		                          </c:forEach>
                        	  </select>   
                        	  
			              	  <div class="line line-dashed b-b line-lg pull-in"></div>
			              	  
			              	  <label><%=propLang.get("flo_dsfun") %></label>
					          <select id="idflow" name="idflow" data-required="true" 
					          	onchange="reloadList()" class="form-control parsley-validated chosen-select">
						          <option value="">-</option>
						          <c:forEach items="${flow}" var="flow" varStatus="status">
		                          <option value="${flow.idflow}">${flow.idflow} - ${flow.description}</option>
		                          </c:forEach>
                        	  </select>   
                        	  
                        	  <div id="msg">${msg}</div>
                        	  
					      </section>
					      
					      <c:if test="${(iddevice != '')  && (idflow != '')}">
					      <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								    <th width="10%">
								    	<div class="checkbox i-checks">
						                  <label>
						                    <input name="chk" type="checkbox" value="">
						                    <i></i>
						                  </label>
						                </div>
								    </th>
							        <th width="10%"><%=propLang.get("flo_idstep") %></th>
							        <th width="10%"><%=propLang.get("flo_enabled") %></th>
							        <th><%=propLang.get("flo_descrizione") %></th>
							        <th width="10%"></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${step}" var="step" varStatus="status">
								<tr>
								  <td>
									  <div class="checkbox i-checks">
						                  <label>
						                    <input name="chkStep" type="checkbox" value="${step.xcdel}" ${step.cmp1}>
						                    <i></i>
						                  </label>
						              </div>
								  </td>
								  <td><span class="label bg-light">${step.xcdel}</span></td>
								  <td>${step.cmp3}</td>
			                      <td>${step.xdtab}</td>
			                      <td>
			                      <c:if test = "${step.cmp1 != ''}">
				                      <a href="../../rfid/rel/devtype-step-config.htm?id_devicetype=${step.cmp8}&idflow=${step.cmp9}&idstep=${step.cmp10}" 
				                      	data-toggle="ajaxModal" class="btn btn-default"><i class="fa fa-cogs"></i> Config</a></td>
			                      </c:if>
			                    </tr>
							  </c:forEach>
		  					  </tbody>
		  					  
		  					  <tfoot>
		  					  	<tr>
		  					  	  <td colspan="5" class="text-right">
		  					  	  	<a href="#" onclick="setRelDevStep();" class="btn btn-success btn-lg">
		  					  	  		<%=propLang.get("btn_autorizza") %></a>
		  					  	  </td>
		  					  	</tr>
		  					  </tfoot>
		  					  
		  					</table>
		  					
		  				  </div>
					      </c:if>
					      
		  				  </form>
		  					
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
  	
    $('#id_devicetype').val('${id_devicetype}');
    $('#idflow').val('${idflow}');
    
    function reloadList() {
      
    	var iddevice = $("#id_devicetype").val();
        var idflow = $('#idflow').val();
    	
  	  	javascript:window.location='rel_dev_step.htm?id_devicetype='+iddevice+'&idflow='+idflow;
  	  	
    }
    
    $('#example').dataTable( {
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
        "aaSorting": [],
        "iDisplayLength": 50
    } );
    
    function setRelDevStep() {
  	  
      var iddevice = $("#id_devicetype").val();
      var idflow = $('#idflow').val();
  	  
  	  var chk = "";
  	  $('input[name="chkStep"]:checked').each(function() {
  		  chk=chk+this.value+","; 
  	  });
  	  
  	  chk = chk.substring(0, chk.length - 1);
  	  
  	  var msgErr = "";
  	  
  	  if(iddevice=='') {
  		  msgErr = msgErr+" - <%=propLang.get("sel_device") %><br/>";
  	  }
  	  if(idflow=='') {
		  msgErr = msgErr+" - Select Flow<br/>";
	  }
  	  
  	  if(msgErr!='') {

  		  $('#msg').html("<div class=\"line line-dashed b-b line-lg pull-in\"></div>"
  				+"<div class=\"alert alert-danger\"><%=propLang.get("msgError") %><br/>"+msgErr+"</div>");
  		  
  		  document.getElementById('msg').scrollIntoView();
  	  }
  	  else {
  		
  		  // Redirect a rel_report_user-list.htm con paramteri:
  		  // - azione = (S)
  		  // - id_report = (id_report)
  		  // - idroles = (id_role)
  		  // - idcustomers = (chk)
  		  
  		  //javascript:window.location='rel_report_user-list.htm?azione=S&id_report='+id_report+'&id_role='+id_role+'&idcustomers='+chk;
  	  	  //window.location.href='rel_report_user-list.htm?azione=S&id_report='+id_report+'&id_role='+id_role+'&idcustomers='+chk;
  		  location.href='rel_dev_step.htm?azione=S&id_devicetype='+iddevice+'&idflow='+idflow+'&idstep='+chk;
		  
  	  }
  	  
    }
    
    
    function addRowConfig(table) {
	  	
  	  //$last = $('.idrow:last').val();
  		$last = $('#'+table+' tr').length-1;
  	  	$last=parseInt($last)+1;
  	  		
  	  	var $tr = '<tr>'
  				+'<td><input type="hidden" id="'+$last+'config_nrrig" name="config_nrrig" value="'+$last+'" class="idrow">'
  				+'	<input type="text" id="'+$last+'config_name" name="config_name" value="" class="form-control"/>'
  				+'</td>'
  				+'<td><select id="'+$last+'config_type" name="config_type" class="form-control">'
  				+'	<option value="text">Text</option>'
  				+'	<option value="number">Number</option>'
  				+'</select>'
  				+'<td><input type="text" id="'+$last+'config_value" name="config_value" value="" class="form-control"/></td>'
  				+'<td><span onclick="$(this).parents(\'tr\').remove();" class="btn btn-danger m-l-md"><i class="icon-close"></i></span></td>'
  				+'</tr>';	
  				
  		if($last==1) {
  			$("#"+table+" tbody").prepend($tr);
  		}
  		else {
  	  		$("#"+table).find("tbody tr:last").after($tr);
  		}
    }
    
    function parsleyForm() {
  	  $('#actionForm').parsley();
    }
  	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
</body>
</html>
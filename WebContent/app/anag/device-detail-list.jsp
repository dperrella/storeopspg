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
              
            <%@ include file="../../inc2/menuApp.jsp" %>
                 
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("add_dsfun") %></h2>
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("add_dsfun") %>: ${device_doorid} - ${device_name}
			              </header>
			              
			              <form data-validate="parsley" id="dd-form" action="device-detail-list.htm" method="post">
			              <input type="hidden" name="id_device" value="${id_device}"/>
			              <input type="hidden" name="azione" value="S"/>
			              <input type="hidden" name="tipoForm" value="${tipoForm}"/>
			              
			              <section class="wrapper">
			              	  
			              	  <div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="device-detail-ric.htm"><%=propLang.get("tornaRic") %></a></li>
								</ul>
							  </div>
							    
                        	  <div id="msg">${msg}</div>
					       	  
					      </section>
					      
			              <div class="table-responsive">
			                <table class="table table-striped b-t m-b-none">
			                  <thead>
								  <tr>
								    <th style="width:60px"><%=propLang.get("add_id_line") %></th>
							        <th style="width:200px"><%=propLang.get("add_id_area_from") %></th>
							        <th style="width:200px"><%=propLang.get("add_id_area_to") %></th>
							        <th><%=propLang.get("add_note") %></th>
							        <th style="width:60px">Status</th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
	      					  <c:forEach items="${devdet}" var="devdet" varStatus="status">
	      					  	  <tr>
							  	    <td><input type="hidden" id="${devdet.id_line}nrrig" name="nrrig" value="${devdet.id_line}" class="idrow">
							  	    	<input type="text" id="${devdet.id_line}id_line" name="id_line" value="${devdet.id_line}" class="form-control parsley-validated" 
							  	    	data-required="true" readonly/></td>
							  	    <td><select id="${devdet.id_line}id_area_from" name="id_area_from${devdet.id_line}" class="form-control parsley-validated" data-required="true" style="width:100%" ${devdet.cmp1}>
							  	    		<option>-</option>
							  	    		<c:forEach items="${aree}" var="aree" varStatus="status">
					                        <option value="${aree.id_area}">${aree.codice} - ${aree.description}</option>
					                        </c:forEach>
							  	    	</select>
							  	    	<script>
							  	    		document.getElementById('${devdet.id_line}id_area_from').value="${devdet.id_area_from}";
							  	    	</script>
							  	    	</td>
							  	    <td><select id="${devdet.id_line}id_area_to" name="id_area_to${devdet.id_line}" class="form-control parsley-validated" data-required="true" style="width:100%" ${devdet.cmp1}>
							  	    		<option>-</option>
							  	    		<c:forEach items="${aree}" var="aree" varStatus="status">
					                        <option value="${aree.id_area}">${aree.codice} - ${aree.description}</option>
					                        </c:forEach>
							  	    	</select>
							  	    	<script>
							  	    		document.getElementById('${devdet.id_line}id_area_to').value="${devdet.id_area_to}";
							  	    	</script>
							  	    	</td>
							  	    <td><input type="text" id="${devdet.id_line}note" name="note${devdet.id_line}" class="form-control parsley-validated" data-required="true" value="${devdet.note}" ${devdet.cmp1}/></td>
							  	    <td>
							  	    	<div class="form-group">
					                      <div class="col-sm-10">
					                        <label class="switch">
					                          <input type="checkbox" id="${devdet.id_line}status" 
					                          name="status${devdet.id_line}" ${devdet.status} 
					                          value="ON"
					                          onclick="rowStatus('${devdet.id_line}')">
					                          <span></span>
					                        </label>
					                      </div>
					                    </div>
							  	    </td>
				  				  </tr>
	      					  </c:forEach>
	      					  </tbody>
		  					  <tfoot>
		  					  	<tr>
		  					  	  <td colspan="7" class="text-right">
		  					  	  	<a href="#" onclick="setDeviceDetail();" class="btn btn-success btn-lg">
		  					  	  		<%=propLang.get("pop_ap_conferma") %></a>
		  					  	  </td>
		  					  	</tr>
		  					  </tfoot>
		  					</table>
		  				  </div>
		  					
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
				        
                        <a href="device-detail-ric.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-magnifier"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("add_dsfun") %></span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="aree-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("aar_cerca") %>">
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
	
	function setDeviceDetail() {
	  
		
	  	
	  $("#dd-form").submit();
		
  	}
	
	function rowStatus(row) {
		
		if ($('#'+row+'status').is(":checked"))
		{
			$('#'+row+'id_area_from').prop('disabled', false);
			$('#'+row+'id_area_to').prop('disabled', false);
			$('#'+row+'note').prop('disabled', false);
			
			//reinit parsley
	        $('#dd-form').parsley();
			
		}
		else {
			$('#'+row+'id_area_from').prop('disabled', true);
			$('#'+row+'id_area_to').prop('disabled', true);
			$('#'+row+'note').prop('disabled', true);
			
			$('#dd-form').parsley();
			
		}
		
	}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
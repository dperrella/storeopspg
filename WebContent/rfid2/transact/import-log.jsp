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
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
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
        
        <section id="content">
          <section class="hbox stretch">   
          
          	<!-- menu app -->
              
            <%@ include file="../../inc2/menuApp12.jsp" %>
                   
            <section>
              <section class="vbox">
              
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b">TransACT Import Products</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                
			                <span id="table_id">TransACT Import Products</span>
			                
			                <a href="../transact/import-log.htm" class="btn btn-default btn-xs m-l-sm pull-right" >
	                            <i class="icon-refresh"></i>
	                            Refresh Table
	                        </a>
			              </header>
			              
			              <section class="panel wrapper">
			              	
			              	<form enctype="multipart/form-data" action="#" method="post">
			              		<div class="row">
			              			<div class="col-sm-10">
			              				<label>Manual Import</label><br/>
			              				<input name="file[]" type="file" class="form-control" multiple/>
							    	</div>
			              			<div class="col-sm-2">
			              				<label>&nbsp;</label><br/>
			              				<input type="button" id="submit" class="btn btn-primary btn-block" value="Upload File" />
			              			</div>
			              		</div>
							    
							</form>
					
			              </section>
			              
			              
			                
			              <div id="msg">${msg}</div>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
						      		  <th>File name</th>
								      <th>File length</th>
								      <th>Import Date</th>
								      <th>Elaboration Status</th>
								      <th>Elaboration Date</th>
								      <th>Error</th>
								      <th>Elaboration Result</th>
						    	  </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach var="log" items="${logList}" varStatus="status">
					            <tr>
					                <td><b>${log.filename}</b></td>
					                <td>${log.size}</td>
					                <td>${log.importTimestamp}</td>
					                <td><span class="label bg-light">
								    	<c:choose>
					                		<c:when test="${log.elaborated == true}">
								    			Worked
								  			</c:when>
								  			<c:otherwise>
								  				Not Worked
								  			</c:otherwise>
										</c:choose>
										</span>
									</td>
					                <td>${log.elaborationTimestamp}</td>
					                <td>
					                	<c:choose>
								    		<c:when test="${log.error == true}">
								    			In error
								  			</c:when>
								  			<c:otherwise>
								  				No error
								  			</c:otherwise>
										</c:choose>
					                </td>
					                <td>${log.elaborationResult}</td>
					            </tr>
				        	  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
		  				  </div>
		  				
		  				</section>
		  				
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
  
  <!-- Datatables -->
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
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
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
  
  	
  
  $('body').on('click', '#submit', function(e){
      e.preventDefault();
      //$('#loader').show();
      
      showLoader2();
      
      var formData = new FormData($(this).parents('form')[0]);
      
      $.ajax({
          url: '../../SetFiles',
          type: 'POST',
          xhr: function() {
              var myXhr = $.ajaxSettings.xhr();
              return myXhr;
          },
          success: function (data) {
        	  hideLoader2();
              alert("Data upload result:\n" + data);
          },
          data: formData,
          cache: false,
          contentType: false,
          processData: false
      });
      return false;
	});
  
  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
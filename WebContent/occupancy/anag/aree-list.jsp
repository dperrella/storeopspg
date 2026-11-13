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
		z-index: 10000;
	}	
		
	#ajaxModal {
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
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp16.jsp" %>
              
              <section>
                  <section class="vbox">
              
               		<section class="scrollable padder-lg " id="bjax-target">
                  	  	
                  	  	<h2 class="font-thin m-b">Areas</h2>
	              
						<section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Areas List
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									
									<c:choose>
                      					<c:when test="${areeSize gt 5}">
                      						<li><a href="#" disabled>New Areas [Disabled - Max Area Reached]</a></li>
                      					</c:when>
					  					<c:otherwise>
					  						<li><a href="aree-dett.htm?tipoForm=I" data-toggle="ajaxModal">New Areas</a></li>
					  					</c:otherwise>
					  				</c:choose>
									
								</ul>
							</div>
						  </section>
			              
			              <div id="msg">${msg}</div>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
							        <th width="10%">Id</th>
							        <th width="70%">Description</th>
							        <th width="10%">Threshold
							        <th>EDIT</th>
							        <th>DEL</th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${aree}" var="aree" varStatus="status">
								<tr>
								  <td><span class="label bg-light">${aree.id}</span></td>
								  <td>${aree.description}</td>
								  <td>${aree.threshold}</td>
								  <td><a href="aree-dett.htm?tipoForm=M&id=${aree.id}" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-edit"></i></a></td>
								  <td><a href="aree-dett.htm?tipoForm=E&id=${aree.id}" class="btn btn-danger" data-toggle="ajaxModal"><i class="fa fa-times"></i></a></td>
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
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
	
	$('#example').dataTable( {
	      "language": {
	          "lengthMenu": "_MENU_ &nbsp;&nbsp;Numbers of Records per Page",
	          "zeroRecords": "No record found",
	          "info": "Page _PAGE_ of _PAGES_",
	          "infoEmpty": "No record available",
	          "infoFiltered": "(Filtrati da _MAX_ records totali)",
	          "decimal": ",",
	          "thousands": ".",
	          search: "Search:&nbsp;&nbsp; "
	      },
	      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	      "aaSorting": [],
	      "iDisplayLength": 50
	  } );
	  
	  function parsleyForm() {
		  $('#actionForm').parsley();
	  }
	  
	  
	  function ml() {
		  
		  
		  if($('#automatic_adjust').is(':checked')) {
			  $('#perc_threshold').attr('readonly', true);
		        $('#average_time').attr('readonly', true); 
		        
		        $('#avgbtn').show();
		  }
		  else {
			  $('#perc_threshold').attr('readonly', false);
		        $('#average_time').attr('readonly', false); 
		        
		        $('#avgbtn').hide();
		  }
	  	$('#automatic_adjust').change(function () {
		  
		    if (this.checked) {
		        $('#perc_threshold').attr('readonly', true);
		        $('#average_time').attr('readonly', true); 
		        
		        $('#avgbtn').show();
		    }
		    else {
		    	$('#perc_threshold').attr('readonly', false);
		        $('#average_time').attr('readonly', false); 
		        
		        $('#avgbtn').hide();
		    }
		});
	  }
	  
	  function relay() {
		  
		  var relay_type = $('#relay_type').val();
		  chkCommands(relay_type);
		  
		  console.log('aa');
		  if($('#use_relay').is(':checked')) {
			  $('#iprelay').attr('readonly', false);
			  $('#relay_cmd').attr('readonly', false); 
			  $('#relay_type').attr('readonly', false); 
			  $('#addIp').attr('disabled', false);
			  
			  var ipr= $('._ipr');
			  for (var i = 0; i < ipr.length; i++) {
			    // Using $() to re-wrap the element.
			    $(ipr[i]).attr('readonly', false);
			  }
			  
			  
			  
			  console.log('bb');
		  }
		  else {
			  $('#iprelay').attr('readonly', true);
		        $('#relay_cmd').attr('readonly', true); 
		        $('#relay_type').attr('readonly', true); 
		        $('#addIp').attr('disabled', true);
		        $('#iprelay').val('');
		        $('#relay_cmd').val('');
		        $('#relay_type').val('');
		        
		        var ipr= $('._ipr');
				  for (var i = 0; i < ipr.length; i++) {
				    // Using $() to re-wrap the element.
				    $(ipr[i]).attr('readonly', true);
				    $(ipr[i]).val('');
				  }
		        
		  }
	  	$('#use_relay').change(function () {
		  
		    if (this.checked) {
		        $('#iprelay').attr('readonly', false);
		        $('#relay_cmd').attr('readonly', false); 
		        $('#relay_type').attr('readonly', false); 
		        $('#addIp').attr('disabled', false);
		        
		        var ipr= $('._ipr');
				  for (var i = 0; i < ipr.length; i++) {
				    // Using $() to re-wrap the element.
				    $(ipr[i]).attr('readonly', false);
				  }
		        
		    }
		    else {
		    	$('#iprelay').attr('readonly', true);
		        $('#relay_cmd').attr('readonly', true); 
		        $('#relay_type').attr('readonly', true); 
		        $('#addIp').attr('disabled', true);
		        $('#iprelay').val('');
		        $('#relay_cmd').val('');
		        $('#relay_type').val('');
		        
		        var ipr= $('._ipr');
				  for (var i = 0; i < ipr.length; i++) {
				    // Using $() to re-wrap the element.
				    $(ipr[i]).attr('readonly', true);
				    $(ipr[i]).val('');
				  }
		    }
		});
	  }
	  
	  function chkCommands(relay) {
		  if(parseInt(relay)==1) {
			  $("#relay_cmd option[value='tl']").attr("disabled","disabled")
			  $('#relay_cmd').val('on');
		  }
		  else {
			  $("#relay_cmd option[value='tl']").removeAttr('disabled'); 
		  }
		  
	  }
	  
	  function addIp() {
		  
		  $('#ipr').append('<div class="ipr"s><div class="form-group pull-in clearfix">'
			        +'<div class="col-sm-10">'
			        +'  <label>IP Web Relay</label>'
			        +'  <input type="text" id="iprelay" name="iprelay" '
			        +'  	class="form-control parsley-validated" value="${ipr}">'
			        +'</div>'
			        +'<div class="col-sm-2 pull-right" align="right">'
			        +' 	<label>&nbsp;</label>'
			        +' 	<div id="btndel"><span onclick="if(confirm(\'Confirm Delete Ip?\')){deleteIp(this)}" class="btn btn-danger"><i class="icon-close"></i></span></div>'
			       	+'</div>'
			       	+'</div>'
			       	+'<div class="line line-dashed b-b line-lg pull-in"></div></div>');
		  
	  }
	  
	  function deleteIp(el) {
		  
		  var numItems = $('.ipr').length;
		  if(numItems>1)
		  	$(el).closest('.ipr').remove();
		  else
			alert('You must have 1 ip');
	  }
	  
	  function chk(e, h, d) {
			
			//alert(e.is(":checked"));
			
			if(e.is(":checked")) {
				$( "#"+h+"_"+d ).prop( "disabled", true );
			}
			else {
				$( "#"+h+"_"+d ).prop( "disabled", false );
			}
			
		}
  </script>
  
</body>
</html>
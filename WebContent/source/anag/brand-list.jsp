<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
import="java.util.Date"
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
  <title><%=propLang.get("itm_brand") %> | SmartSuite</title>
  <meta name="description" content="AppAtSource" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
   <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
   <link rel="stylesheet" href="css/magnific-popup.css" type="text/css" />
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
	.myDanger{
		border-color:red;
	}	
	.myClean{
		border-color:#cbd5dd;
	}	
     .myPlus {
		cursor:pointer;
	}	
     .myMinus {
		cursor:pointer;
	}	
		/* This adds padding from top and bottom */
	.custom-popup-class .mfp-container {
	  padding-top: 40px;
	  padding-bottom: 40px;
	}
	/* This adds max-width to popup content */
	.custom-popup-class .mfp-content {
	  width: 100%;
	  max-width: 700px;
	}
	.mfp-content .mfp-close{
		cursor:pointer !important;
	}
	/* This makes "#popup-with-something" scale down proportionally */
	#popup-with-something {
	  width: 100%;
	  height: 0;
	  overflow: hidden;
	  /* This is scale ratio */
	  padding-top: 56.25%;
	}
	/* This shifts close button outside of the container */
	#popup-with-something .mfp-close {
	  top: -44px;
	  color: #FFF;
	  text-align: right;
	  right: 1px;
	}
	/* This is an element itself */
	.some-element {
	  background: #EBE8E8;
	  position: absolute;
	  top: -3px;
	  left: 0;
	  width: 100%;
	  height: 100%;
	  box-shadow: 0 0 8px rgba(0, 0, 0, .6);
	}
	.spinner {
    font-size: 3px;
    text-indent: -999em;
    overflow: hidden;
    width: 1em;
    height: 1em;
    margin: 10em auto;
    border-radius: 50%;
    animation: spin2 5s infinite ease-in;
}
@keyframes spin2 {
    0%,90%,100% {
        box-shadow: 6em 0em 0 1em #000000, 2em 0em 0 -1em #000000, -2em 0em 0 -1em #000000, -6em 0em 0 -1em #000000, -10em 0em 0 -1em #000000;
    }
    10%,80% {
        box-shadow: 6em 0em 0 -1em #000000, 2em 0em 0 1em #000000, -2em 0em 0 -1em #000000, -6em 0em 0 -1em #000000, -10em 0em 0 -1em #000000;
    }
    20%,70% {
        box-shadow: 6em 0em 0 -1em #000000, 2em 0em 0 -1em #000000, -2em 0em 0 1em #000000, -6em 0em 0 -1em #000000, -10em 0em 0 -1em #000000;
    }
    30%,60% {
        box-shadow: 6em 0em 0 -1em #000000, 2em 0em 0 -1em #000000, -2em 0em 0 -1em #000000, -6em 0em 0 1em #000000, -10em 0em 0 -1em #000000;
    }
    40%,50% {
        box-shadow: 6em 0em 0 -1em #000000, 2em 0em 0 -1em #000000, -2em 0em 0 -1em #000000, -6em 0em 0 -1em #000000, -10em 0em 0 1em #000000;
    }
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
          <%@ include file="../../inc2/menuApp13.jsp" %>       
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("itm_brand") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("itm_brand") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="setTable.jsp?tipo=B&tipoForm=I" data-toggle="ajaxModal" id="addItem">New Record</a></li>
								</ul>
							</div>
						  </section>
							
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables" width="100%">
			                  <thead>
								  <tr>
							        <th width="10%">ID</th>
							        <th width="80%"><%=propLang.get("itm_description") %></th>
							        <th width="5%"></th>
							        <th width="5%"></th>
							      </tr>
							  </thead>
		  					</table>
		  					
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
  
  
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
  <script type="text/javascript" src="../main/xlsx/shim.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/xlsx.full.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/Blob.js"></script>
  <script type="text/javascript" src="../main/xlsx/FileSaver.js"></script>
    
    
  <script>
  var username = "${idusr}";
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		loadTable();
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
  var itemsJson;

  var table;
  
  function loadTable() {
	  
	  $('#example').dataTable( {
		"processing": true,
		 "ajax": {
		      "url": "../../ats/GetBrand",
		      "type": 'POST',
		      "dataSrc": 'listings',
		      "data": {idusr:username}
		 },
		"columns": [
				      { data: 'key' },
				      { data: 'label' },
				      {data:null},
					  {data:null}
		  		 ],
		  
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
	      "columnDefs": [
	          {
	              "targets": [ 0 ],
	              "className": "text-center"
	          },
	          {
	              "targets": [ 2 ],
	              "className": "text-center"
	          },
	          {
	              "targets": [ 3 ],
	              "className": "text-center"
	          }
	          
	      ], 
	      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	      "iDisplayLength": 50,
	      "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	    	    $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData["key"] + '</span>');
	    	    $('td:eq(2)', nRow).html('<a href="setTable.jsp?tipo=B&tipoForm=E&key='+aData["key"]+'&value='+aData["label"]+'" id="editItem" class="btn btn-default myEdit" data-toggle="ajaxModal"><i class="fa fa-pencil"></i></a>');
	    	    $('td:eq(3)', nRow).html('<a href="setTable.jsp?tipo=B&tipoForm=D&key='+aData["key"]+'&value='+aData["label"]+'" id="deleteItem" class="btn btn-danger myDelete" data-toggle="ajaxModal"><i class="fa fa-times"></i></a>');
	    	    return nRow;
	      },
	  } );
	  
  }
  
  function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		 });
	  }
  
  function saveTable() {
	  
	  var tipoForm = $('#tipoForm').val();
	  var tipo = $('#tipo').val();
	  var key = $('#key').val();
	  var value = $('#value').val();
	  
	  showLoader2();
		
	  $.ajax({
	      url: '../ajax/saveTable.jsp',
	      type: 'POST',
	      dataType: 'html',
	      async: true,
	      data: 'tipoForm='+tipoForm+'&tipo='+tipo+'&key='+key+'&value='+value,
	      success: function (data) {
			
	    	  hideLoader2();
	    	  reload();
	      },
	      error: function () {
	      		
	    	  hideLoader2();
	    	  
	      }
	  });
	  
  }
  
  function reload() {
	  
	  $('#example').DataTable().destroy();
		
	  loadTable();
  }

  </script>

  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
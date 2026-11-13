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
  <title><%=propLang.get("aud_list") %> | SmartSuite</title>
  <meta name="description" content="AppAtSource" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="css/jquery.datetimepicker.min.css" type="text/css" />  
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
		
	 .myPlus {
		cursor:pointer;
	}
	.myDanger{
		border-color:red;
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
				   margin-left: -100px;" />
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
                      
		                <h2 class="font-thin m-b"><%=propLang.get("aud_list") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("aud_list") %>
			                
			                <a href="#" onclick="excelExport()" class="btn btn-default btn-xs m-l-sm pull-right" >
	                            <i class="fa fa-table"></i>
	                            Export Data
	                        </a>
	                        
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="audits-dett.htm?tipoForm=I&v=<%=new Date()%>" data-toggle="ajaxModal" id="addAudit"><%=propLang.get("aud_new") %></a></li>
									<li><a href="#" onclick="reload(0)">Only Active</a></li>
									<li><a href="#" onclick="reload(1)">Only Closed</a></li>
									<li><a href="#" onclick="reload(2)">All</a></li>
								</ul>
							</div>
							
							
							<div id="_prt">
							  
							  	<h3 id="_name"></h3>
							  	<p id="_store"></p>
							  	<p id="_closing_date"></p>
							  	
							  	<hr/>
							  	
							  	<table id="_auditTable" class="table">
							  		<thead>
							  		<tr>
							  			<th>Item Name</th>
							  			<th>Vendor</th>
							  			<th>Brand</th>
							  			<th>Category</th>
							  			<th>Photo</th>
							  			<th>Audit Result</th>
							  		</tr>
							  		</thead>
							  		<tbody id="_tbody">
							  		
							  		</tbody>
							  	</table>
							  
						    </div>
						    
						    
						    <div id="_xls">
							  
							  	
							  	<table id="_auditTable2" class="table">
							  		<thead>
							  		<tr>
							  			<td colspan="8">Name <div id="_name2"></div></td>
							  		</tr>
							  		<tr>
							  			<td colspan="8">Store <div id="_store2"></div>
							  		</tr>
							  		<tr>
							  			<td colspan="8">Date <div id="_closing_date2"></div></td>
							  		</tr>
							  		<tr>
							  			<td colspan="8">&nbsp;</td>
							  		</tr>
							  		
							  		<tr>
							  			<th>Id Item</th>
							  			<th>Item Name</th>
							  			<th>Vendor</th>
							  			<th>Brand</th>
							  			<th>Category</th>
							  			<th>Barcode</th>
							  			<th>Note</th>
							  			<th>Audit Result</th>
							  		</tr>
							  		</thead>
							  		<tbody id="_tbody2">
							  		
							  		</tbody>
							  	</table>
							  
						    </div>
							
						  </section>
						  
						  
						  
							
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables" width="100%">
			                  <thead>
								  <tr>
							        <th width="5%"><%=propLang.get("aud_id") %></th>
							        <th width="15%"><%=propLang.get("aud_name") %></th>
							        <th width="15%"><%=propLang.get("aud_cr_date") %></th>
							        <th width="15%"><%=propLang.get("aud_cl_date") %></th>
							        <th width="10%"><%=propLang.get("aud_type") %></th>
							        <th width="10%"><%=propLang.get("aud_store") %></th>
							        <th width="10%"><%=propLang.get("aud_state") %></th>
							        <th width="5%"></th>
							        <th width="5%"></th>
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
  
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <script type="text/javascript" src="../main/xlsx/shim.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/xlsx.full.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/Blob.js"></script>
  <script type="text/javascript" src="../main/xlsx/FileSaver.js"></script>
  
  
  <script>
  
  $('#_prt').hide();
  
  var username = "${idusr}";
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		loadTable(2);
		$('#_auditTable').hide();
		$('#_auditTable2').hide();
		
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
  
  
  function loadTable(_state) {
	
  	table = $('#example').dataTable( {
		"processing": true,
		 "ajax": {
		      "url": "../../ats/GetAudits",
		      "type": 'POST',
		      "dataSrc": 'listings',
		      "data": {idusr:username, state: _state}
		 },
		"columns": [
					  { data: 'id' },
				      { data: 'name' },
				      { data: 'creation_date' },
				      { data: 'closing_date' },
				      { data: 'type' },
				      { data: 'store' },
				      { data: 'state' },
					  {data:null},
					  {data:null},
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
                  "targets": [ 6 ],
                  "className": "text-center"
              },
              {
                  "targets": [ 7 ],
                  "className": "text-center"
              },
              {
                  "targets": [ 8 ],
                  "className": "text-center"
              },
              {
                  "targets": [ 9 ],
                  "className": "text-center"
              },
              {
                  "targets": [ 10 ],
                  "className": "text-center"
              }
              
          ], 
	      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	      "iDisplayLength": 50,
	      "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	    	    $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData["id"] + '</span>');
	    	    
	    	    if(aData["state"]==0) {
	    	    	$('td:eq(6)', nRow).html('<span class="label bg-success">Open</span>');
	    	    	$('td:eq(7)', nRow).html('<a href="audits-dett.htm?tipoForm=E&id='+aData["id"]+'" id="editItem" class="btn btn-default myEdit" data-toggle="ajaxModal"><i class="fa fa-pencil"></i></a>');
		    	    $('td:eq(8)', nRow).html('<a href="audit-items.htm?tipoForm=E&id='+aData["id"]+'" id="addAuditItems" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-tasks"></i></a>');
		    	    $('td:eq(10)', nRow).html('<a href="audits-dett.htm?tipoForm=D&id='+aData["id"]+'" id="deleteItem" class="btn btn-danger myDelete" data-toggle="ajaxModal"><i class="fa fa-times"></i></a>');
		    	    $('td:eq(9)', nRow).html('<a href="audits-dett.htm?tipoForm=C&id='+aData["id"]+'" id="closeAudit" class="btn btn-default myClose" data-toggle="ajaxModal"><i class="fa fa-lock" aria-hidden="true"></i></a>');
		    	    
	    	    }
	    	    else {
	    	    	$('td:eq(6)', nRow).html('<span class="label bg-danger">Close</span>');
	    	    	$('td:eq(7)', nRow).html('<a href="audits-dett.htm?tipoForm=E&id='+aData["id"]+'" id="editItem" class="btn btn-default myEdit" data-toggle="ajaxModal" disabled><i class="fa fa-pencil"></i></a>');
		    	    $('td:eq(8)', nRow).html('<a href="audit-items.htm?tipoForm=E&id='+aData["id"]+'" id="addAuditItems" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-tasks"></i></a>');
		    	    $('td:eq(10)', nRow).html('<a href="audits-dett.htm?tipoForm=D&id='+aData["id"]+'" id="deleteItem" class="btn btn-danger myDelete" data-toggle="ajaxModal"><i class="fa fa-times"></i></a>');
		    	    $('td:eq(9)', nRow).html('<a href="audits-dett.htm?tipoForm=C&id='+aData["id"]+'" id="closeAudit" class="btn btn-default myClose" data-toggle="ajaxModal" disabled><i class="fa fa-lock" aria-hidden="true"></i></a>');
		    	    
	    	    }
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
	  
	  
	  function excelExport() {
			
			var href = '../export/audit.jsp';
	  	
	      $('div.modal-backdrop ').remove();
	      $('#ajaxModal').remove();
	      $("div.modal").remove();
	      
	      showLoader2();
			
	      var data = '';
	      $.ajax({
		  	  url: href,
		  	  async: true,
		  	  dataType: 'html',
		  	  beforeSend: function () {
		  		  
		  	  },
		  	  success: function(html) {
		  	      
		  		  var $modal = $('<div class="modal fade" id="ajaxModal"><div class="modal-body"></div></div>');
		  	      $('body').append($modal);
		  	      $modal.modal();
		  	        
		  		  //alert(html);
		  		  $modal.append(html);
		  		  
		  		  hideLoader2();
		  		  
		  	  },
		  	  error: function (xhr, ajaxOptions, thrownError) {
		 	        alert(xhr.status);
		 	        alert(thrownError);
		 	        
		 	        hideLoader2();
		 	        
		 	      }
		  	});
			
		}
	  
	  function reload(state) {
		  
		  $('#example').DataTable().destroy();
			
		  loadTable(state);
	  }
	  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
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
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
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
              
            <%@ include file="../../inc2/menuApp12.jsp" %>
                   
            <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b>Documents Filters</b>
		                </span>
		                
		                <form id="frm-export" name="frm-export" method="post">
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                
		                
		                	<li class="list-group-item">
	                        	<label>Sites</label>
						        <select name="site" id="site" class="form-control ss" onchange="reloadTable()">
						        	<option value="">-</option>
						        	<c:forEach items="${sites}" var="sites" varStatus="status">
						        	<option value="${sites.idSite}">${sites.description}</option>
						        	</c:forEach>
						        </select>
	                        </li>
	                        
	                        <li class="list-group-item">
	                        	<label>Flow</label>
						        <select name="flow" id="flow" class="form-control ss" onchange="reloadTable();reloadStep(this.value)">
						        	<option value="">-</option>
						        	<c:forEach items="${flows}" var="flows" varStatus="status">
						        	<option value="${flows.idflow}">${flows.description}</option>
						        	</c:forEach>
						        </select>
	                        </li>
	                        
	                        <li class="list-group-item">
	                        	<label>Step</label>
						        <select name="step" id="step" class="form-control ss" onchange="reloadTable()">
						        	<option value="">-</option>
						        </select>
	                        </li>
	                        
	                        <li class="list-group-item">
	                        	<label>Doc Status</label>
	                        	<select name="status" id="status" class="form-control ss" onchange="reloadTable();">
						        	<option value="null"><%=propLang.get("open") %></option>
						        	<option value="X"><%=propLang.get("close") %></option>
						        	<option value="ALL"><%=propLang.get("all") %></option>
						        </select>
						    </li>
	                        
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtda") %></label>
						        <input class="datepicker-input2 form-control" name="from" id="from" 
						          	autocomplete="off" type="text" value="${oggi}" data-date-format="yyyy-MM-dd" > 
	                        </li>
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtal") %></label>
						        <input class="datepicker-input2 form-control" name="to" id="to" 
						          	autocomplete="off" type="text" value="${oggi}" data-date-format="yyyy-MM-dd" >  
	                        </li>
	                        
	                        
		                	
	                    </ul>
                        </form>
                      </div>
                    </section>
                    
                  </section>
                </aside>
                <!-- / side content -->
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder">
                      	
						<div class="row row-sm">
							<div class="col-xs-12">
								
								
								<h2 class="font-thin m-b">Documents</h2>
								
								<div id="msg"></div>
								
								
		                      	
		                      	<section class="panel panel-default" id="tb-data">
					              <header class="panel-heading font-bold">
					                <span id="table_id">Documents</span>
					              </header>
					              
					              <section class="panel wrapper">
					              	<div class="subnav">
										<ul class="nav nav-pills">
											<li><a href="documents-new.htm" data-toggle="ajaxModal">New Document</a></li>
										</ul>
									</div>
								  </section>
					            
					              
					              <div class="table-responsive">
		                      		<table id="test" class="table table-striped">
									<thead>
										<tr>
											<th style="width:5%">No.</th>
											<th style="width:5%">Status</th>
											<th style="width:5%">Doc Num</th>
											<th style="width:10%">Doc Ref</th>
											<th style="width:15%">Doc Date</th>
											<th style="width:20%">Step</th>
											<th style="width:20%">Site</th>
											<th style="width:20%">Type</th>
											<th style="width:5%">Expected</th>
											<th style="width:5%">Read</th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											
											
										</tr>
									</thead>
									<tbody>
										
									</tbody>
									<tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="16"><div id="total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					</tfoot>
									</table>
		                      	  </div>
		                        </section>
		                        
		                        
							</div>
						</div>
						
		            </section>
			      </section>
			    </section>
             </section>
       
      </section>
    </section>    
  </section>
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js?v=2"></script>
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
  <script type="text/javascript" src="../../js/jquery.printElement.min.js">
  
  <script type="text/javascript" src="../main/xlsx/shim.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/xlsx.full.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/Blob.js"></script>
  <script type="text/javascript" src="../main/xlsx/FileSaver.js"></script>
  
  <script>
  
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
	
	
	$('.datepicker-input2').datepicker({
	    format: 'yyyy-mm-dd',
	    orientation: 'top',
	    autoclose: true
	});  
	
	$("#from").on('changeDate', function(ev) {
		reloadTable();
	});
	$("#to").on("changeDate", function (e) {
		reloadTable();
	});
	
	function reloadTable() {
		
		loadTable();
		
	}
	
	function reloadStep(val) {
  		
  		// Call ws GetComuni
		$('#step').empty();
		
		// Call ws GetProvince
		$.getJSON('../ajax/getStep.jsp?idflow='+val, function (data) {
			
			// Legge json di risposta...
	    	var lista = data.step;
	    	var id = "";
	    	var description = "";
	    	$('#step').append($('<option>', {
    		    value: '',
    		    text: '-'
    		}));
	    	$.each(lista, function(index, lista) {
				id = lista.idstep;
	    		description = lista.description;
	    		
	    		$('#step').append($('<option>', {
	    		    value: id,
	    		    text: description
	    		}));
	    	});
	    	
	    	$('.ss').selectpicker('refresh');
	    	
		});
		
  	}
	
	
	function loadTable() {
		
		var idflow = $('#flow').val();
		var idstep = $('#step').val();
		var idsite = $('#site').val();
		var status = $('#status').val();
		var from = $('#from').val();
		var to = $('#to').val();
		
		
		var idcus = '<%=session.getAttribute("idcus")%>';
		
		//alert("../../servlet/ws/GetDocs?idflow="+idflow+"&idstep="+idstep+"&idsite="+idsite+"&idcus="+idcus);
		$('#test').DataTable( {
			"bDestroy": true,
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
		      //"sDom": "<'row'<'col-sm-12'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
		      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
		       "aaSorting": [],
		      "iDisplayLength": 10,
		      "aLengthMenu": [[10, 50, 100, 500, 1000], [10, 50, 100, 500, 1000]],
		      
	        "processing": true,
	        "serverSide": true,
	        
	        "ajax": {
	        	"url": "../../servlet/ws/GetDocs?idflow="+idflow+"&idstep="+idstep+"&idsite="+idsite+"&idcus="+idcus+"&status="+status+"&from="+from+"&to="+to,
	        	"type": "POST"
	        },
	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  $('td:eq(3)', nRow).html('<span class="label bg-light">' + aData[3] + '</span>');
	        	  $('td:eq(8)', nRow).html('<span class="pull-right">' + aData[8] + '</span>');
	        	  $('td:eq(9)', nRow).html('<span class="pull-right">' + aData[9] + '</span>');
	        	  $('td:eq(10)', nRow).html('<a href="../../rfid2/docs/documents-dett.htm?iddoc=' + aData[10] + '" class="btn btn-default"><i class="fa fa-search"></i></a>');
	        	  $('td:eq(11)', nRow).html('<a href="../../rfid2/docs/documents-create.htm?iddoc=' + aData[10] + '" class="btn btn-default"><i class="fa fa-edit"></i></a>');
	        	  
	        	  $('td:eq(12)', nRow).html('<a href="../../rfid2/docs/documents-print.htm?iddoc=' + aData[10] + '" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-print"></i></a>');
	        	  
	        	  var role = '<%=session.getAttribute("tipo").toString().trim() %>';
	        	  
	        	  if(role!='3') {
		        	  
	        	  	  $('td:eq(13)', nRow).html('<a href="#" onclick="delDoc(' + aData[10] + ')" class="btn btn-danger"><i class="fa fa-times"></i></a>');
	              
		        	  
		        	  if(aData[14]=='X') {
		        		  
		        		  if(aData[15]!='QC') {
		        				$('td:eq(14)', nRow).html('<a href="#" onclick="openDoc(' + aData[10] + ')" class="btn btn-success"><i class="fa fa-unlock"></i></a>');
		      	           }
		        		  else {
		        			  $('td:eq(14)', nRow).html('');
		        		  }
		        		  
		        		  //$('td:eq(14)', nRow).html('<a href="#" onclick="openDoc(' + aData[10] + ')" class="btn btn-success"><i class="fa fa-unlock"></i></a>');
		      	           
		        	  }
		        	  else {
		        		  $('td:eq(14)', nRow).html('');
		        	  }
		        	  
	        	  }
	        	  else {
	        		  
	        		  $('td:eq(13)', nRow).html('');
	        		  $('td:eq(14)', nRow).html('');
	        	  }
	        	  
	        	  //console.log(aData[16]);
	        	  
	        	  if(aData[16]=='32') {
	        		  if(aData[14]=='X') {
	        		  	$('td:eq(15)', nRow).html('<a href="#" onclick="callStored(' + aData[10] + ')" class="btn btn-primary"><i class="fa fa-file-text"></i></a>');  
	        		  }
	        		  else {
	        			  $('td:eq(15)', nRow).html('');
	        		  }
	        	  }
	        	  else {
	        		  $('td:eq(15)', nRow).html('');
	        	  }
	              return nRow;
	        },
	        "fnInitComplete": function (settings,json) {
	              $('#total').html('<b>Total Records: '+settings.fnRecordsTotal()+' records</b>');
	        }
	    } );
		
	}
	
	$('.ss').selectpicker({
	    liveSearch: true,
	    tickIcon: 'fa fa-check'
	 });
	
	function parsleyForm() {
	    $('#actionForm').parsley();
    }
    function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		});
    }
    
    function stampa() {
    	
    	var iddoc = $('#iddoc').val();
    	var idprinter = $('#idprinter').val();
    	var idtemplate = $('#idtemplate').val();
    	
    	var zplfile = $('#zplfile').prop('checked');
    	
    	var zplbrowser = $('#zplbrowser').prop('checked');
    	
    	var perc = $('#perc').val();
    	
    	
    	if(idprinter!=''&&idtemplate!='') {
    		//stampa
    		
    		$.ajax({
	  		      url: '../ajax/getZpl.jsp?iddoc='+iddoc+'&idprinter='+idprinter+'&idtemplate='+idtemplate+'&zplfile='+zplfile+'&perc='+perc,
	  		      type: 'POST',
	  		      dataType: 'html',
	  		      async: true,
	  		      success: function (data) {
	  					
	  		    	  if(zplfile||zplbrowser) {
	  		    		
	  		    		showLoader2();
	  		    		var text = $.trim(data);
	  		    	    var filename = "zpl.txt";
	  		    	    
	  		    	    if(zplfile) {
	  		    	    	download(filename, text);
	  		    	    }
	  		    	    
	  		    	    if(zplbrowser) {
	  		    	    	
	  		    	    	var myWindow=window.open('data:text/plain;charset=utf-8');
			    	    	myWindow.document.write(text);
	  		    	    	myWindow.print();
	  		    	    	
	  		    	    }
	  		    	    
	  		    	    $('div.modal-backdrop ').remove();
		  		        $('#ajaxModal').remove();
		  		        $("div.modal").remove();
		  		        
		  		          hideLoader2();
		  		        
	  		    	  }
	  		    	  else {
	  		    		alert("Stampa inviata alla stampante");
	  		    	  }
	  		    	  
	  		      },
	  		      error: function () {
	  		      	//alert('Errore! si e\' verificato un errore.');
	  		      }
	  		});
    		
    	}
    	else if(idprinter!=''&&idtemplate=='') {
    		alert("selezionare un template");
    	}
		else if(idprinter==''&&idtemplate!='') {
			alert("selezionare una stampante");
    	}
    	else {
    		alert("selezionare una stampante ed un template");
    	}
    	
    }
    
    /*
    function download(filename, text) {
        var element = document.createElement('a');
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
        element.setAttribute('download', filename);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    }
    */
    
    function download(filename, text) {
        var element = document.createElement('a');
        var blobdata = new Blob([text],{encoding:"UTF-8",type:"text/plain;charset=UTF-8"});
        var url = URL.createObjectURL(blobdata);
        element.setAttribute('href', url);
        //element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
        element.setAttribute('download', filename);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    }
    
    function delDoc(iddoc) {
    	
    	if(confirm('Confirm Delete Document?')) {
    		
    		$.ajax({
    		  	  url: "../../servlet/ws/DeleteDoc?iddoc="+iddoc,
    		  	  async: true,
    		  	  dataType: 'html',
    		  	  beforeSend: function () {
    		  		  showLoader2();
    		  	  },
    		  	  success: function(html) {
    		  	      
    		  		  //hideLoader2();
    		  		  
    		  		loadTable();
    		  		  
    		  	  },
    		  	  error: function (xhr, ajaxOptions, thrownError) {
    		 	        alert(xhr.status);
    		 	        alert(thrownError);
    		 	        
    		 	        hideLoader2();
    		 	        
    		 	      }
    		  	});
    		
    	}
    	else {
    		
    		console.log('NOT confirm...');
    		
    	}
    	
    }
    
	function callStored(iddoc) {
    	
    	if(confirm('Confirm Send Document to Gamma FTP?')) {
    		
    		$.ajax({
    		  	  url: "../../servlet/ws/GammaStored?iddoc="+iddoc,
    		  	  async: true,
    		  	  dataType: 'html',
    		  	  beforeSend: function () {
    		  		  showLoader2();
    		  	  },
    		  	  success: function(html) {
    		  	      
    		  		  hideLoader2();
    		  		  
    		  		  alert("Successfully Doc Generated!");
    		  		  
    		  		//loadTable();
    		  		  
    		  	  },
    		  	  error: function (xhr, ajaxOptions, thrownError) {
    		 	        alert(xhr.status);
    		 	        alert(thrownError);
    		 	        
    		 	        hideLoader2();
    		 	        
    		 	      }
    		  	});
    		
    	}
    	else {
    		
    		console.log('NOT confirm...');
    		
    	}
    	
    }
    
	function openDoc(iddoc) {
    	
    	if(confirm('Confirm Open Document?')) {
    		
    		$.ajax({
    		  	  url: "../../servlet/ws/OpenDoc?iddoc="+iddoc,
    		  	  async: true,
    		  	  dataType: 'html',
    		  	  beforeSend: function () {
    		  		  showLoader2();
    		  	  },
    		  	  success: function(html) {
    		  	      
    		  		  //hideLoader2();
    		  		  
    		  		loadTable();
    		  		  
    		  	  },
    		  	  error: function (xhr, ajaxOptions, thrownError) {
    		 	        alert(xhr.status);
    		 	        alert(thrownError);
    		 	        
    		 	        hideLoader2();
    		 	        
    		 	      }
    		  	});
    		
    	}
    	else {
    		
    		console.log('NOT confirm...');
    		
    	}
    	
    }
    
    
	
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  </body>
</html>
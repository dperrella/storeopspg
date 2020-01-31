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
                 <section class="scrollable padder">
                   	
					<div class="row row-sm">
						<div class="col-xs-12">
                      
                      	  
		                <h2 class="font-thin m-b">Stock Search</h2>
		                
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span id="table_id">Stock Search</span>
			              </header>
			                  
			              <section class="panel wrapper">
			              
			              <form action="stock-list.htm" name="frm" id="frm">
			                <label>Barcode</label>
					        <input type="text" class="form-control" name="sku" id="sku" value="${sku}" placeholder="Search Barcode"/>
                          </form>
                          <div id="msg">${msg}</div>
                       		
                        	  
			            
						  </section>
			              
			              <div class="table-responsive" >
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								    <th width="90%">EPC</th>
								    <th></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
	      					  
	      					  
							  <c:forEach items="${products}" var="products" varStatus="status">
							  	
							  	<tr>
							  	  <td>${products.epc}</td>
								  <td><a href="../docs/documents-row-dett.jsp?sku=${sku}&epc=${products.epc}&type=EPC" data-toggle="ajaxModal" class="btn btn-default"><i class="fa fa-info-circle"></i></a>
								  </td>
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					  
		  					</table>
		  					
		  				  </div>
		  				
		  				</section>
		  				
		  				
		  				
		  				</div>
		  			</div>
		  				  
			        </section> 
	              </section>
	            </section>
                
                <!-- side content -->
	            <aside class="aside bg-light dk" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="scrollable hover" style="margin-bottom:65px">
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
	                    
	                    <span class="bg-light dker list-group-item">
			                <b>Product Detail</b>
			            </span>
	              
			            <form id="frm-export" name="frm-export" method="post">
			              <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
			              
			              
			              	<li class="list-group-item">
			                	<label><b>Code</b></label><br/>
			                  	${code}
			                </li>
			                     
			                <li class="list-group-item">
			                  	<label><b>Description</b></label><br/>
			                  	${description}
			                </li>
			                
			                <li class="list-group-item">
			                  	<label><b>Sku</b></label><br/>
			                  	${sku}
			                </li>
			              </ul>
			            </form>
	             	  </div>
	                </section>
	                
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
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  
  <script type="text/javascript" src="../main/xlsx/shim.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/xlsx.full.min.js"></script>
  <script type="text/javascript" src="../main/xlsx/Blob.js"></script>
  <script type="text/javascript" src="../main/xlsx/FileSaver.js"></script>
  
  
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
	
	var table = $('#example').dataTable( {
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
  
  function reloadTable() {
	  
	  var type = $('#type').val();
	  var onlydiff = false;
	  if($('#onlydiff').prop('checked')) {
		  onlydiff=true;
	  }
	  
	  showLoader2();
	  location.href="documents-dett.htm?iddoc=${iddoc}&onlydiff="+onlydiff+"&type="+type;
	  
  }
  
  /*
  function excelExport() {
	  function doit(type, fn, dl) {
			var elt = document.getElementById('table_wrapper2');
			var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
			return dl ?
				XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
				XLSX.writeFile(wb, fn || ('doc.' + (type || 'xlsx')));
		}
		doit('xlsx');
  }
  */
  
  function excelExport() {
		
		var iddoc = '${iddoc}';
		
		var type = '${type}';
		var onlydiff = '${onlydiff}';
		var href = '../popup/docExport.jsp?iddoc='+iddoc+'&type='+type+'&onlydiff='+onlydiff;
  	
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
  
  
  $('#type').val('${type}');
  var onlydiff = '${onlydiff}';
  if(onlydiff=='true') {
	  $('#onlydiff').prop('checked', true);
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
  
  
  function stampaBarcode() {
	  
	  var iddoc = $('#iddoc').val();
  	  var idprinter = $('#idprinter').val();
  	  var idtemplate = $('#idtemplate').val();
  	  var sku = $('#sku').val();
  	  var qty = $('#qty').val();
	  
  	
  	if(idprinter!=''&&idtemplate!='') {
  		//stampa
  		
  		$.ajax({
  		      url: '../ajax/getZplDettSku.jsp?iddoc='+iddoc+'&idprinter='+idprinter+'&idtemplate='+idtemplate+'&sku='+sku+'&qty='+qty,
  		      type: 'POST',
  		      dataType: 'html',
  		      async: true,
  		      success: function (data) {
  					
  		    	  alert("Stampa inviata alla stampante");
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
  
  
  function stampa() {
	  
	  var iddoc = $('#iddoc').val();
  	  var idprinter = $('#idprinter').val();
  	  var idtemplate = $('#idtemplate').val();
  	
	  var datastring = table.$('input[type="checkbox"]').serialize();
	  
	  if(idprinter!=''&&idtemplate!='') {
  		//stampa
  		
  		$.ajax({
  		      url: '../ajax/getZplDett.jsp?iddoc='+iddoc+'&idprinter='+idprinter+'&idtemplate='+idtemplate,
  		      type: 'POST',
  		      dataType: 'html',
  		      data: datastring,
		      async: true,
  		      success: function (data) {
  					
  		    	  alert("Stampa inviata alla stampante");
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
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
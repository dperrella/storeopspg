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
  <link href="../../js/select2/css/select2.min.css" rel="stylesheet" />
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
                   
            <!-- side content -->
            <aside class="aside bg-light dk" id="sidebar">
              
              <section class="vbox animated fadeInUp">
              <!--
              	<form id="frm-create" name="frm-create" method="post">
              -->
                <section class="scrollable hover">
                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                    
                    <span class="bg-light dker list-group-item">
		                <b>Products Filters</b>
		            </span>
              
		      		  <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                   
		                <li class="list-group-item">
		                  	<label>Products</label>
					        <select name="code" id="code" class="form-control" onchange="editFields(this.value)" >
					        	<option value="">-</option>
					        </select>
		                </li>
		                <li class="list-group-item">
		                  	<label>Category</label>
					        <select name="category" id="category" class="form-control" onchange="editFields(this.value)">
					        	<option value="">-</option>
					        </select>
		                </li>
		                <li class="list-group-item">
		                  	<label>Season</label>
					        <select name="season" id="season" class="form-control " onchange="editFields(this.value)">
					        	<option value="">-</option>
					        </select>
		                </li>
		                <li class="list-group-item">
		                  	<label>Item Code</label>
					        <select name="itemcode" id="itemcode" class="form-control " onchange="editFields(this.value)">
					        	<option value="">-</option>
					        </select>
		                </li>
		                <li class="list-group-item">
		                  	<label>Color Code</label>
					        <select name="colourcode" id="colourcode" class="form-control " onchange="editFields(this.value)">
					        	<option value="">-</option>
					        </select>
		                </li>
		                <li class="list-group-item">
		                  	<label>Size</label>
					        <select name="size" id="size" class="form-control " onchange="editFields(this.value)">
					        	<option value="">-</option>
					        </select>
		                </li> 
		                
		                
		              </ul>
		            
             	  </div>
                </section>
                <footer class="footer footer-md bg-light dker">
	                <div class="input-group m-t-sm m-b-sm col-sm-12">
                    	<a href="#" onclick="sendValue()" class="btn btn-primary btn-lg btn-block">Insert</a>   
                    </div>
                </footer>
              	<!-- 
              	</form>
                  -->
              </section>
              
            </aside>
            
            <!-- / side content -->
            
            <section>
              <section class="vbox">
                 <section class="scrollable padder">
                   	
					<div class="row row-sm">
						<div class="col-xs-12">
                      
                      	  
		                <h2 class="font-thin m-b">${dssite} - ${dsflow} ${dsstep}</h2>
		                
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span id="table_id">Doc. No. ${iddoc} - ${doc_ref} - ${dstype}</span>
			                
			                <a href="#" onclick="excelExport()" class="btn btn-default btn-xs m-l-sm pull-right" >
	                            <i class="fa fa-table"></i>
	                            Export Data
	                        </a>
	                        
	                        
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="documents-list.htm"><%=propLang.get("torna") %></a></li>
									<li><a href="#" onclick="deleteSelected()"> Cancella Selezionati</a></li>
								</ul>
							</div>
						  </section>
			                
			              <div id="msg">${msg}</div>
			              
			              <div class="table-responsive" >
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								    <th>
								    	<div class="checkbox i-checks">
						                  <label>
						                    <input name="chk" type="checkbox" value="">
						                    <i></i>
						                  </label>
						                </div>
								    </th>
								    <th></th>
							        <th width="20%">SKU / EPC</th>
							        <th></th>
							        <th></th>
							        <th width="15%">Package ID</th>
							        <th width="15%">Code</th>
							        <th width="30%">Description</th>
							        <th></th>
							        <th width="5%">Expected</th>
							        
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
	      					  
	      					  
							  <c:forEach items="${doc}" var="doc" varStatus="status">
							  	
							  	<tr>
							  	  <td>
									  <div class="checkbox i-checks">
						                  <label>
						                    <input name="chkSku" type="checkbox" value="${doc.sku}">
						                    <i></i>
						                  </label>
						              </div>							  	  
							  	  </td>
								  <td>
								  <c:choose>
								    <c:when test="${doc.qty_theo!=doc.qty_read}">
								        <i class="fa fa-times text-danger"></i>
								    </c:when>    
								    <c:otherwise>
								        <i class="fa fa-check text-success"></i>
								    </c:otherwise>
								  </c:choose>
								  </td>
								  <td>${doc.sku}<br/><small><i>${doc.epc}</i></small></td>
								  <td><a href="documents-row-dett.jsp?sku=${doc.sku}&epc=${doc.epc}&type=${type}" data-toggle="ajaxModal" class="btn btn-default"><i class="fa fa-info-circle"></i></a></td>
								  <td><a href="#" onclick="BarsCode('${doc.sku}')" data-toggle="modal" data-target="#exampleModal" class="btn btn-default"><i class="fa fa-barcode"></i></a></td>
								  
								  <td>${doc.packageId}</td>
								  <td><span class="label bg-light">${doc.code}</span></td>
								  <td>${doc.description}</td>
								  <td><a href="documents-print-sku.htm?iddoc=${iddoc}&sku=${doc.sku}&qty=${doc.qty_theo}" data-toggle="ajaxModal" class="btn btn-default"><i class="fa fa-print"></i></a></td>
								  <td style="text-align:right">${doc.qty_theo}</td>
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					  <tfoot>
		  					  	<tr>
		  					  	  <td colspan="9">Totale</td>
		  					  	  <td style="text-align:right"><b>${totTheo}</b></td>
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
				        
                        <a href="documents-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-docs"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("doc_dsfun") %></span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="documents_type-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("doc_cerca") %>">
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
  
  <!-- Modal -->
  <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title" id="exampleModalLabel">BARCODE</h4>
	        
	      </div>
	      <div class="modal-body" align="center">
	
	        <img src="" id="barcodeimg" style="width:520px"/>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	       
	      </div>
	    </div>
	  </div>
  </div>
  <!-- Fine Modal -->
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
  
  <script src="../../js/select2/js/select2.min.js?v=2"></script>
  
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <script src="../../js/csvExport.js?v2"></script>
  
	
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
      "iDisplayLength": 50,
      "aLengthMenu": [[10, 50, 100, 500, 1000], [10, 50, 100, 500, 1000]],
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
  
  
  function stores2Export() {
		
		var iddoc = '${iddoc}';
		var doc_ref = '${doc_ref}';
		
		var href = '../popup/stores2Export.jsp?iddoc='+iddoc+"&doc_ref="+doc_ref;
	
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
  	  var zplfile = $('#zplfile').prop('checked');
  	  var zplbrowser = $('#zplbrowser').prop('checked');
	
	
  	 
  	if(idprinter!=''&&idtemplate!='') {
  		//stampa
  		
  		$.ajax({
  		      url: '../ajax/getZplDettSku.jsp?iddoc='+iddoc+'&idprinter='+idprinter+'&idtemplate='+idtemplate+'&sku='+sku+'&qty='+qty+'&zplfile='+zplfile,
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
  
  
  function stampa() {
	  
	  var iddoc = $('#iddoc').val();
  	  var idprinter = $('#idprinter').val();
  	  var idtemplate = $('#idtemplate').val();
  	  var zplfile = $('#zplfile').prop('checked');
  	  var zplbrowser = $('#zplbrowser').prop('checked');
  	  var perc = $('#perc').val();
  	
	  var datastring = table.$('input[type="checkbox"]').serialize();
	  
	  if(idprinter!=''&&idtemplate!='') {
  		//stampa
  		
  		$.ajax({
  		      url: '../ajax/getZplDett.jsp?iddoc='+iddoc+'&idprinter='+idprinter+'&idtemplate='+idtemplate+'&zplfile='+zplfile+'&perc='+perc,
  		      type: 'POST',
  		      dataType: 'html',
  		      data: datastring,
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
  
  function deleteSelected() {
	  
	  var iddoc = '${iddoc}';
  	  var datastring = table.$('input[type="checkbox"]').serialize();
	  
  		
	  $.ajax({
		      url: '../ajax/deleteSelected.jsp?iddoc='+iddoc,
		      type: 'POST',
		      dataType: 'html',
		      data: datastring,
      		  async: true,
		      success: function (data) {
		    	  location.href="documents-create.htm?iddoc=${iddoc}";
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
 		
  	
	  
  }
  
  
  function download(filename, text) {
      var element = document.createElement('a');
      element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
      element.setAttribute('download', filename);

      element.style.display = 'none';
      document.body.appendChild(element);

      element.click();

      document.body.removeChild(element);
  }
  
  
  function BarsCode(str){

	  document.getElementById("barcodeimg").src = "";

	  $.ajax({
	  	url: '../../GetBarcodesGenerate',
	  	async: false,
	  	data : {
	  		barcode : str
	  	},
	  	success : function(responseText) {
	  		document.getElementById("barcodeimg").src = responseText;				
	  	}
	  });
	    	
  }
  
  
  
  
  $('#code').select2({
		minimumInputLength: 3,
	    tags: [],
	    ajax: {
	        url: '../../servlet/ws/GetProducts2?id_customer=${idcus}',
	        dataType: 'json',
	        type: "GET",
	        quietMillis: 50,
	        data: function (param) {
	        	return {
	                term: param.term,
	                category: $('#category').val(),
	                season: $('#season').val(),
	                itemcode: $('#itemcode').val(),
	                colourcode: $('#colourcode').val(),
	                size: $('#size').val()
	                
	            };
	        },
	        processResults: function (data) {
	        	
	        	//alert(JSON.stringify(data));
	        	
	            return {
	                results: $.map(data.listings, function (item) {
	                    return {
	                        text: item.code+' '+item.name,
	                        id: item.code
	                    }
	                })
	            };
	            
	        }
	    }
	});
	
	$('#category').select2({
		minimumInputLength: 0,
	    tags: [],
	    ajax: {
	        url: '../../servlet/ws/GetCategory2?id_customer=${idcus}',
	        dataType: 'json',
	        type: "GET",
	        quietMillis: 50,
	        data: function (param) {
	        	return {
	                term: param.term,
	                code: $('#code').val(),
	                season: $('#season').val(),
	                itemcode: $('#itemcode').val(),
	                colourcode: $('#colourcode').val(),
	                size: $('#size').val()
	            };
	        },
	        processResults: function (data) {
	        	
	        	//alert(JSON.stringify(data));
	        	
	            return {
	                results: $.map(data.listings, function (item) {
	                    return {
	                        text: item.category,
	                        id: item.category
	                    }
	                })
	            };
	            
	        }
	    }
	});
	
	$('#season').select2({
		minimumInputLength: 0,
	    tags: [],
	    ajax: {
	        url: '../../servlet/ws/GetSeason2?id_customer=${idcus}',
	        dataType: 'json',
	        type: "GET",
	        quietMillis: 50,
	        data: function (param) {
	        	return {
	                term: param.term,
	                code: $('#code').val(),
	                season: $('#season').val(),
	                itemcode: $('#itemcode').val(),
	                colourcode: $('#colourcode').val(),
	                size: $('#size').val()
	            };
	        },
	        processResults: function (data) {
	        	
	        	//alert(JSON.stringify(data));
	        	
	            return {
	                results: $.map(data.listings, function (item) {
	                    return {
	                        text: item.season,
	                        id: item.season
	                    }
	                })
	            };
	            
	        }
	    }
	});
	
	$('#itemcode').select2({
		minimumInputLength: 0,
	    tags: [],
	    ajax: {
	        url: '../../servlet/ws/GetItemCode2?id_customer=${idcus}',
	        dataType: 'json',
	        type: "GET",
	        quietMillis: 50,
	        data: function (param) {
	        	return {
	                term: param.term,
	                code: $('#code').val(),
	                season: $('#season').val(),
	                category: $('#category').val(),
	                colourcode: $('#colourcode').val(),
	                size: $('#size').val()
	            };
	        },
	        processResults: function (data) {
	        	
	        	//alert(JSON.stringify(data));
	        	
	            return {
	                results: $.map(data.listings, function (item) {
	                    return {
	                        text: item.itemcode,
	                        id: item.itemcode
	                    }
	                })
	            };
	            
	        }
	    }
	});
	
	$('#colourcode').select2({
		minimumInputLength: 0,
	    tags: [],
	    ajax: {
	        url: '../../servlet/ws/GetColourCode2?id_customer=${idcus}',
	        dataType: 'json',
	        type: "GET",
	        quietMillis: 50,
	        data: function (param) {
	        	return {
	                term: param.term,
	                code: $('#code').val(),
	                season: $('#season').val(),
	                category: $('#category').val(),
	                itemcode: $('#itemcode').val(),
	                size: $('#size').val()
	            };
	        },
	        processResults: function (data) {
	        	
	        	//alert(JSON.stringify(data));
	        	
	            return {
	                results: $.map(data.listings, function (item) {
	                    return {
	                        text: item.colourcode,
	                        id: item.colourcode
	                    }
	                })
	            };
	            
	        }
	    }
	});
	
	
	$('#size').select2({
		minimumInputLength: 0,
	    tags: [],
	    ajax: {
	        url: '../../servlet/ws/GetSize2?id_customer=${idcus}',
	        dataType: 'json',
	        type: "GET",
	        quietMillis: 50,
	        data: function (param) {
	        	return {
	                term: param.term,
	                code: $('#code').val(),
	                season: $('#season').val(),
	                category: $('#category').val(),
	                itemcode: $('#itemcode').val(),
	                colourcode: $('#colourcode').val()
	            };
	        },
	        processResults: function (data) {
	        	
	        	//alert(JSON.stringify(data));
	        	
	            return {
	                results: $.map(data.listings, function (item) {
	                    return {
	                        text: item.size,
	                        id: item.size
	                    }
	                })
	            };
	            
	        }
	    }
	});
	
	var code = $('#code').val();
	var category = $('#category').val();
	var season = $('#season').val();
	var itemcode = $('#itemcode').val();
	var colourcode = $('#colourcode').val();
	var size = $('#size').val();
	
  
  
  
  
  
  
  
  
  function sendValue() {
	  
	  var category = $('#category').val();
	  var season = $('#season').val();
	  var itemcode = $('#itemcode').val();
	  var colourcode = $('#colourcode').val();
	  var size = $('#size').val();
	  var code = $('#code').val();
	  
	  
	  $.ajax({
	  	  url: "../../servlet/ws/InsertDocRow?id_customer=${idcus}&iddoc=${iddoc}&category="+category+"&season="+season+"&itemcode="+itemcode+"&colourcode="+colourcode+"&size="+size+"&code="+code,
	  	  async: true,
	  	  dataType: 'html',
	  	  beforeSend: function () {
	  		  showLoader2();
	  	  },
	  	  success: function(html) {
	  	      
	  		  hideLoader2();
	  		  
	  		location.href="documents-create.htm?iddoc=${iddoc}";
	  		  
	  	  },
	  	  error: function (xhr, ajaxOptions, thrownError) {
	 	        alert(xhr.status);
	 	        alert(thrownError);
	 	        
	 	        hideLoader2();
	 	        
	 	      }
	  	});
		
	}
	  
	  

  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
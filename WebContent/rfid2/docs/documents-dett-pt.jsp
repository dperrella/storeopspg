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
                   
            <!-- side content -->
            <aside class="aside bg-light dk" id="sidebar">
              <section class="vbox animated fadeInUp">
                <section class="scrollable hover" style="margin-bottom:65px">
                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                    
                    <span class="bg-light dker list-group-item">
		                <b>Documents Filters</b>
		            </span>
              
		            <form id="frm-export" name="frm-export" method="post">
		              <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		              
		              
		              	<li class="list-group-item">
		                	<label class="i-checks" style="margin-left:10px">
					            <input type="checkbox" name="onlydiff" id="onlydiff" value="S" onclick="reloadTable()" disabled><i></i> Only Differences
					        </label>
		                </li>
		                     
		                <li class="list-group-item">
		                  	<label>View Type</label>
					        <select name="type" id="type" class="form-control ss" onchange="reloadTable()">
					        	<option value="SKU">SKU</option>
					        	<option value="EPC">EPC</option>
					        	<option value="PT">PIANO TAGLIE</option>
					        </select>
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
									<li><a href="documents-print.htm?iddoc=${iddoc}" data-toggle="ajaxModal"><%=propLang.get("btnStampa") %> Selezionati</a></li>
									<c:if test = "${expected == 'N'}">
									<li><a href="#" onclick="diffGiacenze()">Differenze Inventario</a></li>
									</c:if>
									<li><a href="#" onclick="stores2Export()">Stores2 Export Data</a></li>
									<li><a href="#" onclick="export2()">Export con Piano Taglie</a></li>
									
									<!-- 
									<c:if test = "${isInventory == 'Y'}">
									<li><a href="#" onclick="closeDoc(1)">Rettifica e Chiudi</a></li>
									<li><a href="#" onclick="closeDoc(2)">Rettifica e Crea nuovo Inv. per differenze</a></li>
									</c:if>
									-->
									
									<c:if test = "${dsstep == 'Inventario'}">
									<li><a href="#" onclick="closeDoc(1)">Rettifica e Chiudi</a></li>
									<li><a href="#" onclick="closeDoc(2)">Rettifica e Crea nuovo Inv. per differenze</a></li>
									</c:if>
								</ul>
							</div>
						  </section>
			                
			              <div id="msg">${msg}</div>
			              
			              <div class="table-responsive" >
			                
			                ${table}
			                
		  				  </div>
		  				
		  				</section>
		  				
		  				<div class="row m-t-md m-b-md" align="center">
		                    <div class="col-sm-6">
		                      <div class="bg-primary lter wrapper-md r">
		                        <a href="#">
		                          <span class="h4 block"><i class="icon-docs i-lg"></i> Total Theoretical</span>
				                  <span class="text-muted font-thin" style="text-align:center;font-size:100px">${totTheo}</span>
		                        </a>
		                      </div>
		                    </div>
		                    <div class="col-sm-6">
		                      <div class="bg-black wrapper-md r">
		                        <a href="#">
		                          <span class="h4 block"><i class="icon-feed i-lg"></i> Total Read</span>
				                  <span class="text-muted font-thin" style="text-align:center;font-size:100px">${totRead}</span>
		                       </a>
		                      </div>
		                    </div>
		                  </div>
		  				
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
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
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
	  
	  if(type=='PT') {
		  alert('todo...');
	  }
	  else {
	  	location.href="documents-dett.htm?iddoc=${iddoc}&onlydiff="+onlydiff+"&type="+type;
	  }
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
  
  
  
  function diffGiacenze() {
		
		var iddoc = '${iddoc}';
		var doc_ref = '${doc_ref}';
		
		var href = '../popup/diffGiacenze.jsp?iddoc='+iddoc+"&doc_ref="+doc_ref;
	
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
  
  
  function closeDoc(closeType) {
	  
	  var iddoc = '${iddoc}';
	  var datastring = table.$('input[type="checkbox"]').serialize();
	  
	  showLoader2();
	  
	  $.ajax({
		      url: '../../servlet/ws/CloseDoc?iddoc='+iddoc+'&closeType='+closeType,
		      type: 'POST',
		      dataType: 'html',
		      data: datastring,
		      async: true,
		      success: function (data) {
					
		    	  location.href="documents-list.htm";
		    	
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
  	}
  
  function export2() {
		
		var iddoc = '${iddoc}';
		
		var href = '../popup/docExportNew2.jsp?iddoc='+iddoc;
	
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
	
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
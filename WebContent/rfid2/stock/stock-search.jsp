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
		                
		                
		                
		                
		                
		                
		                <li class="list-group-item">
		                  	<label>Location</label>
					        <select name="loc" id="loc" class="form-control " onchange="editFields(this.value)">
					        	<option value="">-</option>
					        	<c:forEach items="${location}" var="location" varStatus="status">
					        	<option value="${location.idLocation}">${location.idLocation}. ${location.description}</option>
					        	</c:forEach>
					        	
					        </select>
		                </li> 
		                
		                
		                
		                
		                
		              </ul>
		            
             	  </div>
                </section>
                
                <footer class="footer footer-md bg-light dker">
	                <div class="input-group m-t-sm m-b-sm col-sm-12">
                    	<a href="#" onclick="resetFilters()" class="btn btn-primary btn-lg btn-block">Reset Filters</a>   
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
                      
                      	  
		                <h2 class="font-thin m-b">${dssite}</h2>
		                
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span id="table_id">${dssite}</span>
			                
			                
			                <a href="#" onclick="excelExport()" class="btn btn-default btn-xs m-l-sm pull-right" >
	                            <i class="fa fa-table"></i>
	                            Export Data
	                        </a>
	                        
	                        
			              </header>
			              
			              <div class="table-responsive">
                      		<table id="test" class="table table-striped">
							<thead>
								<tr>
									<th style="width:5%">No.</th>
									<th style="width:15%">Code</th>
									<th style="width:20%">Name</th>
									<th style="width:20%">Color</th>
									<th style="width:10%">Size</th>
									<th style="width:20%">Location</th>
									<th style="width:10%">Qty</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
							<tfoot>
	      					  	  <tr>
	      					  	  	<td colspan="7"><div id="total" style="margin-left:5px"></div></td>
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
				        
                        <a href="sites-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-building-o"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("sit_dsfun") %></span>
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
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("sit_cerca") %>">
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
	var loc = $('#loc').val();
	
	function editFields(val) {
		
		code = $('#code').val();
		category = $('#category').val();
		season = $('#season').val();
		itemcode = $('#itemcode').val();
		colourcode = $('#colourcode').val();
		size = $('#size').val();
		loc = $('#loc').val();
		
		loadTable();
		
	}
	
	var tb;
	
	function loadTable() {
		
		var idsite = '${idsite}';
		var idcus = '${idcus}';
		nr = 0;
		
		//alert("../../servlet/ws/GetStock?idsite="+idsite+"&code="+code+"&category="+category+"&season="+season+"&itemcode="+itemcode+"&colourcode="+colourcode+"&size="+size);
		
		
		//alert("../../servlet/ws/GetDocs?idflow="+idflow+"&idstep="+idstep+"&idsite="+idsite+"&idcus="+idcus);
		tb = $('#test').DataTable( {
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
		      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
		       "aaSorting": [],
		      "iDisplayLength": 50,
		      "aLengthMenu": [[10, 50, 100, 500, 1000], [10, 50, 100, 500, 1000]],
		    "processing": true,
	        "serverSide": true,
	        "ajax": {
	        	"url": "../../servlet/ws/GetStock?idsite="+idsite+"&code="+code+"&category="+category+"&season="+season+"&itemcode="+itemcode+"&colourcode="+colourcode+"&size="+size+"&location="+loc,
	        	"type": "POST"
	        },
	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  nr++;
	        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
	        	  
	        	  $('#ppp').html(nr+' of ');
	        	  return nRow;
	        },
	        "fnInitComplete": function (settings,json) {
	        	console.log(JSON.stringify(json));
	              $('#total').html('<b>Total Records: <span id="ppp"></span>'+settings.fnRecordsTotal()+' records</b>');
	        }
	    } );
		
		
		  tb.on( 'search.dt', function () {
	          nr=0;
	      } );
	    
	}
	
	
  
  
    function excelExport() {
		
    	var idsite = '${idsite}';
		var idcus = '${idcus}';
		
	  var href = "../popup/stockExport.jsp?idsite="+idsite+"&code="+code+"&category="+category+"&season="+season+"&itemcode="+itemcode+"&colourcode="+colourcode+"&size="+size+"&location="+loc;
  	
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
  
  
  function resetFilters() {
	  $('#code').val(null).trigger('change');
	  $('#category').val(null).trigger('change');
	  $('#season').val(null).trigger('change');
	  $('#itemcode').val(null).trigger('change');
	  $('#colourcode').val(null).trigger('change');
	  $('#size').val(null).trigger('change');
  }
  
  
  
  
  
  
  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
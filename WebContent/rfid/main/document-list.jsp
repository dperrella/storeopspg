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
ReadProperties propss = new ReadProperties("connection.properties");
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
  </style>
</head>

<body onload="GetConfig()">
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
              
            <%@ include file="../../inc2/menuApp4.jsp" %>
                   
            <section>
              <section class="vbox">
              
                <section class="scrollable padder-lg m-t" id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <!-- <h2 class="font-thin m-b"><%//=propLang.get("doc_dsfun") %></h2> -->
		                
		                
		                <section class="panel panel-default">
			            <!-- 
			              <header class="panel-heading font-bold">
			                <%//=propLang.get("doc_dsfun") %>
			              </header>
			             --> 
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="index.htm"><%=propLang.get("torna") %></a></li>
									<li id="crea_doc"><a data-toggle="modal" data-target="#myModal" style="cursor: pointer;"><%=propLang.get("crea_doc") %></a></li>
									<li style="margin-right: 0px; float: right;">
									<label class="switch" >
			                          <input type="checkbox" id="status_chk" value="ALL" onclick="reloadDocs()">
			                          <span></span>
			                         
			                        </label>
									</li>
									<li style="margin-top:5px;margin-right: 5px; float: right;">
									ALL Docs
									</li>
									
								</ul>
								
							</div>
						  </section>
						  

			              
			              <div id="msg">${msg}</div>
			              
			              
			              <form action="document-dett.htm" method="post">
			              
			              <input type="hidden" name="status" id="status" value=""/>
			              
			              <input type="hidden" name="scanner" id="scanner" value=""/>
			              <input type="hidden" name="view_type" id="view_type" value=""/>
			              <input type="hidden" name="iddoc" id="iddoc" value=""/>
			              <input type="hidden" name="iddevice" id="iddevice" value=""/>
			              <input type="hidden" name="idflow" id="idflow" value=""/>
			              <input type="hidden" name="idstep" id="idstep" value=""/>
			              <input type="hidden" name="docnum" id="docnum" value=""/>
			              <input type="hidden" name="group" id="group" value=""/>
			              <input type="hidden" name="docdata" id="docdata" value=""/>
			              <input type="hidden" name="docref_destination" id="docref_destination" value=""/>
			              <input type="hidden" name="doc_exchange" id="doc_exchange" value=""/>
			              <input type="hidden" name="docref_origin" id="docref_origin" value=""/>
			              </form>
			              
			             <div class="table-responsive" id="details_list">
			               <table class="table table-striped m-b-none" id="details"></table>
						</div>			              
<!-- 			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
							        <th width="10%"><%=propLang.get("flo_idflow") %></th>
							        <th width="70%"><%=propLang.get("flo_descrizione") %></th>
							        <th width="10%"><%=propLang.get("flo_enabled") %></th>
							        <th></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${flow}" var="flow" varStatus="status">
								<tr>
								  <td><span class="label bg-light">${flow.idflow}</span></td>
								  <td>${flow.description}</td>
								  <td>${flow.enabled}</td>
			                      <td><div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu">
					                    <li><a href="flow-dett.htm?tipoForm=V&idflow=${flow.idflow}" data-toggle="ajaxModal"><i class="fa fa-search"></i> <%=propLang.get("dett") %></a></li>
					                    <li><a href="flow-dett.htm?tipoForm=M&idflow=${flow.idflow}" data-toggle="ajaxModal"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li>
					                    <li><a href="flow-dett.htm?tipoForm=E&idflow=${flow.idflow}" data-toggle="ajaxModal"><i class="fa fa-times"></i> <%=propLang.get("eli") %></a></li>
					                  	<li class="divider"></li>
					                  	<li><a href="flow-step.htm?tipoForm=V&idflow=${flow.idflow}&flow_description=${flow.description}"><i class="fa fa-step-forward"></i> Flow Steps</a></li>
					                  </ul>
					                </div></td>
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
		  				  </div>
-->		  				
		  				</section>
		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>
                
                <!-- side content 
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="flow-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-gears "></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("flo_dsfun") %></span>
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
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("flo_cerca") %>">
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
        <h5 class="modal-title" id="exampleModalLabel">BARCODE</h5>
        
      </div>
      <div class="modal-body" align="center">

        <img src="" id="barcodeimg" style="width:520px"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       
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
  
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
    
  <script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet"> 
  
  <!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
      <div class="modal-body">
        <table width="100%">
        	<tr>
        		<td width="25%">Origin</td>
        		<td width="75%"><select id="idorigin" name="idorigin" class="form-control"></select></td>
        	</tr>
        	<tr>
        		<td width="25%">Destination</td>
        		<td width="75%"><select id="iddestination" name="iddestination" class="form-control"></select></td>
        	</tr>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-success" onclick="CrateDoc()" data-dismiss="modal">Crea</button>
      </div>
    </div>

  </div>
</div>
  <!-- fine -->

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
	
var scanner;
var view_type;

function GetConfig(){
	
	  var opt = "";
	  var opt2 = "";
	
	  idflow = "<%=request.getParameter("flow")%>";
	  idstep = "<%=request.getParameter("step")%>";	
	  
	  var urls = "../../rfid/ws/GetStepConfig?idflow="+idflow+"&idstep="+idstep;
	  
	  $.getJSON(urls, function (data) {
		  /**/
		  var config = data.config_during;
		  var creadoc = config.create_doc;
		  view_type = config.view_type;
		  scanner = config.scanner;
		  
		  $('#scanner').val(scanner);
		  $('#view_type').val(view_type);
		  
		  
		  //alert(scanner);
		  /**/
		  var origin = data.origin_warehouse;
		  var destination = data.destination_warehouse;
		  
		  //creadoc = true;

		  if (creadoc=="true" || creadoc==true || creadoc=="t"){
			  document.getElementById("crea_doc").style.display="inline";
			  /**/
			  var lista = JSON.parse(origin);
			  lista = lista.listings;
				 	$.each(lista, function(index, lista) {
				 		val = lista.id_warehouse;
				 		des = lista.description;
						opt += "<option value='"+val+"'>"+des+"</option>";
					});
				 	
				 	
				 	document.getElementById("idorigin").innerHTML = opt;
				 	
			  var lista2 = JSON.parse(destination);
			  lista2 = lista2.listings;
				 	$.each(lista2, function(index, lista2) {
				 		val2 = lista2.id_warehouse;
				 		des2 = lista2.description;
						opt2 += "<option value='"+val2+"'>"+des2+"</option>";	
					});				 	
			  /**/
				 	document.getElementById("iddestination").innerHTML = opt2;
			  
		  }else{
			  document.getElementById("crea_doc").style.display="none";
		  }	
		  
		  
		  
		  GetDocuments(view_type, scanner);
		  

	  });

	
	  
	  
	  
}

function reloadDocs() {
	
	if($("#status_chk").is(':checked'))
	    $("#status").val("ALL");  // checked
	else
		$("#status").val("");  // unchecked
	
	GetDocuments(view_type, scanner);
}

  
  function GetDocuments(par_view_type, par_scanner){

	  idflow = "<%=request.getParameter("flow")%>";
	  idstep = "<%=request.getParameter("step")%>";
	  iddevice = "<%=request.getParameter("iddevice")%>";
	  
	  var status = $('#status').val();
	  
		/**/
			//document.getElementById("details_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
			
			var urls = "../../rfid/ws/ReadDoc?idflow="+idflow+"&idstep="+idstep+"&status="+status;

			$.getJSON(urls, function (data) {
				
				
				var lista = data.listings;
				/**/
				  	var docnumber = "";
				  	var origdescr = "";
				  	var destdescr = "";
				  	var idflow = "";
				  	var idstep = "";
				  	var docdata = "";
				  	var typedescr = "";
				/**/
					var id_doc_prec = "";
					var destination_warehouse = "";
					var docref_destination = "";
					var doc_exchange = "";
					var update_diary = "";
					var iddoc = "";
					var docref_origin = "";
					var doctype = "";
					var extra = "";
					var destination_location = "";
					var doc_note = "";
					var origin_warehouse = "";
					var status = "";
				  	
					var qty_theo = "";
					var qty_read = "";
					
				  	var testdata_ini = "";
				  	var testdata_fin = "";
				  	
				  	var testdata_ini = "[";
			 		var testdata_fin = "]";
			 		var testdata_row = "";
			 		
			 		
			 		
				 	$.each(lista, function(index, lista) {
					  	docnumber = lista.docnum;
					  	origdescr = lista.origin_description;
					  	destdescr = lista.destination_description;
					  	idflow = lista.id_flow;
					  	idstep = lista.id_step;
					  	docdata = lista.docdate;
					  	typedescr = lista.type_description;
					  	/**/
						id_doc_prec = lista.id_doc_prec;
						destination_warehouse = lista.destination_warehouse;
						docref_destination = lista.docref_destination;
						doc_exchange = lista.doc_exchange;
						
						update_diary = lista.update_diary;
						iddoc = lista.iddoc;
						docref_origin = lista.docref_origin;
						doctype = lista.doctype;
						extra = lista.extra;
						destination_location = lista.destination_location;
						doc_note = lista.doc_note;
						origin_warehouse = lista.origin_warehouse;
						status = lista.status;	
						
						qty_theo = lista.tot_theo;	
						qty_read = lista.tot_read;	
						group = "B";
					  	/**/

					  	docdata = convertDate(docdata);

					  	doc_exchange = (doc_exchange == null ? '' : doc_exchange);
					  	
					  	docref_origin = (docref_origin == null ? '' : docref_origin);
					  	
					    emptys = "<div class='btn-group'> ";
					    emptys += "<a href='./document-dett.htm?iddoc="+iddoc+"&iddevice="+iddevice+"&idflow="+idflow+"&idstep="+idstep+"&docnum="+docnumber+"&group="+group+"&docdata="+docdata+"&docref_destination="+doc_exchange+"&docref_origin="+docref_origin+"&scanner="+par_scanner+"&view_type="+par_view_type+"' class='btn btn-default btn-sm'><i class='fa fa-external-link'></i></a> ";
					    //emptys += "<a onclick=test('"+iddoc+"', '"+idflow+"', '"+idstep+"', '"+docnumber+"', '"+group+"', '"+docdata+"', '"+docref_destination+"', '"+docref_origin+"') class='btn btn-default btn-sm'><i class='fa fa-external-link'></i></a> ";
					    if(doc_exchange != ""){
					    	emptys += "<button class='btn btn-default btn-sm' onclick=BarsCode('"+doc_exchange+"') data-toggle='modal' data-target='#exampleModal'><i class='fa fa-barcode'></i></button> ";	
					    }
					    emptys += "</div>";
		 		 		
		 		 		/*
				 		emptys = "<div class='btn-group'> "+
				         "<button class='btn btn-default dropdown-toggle btn-sm' data-toggle='dropdown'> Action <span class='caret'></span></button> "+
				   		 "<ul class='dropdown-menu' style='margin-left: -100px;'> "+
				   		 "<li><a href='./document-dett.htm?iddoc="+iddoc+"&docnum="+docnumber+"'><i class='fa fa-external-link'></i> View</a></li> "+
				   		 "<li><a onclick=BarsCode('"+docref_destination+"') style='cursor: pointer;'><i class='fa fa-barcode'></i> Barcode</a></li> "+
				   		 "</ul> "+
				 		 "</div>";
				 		 */
						
				 		if (testdata_row == ""){
				 			testdata_row = '{"docnumber":"'+docnumber+'","origdescr":"'+origdescr+'","destdescr":"'+destdescr+'","idflow":"'+idflow+'","idstep":"'+idstep+'","docdata":"'+docdata+'","typedescr":"'+typedescr+'","qty_theo":"'+qty_theo+'","qty_read":"'+qty_read+'","emptys":"'+emptys+'"}';
				 		}else{
				 			testdata_row = testdata_row + ',{"docnumber":"'+docnumber+'","origdescr":"'+origdescr+'","destdescr":"'+destdescr+'","idflow":"'+idflow+'","idstep":"'+idstep+'","docdata":"'+docdata+'","typedescr":"'+typedescr+'","qty_theo":"'+qty_theo+'","qty_read":"'+qty_read+'","emptys":"'+emptys+'"}';
				 		}
						
					});
				 	
				 	
				 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
				 	document.getElementById("details_list").innerHTML = "<table id='details' class='table table-striped m-b-none' width='100%'></table>";
				 	
			 		var head = "<thead><th width='8%'>Doc Num</th><th width='15%'>Origin</th><th width='15%'>Destination</th><th width='8%'>flow</th><th width='8%'>Step</th><th width='10%'>Data Doc.</th><th width='10%'>Type Description</th><th width='5%'>Theo</th><th width='5%'>Read</th><th>&nbsp;</th></thead>";
			 		
			 		document.getElementById("details").innerHTML = head;

			 		$('#details').dataTable({
			 		      "language": {
			 		          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
			 		          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
			 		          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_ <%=" - " + propLang.get("nRec") %> _MAX_ <%=" : "+propLang.get("nRecVis")+"("%> _START_ <%=" - "%> _END_ <%=")"%>",
			 		          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
			 		          "infoFiltered": "(Filtrati da _MAX_ records totali)",
			 		          "decimal": ",",
			 		          "thousands": ".",
			 		          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
			 		      },
			 			"bProcessing": true,
			 			"aaData": testdata,
			 		    "aoColumns": [{
			 		        "mDataProp": "docnumber"
			 		    }, {
			 		        "mDataProp": "origdescr"
			 		    }, {
			 		        "mDataProp": "destdescr"
			 		    }, {
			 		        "mDataProp": "idflow"
			 		    }, {
			 		        "mDataProp": "idstep"
			 		    }, {
			 		        "mDataProp": "docdata"
			 		    }, {
			 		        "mDataProp": "typedescr"
			 		    }, {
			 		        "mDataProp": "qty_theo"
			 		    }, {
			 		        "mDataProp": "qty_read"
			 		    }, {
			 		        "mDataProp": "emptys"
			 		    }],	 		    
			 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
			 		    "bPaginate":true,
			 		   	"bDestroy": true
			 		});
							 	

				});
	  
/**/	  
  }
  
  function test(iddoc, idflow, idstep, docnum, group, docdate, docref_destination, docref_origin) {
	  
	  alert(iddoc);
	  //location.href(\'./document-dett.htm?iddoc="+iddoc+"&iddevice="+iddevice+"&idflow="+idflow+"&idstep="+idstep+"&docnum="+docnumber+"&group="+group+"&docdata="+docdata+"&docref_destination="+docref_destination+"&docref_origin="+docref_origin+"&scanner="+$('#scanner').val()+"&view_type="+$('#view_type').val()+"\')
	  
  }
  
  //var rootsimg = "file:///c://sct/Stampe/";
  //alert(rootsimg);
  function BarsCode(str){
	  
	  //alert(str)
	  
  	$.ajax({
		url: '../../GetBarcodesGenerate',
		async: false,
		data : {
			barcode : str
		},
		success : function(responseText) {

			//alert(responseText)
				document.getElementById("barcodeimg").src = responseText;				
			
		}
	
	});
  }
  
  function CrateDoc(){
	  
	  idflow = "<%=request.getParameter("flow")%>";
	  idstep = "<%=request.getParameter("step")%>";
	  iddevice = "<%=request.getParameter("iddevice")%>";
	  
	  	$.ajax({
			url: '../../rfid/ws/WriteDoc',
			async: false,
			data : {
				idstep : idstep,
				idflow : idflow,
				origin_warehouse : $("#idorigin").val(),
				destination_warehouse : $("#iddestination").val()	
			},
			success : function(responseText) {

				alertify.success("Documento Creato con successo");
				myVar = setTimeout(function(){ GetDocuments(view_type, scanner); }, 2000);
				
			}
		
		});
	  
  }
  
  function convertDate(inputFormat) {
	  function pad(s) { return (s < 10) ? '0' + s : s; }
	  var d = new Date(inputFormat);
	  return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('-');
	}  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
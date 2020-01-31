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
/*	
	tr:nth-child(even) {
    	background-color: #ccc
	}
*/	
  </style>
</head>
<body onload="GetDocuments()">
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
            
<!-- Aside Sinistra                 
	            <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin"><%//=propLang.get("doc_colli") %></p>
                        </div>
                        <div id="aree">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto" id="ulpackage">
                        	
                        	<c:forEach items="" var="fun" varStatus="status">
	                          <li class="list-group-item">
			                    <div class="pull-left m-l-xs m-r-sm">
									
									<input type="hidden" name="nrRiga" value="" />
									<input type="hidden" name="cdfun" value="" />
									<div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" name="post" id="post" data-required="true">
			                            <i></i>
			                          </label>
			                        </div>

		                        </div> 
		                        <div class="clear">
		                        	<div><a href="#"></a></div> 
		                       		<small class="text-muted"></small> 
		                        </div> 
		                        
			                  </li>
	                        </c:forEach>
	                          
	                        </ul>
                        </div>
                    </section>
                  </section>
                </section>                
<!--  -->                 
                   
            <section>
              <section class="vbox">
              
                <section class="scrollable m-t padder-lg" id="bjax-target">

					<div class=" row row-sm">

		                <!-- <h2 class="font-thin m-b"><%//=propLang.get("doc_dsfun") %></h2>-->
		                

		                <section class="panel panel-default">
<!-- 
			              <header class="panel-heading font-bold">
			                <%//=propLang.get("doc_dsfun") %>
			              </header>
-->

			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills" style="text-align: center;" align="center">
									<li><a href="document-list.htm?flow=<%=request.getParameter("idflow")%>&step=<%=request.getParameter("idstep")%>&iddevice=<%=request.getParameter("iddevice")%>" class="btn btn-default"><%=propLang.get("torna") %></a></li>
									<li><a href="#" onclick="callRead()" class="btn btn-default">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=propLang.get("read_s") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
									<li style="margin-right: 16px; float: right;"><input type="checkbox" id="barcodeepcchk" data-toggle="toggle" checked data-on="BARCODE" data-off="EPC" onchange="GetDocuments()" data-onstyle="primary" data-offstyle="primary"></li>
								</ul>
								<!-- 
							    <div style="margin-left: 16px; float: right;">
									<input type="checkbox" id="barcodeepcchk" data-toggle="toggle" checked data-on="BARCODE" data-off="EPC" onchange="GetDocuments()" data-onstyle="primary" data-offstyle="primary">								
								</div>
								-->       
							</div>
						  </section>
			              
			              
			              <div>
			              	<table width="100%">
			              	<!-- 
								<tr>
									<td width="25%" colspan="4"><span style="margin-left: 16px;">Step: <%=request.getParameter("idstep") %></span></td>
									<td width="25%">Data Doc: <%=request.getParameter("docdata") %></td>
									<td width="25%">Origin: <%=request.getParameter("docref_origin") %></td>
									<td width="25%">Destination: <%=request.getParameter("docref_destination") %></td>
								</tr>
							-->	
								<tr>
									<td>
									<span style="margin-left: 16px; font-weight: bold; font-size:20px;">Step: <%=request.getParameter("idstep") %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Data Documento: <%=request.getParameter("docdata") %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Origine: <%=request.getParameter("docref_origin") %> <!--  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Destinazione: <%=request.getParameter("docref_destination") %>--></span><span style="float: right; margin-right: 16px; font-weight: bold; font-size:20px;"">Numero Documento: <%=request.getParameter("docnum") %></span>
									</td>
								</tr>
								<!-- 			         
								<tr>
									<td><input type="text" id="steps" name="steps" class="form-control" style="width: 90%; margin-left: 16px;" readonly value="<%=request.getParameter("idstep") %>" /></td>
									<td><input type="text" id="datadocs" name="datadocs" class="form-control" style="width: 90%" readonly value="<%=request.getParameter("docdata") %>" /></td>
									<td><input type="text" id="origins" name="origins" class="form-control" style="width: 90%" readonly value="<%=request.getParameter("docref_origin") %>" /></td>
									<td><input type="text" id="destinations" name="destinations" class="form-control" style="width: 90%" readonly value="<%=request.getParameter("docref_destination") %>" /></td>
								</tr>
								-->			         								     	
			              	</table>
			              </div>
			              
			              <div id="msg">${msg}</div>
		   
			             
			             <div class="table-responsive" id="details_list">
			               <table class="table table-striped m-b-none" id="details"></table>
						</div>			              
 				
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
  
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
  <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
  

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

	
  var scanner = "<%=request.getParameter("scanner")%>";
  var view_type = "<%=request.getParameter("view_type")%>";
  var iddevice = "<%=request.getParameter("iddevice")%>";
  
  var idflow = "<%=request.getParameter("idflow")%>";
  var idstep = "<%=request.getParameter("idstep")%>";
  var iddoc = "<%=request.getParameter("iddoc")%>";
  var urlws;
  var parws;
  
	//load config device
	loadConfig();
  
  function callRead() {
	  
	  //alert(urlws);
	  
	  var urls = "../../rfid/ws/ReadFromDevice?idflow="+idflow+"&idstep="+idstep+"&iddoc="+iddoc+"&urlws="+urlws+"&parws="+parws;
	  
	  //alert(parws);
	  $.getJSON(urls, function (data) {
		  
	  });
	  
  }
  
  function loadConfig() {
	  
	  var urls = "../../rfid/ws/GetConfig?iddevice="+iddevice;
	  $.getJSON(urls, function (data) {
			
		  if(scanner) {
			  
			  switch(view_type) {
			  
			    case "CPID":
			    	
			    	var ip = data.ip;
					var port = data.port;
					var cmd_read = data.cmd_read;
					urlws = "http://localhost:8080/SmartSuite/rfid/ws/CpidRead";
					parws = "ip="+ip+";port="+port+";cmd="+cmd_read;
					
			        break;
			    case "IMPINJ":
			        
			    	var ip = data.ip;
			    	urlws = "http://localhost:8080/SmartSuite/rfid/ws/ImpinjRead";
			    	parws = "ip="+ip;
					
			        break;
			    case "WIRAMA":
			    	
			    	break;
			    case "ZEBRA":
			    	
			    	break;
			    default:
			    	
			        //code block
			}
			  
		  }
			  
			
	  });

  }
  
  var packages = "";
  function GetDocuments(){

	  if ($('#barcodeepcchk').is(':checked')) {
		  group = "B";
	  }else{
		  group = "E";
	  }

	  idflow = "<%=request.getParameter("idflow")%>";
	  idstep = "<%=request.getParameter("idstep")%>";
	  docnum = "<%=request.getParameter("docnum")%>";
	  group = group;
	  var visible = "";
		/**/
			//document.getElementById("details_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
			
			var urls = "../../rfid/ws/ReadDocDetails?idflow="+idflow+"&idstep="+idstep+"&docnum="+docnum+"&group="+group;

			
			$.getJSON(urls, function (data) {
				
				var lista = data.listings;
				/**/
					var packageid = "";
				  	var barcodeepc = "";
				  	var name = "";
				  	var fbe = "";
				  	var qtaa = "";
				  	var qtar = "";
				  	var stato = "";
				  	var emptys = "";
				  	
				  	var testdata_ini = "";
				  	var testdata_fin = "";
				  	
				  	var testdata_ini = "[";
			 		var testdata_fin = "]";
			 		var testdata_row = "";
			 		
				 	$.each(lista, function(index, lista) {
				 		
				 		packageid = lista.package_id;
				 		
				 		
				 		if (group == "B"){
				 			barcodeepc = lista.barcode;
				 			fbe = "B";
				 		}else{
				 			barcodeepc = lista.epc;
				 			fbe = "E";
				 		}
				 		
				 		if (barcodeepc == null){
				 			barcodeepc = "";
				 		}
				 		
				 		
				 		
				 		name = lista.name;
				 		
					  	qtaa = lista.qty_theoretical;
					  	qtar = lista.qty_readed;
					  	stato = lista.status;
					  	
					  	if (parseFloat(qtaa)===parseFloat(qtar)){
					  		stato = "<i class='fa fa-circle text-success'></i>";
					  	}else{
					  		stato = "<i class='fa fa-circle text-danger'></i>";
					  	}
					  	
					  	
					  	diff = qtaa - qtar;		
					  
					  	//emptys = "";
					  	barcodeepc = barcodeepc.replace(/[^a-zA-Z0-9]/g,'');
					  	
					  					  	
					    emptys = "<div class='btn-group'>";
					    if(fbe == "B"){
					    	//emptys += "<button class='btn btn-default btn-sm' onclick=BarsCode("+barcodeepc+") data-toggle='modal' data-target='#exampleModal'><i class='fa fa-barcode'></i></button> ";
					    	visible = "style='visibility: visible;'";
					    }else{
					    	visible = "style='visibility: hidden;'";
					    }
					    emptys += "<button class='btn btn-default btn-sm' onclick=BarsCode("+barcodeepc+") data-toggle='modal' data-target='#exampleModal' "+visible+"><i class='fa fa-barcode'></i></button> ";
					    emptys += "</div>";
						
				 		if (testdata_row == ""){
				 			testdata_row = '{"barcodeepc":"'+barcodeepc+'","name":"'+name+'","qtaa":"'+qtaa+'","qtar":"'+qtar+'","diff":"'+diff+'","stato":"'+stato+'","empty":"'+emptys+'"}';
				 		}else{
				 			testdata_row = testdata_row + ',{"barcodeepc":"'+barcodeepc+'","name":"'+name+'","qtaa":"'+qtaa+'","qtar":"'+qtar+'","diff":"'+diff+'","stato":"'+stato+'","empty":"'+emptys+'"}';
				 		}
				 		
				 		
				 		//packages += '<li class="list-group-item">'+packageid+'</li>';
						
					});
				 	
				 	//$("#ulpackage").html(packages);
				 	
				 	
				 	var tfooter = "<tfoot><tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th></tr></tfoot>"
			 		
				 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
				 	document.getElementById("details_list").innerHTML = "<table id='details' class='table table-striped m-b-none' width='100%'></table>";
				 	//document.getElementById("details_list").innerHTML = "<table id='details' class='table m-b-none' width='100%'></table>";				 	
			 		var head = "<thead><th width='15%'>Barcode/EPC</th><th width='37%'>Descrizione</th><th width='13%'>QTA Attesa</th><th width='13%'>QTA Rilevata</th><th width='13%'>Differenze</th><th width='9%'>Stato</th><th width='5%'>&nbsp;</th></thead>";

			 		document.getElementById("details").innerHTML = head + tfooter;

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
			 		        "mDataProp": "barcodeepc"
			 		    }, {
			 		        "mDataProp": "name"
			 		    }, {
			 		        "mDataProp": "qtaa"
			 		    }, {
			 		        "mDataProp": "qtar"
			 		    }, {
			 		        "mDataProp": "diff"
			 		    }, {
			 		        "mDataProp": "stato"
			 		    }, {
			 		        "mDataProp": "empty"
			 		    }],	 		    
			 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
			 		    "bPaginate":true,
			 		   	"bDestroy": true,
			 		    "footerCallback": function ( row, data, start, end, display ) {
			 	            var api = this.api(), data;

			 	            var intVal = function ( i ) {
			 	                return typeof i === 'string' ?
			 	                    i.replace(/[\$,]/g, '')*1 :
			 	                    typeof i === 'number' ?
			 	                        i : 0;
			 	            };
			 	 
			 	            total3 = api
			 	                .column(4)
			 	                .data()
			 	                .reduce(function (a, b) {
			 	                    return intVal(a) + intVal(b);
			 	                }, 0);
			 	            
			 	            total2 = api
			 	                .column(2)
			 	                .data()
			 	                .reduce(function (a, b) {
			 	                    return intVal(a) + intVal(b);
			 	                }, 0);
			 	            
			 	            total = api
			 	                .column(3)
			 	                .data()
			 	                .reduce(function (a, b) {
			 	                    return intVal(a) + intVal(b);
			 	                }, 0);
			 	 
			 	            pageTotal3 = api
			 	                .column(4, { page: 'current'} )
			 	                .data()
			 	                .reduce(function (a, b) {
			 	                    return intVal(a) + intVal(b);
			 	                }, 0);
			 	            
			 	            pageTotal2 = api
			 	                .column(2, { page: 'current'} )
			 	                .data()
			 	                .reduce(function (a, b) {
			 	                    return intVal(a) + intVal(b);
			 	                }, 0);
			 	            
			 	            pageTotal = api
			 	                .column(3, { page: 'current'} )
			 	                .data()
			 	                .reduce(function (a, b) {
			 	                    return intVal(a) + intVal(b);
			 	                }, 0);
			 	 
			 	            $(api.column(2).footer()).html(
			 	                ''+pageTotal2+' ('+ total2 +' Totali)'
			 	            );
			 	            			 	            
			 	            $(api.column(3).footer()).html(
			 	                ''+pageTotal +' ('+ total +' Totali)'
			 	            );
			 	            
			 	            $(api.column(4).footer()).html(
				 	            ''+pageTotal3 +' ('+ total3 +' Totali)'
				 	        );			 	            
			 	        }
			 		});
				
			 		
				
				});
/**/	  
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
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
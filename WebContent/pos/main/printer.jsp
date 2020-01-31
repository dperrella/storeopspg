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
  <title>Printer New | <%=propLang.get("APPS") %></title>
  <link rel="shortcut icon" href="../../images/logos.ico">
  <meta name="description" content="ZPL" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>

  <script src="../../js/jquery.min.js"></script>
  <script src="../../js/bootstrap.js"></script>
  
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <script src="../../js/app.js"></script>
  <script src="../../js/gethoms.js"></script>    
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>

<!--[if lt IE 9]>
   <script src="../../js/ie/html5shiv.js"></script>
   <script src="../../js/ie/respond.min.js"></script>
   <script src="../../js/ie/excanvas.js"></script>
<![endif]-->
<style type="text/css">
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
<body>
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

        <section id="content">
          <section class="hbox stretch">          
          <%@ include file="../../inc2/menuApp8.jsp" %>
            <section>
              <section class="vbox">
                <section class="scrollable padder" id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="ZPL - "+ propLang.get("sm_c_pdc") %></h2>
		                

			 
					<div class="row">
			                
					                
            </div>			  
			  
<!-- Fine blocchi -->			              		
					<div class="row" style="display: <%=session.getAttribute("Tab1")%>">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-list-alt" aria-hidden="true"></i> <%= "List ZPL" %>  
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">
						  
			              <div class="table-responsive">
			              <!-- 
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
							        <th width="10%"><%=propLang.get("sm_cont_dd") %></th>
							        <th width="15%"><%=propLang.get("sm_cont_do") %></th>
							        <th width="17%"><%=propLang.get("sm_cont_cl") %></th>
							        <th width="25%"><%=propLang.get("sm_cont_ne") %></th>
							        <th width="25%"><%=propLang.get("sm_cont_ce") %></th>
							        <th width="8%"><%=propLang.get("sm_cont_is") %></th>
							        <th></th>
							      </tr>
							  </thead>	      					  
	      					  <tbody>
							  <c:forEach items="${Listlog}" var="Listlog" varStatus="status">
								<tr>
								  <td><span class="label bg-light">${Listlog.diff}</span></td>
								  <td>${Listlog.date}</td>
			                      <td>${Listlog.cli}</td>
								  <td>${Listlog.neg}</td>
			                      <td>${Listlog.code}</td>
								  <td>${Listlog.idst}</td>	
								  <td>
								  <% if (session.getAttribute("RIDC").equals("super") || session.getAttribute("RIDC").equals("countryadmin") || session.getAttribute("RIDC").equals("list-admin")) {%>
								  <div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu" style="margin-left: -100px;">
					                    <li><a href="smartcontrol-stores-dett.htm?id=${Listlog.idst}"><i class="fa fa-edit"></i> <%=propLang.get("mod") %></a></li>
					                    <li><a href="smartcontrol-stores-dett-device.htm?id=${Listlog.idst}"><i class="fa fa-edit"></i> <%=propLang.get("mod") + " Device" %></a></li>
					                    <li><a href="smartcontrol-stores-dett-closing.htm?id=${Listlog.idst}"><i class="fa fa-edit"></i> <%=propLang.get("mod") + " Closing Time"%></a></li>
					                  </ul>
					              </div>
					              <% }else{ %>
					           	 <div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle btn-sm" data-toggle="dropdown"><%=propLang.get("azioni") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu" style="margin-left: -100px;">
					                    <li><a href="smartcontrol-stores-dett.htm?id=${Listlog.idst}"><i class="fa fa-edit"></i> <%=propLang.get("sm_vis") %></a></li>
					                  </ul>
					              </div>    
					              <%} %>
								  </td>		                      			                      
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					-->
		  					<table>
		  						<tr>
		  							<td><%=propLang.get("tick_conte") %></td>
		  							<td id="progressivo_" style="font-weight: bold;">&nbsp;<input type="text" id="progressivo" style="width: 80px; height: 20px !important;" value="<%=session.getAttribute("SeqN")%>" readonly/></td>
		  						</tr>
		  						<tr><td colspan="2">&nbsp;</td></tr>
		  						<tr>
		  							<td><%=propLang.get("tick_nzpl") %></td>
		  							<td>&nbsp;<input type="text" id="numzpl" style="width: 80px; height: 20px !important;" onkeyup="numerics(this.value)"/></td>
		  						</tr>
		  						<tr><td colspan="2">&nbsp;</td></tr>
		  						<tr style="display: none;">
		  							<td><button onclick="Prints()" class="form-control"><i class="fa fa-print"></i> <%=propLang.get("tick_print") %></button></td>
		  							<td>&nbsp;</td>
		  						</tr>		  						
								<tr><td colspan="2">&nbsp;</td></tr>
	  						
		  						<tr id="downfile" style="display: none;"> 
		  							<td><a href="" id="down"  download="ZPL" class="form-control"><%=propLang.get("tick_down") %></a></td>
		  							<td>&nbsp;</td>
		  						</tr>		  						
		  						
		  					</table>
		  				  </div>
		  				 		<div style="margin-left: 16px;">
										<table width="100%">
								              <tbody>
								              <tr><td>&nbsp;</td></tr>
								              <tr><td align="left">
								              <button type="button" id="back" class="btn btn-default" onclick="document.location.href='printerlist.htm'"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> <%=propLang.get("tick_back") %></button>
								              <button type="button" id="delete" class="btn btn-default" onclick="Erase()"><i class="fa fa-refresh" aria-hidden="true"></i> <%=propLang.get("tick_canc") %></button> 								                                 
								              <!--  <button type="button" id="delete" class="btn btn-default" onclick="document.location.href='printer.htm'"><i class="fa fa-refresh" aria-hidden="true"></i> Cancel</button>-->
											  <button type="button" id="print" onclick="Prints()" class="btn btn-default"><i class="fa fa-print"></i> <%=propLang.get("tick_print") %></button>								                                  
								              </td>
								        </tr></tbody></table>
								</div>
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>
			
													            		
			              		<!--  -->

		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>
                
                <!-- side content -->
<!-- 
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	            
						<%@ include file="../../inc/smartcontrol-menu-right.jsp" %>
				
	                </section>
	                
	                <footer class="footer footer-md bg-black">
	                  <form action="#" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                        </span> 
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            -->
	            <!-- / side content -->
                
               
                
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  
  <!-- fine pietro -->
 <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel" align="center"><%="Device not Enabled" %></h4>
      </div>
      <div class="modal-body">
        <table width="100%">
        	<tr>
        		<td width="34%"><%="Enabled" %> </td>
        		<td width="66%">
	        		<select type="text" id="choiseenabled" class="form-control">
	        		<option value="">Choise</option>
	        			<option value="t">True</option>
	        			<option value="f">False</option>
	        		</select>
        		</td>
        	</tr>
        	<tr>
        		<td width="34%"><%="Reason" %></td>
        		<td width="66%">
        			<select type="text" id="choisereason" class="form-control">
	        			<option value="">Choise</option>
	        			${Causeop}
	        		</select>
        		</td>
        	</tr>
        	<tr>
        		<td width="34%"><%="Updating" %> </td>
        		<td width="66%">
	        		<select type="text" id="choiseupdating" class="form-control">
	        		<option value="">Choise</option>
	        			<option value="t">True</option>
	        			<option value="f">False</option>
	        		</select>
        		</td>
        	</tr>
        	<tr>
        		<td width="34%"><%="Notes" %> </td>
        		<td width="66%">
				<textarea rows="2" cols="60" id="choisenotes"></textarea>
        		</td>
        	</tr>        	        	        	
        	<tr><td colspan="3">&nbsp;</td></tr>        	        	
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-default" onclick="SavesDne()">Save</button>
      </div>
    </div>
  </div>
</div> 
  	<script src="../../js/alertify.min.js"></script>
    <link href="../../css/alertify.core.css" rel="stylesheet">
	<link href="../../css/alertify.default.css" rel="stylesheet">
  <script>
    $('#example').dataTable( {
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
      "iDisplayLength": 10
  } );
    
    $('#example2').dataTable( {
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
        "iDisplayLength": 10
    } );
    
    $('#example3').dataTable( {
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
        "iDisplayLength": 10
    } );
    
    $('#example4').dataTable( {
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
        "iDisplayLength": 10
    } );    
    
    $('#example5').dataTable( {
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
        "iDisplayLength": 10
    } ); 
    
    $('#example6').dataTable( {
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
        "iDisplayLength": 10
    } );      
  </script>
  <script>
var Mod,Id;
function oper(mod,id){
	Mod = mod;
	Id = id;
}  
function SavesDne(){
	
	if (document.getElementById("choiseenabled").value == ""){
		alertify.log("<%=propLang.get("sm_obb_def") %>");
		document.getElementById("choiseenabled").focus();
		return;
	}
	
	if (document.getElementById("choisereason").value == ""){
		alertify.log("<%=propLang.get("sm_obb_def") %>");
		document.getElementById("choisereason").focus();
		return;
	}
	if (document.getElementById("choiseupdating").value == ""){
		alertify.log("<%=propLang.get("sm_obb_def") %>");
		document.getElementById("choiseupdating").focus();
		return;
	}
	
choiseenabled = document.getElementById("choiseenabled").value;
choisereason = document.getElementById("choisereason").value;
choiseupdating = document.getElementById("choiseupdating").value;
choisenotes = document.getElementById("choisenotes").value;

	$.ajax({
		url : "../../smcontrol",
		data : {
			tab : Mod,
			id : Id,
			choiseenabled : choiseenabled,
			choisereason : choisereason,
			choiseupdating : choiseupdating,
			choisenotes : choisenotes
		},
		success : function(responseText) {
			location.href = "smartcontrol.htm";
			alertify.success("<%=propLang.get("msgSuccess")%>");
		}
	});

}
</script>
<script>
var delay = "<%=session.getAttribute("Timing")%>";
if (delay != "" && delay > 0){
	myVar = setTimeout(Refreshh,delay);
}

function Refreshh(){
	location.href='smartcontrol.htm';
}

</script>
<script>
function Prints(){
	
	if (document.getElementById("numzpl").value == ""){
		alertify.error("Numero di ZPL Obbligatorio");
		document.getElementById("numzpl").focus();
		return;
	}
	
	
	$.ajax({
		url : "../../zplprint",
		data : {
			File : document.getElementById("HidfileZPL").value,
			SeqN : document.getElementById("HidSeqN").value,
			SeqS : document.getElementById("HidSeqS").value,
			Numero : document.getElementById("numzpl").value
		},
		success : function(responseText) {

			alertify.success("File Creato e Salvato in: " + responseText);
			var num = parseInt(document.getElementById("progressivo").value);
			num = num + parseInt(document.getElementById("numzpl").value);
			document.getElementById("progressivo").value = num;
			document.getElementById("down").href = responseText;
			document.getElementById("downfile").style.display = "inline";
	
		}
	});	
	
}

function Reload(obj){
	
	var Flag = obj.value;

		$.ajax({
			url : "../../smmodality",
			data : {
				flag : Flag,
			},
			success : function(responseText) {

				var pp = responseText;

				
				var parts = pp.split("$");
				var resp = parts[0]; 
				var flags = parts[1];

				document.getElementById("DivExample3").innerHTML = resp;
				
				if(flags == "ip"){
					document.getElementById("example3_info").style.visibility = "hidden";
					document.getElementById("example3_paginate").style.visibility = "hidden";
				}else if(flags == "all"){
					document.getElementById("example3_info").style.visibility = "visible";
					document.getElementById("example3_paginate").style.visibility = "visible";
					//$("#example3_length").val(100)
					var vals = $("[name='example3_length']");
					//vals.val(10);
					//$("[name='example3_length']").val(10).change();

					$("[name='example3_length']").val(10).trigger('change');
				}else{
					document.getElementById("example3_info").style.visibility = "hidden";
					document.getElementById("example3_paginate").style.visibility = "hidden";
					location.href='smartcontrol.htm';
				}
		
			}
		});	

}

function numerics(ob){
	if (isNaN(ob)){
		alertify.error("Inserire solo valori numerici");
		document.getElementById("numzpl").value = "";
		document.getElementById("numzpl").focus();
		return;
	}
}

function Erase(){
	document.getElementById("numzpl").value = "";
} 
</script>
<script>  
//var err = "<%//=propLang.get("sm_notautorized") %>";
</script>
<!--  
<script src="../../js/autorized.js"></script>
<script src="../../js/clock.js"></script>
-->
<!-- 
<% //if (session.getAttribute("RIDC").equals("super") || session.getAttribute("RIDC").equals("countryadmin") || session.getAttribute("RIDC").equals("list-admin")) {%>
<script>
document.getElementById("id_notification").style.display = "inline";
</script> 
<% //} //else {%>
<script>
document.getElementById("id_notification").style.display = "none";
</script> 
<% //}%>
-->
<input type="hidden" id="HidfileZPL" value="<%=session.getAttribute("FileZPL")%>">
<input type="hidden" id="HidSeqN" value="<%=session.getAttribute("SeqN")%>">
<input type="hidden" id="HidSeqS" value="<%=session.getAttribute("SeqS")%>">

  <script>
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();
		
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
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
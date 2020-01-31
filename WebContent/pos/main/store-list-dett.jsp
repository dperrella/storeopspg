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
  <title>Add Negozio | <%=propLang.get("APPS") %></title>
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
                      
		                <h2 class="font-thin m-b"><%="Store	 " %></h2>
			  
<!-- Fine blocchi -->			              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-shopping-cart" aria-hidden="true"></i> <%="Store"%>
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive">

<!--  -->
							<table width="100%">
<!-- 							
								<tr>
								<td colspan="2" width="34%">Export to Analitycs</td>
								<td width="33%" style="visibility:hidden;" id="agreestart">Date Agreement Start</td>
								<td width="33%" style="visibility:hidden;" id="agreeend">Date Agreement End</td>
								</tr>
								<tr>
								<td colspan="2" width="34%">
								
								<div class="btn-group" data-toggle="buttons" id="divToanalitics">
									  <label class="btn btn-default" id="radexpsi" onclick="Vis2(true)">
									    <input type="radio" id="radioexpsi" name="radiobutton2" value="true"> <%=propLang.get("sm_yes") %>
									  </label>
									  <label class="btn btn-default active" id="radexpno" onclick="Vis2(false)">
									    <input type="radio" id="radioexpno" name="radiobutton2" value="false" checked> <%=propLang.get("sm_no") %>
									  </label>
									</div>
									
								</td>
								<td width="33%" style="visibility:hidden;" id="agreestartdate">
								<input type="text" id="dateagreementstart" style="width: 95%" placeholder="Date Agreement Start" class="form-control" value="<%=session.getAttribute("DateAgreeStart") %>" onclick="Calendar.show(this, '%Y-%m-%d', false)" onfocus="Calendar.show(this, '%Y-%m-%d', false)" onblur="Calendar.hide()" readonly/>
								</td>
								<td width="33%" style="visibility:hidden;" id="agreeenddate">
								<input type="text" id="dateagreementend" style="width: 95%" placeholder="Date Agreement End" class="form-control" value="<%=session.getAttribute("DateAgreeEnd") %>" onclick="Calendar.show(this, '%Y-%m-%d', false)" onfocus="Calendar.show(this, '%Y-%m-%d', false)" onblur="Calendar.hide()" readonly/>								
								</td>																
								</tr>	
								<tr><td colspan="4">&nbsp;</td></tr>
-->
<!--  -->								
								<tr>
									<td colspan="2" width="34%">ID</td>
									<td width="33%"><%=propLang.get("tick_desc") %></td>
									<td width="33%"><%=propLang.get("tick_nadb") %></td>
								</tr>
								<tr>
									<td colspan="2" width="34%"><input type="text" id="id" style="width: 95%" placeholder="ID" class="form-control" value="<%=session.getAttribute("ID") %>" readonly /></td>
									<td width="33%">
										<input type="text" id="descrizione" style="width: 95%" placeholder="Descrizione" class="form-control" value="<%=session.getAttribute("Descrizione") %>" />
									</td>
									<td width="33%"><input type="text" id="nomedb" style="width: 95%" placeholder="Nome Database" class="form-control" value="<%=session.getAttribute("Nomedb") %>" /></td>
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr>	
								<tr>
									<td colspan="2" width="34%"><%=propLang.get("tick_ip") %></td>
									<td width="33%" id="tdregion"><%=propLang.get("tick_usdb") %></td>
									<td width="33%" id="tdprovince"><%=propLang.get("tick_pwdb") %></td>
								</tr>
								<tr>
									<td colspan="2" width="34%">
										<input type="text" id="ip" style="width: 95%" placeholder="IP" class="form-control" value="<%=session.getAttribute("Ip") %>" />									
									</td>
									<td width="33%">
										<input type="text" id="userdb" style="width: 95%" placeholder="Utenza DB" class="form-control" value="<%=session.getAttribute("Utenza") %>" />
									</td>
									<td width="33%">
										<input type="text" id="pwddb" style="width: 95%" placeholder="Password DB" class="form-control" value="<%=session.getAttribute("Pwd") %>" />
									</td>
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr>
								<tr>
									<td colspan="4" width="34%"><%=propLang.get("tick_conn") %></td>
								</tr>
								<tr>
									<td colspan="4" width="34%">
										<input type="text" id="connstring" style="width: 98.3%" placeholder="Connection String" class="form-control"  value="<%=session.getAttribute("Conn") %>"/>
									</td>
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr>
<!-- 									
								<tr>
									<td colspan="3" width="34%">Address</td>
									<td width="34%">Postal Code</td>
								</tr>
								<tr>
									<td colspan="3" width="34%">
										<input type="text" id="address" style="width: 97.5%" placeholder="Address" class="form-control"  value="" />
									</td>
									<td width="34%">
										<input type="text" id="postalcode" style="width: 95%" placeholder="Postal Code" class="form-control"  value="" />
									</td>									
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr>	
								<tr>
									<td colspan="3" width="34%">Phone</td>
									<td width="34%">Mail Address</td>
								</tr>
								<tr>
									<td colspan="3" width="34%">
										<input type="text" id="phone" style="width: 97.5%" placeholder="Phone" class="form-control"  value="" />
									</td>
									<td width="34%">
										<input type="text" id="mailaddress" style="width: 95%" placeholder="Mail Address" class="form-control"  value="" />
									</td>									
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr>																																		
								<tr>
									<td colspan="2" width="34%">Closing Time Default Template</td>
									<td width="33%">Account Number (SAP ID)</td>
									<td width="33%">Customer Id Store</td>
								</tr>
								<tr>
									<td colspan="2" width="34%">
										<select id="closingtime" style="width: 95%;" class="form-control">
											<option value="0">Choise</option>
											<%//=session.getAttribute("Templop") %>
										</select>
									</td>
									<td width="33%"><input type="text" id="accountnumber" style="width: 95%" placeholder="Account Number" class="form-control" value="" /></td>
									<td width="33%"><input type="text" id="customeridstore" style="width: 95%" placeholder="Customer Id Store" class="form-control" value="" />
									</td>
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr>		
								<tr>
									<td colspan="2" width="34%">Brand Name</td>
									<td width="33%">Brand Logo</td>
									<td width="33%">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2" width="34%">
									<input type="text" id="brandname" style="width: 95%" placeholder="Brand Name" class="form-control" value="" />
									</td>
									<td width="33%">
									
							        <div class="input-group">
							                <label class="input-group-btn">
							                    <span class="btn btn-default">
							                        Browse&hellip; <input type="file" style="display: none;" multiple id="file" name="file">
							                    </span>
							                </label>
							                
							                <input type="text" class="form-control" style=" width: 94%;" id="brandlogo" readonly value="" onblur="FileUpd()" />
							        </div>

																		
									
									</td>
									<td width="33%"></td>
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr>
-->								
							</table>
<!--  -->	
		  				  </div>
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>	
			
													            		
			              		<!--  -->
			              		
							</div>


								<div style="margin-left: 16px;">
										<table width="100%">
								              <tbody><tr><td align="left">
								              <button type="button" id="back" class="btn btn-default" onclick="history.back()"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> <%=propLang.get("tick_back") %></button> 
								              <button type="button" id="delete" class="btn btn-default" onclick="document.location.href='store-list-add.htm'"><i class="fa fa-refresh" aria-hidden="true"></i> <%=propLang.get("tick_canc") %></button>                    
								              <button type="button" id="Save" class="btn btn-default" onclick="Saves()"><i class="fa fa-floppy-o" aria-hidden="true"></i> <%=propLang.get("tick_save") %></button>
								              </td>
								        </tr></tbody></table>

		  				
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
      "lengthMenu": <%="[["+propLang.get("numeroxpage")+"],["+propLang.get("numeroxpage")+"]]" %>,
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
			var num = parseInt(document.getElementById("progressivo").innerHTML);
			num = num + parseInt(document.getElementById("numzpl").value);
			document.getElementById("progressivo").innerHTML = num;
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

/*
$('.paginate_button').on('click', function() {
	  alert($(this).attr('data-dt-idx'));
	  alert($(this).attr('aria-controls'));
});
*/
function Saves(){

	//exptoana
	if (document.getElementById("id").value == ""){
		alertify.log("<%=propLang.get("sm_obb_id")%>");
		document.getElementById("id").focus();
		return;
	}
	
	if (document.getElementById("descrizione").value == ""){
		alertify.log("<%=propLang.get("sm_obb_desc")%>");
		document.getElementById("descrizione").focus();
		return;
	}
	
	if (document.getElementById("connstring").value == ""){
		alertify.log("<%=propLang.get("sm_obb_conn")%>");
		document.getElementById("connstring").focus();
		return;
	}
	
	if (document.getElementById("ip").value == ""){
		alertify.log("<%=propLang.get("sm_obb_ip")%>");
		document.getElementById("ip").focus();
		return;
	}	
	
	if (document.getElementById("userdb").value == ""){
		alertify.log("<%=propLang.get("sm_obb_usr")%>");
		document.getElementById("userdb").focus();
		return;
	}		
	
	if (document.getElementById("pwddb").value == ""){
		alertify.log("<%=propLang.get("sm_obb_pwd")%>");
		document.getElementById("pwddb").focus();
		return;
	}		
	
	if (document.getElementById("nomedb").value == ""){
		alertify.log("<%=propLang.get("sm_obb_dbn")%>");
		document.getElementById("nomedb").focus();
		return;
	}	
			
	Tab = "Sto";
	Flag = "M";
		$.ajax({
			url : "../../smcontrolzpl",
			data : {
				tab : Tab,
				flag : Flag,
				id : document.getElementById("id").value,
				desc : document.getElementById("descrizione").value,
				conn : document.getElementById("connstring").value,
				ip : document.getElementById("ip").value,
				userdb : document.getElementById("userdb").value,
				pwddb : document.getElementById("pwddb").value,
				nomedb : document.getElementById("nomedb").value
			},
			success : function(responseText) {
				//location.href="smartcontrol-stores.htm";
				alertify.success(responseText);
				setTimeout(function(){ location.href="store-list.htm"; }, 2000);

			}
		});
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
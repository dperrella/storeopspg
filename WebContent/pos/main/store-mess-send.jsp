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
  <title><%="Messaging"%> | <%=propLang.get("APPS") %></title>
  <link rel="shortcut icon" href="../../images/logos.ico">
  <meta name="description" content="SmartHR" />
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
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="Send Message" %></h2>
		                

		              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-paper-plane" aria-hidden="true"></i> <%="Send Message" %>
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">
									
									<!-- Inizio combo+div -->
									<div class="panel-body">
									<table width="100%">
										<tr>&nbsp;</tr>
										<tr>
											<td align="center"><%=session.getAttribute("StoresNegoz") %></td>
										</tr>
									</table>
									</div>
									<!-- Fine combo+div -->
									
									
									<div class="col-lg-6">
										<div class="panel-heading">
											<i class="fa fa-object-group" aria-hidden="true"></i> <%="Titolo" %>
										</div>
<input type="text" id="title" class="form-control" placeholder="Titolo" />
									</div>
									<div class="col-lg-6">
										<div class="panel-heading">
											<i class="fa fa-file-text-o" aria-hidden="true"></i> <%="Messaggio" %>
											<div style="float: right;">
												<button id="ricorsivi" title="Messaggi Ricorsivi" onclick="VisualizzaRic(true)">Messaggi Ricorsivi <i class="fa fa-refresh fa-spin fa-fw" aria-hidden="true"></i></button>
											</div>
										</div>
										<select id="ricmess" class="form-control" style="display: none; margin-top: 5px; margin-bottom: 5px;" onchange="Sel()">
										<option value="">-- Seleziona --</option>
										<%=session.getAttribute("Messric") %>
										</select>
										<textarea class="form-control" id="messages" rows="4" cols="50" placeholder="Messaggio"></textarea>

									</div>
									<div class="col-lg-10">&nbsp;</div>
									<!-- 
									<div class="col-lg-6">
										<div class="panel-heading">
											<i class="fa fa-barcode" aria-hidden="true"></i> <%//="Code" %>
										</div>
										<input type="text" id="code" class="form-control" style="width:100%;" />
									</div>
									<div class="col-lg-6">
										<div class="panel-heading">
											<i class="fa fa-text-width" aria-hidden="true"></i> <%//="Description" %>
										</div>
										<input type="text" id="description" class="form-control" style="width:100%;" />
									</div>		
									-->							
							<!-- /.panel-body -->
						</div>
						
						</div>
						
								</div>
								<div style="margin-left: 16px;">
										<table width="100%">
								              <tbody>
								              	<tr>
									              <td align="left">
										              <!--  <button type="button" id="back" class="btn btn-default" onclick="history.back()"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> Back</button>    -->
										              <button type="button" id="delete" class="btn btn-default" onclick="document.location.href='store-mess-send.htm'"><i class="fa fa-refresh" aria-hidden="true"></i> Cancel</button>                    
										              <button type="button" id="Save" class="btn btn-default" onclick="Saves()"><i class="fa fa-paper-plane" aria-hidden="true"></i> Invia</button>
									              </td>
								        	  	</tr>
								        	  </tbody>
								        </table>
								</div>
					</div>
			              		

		  				
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
  
  <input type="hidden" id="Stores" name="Stores" />
  <input type="hidden" id="Stores2" name="Stores2" value="NO" />
  	<script src="../../js/alertify.min.js"></script>
    <link href="../../css/alertify.core.css" rel="stylesheet">
	<link href="../../css/alertify.default.css" rel="stylesheet">
	
	 
	
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>	
  <link href="../../css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
  <script src="../../js/bootstrap-multiselect2.js" type="text/javascript"></script>
 <!-- fine pietro -->
    <script type="text/javascript">
       
    var stores = "";
    var stores2 = "";    
        $(function () {
            $('#lstFruits').multiselect({
                includeSelectAllOption: true
            });
            $('#btnSelected').click(function () {
                var selected = $("#lstFruits option:selected");
                var message = "";
                selected.each(function () {
                    message += $(this).text() + " " + $(this).val() + "\n";
                });
                //alert(message);
            });
        });
    </script>
  <script>
function SelDel2(){
	document.getElementById("Stores").value = "ALL";
	document.getElementById("Stores2").value = "ALL";
}

function Saves(){
	
	if (document.getElementById("lstFruits").value == ""){
		alertify.log("Store obbligatorio");
		return;
	}

	if (document.getElementById("title").value == ""){
		alertify.log("Titolo Obbligatorio");
		return;
	}

	if (document.getElementById("messages").value == ""){
		alertify.log("Messaggio Obbligatorio");
		return;
	}

	
	if (document.getElementById("Stores2").value == "ALL"){
		var checkedValues = $('input:checkbox:checked').map(function() {return this.value;}).get();
		document.getElementById("Stores").value = checkedValues; 
		var rep = document.getElementById("Stores").value;
		//rep = rep.substring(16);
		rep = rep.replace("multiselect-all,","");
		document.getElementById("Stores").value = rep;
	}else{
		var checkedValues = $('input:checkbox:checked').map(function() {return this.value;}).get();
		document.getElementById("Stores").value = checkedValues;
	}
	
	Tab = "SendMessage";
	Flag = "I";
	Id = document.getElementById("Stores").value;
	Title = document.getElementById("title").value;
	Messages = document.getElementById("messages").value;
	
		$.ajax({
			url : "../../smsendermessages",
			data : {
				tab : Tab,
				flag : Flag,
				id : Id,
				title : Title,
				message : Messages
			},
			success : function(responseText) {
				alertify.success(responseText);
				//setTimeout(function(){ location.href="store-mess-send.htm"; }, 2000);
			}
		});
		
}

var fl = false;
function VisualizzaRic(str){
	if (str == fl){
		document.getElementById("ricmess").style.display = "none";
		fl = false;
	}else{
		document.getElementById("ricmess").style.display = "inline";
		fl = true;

	}
}

function Sel(){

	var ms = $("#ricmess option:selected").text();
	var tt = $("#ricmess option:selected").val();

	if (tt != ""){
		document.getElementById("messages").innerHTML = ms;
		document.getElementById("title").value = tt; 
		document.getElementById("messages").value = ms;
	}else{
		document.getElementById("messages").innerHTML = "";
		document.getElementById("title").value = ""; 
		document.getElementById("messages").value = "";
	}

	/**/
	document.getElementById("ricmess").style.display = "none";
	fl = false;
}

</script>
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
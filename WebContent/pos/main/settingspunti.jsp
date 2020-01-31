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
  
  <title>Fidelity Card Carico | Montoro</title>
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
                      
		                <h2 class="font-thin m-b"><%="Fidelity Card Settings" %></h2>
		                

		              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<!-- 
								<i class="fa fa-list-alt" aria-hidden="true"></i> <%="Status" %>
								<div style="float: right;"><button onclick="reloads()"><i class="fa fa-refresh" aria-hidden="true"></i> Reload</button></div>
								-->  
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive">

							<table width="100%">						
							<tr>
								<td width="34%"><%=propLang.get("tick_vapo") %></td>
								<td width="33%"><%=propLang.get("tick_cont") %> &euro;</td>
								<td width="33%"><%=propLang.get("tick_step") %></td>
							</tr>
							<tr>
								<td width="34%">
									<input type="text" id="valpunto" class="form-control" placeholder="Valore Punto" style="width: 95%; background: white;" value="1" readonly >				
								</td>
								<td width="33%">
									<input type="text" id="valeuro" class="form-control" placeholder="Valore Euro" style="width: 95%;" value="${Perc}" onkeypress="return event.charCode >= 48 && event.charCode <= 57 || event.charCode > 45 && event.charCode <= 46">								
								</td>
								<td width="33%">
									<input type="text" id="valstep" class="form-control" placeholder="Valore step" style="width: 95%;" value="${Vals}" onkeypress="return event.charCode >= 48 && event.charCode <= 57 || event.charCode > 45 && event.charCode <= 46">
								</td>
							</tr>						

<!--  -->															
							<tr><td colspan="3" id="trLoad" style="visibility:hidden;"><div style="align: center;" align="center"><img src="../../images/loading222.gif" width="79px" height="50px" /></div></td></tr>																									
							</table>
		  				  </div>
								<div style="margin-left: 0px;">
										<table width="100%">
								              <tbody>
								              	<tr>
									              <td align="left">
										              <!--  <button type="button" id="back" class="btn btn-default" onclick="history.back()"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> Back</button>    -->
										              <button type="button" id="delete" class="btn btn-default" onclick="window.location.reload(true);;"><i class="fa fa-refresh" aria-hidden="true"></i> <%=propLang.get("tick_canc") %></button>                    
										              <button type="button" id="Save" class="btn btn-default" onclick="FidelitySave()"><i class="fa fa-upload" aria-hidden="true"></i> <%=propLang.get("tick_save") %></button>
									              </td>
								        	  	</tr>
								              	<tr><td>&nbsp;</td></tr>								        	  	
								        	  </tbody>
								        </table>
								</div>	
<!--  -->
			              <div class="table-responsive"  id="stock_list">
			                <table class="table table-striped m-b-none" id="stock"></table>
		  				  </div>
		  				  									  				  
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
								
							
							<!--  -->
															
							<!--  -->	
								
					</div>

		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>               
               
                
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
 
<script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet">   
<!--  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>

<script src="http://www.jbPivot.org/js/vendor/jbpivot.min.js"></script>
<link rel="stylesheet" href="http://www.jbPivot.org/css/jbpivot.min.css">

<script src="../../js/orb/orb.min.js"></script>
<link rel="stylesheet" href="../../js/orb/orb.min.css">
-->
  	<!-- Calendar -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
function FidelitySave(){

	if (document.getElementById("valeuro").value == ""){
		alertify.log("Valore Euro Obbligatorio");
		document.getElementById("valeuro").focus();
		return;
	}
	
	if (document.getElementById("valstep").value == ""){
		alertify.log("Valore Step Obbligatorio");
		document.getElementById("valstep").focus();
		return;
	}
	
	var number1 = $("#valpunto").val();
	var number2 = $("#valeuro").val();

	var perc = $("#valeuro").val()	
	

		$.ajax({
			url: '../../SetSettingsFidelity',
			data : {
				valp : $("#valpunto").val(),
				vale : $("#valeuro").val(),
				perc : perc,
				vals : $("#valstep").val()
			},
			success : function(responseText) {
				if (responseText == "OK"){
					location.href="./fidelity-card.htm";
					//location.href="./caricopunti.htm";
				}else{
					alert("Close with Problem");
				}
			}
		
		});


}

function getHomes(){
	//app/zpl/stock.htm
	var urls = window.location.origin+"/SmartSuite/pos/main/fidelity-card.htm";
	document.getElementById("getHome").href = urls;
}



function ControlloFis(str){
	
	// 3406514570	
	
	if (str != ""){
		$.ajax({
			url: '../../GetCodFiscale',
			data : {
				fis : str
			},
			success : function(responseText) {
				if (responseText == "OK"){
					//location.href="./fidelity-card.htm";
				}else{
					alertify.log("Cod Fiscale già presente in archivio");
					document.getElementById("fidCfis").focus();
					return;
				}
			}
		
		});
	}
}

document.getElementById("fidCod").focus();
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
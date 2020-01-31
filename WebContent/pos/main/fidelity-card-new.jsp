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
  
  <title>Fidelity Card New | <%=propLang.get("APPS") %></title>
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
                <section class="scrollable padder " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="Fidelity Card New" %></h2>
		                
		              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
									<%="Fidelity Card New" %>
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive">

							<table width="100%">
							<tr>
								<td width="34%"><%=propLang.get("tick_name")%></td>
								<td width="33%"><%=propLang.get("tick_surn")%></td>
								<td width="33%"><%=propLang.get("tick_sex")%></td>
							</tr>
							<tr>
								<td width="34%">									
									<input type="text" id="fidName" class="form-control" placeholder="Nome" style="width: 95%;">
								</td>
								<td width="33%">
									<input type="text" id="fidSurname" class="form-control" placeholder="Cognome" style="width: 95%;">
								</td>
								<td width="33%">
									<select id="fidSex" class="form-control" style="width: 95%;">
									<option value="M">UOMO</option>
									<option value="F">DONNA</option> 
									</select>							
								</td>
							</tr>						
							<tr><td colspan="3">&nbsp;</td></tr>
							<tr>
								<td width="34%"><%=propLang.get("tick_birt")%></td>
								<td width="33%"><%=propLang.get("tick_phon")%></td>
								<td width="33%"><%=propLang.get("tick_mail")%></td>
							</tr>
							<tr>
								<td width="34%">
									<input type="text" id="fidBirth" class="form-control" placeholder="Data di nascita" style="width: 95%; background: white;" readonly>				
								</td>
								<td width="33%">
									<input type="text" id="fidTel" class="form-control" placeholder="Telefono" style="width: 95%;">								
								</td>
								<td width="33%">
									<input type="text" id="fidMail" class="form-control" placeholder="E-Mail" style="width: 95%;">
								</td>
							</tr>						
							<tr><td colspan="3">&nbsp;</td></tr>
							<tr>
								<td width="34%"><%=propLang.get("tick_fico")%></td>
								<td width="33%"><%=propLang.get("tick_cate")%></td>
								<td width="33%"><%=propLang.get("tick_stat")%></td>
							</tr>
							<tr>
								<td width="34%">
								<input type="text" id="fidCfis" class="form-control" placeholder="Codice Fiscale" maxlength="16" style="width: 95%;" onblur="ControlloFis(this.value)">
								</td>
								<td width="33%">
								<select id="fidCategory" name="fidCategory" class="form-control" style="width: 95%;">
									<option value="">-- Choise --</option>
									${Category}
								</select>
								</td>
								<td width="33%">
								<select id="fidStatus" name="fidStatus" class="form-control" style="width: 95%;">
									<option value="true">ATTIVO</option>
									<option value="false">NON ATTIVO</option>
								</select>	
								</td>
							</tr>
<!--  -->
							<tr><td colspan="3">&nbsp;</td></tr>
							<tr>
								<td width="34%"><%=propLang.get("tick_city")%></td>
								<td width="33%"><%=propLang.get("tick_addr")%></td>
								<td width="33%"><%=propLang.get("tick_cofc")%></td>
							</tr>
							<tr>
								<td width="34%">
								<select id="fidCity" name="fidCity" class="form-control" style="width: 95%;">
									${Comuni}
								</select>
								</td>
								<td width="33%">
									<input type="text" id="fidAddress" class="form-control" placeholder="Indirizzo" style="width: 95%;">
								</td>
								<td width="33%">
									<input type="text" id="fidcodfid" class="form-control" placeholder="Codice Fidelity Card" maxlength="13" style="width: 95%;">
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
										              <button type="button" id="delete" class="btn btn-default" onclick="window.location.reload(true);;"><i class="fa fa-refresh" aria-hidden="true"></i> <%=propLang.get("tick_canc")%></button>                    
										              <button type="button" id="Save" class="btn btn-default" onclick="FidelitySave()"><i class="fa fa-save" aria-hidden="true"></i> <%=propLang.get("tick_save")%></button>
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

	if (document.getElementById("fidName").value == ""){
		alertify.log("Name Obbligatorio");
		document.getElementById("fidName").focus();
		return;
	}
	
	if (document.getElementById("fidSurname").value == ""){
		alertify.log("Surname Obbligatorio");
		document.getElementById("fidSurname").focus();
		return;
	}	
	
	if (document.getElementById("fidcodfid").value == ""){
		alertify.log("Fideli Card Obbligatorio");
		document.getElementById("fidcodfid").focus();
		return;
	}
	
	if (document.getElementById("fidBirth").value == ""){
		alertify.log("Data nascita Obbligatorio");
		document.getElementById("fidBirth").focus();
		return;
	}	
	
/*	
	if (document.getElementById("fidCfis").value == ""){
		alertify.log("Codice Fiscale Obbligatorio");
		document.getElementById("fidCfis").focus();
		return;
	}else{
		var codfis = validaCodiceFiscale(document.getElementById("fidCfis").value);
		if (codfis == false){
			alertify.log("Controllare Codice Fiscale");
			document.getElementById("fidCfis").focus();
			return;		
		}
	}	

	
	if (document.getElementById("fidBirth").value == ""){
		alertify.log("Data nascita Obbligatorio");
		document.getElementById("fidBirth").focus();
		return;
	}	
	
	
	if (document.getElementById("fidStatus").value == ""){
		alertify.log("Status Obbligatorio");
		document.getElementById("fidStatus").focus();
		return;
	}		
	
	
	if (document.getElementById("fidMail").value != ""){
		var email = validEmail(document.getElementById("fidMail").value);
		if (email == false){
			document.getElementById("fidMail").focus();
			return;		
		}
	}else{
		alertify.log("Mail Obbligatorio");
		document.getElementById("fidMail").focus();
		return;	
	}
*/
		$.ajax({
			url: '../../SetFidelitycard',
			data : {
				nam : $("#fidName").val(),
				sur : $("#fidSurname").val(),
				sex : $("#fidSex").val(),
				add : $("#fidAddress").val(),
				tel : $("#fidTel").val(),
				ema : $("#fidMail").val(),
				fis : $("#fidCfis").val(),
				cat : $("#fidCategory").val(),
				sta : $("#fidStatus").val(),
				bir : $("#fidBirth").val(),
				com : $("#fidCity").val(),
				cof : $("#fidcodfid").val()
			},
			success : function(responseText) {
				if (responseText == "OK"){
					location.href="./fidelity-card.htm";
				}else{
					alertify.error("Codice Fidelity Card già presente in Archivio");
				}
			}
		
		});

}

function validEmail(emailStr){
	
    var emailPat = /^(.+)@(.+)$/;
    var specialChars = "\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
    var validChars = "[^\\s" + specialChars + "]";
    var quotedUser = "(\"[^\"]*\")";
    var ipDomainPat = /^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
    var atom = validChars + "+";
    var word = "(" + atom + "|" + quotedUser + ")";
    var userPat = new RegExp("^" + word + "(\\." + word + ")*$");
    var domainPat = new RegExp("^" + atom + "(\\." + atom + ")*$");
    var matchArray = emailStr.match(emailPat);
    if (matchArray == null) {
    	alertify.log("L'email sembra essere sbagliata: (controlla @ e .)");
        return false;
    }
    var user = matchArray[1];
    var domain = matchArray[2];
    if (user.match(userPat) == null) {
    	alertify.log("La parte dell'email prima di '@' non sembra essere valida!");
        return false;
    }
    var IPArray = domain.match(ipDomainPat);
    if (IPArray != null) {
        for (var i = 1; i <= 4; i++) {
            if (IPArray[i] > 255) {
            	alertify.log("L'IP di destinazione non è valido!");
                return false;
            }
        }
        return true;
    }
    var domainArray = domain.match(domainPat);
    if (domainArray == null) {
    	alertify.log("La parte dell'email dopo '@' non sembra essere valida!");
        return false;
    }
    var atomPat = new RegExp(atom, "g");
    var domArr = domain.match(atomPat);
    var len = domArr.length;
    if (domArr[domArr.length - 1].length < 2 || domArr[domArr.length - 1].length > 6) {
    	alertify.log("Il dominio di primo livello (es: .com e .it) non sembra essere valido!");
        return false;
    }
    if (len < 2) {
        var errStr = "L'indirizzo manca del dominio!";
        alertify.log(errStr);
        return false;
    }
    
    return true;
}


function validaCodiceFiscale(cf){
    var validi, i, s, set1, set2, setpari, setdisp;
    if( cf == '' )  return '';
    cf = cf.toUpperCase();
    if( cf.length != 16 )
        return false;
    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for( i = 0; i < 16; i++ ){
        if( validi.indexOf( cf.charAt(i) ) == -1 )
            return false;
    }
    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
    s = 0;
    for( i = 1; i <= 13; i += 2 )
        s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
    for( i = 0; i <= 14; i += 2 )
        s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
    if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) )
        return false;
    return true;
}

options = {
		changeMonth: true,
		changeYear: true,
		yearRange: '1950:2100',
		closeText: 'Chiudi',
		prevText: 'Prec',
		nextText: 'Succ',
		currentText: 'Oggi',
		monthNames: ['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
		monthNamesShort: ['Gen','Feb','Mar','Apr','Mag','Giu', 'Lug','Ago','Set','Ott','Nov','Dic'],
		dayNames: ['Domenica','Lunedì','Martedì','Mercoledì','Giovedì','Venerdì','Sabato'],
		dayNamesShort: ['Dom','Lun','Mar','Mer','Gio','Ven','Sab'],
		dayNamesMin: ['Do','Lu','Ma','Me','Gio','Ve','Sa'],
		dateFormat: 'dd-mm-yy', 
		firstDay: 1,
		isRTL: false	
}

$( function() {	
		$("#fidBirth").datepicker(options);	
} );

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
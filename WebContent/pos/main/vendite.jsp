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
  
  <title>Vendite | <%=propLang.get("APPS") %></title>
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
        	<!-- 
               <aside class="aside-md bg-dark nav-xs" id="sidebar2"> 
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover" style="margin-bottom:40px">
                    
						<%//@ include file="../../inc/smart-menu-left.jsp" %>

                    </section>
                  </section>
                </aside>
                <!--  -->        
        <!-- /.aside -->
        <section id="content">
          <section class="hbox stretch">         
          <%@ include file="../../inc2/menuApp8.jsp" %> 
            <section>
              <section class="vbox">
                <section class="scrollable padder" id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="Vendite" %></h2>

		              		
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

								<div id="div1">
									<table width="100%">
										<tr><td width="100%" align="center" colspan="3"><span style="font-weight: bold"><%=propLang.get("tick_stor") %></span></td></tr>
										<tr>
											<td width="100%"><%=propLang.get("tick_stor") %></td>
										</tr>
										<tr>
											<td width="100%">
					              				<select id="codstore" class="form-control" style="width: 98%;" onchange="camb()">
													<option value="">-- Seleziona Store --</option>
													${ComboCli}
												</select>
										    </td>
										</tr>										
										<tr><td width="100%" align="center" colspan="3">&nbsp;</td></tr>
									</table>
								</div>										
								<div id="div2">
									<table width="100%">
										<tr><td width="100%" align="center" colspan="3"><span style="font-weight: bold"><%=propLang.get("tick_week") %></span></td></tr>
										<tr>
											<td width="33%"><%=propLang.get("tick_year") %></td>
											<td width="33%"><%=propLang.get("tick_week") %></td>
											<td width="34%">&nbsp;</td>
										</tr>
										<tr>
											<td width="33%">
												<select id="year" class="form-control" style="width: 98%;" onchange="SelMonthsWeek(this)">
													<option value="2018">2018</option>
													<option value="2019">2019</option>
													<option value="2020">2020</option>
													<option value="2021">2021</option>
												</select>
											</td>
											<td width="33%">
												<select id="week" class="form-control" style="width: 98%;" onChange="SelDate(this)">
														${StoresDate}
												</select>
											</td>
											<td width="34%" align="center">
												<input type="button" onclick="SelCarica()" id="vendutoBtn" class="btn btn-default" value="<%=propLang.get("tick_uplo") %>" />
											</td>
										</tr>										
									</table>
								</div>
								
								<div id="div3" style="display: none;">
									<table width="100%">
									<tr><td width="100%" align="center" colspan="3"><span style="font-weight: bold"><%=propLang.get("tick_mont") %></span></td></tr>
										<tr>
											<td width="33%"><%=propLang.get("tick_year") %></td>
											<td width="33%"><%=propLang.get("tick_mont") %></td>
											<td width="34%">&nbsp;</td>
										</tr>
										<tr>
											<td width="33%">
												<select id="year2" class="form-control" style="width: 98%;" onchange="SelMonthsWeek(this)">
													<option value="2018">2018</option>
													<option value="2019">2019</option>
													<option value="2020">2020</option>
													<option value="2021">2021</option>
												</select>
											</td>
											<td width="33%">
												<select id="month" class="form-control" style="width: 98%;" onChange="SelDate2(this)">
														<option value="1" value2="01-01" value3="31-01"><%=propLang.get("gen") %></option>
														<option value="2" value2="01-02" value3="28-02"><%=propLang.get("feb") %></option>
														<option value="3" value2="01-03" value3="31-03"><%=propLang.get("mar") %></option>
														<option value="4" value2="01-04" value3="30-04"><%=propLang.get("apr") %></option>
														<option value="5" value2="01-05" value3="31-05"><%=propLang.get("mag") %></option>
														<option value="6" value2="01-06" value3="30-06"><%=propLang.get("giu") %></option>
														<option value="7" value2="01-07" value3="31-07"><%=propLang.get("lug") %></option>
														<option value="8" value2="01-08" value3="31-08"><%=propLang.get("ago") %></option>
														<option value="9" value2="01-09" value3="30-09"><%=propLang.get("set") %></option>
														<option value="10" value2="01-10" value3="31-10"><%=propLang.get("ott") %></option>
														<option value="11" value2="01-11" value3="30-11"><%=propLang.get("nov") %></option>
														<option value="12" value2="01-12" value3="31-12"><%=propLang.get("dic") %></option>
												</select>
											</td>
											<td width="34%" align="center">
												<input type="button" onclick="SelCarica()" id="vendutoBtn" class="btn btn-default" value="<%=propLang.get("tick_uplo") %>" />
											</td>
										</tr>										
									</table>
								</div>								

								<div id="div4" style="display: none;">
									<table width="100%">
									<tr><td width="100%" align="center" colspan="3"><span style="font-weight: bold"><%=propLang.get("tick_per") %></span></td></tr>
										<tr>
											<td width="33%"><%=propLang.get("tick_perf") %></td>
											<td width="33%"><%=propLang.get("tick_pert") %></td>
											<td width="34%">&nbsp;</td>
										</tr>
										<tr>
											<td width="33%">
												<input type="text" id="datestore" class="form-control" style="width: 98%;" readonly>
											</td>
											<td width="33%">
												<input type="text" id="datestorea" class="form-control" style="width: 98%;" readonly>
											</td>
											<td width="34%" align="center">
												<input type="button" onclick="SelCarica()" id="vendutoBtn" class="btn btn-default" value="CARICA"  />
											</td>
										</tr>										
									</table>
								</div>		
								
								
								
								
	  					
		  				  </div>
							</div>
					<div class="row" id="ticket_list" style="margin-top: 50px;">

			              <div class="table-responsive" id="ticketResp1_Dot">
			              	<table id="test" class="table table-striped m-b-none" width="100%"></table>
		  				  </div>

					</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>

		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>               
               
                 <aside class="aside-md bg-dark nav-xs" id="sidebar2"> 
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover" style="margin-bottom:40px">
                    
						<%@ include file="../../inc/smart-menu-right-pos.jsp" %>

                    </section>
                    
                    <footer class="footer footer-md bg-light dker">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                      
	                        <span class="input-group-btn">
	                          <a href="javascipt:void;" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></a>
	                        </span>
	                        <input type="text" id="search_input" class="form-control input-sm bg-empty b-b b-dark no-border" placeholder="Search Vendite"> 
	                      
	                      </div>
	                    </div>
                    </footer>
                    
                  </section>
                </aside>
                
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

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <!-- fine pietro -->
  <script>
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
	    $("#datestore").datepicker(options);
	    $("#datestorea").datepicker(options);
	} );  
/**/
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();

if(dd<10) {
    dd = '0'+dd
} 

if(mm<10) {
    mm = '0'+mm
} 

today = dd + '-' + mm + '-' + yyyy;

document.getElementById("datestore").value = today;
document.getElementById("datestorea").value = today;
/**/
  </script>
    <script>
function oper(tab,flag,id) {
	
	if (flag == "E"){
		
		alertify.set({ labels: {
		    ok     : "<%=propLang.get("sm_action_ok") %>",
		    cancel : "<%=propLang.get("sm_action_ko") %>"
		} });
		
		alertify.confirm("<%=propLang.get("sm_action_action") %>", function (e) {
		    if (e) {
		    	
				$.ajax({
					url : "../../smcontrol",
					data : {
						tab : tab,
						flag : flag,
						id : id
					},
					success : function(responseText) {
						alertify.success(responseText);
						setTimeout(function(){ location.href="smartcontrol-customers.htm"; }, 2000);
					}
				});
				
		    } else {
		    	alertify.log("<%=propLang.get("sm_az_no") %>");
		    }
		});
	}
	

		/*
		$.ajax({
			url : "../../smcontrol",
			data : {
				tab : tab,
				flag : flag,
				id : id
			},
			success : function(responseText) {
				alertify.success(responseText);
				setTimeout(function(){ location.href="smartcontrol-customers.htm"; }, 2000);
			}
		});
		*/
}


</script>
<script>
function reloads(){
	location.reload();
}

setInterval(reloads, 600000);

var International = "2";
function Pages(inte){
	
	International = inte;
	
	switch(inte) {
    case "2":
    	document.getElementById("div2").style.display = "inline";
    	document.getElementById("div3").style.display = "none";
    	document.getElementById("div4").style.display = "none";
        break;
    case "3":
    	document.getElementById("div3").style.display = "inline";
    	document.getElementById("div2").style.display = "none";
    	document.getElementById("div4").style.display = "none";        
        break;
    case "4":
    	document.getElementById("div4").style.display = "inline";
    	document.getElementById("div3").style.display = "none";
    	document.getElementById("div2").style.display = "none";
        break;
}
	
	document.getElementById("ticket_list").style.display = "none";
	
}

var DateFrom;
var DateTo;
function SelDate(iDate){

	var x = iDate.options[iDate.selectedIndex].getAttribute("value2");
	var y = iDate.options[iDate.selectedIndex].getAttribute("value3");
	DateFrom = x;
	DateTo = y;

}

function SelDate2(iDate){

	var x = iDate.options[iDate.selectedIndex].getAttribute("value2")+"-"+$("#year2").val();
	var y = iDate.options[iDate.selectedIndex].getAttribute("value3")+"-"+$("#year2").val();
	DateFrom = x;
	DateTo = y;

}



function SelCarica(){

	if (International == "2"){
		var iDate = document.getElementById("week");
		DateFrom =  iDate.options[iDate.selectedIndex].getAttribute("value2");
		DateTo =  iDate.options[iDate.selectedIndex].getAttribute("value3");
	}	

	if (International == "3"){
		var iDate = document.getElementById("month");
		DateFrom = iDate.options[iDate.selectedIndex].getAttribute("value2")+"-"+$("#year2").val();
		DateTo = iDate.options[iDate.selectedIndex].getAttribute("value3")+"-"+$("#year2").val();
	}	

	if (International == "4"){
		
		DateFrom = $("#datestore").val();
		DateTo = $("#datestorea").val();
		
		
		if (document.getElementById("datestore").value > document.getElementById("datestorea").value){
			alertify.log("Il campo 'Periodo Da' non può essere maggiore di 'Periodo A'");
			document.getElementById("datestore").focus();
			return;
		}
		
	}	
	
	
	if (document.getElementById("codstore").value == ""){
		alertify.log("Campo Store Obbligatorio");
		document.getElementById("codstore").focus();
		return;
	}
	
	

	document.getElementById("ticket_list").style.display = "inline";
	
	var codstore = document.getElementById("codstore").value;
	var datestore = document.getElementById("datestore").value;
	var datestorea = document.getElementById("datestorea").value;
 	var head = "";
 	
	document.getElementById("ticket_list").style.visibility= "visible";
	document.getElementById("ticket_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
	

	var urls = "../../GetVenduto?client="+codstore+"&data="+DateFrom+"&dataa="+DateTo;

	$.getJSON(urls, function (data) {

		var lista = data.listings;

		  	var idclient = "";
		  	var giorno = "";
		  	var codart = "";
		  	var codartalt = "";
		  	var parte = "";
		  	var codcol = "";
		  	var variante = "";
		  	var pezzi = "";
		  	var valore = "";
		  	
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";

		 	$.each(lista, function(index, lista) {
		 		
		 		idclient = lista.Idclient;
		 		giorno = lista.Giorno;
		 		codart = lista.Codart;
		 		//codartalt = "888888";//lista.Codartalt;
		 		codartalt = lista.Codartalt;
		 		parte = lista.Parte;
		 		codcol = lista.Codcol;
		 		variante = lista.Variante;
		 		pezzi = lista.Pezzi;
		 		valore = lista.Valore;


		 		if (testdata_row == ""){
		 			testdata_row = '{"giorno":"'+giorno+'","codart":"'+codart+'","codartalt":"'+codartalt+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","pezzi":"'+pezzi+'","valore":"'+valore+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"giorno":"'+giorno+'","codart":"'+codart+'","codartalt":"'+codartalt+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","pezzi":"'+pezzi+'","valore":"'+valore+'"}';
		 		}

			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 
		 	document.getElementById("ticket_list").innerHTML = "<table id='test' class='table table-striped m-b-none' width='100%'></table>";
		 
	 		var head = "<thead><th width='15%'><%=propLang.get("tick_data") %></th><th width='15%'><%=propLang.get("tick_art") %></th><th width='15%'><%=propLang.get("tick_alt") %></th><th width='10%'><%=propLang.get("tick_part") %></th><th width='10%'><%=propLang.get("tick_col") %></th><th width='10%'><%=propLang.get("tick_var") %></th><th width='10%'><%=propLang.get("tick_pric") %></th><th width='10%'><%=propLang.get("tick_val") %></th></thead>";
	 		
	 		document.getElementById("test").innerHTML = head;

	 		$('#test').dataTable({
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
	 		        "mDataProp": "giorno"
	 		    }, {
	 		        "mDataProp": "codart"
	 		    }, {
	 		        "mDataProp": "codartalt"
	 		    }, {
	 		        "mDataProp": "parte"
	 		    }, {
	 		        "mDataProp": "codcol"
	 		    }, {
	 		        "mDataProp": "variante"
	 		    }, {
	 		        "mDataProp": "pezzi"
	 		    }, {
	 		        "mDataProp": "valore"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});

		});
	
}

function SelMonthsWeek(ye){

var yea = ye.value; 
	
$.ajax({
	url : "../../GetMonthWeek",
	data : {
		year : yea
	},
	success : function(responseText) {
		var flap = responseText.split("$");
		document.getElementById("month").innerHTML = flap[1];
		document.getElementById("week").innerHTML = flap[0];
	}
});
	
}

Date.prototype.getWeek = function() {
	  var jan4th = new Date(this.getFullYear(),0,4);
	  return Math.ceil((((this - jan4th) / 86400000) + jan4th.getDay()+1)/7);
	  
}

var now = new Date();
var weekNumber = now.getWeek();
var month = now.getMonth() + 1;

document.getElementById("week").value = weekNumber;
document.getElementById("month").value = month;
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
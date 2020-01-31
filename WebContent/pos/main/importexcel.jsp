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
  
  <title>Import Excel | <%=propLang.get("APPS") %></title>
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
                      
		                <h2 class="font-thin m-b"><%="Import File Excel" %></h2>

		              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<%="Import File Excel" %>
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive">

								<div id="div1">
									<table width="100%">
										<!-- <tr><td width="100%" align="center" colspan="3"><span style="font-weight: bold">File</span></td></tr> -->
										<tr>
											<td width="100%"><%=propLang.get("tick_sefi") %></td>
										</tr>
										<tr>
											<td width="50%">
											<input type="file" class="form-control" id="sortpicture" style="width: 50%;" name="sortpic" onchange="FileNameUpload();">
<!-- 
							                <label class="input-group-btn">
							                    <span class="btn btn-default">
							                        Browse<input type="file" style="display: none;" class="form-control" id="sortpicture" name="sortpic" onchange="FileNameUpload();" disabled>
							                    </span>
							                </label>

							                
							                <input type="text" class="form-control" style="width: 50%;" id="brandlogo" readonly="" disabled>							                
-->
										    </td>
										    <td width="50%"><button id="upload" class="btn btn-default saves" onclick="upLoadImage();"><i class="fa fa-upload"></i> <%=propLang.get("tick_uplo") %></button></td>
										</tr>										
										<tr><td width="100%" align="center" colspan="3">&nbsp;</td></tr>
									</table>
								</div>										
							
								
					<div class="row" id="ticket_list" style="margin-top: 50px;">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<i class="fa fa-list-alt" aria-hidden="true"></i> <%="Cod. Art List" %>
							</div>
							<!-- /.panel-heading -->
							

			              <div class="table-responsive" id="ticketResp1_Dot">
			              	<table id="test" class="table table-striped m-b-none" width="100%"></table>
		  				  </div>
							
							<!-- /.panel-body -->
						</div>
								</div>
					</div>	  
					
										
		  				  </div>
							</div>
							<div style="float: left; display: none; top-margin: 150px;" id="opers">
								<button onclick="location.reload();" class="btn btn-default saves" ><%=propLang.get("tick_canc") %></button>&nbsp;
								<button onclick="Insert()" class="btn btn-default saves" ><%=propLang.get("tick_uplo") %></button>
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
					</div>

		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>               
               
                <!-- side content -->
	            <aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 25px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="view-import.htm" style="padding-left: 25px" class="list-group-item active">
                          <i class="fa fa-list-alt"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%="List Import" %></span>
                        </a>

						
                      </div>
	                </section>

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
function FileNameUpload(){
	var fileupl = document.getElementById('sortpicture').value;
	fileupl = fileupl.replace("C:\\fakepath\\","");
	//document.getElementById('brandlogo').value = fileupl;
}

var Auploaded = false;
var FileRecords = "";
var FileRecords2 = "";
function upLoadImage(){

	if ($("#sortpicture").val() != ""){
	    var file_data = $("#sortpicture").prop("files")[0];   
	    var form_data = new FormData();                  
	    form_data.append("file", file_data);                      
	    $.ajax({
	                url: "../../FileUpload", // point to server-side PHP script 
	                dataType: "text",  // what to expect back from the PHP script, if anything
	                cache: false,
	                contentType: false,
	                processData: false,
	                data: form_data,                         
	                type: "post",
	                success: function(php_script_response){
	                	//var root = "C:/xampp/tomcat/webapps/SmartAssets/images/";
	                	var resp = $("#sortpicture").val();
	                	resp = resp.replace("C:\\fakepath\\","");
	                    var respDiv = "File-Image: "+resp+" Successfully Uploaded.";
	                    //$("#FileUploadResponse").html(respDiv);
	                    Auploaded = true;
	                    alertify.success(respDiv);
	                    var cells = "";
	                    FileRecords = replaceAll(php_script_response, "$", "-");
	                    FileRecords2 = replaceAll(php_script_response, "$", "','");
	                    document.getElementById("opers").style.display = "inline";
	                    LoadsTables();
	                    /*
	                    var rows = php_script_response.split("$");
	                    for(var i = 0; i < rows.length; i++){
	                    	cells += "<tr><td style='width: 100%;'>"+rows[i]+"</td></tr>";
	                    }
	                    
	                    document.getElementById("test").innerHTML = cells; 
	                    
	                    */

						/**/
	                }
	     });
	}else{
		alertify.log("Selezionare un file");
		return;
	}
	

}

function replaceAll(str, cerca, sostituisci) {
	  return str.split(cerca).join(sostituisci);
}

function Insert(){
	
	$.ajax({
		url : "../../CodArtInsert",
		data : {
			rec : FileRecords
		},
		success : function(responseText) {
			if (responseText == "OK"){
				responseText = "Inserimento avvenuto con successo";
			}else{
				responseText = "Problema inserimento articoli";
			}
			alertify.log(responseText);
		}
	});
	
}

function LoadsTables(){
	
	document.getElementById("ticket_list").style.display = "inline";
	 	
	document.getElementById("ticket_list").style.visibility= "visible";
	document.getElementById("ticket_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";

	
	var urls = "../../GetCodArticles?codart="+FileRecords2;
	$.getJSON(urls, function (data) {

		var lista = data.listings;

		  	var id = "";
		  	var codalt = "";
		  	var ref = "";
		  	var nam = "";
		  	var pri = "";
		  	var cat = "";
		  	var codcol = "";
		  	var taglia = "";
		  	var parte = "";		  	
		  	var variante = "";
		  	
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";

		 	$.each(lista, function(index, lista) {
		 		
		 		id = lista.Id;
		 		codalt = lista.Codalt;
		 		ref = lista.Reference;
		 		nam = lista.Name;
		 		pri = lista.Pricesell;
		 		cat = lista.Category;
		 		codcol = lista.Codcol;
		 		taglia = lista.Taglia;
		 		parte = lista.Parte;
		 		variante = lista.Variante;

		 		if (testdata_row == ""){
		 			testdata_row = '{"id":"'+id+'","codalt":"'+codalt+'","ref":"'+ref+'","nam":"'+nam+'","pri":"'+pri+'","cat":"'+cat+'","codcol":"'+codcol+'","taglia":"'+taglia+'","parte":"'+parte+'","variante":"'+variante+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"id":"'+id+'","codalt":"'+codalt+'","ref":"'+ref+'","nam":"'+nam+'","pri":"'+pri+'","cat":"'+cat+'","codcol":"'+codcol+'","taglia":"'+taglia+'","parte":"'+parte+'","variante":"'+variante+'"}';
		 		}

			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 
		 	document.getElementById("ticket_list").innerHTML = "<table id='test' class='table table-striped m-b-none' width='100%'></table>";
		 
	 		var head = "<thead><th width='10%'>ID</th><th width='10%'><%=propLang.get("tick_alt") %></th><th width='10%'><%=propLang.get("tick_ref") %></th><th width='10%'><%=propLang.get("tick_name") %></th><th width='10%'><%=propLang.get("tick_pric") %></th><th width='10%'><%=propLang.get("tick_cate") %></th><th width='10%'><%=propLang.get("tick_col") %></th><th width='10%'><%=propLang.get("tick_size") %></th><th width='10%'><%=propLang.get("tick_part") %></th><th width='10%'><%=propLang.get("tick_var") %></th></thead>";
	 		
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
	 		        "mDataProp": "id"
	 		    }, {
	 		        "mDataProp": "codalt"
	 		    }, {
	 		        "mDataProp": "ref"
	 		    }, {
	 		        "mDataProp": "nam"
	 		    }, {
	 		        "mDataProp": "pri"
	 		    }, {
	 		        "mDataProp": "cat"
	 		    }, {
	 		        "mDataProp": "codcol"
	 		    }, {
	 		        "mDataProp": "taglia"
	 		    }, {
	 		        "mDataProp": "parte"
	 		    }, {
	 		        "mDataProp": "variante"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});

		});
	
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
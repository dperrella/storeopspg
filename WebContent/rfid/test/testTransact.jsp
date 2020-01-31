<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  
  <title><%="Test Transact" %> | Smart-Suite</title>
  <link rel="shortcut icon" href="../../images/logos.ico">
  <meta name="description" content="ZPL" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css" type="text/css" />  
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

#KO
{
color: #CD5C5C;
}

#OK
{
color: #228B22;
}

#WARNING
{
color: #FFD700;
}

#loading_screen
{  
  display: none;
  position: absolute;
  left: 0px;
  top: 0px;
  height: 100%;
  width: 100%;
  background-color: gray;
  color: white;  
  text-align: center;
  padding-top: 100px;
  opacity: 0.9;
  filter: alpha(opacity=50);
  z-index: 999;
}
.alt{
height: 117px;
}
</style>  
<script type="text/javascript">
(function(){   
  if (window.addEventListener)
  {
    window.addEventListener("load", nascondi_loading_screen, false);    
  }else{
    window.attachEvent("onload", nascondi_loading_screen);
  }
})();
function mostra_loading_screen()
{
  document.getElementById("loading_screen").style.display = 'block';
}
function nascondi_loading_screen()
{
  document.getElementById("loading_screen").style.display = 'none';
}
</script>
</head>
<body class="" onload="Controlla();">
<form id="form1" method="post" action="smartcontrol.htm" role="Control"></form>
<div id="loading_screen">
  <h1><img src="../../images/loading.gif"/></h1> 
  <p><%=propLang.get("sm_caricamento_control") %><br/></p>
</div>
<script type="text/javascript">
mostra_loading_screen();
</script>
  <section class="vbox">
    <header class="bg-white-only header header-md navbar navbar-fixed-top-xs">
<div class="navbar-header aside bg-danger nav-xs">
  <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
    <i class="icon-list"></i>
  </a>
  <a href="../../app/main/index.htm" class="navbar-brand text-lt">
    <img src="../../images/altsix.png" alt="SmartSuite" style="width:32px">
    <span class="hidden-nav-xs m-l-sm">SmartSuite</span>
  </a>
  <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".user">
    <i class="icon-settings"></i>
  </a>
</div>      

<ul class="nav navbar-nav hidden-xs">
  <li>
    <a href="#nav,.navbar-header" data-toggle="class:nav-xs,nav-xs" style="padding-left: 28px;padding-right:28px" class="text-muted">
      <i class="fa fa-indent text"></i>
      <i class="fa fa-dedent text-active"></i>
    </a>
  </li>
</ul>
      
      <!-- <form method="post" action="../../app/main/ricerca.htm" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">-->
      <form method="post" action="#" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">
        <div class="form-group">
          <div class="input-group">
          <!--  
            <span class="input-group-btn">
              <button type="button" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
            </span>
            <input type="text" name="myInput" id="myInput" class="form-control input-sm no-border rounded" style="width: 300%" placeholder="<%//=propLang.get("cerca_smc") %>..."> 
          -->
          </div>
        </div>
      </form>
      <div class="navbar-right ">
        <ul class="nav navbar-nav m-n hidden-xs nav-user user">
          
          <%//=session.getAttribute("notif") %>
          
          <li class="dropdown">
            <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
              <span class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
                <img src="../../images/${foto}" alt="...">
              </span>${username}<b class="caret"></b>&nbsp;&nbsp;&nbsp;&nbsp;            
            </a>
            <ul class="dropdown-menu animated fadeInRight" style="margin-right: 15px;">
                       
              <li>
                <a href="../../app/main/profilo.htm"><%=propLang.get("profilo") %></a>
              </li>
              <!--
              <li>
                <a href="../main/notifiche.htm">
                  <span class="badge bg-danger pull-right"><%//=session.getAttribute("numNotif") %></span>
                  <%//=propLang.get("notifiche") %>
                </a>
              </li>
              -->
              <li>
                <a href="../../app/main/docs.html"><%=propLang.get("guida") %></a>
              </li>
              <li class="divider"></li>
              <li>
                <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal"><%=propLang.get("esci") %></a>
              </li>
            </ul>
          </li>
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
  
            <footer class="footer hidden-xs no-padder text-center-nav-xs">
              <div class="bg hidden-xs ">
                  <div class="dropdown dropup wrapper-sm clearfix">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                      <span class="thumb-sm avatar pull-left m-l-xs">                        
                        <img src="../../images/${foto}" class="dker" alt="...">
                        <i class="on b-black"></i>
                      </span>
                      <span class="hidden-nav-xs clear">
                        <span class="block m-l">
                          <strong class="font-bold text-lt">${username}</strong> 
                          <b class="caret"></b>
                        </span>
                        <span class="text-muted text-xs block m-l">${desc}</span>
                      </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight aside text-left">                      
                      
                      <li>
                        <a href="../../app/main/profilo.htm"><%=propLang.get("profilo") %></a>
                      </li>
                      <!-- 
                      <li>
                        <a href="../main/notifiche.htm">
                          <span class="badge bg-danger pull-right"><%//=session.getAttribute("numNotif") %></span>
                          <%//=propLang.get("notifiche") %>
                        </a>
                      </li>
                      -->
                      <li>
                        <a href="../../app/main/docs.html"><%=propLang.get("guida") %></a>
                      </li>
                      <li class="divider"></li>
                      <li>
                        <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" ><%=propLang.get("esci") %></a>
                      </li>
                    </ul>
                  </div>
                </div>            
			</footer>
			
          </section>
        </aside>

        <section id="content">
          <section class="hbox stretch">          
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="Test Transact" %></h2>
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold" style="text-align: left;">
			                <span id="Testata_" style="text-align: left; font-weight: bold;"><%="Test Transact" %></span>
			              </header>

<!-- inizio panel red -->
			              <section class="panel wrapper"  id="subContent">
			              	<div class="subnav" >
		              		
					<div class="row">
								<div class="col-lg-12">

							<!-- /.panel-heading -->
							<div class="panel-body">
						

			              <div class="table-responsive"  id="ticket_list">
			                <table class="table table-striped m-b-none" id="test"></table>
		  				  </div>
		  				  									  				  
							</div>

								</div>
								
							
							<!--  -->
															
							<!--  -->	
								
					</div>
					
			              		
							</div>
						  </section>

		  				
		  				</section>
		  				
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
<input type="hidden" id="idflow">
<input type="hidden" id="idstep">
<script>
/**/
function Controlla(){
	
var conts = 0;	

document.getElementById("idflow").value = "6";
document.getElementById("idstep").value = "5";

document.getElementById("ticket_list").style.display = "inline";
	
document.getElementById("ticket_list").style.visibility= "visible";
document.getElementById("ticket_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";

//var urls = "http://intra.morato.it:3213/Concentratore/GetTickets?client="+codstore+"&data="+datestore+"&soglia="+numstore;
var urls = "http://localhost:8080/SmartSuite/rfid/ws/ReadDoc?idflow="+document.getElementById("idflow").value+"&idstep="+document.getElementById("idstep").value;

$.getJSON(urls, function (data) {

	var lista = data.listings;
/**/
 var link = "";
 var btn = "";
/**/
	  	var docnum = "";
	  	var docref_destination = "";
	  	var origin_description = "";
	  	var id_flow = "";
	  	var id_step = "";
	  	var destination_description = "";
	  	var docdate = "";
	  	var type_description = "";
	  	
	  	var testdata_ini = "";
	  	var testdata_fin = "";
	  	
	  	var testdata_ini = "[";
 		var testdata_fin = "]";
 		var testdata_row = "";

	 	$.each(lista, function(index, lista) {
	 		
	 		docnum = lista.docnum;
	 		docref_destination = lista.docref_destination;
	 		origin_description = lista.origin_description;
	 		id_flow = lista.id_flow;
	 		id_step = lista.id_step;
	 		destination_description = lista.destination_description;
	 		docdate = lista.docdate;
	 		type_description = lista.type_description;
	 		
	 		
	 		if (docref_destination == "" || docref_destination == null){
	 			link = "http://localhost:8080/SmartSuite/rfid/ws/PushDocsTransact?idflow="+id_flow+"&idstep="+id_step+"&docnum="+docnum;
	 			btn = "PUSH";
	 		}else{
	 			link = "http://localhost:8080/SmartSuite/rfid/ws/CheckDocsTransact?idflow="+id_flow+"&idstep="+id_step+"&docnum="+docnum;
	 			btn = "&nbsp;&nbsp;GET&nbsp;&nbsp;";	 			
	 		}
	 		
	 		emptys = "<div class='btn-group'> "+
	            		 "<a class='btn btn-default' id='a_"+conts+"' onclick=Refr(this,'"+link+"') > "+btn+"</a>"+
        			 "</div>";


	 		if (testdata_row == ""){
	 			testdata_row = '{"docnum":"'+docnum+'","id_flow":"'+id_flow+'","id_step":"'+id_step+'","origin_description":"'+origin_description+'","destination_description":"'+destination_description+'","docdate":"'+docdate+'","type_description":"'+type_description+'","empty":"'+emptys+'"}';
	 		}else{
	 			testdata_row = testdata_row + ' , {"docnum":"'+docnum+'","id_flow":"'+id_flow+'","id_step":"'+id_step+'","origin_description":"'+origin_description+'","destination_description":"'+destination_description+'","docdate":"'+docdate+'","type_description":"'+type_description+'","empty":"'+emptys+'"}';
	 		}
	 		
	 		conts++;

		});

	 	//document.write(testdata_row)
	 	
	 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
	 	
	 	document.getElementById("ticket_list").innerHTML = "<table id='test' class='table table-striped m-b-none' width='100%'></table>";
	 	
 		var head = "<thead>"+
 				   "<th width='10%'>Doc Number</th>"+
 				   "<th width='10%'>Id Flow</th>"+
 				   "<th width='10%'>Id Step</th>"+
 				   "<th width='20%'>Origin</th>"+
 				   "<th width='20%'>Destination</th>"+
 				   "<th width='15%'>Date</th>"+
 				   "<th width='10%'>Type</th>"+
 				   "<th width='5%' align='center'>&nbsp;</th>"+
 				   "</thead>";
 		
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
 		        "mDataProp": "docnum"
 		    }, {
 		        "mDataProp": "id_flow"
 		    }, {
 		        "mDataProp": "id_step"
 		    }, {
 		        "mDataProp": "origin_description"
 		    }, {
 		        "mDataProp": "destination_description"
 		    }, {
 		        "mDataProp": "docdate"
 		    }, {
 		        "mDataProp": "type_description"
 		    }, {
 		        "mDataProp": "empty"
 		    }],	 		    
 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
 		    "bPaginate":true,
 		   	"bDestroy": true
 		});
 		

	});	 
		  


}	
/**/

function Refr(obj,str){

	document.getElementById(obj.id).style.pointerEvents = "none";
	document.getElementById(obj.id).style.backgroundColor = "#E1E1E1";

	$.ajax({
			url: str,
			async: false,
			success : function(responseText) {
				alertify.success("Operazione Effettuata");
				myVar = setTimeout(Controlla, 2000);						
			}
	});
} 
</script>
  </body>
</html>
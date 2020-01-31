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
ReadProperties propLang_ = new ReadProperties("connection.properties");
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  
  <title><%=propLang.get("proxyrules") %> | SmartSuite</title>
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
<body class="" onload="GetProxyRules();">
<form id="form1" method="post" action="smartcontrol.htm" role="Control"></form>
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
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
				<%@ include file="../../inc2/menuApp5.jsp" %>     
				         
                <section class="vbox">
	                <section class="scrollable padder" id="scrollable">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("proxyrules") %></h2>
		                
	
						

			              <div class="row" id="DivOccurrence">
			                
		  				  </div>
		  				  									  				  



		  				
		  				</section>
		  				  
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

<script src="../../js/orb/orb.min.js"></script>
<link rel="stylesheet" href="../../js/orb/orb.min.css">
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

/**/
var count = 0;
function GetProxyRules(){		

var testdata_ini = "[";
var testdata_fin = "]";
var testdata_row = "";	
	
var wsproxyrules = "<%=propLang_.get("wsproxyrules") %>";

$('#DivOccurrence')
   .empty()
   .append('');

$.getJSON(wsproxyrules, function (data) {
    var lista = data;		    
    
	$.each(lista, function(index, lista) {
		keys = lista.typefile_id;
		values = lista.import;
		
		if (values == true){
			values = "White List";
		}else{
			values = "Black List";
		}
		
		divname = 'div_'+count;

	    $('#DivOccurrence').append('<div class="col-sm-6"><section class="panel panel-default"><header class="panel-heading bg-light"><span class="hidden-sm"><b>'+keys.toUpperCase()+" &nbsp; - &nbsp; "+values.toUpperCase()+'</b></span></header><div id="'+divname+'" class="table table-striped m-b-none" width="100%"></div></section></div>');
	    var lista2 = lista.datas;
	    /**/
			$.each(lista2, function(index, lista2) {
				acc = lista2.account_id;
				sto = lista2.store_id;
				
		 		if (testdata_row == ""){
		 			testdata_row = '{"account_id":"'+acc+'","store_id":"'+sto+'"}';
		 		}else{
		 			testdata_row = testdata_row + ' , {"account_id":"'+acc+'","store_id":"'+sto+'"}';
		 		}
				
			 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
			 	
			 	document.getElementById(divname).innerHTML = "<table id='tab_"+divname+"' class='table table-striped m-b-none' width='100%'></table>";

			 	var head = "<thead><th width='30%'>Account Id</th><th width='70%'>Store Id</th></thead>";
		 		document.getElementById('tab_'+divname).innerHTML = head;
		 		$('#tab_'+divname).dataTable({
		 		      "language": {
		 		          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
		 		          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
		 		          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_ <%=" - " + propLang.get("nRec") %> _MAX_ ",
		 		          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
		 		          "infoFiltered": "(Filtrati da _MAX_ records totali)",
		 		          "decimal": ",",
		 		          "thousands": ".",
		 		          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
		 		      },
		 			"bProcessing": true,
		 			"aaData": testdata,
		 		    "aoColumns": [{
		 		        "mDataProp": "account_id"
		 		    }, {
		 		        "mDataProp": "store_id"
		 		    }],	 		    
		 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
		 		    "bPaginate":true,
		 		   	"bDestroy": true
		 		});
				
			});	    
	    /**/
		count++;
	});
});

}
/**/
function Controlla(){ 
	
	for (var i=0; i<=count; i++){
		if ($("#val_"+i).val() == ""){
			alertify.error("campo "+$("#key_"+i).val()+" è obbligatorio");
			return;
		}
	}

}	
</script>

  </body>
</html>

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
  
  <title>Fidelity Card | <%=propLang.get("APPS") %></title>
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
<body onload="FidelitySearch();">
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
                      
		                <h2 class="font-thin m-b"><%="Fidelity Card List" %></h2>
		                

		              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
							<%="Fidelity Card List" %>
							</div>
							<!-- /.panel-heading -->
	
							
						 <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="fidelity-card-new.htm" class="btn btn-default"><%=propLang.get("tick_fid_new")%></a></li>
								</ul>
							</div>
						  </section>							

			              <div class="table-responsive"  id="stock_list">
			                <table class="table table-striped m-b-none" id="stock"></table>
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
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 32px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu Fidelity</span>
				        </a>
				        
						<%@ include file="../../inc/fidelity-menu-right.jsp" %>
						
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
<!--  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>

<script src="http://www.jbPivot.org/js/vendor/jbpivot.min.js"></script>
<link rel="stylesheet" href="http://www.jbPivot.org/css/jbpivot.min.css">
-->
<script src="../../js/orb/orb.min.js"></script>
<link rel="stylesheet" href="../../js/orb/orb.min.css">

<script>
var _action = "<%=propLang.get("azioni")%>";
var _modify = "<%=propLang.get("Mod")%>";
var _delete = "<%=propLang.get("Eli")%>";
var _view = "<%=propLang.get("View")%>";
function FidelitySearch(){
	
	
	document.getElementById("stock_list").style.visibility= "visible";
	document.getElementById("stock_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading222.gif' width='79px' height='50px' /></div>";

	var urls = "../../GetFidelityList";

	$.getJSON(urls, function (data) {

		var list = data.listings;
	  		var idfid = "";
	  		var fidnam = "";
	  		var fidsur = "";
		  	var fidsex = "";
		  	var fidadd = "";
		  	var fidtel = "";
		  	var fidmai = "";
		  	var fidcfi = "";
		  	var fidcat = "";
		  	var fidsta = "";
		  	var fidcod = "";
		  	var fiddat = "";
		  	
		  	var storedata_ini = "";
		  	var storedata_fin = "";

		  	var storedata_ini = "[";
	 		var storedata_fin = "]";
	 		var storedata_row = "";
	 		
		 	$.each(list, function(index, list) {
		  		idfid = list.Idfidelity;
		  		fidnam = list.Fidalityname;
		  		fidsur = list.Fidelitysurname;
			  	fidsex = list.Fidelitysex;
			  	fidadd = list.Fidelityaddress;
			  	fidtel = list.Fidelitytel;
			  	fidmai = list.Fidelitymail;
			  	fidcfi = list.Fidelitycodfiscale;
			  	fidcat = list.Fidelitycategory;
			  	fidsta = list.Fidelitystatus;
			  	fidcod = list.Fidelitycode;
			  	fiddat = list.Fidelitydate;			  	
			  	
		 		emptys = "<div class='btn-group'> "+
		         "<button class='btn btn-default dropdown-toggle btn-sm' data-toggle='dropdown'> "+_action+" <span class='caret'></span></button> "+
		   		 "<ul class='dropdown-menu' style='margin-left: -100px;'> "+
		   		 "<li><a href='./fidelity-card-upd.htm?fidcard="+idfid+"'><i class='fa fa-external-link'></i> View</a></li> "+
		   		 "<li><a onclick=FidDel('"+idfid+"') style='cursor: pointer;'><i class='fa fa-times'></i> Delete</a></li> "+
		   		 "</ul> "+
		 		 "</div>";
				
		 		if (storedata_row == ""){
		 			storedata_row = '{"fidnam":"'+fidnam+'","fidsur":"'+fidsur+'","fidsex":"'+fidsex+'","fidadd":"'+fidadd+'","fidtel":"'+fidtel+'","fidmai":"'+fidmai+'","fidcfi":"'+fidcfi+'","fidcat":"'+fidcat+'","fidsta":"'+fidsta+'","fidcod":"'+fidcod+'","fiddat":"'+fiddat+'","emptys":"'+emptys+'"}';
		 		}else{
		 			storedata_row = storedata_row + ' , {"fidnam":"'+fidnam+'","fidsur":"'+fidsur+'","fidsex":"'+fidsex+'","fidadd":"'+fidadd+'","fidtel":"'+fidtel+'","fidmai":"'+fidmai+'","fidcfi":"'+fidcfi+'","fidcat":"'+fidcat+'","fidsta":"'+fidsta+'","fidcod":"'+fidcod+'","fiddat":"'+fiddat+'","emptys":"'+emptys+'"}';
		 		}
		 		
			});
	 		

		 	storedata = JSON.parse(storedata_ini + storedata_row + storedata_fin);
		 	document.getElementById("stock_list").innerHTML = "<table id='stock' class='table table-striped m-b-none' width='100%'></table>";
		 	

	 		var assetshead = "<thead><th width='9%'><%=propLang.get("tick_name")%></th><th width='8%'><%=propLang.get("tick_surn")%></th><th width='5%'><%=propLang.get("tick_sex")%></th><th width='15%'><%=propLang.get("tick_addr")%></th><th width='8%'><%=propLang.get("tick_phon")%></th> "+
	 		"<th width='10%'><%=propLang.get("tick_mail")%></th> "+
	 		"<th width='9%'><%=propLang.get("tick_fico")%></th> "+
	 		"<th width='7%'><%=propLang.get("tick_cate")%></th> "+
	 		"<th width='7%' style='text-align:center;'><%=propLang.get("tick_stat")%></th> "+
	 		"<th width='5%'><%=propLang.get("tick_code")%></th> "+
	 		"<th width='7%'><%=propLang.get("tick_data")%></th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"</thead>";
	 		
	 		//"aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
	 		document.getElementById("stock").innerHTML = assetshead;
	 		$('#stock').dataTable({
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
	 			"bProcessing": false,
	 			"aaData": storedata,
	 		    "aoColumns": [{
	 		        "mDataProp": "fidnam"
	 		    }, {
	 		        "mDataProp": "fidsur"
	 		    }, {
	 		        "mDataProp": "fidsex"
	 		    }, {
	 		        "mDataProp": "fidadd"
	 		    }, {
	 		        "mDataProp": "fidtel"
	 		    }, {
	 		        "mDataProp": "fidmai"
	 		    }, {
	 		        "mDataProp": "fidcfi"
	 		    }, {
	 		        "mDataProp": "fidcat"
	 		    }, {
	 		        "mDataProp": "fidsta"
	 		    }, {
	 		        "mDataProp": "fidcod"
	 		    }, {
	 		        "mDataProp": "fiddat"
	 		    }, {
	 		        "mDataProp": "emptys"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true,
                "iDisplayLength": 50
	 		});
					 	
	});

}

function FidDel(obj){
	
	
	alertify.set({ labels: {
	    ok     : "<%=propLang.get("action_ok") %>",
	    cancel : "<%=propLang.get("action_ko") %>"
	} });
	
	alertify.confirm("<%=propLang.get("action_action") %>", function (e) {
	    if (e) {


	    	$.ajax({
	    		url: '../../DelFidelitycard',
	    		data : {
	    			ids : obj
	    		},
	    		success : function(responseText) {
	    			if (responseText == "OK"){
	    				location.href="./fidelity-card.htm";
	    			}else{
	    				alert("Close with Problem");
	    			}
	    		}
	    	
	    	});
	    	
	    	
	    } else {
	    	alertify.log("<%=propLang.get("az_no") %>");
	    }
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
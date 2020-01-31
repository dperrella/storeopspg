<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
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
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
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
		z-index: 10000;
	}	
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
	.fixed {
	    position: absolute;
	}
	#ajaxModal {
		z-index: 10000;
	}
  </style>
</head>
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
            
			<%@ include file="../../inc2/leftFooter.jsp" %>
            
          </section>
        </aside>
        <!-- /.aside -->
        
        <section id="content">
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp5.jsp" %>
              
              	<!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b>Select Table</b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                	<li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('account_details', 'N', '')">
		                		<i class="fa fa-table"></i> account_details</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('aggr_kpi_data', 'N', '')">
	                        	<i class="fa fa-table"></i> aggr_kpi_data</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('dim_door_type', 'N', '')">
	                        	<i class="fa fa-table"></i> dim_door_type</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('dim_event_type', 'N', '')">
	                        	<i class="fa fa-table"></i> dim_event_type</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('dim_kpi_type', 'N', '')">
	                        	<i class="fa fa-table"></i> dim_kpi_type</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('dim_reason_code', 'N', '')">
	                        	<i class="fa fa-table"></i> dim_reason_code</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('dim_store_hours', 'N', '')">
	                        	<i class="fa fa-table"></i> dim_store_hours</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('dim_users', 'N', '')">
	                        	<i class="fa fa-table"></i> dim_users</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('fact_event_data', 'N', '')">
	                        	<i class="fa fa-table"></i> fact_event_data</a></li>
	                        <li class="list-group-item"><a href="#" data-toggle="tab" onclick="callws('fact_event_detail', 'N', '')">
	                        	<i class="fa fa-table"></i> fact_event_detail</a></li>
	                    </ul>
                        
                      </div>
                    </section>
                  </section>
                </aside>
                <!-- / side content -->
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      <h2 class="font-thin m-b">JSON Fast View</h2>
                      
                      <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <span id="table_id">Select Table</span>
			              </header>
			              
			              <div class="table-responsive">
                      		<div id="TableCont"></div>
                      	  </div>
                      </section>
		  					  
                    </section>
                  </section>
                </section>
              
              
              
              
              </section>
            </section>
          </section>
        </section>
        
        
      </section>
    </section>    
  </section>
  
  
  <script src="../../js/jquery.min.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
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
  
  	function callws(type, newpag, op) {
  		//alert(type);
  		
  		var nrpag=0;
  		if(newpag=='S') {
  			nrpag = localStorage.getItem("pag");
  			if(op=='pl')
  				nrpag = parseFloat(nrpag)+10;
  			else
  				nrpag = parseFloat(nrpag)-10;
  		} 
  		
  		if(nrpag<0)
  			nrpag = 0;
  		localStorage.setItem("pag",nrpag);
  		
  		//alert('http://cusago.smart-control.it:3000/'+type+'?id_file=eq.${id_file}&limit=10&offset='+nrpag);
  		
  		$('#table_id').html(type);
  		
  		$.ajax({
	        url: 'http://cusago.smart-control.it:3000/'+type+'?id_file=eq.${id_file}&limit=10&offset='+nrpag,
	        type: 'GET',
	        dataType:'json',
	        //async: false,
	        beforeSend : function (){
	        	showLoader();
            },
	        success: function (data) {
	        	
	        	
	        	hideLoader();
	        	
	        	//alert(JSON.stringify(data));
	        	
	        	var mydata = eval(data);
	        	var table = $.makeTable(mydata, type);
	        	
	        	$('#TableCont').html('');
	        	$(table).appendTo("#TableCont");
	        	
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            alert(xhr.status+' '+thrownError);
	            
	            hideLoader();
	          }
	    });
  		
  	}
  	
  	$.makeTable = function (mydata, type) {
  	    var table = $('<table class="table table-striped m-b-none">');
  	    var tblHeader = "<tr>";
  	    for (var k in mydata[0]) tblHeader += "<th>" + k + "</th>";
  	    tblHeader += "</tr>";
  	    $(tblHeader).appendTo(table);
  	    
  	    var count = mydata.length;
  	    var col = 0;
  	    $.each(mydata, function (index, value) {
  	    	col=0;
  	    	//count+=1;
	        var TableRow = "<tr>";
  	        $.each(value, function (key, val) {
  	        	col+=1;
  	            TableRow += "<td>" + val + "</td>";
  	        });
  	        
  	        TableRow += "</tr>";
  	        
  	        $(table).append(TableRow);
  	    });
  	    
  	  	if(count==0) {
  	  		table.html('');
  	  		table = $('<table class="table">');
  	  		$(table).append("<tr><td><b>No record found</b></td></tr>");
        }
  	  	else {
  	  		if(count==10) {
  	  			$(table).append('<tfoot><tr><td colspan='+col+'><div class="row"><div class="col-sm-4"><a href="#" onclick="callws(\''+type+'\', \'S\', \'mi\')" class="btn btn-default pull-left">Prev</a></div><div class="col-sm-4" align="center">Page '+((parseFloat(localStorage.getItem("pag"))/10)+1)+'</div><div class="col-sm-4"><a href="#" onclick="callws(\''+type+'\', \'S\', \'pl\')" class="btn btn-default pull-right">Next</a></div></div></td></tfoot>');
  	  		}
  	  	}
  	    
  	    return ($(table));
  	};
  	
  </script>
  
 
  
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
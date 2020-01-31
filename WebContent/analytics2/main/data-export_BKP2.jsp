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
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/chosen/chosen.css" type="text/css" />
  
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
        <!-- /.aside -->
        
        <section id="content">
          <section class="hbox stretch">   
          	<!-- menu app -->
            <%@ include file="../../inc2/menuApp6.jsp" %>
                   
            <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b>Data Export Filters</b>
		                </span>
		                
		                <form id="frm-export" name="frm-export" method="post">
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                	<li class="list-group-item">
		                	  <label><%=propLang.get("pop_us_sel") %> KPI - <a href="#" onclick="selAll()"
	                        		class="btn btn-default btn-xs"><%=propLang.get("sel_all") %></a></label>
					          <select multiple id="kpi" name="kpi" data-required="true" 
					          	class="form-control parsley-validated chosen-select">
						          <c:forEach items="${kpi}" var="kpi" varStatus="status">
		                          <option value="${kpi.xcdel}">${kpi.xdtab}</option>
		                          </c:forEach>
	                       	  </select>
		                	</li>
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtda") %></label>
						        <input class="datepicker-input2 form-control" name="dtda" id="dtda" 
						          	autocomplete="off" type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
	                        </li>
	                        <li class="list-group-item">
	                        	<label><%=propLang.get("sel_dtal") %></label>
						        <input class="datepicker-input2 form-control" name="dtal" id="dtal" 
						          	autocomplete="off" type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
	                        </li>
	                        <li class="list-group-item">
	                        	<label>Export Type</label>
						        <select id="export_type" name="export_type" class="form-control" onchange="checkExport(this.value)">
						          	<option value="M" selected>E-Mail</option>
						          	<option value="W">Download</option>
						        </select>
	                        </li>
	                        <li class="list-group-item">
	                        	<label>E-Mail to send</label>
						        <input type="text" id="mail" name="mail" value=""
						        	class="form-control"/>
	                        </li>
	                    </ul>
                        </form>
                      </div>
                    </section>
                    <footer class="footer footer-md bg-light dker">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <a href="javascipt:void;" onclick="esporta()" class="btn btn-primary btn-block"><i class="fa fa-gear"></i> Data Export</a>
	                        </span>
	                      </div>
	                    </div>
                    </footer>
                  </section>
                </aside>
                <!-- / side content -->
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      	
					  <div class="row row-sm" id="intro">
							<div class="col-xs-12">
		                    	<div class="text-center m-b-lg">
									<h1 class="fontBig text-white animated"><%=propLang.get("sel_filters") %></h1>
									
								</div>
							</div>
						</div>
						<div class="row m-t row-sm">
							<div class="col-xs-12">
								<div id="msg"></div>
								
								
		                      	
		                      	<section class="panel panel-default">
					              <header class="panel-heading font-bold">
					                <span id="table_id">Select Table</span>
					              </header>
					              
					              <div class="table-responsive">
		                      		<div id="TableCont"></div>
		                      	  </div>
		                        </section>
							</div>
						</div>
						
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
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
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
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
	$('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'bottom',
	    autoclose: true
	});  
	
	function selAll() {
  		$('#kpi option').prop('selected', true);
  		$('#kpi').trigger("chosen:updated");
  	}
	
	function checkExport(type) {
		if(type=='W'){
			$('#mail').val('');
			$('#mail').prop('disabled', true);
		}
		else {
			$('#mail').prop('disabled', false);
		}
	}
	
	function esporta() {
		
		var err = "";
		
		if (!$("#kpi").val())
		{
			err+="<br/> - Choose at least one KPI";
		}
		
		
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		
		var from = $("#dtda").val().split("-")
		var f = new Date(from[2], from[1] - 1, from[0])
		
		var to = $("#dtal").val().split("-")
		var t = new Date(to[2], to[1] - 1, to[0])
		
		
		// end - start returns difference in milliseconds 
		var diff = new Date(t.getTime() - f.getTime());
		
		// get days
		var days = diff/1000/60/60/24;
		
		if($.trim(dtda)=='') {
			err+="<br/> - Set From date";
		}
		if($.trim(dtal)=='') {
			err+="<br/> - Set To date";
		}
		if(days<0) {
			err+="<br/> - To date < From date";
		}
		
		var exportType = $('#export_type').val();
		
		if(exportType=='M'){
			var mail = $('#mail').val();
			if($.trim(mail)=='') {
				err+="<br/> - Insert a valid email";
			}
		}
		
		var data = $('#frm-export').serialize();
		
		err='';
		if($.trim(err)!='') {
			$('#msg').html("<div class=\"alert alert-warning\"><strong>Check the errors:</strong>"+err+"</div>");
		}
		else {
			
			$('#msg').html('');
			$('#msg').hide();
			
			$('#intro').hide();
			showLoader2();
			
			
			callws('Data Export', 'N', '');
		}
		
		//alert(data);
		
	}
	
	function callws(type, newpag, op) {
  		//alert(type);
  		
  		var nrpag=0;
  		if(newpag=='S') {
  			nrpag = localStorage.getItem("pag");
  			if(op=='pl')
  				nrpag = parseFloat(nrpag)+1;
  			else
  				nrpag = parseFloat(nrpag)-1;
  		} 
  		
  		if(nrpag<0)
  			nrpag = 0;
  		localStorage.setItem("pag",nrpag);
  		
  		//alert('http://cusago.smart-control.it:3000/'+type+'?id_file=eq.${id_file}&limit=10&offset='+nrpag);
  		
  		$('#table_id').html(type);
  		
  		
  		//http://localhost:8080/SmartCounterWS/app/json/json-analytics.jsp?kpi=EAS,VI2&year=2018&month=10&day=16&timegrouped=15&key=d401561e77c923bb8b25fd5f3a404ca0&pag=1&nrRecord=100
  				
  		var urlToRead = 'http://pc.smart-control.it:8080/SmartCounterWS/app/json/json-analytics.jsp?kpi=EAS,VI2&year=2018&month=10&day=16&timegrouped=15&key=d401561e77c923bb8b25fd5f3a404ca0&pag=1&nrRecord=100';
  		
  		
  				
  				
  		$.ajax({
	        //url: 'http://pc.smart-control.it:8080/cusago.smart-control.it:3000/'+type+'?id_file=eq.${id_file}&limit=10&offset='+nrpag,
	        url: '../json/json-analytics2.jsp?kpi=EAS,VI2&year=2018&month=10&day=16&timegrouped=15&key=d401561e77c923bb8b25fd5f3a404ca0&pag=1&nrRecord=10',
	        type: 'GET',
	        dataType: 'json',
	        //async: false,
	        beforeSend : function (){
	        	//showLoader();
            },
	        success: function (data) {
	        	
	        	hideLoader2();
	        	
	        	//alert(JSON.stringify(data));
	        	
	        	var mydata = eval(data);
	        	var table = $.makeTable(mydata, type);
	        	
	        	$('#TableCont').html('');
	        	$(table).appendTo("#TableCont");
	        	
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            alert(xhr.status+' '+thrownError);
	            
	            hideLoader2();
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
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  </body>
</html>
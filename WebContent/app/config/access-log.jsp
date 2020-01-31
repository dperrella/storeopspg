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
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->

</head>
<body>

  
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

              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
	                
	                	<h2 class="font-thin m-b"><%="Access Log" %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="drivers-dett.htm">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%="Access Log" %>
			              </header>

						  <div class="panel-body">
				                    
				                    <div id="main" style="height:400px; width: 100%;" ></div>

				                    <div style="margin-top: 20px;"></div>
				                        	
				                        	
				                    <div class="row ">
				                    	<div class="col-sm-2">
				                    		<label>Select Date:</label>
				                    		<input type="text" name="datas" id="datas" class="datepicker-input2 form-control" value="${Today}" data-date-format="dd-mm-yyyy" onchange="changeDate()">
				                    	</div>
				                    </div>
				                    
				           </div>
				                    
                        	<div class="table-responsive" id="details_list">
				                <table class="table table-striped b-t m-b-none dt" id="details" data-ride="datatables"></table>
			  				</div>

						  


						  
			            </section>
			 
						</form>
			              
			        </section> 
	              </section>
	            </section>
                
                <!-- side content -->
	            <aside class="aside-md bg-dark nav-xs" id="sidebar2">
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
                        <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 30px" class="text-muted list-group-item">
				            <i class="fa fa-indent text-active"></i>
				            <i class="fa fa-dedent text"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
				        
                        <a href="access-log.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-file-text-o"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%="Access Log" %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="access-log.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="Search log">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
	              </section>              
	            </aside>
	            <!-- / side content -->
                
               </section>
                
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
  
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>

   <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js"></script>


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
   
 
/* Echarts */
function Echarts(h,c){
	
	datah = '['+h+']';
	datah = JSON.parse(datah);
	
	datac = '['+c+']';
	datac = JSON.parse(datac);
	
var myChart = echarts.init(document.getElementById('main'));

 option = {
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: datah
    },
    yAxis: {
        type: 'value'        
    },
    series: [{
        data: datac,
        type: 'line',
        itemStyle: {
            normal: {
                color: 'rgb(204, 51, 51)'
            }
        },
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgb(204, 51, 51)'
                }, {
                    offset: 1,
                    color: 'rgb(204, 51, 51)'
                }])
            }
        },
    }]
};

myChart.setOption(option); 

}

$( function() {
	
	$('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'top',
	    autoclose: true
	}); 

} );


function EListTable(){
	
	var _user = "<%=propLang.get("log_user") %>";
	var _data = "<%=propLang.get("log_date") %>";
	var _hour = "<%=propLang.get("log_hour") %>";
	var _cont = "<%=propLang.get("log_count") %>";
	
	/**/
	document.getElementById("details_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading.gif' /></div>";
	var urls = "../../GetListTable?datas="+$("#datas").val();
	
	$.getJSON(urls, function (data) {
		
		var lista = data.listings;

		  	var user = "";
		  	var logdat = "";
		  	var hour = "";
		  	var num = "";
		  	
		  	var testdata_ini = "";
		  	var testdata_fin = "";
		  	
		  	var testdata_ini = "[";
	 		var testdata_fin = "]";
	 		var testdata_row = "";
	
		 	$.each(lista, function(index, lista) {
		 		user = lista.Iduser;
		 		logdat = lista.Logdate;
		 		hour = lista.Hour;
		 		num = lista.Num;
				
		 		if (testdata_row == ""){
		 			testdata_row = '{"user":"'+user+'","logdat":"'+logdat+'","hour":"'+hour+'","num":"'+num+'"}';
		 		}else{
		 			testdata_row = testdata_row + ',{"user":"'+user+'","logdat":"'+logdat+'","hour":"'+hour+'","num":"'+num+'"}';
		 		}
				
			});

		 	testdata = JSON.parse(testdata_ini + testdata_row + testdata_fin);
		 	document.getElementById("details_list").innerHTML = "<table id='details' class='table table-striped m-b-none' width='100%'></table>";
		 	
	 		var head = "<thead><th width='45%'>"+_user+"</th><th width='25%'>"+_data+"</th><th width='20%'>"+_hour+"</th><th width='10%'>"+_cont+"</th></thead>";

	 		document.getElementById("details").innerHTML = head;

	 		$('#details').dataTable({
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
	 		        "mDataProp": "user"
	 		    }, {
	 		        "mDataProp": "logdat"
	 		    }, {
	 		        "mDataProp": "hour"
	 		    }, {
	 		        "mDataProp": "num"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});
					 	

		});
/**/
	
}

function changeDate(){
	
	if ($("#datas").val() == ""){
		return;
	}
		
	$.ajax({
		url: '../../GetLogsCharts',
		async: false,
		data : {
			datas : $("#datas").val()
		},
		success : function(responseText) {

			var gets = responseText.split("-");

			Echarts(gets[0],gets[1]);
			EListTable();
		}
	
	});

	
}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
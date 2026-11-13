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
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  
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
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10001;
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
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp16.jsp" %>
              
              <section>
                  <section class="vbox">
              
               		<section class="scrollable padder-lg " id="bjax-target">
                  	  	
                  	  	<h2 class="font-thin m-b">Stores</h2>
	              
						<section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Stores Area List
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="stores-list.htm">Back to Stores</a></li>
									<li><a href="#" onclick="location.reload();">Refresh</a></li>
								</ul>
							</div>
						  </section>
						  
			              <div id="msg">${msg}</div>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
							        <th width="10%">Id</th>
							        <th width="45%">Description</th>
							        <th width="10%">Last Activity WS</th>
							        <th width="10%">Threshold Last Update</th>
							        <th width="10%">Warning Percentage</th>
							        <th width="10%">Threshold</th>
							        <th></th>
							        <th></th>
							        
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${aree}" var="aree" varStatus="status">
								<tr>
								  <td><span class="label bg-light">${aree.id}</span></td>
								  <td>${aree.description}</td>
								  <td>${aree.activityLastUpdate}</td>
								  <td>${aree.thresholdLastUpdate}</td>
								  <td>${aree.warningPercentage}</td>
								  <td>${aree.threshold}</td>
								  <td><a href="stores-area-detail.htm?idstore=${aree.idStore}&idarea=${aree.id}" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-edit"></i></a></td>
								  <td><a href="change-area-detail.htm?idstore=${aree.idStore}&idarea=${aree.id}" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-cog"></i></a></td>
								
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
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
  <!-- 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  -->
  <script src="../../js/jquery.min.js"></script>
  
  <!-- The core Firebase JS SDK is always required and must be listed first -->
  <script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-app.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-database.js"></script>
  
  
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Datatables -->
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  

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
	
	function parsleyForm() {
	  $('#actionForm').parsley();
    }
	
	$('#example').dataTable( {
	      "language": {
	          "lengthMenu": "_MENU_ &nbsp;&nbsp;Numbers of Records per Page",
	          "zeroRecords": "No record found",
	          "info": "Page _PAGE_ of _PAGES_",
	          "infoEmpty": "No record available",
	          "infoFiltered": "(Filtrati da _MAX_ records totali)",
	          "decimal": ",",
	          "thousands": ".",
	          search: "Search:&nbsp;&nbsp; "
	      },
	      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	      "aaSorting": [],
	      "iDisplayLength": 50
	  } );
	
  </script>
  
  <script>
	//Your web app's Firebase configuration
	var firebaseConfig = {
	apiKey: "AIzaSyCMslIC8kpABVxT6aNCvFlzt4BUN70hCDU",
	authDomain: "rnprj-a2be7.firebaseapp.com",
	databaseURL: "https://rnprj-a2be7.firebaseio.com",
	projectId: "rnprj-a2be7",
	storageBucket: "rnprj-a2be7.appspot.com",
	messagingSenderId: "845204873852",
	appId: "1:845204873852:web:1693d4cf07826cc8a01ff9"
	};
	//Initialize Firebase
	firebase.initializeApp(firebaseConfig);
	
	var database = firebase.database();	
	
	function readFirebase(storeCode) {
		
		
		try {
		  mRef.off('value');
		}
		catch(err) {
		  console.log(err);
		}

		
		mRef = database.ref("settings/"+storeCode);
		mRef.on('value', (snapshot) => {
			  let datas = snapshot.val();
			  
			  //alert(JSON.stringify(datas));
			  var firmware = datas.firmware.split('_')[0];
			  var Major = firmware.split('.')[0];
			  var Minor = firmware.split('.')[1];
			  var Patch = firmware.split('.')[2];
			  
			  $('#firmware').val(datas.firmware);
			  
			  if(parseInt((Major+''+Minor+''+Patch)) >=142) {
				  $('#confirm').attr('disabled', false);
				  $('#firmwaremsg').html('');
			  }
			  else {
				  $('#direction').attr('disabled', true);
				  $('#value').attr('disabled', true);
			  }
			  
			  
			  
			  
		      //alert(datas.firmware);
		});	
		  
		
		
	}
	
	function sendOccupancy() {
		
		$('#directionErr').html('');
		$('#valueErr').html('');
		$('#directionForm').removeClass('has-error');
		$('#valueForm').removeClass('has-error');
		
		
		var idArea = $('#idarea').val();
		var storeCode = $('#storeCode').val();
		var storeCodeEncoded = $('#storeCodeEncoded').val();
		var direction = $('#direction').val();
		var value = $('#value').val();
		
		var err = false;
		if(direction.length==0) {
			err = true;
			$('#directionErr').html('Select Direction');
			$('#directionForm').addClass('has-error');
		}
		if(value.length==0) {
			err = true;
			$('#valueErr').html('Select a Value');
			$('#valueForm').addClass('has-error');
		}
		
		if(err) {
			
		}
		else {
			
			showLoader2();
			
			$.ajax({
	    		url : "../../occupancy/setOccupancy",
	    		method: "POST",
	    		data: 'storeToken='+storeCodeEncoded+'&areaId='+idArea+'&direction='+direction+'&value='+value,
	    		success : function(response) {
	    			hideLoader2();
	    			
	    			$('#direction').val('');
	    			$('#value').val('');
	    			
	    			
	    		}
	    	});
			
			
		}
	}
	
  </script>
</body>
</html>
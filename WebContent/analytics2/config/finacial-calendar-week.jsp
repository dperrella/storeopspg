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
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp6.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
	                
	                	<h2 class="font-thin m-b"><%=propLang.get("fin_dett") %></h2>
		                
		                 
		                <form data-validate="parsley" method="post" action="financial-calendar-week.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" name="caltype" value="${caltype}">
                  		<input type="hidden" name="idcus" value="${idcus}">
                  		<input type="hidden" name="year" value="${year}">
                  		<input type="hidden" name="weekstart" value="${weekstart}">
                  		<input type="hidden" name="weeknumber" value="${weeknumber}">
                  		<input type="hidden" name="firstweek" value="${firstweek}">
                  		
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
                  		
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("fin_dett") %>
			              </header>
			              
			              
						  <div class="table-responsive">
						  	<div class="wrapper">
							  	<div class="subnav">
									<ul class="nav nav-pills">
										<li><a href="financial-calendar.htm?idcus=${idcus}"><%=propLang.get("torna") %></a></li>
									</ul>
								</div>
								
								<div id="msg">${msg}</div>
						  
							</div>
			                <table class="table table-striped b-t m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								  	<th width="5%">No.</th>
							        <th width="65%">Week Number</th>
							        <th width="15%"><%=propLang.get("fin_from") %></th>
							        <th width="15%"><%=propLang.get("fin_to") %></th>
							      </tr>
							  </thead>
							  
							  <tbody>
							  <c:forEach items="${fc}" var="fc" varStatus="status">
								  <tr>
								  	<td>${fc.calperiod}</td>
									<td>Week Nò. ${fc.calperiod}</td>
									<td><input type="text" name="fromdate" id="${fc.calperiod}_fromdate"class="form-control" value="${fc.fromdate}" readonly/></td>
									<td><input type="text" name="todate" id="${fc.calperiod}_todate" class="form-control" value="${fc.todate}" readonly/></td>
								  </tr>
							  </c:forEach>
							  </tbody>
							</table>
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
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
				        
                        <a href="financial-calendar.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-calendar"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("fin_dsfun") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="financial-calendar.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("rol_cerca") %>">
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
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
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

	
$( function() {
	$('.datepicker-input2').datepicker({
	    format: 'yyyy-mm-dd',
	    orientation: 'top',
	    autoclose: true
	}).on('changeDate', function (ev) {
	    //alert(this.value+" "+this.id);
	    var temp = this.id.split("_");
	    
	    var period = temp[0];
	    var type = temp[1];
	    
	    //alert(period+" **** "+type);
	    
	    if(type=='fromdate') {
	    	if(parseInt(period)>1) {
	    		
	    		period = parseInt(period)-1;
	    		type = '_todate';
	    		
	    	}
	    }
	    else {
	    	if(parseInt(period)<12) {
	    		
	    		period = parseInt(period)+1;
	    		type = '_fromdate';
	    		
	    	}
	    }
	    
	    //alert(period+'_'+type);
	    
	    var date = new Date(this.value)
	    
	    var from = this.value.split("-")
	    var f = new Date(from[2], from[1] - 1, from[0])
	    date.setDate(date.getDate() + 1);
	    
	    var dd = date.getDate();
	    var mm = date.getMonth()+1; //January is 0!
	    var yyyy = date.getFullYear();
	    if(dd<10){
	        dd='0'+dd;
	    } 
	    if(mm<10){
	        mm='0'+mm;
	    } 
	    
	    
	    $('#'+period+''+type).val(yyyy+"-"+mm+"-"+dd);
	    
	});;  
} );

function setSelectPicker() {
	$('.selectpicker').selectpicker();
}


$('.input-daterange input').each(function() {
    $(this).datepicker('clearDates');
});

/**/
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  </body>
</html>
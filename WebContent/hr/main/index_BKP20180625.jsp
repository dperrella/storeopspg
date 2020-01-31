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
              
              <c:if test = "${idcus==''}">
                   	<div class="row row-sm">
						<div class="col-xs-12">
	                    	<div class="text-center m-b-lg">
								<h1 class="fontBig text-white animated"><%=propLang.get("sel_cust") %></h1>
							</div>
						</div>
					</div>
              </c:if>
                   
              <c:if test = "${idcus!=''}">
              <%@ include file="../../inc2/menuApp7.jsp" %>
              
              <section>
                  <section class="vbox">
              		
              		<section class="scrollable padder" id="scrollable">
              		
              		
	                    
                      <h2 class="font-thin m-b">Desk Smart HR <%//=propLang.get("customer") %> - <%=propIdx.get("versione") %> 2.0.0</h2>
	            
			              <div class="row row-sm">
			              	
			              	<div class="col-xs-12">
		
								<div class="row m-b-lg text-center">
				                    <div class="col-sm-4">
				                      <div class="bg-danger lter wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="icon-users i-lg"></i> <%=propIdx.get("nrdip") %></span>
				                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrcli}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-sm-4">
				                      <div class="bg-black wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fa fa-building-o i-lg"></i> <%=propIdx.get("nrdep") %></span>
				                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrdep}</span>
				                        </a>
				                      </div>
				                    </div>
				                    <div class="col-sm-4">
				                      <div class="bg-light dker wrapper-md r">
				                        <a href="#">
				                          <span class="h4 block"><i class="fa fa-calendar icon i-lg"></i> <%=propIdx.get("nrplan") %></span>
				                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nrplan}</span>
				                        </a>
				                      </div>
				                    </div>
				                  </div>
							
							  </div>
							  
							</div>
							
						
             	  
		                </section> 
		              </section>
		            </section>
		            
		            <!-- side content -->
		            <aside class="aside-md bg-dark dker" id="sidebar">
		              <section class="vbox animated fadeInRight">
		                <section class="w-f-md scrollable hover">
		                  
		                  <div class="wrapper bg-black"><h4 class="font-thin m-t-xs text-white"><%=propIdx.get("ultplan") %></h4></div>
		                  
		                  <ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
		                    <c:forEach items="${plan}" var="plan" varStatus="status">
				            <li class="list-group-item">
		                      
		                      <div class="clear">
		                        <div><a href="../planner/planner-list-tab2.htm?tp=I&year=${plan.cmp1}&week=${plan.cmp2}&id_departments=${plan.cmp3}">${plan.cmp4}</a></div>
		                        <small class="text-muted smallest">${plan.cmp1} <%=propIdx.get("sett") %> ${plan.cmp2}</small>
		                      </div>
		                    </li>
		                    </c:forEach>
		                  </ul>
		                </section>
		                <footer class="footer footer-md bg-black">
		                  <form action="../../hr/planner/planification-list.htm" class="" role="search">
		                    <div class="form-group clearfix m-b-none">
		                      <div class="input-group m-t m-b">
		                        <span class="input-group-btn">
		                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
		                        </span>
		                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border" placeholder="<%=propIdx.get("cercaplan") %>">
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
                </c:if>
             	  
                
                
               
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
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
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
	
	function setSelectPicker() {
		$('.selectpicker').selectpicker();
	}
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  <form id="chgUser" action="index.htm" method="post">
  	<input type="hidden" name="idcus" id="idcus_frm"/>
  </form>
  
  	<div id="myModalCus" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" onclick="dismissCus()">&times;</button>
	        <h3 class="font-thin">Select Customer</h3>
	      </div>
	      <div class="modal-body" id="SelCus" style="width:100%;">
			<select id="idcus" name="idcus" title="Select Customer" class="selectpicker remove-example" data-live-search="true" style="background: #fff; min-width:100%; width:100%; border: 1px;">
				<c:forEach items="${cus}" var="cus" varStatus="status">
				<option value="${cus.xcdel}">${cus.xdtab}</option>
				</c:forEach>
			</select>   
			<div id="customers" name="customers" style="background: #eee; min-width:100%; width:100%; border: 1px;"></div>     
	      </div>
	      <div class="modal-footer" style="margin-top: 20px;">
	        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="dismissCus()"><i class="fa fa-power-off" aria-hidden="true"></i> Close</button>
	        <button type="button" class="btn btn-primary btn-lg" onclick="selectCustomer('idcus')" data-dismiss="modal"><i class="icon-user" aria-hidden="true"></i>&nbsp;<i class="fa fa-exchange" aria-hidden="true"></i> Change</button>
	      </div>
	    </div>
	
	  </div>
	  
	</div>
	
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>
  
  <c:if test = "${nr_customers > 1}">

  <script>
  	$('#myModalCus').modal('show');
  </script>
  
  </c:if>
  
  <script>
    function openModalCus() {
    	$('#myModalCus').modal('show');
    }
    function dismissCus() {
    	var idcus = $('#idcus').val();
    	if(idcus=='') {
    		$('#chgUser').submit();
    	}
    }
    
  	function selectCustomer(id) {
  		
  		var idcus = $('#'+id).val();
  		$('#idcus_frm').val(idcus);
  		
  		$('#chgUser').submit();
  	}
  </script>
  
  </body>
</html>
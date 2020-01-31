<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page 
language="java" 
import="java.util.*"
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
		z-index: 10000;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
	#ajaxModal {
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
               
              <%@ include file="../../inc2/menuApp13.jsp" %>
              
              
              
              <aside class="aside bg-light dk" >
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
	                    
	                    <span class="bg-light dker list-group-item">
		                  <b><%=propLang.get("sto_dsfun") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                <c:forEach var="storeList" items="${storeList}" varStatus="status">
		                <li class="list-group-item">
		                	
		                	<a href="#" id="${storeList.storeId}" class="audits">
		                		<span class="text-ellipsis"><i class="fa fa-building-o icon"></i> ${storeList.description}</span>
		                		<span class="text-muted text-ellipsis">${storeList.storeId}</span>
		                	</a>
		                </li>
		                </c:forEach>
			            </ul>
		            
	             	  </div>
	                </section>
	                
	                <footer class="footer footer-md bg-light dker">
	                  
	                  <div class="form-group clearfix m-b-none">
	                    <div class="input-group m-t m-b">
	                      <span class="input-group-btn">
	                        <a href="javascipt:void;" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></a>
	                      </span>
	                      <input type="text" id="search_input" name="search_input" value="" class="form-control input-sm bg-empty b-b b-dark no-border" placeholder="<%=propLang.get("sto_cerca") %>">
	                    </div>
	                  </div>
	                  
	                </footer>
	              </section>
	            </aside>
	            <!-- / side content -->
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg " id="bjax-target">
                      
                	
                      	
					  <div class="row row-sm" id="intro">
							<div class="col-xs-12">
		                    	<div class="text-center m-b-lg">
									<h1 class="fontBig text-white animated"><%=propLang.get("sel_stores") %></h1>
									
								</div>
							</div>
						</div>
						<div class="row row-sm">
                      		
							<div id="auditPanel">
								<h2 class="font-thin m-b">Audit List</h2>
							
								<section class="panel panel-default">
				                    <header class="panel-heading bg-light">
				                      
				                      <span class="text-muted m-r-sm"><i class="fa fa-list-alt"></i></span>
				                      <span class="hidden-sm">Audit</span>
				                      
				                    </header>
									  <div class="table-responsive" id="auditTableDiv">
									    <table class="table table-striped m-b-none" id="auditsTable" data-ride="datatables">
									      <thead>
									        <tr>
									          <th style="width:5%">ID</th>
									          <th>Nome</th>
									          <th style="width:15%">Data Creazione</th>
									          <th style="width:15%">Data Chiusura</th>
									          <th style="width:10%">Stato</th>
									          <th style="width:5%"></th>
									        </tr>
									      </thead>
									    </table>
									  </div>
								</section>
								
							</div>
							
							
						</div>
					
					
					
					
					
					                  	
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
  
  
  
  
  
  
  <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <a class="close" data-dismiss="modal">&times;</a>
	        <h3 class="font-thin" id="exampleModalLabel">Items</h3>
	      </div>
	      <div class="modal-body">
	        
	        
	        <div class="table-responsive" id="auditItemsTableDiv">
			    <table class="table table-striped m-b-none" id="auditItemsTable" data-ride="datatables">
			      <thead>
			        <tr>
			          <th>Codice a barre</th>
			          <th>Nome</th>
			          <th>Descrizione</th>
			          <th>Vendor</th>
			          <th>Brand</th>
			          <th>Nazione</th>
			          <th>Protected</th>
			          <th>Evolution report link</th>
			          <th>Audit Result</th>
			          <th>Note</th>
			          <th>Lotto</th>
			          <th>Scadenza</th>
			         
			        </tr>
			      </thead>
			    </table>
			  </div>
	        
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	  
  
  <script src="../../js/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- Filter list -->
  <script src="../../js/jquery.fastLiveFilter.js"></script>
  
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <script src="js/storespage.js?ver<%=new Date().getTime()%>"></script>
					  
 
  
  
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
	
	$(function() {
	    $('#search_input').fastLiveFilter('#search_list');
	});
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  <form id="chgUser" action="index.htm" method="post">
  	<input type="hidden" name="idcus" id="idcus_frm"/>
  </form>
  
  	<div id="myModalCus" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <div class="modal-content">
	      <div class="modal-header">
	        <a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
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
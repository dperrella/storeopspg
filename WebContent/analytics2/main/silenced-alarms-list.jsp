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
          <section class="hbox stretch">   
          	<!-- menu app -->
            <%@ include file="../../inc2/menuApp6.jsp" %>
                   
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("asi") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("asi") %>
			                <a href="../../analytics2/main/active-alarms-list.htm" class="btn btn-default btn-xs m-l-sm pull-right" >
	                            <i class="fa fa-warning"></i>
	                            <%=propLang.get("aca") %> 
	                        </a>
			              </header>
			              
			              <c:if test="${tot > 0}">
			              
			              <div class="row wrapper">
		              	    <div class="col-sm-4 hidden-xs">
		                    	<a href="#" onclick="del()" class="btn btn-default btn-danger"><%=propLang.get("delSel") %></a>
		                    </div>
		                    <div class="col-sm-4 text-center">
		                      <small class="text-muted inline m-t-sm m-b-sm"><%=propLang.get("staivis") %> ${ini}-${fin+ini} <%=propLang.get("di") %></small>
		                    </div>
		                    <div class="col-sm-4 text-right text-center-xs">                
		                      <ul class="pagination pagination m-t-none m-b-none">
		                        <li><a href="#" onclick="setPage('${prev}');showLoader()"><i class="fa fa-chevron-left"></i> <%=propLang.get("prev") %></a></li>
		                        <li><a href="#" onclick="setPage('${next}');showLoader()"><%=propLang.get("next") %> <i class="fa fa-chevron-right"></i></a></li>
		                      </ul>
		                    </div>
		                 
			              </div>
			              
			              <div id="tab" class="table-responsive">
			                <table class="table table-striped b-t m-b-none">
			                  <thead>
								  <tr>
								  <!-- 
							        <th><a href="active-alarms-list.htm?order=id&sort=${sort}">Id</a></th>
							      -->
							        <th></th>
							        <th><a href="silenced-alarms-list.htm?order=code&sort=${sort}"><%=propLang.get("aa_code") %></a></th>
							        <th><a href="active-alarms-list.htm?order=description&sort=${sort}"><%=propLang.get("aa_descr") %></a></th>
							        <th><a href="active-alarms-list.htm?order=dssto&sort=${sort}"><%=propLang.get("aa_store") %></a></th>
							        <th><a href="active-alarms-list.htm?order=fromdate&sort=${sort}"><%=propLang.get("aa_from_time") %></a></th>
							        <th><a href="active-alarms-list.htm?order=todate&sort=${sort}"><%=propLang.get("aa_to_time") %></a></th>
							        
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${alarms}" var="alarms" varStatus="status">
								<tr>
								<!-- 
								  <td>${alarms.cmp1}</td>
								-->
								  <td>${alarms.cmp8}</td>
								  
								  <td><span class="label bg-light">${alarms.cmp1}</span></td>
								  <td>${alarms.cmp2}</td>
								  <td>${alarms.cmp3}</td>
								  <td>${alarms.cmp4}</td>
								  <td>${alarms.cmp5}</td>
								  
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					</table>
		  					
		  				  </div>
		  				  
		  				  <footer class="panel-footer">
			                  <div class="row">
			                    <div class="col-sm-4 hidden-xs">
			                      	               
			                    </div>
			                    <div class="col-sm-4 text-center">
			                      <small class="text-muted inline m-t-sm m-b-sm"><%=propLang.get("staivis") %> ${ini}-${fin+ini} <%=propLang.get("di") %></small>
			                    </div>
			                    <div class="col-sm-4 text-right text-center-xs">                
			                      <ul class="pagination pagination m-t-none m-b-none">
			                        <li><a href="#" onclick="setPage('${prev}');showLoader()"><i class="fa fa-chevron-left"></i> <%=propLang.get("prev") %></a></li>
			                        <li><a href="#" onclick="setPage('${next}');showLoader()"><%=propLang.get("next") %> <i class="fa fa-chevron-right"></i></a></li>
			                      </ul>
			                    </div>
			                  </div>
			              </footer>
		  				  
		  				  </c:if>
			              
			              <c:if test="${tot == 0}">
			              	
			              	<div class="row wrapper text-center">
			              		
			              		<p class="h3 font-thin"><%=propLang.get("dt_nessunrecord") %></p><p></p>
			              		<p><%=propLang.get("msgparm") %></p> 
			              		
			              	</div>
			              	<footer class="panel-footer">
			                  <div class="row">
			                    <div class="col-sm-4 hidden-xs">
			                      	               
			                    </div>
			                    <div class="col-sm-4 text-center">
			                      <a href="../main/active-alarms-list.htm" onclick="showLoader()" class="btn btn-success btn-lg"><%=propLang.get("backDash") %></a> 
			                    </div>
			                    <div class="col-sm-4 text-right text-center-xs">                
			                      
			                    </div>
			                  </div>
			              	</footer>
			              	
			              </c:if>
		  				
		  				</section>
		  				
		  				</div>
		  				  
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
				        
                        <a href="active-alarms-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="fa fa-warning icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("aca") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="active-alarms-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("ia") %>">
	                      </div>
	                    </div>
	                  </form>
	                </footer>
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
  
  <section>
	<!-- Inizio Form -->
	<form id="formPage" action="silenced-alarms-list.htm" method="post">
	             	
		<input type="hidden" id="pag" name="pag" value="">
	
	</form>
	<!-- Fine Form -->
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
	function setPage(page) {
		
		$('#pag').val(page);
			
		$( "#formPage" ).submit();	
		
	}
	
	function del(sec, tm) {
		
		showLoader2();
		var checked = [];

		$('#tab [name="chk"]:checked').each (function (i, e)
		{
		    checked.push ($(this).val ());
		});

		//alert (sec+' '+tm+'\n\n'+checked.join (','));
		
		var chk = checked.join (',');
		
		$.ajax({
		      url: '../ajax/delSil.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'checked='+chk,
		      success: function (data) {
				
		    	showLoader2();
				//alert("<%=propLang.get("msgSuccess")%>");
				
				location.href="silenced-alarms-list.htm";
				
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
  </script>
  <%@ include file="../../inc/autorized.jsp" %>
  
  <%if(Integer.parseInt(session.getAttribute("nrcus").toString())<=1) { %>
  <script>
  	$('#mnu_sel_cus').remove();
  </script>
  <%} %>	
  </body>
</html>
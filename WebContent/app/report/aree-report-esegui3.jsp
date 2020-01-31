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
  <title><%=propLang.get("ara_static") %> | Smart Analytics</title>
  <meta name="description" content="Desk SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <!-- 
  <link rel="stylesheet" href="../../js/datepicker/datepicker.css" type="text/css" />
  -->
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
	div.horizontal {
	    width: 100%;
	    overflow: auto;
	}
	.tree ul {
		padding-top: 20px; position: relative;
		
		transition: all 0.5s;
		-webkit-transition: all 0.5s;
		-moz-transition: all 0.5s;
	}
	
	.tree li {
		float: left; text-align: center;
		list-style-type: none;
		position: relative;
		padding: 20px 5px 0 5px;
		
		transition: all 0.5s;
		-webkit-transition: all 0.5s;
		-moz-transition: all 0.5s;
	}
	
	
	.tree li::before, .tree li::after{
		content: '';
		position: absolute; top: 0; right: 50%;
		border-top: 1px solid #ccc;
		width: 50%; height: 20px;
	}
	.tree li::after{
		right: auto; left: 50%;
		border-left: 1px solid #ccc;
	}
	
	.tree li:only-child::after, .tree li:only-child::before {
		display: none;
	}
	
	.tree li:only-child{ padding-top: 0;}
	
	.tree li:first-child::before, .tree li:last-child::after{
		border: 0 none;
	}
	.tree li:last-child::before{
		border-right: 1px solid #ccc;
		border-radius: 0 5px 0 0;
		-webkit-border-radius: 0 5px 0 0;
		-moz-border-radius: 0 5px 0 0;
	}
	.tree li:first-child::after{
		border-radius: 5px 0 0 0;
		-webkit-border-radius: 5px 0 0 0;
		-moz-border-radius: 5px 0 0 0;
	}
	
	.tree ul ul::before{
		content: '';
		position: absolute; top: 0; left: 50%;
		border-left: 1px solid #ccc;
		width: 0; height: 20px;
	}
	
	.tree li a{
		border: 1px solid #ccc;
		padding: 5px 10px;
		text-decoration: none;
		color: #666;
		font-family: arial, verdana, tahoma;
		font-size: 16px;
		display: inline-block;
		
		border-radius: 5px;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		
		transition: all 0.5s;
		-webkit-transition: all 0.5s;
		-moz-transition: all 0.5s;
	}
	
	.tree li a:hover, .tree li a:hover+ul li a {
		background: #c8e4f8; color: #000; border: 1px solid #94a0b4;
	}
	.tree li a:hover+ul li::after, 
	.tree li a:hover+ul li::before, 
	.tree li a:hover+ul::before, 
	.tree li a:hover+ul ul::before{
		border-color:  #94a0b4;
	}
	
  </style>
</head>
<body class="">
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
            	<%@ include file="../../inc2/menuApp.jsp" %>
            
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      <h2 class="font-thin m-b"><%=propLang.get("ara_static") %></h2>
                      
                      <div class="row row-sm">
	              	
		              	<div class="col-xs-12">
			                  
			                  <div id="msg">${msg}</div>
			                  
			                  <form action="../report/aree-report-esegui3.htm" id="formPage"
			                  	method="post" onsubmit="return control()">
			                  <input type="hidden" name="id_store" value="${id_store}"/>
			                  <input type="hidden" name="tipo" id="tipo" value="${tipo}">
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <ul class="nav nav-tabs pull-right">
			                        <li id="T1"><a href="#dayTab" data-toggle="tab" onclick="$('#tipo').val('D')"><%=propLang.get("tipo_day") %></a></li>
			                        <li id="T2"><a href="#weekTab" data-toggle="tab" onclick="$('#tipo').val('W')"><%=propLang.get("tipo_week") %></a></li>
			                        <li id="T3"><a href="#monthTab" data-toggle="tab" onclick="$('#tipo').val('M')"><%=propLang.get("tipo_month") %></a></li>
			                        <li id="T4"><a href="#periodTab" data-toggle="tab" onclick="$('#tipo').val('P')"><%=propLang.get("tipo_period") %></a></li>
			                      </ul>
			                      
			                      <span class="hidden-sm"><b>${description_store}</b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                      
			                      <div id="msg">${msg}</div>
	                        	  
			                      <div class="tab-content">              
			                        <div class="tab-pane" id="dayTab">
			                        	<div class="form-group pull-in clearfix">
			                        		<div class="col-sm-12">
									          <label><%=propLang.get("sel_day") %></label>
									          <input class="datepicker-input form-control" name="day" id="day" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									    </div>
									</div>
			                        
			                        <div class="tab-pane" id="weekTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearWeek" name="yearWeek" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana()">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-10">
									          <label><%=propLang.get("sel_week") %></label>
									          <select id="week" name="week" data-required="true" class="form-control parsley-validated">
										          <option value="">-</option>
										          <c:forEach items="${weeks}" var="weeks" varStatus="status">
						                          <option value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
						                          </c:forEach>
				                        	  </select>   
									        </div>
								    	</div>
								    </div>
			                        <div class="tab-pane" id="monthTab">
			                        	
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-2">
									          <label><%=propLang.get("sel_year") %></label>
									          <select id="yearMonth" name="yearMonth" data-required="true" class="form-control parsley-validated">
										          <option value="2015">2015</option>
										          <option value="2016">2016</option>
										          <option value="2017">2017</option>
										          <option value="2018">2018</option>
						                      </select>   
									        </div>
									        
									        <div class="col-sm-10">
									          <label><%=propLang.get("sel_month") %></label>
									          <select id="month" name="month" data-required="true" class="form-control parsley-validated">
										          <option value="">-</option>
										          <c:forEach items="${mn}" var="mn" varStatus="status">
						                          <option value="${mn.xcdel}" ${mn.cmp1}>${mn.xcdel}: ${mn.xdtab}</option>
						                          </c:forEach>
				                        	  </select>   
									        </div>
								    	</div>
								    	
								    	
			                        	
			                        </div>
			                        <div class="tab-pane" id="periodTab">
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-6">
									          <label><%=propLang.get("sel_dtda") %></label>
									          <input class="datepicker-input form-control" name="dtda" id="dtda" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									        
									        <div class="col-sm-6">
									          <label><%=propLang.get("sel_dtal") %></label>
									          <input class="datepicker-input form-control" name="dtal" id="dtal" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
									        </div>
								    	</div>
								    	
			                        </div>
			                      </div>
			                    
			                      <div class="line line-dashed b-b line-lg pull-in"></div>
			                      <div class="form-group pull-in clearfix">
							        	<div class="col-sm-6">
						                	
						                	<label><%=propLang.get("ara_orario") %></label>
					                        <div class="radio i-checks">
						                      <label class="i-checks">
						                        <input type="radio" 
						                        	id="orario1" name="orario" value="S"><i></i> <%=propLang.get("aar_S") %>
						                      </label>
						                    </div>
						                    
						                    <div class="radio i-checks">
						                      <label class="i-checks">
						                        <input type="radio" 
						                        	id="orario2" name="orario" value="N"><i></i> <%=propLang.get("aar_N") %>
						                      </label>
						                    </div>
						                    
						                </div>
						            </div>
			                    </div>
			                    <footer class="panel-footer text-right bg-light lter">
			                    	<input type="hidden" name="tipo" id="tipo" value="D">
			                    	
							      	<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("btn_esegui_report") %>"/>
							    </footer>
			                  </section>
			                  
			                  </form>
			                  
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="hidden-sm"><b>Diagram <%=propLang.get("ara_static") %></b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                    	<div class="horizontal">
			                    	<div class="tree text-center">
										<!-- 
										<ul>
										  <li><a href="#">Linea 1<br/><span style="font-size:40px">0</span></a>
											  <ul>
											    <li><a href="#">Linea 2 - A</a></li>
											    <li><a href="#">Linea 2 - B</a>
											    	<ul>
													    <li><a href="#">Linea 3 - A</a></li>
													    <li><a href="#">Linea 3 - B</a></li>
													    <li><a href="#">Linea 3 - C</a></li>
													</ul>
											  </ul>
										  </li> 
										</ul>
										-->
										${albero}
	   								</div>
	   								</div>
			                    </div>
			                  </section>
			                  <!-- 
			                  <div class="text-center m-b-lg">
						          <h1 class="h text-white animated fadeInDownBig" style="font-size:100px">Work in Progress</h1>
					          </div>
					          -->
		                  </div>
			                  
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
  <script src="../../js/jquery.min.js"></script>
  <!-- Filter list -->
  <script src="../../js/jquery.fastLiveFilter.js"></script>
  
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- datepicker -->
  <script src="../../js/datepicker/bootstrap-datepicker.js"></script>
  <!-- Chosen -->
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
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
	
	$('#yearWeek').val('${currentYear}');
  	$('#yearMonth').val('${currentYear}');
  	$('#id_store').val('${id_store}');
  	
  	$('#day').val('${day}');
  	$('#yearWeek').val('${yearWeek}');
  	$('#week').val('${week}');
  	$('#yearMonth').val('${yearMonth}');
  	$('#month').val('${month}');
  	$('#dtda').val('${dtda}');
  	$('#dtal').val('${dtal}');
  	
  	var $radios2 = $('input:radio[name=orario]');
    if($radios2.is(':checked') === false) {
        $radios2.filter('[value=${orario}]').prop('checked', true);
    }
    
  	var tipo = '${tipo}';
  	if(tipo=='D') {
  		$("#T1" ).addClass("active");	
  		$('#dayTab').addClass("active");
  	}
  	if(tipo=='W') {
  		$("#T2" ).addClass("active");	
  		$('#weekTab').addClass("active");
  	}
  	if(tipo=='M') {
  		$("#T3" ).addClass("active");	
  		$('#monthTab').addClass("active");
  	}
  	if(tipo=='P') {
  		$("#T4" ).addClass("active");	
  		$('#periodTab').addClass("active");
  	}
  	
  	
  	
  	
  	
  	function reloadForm() {
  		
  		showLoader();
  		$('#formPage').submit();
  		
  	}
  	
  	function control() {
  		
  		
  			showLoader();	
  		
  	}
  </script> 
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  
  
</body>
</html>
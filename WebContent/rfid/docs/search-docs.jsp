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
          <section class="hbox stretch">   
          
          	<!-- menu app -->
              
            <%@ include file="../../inc2/menuApp4.jsp" %>
                   
            	<!-- .aside -->
            <aside class="aside-lg bg-light b-r" id="aside">
              <div class="wrapper">
                <h4 class="m-t-none"><%=propLang.get("doc_cerca") %></h4>
                  
                  <div class="form-group">
                    <label>Warehouse</label>
                    <select name="warehouse" id="warehouse" class="input-sm form-control">
                    	<c:forEach items="${wha}" var="wha" varStatus="status">
						<option value="${wha.xcdel}">${wha.xdtab}</option>
						</c:forEach>
                    </select>
                  </div>
                  <div class="form-group">
                    <label>Type</label>
                    <select name="type" id="type" class="input-sm form-control">
                    	<option value="B">Barcode</option>
                    	<option value="E">EPC</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label>Search String</label>
                    <input type="text" placeholder="Search..." name="q" id="q" class="input-sm form-control">
                  </div>
                  <div class="m-t-lg"><a href="#" onclick="cerca()" class="btn btn-lg btn-block btn-default">
                  	<%=propLang.get("doc_cerca") %></a></div>
                
              </div>
            </aside>
            <!-- /.aside -->
            
            
            
            
            
            <aside>
              <section class="vbox">
                <section class="scrollable wrapper">
                  
                  
                  <div id="search-result"></div>
                  
                  <!-- 
                  <div class="timeline">
                    <article class="timeline-item active">
                        <div class="timeline-caption">
                          <div class="panel bg-primary lt no-borders">
                            <div class="panel-body">
                              <span class="timeline-icon"><i class="fa fa-bell-o time-icon bg-primary"></i></span> 
                              <span class="timeline-date">7:30 am</span>
                              <div class="text-sm">Wake up</div>
                              <h5>Me</h5>
                              <div class="m-t-sm timeline-action">
                                <span class="h3 pull-left m-r-sm">4:51</span>
                                <button class="btn btn-sm btn-default btn-bg"><i class="fa fa-pause"></i> Pause</button>
                                <button class="btn btn-sm btn-default btn-bg"><i class="fa fa-check"></i> Confirm</button>
                              </div>
                            </div>
                          </div>
                        </div>
                    </article>
                    <article class="timeline-item alt">
                        <div class="timeline-caption">
                          <div class="panel panel-default">
                            <div class="panel-body">
                              <span class="arrow right	"></span>
                              <span class="timeline-icon"><i class="fa fa-phone time-icon bg-primary"></i></span>
                              <span class="timeline-date">12:25 am</span>
                              <div class="text-sm">Call to</div>
                              <h5>                                
                                Jason Cokde (021-254-3523)
                              </h5>
                            </div>       
                          </div>
                        </div>
                    </article>
                    <article class="timeline-item alt">
                        <div class="timeline-caption">                
                          <div class="panel panel-default">
                            <div class="panel-body">
                              <span class="arrow right"></span>
                              <span class="timeline-icon"><i class="fa fa-male time-icon bg-success"></i></span>
                              <span class="timeline-date">10:00 am</span>
                              <div class="text-sm">Appointment</div>
                              <h5>                                
                                Carmark Sook (.inc company)
                              </h5>
                              <p>Morbi nec nunc condimentum, egestas dui nec, </p>
                            </div>
                          </div>
                        </div>
                    </article>          
                    <article class="timeline-item">
                        <div class="timeline-caption">                
                          <div class="panel panel-default">
                            <div class="panel-body">
                              <span class="arrow left"></span>
                              <span class="timeline-icon"><i class="fa fa-plane time-icon bg-dark"></i></span>
                              <span class="timeline-date">8:00 am</span>
                              <div class="text-sm">Fly to</div>
                              <h5>                                
                                Newyork City
                              </h5>
                              <p>82°, Very hot with some sun</p>
                            </div>
                          </div>
                        </div>
                    </article>
                    <article class="timeline-item alt">
                        <div class="timeline-caption">                
                          <div class="panel panel-default">
                            <div class="panel-body">
                              <span class="arrow right"></span>
                              <span class="timeline-icon"><i class="fa fa-file-text time-icon bg-info"></i></span>
                              <span class="timeline-date">9:30 am</span>
                              <div class="text-sm">Meeting</div>
                              <h5>                                
                                Office A - 2 floor
                              </h5>
                              <p>Iaculis lorem justo porttitor orci. Vivamus vestibulum tortor augue. Donec elementum mollis velit.</p>
                            </div>
                          </div>
                        </div>
                    </article>
                    <article class="timeline-item">
                        <div class="timeline-caption">
                          <div class="panel panel-default">
                            <div class="panel-body">
                              <span class="arrow left"></span>
                              <span class="timeline-icon"><i class="fa fa-code time-icon bg-dark"></i></span>
                              <span class="timeline-date">9:00 am</span>
                              <div class="text-sm">Work on</div>
                              <h5>                                
                                Web application project
                              </h5>
                              <p>Iaculis lorem justo porttitor orci. Donec elementum mollis velit.</p>
                            </div>
                          </div>
                        </div>
                    </article>
                    <div class="timeline-footer"><a href="#"><i class="fa fa-plus time-icon inline-block bg-dark"></i></a></div>
                    -->
                    
                    
                  </div>
                </section>
              </section>
            </aside>
            <!-- /.aside -->     
            
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
				        
                        <a href="docs-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-docs"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("doc_doc") %></span>
                        </a>
						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="docs-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("doc_cerca") %>">
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
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
  $('#example').dataTable( {
      "language": {
          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_",
          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
          "infoFiltered": "(Filtrati da _MAX_ records totali)",
          "decimal": ",",
          "thousands": ".",
          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
      },
      "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
      "aaSorting": [],
      "iDisplayLength": 50
  } );
  
  function cerca() {
	  
	  var warehouse = $('#warehouse').val();
	  var type = $('#type').val();
	  var q = $('#q').val();
	  
	  showLoader();
	  
	  var dsType = '';
	  if(type=='B') {
		  dsType = "Barcode";
	  }
	  else {
		  dsType = "EPC"
	  }
	  
	  $("#search-result").empty();
	  
	  $.getJSON('../../servlet/ws/SearchDocuments?warehouse='+warehouse+'&type='+type+'&q='+q, function (data) {
  		
		  
		  //alert(JSON.stringify(data));
		  var lista = data.listings;
  		  
		  var str = '<div class="timeline">'
              +'<article class="timeline-item active">'
              +'<div class="timeline-caption">'
              +'  <div class="panel bg-dark lt no-borders">'
              +'    <div class="panel-body">'
              +'      <span class="timeline-icon"><i class="fa fa-barcode time-icon bg-dark"></i></span>' 
              +'      <span class="timeline-date">'+q+'</span>'
              +'      <div class="text-sm">Type</div>'
              +'      <h5>'+dsType+'</h5>'
              +'    </div>'
              +'  </div>'
              +'</div>'
          +'</article>';
		  
		  /*
		  $("#search-result").append('<div class="timeline">'
                  +'<article class="timeline-item active">'
                  +'<div class="timeline-caption">'
                  +'  <div class="panel bg-primary lt no-borders">'
                  +'    <div class="panel-body">'
                  +'      <span class="timeline-icon"><i class="fa fa-barcode time-icon bg-primary"></i></span>' 
                  +'      <span class="timeline-date">'+q+'</span>'
                  +'    </div>'
                  +'  </div>'
                  +'</div>'
              +'</article>');
		  */
		  
		  
		  var id = "";
		  var docnumber = "";
		  var doctype = "";
		  var docdate = "";
		  var origin_descr = "";
		  var dest_descr = "";
		  var doc_descr = "";
		  var verso = "";
		  
		  var alt = "";
		  var color = "";
		  var arrow = "";
		  $.each(lista, function(index, lista) {
			  
			  id = lista.id;
			  docnumber = lista.docnumber;
			  doctype = lista.doctype;
			  docdate = lista.docdate;
			  origin_descr = lista.origin_descr;
			  dest_descr = lista.dest_descr;
			  doc_descr = lista.doc_descr;
			  verso = lista.verso;
			  
			  if(parseFloat(verso)>0) {
				  alt = "alt";
				  color = "bg-success";
				  arrow = "right";
			  }				  
			  else {
				  alt = "";
				  color = "bg-danger";
				  arrow = "left";
			  }
				  
			  str+='<article class="timeline-item '+alt+'">'
              +'<div class="timeline-caption">'
              +'<div class="panel panel-default">'
              +'  <div class="panel-body">'
              +'    <span class="arrow '+arrow+'"></span>'
              +'    <span class="timeline-icon"><i class="fa fa-file-text time-icon '+color+'"></i></span>'
              +'    <span class="timeline-date">'+docdate+'</span>'
              +'    <div class="text-sm">Document No. '+docnumber+'</div>'
              +'    <h5>'
              +'      '+origin_descr+' '
              +'    </h5>'
              +'    <h5>'
              +'      '+doc_descr+' '
              +'    </h5>'
              +'  </div>'     
              +'</div>'
              +'</div>'
              +'</article>';
              
              /*
			  $("#search-result").append('<article class="timeline-item '+alt+'">'
                      +'<div class="timeline-caption">'
                      +'<div class="panel panel-default">'
                      +'  <div class="panel-body">'
                      +'    <span class="arrow '+arrow+'"></span>'
                      +'    <span class="timeline-icon"><i class="fa-file-text time-icon '+color+'"></i></span>'
                      +'    <span class="timeline-date">'+docdate+'</span>'
                      +'    <div class="text-sm">Document No. '+docnumber+'</div>'
                      +'    <h5>'
                      +'      '+origin_descr+' '
                      +'    </h5>'
                      +'    <h5>'
                      +'      '+doc_descr+' '
                      +'    </h5>'
                      +'  </div>'     
                      +'</div>'
                      +'</div>'
                      +'</article>');
              */
			  
			  
		  });
		  str+='</div>';
		  
		  //$("#search-result").append('</div>');
			$("#search-result").append(str);
				  
		  hideLoader();
		  
	  });
	  
	  //alert(type+" "+q);
	  
  }
  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
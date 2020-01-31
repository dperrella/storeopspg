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
            <%@ include file="../../inc2/menuApp2.jsp" %>
                     
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%=propLang.get("uc_dsfun") %></h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("uc_dsfun") %>
			              </header>
			              
			              <section class="panel wrapper">
			              
			                  <label><%=propLang.get("sel_user") %></label>
					          <select id="idusr" name="idusr" data-required="true" 
					          	onchange="reloadList()" class="form-control parsley-validated chosen-select">
						          <option value="">-</option>
						          <c:forEach items="${users}" var="users" varStatus="status">
		                          <option value="${users.idusr}">${users.idusr} - ${users.nome} ${users.cogn}</option>
		                          </c:forEach>
                        	  </select>   
                        	  
                        	  <div id="msg">${msg}</div>
					       
					      </section>
			              
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								    <th width="10%">
								    	<!-- 
								    	<div class="checkbox i-checks">
						                  <label>
						                    <input name="chk" type="checkbox" value="">
						                    <i></i>
						                  </label>
						                </div>
						                -->
								    </th>
							        <th width="10%"><%=propLang.get("cus_id") %></th>
							        <th width="20%"><%=propLang.get("cus_brandname") %></th>
							        <th width="40%"><%=propLang.get("cus_descrizione") %></th>
							        <th width="20%"><%=propLang.get("cus_brandlogo") %></th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${cus}" var="cus" varStatus="status">
								<tr>
								  <td>
									  <div class="checkbox i-checks">
						                  <label>
						                    <input name="chkCustomer" type="checkbox" value="${cus.id}" ${cus.cmp1} disabled>
						                    <i></i>
						                  </label>
						              </div>
								  </td>
								  <td><span class="label bg-light">${cus.id}</span></td>
								  <td>${cus.brandname}</td>
			                      <td>${cus.description}</td>
			                      <td>${cus.brandlogo}</td>
			                    </tr>
							  </c:forEach>
		  					  </tbody>
		  					  
		  					  <!-- 
		  					  <tfoot>
		  					  	<tr>
		  					  	  <td colspan="5" class="text-right">
		  					  	  	<a href="#" onclick="setRelUserCustomers();" class="btn btn-success btn-lg">
		  					  	  		<%=propLang.get("btn_autorizza") %></a>
		  					  	  </td>
		  					  	</tr>
		  					  </tfoot>
		  					  -->
		  					  
		  					</table>
		  					
		  				  </div>
		  				  
		  				  
					      
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
				        
                        <a href="rel_user_customers-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-share icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("uc_dsfun") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="rel_user_customers-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("uc_cerca") %>">
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
  
  <script src="../../js/chosen/chosen.jquery.min.js"></script>
  
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
	
  $('#idusr').val('${idusr}');
  
  function reloadList() {
	  var idusr = $('#idusr').val();
		
	  javascript:window.location='rel_user_customers-list.htm?idusr='+idusr;
	  window.location.href='rel_user_customers-list.htm?idusr='+idusr; 
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
  
  function setRelUserCustomers() {
	  
	  var idusr = $('#idusr').val();
	  
	  var chk = "";
	  $('input[name="chkCustomer"]:checked').each(function() {
		  chk=chk+this.value+","; 
	  });
	  
	  chk = chk.substring(0, chk.length - 1);
	  
	  if(idusr=='') {
		  //alert("Selezionare un utente");
		  
		  $('#msg').html("<div class=\"line line-dashed b-b line-lg pull-in\"></div>"
				+"<div class=\"alert alert-danger\"><strong><%=propLang.get("sel_user")%></strong></div>");
		  
		  document.getElementById('msg').scrollIntoView();
	  }
	  else {
		
		  // Redirect a rel_user_customers-list.htm con paramteri:
		  // - azione = (S)
		  // - idusr (idusr)
		  // - idcustomers (chk)
		  
		  javascript:window.location='rel_user_customers-list.htm?azione=S&idusr='+idusr+'&idcustomers='+chk;
	  	  window.location.href='rel_user_customers-list.htm?azione=S&idusr='+idusr+'&idcustomers='+chk; 
		  
	  }
	  
  }
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
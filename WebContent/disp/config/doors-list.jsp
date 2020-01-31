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
            <%@ include file="../../inc2/menuApp5.jsp" %>
                     
            <section>
              <section class="vbox">
                <section class="scrollable padder-lg " id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b">Customer / Doors</h2>
		                
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Customer / Doors
			              </header>
			              
			              <section class="panel wrapper">
			              
			                  <label><%=propLang.get("cus_dsfun") %></label>
					          <select id="idcus" name="idcus" data-required="true" 
					          	onchange="reloadList()" class="form-control parsley-validated chosen-select">
						          <option value="">-</option>
						          <c:forEach items="${cus}" var="cus" varStatus="status">
		                          <option value="${cus.id}">${cus.account_code} - ${cus.brandname} ${cus.description}</option>
		                          </c:forEach>
                        	  </select>   
                        	  
                        	  <div id="msg">${msg}</div>
					       
					       	  <c:if test = "${idcus!=''}">
					       	  
					       	  <div class="line line-dashed b-b line-lg pull-in"></div>
						      
				      		  <div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="#" onclick="newDoor()">New Door</a></li>
									<li><a href="#" onclick="setDoorType()">Set Door Type to selected Door</a></li>
									<li><a href="#" onclick="delSelected()">Disable Selected</a></li>
								</ul>
							  </div>
					      	  </c:if>
			              
					      </section>
					      
					      
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
			                  <thead>
								  <tr>
								    <th width="10%"></th>
							        <th width="10%">ID Door</th>
							        <th width="25%">Description</th>
							        <th width="20%">Door Type</th>
							        <th width="10%">Enabled</th>
							        <th width="10%">Origin</th>
							        <th width="10%">Account Id</th>
							        <th width="5%"></th>
							        
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${tab}" var="tab" varStatus="status">
								<tr>
								  <td>
									  <div class="checkbox i-checks">
						                  <label>
						                    <input name="chkDoor" class="theClass" type="checkbox" value="${tab.xcdel}">
						                    <i></i>
						                  </label>
						              </div>
								  </td>
								  <td><span class="label bg-light">${tab.xcdel}</span></td>
								  <td>${tab.xdtab}</td>
			                      <td>${tab.cmp2}</td>
			                      <td>${tab.cmp3}</td>
			                      <td>${tab.cmp4}</td>
			                      <td><span class="label bg-light">${tab.cmp1}</span></td>
			                      <td><a href="#" onclick="setDoorDesc('${tab.xcdel}','${tab.xdtab}')" class="btn btn-default"><i class="icon-note"/></i></a></td>
			                    </tr>
							  </c:forEach>
		  					  </tbody>
		  					  
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
				        
                        <a href="rel_cus_kpi-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-share icon"></i> 
                          <span class="hidden-nav-xs m-l-sm">Customer / KPI</span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="rel_cus_kpi-list.htm" class="" role="search">
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
  
  <!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
        <h4 id="exampleModalLabel">Select Door Type</h4>
      </div>
      <div class="modal-body">
      	
      	 <div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<label>Doors</label>
        		<input type="text" name="door" id="door" class="form-control" value="" readonly/>
        	</div>
        </div>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
						      
        <div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<label>Door Type</label>
				<select id="aisle_type" name="aisle_type" class="form-control">
		        	<option value="">-</option>
		        	<c:forEach items="${ais}" var="ais" varStatus="status">
		        	<option value="${ais.xcdel}">${ais.xdtab}</option>
		        	</c:forEach>
		        </select>
		    </div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" onclick="edtDoors()" class="btn btn-lg btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>


  <!-- Modal -->
<div class="modal fade" id="exampleModal2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
        <h4 id="exampleModalLabel">Edit Door</h4>
      </div>
      <div class="modal-body">
      	
      	 <div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<label>Doors</label>
        		<input type="text" name="door2" id="door2" class="form-control" value="" readonly/>
        	</div>
        </div>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
						      
        <div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<label>Description</label>
				<input type="text" name="desc2" id="desc2" class="form-control" value=""/>
		    </div>
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
						      
        <div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<label>Enabled</label>
				<select class="form-control" name="enabled2" id="enabled2">
					<option value="true" selected>True</option>
					<option value="true">False</option>
				</select>
		    </div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" onclick="modifica()" class="btn btn-lg btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
        <h4 id="exampleModalLabel">New Door</h4>
      </div>
      <div class="modal-body">
      	
      	 <div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<label>Doors</label>
        		<input type="text" name="door3" id="door3" class="form-control" value=""/>
        	</div>
        </div>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
						      
        <div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<label>Description</label>
				<input type="text" name="desc3" id="desc3" class="form-control" value=""/>
		    </div>
		</div>
		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" onclick="insert()" class="btn btn-lg btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
  
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
	
	
  $('#idcus').val('${idcus}');
  
  function reloadList() {
	  var idcus = $('#idcus').val();
		
	  javascript:window.location='doors-list.htm?idcus='+idcus;
	  window.location.href='doors-list.htm?idcus='+idcus; 
  }
  
  function setDoorType() {
	  
	  var id = '';
	  $('.theClass:checkbox:checked').each(function () {
	       var sThisVal = (this.checked ? $(this).val() : "");
	       id+=sThisVal+",";
	  });
	  
	  id=id.slice(0, -1);
	  
	  if(id=='') {
		  alert('Select almost 1 door');
	  }
	  else {
		  $('#door').val(id);
		  $("#exampleModal").modal()
	  }
	  
	  //alert(id);
	  
  }
  
  function delSelected() {
	  
	  
	  var id = '';
	  $('.theClass:checkbox:checked').each(function () {
	       var sThisVal = (this.checked ? $(this).val() : "");
	       id+=sThisVal+",";
	  });
	  
	  id=id.slice(0, -1);
	  
	  if(id=='') {
		  alert('Select almost 1 door');
	  }
	  else {
		  if(confirm('Confirm Delete door(s) '+id+"?")) {

			  delDoors(id);
			  
		  }
	  }
	  //alert(id);
	  
  }
  
  function delDoors(id) {
	  
	  var idcus = '${idcus}';
	  var door = id;
	  
	  showLoader2();
	  
	  javascript:window.location='doors-list.htm?tipoForm=E&azione=A&idcus='+idcus+'&door='+door;
	  window.location.href='doors-list.htm?tipoForm=E&azione=A&idcus='+idcus+'&door='+door; 
	  
  }
  
  function setDoorDesc(id, desc) {
	  
	  $('#door2').val(id);
	  $('#desc2').val(desc);
	  
	  $("#exampleModal2").modal()
	  
	  
  }
  
  function newDoor() {
	  
	  $("#exampleModal3").modal()
	  
	  
  }
  
  function insert() {
	  
	  var idcus = '${idcus}';
	  var door = $('#door3').val();
	  var desc = $('#desc3').val();
	  
	  $("#exampleModal3").hide();
	  
	  javascript:window.location='doors-list.htm?tipoForm=I&azione=A&idcus='+idcus+'&door='+door+'&desc='+desc;
	  window.location.href='doors-list.htm?tipoForm=I&azione=A&idcus='+idcus+'&door='+door+'&desc='+desc; 
	  
  }
  
  function modifica() {
	  
	  var idcus = '${idcus}';
	  var door = $('#door2').val();
	  var desc = $('#desc2').val();
	  var en = $('#enabled2').val();
	  
	  
	  $("#exampleModal2").hide();
	  
	  javascript:window.location='doors-list.htm?tipoForm=M&azione=A&idcus='+idcus+'&door='+door+'&desc='+desc+'&enabled='+en;
	  window.location.href='doors-list.htm?tipoForm=M&azione=A&idcus='+idcus+'&door='+door+'&desc='+desc+'&enabled='+en; 
	  
  }
  
  function edtDoors() {
	  
	  var idcus = $('#idcus').val();
	  var door = $('#door').val();
	  var aisle_type = $('#aisle_type').val();
	  
	  $("#exampleModal").hide();
	  
	  showLoader2();
	  
	  javascript:window.location='doors-list.htm?tipoForm=A&azione=A&idcus='+idcus+'&door='+door+'&aisle_type='+aisle_type;
	  window.location.href='doors-list.htm?tipoForm=A&azione=A&idcus='+idcus+'&door='+door+'&aisle_type='+aisle_type; 
	  
	  
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
  
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
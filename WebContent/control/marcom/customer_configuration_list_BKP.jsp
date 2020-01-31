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

String username = (String) session.getAttribute("username").toString().toUpperCase();
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
  <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
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
	div.horizontal {
	    width: 100%;
	    overflow: auto;
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
				   margin-left: -100px;" />
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
                  
                  <%@ include file="../../inc2/menuApp3.jsp" %>
              
                  <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                  	  <h2 class="font-thin m-b">Desk CheckPoint Devices - Customers Rules</h2>
                      
                      <div class="row row-sm">
	              	  	<div class="col-xs-12">
	                    
	                    	<section class="panel panel-default" id="clients_data_view">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-users"></i></span>
			                      <span class="hidden-sm">Customers Rules</span>
			                      
			                    </header>
			                    <section class="panel wrapper">
					              	<div class="subnav">
										<ul class="nav nav-pills">
											<li><a href="index.jsp">Back to Clients</a></li>
											<li><a href="#" data-toggle="modal" data-target="#exampleModal">New Rule</a></li>
										</ul>
									</div>
								</section>
								
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="clients_data_table" data-ride="datatables">
					                  <thead>
										  <tr>
										  	<th>Rule ID</th>
		                                    <th>Account Code</th>
		                                    <th>Description</th>
		                                    <th>Range From</th>
		                                    <th>Range To</th>
		                                    <th>Size</th>
		                                    <th>Tunneling</th>
									        <th></th>	
									      </tr>
									  </thead>
			      					  <tbody id="clients_list">
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="8"><div id="clients_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
	                    
	                    
	                    
                           
                        
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
  
  <script src="../../js/bootstrap-select/js/bootstrap-select.js"></script>
  
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <script>
    
     jQuery(window).load(function() {
		jQuery('#loading-image').hide();
        
		showLoader2();
		getClients();
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
	function selpick() {
		$('.ss').selectpicker({
		    liveSearch: true,
		    tickIcon: 'fa fa-check'
		 });
	  }
	
	  
	var username = '<%=username%>';
	var clients = [];
    
	
    function getClients() {
    	
    	
        $.ajax({
            type: "GET",
            accepts: "application/json",
            url: '../../servlet/ws/GetCustomerConfiguration?username='+username,
            contentType: "application/json",
            cache: false,
            success: function (data) {
                clients = data.listings;
                fillClientsTable(data.listings);
                
                if (! $.fn.dataTable.isDataTable('#clients_data_table')) {
                    table = $('#clients_data_table').DataTable({ 
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
                        "iDisplayLength": 10
          		    });
                    
                    //$('#clients_data_view').show();
                    
                    $('#clients_total').html('<b>Total Rules: '+clients.length+'</b>');
                    
                }
                
                
                hideLoader2();
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                console.log(errorThrown);
            }
        });
    }
    
    function fillClientsTable(data) {
        const tBody = $("#clients_list");
        
        //$(tBody).empty();
        
        $('#clients_list > tr').remove();
        $.each(data, function (i, obj) {
        	
        	const tr = $("<tr></tr>")
                .append($("<td></td>").html(""+obj.ruleid+""))
                .append($("<td></td>").html("<span class='label bg-light'>"+obj.accountid+"</span>"))
                .append($("<td></td>").html(obj.description))
                .append($("<td></td>").html(obj.range_from))
                .append($("<td></td>").html(obj.range_to))
                .append($("<td></td>").html(obj.size))
                .append($("<td></td>").text(obj.tunneling))
                //.append($("<td><a href='#' class='btn btn-default' onclick='updateClient(" + i.toString() + ");'><i class='fa fa-edit'></i></a></td>"))
                .append($("<td><a href='#' class='btn btn-danger' onclick='deleteClient(" + i.toString() + ");'><i class='fa fa-times'></i></a></td>"));
                
            tr.appendTo(tBody);
        });
    }
    
    
    function updateClient(index) {    
    	
    	console.log(JSON.stringify(clients[index]));
		
    }
    
    function deleteClient(index) {        
    	
    	if(confirm("Confirm Delete record?")) {
    		
    		console.log(JSON.stringify(clients[index]));
    		
    		
    		
    		showLoader2();
    		
    		$.ajax({
                type: "POST",
                url: '../../servlet/ws/SetCustomerConfiguration',
                cache: false,
                dataType: 'json',
                data: 'tipoForm=E&ruleid='+clients[index].ruleid,
                success: function (data) {
                    console.log('successfully deleted!');
                    console.log(JSON.stringify(data));
                    
                    
                    loadTable();
                    
                    getClients();
                    hideLoader2();
                    
                    alert(data.message)
                },
                error: function (jqXHR, textStatus, errorThrown) {  
                	
                	console.log(jqXHR);
                	console.log(textStatus);
                    console.log(errorThrown);
                    
                    hideLoader2();
                }
            });
	        
    	}
    }
    
  
    
	$(document).on("submit", "form", function(e){
	    e.preventDefault();
	    //alert('it works!');
	    
	    showLoader2();
	    
	    var data = $('#actionForm').serialize();
	    
	    console.log(data);
	    
	    
	    $.ajax({
            type: "POST",
            url: '../../servlet/ws/SetCustomerConfiguration',
            cache: false,
            data: data,
            dataType: 'json',
            success: function (_data) {
                console.log('successfully added!');
                console.log(JSON.stringify(_data));
                
                $('#exampleModal').modal('toggle');
                
                loadTable();
                
                getClients();
                hideLoader2();
                
                alert(_data.message);
            },
            error: function (jqXHR, textStatus, errorThrown) {  
            	
            	console.log(jqXHR);
            	console.log(textStatus);
                console.log(errorThrown);
                
                hideLoader2();
            }
        });
	    
	    return false;
	});
	
	
	function loadTable() {
		
		table.destroy();
		
		$('#clients_data_table tbody').remove();
		$('#clients_data_table').append("<tbody id=\"clients_list\"></tbody>");
		
		$('#clients_list').append('<tr><td colspan="8">Please Wait...</td></tr>');	
		
	} 
	
	
	function getCustomers() {
    	
    	var username = '<%=username%>';
    	
    	showLoader2();
    	
    	$.ajax({
            type: "GET",
            accepts: "application/json",
            url: "../../ats/GetCustomers?username="+username,
            contentType: "application/json",
            cache: false,
            success: function (data) {
                
            	//alert(JSON.stringify(data));
            	
            	 $.each(data.customers, function (i, obj) {
            		 
            		
            		 $('#customerId').append('<option value="'+obj.id+'">'+obj.accountid+' - '+obj.description+'</option>'); 
            		 
                 	
            	 });
            	 
            	 $('#customerId').selectpicker('refresh');
            	
                
                hideLoader2();
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                console.log(errorThrown);
            }
        });
    	
    	
    }
	
	function chgForm(idx, val) {
		
		try {
			if(parseInt(val)>0) {
				$('#'+idx+'from').prop('disabled', true);
				$('#'+idx+'to').prop('disabled', true);
				$('#'+idx+'from').val(val);
				$('#'+idx+'to').val(val);
			}
			else {
				// variable
				$('#'+idx+'from').prop('disabled', false);
				$('#'+idx+'to').prop('disabled', false);
				$('#'+idx+'from').val("");
				$('#'+idx+'to').val("");
			}
		}
		catch(error) {
			console.log(error);
		}
		
		$('.ss').selectpicker('refresh');
	}
	
</script>

<div class="modal" id="exampleModal" tabindex="-1" role="dialog">
  	  <form action="#" id="actionForm" name="actionForm" method="post">
  	  <input type="hidden" name="tipoForm" id="tipoForm" value="I"/>
  	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <a class="close" data-dismiss="modal">&times;</a>
			<h3 class="font-thin">Rule Detail</h3>
	      </div>
	      <div class="modal-body">
	        <div class="form-group pull-in clearfix">
		        <div class="col-sm-3">
		          <label>Rule ID</label>
		          <input type="text" id="ruleid" name="ruleid" class="form-control" value="" readonly>   
		        </div>
		    </div>
		    <div class="line line-dashed b-b line-lg pull-in"></div>
	    	<div class="form-group pull-in clearfix">
		        <div class="col-sm-12">
		          <label>Customer</label>
		          <select name="customerId" id="customerId"
	              	class="form-control ss" data-required="true">
	              	<option value="">-</option>
	               </select>
	              </div>
		    </div>
		    <div class="line line-dashed b-b line-lg pull-in"></div>
	    	<div class="form-group pull-in clearfix">
		        
		        
		        <div class="col-sm-3">
		          <label>Ip1</label>
		          <select name="ip1" id="ip1"
	              	class="form-control ss" data-required="true" onchange="chgForm(1, this.value)">
	              	<option value="">-</option>
	              	<option value="-1">Variable</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>Ip2</label>
		          <select name="ip2" id="ip2"
	              	class="form-control ss" data-required="true" onchange="chgForm(2, this.value)">
	              	<option value="">-</option>
	              	<option value="-1">Variable</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>Ip3</label>
		          <select name="ip3" id="ip3"
	              	class="form-control ss" data-required="true"onchange="chgForm(3, this.value)">
	              	<option value="">-</option>
	              	<option value="-1">Variable</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>Ip4</label>
		          <select name="ip4" id="ip4"
	              	class="form-control ss" data-required="true" onchange="chgForm(4, this.value)">
	              	<option value="">-</option>
	              	<option value="-1">Variable</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
		    </div>
		    
		    <div class="line line-dashed b-b line-lg pull-in"></div>
	    	<div class="form-group pull-in clearfix">
		        <div class="col-sm-3">
		          <label>From</label>
		          <select name="from1" id="1from"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>&nbsp;</label>
		          <select name="from2" id="2from"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>&nbsp;</label>
		          <select name="from3" id="3from"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>&nbsp;</label>
		          <select name="from4" id="4from"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              		<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
		    </div>
		    
		    <div class="line line-dashed b-b line-lg pull-in"></div>
	    	<div class="form-group pull-in clearfix">
		        <div class="col-sm-3">
		          <label>To</label>
		          <select name="to1" id="1to"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>&nbsp;</label>
		          <select name="to2" id="2to"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>&nbsp;</label>
		          <select name="to3" id="3to"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              	<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
	            <div class="col-sm-3">
		          <label>&nbsp;</label>
		          <select name="to4" id="4to"
	              	class="form-control ss" data-required="true" disabled>
	              	<option value="">-</option>
	              		<%
	              	for(int i=1;i<255;i++) {
	              	%>
	              	<option value="<%=i%>"><%=i %></option>
	              	<%
	              	}
	              	%>
	              	
	              </select>
	            </div>
		    </div>
		    
		    <div class="line line-dashed b-b line-lg pull-in"></div>
	    	<div class="form-group pull-in clearfix">
		        <div class="col-sm-6">
		    		<label>Size</label>
		          
				    <div class="radio i-checks">
                      <label>
                        <input type="radio" name="size" value="1" data-required="true">
                        <i></i>
                        1
                      </label>
                    </div>
                    <div class="radio i-checks">
                      <label>
                        <input type="radio" name="size" value="254" data-required="true">
                        <i></i>
                        254	
                      </label>
                    </div>
                </div>
                <div class="col-sm-6">
		    		<label>Tunneling</label>
		          
				    <div class="radio i-checks">
                      <label>
                        <input type="radio" name="tunneling" value="true" data-required="true">
                        <i></i>
                        Yes
                      </label>
                    </div>
                    <div class="radio i-checks">
                      <label>
                        <input type="radio" name="tunneling" value="false" data-required="true">
                        <i></i>
                        No	
                      </label>
                    </div>
                </div>
              </div>
                
  
	      </div>
	      <div class="modal-footer">
	      	<img src="../../images/spacer.gif" onload="parsleyForm();selpick();getCustomers()"/>
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <input type="submit"  value="Save changes" class="btn btn-primary btn-lg"/>
	      </div>
	    </div>
	  </div>
	  </form>
	</div>

<%@ include file="../../inc/autorized.jsp" %>
      
</body>
</html>
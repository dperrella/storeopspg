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
  <title><%=propLang.get("cus_dett") %> | SmartSuite</title>
  <meta name="description" content="SmartHR" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.css" />
  
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
                  	
              	<aside class="aside aside-md bg-light dk" >
	              <section class="vbox animated fadeInUp">
	                <section class="w-f-md scrollable hover">
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
	            		  <span class="bg-light dker list-group-item">
			                  <b>Category</b>
			              </span>
			                
	            		  <div id="treeview"></div>
		                  
		              </div>
	            	</section>
	              	<footer class="footer footer-md bg-light dker">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <a href="../category/addCategory.jsp?type=0" data-toggle="ajaxModal" class="btn btn-primary btn-block"><i class="fa fa-edit"></i> New Category</a>
	                        </span>
	                      </div>
	                    </div>
                    </footer>
	              </section>
	            </aside>
	            <!-- / side content -->  
	            
	            <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      	
                      	
                      	<div class="row row-sm" id="intro">
							<div class="col-xs-12">
		                    	<div class="text-center m-b-lg">
									<h1 class="fontBig text-white animated">Select or Add Category</h1>
									<h3 class="m-t-xl"><a href="customers-dett.htm?id=${idcus}&tipoForm=M">Back to Customer</a></h3>
								</div>
							</div>
						</div>
						
		                <h2 class="font-thin m-b" id="node_title"></h2>
		                
		                <input type="hidden" id="_category" name="_category" value=""/>
		                <input type="hidden" id="_type" name="_type" value=""/>
		                
	                	<section class="panel panel-default" id="tb-data" style="display:none">
			              <header class="panel-heading font-bold">
			                <span id="table_id">Category Data</span>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="customers-dett.htm?id=${idcus}&tipoForm=M">Back To Customer</a></li>
									<li id="_addchild"><a href="../category/addCategory.jsp?type=1" data-toggle="ajaxModal">Add Child</a></li>
									<li id="_importdata"><a href="../category/importData.jsp" data-toggle="ajaxModal">Import Data</a></li>
									<li><a href="#" onclick="del()">Delete Category</a></li>
									<li id="_delsel"><a href="#" class="delete-row">Delete Selected</a></li>
									<li><div class="checkbox i-checks"><label><input type="checkbox" id="required" name="required" onclick="setCategoryRequired()"><i></i> Required</label></div></li>
								</ul>
							</div>
						  </section>
			              
			              <div class="table-responsive">
			              	<div class="padder m-b" id="_addvalue"> 
			               	<form>
					        	<label>Add Value in Category</label>
					        	<div class="input-group">
		                          <input type="text" class="form-control" id="_val" placeholder="Value">
		                          <span class="input-group-btn">
		                            <button class="btn btn-default add-row" id="_valbtn" type="button">Add</button>
		                          </span>
		                        </div>
						    </form>
					        </div>
			                
                      		<table id="test" class="table table-striped b-t">
							<thead>
								<tr>
									<th style="width:5%">#</th>
									<th>Value</th>
								</tr>
							</thead>
							<tbody id="fbody">
							
							</tbody>
							<tfoot>
	      					  	  <tr>
	      					  	  	<td colspan="2"><div id="total" style="margin-left:5px">0 Rows</div></td>
	      					  	  </tr>
	      					</tfoot>
							</table>
                      	  </div>
                        </section>
		                
		            
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
  
  <script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.js"></script>
  
  
  
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
	
	jqueryRic();
	
	$(document).ready(function(){
		$.ajax({ 
			url: "../../servlet/ws/GetCustomersCategory?idcus=${idcus}",
		   	method:"POST",
		   	dataType: "json",       
		   	beforeSend: function( xhr ) {
		   	    showLoader2();
		   	},
		   	success: function(data)  
	   		{
		   		hideLoader2();
	  			$('#treeview').treeview({
	  				data: data,
	  				selectedBackColor: '#68b4c8',
	  				onNodeSelected: function(event, data) {
  				    	
	  					//alert(data.name);
	  					$('#node_title').show();
  				    	$('#node_title').html('${description} '+data.name);
  				    	$('#_category').val(data.name);
  				    	$('#_type').val(data.type);
  				    	$('#intro').hide();
  				    	$('#tb-data').show();
  				    	
  				    	var hasChild = data.hasChild;
  				    	if(hasChild==undefined)
  				    		hasChild = false;
  				    	
  				    	getCategoryDetail(data.name, hasChild);
  				  	}
	  			});
	   		}   
	 	});
	 
	});
	
	function del() {
		
		var category = $('#_category').val();
		var parent = '0';
		var type = 'D';
		
		if(confirm("Confirm Delete Category?")) {
			setCategory(category, parent, type, '0');
			
			$('#intro').show();
		    $('#tb-data').hide();
		    $('#node_title').hide();
		}
		
		
	}
	
	function add(_type) {
		
		var category = $('#category').val();
		var type = 'I';
		var hasChild =  false;
		if(parseInt(_type)==0) {
			console.log("=0");
			parent = '0';
			hasChild =  false;
		}
		else {
			console.log("!=0");
			parent = $('#_category').val();
			hasChild =  true;
		}
		
		setCategory(category, parent, type, hasChild);
		
		$('#_category').val('');
		$('#_type').val('');
		
		
		$('.modal').remove(); 
		$('div.modal-backdrop').remove();
	}
	
	function setCategory(category, parent, type, _hasChild) {
		
		$.ajax({ 
			url: "../../servlet/ws/SetCustomersCategory?idcus=${idcus}&category="+category+"&parent_category="+parent+"&type="+type,
		   	method:"POST",
		   	dataType: "json",       
		   	beforeSend: function( xhr ) {
		   	    showLoader2();
		   	},
		   	success: function(data)  
	   		{
		   		hideLoader2();
		   		
		   		if(_hasChild==true) {
		        	$('#_importdata').hide();
		        	//$('#_addvalue').hide();
		        	
		        	$("#_val").attr("disabled", true)
		        	$("#_valbtn").attr("disabled", true)
		        	$("#_delsel").hide();
		        }
		        else {
		        	$('#_importdata').show();
		        	//$('#_addvalue').show();
		        	
		        	$("#_val").attr("disabled", false)
		        	$("#_valbtn").attr("disabled", false)
		        	$("#_delsel").show();
		        }
		   		
	  			$('#treeview').treeview({
	  				data: data,
	  				selectedBackColor: '#68b4c8',
	  				onNodeSelected: function(event, data) {
  				    	
	  					//alert(data.name);
	  					$('#node_title').show();
  				    	$('#node_title').html('${description} '+data.name);
  				    	$('#_category').val(data.name);
  				    	$('#_type').val(data.type);
  				    	$('#intro').hide();
  				    	$('#tb-data').show();
  				    	
  				    	var hasChild = data.hasChild;
  				    	if(hasChild==undefined)
  				    		hasChild = false;
  				    	
  				    	getCategoryDetail(data.name, hasChild);
  				  	}
	  			});
	   		}   
	 	});
		
	}
	
	function getCategoryDetail(category, hasChild) {
		
		$.ajax({ 
			url: "../../servlet/ws/GetCustomersCategoryValues?idcus=${idcus}&category="+category,
		   	method:"POST",
		   	dataType: "json",       
		   	beforeSend: function( xhr ) {
		   	    showLoader2();
		   	},
		   	success: function(data)  
	   		{
		   		hideLoader2();
	  			
		   		
		   		$('#test tbody').empty();
		   		
		   		
		   		var lista = data.value;
		   		$.each(lista, function(index, lista) {
		   			
		   			//console.log(lista );
		   			
		   			var markup = "<tr><td><div class='checkbox i-checks'><label><input type='checkbox' name='record'><i></i></label></div></td><td>" + lista + "</td></tr>";
		   	        $("table tbody").append(markup);
		   	        
		   			
		   		});
		   		
		   		var rowCount = $('#test tbody tr').length;
		        $("#total").html(rowCount+" Rows");
		   		
		        console.log(data.required);
		        if(data.required=='t')
		   			$('#required').prop('checked', true);
		        else
		        	$('#required').prop('checked', false);
		        
		   		if(rowCount>0) {
		   			$('#_addchild').hide();
		   			//$('#_importdata').hide();
		   		}
		   		else {
		   			$('#_addchild').show();
		   			//$('#_importdata').show();
		   		}
		   			
		        console.log(hasChild);
		        if(hasChild==true) {
		        	$('#_importdata').hide();
		        	//$('#_addvalue').hide();
		        	
		        	$("#_val").attr("disabled", true)
		        	$("#_valbtn").attr("disabled", true)
		        	$("#_delsel").hide();
		        }
		        else {
		        	$('#_importdata').show();
		        	//$('#_addvalue').show();
		        	
		        	$("#_val").attr("disabled", false)
		        	$("#_valbtn").attr("disabled", false)
		        	$("#_delsel").show();
		        }
		   		//alert(JSON.stringify(data));
		   		
	   		}   
	 	});
		
	}
	
	function setCategoryValues(_val) {
		
		var category = $('#_category').val();
		
		//alert(category+' '+JSON.stringify(_val));
		
		
		$.ajax({ 
			url: "../../servlet/ws/SetCustomersCategory",
			data: "idcus=${idcus}&category="+category+"&type=V&value="+JSON.stringify(_val),
		   	method:"POST",
		   	dataType: "json",       
		   	beforeSend: function( xhr ) {
		   	    showLoader2();
		   	},
		   	success: function(data)  
	   		{
		   		hideLoader2();
		   		
		   		var rowCount = $('#test tbody tr').length;
		        
		   		if(rowCount>0) {
		   			$('#_addchild').hide();
		   			//$('#_importdata').hide();
		   		}
		   		else {
		   			$('#_addchild').show();
		   			//$('#_importdata').show();
		   		}
		        
		   		
		   		
	  			$('#treeview').treeview({
	  				data: data,
	  				selectedBackColor: '#68b4c8',
	  				onNodeSelected: function(event, data) {
  				    	
	  					//alert(data.name);
	  					$('#node_title').show();
  				    	$('#node_title').html('${description} '+data.name);
  				    	$('#_category').val(data.name);
  				    	$('#_type').val(data.type);
  				    	$('#intro').hide();
  				    	$('#tb-data').show();
  				    	
  				    	getCategoryDetail(data.name);
  				  	}
	  			});
		   		
	   		}   
	 	});
		
	}
	
	function setCategoryRequired() {
		
		var category = $('#_category').val();
		var chk = $('#required').is(":checked");
		var _val = false;
		if(chk) {
			_val = true;
		}
		else {
			_val = false;
		}
		
		//alert(category+' '+JSON.stringify(_val));
		
		
		$.ajax({ 
			url: "../../servlet/ws/SetCustomersCategory",
			data: "idcus=${idcus}&category="+category+"&type=R&required="+_val,
		   	method:"POST",
		   	dataType: "json",       
		   	success: function(data)  
	   		{
		   		
	   		}   
	 	});
		
	}
	
	
	$(".add-row").click(function(){
        var _val = $("#_val").val();
        var markup = "<tr><td><div class='checkbox i-checks'><label><input type='checkbox' name='record'><i></i></label></div></td><td>" + _val + "</td></tr>";
        $("table tbody").append(markup);
        
        var rowCount = $('#test tbody tr').length;
        
        
        $("#_val").val("");
        $("#total").html(rowCount+" Rows");
        
        var newList = []
        $("#test > tbody > tr").each(function () {
           	
        	//console.log($(this).find('td').eq(1).text());
        	newList.push($(this).find('td').eq(1).text());
        });
        
        //console.log(JSON.stringify(newList));
        
        setCategoryValues(newList);
        
        
    });
    
    // Find and remove selected table rows
    $(".delete-row").click(function(){
        $("table tbody").find('input[name="record"]').each(function(){
            if($(this).is(":checked")){
                $(this).parents("tr").remove();
            }
        });
        
		var rowCount = $('#test tbody tr').length;
        $("#total").html(rowCount+" Rows");
        
        
        var newList = []
        $("#test > tbody > tr").each(function () {
           	
        	//console.log($(this).find('td').eq(1).text());
        	newList.push($(this).find('td').eq(1).text());
        });
        
        //console.log(JSON.stringify(newList));
        
        setCategoryValues(newList);
        
    });
    
    
    function importCsv() {
   	
   		var category = $('#_category').val();
   		
   		$('#___category').val(category);
   		$('#___idcus').val('${idcus}');
   		
		
        var fd = new FormData();
        var files = $('#file')[0].files[0];
        fd.append('category',category); 
        fd.append('idcus','${idcus}'); 
        fd.append('file',files);
        
        
        $.ajax({
        	url: '../../servlet/ws/ImportCsv',
            type: 'post',
            //encType : "multipart/form-data",
            cache : false,
            data: fd,
            contentType: false,
            processData: false,
            success: function(response){
                 
            	$('.modal').remove(); 
        		$('div.modal-backdrop').remove();
        		
            	getCategoryDetail(category);
            	 
            },
       	});
	}
   	
   
    
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  
  </body>
</html>
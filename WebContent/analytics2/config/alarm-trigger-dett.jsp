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
            <%@ include file="../../inc2/menuApp6.jsp" %>
                   
            <aside class="col-sm-9 " >
              <section class="vbox">
                <section class="scrollable" id="bjax-target">
                      
                	  
		                <h2 class="font-thin m-b"><%=propLang.get("aat_dsfun") %></h2>
		                
		                <form data-validate="parsley" method="post" action="alarm-trigger-dett.htm">
                  		<input type="hidden" name="azione" value="A">
                  		<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">
		                
		                <section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                <%=propLang.get("aat_dsfun") %>
			              </header>
			              
			              <section class="panel wrapper">
			              	<div class="subnav">
								<ul class="nav nav-pills">
									<li><a href="alarm-trigger-list.htm"><%=propLang.get("torna") %></a></li>
								</ul>
							</div>
						  </section>
						  
						  <div class="wrapper">
						  
						  	<div id="msg">${msg}</div>
						  	
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("aat_id") %></label>
						          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly ${key}>   
						        </div>
						    </div>
						  	
						  	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("aat_code") %></label>
						          <input type="text" id="code" name="code" class="form-control" data-required="true"  value="${code}">   
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("aat_descrizione") %> (IT)</label>
						          <textarea id="description_it" name="description_it" data-required="true" 
						          style="height:100px" class="form-control">${description_it}</textarea>  
						        </div>
						        
						        <div class="col-sm-4">
						          <label><%=propLang.get("aat_descrizione") %> (EN)</label>
						          <textarea id="description_en" name="description_en" data-required="true" 
						          style="height:100px" class="form-control">${description_en}</textarea>   
						        </div>
						        
						        <div class="col-sm-4">
						          <label><%=propLang.get("aat_descrizione") %> (ES)</label>
						          <textarea id="description_es" name="description_es" data-required="true" 
						          style="height:100px" class="form-control">${description_es}</textarea>
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						  	<div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label><%=propLang.get("aat_threshold") %></label>
						          <input type="text" data-type="number" data-required="true" id="threshold" name="threshold" class="form-control" value="${threshold}"> 
						        </div>
						        <div class="col-sm-8">
						          <label>Condition</label>
						          <select id="sign" name="sign" class="form-control parsley-validated">
						          	<option value=">=">Trigger enabled if Threashold IS Reached</option>
						          	<option value="<">Trigger enabled if Threashold IS NOT Reached</option>
						          </select>  
						        </div>
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Times Frame KPI Calculation</label>
						          <select id="timeunit" name="timeunit" class="form-control parsley-validated">
						          	<option value="3600">1 Hour</option>
						          	<option value="21600">6 Hours</option>
						          	<option value="43200">12 Hours</option>
						          	<option value="86400">24 Hours</option>
						          	<option value="172800">48 Hours</option>
						          	<option value="259200">72 Hours</option>
						          </select>
						        </div>
						        <div class="col-sm-8">
		                        
		                        	<label>&nbsp;</label>
		                        	
		                        	<br/>
		                        	<label class="checkbox-inline i-checks">
			                          <input type="radio" 
				                        	id="orario1" name="orario" value="true"><i></i> Workinh Hours
			                        </label>
			                        <label class="checkbox-inline i-checks">
			                          <input type="radio" 
				                        	id="orario2" name="orario" value="false"><i></i> Solar Hours
			                        </label>
			                        
				                 </div>
				                 
						    </div>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						     
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-12">
							        <label>KPI</label>
								    <select id="kpi" name="kpi" data-required="true" 
								    	class="form-control parsley-validated chosen-select">
								          <option value="">-</option>
								          <c:forEach items="${ec}" var="ec" varStatus="status">
				                          <option value="${ec.code}">${ec.code} - ${ec.description}</option>
				                          </c:forEach>
		                        	</select>   
		                        </div>
	                        </div>
	                        
                        
	                        <!-- 
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						    	<div class="col-sm-12">
							        <label><%=propLang.get("aat_adaptive") %></label>
								    <div class="checkbox i-checks">
			                          <label>
			                            <input type="checkbox" name="adaptive" class="parsley-validated" value="t" ${adaptive}><i></i>
			                          </label>
			                        </div>
			                    </div>
	                        </div>
						    -->
						    <input type="hidden" name="adaptive" value="f"/>
						    
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
						        <div class="col-sm-4">
						          <label>Control Frequency</label>
						          
						          <!-- 
						          <input type="text" data-type="number" data-required="true" id="controlfrequency" name="controlfrequency" class="form-control" value="${controlfrequency}">   
						          -->
						          <select class="form-control" data-required="true" id="controlfrequency" name="controlfrequency">
						          	<option value="1">1 Hour</option>
						          	<option value="3">3 Hours</option>
						          	<option value="6">6 Hours</option>
						          	<option value="12">12 Hours</option>
						          </select>
						        </div>
						    </div>
						   	
						   	
						   	<!-- >
						    <div class="line line-dashed b-b line-lg pull-in"></div>
						    
						    <div class="form-group pull-in clearfix">
	                        	<div class="col-sm-6">
		                        
		                        	<label><%=propLang.get("ara_orario") %></label>
			                        <div class="radio i-checks">
				                      <label class="i-checks">
				                        <input type="radio" 
				                        	id="orario1" name="orario" value="true"><i></i> <%=propLang.get("aar_S") %>
				                      </label>
				                    </div>
				                    
				                    <div class="radio i-checks">
				                      <label class="i-checks">
				                        <input type="radio" 
				                        	id="orario2" name="orario" value="false"><i></i> <%=propLang.get("aar_N") %>
				                      </label>
				                    </div>
		                        </div>
		                    </div>
		                    -->
						    
						    <div class="panel-footer bg-light">
		                      <div class="padder">
	                            <span class="h4 block">Scheduler</span>
	                          </div>
		                    </div>
					        <br/> 
					        
					        <div class="form-group pull-in clearfix">
						    	<div class="col-sm-2">
							        <label><%=propLang.get("hour_from") %></label>
								    <select id="hour_from" name="hour_from"
								    	class="form-control parsley-validated" onchange="controllaOrario()">
								          <option value="">-</option>
								          <option value="00:00:00">00:00</option>
								          <option value="01:00:00">01:00</option>
								          <option value="02:00:00">02:00</option>
								          <option value="03:00:00">03:00</option>
								          <option value="04:00:00">04:00</option>
								          <option value="05:00:00">05:00</option>
								          <option value="06:00:00">06:00</option>
								          <option value="07:00:00">07:00</option>
								          <option value="08:00:00">08:00</option>
								          <option value="09:00:00">09:00</option>
								          <option value="10:00:00">10:00</option>
								          <option value="11:00:00">11:00</option>
								          <option value="12:00:00">12:00</option>
								          <option value="13:00:00">13:00</option>
								          <option value="14:00:00">14:00</option>
								          <option value="15:00:00">15:00</option>
								          <option value="16:00:00">16:00</option>
								          <option value="17:00:00">17:00</option>
								          <option value="18:00:00">18:00</option>
								          <option value="19:00:00">19:00</option>
								          <option value="20:00:00">20:00</option>
								          <option value="21:00:00">21:00</option>
								          <option value="22:00:00">22:00</option>
								          <option value="23:00:00">23:00</option>
								     </select>   
		                        </div>
		                        
		                        <div class="col-sm-2">
							        <label><%=propLang.get("hour_to") %></label>
								    <select id="hour_to" name="hour_to"
								    	class="form-control parsley-validated" onchange="controllaOrario()">
								          <option value="">-</option>
								          <option value="00:00:00">00:00</option>
								          <option value="01:00:00">01:00</option>
								          <option value="02:00:00">02:00</option>
								          <option value="03:00:00">03:00</option>
								          <option value="04:00:00">04:00</option>
								          <option value="05:00:00">05:00</option>
								          <option value="06:00:00">06:00</option>
								          <option value="07:00:00">07:00</option>
								          <option value="08:00:00">08:00</option>
								          <option value="09:00:00">09:00</option>
								          <option value="10:00:00">10:00</option>
								          <option value="11:00:00">11:00</option>
								          <option value="12:00:00">12:00</option>
								          <option value="13:00:00">13:00</option>
								          <option value="14:00:00">14:00</option>
								          <option value="15:00:00">15:00</option>
								          <option value="16:00:00">16:00</option>
								          <option value="17:00:00">17:00</option>
								          <option value="18:00:00">18:00</option>
								          <option value="19:00:00">19:00</option>
								          <option value="20:00:00">20:00</option>
								          <option value="21:00:00">21:00</option>
								          <option value="22:00:00">22:00</option>
								          <option value="23:00:00">23:00</option>
								     </select>   
		                        </div>
		                        
	                        </div>
	                        
	                        
					      
						  </div>
						  
						  <footer class="panel-footer text-right bg-light lter">
					      	<button type="submit" id="submit" class="btn btn-success btn-lg" ${btnActive}><%=propLang.get("btnConferma") %></button>
					      </footer>
						  
			              
			              
		  				</section>
		  				</form>
		  				
		  				  
			        </section> 
	              </section>
	            </aside>
                
                <section class="col-sm-3 no-padder">
                  <section class="vbox">
                    <section class="scrollable bg-light dk w-f-md">
                    	<div class="bg-light dker wrapper">
	                     	<p class="h4 font-thin">Trigger Relation List</p>
                        </div>
                        <div id="stores">
                        	<ul class="list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        	<c:forEach items="${rel}" var="rel" varStatus="status">
	                          <li class="list-group-item">
	                          	<div class="pull-right m-l-xs m-r-sm">
									<a href="#" onclick="deleteRel('${id}','${rel.xcdel}','${rel.cmp2}')" class="btn btn-default btn-xs btn-danger m-l"><i class="fa fa-times-circle"></i></a>	                          	
	                          	</div>
			                    <div class="clear text-ellipsis">
		                        	${rel.xdtab}<br/><small class="text-muted">${rel.cmp1}</small>
		                        </div> 
		                      </li>
	                        </c:forEach>
	                        </ul>
                        </div>
                    </section>
                    <footer class="footer footer-md bg-light dker">
	                    
	                    <c:if test="${tipoForm!='I'}">
	                    <a href="#" onclick="openModal();"
	                    	class="btn btn-default btn-block">New Relation</a> 
	                    </c:if>
	                    
	                </footer>
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
				        
                        <a href="alarm-trigger-list.htm" style="padding-left: 30px" class="list-group-item active">
                          <i class="icon-pin icon"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=propLang.get("aat_dsfun") %></span>
                        </a>

						
                      </div>
	                </section>
	                <footer class="footer footer-md bg-black">
	                  <form action="alarm-trigger-list.htm" class="" role="search">
	                    <div class="form-group clearfix m-b-none">
	                      <div class="input-group m-t m-b">
	                        <span class="input-group-btn">
	                          <button type="submit" class="btn btn-sm bg-empty text-muted btn-icon"><i class="fa fa-search"></i></button>
	                        </span>
	                        <input type="text" name="q" class="form-control input-sm text-white bg-empty b-b b-dark no-border hidden-nav-xs" placeholder="<%=propLang.get("aat_cerca") %>">
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
	
  	// values
  	$('#kpi').val('${kpi}');
  	$('#timeunit').val('${timeunit}');
  	$('#sign').val('${sign}');
  	$('#hour_from').val('${hour_from}');
  	$('#hour_to').val('${hour_to}');
  	$('#controlfrequency').val('${controlfrequency}');
  	
  	var $radios2 = $('input:radio[name=orario]');
    if($radios2.is(':checked') === false) {
        $radios2.filter('[value=${orario}]').prop('checked', true);
    }
    
  	
  	function controllaOrario() {
  		
  		var hour_from = $('#hour_from').val();
  		var hour_to = $('#hour_to').val();
  		
  		if(hour_from=='')
  			hour_from = "00:00:00";
  		if(hour_to=='')
  			hour_to = "00:00:00";
  		
  		var hour_from_num = hour_from.replace(":", "");
  		var hour_to_num = hour_to.replace(":", "");
  		
  		if(hour_to_num<hour_from_num) {
  			$('#hour_to').val(hour_from);
  		}
  	} 
  	
  	function setSelectPicker() {
		$('.selectpicker').selectpicker();
	}
	
  	//MODAL
	
  	var om = '${openModal}';
  	
  	if(om=='Y') {
  		
		openModal();
		
	}
  	
  	function openModal() {
  		$( 'div.modal-backdrop').remove();
		$( '.modal' ).remove();
		
		var href = "../popup/rel_trigger_stores.jsp?id_trigger=${id}";
		
		$.get(href, function(data) {
			$('<div class="modal" >' + data + '</div>').modal();
		});
  	}
  	
  	function readStores(id_customer) {
  		
		$.getJSON('../../servlet/ws/GetSto?id_customer='+id_customer, function (data) {
  		    
  		    var lista = data.listings;
  		    
  		    var key = "";
		  	var value = "";
		  	
		  	var $select = $('#idsto');                        
		    $select.find('option').remove();
		    $('<option>').val('*').text('<%=propLang.get("all_stores")%>').appendTo($select);     
		    
  		    $.each(lista, function(index, lista) {
  			  
  		    	key = lista.xcdel;
  		    	value = lista.xdtab;
  		    	
  		    	$('<option>').val(key).text(value).appendTo($select);     
  			  
  		    });
  		    
  		    $('#idsto').trigger("chosen:updated");
  		    
  		    
		});
  		
  	}
  	
  	function saveRel() {
  		
  		var idtri = $('#id_trigger').val();
  		var idcus = $('#idcus').val();
  		var idsto = $('#idsto').val();
  		var sto = idsto.join();
  		var err = '';
  		if(idcus=='') {
  			alert('<%=propLang.get("sel_cust")%>');
  			err+= 'Y';
  		}
  		if(sto=='') {
  			alert('<%=propLang.get("sm_almeno_store")%>');
  			err+= 'Y';
  		}
  		
  		if(err=='') {
  			// Submit form
  			//alert(idcus+" "+sto);
  			
  			$.ajax({
  		      url: '../ajax/saveRel.jsp',
  		      type: 'POST',
  		      dataType: 'html',
  		      async: true,
  		      data: 'idtri='+idtri+'&idcus='+idcus+'&idsto='+idsto,
  		      success: function (data) {
  		    	$( 'div.modal-backdrop').remove();
  				$( '.modal' ).remove();
  				
  				alert("<%=propLang.get("msgSuccess")%>");
  				
  				location.href="alarm-trigger-dett.htm?tipoForm=M&id="+idtri;
  				
  		      },
  		      error: function () {
  		      	alert('Errore! si e\' verificato un errore.');
  		      }
  		});
  			
  		}
  		
  	}
  	
  	function reloadChosen() {
  		
  		$(".chosen-select").chosen({no_results_text: "Oops, nothing found!"});
  		
  		$("#idcus").chosen().change(function(){
  			var id = $(this).val();
	        readStores(id); 
	    });
  		
  	}
  	
  	function deleteRel(id, idcus, idsto) {
  		
  		if(confirm('<%=propLang.get("msgDelete")%>?')) {
  			
  			$.ajax({
    		      url: '../ajax/delRel.jsp',
    		      type: 'POST',
    		      dataType: 'html',
    		      async: true,
    		      data: 'idtri='+id+'&idcus='+idcus+'&idsto='+idsto,
    		      success: function (data) {
    		    	
    		    	showLoader2();
    				location.href="alarm-trigger-dett.htm?tipoForm=M&id="+id;
    				
    		      },
    		      error: function () {
    		      	alert('Errore! si e\' verificato un errore.');
    		      }
    		});
  		}
  	}
  	
  </script>
  
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>
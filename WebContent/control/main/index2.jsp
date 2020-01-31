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
              
              <%@ include file="../../inc2/menuApp3.jsp" %>
              
              <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="bg-light dker list-group-item">
		                  <b><%=propLang.get("pop_us_sel") %> <%=propLang.get("tab_dettagli") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                	<li class="list-group-item" id="logsLi"><a href="#" onclick="getLogs()">
		                		<i class="fa fa-list-alt"></i> Monitoring Alarms</a></li>
	                        <li class="list-group-item" id="dnpLi"><a href="#" onclick="getDnp()">
	                        	<i class="fa fa-desktop"></i> Device Not Paired</a></li>
	                        <li class="list-group-item" id="pcnuLi"><a href="#" onclick="getPcnu()">
	                        	<i class="fa fa-users"></i> Counter Not Update</a></li>
	                        <!-- 
	                        <li class="list-group-item" id="annuLi"><a href="#" onclick="getAnnu()">
	                        	<i class="icon-bar-chart"></i> Analytics Last Update</a></li>
	                        -->
	                        <!-- 
	                        <li class="list-group-item" id="alLi"><a href="#" onclick="getAl()">
	                        	<i class="fa fa-bullhorn"></i> Analytics Active Alarms</a></li>
	                        -->
	                        <li class="list-group-item" id="al2Li"><a href="#" onclick="getAl2()">
	                        	<i class="fa fa-warning"></i> Analytics2.0 Active Alarms</a></li>
	                        <li class="list-group-item" id="ulLi"><a href="#" onclick="getUl()">
	                        	<i class="fa fa-list"></i> Updating List</a></li>
	                        <li class="list-group-item" id="dneLi"><a href="#" onclick="getDne()">
	                        	<i class="fa fa-ban"></i> Devices Not Enabled</a></li>
	                        <li class="list-group-item" id="jsLi"><a href="#" onclick="getJs()">
	                        	<i class="fa fa-gears"></i> Job Status</a></li>
	                        
	                    </ul>
                        
                      </div>
                    </section>
                  </section>
                </aside>
                <!-- / side content -->
              
              
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                  	  <a href="index2.htm" class="pull-right text-muted m-t-lg"><i class="icon-refresh i-lg inline"></i> Official</a>
                  
                      <h2 class="font-thin m-b">Desk Smart Control <%=propLang.get("versione") %> 2.0.0</h2>
                      
                      <div class="row row-sm">
	              	
		              	<div class="col-xs-12">
	
							    	
			                
			                <section class="panel panel-default" id="logsPanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-list-alt"></i></span>
			                      <span class="hidden-sm">Monitoring Alarms</span>
			                      
			                    </header>
			                    
			                    <div class="panel-body">
			                    	<div id="x_main" style="height:400px;"></div>
			                    </div>
			                    <footer class="panel-footer bg-white b-b">
				                  <div class="row text-center no-gutter">
				                    <div class="col-xs-12">
				                      <p class="h3 font-bold m-t" id="mainTot"></p>
				                      <p class="text-muted">DEVICES IN ERROR</p>                        
				                    </div>
				                  </div>
				                </footer>
				                    
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="logs" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th><%=propLang.get("sm_cont_dd") %></th>
									        <th><%=propLang.get("sm_cont_do") %></th>
									        <th><%=propLang.get("sm_cont_cl") %></th>
									        <th><%=propLang.get("sm_cont_ne") %></th>
									        <th><%=propLang.get("sm_cont_ce") %></th>
									        <th>Note</th>
									        <th><%=propLang.get("sm_cont_is") %></th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="8"><div id="logs_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default" id="dnpPanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-desktop"></i></span>
			                      <span class="hidden-sm">Device not Paired</span>
			                      
			                    </header>
			                    <div class="table-responsive horizontal">
					                <table class="table table-striped m-b-none" id="dnp" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="5%">Account ID</th>
									        <th width="5%">Store ID</th>
									        <th width="10%">Store Name</th>
									        <th width="5%">Device ID</th>
									        <th width="10%"><%=propLang.get("sm_cont_dn") %></th>
									        <th width="10%"><%=propLang.get("sm_cont_ts") %></th>
									        <th width="10%"><%=propLang.get("sm_cont_ip_") %></th>
									        <th width="5%"><%=propLang.get("sm_notpaired") %></th>
									        <th width="5%"><%=propLang.get("sm_preregistration") %></th>
									        <th width="5%"><%=propLang.get("sm_storename") %></th>
									        <th width="5%"><%=propLang.get("sm_storenote") %></th>	
									        <th></th>
									        <th></th>								        
									       
									      </tr>
									  </thead>
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="13"><div id="dnp_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default" id="pcnuPanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-users"></i></span>
			                      <span class="hidden-sm">People Counter Not Update</span>
			                      
			                    </header>
			                    
			                    <div class="panel-body">
			                    	<div id="x_main2" style="height:400px;"></div>
			                    </div>
			                    <footer class="panel-footer bg-white b-b">
				                  <div class="row text-center no-gutter">
				                    <div class="col-xs-12">
				                      <p class="h3 font-bold m-t" id="main2Tot"></p>
				                      <p class="text-muted">DEVICES NOT UPDATE</p>                        
				                    </div>
				                  </div>
				                </footer>
				                
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="pcnu" data-ride="datatables">
					                  <thead>
										  <tr>
									        <!-- 
									        <th width="10%"><%=propLang.get("sm_cont_is") %></th>
									        -->
									        <th width="10%">Account ID</th>
									        <th width="10%">Store ID</th>
									        <th width="10%">Door ID</th>
									        <th width="25%"><%=propLang.get("sm_cont_cl") %></th>
									        <th width="25%"><%=propLang.get("sm_cont_dn") %></th>
									        <th width="20%"><%=propLang.get("sm_cont_lt") %></th>
									      </tr>
									  </thead>
			      					 
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="6"><div id="pcnu_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default" id="annuPanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="icon-bar-chart"></i></span>
			                      <span class="hidden-sm">Analytics Last Update</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="annu" data-ride="datatables">
					                  <thead>
										  <tr>
									        <!-- 
									        <th width="10%"><%=propLang.get("sm_cont_is") %></th>
									        -->
									        <th width="10%">Account ID</th>
									        <th width="10%">Store ID</th>
									        <th width="10%">Door ID</th>
									        <th width="25%"><%=propLang.get("sm_cont_cl") %></th>
									        <th width="25%"><%=propLang.get("sm_cont_dn") %></th>
									        <th width="20%"><%=propLang.get("sm_cont_lt") %></th>
									      </tr>
									  </thead>
			      					 
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="6"><div id="annu_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default" id="alPanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-bullhorn"></i></span>
			                      <span class="hidden-sm">Analytics Active Alarms</span>
			                      
			                    </header>
			                    <div class="table-responsive horizontal">
					                <table class="table table-striped m-b-none" id="al" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="12%"><%=propLang.get("sm_a_id") %></th>
									        <th width="3%"><%=propLang.get("sm_a_dn") %></th>
									        <th width="3%"><%=propLang.get("sm_a_si") %></th>
									        <th width="6%"><%=propLang.get("sm_a_sn") %></th>
									        <th width="12%"><%=propLang.get("sm_a_ts") %></th>
									        <th width="15%"><%=propLang.get("sm_a_us") %></th>
									        <th width="14%"><%=propLang.get("sm_a_ip_") %></th>
									        <th width="2%"><%=propLang.get("sm_a_dt") %></th>
									        <th width="2%"><%=propLang.get("sm_a_ma") %></th>
									        <th width="9%"><%=propLang.get("sm_a_za") %></th>
									        <th width="7%"><%=propLang.get("sm_a_sc") %></th>
									        <th width="7%"><%=propLang.get("sm_a_ai") %></th>
									        <th width="7%"><%=propLang.get("sm_a_di") %></th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="14"><div id="al_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default" id="al2Panel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-warning"></i></span>
			                      <span class="hidden-sm">Analytics 2.0 Active Alarms</span>
			                      
			                      <a href="../../analytics2/main/active-alarms-list.htm" class="btn btn-default btn-xs m-l-sm pull-right" >
			                            <i class="fa fa-cogs"></i>
			                            <%=propLang.get("gestAl") %> 
			                        </a>
			                        
			                    </header>
			                    <div id="tab" class="table-responsive horizontal">
			                    	
			                    	<div class="padder" style="margin-top:10px">
			                    	<div class="btn-group">
					                  <button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><%=propLang.get("sil") %> <span class="caret"></span></button>
					                  <ul class="dropdown-menu">
					                    <li><a href="#" onclick="sil('3600', '1H')">Mark as read</a></li>
					                    <li><a href="#" onclick="sil('10800', '3H')">Silenced for 3 Hours</a></li>
					                    <li><a href="#" onclick="sil('21600', '6H')">Silenced for 6 Hours</a></li>
					                    <li><a href="#" onclick="sil('43200', '12H')">Silenced for 12 Hours</a></li>
					                    <li><a href="#" onclick="sil('86400', '1D')">Silenced for 1 Day</a></li>
					                    <li><a href="#" onclick="sil('604800', '1W')">Silenced for 1 Week</a></li>
					                    <li><a href="#" onclick="sil('2628000', '1M')">Silenced for 1 Month</a></li>
					                    <li><a href="#" onclick="sil('31536000', '1Y')">Silenced for 1 Year</a></li>
					                    <li><a href="#" id="button">Select Period</a></li>
					                  </ul>
					                </div>
					                </div>
					                
					                <div class="line line-dashed b-b line-lg pull-in"></div>
					                
					                <table class="table table-striped m-b-none" id="al2" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th></th>
									        <th><%=propLang.get("cli") %></th>
									        <th><%=propLang.get("aa_store") %></th>
									        <th><%=propLang.get("aa_code") %></th>
									        <th><%=propLang.get("aa_descr") %></th>
									        <th><%=propLang.get("aa_threshold") %></th>
									        <th><%=propLang.get("aa_timeunit") %></th>
									        <th><%=propLang.get("aa_control_time") %></th>
									        <th><%=propLang.get("aa_from_time") %></th>
									        <th><%=propLang.get("aa_to_time") %></th>
									        <th>Value</th>
									        <th>Note</th>
									        <th></th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="13"><div id="al2_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                
			                
			                
			                
			                
			                
			                <section class="panel panel-default" id="ulPanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-list"></i></span>
			                      <span class="hidden-sm">Updating List</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="ul" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="25%"><%=propLang.get("sm_cont_cl") %></th>
									        <th width="10%">Store Id</th>
									        <th width="30%"><%=propLang.get("sm_cont_ne") %></th>
									        <th width="25%"><%=propLang.get("sm_cont_ip") %></th>
									        <th width="10%">Account Id</th>
									        <th width="10%">Store Id</th>
									        <th width="10%">Door Id</th>
									        <th></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="7"><div id="ul_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default" id="dnePanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-ban"></i></span>
			                      <span class="hidden-sm">Device not Enabled</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="dne" data-ride="datatables">
					                  <thead>
										  <tr>
									        <th width="8%"><%=propLang.get("sm_cont_id") %></th>
							        		<th width="13%"><%=propLang.get("sm_cont_cl") %></th>
							        		<th width="8%">Store Id</th>
									        <th width="23%"><%=propLang.get("sm_cont_ne") %></th>
									        <th width="8%"><%=propLang.get("sm_cont_ip_") %></th>
									        <th width="14%"><%=propLang.get("sm_cont_dt") %></th>
									        <th width="16%"><%=propLang.get("sm_cont_dd_") %></th>
									        <th width="10%"><%=propLang.get("sm_cont_re") %></th>
							        		<th width="8%"></th>
									      </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="8"><div id="dne_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                <section class="panel panel-default" id="jsPanel">
			                    <header class="panel-heading bg-light">
			                      
			                      <span class="text-muted m-r-sm"><i class="fa fa-gears"></i></span>
			                      <span class="hidden-sm">Job Status</span>
			                      
			                    </header>
			                    <div class="table-responsive">
					                <table class="table table-striped m-b-none" id="js" data-ride="datatables">
					                  <thead>
										  <tr>
										    <th width="5%"></th>
									        <th width="25%">Process</th>
							        		<th width="10%">Type</th>
							        		<th width="10%">Last fail</th>
									        <th width="10%">last Successful</th>
									        <th width="10%">Next Run</th>
									        <th width="10%">Last Status</th>
									        <th width="10%">Current Status</th>
									        <th width="10%">Status Since</th>
							        	 </tr>
									  </thead>
			      					  
			      					  <tbody>
			      					  </tbody>
			      					  <tfoot>
			      					  	  <tr>
			      					  	  	<td colspan="9"><div id="js_total" style="margin-left:5px"></div></td>
			      					  	  </tr>
			      					  </tfoot>
			      					</table>
			      				</div>
			                </section>
			                
			                
			                <div class="row m-t-lg m-b-lg text-center">
								
								<div class="col-sm-4">
			                      <div class="bg-danger lter wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="icon-users i-lg"></i> <%=propLang.get("nr_customers") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_customers}</span>
			                        </a>
			                      </div>
			                    </div>
			                    
			                    <div class="col-sm-4">
			                      <div class="bg-black wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="fa fa-building-o i-lg"></i> <%=propLang.get("nr_stores") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_stores}</span>
			                        </a>
			                      </div>
			                    </div>
			                    <div class="col-sm-4">
			                      <div class="bg-light dker wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="icon-feed icon i-lg"></i> <%=propLang.get("nr_devices") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_devices}</span>
			                        </a>
			                      </div>
			                    </div>
			                    
			                </div>
			                
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
  
  <!--
  <script src="../../js/bootstrap-modal.js"></script>
  -->
  
  
  <!-- Echarts -->
  <script src="../../js/echarts/echarts.min.js?v=2"></script>
  
  <script>
  
  
  
    jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		
		getLogs();
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
	
	/*
	//MODAL
	$('.getModal').click(function(e) {
	
		alert('modal');
		e.preventDefault();
		var href = $(e.target).attr('href');
	
		if (href.indexOf('#') == 0) {
			$(href).modal('open');
		} else {
			$.get(href, function(data) {
				$('<div class="modal" >' + data + '</div>').modal();
			});
		}
	});
	*/
	
	
	
	
	
	function getLogs() {
		
		$('#logsPanel').show();
		$('#dnpPanel').hide();
		$('#pcnuPanel').hide();
		$('#annuPanel').hide();
		$('#alPanel').hide();
		$('#al2Panel').hide();
		$('#ulPanel').hide();
		$('#dnePanel').hide();
		$('#jsPanel').hide();
		
		
		$('#logsLi').addClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').removeClass('active');
		
		
		// based on prepared DOM, initialize echarts instance
	    var myChart = echarts.init(document.getElementById('x_main'));
		
		try {
			myChart.dispose();
		}
		catch(err) {}
		
		$('#x_main').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		$.ajax({
		      url: '../../servlet/ws/GetLogChart',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      success: function (data) {
				
		    	  var array = $.trim(data).split('##@@');
		    	  
		    	  var tot=""+array[0];
		    	  var cus=""+array[1];
		    	  var dt=""+array[2];
		    	  
		    	// specify chart configuration item and data
		  	    option = {
		    		color : ['#EE5154'],
		  		    title : {
		  		        text: 'Customers Logs',
		  		        subtext: 'Barchart'
		  		    },
		  		    tooltip : {
		  		        trigger: 'axis',
		  		        axisPointer : {            
		  		            type : 'shadow'      
		  		        }
		  		    },
		  		    toolbox: {
		  		        show : true,
		  		        showTitle: false,
		  		        feature : {
		  		        	
		  		        	//dataView : {show: true, readOnly: false, title: 'Data View'},
		  		            magicType : {show: true, type: ['line', 'bar']},
		  		            restore : {show: true},
		  		            saveAsImage : {show: true}
		  		        }
		  		    },
		  		    calculable : true,
		  		    xAxis : [
		  		        {
		  		        	type: 'category',
		  		            data: cus.split(',')
		  		        }
		  		    ],
		  		    yAxis : [
		  		        {
		  		            type : 'value'
		  		        }
		  		    ],
		  		    series: [{
		  		        data: dt.split(','),
		  		        type: 'bar',
		  		        markLine : {
		  	                data : [
		  	                    {type : 'max', name: 'Max'}
		  	                ]
		  	            },
		  	            itemStyle: {
		  	                emphasis: {
		  	        	        barBorderWidth: 1,
		  	        	        shadowBlur: 10,
		  	        	        shadowOffsetX: 0,
		  	        	        shadowOffsetY: 0,
		  	        	        shadowColor: 'rgba(0,0,0,0.5)'
		  	        	    }
		  	            },
		  		    }]
		  		    
		  		};
 
				myChart = echarts.init(document.getElementById('x_main'));
				myChart.setOption(option);
				
				$('#mainTot').html(tot);
					  
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
				
		var logs = $('#logs').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      "columns": [
		      { "width": "10%" },
		      { "width": "10%" },
		   	  { "width": "25%" },
		      { "width": "25%" },
		   	  { "width": "15%" },
		      { "width": "5%" },
		  	  { "width": "5%" },
		      { "width": "5%" }
		      
		      
			    ],
		      //"serverSide": true,
		      "ajax": {
	            "url": "../../servlet/ws/GetLog",
	            //"dataType": "jsonp"
	          },
	          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  $('td:eq(7)', nRow).html('<a href="../../app/anag/stores-dett.htm?tipoForm=M&id=' + aData[6] + '" class="btn btn-default">Edit</a>');
	              $('td:eq(6)', nRow).html('<span class="label bg-light">' + aData[5] + '</span>');
	              
	              
	              /*
	              var note = aData[6];
	              
	              
	              if( note.length>0 ) {
	              	$('td:eq(5)', nRow).html('<span">'+aData[6]+'</span>');
	              }
	              else {
	            	  $('td:eq(5)', nRow).html('<span">-</span>');
	              }
	              */
	              var note = aData[9];
	              var cls = "";
	              if( note.length>0 ) {
	            	  cls = "btn-success";
	              }
	              else {
	            	  cls = "btn-default";
	              }
	              $('td:eq(5)', nRow).html('<a href="store-notes.jsp?id='+aData[6]+'" id="_'+aData[6]+'_btn" class="btn '+cls+'" data-toggle="ajaxModal"> <i class="fa fa-info"></i> </a>');
	              
	              
	              return nRow;
	          },
	          "fnInitComplete": function (settings,json) {
	              $('#logs_total').html('<b>Total Logs: '+settings.fnRecordsTotal()+' records</b>');
	           }
	          
	
		  } );
		
	}
	
	
	function getAl() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').hide();
		$('#pcnuPanel').hide();
		$('#annuPanel').hide();
		$('#alPanel').show();
		$('#al2Panel').hide();
		$('#ulPanel').hide();
		$('#dnePanel').hide();
		$('#jsPanel').hide();
		
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').addClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').removeClass('active');
		
		var al = $('#al').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	            "url": "../../servlet/ws/GetActiveAlarms",
	            //"dataType": "jsonp"
	          },
	          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  $('td:eq(13)', nRow).html('<a id="' + aData[14] + '" href="#" onclick="setAsRead(\'' + aData[14] + '\')" class="btn btn-default"><i class="fa fa-check"></i></a>');
	              $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
	              return nRow;
	          },
	          "fnInitComplete": function (settings,json) {
	              $('#al_total').html('<b>Total Active Alarms: '+settings.fnRecordsTotal()+' records</b>');
	           }
		  } );
	}
	
	var al2;
	function getAl2() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').hide();
		$('#pcnuPanel').hide();
		$('#annuPanel').hide();
		$('#alPanel').hide();
		$('#al2Panel').show();
		$('#ulPanel').hide();
		$('#dnePanel').hide();
		$('#jsPanel').hide();
		
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').addClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').removeClass('active');
		
		al2 = $('#al2').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	            "url": "../../servlet/ws/GetActiveAlarms2",
	            //"dataType": "jsonp"
	          },
	          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  $('td:eq(3)', nRow).html('<span class="label bg-light">' + aData[3] + '</span>');
	        	  $('td:eq(10)', nRow).html('<b>' + aData[10] + '</b>');
	        	  
	        	  var note = aData[18];
	              var cls = "";
	              if( note.length>0 ) {
	            	  cls = "btn-success";
	              }
	              else {
	            	  cls = "btn-default";
	              }
	              
	        	  $('td:eq(11)', nRow).html('<a href="store-notes.jsp?id='+aData[14]+'" id="__'+aData[14]+'_btn" class="btn '+cls+'" data-toggle="ajaxModal""> <i class="fa fa-info"></i> </a>');
	              	 
	        	  $('td:eq(12)', nRow).html('<a href="../popup/alarm-dett.jsp?id=' + aData[13] + '" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-search"></i></a>');
	        	  
	        	  if(parseFloat(aData[10])>0) {
	        	  	$('td:eq(13)', nRow).html('<a href="../../analytics2/main/index-report-devices-modal.htm?idstores='+aData[14]+'&kpi='+aData[15]+'&dtda='+aData[16]+'&dtal='+aData[17]+'&tipoPeriod=DD&tipo2=DD&kpi_selected='+aData[15]+'" class="btn btn-default" data-toggle="ajaxModal" onclick="showLoader2()"><i class="icon-bar-chart"></i></a>');
	        	  }
	        	  else {
	        		$('td:eq(13)', nRow).html('-');
	        	  }
	              return nRow;
	          },
	          "fnInitComplete": function (settings,json) {
	              $('#al2_total').html('<b>Total Active Alarms: '+settings.fnRecordsTotal()+' records</b>');
	           }
		  } );
	}
	
	function getUl() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').hide();
		$('#pcnuPanel').hide();
		$('#annuPanel').hide();
		$('#alPanel').hide();
		$('#al2Panel').hide();
		$('#ulPanel').show();
		$('#dnePanel').hide();
		$('#jsPanel').hide();
		
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').addClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').removeClass('active');
		
		var ul = $('#ul').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	            "url": "../../servlet/ws/GetUpdating",
	            //"dataType": "jsonp"
	          },
	          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	              $('td:eq(7)', nRow).html('<a href="../../app/anag/stores-dett.htm?tipoForm=M&id=' + aData[1] + '" class="btn btn-default">Edit</a>');
	              $('td:eq(4)', nRow).html('<span class="label bg-light">' + aData[4] + '</span>');
	              $('td:eq(5)', nRow).html('<span class="label bg-light">' + aData[5] + '</span>');
	              $('td:eq(6)', nRow).html('<span class="label bg-light">' + aData[6] + '</span>');
	              return nRow;
	          },
	          "fnInitComplete": function (settings,json) {
	              $('#ul_total').html('<b>Total Updating List: '+settings.fnRecordsTotal()+' records</b>');
	           }
		  } );
	
	}
	
	function getDnp() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').show();
		$('#pcnuPanel').hide();
		$('#annuPanel').hide();
		$('#alPanel').hide();
		$('#al2Panel').hide();
		$('#ulPanel').hide();
		$('#dnePanel').hide();
		$('#jsPanel').hide();
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').addClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').removeClass('active');
		
		
		var dnp = $('#dnp').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	            "url": "../../servlet/ws/GetNotPaired",
	            //"dataType": "jsonp"
	          },
	          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
	        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
	              $('td:eq(3)', nRow).html('<span class="label bg-light">' + aData[3] + '</span>');
	              $('td:eq(11)', nRow).html('<a href="devices-notpaired.jsp?tipoForm=I&accountid='+aData[0]+'&storeid= '+aData[1]+'&doorid='+aData[3]+'" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-edit"></i></a>');
	              //$('td:eq(10)', nRow).html('<a href="devices-notpaired.jsp?tipoForm=E&accountid='+aData[5]+'&storeid= '+aData[2]+'&doorid='+aData[0]+'" class="btn btn-default" data-toggle="ajaxModal"><i class="fa fa-times"></i></a>');
	              
	              $('td:eq(12)', nRow).html('<a href="#" onclick="DeleteNotPaired(\'' + aData[0] + '\', \'' + aData[1] + '\', \'' + aData[3] + '\')" class="btn btn-default"><i class="fa fa-times"></i></a>');
	              
	              
	              return nRow;
	          },
	          "fnInitComplete": function (settings,json) {
	              $('#dnp_total').html('<b>Total Device Not Paired: '+settings.fnRecordsTotal()+' records</b>');
	              
	              /*
	            //MODAL
	          	$('.getModal').click(function(e) {
	          		e.preventDefault();
	          		var href = $(e.target).attr('href');
	          	
	          		if (href.indexOf('#') == 0) {
	          			$(href).modal('open');
	          		} else {
	          			$.get(href, function(data) {
	          				$('<div class="modal" >' + data + '</div>').modal();
	          			});
	          		}
	          	});
	              */
	          	
	           },
	           "fnCreatedRow": function( nRow, aData, iDataIndex ) {
	               $(nRow).attr('id', aData[5]+'_'+aData[2]+'_'+aData[0]);
	           }
	
		  } );
	}
	
	function getPcnu() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').hide();
		$('#pcnuPanel').show();
		$('#annuPanel').hide();
		$('#alPanel').hide();
		$('#al2Panel').hide();
		$('#ulPanel').hide();
		$('#dnePanel').hide();
		$('#jsPanel').hide();
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').addClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').removeClass('active');
		
		// based on prepared DOM, initialize echarts instance
	    var myChart = echarts.init(document.getElementById('x_main2'));
		
		try {
			myChart.dispose();
		}
		catch(err) {}
		
		$('#x_main2').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		$.ajax({
		      url: '../../servlet/ws/GetNotUpdateChart',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      success: function (data) {
				
		    	  var array = $.trim(data).split('##@@');
		    	  
		    	  var tot=""+array[0];
		    	  var cus=""+array[1];
		    	  var dt=""+array[2];
		    	  
		    	// specify chart configuration item and data
		  	    option = {
		    		color : ['#EE5154'],
		  		    title : {
		  		        text: 'Customers Counter Not Update',
		  		        subtext: 'Barchart'
		  		    },
		  		    tooltip : {
		  		        trigger: 'axis',
		  		        axisPointer : {            
		  		            type : 'shadow'      
		  		        }
		  		    },
		  		    toolbox: {
		  		        show : true,
		  		        showTitle: false,
		  		        feature : {
		  		        	
		  		        	//dataView : {show: true, readOnly: false, title: 'Data View'},
		  		            magicType : {show: true, type: ['line', 'bar']},
		  		            restore : {show: true},
		  		            saveAsImage : {show: true}
		  		        }
		  		    },
		  		    calculable : true,
		  		    xAxis : [
		  		        {
		  		        	type: 'category',
		  		            data: cus.split(',')
		  		        }
		  		    ],
		  		    yAxis : [
		  		        {
		  		            type : 'value'
		  		        }
		  		    ],
		  		    series: [{
		  		        data: dt.split(','),
		  		        type: 'bar',
		  		        markLine : {
		  	                data : [
		  	                    {type : 'max', name: 'Max'}
		  	                ]
		  	            },
		  	            itemStyle: {
		  	                emphasis: {
		  	        	        barBorderWidth: 1,
		  	        	        shadowBlur: 10,
		  	        	        shadowOffsetX: 0,
		  	        	        shadowOffsetY: 0,
		  	        	        shadowColor: 'rgba(0,0,0,0.5)'
		  	        	    }
		  	            },
		  		    }]
		  		    
		  		};
 
				myChart = echarts.init(document.getElementById('x_main2'));
				myChart.setOption(option);
				
				$('#main2Tot').html(tot);
					  
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
		
		var pcnu = $('#pcnu').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	            "url": "../../servlet/ws/GetNotUpdate",
	            //"dataType": "jsonp"
	          },
	          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
	        	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
	        	  $('td:eq(2)', nRow).html('<span class="label bg-light">' + aData[2] + '</span>');
	        	  return nRow;
	          },
	          "fnInitComplete": function (settings,json) {
	              $('#pcnu_total').html('<b>Total People Counter Not Update: '+settings.fnRecordsTotal()+' records</b>');
	           }
	      } );
	
	}
	
	function getAnnu() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').hide();
		$('#pcnuPanel').hide();
		$('#annuPanel').show();
		$('#alPanel').hide();
		$('#al2Panel').hide();
		$('#ulPanel').hide();
		$('#dnePanel').hide();
		$('#jsPanel').hide();
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').addClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').removeClass('active');
		
		var annu = $('#annu').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	          "url": "../../servlet/ws/GetNotUpdateAnalytics",
	          //"dataType": "jsonp"
	        },
	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	      	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
	      	  $('td:eq(1)', nRow).html('<span class="label bg-light">' + aData[1] + '</span>');
	      	  $('td:eq(2)', nRow).html('<span class="label bg-light">' + aData[2] + '</span>');
	      	  return nRow;
	        },
	        "fnInitComplete": function (settings,json) {
	            $('#annu_total').html('<b>Total Analytics Not Update: '+settings.fnRecordsTotal()+' records</b>');
	         }
	    } );
	
	}
	
	function getDne() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').hide();
		$('#pcnuPanel').hide();
		$('#annuPanel').hide();
		$('#alPanel').hide();
		$('#al2Panel').hide();
		$('#ulPanel').hide();
		$('#dnePanel').show();
		$('#jsPanel').show();
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').addClass('active');
		$('#jsLi').addClass('active');
		
		var dne = $('#dne').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	            "url": "../../servlet/ws/GetNotEnabled",
	            //"dataType": "jsonp"
	          },
	          "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	        	  $('td:eq(0)', nRow).html('<span class="label bg-light">' + aData[0] + '</span>');
	        	  $('td:eq(8)', nRow).html('<a href="../../app/anag/stores-dett.htm?tipoForm=M&id=' + aData[2] + '" class="btn btn-default">Edit</a>');
	        	  
	        	  return nRow;
	          },
	          "fnInitComplete": function (settings,json) {
	              $('#dne_total').html('<b>Total Device Not Enabled: '+settings.fnRecordsTotal()+' records</b>');
	           }
		  } );
		
	}
	
	function setAsRead(id) {
		
		//alert(id);
		
  		var msg = '';
  		$.ajax({
	        url: 'SetRead.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'id='+id,
	        success: function (data) {
	        	msg = data;
	        	$('#'+id).closest('tr').remove();
	        },
	        error: function () {
	        	alert('Errore! si e\' verificato un errore.');
	        }
	    });
  		
  		if(msg!='') {
  			alert(msg);
  		}
  		else {
  			// Nascondi riga
  		}
  	}
	
	function DeleteNotPaired(accountid, storeid, doorid) {
		
		//alert(id);
		
  		var msg = '';
  		$.ajax({
	        url: 'DeleteNotPaired.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'accountid='+accountid+'&storeid='+storeid+'&doorid='+doorid,
	        success: function (data) {
	        	msg = data;
	        	$('#'+accountid+'_'+storeid+'_'+doorid).remove();
	        },
	        error: function () {
	        	alert('Errore! si e\' verificato un errore.');
	        }
	    });
  		
  		if(msg!='') {
  			alert(msg);
  		}
  		else {
  			// Nascondi riga
  		}
  	}
	
	function insertDeviceSlave(accountid , storeid , doorid) {
		
		//alert(accountid+' '+storeid+' '+doorid);
		$.ajax({
	        url: 'insertDeviceSlave.jsp?accountid='+accountid+'&storeid='+storeid+'&doorid='+doorid,
	        type: 'POST',
	        dataType: 'html',
	        async: false,
	        beforeSend : function (){
	        
	        },
	        success: function (data) {
	        	$("#notpaireddmsg").html("<div class=\"alert alert-success\"><strong><%=propLang.get("msgSuccess")%></strong></div>");
	        },
	        error: function () {
	        	alert("<%=propLang.get("errore") %>");
	        }
	    });
		
		
	}
	
	
	
	
	
	$('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'bottom',
	    autoclose: true
	});  
	
	$('#button').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'top'
	}).on('changeDate', function(ev){
	    $('#button').datepicker('hide');
	    
	    var d = new Date();
	    var n = d.getTime();
	    var n2=ev.date.valueOf();
	    
	    
	    
	    var ms=(n2-n)/1000;
	    ms=parseInt(ms);
	    //alert(ms);
	    
	    sil(ms, 'P');
	});
	
	

	function sil(sec, tm) {
		
		
		var checked = [];
		
		$("input:checkbox", al2.fnGetNodes()).each(function () {
            var sel = $(this).is(":checked");
            if (sel) {
                //var no = $(this).parent().prev().prev().text();
                //alert("no:" + $(this).val () + " is checked");
                checked.push ($(this).val ());
            }
        })
        
		

		/*
		$('#tab [name="chk"]:checked').each (function (i, e)
		{
		    checked.push ($(this).val ());
		});
		*/
		if(checked.length==0) {
			alert('Select almost 1 row');
		}
		else {
			showLoader2();
			//alert (sec+' '+tm+'\n\n'+checked.join (','));
			
			var chk = checked.join (',');
			
			$.ajax({
			      url: '../../analytics2/ajax/saveSil.jsp',
			      type: 'POST',
			      dataType: 'html',
			      async: true,
			      data: 'sec='+sec+'&tm='+tm+'&checked='+chk,
			      success: function (data) {
					
			    	hideLoader2();
					//alert("<%=propLang.get("msgSuccess")%>");
					
					//location.href="active-alarms-list.htm";
					getAl2();
					
			      },
			      error: function () {
			      	alert('Errore! si e\' verificato un errore.');
			      }
			});
			
		}
		
	}
	
	
	
	function getJs() {
		
		$('#logsPanel').hide();
		$('#dnpPanel').hide();
		$('#pcnuPanel').hide();
		$('#annuPanel').hide();
		$('#alPanel').hide();
		$('#al2Panel').hide();
		$('#ulPanel').hide();
		$('#dnePanel').hide();
		$('#jsPanel').show();
		
		$('#logsLi').removeClass('active');
		$('#dnpLi').removeClass('active');
		$('#pcnuLi').removeClass('active');
		$('#annuLi').removeClass('active');
		$('#alLi').removeClass('active');
		$('#al2Li').removeClass('active');
		$('#ulLi').removeClass('active');
		$('#dneLi').removeClass('active');
		$('#jsLi').addClass('active');
		
		var js = $('#js').dataTable( {
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
		      "iDisplayLength": 10,
		      "processing": true,
		      "destroy": true,
		      //"serverSide": true,
		      "ajax": {
	          "url": "../../servlet/ws/GetJobStatus",
	          //"dataType": "jsonp"
	        },
	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	      	  $('td:eq(2)', nRow).html('<span class="label bg-light">' + aData[2] + '</span>');
	      	  $('td:eq(7)', nRow).html('<b>' + aData[7] + '</b>');
	      	  return nRow;
	        },
	        "fnInitComplete": function (settings,json) {
	            $('#js_total').html('<b>Total Jobs: '+settings.fnRecordsTotal()+' records</b>');
	         }
	    } );
	
	}
	
	function saveNote(idstore) {
		
		var notes = $('#notes').val();
				
		$('.modal').remove(); $('div.modal-backdrop').remove();
		
		//alert(notes);
		
		showLoader2();
		
		$.ajax({
		      url: 'SetNotes.jsp',
		      type: 'POST',
		      data: 'idstore='+idstore+'&notes='+notes,
		      dataType: 'html',
		      async: true,
		      success: function (data) {
				
		    	  hideLoader2();	
		    	  
		    	  if(notes.length>0) {
			    	  $("#_"+idstore+"_btn").removeClass("btn-default");
			    	  $("#_"+idstore+"_btn").addClass("btn-success");
		    	  }
		    	  else {
		    		  $("#_"+idstore+"_btn").removeClass("btn-success");
			    	  $("#_"+idstore+"_btn").addClass("btn-default");
		    	  }
		      },
		      error: function () {
		      	alert('Errore! si e\' verificato un errore.');
		      }
		});
		
		
	}
	
  </script> 
	
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
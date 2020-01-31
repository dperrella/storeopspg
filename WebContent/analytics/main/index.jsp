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
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp.jsp" %>
              
              
              
              
              
              
              <!-- side content -->
                <aside class="aside bg-light dk" id="sidebar">
                  <section class="vbox animated fadeInUp">
                    <section class="scrollable hover" style="margin-bottom:40px">
                      <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto">
                        
                        <span class="list-group-item">
		                  <b><%=propLang.get("sel_report") %></b>
		                </span>
		                
		                <ul id="search_list" class="list-group no-bg auto m-t-n-xxs">
		                <c:forEach items="${rep}" var="rep" varStatus="status">
		                <li class="list-group-item" style="min-height:60px">
		                	<div class="radio i-checks">
		                      <label class="i-checks">
		                        <input type="radio" onclick="reloadForm(this.value, '${rep.code}', '${rep.description}', '${rep.icon}', '${rep.id_report_type}', '${rep.id_subreport_type}', '${rep.alarm1}', '${rep.alarm2}', '${rep.cmp1}', '${rep.cmp2}', '${rep.historic}', '${rep.benchmark}')" 
		                        	id="${rep.id}" name="id_report" value="${rep.id}" ${rep.cmp1}><i></i> ${rep.description}
		                      </label>
		                    </div>
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
	                        <input type="text" id="search_input" class="form-control input-sm bg-empty b-b b-dark no-border" placeholder="<%=propLang.get("rep_cerca") %>">
	                      </div>
	                    </div>
                    </footer>
                  </section>
                </aside>
                <!-- / side content -->
                <section>
                  <section class="vbox">
                    <section class="scrollable padder-lg">
                      <h2 class="font-thin m-b">Desk Smart Analytics <%=propLang.get("versione") %> 2.0.0</h2>
                      
                      <div class="row row-sm">
	              	
		              	<div class="col-xs-12">
	
							<div class="row m-b-lg text-center">
							<!-- 
			                    <div class="col-sm-4">
			                      <div class="bg-primary wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="icon-users i-lg"></i> <%=propLang.get("nr_customers") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_customers}</span>
			                        </a>
			                      </div>
			                    </div>
			                -->
			                    <div class="col-sm-6">
			                      <div class="bg-black wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="fa fa-building-o i-lg"></i> <%=propLang.get("nr_stores") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_stores}</span>
			                        </a>
			                      </div>
			                    </div>
			                    <div class="col-sm-6">
			                      <div class="bg-light dker wrapper-md r">
			                        <a href="#">
			                          <span class="h4 block"><i class="icon-feed icon i-lg"></i> <%=propLang.get("nr_devices") %></span>
			                          <span class="text-muted font-thin" style="text-align:center;font-size:100px">${nr_devices}</span>
			                        </a>
			                      </div>
			                    </div>
			                  </div>
			                  
			                  <div id="msg">${msg}</div>
			                  
			                  <c:if test="${id_report != ''}">
			                  
			                  <form id="formPage" name="formPage" action="../../app/report/report${id_report_type}-${id_subreport_type}.htm" 
			                  	method="post" onsubmit="return control()">
			                  <input type="hidden" name="id_report" value="${id_report}"/>
			                  <input type="hidden" name="code" value="${code}"/>
			                  <input type="hidden" name="description" value="${description}"/>
			                  <input type="hidden" name="alarm1" value="${alarm1}"/>
			                  <input type="hidden" name="alarm2" value="${alarm2}"/>
			                  <input type="hidden" name="alarm1_descr" value="${alarm1_descr}"/>
			                  <input type="hidden" name="alarm2_descr" value="${alarm2_descr}"/>
			                  <input type="hidden" name="historic" value="${historic}"/>
			                  <input type="hidden" name="benchmark" value="${benchmark}"/>
			                  <input type="hidden" name="id_report_type" value="${id_report_type}"/>
			                  <input type="hidden" name="id_subreport_type" value="${id_subreport_type}"/>
			                  
			                  <section class="panel panel-default">
			                    <header class="panel-heading bg-light">
			                      <ul class="nav nav-tabs pull-right">
			                        <li class="active"><a href="#dayTab" data-toggle="tab" onclick="$('#tipo').val('D')"><%=propLang.get("tipo_day") %></a></li>
			                        <li><a href="#weekTab" data-toggle="tab" onclick="$('#tipo').val('W')"><%=propLang.get("tipo_week") %></a></li>
			                        <li><a href="#monthTab" data-toggle="tab" onclick="$('#tipo').val('M')"><%=propLang.get("tipo_month") %></a></li>
			                        
			                        <!-- 
			                        <li><a href="#periodTab" data-toggle="tab" onclick="$('#tipo').val('P')"><%=propLang.get("tipo_period") %></a></li>
			                        -->
			                      </ul>
			                      
			                      <span class="text-muted m-r-sm"><i class="${icon}"></i></span>
			                      <span class="hidden-sm"><b><!-- ${code} - -->${description}</b></span>
			                      
			                    </header>
			                    <div class="panel-body">
			                    
			                    
			                      <c:choose>
								    <c:when test="${alarm2==''}">
								       
								       <div class="row m-b-lg">
					                    <div class="col-sm-12">
					                      <div class="bg-black lter wrapper-md r">
					                        <a href="#">
					                          <span class="h4 m-b-xs block"><i class="fa fa-warning i-lg"></i> ${alarm1} - ${alarm1_descr}</span>
					                        </a>
					                      </div>
					                    </div>
					                  </div>
								       
								    </c:when>
								    <c:otherwise>
								    
								       <div class="row m-b-lg">
					                    <div class="col-sm-6">
					                      <div class="bg-black lter wrapper-md r">
					                        <a href="#">
					                          <span class="h4 m-b-xs block"><i class="fa fa-warning i-lg"></i> ${alarm1} - ${alarm1_descr}</span>
					                        </a>
					                      </div>
					                    </div>
					                    <div class="col-sm-6">
					                      <div class="bg-danger lter wrapper-md r">
					                        <a href="#">
					                          <span class="h4 m-b-xs block"><i class="fa fa-warning i-lg"></i> ${alarm2} - ${alarm2_descr}</span>
					                        </a>
					                      </div>
					                    </div>
					                  </div>
					                  
								    </c:otherwise>
								  </c:choose>
								  
								  <div class="form-group pull-in clearfix">
							        <div class="col-sm-12">
				                      <label><%=propLang.get("sel_customer") %></label>
							          <select id="id_customer" name="id_customer"
							          	class="form-control chosen-select" onchange="reloadForm2()">
								          <option value="">-</option>
				                          <c:forEach items="${cus}" var="cus" varStatus="status">
				                          <option value="${cus.id}">${cus.id} - ${cus.description}</option>
				                          </c:forEach>
		                        	  </select>
		                        	</div>
		                          </div>   
								  
								  <div class="line line-dashed b-b line-lg pull-in"></div>
			                      
			                      <div class="form-group pull-in clearfix">
							        <div class="col-sm-11">
				                      <label><%=propLang.get("sel_stores1") %></label>
							          <select multiple id="id_store" name="id_store" data-required="true" 
							          	class="form-control parsley-validated chosen-select">
								          <c:forEach items="${sto}" var="sto" varStatus="status">
				                          <option value="${sto.id}">${sto.cmp1} - ${sto.description}</option>
				                          </c:forEach>
		                        	  </select>
		                        	</div>
		                        	<div class="col-sm-1">
		                        	<label>&nbsp;</label>
		                        	<a href="#" onclick="selAll()"
		                        		class="btn btn-default btn-block btn-sm"><%=propLang.get("sel_all") %></a></div>
		                          </div>   
	                        	  
	                        	  <c:if test="${benchmark=='t'}">
	                        	  	
	                        	  	<div class="line line-dashed b-b line-lg pull-in"></div>
									    	
						        	<div class="panel-footer bg-light">
				                      <div class="padder">
			                            <span class="h4 block">
			                            	<%=propLang.get("confrontoStore") %>
			                            </span>
			                          </div>
				                    </div>
				                    <br/>
	                        	  	
	                        	  	<div class="form-group pull-in clearfix">
							          <div class="col-sm-11">
		                        	  	<label><%=propLang.get("sel_stores2") %></label>
							            <select multiple id="id_storeConfronto" name="id_storeConfronto" data-required="true" 
							          	  class="form-control parsley-validated chosen-select">
									          <c:forEach items="${sto}" var="sto" varStatus="status">
					                          <option value="${sto.id}">${sto.cmp1} - ${sto.description}</option>
					                          </c:forEach>
		                        	    </select>  
		                        	  </div>
		                        	  <div class="col-sm-1">
			                        	<label>&nbsp;</label>
			                        	<a href="#" onclick="copyAll()"
			                        		class="btn btn-default btn-block btn-sm"><%=propLang.get("copy_all") %></a></div>
		                        	</div>
		                        	  
	                        	  </c:if>
						          
			                      <div class="tab-content">              
			                        <div class="tab-pane active" id="dayTab">
			                        	<div class="form-group pull-in clearfix">
			                        		<div class="col-sm-12">
									          <label><%=propLang.get("sel_day") %></label>
									          <input class="datepicker-input2 form-control" name="day" id="day" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									    </div>
									    
									    <c:if test="${historic=='t'}">
									    	<div class="line line-dashed b-b line-lg pull-in"></div>
									    	
								        	<div class="panel-footer bg-light">
						                      <div class="padder">
					                            <span class="h4 block">
					                            	<%=propLang.get("confrontoDate") %>
					                            </span>
					                          </div>
						                    </div>
						                    <br/>
						                    <div class="form-group pull-in clearfix">
				                        		<div class="col-sm-12">
										          <label><%=propLang.get("sel_day") %></label>
										          <input class="datepicker-input2 form-control" name="dayConfronto" id="dayConfronto" 
										          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
										        </div>
										    </div>
									    
								        </c:if>
								        
								        
								        
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
										          <option value="2019">2019</option>
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
								    	
								    	<c:if test="${historic=='t'}">
									    	<div class="line line-dashed b-b line-lg pull-in"></div>
									    	
								        	<div class="panel-footer bg-light">
						                      <div class="padder">
					                            <span class="h4 block">
					                            	<%=propLang.get("confrontoDate") %>
					                            </span>
					                          </div>
						                    </div>
						                    <br/>
						                    
						                    <div class="form-group pull-in clearfix">
								        		<div class="col-sm-2">
										          <label><%=propLang.get("sel_year") %></label>
										          <select id="yearWeekConfronto" name="yearWeekConfronto" data-required="true" class="form-control parsley-validated" onchange="cambiaSettimana()">
											          <option value="2015">2015</option>
											          <option value="2016">2016</option>
											          <option value="2017">2017</option>
											          <option value="2018">2018</option>
											          <option value="2019">2019</option>
							                      </select>   
										        </div>
										        
										        <div class="col-sm-10">
										          <label><%=propLang.get("sel_week") %></label>
										          <select id="weekConfronto" name="weekConfronto" data-required="true" class="form-control parsley-validated">
											          <option value="">-</option>
											          <c:forEach items="${weeks}" var="weeks" varStatus="status">
							                          <option value="${weeks.xcdel}" ${weeks.cmp1}>${weeks.xcdel}: ${weeks.xdtab}</option>
							                          </c:forEach>
					                        	  </select>   
										        </div>
									    	</div>
									    
								        </c:if>
								    	
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
										          <option value="2019">2019</option>
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
								    	
								    	<c:if test="${historic=='t'}">
									    	<div class="line line-dashed b-b line-lg pull-in"></div>
									    	
								        	<div class="panel-footer bg-light">
						                      <div class="padder">
					                            <span class="h4 block">
					                            	<%=propLang.get("confrontoDate") %>
					                            </span>
					                          </div>
						                    </div>
						                    <br/>
						                    
						                    <div class="form-group pull-in clearfix">
								        		<div class="col-sm-2">
										          <label><%=propLang.get("sel_year") %></label>
										          <select id="yearMonthConfronto" name="yearMonthConfronto" data-required="true" class="form-control parsley-validated">
											          <option value="2015">2015</option>
											          <option value="2016">2016</option>
											          <option value="2017">2017</option>
											          <option value="2018">2018</option>
											          <option value="2019">2019</option>
							                      </select>   
										        </div>
										        
										        <div class="col-sm-10">
										          <label><%=propLang.get("sel_month") %></label>
										          <select id="monthConfronto" name="monthConfronto" data-required="true" class="form-control parsley-validated">
											          <option value="">-</option>
											          <c:forEach items="${mn}" var="mn" varStatus="status">
							                          <option value="${mn.xcdel}" ${mn.cmp1}>${mn.xcdel}: ${mn.xdtab}</option>
							                          </c:forEach>
					                        	  </select>   
										        </div>
									    	</div>
						                    
						                </c:if>
			                        	
			                        </div>
			                        
			                        <!-- 
			                        <div class="line line-dashed b-b line-lg pull-in"></div>
			                      
				                      
				                    <div class="form-group pull-in clearfix">
								        <div class="col-sm-12">
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
				                        
			                        <!-- 
			                        <div class="tab-pane" id="periodTab">
			                        	<div class="form-group pull-in clearfix">
							        		<div class="col-sm-6">
									          <label><%=propLang.get("sel_dtda") %></label>
									          <input class="datepicker-input2 form-control" name="dtda" id="dtda" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
									        </div>
									        
									        <div class="col-sm-6">
									          <label><%=propLang.get("sel_dtal") %></label>
									          <input class="datepicker-input2 form-control" name="dtal" id="dtal" 
									          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
									        </div>
								    	</div>
								    	
								    	<c:if test="${historic=='t'}">
								    	
								    	<div class="line line-dashed b-b line-lg pull-in"></div>
									    	
								        	<div class="panel-footer bg-light">
						                      <div class="padder">
					                            <span class="h4 block">
					                            	<%=propLang.get("confrontoDate") %>
					                            </span>
					                          </div>
						                    </div>
						                    <br/>  
						                    
						                    <div class="form-group pull-in clearfix">
								        		<div class="col-sm-6">
										          <label><%=propLang.get("sel_dtda") %></label>
										          <input class="datepicker-input2 form-control" name="dtdaConfronto" id="dtdaConfronto" 
										          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
										        </div>
										        
										        <div class="col-sm-6">
										          <label><%=propLang.get("sel_dtal") %></label>
										          <input class="datepicker-input2 form-control" name="dtalConfronto" id="dtalConfronto" 
										          	type="text" value="${oggi}" data-date-format="dd-mm-yyyy" >  
										        </div>
									    	</div>
								    		
								    	</c:if>
			                        </div>
			                        -->
			                      </div>
			                    </div>
			                    <footer class="panel-footer text-right bg-light lter">
			                    	<input type="hidden" name="tipo" id="tipo" value="D">
			                    	
							      	<input type="submit" class="btn btn-success btn-lg" value="<%=propLang.get("btn_esegui_report") %>"/>
							    </footer>
			                  </section>
			                  
			                  </form>
			                  </c:if>
						
							</div>
						
					  	</div>
					  	
					  	<c:if test="${id_report == ''}">
					  		
					  		<c:choose>
							    <c:when test="${num=='0'}">
							    	<div class="row m-n">
								      <div class="col-sm-12">
								      	
								        <div class="text-center m-b-lg">
								          <h1 class="fontBig text-white animated"><%=propLang.get("sel_report") %></h1>
								        </div>
								        
								      </div>
								    </div>  
							    </c:when>    
							    <c:otherwise>
							        
							        <section class="panel panel-default">
						              <header class="panel-heading font-bold">
						                <%=propLang.get("aa") %>
						                
						                <a href="../../app/report/inactive-alarms-list.htm" class="btn btn-default btn-xs m-l-sm pull-right" >
				                            <i class="fa fa-power-off"></i>
				                            <%=propLang.get("ia") %> 
				                        </a>
						              </header>
						              
						              
						              <div class="table-responsive">
						                <table class="table table-striped m-b-none" id="example" data-ride="datatables">
						                  <thead>
											  <tr>
										        <th>Id</th>
										        <th><%=propLang.get("aa_devicename") %></th>
										        <th><%=propLang.get("aa_store") %></th>
										        <th><%=propLang.get("aa_code") %></th>
										        <th><%=propLang.get("aa_descr") %></th>
										        <th><%=propLang.get("aa_threshold") %></th>
										        <th><%=propLang.get("aa_timeunit") %></th>
										        <th><%=propLang.get("aa_control_time") %></th>
										        <th><%=propLang.get("aa_from_time") %></th>
										        <th><%=propLang.get("aa_to_time") %></th>
										        <th>Value</th>
										        <th></th>
										      </tr>
										  </thead>
				      					  
				      					  <tbody>
										  <c:forEach items="${alarms}" var="alarms" varStatus="status">
											<tr id="${alarms.cmp1}">
											  <td>${alarms.cmp1}</td>
											  <td>${alarms.cmp2}</td>
											  <td>${alarms.cmp3}</td>
											  <td><span class="label bg-light">${alarms.cmp4}</span></td>
											  <td>${alarms.cmp5}</td>
											  <td>${alarms.cmp6}</td>
											  <td>${alarms.cmp7}</td>
											  <td>${alarms.cmp8}</td>
											  <td>${alarms.cmp9}</td>
											  <td>${alarms.cmp10}</td>
											  <td>${alarms.xdtab}</td>
											  <td><a href="#" onclick="setAsRead('${alarms.cmp1}')" class="btn btn-default">
											  <i class="fa fa-check"></i></a></td>
											</tr>
										  </c:forEach>
					  					  </tbody>
					  					</table>
					  					
					  				  </div>
					  				
					  				</section>    
							        
							        
							        
							    </c:otherwise>
							</c:choose>
			            
			            </c:if>
                      
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
  
  $('#id_customer').val('${id_customer}');
  
  $('.datepicker-input2').datepicker({
	    format: 'dd-mm-yyyy',
	    orientation: 'top',
	    autoclose: true
	});  
  
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
      "iDisplayLength": 10
    } );
  
  
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
  	
  	var $radios2 = $('input:radio[name=orario]');
    if($radios2.is(':checked') === false) {
        $radios2.filter('[value=${orario}]').prop('checked', true);
    }
  	
  	$(function() {
	    $('#search_input').fastLiveFilter('#search_list');
	});
  	
  	var $radios = $('input:radio[name=id_report]');
    if($radios.is(':checked') === false) {
        $radios.filter('[value=${id_report}]').prop('checked', true);
    }
    
  	function reloadForm(id_report, code, description, icon, id_report_type, id_subreport_type, alarm1, alarm2, alarm1_descr, alarm2_descr, historic, benchmark) {
  		
  		showLoader();
  		
  		var orario = $("#formPage input[name='orario']:checked").val();
  		
  		location.href='index.htm?id_report='+id_report+'&code='+code+'&description='+description+'&icon='+icon
  				+'&id_report_type='+id_report_type+'&id_subreport_type='+id_subreport_type
  				+'&alarm1='+alarm1+'&alarm2='+alarm2+'&alarm1_descr='+alarm1_descr+'&alarm2_descr='+alarm2_descr
  				+'&historic='+historic+'&benchmark='+benchmark+'&orario='+orario;
  		
  	}
  	
  	function control() {
  		
  		// Controlla che almeno uno store sia stato selezionato
  		var stores = "";
  		$('#id_store :selected').each(function(i, sel){ 
		    stores=stores+$(sel).val(); 
		});
  		
  		
  		
  		if(stores=='') {
  			$('#msg').html("<div class=\"alert alert-warning\"><strong><%=propLang.get("sel_stores")%></strong></div>");
  			
  			return false;
  		}
  		else {
  			showLoader();	
  		}
 		
  		
  	}
  	
  	function selAll() {
  		$('#id_store option').prop('selected', true);
  		$('#id_store').trigger("chosen:updated");
  	}
  	
  	function copyAll() {
  		$('#id_store :selected').each(function(i, sel){ 
  			$('#id_storeConfronto option[value='+$(sel).val()+']').attr('selected', true);
  		});
  		$('#id_storeConfronto').trigger("chosen:updated");
  	}
  	
  	function reloadForm2() {
  		
  		$('#formPage').removeAttr('onsubmit')
  		
  		$('#formPage').attr('action', 'index.htm');
  		
  		$('#formPage').submit();
  		
  	}
  	
  	function setAsRead(id) {
  		var msg = '';
  		$.ajax({
	        url: 'SetRead.jsp',
	        type: 'POST',
	        dataType: 'html',
	        async: true,
	        data: 'id='+id,
	        success: function (data) {
	        	msg = data;
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
  </script> 
  
  <%@ include file="../../inc/autorized.jsp" %>
</body>
</html>
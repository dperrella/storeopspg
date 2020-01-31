<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
	import="pkg.utility.Utility"
    pageEncoding="ISO-8859-1"
%>


				<aside class="bg-dark nav-xs aside hidden-print" id="sidebar">           
		          <section class="vbox">
		            <section class="w-f-md scrollable">
		              <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">
		
		                <!-- nav -->                 
		                
		                
		
						<nav class="nav-primary">
	            
	            		<ul class="nav bg clearfix">
	            		
	            		<li class="b-b" style="border-color:#4D5D6D">
						<a href="#sidebar" onclick="timerResize()" data-toggle="class:nav-xs,nav-xs" class="text-muted hidden-xs">
				            <i class="fa fa-indent text"></i>
				            <i class="fa fa-dedent text-active"></i>
				            <span class="hidden-nav-xs h4 hont-thin">Menu</span>
				        </a>
				        </li>
				        <li class="b-b" style="border-color:#4D5D6D">
                  		<a href="../../hb/main/index.htm" onclick="showLoader2()">
                          <i class="fa fa-dashboard"></i> 
                          <span class="hidden-nav-xs">Dashboard</span>
                        </a>
                        </li>
                        <li class="b-b" style="border-color:#4D5D6D">
                        <a href="../../hb/popup/sel_customer.jsp" data-toggle="ajaxModal" id="mnu_sel_cus">
                          <i class="icon-users"></i> 
                          <span class="hidden-nav-xs">Change Customer</span>
                        </a>
                        </li>
<%
String[] mnuHEB01=null;

String mnuArrayHEB01 = ""+session.getAttribute("mnuArrayHEB01");
mnuArrayHEB01=Utility.noNull(mnuArrayHEB01);
System.out.println("-----"+mnuArrayHEB01);
if(mnuArrayHEB01.trim().length()>0) {
	mnuHEB01 = mnuArrayHEB01.split(";");

	m = 0;
	for(m=0; m < mnuHEB01.length ; m++)
	{
 %>
 						<li class="b-b" style="border-color:#4D5D6D">
 						<a href="<%=mnuHEB01[m].substring(55,105) %>" onclick="showLoader2()">
                          <i class="<%=mnuHEB01[m].substring(105,mnuHEB01[m].length()) %>"></i> 
                          <span class="hidden-nav-xs"><%=mnuHEB01[m].substring(3,53) %></span>
                        </a>
                        </li>
<%
	}
}
%>
						</ul>
						</nav>

 				  	  </div>
	                </section>
	                
	              </section>              
	            </aside>
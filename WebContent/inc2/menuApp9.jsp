<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
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
	            		<a href="#sidebar" data-toggle="class:nav-xs,nav-xs" class="text-muted hidden-xs">
				            <i class="fa fa-indent text"></i>
				            <i class="fa fa-dedent text-active"></i>
				            <span class="hidden-nav-xs h4 hont-thin">Menu</span>
				        </a>
				        </li>
<%
String aaapp = session.getAttribute("aaapp").toString();
if(aaapp.equals("CT2")) {
%>
				        
				        <li class="b-b" style="border-color:#4D5D6D">
                  		<a href="../../counter2/main/index.htm">
                          <i class="fa fa-dashboard"></i> 
                          <span class="hidden-nav-xs">Dashboard</span>
                        </a>
                        </li>
                        <li class="b-b" style="border-color:#4D5D6D">
                        <a href="../popup/sel_customer.jsp" data-toggle="ajaxModal" id="mnu_sel_cus">
                          <i class="icon-users"></i> 
                          <span class="hidden-nav-xs">Change Customer</span>
                        </a>
                        </li>
<%
}
else if(aaapp.equals("CNP")) {
%>
						<li class="b-b" style="border-color:#4D5D6D">
                  		<a href="../../counterPlus/main/index.htm">
                          <i class="fa fa-dashboard"></i> 
                          <span class="hidden-nav-xs">Dashboard</span>
                        </a>
                        </li>
                        <li class="b-b" style="border-color:#4D5D6D">
                        <a href="../../counterPlus/popup/sel_customer.jsp" data-toggle="ajaxModal" id="mnu_sel_cus">
                          <i class="icon-users"></i> 
                          <span class="hidden-nav-xs">Change Customer</span>
                        </a>
                        </li>
<%
}
%>
<%
String[] mnuCT201=null;

String mnuArrayCT201 = (String) session.getAttribute("mnuArrayCT201");
System.out.println("-----"+mnuArrayCT201);
if(mnuArrayCT201.trim().length()>0) {
	mnuCT201 = mnuArrayCT201.split(";");

	m = 0;
	for(m=0; m < mnuCT201.length ; m++)
	{
 %>
 						<li class="b-b" style="border-color:#4D5D6D">
 						<a href="<%=mnuCT201[m].substring(55,105) %>">
                          <i class="<%=mnuCT201[m].substring(105,mnuCT201[m].length()) %>"></i> 
                          <span class="hidden-nav-xs"><%=mnuCT201[m].substring(3,53) %></span>
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
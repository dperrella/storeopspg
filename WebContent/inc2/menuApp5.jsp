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
				        <li class="b-b" style="border-color:#4D5D6D">
                  		<a href="../../disp/main/index.htm">
                          <i class="fa fa-dashboard"></i> 
                          <span class="hidden-nav-xs">Dashboard</span>
                        </a>
                        </li>
<%
String[] mnuDIS01=null;

String mnuArrayDIS01 = (String) session.getAttribute("mnuArrayDIS01");
System.out.println("-----"+mnuArrayDIS01);
if(mnuArrayDIS01.trim().length()>0) {
	mnuDIS01 = mnuArrayDIS01.split(";");

	m = 0;
	for(m=0; m < mnuDIS01.length ; m++)
	{
 %>
 						<li class="b-b" style="border-color:#4D5D6D">
 						<a href="<%=mnuDIS01[m].substring(55,105) %>">
                          <i class="<%=mnuDIS01[m].substring(105,mnuDIS01[m].length()) %>"></i> 
                          <span><%=mnuDIS01[m].substring(3,53) %></span>
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
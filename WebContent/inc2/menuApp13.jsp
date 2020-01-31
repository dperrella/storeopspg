<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
%>

				<aside class="bg-dark nav-xs aside hidden-print" id="sidebar">           
		          <section class="vbox">
		            <section class="scrollable">
		              <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">
		
	                  <nav class="nav-primary">
			           
	                  	<ul class="nav bg clearfix">
				       	  <li class="b-b" style="border-color:#4D5D6D">
				            <a href="#sidebar" data-toggle="class:nav-xs,nav-xs"  class="text-muted hidden-xs">
					            <i class="fa fa-indent text"></i>
					            <i class="fa fa-dedent text-active"></i>
					            <span class="hidden-nav-xs h4 hont-thin">Menu</span>
					        </a>
				          </li>
				      	  <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../../source/main/index.htm" onclick="showLoader2()" >
	                          <i class="fa fa-dashboard"></i> 
	                          <span class="hidden-nav-xs">Dashboard</span>
	                        </a>
				          </li>
				          
				      
<%
String[] mnuSOU01=null;

String mnuArraySOU01 = (String) session.getAttribute("mnuArraySOU01");
System.out.println("-----"+mnuArraySOU01);
if(mnuArraySOU01.trim().length()>0) {
	mnuSOU01 = mnuArraySOU01.split(";");

	m = 0;
	
	for(m=0; m < mnuSOU01.length ; m++)
	{
 %>
 							<li class="b-b" style="border-color:#4D5D6D">
		                      <a href="<%=mnuSOU01[m].substring(55,105) %>" onclick="showLoader2()"  class="auto">
		                        <i class="<%=mnuSOU01[m].substring(105,mnuSOU01[m].length()) %>"></i>
                        	    <span><%=mnuSOU01[m].substring(3,53) %></span>
		                      </a>
		                    </li>
<%
	}
%>
						
<%
}
%>

						</ul>
 				  	  </nav>
 				  	  </div>
	                </section>
	                
	              </section>              
	            </aside>
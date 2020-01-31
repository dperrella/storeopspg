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
				            <a href="#sidebar" data-toggle="class:nav-xs,nav-xs"  class="text-muted hidden-xs">
					            <i class="fa fa-indent text"></i>
					            <i class="fa fa-dedent text-active"></i>
					            <span class="hidden-nav-xs h4 hont-thin">Menu</span>
					        </a>
				          </li>
				          <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../../analytics2/main/index.htm" onclick="showLoader2()" >
	                          <i class="fa fa-dashboard"></i> 
	                          <span class="hidden-nav-xs">Dashboard</span>
	                        </a>
				          </li>
				          <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../../analytics2/popup/sel_customer.jsp" data-toggle="ajaxModal" id="mnu_sel_cus">
                        	  <i class="icon-users"></i> 
	                          <span class="hidden-nav-xs">Change Customer</span>
	                        </a>
				          </li>
<%
String[] mnuSDA01=null;

String mnuArraySDA01 = ""+session.getAttribute("mnuArraySDA01");
mnuArraySDA01=Utility.noNull(mnuArraySDA01);
System.out.println("-----"+mnuArraySDA01);
if(mnuArraySDA01.trim().length()>0) {
	mnuSDA01 = mnuArraySDA01.split(";");

	m = 0;
	for(m=0; m < mnuSDA01.length ; m++)
	{
 %>
 						<li class="b-b" style="border-color:#4D5D6D">
 						<a href="<%=mnuSDA01[m].substring(55,105) %>" onclick="showLoader2()">
                          <i class="<%=mnuSDA01[m].substring(105,mnuSDA01[m].length()) %>"></i> 
                          <span class="hidden-nav-xs"><%=mnuSDA01[m].substring(3,53) %></span>
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
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
				          <%if(Integer.parseInt(session.getAttribute("nrcus").toString())>1) { %>
				          <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../popup/sel_customer.jsp" data-toggle="ajaxModal"  id="mnu_sel_cus">
	                          <i class="icon-users"></i> 
	                          <span class="hidden-nav-xs">Change Customer</span>
	                        </a>
				          </li>
				          <%} %>
				      	  <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../main/index.htm" onclick="showLoader2()" >
	                          <i class="fa fa-dashboard"></i> 
	                          <span class="hidden-nav-xs">Dashboard</span>
	                        </a>
				          </li>
				          
				      
<%
String[] mnuCNP01=null;

String mnuArrayCNP01 = (String) session.getAttribute("mnuArrayCNP01");
System.out.println("-----"+mnuArrayCNP01);
if(mnuArrayCNP01.trim().length()>0) {
	mnuCNP01 = mnuArrayCNP01.split(";");

	m = 0;
	
	for(m=0; m < mnuCNP01.length ; m++)
	{
 %>
 							<li class="b-b" style="border-color:#4D5D6D">
		                      <a href="<%=mnuCNP01[m].substring(55,105) %>" onclick="showLoader2()"  class="auto">
		                        <i class="<%=mnuCNP01[m].substring(105,mnuCNP01[m].length()) %>"></i>
                        	    <span><%=mnuCNP01[m].substring(3,53) %></span>
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
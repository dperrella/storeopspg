<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
	import="pkg.utility.Utility"
    pageEncoding="ISO-8859-1"
%>

				<aside class="bg-dark nav-xs aside hidden-print" id="sidebar">           
		          <section class="vbox">
		            <section class="scrollable">
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
				          	<a href="../../rfid2/main/index.htm" onclick="showLoader2()" >
	                          <i class="fa fa-dashboard"></i> 
	                          <span class="hidden-nav-xs">Dashboard</span>
	                        </a>
				          </li>
				          <%if(Integer.parseInt(session.getAttribute("nrcus").toString())>1) { %>
				          <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../../rfid2/popup/sel_customer.jsp" data-toggle="ajaxModal"  id="mnu_sel_cus">
	                          <i class="icon-users"></i> 
	                          <span class="hidden-nav-xs">Change Customer</span>
	                        </a>
				          </li>
				          <%} %>
				        </ul>
				        
<%
String[] mnuRF201=null;
String[] mnuRF202=null;
String[] mnuRF203=null;
String[] mnuRF204=null;

String mnuArrayRF201 = ""+session.getAttribute("mnuArrayRF201");
mnuArrayRF201=Utility.noNull(mnuArrayRF201);

String mnuArrayRF202 = ""+session.getAttribute("mnuArrayRF202");
mnuArrayRF202=Utility.noNull(mnuArrayRF202);

String mnuArrayRF203 = ""+session.getAttribute("mnuArrayRF203");
mnuArrayRF203=Utility.noNull(mnuArrayRF203);

String mnuArrayRF204 = ""+session.getAttribute("mnuArrayRF204");
mnuArrayRF204=Utility.noNull(mnuArrayRF204);

if(mnuArrayRF201.trim().length()>0)
	mnuRF201 = mnuArrayRF201.split(";");

if(mnuArrayRF202.trim().length()>0)
	mnuRF202 = mnuArrayRF202.split(";");

if(mnuArrayRF203.trim().length()>0)
	mnuRF203 = mnuArrayRF203.split(";");

if(mnuArrayRF204.trim().length()>0)
	mnuRF204 = mnuArrayRF204.split(";");
%>

					<ul class="nav" data-ride="collapse">
	                    <li class="hidden-nav-xs padder m-t m-b-sm text-xs text-muted">
	                      <%=propLang.get("menu") %>
	                    </li>

<%
		
			for(m=0; m < mnuRF201.length ; m++)
			{
 %>
		                    <li class="b-b" style="border-color:#4D5D6D">
		                      <a href="<%=mnuRF201[m].substring(55,105) %>" onclick="showLoader2()"  class="auto">
		                        <i class="<%=mnuRF201[m].substring(105,mnuRF201[m].length()) %>"></i>
                        	    <span><%=mnuRF201[m].substring(3,53) %></span>
		                      </a>
		                    </li>
<%
			}
		
 %>      


<%
if(mnuRF202!=null) {
	if(mnuRF202.length==1) {
		m=0;
 %>
	
	 					<li class="b-b" style="border-color:#4D5D6D">
	                      <a href="<%=mnuRF202[m].substring(55,105) %>" onclick="showLoader2()"   >
	                          <i class="<%=mnuRF202[m].substring(105,mnuRF202[m].length()) %>"></i> 
	                          <span><%=mnuRF202[m].substring(3,53) %></span>
	                      </a>
	                    </li>
<%	
	}
	else {
%>
	                    <li class="b-b" style="border-color:#4D5D6D">
	                      <a href="#" class="auto" >
	                        <span class="pull-right text-muted">
	                          <i class="fa fa-angle-left text"></i>
	                          <i class="fa fa-angle-down text-active"></i>
	                        </span>
	                        <i class="fa fa-gear icon">
                        	</i>
	                        <span><%=propLang.get("rf2config") %></span>
	                      </a>
	                      <ul class="nav dk text-sm">
	                    
<%
		for(m=0; m < mnuRF202.length ; m++)
		{
 %>
		                    <li>
		                      <a href="<%=mnuRF202[m].substring(55,105) %>" onclick="showLoader2()">
		                        <i class="fa fa-angle-right text-xs"></i>
                        	    <span><%=mnuRF202[m].substring(3,53) %></span>
		                      </a>
		                      
		                    </li>
<%
		}
 %>      
	                      </ul>
	                    </li>
<%
	}
}
%>

<%
if(mnuRF203!=null) {
	if(mnuRF203.length==1) {
		m=0;
 %>
	
	 					<li class="b-b" style="border-color:#4D5D6D">
	                      <a href="<%=mnuRF203[m].substring(55,105) %>" onclick="showLoader2()"   >
	                          <i class="<%=mnuRF203[m].substring(105,mnuRF203[m].length()) %>"></i> 
	                          <span><%=mnuRF203[m].substring(3,53) %></span>
	                      </a>
	                    </li>
<%	
	}
	else {
%>
	                    <li class="b-b" style="border-color:#4D5D6D">
	                      <a href="#" class="auto" >
	                        <span class="pull-right text-muted">
	                          <i class="fa fa-angle-left text"></i>
	                          <i class="fa fa-angle-down text-active"></i>
	                        </span>
	                        <i class="icon-printer icon">
                        	</i>
	                        <span><%=propLang.get("btnStampa") %></span>
	                      </a>
	                      <ul class="nav dk text-sm">
<%
		for(m=0; m < mnuRF203.length ; m++)
		{
 %>
		                    <li>
		                      <a href="<%=mnuRF203[m].substring(55,105) %>" onclick="showLoader2()">
		                        <i class="fa fa-angle-right text-xs"></i>
                        	    <span><%=mnuRF203[m].substring(3,53) %></span>
		                      </a>
		                      
		                    </li>
<%
		}
 %>      
	                      </ul>
	                    </li>
<%
	}
}
%>

<%
if(mnuRF204!=null) {
	if(mnuRF204.length==1) {
		m=0;
 %>
	
	 					<li class="b-b" style="border-color:#4D5D6D">
	                      <a href="<%=mnuRF204[m].substring(55,105) %>" onclick="showLoader2()"   >
	                          <i class="<%=mnuRF204[m].substring(105,mnuRF204[m].length()) %>"></i> 
	                          <span><%=mnuRF204[m].substring(3,53) %></span>
	                      </a>
	                    </li>
<%	
	}
	else {
%>
	                    <li class="b-b" style="border-color:#4D5D6D">
	                      <a href="#" class="auto" >
	                        <span class="pull-right text-muted">
	                          <i class="fa fa-angle-left text"></i>
	                          <i class="fa fa-angle-down text-active"></i>
	                        </span>
	                        <i class="fa fa-tumblr-square icon">
                        	</i>
	                        <span>TransACT Connect</span>
	                      </a>
	                      <ul class="nav dk text-sm">
<%
		for(m=0; m < mnuRF204.length ; m++)
		{
 %>
		                    <li>
		                      <a href="<%=mnuRF204[m].substring(55,105) %>" onclick="showLoader2()">
		                        <i class="fa fa-angle-right text-xs"></i>
                        	    <span><%=mnuRF204[m].substring(3,53) %></span>
		                      </a>
		                      
		                    </li>
<%
		}
 %>      
	                      </ul>
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
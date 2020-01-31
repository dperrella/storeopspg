<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
	import="pkg.utility.Utility"
    pageEncoding="ISO-8859-1"
%>


				<aside class="aside-md bg-dark nav-xs" id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto bg clearfix">
	                  
	                  	<ul class="nav bg clearfix">
				       	  <li class="b-b" style="border-color:#4D5D6D">
				            <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 28px" class="text-muted hidden-xs">
					            <i class="fa fa-indent text"></i>
					            <i class="fa fa-dedent text-active"></i>
					            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
					        </a>
				          </li>
				          <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../../hr/main/index.htm" onclick="showLoader2()" style="padding-left: 28px">
	                          <i class="fa fa-dashboard"></i> 
	                          <span class="hidden-nav-xs m-l-sm">Dashboard</span>
	                        </a>
				          </li>
				          <%if(Integer.parseInt(session.getAttribute("nrcus").toString())>1) { %>
  
				          <li class="b-b" style="border-color:#4D5D6D">
				          	<a href="../../analytics2/popup/sel_customer.jsp" data-toggle="ajaxModal" style="padding-left: 28px" id="mnu_sel_cus">
	                          <i class="icon-users"></i> 
	                          <span class="hidden-nav-xs m-l-sm">Change Customer</span>
	                        </a>
				          </li>
				          <%} %>
				        </ul>
                        
                        
                        
<%

String[] mnuSHR01=null;
String[] mnuSHR02=null;
String[] mnuSHR03=null;
String[] mnuSHR04=null;

String mnuArraySHR01 = ""+session.getAttribute("mnuArraySHR01");
mnuArraySHR01=Utility.noNull(mnuArraySHR01);

String mnuArraySHR02 = ""+session.getAttribute("mnuArraySHR02");
mnuArraySHR02=Utility.noNull(mnuArraySHR02);

String mnuArraySHR03 = ""+session.getAttribute("mnuArraySHR03");
mnuArraySHR03=Utility.noNull(mnuArraySHR03);

String mnuArraySHR04 = ""+session.getAttribute("mnuArraySHR04");
mnuArraySHR04=Utility.noNull(mnuArraySHR04);

if(mnuArraySHR01.trim().length()>0)
	mnuSHR01 = mnuArraySHR01.split(";");

if(mnuArraySHR02.trim().length()>0)
	mnuSHR02 = mnuArraySHR02.split(";");

if(mnuArraySHR03.trim().length()>0)
	mnuSHR03 = mnuArraySHR03.split(";");

if(mnuArraySHR04.trim().length()>0)
	mnuSHR04 = mnuArraySHR04.split(";");
%>

					<ul class="nav" data-ride="collapse">
	                    <li class="hidden-nav-xs padder m-t m-b-sm text-xs text-muted">
	                      <%=propLang.get("menu") %>
	                    </li>
<%
if(mnuSHR01!=null) {
	if(mnuSHR01.length==1) {
		m=0;
 %>
	
	 					<li class="b-b" style="border-color:#4D5D6D">
	                      <a href="<%=mnuSHR01[m].substring(55,105) %>" onclick="showLoader2()"  style="padding-left: 28px" class="list-group-item">
	                          <i class="<%=mnuSHR01[m].substring(105,mnuSHR01[m].length()) %>"></i> 
	                          <span class="hidden-nav-xs m-l-sm"><%=mnuSHR01[m].substring(3,53) %></span>
	                      </a>
	                    </li>
<%	
	}
	else {
%>
	                    <li class="b-b" style="border-color:#4D5D6D">
	                      <a href="#" class="auto" style="padding-left: 18px" class="list-group-item">
	                        <span class="pull-right text-muted">
	                          <i class="fa fa-angle-left text"></i>
	                          <i class="fa fa-angle-down text-active"></i>
	                        </span>
	                        <i class="icon-drawer icon">
	                        </i>
	                        <span class="hidden-nav-xs m-l-sm"><%=propLang.get("anagrafici") %></span>
	                      </a>
	                      <ul class="nav dk text-sm" style="display: none;">
<%
		for(m=0; m < mnuSHR01.length ; m++)
		{
 %>
		                    <li>
		                      <a href="<%=mnuSHR01[m].substring(55,105) %>" onclick="showLoader2()" style="padding-left: 28px" class="list-group-item">
		                        <i class="<%=mnuSHR01[m].substring(105,mnuSHR01[m].length()) %>"></i> 
                        	    <span class="hidden-nav-xs m-l-sm"><%=mnuSHR01[m].substring(3,53) %></span>
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
if(mnuSHR02!=null) {
	if(mnuSHR02.length==1) {
		m=0;
 %>
	
	 					<li class="b-b" style="border-color:#4D5D6D">
	                      <a href="<%=mnuSHR02[m].substring(55,105) %>" onclick="showLoader2()"  style="padding-left: 28px" >
	                          <i class="<%=mnuSHR02[m].substring(105,mnuSHR02[m].length()) %>"></i> 
	                          <span class="hidden-nav-xs m-l-sm"><%=mnuSHR02[m].substring(3,53) %></span>
	                      </a>
	                    </li>
<%	
	}
	else {
%>
	                    <li class="b-b" style="border-color:#4D5D6D">
	                      <a href="#" class="auto" style="padding-left: 18px">
	                        <span class="pull-right text-muted">
	                          <i class="fa fa-angle-left text"></i>
	                          <i class="fa fa-angle-down text-active"></i>
	                        </span>
	                        <i class="icon-share icon">
                        	</i>
	                        <span class="hidden-nav-xs m-l-sm"><%=propLang.get("relazioni") %></span>
	                      </a>
	                      <ul class="nav dk text-sm" style="display: none;">
<%
		for(m=0; m < mnuSHR02.length ; m++)
		{
 %>
		                    <li>
		                      <a href="<%=mnuSHR02[m].substring(55,105) %>" onclick="showLoader2()" style="padding-left: 28px" class="list-group-item">
		                        <i class="<%=mnuSHR02[m].substring(105,mnuSHR02[m].length()) %>"></i> 
                        	    <span class="hidden-nav-xs m-l-sm"><%=mnuSHR02[m].substring(3,53) %></span>
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
if(mnuSHR03!=null) {
	if(mnuSHR03.length==1) {
		m=0;
 %>
	
	 					<li class="b-b" style="border-color:#4D5D6D">
	                      <a href="<%=mnuSHR03[m].substring(55,105) %>" onclick="showLoader2()"  style="padding-left: 28px" >
	                          <i class="<%=mnuSHR03[m].substring(105,mnuSHR03[m].length()) %>"></i> 
	                          <span class="hidden-nav-xs m-l-sm"><%=mnuSHR03[m].substring(3,53) %></span>
	                      </a>
	                    </li>
<%	
	}
	else {
%>
	                    <li class="b-b" style="border-color:#4D5D6D">
	                      <a href="#" class="auto" style="padding-left: 18px">
	                        <span class="pull-right text-muted">
	                          <i class="fa fa-angle-left text"></i>
	                          <i class="fa fa-angle-down text-active"></i>
	                        </span>
	                        <i class="icon-calendar icon">
                        	</i>
	                        <span class="hidden-nav-xs m-l-sm"><%=propLang.get("pianificazione") %></span>
	                      </a>
	                      <ul class="nav dk text-sm" style="display: none;">
<%
		for(m=0; m < mnuSHR03.length ; m++)
		{
 %>
		                    <li>
		                      <a href="<%=mnuSHR03[m].substring(55,105) %>" onclick="showLoader2()" style="padding-left: 28px" class="list-group-item">
		                        <i class="<%=mnuSHR03[m].substring(105,mnuSHR03[m].length()) %>"></i> 
                        	    <span class="hidden-nav-xs m-l-sm"><%=mnuSHR03[m].substring(3,53) %></span>
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
if(mnuSHR04!=null) {
	if(mnuSHR04.length==1) {
		m=0;
 %>
	
	 					<li class="b-b" style="border-color:#4D5D6D">
	                      <a href="<%=mnuSHR04[m].substring(55,105) %>" onclick="showLoader2()"  style="padding-left: 28px" >
	                          <i class="<%=mnuSHR04[m].substring(105,mnuSHR04[m].length()) %>"></i> 
	                          <span class="hidden-nav-xs m-l-sm"><%=mnuSHR04[m].substring(3,53) %></span>
	                      </a>
	                    </li>
<%	
	}
	else {
%>
	                    <li class="b-b" style="border-color:#4D5D6D">
	                      <a href="#" class="auto" style="padding-left: 18px">
	                        <span class="pull-right text-muted">
	                          <i class="fa fa-angle-left text"></i>
	                          <i class="fa fa-angle-down text-active"></i>
	                        </span>
	                        <i class="fa fa-check-square-o icon">
                        	</i>
	                        <span class="hidden-nav-xs m-l-sm"><%=propLang.get("controlli") %></span>
	                      </a>
	                      <ul class="nav dk text-sm" style="display: none;">
<%
		for(m=0; m < mnuSHR04.length ; m++)
		{
 %>
		                    <li>
		                      <a href="<%=mnuSHR04[m].substring(55,105) %>" onclick="showLoader2()" style="padding-left: 28px" class="list-group-item">
		                        <i class="<%=mnuSHR04[m].substring(105,mnuSHR04[m].length()) %>"></i> 
                        	    <span class="hidden-nav-xs m-l-sm"><%=mnuSHR04[m].substring(3,53) %></span>
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

 				  	  </div>
	                </section>
	                
	              </section>              
	            </aside>
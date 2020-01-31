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
                        <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 28px" class="text-muted list-group-item hidden-xs">
				            <i class="fa fa-indent text"></i>
				            <i class="fa fa-dedent text-active"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
                  		<a href="../../hr/main/index.htm" onclick="showLoader2()" style="padding-left: 28px" class="list-group-item">
                          <i class="fa fa-dashboard"></i> 
                          <span class="hidden-nav-xs m-l-sm">Dashboard</span>
                        </a>
                        <a href="../../analytics2/popup/sel_customer.jsp" data-toggle="ajaxModal" style="padding-left: 28px" class="list-group-item" id="mnu_sel_cus">
                          <i class="icon-users"></i> 
                          <span class="hidden-nav-xs m-l-sm">Change Customer</span>
                        </a>
                        
<%

String[] mnuSHR01=null;
String[] mnuSHR02=null;
String[] mnuSHR03=null;
String[] mnuSHR04=null;

String mnuArraySHR01 = ""+session.getAttribute("mnuArraySHR01");
mnuArraySHR01=Utility.noNull(mnuArraySHR01);

if(mnuArraySHR01.trim().length()>0) {
	mnuSHR01 = mnuArraySHR01.split(";");

	m = 0;
	for(m=0; m < mnuSHR01.length ; m++)
	{
 %>
 						<a href="<%=mnuSHR01[m].substring(55,105) %>" onclick="showLoader2()"  style="padding-left: 28px" class="list-group-item">
                          <i class="<%=mnuSHR01[m].substring(105,mnuSHR01[m].length()) %>"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=mnuSHR01[m].substring(3,53) %></span>
                        </a>
<%
	}
}
%>





 				  	  </div>
	                </section>
	                
	              </section>              
	            </aside>
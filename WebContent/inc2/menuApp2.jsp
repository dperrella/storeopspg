<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
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
                  		<a href="../../counter/main/index.htm" style="padding-left: 28px" class="list-group-item">
                          <i class="fa fa-dashboard"></i> 
                          <span class="hidden-nav-xs m-l-sm">Dashboard</span>
                        </a>
<%
String[] mnuSCT01=null;

String mnuArraySCT01 = (String) session.getAttribute("mnuArraySCT01");
System.out.println("-----"+mnuArraySCT01);
if(mnuArraySCT01.trim().length()>0) {
	mnuSCT01 = mnuArraySCT01.split(";");

	m = 0;
	for(m=0; m < mnuSCT01.length ; m++)
	{
 %>
 						<a href="<%=mnuSCT01[m].substring(55,105) %>" style="padding-left: 28px" class="list-group-item">
                          <i class="<%=mnuSCT01[m].substring(105,mnuSCT01[m].length()) %>"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=mnuSCT01[m].substring(3,53) %></span>
                        </a>
<%
	}
}
%>


 				  	  </div>
	                </section>
	                
	              </section>              
	            </aside>
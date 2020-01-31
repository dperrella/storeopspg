<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
%>

				<aside class="aside-md bg-dark nav-xs " id="sidebar">
	              <section class="vbox animated fadeInUp">
	                <section class="scrollable hover">
	            
	                  <div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto bg clearfix">
                        <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 28px" class="text-muted list-group-item hidden-xs">
				            <i class="fa fa-indent text"></i>
				            <i class="fa fa-dedent text-active"></i>
				            <span class="hidden-nav-xs m-l-sm h4 hont-thin">Menu</span>
				        </a>
                  		<a href="../../analytics/main/index.htm" style="padding-left: 28px" class="list-group-item">
                          <i class="fa fa-dashboard"></i> 
                          <span class="hidden-nav-xs m-l-sm">Dashboard</span>
                        </a>
<%
String[] mnuSAN01=null;
String[] mnuSAN02=null;
String[] mnuSAN03=null;

String mnuArraySAN01 = (String) session.getAttribute("mnuArraySAN01");
if(mnuArraySAN01.trim().length()>0) {
	mnuSAN01 = mnuArraySAN01.split(";");
	m = 0;
	for(m=0; m < mnuSAN01.length ; m++)
	{
 %>
 						<a href="<%=mnuSAN01[m].substring(55,105) %>" style="padding-left: 28px" class="list-group-item">
                          <i class="<%=mnuSAN01[m].substring(105,mnuSAN01[m].length()) %>"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=mnuSAN01[m].substring(3,53) %></span>
                        </a>
<%
	}
}
%>

<%
String mnuArraySAN02 = (String) session.getAttribute("mnuArraySAN02");
if(mnuArraySAN02.trim().length()>0) {
	mnuSAN02 = mnuArraySAN02.split(";");
	m = 0;
	for(m=0; m < mnuSAN02.length ; m++)
	{
 %>
 						<a href="<%=mnuSAN02[m].substring(55,105) %>" style="padding-left: 28px" class="list-group-item">
                          <i class="<%=mnuSAN02[m].substring(105,mnuSAN02[m].length()) %>"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=mnuSAN02[m].substring(3,53) %></span>
                        </a>
<%
	}
}
%>

<%
String mnuArraySAN03 = (String) session.getAttribute("mnuArraySAN03");
if(mnuArraySAN03.trim().length()>0) {
	mnuSAN03 = mnuArraySAN03.split(";");
	m = 0;
	for(m=0; m < mnuSAN03.length ; m++)
	{
 %>
 						<a href="<%=mnuSAN03[m].substring(55,105) %>" style="padding-left: 28px" class="list-group-item">
                          <i class="<%=mnuSAN03[m].substring(105,mnuSAN03[m].length()) %>"></i> 
                          <span class="hidden-nav-xs m-l-sm"><%=mnuSAN03[m].substring(3,53) %></span>
                        </a>
<%
	}
}
%>
 				  	  </div>
	                </section>
	                
	              </section>              
	            </aside>
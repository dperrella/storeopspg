<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
%>

				<nav class="nav-primary hidden-xs">
                  
<%
// Legge il file di properties associato alla lingua sul profilo
ReadProperties prop = new ReadProperties(session.getAttribute("language").toString()+".properties");

String[] mnuZZ=null;
String[] mnuYY=null;

String[] mnu01=null;

String mnuArrayZZ = (String) session.getAttribute("mnuArrayZZ");
if(mnuArrayZZ.trim().length()>0)
	mnuZZ = mnuArrayZZ.split(";");

String mnuArrayYY = (String) session.getAttribute("mnuArrayYY");
if(mnuArrayYY.trim().length()>0)
	mnuYY = mnuArrayYY.split(";");

String mnuArray01 = (String) session.getAttribute("mnuArray01");
if(mnuArray01.trim().length()>0)
	mnu01 = mnuArray01.split(";");

int m = 0;
%>                  
                  
                  
<%
if(mnuZZ!=null) {	
	if(mnuZZ.length==1) {
%>
		<ul class="nav bg clearfix">
       	  <li>
            <a href="<%=mnuZZ[0].substring(55,105) %>">
              <i class="<%=mnuZZ[0].substring(105,mnuZZ[0].length()) %>"></i>
              <span><%=mnuZZ[0].substring(3,53) %></span>
            </a>
          </li>
        </ul>
<%	
	}
	else {
%>
 				 <ul class="nav bg clearfix">
	            	<li class="hidden-nav-xs padder m-t m-b-sm text-xs text-muted">
	                      Smart Suite
	                </li>
<%
		for(m=0; m < mnuZZ.length ; m++)
		{
 %>
                    <li>
                      <a href="<%=mnuZZ[m].substring(55,105) %>" class="auto">
                        <i class="<%=mnuZZ[m].substring(105,mnuZZ[m].length()) %>"></i>
                        <span><%=mnuZZ[m].substring(3,53) %></span>
                      </a>
                    </li>
<%
		}
	}
%>
	             	<li class="m-b hidden-nav-xs"></li>
                  </ul>
<%
}
%>




				  <ul class="nav" data-ride="collapse">
                    <li class="hidden-nav-xs padder m-t m-b-sm text-xs text-muted">
                      Config <%=prop.get("menu") %>
                    </li>
<%
if(mnuYY!=null) {
	if(mnuYY.length==1) {
 %>
 					<li>
                      <a href="<%=mnuYY[0].substring(55,105) %>">
                        <i class="<%=mnuYY[0].substring(105,mnuYY[0].length()) %>"></i>
                        <span><%=mnuYY[0].substring(3,53) %></span>
                      </a>
                    </li>
<%	
	}
	else {
%>
                    <li >
                      <a href="#" class="auto">
                        <span class="pull-right text-muted">
                          <i class="fa fa-angle-left text"></i>
                          <i class="fa fa-angle-down text-active"></i>
                        </span>
                        <i class="icon-settings icon">
                        </i>
                        <span>Config</span>
                      </a>
                      <ul class="nav dk text-sm">
<%
		for(m=0; m < mnuYY.length ; m++)
		{
 %>
	                    <li>
	                      <a href="<%=mnuYY[m].substring(55,105) %>" class="auto">
	                        <i class="fa fa-angle-right text-xs"></i>
	                        <span><%=mnuYY[m].substring(3,53) %></span>
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
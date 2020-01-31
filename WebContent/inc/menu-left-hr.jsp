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
String[] mnu01=null;
String[] mnu02=null;
String[] mnu03=null;
String[] mnu04=null;

String tipoLogin = (String) session.getAttribute("tipoLogin");

String mnuArrayZZ = (String) session.getAttribute("mnuArrayZZ");
if(mnuArrayZZ.trim().length()>0)
	mnuZZ = mnuArrayZZ.split(";");

String mnuArray01 = (String) session.getAttribute("mnuArray01");
if(mnuArray01.trim().length()>0)
	mnu01 = mnuArray01.split(";");

String mnuArray02 = (String) session.getAttribute("mnuArray02");
if(mnuArray02.trim().length()>0)
	mnu02 = mnuArray02.split(";");

String mnuArray03 = (String) session.getAttribute("mnuArray03");
if(mnuArray03.trim().length()>0)
	mnu03 = mnuArray03.split(";");

String mnuArray04 = (String) session.getAttribute("mnuArray04");
if(mnuArray04.trim().length()>0)
	mnu04 = mnuArray04.split(";");

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
	                      <%=prop.get("config") %>
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
	             	<!-- <li class="m-b hidden-nav-xs"></li> -->
                  </ul>
<%
}
%>
                  
                  <ul class="nav" data-ride="collapse">
                    <li class="hidden-nav-xs padder m-t m-b-sm text-xs text-muted">
                      <%=prop.get("menu") %>
                    </li>
 
 
<%
if(mnu01!=null) {
	if(mnu01.length==1) {
 %>
 					<li>
                      <a href="<%=mnu01[0].substring(55,105) %>">
                        <i class="<%=mnu01[0].substring(105,mnu01[0].length()) %>"></i>
                        <span><%=mnu01[0].substring(3,53) %></span>
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
                        <i class="icon-drawer icon">
                        </i>
                        <span><%=prop.get("anagrafici") %></span>
                      </a>
                      <ul class="nav dk text-sm">
<%
		for(m=0; m < mnu01.length ; m++)
		{
 %>
	                    <li>
	                      <a href="<%=mnu01[m].substring(55,105) %>" class="auto">
	                        <i class="fa fa-angle-right text-xs"></i>
	                        <span><%=mnu01[m].substring(3,53) %></span>
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
if(mnu02!=null) {
	if(mnu02.length==1) {
 %>
 					<li>
                      <a href="<%=mnu02[0].substring(55,105) %>">
                        <i class="<%=mnu02[0].substring(105,mnu02[0].length()) %>"></i>
                        <span><%=mnu02[0].substring(3,53) %></span>
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
                        <i class="icon-share icon">
                        </i>
                        <span><%=prop.get("relazioni") %></span>
                      </a>
                      <ul class="nav dk text-sm">
<%
		for(m=0; m < mnu02.length ; m++)
		{
 %>
	                    <li>
	                      <a href="<%=mnu02[m].substring(55,105) %>" class="auto">
	                        <i class="fa fa-angle-right text-xs"></i>
	                        <span><%=mnu02[m].substring(3,53) %></span>
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
if(mnu03!=null) {
	if(mnu03.length==1) {
 %>
 					<li>
                      <a href="<%=mnu03[0].substring(55,105) %>">
                        <i class="<%=mnu03[0].substring(105,mnu03[0].length()) %>"></i>
                        <span><%=mnu03[0].substring(3,53) %></span>
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
                        <i class="icon-calendar icon">
                        </i>
                        <span><%=prop.get("pianificazione") %></span>
                      </a>
                      <ul class="nav dk text-sm">
<%
		for(m=0; m < mnu03.length ; m++)
		{
 %>
	                    <li>
	                      <a href="<%=mnu03[m].substring(55,105) %>" class="auto">
	                        <i class="fa fa-angle-right text-xs"></i>
	                        <span><%=mnu03[m].substring(3,53) %></span>
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
if(mnu04!=null) {
	if(mnu04.length==1) {
 %>
 					<li>
                      <a href="<%=mnu04[0].substring(55,105) %>">
                        <i class="<%=mnu04[0].substring(105,mnu04[0].length()) %>"></i>
                        <span><%=mnu04[0].substring(3,53) %></span>
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
                        <i class="fa fa-check-square-o icon">
                        </i>
                        <span><%=prop.get("controlli") %></span>
                      </a>
                      <ul class="nav dk text-sm">
<%
		for(m=0; m < mnu04.length ; m++)
		{
 %>
	                    <li>
	                      <a href="<%=mnu04[m].substring(55,105) %>" class="auto">
	                        <i class="fa fa-angle-right text-xs"></i>
	                        <span><%=mnu04[m].substring(3,53) %></span>
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
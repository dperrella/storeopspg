<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
    
%>
<%
ReadProperties prop_ = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
                 
<div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
  <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 20px" class="text-muted list-group-item" onclick="displayHid();">
          <i class="fa fa-dedent text-active"></i>
          <i class="fa fa-indent text"></i>
          <span class="hidden-nav-xs m-l-sm h4 hont-thin"><%=prop_.get("sm_m_per") %></span>
      </a>
<a href="smartcounter.htm?type=1" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="icon-home"></i> <span class="hidden-nav-xs m-l-sm"><%="Home"%></span>
</a>            
<a onclick="Pages('1')" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-sort-numeric-asc"></i> 
<span class="hidden-nav-xs m-l-sm"><%=prop_.get("sm_rol_1") %></span>
</a>
<a onclick="Pages('2')" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-list-ol"></i> 
<span class="hidden-nav-xs m-l-sm"><%=prop_.get("sm_rol_2") %></span>
</a>
<a onclick="Pages('3')" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="icon-calendar"></i> 
<span class="hidden-nav-xs m-l-sm"><%=prop_.get("sm_rol_3") %></span>
</a>
<a onclick="Pages('4')" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<!--  <i class="fa fa-columns"></i> fa-sort-alpha-asc -->
<i class="fa fa-sort-alpha-asc"></i>
<span class="hidden-nav-xs m-l-sm"><%=prop_.get("sm_rol_4") %></span>
  </a>
  
</div>
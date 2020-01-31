<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
%>
<%
ReadProperties prop_2 = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
                 
<div class="list-group no-radius no-border no-bg m-t-n-xxs m-b-none auto hidden-xs bg clearfix">
     <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 20px" class="text-muted list-group-item" onclick="displayHid();">
    <i class="fa fa-indent text-active"></i>
    <i class="fa fa-dedent text"></i>
    <span class="hidden-nav-xs m-l-sm h4 hont-thin"><%=prop_2.get("sm_m_imp") %></span>
</a>

<a href="./smartuser.htm" style="padding-left: 22px; cursor:pointer;" class="list-group-item active">
  <i class="icon-user fa-fw"></i>  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_g_pr") %></span>
</a>	
<a href="./smartsettings.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-gear fa-spin fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_m_imp_") %></span>
</a>

<% String Role = (String) session.getAttribute("Role");
if(Role.equals("ADMIN")) {
%>
<a href="./smartusers.htm" style="padding-left: 22px; cursor:pointer;" class="list-group-item active">
  <i class="icon-users fa-fw"></i>  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_g_ut") %></span>
</a>				        
<a href="./smarttime.htm" style="padding-left: 22px; cursor:pointer;" class="list-group-item active">
  <!-- <i class="fa fa-clock-o fa-fw"></i>  <span class="hidden-nav-xs m-l-sm"><%//=prop_2.get("sm_g_or") %></span> -->
  <i class="icon-clock fa-fw"></i>  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_g_or") %></span>
</a>				        
<a href="./smarttemplate.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-calendar fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_g_tot") %></span>
</a>				        
<a href="./smartfactor.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-superscript fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_g_adf") %></span>
</a>				        
<a href="./smartdevice.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-desktop fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_g_dev") %></span>
</a>                        
<% } %>                        
<a onclick="ActDeact()" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-bookmark-o fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%=prop_2.get("sm_m_lege_") %></span>
</a>
</div>
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
  <a href="#sidebar2" data-toggle="class:nav-xs,nav-xs" style="padding-left: 20px" class="text-muted list-group-item">
          <i class="fa fa-dedent text-active"></i>
          <i class="fa fa-indent text"></i> <span class="hidden-nav-xs m-l-sm h4 hont-thin"><%=prop_.get("sm_m_ope") %></span>
      </a>            
<a href="smartcontrol.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="icon-home"></i> <span class="hidden-nav-xs m-l-sm"><%="Home"%></span>
</a>
<a href="smartcontrol-customers.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-archive"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_1") %></span>
</a>
<a href="smartcontrol-stores.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-shopping-cart"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_2") %></span>
</a>
<a href="smartcontrol-devices.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-desktop"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_3") %></span>
</a>
<a href="smartcontrol-customers-agreement.htm" onclick="Caricamento();" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-database"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_4") %></span>
  </a>
<% if (session.getAttribute("RIDC").equals("super") || session.getAttribute("RIDC").equals("countryadmin") || session.getAttribute("RIDC").equals("list-admin")) {%>
<a href="smartcontrol-optempl.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-calendar"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_5") %></span>
  </a>  
<a href="smartcontrol-reports.htm" style="padding-left: 22px; cursor:pointer;" class="list-group-item">
<i class="fa fa-file-text-o"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_6") %></span>
  </a>  
 <a href="smartcontrol-alerts.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-bullhorn"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_7") %></span>
  </a>
<!--  
   <a href="smartcontrol-template-export.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-file-excel-o"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_12") %></span>
  </a> 
-->
  <a href="smartcontrol-users.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<!-- 
<i class="fa fa-user-plus"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_8") %></span>
-->
  <i class="icon-user-follow"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_8") %></span>
  </a>
  <% } %>  
  <a href="smartcontrol-user.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="icon-user"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_9") %></span>
  </a>
  <% if (session.getAttribute("RIDC").equals("super")) {%>
  <a href="smartcontrol-usersadmin.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="icon-users"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_10") %></span>
  </a>
  <% } %>
  <% if (session.getAttribute("RIDC").equals("super") || session.getAttribute("RIDC").equals("countryadmin") || session.getAttribute("RIDC").equals("list-admin")) {%>
  <!-- 
  <a onclick="Pages('11')" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
<i class="fa fa-check-square-o"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_11") %></span>
  </a>
 -->  
  <a href="smartcontrol-serveradmin.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
	<i class="fa fa-server"></i> <span class="hidden-nav-xs m-l-sm"><%=prop_.get("smc_rol_13") %></span>
  </a>
  <% } %>
  <!-- 
  <a href="smartcontrol-load-store.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item">
	<i class="fa fa-file-excel-o"></i> <span class="hidden-nav-xs m-l-sm"><%="Load Stores" %></span>
  </a>  
  -->
</div>
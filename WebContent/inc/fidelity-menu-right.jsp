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

<a href="caricopunti.htm" style="padding-left: 32px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-upload"></i>
  <span class="hidden-nav-xs m-l-sm"><%="Caricamento Punti" %></span>
</a>

<a href="saldo.htm" style="padding-left:30px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-money fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%="Saldo" %></span>
</a>

<a href="scaricopunti.htm" style="padding-left:32px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-download"></i>
  <span class="hidden-nav-xs m-l-sm"><%="Scarico Punti" %></span>
</a>

<a href="settingspunti.htm" style="padding-left: 32px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-cog"></i>
  <span class="hidden-nav-xs m-l-sm"><%="Settings Punti" %></span>
</a>
               

</div>
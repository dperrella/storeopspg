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
     <a href="#sidebar" data-toggle="class:nav-xs,nav-xs" style="padding-left: 20px" class="text-muted list-group-item">
    <i class="fa fa-indent text-active"></i>
    <i class="fa fa-dedent text"></i>
    <span class="hidden-nav-xs m-l-sm h4 hont-thin"><%="Sales Situation" %></span>
</a>

<a href="salessituation.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-ticket fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%="Tickets" %></span>
</a>

<a href="closecash.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-money fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%="Close Cash" %></span>
</a>

<a href="documents.htm" style="padding-left: 20px; cursor:pointer;" class="list-group-item active">
  <i class="fa fa-file-word-o fa-fw"></i>
  <span class="hidden-nav-xs m-l-sm"><%="Documents" %></span>
</a>

               

</div>
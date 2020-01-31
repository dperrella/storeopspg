<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
%>
<%
// Legge il file di properties associato alla lingua sul profilo
ReadProperties propts = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!-- <form method="post" action="../../app/main/ricerca.htm" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">-->
<form method="post" action="#" class="navbar-form navbar-left input-s-lg m-t m-l-n-xs hidden-xs" role="search">
  <div class="form-group">
    <div class="input-group">
      <span class="input-group-btn">
       <button type="button" class="btn btn-sm bg-white btn-icon rounded"><i class="fa fa-search"></i></button>
      </span>
      <input type="text" name="myInput_" id="myInput_" onkeyup="myFunction()" class="form-control input-sm no-border rounded" style="width: 300%" placeholder="<%=propts.get("cerca") %>..."> 
    </div>
  </div>
</form>
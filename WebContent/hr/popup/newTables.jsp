<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<form action="tables-list.htm" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" name="xcdtb" value="TAB">
<input type="hidden" id="tipoForm" name="tipoForm" value="I">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin"><%=propLang.get("tab_newtab") %></h3>
	</div>
	<div class="modal-body">
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("tab_xcdtb") %></label>
	          <input type="text" id="xcdtb" name="xcdtb" class="form-control" value="TAB" maxlength="3" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("tab_xcdel") %></label>
	          <input type="text" id="xcdel" name="xcdel" class="form-control" value="" maxlength="4">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group">
	        <label><%=propLang.get("tab_xdtab") %></label>
	        <input type="text" id="xdtab" name="xdtab" class="form-control" value="">                        
	    </div>
	    
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

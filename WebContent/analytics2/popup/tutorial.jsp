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
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin">Analytics Guide</h3>
	</div>
	<div class="modal-body">
		
		<iframe id="tutorial" width="560" height="315" src="https://storeops.checkpointsystems.com/<%=session.getAttribute("language").toString() %>_Analytics.mp4" frameborder="0" allowfullscreen></iframe>
			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Close</a>
	</div>
  </div>
</div>
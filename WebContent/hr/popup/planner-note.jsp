<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");
String tp = request.getParameter("tp");
String status = request.getParameter("status");
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin">Note</h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	    	<div class="col-sm-12">
		        <label>Note:</label><br/>
				<textarea name="note" id="note" style="height: 100px" class="form-control"></textarea>
			</div>
		</div>
					
	</div>
	<div class="modal-footer">
	    <a class="btn btn-default" data-dismiss="modal" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<a class="btn btn-success btn-lg" onclick="setNote('<%=status%>')"><%=propLang.get("pop_ap_conferma") %></a>
	</div>
  </div>
</div>

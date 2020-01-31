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

String id_store = request.getParameter("id_store");
id_store = Utility.noNull(id_store);
String af = request.getParameter("af");
af = Utility.noNull(af);
String afm = request.getParameter("afm");
afm = Utility.noNull(afm);
String afs = request.getParameter("afs");
afs = Utility.noNull(afs);
%>

<div class="modal-dialog modal-sm">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Adjustment Factor</h3>
	</div>
	<div class="modal-body">
		
		<input type="hidden" name="id_store" id="id_store" value="<%=id_store %>"/>
		
		<label>Valore in %</label><br/>
		<input type="text" class="form-control" placeholder="Value in %" id="af" name="af" value="<%=af %>"/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>Valore Fisso Mattina</label><br/>
		<input type="text" class="form-control" placeholder="Value in %" id="afm" name="afm" value="<%=afm %>"/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>Valore Fisso Sera</label><br/>
		<input type="text" class="form-control" placeholder="Value in %" id="afs" name="afs" value="<%=afs %>"/>
			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setAF(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("btnConferma") %></a>
	</div>
  </div>
</div>

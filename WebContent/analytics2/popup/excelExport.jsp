<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.analytics2.DeskReportExcel"
import="pkg.analytics2.DeskReportComparisonExcel"
import="pkg.analytics2.DeskReportAdvancedExcel"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
String table="";
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Excel Export</h3>
	</div>
	<div class="modal-body">

<%	
	// **********************************************************
	// Connessione al DATABASE
	try
	{
		String idcus = request.getParameter("idcus");
		String dtda = request.getParameter("dtda");
		String dtal = request.getParameter("dtal");
		String idstores = request.getParameter("idstores");
		String kpi = request.getParameter("kpi");
		String comparison = request.getParameter("comparison");
		String group = request.getParameter("group");
		String upd = request.getParameter("upd");
		String tipoPeriod = request.getParameter("tipoPeriod");
		String lang = request.getParameter("lang");
		String like4like = request.getParameter("like4like");
		String id_advanced = request.getParameter("id_advanced");
		
		String tipo = request.getParameter("tipo");
		
		if(tipo.equals("R")) {
			table = DeskReportExcel.getXls(idcus, dtda, dtal, idstores, kpi, 
				comparison, group, upd, tipoPeriod, lang, session.getAttribute("uid").toString());
		}
		else if(tipo.equals("C")) {
			table = DeskReportComparisonExcel.getXls(idcus, dtda, dtal, idstores, kpi, 
    				comparison, group, upd, tipoPeriod, lang, like4like, session.getAttribute("uid").toString());
		}
		else if(tipo.equals("A")) {
			table = DeskReportAdvancedExcel.getXls(idcus, dtda, dtal, idstores, kpi, 
    				comparison, group, upd, tipoPeriod, lang, like4like, id_advanced);	
		}
		else {
			table = "";
		}
			
		if(table.length()>0) {
%>
			<div class="alert alert-success">
              <i class="icon-check"></i> <strong><%=propLang.get("msgSuccess") %></strong>.
            </div>
				
		
<%
		}
		else {
%>
			<div class="alert alert-danger">
              <i class="fa fa-warning"></i> <strong><%=propLang.get("msgError") %></strong>.
            </div>
<%
		}

	}
	catch(Exception e) {
		e.printStackTrace();
	}
%>
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
	</div>
  </div>
</div>

<div id="table_wrapper2" style="visibility:hidden">
<%=table %>
</div>

<script>
	
    function doit(type, fn, dl) {
		var elt = document.getElementById('table_wrapper2');
		var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
		return dl ?
			XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
			XLSX.writeFile(wb, fn || ('download.' + (type || 'xlsx')));
	}
	doit('xlsx');

	$('#table_wrapper2').hide();
	

</script>
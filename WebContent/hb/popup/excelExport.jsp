<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.hb.DeskReportExcel"
import="pkg.hb.DeskReportComparisonExcel"
import="pkg.hb.DeskReportAdvancedExcel"
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
			
	var data_type = 'data:application/vnd.ms-excel';
       var table_div = document.getElementById('table_wrapper2');//table_wrapper is table id
       var table_html = table_div.outerHTML.replace(/ /g, '%20');

       var a = document.createElement('a');
       a.href = data_type + ', ' + table_html;
       a.download = 'table_name_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
       a.click();

</script>
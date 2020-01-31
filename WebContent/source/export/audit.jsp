<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.sql.*"
import="java.util.ArrayList"
import="java.util.HashMap"
import="java.util.List"
import="java.util.Map"
import="pkg.beans.ats.Audit"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
String table="";

String idusr = session.getAttribute("username").toString();
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
	Connection conn = null;
	List<Audit> audit = new ArrayList<Audit>();
	audit.clear();

	try
	{
		conn = Utility.getConnData();
		audit = Audit.getAuditsByUser(conn, idusr, "2");
		
		table+= "<table class=\"table table-striped\">"+
				"<thead>"+
				"	<tr>"+
				"		<th>#</th>"+
				"		<th>"+propLang.get("aud_id")+"</th>"+
				"		<th>"+propLang.get("aud_name")+"</th>"+
				"		<th>"+propLang.get("aud_cr_date")+"</th>"+
				"		<th>"+propLang.get("aud_cl_date")+"</th>"+
				"		<th>"+propLang.get("aud_type")+"</th>"+
				"		<th>"+propLang.get("aud_store")+"</th>"+
				"		<th>"+propLang.get("aud_state")+"</th>"+
				"	</tr>"+
				"</thead>"+
				"<tbody>";
		
		int $riga = 0;
		
		for(int i=0;i<audit.size();i++) {
			$riga = i+1;
			
			table+="<tr>"+
					"<td>"+$riga+"</td>"+
					"<td>"+audit.get(i).getIdAudit()+"</td>"+
					"<td>"+audit.get(i).getName()+"</td>"+
					"<td>"+audit.get(i).getCreationDate()+"</td>"+
					"<td>"+audit.get(i).getClosingDate()+"</td>"+
					"<td>"+audit.get(i).getTypeDescription()+"</td>"+
					"<td>"+audit.get(i).getStoreDescription()+"</td>"+
					"<td>"+audit.get(i).getState()+"</td>"+
					"</tr>";	
		}
		
		table+=	"</tbody>"+
				"</table>";
		
			
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
	finally {
		if(conn!=null)
			conn.close();
	}
%>
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ba_chiudi") %></a>
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
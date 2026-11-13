<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.sql.*"
import="java.util.ArrayList"
import="java.util.HashMap"
import="java.util.List"
import="java.util.Map"
import="pkg.beans.Tables"
import="pkg.beans.rfid.DocumentDetail"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Date"
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
		<h3 class="font-thin">P2R Export</h3>
	</div>
	<div class="modal-body">

<%	
	DateFormat dateNum = new SimpleDateFormat("yyyyMMddKKmm");

	// **********************************************************
	// Connessione al DATABASE
	Connection conn = null;
	List<String> doc = new ArrayList<String>();
	doc.clear();
	try
	{
		conn = Utility.getConnRfid();
		
		int iddoc = Integer.parseInt(request.getParameter("iddoc"));
		String doc_ref = request.getParameter("doc_ref");
		
		boolean diff = false;
		
		doc = DocumentDetail.getP2RList(conn, iddoc);
		
		table="<table><caption>"+dateNum.format(new Date())+"_"+doc_ref+"</caption><tbody>";
		
		for(int i=0;i<doc.size();i++) {
			
				table+="<tr>"+
						"<td>"+doc.get(i)+"</td>"+
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
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
	</div>
  </div>
</div>

<div id="table_wrapper2" style="visibility:hidden">
<%=table %>
</div>

<script>
			
	/*
	function doit(type, fn, dl) {
		var elt = document.getElementById('table_wrapper2');
		var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
		return dl ?
			XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
			XLSX.writeFile(wb, fn || ('download.' + (type || 'csv')));
	}
	doit('csv');
	*/
	$("#table_wrapper2").tableToCSV();
	$('#table_wrapper2').hide();
	
	
</script>
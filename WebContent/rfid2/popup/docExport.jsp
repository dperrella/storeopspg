<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.sql.*"
import="java.util.ArrayList"
import="java.util.HashMap"
import="java.util.List"
import="java.util.Map"
import="pkg.beans.rfid.DocumentDetail"
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
	Connection conn = null;
	List<DocumentDetail> doc = new ArrayList<DocumentDetail>();
	doc.clear();
	try
	{
		conn = Utility.getConnRfid();
		
		int iddoc = Integer.parseInt(request.getParameter("iddoc"));
		
		String onlyDiff = ""+request.getParameter("onlydiff");
		String type = ""+request.getParameter("type");
		
		onlyDiff = Utility.noNull(onlyDiff);
		type = Utility.noNull(type);
		
		boolean diff = false;
		if(onlyDiff.equals("true"))
			diff = true;
		
		doc = DocumentDetail.getDocumentsDetailByIdDocument(conn, iddoc, diff, type);
		
		table+= "<table class=\"table table-striped\">"+
				"<thead>"+
				"	<tr>"+
				"		<th>Row</th>"+
				"		<th>SKU/EPC</th>"+
				"		<th>PackageId</th>"+
				"		<th>Code</th>"+
				"		<th>Description</th>"+
				"		<th>Expected</th>"+
				"		<th>Read</th>"+
				"		<th>Differences</th>"+
				"	</tr>"+
				"</thead>"+
				"<tbody>";
		
		int $riga = 0;
		String $sku="";
		String $packageId="";
		String $code="";
		String $description="";
		int $theo=0;
		int $read=0;
		int $diff=0;
		
		for(int i=0;i<doc.size();i++) {
			$riga = i;
			$sku = doc.get(i).getSku()+"<br/>"+doc.get(i).getEpc();
			$packageId = doc.get(i).getPackageId();
			$code = doc.get(i).getCode();
			$description = doc.get(i).getDescription();
			$theo = doc.get(i).getQty_theo();
			$read = doc.get(i).getQty_read();
			$diff = $theo-$read;
			
			table+="<tr>"+
					"<td>"+$riga+"</td>"+
					"<td>"+$sku+"</td>"+
					"<td>"+$packageId+"</td>"+
					"<td>"+$code+"</td>"+
					"<td>"+$description+"</td>"+
					"<td>"+$theo+"</td>"+
					"<td>"+$read+"</td>"+
					"<td>"+$diff+"</td>"+
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
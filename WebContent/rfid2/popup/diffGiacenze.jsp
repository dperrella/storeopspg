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
		<h3 class="font-thin">Differenza Giacenze</h3>
	</div>
	<div class="modal-body">

<%	
//**********************************************************
	// Connessione al DATABASE
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	try
	{
		conn = Utility.getConnRfid();
		st = conn.createStatement();
		sql = "select * from vw_diff_giacenze ";
		
		int iddoc = Integer.parseInt(request.getParameter("iddoc"));
		String doc_ref = request.getParameter("doc_ref");
		
		
		table+= "<table class=\"table table-striped\">"+
				"<thead>"+
				"	<tr>"+
				"		<th>Nodo</th>"+
				"		<th>Prodotto</th>"+
				"		<th>Variante</th>"+
				"		<th>Qta</th>"+
				"		<th>Taglia</th>"+
				"	</tr>"+
				"</thead>"+
				"<tbody>";
		
		rs=st.executeQuery(sql);
		while(rs.next()) {
			table+="<tr>"+
					"<td>"+rs.getString("nodo")+"</td>"+
					"<td>"+rs.getString("prodotto")+"</td>"+
					"<td>"+rs.getString("variante")+"</td>"+
					"<td>"+rs.getString("qta")+"</td>"+
					"<td>"+rs.getString("taglia")+"</td>"+
					"</tr>";	
		}
		rs.close();
		
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
		if(st!=null)
			st.close();
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
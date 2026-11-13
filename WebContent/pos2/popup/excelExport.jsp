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
String table = "";
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
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	
	try
	{
		conn = Utility.getConnPos();
		st = conn.createStatement();
		String store = request.getParameter("store");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		sql = "SELECT * from public.venduto_x_store('"+from+"', '"+to+"', '"+store+"')";
		rs = st.executeQuery(sql);
		
		table+="<table><tr>"
				+"<th>Store</th><th>Date</th><th>Product</th><th>Brand</th><th>Code</th><th>Cod. Art.</th><th>Progressive</th><th>Size</th><th>Colour</th><th>Season</th><th>Pieces</th><th>Prices</th>"
				+"</tr><tbody>";
		while(rs.next()) {
			table+="<tr>"
					+"<td>"+rs.getString("descrizione")+"</td>"
					+"<td>"+rs.getString("datenew")+"</td>"
					+"<td>"+rs.getString("name")+"</td>"
					+"<td>"+rs.getString("brand")+"</td>"
					+"<td>"+rs.getString("code")+"</td>"
					+"<td>"+rs.getString("codart")+"</td>"
					+"<td>"+rs.getString("progressivo")+"</td>"
					+"<td>"+rs.getString("taglia")+"</td>"
					+"<td>"+rs.getString("codcol")+"</td>"
					+"<td>"+rs.getString("stagione")+"</td>"
					+"<td>"+rs.getString("pezzi")+"</td>"
					+"<td>"+rs.getString("prezzo")+"</td></tr>";
					
		}
		rs.close();
%>
			<div class="alert alert-success">
              <i class="icon-check"></i> <strong><%=propLang.get("msgSuccess") %></strong>.
            </div>
				
<%
	}
	catch(Exception e) {
		e.printStackTrace();
		
%>
			<div class="alert alert-danger">
              <i class="icon-check"></i> <strong><%=e.getMessage() %></strong>.
            </div>
<%
	}
	finally {
		try {
			st.close();
			conn.close();
		}
		catch(Exception e) {}
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
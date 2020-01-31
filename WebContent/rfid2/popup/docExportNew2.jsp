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
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	try
	{
		conn = Utility.getConnRfid();
		st = conn.createStatement();
		
		// Intestazioni
		sql = "select * from mv_sizeplan";
		rs = st.executeQuery(sql);
		
		int iddoc = Integer.parseInt(request.getParameter("iddoc"));
		
		table+= "<table class=\"table table-striped\">"+
				"<thead>"+
				"	<tr>"+
				"		<th>Row</th>"+
				"		<th>Products</th>"+
				"		<th>Type</th>";
		while(rs.next()) {
			table+= "		<th>"+rs.getString("string_agg")+"</th>";
		}
		rs.close();
		
		table+=	"	</tr>"+
				"</thead>"+
				"<tbody>";
		
		int $riga = 0;
		String $product="";
		
		sql = "select "
			+"(select description from products where (code||'-'||variant||'-'||colourcode) = a.prodotto limit 1) description, a.* "
			+"from grid_doc2("+iddoc+") a ";
		rs=st.executeQuery(sql);
		while(rs.next()) {
			
			
			
			table+="<tr>";
				
			if(!$product.equalsIgnoreCase(rs.getString("description"))) {
				$riga++;
				table+= "<td>"+$riga+"</td><td>"+rs.getString("description")+"</td>";
			}
			else {
				table+= "<td></td><td></td>";
			}
					
			table+= ""+
					"<td>"+rs.getString("tipo")+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s1"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s2"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s3"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s4"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s5"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s6"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s7"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s8"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s9"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s10"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s11"))+"</td>"+
					"<td>"+Utility.noNull(rs.getString("s12"))+"</td>"+
					"</tr>";	
					
			$product = rs.getString("description");
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
%>
			<div class="alert alert-danger">
              <i class="fa fa-warning"></i> <strong><%=e.getMessage() %></strong>.
            </div>
<%
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
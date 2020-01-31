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
		
		String idsite = request.getParameter("idsite");
		String code = ""+request.getParameter("code");
		code=Utility.noNull(code);
		String category = ""+request.getParameter("category");
		category = Utility.noNull(category);
		String season = ""+request.getParameter("season");
		season = Utility.noNull(season);
		String itemcode = ""+request.getParameter("itemcode");
		itemcode = Utility.noNull(itemcode);
		String colourcode = ""+request.getParameter("colourcode");
		colourcode = Utility.noNull(colourcode);
		String size = ""+request.getParameter("size");
		size = Utility.noNull(size);
		String location = ""+request.getParameter("location");
		location = Utility.noNull(location);
		
		String idcus = session.getAttribute("idcus").toString();
		
		String cond2= "";
		
		if(code.length()>0)
			cond2+= " and p.code = '"+code+"' ";
		if(idsite.length()>0)
			cond2+= " and s.id_site = "+idsite+" ";
		
		if(category.length()>0)
			cond2+=" and category = '"+category+"' ";
		if(season.length()>0)
			cond2+=" and season = '"+season+"' ";
		if(itemcode.length()>0)
			cond2+=" and itemcode = '"+itemcode+"' ";
		if(colourcode.length()>0)
			cond2+=" and colourcode = '"+colourcode+"' ";
		if(size.length()>0)
			cond2+=" and size = '"+size+"' ";
		if(location.length()>0)
			cond2+=" and l.id = "+location+" ";
		
		
		table+= "<table class=\"table table-striped\">"+
				"<thead>"+
				"	<tr>"+
				"		<th>Row</th>"+
				"		<th>Code</th>"+
				"		<th>Name</th>"+
				"		<th>Color</th>"+
				"		<th>Size</th>"+
				"		<th>Location</th>"+
				"		<th>Qty</th>"+
				"	</tr>"+
				"</thead>"+
				"<tbody>";
		
		
		sql = "select code, p.description as name, colordesc, size, sizecod, l.id idloc, l.description dsloc, sum(units) qty\n" + 
				"from products p \n" + 
				"left join stockdiary s on p.sku = s.barcode\n" + 
				"left join location l on l.id = s.id_location\n" + 
				"where p.id_customer = "+idcus+" and id_site = "+idsite+"\n" + 
				" "+cond2+" "+
				"group by code, p.description, colordesc, size, sizecod, l.id, l.description\n" + 
				" ";
		rs=st.executeQuery(sql);
		
		int $riga = 0;
		code="";
		String name="";
		String color="";
		size="";
		int qty=0;
		
		
		while(rs.next()) {
			
			$riga = rs.getRow();
			code = rs.getString("code");
			name = rs.getString("name");
			color = rs.getString("colordesc");
			size = rs.getString("size");
			
			qty = rs.getInt("qty");
			
			
			
			table+="<tr>"+
					"<td>"+$riga+"</td>"+
					"<td>"+code+"</td>"+
					"<td>"+name+"</td>"+
					"<td>"+color+"</td>"+
					"<td>"+size+"</td>"+
					"<td>"+rs.getString("dsloc")+"</td>"+
					"<td>"+qty+"</td>"+
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
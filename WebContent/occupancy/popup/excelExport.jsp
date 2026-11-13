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
		String accountId = request.getParameter("accountId");
		String store = request.getParameter("store");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			conn = Utility.getConnDispatcher();
			sql = "select * from occupancy.received_data('"+accountId+"','"+store+"','"+from+"','"+to+"') ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			
			int $riga = 0;
			
			while(rs.next()) {
				$riga+=1;
				
				table+="<tr>"
						+"<td>"+$riga+"</td>"
						+"<td>"+Utility.noNull(rs.getString("storename"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("device"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("tstamp"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("areaid"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("areaname"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("in"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("out"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("occupancy"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("threshold"))+"</td>"
						+"<td>"+Utility.noNull(rs.getString("perc_warning"))+"</td>"
						+"</tr>";
						
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
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
<table>
	<tr>
		<th style="width:5%">#</th>
		<th style="width:20%">Store Name</th>
		<th style="width:10%">Device</th>
		<th style="width:10%">Time Stamp</th>
		<th style="width:5%">Area Id</th>
		<th style="width:10%">Area Name</th>
		<th style="width:5%">In</th>
		<th style="width:5%">Out</th>
		<th style="width:5%">Occupancy</th>
		<th style="width:5%">Threshold</th>
		<th style="width:5%">Perc Warning</th>
	</tr>
	<tbody>
	<%=table %>
	</tbody>
</table>
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
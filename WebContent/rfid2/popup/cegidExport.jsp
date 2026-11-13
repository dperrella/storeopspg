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
String type=request.getParameter("type");
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Cegid Export <%=type %></h3>
	</div>
	<div class="modal-body">

<%	
	DateFormat dateNum = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	DateFormat dateNumIso = new SimpleDateFormat("yyyyMMdd");

	// **********************************************************
	// Connessione al DATABASE
	Connection conn = null;
	List<Tables> doc = new ArrayList<Tables>();
	doc.clear();
	try
	{
		conn = Utility.getConnRfid();
		
		int iddoc = Integer.parseInt(request.getParameter("iddoc"));
		String doc_ref = request.getParameter("doc_ref");
		
		boolean diff = false;
		
		doc = DocumentDetail.getCegidList(conn, iddoc, type);
		String codiceNegozio = "";
		for(int i=0;i<doc.size();i++) {
			codiceNegozio = doc.get(i).getXcdel();
			break;
		}
		
		table="<table><caption>"+type+"_"+dateNum.format(new Date())+"_"+codiceNegozio+"</caption><tbody>";
		
		for(int i=0;i<doc.size();i++) {
			
			switch(type) {
			case "SER":
				table+="<tr>"+
						"<td>SERC1 </td>"+
						"<td>ALF</td>"+
						"<td>"+doc.get(i).getXcdtb()+"</td>"+
						"<td>"+doc.get(i).getXdtab()+"</td>"+
						"<td>"+doc.get(i).getCmp1()+"</td>"+
						"</tr>";	
				break;
			case "INV":
				table+="<tr>"+
						"<td>INVC1 </td>"+
						"<td>"+doc.get(i).getCmp2()+"</td>"+
						"<td>"+doc.get(i).getXdtab()+"</td>"+
						"<td>"+doc.get(i).getCmp1()+"</td>"+
						"</tr>";	
				break;
			case "R30":
				table+="<tr>"+
						"<td>DDTC1 </td>"+
						"<td>"+doc.get(i).getXcdel()+"</td>"+
						"<td>"+doc.get(i).getCmp2()+"</td>"+
						"<td>"+doc.get(i).getXdtab()+"</td>"+
						"<td>1</td>"+
						"<td>VELASCA</td>"+
						"<td>"+dateNumIso.format(new Date())+"</td>"+
						"<td>"+doc.get(i).getXcdtb()+"</td>"+
						"<td>EUR</td>"+
						"<td></td>"+
						"<td>"+doc.get(i).getCmp1()+"</td>"+
						"</tr>";	
				break;
			case "R31":
				table+="<tr>"+
						"<td>RIFC1 </td>"+
						"<td>"+doc.get(i).getXcdel()+"</td>"+
						"<td>"+doc.get(i).getCmp2()+"</td>"+
						"<td>"+doc.get(i).getXdtab()+"</td>"+
						"<td>1</td>"+
						"<td>VELASCA</td>"+
						"<td>"+dateNumIso.format(new Date())+"</td>"+
						"<td>"+doc.get(i).getXcdtb()+"</td>"+
						"<td>EUR</td>"+
						"<td></td>"+
						"<td>"+doc.get(i).getCmp1()+"</td>"+
						"</tr>";	
				break;
			case "R32":
				table+="<tr>"+
						"<td>RESC1 </td>"+
						"<td>"+doc.get(i).getXcdel()+"</td>"+
						"<td>"+doc.get(i).getCmp2()+"</td>"+
						"<td>"+doc.get(i).getXdtab()+"</td>"+
						"<td>1</td>"+
						"<td>VELASCA</td>"+
						"<td>"+dateNumIso.format(new Date())+"</td>"+
						"<td>"+doc.get(i).getXcdtb()+"</td>"+
						"<td>EUR</td>"+
						"<td></td>"+
						"<td>"+doc.get(i).getCmp1()+"</td>"+
						"</tr>";	
				break;
			case "R10":
				
				break;
			}
			
			
		}
		
		if(type.equals("R10")) {
			
			table+="<tr>"+
					"<td>TREC1 </td>"+
					"<td>NM002</td>"+
					"<td>NM0022</td>"+
					"<td>0044410</td>"+
					"<td>FD000100A13A67B51</td>"+
					"<td>20210615</td>"+
					"<td>"+iddoc+"</td>"+
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
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.sql.*"
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
		conn = Utility.getConnData();
		st=conn.createStatement();
		
		String kpi = request.getParameter("kpi");
		String dtda = request.getParameter("dtda");
		String dtal = request.getParameter("dtal");
		
		dtda = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
		dtal = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
		
		String account_code = request.getParameter("account_code");
		String id_customer = request.getParameter("id_customer");
		
		String idstore = ""+request.getParameter("idstore");
		idstore=Utility.noNull(idstore);
		if(idstore.length()==0)
			idstore = "null";
		else 
			idstore = "'"+idstore+"'";
		
		String hour = ""+request.getParameter("hour");
		hour=Utility.noNull(hour);
		if(hour.length()==0)
			hour = "false";
		
		
		sql  =  "select a.*, s.store_name, d.door_description from raw_data_export( "+
				"	'"+account_code+"', "+
				"	'"+kpi+"', "+
				"	'"+dtda+"', "+
				"	'"+dtal+"', "+
				"   "+idstore+" "+
				") a " + 
				"left join stores s on _account_id = account_id and a.store_id = s.store_id "+
				"left join doors d on _account_id = d.account_id and a._doornumber = d.door_type_id ";


		
		System.out.println("**** "+sql);
		rs=st.executeQuery(sql);
		
		table+= "<table class=\"table table-striped\">"+
				"<thead>"+
				"	<tr>"+
				"		<th>Row</th>"+
				"		<th>AccountID</th>"+
				"		<th>StoreID</th>"+
				"		<th>DoorID</th>"+
				"		<th>Store</th>"+
				"		<th>Door</th>"+
				"		<th>Event</th>"+
				"		<th>Event Time</th>"+
				"	</tr>"+
				"</thead>"+
				"<tbody>";
		
		int $riga = 0;
		String $storeid="";
		String $doorid="";
		String $accountid="";
		String $store_name="";
		String $eventid="";
		String $eventtime="";
		String $door="";
		while(rs.next()) {
			
			$riga = rs.getRow();
			$storeid = rs.getString("store_id");
			$doorid = rs.getString("_doornumber");
			$accountid = rs.getString("_account_id");
			$store_name = rs.getString("store_name");
			$eventtime = rs.getString("_event_time_stamp");
			$eventid = rs.getString("_eventid");
			$door = ""+rs.getString("door_description");
			$door = Utility.noNull($door);
			if($door.length()==0)
				$door = "-";
			
			table+="<tr>"+
				"<td>"+$riga+"</td>"+
				"<td>"+$accountid+"</td>"+
				"<td>"+$storeid+"</td>"+
				"<td>"+$doorid+"</td>"+
				"<td>"+$store_name+"</td>"+
				"<td>"+$door+"</td>"+
				"<td>"+$eventid+"</td>"+
				"<td>"+$eventtime+"</td>"+
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
	
	
	
	/*
	var data_type = 'data:application/vnd.ms-excel';
       var table_div = document.getElementById('table_wrapper2');//table_wrapper is table id
       var table_html = table_div.outerHTML.replace(/ /g, '%20');

       var a = document.createElement('a');
       a.href = data_type + ', ' + table_html;
       a.download = 'table_name_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
       a.click();
       
       $('#table_wrapper2').hide();
   	*/
   

</script>
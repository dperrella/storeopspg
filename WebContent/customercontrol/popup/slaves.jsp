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

String username = (String) session.getAttribute("username");
String iddevice = ""+request.getParameter("iddevice");
iddevice=iddevice.replaceAll("\\?cd=undefined", "");

String name = ""+request.getParameter("name");
name=name.replaceAll("\\?cd=undefined", "");

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnData();
	st = conn.createStatement();
		
	
%>

<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin">Slave Device List <b><%=name %></b></h3>
	</div>
	<div class="modal-body">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th>Serial</th>
			  <th>Device Name</th>
			  <th>Device Type</th>
			  <th>Device Location</th>
			  <th>Firmware Version</th>
			  <th colspan="2">Status</th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%

//Device Slaves
	sql = "select deviceslaves.*, error_type.description errdesc, deviceslavestype.description typedesc from deviceslaves "+
		"left join error_type on eventcode = errorcode "+
		"left join deviceslavestype on deviceslaves.device_type_id = deviceslavestype.id "+
		"where id_device = "+iddevice+" order by door_description, device_type_id ";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	
	String enab = "";
	String eventcode = "";
	String provv = "";
	String errdesc = "";
	
	while(rs.next()) {
		enab = rs.getString("enabled");
		provv = ""+rs.getString("provissorio");
		provv = Utility.noNull(provv);
		eventcode = ""+rs.getString("eventcode");
		eventcode = Utility.noNull(eventcode);

		if(enab.equals("t") && provv.equals("t")) {
			errdesc =rs.getString("errdesc");
			enab = "<i class=\"text-warning icon-check\"></i>";
		}else if(enab.equals("t") && (provv.equals("f") || "".equals(provv))){
			
			if(eventcode.trim().length()==0||eventcode.trim().equalsIgnoreCase("OK")) {
				enab = "<i class=\"text-success icon-check\"></i>";
			}
			else {
				errdesc =rs.getString("errdesc");
				enab = "<i class=\"text-danger icon-ban\"></i>";
			}
			
			
		}else {
			enab = "<i class=\"text-success icon-check\"></i>";
		}
		
		String firmware_version = Utility.noNull(rs.getString("firmware_version"));
		if(firmware_version.length()==0)
			firmware_version = "-";
%>
		<tr>
			  <td><span class="label bg-light"><%=rs.getString("id_slave") %></span></td>
			  <td><%=rs.getString("slavename") %></td>
			  <td><%=rs.getString("typedesc") %></td>
			  <td><%=rs.getString("door_description") %></td>
			  <td><%=firmware_version %></td>
			  <td><span class="label bg-light"><%=eventcode %></span><br/><%=errdesc %></td>
			  <td><%=enab %></td>
		</tr>
<%
		errdesc = "";
	}
	rs.close();

%>
		
	  	</tbody>
	  </table>
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Close</a>
	</div>
  </div>
</div>
<%
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

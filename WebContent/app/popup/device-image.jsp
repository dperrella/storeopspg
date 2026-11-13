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

String iddevice = request.getParameter("iddevice").replaceAll("\\?cd=undefined", "");
String url = request.getParameter("url").replaceAll("\\?cd=undefined", "");

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;

conn = Utility.getConnData();
st=conn.createStatement();
rs=st.executeQuery("select * from device_images where iddevice = "+iddevice+" and url = '"+url+"' ");
String note = "";
if(rs.next()) {
	note = rs.getString("note");
}
rs.close();
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Device Images</h3>
	</div>
	<div class="modal-body">
		
		<img src="../../<%=url%>" style="width:100%"/>
		<br/>
		<br/>
		<b>Note:</b><br/>
		<textarea style="height:100px" class="form-control" id="modalnote"><%=note %></textarea>
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-danger" style="float:left"  onclick="confirmDelete('<%=iddevice %>','<%=url %>');">Delete Image</a>
		<a class="btn btn-primary btn-lg"  onclick="update('<%=iddevice %>','<%=url %>', $('#modalnote').val());">Update Note</a>
	</div>
  </div>
</div>
<%
st.close();
conn.close();
%>

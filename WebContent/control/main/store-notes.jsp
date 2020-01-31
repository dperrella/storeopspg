<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
String id = request.getParameter("id");
id = Utility.noNull(id);
id=id.replaceAll("\\?cd=undefined","");

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";

String note = "";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnData();
	st = conn.createStatement();
	sql  =  "select notes from f_stores where id = "+id+" ";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	
	if(rs.next()) {
		
		note = Utility.noNull(rs.getString("notes"));
		
	}
	rs.close();
	
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Store Notes</h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Note</label>
	          <input type="hidden" name="id" id="id" value="<%=id%> ">
	          <textarea id="notes" name="notes" class="form-control" style="height:200px"><%=note %></textarea>   
	        </div>
	    </div>
	   
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-primary btn-lg" data-dismiss="modal" onclick="saveNote('<%=id%>')"><%=propLang.get("save") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

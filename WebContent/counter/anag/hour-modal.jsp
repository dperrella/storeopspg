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

String id_store = request.getParameter("id_store");
id_store = Utility.noNull(id_store);

Connection conn = null;
Statement st = null;
ResultSet rs = null;

String sql = "";

conn = Utility.getConnControl();
st=conn.createStatement();
sql   = "select o.* from dailyop_template t , optempl o,  stores s "+
		"where t.id_customer::integer = s.id_customer "+
		"and o.id = t.id_optempl "+
		"and s.id = "+id_store+" "+
		"group by o.id, o.description, o.shortdescr ";
rs=st.executeQuery(sql);
%>

<div class="modal-dialog modal-sm">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Template Off Time</h3>
	</div>
	<div class="modal-body">
		
		<input type="hidden" name="id_store" id="id_store" value="<%=id_store %>"/>
		
		<label>Template Off Time</label><br/>
		<select class="form-control" id="id_optempl" name="id_optempl">
<%
while(rs.next()) {
%>
			<option value="<%=rs.getString("id")%>"><%=rs.getString("shortdescr") %></option>
<%
}
rs.close();
%>
		</select>
			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setTemplate(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("btnConferma") %></a>
	</div>
  </div>
</div>
<%
if(st!=null) 
	st.close();
if(conn!=null) 
	conn.close();
%>

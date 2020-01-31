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

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";
int count = 0;

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnData();
	st = conn.createStatement();
		
	sql   = "select xcdel, xdtab "+
			"from ztables a  "+
			"where xcdtb = 'COM' ";
	rs=st.executeQuery(sql);
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("pop_us_sel") %> Comparison Type</h3>
	</div>
	<div class="modal-body">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th></th>
			  <th><%=propLang.get("ala_code") %></th>
			  <th><%=propLang.get("tab_xdtab") %></th>
			  
			</tr>
		  </thead>
		  <tbody>
<%
String chk = "";
while (rs.next())
{
%>
			<tr>
			  <td>
				  <div class="radio i-checks" style="margin-top:5px;margin-bottom:5px">
	                  <label>
	                    <input name="chkComparison" type="radio" value="<%=rs.getString("xcdel") %>,<%=rs.getString("xdtab").trim() %>">
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("xcdel") %></span></td>
			  <td><%=rs.getString("xdtab") %></td>
			</tr>
<%
}
rs.close();
%>

			<tr>
			  <td>
				  <div class="radio i-checks" style="margin-top:5px;margin-bottom:5px">
	                  <label>
	                    <input name="chkAdvancedComparison" type="radio" value="advancedComparison" onclick="advancedComparison()">
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light">ADV</span></td>
			  <td>ADVANCED COMPARISON</td>
			</tr>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-default" onclick="saveComparison()"><%=propLang.get("pop_default") %></a>
		<a class="btn btn-primary btn-lg" onclick="getComparison('GO')"><%=propLang.get("pop_run") %></a>	
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

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

String id_trigger = request.getParameter("id_trigger");
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Relate Trigger to Customer/Stores</h3>
	</div>
	<div class="modal-body">
<%

//**********************************************************
//Parametri per SQL
Connection conn = null;
Connection connAdmin = null;
Statement st = null;
Statement stAdmin = null;
ResultSet rs = null;
	
String sql = "";
String opt = "<option value=\"*\">-</option>";

//**********************************************************
//Connessione al DATABASE
try
{
	conn=Utility.getConnData();
	connAdmin=Utility.getConnAdmin();
	
	st = conn.createStatement();
	stAdmin = connAdmin.createStatement();
	
	// Customers
	sql  =  "select * , '"+(String)session.getAttribute("username")+"' as user from customers a where "
			+ "id in(select idcustomer from rel_user_customers "
			+ "where idusr = '"+(String)session.getAttribute("username")+"') "
			+ "union all "
			+ "select * from customerscountry b where idusr = '"+(String)session.getAttribute("username")+"' ";
	rs=stAdmin.executeQuery(sql);
	while(rs.next()) {
		opt+="<option value=\""+rs.getString("id")+"\">"+rs.getString("account_code")+" - "+rs.getString("description")+"</option>";
	}
	rs.close();
	
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
		if(stAdmin!=null)
			stAdmin.close();
		if(connAdmin!=null)
			connAdmin.close();
	}
	catch(Exception e) {}
}
%>

		
		
		
		<label>ID Trigger</label>
		<input type="text" id="id_trigger" name="id_trigger" class="form-control" value="<%=id_trigger %>" readonly/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label><%=propLang.get("sel_cust") %></label>
		<select class="form-control chosen-select" name="idcus" id="idcus" onchange="readStores(this.value)">
			<%=opt %>
		</select>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label><%=propLang.get("sel_stores") %></label>
		<select class="form-control chosen-select" multiple name="idsto" id="idsto">
			<!-- <option value="*"><%=propLang.get("sel_all") %></option>-->
		</select>
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="reloadChosen();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("later") %></a>
		<a class="btn btn-primary btn-lg" onclick="saveRel()"><%=propLang.get("save") %></a>	
	</div>
  </div>
</div>

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
	conn=Utility.getConnAdmin();
	st = conn.createStatement();
		
	sql  =  "select account_code, account_code_evolvestore, description from customers where "
			+ "id in(select idcustomer from rel_user_customers "
			+ "where upper(idusr) = '"+(String)session.getAttribute("username")+"') ";
	System.out.println(sql);
	
	st = conn.createStatement();
	// Esegue l'sql
	rs=st.executeQuery(sql);
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("pop_us_sel") %> Customer</h3>
	</div>
	<div class="modal-body">
		
		<select id="idcus_modal" name="idcus" title="Select Customer" class="selectpicker remove-example" data-live-search="true" style="background: #fff; min-width:100%; width:100%; border: 1px;">
<%
	String cus_id = "";
	String cus_id2 = "";
	while(rs.next()) {
		cus_id = rs.getString("account_code");
		cus_id2= Utility.noNull(rs.getString("account_code_evolvestore"));
		//if(cus_id2.length()>0)
		//	cus_id = cus_id2;
%>
		<option value="<%=cus_id%>"><%=cus_id+" "+rs.getString("description")%></option>
<%
	}
	rs.close();

%>
		</select>   
		<div id="customers" name="customers" style="background: #eee; min-width:100%; width:100%; border: 1px;"></div>     
      
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="setSelectPicker()"/>
				<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-primary btn-lg" onclick="selectCustomer('idcus_modal')"><i class="icon-user" aria-hidden="true"></i>&nbsp;<i class="fa fa-exchange" aria-hidden="true"></i> Change</a>	
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

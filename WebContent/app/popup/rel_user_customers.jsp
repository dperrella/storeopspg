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

String tipoForm = request.getParameter("tipoForm");
tipoForm = Utility.noNull(tipoForm);

String idusr = request.getParameter("idusr");
idusr = Utility.noNull(idusr);

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";
int count = 0;

String arrayCustomers = (String) session.getAttribute("arrayCustomers");
if(arrayCustomers.length()==0)
	arrayCustomers = "0";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnAdmin();
	st = conn.createStatement();
	
	String role = (String) session.getAttribute("tipo").toString().trim();
	String arrayCus = (String) session.getAttribute("arrayCustomers");
	
	if(tipoForm.equals("I")) {
		if(role.equals("2"))
			sql   = "select id, description, brandname, account_code, 0 as idcustomer from customers ";
		else 
			sql   = "select id, description, brandname, account_code, 0 as idcustomer from customers "+
					"where id in("+arrayCus+")";
	}
	else {
		sql   = "select a.id, a.description, a.brandname, account_code, category1 as idcustomer "+
				"from customers a "+
				"left join rel_user_category b on a.account_code = b.category1 and b.idusr = '"+idusr+"' "+
				"where id in("+arrayCus+")"+
				"group by a.id, a.description, a.brandname, account_code, category1 ";
		
	}
	System.out.println(sql);
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
		<h3 class="font-thin"><%=propLang.get("uc_dsfun") %></h3>
	</div>
	<div class="modal-body">
		
		<div style="overflow:auto; height:275px;">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_us_filtra") %>">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th>
			  	<div class="checkbox i-checks">
                  <label>
                    <input name="chk" type="checkbox" value="">
                    <i></i>
                  </label>
                </div>
			  </th>
			  <th><%=propLang.get("uc_id") %></th>
			  <th><%=propLang.get("uc_description") %></th>
			  <th><%=propLang.get("uc_brandname") %></th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
String chk = "";
while (rs.next())
{
	if(rs.getString("account_code").equals(rs.getString("idcustomer"))) {
		chk = "checked";
	}
%>
			<tr>
			  <td>
				  <div class="checkbox i-checks">
	                  <label>
	                    <input name="chkCustomer" type="checkbox" value="<%=rs.getString("account_code") %>" <%=chk %>>
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("account_code") %></span></td>
			  <td><%=rs.getString("description") %></td>
			  <td><%=rs.getString("brandname") %></td>
<%
	chk = "";
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setRelUserCustomers(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("uc_sel") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

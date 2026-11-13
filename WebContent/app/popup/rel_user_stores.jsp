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

String customers = request.getParameter("customers");
customers = Utility.noNull(customers);



//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";
int count = 0;

String arrayStores = (String) session.getAttribute("arrayStores");
if(arrayStores.length()==0)
	arrayStores = "0";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnAdmin();
	st = conn.createStatement();
	
	if(customers.length()==0)
		customers = "'0'";
	else {
		String temp[]=customers.split(",");
		customers = "";
		for(int i=0;i<temp.length;i++) {
			customers+="'"+temp[i]+"',";
		}
		if(customers.length()>0)
			customers=customers.substring(0,customers.length()-1);
	}
	
	String role = (String) session.getAttribute("tipo").toString().trim();
	String arraySto = (String) session.getAttribute("arrayStores");
	
	
	if(tipoForm.equals("I")) {
		if(role.equals("2"))
			sql   = "select a.id, a.description, c.brandname, a.store_code, 0 as idstore, c.account_code as id_customer, a.country "+
					"from f_stores a, customers c where a.id_customer = c.id and c.account_code in("+customers+") order by c.brandname";
		else
			sql   = "select a.id, a.description, c.brandname, a.store_code, 0 as idstore, c.account_code as id_customer, a.country "+
					"from f_stores a, customers c "+
					"where a.id_customer = c.id and c.account_code in("+customers+") "+
					"and a.id in("+arrayStores+") "+
					"order by c.brandname";
	}
	else {
		if(role.equals("2"))
			sql   = "select a.id, a.description, c.brandname,  a.store_code, category3 as idstore, c.account_code as id_customer, a.country "+
					"from customers c, f_stores a "+
					"left join rel_user_category b on a.store_code::text = b.category3 and b.idusr = '"+idusr+"' "+
					"where a.id_customer = c.id and c.account_code in("+customers+") "+
					"order by c.brandname";
		else
			sql   = "select a.id, a.description, c.brandname,  a.store_code, category3 as idstore, c.account_code as id_customer, a.country "+
					"from customers c, f_stores a "+
					"left join rel_user_category b on a.store_code::text = b.category3 and b.idusr = '"+idusr+"' "+
					"where a.id_customer = c.id and c.account_code in("+customers+") "+
					"and a.id in("+arrayStores+") "+
					"order by c.brandname";
	}
	System.out.println(sql);
	rs=st.executeQuery(sql);
%>		

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("us_dsfun") %></h3>
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
			  <th><%=propLang.get("us_id") %></th>
			  <th><%=propLang.get("us_description") %></th>
			  <th><%=propLang.get("us_brandname") %></th>
			  
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
String chk = "";
String store_code = "";
while (rs.next())
{
	store_code = ""+rs.getString("store_code");
	if(store_code.equals(rs.getString("idstore"))) {
		chk = "checked";
	}
%>
			<tr>
			  <td>
				  <div class="checkbox i-checks">
	                  <label>
	                    <input name="chkStore" type="checkbox" value="<%=rs.getString("id_customer") %>;<%=rs.getString("country") %>;<%=rs.getString("store_code") %>" <%=chk %>>
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("store_code") %></span></td>
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
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setRelUserStores(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("uc_sel") %></a>
	</div>
  </div>
</div>
<%
}
catch(Exception e) {
	e.printStackTrace();
}
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

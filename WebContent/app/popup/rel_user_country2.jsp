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

String idusr = request.getParameter("idusr");
idusr = Utility.noNull(idusr);

String customers = request.getParameter("customers");
customers = Utility.noNull(customers);

String tipoForm = request.getParameter("tipoForm");
tipoForm = Utility.noNull(tipoForm);


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

	String role = (String) session.getAttribute("tipo").toString().trim();
	String arrayCou = (String) session.getAttribute("arrayCountry");
	
	System.out.println(arrayCou);
	
	if(tipoForm.equals("I")) {
		if(role.equals("2"))
			sql   = "select a.id_naz, a.nome_nazione, 0 as category2 "+
					"from nazioni a ";
		else
			sql   = "select a.id_naz, a.nome_nazione, 0 as category2 "+
					"from nazioni a "+
					"where a.id_naz in("+arrayCou+") ";
	}
	else {
		if(role.equals("2"))
			sql   = "select a.id_naz::text, a.nome_nazione, b.category2 "+
					"from nazioni a "+
					"left join rel_user_category b on a.id_naz::text = b.category2 and b.idusr = '"+idusr+"' "+
					"group by id_naz, nome_nazione, category2 ";
		else
			sql   = "select a.id_naz::text, a.nome_nazione, b.category2 "+
					"from nazioni a "+
					"left join rel_user_category b on a.id_naz::text = b.category2 and b.idusr = '"+idusr+"' "+
					"where a.id_naz in("+arrayCou+") "+
					"group by id_naz, nome_nazione, category2 ";
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
			  <th>ID</th>
			  <th>Country</th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
String chk = "";
while (rs.next())
{
	if(rs.getString("id_naz").equals(rs.getString("category2"))) {
		chk = "checked";
	}
%>
			<tr>
			  <td>
				  <div class="checkbox i-checks">
	                  <label>
	                    <input name="chkCountry" type="checkbox" value="<%=rs.getString("id_naz") %>" <%=chk %>>
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("id_naz") %></span></td>
			  <td><%=rs.getString("nome_nazione") %></td>
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
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setRelUserCountry2(); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("uc_sel") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

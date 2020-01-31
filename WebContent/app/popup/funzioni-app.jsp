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

String cdapp = request.getParameter("cdapp");
cdapp = Utility.noNull(cdapp);

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
	conn=Utility.getConnAdmin();
	st = conn.createStatement();
	
	if(tipoForm.equals("I")) {
		sql   = "select cdfun, descr, '' as cdfun_aut from zfunzioni where cdapp = '"+cdapp+"' ";
	}
	else {
		sql   = "select a.cdfun, a.descr, b.cdfun as cdfun_aut "+
				"from zfunzioni a "+
				"left join zutefunz b on a.cdfun = b.cdfun and b.idusr = '"+idusr+"' "+
				"where cdapp = '"+cdapp+"' ";
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
			  <th><%=propLang.get("fun_dsfun") %></th>
			  <th><%=propLang.get("fun_descrizione") %></th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
String chk = "";
while (rs.next())
{
	if(rs.getString("cdfun").equals(rs.getString("cdfun_aut"))) {
		chk = "checked";
	}
%>
			<tr>
			  <td>
				  <div class="checkbox i-checks">
	                  <label>
	                    <input name="chkFun" type="checkbox" value="<%=rs.getString("cdfun") %>" <%=chk %>>
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("cdfun") %></span></td>
			  <td><%=rs.getString("descr") %></td>
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
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="setFunzioni('<%=cdapp %>'); $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("uc_sel") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

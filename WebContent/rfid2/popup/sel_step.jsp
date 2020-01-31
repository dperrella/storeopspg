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
	conn=Utility.getConnRfid();
	st = conn.createStatement();
		
	sql   = "select s.idstep, s.description, coalesce( c.step , '') step "+
			"from f_step s "+
			"left join pref_user_step c on trim(c.step) = idstep::text "+
			"and c.idusr = '"+session.getAttribute("username")+"' and c.idcus = "+session.getAttribute("idcus")+" "+
			"where idflow in (select idflow from flow where id_customer = "+session.getAttribute("idcus")+") "+
			"order by s.idstep ";
	
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
		<h3 class="font-thin"><%=propLang.get("pop_us_sel") %> STEP</h3>
	</div>
	<div class="modal-body">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_em_filtra") %>">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th>
			  	<div class="checkbox i-checks" style="margin-top:5px;margin-bottom:5px">
                  <label>
                    <input name="chk" type="checkbox" value="">
                    <i></i>
                  </label>
                </div>
			  </th>
			  <th><%=propLang.get("ala_code") %></th>
			  <th><%=propLang.get("tab_xdtab") %></th>
			  
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
String chk = "";
while (rs.next())
{
	
	if(rs.getString("idstep").equals(rs.getString("step"))) {
		chk = "checked";
	}
	count+=1;
%>
			<tr>
			  <td>
				  <div class="checkbox i-checks" style="margin-top:5px;margin-bottom:5px">
	                  <label>
	                    <input class="step" name="chkStep" type="checkbox" value="<%=rs.getString("idstep").trim() %>" <%=chk %>>
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("idstep") %></span></td>
			  <td><%=rs.getString("description") %></td>
			</tr>
<%
	chk = "";
}
rs.close();
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="$('[data-toggle=popover]').popover();jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-default" onclick="saveStep()"><%=propLang.get("pop_default") %></a>
		<a class="btn btn-primary btn-lg" onclick="getStep('GO')"><%=propLang.get("pop_run") %></a>	
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

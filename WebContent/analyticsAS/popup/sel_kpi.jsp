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
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("pop_us_sel") %> KPI</h3>
	</div>
	<div class="modal-body">
	
		<%
		String is_admin = session.getAttribute("is_admin").toString();
		if(is_admin.equalsIgnoreCase("S")) {
		%>
		<div class="subnav">
			<ul class="nav nav-pills">
				<li><a href="#" onclick="$('#_cuskpi').show();$('#_datkpi').hide();">View Customer KPI</a></li>
				<li><a href="#" onclick="$('#_cuskpi').hide();$('#_datkpi').show();">View KPI with Data</a></li>
			</ul>
		</div>
		
		<br/>
		<%
		}
		%>
		
		<div id="_cuskpi">
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_em_filtra") %>">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th style="width:10%">
			  	<div class="checkbox i-checks" style="margin-top:5px;margin-bottom:5px">
                  <label>
                    <input name="chk" type="checkbox" value="">
                    <i></i>
                  </label>
                </div>
			  </th>
			  <th style="width:10%"><%=propLang.get("ala_code") %></th>
			  <th><%=propLang.get("tab_xdtab") %></th>
			  
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
sql   = "select trim(xcdel) xcdel, xdtab, xdesc, coalesce( trim(c.kpi) , '') idkpi "+
		"from ztables a , rel_cus_kpi b "+
		"left join pref_user_kpi c on trim(b.idkpi) = trim(c.kpi) "+
		"and c.idusr = '"+session.getAttribute("username")+"' and c.idcus = "+session.getAttribute("idcus")+" "+
		"where xcdtb = 'KPI' and b.idcus = "+session.getAttribute("idcus")+" "+
		"and trim(xcdel) = trim(b.idkpi) ";

System.out.println(sql);
rs=st.executeQuery(sql);

String chk = "";
String xdesc = "";
String info = "";
while (rs.next())
{
	xdesc = ""+rs.getString("xdesc");
	xdesc = Utility.noNull(xdesc);
	
	if(xdesc.trim().length()>0) {
		info = "<span class=\"pull-right\" data-toggle=\"popover\" "+
				"data-html=\"true\" data-placement=\"left\" data-content=\"<div class='scrollable' style='height:40px'>"+xdesc+"</div>\" title=\"\" data-original-title='<button type=\"button\" class=\"close pull-right\" data-dismiss=\"popover\">&times;</button>"+rs.getString("xcdel")+"'><i class=\"icon-info\"></i></span>";
	}
	
	if(rs.getString("xcdel").equals(rs.getString("idkpi"))) {
		chk = "checked";
	}
	count+=1;
%>
			<tr>
			  <td>
				  <div class="checkbox i-checks" style="margin-top:5px;margin-bottom:5px">
	                  <label>
	                    <input class="kpi" name="chkKpi" type="checkbox" value="<%=rs.getString("xcdel").trim() %>" <%=chk %>>
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("xcdel").trim() %></span></td>
			  <td><%=rs.getString("xdtab") %> <%=info%><!-- <br/><small><%=xdesc %></small>--></td>
			</tr>
<%
	chk = "";
	info = "";
}
rs.close();
%>
		  </tbody>
		</table>
		</div>
		
		
		
		
		
		
		<div id="_datkpi">
		<input id="searchInput2" class="form-control" type="text" value="<%=propLang.get("pop_em_filtra") %>">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th style="width:10%">
			  	<div class="checkbox i-checks" style="margin-top:5px;margin-bottom:5px">
                  <label>
                    <input name="chk" type="checkbox" value="">
                    <i></i>
                  </label>
                </div>
			  </th>
			  <th style="width:10%"><%=propLang.get("ala_code") %></th>
			  <th><%=propLang.get("tab_xdtab") %></th>
			  
			</tr>
		  </thead>
		  <tbody id="fbody2">
<%
sql   = "select trim(xcdel) xcdel, xdtab, xdesc, '' idkpi "+
		"from ztables a "+
		"join kpi_x_customer c on trim(xcdel) = trim(c.kpi) "+
		"and c.id_customer = "+session.getAttribute("idcus")+" ";

System.out.println(sql);
rs=st.executeQuery(sql);

chk = "";
xdesc = "";
info = "";
while (rs.next())
{
	xdesc = ""+rs.getString("xdesc");
	xdesc = Utility.noNull(xdesc);
	
	if(xdesc.trim().length()>0) {
		info = "<span class=\"pull-right\" data-toggle=\"popover\" "+
				"data-html=\"true\" data-placement=\"left\" data-content=\"<div class='scrollable' style='height:40px'>"+xdesc+"</div>\" title=\"\" data-original-title='<button type=\"button\" class=\"close pull-right\" data-dismiss=\"popover\">&times;</button>"+rs.getString("xcdel")+"'><i class=\"icon-info\"></i></span>";
	}
	
	if(rs.getString("xcdel").equals(rs.getString("idkpi"))) {
		chk = "checked";
	}
	count+=1;
%>
			<tr>
			  <td>
				  <div class="checkbox i-checks" style="margin-top:5px;margin-bottom:5px">
	                  <label>
	                    <input class="kpi" name="chkKpi" type="checkbox" value="<%=rs.getString("xcdel").trim() %>" <%=chk %>>
	                    <i></i>
	                  </label>
	              </div>
			  </td>
			  <td><span class="label bg-light"><%=rs.getString("xcdel").trim() %></span></td>
			  <td><%=rs.getString("xdtab") %> <%=info%><!-- <br/><small><%=xdesc %></small>--></td>
			</tr>
<%
	chk = "";
	info = "";
}
rs.close();
%>
		  </tbody>
		</table>
		</div>
		
		
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="$('[data-toggle=popover]').popover();jqueryRic();$('#_datkpi').hide();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-default" onclick="saveKpi()"><%=propLang.get("pop_default") %></a>
		<a class="btn btn-primary btn-lg" onclick="getKpi('GO')"><%=propLang.get("pop_run") %></a>	
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

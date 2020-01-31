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

String id_device = request.getParameter("id_device");
String id_customer = request.getParameter("id_customer");
String account_id = request.getParameter("account_id");

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

%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Lines Config</h3>
	</div>
	<div class="modal-body">
		
		<table class="table table-striped b-t m-b-none">
		  <thead>
			  <tr>
			    <th style="width:60px"><%=propLang.get("add_id_line") %></th>
		        <th style="width:200px"><%=propLang.get("sm_a_di") %></th>
		        <th><%=propLang.get("uc_description") %></th>
		        <th style="width:60px">Status</th>
		      </tr>
		  </thead>
		  <tbody>
		  <c:forEach items="${devdet}" var="devdet" varStatus="status">
 		  <tr>
	  	    <td><input type="hidden" id="${devdet.xcdel}nrrig" name="nrrig" value="${devdet.xcdel}" class="idrow">
	  	    	<input type="text" id="${devdet.xcdel}id_line" name="id_line" value="${devdet.xcdel}" class="form-control parsley-validated" 
	  	    	data-required="true" readonly/>
	  	    	
	  	    	</td>
	  	    <td><select id="${devdet.xcdel}doorid" name="doorid${devdet.xcdel}" class="form-control parsley-validated" data-required="true" style="width:100%" ${devdet.cmp1}>
	  	    		<option>-</option>
	  	    		<c:forEach items="${door}" var="door" varStatus="status">
                    <option value="${door.xcdel}">${door.xcdel} - ${door.xdtab}</option>
                    </c:forEach>
	  	    	</select>
	  	    	<script>
	  	    		document.getElementById('${devdet.xcdel}doorid').value="${devdet.xcdel}";
	  	    	</script>
	  	    </td>
	  	    <td><input type="text" id="${devdet.xcdel}note" name="note${devdet.xcdel}" class="form-control parsley-validated" data-required="true" value="${devdet.xdtab}" ${devdet.cmp1}/></td>
	  	    <td>
	  	    	<div class="form-group">
                     <div class="col-sm-10">
                       <label class="switch">
                         <input type="checkbox" id="${devdet.xcdel}status" 
                         name="status${devdet.xcdel}" ${devdet.cmp2} 
                         value="ON"
                         onclick="rowStatus('${devdet.xcdel}')">
                         <span></span>
                       </label>
                     </div>
                   </div>
	  	    </td>
		  </tr>
 		  </c:forEach>
 		  </tbody>
		</table>
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" data-dismiss="modal" onclick="saveLines()"><%=propLang.get("save") %></a>
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

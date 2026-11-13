<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.util.Date"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>

<form id="actionForm"  method="post">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Item Status</h3>
	</div>
	<div class="modal-body" id="modalBody">
		
		<h4><%=request.getParameter("name") %></h4>
		<div class="btn-group btn-group-justified">
		  <a class="btn btn-lg btn-default" id="sts1" onclick="chgStatus(1, 'Y')">Protected</a>
		  <a class="btn btn-lg btn-default" id="sts2" onclick="chgStatus(2, 'Y')">Previous Protected</a>
		  <a class="btn btn-lg btn-default" id="sts3" onclick="chgStatus(3, 'Y')">Obsolete</a>
		</div>
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
	</div>
  </div>
</div>
</form>
<script>
	var id = "<%=request.getParameter("id")%>";
	var status = "<%=request.getParameter("status").replaceAll("\\?cd=undefined","") %>";
	
	var idusr = "<%=Utility.noNull(session.getAttribute("username").toString())%>";
	var role = "<%=Utility.noNull(session.getAttribute("tipo").toString())%>";
	
	function chgStatus(val, sub) {
		
		$('#sts1').removeClass('active btn-success');
		$('#sts2').removeClass('active btn-success');
		$('#sts3').removeClass('active btn-success');
		
		$('#sts1').addClass('btn-default');
		$('#sts2').addClass('btn-default');
		$('#sts3').addClass('btn-default');
		
		
		$('#sts'+val).addClass('active btn-success');
		
		if(sub=='Y') {
			showLoader2();
			
			$.ajax({
				url: '../../ats/SetItem',
				type: 'POST',
				dataType: "html",
				data: {id:id, type:'S', idusr: idusr, role: role, isProtected: val},
				success: function (data) {
						//location.reload();
						getTable();
						$('.modal').remove(); $('div.modal-backdrop').remove();
						hideLoader2();
					}
				});
		}
		
	}
	
	chgStatus(status, 'N');
	
	
	
	
	

</script>

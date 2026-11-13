<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<form id="ticketForm" action="stores-ticket.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">${title}</h3>
	</div>
	<div class="modal-body">
		<input type="hidden" class="form-control" id="id" name="id" value="${id}"/>
		<input type="hidden" class="form-control" id="idStore" name="idStore" value="${idStore}"/>
		<input type="hidden" class="form-control" id="sapCode" name="sapCode" value="${sapCode}" readonly/>
		<input type="hidden" class="form-control" id="callId" name="callId" value="${callId}"/>
		
		<!-- 
		<label>Sap Code</label><br/>
		<input type="text" class="form-control" id="sapCode" name="sapCode" value="${sapCode}" readonly/>
		<div class="line line-dashed b-b line-lg pull-in"></div>
        -->
        
        <div id="err"></div>
        
        <label>Title</label><br/>
		<input type="text" class="form-control" id="callDescriptionShort" name="callDescriptionShort" value="${callDescriptionShort}" data-required="true" ${fromControl}/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
        
		<label>Description</label><br/>
		<textarea class="form-control" style="height:100px" id="callDescriptionLong" name="callDescriptionLong" data-required="true">${callDescriptionLong}</textarea> 
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
             <div class="padder">
                  <span class="h4 block">Ticket History</span>
                </div>
           </div>
        <br/>
		
		<table class="table">
			<tr>
				<th>Call Id</th>
				<th>Open Date</th>
				<th>Title</th>
				<th>Description</th>
				<th>Close Date</th>
			</tr>
			<tbody>
			<c:forEach items="${stList}" var="st" varStatus="status">
			<tr>
				<td><span class="label bg-light">${st.callId}</span></td>
				<td>${st.openDate}</td>
				<td>${st.callDescriptionShort}</td>
				<td>${st.callDescriptionLong}</td>
				<td>${st.closeDate}</td>
			</tr>
	        </c:forEach>
			</tbody>
		</table>  
		  
		
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<a class="btn btn-primary btn-lg" onclick="ticket()"><%=propLang.get("tick_save") %></a>
	</div>
  </div>
</div>
</form>

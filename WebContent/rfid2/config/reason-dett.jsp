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
<form id="actionForm" action="reason-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("rsn_dsfun") %></h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("rsn_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("rsn_descrizione") %></label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <!--  
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("rsn_loctype_in") %></label>
	          <select name="id_loctype_in" id="id_loctype_in"
              	class="form-control ss">
                <option value="">-</option>
                <c:forEach items="${loctype}" var="loctype" varStatus="status">
				<option value="${loctype.idLocationtype}">${loctype.description}</option>
				</c:forEach>
              </select>
              
              <script>
              	$('#id_loctype_in').val('${id_loctype_in}');
           	  </script>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("rsn_loctype_out") %></label>
	          <select name="id_loctype_out" id="id_loctype_out"
              	class="form-control ss">
                <option value="">-</option>
                <c:forEach items="${loctype}" var="loctype" varStatus="status">
				<option value="${loctype.idLocationtype}">${loctype.description}</option>
				</c:forEach>
              </select>
              
              <script>
              	$('#id_loctype_out').val('${id_loctype_out}');
           	  </script>
	        </div>
	    </div>
	    -->
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    <input type="hidden" name="id_loctype_in" value="${id_loctype_in}"/>
	    <input type="hidden" name="id_loctype_out" value="${id_loctype_out}"/>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Sign</label>
	          <select name="sign" id="sign"
              	class="form-control ss">
                <option value="+">+</option>
                <option value="-">-</option>
                <option value=" ">No Stock Diary</option>
              </select>
              
              <script>
              	$('#sign').val('${sign}');
           	  </script>
	        </div>
	    </div>
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

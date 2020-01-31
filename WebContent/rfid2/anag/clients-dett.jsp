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
<form id="actionForm" action="clients-list.htm" data-validate="parsley" method="post">
<input type="hidden" name="azione" value="A">
<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}">

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("cli_dsfun") %></h3>
	</div>
	<div class="modal-body">
		
		${msg}
		
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("sit_id") %></label>
	          <input type="text" id="id" name="id" class="form-control" value="${id}" readonly>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("sit_descrizione") %></label>
	          <input type="text" id="description" name="description" 
	          	class="form-control" value="${description}" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <div class="checkbox i-checks">
                <label>
                  <input id="enabled" name="enabled" type="checkbox" ><i></i> <%=propLang.get("rea_enabled") %>
                </label>
              </div>
	        </div>
	        <script>
	          if('${enabled}'=='true') {
              	$('#enabled').prop('checked', true);
              }
	        </script>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="panel-footer bg-light">
          <div class="padder">
               <span class="h4 block"><%=propLang.get("config") %></span>
             </div>
        </div>
        <br/>
        
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("sit_dsfun") %></label>
	          <select name="sites" id="sites" multiple 
              	class="form-control ss">
                <c:forEach items="${sites}" var="sites" varStatus="status">
				<option value="${sites.idSite}">${sites.description}</option>
				</c:forEach>
              </select>
	        </div>
	    </div>
	    
	    <script>
	    var values="${idSite}";
	    $.each(values.split(","), function(i,e){
	        $("#sites option[value='" + e + "']").prop("selected", true);
	    });
	    </script>
        
        <div class="line line-dashed b-b line-lg pull-in"></div>
	    
        <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Steps</label>
	          <select name="steps" id="steps" multiple 
              	class="form-control ss">
                <c:forEach items="${steps}" var="steps" varStatus="status">
				<option value="${steps.idStep}">${steps.description}</option>
				</c:forEach>
              </select>
	        </div>
	    </div>
	    
	    <script>
	    var values="${idStep}";
	    $.each(values.split(","), function(i,e){
	        $("#steps option[value='" + e + "']").prop("selected", true);
	    });
	    </script>
        
        
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

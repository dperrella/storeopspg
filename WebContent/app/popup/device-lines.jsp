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
<form data-validate="parsley" id="dd-form" action="device-detail-list.htm" method="post">
<input type="hidden" name="id_device" value="${id_device}"/>
<input type="hidden" name="id_customer" value="${id_customer}"/>
<input type="hidden" name="account_id" value="${account_id}"/>
<input type="hidden" name="linNum" value="${linNum}"/>

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
		        <th><%=propLang.get("sm_a_di") %></th>
		        <!-- 
		        <th><%=propLang.get("uc_description") %></th>
		        -->
		        <th style="width:60px">Status</th>
		        <c:if test = "${device_type == 5}">
		        <th style="width:60px">Entrance</th>
		        </c:if>
		      </tr>
		  </thead>
		  <tbody>
		  <c:forEach items="${devdet}" var="devdet" varStatus="status">
 		  <tr>
	  	    <td><input type="hidden" id="${devdet.xdtab}nrrig" name="nrrig" value="${devdet.xdtab}" class="idrow">
	  	    	<input type="hidden" id="${devdet.xdtab}id_line" name="id_line" value="${devdet.xdtab}" class="form-control parsley-validated" 
	  	    	data-required="true" readonly/>
	  	    	<input type="text" id="${devdet.xdtab}id_line_vid" name="id_line_vid" value="${devdet.xcdtb}" class="form-control parsley-validated" 
	  	    	readonly/>
	  	    <td><div id="${devdet.xdtab}sel"><select id="${devdet.xdtab}doorid" name="doorid${devdet.xdtab}" class="form-control parsley-validated" 
	  	    	data-required="true" style="width:100%" ${devdet.cmp1} onchange="setNew(this.value, '${devdet.xdtab}')">
	  	    		<optgroup>
	  	    		<option>-</option>
	  	    		<c:forEach items="${door}" var="door" varStatus="status">
                    <option value="${door.xcdel}" ${door.cmp4}>${door.xcdel} - ${door.xdtab}</option>
                    </c:forEach>
                    </optgroup>
                    <optgroup>
                    <option value="NEW">New Door</option>
                    </optgroup>
	  	    	</select>
	  	    	</div>
	  	    	<div id="${devdet.xdtab}new">
	  	    		<div class="row">
	  	    			<div class="col-sm-3">
	  	    				<input type="text" id="${devdet.xdtab}id" name="id${devdet.xdtab}" 
	  	    					class="form-control parsley-validated" placeholder="Door Id" value=""/>
	  	    			</div>
	  	    			<div class="col-sm-9">
	  	    				<input type="text" id="${devdet.xdtab}description" name="description${devdet.xdtab}" 
	  	    					class="form-control parsley-validated" placeholder="Door Description" value=""/>
	  	    			</div>
	  	    		</div>
	  	    	
	  	    	</div>
	  	    	<script>
	  	    		document.getElementById('${devdet.xdtab}doorid').value="${devdet.cmp3}";
	  	    		$('#${devdet.xdtab}new').hide();
	  	    	</script>
	  	    </td>
	  	    <!-- 
	  	    <td><input type="text" id="${devdet.xdtab}note" name="note${devdet.xdtab}" class="form-control parsley-validated" value="" ${devdet.cmp1}/></td>
	  	    -->
	  	    <td>
	  	    	<div class="form-group">
                     <div class="col-sm-10">
                       <label class="switch">
                         <input type="checkbox" id="${devdet.xdtab}status" 
                         name="status${devdet.xdtab}" ${devdet.cmp2} 
                         value="ON"
                         onclick="rowStatus('${devdet.xdtab}')">
                         <span></span>
                       </label>
                     </div>
                   </div>
	  	    </td>
	  	    
	  	    <c:if test = "${device_type == 5}">
	  	    <td>
	  	    	<div class="form-group">
                     <div class="col-sm-10">
                       <label class="checkbox m-n i-checks">
                         <input type="checkbox" id="${devdet.xdtab}entrance" 
                         name="entrance${devdet.xdtab}" ${devdet.cmp4} 
                         value="ON">
                         <i></i>
                       </label>
                     </div>
                     
                     
                   </div>
	  	    </td>
	  	    </c:if>
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
</form>
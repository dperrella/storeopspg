<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"  %>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>

	 
      <input type="hidden" name="day" value="${giorno}">
	  <input type="hidden" id="data${giorno}" name="data" value="${data_giorno}">  
      
	  <div class="table-responsive">
      <table class="table b-t b-light">
               
	    <tbody id="body-table">
	    
	    <c:forEach items="${plan2}" var="emp" varStatus="status">
									    		
   			<tr id="row${giorno}${emp.id_employee}" class="${emp.eyeClass}">
   				
   				<td class="b-r" style="width:40px;border-bottom:2px solid #77848F">
			    	<div id="status${giorno}${emp.id_employee}">${s}</div>
			    </td>
			    <td class="b-r" style="width:120px; border-bottom:2px solid #77848F">
					<div style="width:120px;" class="text-ellipsis">${emp.name} ${emp.surname}</div>
					
					<input type="hidden" name="id_employee${giorno}" value="${emp.id_employee}"/>
					<input type="hidden" id="id_departments${giorno}${emp.id_employee}" name="id_departments${giorno}${emp.id_employee}" value="${emp.id_dep}"/>
					<input type="text" id="ds_departments${giorno}${emp.id_employee}" name="ds_departments${giorno}${emp.id_employee}" 
						style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="${emp.ds_dep}" disabled/>
					
					<div id="periods${giorno}${emp.id_employee}">${emp.inputs}</div>
					
				</td>
				<!-- 
				<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F">
					<a href="../../app/popup/departments_planner.jsp?iddest=id_departments${giorno}${emp.id_employee}&dsdest=ds_departments${giorno}${emp.id_employee}" 
						class="getModal btn btn-default btn-xs" data-toggle="tooltip"
						data-placement="top" title="" data-original-title="<%=propLang.get("cambiaDip") %>" ${dis}>
			      		T.</a>
				</td>
				-->
				<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F">
					<div class="btn-group-vertical">
					
					<!-- 
					<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
						onclick="confirmAddSlider('0', '${emp.id_employee}')"
						data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnAddL") %>" ${dis}><i class="icon-plus" style="margin-top:6px;margin-left:-6px"></i>
					</a>
					-->
					<a href="#" class="btn btn-default btn-block btn-xs"  data-toggle="tooltip" 
						onclick="confirmRemoveSlider('${giorno}', '${emp.id_employee}')"
						data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
						<i class="icon-close" style="margin-top:8px;margin-left:-6px"></i>
					</a>
					</div>
				</td>
				<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px; border-bottom:2px solid #77848F"">
					
					<div class="0${emp.id_employee}ruler ui-slider">${emp.ruler}</div>
					<br/>
					<div class="0${emp.id_employee}ruler2 ui-slider">${emp.day0}</div>
				    
				</td>
				
				<td class="b-l" style="width:60px; border-bottom:2px solid #77848F">
					<input type="text" name="totDip${giorno}${emp.id_employee}" id="totDip${giorno}${emp.id_employee}" 
						value="${emp.tot_day_emp}" style="width:50px;text-align:right; background: transparent; 
						border:0px;font-weight:bold;color:#000" readonly />
				</td>
	
				
   			</tr>
   		
   		</c:forEach>
		
	    </tbody>
	    
	  </table>
	  
	  </div>
	  
	  
	  <div class="totaliGiorno">
        <table class="table b-t b-light">
          <tfoot>
	      
	      <!-- Intestazioni -->
	      <tr>
	    	<td style="width:205px"></td>
	    	<td class="barra">
	    		<div style="height:13px;margin-top:-5px" class="0intestazioni ui-slider">${rulertot}</div>
	    	</td>
	    	<td style="width:60px"></td>
	      </tr>
	      
	      <!-- Totale x Fascia -->
	      <tr>
	    	<td style="width:205px"><b><%=propLang.get("tot1") %></b></td>
	    	<td class="barra">
	    		<div style="height:20px" class="${giorno}tot1 ui-slider">${tot1}</div>
	    	</td>
	    	<td style="width:60px"><input type="text" name="${giorno}totSett1" id="${giorno}totSett1" 
					value="${ttt1}" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	      <!-- Totale x Risorsa -->
	      <tr>
	    	<td style="width:205px"><b><%=propLang.get("tot2") %></b></td>
	    	<td class="barra">
	    		<div style="height:20px" class="${giorno}tot2 ui-slider">${tot2}</div>
	    	</td>
	    	<td style="width:60px"><input type="text" name="${giorno}totSett2" id="${giorno}totSett2" 
					value="${ttt2}" style="width:50px;text-align:right;background:transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	      <!-- Nomero tabella (da creare) -->
	      <tr>
	    	<td style="width:205px"><b><%=propLang.get("tot3") %></b></td>
	    	<td class="barra">
	    		<div style="height:20px" class="${giorno}tot3 ui-slider">${tot3}</div>
	    	</td>
	    	<td style="width:60px"><input type="text" name="${giorno}totSett3" id="${giorno}totSett3" 
					value="${ttt3}" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	     </tfoot>
       </table>
       </div>
       <div class="spacer"></div>
  
	  


<script>
//MODAL
$('.getModalDep').click(function(e) {
	
	$('.mia2').remove(); $('div.modal-backdrop').remove();
	
	console.log('rim2');
	
	e.preventDefault();
	var href = $(e.target).attr('href');
	
	$.get(href, function(data) {
		$('<div class="modal mia2" >' + data + '</div>').modal();
	});
	
});
</script>
  
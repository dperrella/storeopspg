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
<!--  Giorno -->  
<c:forEach items="${day}" var="day" varStatus="status">
	  
	<section class="panel panel-default">
      <header class="panel-heading font-bold">
         <span class="${h3}">${day.xdtab}</span>
        <input type="hidden" name="day" value="${day.xcdel}">
        <input type="hidden" id="data${day.xcdel}" name="data" value="${day.cmp1}">
        
        <input type="hidden" id="tot3${day.xcdel}hidden" name="tot3${day.xcdel}hidden" value="${day.cmp2}">
      </header>
	  
	  <div class="table-responsive">
      <table class="table b-t b-light">
               
	    <tbody id="body-table">
	    
	    <c:forEach items="${emp}" var="emp" varStatus="status">
		  <tr id="row${day.xcdel}${emp.id}">
		    
			<td class="b-r" style="width:120px; border-bottom:2px solid #77848F" rowspan="2">
				<div style="width:120px;">${emp.name}<br/>${emp.surname}
				<c:if test="${tpvis == 1}">
					<br/><b>${emp.cmp4}</b>
				</c:if>	
				</div>
				<input type="hidden" name="id_employee${day.xcdel}" value="${emp.id}"/>
				<input type="hidden" id="id_departments${day.xcdel}${emp.id}" name="id_departments${day.xcdel}${emp.id}" value="${emp.cmp2}"/>
				<input type="text" id="ds_departments${day.xcdel}${emp.id}" name="ds_departments${day.xcdel}${emp.id}" 
					style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="${emp.cmp3}" disabled/>
				
				<div id="status${day.xcdel}${emp.id}"></div>
				
				<div id="periods${day.xcdel}${emp.id}"></div>
				
			</td>
			
			<td class="b-r" style="width:${width}px; border-bottom:2px solid #77848F" rowspan="2">
				<a href="../../hr/popup/departments_planner.jsp?iddest=id_departments${day.xcdel}${emp.id}&dsdest=ds_departments${day.xcdel}${emp.id}" 
					class="getModal${day.xcdel} btn btn-default btn-block ${btn}" data-toggle="tooltip"
					data-placement="top" title="" data-original-title="<%=propLang.get("cambiaDip") %>" ${dis}>
		      		T.</a>
			</td>
			
			<td class="b-r" style="width:${width}px;">
				<a href="#" class="btn btn-default btn-block ${btn}" data-toggle="tooltip" 
					onclick="confirmAddSliderLeft('${day.xcdel}', '${emp.id}', 'L')"
					data-placement="top" title="" data-original-title="<%=propLang.get("btnAddL") %>" ${dis}>
					<i class="icon-arrow-left"></i>
				</a>
			</td>
			
			<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px">
				
				<div class="${day.xcdel}${emp.id}tempi"></div>
			    
				<div class="${day.xcdel}${emp.id}ruler ui-slider"></div>
			    
			</td>
			
			<td class="b-r" style="width:${width}px;">
				<a href="#" class="btn btn-default btn-block ${btn}" data-toggle="tooltip" 
					onclick="confirmAddSliderRight('${day.xcdel}', '${emp.id}', 'R')"
					data-placement="top" title="" data-original-title="<%=propLang.get("btnAddR") %>" ${dis}>
					<i class="icon-arrow-right"></i>
				</a>
			</td>
			
			<td class="b-l" style="width:60px; border-bottom:2px solid #77848F"" rowspan="2">
				<input type="text" name="totDip${day.xcdel}${emp.id}" id="totDip${day.xcdel}${emp.id}" 
					value="0" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly />
			</td>
			
		  </tr>
		  <tr>
		  	<td class="b-r" style="width:${width}px; border-bottom:2px solid #77848F">
				<a href="#" class="btn btn-default btn-block ${btn}" data-toggle="tooltip" 
					onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
					data-placement="top" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
					<i class="icon-close"></i>
				</a>
			</td>
		  	<td colspan="18" class="b-r" style="height:${rowheight}px; border-bottom:2px solid #77848F"">
		  		<div class="${day.xcdel}${emp.id}"></div>
			</td>
			<td class="b-l" style="width:50px;border-bottom:2px solid #77848F">
				
				<a href="#" class="btn btn-default btn-block ${btn}" data-toggle="tooltip" 
					onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
					data-placement="top" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
					<i class="icon-close"></i>
				</a>
			</td>
		  </tr>
		  
		</c:forEach>
	    
	    
	    <!-- Trasferimenti -->
		<c:forEach items="${trasf}" var="trasf" varStatus="status">
				
			<tr id="row${day.xcdel}${trasf.xcdtb}">
			    
				<td class="b-r" style="width:120px; border-bottom:2px solid #77848F" rowspan="2">
					<div style="width:120px;">${trasf.xcdel}<br/>${trasf.xdtab}<br/><b>${trasf.cmp2}</b></div>
					
					<input type="hidden" name="id_employee${day.xcdel}" value="${trasf.xcdtb}"/>
					<input type="hidden" id="id_departments${day.xcdel}${trasf.xcdtb}" name="id_departments${day.xcdel}${trasf.xcdtb}" value=""/>
					<input type="text" id="ds_departments${day.xcdel}${trasf.xcdtb}" name="ds_departments${day.xcdel}${trasf.xcdtb}" 
						style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="" disabled/>
					
					<div id="status${day.xcdel}${trasf.xcdtb}"></div>
					
					<div id="periods${day.xcdel}${trasf.xcdtb}"></div>
					
				</td>
				
				<td class="b-r" style="width:${width}px; border-bottom:2px solid #77848F" rowspan="2">
					
				</td>
				
				<td class="b-r" style="width:${width}px; border-bottom:2px solid #77848F" rowspan="2">
					
				</td>
				
				<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px">
					
					<div class="${day.xcdel}${trasf.xcdtb}tempi"></div>
				    
					<div class="${day.xcdel}${trasf.xcdtb}ruler ui-slider"></div>
				    
				</td>
				
				<td class="b-r" style="width:${width}px; border-bottom:2px solid #77848F" rowspan="2">
					
				</td>
				
				<td class="b-l" style="width:60px; border-bottom:2px solid #77848F" rowspan="2">
					<input type="text" name="totDip${day.xcdel}${trasf.xcdtb}" id="totDip${day.xcdel}${trasf.xcdtb}" 
						value="0" style="width:50px;text-align:right; background: transparent; 
						border:0px;font-weight:bold;color:#000" readonly />
				</td>
				
			  </tr>
			  <tr>
			  	<td colspan="18" class="b-r" style="height:${rowheight}px; border-bottom:2px solid #77848F">
			  		<div class="${day.xcdel}${trasf.xcdtb}"></div>
			  		
			  		<script>
			  		${trasf.cmp1}
			  		
			  		// Righello + input ore
			  		var id='';
			  	 	for (var i = 0; i <= 17; i+=0.5) {
			  	      	
			  	 		id = ""+((i+6)+1);
			  	 		id = id.replace(/\./g,"");
			  	 		
			  	    	if(isInt(i)) {
			  	    		var el = $('<label class="ruler${giorno}${trasf.xcdtb}'+id+' ruler">'+((i+6)+1)+'<br/>|</label>').css('left',(i/17*100)+'%');
			  	    	}
			  	    	else {
			  	    		var el = $('<label class="ruler${giorno}${trasf.xcdtb}'+id+' ruler">&nbsp;<br/><span style="font-size:8px">&times;</span></label>').css('left',(i/17*100)+'%');
			  	    	}
			  	    	$( ".${giorno}${trasf.xcdtb}ruler" ).append(el);
			  	    	
			  	    }
			  	 	</script>
				</td>
			</tr>
			  
		</c:forEach>
	    
	    
	    </tbody>
	    <tfoot>
	      <!-- Totale x Fascia -->
	      <tr>
	    	<td colspan="3"><b><%=propLang.get("tot1") %></b></td>
	    	<td colspan="18">
	    		
	    		<div style="height:30px" class="${day.xcdel}tot1 ui-slider"></div>
	    		
	    	</td>
	    	<td></td>
	    	<td><input type="text" name="${day.xcdel}totSett1" id="${day.xcdel}totSett1" 
					value="0" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	      <!-- Totale x Risorsa -->
	      <tr>
	    	<td colspan="3"><b><%=propLang.get("tot2") %></b></td>
	    	<td colspan="18">
	    		
	    		<div style="height:30px" class="${day.xcdel}tot2 ui-slider"></div>
	    		
	    	</td>
	    	<td></td>
	    	<td><input type="text" name="${day.xcdel}totSett2" id="${day.xcdel}totSett2" 
					value="0" style="width:50px;text-align:right;background:transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	      <!-- Numero tabella (da creare) -->
	      <tr>
	    	<td colspan="3"><b><%=propLang.get("tot3") %></b></td>
	    	<td colspan="18">
	    		
	    		<div style="height:30px" class="${day.xcdel}tot3 ui-slider"></div>
	    		
	    	</td>
	    	<td></td>
	    	<td><input type="text" name="${day.xcdel}totSett3" id="${day.xcdel}totSett3" 
					value="0" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	    </tfoot>
	  </table>
	  
	  </div>
	
  	</section>
  
</c:forEach>
<!--  fine Schiera giorni -->

<script>
${newDepart}
</script>
  
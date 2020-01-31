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
	  
      <div class="row">
		<div class="col-sm-4">
			<div class="subnav">
				<ul class="nav nav-pills">
					<li><a href="planner-ric.htm?tp=${tp}"><%=propLang.get("tornaRic") %></a></li>
				</ul>
			</div>	
	   	</div>
	   	<div class="col-sm-8 text-right"><span class="h3 font-thin">${day.xdtab}</span></div>
   	  </div>
   	  <input type="hidden" name="day" value="${day.xcdel}">
	  <input type="hidden" id="data${day.xcdel}" name="data" value="${day.cmp1}">  
      
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
			
			<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
				<a href="../../hr/popup/departments_planner.jsp?iddest=id_departments${day.xcdel}${emp.id}&dsdest=ds_departments${day.xcdel}${emp.id}" 
					class="getModal${day.xcdel} btn btn-default btn-block ${btn}" data-toggle="tooltip"
					data-placement="top" title="" data-original-title="<%=propLang.get("cambiaDip") %>" ${dis}>
		      		T.</a>
			</td>
			
			<td class="b-r" style="width:50px;">
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
			
			<td class="b-r" style="width:50px;">
				<a href="#" class="btn btn-default btn-block ${btn}" data-toggle="tooltip" 
					onclick="confirmAddSliderRight('${day.xcdel}', '${emp.id}', 'R')"
					data-placement="top" title="" data-original-title="<%=propLang.get("btnAddR") %>" ${dis}>
					<i class="icon-arrow-right"></i>
				</a>
			</td>
			
			<td class="b-l" style="width:60px; border-bottom:2px solid #77848F" rowspan="2">
				<input type="text" name="totDip${day.xcdel}${emp.id}" id="totDip${day.xcdel}${emp.id}" 
					value="0" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly />
			</td>
			
		  </tr>
		  <tr>
		    <td class="b-r" style="border-bottom:2px solid #77848F">
				<a href="#" class="btn btn-default btn-block ${btn}" data-toggle="tooltip" 
					onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
					data-placement="top" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
					<i class="icon-close"></i>
				</a>
			</td>
		  	<td colspan="18" class="b-r" style="height:${rowheight}px; border-bottom:2px solid #77848F"">
		  		<div class="${day.xcdel}${emp.id}"></div>
			</td>
			<td class="b-l" style="border-bottom:2px solid #77848F">
				<a href="#" class="btn btn-default btn-block ${btn}" data-toggle="tooltip" 
					onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
					data-placement="top" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
					<i class="icon-close"></i>
				</a>
				
				<script>
				/*
				// Contenitore input per insert
	  	  		var scntDiv = $("#periods${day.xcdel}${emp.id}");
	  	  		// Pulisco il contenitore
	  			scntDiv.empty();
	  	  		
				if('${day.xcdel}'=='0') {
	  		    	${emp.lun}
	  		    }
	  		    if('${day.xcdel}'=='1') {
	  				${emp.mar}
	  		    }
	  		  	if('${day.xcdel}'=='2') {
	  				${emp.mer}
	  		  	}
	  		  	if('${day.xcdel}'=='3') {
	  				${emp.gio}
	  		  	}
	  		  	if('${day.xcdel}'=='4') {
	  				${emp.ven}
	  		  	}
	  		  	if('${day.xcdel}'=='5') {
	  				${emp.sab}
	  		  	}
	  		  	if('${day.xcdel}'=='6') {
	  				${emp.dom}
	  		  	}
				*/
				// Contenitore input per insert
	  	  		var scntDiv = $("#periods${day.xcdel}${emp.id}");
	  	  		// Pulisco il contenitore
	  			scntDiv.empty();
	  	  		
	  		    // Intervalli
	  		    if('${day.xcdel}'=='0') {
	  		    	${emp.lun}
	  		    }
	  		    if('${day.xcdel}'=='1') {
	  				${emp.mar}
	  		    }
	  		  	if('${day.xcdel}'=='2') {
	  				${emp.mer}
	  		  	}
	  		  	if('${day.xcdel}'=='3') {
	  				${emp.gio}
	  		  	}
	  		  	if('${day.xcdel}'=='4') {
	  				${emp.ven}
	  		  	}
	  		  	if('${day.xcdel}'=='5') {
	  				${emp.sab}
	  		  	}
	  		  	if('${day.xcdel}'=='6') {
	  				${emp.dom}
	  		  	}
	  		    
	  	  		// Righello + input ore
	  	  		var id='';
	  	  	 	for (var i = 0; i <= 17; i+=0.5) {
	  	  	      	
	  	  	 		id = ""+((i+6)+1);
	  	  	 		id = id.replace(/\./g,"");
	  	  	 		
	  	  	    	if(isInt(i)) {
	  	  	    		var el = $('<label class="ruler${day.xcdel}${emp.id}'+id+' ruler">'+((i+6)+1)+'<br/>|</label>').css('left',(i/17*100)+'%');
	  	  	    		var inp = $('<input class="${day.xcdel}${emp.id}'+id+'" type="hidden" id="${day.xcdel}${emp.id}'+id+'" name="day${day.xcdel}'+id+'" value="0">');
	  	  	    		var inp2 = $('<input class="${day.xcdel}${emp.id}_X'+id+'" type="hidden" id="${day.xcdel}${emp.id}_X'+id+'" name="day${day.xcdel}_X'+id+'" value="0">');
	  	  	    	}
	  	  	    	else {
	  	  	    		var el = $('<label class="ruler${day.xcdel}${emp.id}'+id+' ruler">&nbsp;<br/><span style="font-size:8px">&times;</span></label>').css('left',(i/17*100)+'%');
	  	  	    		var inp2 = $('<input class="${day.xcdel}${emp.id}_X'+id+'" type="hidden" id="${day.xcdel}${emp.id}_X'+id+'" name="day${day.xcdel}_X'+id+'" value="0">');
	  	  	  	    	
	  	  	    	}
	  	  	    	$( ".${day.xcdel}${emp.id}ruler" ).append(el);
	  	  	    	
	  	  	    	$( ".${day.xcdel}${emp.id}tempi" ).append(inp);
	  	  	    	$( ".${day.xcdel}${emp.id}tempi" ).append(inp2);
	  	  	    	
	  	  	    }
	  	  	    
	  	  		// modifica il totale
	  			setTot('${day.xcdel}' , '${emp.id}');
				</script>
			</td>
		  </tr>
		  
		</c:forEach>
		
		
	    
	    
	    <!-- Trasferimenti -->
		<c:forEach items="${trasf}" var="trasf" varStatus="status">
				
			<tr id="row${day.xcdel}${trasf.xcdtb}">
			    
				<td class="b-r" style="width:120px; border-bottom:2px solid #77848F" rowspan="2">
					<div style="width:120px;">${trasf.xcdel}<br/>${trasf.xdtab}
					<c:if test="${tpvis == 1}">
					<br/><b>${trasf.cmp2}</b>
					</c:if>
					</div>
					
					<input type="hidden" name="id_employee${day.xcdel}" value="${trasf.xcdtb}"/>
					<input type="hidden" id="id_departments${day.xcdel}${trasf.xcdtb}" name="id_departments${day.xcdel}${trasf.xcdtb}" value=""/>
					<input type="text" id="ds_departments${day.xcdel}${trasf.xcdtb}" name="ds_departments${day.xcdel}${trasf.xcdtb}" 
						style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="" disabled/>
					
					<div id="status${day.xcdel}${trasf.xcdtb}"></div>
					
					<div id="periods${day.xcdel}${trasf.xcdtb}"></div>
					
				</td>
				
				<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
					
				</td>
				
				<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
					
				</td>
				
				<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px">
					
					<div class="${day.xcdel}${trasf.xcdtb}tempi"></div>
				    
					<div class="${day.xcdel}${trasf.xcdtb}ruler ui-slider"></div>
				    
				</td>
				
				<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
					
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
	  
	  <script>
		// Righello
	 	for (var i = 0; i <= 17; i+=0.5) {
	      	
	    	if(isInt(i)) {
	    		// Totali
	    		var el2 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
	    		var el3 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
	    		var el4 = $('<label class="ruler">|</label>').css('left',(i/17*100)+'%');
	    	}
	    	else {
	    		var el2 = $('<label id="${day.xcdel}tot1'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
	    		var el3 = $('<label id="${day.xcdel}tot2'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
	    		var el4 = $('<label id="${day.xcdel}tot3'+((i+0.5)+6)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/17*100)+'%');
	    		
	    	}
	    	$( ".${day.xcdel}tot1" ).append(el2);
	    	$( ".${day.xcdel}tot2" ).append(el3);
	    	$( ".${day.xcdel}tot3" ).append(el4);
	    }
	  	
	 	var tot3 = ""+$('#tot3${day.xcdel}hidden').val();
	 	if(tot3=="undefined") {
	 		tot3 = "0";
	 	}
	 	console.log('tot3:' + tot3);
	 	
	 	var res = tot3.split(",");
	 	
	 	for ( var i = 0, l = res.length; i < l; i++ ) {
			
	 		$('#${day.xcdel}tot3'+(i+7)).html(''+res[i]+'');
	 		
		}
	 	
	 	setTot1('${day.xcdel}');
	 	// FINE GIORNO
	  	// ========================
	  		
	 	$('.getModal${day.xcdel}').click(function(e) {
			
			e.preventDefault();
			var href = $(e.target).attr('href');
			
			$.get(href, function(data) {
				$('<div class="modal mia" >' + data + '</div>').modal();
			});
			
		});
	  </script>
	  
</c:forEach>
<!--  fine Schiera giorni -->

<script>
${newDepart}
</script>
  
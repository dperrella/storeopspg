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
	 
      <input type="hidden" name="day" value="${day.xcdel}">
	  <input type="hidden" id="data${day.xcdel}" name="data" value="${day.cmp1}">  
	  
	  
	  
	  <!-- Per heatmap -->
	  <input type="hidden" id="${day.xcdel}_xAxis" name="${day.xcdel}_xAxis" value="${xAxis}">
	  <input type="hidden" id="${day.xcdel}_yAxis" name="${day.xcdel}_yAxis" value="${yAxis}">
	  <input type="hidden" id="${day.xcdel}_heatmap"name="${day.xcdel}_heatmap" value="${heatmap}">
	  <input type="hidden" id="${day.xcdel}_max" name="${day.xcdel}_max" value="${max}">
	  <input type="hidden" id="${day.xcdel}_height"name="${day.xcdel}_height" value="${height}">
	     
	 
      
	  <div class="table-responsive">
      <table class="table b-t b-light">
               
	    <tbody id="body-table">
	    
	    <c:forEach items="${emp}" var="emp" varStatus="status">
		  <tr id="row${day.xcdel}${emp.id}" class="${emp.address}">
		    
		    <td class="b-r" style="width:40px;border-bottom:2px solid #77848F">
		    	<div id="status${day.xcdel}${emp.id}"></div>
		    </td>
			<td class="b-r" style="width:120px; border-bottom:2px solid #77848F">
				<div style="width:120px;" class="text-ellipsis">${emp.name} ${emp.surname}</div>
				
				<input type="hidden" name="id_employee${day.xcdel}" value="${emp.id}"/>
				<input type="hidden" id="id_departments${day.xcdel}${emp.id}" name="id_departments${day.xcdel}${emp.id}" value="${emp.cmp2}"/>
				<input type="text" id="ds_departments${day.xcdel}${emp.id}" name="ds_departments${day.xcdel}${emp.id}" 
					style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="${emp.cmp3}" disabled/>
				
				<div id="trasferte${day.xcdel}${emp.id}"></div>
												
				<div id="periods${day.xcdel}${emp.id}"></div>
				
			</td>
			
			<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F">
				<a href="../../hr/popup/departments_planner.jsp?iddest=id_departments${day.xcdel}${emp.id}&dsdest=ds_departments${day.xcdel}${emp.id}&day=${day.xcdel}&idemployee=${emp.id}" 
					class="getModalDep btn btn-default btn-xs" data-toggle="tooltip"
					data-placement="top" title="" data-original-title="<%=propLang.get("cambiaDip") %>" ${dis}>
		      		T.</a>
			</td>
			
			<td class="b-r m-xs" style="width:30px; border-bottom:2px solid #77848F">
				<div class="btn-group-vertical">
				<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
					onclick="confirmAddSliderLeft('${day.xcdel}', '${emp.id}', 'L')"
					data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnAddL") %>" ${dis}><i class="icon-arrow-left" style="margin-top:6px;margin-left:-6px"></i>
				</a>
				<a href="#" class="btn btn-default btn-block btn-xs"  data-toggle="tooltip" 
					onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
					data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
					<i class="icon-close" style="margin-top:6px;margin-left:-6px"></i>
				</a>
				</div>
			</td>
			
			<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px; border-bottom:2px solid #77848F"">
												
				<div class="${day.xcdel}${emp.id}tempi"></div>
			    
				<div class="${day.xcdel}${emp.id}ruler ui-slider"></div>
				<br/>
				<div class="${day.xcdel}${emp.id}"></div>
			    
			</td>
			
			<td class="b-r" style="width:30px;border-bottom:2px solid #77848F"">
				<div class="btn-group-vertical">
				<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
					onclick="confirmAddSliderRight('${day.xcdel}', '${emp.id}', 'R')"
					data-placement="top" style="width:20px;" title="" data-original-title="<%=propLang.get("btnAddR") %>" ${dis}>
					<i class="icon-arrow-right" style="margin-top:6px;margin-left:-6px"></i>
				</a>
				<a href="#" class="btn btn-default btn-block btn-xs" data-toggle="tooltip" 
					onclick="confirmRemoveSlider('${day.xcdel}', '${emp.id}')"
					data-placement="top" style="width:20px" title="" data-original-title="<%=propLang.get("btnDel") %>" ${dis}>
					<i class="icon-close" style="margin-top:6px;margin-left:-6px"></i>
				</a>
				</div>
			</td>
			
			<td class="b-l" style="width:60px; border-bottom:2px solid #77848F">
				<input type="text" name="totDip${day.xcdel}${emp.id}" id="totDip${day.xcdel}${emp.id}" 
					value="0" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly />
				
				<br/>
										
				<input type="hidden" name="monteDipIni${day.xcdel}${emp.id}" id="monteDipIni${day.xcdel}${emp.id}" 
							value="${emp.phone}"/>		
				<input type="text" name="monteDip${day.xcdel}${emp.id}" id="monteDip${day.xcdel}${emp.id}" 
					value="${emp.phone}" style="width:50px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#FAD733" readonly />
				
				<script>
				
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
	  		    
	  		 	 setRighello('${day.xcdel}' , '${emp.id}');
	  		  
	  	  		
	  	  	    
	  	  		// modifica il totale
	  			setTot('${day.xcdel}' , '${emp.id}');
	  	  		
	  	  		setMonte('${emp.id}');
				</script>
			</td>
			
		  </tr>
		  
		</c:forEach>
		
		
	    
	    
	    <!-- Trasferimenti -->
		<c:forEach items="${trasf}" var="trasf" varStatus="status">
				
			<tr id="row${day.xcdel}${trasf.xcdtb}">
			    <td class="b-r" style="border-bottom:2px solid #77848F">
			    	<div id="status${day.xcdel}${trasf.xcdtb}"></div>
			    </td>
				<td class="b-r" style="width:120px; border-bottom:2px solid #77848F">
					<div style="width:120px;" class="text-ellipsis">${trasf.xcdel} ${trasf.xdtab}</div>
					
					<input type="hidden" name="id_employee${day.xcdel}" value="${trasf.xcdtb}"/>
					<input type="hidden" id="id_departments${day.xcdel}${trasf.xcdtb}" name="id_departments${day.xcdel}${trasf.xcdtb}" value=""/>
					<input type="text" id="ds_departments${day.xcdel}${trasf.xcdtb}" name="ds_departments${day.xcdel}${trasf.xcdtb}" 
						style="padding:0px;margin:0px;color:#000;border:0px; width:100px; background:none;font-weight:bold;" value="" disabled/>
					
					<div id="periods${day.xcdel}${trasf.xcdtb}"></div>
					
				</td>
				
				<td class="b-r" style="width:50px; border-bottom:2px solid #77848F">
					
				</td>
				
				<td class="b-r" style="width:50px; border-bottom:2px solid #77848F">
					
				</td>
				
				<td colspan="18" class="b-r" style="background:#fff; height:${rowheight}px; border-bottom:2px solid #77848F"">
					
					<div class="${day.xcdel}${trasf.xcdtb}tempi"></div>
				    
					<div class="${day.xcdel}${trasf.xcdtb}ruler ui-slider"></div>
				    <br/>
				    <div class="${day.xcdel}${trasf.xcdtb}"></div>
			  		
			  		<script>
			  		${trasf.cmp1}
			  		
			  		// Righello + input ore
			  		var id='';
			  	 	for (var i = 0; i <= 18; i+=0.5) {
			  	      	
			  	 		id = ""+((i+5)+1);
			  	 		id = id.replace(/\./g,"");
			  	 		
			  	    	if(isInt(i)) {
			  	    		var el = $('<label class="ruler${giorno}${trasf.xcdtb}'+id+' ruler">'+((i+5)+1)+'</label>').css('left',(i/18*100)+'%');
			  	    	}
			  	    	else {
			  	    		var el = $('<label class="ruler${giorno}${trasf.xcdtb}'+id+' ruler"><span style="font-size:8px">&times;</span></label>').css('left',(i/18*100)+'%');
			  	    	}
			  	    	$( ".${giorno}${trasf.xcdtb}ruler" ).append(el);
			  	    	
			  	    }
			  	 	</script>
				</td>
				
				<td class="b-r" style="width:50px; border-bottom:2px solid #77848F" rowspan="2">
					
				</td>
				
				<td class="b-l" style="width:60px; border-bottom:2px solid #77848F" rowspan="2">
					<input type="text" name="totDip${day.xcdel}${trasf.xcdtb}" id="totDip${day.xcdel}${trasf.xcdtb}" 
						value="0" style="width:50px;text-align:right; background: transparent; 
						border:0px;font-weight:bold;color:#000" readonly />
				</td>
				
			  </tr>
			  
		</c:forEach>
	    
	    
	    </tbody>
	    
	  </table>
	  
	  </div>
	  
	  
	  <!--  Totale Settimana -->
      <div class="totaliGiorno">
      <table class="table b-t b-light">
        <tfoot>
	      
	      <!-- Intestazioni -->
	      <tr>
	    	<td style="width:240px"></td>
	    	<td class="barra">
	    		<div style="height:13px;margin-top:-5px" class="${day.xcdel}intestazioni ui-slider"></div>
	    	</td>
	    	<td style="width:90px"></td>
	      </tr>
	      
	      <!-- Totale x Fascia -->
	      <tr>
	    	<td style="width:240px"><b><%=propLang.get("tot1") %></b></td>
	    	<td class="barra">
	    		<div style="height:20px" class="${day.xcdel}tot1 ui-slider"></div>
	    	</td>
	    	<td style="width:90px"><input type="text" name="${day.xcdel}totSett1" id="${day.xcdel}totSett1" 
					value="0" style="width:87px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	      <!-- Totale x Risorsa -->
	      <tr>
	    	<td style="width:240px"><b><%=propLang.get("tot2") %></b></td>
	    	<td class="barra">
	    		<div style="height:20px" class="${day.xcdel}tot2 ui-slider"></div>
	    	</td>
	    	<td style="width:90px"><input type="text" name="${day.xcdel}totSett2" id="${day.xcdel}totSett2" 
					value="0" style="width:87px;text-align:right;background:transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	      <!-- Nomero tabella (da creare) -->
	      <tr>
	    	<td style="width:240px"><b><%=propLang.get("tot3") %></b></td>
	    	<td class="barra">
	    		<div style="height:20px" class="${day.xcdel}tot3 ui-slider"></div>
	    	</td>
	    	<td style="width:90px"><input type="text" name="${day.xcdel}totSett3" id="${day.xcdel}totSett3" 
					value="0" style="width:87px;text-align:right; background: transparent; 
					border:0px;font-weight:bold;color:#000" readonly /></td>
	      </tr>
	     </tfoot>
	            </table>
	           </div>
	           <div class="spacer"></div>
  
	  
	  <script>
		// Righello
	 	for (var i = 0; i <= 18; i+=0.5) {
	      	
	    	if(isInt(i)) {
	    		var intestazioni = $('<label class="ruler">'+((i+5)+1)+'</label>').css('left',(i/18*100)+'%');
	    		
	    		// Totali
	    		var el2 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
	    		var el3 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
	    		var el4 = $('<label class="ruler">|</label>').css('left',(i/18*100)+'%');
	    	}
	    	else {
	    		var intestazioni = $('<label id="${day.xcdel}intestazioni'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">&nbsp;</label>').css('left',(i/18*100)+'%');
	    		
	    		var el2 = $('<label id="${day.xcdel}tot1'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
	    		var el3 = $('<label id="${day.xcdel}tot2'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
	    		var el4 = $('<label id="${day.xcdel}tot3'+((i+0.5)+5)+'" class="ruler" style="color:#000;font-size:14px">0</label>').css('left',(i/18*100)+'%');
	    		
	    	}
	    	$( ".${day.xcdel}intestazioni" ).append(intestazioni);
	    	
	    	$( ".${day.xcdel}tot1" ).append(el2);
	    	$( ".${day.xcdel}tot2" ).append(el3);
	    	$( ".${day.xcdel}tot3" ).append(el4);
	    }
	  	
	 	//var tot3 = ""+$('#tot3${day.xcdel}hidden').val();
	 	var tot3 = "${day.cmp2}";
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
	  	
	  	/*
	 	$('.getModal${day.xcdel}').click(function(e) {
			
			e.preventDefault();
			var href = $(e.target).attr('href');
			
			$.get(href, function(data) {
				$('<div class="modal mia" >' + data + '</div>').modal();
			});
			
		});
	 	*/
	  </script>
	  
</c:forEach>
<!--  fine Schiera giorni -->

<script>
${newDepart}

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
  
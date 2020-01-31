<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<script>




</script>

<div class="modal-dialog modal-lg" id="planModal">
	<div class="modal-content">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">&times;</a>
			<h3 class="font-thin">${employee}</h3>
		</div>
		<div class="modal-body">
		
		
		
		<div id="sked"></div>
		
		
		<div class="row row-sm">
			
			<div class="col-xs-12 col-md-6 ${col}" align="center">
				<div class="bg-light lter wrapper-md r">
	                <span class="h4 block">Monte Ore</span>
	                <div class="h1 m-t-xs block text-ellipsis" style="color:#333">${resto}/
	                	${monteore}</div>
	            </div>
            </div>
			<c:forEach items="${tab}" var="tiles" varStatus="status">
       		<div class="col-xs-12 col-md-6 ${col}" align="center">
				<div class="bg-light lter wrapper-md r">
	                <span class="h4 block">${tiles.cmp9}</span>
	                <div class="h1 m-t-xs block text-ellipsis" style="color:${tiles.xdtab}">${tiles.cmp8}</div>
	            </div>
            </div>
       		</c:forEach>
       	</div>
		
		<hr/>
		
		
		<table class="table b-light">
	      <tr>
	    	<th style="border-top:0px"><%=propLang.get("tipo_turno") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("lun") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("mar") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("mer") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("gio") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("ven") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("sab") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("dom") %></th>
	    	<th style="width:10%;border-top:0px"><%=propLang.get("totali") %></th>
	     </tr>
	     <tbody>
	        <c:forEach items="${tab}" var="plan" varStatus="status">
	      	<tr>
	      		<td class="b-r bg-emp">
	      			<span class="label" style="background-color:${plan.xdtab}">${plan.xcdel}</span>
				</td>
				<td class="b-r">
					<input type="text" name="tot0" id="tot0${plan.cmp10}" value="${plan.cmp1}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
				</td>
				<td class="b-r">
					<input type="text" name="tot1" id="tot1${plan.cmp10}" value="${plan.cmp2}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
				</td>
				<td class="b-r">
					<input type="text" name="tot2" id="tot2${plan.cmp10}" value="${plan.cmp3}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
				</td>
				<td class="b-r">
					<input type="text" name="tot3" id="tot3${plan.cmp10}" value="${plan.cmp4}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
				</td>
				<td class="b-r">
					<input type="text" name="tot4" id="tot4${plan.cmp10}" value="${plan.cmp5}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
				</td>
				<td class="b-r">
					<input type="text" name="tot5" id="tot5${plan.cmp10}" value="${plan.cmp6}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
				</td>
				<td class="b-r">
					<input type="text" name="tot6" id="tot6${plan.cmp10}" value="${plan.cmp7}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
				</td>
				<td class="b-r">
					<input type="text" name="totDef" id="totDef${plan.cmp10}" value="${plan.cmp8}" style="width:50px;text-align:right;background: transparent; 
					border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
				</td>
				
			</tr>
		  </c:forEach>
		  </tbody>
		  
	   	</table>
	   	
	   	
		
		</div>
		<div class="modal-footer">
			<img src="../../images/spacer.gif" onload="setSked(${a} , ${m} , ${d} , [${locations}] , [${events}], '${employee}', 
			'${lun}','${mar}','${mer}','${gio}','${ven}','${sab}','${dom}')"/>
	    
			<a class="btn btn-default" data-dismiss="modal"><%=propLang.get("pop_ba_chiudi") %></a>
		</div>
  	</div>
</div>






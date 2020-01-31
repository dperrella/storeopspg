<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"  %>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
      
	  <div class="table-responsive">
      <table class="table b-t b-light">
        
        <tr>
	    	<th class="b-r"><%=propLang.get("emp") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("lun") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("mar") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("mer") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("gio") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("ven") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("sab") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("dom") %></th>
	    	<th class="b-r" style="width:10%"><%=propLang.get("totali") %></th>
	    </tr>     
	    <tbody id="body-table">
	    
	    <c:forEach items="${tot}" var="emp" varStatus="status">
		<tr>
			<td class="b-r">${emp.xdtab}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp1}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp2}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp3}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp4}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp5}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp6}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp7}</td>
			<td class="b-r" style="color:#000;font-weight:bold;text-align:right">${emp.cmp8}</td>
			
		</tr>
		</c:forEach>
		
	    </tbody>
	    
	    
		<tfoot>
		<tr>
			<td class="b-r"></td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${tt0}</td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${tt1}</td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${tt2}</td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${tt3}</td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${tt4}</td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${tt5}</td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${tt6}</td>
			<td class="b-r" style="font-size:20px;color:#000;font-weight:bold;text-align:right">${ttt}</td>
			
		</tr>
		</tfoot>
	  </table>
	  
	  </div>
	
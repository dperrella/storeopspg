<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
%>
<%
// Legge il file di properties associato alla lingua sul profilo
ReadProperties propf = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
 <footer class="footer hidden-xs no-padder text-center-nav-xs">
   <div class="bg hidden-xs ">
       <div class="dropdown dropup wrapper-sm clearfix">
         <a href="#" class="dropdown-toggle" data-toggle="dropdown">
           <span class="thumb-sm avatar pull-left m-l-xs">                        
             <img src="../../images/${foto}" class="dker" alt="...">
             <i class="on b-black"></i>
           </span>
           <span class="hidden-nav-xs clear">
             <span class="block m-l">
               <strong class="font-bold text-lt">${username}</strong> 
               <b class="caret"></b>
             </span>
             <span class="text-muted text-xs block m-l">${desc}</span>
           </span>
         </a>
         <ul class="dropdown-menu animated fadeInRight aside text-left">                      
           <li>
             <a href="../../app/main/profilo.htm"><%=propf.get("profilo") %></a>
           </li>
           <li>
             <a href="../main/notifiche.htm">
               <span id="spanCount3" class="badge bg-danger pull-right"  style="font-size: 10px;"><%=session.getAttribute("numNotif") %></span>
               <%=propf.get("notifiche") %>
             </a>
           </li>
           <c:if test = "${fn:contains(uri, 'analytics2')}">
	       <li>
             <a href="../../analytics2/popup/tutorial.jsp" data-toggle="ajaxModal"><%=propIdx.get("guida") %></a>
           </li>
	       </c:if>
           <li class="divider"></li>
           <li>
             <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal" ><%=propf.get("esci") %></a>
                   </li>
                 </ul>
               </div>
             </div>            
</footer>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />

<li class="dropdown">
  <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
    <span class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
      <img src="../../images/${foto}" alt="...">
    </span>
    ${username} <b class="caret"></b>
  
  </a>
  <ul class="dropdown-menu animated fadeInRight">            
    <li>
      <a href="../../app/main/profilo.htm"><%=propIdx.get("profilo") %></a>
    </li>
    <li>
      <a href="../../app/config/notification-list.htm">
        <%=propIdx.get("notifiche") %>
      </a>
    </li>
    <c:if test = "${fn:contains(uri, 'analytics2')}">
    <li>
      <a href="../../analytics2/popup/tutorial.jsp" data-toggle="ajaxModal"><%=propIdx.get("guida") %></a>
    </li>
    </c:if>
    <li class="divider"></li>
    <li>
      <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal"><%=propIdx.get("esci") %></a>
    </li>
  </ul>
</li>
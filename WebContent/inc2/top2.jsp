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
    <li>
      <a href="../../app/main/docs.html"><%=propIdx.get("guida") %></a>
    </li>
    <li class="divider"></li>
    <li>
      <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal"><%=propIdx.get("esci") %></a>
    </li>
  </ul>
</li>
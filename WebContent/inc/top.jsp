<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
	import="pkg.properties.ReadProperties"
    pageEncoding="ISO-8859-1"
%>
<%
// Legge il file di properties associato alla lingua sul profilo
ReadProperties propt = new ReadProperties(session.getAttribute("language").toString()+".properties");

%>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
              <span class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
                <img src="../../images/${foto}" alt="...">
              </span>${username}<b class="caret"></b>&nbsp;&nbsp;&nbsp;&nbsp;
            
            </a>
            <ul class="dropdown-menu animated fadeInRight" style="margin-right: 15px;">            
              <li>
                <a href="../../app/main/profilo.htm"><%=propt.get("profilo") %></a>
              </li>
              <li>
                <a href="../main/notifiche.htm">
                  <span id="spanCount2" class="badge bg-danger pull-right" style="font-size: 10px;"><%=session.getAttribute("numNotif") %></span>
                  <%=propt.get("notifiche") %>
                </a>
              </li>
              <li>
                <a href="../../app/main/docs.html"><%=propt.get("guida") %></a>
              </li>
              <li class="divider"></li>
              <li>
                <a href="../../app/main/modal.lockme.jsp" data-toggle="ajaxModal"><%=propt.get("esci") %></a>
              </li>
            </ul>
          </li>
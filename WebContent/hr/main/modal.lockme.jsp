<%@ page 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<div class="modal-over">
  <div class="modal-center animated fadeInUp text-center" style="width:200px;margin:-80px 0 0 -100px;">
    <div class="thumb-md"><img src="../../images/<%=session.getAttribute("foto") %>" class="img-circle b-a b-light b-3x"></div>
    <p class="text-white h4 m-t m-b"><%=session.getAttribute("username") %></p>
    <div class="input-group">
      <input type="hidden" id="modalUsername" name="username" value="<%=session.getAttribute("username") %>">
      <input type="password" id="modalPassword" name="password" class="form-control text-sm btn-rounded" placeholder="<%=propLang.get("inserisciPassword")%>">
      <span class="input-group-btn">
        <button class="btn btn-success btn-rounded" type="button" onclick="controlLogin('<%=session.getAttribute("username") %>',''+document.getElementById('modalPassword').value+'')" ><i class="fa fa-arrow-right"></i></button>
      </span>
    </div>
    <br/><br/>
    
    <a href="../../signin.jsp?msg=O" class="btn btn-success btn-lg" style="width:200px"><i class="fa fa-sign-out"></i> <%=propLang.get("tornaLogin")%></a>
  	<!--  
  	<a href="../../signin_MC.jsp?msg=O" class="btn btn-success btn-lg" style="width:200px"><i class="fa fa-sign-out"></i> <%=propLang.get("tornaLogin")%></a>
  	-->
  </div>
</div>
<%@ page 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String url2 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

if(url2.contains("localhost")) {
	url2 = url2.replaceAll("https://", "http://");
}
else {
	url2 = url2.replaceAll("http://", "https://");
	url2 = url2.replaceAll(":80", "");
}

String origin = ""+session.getAttribute("origin");
origin = Utility.noNull(origin);

String _signout = "";

if(origin.equalsIgnoreCase("Microsoft")) {
	_signout = "https://login.microsoftonline.com/common/oauth2/v2.0/logout?client_id=96014a6f-3040-4e2c-ac0f-f497788b5421&post_logout_redirect_uri="+url2+"/signinV2.htm?action=signout";
	//_signout = "https://login.microsoftonline.com/057b692d-19f3-4f35-be28-764d78ae6997/oauth2/logout?client_id=cd25dc42-1537-4e99-9607-6bf04b8749a8&post_logout_redirect_uri="+url2+"/signin.jsp?msg=O";
}
else {
	_signout = url2+"/signinV2.htm?action=signout";
}

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
    <a href="<%=_signout %>" class="btn btn-success btn-lg" style="width:200px"><i class="fa fa-sign-out"></i> <%=propLang.get("tornaLogin")%></a>
  	
    <!-- 
    <a href="../../signinV2.htm?action=signout" class="btn btn-success btn-lg" style="width:200px"><i class="fa fa-sign-out"></i> <%=propLang.get("tornaLogin")%></a>
  	-->
  </div>
</div>
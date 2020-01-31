<%@ page 
language="java"
import="pkg.utility.Utility"
import="pkg.utility.JSendMail"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String msg = "";

try {
	String dest = request.getParameter("dest");
	String ogg = request.getParameter("ogg");
	String mes = request.getParameter("mes");
	String allegato = request.getParameter("allegato");
	
	String mitt = ""+session.getAttribute("mail");
	mitt=Utility.noNull(mitt);
	
	if(mitt.trim().length()==0)
		mitt="info@smarthr.it";
	
	// Invia mail
	JSendMail.msgMail(dest,ogg,mes,allegato+",",mitt);
	
	msg="<br/><div class=\"alert alert-success\"><strong>"+propLang.get("msgMailOk")+" "+dest+".</strong></div>";
}
catch(Exception e) {
	msg="<br/><div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";
}

%><%=msg%>
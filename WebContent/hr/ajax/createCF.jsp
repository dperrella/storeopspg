<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.CodiceFiscale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String nome = request.getParameter("nome");
String cognome = request.getParameter("cognome");
String sesso = request.getParameter("sesso");
String dataNascita = request.getParameter("dataNascita");
String codLuogoNascita = request.getParameter("codLuogoNascita");

String cf = CodiceFiscale.getCF(nome, cognome, sesso, dataNascita, codLuogoNascita);
%>
<%=cf%>
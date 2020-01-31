<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Date"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

Date date = new Date();
DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
String currentYear = dateIso.format(date).substring(0,4);
%>
<div class="modal-dialog modal-sm">
  <div class="modal-content">
  	<form action="financial-calendar-month.htm" method="post">
		
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Create Calendar</h3>
	</div>
	<div class="modal-body">
		<input type="hidden" name="caltype" value="M"/>
		<input type="hidden" name="azione" value="S"/>
		<input type="hidden" name="tipoForm" value="I"/>
		<input type="hidden" name="idcus" value="<%=session.getAttribute("idcus")%>"/>
		
		<label><%=propLang.get("sel_year") %></label>
		<select class="form-control" name="year" id="year">
		<%
		String sel = "";
		for(int i=2010;i<2025;i++) {
			if(i==Integer.parseInt(currentYear)) {
				sel = "selected";
			}
		%>
			<option value="<%=i %>" <%=sel %>><%=i %></option>
		<%
			sel = "";
		}
		%>
		</select>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label><%=propLang.get("month_start") %></label>
		<select class="form-control" name="monthstart" id="monthstart">
			<option value="0" selected><%=propLang.get("gen") %></option>
			<option value="1"><%=propLang.get("feb") %></option>
			<option value="2"><%=propLang.get("mar2") %></option>
			<option value="3"><%=propLang.get("apr") %></option>
			<option value="4"><%=propLang.get("mag") %></option>
			<option value="5"><%=propLang.get("giu") %></option>
			<option value="6"><%=propLang.get("lug") %></option>
			<option value="7"><%=propLang.get("ago") %></option>
			<option value="8"><%=propLang.get("set") %></option>
			<option value="9"><%=propLang.get("ott") %></option>
			<option value="10"><%=propLang.get("nov") %></option>
			<option value="11"><%=propLang.get("dic") %></option>
		</select>
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<button type="submit" class="btn btn-primary btn-lg"><%=propLang.get("btnConferma") %></button>
	</div>
	</form>
			
  </div>
</div>
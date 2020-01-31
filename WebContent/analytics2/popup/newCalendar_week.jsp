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
  	<form action="financial-calendar-week.htm" method="post">
		
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Create Calendar</h3>
	</div>
	<div class="modal-body">
		<input type="hidden" name="caltype" value="W"/>
		<input type="hidden" name="azione" value="S"/>
		<input type="hidden" name="tipoForm" value="I"/>
		<input type="hidden" name="idcus" value="<%=session.getAttribute("idcus")%>"/>
		
		<label><%=propLang.get("sel_year") %></label>
		<select class="form-control" name="year" id="year" onchange="chgYear(this.value)">
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
		
		<label><%=propLang.get("week_start") %></label>
		<select class="form-control" name="weekstart" id="weekstart" onchange="chgDay(this.value)">
			<option value="M" selected><%=propLang.get("lun") %></option>
			<option value="S"><%=propLang.get("dom") %></option>
		</select>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>Weeks Number</label>
		<input type="number" class="form-control" id="weeknumber" name="weeknumber" value="52"/>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		
		<label>First Week Start</label>
		<input type="text" class="datepicker-input2 form-control" id="firstweek" name="firstweek" value=""/>
		
		
		
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="setCal();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<button type="submit" class="btn btn-primary btn-lg"><%=propLang.get("btnConferma") %></button>
	</div>
	</form>
			
  </div>
</div>
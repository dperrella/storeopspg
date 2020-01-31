<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String id_departments = ""+request.getParameter("id_departments");
id_departments = id_departments.replaceAll("\\?cd=undefined","");
String id_employee = ""+request.getParameter("id_employee");
id_employee = id_employee.replaceAll("\\?cd=undefined","");
String year = ""+request.getParameter("year");
year = year.replaceAll("\\?cd=undefined","");
String week = ""+request.getParameter("week");
week = week.replaceAll("\\?cd=undefined","");
String day = ""+request.getParameter("day");
day = day.replaceAll("\\?cd=undefined","");

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
conn=Utility.getConnHR();
st = conn.createStatement();
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("pop_ap_title") %></h3>
	</div>
	<div class="modal-body">

		<input type="hidden" id="id_departments" name="id_departments" value="<%=id_departments %>"/>
		<input type="hidden" id="id_employee" name="id_employee" value="<%=id_employee %>"/>
		<input type="hidden" id="year" name="year" value="<%=year %>"/>
		<input type="hidden" id="week" name="week" value="<%=week %>"/>
		<input type="hidden" id="day" name="day" value="<%=day %>"/>
		
			
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("pop_ap_tipo") %></label>
	          
	          <select id="addPlan_id_planification_type" name="id_planification_type" 
	          	class="form-control" data-required="true" onchange="controllaTipo(this.value)">
		        <option value="">-</option>
<%
//sql  =  "select * from hr_planification_type ";
sql  =  "select * from hr_planification_type where upper(id) <> upper('da24f14a-1436-4295-8f99-8b1a450b6277') ";
rs=st.executeQuery(sql);
while(rs.next()) {
%>
				<option value="<%=rs.getString("id")%>"><%=rs.getString("description")%></option>
<%
}
rs.close();
%>
		      </select>  
	                   	    
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	    	<div class="col-sm-2">
	          <label><%=propLang.get("pop_ap_oraini") %></label>
	          <select id="addPlan_ora_ini" name="addPlan_ora_ini" class="form-control" data-required="true">
	          	<option value="">-</option>
	          	<option value="06:00">06:00</option>
	          	<option value="06:30">06:30</option>
	          	<option value="07:00">07:00</option>
	          	<option value="07:30">07:30</option>
	          	<option value="08:00">08:00</option>
	          	<option value="08:30">08:30</option>
	          	<option value="09:00">09:00</option>
	          	<option value="09:30">09:30</option>
	          	<option value="10:00">10:00</option>
	          	<option value="10:30">10:30</option>
	          	<option value="11:00">11:00</option>
	          	<option value="11:30">11:30</option>
	          	<option value="12:00">12:00</option>
	          	<option value="12:30">12:30</option>
	          	<option value="13:00">13:00</option>
	          	<option value="13:30">13:30</option>
	          	<option value="14:00">14:00</option>
	          	<option value="14:30">14:30</option>
	          	<option value="15:00">15:00</option>
	          	<option value="15:30">15:30</option>
	          	<option value="16:00">16:00</option>
	          	<option value="16:30">16:30</option>
	          	<option value="17:00">17:00</option>
	          	<option value="17:30">17:30</option>
	          	<option value="18:00">18:00</option>
	          	<option value="18:30">18:30</option>
	          	<option value="19:00">19:00</option>
	          	<option value="19:30">19:30</option>
	          	<option value="20:00">20:00</option>
	          	<option value="20:30">20:30</option>
	          	<option value="21:00">21:00</option>
	          	<option value="21:30">21:30</option>
	          	<option value="22:00">22:00</option>
	          	<option value="22:30">22:30</option>
	          	<option value="23:00">23:00</option>
	          	<option value="23:30">23:30</option>
	          	<option value="24:00">24:00</option>
	          </select>
	        </div>
	        
	        <div class="col-sm-2">
	          <label><%=propLang.get("pop_ap_orafin") %></label>
	          <select id="addPlan_ora_fin" name="addPlan_ora_fin" class="form-control" data-required="true">
	          	<option value="">-</option>
	          	<option value="06:00">06:00</option>
	          	<option value="06:30">06:30</option>
	          	<option value="07:00">07:00</option>
	          	<option value="07:30">07:30</option>
	          	<option value="08:00">08:00</option>
	          	<option value="08:30">08:30</option>
	          	<option value="09:00">09:00</option>
	          	<option value="09:30">09:30</option>
	          	<option value="10:00">10:00</option>
	          	<option value="10:30">10:30</option>
	          	<option value="11:00">11:00</option>
	          	<option value="11:30">11:30</option>
	          	<option value="12:00">12:00</option>
	          	<option value="12:30">12:30</option>
	          	<option value="13:00">13:00</option>
	          	<option value="13:30">13:30</option>
	          	<option value="14:00">14:00</option>
	          	<option value="14:30">14:30</option>
	          	<option value="15:00">15:00</option>
	          	<option value="15:30">15:30</option>
	          	<option value="16:00">16:00</option>
	          	<option value="16:30">16:30</option>
	          	<option value="17:00">17:00</option>
	          	<option value="17:30">17:30</option>
	          	<option value="18:00">18:00</option>
	          	<option value="18:30">18:30</option>
	          	<option value="19:00">19:00</option>
	          	<option value="19:30">19:30</option>
	          	<option value="20:00">20:00</option>
	          	<option value="20:30">20:30</option>
	          	<option value="21:00">21:00</option>
	          	<option value="21:30">21:30</option>
	          	<option value="22:00">22:00</option>
	          	<option value="22:30">22:30</option>
	          	<option value="23:00">23:00</option>
	          	<option value="23:30">23:30</option>
	          	<option value="24:00">24:00</option>
	          </select>
	        </div>
	    </div>
	    
	    
	    			
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<a class="btn btn-success btn-lg" onclick="addPlanification()"><%=propLang.get("pop_ap_conferma") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

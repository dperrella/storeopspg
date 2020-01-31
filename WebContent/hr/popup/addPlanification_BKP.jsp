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
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");
String tp = request.getParameter("tp");

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
conn=Utility.getConnAdmin();
st = conn.createStatement();
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("pop_ap_title") %></h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	    	<div class="col-sm-12">
		        <label><%=propLang.get("pop_ap_data") %></label><br/>
		        <select name="addPlan_day" id="addPlan_day" style="width:100%" class="form-control" multiple="multiple" data-required="true">
		        	<!-- 
		        	<option value="">-</option>
		        	-->
<%
//=====================
// Settimana
String giorno = "";
//Formato data
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
// Get calendar set to current date and time
Calendar cweek = Calendar.getInstance(Locale.GERMAN);  

// Set the calendar to monday of the current week
cweek.set(Calendar.YEAR, Integer.parseInt(year));     
cweek.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
cweek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

String newDepart = "";

for (int d = 0; d < 7; d++) {
    
    
    if(d==0)
		giorno = propLang.get("lun")+" "+sdf.format(cweek.getTime());
	if(d==1)
		giorno = propLang.get("mar")+" "+sdf.format(cweek.getTime());
	if(d==2)
		giorno = propLang.get("mer")+" "+sdf.format(cweek.getTime());
	if(d==3)
		giorno = propLang.get("gio")+" "+sdf.format(cweek.getTime());
	if(d==4)
		giorno = propLang.get("ven")+" "+sdf.format(cweek.getTime());
	if(d==5)
		giorno = propLang.get("sab")+" "+sdf.format(cweek.getTime());
	if(d==6)
		giorno = propLang.get("dom")+" "+sdf.format(cweek.getTime());
%>
					<option value="<%=sdf.format(cweek.getTime()) %>"><%=giorno %></option>
<%	
	cweek.add(Calendar.DATE, 1);

}
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
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("pop_ap_tipo") %></label>
	          
	          <select id="addPlan_id_planification_type" name="id_planification_type" 
	          	class="form-control" data-required="true" onchange="controllaTipo(this.value)">
		        <option value="">-</option>
<%
sql  =  "select * from hr_planification_type ";
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
	        <div class="col-sm-12">
	          <label><%=propLang.get("pop_ap_dip") %></label>
	          
	          <select id="addPlan_id_employee" name="addPlan_id_employee" 
	          	class="form-control" data-required="true">
		        <option value="">-</option>
<%
/*
sql  =  "select c.id, c.name, c.surname, d.description, id_departments from "+
		"hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
		"where a.id=b.id_roles "+
		"and b.id_employee = c.id "+ 
		"and b.id_departments = d.id "+
		"and d.id = '"+id_departments+"' "+
		"group by c.id, c.name, c.surname, d.description, id_departments ";
*/

sql = "select c.id, c.name, c.surname, d.description, v.id_departments "
		+ "from vw_emp_dep_indate v, hr_employee c, hr_departments d "
		+ "where c.id = v.id_employee and d.id = v.id_departments "
		+ "and anno = "+year+" and settimana = "+week+" and v.id_Departments = '"+id_departments+"' "
		+ "group by c.id, c.name, c.surname, d.description, v.id_departments ";

System.out.println(sql);
rs=st.executeQuery(sql);
while(rs.next()) {
%>
				<option value="<%=rs.getString("id")%>"><%=rs.getString("name")+" "+rs.getString("surname") %></option>
<%
}
rs.close();
%>
		      </select>  
	                   	    
	        </div>
	    </div>
	    
						    
					
	</div>
	<div class="modal-footer">
	    <img src="../../images/spacer.gif" onload="setMulti();"/>
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

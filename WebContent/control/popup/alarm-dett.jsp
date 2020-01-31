<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
String id = request.getParameter("id");
id = Utility.noNull(id);
id=id.replaceAll("\\?cd=undefined","");

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";

String code = "";
String description = "";
String threshold = "";
String timeunit = "";
String kpi = "";
String controlfrequency = "";
String sign = "";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnData();
	st = conn.createStatement();
	sql  =  "select a.id, a.code, "+
			"b.description description_it, "+
			"c.description description_en, "+
			"d.description description_es, "+
			"a.threshold, a.timeunit, "+
			"a.kpi, a.adaptive, a.controlfrequency, a.lastrun, a.sign, "+
			"a.hour_from, a.hour_to, a.store_time "+
			"from an_alarm_report a, "+
			"lang_alarm_report b, lang_alarm_report c, lang_alarm_report d "+
			"where a.id = b.id_alarm_report and b.lang = 'IT' "+
			"and a.id = c.id_alarm_report and c.lang = 'EN' "+
			"and a.id = d.id_alarm_report and d.lang = 'ES' "+
			"and a.id = '"+id+"' ";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	if(rs.next()) {
		
		code = rs.getString("code");
		if(session.getAttribute("language").toString().equalsIgnoreCase("IT"))
			description = rs.getString("description_it");
		else if(session.getAttribute("language").toString().equalsIgnoreCase("EN"))
			description = rs.getString("description_en");
		else if(session.getAttribute("language").toString().equalsIgnoreCase("ES"))
			description = rs.getString("description_es");
		
		threshold = rs.getString("threshold");
		timeunit = rs.getString("timeunit");
		kpi = rs.getString("kpi");
		controlfrequency = rs.getString("controlfrequency");
		sign = rs.getString("sign");
		
	}
	rs.close();
	
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Alarm Detail</h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aat_code") %></label>
	          <input type="text" id="code" name="code" class="form-control" value="<%=code%>" disabled>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aat_descrizione") %></label>
	          <input type="text" id="description" name="description" class="form-control" value="<%=description%>" disabled>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("aat_threshold") %></label>
	          <input type="text" data-type="number" data-required="true" id="threshold" name="threshold" class="form-control" value="<%=threshold%>" disabled>  
	        </div>
	        <div class="col-sm-8">
	          <label>Condition</label>
	          <%
	          String sel1="";
	          String sel2="";
	          if(sign.trim().equals(">="))
	        	  sel1="selected";
	          else
	        	  sel2="selected";
	          %>
	          <select id="sign" name="sign" class="form-control parsley-validated" disabled>
	          	<option value=">=" <%=sel1 %>>Trigger enabled if Threashold IS Reached</option>
	          	<option value="<" <%=sel1 %>>Trigger enabled if Threashold IS NOT Reached</option>
	          </select>  
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Times Frame KPI Calculation</label>
	          <%
	          sel1="";
	          sel2="";
	          String sel3="";
	          String sel4="";
	          String sel5="";
	          String sel6="";
	          
	          if(timeunit.trim().equals("3600"))
	        	  sel1="selected";
	          else if(timeunit.trim().equals("21600"))
	        	  sel2="selected";
	          else if(timeunit.trim().equals("43200"))
	        	  sel3="selected";
	          else if(timeunit.trim().equals("86400"))
	        	  sel4="selected";
	          else if(timeunit.trim().equals("172800"))
	        	  sel5="selected";
	          else if(timeunit.trim().equals("259200"))
	        	  sel6="selected";
	          
	          %>
	          <select id="timeunit" name="timeunit" class="form-control parsley-validated" disabled>
	          	<option value="3600" <%=sel1 %>>1 Hour</option>
	          	<option value="21600" <%=sel2 %>>6 Hours</option>
	          	<option value="43200" <%=sel3 %>>12 Hours</option>
	          	<option value="86400" <%=sel4 %>>24 Hours</option>
	          	<option value="172800" <%=sel5 %>>48 Hours</option>
	          	<option value="259200" <%=sel6 %>>72 Hours</option>
	          </select>
	        </div>
	            
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>KPI</label>
	          <input type="text" id="kpi" name="kpi" class="form-control" value="<%=kpi%>" disabled>   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Control Frequency</label>
	          <input type="text" id="controlfrequency" name="controlfrequency" class="form-control" value="<%=controlfrequency%> Hours" disabled>   
	        </div>
	    </div>
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
	</div>
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>

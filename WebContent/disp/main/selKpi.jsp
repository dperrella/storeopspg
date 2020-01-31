<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String code = ""+request.getParameter("code");
String drv = ""+request.getParameter("drv");

String kpi = ""+request.getParameter("kpi");
kpi = Utility.noNull(kpi);
String kpi_old = kpi;
//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String kpiList = "";

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnDispatcher();
	st = conn.createStatement();
	sql = "select * from public.ztables where xcdtb = 'KPI' and calculated = false ";
	rs = st.executeQuery(sql);
	String sel = "";
	while(rs.next()) {
		if(rs.getString("xcdel").trim().equals(kpi.trim())) {
			sel = "selected";
		}
		kpiList+="<option value=\""+rs.getString("xcdel").trim()+"\" "+sel+">"+rs.getString("xcdel").trim()+" - "+rs.getString("xdtab").trim()+"</option>";
		sel = "";
	}
	rs.close();
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	if(st!=null)
		st.close();
	if(conn!=null)
		conn.close();
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Event <%=code %> / <%=drv %></h3>
	</div>
	<div class="modal-body">
		
		<input type="hidden" id="code" name="code" value="<%=code %>"/>
		<input type="hidden" id="drv" name="drv" value="<%=drv %>"/>
		<input type="hidden" id="kpi_old" name="kpi_old" value="<%=kpi_old %>"/>
		
		<p class="h4">Select Kpi</p>
		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-10">
	          <label >KPI</label>
	          <select class="form-control" name="kpi_sel" id="kpi_sel" onchange="modForm(this.value)">
	          	<option value="">-</option>
	          	<%=kpiList %>
              </select>
	        </div>
	        <div class="col-sm-2">
	        	<label>&nbsp;</label>
	        	<a href="#" id="kpi_rel" onclick="relKpi()" class="btn-block btn btn-default"><i class="fa fa-check"></i></a>
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <p class="h4">Or Create New Kpi</p>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-4">
	          <label><%=propLang.get("aki_code") %></label>
	          <input type="text" id="kpi_code" name="kpi_code" class="form-control" value="">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aki_name") %></label>
	          <input type="text" id="kpi_name" name="kpi_name" class="form-control" value="">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label><%=propLang.get("aki_desc") %></label>
	          <input type="text" id="kpi_desc" name="kpi_desc" class="form-control" value="">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-6">
	          <label><%=propLang.get("aki_symb") %></label>
			  <select id="kpi_symbol" name="kpi_symbol" class="form-control">
					<option value="" selected> -- Select -- </option>
					<option value="&euro;">&euro;</option>
					<option value="%">%</option>
					<option value="$">$</option>
			  </select>    
	        </div>
	        <div class="col-sm-6">
	            <label><%=propLang.get("aki_color") %></label>
		        <div id="cp2" class="input-group colorpicker-component">
				    <input id="kpi_color" name="kpi_color" type="text" value="" class="form-control" />
				    <span class="input-group-addon"><i></i></span>
				</div>
                 
		    </div>
	    </div>
	    
    </div>
    <div class="modal-footer">
		<img src="../../images/spacer.gif" onload="valutateModal();callColor();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-success btn-lg" id="kpi_btn" data-dismiss="modal" onclick="writeKpi();"><%=propLang.get("btnConferma") %></a>
	</div>
  </div>
</div>

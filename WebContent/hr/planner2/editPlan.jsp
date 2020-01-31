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
String idtype=request.getParameter("idtype");
String id_employee=request.getParameter("id_employee");
String id_departments=request.getParameter("id_departments");
String id_departments_sel=request.getParameter("id_departments_sel");

//Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
conn=Utility.getConnHR();
st = conn.createStatement();
String employee = "";
sql = "select name, surname from hr_employee where id = '"+id_employee+"' ";
rs=st.executeQuery(sql);
if(rs.next()) {
	employee = rs.getString("name")+" "+rs.getString("surname");
}
rs.close();
%>
<div id="editModal" class="modal-dialog modal-sm">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h4 class="font-thin"><%=employee %></h4>
	</div>
	<div class="modal-body">
		<%
			sql = "select id, description from hr_departments where id_customer = "+session.getAttribute("idcus")+" ";
			rs=st.executeQuery(sql);
			
			
			if(idtype.equals("da24f14a-1436-4295-8f99-8b1a450b6277")) {
				
		%>			
		
		<div class="form-group pull-in clearfix">
			<div class="col-sm-12">
				<div class="checkbox m-r-xs">
                  <label class="i-checks">
                    <input type="checkbox" id="insTrasf" onclick="showHide()"><i></i> Inserisci Trasferta
                  </label>
                </div>
			</div>
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
		    
		
		<%
			}
		%>
	    
	    <div id="divTrasf">
		    
			<div class="form-group pull-in clearfix" >
		    	<div class="col-sm-12">	    
					<label>Seleziona Dipartimento per Trasferta</label>
					<select name="trasf_id_departments" id="trasf_id_departments" 
			       		class="form-control ss">
			       		<!-- 
			       		<option value="">-</option>
			       		-->
			       		<%
			       		String sel = "";
			       		while(rs.next()) {	
			       			if(rs.getString("id").equals(id_departments_sel))
			       				sel = "selected";
			       		%>
			       		<option value="<%=rs.getString("id")%>" 
			       			data-description="<%=rs.getString("description")%>"  <%=sel %>><%=rs.getString("description") %></option>
			       		<%
			       			sel = "";
			       		}
			       		rs.close();
			       		%>
				    </select> 
				    
				</div>
			</div>
			
			
		</div>
		
		<div id="divDurata">
		
			<div class="form-group pull-in clearfix">
				<div class="col-sm-12">
				
					<label><%=propLang.get("tipo_turno") %></label>
					<select name="planification_type2" id="planification_type2" 
	                    class="form-control col-sm-2 ss">
	                    <option value="">-</option>
	                    <%
	                    sql = "select id, description, color, col from hr_planification_type ";
	        			rs=st.executeQuery(sql);
	        			sel = "";
	        			while(rs.next()) {
	        				if(rs.getString("id").equals(idtype))
			       				sel = "selected";
	                    %>
	                    <option value="<%=rs.getString("id")%>" <%=sel%>
							data-description="<%=rs.getString("description")%>" 
							data-css="<%=rs.getString("color").trim()%>"><%=rs.getString("description")%></option>
						<%
							sel="";
	        			}
	        			rs.close();
						%>
	                </select>
	            </div>
	        </div>
			
			<div class="form-group pull-in clearfix">
				<div class="col-sm-12">
					<div class="checkbox m-r-xs">
	                  <label class="i-checks">
	                    <input type="checkbox" id="chktime" onclick="showHide2()"><i></i> Visualizza Inizio e Fine
	                  </label>
	                </div>
				</div>
			</div>
			
			<div class="line line-dashed b-b line-lg pull-in"></div>
			
			
			<div id="divTime">
				<div class="form-group pull-in clearfix" >
					<div id="datepairExample">
				    	<div class="col-sm-6">	 
							<label><%=propLang.get("sm_f_hour") %></label>
							<input type="text" name="fromhour" id="fromhour" class="time start form-control" placeholder="From Time" value="">
						</div>
						<div class="col-sm-6">	 
							<label><%=propLang.get("sm_t_hour") %></label>
								<input type="text" name="tohour" id="tohour" class="time end form-control" placeholder="To Time" value="">
						
						</div>
					</div>
				</div>
				
			</div>
			
			
			<div id="divTime2">
			
				<label><%=propLang.get("duration") %></label>
				<input id="duration2" name="duration2" class="slider slider-horizontal form-control" type="text" value="4" style="width:100%" data-slider-min="0" data-slider-max="18" 
					data-slider-step="0.25" data-slider-value="4" data-slider-orientation="horizontal" >
				      
			</div>                		
			
		</div>
		
		<div class="line line-dashed b-b line-lg pull-in"></div>
						     
		
					
		<!--  
		<img src="../../images/spacer.gif" onload="setDatePair();selpick();$('#divTrasf').hide();$('#divTime').hide();$('#duration2').slider();"/>
	    -->
	    <img src="../../images/spacer.gif" onload="setDatePair();selpick();$('#divTrasf').hide();typeVis();$('#duration2').slider();"/>
	    
	    
	    <div align="right" class="no-padder">
		<a class="btn btn-default cls" data-dismiss="modal" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();"><%=propLang.get("pop_ap_chiudi") %></a>
	
		<a href="#" onclick="if(confirm('<%=propLang.get("msgDelete")%>?')) { deletePlan();$( '.modal-backdrop').remove();$( '.modal.in' ).remove(); $('#editModal .cls').click()}" 
			class="btn btn-danger btn-lg" style="width:80px"
			>Delete</a>
		<%
		//if(idtype.equals("da24f14a-1436-4295-8f99-8b1a450b6277")) {
		%>
		<a href="#" onclick="modificaPlan($('#trasf_id_departments').val(), $('#trasf_id_departments').find(':selected').data('description'), '<%=idtype %>');$( '.modal-backdrop').remove();$( '.modal.in' ).remove();" 
			class="btn btn-primary btn-lg" style="width:80px"
			><%=propLang.get("save") %></a>
		<%
		//}
		%>
		</div>
	</div>
	<!-- 
	<div class="modal-footer">
	    
	</div>
	-->
  </div>
</div>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();

%>
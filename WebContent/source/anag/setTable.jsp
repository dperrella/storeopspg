<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
String tipo = request.getParameter("tipo");
tipo = Utility.noNull(tipo);
tipo=tipo.replaceAll("\\?cd=undefined","");

String tipoForm = request.getParameter("tipoForm");
tipoForm = Utility.noNull(tipoForm);
tipoForm=tipoForm.replaceAll("\\?cd=undefined","");

String key = request.getParameter("key");
key = Utility.noNull(key);
key=key.replaceAll("\\?cd=undefined","");

String value = request.getParameter("value");
value = Utility.noNull(value);
value=value.replaceAll("\\?cd=undefined","");

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

Connection conn = null;
Statement st = null;
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Record Detail</h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
		
			<%
			if(tipoForm.trim().equals("D")) {
			%>
			<div class="col-sm-12">
				<div class="alert alert-warning"><button type="button" class="close" data-dismiss="alert">&times;</button>
  				<i class="fa fa-ban-circle"></i><strong>Confirm Delete Record</strong></div>
			</div>
			<%
			}
			%>
	        <div class="col-sm-12">
	          <label>Description</label>
	          <input type="hidden" name="tipo" id="tipo" value="<%=tipo%> ">
	          <input type="hidden" name="tipoForm" id="tipoForm" value="<%=tipoForm%> ">
	          <input type="hidden" name="key" id="key" value="<%=key%> ">
	          <input type="text" id="value" name="value" class="form-control" value="<%=value %>"/>
	        </div>
	    </div>
	        
	        <%
	        int count = 0;
        	
	        try {
	        	conn = Utility.getConnData();
	        	st = conn.createStatement();
	        	ResultSet rs = null;
	        	
	        	String sql = "";
	        	String fld = "";
	        	
	        	if(tipo.equals("V")) {
	        		fld = "idvendor";
	        	}
	        	else if(tipo.equals("B")) {
	        		fld = "idbrand";
		        }
	        	else if(tipo.equals("C")) {
	        		fld = "idcategory";
		        }
				
				sql = "select count(0) num from ats.items where "+fld+" = "+key+" ";
				rs = st.executeQuery(sql);
				if(rs.next()) {
					count = rs.getInt("num");
				}
				rs.close();
				
				if(count>0&&tipoForm.equals("D")) {
			%>
			<div class="line line-dashed b-b line-lg pull-in"></div>
	    	
	    	<div class="form-group pull-in clearfix">
		        <div class="col-sm-12">
		          <label>Warning!</label>
				  <%=count %> Items found with this Vendor. 
		        </div>
		    </div>
			    
			
			<%
				}
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
	   
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<a class="btn btn-primary btn-lg" data-dismiss="modal" onclick="saveTable()"><%=propLang.get("save") %></a>
	</div>
  </div>
</div>
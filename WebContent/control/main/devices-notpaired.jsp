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

String tipoForm = request.getParameter("tipoForm");
tipoForm = Utility.noNull(tipoForm);

String accountid = request.getParameter("accountid");
accountid = Utility.noNull(accountid);
String storeid = request.getParameter("storeid");
storeid = Utility.noNull(storeid);
String doorid = request.getParameter("doorid");
doorid = Utility.noNull(doorid);
doorid=doorid.replaceAll("\\?cd=undefined","");

System.out.println(accountid+" "+storeid+" "+doorid);


//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String sql = "";
%>


<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Devices Not Paired</h3>
	</div>
	<div class="modal-body">
<%
// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnControl();
	st = conn.createStatement();
	
	int count_customer = 0;
	String id_customer = "";
	String companyname = "";
	String customer_description = "";
	boolean customer_create = false;
	sql   = "select * from customers where account_code = '"+accountid+"' ";
	rs = st.executeQuery(sql);
	if(rs.next()) {
		count_customer+=1;
		
		id_customer = rs.getString("id");
		companyname = rs.getString("companyname");
		customer_description = rs.getString("description");
		customer_create = true;
	}
	rs.close();
%>
		<div class="panel-footer bg-light">
        	<div class="padder">
            	<span class="h4 block">Customer</span>
            </div>
        </div>
        <br/>
<%
	if(customer_create==true) {
%>
		
		<div class="form-group">
            <label>Company Name</label>
            <input type="text" id="companyname" name="companyname" class="form-control" value="<%=companyname%>">   
        </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group">
	        <label>Description</label>
	        <input type="text" id="customer_description" name="customer_description" class="form-control" value="<%=customer_description%>">                        
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
		
<%
	}
	if(customer_create==false) {
%>
		
		<div class="form-group text-right">
	      	<a href="../../app/anag/customers-dett.htm?tipoForm=I" class="btn btn-primary btn-block btn-lg">Create Customer</a>
	    </div>
		  	
<%
	}
	else {
		
		int count_store = 0;
		String id_store = "";
		String store_description = "";
		String address = "";
		boolean store_create = false;
		sql   = "select * from stores where store_code = '"+storeid.replaceAll(" ","")+"' and id_customer = "+id_customer+" ";
		System.out.println(sql);
		
		rs = st.executeQuery(sql);
		if(rs.next()) {
			count_store+=1;
			
			id_store = rs.getString("id");
			store_description = rs.getString("description");
			address = rs.getString("address");
			store_create = true;
		}
		rs.close();
%>
		
		<div class="panel-footer bg-light">
        	<div class="padder">
            	<span class="h4 block">Store</span>
            </div>
        </div>
        <br/>
<%
		if(store_create==true) {
%>
		
		<div class="form-group">
            <label>Store Description</label>
            <input type="text" id="store_description" name="store_description" class="form-control" value="<%=store_description%>">   
        </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	    
	    <div class="form-group">
	        <label>Address</label>
	        <input type="text" id="address" name="address" class="form-control" value="<%=address%>">                        
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
<%
		}
		if(store_create==false) {
%>
		<div class="form-group text-right">
	      	<a href="../../app/anag/stores-dett.htm?tipoForm=I&id_customer=<%=id_customer %>" class="btn btn-primary btn-block btn-lg">Create Store</a>
	    </div>
<%		
		}
		else {
			
			int num = 0;
			sql =   "select count(store_code) num from stores "+
					"join customers on id_customer = customers.id join devices on id_store = stores.id "+
					"where device_type = 2 and store_code = '"+storeid+"' and account_code = '"+accountid+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				num=rs.getInt("num");
			}
			rs.close();
			
			String action = "";
			if(num==0) {
				
				action = "<a href=\"../../app/anag/devices-dett.htm?tipoForm=I&id_store="+id_store+"&accountid="+accountid+"&storeid="+storeid+"&doorid="+doorid+"\" class=\"btn btn-primary btn-block btn-lg\">Create Device</a>";
				sql   = "select alarmdirection doorid,storeid,accountid,eventcode, to_timestamp(min(ftpcountfile.tstamp)) minimo , "+
						"to_timestamp(max(ftpcountfile.tstamp)) massimo,count(0) righe from ftpcountfile "+
						"join devicenotpaired on alarmdirection = deviceid and storeid = siteid and accountid = userstr "+
						"left join customers on accountid = account_code "+
						"left join stores on ftpcountfile.storeid = stores.store_code and customers.id = id_customer "+
						"where alarmdirection = '"+doorid+"'  and storeid = '"+storeid+"'  and userstr = '"+accountid+"' "+
						"group by stores.description,customers.brandname,alarmdirection,storeid,accountid,eventcode "+
						"order by accountid,storeid,doorid,eventcode ";
				
			}
			else {
				
				action = "<a href=\"#\" onclick=\"insertDeviceSlave('"+accountid+"', '"+storeid+"', '"+doorid+"')\" class=\"btn btn-primary btn-block btn-lg\">Create Device</a>";
				sql   = "select alarmdirection doorid,storeid,accountid,eventcode, to_timestamp(min(ftpcountfile.tstamp)) minimo , "+
						"to_timestamp(max(ftpcountfile.tstamp)) massimo,count(0) righe from ftpcountfile "+
						"join devicenotpaired on alarmdirection = deviceid and storeid = siteid and accountid = userstr "+
						"left join customers on accountid = account_code "+
						"left join stores on ftpcountfile.storeid = stores.store_code and customers.id = id_customer "+
						"where storeid = '"+storeid+"' and userstr = '"+accountid+"' group by stores.description,customers.brandname, "+
						"alarmdirection,storeid,accountid,eventcode "+
						"order by accountid,storeid,doorid,eventcode ";
				
			}
%>
			<div class="line line-dashed b-b line-lg pull-in"></div>
		
			<div class="panel-footer bg-light">
	        	<div class="padder">
	            	<span class="h4 block">Devices</span>
	            </div>
	        </div>
	        <br/>
	        <div class="form-group">
	        <table class="table table-striped b-t b-light">
			  <thead>
				<tr>
				  
				  <th>Door ID</th>
				  <th>Store ID</th>
				  <th>Account ID</th>
				  <th>Event Code</th>
				  <th>Min</th>
				  <th>Max</th>
				  <th>Row</th>
				  
				</tr>
			  </thead>
			  <tbody id="fbody">
<%		
			rs=st.executeQuery(sql);
			while(rs.next()) {
%>
				<tr>
				  
				  <td><span class="label bg-light"><%=rs.getString("doorid") %></span></td>
				  <td><span class="label bg-light"><%=rs.getString("storeid") %></span></td>
				  <td><span class="label bg-light"><%=rs.getString("accountid") %></span></td>
				  <td><%=rs.getString("eventcode") %></td>
				  <td><%=rs.getString("minimo") %></td>
				  <td><%=rs.getString("massimo") %></td>
				  <td><%=rs.getString("righe") %></td>
				  
				</tr>
<%
			}
			rs.close();
			
			
%>
			  </tbody>
			  <tfoot>
			  	<tr>
			  	  <td colspan="7">
			  	  	<div class="form-group text-right">
			  	  		<%=action %>
			  	  	</div>
			  	  </td>
			  	</tr>
			  </tfoot>
			</table>
			
			<div id="notpaireddmsg"></div>
			</div>
<%		
		}
	}
%>
	    
	    
<%	
}
catch(Exception e) {
	e.printStackTrace();
}
%>
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
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

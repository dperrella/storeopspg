<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<form name="actionForm" id="actionForm" method="post">
<input type="hidden" name="__server" id="__server" value=""/>
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Add Client to <span id="_dsServer" class="text-danger bold"></span></h3>
	</div>
	<div class="modal-body">
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Device Type</label>
	          <select name="devType" id="devType"
              	class="form-control ss" onchange="checkIP4(this.value);getCustomers()">
                <option value="0" selected>Router</option>
                <option value="1">Neo, HIGD, Hella, EAS Server</option>
                <option value="2">Router NAT</option>
                <option value="3">User</option>
              </select>
              </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	   
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-6">
	          <label>Name</label>
	          <input type="text" id="name" name="name" 
	          	class="form-control" value="" data-required="true">   
	        </div>
	        
	        <div class="col-sm-6">
	          <label>Device Id</label>
	          <input type="text" id="code" name="code" 
	          	class="form-control" value="" data-required="true">   
	        </div>
	    </div>
	    
	    <div class="line line-dashed b-b line-lg pull-in"></div>
	   
	    <div class="form-group pull-in clearfix">
	        <div class="col-sm-6">
	          <label>Customer</label>
	          <select name="customerId" id="customerId"
              	class="form-control ss" onchange="getStores(this.value)" data-required="true">
              	<option value="">-</option>
               </select>
              </div>
              
            <div class="col-sm-6">
	          <label>Store</label>
	          <select name="storeId" id="storeId"
              	class="form-control ss" data-required="true">
              	<option value="">-</option>
               </select>
              </div>
	    </div>
	    
	    
	    <div id="ips">
	    
	   	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
	    <div class="panel-footer bg-light">
        	<div class="padder">
            	<span class="h4 block">VPN IP Address</span>
            </div>
        </div>
        <br/>
	    <div class="form-group pull-in clearfix">
	        
	        <div class="col-sm-3">
	          <label>iP1st</label>
	          
	          <select class="form-control ss" id="iP1st" name="iP1st" data-required="true">
	          	<option value="">-</option>
	          </select>
	            
	        </div>
	        
	        <div class="col-sm-3">
	          <label>iP2nd</label>
	          
	          <select class="form-control ss" id="iP2nd" name="iP2nd" data-required="true">
	          	<option value="">-</option>
	          </select>
	            
	        </div>
	        
	        <div class="col-sm-3">
	          <label>iP3rd</label>
	          <select class="form-control ss" id="iP3rd" name="iP3rd" data-required="true">
	          	<option value="">-</option>
	          </select>
	           
	        </div>
	        
	         <div class="col-sm-3">
	          <label>iP4th</label>
	          <select class="form-control ss" id="iP4th" name="iP4th" data-required="true">
	          	<option value="">-</option>
	          </select>
	           
	        </div>
	    </div>
	    
	    <div id="networkIpdiv">
	    
		    <div class="line line-dashed b-b line-lg pull-in"></div>
		    
		    <div class="panel-footer bg-light">
	        	<div class="padder">
	            	<span class="h4 block">Network IP Class</span>
	            </div>
	        </div>
	        <br/>
	        <div class="form-group pull-in clearfix">
		        <div class="col-sm-12">
		          <label>Network IP</label>
		          <input type="text" id="networkIp" name="networkIp" 
		          	class="form-control" value="" data-required="true">   
		        </div>
	        </div>
        
        </div>
        
        </div>
	    
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick();getCustomers();getDsServer()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
		<input type="submit" class="btn btn-primary btn-lg" value="<%=propLang.get("pop_ap_conferma") %>">	
	</div>
  </div>
</div>
</form>

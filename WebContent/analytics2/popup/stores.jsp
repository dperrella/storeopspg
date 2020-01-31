<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String nrstores = ""+request.getParameter("nrstores");
nrstores = nrstores.replaceAll("\\?cd=undefined","");
nrstores = Utility.noNull(nrstores);

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Select Stores</h3>
	</div>
	<div class="modal-body">
		
		<header class="panel-heading bg-light">
			<span class="hidden-sm"><b>Where? <span id="modal-nrstores">(<%=nrstores%> Stores selected)</span></b></span>
	    </header>
	    
	    <div class="row">
	    	<div class="col-sm-6">
			    <div class="input-group" style="margin-top:10px">
		        	<div class="checkbox i-checks">
		              <label>
		                <input name="updStores" id="updStores" type="checkbox" value="S">
		                <i></i>
		                Only Updates Stores
		              </label>
		            </div>
			    </div>
			</div>
			<div class="col-sm-6">
				<button id="btn-uncheck" onclick="uncheckAll()" class="btn btn-default" style="margin-top:12px" disabled>Uncheck All</button>
			</div>
		</div>
	    
	    <div class="input-group" style="margin-top:10px">
          <input type="text" class="form-control" id="input-search" placeholder="Type to search..." value="">
          <span class="input-group-btn">
            <button id="search-btn" class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
          </span>
        </div>
        
        <div style="margin-top:10px" id="tree"></div>
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="getStores();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-default" onclick="saveNodes()"><%=propLang.get("pop_default") %></a>
		<a class="btn btn-primary btn-lg" onclick="getNodes('GO')"><%=propLang.get("pop_run") %></a>	
	</div>
  </div>
</div>

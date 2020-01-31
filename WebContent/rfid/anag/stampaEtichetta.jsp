<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.sql.*"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<%
String sku = request.getParameter("sku");
String ds1pro = request.getParameter("ds1pro");
String ip = request.getParameter("ip");
String port = request.getParameter("port");
%>
<div class="modal-dialog modal-sm">
  <div class="modal-content">
	
	<div class="modal-header">
		<a class="close" data-dismiss="modal" style="color: rgba(35, 40, 50, 1)" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();">&times;</a>
		<span class="h4" style="color: rgba(35, 40, 50, 1)">Print SKU. <b><%=sku %></b></span>
	</div>
	<div class="modal-body" style="color: rgba(35, 40, 50, 1)">
		<label>Quantity</label>
		<input type="text" name="nr" id="nr" value="0" style="text-align: right" class="form-control input-lg" readonly/>
		<input type="hidden" name="sku" id="sku" value="<%=sku %>"/>
		<input type="hidden" name="ds1pro" id="ds1pro" value="<%=ds1pro %>"/>
		<input type="hidden" name="ip" id="ip" value="<%=ip%>"/>
		<input type="hidden" name="port" id="port" value="<%=port%>"/>
		
		<br/>
		<div class="row">
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('7')"><span style="font-size:30px">7</span></button>
			</div>
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('8')"><span style="font-size:30px">8</span></button>
			</div>
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('9')"><span style="font-size:30px">9</span></button>
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('4')"><span style="font-size:30px">4</span></button>
			
			</div>
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('5')"><span style="font-size:30px">5</span></button>
			
			</div>
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('6')"><span style="font-size:30px">6</span></button>
			
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('1')"><span style="font-size:30px">1</span></button>
			
			</div>
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('2')"><span style="font-size:30px">2</span></button>
			
			</div>
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('3')"><span style="font-size:30px">3</span></button>
			
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-xs-8">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="addNum('0')"><span style="font-size:30px">0</span></button>
			
			</div>
			<div class="col-xs-4">
			<button class="btn btn-default btn-lg btn-block" style="height:60px"
				onclick="delChar()"><span style="font-size:30px">Del</span></button>
			
			</div>
		</div>		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$( '.modal-backdrop').remove();$( '.modal.in' ).remove();">Close</a>
		<a href="#" onclick="stampa()" class="btn btn-primary btn-lg">Print</a>
	</div>
	
  </div>
</div>


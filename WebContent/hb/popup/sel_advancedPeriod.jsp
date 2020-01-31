<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Date"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String sql   = "";

conn=Utility.getConnData();
st=conn.createStatement();

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String tipo = ""+request.getParameter("tipo");
tipo = Utility.noNull(tipo);

//Formato data
DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
Date date = new Date();
String oggi = dateNum.format(date);
String oggiIta = dateIta.format(date);
String oggiIso = dateIta.format(date);

String currentYear = oggi.substring(0,4);
String currentMonth = oggi.substring(4,6);
String currentWeek = "";

String optYearM = "";
String optYearW = "";

sql = "select year from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' group by year order by year ";
rs=st.executeQuery(sql);
String sel = "";
while(rs.next()) {
	if(rs.getString("year").equals(currentYear)) {
		sel = "selected";
	}
	optYearM+="<option value=\""+rs.getString("year")+"\" "+sel+">"+rs.getString("year")+"</option>";
	sel="";
}
rs.close();

sql = "select year, count(calperiod) nr_weeks from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='W' group by year order by year ";
rs=st.executeQuery(sql);
sel = "";
int nr_weeks = 0;
while(rs.next()) {
	if(rs.getString("year").equals(currentYear)) {
		sel = "selected";
	}
	nr_weeks = rs.getInt("nr_weeks");
	optYearW+="<option value=\""+rs.getString("year")+"\" "+sel+">"+rs.getString("year")+"</option>";
	sel="";
}
rs.close();

//=======================
sql = "select min(fromdate) fromdate, max(todate) todate from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' and year = "+currentYear+" ";
rs=st.executeQuery(sql);
String fin_dtda = "";
String fin_dtal = "";
while(rs.next()) {
	fin_dtda = dateIso.format(rs.getDate("fromdate"));
	fin_dtal = dateIso.format(rs.getDate("todate"));
}
rs.close();

//=======================
// mesi
String optMM = "";
sql = "select * from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' and year = "+currentYear+" order by fromdate ";
rs=st.executeQuery(sql);
String mm = "";

while(rs.next()) {
	mm = "00"+rs.getInt("calperiod");
	mm = mm.substring(mm.length()-2,mm.length());
	
	String monthString;
    switch (rs.getInt("calperiod")) {
        case 1:  monthString = propLang.get("gen");
                 break;
        case 2:  monthString = propLang.get("feb");
                 break;
        case 3:  monthString = propLang.get("mar2");
                 break;
        case 4:  monthString = propLang.get("apr");
                 break;
        case 5:  monthString = propLang.get("mag");
                 break;
        case 6:  monthString = propLang.get("giu");
                 break;
        case 7:  monthString = propLang.get("lug");
                 break;
        case 8:  monthString = propLang.get("ago");
                 break;
        case 9:  monthString = propLang.get("set");
                 break;
        case 10: monthString = propLang.get("ott");
                 break;
        case 11: monthString = propLang.get("nov");
                 break;
        case 12: monthString = propLang.get("dic");
                 break;
        default: monthString = "Invalid month";
                 break;
    }
	optMM+="<option value=\""+mm+"\">"+monthString+"</option>";
	
}
rs.close();

	
int mmini = Integer.parseInt(fin_dtda.substring(0,4)+fin_dtda.substring(5,7));
int mmfin = Integer.parseInt(fin_dtal.substring(0,4)+fin_dtal.substring(5,7));

//======================
//Quarter
String optQQ = "";
int q = 0;
int qnum = 0;
for(int i=mmini;i<=mmfin;i++) {
	
	String sI = ""+i;
	
	if(i<=Integer.parseInt(sI.substring(0,4)+"12")&&Integer.parseInt(sI.substring(4,6))>0) {
		
		q+=1;
		
		if(q==3) {
			qnum+=1;
			
			int ini2 = (Integer.parseInt(sI.substring(4,6))-2);
			if(ini2<=0)
				ini2=ini2+12;
			
			String ini = "00"+ini2;
			ini = ini.substring(ini.length()-2,ini.length());
			
			optQQ+="<option value=\""+ini+"#"+sI.substring(4,6)+"\">Q"+qnum+" - "+propLang.get(ini)+" - "+propLang.get(sI.substring(4,6))+"</option>";	
			
			q=0;
		}
	}	
}

//=======================
// Settimane
String optWE = "";

sql = "select * from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='W' and year = "+currentYear+" order by calperiod ";
rs=st.executeQuery(sql);
String we = "";
while(rs.next()) {
	we = "00"+rs.getInt("calperiod");
	we = we.substring(we.length()-2,we.length());

	String dtda="";
	String dtal="";
	
	dtda = dateIta.format(rs.getDate("fromdate"));
	dtal = dateIta.format(rs.getDate("todate"));
	
	optWE+="<option value=\""+dtda+"#"+dtal+"\">Week: "+we+" - "+sdf.format(rs.getDate("fromdate"))+" - "+sdf.format(rs.getDate("todate"))+"</option>";
}
%>

<form action="report-counter.htm">
<input type="hidden" name="tipo" value="<%=tipo %>"/>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Advanced Comparison - Select Period</h3>
		
	</div>
	<div class="modal-body">	
		
			<header class="panel-heading bg-light">
				<ul class="nav nav-tabs pull-right">
		           	<li class="active"><a href="#yearTab" data-toggle="tab" onclick="$('#tipo').val('Y');$('#tipoPeriod').val('Y')"><%=propLang.get("tipo_year") %></a></li>
		           	<li><a href="#quarterTab" data-toggle="tab" onclick="$('#tipo').val('Q');$('#tipoPeriod').val('Q')"><%=propLang.get("tipo_quarter") %></a></li>
		           	<li><a href="#monthTab" data-toggle="tab" onclick="$('#tipo').val('M');$('#tipoPeriod').val('M')"><%=propLang.get("tipo_month") %></a></li>
		        	<li><a href="#weekTab" data-toggle="tab" onclick="$('#tipo').val('W');$('#tipoPeriod').val('W')"><%=propLang.get("tipo_week") %></a></li>
		           	<li><a href="#dayTab" data-toggle="tab" onclick="$('#tipo').val('D');$('#tipoPeriod').val('D')"><%=propLang.get("tipo_day") %></a></li>
		        </ul>
	        	<span class="hidden-sm"><b>When?</b></span>
	        </header>
	    	<div style="margin-top:20px">
		    	
            	<div class="form-group clearfix">
	       			 
	                 <div class="tab-content">              
	                    
	                    <div class="tab-pane active" id="yearTab">
		                   	<div class="form-group pull-in clearfix">
		                   		
		                   		<div class="col-sm-12">
		                   		<table class="table" id="righe1">
		                   			<thead>
		                   				<tr>
		                   					<th style="width:70%"><%=propLang.get("sel_year") %></th>
		                   					<th style="width:15%"></th>
		                   					<th style="width:15%"></th>
		                   				</tr>
		                   			</thead>
		                   			<tbody>
		                   				<tr>
				                			<td><input type="hidden" id="1nrrig1" name="nrrig1" value="1" class="idrow">
				                				<select id="1year" name="year" class="form-control input-lg">
										          <%=optYearM %>
								              </select> 
				                				</td>
				                			<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow('righe1')"><i class="icon-plus"></i></a></td>
				                			<td></td>
				                		</tr>
				                	</tbody>
				                </table>
				                </div>
				                
						    </div>
					    </div>
					    
	                    <div class="tab-pane" id="quarterTab">
	                   	   	<div class="form-group pull-in clearfix">
	                   	   	
	                   	   	
	                   	   		<div class="col-sm-12">
		                   		<table class="table" id="righe2">
		                   			<thead>
		                   				<tr>
		                   					<th style="width:20%"><%=propLang.get("sel_year") %></th>
		                   					<th style="width:50%"><%=propLang.get("sel_quarter") %></th>
		                   					<th style="width:15%"></th>
		                   					<th style="width:15%"></th>
		                   				</tr>
		                   			</thead>
		                   			<tbody><tr>
				                			<td><input type="hidden" id="1nrrig2" name="nrrig2" value="1" class="idrow">
				                				<select id="1yearQuarter" name="yearQuarter" class="form-control input-lg" onchange="cambiaSettimana2(this.value, '1quarter')">
										          <%=optYearM %>
								              </select>   
				                			</td>
				                			<td><select id="1quarter" name="xquarter" class="form-control input-lg">
										       	<%=optQQ %>
										        </select> 
										    </td>
				                			<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow('righe2')"><i class="icon-plus"></i></a></td>
				                			<td></td>
				                		</tr>
				                	</tbody>
				                </table>
				                </div>
	                   	   		
		    				</div>
	    	           	</div>
	    	           	
	                    <div class="tab-pane" id="monthTab">
	                   	   	<div class="form-group pull-in clearfix">
	                   	   	
	                   	   		<div class="col-sm-12">
		                   		<table class="table" id="righe3">
		                   			<thead>
		                   				<tr>
		                   					<th style="width:20%"><%=propLang.get("sel_year") %></th>
		                   					<th style="width:50%"><%=propLang.get("sel_month") %></th>
		                   					<th style="width:15%"></th>
		                   					<th style="width:15%"></th>
		                   				</tr>
		                   			</thead>
		                   			<tbody><tr>
				                			<td><input type="hidden" id="1nrrig3" name="nrrig3" value="1" class="idrow">
				                				<select id="1yearMonth" name="yearMonth" class="form-control input-lg" onchange="cambiaSettimana2(this.value, '1month')">
										          <%=optYearM %>
								              </select>
				                			</td>
				                			<td><select id="1month" name="month" class="form-control input-lg">
										          <option value="">-</option>
										          <%=optMM %>
								              </select> 
										    </td>
				                			<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow('righe3')"><i class="icon-plus"></i></a></td>
				                			<td></td>
				                		</tr>
				                	</tbody>
				                </table>
				                </div>
	                   	   	
		    				</div>
	    	           	</div>
	                    
	                    <div class="tab-pane" id="weekTab">
		                   	<div class="form-group pull-in clearfix">
		                   	
		                   		<div class="col-sm-12">
		                   		<table class="table" id="righe4">
		                   			<thead>
		                   				<tr>
		                   					<th style="width:20%"><%=propLang.get("sel_year") %></th>
		                   					<th style="width:50%"><%=propLang.get("sel_week") %></th>
		                   					<th style="width:15%"></th>
		                   					<th style="width:15%"></th>
		                   				</tr>
		                   			</thead>
		                   			<tbody><tr>
				                			<td><input type="hidden" id="1nrrig4" name="nrrig4" value="1" class="idrow">
				                				<select id="1yearWeek" name="yearWeek" class="form-control input-lg" onchange="cambiaSettimana2(this.value, '1week')">
										          <%=optYearW %>
								               </select>   
				                			</td>
				                			<td><select id="1week" name="week" class="form-control input-lg">
										          <option value="">-</option>
										          <%=optWE %>
								              </select>
										    </td>
				                			<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow('righe4')"><i class="icon-plus"></i></a></td>
				                			<td></td>
				                		</tr>
				                	</tbody>
				                </table>
				                </div>
		                   	
				    		</div>
	    				</div>
	    				
	                    <div class="tab-pane" id="dayTab">
		                   	<div class="form-group pull-in clearfix">
		                   	
		                   		<div class="col-sm-12">
		                   		<table class="table" id="righe5">
		                   			<thead>
		                   				<tr>
		                   					<th style="width:70%"><%=propLang.get("sel_day") %></th>
		                   					<th style="width:15%"></th>
		                   					<th style="width:15%"></th>
		                   				</tr>
		                   			</thead>
		                   			<tbody><tr>
				                			<td><input type="hidden" id="1nrrig5" name="nrrig5" value="1" class="idrow">
				                				<input class="datepicker-input2 form-control input-lg" name="day" id="1day" 
						          					type="text" value="${oggi}" data-date-format="dd-mm-yyyy" > 
				                			</td>
				                			<td><a href="javascript:void;" class="btn btn-default btn-lg m-l-md" onclick="addRow('righe5')"><i class="icon-plus"></i></a></td>
				                			<td></td>
				                		</tr>
				                	</tbody>
				                </table>
				                </div>
				                
						    </div>
					    </div>
					    
	                 </div>
	               
	           </div>
	       </div>
	      

	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="setCal();changeTab()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
		<a class="btn btn-primary btn-lg" onclick="getAdvancedDates()"><%=propLang.get("pop_run") %></a>	
	</div>
  </div>
</div>
</form>
<%
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>
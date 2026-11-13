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
import="pkg.beans.ccn.Stores"
import="java.util.List"
import="java.util.ArrayList"
import="java.util.HashMap"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

List<Stores> sssto = new ArrayList<Stores>();
sssto = (List) session.getAttribute("sto");

String tipo = ""+request.getParameter("tipo");
tipo = Utility.noNull(tipo);

//Formato data
DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
Date date = new Date();
String oggi = dateNum.format(date);
String oggiIta = dateIta.format(date);
String oggiIso = dateIta.format(date);

String currentYear = oggi.substring(0,4);
String currentMonth = oggi.substring(4,6);
String currentWeek = "";

String idstores = ""+request.getParameter("idstores");
idstores = Utility.noNull(idstores);
%>


<form action="../report/report-occupancy.htm" method="post" onsubmit="return control(this)">
<input type="hidden" name="tipo" value="<%=tipo %>"/>
<input type="hidden" name="tipoPeriod" value="<%=tipo %>"/>
<input type="hidden" name="verso" id="verso" value="<%=session.getAttribute("_verso") %>">
			                    	 	
<input type="hidden" name="idstores" id="__idstores" value=""/>
<input type="hidden" name="kpi" id="__kpi" value=""/>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Smart Occupancy</h3>
	</div>
	<div class="modal-body">	
		
		
		
	    

<%
//Connection conn = Utility.getConnData();
//sto = Stores.getStoresByIdCustomerAndUsername(conn, Integer.parseInt(session.getAttribute("idcus").toString()), session.getAttribute("username").toString());
%>
		
		<div class="form-group pull-in clearfix">
	        <div class="col-sm-12">
	          <label>Store(s)</label>
	          <select name="id_store" id="id_store" 
              	class="form-control ss">
              	<option value=""></option>
              	<%for(int i=0;i<sssto.size();i++){ %>
                <option value="<%=sssto.get(i).getIdStore()%>"><%=sssto.get(i).getDescription()%></option>
                <%} %>
			  </select>
			</div>
			
	    </div>
	    
	    <div id="msg"></div>

		<div class="line line-dashed b-b line-lg pull-in"></div>
	    
<%
if(tipo.equals("D")) {
%>	
		<div class="form-group pull-in clearfix">
       		<div class="col-sm-12">
	          <label><%=propLang.get("sel_day") %></label>
	          <input type="text" name="day" id="day" class="datepicker-input2 form-control"
				autocomplete="off" placeholder="gg-mm-aaaa" data-date-format="dd-mm-yyyy" value="<%=dateIta.format(new Date()) %>"/>
	        </div>
	    </div>	
<%
}
else if(tipo.equals("W")) {
	
	// =======================
	// Settimane
	String optWE = "";
	
	Locale lc = new Locale("en","UK");
	Calendar c = Calendar.getInstance(lc);
	c.set(Integer.parseInt(currentYear), 0, 1);
	
	// Formato data
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	  
	// Numero di settimane
	int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
	
	// Settimana attuale
	c.setTime(date);
	currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
	currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
	
	System.out.println(currentWeek);
	
	// Per settimanale
	Calendar cal = Calendar.getInstance();
	// Imposta l'anno corrente
	cal.set(Integer.parseInt(currentYear), 0, 1);
	
	String week = "";
	
	// campi tables
	String $xcdtb = "SET";
	String $xcdel = "";
	String $xdtab = "";
	String $cmp1 = "";
	
	for (int i=1; i<=weeks; i++) {
		  
		week = "00"+i;
		week = week.substring(week.length()-2,week.length());
		
		// valorizza xcdel col numero della settimana
		$xcdel = week;
		
		cal.set(Integer.parseInt(currentYear), 0, 1);
		cal.set(Calendar.WEEK_OF_YEAR, i);        
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		// valorizza il primo giorno della settimana
		$xdtab=sdf.format(cal.getTime())+" - ";
		
		cal.add(Calendar.DATE, 6);  
		//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		// valorizza l'ultimo giorno della settimana
		$xdtab+=sdf.format(cal.getTime());
		
		// Settimana attuale
		if($xcdel.equals(currentWeek)) {
			$cmp1 = "selected ";
		}
		
		optWE+="<option value=\""+$xcdel+"\" "+$cmp1+">"+$xdtab+"</option>";
		
		//*blank bariabili
		$xdtab = "";
		$cmp1 = "";
  	}
%>
		<div class="form-group pull-in clearfix">
       		<div class="col-sm-4">
	          <label><%=propLang.get("sel_year") %></label>
	          <select id="yearWeek" name="yearWeek" class="form-control" onchange="cambiaSettimana(this.value)">
		          <option value="2015">2015</option>
		          <option value="2016">2016</option>
		          <option value="2017">2017</option>
		          <option value="2018">2018</option>
		          <option value="2019">2019</option>
		          <option value="2020" selected>2020</option>
		       </select>   
	        </div>
	        
	        <div class="col-sm-8">
	          <label><%=propLang.get("sel_week") %></label>
	          <select id="week" name="week" class="form-control">
		          <option value="">-</option>
		          <%=optWE %>
              </select>   
	        </div>
    	</div>
<%
}
else if(tipo.equals("M")) {
	
	// =======================
	// Mesi
	String mm = "";
	String desMm = "";
	String optMM = "";
	
	for(int i=1;i<=12;i++) {
		
		mm = "00"+i;
		mm = mm.substring(mm.length()-2, mm.length());
		
		if(mm.equals("01")) {
			desMm = propLang.get("gen");
		}
		else if(mm.equals("02")) {
			desMm = propLang.get("feb");
		}
		else if(mm.equals("03")) {
			desMm = propLang.get("mar2");
		}
		else if(mm.equals("04")) {
			desMm = propLang.get("apr");
		}
		else if(mm.equals("05")) {
			desMm = propLang.get("mag");
		}
		else if(mm.equals("06")) {
			desMm = propLang.get("giu");
		}
		else if(mm.equals("07")) {
			desMm = propLang.get("lug");
		}
		else if(mm.equals("08")) {
			desMm = propLang.get("ago");
		}
		else if(mm.equals("09")) {
			desMm = propLang.get("set");
		}
		else if(mm.equals("10")) {
			desMm = propLang.get("ott");
		}
		else if(mm.equals("11")) {
			desMm = propLang.get("nov");
		}
		else if(mm.equals("12")) {
			desMm = propLang.get("dic");
		}
		optMM+="<option value=\""+mm+"\">"+desMm+"</option>";
		
	}
%>
		<div class="form-group pull-in clearfix">
       		<div class="col-sm-4">
	          <label><%=propLang.get("sel_year") %></label>
	          <select id="yearMonth" name="yearMonth" class="form-control">
		          <option value="2015">2015</option>
		          <option value="2016">2016</option>
		          <option value="2017">2017</option>
		          <option value="2018">2018</option>
		          <option value="2019">2019</option>
		          <option value="2020" selected>2020</option>
		       </select>   
	        </div>
	        
	        <div class="col-sm-8">
	          <label><%=propLang.get("sel_month") %></label>
	          <select id="month" name="month" class="form-control">
		          <option value="">-</option>
		          <%=optMM %>
              </select>   
	        </div>
    	</div>
<%
}
else if(tipo.equals("P")) {
%>
		<div class="form-group pull-in clearfix">
       		<div class="col-sm-6">
	          <label><%=propLang.get("sel_dtda") %></label>
	          <input type="text" name="_dtda" id="dtda" class="datepicker-input2 form-control"
				autocomplete="off" placeholder="gg-mm-aaaa" data-date-format="dd-mm-yyyy"/>
	        </div>
	        
	        <div class="col-sm-6">
	          <label><%=propLang.get("sel_dtal") %></label>
	          <input type="text" name="_dtal" id="dtal" class="datepicker-input2 form-control"
				autocomplete="off" placeholder="gg-mm-aaaa" data-date-format="dd-mm-yyyy"/>
	        </div>
	    </div>	
<%
}
%>
	</div>

	<div class="modal-footer">
		
		
		<img src="../../images/spacer.gif" onload="setCal();selpick()"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_us_chiudi") %></a>
		<input type="submit" value="<%=propLang.get("btn_esegui_report") %>" class="btn btn-success btn-lg"/>
	</div>
  </div>
</div>
</form>
<%
//conn.close();
%>
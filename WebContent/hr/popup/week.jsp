<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.util.Calendar"
import="java.util.Date"
import="java.util.Locale"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.List"
import="java.util.ArrayList"
import="pkg.beans.Tables"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%!
public static String getStatus(Connection conn, String id_departments, String year, String week) throws SQLException {
	
	String status = "";
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	try {
		st=conn.createStatement();
		sql   = "select status, xdtab "+
				"from hr_planification p "+
				"left join ztables on trim(status) = trim(xcdel) "+
				"where p.id_departments = '"+id_departments+"' "+
				"and p.plan_year = '"+year+"' and p.plan_week = '"+week+"' "+
				"and p.id_employee in (select v.id_employee "+
				"from vw_emp_dep_indate v where v.id_departments = '"+id_departments+"' "+
				"and anno = "+year+" and settimana = "+week+" "+
				//"and dow = '0' "+
				") "+
				"group by status, xdtab "+
				"order by status ";
		System.out.println("getstatus = "+sql);
		rs=st.executeQuery(sql);
		if(rs.next()) {
			status = rs.getString("status");
		}
		rs.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	if(st!=null)
		st.close();
	
	return status;
	
}
%>
<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String year = ""+request.getParameter("year");
year = Utility.noNull(year);
String id_departments = ""+request.getParameter("id_departments");
id_departments = Utility.noNull(id_departments);
String tp = ""+request.getParameter("tp");
tp = Utility.noNull(tp);

System.out.println(year);

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";
int count = 0;

// **********************************************************
// Connessione al DATABASE
try
{
	List<Tables> wk = new ArrayList<Tables>();
	
	// Data
	DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
	Date date = new Date();
	String oggi = dateNum.format(date);
	String currentYear = oggi.substring(0,4);
	String currentWeek = "";
	
	
	conn=Utility.getConnHR();
	// =======================
	// Settimane
				
	Calendar c = Calendar.getInstance(Locale.GERMAN);
	c.set(Integer.parseInt(year), 0, 1);
	
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
	Calendar cal = Calendar.getInstance(Locale.GERMAN);
	// Imposta l'anno corrente
	cal.set(Integer.parseInt(year), 0, 1);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	
	String week = "";
	
	// campi tables
	String $xcdtb = "SET";
	String $xcdel = "";
	String $xdtab = "";
	String status = "";
	String $cmp1 = "";
	for (int i=1; i<=weeks; i++) {
		
		week = "00"+i;
		week = week.substring(week.length()-2,week.length());
		
		// valorizza xcdel col numero della settimana
		$xcdel = week;
		
		cal.set(Integer.parseInt(year), 0, 1);
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
		
		if(id_departments.length()>0) {
			status = ""+getStatus(conn, id_departments, year, week);
			status = Utility.noNull(status);
		}
		// valorizza la lista
		if(tp.equals("A")) {
			
			// Controlla se la settimana ha una richiesta di approvazione
			if(status.equals("X")) {
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,"","",""));
			}
			
		}
		else {
			
			if(status.equals("R")) {
				status = "#f05050";
			}
			if(status.equals("X")) {
				status = "#545ca6";
			}
			if(status.equals("A")) {
				status = "#1ab667";
			}
			if(status.equals("L")) {
				status = "#4cb6cb";
			}
			// Aggiunge le sole settimane NON LAVORATE
			if(!status.equals("L")) {
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,status,"",""));
			}
			status = "";
		}
		//*blank bariabili
		$xdtab = "";
		$cmp1 = "";
  	}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin"><%=propLang.get("sm_vis_date_") %></h3>
	</div>
	<div class="modal-body">
		
		<div style="overflow:auto; height:275px;">
		
		<input id="searchInput" class="form-control" type="text" value="<%=propLang.get("pop_em_filtra") %>">
		
		<table class="table table-striped b-t b-light">
		  <thead>
			<tr>
			  <th>Status</th>
			  <th>Week</th>
			  <th>Period</th>
			  <th></th>
			</tr>
		  </thead>
		  <tbody id="fbody">
<%
for(int i=0;i<wk.size();i++) 
{
%>
			<tr>
			  <td><i class="fa fa-circle" style="color:<%=wk.get(i).getCmp2() %>"/></i></td>
			  <td><%=wk.get(i).getXcdel() %></td>
			  <td><%=wk.get(i).getXdtab() %></td>
			  <td><a href="#" data-dismiss="modal" class="btn btn-default btn-xs"
			  onclick="document.getElementById('week').value = '<%=wk.get(i).getXcdel()%>';
			  weeksel('<%=wk.get(i).getXcdel()%> - <%=wk.get(i).getXdtab()%>', '<%=wk.get(i).getCmp2() %>');
			  $('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_em_sel") %></a></td>
<%
}
%>
		  </tbody>
		</table>
			
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="jqueryRic();"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_em_chiudi") %></a>
	</div>
  </div>
</div>
</div>
<%
}
catch(Exception e) {
	e.printStackTrace();
}
if(conn!=null)
	conn.close();
%>

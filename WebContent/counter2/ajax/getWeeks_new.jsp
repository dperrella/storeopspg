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
import="java.util.TimeZone"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
///////////
TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));

//=======================
//Settimane
String optWE = "";
try {
	//Legge il file di properties associato alla lingua sul profilo
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
	
	String anno = ""+request.getParameter("anno");
	anno = Utility.noNull(anno);
	
	String weekstart = ""+request.getParameter("weekstart");
	weekstart = Utility.noNull(weekstart);
	
	Calendar c = Calendar.getInstance();
	c.set(Integer.parseInt(anno), 0, 1);
	
	// Formato data
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	  
	// Numero di settimane
	int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
	
	// Per settimanale
	Locale lc = new Locale("en","UK");
	if(weekstart.equals("M")) {
		lc = new Locale("en","UK");
	}
	if(weekstart.equals("S")) {
		lc = new Locale("en","US");
	}
	Calendar cal = Calendar.getInstance(lc);
	// Imposta l'anno corrente
	cal.set(Integer.parseInt(anno), 0, 1);
	
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
		
		if(weekstart.equals("M")) {
			cal.setFirstDayOfWeek(Calendar.MONDAY);
		}
		if(weekstart.equals("S")) {
			cal.setFirstDayOfWeek(Calendar.SUNDAY);
		}
		//cal.set(Integer.parseInt(anno), 0, 1);
		cal.set(Calendar.WEEK_OF_YEAR, i);        
		
		if(weekstart.equals("M")) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		if(weekstart.equals("S")) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
		
		// valorizza il primo giorno della settimana
		$xdtab=sdf.format(cal.getTime())+" - ";
		
		cal.add(Calendar.DATE, 6);  
		//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		// valorizza l'ultimo giorno della settimana
		$xdtab+=sdf.format(cal.getTime());
		
		optWE+="<option value=\""+$xcdel+"\" "+$cmp1+">"+week+": "+$xdtab+"</option>";
		
		//*blank bariabili
		$xdtab = "";
		$cmp1 = "";
  	}
}
catch(Exception e) {
	e.printStackTrace();
}
%>
<%=optWE%>
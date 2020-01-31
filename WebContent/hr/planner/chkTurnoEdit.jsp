<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.Calendar"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%><%
SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
DateFormat df = new SimpleDateFormat("yyyyMMddkkmm");

String plan_year = request.getParameter("year");
String plan_week = request.getParameter("week");
String plan_day = request.getParameter("day");
String id_employee = request.getParameter("id_employee");
String req_data = request.getParameter("req_data");
String ini = request.getParameter("ini");
String fin = request.getParameter("fin");

/*
if(ini.length()==1)
	ini = "0"+ini;
if(fin.length()==1)
	fin = "0"+fin;
*/
if(Double.parseDouble(ini)<10)
	ini = "0"+ini;
if(Double.parseDouble(fin)<10)
	fin = "0"+fin;

System.out.println(ini+" "+fin);

long turnoini = 0;
long turnofin = 0;
String dateIni = "";
String dateFin = "";
Date d_dateIni = null;
Date d_dateFin = null;

dateIni = req_data.replaceAll("/", "");
dateIni = dateIni.substring(4,8)+dateIni.substring(2,4)+dateIni.substring(0,2);
dateIni = dateIni+ini.replaceAll("\\.5", "30");
dateIni = dateIni+"00";
if(dateIni.length()>12)
	dateIni = dateIni.substring(0,12);

System.out.println(dateIni);

d_dateIni = df.parse(dateIni);
turnoini = d_dateIni.getTime();
turnoini = turnoini/1000;

// --------------

dateFin = req_data.replaceAll("/", "");
dateFin = dateFin.substring(4,8)+dateFin.substring(2,4)+dateFin.substring(0,2);
dateFin = dateFin+fin.replaceAll("\\.5", "30");
dateFin = dateFin+"00";
if(dateFin.length()>12)
	dateFin = dateFin.substring(0,12);

System.out.println(dateFin);


d_dateFin = df.parse(dateFin);

//Se l'ora è "24" aggiunge un giorno alla data
if(fin.equals("24")) {
	
	Calendar cfin = Calendar.getInstance(Locale.GERMAN);
	cfin.setTime(d_dateFin);
	cfin.add(Calendar.DATE, 1);
	
	d_dateFin = cfin.getTime();

}


turnofin = d_dateFin.getTime();
turnofin = turnofin/1000;

String sSql 		= "";
String output 		= "";

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;

conn=Utility.getConnHR();
st=conn.createStatement();

sSql  = "select p.id, datetime_start, datetime_end, "+
		"to_timestamp(datetime_start) date_start, to_timestamp(datetime_end) date_end, "+
		"id_planification_type, color, description "+
		"from hr_planification p "+
		"left join hr_planification_type t on t.id = p.id_planification_type "+
		"where plan_year = '"+plan_year+"' "+
		"and plan_week = '"+plan_week+"' "+
		"and plan_day = '"+plan_day+"' "+
		"and id_employee = '"+id_employee+"' "+
		"and datetime_start = "+turnoini+" and datetime_end = "+turnofin+" ";
System.out.println(sSql);
rs = st.executeQuery(sSql);

if (rs.next()) {
	
	if(!rs.getString("id_planification_type").equals("da24f14a-1436-4295-8f99-8b1a450b6277")) {
		
		output = rs.getString("id");
		
	}
	else {
		
		output = "";
		
	}
	
}
rs.close();
st.close();
conn.close();
%><%=output%>
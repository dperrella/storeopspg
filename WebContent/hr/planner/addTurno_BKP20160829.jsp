<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Locale"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
DateFormat hourIta = new SimpleDateFormat("kk:mm");
DateFormat dateTime = new SimpleDateFormat("dd/MM/yyyy kk:mm");

String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");
String day = request.getParameter("day");
String id_employee = request.getParameter("id_employee");
String id_planification_type = request.getParameter("id_planification_type");
String ora_ini = request.getParameter("ora_ini");
String ora_fin = request.getParameter("ora_fin");

String output = "";

String $id = "";
String $datetime_start="";
String $datetime_end="";
String $plan_year="";
String $plan_week="";
String $plan_day="";




// Anno
$plan_year = day.substring(6,10);

// Settimana
Date dateDay = dateIta.parse(day);
Calendar cal = Calendar.getInstance(Locale.GERMAN); 
cal.setTime(dateDay);
$plan_week = ""+cal.get(Calendar.WEEK_OF_YEAR);

// Giorno
$plan_day = ""+cal.get(Calendar.DAY_OF_WEEK);
if($plan_day.equals("1"))
	$plan_day = "6";
else if($plan_day.equals("2"))
	$plan_day = "0";
else if($plan_day.equals("3"))
	$plan_day = "1";
else if($plan_day.equals("4"))
	$plan_day = "2";
else if($plan_day.equals("5"))
	$plan_day = "3";
else if($plan_day.equals("6"))
	$plan_day = "4";
else if($plan_day.equals("7"))
	$plan_day = "5";

// Datetime_start / Datetime_end
String dateTimeIni = day+" "+ora_ini;
String dateTimeFin = day+" "+ora_fin;

Date dateIni = dateTime.parse(dateTimeIni);
Date dateFin = dateTime.parse(dateTimeFin);

cal.setTime(dateIni);
$datetime_start = ""+(cal.getTimeInMillis()/1000);
cal.setTime(dateFin);
$datetime_end = ""+(cal.getTimeInMillis()/1000);

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;

conn=Utility.getConnAdmin();
st=conn.createStatement();

String last_ts = "";
Calendar c = Calendar.getInstance(Locale.GERMAN); 
c.setTime(new Date());
last_ts = ""+(c.getTimeInMillis() / 1000);

String sql   = "insert into hr_planification "+
		"(id_employee, id_departments, datetime_start, datetime_end,"+
		" id_planification_type, plan_year, plan_week, plan_day, last_ts) "+
		"values "+
		"('"+id_employee+"', '"+id_departments+"', "+$datetime_start+", "+$datetime_end+", "+
		 "'"+id_planification_type+"', '"+$plan_year+"', '"+$plan_week+"', '"+$plan_day+"', "+last_ts+") RETURNING id ";
System.out.println(sql);

rs = st.executeQuery(sql);
if(rs.next()) {
	$id = rs.getString(1);
	System.out.println("ID: "+rs.getString(1));
}
else {
	$id = "-1";
	System.out.println("ID: -1");
}
rs.close();
st.close();
conn.close();
%>
<%=output%>
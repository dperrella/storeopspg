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

System.out.println(week);

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

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;

conn=Utility.getConnHR();
st=conn.createStatement();




String temp[]=day.split(",");

for(int i=0;i<temp.length;i++) {

	// Anno
	$plan_year = temp[i].substring(6,10);
	
	// Settimana
	Date dateDay = dateIta.parse(temp[i]);
	Calendar cal = Calendar.getInstance(Locale.GERMAN); 
	cal.setTime(dateDay);
	$plan_week = ""+cal.get(Calendar.WEEK_OF_YEAR);
	$plan_week = "00"+$plan_week;
	$plan_week = $plan_week.substring($plan_week.length()-2,$plan_week.length());
	
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
	String dateTimeIni = temp[i]+" "+ora_ini;
	String dateTimeFin = temp[i]+" "+ora_fin;
	
	Date dateIni = dateTime.parse(dateTimeIni);
	Date dateFin = dateTime.parse(dateTimeFin);
	
	cal.setTime(dateIni);
	$datetime_start = ""+(cal.getTimeInMillis()/1000);
	cal.setTime(dateFin);
	// =======================================
	// Se ora fine = "24:00" aggiunge 1 giorno
	if(ora_fin.equals("24:00")) {
		cal.add(Calendar.DATE, 1);
	}
	// =======================================
	$datetime_end = ""+(cal.getTimeInMillis()/1000);
	
	String last_ts = "";
	Calendar c = Calendar.getInstance(Locale.GERMAN); 
	c.setTime(new Date());
	last_ts = ""+(c.getTimeInMillis() / 1000);
	
	String sql   = "select count(id_employee) num "+
			"from hr_planification "+
			"where id_Departments = '"+id_departments+"' "+
			"and id_employee = '"+id_employee+"' "+
			"and plan_year = '"+$plan_year+"' "+
			"and plan_week = '"+$plan_week+"' "+
			"and plan_day = '"+$plan_day+"' "+
			"and "+
			"("+
			"(datetime_start > "+$datetime_start+" and datetime_start < "+$datetime_end+") "+
			"or "+
			"(datetime_end > "+$datetime_start+" and datetime_end < "+$datetime_end+")"+
			"or (datetime_start = "+$datetime_start+") or (datetime_end = "+$datetime_end+") "+
			") ";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	int count = 0;
	if(rs.next()) {
		count=rs.getInt("num");
	}
	rs.close();
	output=""+count;
}	

st.close();
conn.close();
%>
<%=output%>
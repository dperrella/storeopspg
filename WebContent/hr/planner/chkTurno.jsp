<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");

String ini = "";
String fin = "";
String arrayPeriodi = "";
String arrayCss = "";

String plan_year = request.getParameter("year");
String plan_week = request.getParameter("week");
String plan_day = request.getParameter("day");
String id_employee = request.getParameter("id_employee");

String tp = "";

String sSql 		= "";
String output 		= "";

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;

conn=Utility.getConnHR();
st=conn.createStatement();

sSql  = "select datetime_start, datetime_end, "+
		"to_timestamp(datetime_start) date_start, to_timestamp(datetime_end) date_end, "+
		"id_planification_type, color, description "+
		"from hr_planification p "+
		"left join hr_planification_type t on t.id = p.id_planification_type "+
		"where plan_year = '"+plan_year+"' "+
		"and plan_week = '"+plan_week+"' "+
		"and plan_day = '"+plan_day+"' "+
		"and id_employee = '"+id_employee+"' ";
System.out.println(sSql);
rs = st.executeQuery(sSql);

while (rs.next()) {
	
	ini = sdf.format(rs.getTime("date_start"));
	fin = sdf.format(rs.getTime("date_end"));
	
	ini = ini.replaceAll(":00", "");
	ini = ini.replaceAll(":30", "\\.5");
	
	fin = fin.replaceAll(":00", "");
	fin = fin.replaceAll(":30", "\\.5");
	
	// turno
	if(rs.getString("id_planification_type").equals("da24f14a-1436-4295-8f99-8b1a450b6277")) {
		tp = "turno";
	}
	else if(rs.getString("id_planification_type").equals("afc1e7c2-d84a-461b-a567-ce8771b8c6aa")) {
		tp = "malattia";
	}
	else if(rs.getString("id_planification_type").equals("575ed403-8924-4345-bf86-d2bf0534afb6")) {
		tp = "ferie";
	}
	else if(rs.getString("id_planification_type").equals("3694f2cb-1d27-4ec1-9cd2-dd2bc1bf73b4")) {
		tp = "rol";
	}
	else if(rs.getString("id_planification_type").equals("5d2cd2c5-1942-4138-863e-b5ca14e3fbf2")) {
		tp = "monte";
	}
	else if(rs.getString("id_planification_type").equals("33b31123-b8df-40dd-a4e5-f0458349e220")) {
		tp = "l104";
	}
	else if(rs.getString("id_planification_type").equals("54032bc1-7c3f-4175-934a-6e1fd30101ac")) {
		tp = "mat";
	}
	else if(rs.getString("id_planification_type").equals("199104ab-a536-4079-92f5-bbea69f469fe")) {
		tp = "all";
	}				
	else if(rs.getString("id_planification_type").equals("c08eba66-867c-4dff-970f-8332a3cc7785")) {
		tp = "asp";
	}
	else if(rs.getString("id_planification_type").equals("43d27024-37d7-4e25-ba05-ab36e41ec8dc")) {
		tp = "rc";
	}
	else if(rs.getString("id_planification_type").equals("37d58e57-26e9-4e94-938c-8d4097ae31a0")) {
		tp = "mal1";
	}
	else if(rs.getString("id_planification_type").equals("af51379a-858f-495b-a667-e17d27efabf2")) {
		tp = "mal2";
	}
	else if(rs.getString("id_planification_type").equals("efcd8c64-9355-43c2-a540-e096b366919c")) {
		tp = "com";
	}
	else if(rs.getString("id_planification_type").equals("05c266f5-1d5a-4fa9-bf83-ea6314303e59")) {
		tp = "pes";
	}
	arrayPeriodi+=ini+", "+fin+", ";
	arrayCss+= "false, { styleClass: '"+tp+"' },";
	
	
	System.out.println(ini+", "+fin+" "+tp);
	
}
//tolgo ultimo Byte
if(arrayPeriodi.length()>0)
	arrayPeriodi = arrayPeriodi.substring(0,arrayPeriodi.length()-2);
else
	arrayPeriodi = "8 , 9";

if(arrayCss.length()==0)
	arrayCss = "false, { styleClass: 'turno' },";

rs.close();
st.close();
conn.close();

output = arrayPeriodi+";"+arrayCss;
%>
<%=output%>
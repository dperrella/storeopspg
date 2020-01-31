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
%><%
SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
DateFormat df = new SimpleDateFormat("yyyyMMddkkmm");

String plan_year = request.getParameter("year");
String plan_week = request.getParameter("week");
String plan_day = request.getParameter("day");
String id_employee = request.getParameter("id_employee");
String req_data = request.getParameter("req_data");
String nr = request.getParameter("nr");

String sSql 		= "";
String output 		= "";

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;

String css = "";

try {
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
	
	int count = 0;
	while (rs.next()) {
			
		count+=1;
		
		if(Integer.parseInt(nr)==count) {
			// turno
			if(rs.getString("id_planification_type").equals("da24f14a-1436-4295-8f99-8b1a450b6277")) {
				css = "turno";
			}
			else if(rs.getString("id_planification_type").equals("afc1e7c2-d84a-461b-a567-ce8771b8c6aa")) {
				css = "malattia";
			}
			else if(rs.getString("id_planification_type").equals("575ed403-8924-4345-bf86-d2bf0534afb6")) {
				css = "ferie";
			}
			else if(rs.getString("id_planification_type").equals("3694f2cb-1d27-4ec1-9cd2-dd2bc1bf73b4")) {
				css = "rol";
			}
			else if(rs.getString("id_planification_type").equals("5d2cd2c5-1942-4138-863e-b5ca14e3fbf2")) {
				css = "monte";
			}
			else if(rs.getString("id_planification_type").equals("33b31123-b8df-40dd-a4e5-f0458349e220")) {
				css = "l104";
			}
			else if(rs.getString("id_planification_type").equals("c08eba66-867c-4dff-970f-8332a3cc7785")) {
				css = "asp";
			}
			else if(rs.getString("id_planification_type").equals("54032bc1-7c3f-4175-934a-6e1fd30101ac")) {
				css = "mat";
			}
			else if(rs.getString("id_planification_type").equals("199104ab-a536-4079-92f5-bbea69f469fe")) {
				css = "all";
			}
			else if(rs.getString("id_planification_type").equals("43d27024-37d7-4e25-ba05-ab36e41ec8dc")) {
				css = "rc";
			}
			else if(rs.getString("id_planification_type").equals("1160c4fb-8a1f-4d88-bd34-cbc9ea215903")) {
				css = "lutto";
			}
		}
	}
	rs.close();
}
catch(Exception e) {
	
}
if(css.length()==0)
	css = "turno";

st.close();
conn.close();
%><%=css%>
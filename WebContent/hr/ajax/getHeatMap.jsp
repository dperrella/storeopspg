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
%>
<%

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";
String id_departments = request.getParameter("id_departments");
String settimana = request.getParameter("week");
String anno = request.getParameter("year");

String output = "";

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	
	DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat timeFormat = new SimpleDateFormat("kk:mm");
	
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
	
	// *********
	// HEATMAP
	String xAxis = "";
	String yAxis = "";
	int max = 0;
	int height = 0;
	
	//Calendar c_heat = Calendar.getInstance(new Locale("en","US"));
	Calendar c_heat = Calendar.getInstance(Locale.GERMAN);
	
	// Set the calendar to monday of the current week
	c_heat.set(Calendar.YEAR, Integer.parseInt(anno));     
	c_heat.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
	c_heat.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	
	String dtda = dateIso.format(c_heat.getTime());
	for (int d = 0; d < 6; d++) {
		c_heat.add(Calendar.DATE, 1);
	}
	String dtal = dateIso.format(c_heat.getTime());
	String dates=dtda+" / "+dtal;
	
		height = 500;
		
		sql   = "SELECT ora::time ora "
				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
				+ "'"+dtda+" 23:30', '0.5 hours') ora ";
		rs=st.executeQuery(sql);
		String ora = "";
		while(rs.next()) {
			ora = timeFormat.format(rs.getTime("ora")).replaceAll(":", "");
			if(Integer.parseInt(ora)>=600&&Integer.parseInt(ora)<=2330)
				xAxis+="'"+timeFormat.format(rs.getTime("ora"))+"',";
		}
		rs.close();
		if(xAxis.length()>0)
			xAxis = xAxis.substring(0,xAxis.length()-1);
		
		//yAxis = "'Sunday','Monday', 'Tuesday','Wednesday', 'Thursday', 'Friday', 'Saturday'";
		yAxis = "'"+propLang.get("lun")+"','"+propLang.get("mar")+"','"+propLang.get("mer")+"','"+propLang.get("gio")+"','"+propLang.get("ven")+"','"+propLang.get("sab")+"','"+propLang.get("dom")+"'";
		
		sql   = "SELECT max( "
				+ "(select count(id_employee) from hr_planification "
				+ "where plan_year='"+anno+"' and plan_week = '"+settimana+"' "
				+ "and id_departments = '"+id_departments+"' "
				+ "and plan_day = (extract(isodow from ora) -1)::text "
				+ "and ora::time between "
				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
				+ ")) valore "
				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
				+ "'"+dtal+" 23:30', '0.5 hours') ora   ";
		System.out.println(sql);
		rs=st.executeQuery(sql);
		if(rs.next()) {
			max = rs.getInt("valore");
		}
		rs.close();
		
		sql   = "SELECT ora::date giorno, ora::time ora, "
				+ "case when (extract(isodow from ora) -1) = 0 then 7 else (extract(isodow from ora) -1) end dow1 ,"
				+ "extract(isodow from ora)-1 dow, "
				+ "(select count(id_employee) from hr_planification "
				+ "where plan_year='"+anno+"' and plan_week = '"+settimana+"' "
				+ "and id_departments = '"+id_departments+"' "
				+ "and plan_day = (extract(isodow from ora) -1) ::text "
				+ "and ora::time between "
				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
				+ ") valore "
				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
				+ "'"+dtal+" 23:30', '0.5 hours') ora "
				+ "order by giorno, ora ";
	System.out.println("---");
	System.out.println(sql);
	String heatmap = "";
	rs=st.executeQuery(sql);
	String val = "";
	
	String data1 = "";
	
	while(rs.next()) {
		val=""+rs.getString("valore");
		val=Utility.noNull(val);
		
		if(val.length()==0)
			val = "'-'";
		
		switch (rs.getInt("dow")) {
        	case 0: 
        		data1 = propLang.get("lun");
        		break;
        	case 1: 
        		data1 = propLang.get("mar");
        		break;
        	case 2: 
        		data1 = propLang.get("mer");
        		break;
        	case 3: 
        		data1 = propLang.get("gio");
        		break;
        	case 4: 
        		data1 = propLang.get("ven");
        		break;
        	case 5: 
        		data1 = propLang.get("sab");
        		break;
        	case 6: 
        		data1 = propLang.get("dom");
        		break;
		}
		
		ora = timeFormat.format(rs.getTime("ora")).replaceAll(":", "");
		
		if(Integer.parseInt(ora)>=600&&Integer.parseInt(ora)<=2330) {
			
				heatmap+="[\""+timeFormat.format(rs.getTime("ora"))+"\",\""+data1+"\","+val+"],";
				
		}
		
	}
	rs.close();
	
	if(heatmap.length()>0)
		heatmap=heatmap.substring(0,heatmap.length()-1);
	
	heatmap="["+heatmap+"]";
	
	output = max+"##@@"+heatmap;
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
st.close();
conn.close();
%>
<%=output%>
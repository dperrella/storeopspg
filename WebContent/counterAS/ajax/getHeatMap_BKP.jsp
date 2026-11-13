<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.ArrayList"
import="java.util.Collections"
import="java.util.Calendar"
import="java.util.List"
import="java.util.TimeZone"
import="java.util.Locale"
import="pkg.counterAS.PivotData"
import="pkg.utility.Pivot"
import="pkg.utility.Pivot.PivotReport"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%!
public int getMax(ArrayList list){
    int max = Integer.MIN_VALUE;
    for(int i=0; i<list.size(); i++){
        if((int)list.get(i) > max){
            max = (int)list.get(i);
        }
    }
    return max;
}
%>
<%
///////////
//TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));

Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";


String idcus = ""+session.getAttribute("idcus");

String idstores = request.getParameter("idstores");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String kpi = request.getParameter("kpi");
String tipo = request.getParameter("tipo");

String weekstart = ""+request.getParameter("weekstart");
weekstart = Utility.noNull(weekstart);
if(weekstart.length()==0)
	weekstart = "M";

String output = "";

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat us = new SimpleDateFormat("M/d/yyyy hh:mm:ss aa");

//Formattazione data
Date d1 = sdf.parse(dtda);
dtda = iso.format(d1);
Date d2 = sdf.parse(dtal);
dtal = iso.format(d2);

String xAxis = "";
String yAxis = "";
int max = 0;
int height = 0;
String heatmap = "";



try {
	
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	String formula = "";
	sql  =  "SELECT json_agg(kpi_formulacomp) FROM kpi_formulacomp\r\n"
			+ "where kpi in ('"+kpi+"')";
	System.out.println(sql);
	rs=st.executeQuery(sql);
	if(rs.next()) {
		formula = rs.getString(1);
	}
	rs.close();
	
	String jsonParm = "{"
			+ "\"account_id\": \""+idcus+"\","
			+ "\"stores\": ["
			+ idstores
			+ "],"
			+ "\"kpi\": ["
			+ "\""+kpi+"\""
			+ "],"
			+ "\"from_date\": \""+dtda+"\","
			+ "\"to_date\": \""+dtal+"\","
			+ "\"time_grouped\": \"H\","
			+ "\"groupByDoor\" : false, "
			+ "\"calc\": "+formula+" "
			+ "}";

	System.out.println(jsonParm);

	
	
	//Legge il file di properties associato alla lingua sul profilo
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
	ReadProperties prop = new ReadProperties("connection.properties");
	String SAAS_endpoint = prop.get("saas_endpoint_dan");
	
	System.out.println("\nTIPO: "+tipo+"\n");
	
	org.json.JSONObject json = Utility.consumeRest(SAAS_endpoint, "POST", jsonParm, "");
	org.json.JSONArray data = json.getJSONArray("listings");
	
	Calendar c = Calendar.getInstance(Locale.GERMAN);
	
	
	List<PivotData> reports = new ArrayList<PivotData>();
	reports = Pivot.fillZeroHeatmap(dtda,dtal, "IT");
	
	for (int i = 0; i < data.length(); i++) {
		c.setTime(us.parse(data.getJSONObject(i).getString("date")));
		reports.add(new PivotData(String.format("%02d",c.get(Calendar.DAY_OF_WEEK)),String.format("%02d",Integer.parseInt(data.getJSONObject(i).getString("time"))),data.getJSONObject(i).getInt("value")));
	}
	PivotReport report = PivotReport.create(reports);
	System.out.println(report.toString());
	
	String arr[] = report.toString().split("\n");
	
	
	
	if(tipo.equals("D")) {
		
		c = Calendar.getInstance();
		c.setTime(iso.parse(dtda));
		
		int dd = c.get(Calendar.DAY_OF_WEEK);
		String d = "";
		int dow = 0;
		if(dd==2) {
			d = propLang.get("lun");
			dow = 1;
		}
		if(dd==3) {
			d = propLang.get("mar");
			dow = 2;
		}
		if(dd==4) {
			d = propLang.get("mer");
			dow = 3;
		}
		if(dd==5) {
			d = propLang.get("gio");
			dow = 4;
		}
		if(dd==6) {
			d = propLang.get("ven");
			dow = 5;
		}
		if(dd==7) {
			d = propLang.get("sab");
			dow = 6;
		}
		if(dd==1) {
			d = propLang.get("dom");
			dow = 0;
		}
		
		
		height = 120;
		
		xAxis = "00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,22-23,23-24";
		
		yAxis = ""+d+"";
		
		int min = 0;
		int max2 = 24;
		String temp[] = idstores.split(",");
		
		/*
		if(temp.length>=1) {
			String tmp[]=Utility.getXAxis(conn, idstores).split("##@@");
				
			xAxis = tmp[0];
			min = Integer.parseInt(tmp[1]);
			max2 = Integer.parseInt(tmp[2]);
		}
		*/
		
		System.out.println("MIN:"+min+" - MAX:"+max2);
		
		ArrayList l = new ArrayList();
		String val = "";
		String data1 = "";
		
		c = Calendar.getInstance(Locale.GERMAN);
    	
		int idx = 0;
		for (String v : arr) {
			
			if(idx>0) {
				
				String row[] = v.split("\t");
				for(int i=0;i<row.length;i++) {
					
					if(i==0) {
						
						switch (Integer.parseInt(row[i])) {
				     	case 1: 
				     		data1 = propLang.get("dom");
				     		break;
				     	case 2: 
				     		data1 = propLang.get("lun");
				     		break;
				     	case 3: 
				     		data1 = propLang.get("mar");
				     		break;
				     	case 4: 
				     		data1 = propLang.get("mer");
				     		break;
				     	case 5: 
				     		data1 = propLang.get("gio");
				     		break;
				     	case 6: 
				     		data1 = propLang.get("ven");
				     		break;
				     	case 7: 
				     		data1 = propLang.get("sab");
				     		break;
						}
						
					}
					else {
						
						val=row[i];
						//System.out.println(val);
						val=Utility.noNull(val);
						
						
						if(val.length()>0)
							l.add((int)Math.round(Double.parseDouble(val)/temp.length));
						
						if(val.length()==0)
							val = "'-'";
						
						if(i>=min&&i<=max2)
							heatmap+="["+((i-1)-min)+",\""+data1+"\","+Utility.round((Double.parseDouble(val)/temp.length),2)+"],";
						
					}
					
				}
			}
			
			idx++;
			
		}
		
		try {
			max = getMax(l);
		}
		catch(Exception e) {
			e.printStackTrace();
			max = 0;
		}
		
		if(heatmap.length()>0)
			heatmap=heatmap.substring(0,heatmap.length()-1);
		heatmap = "["+heatmap+"]";
		
		output = max+"##@@"+heatmap+"##@@"+xAxis+"##@@"+yAxis;
		
	}
	else {
		
		height = 500;
			
		
		xAxis = "00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,22-23,23-24";
		
		if(weekstart.equals("M"))
			yAxis = ""+propLang.get("dom")+","+propLang.get("sab")+","+propLang.get("ven")+","+propLang.get("gio")+","+propLang.get("mer")+","+propLang.get("mar")+","+propLang.get("lun")+"";
		else
			yAxis = ""+propLang.get("sab")+","+propLang.get("ven")+","+propLang.get("gio")+","+propLang.get("mer")+","+propLang.get("mar")+","+propLang.get("lun")+","+propLang.get("dom")+"";
			
		int min = 0;
		int max2 = 24;
		
		String temp[] = idstores.split(",");
		
		
		/*
		if(temp.length>=1) {
			String tmp[]=Utility.getXAxis(conn, idstores).split("##@@");
			xAxis = tmp[0];
			min = Integer.parseInt(tmp[1]);
			max2 = Integer.parseInt(tmp[2]);
		}
		*/
		
		System.out.println("MIN:"+min);
		
		ArrayList l = new ArrayList();
		String val = "";
		String data1 = "";
		
		c = Calendar.getInstance(Locale.GERMAN);
    	
		int idx = 0;
		for (String v : arr) {
			
			if(idx>0) {
			
				String row[] = v.split("\t");
				for(int i=0;i<row.length;i++) {
					
					if(i==0) {
						
						switch (Integer.parseInt(row[i])) {
				     	case 1: 
				     		data1 = propLang.get("dom");
				     		break;
				     	case 2: 
				     		data1 = propLang.get("lun");
				     		break;
				     	case 3: 
				     		data1 = propLang.get("mar");
				     		break;
				     	case 4: 
				     		data1 = propLang.get("mer");
				     		break;
				     	case 5: 
				     		data1 = propLang.get("gio");
				     		break;
				     	case 6: 
				     		data1 = propLang.get("ven");
				     		break;
				     	case 7: 
				     		data1 = propLang.get("sab");
				     		break;
						}
						
					}
					else {
						
						val=row[i];
						//System.out.println(val);
						val=Utility.noNull(val);
						
						
						if(val.length()>0)
							l.add((int)Math.round(Double.parseDouble(val)/temp.length));
						
						if(val.length()==0)
							val = "'-'";
						
						if(i>=min&&i<=max2)
							heatmap+="["+((i-1)-min)+",\""+data1+"\","+Utility.round((Double.parseDouble(val)/temp.length),2)+"],";
						
					}
					
				}
				
			}
			
			idx++;
			
		}
		
		try {
			max = getMax(l);
		}
		catch(Exception e) {
			e.printStackTrace();
			max = 0;
		}
		
		if(heatmap.length()>0)
			heatmap=heatmap.substring(0,heatmap.length()-1);
		heatmap = "["+heatmap+"]";
		
		output = max+"##@@"+heatmap+"##@@"+xAxis+"##@@"+yAxis;
	
	}
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
finally {
	try {
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
	}
	catch(Exception e) {}
}
%>
<%=output%>
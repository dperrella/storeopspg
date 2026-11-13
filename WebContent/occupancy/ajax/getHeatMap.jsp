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
String pag = ""+request.getParameter("pag");
pag = Utility.noNull(pag);

String idstores = request.getParameter("idstores");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String kpi = request.getParameter("kpi");
String uid = request.getParameter("uid");

String tipo = ""+request.getParameter("tipo");
tipo = Utility.noNull(tipo);

String weekstart = ""+request.getParameter("weekstart");
weekstart = Utility.noNull(weekstart);
if(weekstart.length()==0)
	weekstart = "M";


String output = "";

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");

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
	
	//Legge il file di properties associato alla lingua sul profilo
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

	
	System.out.println("\nTIPO: "+tipo+"\n");
	
	if(tipo.equals("D")) {
		
		Calendar c = Calendar.getInstance();
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
		
		conn=Utility.getConnData();
		st=conn.createStatement();
		
		height = 120;
		
		xAxis = "00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,23-23,23-24";
		
		yAxis = ""+d+"";
		
		int min = 0;
		int max2 = 23;
		String temp[] = idstores.split(",");
		
		
		System.out.println("pag:"+pag);
		if(temp.length>=1) {
			String tmp[]=Utility.getXAxis(conn, idstores).split("##@@");
				
			xAxis = tmp[0];
			min = Integer.parseInt(tmp[1]);
			max2 = Integer.parseInt(tmp[2]);
		}
		
		System.out.println("MIN:"+min+" - MAX:"+max2);
		
		ArrayList l = new ArrayList();
		
		////////////////////////////////////
		//sql   = "set timezone to 'UTC-2'; ";
		//st.execute(sql);
		////////////////////////////////////
			
		sql   = "select _dow data1 ,_hour data2 ,_val valore "
					+ "from public.landing_heatmap_new('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi+"', '"+uid+"') "
					+ "where _dow = "+dow+" "
					+ "order by 1,2";
		System.out.println("");
		System.out.println(sql);
		System.out.println("");
		
		rs=st.executeQuery(sql);
		String val = "";
		
		String data1 = "";
		
		while(rs.next()) {
			
			val=""+rs.getString("valore");
			//System.out.println(val);
			val=Utility.noNull(val);
			
			
			if(val.length()>0)
				l.add((int)Math.round(Double.parseDouble(val)));
			
			if(val.length()==0)
				val = "'-'";
			
			
			switch (rs.getInt("data1")) {
	     	case 0: 
	     		data1 = propLang.get("dom");
	     		break;
	     	case 1: 
	     		data1 = propLang.get("lun");
	     		break;
	     	case 2: 
	     		data1 = propLang.get("mar");
	     		break;
	     	case 3: 
	     		data1 = propLang.get("mer");
	     		break;
	     	case 4: 
	     		data1 = propLang.get("gio");
	     		break;
	     	case 5: 
	     		data1 = propLang.get("ven");
	     		break;
	     	case 6: 
	     		data1 = propLang.get("sab");
	     		break;
			}
			
			//heatmap+="["+rs.getInt("data2")+",\""+data1+"\","+val+"],";
			System.out.println(rs.getInt("data2")+" "+val);
			if(rs.getInt("data2")>=min&&rs.getInt("data2")<max2)
				heatmap+="["+(rs.getInt("data2")-min)+",\""+data1+"\","+val+"],";
			
		}
		rs.close();
		
		/*
		rs=st.executeQuery("select * from temptb where kpi = 'VIS'");
		while(rs.next()) {
			System.out.println(rs.getString(1)+' '+rs.getString(2)+' '+rs.getString(3)+' '+rs.getString(4));
		}
		rs.close();
		*/
		
		try {
			//double dMax = Collections.max(l);
			//max = (int) Math.round(dMax);
			//max = (int) Collections.max(l);
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
	
		conn=Utility.getConnData();
		st=conn.createStatement();
		
		height = 500;
			
		
		xAxis = "00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,23-23,23-24";
		
		if(weekstart.equals("M"))
			yAxis = ""+propLang.get("dom")+","+propLang.get("sab")+","+propLang.get("ven")+","+propLang.get("gio")+","+propLang.get("mer")+","+propLang.get("mar")+","+propLang.get("lun")+"";
		else
			yAxis = ""+propLang.get("sab")+","+propLang.get("ven")+","+propLang.get("gio")+","+propLang.get("mer")+","+propLang.get("mar")+","+propLang.get("lun")+","+propLang.get("dom")+"";
			
		int min = 0;
		int max2 = 23;
		
		String temp[] = idstores.split(",");
		
		
		System.out.println("pag:"+pag);
		if(temp.length>=1) {
			String tmp[]=Utility.getXAxis(conn, idstores).split("##@@");
				
			xAxis = tmp[0];
			min = Integer.parseInt(tmp[1]);
			max2 = Integer.parseInt(tmp[2]);
		}
		
		System.out.println("MIN:"+min);
		
		ArrayList l = new ArrayList();
			
		sql   = "select _dow data1 ,_hour data2 ,_val valore "
					+ "from public.landing_heatmap_new('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi+"', '"+uid+"') "
					+ "order by 1,2";
		System.out.println("");
		System.out.println(sql);
		System.out.println("");
		
		rs=st.executeQuery(sql);
		String val = "";
		
		String data1 = "";
		
		while(rs.next()) {
			
			val=""+rs.getString("valore");
			System.out.println(val);
			
			val=Utility.noNull(val);
			
			if(val.length()>0)
				l.add((int)Math.round(Double.parseDouble(val)));
			
			if(val.length()==0)
				val = "'-'";
			
			
			switch (rs.getInt("data1")) {
	     	case 0: 
	     		data1 = propLang.get("dom");
	     		break;
	     	case 1: 
	     		data1 = propLang.get("lun");
	     		break;
	     	case 2: 
	     		data1 = propLang.get("mar");
	     		break;
	     	case 3: 
	     		data1 = propLang.get("mer");
	     		break;
	     	case 4: 
	     		data1 = propLang.get("gio");
	     		break;
	     	case 5: 
	     		data1 = propLang.get("ven");
	     		break;
	     	case 6: 
	     		data1 = propLang.get("sab");
	     		break;
			}
			
			//heatmap+="["+rs.getInt("data2")+",\""+data1+"\","+val+"],";
			if(rs.getInt("data2")>=min&&rs.getInt("data2")<max2)
				heatmap+="["+(rs.getInt("data2")-min)+",\""+data1+"\","+val+"],";
			
		}
		rs.close();
		
		/*
		rs=st.executeQuery("select * from temptb where kpi = 'VIS'");
		while(rs.next()) {
			System.out.println(rs.getString(1)+' '+rs.getString(2)+' '+rs.getString(3)+' '+rs.getString(4));
		}
		rs.close();
		*/
		
		try {
			//double dMax = Collections.max(l);
			//max = (int) Math.round(dMax);
			//max = (int) Collections.max(l);
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
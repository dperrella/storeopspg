<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.util.Calendar"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.ArrayList"
import="java.util.Collections"
import="java.util.List"
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
	conn=Utility.getConnData();
	st=conn.createStatement();
	
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
	
	height = 500;
		
	//xAxis = "'08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00',"
	//		+ "'18:00','19:00','20:00','21:00'";
	
	//xAxis = "'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','23-23','23-24'";
	
	xAxis = "00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,23-23,23-24";
	
	//yAxis = "'Saturday','Friday','Thursday','Wednesday','Tuesday','Monday','Sunday'";
    
	Date dDtda = iso.parse(dtda);
	Calendar c = Calendar.getInstance();
	c.setTime(dDtda);
	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	
	System.out.println("******* "+dDtda+" "+dayOfWeek+" *******");
	String weekstart="";
	if(dayOfWeek==2)
		weekstart = "M";
	else 
		weekstart = "S";
	
	if(weekstart.equals("M"))
		yAxis = ""+propLang.get("dom")+","+propLang.get("sab")+","+propLang.get("ven")+","+propLang.get("gio")+","+propLang.get("mer")+","+propLang.get("mar")+","+propLang.get("lun")+"";
	else
		yAxis = ""+propLang.get("sab")+","+propLang.get("ven")+","+propLang.get("gio")+","+propLang.get("mer")+","+propLang.get("mar")+","+propLang.get("lun")+","+propLang.get("dom")+"";
		
		

	//yAxis = "'"+propLang.get("lun")+"','"+propLang.get("mar")+"','"+propLang.get("mer")+"','"+propLang.get("gio")+"','"+propLang.get("ven")+"','"+propLang.get("sab")+"','"+propLang.get("dom")+"'";
			 		
	/*
	sql   = "select max(_val) valore  from "
			+ "public.landing_heatmap('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi+"') ";
	*/
	/*
	sql   = "select max(_val) valore  from "
				+ "public.landing_heatmap_new('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi+"') ";
	rs=st.executeQuery(sql);
	if(rs.next()) {
		max = rs.getInt("valore");
	}
	rs.close();
	*/
	int min = 0;
	
	System.out.println("pag:"+pag);
	if(!pag.equals("D")) {
		String tmp[]=Utility.getXAxis(conn, idstores).split("##@@");
			
		xAxis = tmp[0];
		min = Integer.parseInt(tmp[1]);
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
		
		//val=""+rs.getString("valore");
		/*
		val=Utility.noNull(val);
		if(val.length()==0)
			val = "0";
		*/
		
		val=""+rs.getString("valore");
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
		heatmap+="["+(rs.getInt("data2")-min)+",\""+data1+"\","+val+"],";
		
	}
	rs.close();
	
	try {
		//double dMax = Collections.max(l);
		//max = (int) Math.round(dMax);
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
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
st.close();
conn.close();
%>
<%=output%>
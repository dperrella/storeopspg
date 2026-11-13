<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.ArrayList"
import="java.util.List"
import="pkg.beans.Tables2"
import="pkg.beans.Tables"
import="java.util.Date"
import="java.util.Calendar"
import="java.util.GregorianCalendar"
import="pkg.analytics2.DeskReportDevices"
import="org.json.simple.parser.JSONParser"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
String sql = "";

List<Tables2> sto = new ArrayList<Tables2>();
sto.clear();

List<Tables> timechart = new ArrayList<Tables>();
timechart.clear();


String dtda = request.getParameter("dtda");
dtda = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
String dtal = request.getParameter("dtal");
dtal = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
String idstores = request.getParameter("idstores");
String kpi = request.getParameter("kpi");
String kpi_selected = request.getParameter("kpi_selected");
String kpi_desc_selected = request.getParameter("kpi_selected");
String tipoPeriod = request.getParameter("tipoPeriod");
String uid = request.getParameter("uid2");

String iddevice = ""+request.getParameter("iddevice");
iddevice = Utility.noNull(iddevice);

String times="";
String output="";
String val = "";
String deviceList = "";


try {
	conn=Utility.getConnData();
	
	/*
	sql   = "select * from uuid_generate_v1()";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	if(rs.next()) {
		uid = rs.getString(1);
	}
	rs.close();
	st.close();
	*/
	
	sql   = "select a.*, xdtab, color "+
			"from test.temperature_grid('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+tipoPeriod+"',false, 5 ,'C01','"+uid+"') a , ztables "+
			"where xcdtb = 'KPI' and trim(codkpi) = trim(xcdel) "+
			"and idcat in(select idcat "+
			"from test.temperature_grid('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+tipoPeriod+"',false, 5 ,'C01','"+uid+"') "+
			"where idcat = "+iddevice+" "+
			"group by idcat having sum(tot) <> 0) order by category, codkpi ";
	
	
	System.out.println("-- --- "+sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	// Var per grafico
    String stores = "";
    
	int num = 0;
    
    String td = "";
    
    String descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Device</th><th style=\"border-top: 2px solid #E0E0E0;\">KPI</th>";
	
	deviceList = "";
	val = "";
	
	num = 0;
	int count = 0;
	int rowspan = 0;
	
	String temp3[] = kpi.split(",");
	rowspan = 3;
		
	
	String category = "";
	
	while(rs.next()) {
		
		category = rs.getString("category").replaceAll("'", " ");
		
		if(count==0) {
    		
    		if(num>0) {
    			
    			//System.out.print(rs.getString("category")+" ");
    			// Inserimento in array della riga
        		sto.add(new Tables2("STO",""+num,td,
        				"",category,"","","","","","","",""));
        		
    		}
    		td="";
    		
    		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\">"
    			+ "<b>"+rs.getString("category").replaceAll("'", " ")+"</b></td>";
    		
    		
    		
		}
		if(count>=1&&count<=rowspan)
    		td+="<tr>";
		
		td+="<td class=\"b-r\" style=\"width:120px\">"+rs.getString("xdtab")+"</td>";
		
		deviceList+="'"+rs.getString("codkpi").replaceAll("'", " ")+" "+rs.getString("desckpi").replaceAll("'", " ")+"',";
		
		for(int i=0;i<55;i++) {
			if(rs.getString((i+6))!=null) {
				//System.out.print(rs.getString((i+6))+" ");
				td+="<td class=\"b-r text-right\">"+Utility.round(rs.getDouble((i+6)) , 2)+"</td>";
				val+=Utility.round(rs.getDouble((i+6)) , 2)+",";
			}
		}
		
		if(val.length()>0)
			val=val.substring(0, val.length()-1);
		
		timechart.add(new Tables("TIME",rs.getString("codkpi").replaceAll("'", " "),val,rs.getString("desckpi").replaceAll("'", " "),"","",""));
		val="";
		//td+="<td class=\"b-r text-right\" style=\"width:120px\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
		td+="<td class=\"b-r text-right\" style=\"width:120px\"><b>-</b></td>";
			
		//System.out.println(rs.getString("tot"));
		
		td+="</tr>";
    	
		count+=1;
    	if(count==rowspan) {
    		count = 0;
    	}
    	num+=1;
    	
	}
	rs.close();
	st.close();
	
	// Inserimento in array della riga (ULTIMA RIGA)
	sto.add(new Tables2("STO",""+num,td,
			"",category,"","","","","","","",""));
	
	
	int nrrec = 0;
	for(int i=0;i<sto.size();i++) {
		
		
		output+=sto.get(i).getXdtab();
		
	}
	
	if(output.trim().length()>0)
		nrrec=1;
	
	System.out.println("nrrec: "+nrrec);
	
	if(nrrec==0) {
		output+="<tr><td colspan=\"60\"><div class=\"padder-lg\" align=\"center\"><h2>No Detail Data</h2></div></td>";
	}
	
	
	// ---
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
	
	// Intestazione
	if(tipoPeriod.equals("Y")) { 
 		
 		
 		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			//System.out.println(iso.format(startCalendar.getTime()));
 			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
 			
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
 		
	}
	else if(tipoPeriod.equals("YW")) { 
 		
		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));
 		
 		long diffWeeks = 0;
 		for( long t=1; ; t++ ) {         
 			startCalendar.add( Calendar.WEEK_OF_YEAR, 1 );   // add one day, week, year, etc.
 			if( startCalendar.after(endCalendar) ) {
 	            diffWeeks = t-1;
 	            break;
 			}
 	    }
 		
 			         		
 		for(int i=0;i<=diffWeeks;i++) {
 			
 			times+="'W"+i+"',";
 			
 		}
 		
 	
	}
	else if(tipoPeriod.equals("Q")) {
 		
		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			//System.out.println(iso.format(startCalendar.getTime()));
 			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
 			
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
 	
	}
	else if(tipoPeriod.equals("M")) {
	
		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));
 		
 		Date startDate = startCalendar.getTime();
 		Date endDate = endCalendar.getTime();
 		long startTime = startDate.getTime();
 		long endTime = endDate.getTime();
 		long diffTime = endTime - startTime;
 		long diffDays = diffTime / (1000 * 60 * 60 * 24);
 		DateFormat dateFormat = DateFormat.getDateInstance();
 		/*
 		System.out.println("The difference between "+
 		  dateFormat.format(startDate)+" and "+
 		  dateFormat.format(endDate)+" is "+
 		  diffDays+" days.");
 		*/
 		for(int i=0;i<=diffDays;i++) {
 			
 			//System.out.println(iso.format(startCalendar.getTime()));
 			times+="'"+iso.format(startCalendar.getTime())+"',";
 			
 			startCalendar.add(Calendar.DATE, 1);
 			
 		}
 		
	}
	else if(tipoPeriod.equals("W")) {
 		
		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));
 		
 		Date startDate = startCalendar.getTime();
 		Date endDate = endCalendar.getTime();
 		long startTime = startDate.getTime();
 		long endTime = endDate.getTime();
 		long diffTime = endTime - startTime;
 		long diffDays = diffTime / (1000 * 60 * 60 * 24);
 		DateFormat dateFormat = DateFormat.getDateInstance();
 		/*
 		System.out.println("The difference between "+
 		  dateFormat.format(startDate)+" and "+
 		  dateFormat.format(endDate)+" is "+
 		  diffDays+" days.");
 		*/
 		for(int i=0;i<=diffDays;i++) {
 			
 			times+="'"+iso.format(startCalendar.getTime())+"',";
 			
 		}
 		
	}
	else if(tipoPeriod.equals("D")) {
 		
 		for(int i=0;i<24;i++) {
 			String hh = "00"+i;
 			hh=hh.substring(hh.length()-2,hh.length());
 		}
 		times+="'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10,11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24',";
 		
	}
	
	if(times.length()>0)
		times=times.substring(0, times.length()-1);
	

	
}
catch(Exception e) {
	e.printStackTrace();
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


<div class="row">
							    		
	<div class="col-sm-12">
		<div id="tc" style="height:450px;"></div>
	</div>
</div>

<script>

var myChart_x = echarts.init(document.getElementById('tc'));

myChart_x.dispose();
$('#tc').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");

//based on prepared DOM, initialize echarts instance
var myChart_x = echarts.init(document.getElementById('tc'));

// specify chart configuration item and data
option_x = {
	color: [ '#EF2027', '#3F4787', '#5d6a79', '#F9CD2F', '#C5A848', '#595A7A', '#F7A42D', '#F47C2C', '#F25429', '#F14629'],
		    
    title : {
        text: 'Device: <%=iddevice%>',
       
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            
            type : 'shadow'      
        }
    },
    legend: {
    	data:[<%=deviceList%>],
        type: 'scroll',
        bottom: 10,
        left: 'center',
         
    },
    toolbox: {
        show : true,
        showTitle: false,
        feature : {
        	
        	//dataView : {show: true, readOnly: false, title: 'Data View'},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            // Lista negozi
            boundaryGap : false,
        
            data : [<%=times%>]
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    
    grid: {
    //    top: 110,
    //    left: 15,
    //    right: 15,
    //    height: 160
    	bottom: 80,
    },
    
    series : [
              
		<%
		for(int i=0;i<timechart.size();i++) {
		%>
		{
            name:'<%=timechart.get(i).getXcdel()%> <%=timechart.get(i).getCmp1()%>',
            type:'line',
            //areaStyle: {normal: {}},
            // Valori VIS
            data:[<%=timechart.get(i).getXdtab()%>],
            markLine : {
                data : [
                    {type : 'max', name: 'Max'}
                ]
            },
            itemStyle: {
                emphasis: {
        	        barBorderWidth: 1,
        	        shadowBlur: 10,
        	        shadowOffsetX: 0,
        	        shadowOffsetY: 0,
        	        shadowColor: 'rgba(0,0,0,0.5)'
        	    }
            },
        },
        <%
		}
		%>
        
    ]
};

// use configuration item and data specified to show chart
myChart_x.setOption(option_x);
</script>
##@@
<%=output%>
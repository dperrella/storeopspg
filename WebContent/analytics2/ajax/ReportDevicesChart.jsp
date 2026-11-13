<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.List"
import="pkg.beans.Tables"
import="java.util.ArrayList"
import="java.util.Date"
import="java.util.Calendar"
import="java.util.GregorianCalendar"
import="pkg.analytics2.DeskReportDevices"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
String sql = "";

String dtda = request.getParameter("dtda");
dtda = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
String dtal = request.getParameter("dtal");
dtal = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
String idstores = request.getParameter("idstores");
String kpi_selected = request.getParameter("kpi_selected");
String kpi_desc_selected = request.getParameter("kpi_selected");
String tipoPeriod = request.getParameter("tipoPeriod");
String uid = request.getParameter("uid");

int nrdev = 0;
String deviceList = "";
String val = "";
String times="";

List<Tables> timechart = new ArrayList<Tables>();
timechart.clear();

List<Tables> piechart = new ArrayList<Tables>();
piechart.clear();

try {
	conn=Utility.getConnData();
	
	// Grafico
	sql   = "select * from test.report_grid('"+dtda+"','"+dtal+"',"
				+ "'"+idstores.replaceAll(",","*")+"',"
				+ "'"+kpi_selected.replaceAll(",","*")+"','"+tipoPeriod+"',false, 5 ,'C01', '"+uid+"') "
				+ "where codkpi = '"+kpi_selected+"' and tot > 0 ";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	while(rs.next()) {
		
		nrdev+=1;
		deviceList+="'"+rs.getString("category").replaceAll("'", " ")+"',";
		
		for(int i=0;i<55;i++) {
			if(rs.getString((i+6))!=null) {
				val+=Utility.round(rs.getDouble((i+6)) , 2)+",";
			}
		}
		
		if(val.length()>0)
			val=val.substring(0, val.length()-1);
		
		timechart.add(new Tables("TIME",rs.getString("category").replaceAll("'", " "),val,"","","",""));
		piechart.add(new Tables("PIE",rs.getString("category").replaceAll("'", " "),""+Utility.round(rs.getDouble("tot"), 2),"","","",""));
		val="";
		
	}
	rs.close();
	st.close();
	
	System.out.println("nrdev:"+nrdev);
	
	if(deviceList.length()>0)
		deviceList=deviceList.substring(0, deviceList.length()-1);
	
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
 			startCalendar.add(Calendar.DATE, 1);
 			
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

String chart_col = "8";
String chart_col2= "4";

if(nrdev<=1) {
	chart_col = "12";
	chart_col2= "0";
}
else {
	chart_col = "8";
	chart_col2= "4";
}
%>
<%if(nrdev>0)  {%>
<div class="row">
							    		
	<div class="col-sm-<%=chart_col%>">
		<div id="main_det" style="height:450px;"></div>
	</div>
	<%if(nrdev>1)  {%>
	<div class="col-sm-4">
		<div id="pie_det" style="height:450px;"></div>
	</div>
	<%} %>
</div>

<script>
$('#panelChart').show();

var myChart = echarts.init(document.getElementById('main_det'));

myChart.dispose();
$('#main_det').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");

//based on prepared DOM, initialize echarts instance
var myChart = echarts.init(document.getElementById('main_det'));

// specify chart configuration item and data
option = {
	color: [ '#3F4787', '#EF2027', '#F9CD2F', '#C5A848', '#595A7A', '#F7A42D', '#F47C2C', '#F25429', '#F14629'],
		    
    title : {
        text: '<%=kpi_desc_selected%>',
       
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
            name:'<%=timechart.get(i).getXcdel()%>',
            type:'line',
            areaStyle: {normal: {}},
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
myChart.setOption(option);



<% if(nrdev > 1) { %>

var myChart2 = echarts.init(document.getElementById('pie_det'));

myChart2.dispose();
$('#pie_det').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");

var myChart2 = echarts.init(document.getElementById('pie_det'));

option2 = {
	color: [ '#3F4787', '#EF2027', '#F9CD2F', '#C5A848', '#595A7A', '#F7A42D', '#F47C2C', '#F25429', '#F14629'],
		
    title : {
        text: '<%=kpi_desc_selected%>',
        subtext: 'Devices',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    	 bottom: 10,
         left: 'center',
         data: [<%=deviceList%>],
         type: 'scroll',
         
    },
    toolbox: {
    	show : true,
        showTitle: false,
        
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
	    //    top: 110,
	    //    left: 15,
	    //    right: 15,
	    //    height: 160
	    	bottom: 80,
	},
    series : [
        {
            name: 'Stores',
            type: 'pie',
            //radius : '55%',
            radius: ['20%', '70%'],
            //center: ['50%', '60%'],
            data: [
            	<%
        		for(int i=0;i<timechart.size();i++) {
        		%>
            	{value:<%=piechart.get(i).getXdtab()%>, name:'<%=piechart.get(i).getXcdel()%>'},
            	<%
        		}
            	%>
            ],
        
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

myChart2.setOption(option2);
<%
	}
}
%>
</script>
<%
if(nrdev==0) {
%>
<script>
	$('#panelChart').hide();
</script>
<%
}
%>

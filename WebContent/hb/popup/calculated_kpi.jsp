<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.ArrayList"
import="java.util.Calendar"
import="java.util.Date"
import="java.util.GregorianCalendar"
import="java.util.HashMap"
import="java.util.List"
import="java.util.Locale"
import="java.util.Map"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%!
private static int getRowspan(Connection conn, String kpi) {
	int rowspan = 0;
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	
	try {
		st=conn.createStatement();
		sql="select count(kpi) num "
			+"from kpi_grouped "
			+"where grouped_kpi = '"+kpi+"' ";
		rs=st.executeQuery(sql);
		if(rs.next()) {
			rowspan = rs.getInt("num");
		}
		rs.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		try {
			if(st!=null)
				st.close();
		}
		catch(Exception e) {}
	}
	return rowspan;
}
%>
<%!
private static String getSign(Connection conn, String kpi, String calculated_type) {
	String sign = "";
	
	Statement st = null;
	ResultSet rs = null;
	String sql = "";
	
	try {
		st=conn.createStatement();
		if(calculated_type.equals("1")) {
			sql = "select sign from calculatedkpi where kpi = '"+kpi+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				sign = rs.getString("sign");
			}
			rs.close();
		}
		else {
			sign = "+";
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		try {
			if(st!=null)
				st.close();
		}
		catch(Exception e) {}
	}
	
	return sign;
}
%>

<% 
String filtered = "";

String kpi = ""+request.getParameter("kpi");
kpi = kpi.replaceAll("\\?cd=undefined","");
kpi = Utility.noNull(kpi);


String tp = ""+request.getParameter("tp");
tp = tp.replaceAll("\\?cd=undefined","");
tp = Utility.noNull(tp);

String dtda = ""+request.getParameter("dtda");
dtda = dtda.replaceAll("\\?cd=undefined","");
dtda = Utility.noNull(dtda);

String dtal = ""+request.getParameter("dtal");
dtal = dtal.replaceAll("\\?cd=undefined","");
dtal = Utility.noNull(dtal);

String idstores = ""+request.getParameter("idstores");
idstores = idstores.replaceAll("\\?cd=undefined","");
idstores = Utility.noNull(idstores);

String tipo = ""+request.getParameter("tipo");
tipo = tipo.replaceAll("\\?cd=undefined","");
tipo = Utility.noNull(tipo);

String l4l = ""+request.getParameter("l4l");
l4l = l4l.replaceAll("\\?cd=undefined","");
l4l = Utility.noNull(l4l);

String group = ""+request.getParameter("group");
group = group.replaceAll("\\?cd=undefined","");
group = Utility.noNull(group);

String comparison = ""+request.getParameter("comparison");
comparison = comparison.replaceAll("\\?cd=undefined","");
comparison = Utility.noNull(comparison);

String uid = ""+request.getParameter("uid");
uid = uid.replaceAll("\\?cd=undefined","");
uid = Utility.noNull(uid);

String id_advanced = ""+request.getParameter("id_advanced");
id_advanced = id_advanced.replaceAll("\\?cd=undefined","");
id_advanced = Utility.noNull(id_advanced);

String rowspan = ""+request.getParameter("rowspan");
rowspan = rowspan.replaceAll("\\?cd=undefined","");
rowspan = Utility.noNull(rowspan);

String op = ""+request.getParameter("op");
op = op.replaceAll("\\?cd=undefined","");
op = Utility.noNull(op);



//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

//**********************************************************
// Parametri per SQL
Connection conn = Utility.getConnData();		
PreparedStatement st = null;
ResultSet rs = null;
String sql = "";

String table = "";
String th = "";
String sign = "";
String calculated_type = "";

String kpi_tot = "";
String kpi_det = "";
String formula = "";

String legend = "";
String indicator = "";

try {
	
	/*
	if(uid.length()==0) {
		sql   = "select * from uuid_generate_v1()";
		st = conn.prepareStatement(sql);
		rs = st.executeQuery();
		if(rs.next()) {
			uid = rs.getString(1);
		}
		rs.close();
		st.close();
	}
	*/

	if(uid.length()==0) {
		sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated, calculated_type "
				+ "from public.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
				+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"') a, "
				+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' and op = '"+op+"' "
				+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
	}
	else {
		sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated, calculated_type "
				+ "from test.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
				+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"', '"+uid+"') a, "
				+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' and op = '"+op+"' "
				+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
	}
	System.out.println("NOR:"+sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	String td = "";
    int count = 0;
    int num = 0;
    
    String border = "";
    
    while(rs.next()) {
		
		calculated_type = rs.getString("calculated_type");
		sign = getSign(conn, rs.getString("codkpi").trim(), calculated_type);
				
		if(calculated_type.equals("1"))
	    	rowspan = "3";
		else 
			rowspan = ""+(getRowspan(conn,rs.getString("codkpi").trim())+1);
		
		if(count==0) {
       		
       		if(num>0) {
       			
       			System.out.println(td);
       			table+=td;
       			
       		}
       		td="";
       	
       		td+="<tr><td class=\"b-r\" style=\"width:120px;border-top: 2px solid #E0E0E0;\" rowspan=\""+rowspan+"\"><b>"+rs.getString("op")+"</b></td>";
		}
		
		if(count>=1&&count<=Integer.parseInt(rowspan)) {
       		td+="<tr>";
       		border="";
		}
		else {
			border="border-top: 2px solid #E0E0E0; ";
		}
		
		kpi_tot = rs.getString("desckpi");
		
		td+="<td class=\"b-r\" style=\"width:120px;"+border+"\">"
				+ "<i class=\"fa fa-circle pull-left text-xs m-t-xs\" "
				+ "style=\"color:"+rs.getString("color")+"\"></i> "
				+ rs.getString("desckpi")+"</td>";
		
		for(int i=0;i<31;i++) {
			if(rs.getString((i+5))!=null) {
				//System.out.print(rs.getString((i+6))+" ");
				td+="<td class=\"b-r text-right\" style=\""+border+"\">"+Utility.round(rs.getDouble((i+5)) , 2)+""+rs.getString("kpi_symbol")+"</td>";
				
			}
		}
		
		td+="<td class=\"b-r text-right\" style=\"width:120px;"+border+"\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
			
		//System.out.println(rs.getString("tot"));
		
		td+="</tr>";
       	
		count+=1;
       	if(count==Integer.parseInt(rowspan)) {
       		count = 0;
       	}
       	num+=1;
       	
       	
	}
	rs.close();
	st.close();
	
	System.out.println(td);
    table+=td;
	
    
    
    // ----------------------------------------------
    
    if(uid.length()==0) {
    	
    	
    	
		sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated "
				+ "from public.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
				+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"') a, "
				+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' and op = '"+op+"' "
				+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
	}
	else {
		sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated "
				+ "from test.report_total_det('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
				+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"', '"+uid+"') a, "
				+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' and op = '"+op+"' "
				+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
	}
	System.out.println("NOR:"+sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	td = "";
    count = 0;
    num = 0;
    
    border = "";
    
    
    legend = "[";
	indicator = "[";
	
    while(rs.next()) {
		
		if(count==0) {
    		
    		if(num>0) {
    			
    			System.out.println(td);
    			table+=td;
    			
    		}
    		td="";
    	
    		td+="<tr>";
		}
		
		if(count>=1&&count<=Integer.parseInt(rowspan)) {
    		td+="<tr>";
    		border="";
		}
		else {
			border="border-top: 2px solid #E0E0E0; ";
		}
		
		kpi_det+=rs.getString("desckpi")+"##@@";
		
		td+="<td class=\"b-r\" style=\"width:120px;"+border+"\">"
				+ "<i class=\"fa fa-circle pull-left text-xs m-t-xs\" "
				+ "style=\"color:"+rs.getString("color")+"\"></i> "
				+ rs.getString("desckpi")+"</td>";
		for(int i=0;i<31;i++) {
			if(rs.getString((i+5))!=null) {
				//System.out.print(rs.getString((i+6))+" ");
				td+="<td class=\"b-r text-right\" style=\""+border+"\">"+Utility.round(rs.getDouble((i+5)) , 2)+""+rs.getString("kpi_symbol")+"</td>";
				
			}
		}
		
		td+="<td class=\"b-r text-right\" style=\"width:120px;"+border+"\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
			
		indicator+="{ \"value\": "+Utility.round(rs.getDouble("tot"), 2)+", \"name\": \""+rs.getString("desckpi")+"\" },";
		legend+="\""+rs.getString("desckpi")+"\",";
		
		//System.out.println(rs.getString("tot"));
		
		td+="</tr>";
    	
		count+=1;
    	if(count==Integer.parseInt(rowspan)) {
    		count = 0;
    	}
    	num+=1;
    	
    	
	}
	rs.close();
	st.close();
	
	if(kpi_det.length()>0)
		kpi_det = kpi_det.substring(0,kpi_det.length()-4);
	
	String temp[] = kpi_det.split("##@@");
	for(int i=0;i<temp.length;i++) {
		
		formula+=" "+temp[i]+" "+sign;
		
	}
	
	if(formula.length()>0)
		formula = formula.substring(0,formula.length()-1);
	
	
	System.out.println(td);
    table+=td;
    
    if(indicator.length()>0)
		indicator = indicator.substring(0,indicator.length()-1);
	if(legend.length()>0)
		legend = legend.substring(0,legend.length()-1);
	
	indicator+="]";
	legend+="]";
    
    
    
    
    
    
    
    SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
	
    
    th="<th style=\"border-top: 2px solid #E0E0E0;\">TYPE</th><th style=\"border-top: 2px solid #E0E0E0;\">KPI</th>";
	
	String times="";
    
	// Intestazione
	if(tipo.equals("Y")) {	
 		
 		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
 		
 			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
 			
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
 		
 		
	}
	else if(tipo.equals("YW")) { 
 		
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
 			
 			th+="<th style=\"border-top: 2px solid #E0E0E0;\">W"+(i+1)+"</th>";
 		
 			times+="'W"+i+"',";
 			
 		}
 		
	}
	else if(tipo.equals("Q")) {
 		
		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
     		
 			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
	}
	else if(tipo.equals("M")) {
	
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
 		System.out.println("The difference between "+
 		  dateFormat.format(startDate)+" and "+
 		  dateFormat.format(endDate)+" is "+
 		  diffDays+" days.");
 		
 		for(int i=0;i<=diffDays;i++) {
 			
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
     		
 			times+="'"+iso.format(startCalendar.getTime())+"',";
 			startCalendar.add(Calendar.DATE, 1);
 			
 		}
 		
	}
	else if(tipo.equals("W")) {
 		
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
 		System.out.println("The difference between "+
 		  dateFormat.format(startDate)+" and "+
 		  dateFormat.format(endDate)+" is "+
 		  diffDays+" days.");
 		
 		for(int i=0;i<=diffDays;i++) {
 			
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
 			
 			times+="'"+iso.format(startCalendar.getTime())+"',";
 			
 			startCalendar.add(Calendar.DATE, 1);
 			
 		}
	}
	else if(tipo.equals("D")) {
 		
 		for(int i=0;i<24;i++) {
 			String hh = "00"+i;
 			hh=hh.substring(hh.length()-2,hh.length());
 			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+hh+":00</th>";
 		}
 		
 		/*
 		th="<th style=\"border-top: 2px solid #E0E0E0;\">08:00</th>"
 			+ "<th style=\"border-top: 2px solid #E0E0E0;\">09:00</th>"
 			+ "<th style=\"border-top: 2px solid #E0E0E0;\">10:00</th>"
 			+ "<th style=\"border-top: 2px solid #E0E0E0;\">11:00</th>"
 			+ "<th style=\"border-top: 2px solid #E0E0E0;\">12:00</th>"
 			+ "<th style=\"border-top: 2px solid #E0E0E0;\">13:00</th>"
 			+ "<th style=\"border-top: 2px solid #E0E0E0;\">14:00</th>"
				+ "<th style=\"border-top: 2px solid #E0E0E0;\">15:00</th>"
				+ "<th style=\"border-top: 2px solid #E0E0E0;\">16:00</th>"
				+ "<th style=\"border-top: 2px solid #E0E0E0;\">17:00</th>"
				+ "<th style=\"border-top: 2px solid #E0E0E0;\">18:00</th>"
				+ "<th style=\"border-top: 2px solid #E0E0E0;\">19:00</th>"
				+ "<th style=\"border-top: 2px solid #E0E0E0;\">20:00</th>"
				+ "<th style=\"border-top: 2px solid #E0E0E0;\">21:00</th>";
 		*/
 		
 		times+="'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10,11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24',";
 		
	}
	
	th+="<th style=\"border-top: 2px solid #E0E0E0;\">Total</th>";
	
	
	// filtered kpi
	sql   = "select id, description from rel_kpi_doors "+
			"join aisle_type on iddoor = id "+
			"where idkpi = '"+kpi.replaceAll(",","*")+"' ";
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	while(rs.next()) {
		filtered+="<tr><td>"+rs.getString("id")+"</td><td>"+rs.getString("description")+"</td></tr>";
	}
	rs.close();
	
	if(filtered.length()>0) {
		filtered="<div class=\"line line-dashed b-b line-lg pull-in\"></div><p class=\"h3\">Value from Door Type</p><br/>"+ 
				"<table class=\"table table-bordered\"><tr><th>Id</th><th>Description</th></tr>"+filtered+"</table>";
	}
	
}
catch(Exception e) {
	e.printStackTrace();
}
%>

<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">KPI: <%=kpi %></h3>
	</div>
	<div class="modal-body">
		
		<p class="h3"><%=kpi_tot%> = <%=formula %></p>
		<br/>
		
		
		<div style="width: 100%;overflow: auto;">
		<table class="table table-bordered">
		
		<tr>
		<%=th %>
		</tr>
		<%=table %>
		</table>
		</div>
		
		<div id="pieDett" style="height:400px"></div>
		
		<%=filtered %>
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
	</div>
  </div>
</div>

<script>

var sign = '<%=sign%>';
if(sign=='+') {
	

	var myChart = echarts.init(document.getElementById('pieDett'));
	
	option = {
	    title : {
	        text: '<%=kpi%>',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	    	orient: 'vertical',
	        x: 'left',
	        data: <%=legend%>,
	        type: 'scroll',
	         
	    },
	    
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    series : [
	        {
	            name: 'Stores',
	            type: 'pie',
	            radius : '55%',
	            //center: ['50%', '60%'],
	            data: <%=indicator%>,
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
	
	myChart.setOption(option);
}
else {
	$('#pieDett').hide();
}

</script>
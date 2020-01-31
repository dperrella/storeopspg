<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.GregorianCalendar"
import="pkg.utility.Utility"
import="java.util.ArrayList"
import="java.util.List"
import="pkg.beans.Tables"
import="pkg.beans.rfid.Step"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<%

List<Tables> radarlimit = new ArrayList<Tables>();
radarlimit.clear();

Connection conn = null;
PreparedStatement st 	= null;
ResultSet rs 	= null;
String sql 		= "";
String idsites = request.getParameter("idsites");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String step = request.getParameter("step");
String step_selected = request.getParameter("step_selected");
String tipo = request.getParameter("tipo");

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");

SimpleDateFormat iso2 = new SimpleDateFormat("dd/MM");

//Formattazione data
Date d1 = sdf.parse(dtda);
dtda = iso.format(d1);
Date d2 = sdf.parse(dtal);
dtal = iso.format(d2);

String output = "";

try {
	conn=Utility.getConnRfid();
	String step_orig = step_selected;
	
	step_selected = Step.selectIdstepByGroupedStep2(conn, Integer.parseInt(step_selected));
	System.out.println(step_selected);
	
	sql   = "select a.*, idstep, color, '' as tipo, 0.0 as tot from public.report_total('"+dtda+"','"+dtal+"','"+idsites.replaceAll(",","*")+"','"+step_selected.replaceAll(",","*")+"', "
			+"'"+tipo+"') a , step where step::numeric = idstep order by idstep ";
	
	sql   = "select "+step_orig+" , "+ 
			"sum(col1) col1, sum(col2) col2, sum(col3) col3, sum(col4) col4, sum(col5) col5, "+
			"sum(col6) col6, sum(col7) col7, sum(col8) col8, sum(col9) col9, sum(col10) col10, "+ 
			"sum(col11) col11, sum(col12) col12, sum(col13) col13, sum(col14) col14, sum(col15) col15, "+
			"sum(col16) col16, sum(col17) col17, sum(col18) col18, sum(col19) col19, sum(col20) col20, "+
			"sum(col21) col21, sum(col22) col22, sum(col23) col23, sum(col24) col24, sum(col25) col25, "+
			"sum(col26) col26, sum(col27) col27, sum(col28) col28, sum(col29) col29, sum(col30) col30, "+
			"sum(col31) col31, sum(col32) col32, sum(col33) col33, sum(col34) col34, sum(col35) col35, "+
			"sum(col36) col36, sum(col37) col37, sum(col38) col38, sum(col39) col39, sum(col40) col40, "+
			"sum(col41) col41, sum(col42) col42, sum(col43) col43, sum(col44) col44, sum(col45) col45, "+
			"sum(col46) col46, sum(col47) col47, sum(col48) col48, sum(col49) col49, sum(col50) col50, "+
			"sum(col51) col51, sum(col52) col52, sum(col53) col53, sum(col54) col54, sum(col55) col55, "+
			step_orig+" , max(color) color, '' as tipo, 0.0 as tot "+
			"from public.report_total('"+dtda+"','"+dtal+"','"+idsites.replaceAll(",","*")+"','"+step_selected.replaceAll(",","*")+"', "+
			"'"+tipo+"') a , step where step::numeric = idstep ";
	
	System.out.println("***** "+sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	String val = "";
	String col = "";
	if(rs.next()) {
		col = rs.getString("color");
		for(int i=0;i<55;i++) {
			if(rs.getString((i+2))!=null) {
				val+=Utility.round(rs.getDouble((i+2)) , 2)+",";
			}
		}
		if(val.length()>0)
			val=val.substring(0,val.length()-1);
		
	
	}
	rs.close();
	st.close();
	
	
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
	
	String times="";
    
	// Intestazione
	switch (tipo) {
 	case "Y": 
 		
 		
 		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			times+=""+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+",";
 			
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
 		
 		
 		break;
 		
 	case "YW": 
 		
 		/*
 		String settimane = getWeeks(conn, idcus, dtal.substring(0, 4));
 		String temp222[] = settimane.split(",");
 		int ini = Integer.parseInt(temp222[0]);
 		int fin = Integer.parseInt(temp222[1]);
 		*/
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
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
 			
 			times+="W"+i+",";
 			
 		}
 		
 		break;
 		
 	case "Q":
 		
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			
 			times+=""+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+",";
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
 		
 		break;
 		
 	case "M":
	
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
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
 			
 			
 			times+=""+iso2.format(startCalendar.getTime())+",";
 			startCalendar.add(Calendar.DATE, 1);
 			
 		}
 		
 		break;
 		
 	case "W":
 		
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));
 		
 		startDate = startCalendar.getTime();
 		endDate = endCalendar.getTime();
 		startTime = startDate.getTime();
 		endTime = endDate.getTime();
 		diffTime = endTime - startTime;
 		diffDays = diffTime / (1000 * 60 * 60 * 24);
 		dateFormat = DateFormat.getDateInstance();
 		System.out.println("The difference between "+
 		  dateFormat.format(startDate)+" and "+
 		  dateFormat.format(endDate)+" is "+
 		  diffDays+" days.");
 		
 		for(int i=0;i<=diffDays;i++) {
 			
 			System.out.println(iso2.format(startCalendar.getTime()));
 			
 			
 			times+=""+iso2.format(startCalendar.getTime())+",";
 			
 			startCalendar.add(Calendar.DATE, 1);
 			
 		}
 		
 		break;
 		
 	case "D":
 		
 		for(int i=0;i<24;i++) {
 			String hh = "00"+i;
 			hh=hh.substring(hh.length()-2,hh.length());
 		}
 		
 		times+="00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,22-23,23-24,";
 		break;
	}
	
		
	
	/*
	for(int i=0;i<temp.length;i++) {
		label+=""+tp+""+(i+1)+",";
	}
	*/
	String label = times;
	
	
	
	
	
	
	
	if(label.length()>0)
		label = label.substring(0,label.length()-1);
	
	output = val+"##@@"+label+"##@@"+col;
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
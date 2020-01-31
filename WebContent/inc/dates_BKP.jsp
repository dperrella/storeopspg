<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.Date"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Formato data
DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
Date date = new Date();
String oggi = dateNum.format(date);
String oggiIta = dateIta.format(date);
String oggiIso = dateIta.format(date);

String currentYear = oggi.substring(0,4);
String currentMonth = oggi.substring(4,6);
String currentWeek = "";

//=======================
//Settimane
String optYear = "";

Connection conn = null;
Statement st = null;

conn=Utility.getConnData();
st=conn.createStatement();
ResultSet rs = null;

String sql = "";

sql = "select * from financial_calendar "+
	"where customer_id = "+session.getAttribute("idcus")+" order by year ";
rs=st.executeQuery(sql);
String sel = "";
String fin_dtda = "";
String fin_dtal = "";

boolean is = false;
while(rs.next()) {
	if(rs.getString("year").equals(currentYear)) {
		sel = "selected";
		fin_dtda = dateIso.format(rs.getDate("fromdate"));
		fin_dtal = dateIso.format(rs.getDate("todate"));
		
		System.out.println(fin_dtda+" "+fin_dtal);
		
		is = true;
	}
	optYear+="<option value=\""+rs.getString("year")+"\" "+sel+">"+rs.getString("year")+"</option>";
}
rs.close();

if(is==false) {
	
	sql = "select * from financial_calendar where customer_id = 9999 order by year ";
	rs=st.executeQuery(sql);
	sel = "";
	fin_dtda = "";
	fin_dtal = "";

	while(rs.next()) {
		if(rs.getString("year").equals(currentYear)) {
			sel = "selected";
			fin_dtda = dateIso.format(rs.getDate("fromdate"));
			fin_dtal = dateIso.format(rs.getDate("todate"));
			
			is = true;
		}
		optYear+="<option value=\""+rs.getString("year")+"\" "+sel+">"+rs.getString("year")+"</option>";
	}
	rs.close();

	
}

//=======================
// Settimane
String optWE = "";

//String anno = currentYear;
String anno = fin_dtal.substring(0,4);
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

//campi tables
String $xcdtb = "SET";
String $xcdel = "";
String $xdtab = "";
String $cmp1 = "";

long nr_weeks = Utility.getDateDiff( dateIso.parse(fin_dtda), dateIso.parse(fin_dtal), Calendar.WEEK_OF_YEAR );

Calendar cal = Calendar.getInstance(new Locale("en","US"));
cal.set((Integer.parseInt(fin_dtda.substring(0,4))), (Integer.parseInt(fin_dtda.substring(5,7))-1), (Integer.parseInt(fin_dtda.substring(8,10))));
System.out.println("Date " + sdf.format(cal.getTime()));
cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
System.out.println("Date " + sdf.format(cal.getTime()));
String week = "";

for (int i=0; i<=nr_weeks; i++) {
	String dtda="";
	String dtal="";
	
	dtda = dateIta.format(cal.getTime());
	// valorizza il primo giorno della settimana
	$xdtab=sdf.format(cal.getTime())+" - ";
	
	cal.add(Calendar.DATE, 6);  
	
	dtal = dateIta.format(cal.getTime());
	// valorizza l'ultimo giorno della settimana
	$xdtab+=sdf.format(cal.getTime());
	
	//week = "00"+cal.get(Calendar.WEEK_OF_YEAR);
	//week = week.substring(week.length()-2,week.length());
	
	week="00"+(i+1);
	week = week.substring(week.length()-2,week.length());
	
	//System.out.println($xdtab);
	
	optWE+="<option value=\""+dtda+"#"+dtal+"\" "+$cmp1+">Week: "+week+" - "+$xdtab+"</option>";
	
	cal.add(Calendar.DATE, 1);  
	
	//*blank bariabili
	$xdtab = "";
	$cmp1 = "";
	
}

//=======================
// Mesi
String mm = "";
String desMm = "";
String optMM = "";

int mmini = Integer.parseInt(fin_dtda.substring(0,4)+fin_dtda.substring(5,7));
int mmfin = Integer.parseInt(fin_dtal.substring(0,4)+fin_dtal.substring(5,7));

// ======================
// Quarter
String optQQ = "";
int q = 0;
int qnum = 0;
for(int i=mmini;i<=mmfin;i++) {
	
	String sI = ""+i;
	
	if(i<=Integer.parseInt(sI.substring(0,4)+"12")&&Integer.parseInt(sI.substring(4,6))>0) {
		
		q+=1;
		//System.out.println(i);
		optMM+="<option value=\""+sI.substring(4,6)+"\">"+propLang.get(sI.substring(4,6))+"</option>";	
		
		if(q==3) {
			qnum+=1;
			
			int ini2 = (Integer.parseInt(sI.substring(4,6))-2);
			if(ini2<=0)
				ini2=ini2+12;
			
			
			String ini = "00"+ini2;
			ini = ini.substring(ini.length()-2,ini.length());
			
			//optQQ+="<option value=\"Q"+qnum+"\">Q"+qnum+" - "+propLang.get(ini)+" - "+propLang.get(sI.substring(4,6))+"</option>";	
			
			optQQ+="<option value=\""+ini+"#"+sI.substring(4,6)+"\">Q"+qnum+" - "+propLang.get(ini)+" - "+propLang.get(sI.substring(4,6))+"</option>";	
			
			q=0;
		}
	}	
}

st.close();
conn.close();
%>
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
DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
Date date = new Date();
String oggi = dateNum.format(date);
String oggiIta = dateIta.format(date);
String oggiIso = dateIta.format(date);

String currentYear = oggi.substring(0,4);
String currentMonth = oggi.substring(4,6);
String currentWeek = "";

String optYearM = "";
String optYearW = "";

//=======================
//Settimane
String optYear = "";

Connection conn = null;
Statement st = null;

conn=Utility.getConnData();
st=conn.createStatement();
ResultSet rs = null;

String sql = "";

sql = "select year from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' group by year order by year ";
rs=st.executeQuery(sql);
String sel = "";
while(rs.next()) {
	if(rs.getString("year").equals(currentYear)) {
		sel = "selected";
	}
	optYearM+="<option value=\""+rs.getString("year")+"\" "+sel+">"+rs.getString("year")+"</option>";
	sel="";
}
rs.close();

sql = "select year, count(calperiod) nr_weeks from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='W' group by year order by year ";
rs=st.executeQuery(sql);
sel = "";
int nr_weeks = 0;
while(rs.next()) {
	if(rs.getString("year").equals(currentYear)) {
		sel = "selected";
	}
	nr_weeks = rs.getInt("nr_weeks");
	optYearW+="<option value=\""+rs.getString("year")+"\" "+sel+">"+rs.getString("year")+"</option>";
	sel="";
}
rs.close();

//=======================
sql = "select min(fromdate) fromdate, max(todate) todate from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' and year = "+currentYear+" ";
System.out.println(sql);
rs=st.executeQuery(sql);
String fin_dtda = "";
String fin_dtal = "";
if(rs.next()) {
	fin_dtda = dateIso.format(rs.getDate("fromdate"));
	fin_dtal = dateIso.format(rs.getDate("todate"));
}
rs.close();

//=======================
// mesi
String optMM = "";
sql = "select * from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' and year = "+currentYear+" order by fromdate ";
System.out.println(sql);
rs=st.executeQuery(sql);
String mm = "";

while(rs.next()) {
	mm = "00"+rs.getInt("calperiod");
	mm = mm.substring(mm.length()-2,mm.length());
	
	String monthString;
    switch (rs.getInt("calperiod")) {
        case 1:  monthString = propLang.get("gen");
                 break;
        case 2:  monthString = propLang.get("feb");
                 break;
        case 3:  monthString = propLang.get("mar2");
                 break;
        case 4:  monthString = propLang.get("apr");
                 break;
        case 5:  monthString = propLang.get("mag");
                 break;
        case 6:  monthString = propLang.get("giu");
                 break;
        case 7:  monthString = propLang.get("lug");
                 break;
        case 8:  monthString = propLang.get("ago");
                 break;
        case 9:  monthString = propLang.get("set");
                 break;
        case 10: monthString = propLang.get("ott");
                 break;
        case 11: monthString = propLang.get("nov");
                 break;
        case 12: monthString = propLang.get("dic");
                 break;
        default: monthString = "Invalid month";
                 break;
    }
	optMM+="<option value=\""+mm+"\">"+monthString+"</option>";
	
}
rs.close();

	
int mmini = Integer.parseInt(fin_dtda.substring(0,4)+fin_dtda.substring(5,7));
int mmfin = Integer.parseInt(fin_dtal.substring(0,4)+fin_dtal.substring(5,7));

//======================
//Quarter
String optQQ = "";
int q = 0;
int qnum = 0;
for(int i=mmini;i<=mmfin;i++) {
	
	String sI = ""+i;
	
	if(i<=Integer.parseInt(sI.substring(0,4)+"12")&&Integer.parseInt(sI.substring(4,6))>0) {
		
		q+=1;
		
		if(q==3) {
			qnum+=1;
			
			int ini2 = (Integer.parseInt(sI.substring(4,6))-2);
			if(ini2<=0)
				ini2=ini2+12;
			
			String ini = "00"+ini2;
			ini = ini.substring(ini.length()-2,ini.length());
			
			optQQ+="<option value=\""+ini+"#"+sI.substring(4,6)+"\">Q"+qnum+" - "+propLang.get(ini)+" - "+propLang.get(sI.substring(4,6))+"</option>";	
			
			q=0;
		}
	}	
}

//=======================
// Settimane
String optWE = "";

sql = "select * from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='W' and year = "+currentYear+" order by calperiod ";
rs=st.executeQuery(sql);
String we = "";
while(rs.next()) {
	we = "00"+rs.getInt("calperiod");
	we = we.substring(we.length()-2,we.length());

	String dtda="";
	String dtal="";
	
	dtda = dateIta.format(rs.getDate("fromdate"));
	dtal = dateIta.format(rs.getDate("todate"));
	
	optWE+="<option value=\""+dtda+"#"+dtal+"\">Week: "+we+" - "+sdf.format(rs.getDate("fromdate"))+" - "+sdf.format(rs.getDate("todate"))+"</option>";
}

st.close();
conn.close();
%>
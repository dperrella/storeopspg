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
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String sql   = "";

conn=Utility.getConnRfid();
st=conn.createStatement();

//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String anno = ""+request.getParameter("anno");
anno = Utility.noNull(anno);

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
//mesi
String optMM = "";
sql = "select * from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' and year = "+anno+" order by fromdate ";
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

if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>
<%=optMM%>
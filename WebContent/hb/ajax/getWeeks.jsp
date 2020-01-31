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

conn=Utility.getConnData();
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

sql = "select * from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='W' and year = "+anno+" order by calperiod ";
rs=st.executeQuery(sql);
String we = "";
String optWE = "";

DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

while(rs.next()) {
	we = "00"+rs.getInt("calperiod");
	we = we.substring(we.length()-2,we.length());

	String dtda="";
	String dtal="";
	
	dtda = dateIta.format(rs.getDate("fromdate"));
	dtal = dateIta.format(rs.getDate("todate"));
	
	optWE+="<option value=\""+dtda+"#"+dtal+"\">Week: "+we+" - "+sdf.format(rs.getDate("fromdate"))+" - "+sdf.format(rs.getDate("todate"))+"</option>";
}

if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>
<%=optWE%>
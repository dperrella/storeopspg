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

//=======================
sql = "select min(fromdate) fromdate, max(todate) todate from financial_calendar2 where customer_id = "+session.getAttribute("idcus")+" and caltype='M' and year = "+anno+" ";
rs=st.executeQuery(sql);
String fin_dtda = "";
String fin_dtal = "";
while(rs.next()) {
	fin_dtda = dateIso.format(rs.getDate("fromdate"));
	fin_dtal = dateIso.format(rs.getDate("todate"));
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
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>
<%=optQQ%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.Calendar"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";
String json 	= "";

String provincia = ""+request.getParameter("provincia");
provincia=Utility.noNull(provincia);
String cond = "";
if(provincia.length()>0)
	cond=" where provincia = '"+provincia+"' ";
try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	sql = "select provincia, "
		+"json_agg(c.*) as comuni "
		+"from hr_comuni c "+cond+" "
		+"group by provincia "
		+"order by provincia ";
	rs=st.executeQuery(sql);
	
	json+="";
	while(rs.next()) {

		json+="{\"prov\": \""+rs.getString("provincia")+"\", \"comuni\": "+rs.getString("comuni")+"},";
			
	}
	rs.close();
	
	if(json.length()>0)
		json=json.substring(0,json.length()-1);
	
	json+="";
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
<%=json%>
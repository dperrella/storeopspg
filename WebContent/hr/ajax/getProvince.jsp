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

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	sql = "select provincia from hr_comuni c "
		+"group by provincia "
		+"order by provincia ";
	rs=st.executeQuery(sql);
	
	json+="[";
	while(rs.next()) {

		json+="{\"prov\": \""+rs.getString("provincia")+"\"},";
			
	}
	rs.close();
	
	if(json.length()>0)
		json=json.substring(0,json.length()-1);
	
	json+="]";
}
catch(Exception e) {
	e.printStackTrace();
}
st.close();
conn.close();
%>
<%=json%>
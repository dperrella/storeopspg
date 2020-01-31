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

String idflow = ""+request.getParameter("idflow");
idflow=Utility.noNull(idflow);
String cond = "";
if(idflow.length()>0)
	cond=" where idflow = "+idflow+" ";
try {
	conn=Utility.getConnRfid();
	st=conn.createStatement();
	sql = "select idflow, "
		+"json_agg(s.*) as step "
		+"from step s "+cond+" "
		+"group by idflow "
		+"order by idflow ";
	rs=st.executeQuery(sql);
	
	json+="";
	while(rs.next()) {

		json+="{\"idflow\": \""+rs.getString("idflow")+"\", \"step\": "+rs.getString("step")+"},";
			
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
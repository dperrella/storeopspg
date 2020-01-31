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
String idemp 	= request.getParameter("idemp");
String idrel 	= request.getParameter("idrel");
String role 	= request.getParameter("role");
String dep 		= request.getParameter("dep");
String start 	= request.getParameter("start");
String end	 	= request.getParameter("end");

System.out.println("idrel:"+idrel);

System.out.println("role:"+role);
System.out.println("dep:"+dep);
System.out.println("start:"+start);
System.out.println("end:"+end);

DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

try {
		
	conn=Utility.getConnHR();
	st=conn.createStatement();
	
	// Campi inserimento
	String fld = "";
	String val = "";
	if(start.length()>0) {
		fld+= "datestart ,";
		
		Date tick =  df.parse(start); 
		start = ""+(tick.getTime()/1000);
	    
		val+= "'"+start+"',";
	}
	if(end.length()>0) {
		fld+= "dateend ,";
		
		Date tick =  df.parse(end); 
		end = ""+(tick.getTime()/1000);
		
		val+= "'"+end+"',";
	}
	if(role.length()>0) {
		fld+= "id_roles ,";
		val+= "'"+role+"',";
	}
	if(dep.length()>0) {
		fld+= "id_departments ,";
		val+= "'"+dep+"',";
	}
	
	// Toglie ultimo byte
	if(fld.length()>0)
		fld = fld.substring(0,fld.length()-1);
	if(val.length()>0)
		val = val.substring(0,val.length()-1);
	
	if(fld.length()>0) {
		
		sql   = "insert into rel_dep_employee (id_employee, "+fld+") values ('"+idemp+"', "+val+") RETURNING id_reldepemployee  ";
		System.out.println(sql);
		
		rs = st.executeQuery(sql);
		
		String $idd="";
		if(rs.next()) {
			$idd = rs.getString(1);
			System.out.println("ID: "+rs.getString(1));
		}
		else {
			$idd = "-1";
			System.out.println("ID: -1");
		}
		rs.close();
		
		
		// In caso esistesse già una relazione per il dipendente imposta la data fine
		
		sql  =  "update rel_dep_employee "+
				"set dateend = "+(Long.parseLong(start)-86400)+" "+
				"where id_employee = '"+idemp+"' "+
				"and dateend is null and id_reldepemployee <> '"+$idd+"' ";
		System.out.println(sql);
		st.executeUpdate(sql);
		
		
		
	}
	
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	if(st!=null)
		st.close();
	if(conn!=null)
		conn.close();
}
%>
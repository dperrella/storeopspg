<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="pkg.beans.ats.Vendor"
import="pkg.beans.ats.Brand"
import="pkg.beans.ats.Category"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st = null;

String tipoForm = request.getParameter("tipoForm");
tipoForm = Utility.noNull(tipoForm);
tipoForm = tipoForm.trim();

String idcustomer = request.getParameter("idcustomer");
idcustomer = Utility.noNull(idcustomer);
idcustomer = idcustomer.trim();

String idusr = request.getParameter("idusr");
idusr = Utility.noNull(idusr);
idusr = idusr.trim();

try {
	conn=Utility.getConnData();
	st = conn.createStatement();
	
	String sql = "";
	
	switch (tipoForm) {
       
       	case "I": 
       		
       		sql = "insert into pref_user_customers (idusr, idcustomer) values ('"+idusr+"', "+idcustomer+") ";
    		
       	break;
       	
       	
       	case "D": 
       	
       		sql = "delete from pref_user_customers where idusr = '"+idusr+"' and idcustomer = "+idcustomer+" ";
    		
       	break;
	}
	
	System.out.println(sql);
	st.executeUpdate(sql);
	
	
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		st.close();
		conn.close();
	}
	catch(Exception e) {}
}
%>
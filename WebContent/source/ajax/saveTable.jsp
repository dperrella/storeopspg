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

String tipo = request.getParameter("tipo");
tipo = Utility.noNull(tipo);
tipo = tipo.trim();

String tipoForm = request.getParameter("tipoForm");
tipoForm = Utility.noNull(tipoForm);
tipoForm = tipoForm.trim();

String key = request.getParameter("key");
key = Utility.noNull(key);
key = key.trim();

String value = request.getParameter("value");
value = Utility.noNull(value);
value = value.trim();

System.out.println(tipo);
System.out.println(tipoForm);
System.out.println(key);
System.out.println(value);


try {
	conn=Utility.getConnData();
	
	if(tipo.equals("V")) {
			
		Vendor v = new Vendor();
		switch (tipoForm) {
        
        case "I": 
        	
        	v.setName(value);
        	v.add(conn);
        	
        	break;
        	
        case "E": 
        	
        	v.setIdVendor(Integer.parseInt(key));
        	v.setName(value);
        	v.update(conn);
        	
        	break;
        	
        case "D": 
        	
        	v.setIdVendor(Integer.parseInt(key));
        	v.delete(conn);

        	break;
		}
	}
	if(tipo.equals("B")) {
		
		Brand b = new Brand();
		switch (tipoForm) {
        
        case "I": 
        	
        	b.setName(value);
        	b.add(conn);
        	
        	break;
        	
        case "E": 
        	
        	b.setIdBrand(Integer.parseInt(key));
        	b.setName(value);
        	b.update(conn);
        	
        	break;
        	
        case "D": 
        	
        	b.setIdBrand(Integer.parseInt(key));
        	b.delete(conn);

        	break;
		}
		
	}
	if(tipo.equals("C")) {
		
		Category c = new Category();
		switch (tipoForm) {
        
        case "I": 
        	
        	c.setName(value);
        	c.add(conn);
        	
        	break;
        	
        case "E": 
        	
        	c.setIdCategory(Integer.parseInt(key));
        	c.setName(value);
        	c.update(conn);
        	
        	break;
        	
        case "D": 
        	
        	c.setIdCategory(Integer.parseInt(key));
        	c.delete(conn);

        	break;
		}
		
	}
	
	
	
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		conn.close();
	}
	catch(Exception e) {}
}
%>
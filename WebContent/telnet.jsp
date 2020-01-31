<%@ 
page language="java" 
contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"
import="java.sql.*"
import="pkg.utility.Utility"
import="java.io.BufferedReader"
import="java.io.InputStreamReader"
%>
<%
response.setHeader("Access-Control-Allow-Origin", "*"); 

// request
String ip = request.getParameter("ip");

String log = "";
String cmd = "";
try {
		
	
  	if(System.getProperty("os.name").startsWith("Windows")) {   
        // For Windows
        cmd = "ping -n 2 " + ip;
  	} else {
        // For Linux and OSX
    	cmd = "ping -c 2 " + ip;
  	}
    String resp = "";
    String s = "";
    
	try {
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		// reading output stream of the command
		while ((s = inputStream.readLine()) != null) {
			
			log+=s+"<br/>";
			
		}
		p.waitFor();
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	
}
%>
<%=log%>
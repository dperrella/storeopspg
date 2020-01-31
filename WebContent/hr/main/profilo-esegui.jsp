<%@ page 
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.sql.*"
import="java.io.*"
import="com.oreilly.servlet.*"
import="java.io.FileInputStream"
import="java.io.FileNotFoundException"
import="java.io.IOException"
import="java.io.InputStream"
import="java.util.HashMap"
import="java.security.MessageDigest"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

// Messaggio da restituire...
String msg = "";
String tpmsg = "";

int maxUploadSize 	= 50000000;
MultipartRequest multi = new MultipartRequest(request,".",maxUploadSize); 

String uidusr 	= (String) session.getAttribute("username");
String pwd 		= ""+multi.getParameter("password");
pwd=Utility.noNull(pwd);
String pwd2		= ""+multi.getParameter("password2");
pwd2=Utility.noNull(pwd2);
String nome		= ""+multi.getParameter("nome");
nome=Utility.noNull(nome);
String cogn		= ""+multi.getParameter("cogn");
nome=Utility.noNull(cogn);
String mail		= ""+multi.getParameter("mail");
mail=Utility.noNull(mail);
String nomeFile	= "";

File myFile		= null;

String root = "/var/lib/tomcat8/webapps/ROOT/images/";

// collegamento a DB
Connection conn = null;
Statement st = null;
ResultSet rs = null;

String sql = "";

try
{	
	
	try {
		// Carica il file
		myFile = multi.getFile("myFile"); 
		if(myFile!=null) {
			FileInputStream inStream = new FileInputStream(myFile);
			
			System.out.println(myFile.getName());
			
			FileOutputStream outStream = new FileOutputStream(root+myFile.getName());
			while (inStream.available()>0) 
			{
				outStream.write(inStream.read()); 
			}
			// Chiude il flusso
			inStream.close();
			outStream.close();
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
	conn=Utility.getConnAdmin();
	st = conn.createStatement();
	
	// Modifica Password
	if(pwd.trim().length()>0) {
		
		// Trasformo la password in MD5
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(pwd.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		pwd = sb.toString();
		
		sql   = "update zutenti set pwd='"+pwd.replaceAll("'", "''").trim()+"' where idusr = '"+uidusr+"' ";
		System.out.println(sql);
		st.executeUpdate(sql);
		
	}
	
	// Modifica Foto Profilo
	if(myFile!=null) {
		
		sql   = "update zutenti set foto='"+myFile.getName().replaceAll("'", "''")+"' where idusr = '"+uidusr.trim()+"' ";
		System.out.println(sql);
		st.executeUpdate(sql);
		
		session.setAttribute("foto", myFile.getName());
		
	}
	
	
	// Modifica Descrizione e Mail
	sql   = "update zutenti set nome = '"+nome.toUpperCase().trim().replaceAll("'", "''")+"', "+
			"cogn = '"+cogn.toUpperCase().replaceAll("'", "''")+"', mail = '"+mail.toUpperCase().trim().replaceAll("'", "''")+"' "+
			"where idusr = '"+uidusr.trim()+"' ";
	System.out.println(sql);
	st.executeUpdate(sql);
	
	msg = propLang.get("prof_msgok");
	tpmsg = "S";
}
catch(Exception err)
{
	err.printStackTrace();
	msg = propLang.get("prof_msgko")+err;
	tpmsg = "N";
}

if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>				        	
<script>
javascript:window.location='profilo.htm?msg=<%=msg%>&tpmsg=<%=tpmsg%>';
window.location.href='profilo.htm?msg=<%=msg%>&tpmsg=<%=tpmsg%>';
</script>

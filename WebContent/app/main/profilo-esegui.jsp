<%@ page 
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.sql.*"
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

String uidusr 	= (String) session.getAttribute("username");
String pwd 		= ""+request.getParameter("password");
pwd=Utility.noNull(pwd);
String pwd2		= ""+request.getParameter("password2");
pwd2=Utility.noNull(pwd2);
String nome		= ""+request.getParameter("nome");
nome=Utility.noNull(nome);
String cogn		= ""+request.getParameter("cogn");
cogn=Utility.noNull(cogn);
String mail		= ""+request.getParameter("mail");
mail=Utility.noNull(mail);
String nomeFile	= "";

System.out.println(nome);
System.out.println(cogn);
System.out.println(mail);


// collegamento a DB
Connection conn = null;
Statement st = null;
ResultSet rs = null;

String sql = "";

try
{	
	
		
	conn = Utility.getConnAdmin();
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

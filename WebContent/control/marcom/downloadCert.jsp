<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.io.PrintWriter"
import="java.io.File"
import="java.io.FileInputStream"
import="java.io.FileOutputStream"
import="java.io.IOException"
import="java.util.zip.ZipEntry"
import="java.util.zip.ZipOutputStream"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String id = request.getParameter("id");
String ca_bundle = request.getParameter("ca_bundle");
String certificate = request.getParameter("certificate");
String privatekey = request.getParameter("privatekey");
String configuration = request.getParameter("configuration");

System.out.println(id);
System.out.println(ca_bundle);
System.out.println(certificate);
System.out.println(privatekey);
System.out.println(configuration);



String CA_BUNDLE = getServletContext().getRealPath("ca_bundle.crt");
String CERTIFICATE = getServletContext().getRealPath("certificate.crt");
String PRIVATE_KEY = getServletContext().getRealPath("privatekey.key");
String CONFIGURATION = getServletContext().getRealPath("configuration.ovpn");


String ZIP_PATH = getServletContext().getRealPath("cert_"+id+".zip");
System.out.println(ZIP_PATH);

PrintWriter writer = null;

writer = new PrintWriter(CA_BUNDLE, "UTF-8");
writer.println(ca_bundle);
writer.close();

writer = new PrintWriter(CERTIFICATE, "UTF-8");
writer.println(certificate);
writer.close();

writer = new PrintWriter(PRIVATE_KEY, "UTF-8");
writer.println(privatekey);
writer.close();

writer = new PrintWriter(CONFIGURATION, "UTF-8");
writer.println(configuration);
writer.close();

String[] srcFiles = { CA_BUNDLE, CERTIFICATE, PRIVATE_KEY, CONFIGURATION};

try {
     
    // create byte buffer
    byte[] buffer = new byte[1024];

    FileOutputStream fos = new FileOutputStream(ZIP_PATH);

    ZipOutputStream zos = new ZipOutputStream(fos);
     
    for (int i=0; i < srcFiles.length; i++) {
         
        File srcFile = new File(srcFiles[i]);

        FileInputStream fis = new FileInputStream(srcFile);

        // begin writing a new ZIP entry, positions the stream to the start of the entry data
        zos.putNextEntry(new ZipEntry(srcFile.getName()));
         
        int length;

        while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
        }

        zos.closeEntry();

        // close the InputStream
        fis.close();
         
    }

    // close the ZipOutputStream
    zos.close();
     
}
catch (IOException ioe) {
    System.out.println("Error creating zip file: " + ioe);
}
%>
<%="cert_"+id+".zip" %>
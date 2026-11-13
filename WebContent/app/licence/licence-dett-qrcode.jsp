<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="pkg.utility.QRCodeGenerator"
import="java.util.Date"
import="pkg.utility.TrippleDes"
import="pkg.utility.Utility"

contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

String fileName = "";

String idapp = ""+request.getParameter("idapp");
idapp = Utility.noNull(idapp);
idapp=idapp.replaceAll("\\?cd=undefined", "");

String mac = ""+request.getParameter("macaddr");
mac = Utility.noNull(mac);
mac=mac.replaceAll("\\?cd=undefined", "");

System.out.println("mac:"+mac);



String QR_CODE_IMAGE_PATH = getServletContext().getRealPath(idapp+mac.replaceAll(":","")+".png");
System.out.println(QR_CODE_IMAGE_PATH);

String qrcode = "";
String licence = "";

if(mac.length()>0) {
	
	try {
        TrippleDes td = new TrippleDes();
        
        System.out.println(idapp+mac.toUpperCase());
        licence = td.encrypt(idapp+mac.toUpperCase());
    }
    catch(Exception e) {
        e.printStackTrace();
    }
	System.out.println(licence);
	QRCodeGenerator.generateQRCodeImage(licence, 350, 350, QR_CODE_IMAGE_PATH);
	
	qrcode = "<div class=\"text-center\"><img src=\"../../"+idapp+mac.replaceAll(":","")+".png\" style=\"width:350px;height:350px\"/></div>";
	
}
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Licence Key</h3>
	</div>
	<div class="modal-body">
		
		<div class="text-center" id="_qrcode"><img src="../../images/loader_xs.gif"/></div>
		
		<label>Licence Key</label>
		<div class="input-group">
          <input id="_lic" type="text" class="form-control" value="<%=licence %>" readonly>
          <span class="input-group-btn">
            <button class="btn btn-default" type="button" onclick="copyLic()">Copy</button>
          </span>
        </div>
		
		<hr/>
		
		<div class="text-center m-t m-b"><a href="../../licenceV2.jsp?idapp=<%=idapp %>&mac=<%=mac %>" target="_blank">Public link to this Licence</a></div>
	    
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload="parsleyForm();selpick();getQrcode('<%=idapp+mac.replaceAll(":","")%>.png')"/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a>
	</div>
  </div>
</div>
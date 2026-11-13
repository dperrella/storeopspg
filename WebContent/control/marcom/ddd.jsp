<%@ page 
language="java"
import="pkg.properties.ReadProperties"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
String file = request.getParameter("file");

  
String filename = file; 
String filepath = getServletContext().getRealPath(file);; 
response.setContentType("APPLICATION/OCTET-STREAM"); 
response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\""); 

java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath);
%>

<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
		<h3 class="font-thin">Download Certificate</h3>
      </div>
      <div class="modal-body">
      	<div align="center">
	        <h4>Click Button to Download</h4>
	        <div style="visibility:hidden">
			<textarea>
			<%
			int i; 
			while ((i=fileInputStream.read()) != -1) {
			  out.write(i); 
			} 
			%>
			</textarea>
			</div>
				        
				        
	        <p><a href="../../<%=file %>" class="btn btn-primary btn-lg">Download</a></p>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
</div>
<%
fileInputStream.close(); 
%>

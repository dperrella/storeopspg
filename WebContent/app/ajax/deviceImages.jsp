<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<div class="padder form-group pull-in clearfix">
<%
String output 	= "";
Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";

String iddevice  = request.getParameter("iddevice");
String deviceName  = request.getParameter("deviceName");
String deviceCode  = request.getParameter("deviceCode");
String ids  = request.getParameter("ids");



try {
	conn=Utility.getConnData();
	st=conn.createStatement();
	sql   = "select * from device_images where iddevice = "+iddevice+" ";
	System.out.println(sql);
	rs = st.executeQuery(sql);
	      
	while (rs.next()) {
%>
		<div class="col-sm-2" style="min-height:90px;max-height:90px">
			<a href="../popup/device-image.jsp?iddevice=<%=rs.getString("iddevice")%>&url=<%=rs.getString("url")%>" data-toggle="ajaxModal"><img src="../../images/loader_xs.gif" style="border:1px solid #c0c0c0;max-width: 100%; height: auto;" id="loader_<%=rs.getRow()%>"/></a></div>
		<script>
		// show loading image
		$('#loader_<%=rs.getRow()%>').show();
		
		tryToDownload('../../<%=rs.getString("url")%>','<%=rs.getRow()%>');
		</script>
<%
	}
	
	rs.close();
}
catch(Exception e) {
	e.printStackTrace();
	output = "";
}
finally {
	st.close();
	conn.close();
}
%>
	<div class="col-sm-2">
		<div style="border-width:5px;  
   			border-style:dashed;text-align:center;height:90px;padding-top:25px;padding:-5x">
			<a href="device-addphoto.jsp?iddevice=<%=iddevice %>&deviceName=<%=deviceName %>&deviceCode=<%=deviceCode %>&ids=<%=ids%>" style="font-size:20px" data-toggle="ajaxModal"><b>Add Image</b></a>
		</div>
	</div>
</div>
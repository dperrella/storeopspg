<%@ page 
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="pkg.utility.Utility"
import="java.sql.*"
import="java.io.IOException"
import="java.io.BufferedReader"
import="java.io.InputStreamReader"
import="java.net.*"
import="java.util.zip.GZIPInputStream"
import="pkg.properties.ReadProperties"
%>
<%!
private static String getWebPabeSource(String sURL) throws IOException {
    URL url = new URL(sURL);
    URLConnection urlCon = url.openConnection();
    BufferedReader in = null;

    if (urlCon.getHeaderField("Content-Encoding") != null
            && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                urlCon.getInputStream())));
    } else {
        in = new BufferedReader(new InputStreamReader(
                urlCon.getInputStream()));
    }

    String inputLine;
    StringBuilder sb = new StringBuilder();

    while ((inputLine = in.readLine()) != null)
        sb.append(inputLine);
    in.close();

    return sb.toString();
}
%>
<%
String url = request.getParameter("url");
String parm = request.getParameter("parm");
String p[]=parm.split(";");
parm="";
for(int i=0;i<p.length;i++) {
	parm+=p[i]+"&";
}
if(parm.length()>0)
	parm = parm.substring(0,parm.length()-1);

String res = getWebPabeSource(url+"?"+parm);
%>
<%=res %>
<!-- <%=url %>?<%=parm %><br/>-->
<!-- 
<iframe id="myframe" src="<%=url %>?<%=parm%>" style="height:350px;width:100%; border:1px" 
onload="onMyFrameLoad(this);"></iframe>

<script>
function resizeIframe(obj) {
  obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
}
</script>
-->

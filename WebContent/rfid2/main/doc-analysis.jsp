<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
import="java.text.DecimalFormat"
import="java.sql.*"
pageEncoding="ISO-8859-1"
%>
<% 
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
String table="";
%>
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Doc Analysis Detail</h3>
	</div>
	<div class="modal-body">
<%
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String iddoc = request.getParameter("iddoc");
iddoc=iddoc.replaceAll("\\?cd=undefined", "");
String doc_ref = request.getParameter("doc_ref");
doc_ref=doc_ref.replaceAll("\\?cd=undefined", "");
try {
	
	String sql = "select * from timeline where iddoc = "+iddoc+" order by tstamp ";	
	System.out.println(sql);
	
	conn = Utility.getConnRfid();
	st = conn.createStatement();
	rs = st.executeQuery(sql);
%>
				<div class="timeline">
                    <article class="timeline-item active">
                        <div class="timeline-caption">
                          <div class="panel bg-primary lt no-borders">
                            <div class="panel-body">
                              <span class="timeline-icon"><i class="icon-doc time-icon bg-primary"></i></span> 
                              <span class="timeline-date"><b><%=iddoc %></b></span>
                              <div class="text-sm"><%=doc_ref %></div>
                            </div>
                          </div>
                        </div>
                    </article>
<%
	String lr = "left";
	String alt = "";
	String pattern = "###,###.###";
	DecimalFormat df = new DecimalFormat(pattern);
	
	while(rs.next()) {
%>	

					<article class="timeline-item <%=alt%>">
                        <div class="timeline-caption">
                          <div class="panel panel-default">
                            <div class="panel-body">
                              <span class="arrow <%=lr%>"></span>
                              <span class="timeline-icon"><i class="icon-info time-icon bg-light"></i></span>
                              <span class="timeline-date"><%=rs.getString("tstamp") %></span>
                              <div class="text-sm">Readed: <b><%=rs.getString("letto") %></b></div>
                              <!-- <div class="text-sm"><%=rs.getString("totale") %></div>-->
                              <h5>                                
                                Read Percentage: <b><%=df.format(rs.getDouble("percentuale")) %>%</b>
                              </h5>
                            </div>       
                          </div>
                        </div>
                    </article>
		
<%	
		if(lr.equals("left")) {
			lr = "right";
			alt = "alt";
		}
		else {
			lr = "left";
			alt = "";
		}
	}
	
}
catch(Exception e) {
	
	e.printStackTrace();
%>
	<p><%=e.getMessage() %></p>
<%
}
finally {
	if(st!=null)
		st.close();
	if(conn!=null)
		conn.close();
}
%>
					</div>
	</div>
	<div class="modal-footer">
		<img src="../../images/spacer.gif" onload=""/>
		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_cancel") %></a>
	</div>
  </div>
</div>

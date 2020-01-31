<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
import="pkg.utility.Utility"
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
		<h3 class="font-thin">Row Detail</h3>
	</div>
	<div class="modal-body">
<%
Connection conn = null;
Statement st = null;
ResultSet rs = null;
String type = request.getParameter("type");
type=type.replaceAll("\\?cd=undefined", "");
String sku = request.getParameter("sku");
sku=sku.replaceAll("\\?cd=undefined", "");
String epc = request.getParameter("epc");
epc=epc.replaceAll("\\?cd=undefined", "");
try {
	
	String cond = "";
	
	
	if(type.equals("SKU")) {
		cond = " where barcode = '"+sku+"' "; 
	}
	else {
		cond = " where epc = '"+epc+"' "; 
	}
	
	String sql = "select ts, sum(units), id_location, l.description locdesc, id_site, "+
		"s.description sitedesc , epc, barcode, id_document, id_reason, r.description readesc, y.id_doctype, y.description doctypedesc, x.id, x.doc_ref, r.sign "+
		"from stockdiary d "+
		"join sites s on d.id_site = s.id "+
		"join location l on l.id = d.id_location "+
		"join reason r on r.id = d.id_reason "+
		"left join documents x on x.id = d.id_document "+
		"left join documents_type y on x.id_document_type = y.id_doctype "+
		cond+
		"group by ts, l.description, "+
		"id_location, l.description, id_site, s.description , "+
		"epc, barcode, id_document, id_reason, r.description, y.id_doctype, y.description, x.id, x.doc_ref, r.sign order by ts ";	
	
	conn = Utility.getConnRfid();
	st = conn.createStatement();
	rs = st.executeQuery(sql);
%>
				<div class="timeline">
                    <article class="timeline-item active">
                        <div class="timeline-caption">
                          <div class="panel bg-light no-borders">
                            <div class="panel-body">
                              <span class="timeline-icon"><i class="fa fa-barcode time-icon bg-light dker"></i></span> 
                              <span class="timeline-date"><b><%=sku %></b></span>
                              <div class="text-sm"><%=epc %></div>
                            </div>
                          </div>
                        </div>
                    </article>
<%
	String lr = "left";
	String alt = "";
	String sign = "";
	String doctypedesc = "";
	String doc_ref = "";
	while(rs.next()) {
		doctypedesc = ""+rs.getString("doctypedesc");
		doc_ref = ""+rs.getString("doc_ref");
		
		doctypedesc = Utility.noNull(doctypedesc);
		doc_ref = Utility.noNull(doc_ref);
		
		
		if(rs.getString("sign").equals("-")) {
			sign = "fa fa-minus-circle time-icon bg-danger";
		}
		else {
			sign = "fa fa-plus-circle time-icon bg-success";
		}
%>	

					<article class="timeline-item <%=alt%>">
                        <div class="timeline-caption">
                          <div class="panel panel-default">
                            <div class="panel-body">
                              <span class="arrow <%=lr%>"></span>
                              <span class="timeline-icon"><i class="<%=sign%>"></i></span>
                              <span class="timeline-date"><%=rs.getString("ts") %></span>
                              <div class="text-sm"><%=rs.getString("sitedesc") %></div>
                              <div class="text-sm"><%=rs.getString("locdesc") %></div>
                              <h5>                                
                                <b><%=rs.getString("readesc") %></b>
                                <br/>
                                <b><%=doctypedesc %></b>
                                <br/>
                                <b><%=doc_ref %></b>
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

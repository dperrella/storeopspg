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
<%
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");

String report_type = request.getParameter("report_type");
String language = (String) session.getAttribute("language");

String output 	= "";
Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;

String sql 		= "";

try {
	conn=Utility.getConnAnalytics();
	st=conn.createStatement();
	
	sql   = "select a.id, b.description "+
			"from an_subreport_type a, lang_subreport_type b "+
			"where b.lang = '"+language+"' and a.id=b.id_subreport_type "+
			"and a.id_report = "+report_type+" ";
	System.out.println(sql);
	rs = st.executeQuery(sql);
	
	output+="<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
	output+="<div class=\"form-group pull-in clearfix\">";
	output+="<div class=\"col-sm-12\">";
	output+="<label>"+propLang.get("anr_subreport_type")+"</label>";
	output+="<select id=\"subreport_type\" name=\"subreport_type\" data-required=\"true\" ";
	output+="onchange=\"showDatiReport()\" class=\"form-control parsley-validated chosen-select\">";
	output+="<option value=\"\">-</option>";
	      
	while (rs.next()) {
		output+="<option value=\""+rs.getString("id")+"\">"+rs.getString("id")+" - "+rs.getString("description")+"</option>";
	}
	output+="</select>";
	output+="</div>";
	output+="</div>";
	
	rs.close();
}
catch(Exception e) {
	e.printStackTrace();
	output = "";
}
st.close();
conn.close();
%>
<%=output%>
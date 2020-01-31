<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="pkg.utility.Utility"
import="pkg.HR.Minnum_employeeDett"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head> 
  <meta charset="utf-8" />
  <title>Report Planner | SmartHR</title>
  <meta name="description" content="SmartHR" />
  <link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />
  <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
  <style type="text/css" media="print">
	  @page { size: landscape; }
	  table td {
		  -webkit-print-color-adjust: exact;
		}
  </style>
</head>
<body class="">
	<section id="content" class="scrollable padder-lg">
<% 
String id_departments = request.getParameter("id_departments");
id_departments = Utility.noNull(id_departments);
String year = request.getParameter("year");
year = Utility.noNull(year);
String week = request.getParameter("week");
week = Utility.noNull(week);

//**********************************************************
// Parametri per SQL
Connection conn = null;
Statement st = null;
ResultSet rs = null;
	
String sql = "";
String cond = "";
int count = 0;

// **********************************************************
// Connessione al DATABASE
try
{
	conn=Utility.getConnHR();
	st = conn.createStatement();
	
	String des_departments = Minnum_employeeDett.getDescription(conn, id_departments);

%>
	<h1>Planner Settimana</h1>
	<h2><%=des_departments  %> - <%=year %> Settimana: <%=week %></h2>
<%
	sql   = "select t.xcdel, t.xdtab, c.name, e.*, d.description,  c.surname "+
			"from rel_dep_employee b "+
			"left join ztables t on xcdtb = 'GIO' "+
			"left join hr_roles a on a.id=b.id_roles "+
			"left join hr_employee c on b.id_employee = c.id "+
			"left join hr_departments d on b.id_departments = d.id "+
			"left join vw_planification_print e on b.id_employee = e.id_employee "+
			"and b.id_departments = e.id_departments and e.plan_year = '"+year+"' "+
			"and e.plan_week = '"+week+"' and cast(plan_day as char) = trim(t.xcdel) "+
			"where d.id = '"+id_departments+"' and b.dateend is null "+
			"and a.plan_view = 'Y' "+
			"order by cast(trim(xcdel) as numeric) ";	
	System.out.println(sql);
	rs=st.executeQuery(sql);
	
	String rott = "";
%>

<%
	String p7 = "";
	String p730 = "";
	String p8 = "";
	String p830 = "";
	String p9 = "";
	String p930 = "";
	String p10 = "";
	String p1030 = "";
	String p11 = "";
	String p1130 = "";
	String p12 = "";
	String p1230 = "";
	String p13 = "";
	String p1330 = "";
	String p14 = "";
	String p1430 = "";
	String p15 = "";
	String p1530 = "";
	String p16 = "";
	String p1630 = "";
	String p17 = "";
	String p1730 = "";
	String p18 = "";
	String p1830 = "";
	String p19 = "";
	String p1930 = "";
	String p20 = "";
	String p2030 = "";
	String p21 = "";
	String p2130 = "";
	String p22 = "";
	String p2230 = "";
	String p23 = "";
	String p2330 = "";
	String p24 = "";
	
	String bg7 = "";
	String bg730 = "";
	String bg8 = "";
	String bg830 = "";
	String bg9 = "";
	String bg930 = "";
	String bg10 = "";
	String bg1030 = "";
	String bg11 = "";
	String bg1130 = "";
	String bg12 = "";
	String bg1230 = "";
	String bg13 = "";
	String bg1330 = "";
	String bg14 = "";
	String bg1430 = "";
	String bg15 = "";
	String bg1530 = "";
	String bg16 = "";
	String bg1630 = "";
	String bg17 = "";
	String bg1730 = "";
	String bg18 = "";
	String bg1830 = "";
	String bg19 = "";
	String bg1930 = "";
	String bg20 = "";
	String bg2030 = "";
	String bg21 = "";
	String bg2130 = "";
	String bg22 = "";
	String bg2230 = "";
	String bg23 = "";
	String bg2330 = "";
	String bg24 = "";
	
	while(rs.next()) {
		
		if(!rott.equals(rs.getString("xcdel"))) {
			// Intestazione  giorno
			if(!rott.equals("")) {
%>	
			</table>
		</div>
	</section>
<%
			}
%>
	<section class="panel panel-default">
	    <header class="panel-heading font-bold">
	      <%=rs.getString("xdtab").trim() %> 
	    </header>
	    <div class="table-responsive">
			<table class="table table-striped m-b-none">	
				<tr>
					<td></td>
					<td class="b-r" style="border-right:1px solid #c0c0c0"></td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">7<br/>8</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">8<br/>9</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">9<br/>10</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">10<br/>11</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">11<br/>12</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">12<br/>13</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">13<br/>14</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">14<br/>15</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">15<br/>16</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">16<br/>17</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">17<br/>18</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">18<br/>19</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">19<br/>20</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">20<br/>21</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">21<br/>22</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">22<br/>23</td>
					<td class="b-r" style="border-right:1px solid #c0c0c0" colspan="2">23<br/>24</td>
					
<%
		}
%>
				<tr>
					<td style="width:230px"><%=rs.getString("name") %> <%=rs.getString("surname") %></td>
<%
p7 = ""+rs.getString(15);
p7=Utility.noNull(p7);
if(p7.length()>0) {
	if(Integer.parseInt(p7)>0) {
		bg7 = "url('green.gif');";
		p7 = "X";
	}
	else {
		p7 = "&nbsp;&nbsp;";
	}
}
p730 = ""+rs.getString(16);
p730=Utility.noNull(p730);
if(p730.length()>0) {
	if(Integer.parseInt(p730)>0) {
		bg730 = "url('green.gif');";
		p730 = "X";
	}
	else {
		p730 = "&nbsp;&nbsp;";
	}
}
p8 = ""+rs.getString(17);
p8=Utility.noNull(p8);
if(p8.length()>0) {
	if(Integer.parseInt(p8)>0) {
		bg8 = "url('green.gif');";
		p8 = "X";
	}
	else {
		p8 = "&nbsp;&nbsp;";
	}
}
p830 = ""+rs.getString(18);
p830=Utility.noNull(p830);
if(p830.length()>0) {
	if(Integer.parseInt(p830)>0) {
		bg830 = "url('green.gif');";
		p830 = "X";
	}
	else {
		p830 = "&nbsp;&nbsp;";
	}
}
p9 = ""+rs.getString(19);
p9=Utility.noNull(p9);
if(p9.length()>0) {
	if(Integer.parseInt(p9)>0) {
		bg9 = "url('green.gif');";
		p9 = "X";
	}
	else {
		p9 = "&nbsp;&nbsp;";
	}
}
p930 = ""+rs.getString(20);
p930=Utility.noNull(p930);
if(p930.length()>0) {
	if(Integer.parseInt(p930)>0) {
		bg930 = "url('green.gif');";
		p930 = "X";
	}
	else {
		p930 = "&nbsp;&nbsp;";
	}
}
p10 = ""+rs.getString(21);
p10=Utility.noNull(p10);
if(p10.length()>0) {
	if(Integer.parseInt(p10)>0) {
		bg10 = "url('green.gif');";
		p10 = "X";
	}
	else {
		p10 = "&nbsp;&nbsp;";
	}
}

p1030 = ""+rs.getString(22);
p1030=Utility.noNull(p1030);
if(p1030.length()>0) {
	if(Integer.parseInt(p1030)>0) {
		bg1030 = "url('green.gif');";
		p1030 = "X";
	}
	else {
		p1030 = "&nbsp;&nbsp;";
	}
}
p11 = ""+rs.getString(23);
p11=Utility.noNull(p11);
if(p11.length()>0) {
	if(Integer.parseInt(p11)>0) {
		bg11 = "url('green.gif');";
		p11 = "X";
	}
	else {
		p11 = "&nbsp;&nbsp;";
	}
}
p1130 = ""+rs.getString(24);
p1130=Utility.noNull(p1130);
if(p1130.length()>0) {
	if(Integer.parseInt(p1130)>0) {
		bg1130 = "url('green.gif');";
		p1130 = "X";
	}
	else {
		p1130 = "&nbsp;&nbsp;";
	}
}
p12 = ""+rs.getString(25);
p12=Utility.noNull(p12);
if(p12.length()>0) {
	if(Integer.parseInt(p12)>0) {
		bg12 = "url('green.gif');";
		p12 = "X";
	}
	else {
		p12 = "&nbsp;&nbsp;";
	}
}
p1230 = ""+rs.getString(26);
p1230=Utility.noNull(p1230);
if(p1230.length()>0) {
	if(Integer.parseInt(p1230)>0) {
		bg1230 = "url('green.gif');";
		p1230 = "X";
	}
	else {
		p1230 = "&nbsp;&nbsp;";
	}
}
p13 = ""+rs.getString(27);
p13=Utility.noNull(p13);
if(p13.length()>0) {
	if(Integer.parseInt(p13)>0) {
		bg13 = "url('green.gif');";
		p13 = "X";
	}
	else {
		p13 = "&nbsp;&nbsp;";
	}
}
p1330 = ""+rs.getString(28);
p1330=Utility.noNull(p1330);
if(p1330.length()>0) {
	if(Integer.parseInt(p1330)>0) {
		bg1330 = "url('green.gif');";
		p1330 = "X";
	}
	else {
		p1330 = "&nbsp;&nbsp;";
	}
}
p14 = ""+rs.getString(29);
p14=Utility.noNull(p14);
if(p14.length()>0) {
	if(Integer.parseInt(p14)>0) {
		bg14 = "url('green.gif');";
		p14 = "X";
	}
	else {
		p14 = "&nbsp;&nbsp;";
	}
}
p1430 = ""+rs.getString(30);
p1430=Utility.noNull(p1430);
if(p1430.length()>0) {
	if(Integer.parseInt(p1430)>0) {
		bg1430 = "url('green.gif');";
		p1430 = "X";
	}
	else {
		p1430 = "&nbsp;&nbsp;";
	}
}
p15 = ""+rs.getString(31);
p15=Utility.noNull(p15);
if(p15.length()>0) {
	if(Integer.parseInt(p15)>0) {
		bg15 = "url('green.gif');";
		p15 = "X";
	}
	else {
		p15 = "&nbsp;&nbsp;";
	}
}
p1530 = ""+rs.getString(32);
p1530=Utility.noNull(p1530);
if(p1530.length()>0) {
	if(Integer.parseInt(p1530)>0) {
		bg1530 = "url('green.gif');";
		p1530 = "X";
	}
	else {
		p1530 = "&nbsp;&nbsp;";
	}
}
p16 = ""+rs.getString(33);
p16=Utility.noNull(p16);
if(p16.length()>0) {
	if(Integer.parseInt(p16)>0) {
		bg16 = "url('green.gif');";
		p16 = "X";
	}
	else {
		p16 = "&nbsp;&nbsp;";
	}
}
p1630 = ""+rs.getString(34);
p1630=Utility.noNull(p1630);
if(p1630.length()>0) {
	if(Integer.parseInt(p1630)>0) {
		bg1630 = "url('green.gif');";
		p1630 = "X";
	}
	else {
		p1630 = "&nbsp;&nbsp;";
	}
}
p17 = ""+rs.getString(35);
p17=Utility.noNull(p17);
if(p17.length()>0) {
	if(Integer.parseInt(p17)>0) {
		bg17 = "url('green.gif');";
		p17 = "X";
	}
	else {
		p17 = "&nbsp;&nbsp;";
	}
}
p1730 = ""+rs.getString(36);
p1730=Utility.noNull(p1730);
if(p1730.length()>0) {
	if(Integer.parseInt(p1730)>0) {
		bg1730 = "url('green.gif');";
		p1730 = "X";
	}
	else {
		p1730 = "&nbsp;&nbsp;";
	}
}
p18 = ""+rs.getString(37);
p18=Utility.noNull(p18);
if(p18.length()>0) {
	if(Integer.parseInt(p18)>0) {
		bg18 = "url('green.gif');";
		p18 = "X";
	}
	else {
		p18 = "&nbsp;&nbsp;";
	}
}
p1830 = ""+rs.getString(38);
p1830=Utility.noNull(p1830);
if(p1830.length()>0) {
	if(Integer.parseInt(p1830)>0) {
		bg1830 = "url('green.gif');";
		p1830 = "X";
	}
	else {
		p1830 = "&nbsp;&nbsp;";
	}
}
p19 = ""+rs.getString(39);
p19=Utility.noNull(p19);
if(p19.length()>0) {
	if(Integer.parseInt(p19)>0) {
		bg19 = "url('green.gif');";
		p19 = "X";
	}
	else {
		p19 = "&nbsp;&nbsp;";
	}
}
p1930 = ""+rs.getString(40);
p1930=Utility.noNull(p1930);
if(p1930.length()>0) {
	if(Integer.parseInt(p1930)>0) {
		bg1930 = "url('green.gif');";
		p1930 = "X";
	}
	else {
		p1930 = "&nbsp;&nbsp;";
	}
}
p20 = ""+rs.getString(41);
p20=Utility.noNull(p20);
if(p20.length()>0) {
	if(Integer.parseInt(p20)>0) {
		bg20 = "url('green.gif');";
		p20 = "X";
	}
	else {
		p20 = "&nbsp;&nbsp;";
	}
}
p2030 = ""+rs.getString(42);
p2030=Utility.noNull(p2030);
if(p2030.length()>0) {
	if(Integer.parseInt(p2030)>0) {
		bg2030 = "url('green.gif');";
		p2030 = "X";
	}
	else {
		p2030 = "&nbsp;&nbsp;";
	}
}
p21 = ""+rs.getString(43);
p21=Utility.noNull(p21);
if(p21.length()>0) {
	if(Integer.parseInt(p21)>0) {
		bg21 = "url('green.gif');";
		p21 = "X";
	}
	else {
		p21 = "&nbsp;&nbsp;";
	}
}
p2130 = ""+rs.getString(44);
p2130=Utility.noNull(p2130);
if(p2130.length()>0) {
	if(Integer.parseInt(p2130)>0) {
		bg2130 = "url('green.gif');";
		p2130 = "X";
	}
	else {
		p2130 = "&nbsp;&nbsp;";
	}
}
p22 = ""+rs.getString(45);
p22=Utility.noNull(p22);
if(p22.length()>0) {
	if(Integer.parseInt(p22)>0) {
		bg22 = "url('green.gif');";
		p22 = "X";
	}
	else {
		p22 = "&nbsp;&nbsp;";
	}
}
p2230 = ""+rs.getString(46);
p2230=Utility.noNull(p2230);
if(p2230.length()>0) {
	if(Integer.parseInt(p2230)>0) {
		bg2230 = "url('green.gif');";
		p2230 = "X";
	}
	else {
		p2230 = "&nbsp;&nbsp;";
	}
}
p23 = ""+rs.getString(47);
p23=Utility.noNull(p23);
if(p23.length()>0) {
	if(Integer.parseInt(p23)>0) {
		bg23 = "url('green.gif');";
		p23 = "X";
	}
	else {
		p23 = "&nbsp;&nbsp;";
	}
}
p2330 = ""+rs.getString(48);
p2330=Utility.noNull(p2330);
if(p2330.length()>0) {
	if(Integer.parseInt(p2330)>0) {
		bg2330 = "url('green.gif');";
		p2330 = "X";
	}
	else {
		p2330 = "&nbsp;&nbsp;";
	}
}

%>
					
					<td class="b-r" style="border-right: 1px solid #c0c0c0;"></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg7%>"><%=p7 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg730%>"><%=p730 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg8%>"><%=p8 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg830%>"><%=p830 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg9%>"><%=p9 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg930%>"><%=p930 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg10%>"><%=p10 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1030%>"><%=p1030 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg11%>"><%=p11 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1130%>"><%=p1130 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg12%>"><%=p12 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1230%>"><%=p1230 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg13%>"><%=p13 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1330%>"><%=p1330 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg14%>"><%=p14 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1430%>"><%=p1430 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg15%>"><%=p15 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1530%>"><%=p1530 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg16%>"><%=p16 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1630%>"><%=p1630 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg17%>"><%=p17 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1730%>"><%=p1730 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg18%>"><%=p18 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1830%>"><%=p1830 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg19%>"><%=p19 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg1930%>"><%=p1930 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg20%>"><%=p20 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg2030%>"><%=p2030 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg21%>"><%=p21 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg2130%>"><%=p2130 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg22%>"><%=p22 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg2230%>"><%=p2230 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg23%>"><%=p23 %></td>
					<td class="b-r" style="color:#1ab667; font-size:8px; border-right: 1px solid #c0c0c0; background: <%=bg2330%>"><%=p2330 %></td>
					</tr>
				
<%		
		bg7 = "";
		bg730 = "";
		bg8 = "";
		bg830 = "";
		bg9 = "";
		bg930 = "";
		bg10 = "";
		bg1030 = "";
		bg11 = "";
		bg1130 = "";
		bg12 = "";
		bg1230 = "";
		bg13 = "";
		bg1330 = "";
		bg14 = "";
		bg1430 = "";
		bg15 = "";
		bg1530 = "";
		bg16 = "";
		bg1630 = "";
		bg17 = "";
		bg1730 = "";
		bg18 = "";
		bg1830 = "";
		bg19 = "";
		bg1930 = "";
		bg20 = "";
		bg2030 = "";
		bg21 = "";
		bg2130 = "";
		bg22 = "";
		bg2230 = "";
		bg23 = "";
		bg2330 = "";
		bg24 = "";
		
		rott = rs.getString("xcdel");	
	}
	rs.close();
%>
			</table>
		</div>
	</section>
<%
}
catch(Exception e) {
	e.printStackTrace();
}
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();
%>
	</section>
	
	<script>
	//window.print();
	</script>
<%@ include file="../../inc/autorized.jsp" %>	
  </body>
</html>
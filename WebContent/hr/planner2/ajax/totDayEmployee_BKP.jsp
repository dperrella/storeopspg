<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.Calendar"
import="java.util.Locale"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
Connection conn = null;
Statement st 	= null;
ResultSet rs 	= null;
String sql 		= "";

String msg 		= "";
try {
	String id_departments=request.getParameter("id_departments");
	String year=request.getParameter("year");
	String week=request.getParameter("week");
	String day=request.getParameter("day");
	
	conn=Utility.getConnHR();
	st=conn.createStatement();
	sql = "select row_to_json(t) as t "
		+"from ( "
		+"select "
		+"sum(distinct coalesce((("
		+"	(cast(e.\"6\" as float)) + (cast(e.\"6:30\" as float)) + "
		+"	(cast(e.\"7\" as float)) + (cast(e.\"7:30\" as float)) + "
		+"	(cast(e.\"8\" as float)) + (cast(e.\"8:30\" as float)) + "
		+"	(cast(e.\"9\" as float)) + (cast(e.\"9:30\" as float)) + "
		+"	(cast(e.\"10\" as float)) + (cast(e.\"10:30\" as float)) + "
		+"	(cast(e.\"11\" as float)) + (cast(e.\"11:30\" as float)) + "
		+"	(cast(e.\"12\" as float)) + (cast(e.\"12:30\" as float)) + "
		+"	(cast(e.\"13\" as float)) + (cast(e.\"13:30\" as float)) + "
		+"	(cast(e.\"14\" as float)) + (cast(e.\"14:30\" as float)) + "
		+"	(cast(e.\"15\" as float)) + (cast(e.\"15:30\" as float)) + "
		+"	(cast(e.\"16\" as float)) + (cast(e.\"16:30\" as float)) + "
		+"	(cast(e.\"17\" as float)) + (cast(e.\"17:30\" as float)) + "
		+"	(cast(e.\"18\" as float)) + (cast(e.\"18:30\" as float)) + "
		+"	(cast(e.\"19\" as float)) + (cast(e.\"19:30\" as float)) + "
		+"	(cast(e.\"20\" as float)) + (cast(e.\"20:30\" as float)) + "
		+"	(cast(e.\"21\" as float)) + (cast(e.\"21:30\" as float)) + "
		+"	(cast(e.\"22\" as float)) + (cast(e.\"22:30\" as float)) + "
		+"	(cast(e.\"23\" as float)) + (cast(e.\"23:30\" as float)) "
		+"	)/2) , null , 0) ) totempday,b.id_employee "
		+"	from vw_emp_dep_indate b "
		+"	left join vw_planification_print_new2 e on b.id_employee = e.id_employee "
		//+"	and b.id_departments = e.id_departments "
		+"  and e.plan_year::integer = b.anno "
		+"	and e.plan_week::integer = b.settimana "
		+"	and e.plan_day::integer = b.dow "
		+"	where "
		+"	( "
		+"	b.id_departments = '"+id_departments+"' "
		+"	and b.anno = "+year+" and b.settimana = "+week+" and b.dow = "+day+" "
		+"	)  group by b.id_employee ) t ";
	System.out.println("*****"+sql);
	rs=st.executeQuery(sql);
	msg+="{\"employee\":[";
	String data = "";
	while(rs.next()) {
		System.out.println(rs.getString("t"));
		data+=rs.getString("t")+",";
	}
	rs.close();
	if(data.length()>0)
		data=data.substring(0,data.length()-1);
	msg+=data+"]";
	
	// Totali per giorno
	sql   = "select  "+
			"sum( ((cast(e.\"6\" as float) + cast(e.\"6:30\" as float))/2)) v6 ,"+
			"sum( ((cast(e.\"7\" as float) + cast(e.\"7:30\" as float))/2)) v7 ,"+
			"sum( ((cast(e.\"8\" as float) + cast(e.\"8:30\" as float))/2)) v8 ,"+
			"sum( ((cast(e.\"9\" as float) + cast(e.\"9:30\" as float))/2)) v9 ,"+
			"sum( ((cast(e.\"10\" as float) + cast(e.\"10:30\" as float))/2)) v10 ,"+
			"sum( ((cast(e.\"11\" as float) + cast(e.\"11:30\" as float))/2)) v11 ,"+
			"sum( ((cast(e.\"12\" as float) + cast(e.\"12:30\" as float))/2)) v12 ,"+
			"sum( ((cast(e.\"13\" as float) + cast(e.\"13:30\" as float))/2)) v13 ,"+
			"sum( ((cast(e.\"14\" as float) + cast(e.\"14:30\" as float))/2)) v14 ,"+
			"sum( ((cast(e.\"15\" as float) + cast(e.\"15:30\" as float))/2)) v15 ,"+
			"sum( ((cast(e.\"16\" as float) + cast(e.\"16:30\" as float))/2)) v16 ,"+
			"sum( ((cast(e.\"17\" as float) + cast(e.\"17:30\" as float))/2)) v17 ,"+
			"sum( ((cast(e.\"18\" as float) + cast(e.\"18:30\" as float))/2)) v18 ,"+
			"sum( ((cast(e.\"19\" as float) + cast(e.\"19:30\" as float))/2)) v19 ,"+
			"sum( ((cast(e.\"20\" as float) + cast(e.\"20:30\" as float))/2)) v20 ,"+
			"sum( ((cast(e.\"21\" as float) + cast(e.\"21:30\" as float))/2)) v21 ,"+
			"sum( ((cast(e.\"22\" as float) + cast(e.\"22:30\" as float))/2)) v22 ,"+
			"sum( ((cast(e.\"23\" as float) + cast(e.\"23:30\" as float))/2)) v23 "+
			"from vw_planification_print_new2 e "+
			"where e.id_departments = '"+id_departments+"' "+
			"and e.plan_year::integer = "+year+" "+
			"and e.plan_week::integer = "+week+" "+
			"and e.plan_day::integer = "+day+" ";
	rs=st.executeQuery(sql);
	if(rs.next()) {
		msg+=",\"v6\":"+rs.getDouble("v6")+",";
		msg+="\"v7\":"+rs.getDouble("v7")+",";
		msg+="\"v8\":"+rs.getDouble("v8")+",";
		msg+="\"v9\":"+rs.getDouble("v9")+",";
		msg+="\"v10\":"+rs.getDouble("v10")+",";
		msg+="\"v11\":"+rs.getDouble("v11")+",";
		msg+="\"v12\":"+rs.getDouble("v12")+",";
		msg+="\"v13\":"+rs.getDouble("v13")+",";
		msg+="\"v14\":"+rs.getDouble("v14")+",";
		msg+="\"v15\":"+rs.getDouble("v15")+",";
		msg+="\"v16\":"+rs.getDouble("v16")+",";
		msg+="\"v17\":"+rs.getDouble("v17")+",";
		msg+="\"v18\":"+rs.getDouble("v18")+",";
		msg+="\"v19\":"+rs.getDouble("v19")+",";
		msg+="\"v20\":"+rs.getDouble("v20")+",";
		msg+="\"v21\":"+rs.getDouble("v21")+",";
		msg+="\"v22\":"+rs.getDouble("v22")+",";
		msg+="\"v23\":"+rs.getDouble("v23")+",";
		
	}
	rs.close();
	
	
	
	/*
	sql = "select row_to_json(t) as t "
		+"from ( "
		+"select "
		+"sum(distinct coalesce((("
		+"	(cast(e.\"6\" as float)) + (cast(e.\"6:30\" as float)) + "
		+"	(cast(e.\"7\" as float)) + (cast(e.\"7:30\" as float)) + "
		+"	(cast(e.\"8\" as float)) + (cast(e.\"8:30\" as float)) + "
		+"	(cast(e.\"9\" as float)) + (cast(e.\"9:30\" as float)) + "
		+"	(cast(e.\"10\" as float)) + (cast(e.\"10:30\" as float)) + "
		+"	(cast(e.\"11\" as float)) + (cast(e.\"11:30\" as float)) + "
		+"	(cast(e.\"12\" as float)) + (cast(e.\"12:30\" as float)) + "
		+"	(cast(e.\"13\" as float)) + (cast(e.\"13:30\" as float)) + "
		+"	(cast(e.\"14\" as float)) + (cast(e.\"14:30\" as float)) + "
		+"	(cast(e.\"15\" as float)) + (cast(e.\"15:30\" as float)) + "
		+"	(cast(e.\"16\" as float)) + (cast(e.\"16:30\" as float)) + "
		+"	(cast(e.\"17\" as float)) + (cast(e.\"17:30\" as float)) + "
		+"	(cast(e.\"18\" as float)) + (cast(e.\"18:30\" as float)) + "
		+"	(cast(e.\"19\" as float)) + (cast(e.\"19:30\" as float)) + "
		+"	(cast(e.\"20\" as float)) + (cast(e.\"20:30\" as float)) + "
		+"	(cast(e.\"21\" as float)) + (cast(e.\"21:30\" as float)) + "
		+"	(cast(e.\"22\" as float)) + (cast(e.\"22:30\" as float)) + "
		+"	(cast(e.\"23\" as float)) + (cast(e.\"23:30\" as float)) "
		+"	)/2) , null , 0)) totempday,b.id_employee, monteore "
		+"	from vw_emp_dep_indate b "
		+"	left join vw_planification_print_new2 e on b.id_employee = e.id_employee "
		//+"	and b.id_departments = e.id_departments "
		+"  and e.plan_year::integer = b.anno "
		+"	and e.plan_week::integer = b.settimana "
		+"	and e.plan_day::integer = b.dow "
		+"	where "
		+"	( "
		+"	b.id_departments = '"+id_departments+"' "
		+"	and b.anno = "+year+" and b.settimana = "+week+" "
		+"	) group by b.id_employee, monteore) t ";
	*/
	
	sql = "select row_to_json(t) as t "
		+"from ( select "
		+"(select sum(tot_lun) + sum(tot_mar) + sum(tot_mer) + sum(tot_gio) + sum(tot_ven) + sum(tot_sab) + sum(tot_dom) "
		+"from vw_totweek_new z "
		+"where z.id_departments = '"+id_departments+"' "
		+"and z.anno = "+year+" and z.settimana = "+week+"  and dow = 0 "
		+"and z.id_employee = b.id_employee "
		+") "
		+"as totempday, "
		+"b.id_employee, monteore "
		+"from vw_emp_dep_indate b "
		+"where "
		+"( "
		+"b.id_departments = '"+id_departments+"' "
		+"and b.anno = "+year+" and b.settimana = "+week+"  "
		+") "
		+"group by b.id_employee, monteore) t ";

	System.out.println("sql monte: "+sql);
	
	
	rs=st.executeQuery(sql);
	msg+="\"monte\":[";
	data = "";
	while(rs.next()) {
		System.out.println(rs.getString("t"));
		data+=rs.getString("t")+",";
	}
	rs.close();
	if(data.length()>0)
		data=data.substring(0,data.length()-1);
	msg+=data+"]";
	
	msg+="}";
	
	
	
	System.out.println("*******"+msg);
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
	}
	catch(Exception e) {}
}
%>
<%=msg%>
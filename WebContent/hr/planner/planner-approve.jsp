<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.util.Locale"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="pkg.utility.Utility"
import="pkg.utility.JSendMail"
import="pkg.HR.Minnum_employeeDett"
import="pkg.utility.PlannerMail"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

//Utente sessione
String idusr = (String)session.getAttribute("username");
		
String id_departments = request.getParameter("id_departments");
String year = request.getParameter("year");
String week = request.getParameter("week");
String status = request.getParameter("status");
String tp = request.getParameter("tp");

// In caso di riapertura 
String note = ""+request.getParameter("note");
note = Utility.noNull(note);

String sql = "";
String msg = "";

Connection conn = null;
Statement st 	= null;
Statement up 	= null;
ResultSet rs 	= null;

// Connadmin
Connection connAdmin = null;
Statement stAdmin = null;

String last_ts = "";
Calendar c = Calendar.getInstance(Locale.GERMAN);
c.setTime(new Date());
last_ts = ""+(c.getTimeInMillis() / 1000);

String dsdep = "";

try {
	conn=Utility.getConnHR();
	st=conn.createStatement();
	up=conn.createStatement();
	
	try {
		// dsdep
		sql = "select description from hr_departments where id = '"+id_departments+"' ";
		rs=st.executeQuery(sql);
		if(rs.next()) {
			dsdep = rs.getString("description");			
		}
		rs.close();
	}
	catch(Exception e) {
		System.out.println("ERR: "+e.getMessage()+"\nSQL: "+sql);
	}
	connAdmin=Utility.getConnAdmin();
	stAdmin=connAdmin.createStatement();
	
	sql   = "update hr_planification set status = '"+status+"', last_ts = "+last_ts+" , idusr_mod = '"+idusr+"' "+
			"where plan_year = '"+year+"' and plan_week = '"+week+"' "+
			"and id_employee in (select x.id_employee from rel_dep_employee x "+
			"where x.id_departments = '"+id_departments+"') and id_departments = '"+id_departments+"' ";
	st.executeUpdate(sql);
	
	// In caso di note...
	if(note.length()>0) {
		
		//sql   = "delete from hr_planification_note where id_departments = '"+id_departments+"' and plan_year = '"+year+"' and plan_week = '"+week+"' ";
		//st.executeUpdate(sql);
		
		sql   = "insert into hr_planification_note "+
				"(id_departments, plan_year, plan_week, idusr, tstamp, note) "+
				"values "+
				"('"+id_departments+"', '"+year+"', '"+week+"', '"+session.getAttribute("username")+"', current_date, '"+note.toUpperCase().replaceAll("'","''")+"')";
		System.out.println(sql);
		st.executeUpdate(sql);
		
	}
	
	String des_departments = Minnum_employeeDett.getDescription(conn, id_departments);
	
	String roleDest = "";
	String roleDest2= "";
	
	// Notifica
	String idusrDest = "";
	String title = "";
	String notif = "";
	String tpLink = "";
	
	// Per mail
	String mitt = "";
	String dest = "";
	String ogg = "";
	String mess = "";
	// Richiedi approvazione
	if(status.equals("X")) {
		title = "Planner";
		notif = propLang.get("notif_richiedi");
		// Area Manager
		roleDest = "'92b2a236-ba12-4b94-96ef-11bde7b85769'";
		roleDest2= "'4cfd7452-037f-49e0-aaf9-ea84ed110133'";
		tpLink="A";
		ogg = propLang.get("msg_mesRichiedi");
		mess = propLang.get("msg_mesRichiedi")+" "+des_departments+" "+year+"/"+week+"";
	}
	// Rigetta 
	if(status.equals("R")) {
		title = "Planner";
		notif = propLang.get("notif_rigetta");
		// Store Manager
		roleDest = "'a497f308-08e0-4ce0-8e1c-80f50c291186'";
		roleDest2= "'4cfd7452-037f-49e0-aaf9-ea84ed110133'";
		tpLink="I";
		ogg = propLang.get("msg_oggRigetta");
		mess = propLang.get("msg_mesRigetta")+" "+des_departments+" "+year+"/"+week+"";;
	}
	// Approva 
	if(status.equals("A")) {
		title = "Planner";
		notif = propLang.get("notif_approva");
		// Store Manager
		roleDest = "'a497f308-08e0-4ce0-8e1c-80f50c291186'";
		roleDest2= "'4cfd7452-037f-49e0-aaf9-ea84ed110133'";
		tpLink="I";
		ogg = propLang.get("msg_oggApprova");
		mess = propLang.get("msg_mesApprova")+" "+des_departments+" "+year+"/"+week+"";;
	}
	// Riapri 
	if(status.equals("Y")) {
		title = "Planner";
		notif = propLang.get("notif_riapri");
		// Store Manager
		roleDest = "'a497f308-08e0-4ce0-8e1c-80f50c291186','92b2a236-ba12-4b94-96ef-11bde7b85769'";
		roleDest2= "'4cfd7452-037f-49e0-aaf9-ea84ed110133'";
		tpLink="I";
		ogg = propLang.get("msg_oggRiapri");
		mess = propLang.get("msg_mesRiapri")+" "+des_departments+" "+year+"/"+week+".<br/><br/>ID USER: "+idusr+"<br/>"+note.toUpperCase()+"";
		
	}
	
	// ==========================
	// Seleziona Utente (Store Manager / Area Manager)
	sql   = "select a.id_employee, b.name, b.surname, b.email, c.name as role, id_user "+
			"from rel_dep_employee a , hr_employee b "+
			"left join rel_emp_user u on u.id_employee = b.id, hr_roles c "+
			"where id_departments = '"+id_departments+"' "+
			"and dateend is null and a.id_employee = b.id and a.id_roles = c.id "+
			"and id_roles in("+roleDest+") ";
	System.out.println(sql);
	
	rs=st.executeQuery(sql);
	while(rs.next()) {
		// Invia Notifica
		sql   = "Insert into znotif "+
				"(idnota, link, img, titolo, nota, stato, idusr, level, cdapp) "+
				"values "+
				"("+Utility.getNewProgr(connAdmin, "znotif", "idnota", "")+" , 'hr/planner2/planner.htm?tp="+tpLink+"&year="+year+"&week="+week+"&id_departments="+id_departments+"', "+
				"'<i class=\"fa fa-warning i-2x\"></i>' , '"+title.replaceAll("'","''")+"', '"+notif.replaceAll("'","''")+"', '', '"+rs.getString("id_user")+"', 0 , 'SHR') ";
		System.out.println(sql);
		stAdmin.executeUpdate(sql);
		
		// Messaggio
		mess = PlannerMail.getMess(mess, "hr/planner/planner-list-tab2.htm", tpLink, year, week, id_departments, rs.getString("id_user"));
		
		// Invia mail TEST
		// JSendMail.msgMail("danieleperrella@yahoo.it",ogg,mess,"","smartHR@wycon.it");
		// Invia mail
		try {
			JSendMail.msgMail(rs.getString("email"),ogg,mess,"","smarthr@smartretail.it");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	rs.close();
	
	
	// ==========================
	// Seleziona Utente (HR Manager)
	sql   = "select a.id_employee, b.name, b.surname, b.email, c.name as role, id_user "+
			"from rel_dep_employee a , hr_employee b "+
			"left join rel_emp_user u on u.id_employee = b.id, hr_roles c "+
			"where b.id_customer = '"+session.getAttribute("idcus")+"' "+
			"and dateend is null and a.id_employee = b.id and a.id_roles = c.id "+
			"and id_roles in("+roleDest2+") ";
	System.out.println(sql);
	
	rs=st.executeQuery(sql);
	while(rs.next()) {
		// Invia Notifica
		sql   = "Insert into znotif "+
				"(idnota, link, img, titolo, nota, stato, idusr, level, cdapp) "+
				"values "+
				"("+Utility.getNewProgr(connAdmin, "znotif", "idnota", "")+" , 'hr/planner/planner-list-tab2.htm?tp="+tpLink+"&year="+year+"&week="+week+"&id_departments="+id_departments+"', "+
				"'<i class=\"fa fa-warning i-2x\"></i>' , '"+title.replaceAll("'","''")+"', '<b>("+dsdep.replaceAll("'", "''")+ " "+year+"/"+week+")</b> "+notif.replaceAll("'","''")+"', '', '"+rs.getString("id_user")+"', 0 ,'SHR') ";
		System.out.println(sql);
		stAdmin.executeUpdate(sql);
		
		// Messaggio
		mess = PlannerMail.getMess(mess, "hr/planner/planner-list-tab2.htm", tpLink, year, week, id_departments, rs.getString("id_user"));
		
		// Invia mail TEST
		// JSendMail.msgMail("danieleperrella@yahoo.it",ogg,mess,"","smartHR@wycon.it");
		// Invia mail
		try {
			JSendMail.msgMail(rs.getString("email"),ogg,mess,"","smarthr@smartretail.it");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	rs.close();
	
	
	
	msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
		"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
}
catch(Exception e) {
	e.printStackTrace();
	
	msg="<div class=\"alert alert-danger\""+propLang.get("msgError")+e.getMessage()+"</div>"+
		"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
}
if(up!=null)
	up.close();
if(st!=null)
	st.close();
if(conn!=null)
	conn.close();

if(stAdmin!=null)
	stAdmin.close();
if(connAdmin!=null)
	connAdmin.close();
%>
<script>
javascript:window.location='planner-ric.htm?tp=<%=tp%>&msg=<%=msg%>';
window.location.href='planner-ric.htm?tp=<%=tp%>&msg=<%=msg%>';
</script>
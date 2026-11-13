<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.util.ArrayList"
import="java.util.List"
import="pkg.beans.Tables"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%

List<Tables> radarlimit = new ArrayList<Tables>();
radarlimit.clear();

Connection conn = null;
PreparedStatement st 	= null;
ResultSet rs 	= null;
String sql 		= "";
String idstores = request.getParameter("idstores");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String kpi = request.getParameter("kpi");
String kpi_selected = request.getParameter("kpi_selected");
String uid = request.getParameter("uid");

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");

//Formattazione data
Date d1 = sdf.parse(dtda);
dtda = iso.format(d1);
Date d2 = sdf.parse(dtal);
dtal = iso.format(d2);

String output = "";

try {
	conn=Utility.getConnData();
	
	// *******
	// tablist...
	sql   = "select * from landing_widget('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', 1000, 0, '"+uid+"') , "
			+ "ztables where xcdtb = 'KPI' and trim(_kpi) = trim(xcdel) order by _kpi, _inc desc";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	String rott = "";
	String kpi_rs = "";
	double kpi_100 = 0;
	String tab = "";
	int count = 0;
	
	String kpi_list = "";
	String kpi_best = "";
	String kpi_key  = "";
	String kpi_desc = "";
	double kpi_inc  = 0;
	String kpi_color= "";
	String kpi_idstore="";
	String best_selected="";
	String best_list="";
	double kpi_inc_best  = 0;
	
	String active = "";
	while(rs.next()) {
		if(!rs.getString("_kpi").equals(rott)) {

			if(rott.length()>0) {
				//if(tab.length()>0)
				//	tab=tab.substring(0,tab.length()-1);
				if(kpi_list.length()>0)
					kpi_list=kpi_list.substring(0,kpi_list.length()-1);
				System.out.println(tab);
				
				if(kpi_rs.equals(kpi_selected)) {
					active = "active";
					best_selected = kpi_best;
					best_list = kpi_list;
				}
				else {
					active = "";
				}
				
				System.out.println(rs.getString("_kpi")+" "+active);
				/*
				tablist.add(new Tables2("KPI",kpi_rs,tab,kpi_best,kpi_list,kpi_desc,active,
						kpi_color,"","","","",""));
				
				radarlimit.add(new Tables("RADAR", kpi_best, kpi_desc, ""+kpi_100,"","",""));
				*/	
				
				radarlimit.add(new Tables("RADAR", kpi_best, kpi_desc, ""+kpi_100,"","",""));
			}
			
			System.out.println("--");
			
			kpi_rs = rs.getString("_kpi");
			kpi_100= rs.getDouble("_inc");
			kpi_best = rs.getString("_id_store");
			System.out.println(kpi_rs + " "+kpi_100);
			
			count = 0;
			tab = "";
			kpi_list = "";
			
		}
		
		count+=1;
		
		kpi_key  = rs.getString("_kpi");
		kpi_desc = rs.getString("xdtab");
		kpi_inc  = rs.getDouble("_inc");
		kpi_color= rs.getString("color");
		kpi_idstore=rs.getString("_id_store");
		
		kpi_list+=rs.getString("_id_store")+"*";
		//tab+=count+"##@@"+kpi_best+"##@@"+rs.getString("_id_store")+"##@@"+Math.round((((100*rs.getDouble("_inc"))/kpi_100)))+",";
		tab+="<tr><td>"+count+".</td><td><a href=\"#\" onclick=\"callReport('"+kpi_idstore+"')\">"+rs.getString("_description")+"</a></td><td><b>"+rs.getDouble("_inc")+"</b></td><td>"+
			"<div class=\"progress progress-xs\" style=\"margin-bottom:-10px\">"+
            "<div class=\"progress-bar bg-dark\" data-toggle=\"tooltip\" data-original-title=\""+Math.round((((100*rs.getDouble("_inc"))/kpi_100)))+"%\" "+
			"style=\"width: "+Math.round((((100*rs.getDouble("_inc"))/kpi_100)))+"%\"></div>"+
			"</div></td></tr>";
		
		//System.out.println(kpi_rs);
		
		rott = rs.getString("_kpi");
		
	}
	rs.close();
	st.close();
	
	// Ultimo record
	//if(tab.length()>0)
	//	tab=tab.substring(0,tab.length()-1);
	if(kpi_list.length()>0)
		kpi_list=kpi_list.substring(0,kpi_list.length()-1);
	
	System.out.println(tab);
	
	if(kpi_rs.equals(kpi_selected)) {
		active = "active";
		best_selected = kpi_best;
		best_list = kpi_list;
	}
	else {
		active = "";
	}
	
	/*
	if(nrkpi==1) {
		active = "active";
		best_selected = kpi_best;
		best_list = kpi_list;
	}
	*/
	
	/*
	tablist.add(new Tables2("KPI",kpi_rs,tab,
			kpi_best,kpi_list,kpi_desc,active,
			kpi_color,"","","","",""));
	
	radarlimit.add(new Tables("RADAR", kpi_best, kpi_desc, ""+kpi_100,"","",""));
	
	obj.put("tablist", tablist);
	obj.put("radarlimit", radarlimit);
	*/
	
	radarlimit.add(new Tables("RADAR", kpi_best, kpi_desc, ""+kpi_100,"","",""));
	
	String indicator = "[";
	for(int i=0;i<radarlimit.size();i++) {
		
		indicator+="{ \"name\": \""+radarlimit.get(i).getXdtab()+"\", \"max\": "+radarlimit.get(i).getCmp1()+" },";
		
	}
	
	if(indicator.length()>0)
		indicator = indicator.substring(0,indicator.length()-1);
	
	indicator+="]";
	
	// =====================
	// RADAR
	double maxCol1 = 0;
	double maxCol2 = 0;
	double maxCol3 = 0;
	double maxCol4 = 0;
	double maxCol5 = 0;
	
	String c1="";
	String c2="";
	String c3="";
	String c4="";
	String c5="";
	
	
	String serie1 = "";
	String serie2 = "";
	String serie3 = "";
	
	/*
	sql   = "select * from "
			+ "landing_radar('"+dtda+"','"+dtal+"',"
			+ "'"+idstores.replaceAll(",","*")+"',"
			+ "'"+kpi+"') ";
	*/
	
	sql   = "select * from landing_radar_new('"+dtda+"','"+dtal+"', "
			+ "'"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', "
			+ "'"+kpi_selected+"','"+best_selected+"','"+best_list+"', '"+uid+"') ";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	while(rs.next()) {
		
		if(rs.getString("tipo").toUpperCase().equals("LIMITE")) {
			maxCol1 = rs.getDouble("col1");
			maxCol2 = rs.getDouble("col2");
			maxCol3 = rs.getDouble("col3");
			maxCol4 = rs.getDouble("col4");
			maxCol5 = rs.getDouble("col5");
		}
		if(rs.getString("tipo").toUpperCase().equals("SERIE1")) {
			c1=""+rs.getString("col1");
			c1=Utility.noNull(c1);
			c2=""+rs.getString("col2");
			c2=Utility.noNull(c2);
			c3=""+rs.getString("col3");
			c3=Utility.noNull(c3);
			c4=""+rs.getString("col4");
			c4=Utility.noNull(c4);
			c5=""+rs.getString("col5");
			c5=Utility.noNull(c5);
			
			if(c1.length()>0)
				serie1+=c1+",";
			if(c2.length()>0)
				serie1+=c2+",";
			if(c3.length()>0)
				serie1+=c3+",";
			if(c4.length()>0)
				serie1+=c4+",";
			if(c5.length()>0)
				serie1+=c5+",";
			
			if(serie1.length()>0)
				serie1=serie1.substring(0,serie1.length()-1);
			
		}
		if(rs.getString("tipo").toUpperCase().equals("SERIE2")) {
			
			c1=""+rs.getString("col1");
			c1=Utility.noNull(c1);
			c2=""+rs.getString("col2");
			c2=Utility.noNull(c2);
			c3=""+rs.getString("col3");
			c3=Utility.noNull(c3);
			c4=""+rs.getString("col4");
			c4=Utility.noNull(c4);
			c5=""+rs.getString("col5");
			c5=Utility.noNull(c5);
			
			if(c1.length()>0)
				serie2+=c1+",";
			if(c2.length()>0)
				serie2+=c2+",";
			if(c3.length()>0)
				serie2+=c3+",";
			if(c4.length()>0)
				serie2+=c4+",";
			if(c5.length()>0)
				serie2+=c5+",";
			
			if(serie2.length()>0)
				serie2=serie2.substring(0,serie2.length()-1);
			
		}
		if(rs.getString("tipo").toUpperCase().equals("SERIE3")) {
			
			c1=""+rs.getString("col1");
			c1=Utility.noNull(c1);
			c2=""+rs.getString("col2");
			c2=Utility.noNull(c2);
			c3=""+rs.getString("col3");
			c3=Utility.noNull(c3);
			c4=""+rs.getString("col4");
			c4=Utility.noNull(c4);
			c5=""+rs.getString("col5");
			c5=Utility.noNull(c5);
			
			if(c1.length()>0)
				serie3+=c1+",";
			if(c2.length()>0)
				serie3+=c2+",";
			if(c3.length()>0)
				serie3+=c3+",";
			if(c4.length()>0)
				serie3+=c4+",";
			if(c5.length()>0)
				serie3+=c5+",";
			
			if(serie3.length()>0)
				serie3=serie3.substring(0,serie3.length()-1);
			
			
		}
	}
	rs.close();
	st.close();
	
	sql   = "delete from rel_user_chart where idusr = '"+(String) session.getAttribute("username")+"' ";
	st = conn.prepareStatement(sql);
	st.executeUpdate();
	st.close();
	sql   = "insert into rel_user_chart (idusr, idchart) values ('"+(String) session.getAttribute("username")+"', 'R') ";
	st = conn.prepareStatement(sql);
	st.executeUpdate();
	st.close();
	
	output = indicator+"##@@"+serie1+"##@@"+serie2+"##@@"+serie3;
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
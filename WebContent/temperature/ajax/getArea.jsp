<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="java.util.Calendar"
import="java.util.GregorianCalendar"
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
String tipo = request.getParameter("tipo");
String uid = request.getParameter("uid");

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");

SimpleDateFormat iso2 = new SimpleDateFormat("dd/MM");

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
	sql   = "select * from landing_widget('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', 1000,  0, '"+uid+"') , "
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
	String c6="";
	String c7="";
	String c8="";
	String c9="";
	String c10="";
	String c11="";
	String c12="";
	String c13="";
	String c14="";
	String c15="";
	String c16="";
	String c17="";
	String c18="";
	String c19="";
	String c20="";
	String c21="";
	String c22="";
	String c23="";
	String c24="";
	String c25="";
	String c26="";
	String c27="";
	String c28="";
	String c29="";
	String c30="";
	String c31="";
	String c32="";
	String c33="";
	String c34="";
	String c35="";
	String c36="";
	String c37="";
	String c38="";
	String c39="";
	String c40="";
	String c41="";
	String c42="";
	String c43="";
	String c44="";
	String c45="";
	String c46="";
	String c47="";
	String c48="";
	String c49="";
	String c50="";
	String c51="";
	String c52="";
	String c53="";
	String c54="";
	String c55="";
	
	
	String serie1 = "";
	String serie2 = "";
	String serie3 = "";
	String label  = "";
	/*
	sql   = "select * from "
			+ "landing_radar('"+dtda+"','"+dtal+"',"
			+ "'"+idstores.replaceAll(",","*")+"',"
			+ "'"+kpi+"') ";
	*/
	
	sql   = "select * from landing_linechart('"+dtda+"','"+dtal+"', "
			+ "'"+idstores.replaceAll(",","*")+"','"+kpi_selected+"',"
			+ "'"+tipo+"','"+best_selected+"','"+best_list+"', '"+uid+"') ";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	while(rs.next()) {
		
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
			c6=""+rs.getString("col6");
			c6=Utility.noNull(c6);
			c7=""+rs.getString("col7");
			c7=Utility.noNull(c7);
			c8=""+rs.getString("col8");
			c8=Utility.noNull(c8);
			c9=""+rs.getString("col9");
			c9=Utility.noNull(c9);
			c10=""+rs.getString("col10");
			c10=Utility.noNull(c10);
			c11=""+rs.getString("col11");
			c11=Utility.noNull(c11);
			c12=""+rs.getString("col12");
			c12=Utility.noNull(c12);
			c13=""+rs.getString("col13");
			c13=Utility.noNull(c13);
			c14=""+rs.getString("col14");
			c14=Utility.noNull(c14);
			c15=""+rs.getString("col15");
			c15=Utility.noNull(c15);
			c16=""+rs.getString("col16");
			c16=Utility.noNull(c16);
			c17=""+rs.getString("col17");
			c17=Utility.noNull(c17);
			c18=""+rs.getString("col18");
			c18=Utility.noNull(c18);
			c19=""+rs.getString("col19");
			c19=Utility.noNull(c19);
			c20=""+rs.getString("col20");
			c20=Utility.noNull(c20);
			c21=""+rs.getString("col21");
			c21=Utility.noNull(c21);
			c22=""+rs.getString("col22");
			c22=Utility.noNull(c22);
			c23=""+rs.getString("col23");
			c23=Utility.noNull(c23);
			c24=""+rs.getString("col24");
			c24=Utility.noNull(c24);
			c25=""+rs.getString("col25");
			c25=Utility.noNull(c25);
			c26=""+rs.getString("col26");
			c26=Utility.noNull(c26);
			c27=""+rs.getString("col27");
			c27=Utility.noNull(c27);
			c28=""+rs.getString("col28");
			c28=Utility.noNull(c28);
			c29=""+rs.getString("col29");
			c29=Utility.noNull(c29);
			c30=""+rs.getString("col30");
			c30=Utility.noNull(c30);
			c31=""+rs.getString("col31");
			c31=Utility.noNull(c31);
			c32=""+rs.getString("col32");
			c32=Utility.noNull(c32);
			c33=""+rs.getString("col33");
			c33=Utility.noNull(c33);
			c34=""+rs.getString("col34");
			c34=Utility.noNull(c34);
			c35=""+rs.getString("col35");
			c35=Utility.noNull(c35);
			c36=""+rs.getString("col36");
			c36=Utility.noNull(c36);
			c37=""+rs.getString("col37");
			c37=Utility.noNull(c37);
			c38=""+rs.getString("col38");
			c38=Utility.noNull(c38);
			c39=""+rs.getString("col39");
			c39=Utility.noNull(c39);
			c40=""+rs.getString("col40");
			c40=Utility.noNull(c40);
			c41=""+rs.getString("col41");
			c41=Utility.noNull(c41);
			c42=""+rs.getString("col42");
			c42=Utility.noNull(c42);
			c43=""+rs.getString("col43");
			c43=Utility.noNull(c43);
			c44=""+rs.getString("col44");
			c44=Utility.noNull(c44);
			c45=""+rs.getString("col45");
			c45=Utility.noNull(c45);
			c46=""+rs.getString("col46");
			c46=Utility.noNull(c46);
			c47=""+rs.getString("col47");
			c47=Utility.noNull(c47);
			c48=""+rs.getString("col48");
			c48=Utility.noNull(c48);
			c49=""+rs.getString("col49");
			c49=Utility.noNull(c49);
			c50=""+rs.getString("col50");
			c50=Utility.noNull(c50);
			c51=""+rs.getString("col51");
			c51=Utility.noNull(c51);
			c52=""+rs.getString("col52");
			c52=Utility.noNull(c52);
			c53=""+rs.getString("col53");
			c53=Utility.noNull(c53);
			c54=""+rs.getString("col54");
			c54=Utility.noNull(c54);
			c55=""+rs.getString("col55");
			c55=Utility.noNull(c55);
			
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
			if(c6.length()>0)
				serie1+=c6+",";
			if(c7.length()>0)
				serie1+=c7+",";
			if(c8.length()>0)
				serie1+=c8+",";
			if(c9.length()>0)
				serie1+=c9+",";
			if(c10.length()>0)
				serie1+=c10+",";
			if(c11.length()>0)
				serie1+=c11+",";
			if(c12.length()>0)
				serie1+=c12+",";
			if(c13.length()>0)
				serie1+=c13+",";
			if(c14.length()>0)
				serie1+=c14+",";
			if(c15.length()>0)
				serie1+=c15+",";
			if(c16.length()>0)
				serie1+=c16+",";
			if(c17.length()>0)
				serie1+=c17+",";
			if(c18.length()>0)
				serie1+=c18+",";
			if(c19.length()>0)
				serie1+=c19+",";
			if(c20.length()>0)
				serie1+=c20+",";
			if(c21.length()>0)
				serie1+=c21+",";
			if(c22.length()>0)
				serie1+=c22+",";
			if(c23.length()>0)
				serie1+=c23+",";
			if(c24.length()>0)
				serie1+=c24+",";
			if(c25.length()>0)
				serie1+=c25+",";
			if(c26.length()>0)
				serie1+=c26+",";
			if(c27.length()>0)
				serie1+=c27+",";
			if(c28.length()>0)
				serie1+=c28+",";
			if(c29.length()>0)
				serie1+=c29+",";
			if(c30.length()>0)
				serie1+=c30+",";
			if(c31.length()>0)
				serie1+=c31+",";
			if(c32.length()>0)
				serie1+=c32+",";
			if(c33.length()>0)
				serie1+=c33+",";
			if(c34.length()>0)
				serie1+=c34+",";
			if(c35.length()>0)
				serie1+=c35+",";
			if(c36.length()>0)
				serie1+=c36+",";
			if(c37.length()>0)
				serie1+=c37+",";
			if(c38.length()>0)
				serie1+=c38+",";
			if(c39.length()>0)
				serie1+=c39+",";
			if(c40.length()>0)
				serie1+=c40+",";
			if(c41.length()>0)
				serie1+=c41+",";
			if(c42.length()>0)
				serie1+=c42+",";
			if(c43.length()>0)
				serie1+=c43+",";
			if(c44.length()>0)
				serie1+=c44+",";
			if(c45.length()>0)
				serie1+=c45+",";
			if(c46.length()>0)
				serie1+=c46+",";
			if(c47.length()>0)
				serie1+=c47+",";
			if(c48.length()>0)
				serie1+=c48+",";
			if(c49.length()>0)
				serie1+=c49+",";
			if(c50.length()>0)
				serie1+=c50+",";
			if(c51.length()>0)
				serie1+=c51+",";
			if(c52.length()>0)
				serie1+=c52+",";
			if(c53.length()>0)
				serie1+=c53+",";
			if(c54.length()>0)
				serie1+=c54+",";
			if(c55.length()>0)
				serie1+=c55+",";
			
			
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
			c6=""+rs.getString("col6");
			c6=Utility.noNull(c6);
			c7=""+rs.getString("col7");
			c7=Utility.noNull(c7);
			c8=""+rs.getString("col8");
			c8=Utility.noNull(c8);
			c9=""+rs.getString("col9");
			c9=Utility.noNull(c9);
			c10=""+rs.getString("col10");
			c10=Utility.noNull(c10);
			c11=""+rs.getString("col11");
			c11=Utility.noNull(c11);
			c12=""+rs.getString("col12");
			c12=Utility.noNull(c12);
			c13=""+rs.getString("col13");
			c13=Utility.noNull(c13);
			c14=""+rs.getString("col14");
			c14=Utility.noNull(c14);
			c15=""+rs.getString("col15");
			c15=Utility.noNull(c15);
			c16=""+rs.getString("col16");
			c16=Utility.noNull(c16);
			c17=""+rs.getString("col17");
			c17=Utility.noNull(c17);
			c18=""+rs.getString("col18");
			c18=Utility.noNull(c18);
			c19=""+rs.getString("col19");
			c19=Utility.noNull(c19);
			c20=""+rs.getString("col20");
			c20=Utility.noNull(c20);
			c21=""+rs.getString("col21");
			c21=Utility.noNull(c21);
			c22=""+rs.getString("col22");
			c22=Utility.noNull(c22);
			c23=""+rs.getString("col23");
			c23=Utility.noNull(c23);
			c24=""+rs.getString("col24");
			c24=Utility.noNull(c24);
			c25=""+rs.getString("col25");
			c25=Utility.noNull(c25);
			c26=""+rs.getString("col26");
			c26=Utility.noNull(c26);
			c27=""+rs.getString("col27");
			c27=Utility.noNull(c27);
			c28=""+rs.getString("col28");
			c28=Utility.noNull(c28);
			c29=""+rs.getString("col29");
			c29=Utility.noNull(c29);
			c30=""+rs.getString("col30");
			c30=Utility.noNull(c30);
			c31=""+rs.getString("col31");
			c31=Utility.noNull(c31);
			c32=""+rs.getString("col32");
			c32=Utility.noNull(c32);
			c33=""+rs.getString("col33");
			c33=Utility.noNull(c33);
			c34=""+rs.getString("col34");
			c34=Utility.noNull(c34);
			c35=""+rs.getString("col35");
			c35=Utility.noNull(c35);
			c36=""+rs.getString("col36");
			c36=Utility.noNull(c36);
			c37=""+rs.getString("col37");
			c37=Utility.noNull(c37);
			c38=""+rs.getString("col38");
			c38=Utility.noNull(c38);
			c39=""+rs.getString("col39");
			c39=Utility.noNull(c39);
			c40=""+rs.getString("col40");
			c40=Utility.noNull(c40);
			c41=""+rs.getString("col41");
			c41=Utility.noNull(c41);
			c42=""+rs.getString("col42");
			c42=Utility.noNull(c42);
			c43=""+rs.getString("col43");
			c43=Utility.noNull(c43);
			c44=""+rs.getString("col44");
			c44=Utility.noNull(c44);
			c45=""+rs.getString("col45");
			c45=Utility.noNull(c45);
			c46=""+rs.getString("col46");
			c46=Utility.noNull(c46);
			c47=""+rs.getString("col47");
			c47=Utility.noNull(c47);
			c48=""+rs.getString("col48");
			c48=Utility.noNull(c48);
			c49=""+rs.getString("col49");
			c49=Utility.noNull(c49);
			c50=""+rs.getString("col50");
			c50=Utility.noNull(c50);
			c51=""+rs.getString("col51");
			c51=Utility.noNull(c51);
			c52=""+rs.getString("col52");
			c52=Utility.noNull(c52);
			c53=""+rs.getString("col53");
			c53=Utility.noNull(c53);
			c54=""+rs.getString("col54");
			c54=Utility.noNull(c54);
			c55=""+rs.getString("col55");
			c55=Utility.noNull(c55);
			
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
			if(c6.length()>0)
				serie2+=c6+",";
			if(c7.length()>0)
				serie2+=c7+",";
			if(c8.length()>0)
				serie2+=c8+",";
			if(c9.length()>0)
				serie2+=c9+",";
			if(c10.length()>0)
				serie2+=c10+",";
			if(c11.length()>0)
				serie2+=c11+",";
			if(c12.length()>0)
				serie2+=c12+",";
			if(c13.length()>0)
				serie2+=c13+",";
			if(c14.length()>0)
				serie2+=c14+",";
			if(c15.length()>0)
				serie2+=c15+",";
			if(c16.length()>0)
				serie2+=c16+",";
			if(c17.length()>0)
				serie2+=c17+",";
			if(c18.length()>0)
				serie2+=c18+",";
			if(c19.length()>0)
				serie2+=c19+",";
			if(c20.length()>0)
				serie2+=c20+",";
			if(c21.length()>0)
				serie2+=c21+",";
			if(c22.length()>0)
				serie2+=c22+",";
			if(c23.length()>0)
				serie2+=c23+",";
			if(c24.length()>0)
				serie2+=c24+",";
			if(c25.length()>0)
				serie2+=c25+",";
			if(c26.length()>0)
				serie2+=c26+",";
			if(c27.length()>0)
				serie2+=c27+",";
			if(c28.length()>0)
				serie2+=c28+",";
			if(c29.length()>0)
				serie2+=c29+",";
			if(c30.length()>0)
				serie2+=c30+",";
			if(c31.length()>0)
				serie2+=c31+",";
			if(c32.length()>0)
				serie2+=c32+",";
			if(c33.length()>0)
				serie2+=c33+",";
			if(c34.length()>0)
				serie2+=c34+",";
			if(c35.length()>0)
				serie2+=c35+",";
			if(c36.length()>0)
				serie2+=c36+",";
			if(c37.length()>0)
				serie2+=c37+",";
			if(c38.length()>0)
				serie2+=c38+",";
			if(c39.length()>0)
				serie2+=c39+",";
			if(c40.length()>0)
				serie2+=c40+",";
			if(c41.length()>0)
				serie2+=c41+",";
			if(c42.length()>0)
				serie2+=c42+",";
			if(c43.length()>0)
				serie2+=c43+",";
			if(c44.length()>0)
				serie2+=c44+",";
			if(c45.length()>0)
				serie2+=c45+",";
			if(c46.length()>0)
				serie2+=c46+",";
			if(c47.length()>0)
				serie2+=c47+",";
			if(c48.length()>0)
				serie2+=c48+",";
			if(c49.length()>0)
				serie2+=c49+",";
			if(c50.length()>0)
				serie2+=c50+",";
			if(c51.length()>0)
				serie2+=c51+",";
			if(c52.length()>0)
				serie2+=c52+",";
			if(c53.length()>0)
				serie2+=c53+",";
			if(c54.length()>0)
				serie2+=c54+",";
			if(c55.length()>0)
				serie2+=c55+",";
			
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
			c6=""+rs.getString("col6");
			c6=Utility.noNull(c6);
			c7=""+rs.getString("col7");
			c7=Utility.noNull(c7);
			c8=""+rs.getString("col8");
			c8=Utility.noNull(c8);
			c9=""+rs.getString("col9");
			c9=Utility.noNull(c9);
			c10=""+rs.getString("col10");
			c10=Utility.noNull(c10);
			c11=""+rs.getString("col11");
			c11=Utility.noNull(c11);
			c12=""+rs.getString("col12");
			c12=Utility.noNull(c12);
			c13=""+rs.getString("col13");
			c13=Utility.noNull(c13);
			c14=""+rs.getString("col14");
			c14=Utility.noNull(c14);
			c15=""+rs.getString("col15");
			c15=Utility.noNull(c15);
			c16=""+rs.getString("col16");
			c16=Utility.noNull(c16);
			c17=""+rs.getString("col17");
			c17=Utility.noNull(c17);
			c18=""+rs.getString("col18");
			c18=Utility.noNull(c18);
			c19=""+rs.getString("col19");
			c19=Utility.noNull(c19);
			c20=""+rs.getString("col20");
			c20=Utility.noNull(c20);
			c21=""+rs.getString("col21");
			c21=Utility.noNull(c21);
			c22=""+rs.getString("col22");
			c22=Utility.noNull(c22);
			c23=""+rs.getString("col23");
			c23=Utility.noNull(c23);
			c24=""+rs.getString("col24");
			c24=Utility.noNull(c24);
			c25=""+rs.getString("col25");
			c25=Utility.noNull(c25);
			c26=""+rs.getString("col26");
			c26=Utility.noNull(c26);
			c27=""+rs.getString("col27");
			c27=Utility.noNull(c27);
			c28=""+rs.getString("col28");
			c28=Utility.noNull(c28);
			c29=""+rs.getString("col29");
			c29=Utility.noNull(c29);
			c30=""+rs.getString("col30");
			c30=Utility.noNull(c30);
			c31=""+rs.getString("col31");
			c31=Utility.noNull(c31);
			c32=""+rs.getString("col32");
			c32=Utility.noNull(c32);
			c33=""+rs.getString("col33");
			c33=Utility.noNull(c33);
			c34=""+rs.getString("col34");
			c34=Utility.noNull(c34);
			c35=""+rs.getString("col35");
			c35=Utility.noNull(c35);
			c36=""+rs.getString("col36");
			c36=Utility.noNull(c36);
			c37=""+rs.getString("col37");
			c37=Utility.noNull(c37);
			c38=""+rs.getString("col38");
			c38=Utility.noNull(c38);
			c39=""+rs.getString("col39");
			c39=Utility.noNull(c39);
			c40=""+rs.getString("col40");
			c40=Utility.noNull(c40);
			c41=""+rs.getString("col41");
			c41=Utility.noNull(c41);
			c42=""+rs.getString("col42");
			c42=Utility.noNull(c42);
			c43=""+rs.getString("col43");
			c43=Utility.noNull(c43);
			c44=""+rs.getString("col44");
			c44=Utility.noNull(c44);
			c45=""+rs.getString("col45");
			c45=Utility.noNull(c45);
			c46=""+rs.getString("col46");
			c46=Utility.noNull(c46);
			c47=""+rs.getString("col47");
			c47=Utility.noNull(c47);
			c48=""+rs.getString("col48");
			c48=Utility.noNull(c48);
			c49=""+rs.getString("col49");
			c49=Utility.noNull(c49);
			c50=""+rs.getString("col50");
			c50=Utility.noNull(c50);
			c51=""+rs.getString("col51");
			c51=Utility.noNull(c51);
			c52=""+rs.getString("col52");
			c52=Utility.noNull(c52);
			c53=""+rs.getString("col53");
			c53=Utility.noNull(c53);
			c54=""+rs.getString("col54");
			c54=Utility.noNull(c54);
			c55=""+rs.getString("col55");
			c55=Utility.noNull(c55);
			
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
			if(c6.length()>0)
				serie3+=c6+",";
			if(c7.length()>0)
				serie3+=c7+",";
			if(c8.length()>0)
				serie3+=c8+",";
			if(c9.length()>0)
				serie3+=c9+",";
			if(c10.length()>0)
				serie3+=c10+",";
			if(c11.length()>0)
				serie3+=c11+",";
			if(c12.length()>0)
				serie3+=c12+",";
			if(c13.length()>0)
				serie3+=c13+",";
			if(c14.length()>0)
				serie3+=c14+",";
			if(c15.length()>0)
				serie3+=c15+",";
			if(c16.length()>0)
				serie3+=c16+",";
			if(c17.length()>0)
				serie3+=c17+",";
			if(c18.length()>0)
				serie3+=c18+",";
			if(c19.length()>0)
				serie3+=c19+",";
			if(c20.length()>0)
				serie3+=c20+",";
			if(c21.length()>0)
				serie3+=c21+",";
			if(c22.length()>0)
				serie3+=c22+",";
			if(c23.length()>0)
				serie3+=c23+",";
			if(c24.length()>0)
				serie3+=c24+",";
			if(c25.length()>0)
				serie3+=c25+",";
			if(c26.length()>0)
				serie3+=c26+",";
			if(c27.length()>0)
				serie3+=c27+",";
			if(c28.length()>0)
				serie3+=c28+",";
			if(c29.length()>0)
				serie3+=c29+",";
			if(c30.length()>0)
				serie3+=c30+",";
			if(c31.length()>0)
				serie3+=c31+",";
			if(c32.length()>0)
				serie3+=c32+",";
			if(c33.length()>0)
				serie3+=c33+",";
			if(c34.length()>0)
				serie3+=c34+",";
			if(c35.length()>0)
				serie3+=c35+",";
			if(c36.length()>0)
				serie3+=c36+",";
			if(c37.length()>0)
				serie3+=c37+",";
			if(c38.length()>0)
				serie3+=c38+",";
			if(c39.length()>0)
				serie3+=c39+",";
			if(c40.length()>0)
				serie3+=c40+",";
			if(c41.length()>0)
				serie3+=c41+",";
			if(c42.length()>0)
				serie3+=c42+",";
			if(c43.length()>0)
				serie3+=c43+",";
			if(c44.length()>0)
				serie3+=c44+",";
			if(c45.length()>0)
				serie3+=c45+",";
			if(c46.length()>0)
				serie3+=c46+",";
			if(c47.length()>0)
				serie3+=c47+",";
			if(c48.length()>0)
				serie3+=c48+",";
			if(c49.length()>0)
				serie3+=c49+",";
			if(c50.length()>0)
				serie3+=c50+",";
			if(c51.length()>0)
				serie3+=c51+",";
			if(c52.length()>0)
				serie3+=c52+",";
			if(c53.length()>0)
				serie3+=c53+",";
			if(c54.length()>0)
				serie3+=c54+",";
			if(c55.length()>0)
				serie3+=c55+",";
			
			
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
	sql   = "insert into rel_user_chart (idusr, idchart) values ('"+(String) session.getAttribute("username")+"', 'A') ";
	st = conn.prepareStatement(sql);
	st.executeUpdate();
	st.close();
	
	st.close();
	
	String temp[] = serie1.split(",");
	String tp = "";
	if(tipo.equals("Y")) {
		tp = "M";
	}
	else if(tipo.equals("Q")) {
		tp = "M";
	}
	else if(tipo.equals("M")) {
		tp = "D";
	}
	else if(tipo.equals("W")) {
		tp = "D";	
	}
	else if(tipo.equals("D")) {
		tp = "H";
	}
	
	
	
	
	ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
	
	String times="";
    
	// Intestazione
	switch (tipo) {
 	case "Y": 
 		
 		
 		Calendar startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		Calendar endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			times+=""+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+",";
 			
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
 		
 		
 		break;
 		
 	case "YW": 
 		
 		/*
 		String settimane = getWeeks(conn, idcus, dtal.substring(0, 4));
 		String temp222[] = settimane.split(",");
 		int ini = Integer.parseInt(temp222[0]);
 		int fin = Integer.parseInt(temp222[1]);
 		*/
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));
 		
 		long diffWeeks = 0;
 		for( long t=1; ; t++ ) {         
 			startCalendar.add( Calendar.WEEK_OF_YEAR, 1 );   // add one day, week, year, etc.
 			if( startCalendar.after(endCalendar) ) {
 	            diffWeeks = t-1;
 	            break;
 			}
 	    }
 		
 			         		
 		for(int i=0;i<=diffWeeks;i++) {
 			
 			times+="W"+i+",";
 			
 		}
 		
 		break;
 		
 	case "Q":
 		
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));

 		diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
 		diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
 		
 		for(int i=0;i<=diffMonth;i++) {
 			
 			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			
 			times+=""+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+",";
 			startCalendar.add(Calendar.MONTH, 1);
 			
 		}
 		
 		break;
 		
 	case "M":
	
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));
 		
 		Date startDate = startCalendar.getTime();
 		Date endDate = endCalendar.getTime();
 		long startTime = startDate.getTime();
 		long endTime = endDate.getTime();
 		long diffTime = endTime - startTime;
 		long diffDays = diffTime / (1000 * 60 * 60 * 24);
 		DateFormat dateFormat = DateFormat.getDateInstance();
 		System.out.println("The difference between "+
 		  dateFormat.format(startDate)+" and "+
 		  dateFormat.format(endDate)+" is "+
 		  diffDays+" days.");
 		
 		for(int i=0;i<=diffDays;i++) {
 			
 			System.out.println(iso.format(startCalendar.getTime()));
 			
 			
 			times+=""+iso2.format(startCalendar.getTime())+",";
 			startCalendar.add(Calendar.DATE, 1);
 			
 		}
 		
 		break;
 		
 	case "W":
 		
 		startCalendar = new GregorianCalendar();
 		startCalendar.setTime(iso.parse(dtda));
 		endCalendar = new GregorianCalendar();
 		endCalendar.setTime(iso.parse(dtal));
 		
 		startDate = startCalendar.getTime();
 		endDate = endCalendar.getTime();
 		startTime = startDate.getTime();
 		endTime = endDate.getTime();
 		diffTime = endTime - startTime;
 		diffDays = diffTime / (1000 * 60 * 60 * 24);
 		dateFormat = DateFormat.getDateInstance();
 		System.out.println("The difference between "+
 		  dateFormat.format(startDate)+" and "+
 		  dateFormat.format(endDate)+" is "+
 		  diffDays+" days.");
 		
 		for(int i=0;i<=diffDays;i++) {
 			
 			System.out.println(iso2.format(startCalendar.getTime()));
 			
 			
 			times+=""+iso2.format(startCalendar.getTime())+",";
 			
 			startCalendar.add(Calendar.DATE, 1);
 			
 		}
 		
 		break;
 		
 	case "D":
 		
 		for(int i=0;i<24;i++) {
 			String hh = "00"+i;
 			hh=hh.substring(hh.length()-2,hh.length());
 		}
 		
 		times+="00-01,01-02,02-03,03-04,04-05,05-06,06-07,07-08,08-09,09-10,10-11,11-12,12-13,13-14,14-15,15-16,16-17,17-18,18-19,19-20,20-21,21-22,22-23,23-24,";
 		break;
	}
	
		
	
	/*
	for(int i=0;i<temp.length;i++) {
		label+=""+tp+""+(i+1)+",";
	}
	*/
	label = times;
	
	
	
	
	
	
	
	if(label.length()>0)
		label = label.substring(0,label.length()-1);
	
	output = indicator+"##@@"+serie1+"##@@"+serie2+"##@@"+serie3+"##@@"+label;
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
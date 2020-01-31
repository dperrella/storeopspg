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
import="pkg.beans.rfid.Step"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
List<Tables> tablist = new ArrayList<Tables>();
tablist.clear();

Connection conn = null;
PreparedStatement st 	= null;
ResultSet rs 	= null;
PreparedStatement st2 	= null;
ResultSet rs2 	= null;
String sql 		= "";
String idsites = request.getParameter("idsites");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String step = request.getParameter("step");
String tipoPeriod = request.getParameter("tipoPeriod");

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");

//Formattazione data
Date d1 = sdf.parse(dtda);
dtda = iso.format(d1);
Date d2 = sdf.parse(dtal);
dtal = iso.format(d2);

String output = "";

try {
	conn=Utility.getConnRfid();
	
	System.out.println(step);
	
	//Step.selectIdstepByGroupedStep2(conn, Integer.parseInt(step));
	// *******
	// tablist...
	//String tipo = "DIFF";
	String tipo = "";
	
	// naturali...
	sql   = "select a.*, idstep, color, '' as tipo, 0.0 as tot from public.report_total('"+dtda+"','"+dtal+"','"+idsites.replaceAll(",","*")+"','"+step.replaceAll(",","*")+"', "
			+"'"+tipoPeriod+"') a , step where step::numeric = idstep order by idstep ";
	
	
	System.out.println("***** "+sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	String val = "";
	while(rs.next()) {
		
		if(rs.getString("tipo").trim().equalsIgnoreCase("")) {
			for(int i=0;i<55;i++) {
				if(rs.getString((i+2))!=null) {
					val+=Utility.round(rs.getDouble((i+2)) , 2)+",";
				}
			}
			if(val.length()>0)
				val=val.substring(0,val.length()-1);
			
		}
		
		
		//if(rs.getString("tipo").trim().equalsIgnoreCase("DIFF")) {
			String icon = "";
			String color = rs.getString("color");
			
			if(Utility.round(rs.getDouble("tot") , 2)>0)
				icon = "<i style=\"font-size:25px\" class=\"fa fa-caret-up text-success\"></i>";
			else
				icon = "<i style=\"font-size:25px\" class=\"fa fa-caret-down text-danger\"></i>";
				
			tablist.add(new Tables("STEP",rs.getString("idstep"),val,
					""+Utility.round(rs.getDouble("tot") , 2),icon,color,""));	
			
			val="";
		//}
		
	}
	rs.close();
	
	
	// grouped
	sql   = "select * from f_step "+
			"where idstep in("+step+") and type = 'G'";
	st2 = conn.prepareStatement(sql);
	rs2 = st2.executeQuery();
	while(rs2.next()) {
		
		String step_selected = Step.selectIdstepByGroupedStep2(conn, Integer.parseInt(rs2.getString("idstep")));
		
		sql   = "select "+rs2.getString("idstep")+" step, "+ 
				"sum(col1) col1, sum(col2) col2, sum(col3) col3, sum(col4) col4, sum(col5) col5, "+
				"sum(col6) col6, sum(col7) col7, sum(col8) col8, sum(col9) col9, sum(col10) col10, "+ 
				"sum(col11) col11, sum(col12) col12, sum(col13) col13, sum(col14) col14, sum(col15) col15, "+
				"sum(col16) col16, sum(col17) col17, sum(col18) col18, sum(col19) col19, sum(col20) col20, "+
				"sum(col21) col21, sum(col22) col22, sum(col23) col23, sum(col24) col24, sum(col25) col25, "+
				"sum(col26) col26, sum(col27) col27, sum(col28) col28, sum(col29) col29, sum(col30) col30, "+
				"sum(col31) col31, sum(col32) col32, sum(col33) col33, sum(col34) col34, sum(col35) col35, "+
				"sum(col36) col36, sum(col37) col37, sum(col38) col38, sum(col39) col39, sum(col40) col40, "+
				"sum(col41) col41, sum(col42) col42, sum(col43) col43, sum(col44) col44, sum(col45) col45, "+
				"sum(col46) col46, sum(col47) col47, sum(col48) col48, sum(col49) col49, sum(col50) col50, "+
				"sum(col51) col51, sum(col52) col52, sum(col53) col53, sum(col54) col54, sum(col55) col55, "+
				rs2.getString("idstep")+" idstep , max(color) color, '' as tipo, 0.0 as tot "+
				"from public.report_total('"+dtda+"','"+dtal+"','"+idsites.replaceAll(",","*")+"','"+step_selected.replaceAll(",","*")+"', "+
				"'"+tipoPeriod+"') a , step where step::numeric = idstep ";
		
		System.out.println("***** "+sql);
		st = conn.prepareStatement(sql);
		rs = st.executeQuery();
		val = "";
		while(rs.next()) {
			
			if(rs.getString("tipo").trim().equalsIgnoreCase("")) {
				for(int i=0;i<55;i++) {
					if(rs.getString((i+2))!=null) {
						val+=Utility.round(rs.getDouble((i+2)) , 2)+",";
					}
				}
				if(val.length()>0)
					val=val.substring(0,val.length()-1);
				
			}
			
			
			//if(rs.getString("tipo").trim().equalsIgnoreCase("DIFF")) {
				String icon = "";
				String color = rs.getString("color");
				
				if(Utility.round(rs.getDouble("tot") , 2)>0)
					icon = "<i style=\"font-size:25px\" class=\"fa fa-caret-up text-success\"></i>";
				else
					icon = "<i style=\"font-size:25px\" class=\"fa fa-caret-down text-danger\"></i>";
					
				tablist.add(new Tables("STEP",rs.getString("idstep"),val,
						""+Utility.round(rs.getDouble("tot") , 2),icon,color,""));	
				
				val="";
			//}
			
		}
		rs.close();
		
		
	}
	rs2.close();
	st2.close();
	
	st.close();
	
	for(int i=0;i<tablist.size();i++) {
		output+=tablist.get(i).getXcdel()+"##@@"+tablist.get(i).getXdtab()+"##@@"+tablist.get(i).getCmp1()+"##@@"+tablist.get(i).getCmp2()+"##@@"+tablist.get(i).getCmp3()+";";
	}
	
	if(output.length()>0)
		output=output.substring(0,output.length()-1);
	
	
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
import="java.sql.*"
import="pkg.beans.Tables"
import="java.util.List"
import="java.util.ArrayList"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%

List<Tables> chart = new ArrayList<Tables>();
chart.clear();

String idstores = request.getParameter("idstores");
String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String kpi = request.getParameter("kpi");
String tipo = request.getParameter("tipoPeriod");
String sum_avg = request.getParameter("sum_avg");

String like4like = ""+request.getParameter("like4like");
like4like=Utility.noNull(like4like);

String group = request.getParameter("group");
String comparison = request.getParameter("comparison");

boolean l4l = false;
if(like4like.length()>0)
	l4l = true;

int rowspan = Integer.parseInt(request.getParameter("rowspan"));

System.out.println(idstores);
System.out.println(dtda);
System.out.println(dtal);
System.out.println(kpi);
System.out.println(tipo);
System.out.println(sum_avg);
System.out.println(like4like);
System.out.println(group);
System.out.println(comparison);


Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
String sql = "";

String table = "";

try {

	conn = Utility.getConnData();
	
	// Formato data
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
	
	//Formattazione data
	Date d1 = sdf.parse(dtda);
	dtda = iso.format(d1);
	Date d2 = sdf.parse(dtal);
	dtal = iso.format(d2);
	
	sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol "
			+ "from public.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
			+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"') a, "
			+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' "
			+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
			
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
   	int count = 0;
    int count2= 0;
    int num = 0;
      
    String border = "";
      
    String tot = "";
    
    String val = "";
    String val2= "";
	String op = "";
      
	while(rs.next()) {
		
		
		if(count2<2) {
			
			for(int i=0;i<31;i++) {
				if(rs.getString((i+6))!=null) {
					
					if(count2==0) {
						val+=Utility.round(rs.getDouble((i+6)) , 2)+",";
					}
					if(count2==1) {
						val2+=Utility.round(rs.getDouble((i+6)) , 2)+",";
					}
					
				}
			}
			
			if(val.length()>0)
				val=val.substring(0,val.length()-1);
			if(val2.length()>0)
				val2=val2.substring(0,val2.length()-1);
			
			if(rs.getString("op").equalsIgnoreCase(sum_avg)) {
				
				if(count2==0)
					chart.add(new Tables("VAL", rs.getString("codkpi"), val, rs.getString("color"),rs.getString("codkpi"),"",rs.getString("op")));
				
				if(count2==1)
					chart.add(new Tables("VAL", rs.getString("codkpi"), val2, rs.getString("color"),rs.getString("codkpi"),"",rs.getString("op")));
			}
			val = "";
			val2= "";
			
		}
		
		count+=1;
       	if(count==(rowspan*3)) {
       		count = 0;
       	}
       	
       	count2+=1;
       	if(count2==3) {
       		count2 = 0;
       	}
		
       	num+=1;
    }
	rs.close();
	st.close();
		
	num=0;
	
	String v1="";
	String v2="";
	
	for(int i=0;i<chart.size();i++) {
		num+=1;
		
		if(!Utility.ePari(num)) {
			v1=chart.get(i).getXdtab();
		}
		else {
			v2=chart.get(i).getXdtab();
			System.out.println(chart.get(i).getCmp4()+" "+chart.get(i).getXcdel()+" "+v1+" "+v2);
			
			table+=v1+"##@@"+chart.get(i).getCmp1()+"##@@"+chart.get(i).getCmp2()+"##@@"+v2+";";
		}
			
		
	}
	
	if(table.length()>0)
		table=table.substring(0,table.length()-1);
	
}
catch(Exception e) {
	e.printStackTrace();
}
finally {
	try {
		if(conn!=null)
			conn.close();
	}
	catch(Exception e) {}
}
%>
<%=table%>
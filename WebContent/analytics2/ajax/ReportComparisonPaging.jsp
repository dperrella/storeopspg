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
import="pkg.beans.Tables2"
import="org.json.simple.JSONArray"
import="org.json.simple.JSONObject"
import="org.json.simple.parser.JSONParser"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
List<Tables2> sto = new ArrayList<Tables2>();
sto.clear();

Connection conn = null;
PreparedStatement st 	= null;
ResultSet rs 	= null;
String sql 		= "";

String dtda = request.getParameter("dtda");
String dtal = request.getParameter("dtal");
String idstores = request.getParameter("idstores");
String kpi = request.getParameter("kpi");
String tipo = request.getParameter("tipoPeriod");
String like4like = ""+request.getParameter("like4like");
like4like = Utility.noNull(like4like);

boolean l4l = false;
if(like4like.length()>0)
	l4l = true;

String group = request.getParameter("group");
String comparison = request.getParameter("comparison");
String pag = request.getParameter("pag");
int numRow = Integer.parseInt(request.getParameter("numRow"));
String nrRecord = request.getParameter("nrRecord");

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
	String temp[] = kpi.split(",");
	int rowspan = 0;
	for(int i=0;i<temp.length;i++) {
		rowspan+=1;
	}
	
	int nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
	int max = 0;
	int rowIni = 0;
	int rowFin = 0;
	
	if(nrRecord.length()>0) {
		
		for (int i=1; i<=nrPag; i++) {
			
			max+=Integer.parseInt(nrRecord);
			
			if(i==Integer.parseInt(pag)) {
				
				rowIni = (max-Integer.parseInt(nrRecord));
				rowFin = Integer.parseInt(nrRecord);
				
			}
			
		}
	
	}
	
	
	sql   = "select * from test.report_grid('"+dtda+"','"+dtal+"',"
				+ "'"+idstores.replaceAll(",","*")+"',"
				+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"', '"+session.getAttribute("uid")+"') "
				+ "order by category, codkpi, "
         		+ "case when tipo = '' then 1 "
   				+ "when tipo = 'PREV' then 2 "
   				+ "when tipo = 'DIFF' then 3 end "
				+ "limit "+rowFin+" offset "+rowIni+" ";
	System.out.println(sql);
	st = conn.prepareStatement(sql);
	rs = st.executeQuery();
	
	// Var per grafico
    String stores = "";
    
	int count = 0;
    int num = 0;
    int count2 = 0;
    
    String td = "";
    
    String tot = "";
    
	while(rs.next()) {
		
		if(count==0) {
    		
    		if(num>0) {
    			
    			//System.out.print(rs.getString("category")+" ");
    			// Inserimento in array della riga
        		sto.add(new Tables2("STO",""+num,td,
        				"","","","","","","","","",""));
    		}
    		td="";
    		
    		if(count2==0) {
	        	
	    		if(group.equals("1")||group.equals("2")||group.equals("3")) {
	    			
	    			if(group.equals("1")) {
	    				td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan*3+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','1')\"><b>"+Utility.getCustomerDescription(conn, rs.getString("category"))+"</b></a></td>";
	        		}
	    			else if(group.equals("2")) {
	    				td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan*3+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','2')\"><b>"+Utility.getNationDescription(conn, rs.getString("category"))+"</b></a></td>";
	        		}
	    			else if(group.equals("3")) {
	    				td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan*3+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','3')\"><b>"+Utility.getRegionDescription(conn, rs.getString("category"))+"</b></a></td>";		
	    			}
	    		}
	    		else {
	        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan*3+"\"><b>"+rs.getString("category")+"</b></td>";
	    		}
	    		
    		}
    		
		}
		if(count>=1&&count<=(rowspan*3))
    		td+="<tr>";
		
    	if(count2==0) {
    		tot="";
			td+="<td class=\"b-r\" style=\"width:120px\" rowspan=\"3\">"+rs.getString("desckpi")+"</td>";
    	}
    	
    	td+="<td class=\"b-r\" style=\"width:120px;\">"
				+ rs.getString("tipo")+"</td>";
		
				
		for(int i=0;i<55;i++) {
			if(rs.getString((i+6))!=null) {
				//System.out.print(rs.getString((i+6))+" ");
				if(count2==2) {
					tot+=Utility.round(rs.getDouble((i+6)) , 2)+",";
					
					if(rs.getDouble((i+6))<0)
        					td+="<td class=\"b-r text-right\"><i class=\"fa fa-caret-down\" style=\"color:#3366CC\"></i></td>";
        				else if(rs.getDouble((i+6))>0)
        					td+="<td class=\"b-r text-right\"><i class=\"fa fa-caret-up text-danger\"></i></td>";
        				else 
        					td+="<td class=\"b-r text-right\"><i class=\"fa fa-minus\"></i></td>";
					
				}
				else {
					td+="<td class=\"b-r text-right\">"+Utility.round(rs.getDouble((i+6)) , 2)+"</td>";
				}
			}
		}
		
		if(count2==2) {
			td+="<td class=\"b-r text-center\">"
     				+ "<div class=\"sparkline2\" data-type=\"bar\" "
     				+ "data-height=\"45\" data-width=\"100%\" data-line-width=\"3\" "
     				+ "data-line-color=\"#dddddd\" data-spot-color=\"#bbbbbb\" "
     				+ "data-fill-color=\"\" data-highlight-line-color=\"#fff\" "
     				+ "data-spot-radius=\"3\" data-resize=\"true\">"+tot+"</div></td>";
		}
		else {
			td+="<td class=\"b-r text-right\" style=\"width:120px;\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
		}
		
		//System.out.println(rs.getString("tot"));
		
		td+="</tr>";
    	
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
	
	// Inserimento in array della riga (ULTIMA RIGA)
	sto.add(new Tables2("STO",""+num,td,
			"","","","","","","","","",""));
	
	for(int i=0;i<sto.size();i++) {
		
		output+=sto.get(i).getXdtab();
		
	}
}
catch(Exception e) {
	e.printStackTrace();
	output = e.getMessage();
}
conn.close();
%>
<%=output%>
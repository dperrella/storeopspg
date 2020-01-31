package pkg.analytics2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pkg.utility.Utility;

public class ReportTotalComparison {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = Utility.getConnData();
		
		getTotal(conn, 4, "2018-02-05","2018-02-22",
				"1617*1425*1623*1591*1717*1718*1719*1607*1600*1092*1689*1090*1093*1609*1087*1169*1417*1184*1392*1395*1397*1336*1391*1094*1113*1091*1435*1422*1444*1440*1401*1464*1337*1469*1472*1473*1560*1562*1475*1615*1525*1513*1530*1566*1577*1531*1592*1593*1594*1177*1622",
				"EAS*EDI*EOF*EON","W",false,"4","C02","");	
	}
	
	public static String getTotal(Connection conn, int rowspan,
			String dtda, String dtal, String idstores, String kpi, 
			String tipo, boolean l4l, String group, String comparison, String uid) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String table = "";
		try {
		
			sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated "
					+ "from test.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
					+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"', '"+uid+"') a, "
					+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' "
					+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
			System.out.println("COM:"+sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			String td = "";
	        int count = 0;
	        int count2= 0;
	        int num = 0;
	        
	        String border = "";
	        
	        String tot = "";
	        String icon = "";
			while(rs.next()) {
				
				if(count==0) {
	        		
	        		if(num>0) {
	        			
	        			//System.out.println(td);
	        			table+=td;
	        			
	        		}
	        		td="";
	        		
	        		
	        		if(count2==0) {
	        			//System.out.print(count+" "+rs.getString("op")+"(rowspan: "+(rowspan*3)+") ");
	        			td+="<tr><td class=\"b-r\" style=\"width:120px;border-top: 2px solid #E0E0E0;\" rowspan=\""+rowspan*3+"\"><b>"+rs.getString("op")+"</b></td>";
	    			}
	        		//System.out.println(count2+" "+rs.getString("tipo")+ "(rowspan: 3)");
	        	
	        	}
				
				if(count2>=1&&count<=(rowspan*3)) {
	        		td+="<tr>";
	        		border="";
				}
				else {
					border="border-top: 2px solid #E0E0E0; ";
				}
				
				if(count2==0) {
					tot="";
					if(rs.getBoolean("calculated")) {
						icon = "<a href=\"../popup/calculated_kpi_comparison.jsp?kpi="+rs.getString("codkpi")+"&tp=NOR&dtda="+dtda+"&dtal="+dtal+"&idstores="+idstores+"&tipo="+tipo+"&l4l="+l4l+"&group="+group+"&comparison="+comparison+"&uid="+uid+"&rowspan="+rowspan+"&op="+rs.getString("op")+"\" data-toggle=\"ajaxModal\" class=\"pull-left m-r-xs\"><i class=\"icon-calculator text-xs \" style=\"color:"+rs.getString("color")+"\"></i></a>";
					}
					else {
						icon = "<i class=\"fa fa-circle pull-left text-xs m-t-xs\" "
								+ "style=\"color:"+rs.getString("color")+"\"></i> ";
					}
					/*
					td+="<td class=\"b-r\" style=\"width:120px;"+border+"\">"
							+ "<i class=\"fa fa-circle pull-left text-xs m-t-xs\" "
							+ "style=\"color:"+rs.getString("color")+"\"></i> "
							+ rs.getString("desckpi")+icon+"</td>";
					*/
					td+="<td class=\"b-r\" style=\"width:120px;"+border+"\" rowspan=\"3\">"
							+ rs.getString("desckpi")+icon+"</td>";
					icon = "";
					
				}
				
				td+="<td class=\"b-r\" style=\""+border+"\">"
						+ rs.getString("tipo")+"</td>";
				
				for(int i=0;i<55;i++) {
					if(rs.getString((i+5))!=null) {
						//System.out.print(rs.getString((i+6))+" ");
						
						if(count2==2) {
							tot+=Utility.round(rs.getDouble((i+5)) , 2)+",";
							
							if(rs.getDouble((i+5))<0)
	         					td+="<td class=\"b-r text-right\"><i class=\"fa fa-caret-down\" style=\"color:#3366CC\"></i></td>";
	         				else if(rs.getDouble((i+5))>0)
	         					td+="<td class=\"b-r text-right\"><i class=\"fa fa-caret-up text-danger\"></i></td>";
	         				else 
	         					td+="<td class=\"b-r text-right\"><i class=\"fa fa-minus\"></i></td>";
							
						}
						else {
							td+="<td class=\"b-r text-right\" style=\""+border+"\">"+Utility.round(rs.getDouble((i+5)) , 2)+""+rs.getString("kpi_symbol")+"</td>";
						}
					}
				}
				
				if(count2==2) {
					td+="<td class=\"b-r text-center\">"
	         				+ "<div class=\"sparkline\" data-type=\"bar\" "
	         				+ "data-height=\"45\" data-width=\"100%\" data-line-width=\"3\" "
	         				+ "data-line-color=\"#dddddd\" data-spot-color=\"#bbbbbb\" "
	         				+ "data-fill-color=\"\" data-highlight-line-color=\"#fff\" "
	         				+ "data-spot-radius=\"3\" data-resize=\"true\">"+tot+"</div></td>";
				}
				else {
					td+="<td class=\"b-r text-right\" style=\"width:120px;"+border+"\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
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
			
			//System.out.println(td);
        	table+=td;
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return table;
		
	}

}

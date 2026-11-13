package pkg.occupancy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pkg.utility.Utility;

public class ReportTotal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = Utility.getConnData();		
	}
	
	public static String getTotal(Connection conn, int rowspan,
			String dtda, String dtal, String idstores, String kpi, 
			String tipo, String l4l, String group, String comparison, String uid) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String table = "";
		System.out.println(uid);
		try {
		
			if(uid.trim().length()==0) {
				
				sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated "
						+ "from public.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
						+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"') a, "
						+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' "
						+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
				
				
				
			}
			else {
				sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated "
						+ "from test.report_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"',"
						+ "'"+kpi.replaceAll(",","*")+"','"+tipo+"',"+l4l+","+group+",'"+comparison+"', '"+uid+"') a, "
						+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' and op = 'SUM' "
						+ "order by op desc,codkpi, case tipo when '' then 1 when 'PREV' then 2 when 'DIFF' then 3 end asc;";
			}
			System.out.println("NOR:"+sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			String td = "";
	        int count = 0;
	        int num = 0;
	        
	        String border = "";
	        String icon = "";
			while(rs.next()) {
				
				if(count==0) {
	        		
	        		if(num>0) {
	        			
	        			System.out.println(td);
	        			table+=td;
	        			
	        		}
	        		td="";
	        	
	        		td+="<tr><td class=\"b-r\" style=\"width:120px;border-top: 2px solid #E0E0E0;\" rowspan=\""+rowspan+"\"><b>"+rs.getString("op")+"</b></td>";
				}
				
				if(count>=1&&count<=rowspan) {
	        		td+="<tr>";
	        		border="";
				}
				else {
					border="border-top: 2px solid #E0E0E0; ";
				}
				
				if(rs.getBoolean("calculated")) {
					icon = "<a href=\"../popup/calculated_kpi.jsp?kpi="+rs.getString("codkpi")+"&tp=NOR&dtda="+dtda+"&dtal="+dtal+"&idstores="+idstores+"&tipo="+tipo+"&l4l="+l4l+"&group="+group+"&comparison="+comparison+"&uid="+uid+"&rowspan="+rowspan+"&op="+rs.getString("op")+"\" data-toggle=\"ajaxModal\" class=\"pull-left m-r-xs\"><i class=\"icon-calculator text-xs \" style=\"color:"+rs.getString("color")+"\"></i></a>";
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
				td+="<td class=\"b-r\" style=\"width:120px;"+border+"\">"
						+ rs.getString("desckpi")+icon+"</td>";
				icon = "";
				for(int i=0;i<55;i++) {
					if(rs.getString((i+5))!=null) {
						//System.out.print(rs.getString((i+6))+" ");
						td+="<td class=\"b-r text-right\" style=\""+border+"\">"+Utility.round(rs.getDouble((i+5)) , 2)+""+rs.getString("kpi_symbol")+"</td>";
						
					}
				}
				
				td+="<td class=\"b-r text-right\" style=\"width:120px;"+border+"\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
 				
				//System.out.println(rs.getString("tot"));
				
				td+="</tr>";
	        	
				count+=1;
	        	if(count==rowspan) {
	        		count = 0;
	        	}
	        	num+=1;
	        	
	        	
			}
			rs.close();
			st.close();
			
			System.out.println(td);
        	table+=td;
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return table;
		
	}

}

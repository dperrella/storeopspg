package pkg.hb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pkg.utility.Utility;

public class ReportTotalAdvanced {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = Utility.getConnData();
		
		getTotal(conn, 6, 2, "2");
		
	}
	
	public static String getTotal(Connection conn, int rowspan, int numPeriods,
			String id_advanced) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String table = "";
		try {
		
			sql   = "select a.*, color, coalesce(kpi_symbol , null , '') kpi_symbol, calculated "
					+ "from advanced_report_total('"+id_advanced+"') a, "
					+ "ztables where trim(codkpi) = trim(xcdel) and xcdtb = 'KPI' "
					+ "order by op desc,codkpi, tipo";
			System.out.println("ADV:"+sql);
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
	        			td+="<tr><td class=\"b-r\" style=\"width:120px;border-top: 2px solid #E0E0E0;\" rowspan=\""+rowspan+"\"><b>"+rs.getString("op")+"</b></td>";
	    			}
	        		//System.out.println(count2+" "+rs.getString("tipo")+ "(rowspan: 3)");
	        	
	        	}
				
				if(count2>=1&&count<=(rowspan)) {
	        		td+="<tr>";
	        		border="";
				}
				else {
					border="border-top: 2px solid #E0E0E0; ";
				}
				
				if(count2==0) {
					
					if(rs.getBoolean("calculated")) {
						icon = "<a href=\"../popup/calculated_kpi.jsp?kpi="+rs.getString("codkpi")+"&tp=ADV&id_advanced="+id_advanced+"\" data-toggle=\"ajaxModal\" class=\"pull-right\"><i class=\"icon-calculator\"></i></a>";
					}
					tot="";
					td+="<td class=\"b-r\" style=\"width:120px;"+border+"\" rowspan=\""+numPeriods+"\">"
							+ "<i class=\"fa fa-circle pull-left text-xs m-t-xs\" "
							+ "style=\"color:"+rs.getString("color")+"\"></i> "
							+ rs.getString("desckpi")+icon+"</td>";
					icon="";
				}
				
				td+="<td class=\"b-r\" style=\""+border+"\">"
						+ rs.getString("tipo")+"</td>";
				
				for(int i=0;i<55;i++) {
					if(rs.getString((i+6))!=null) {
						//System.out.print(rs.getString((i+6))+" ");
						td+="<td class=\"b-r text-right\" style=\""+border+"\">"+Utility.round(rs.getDouble((i+6)) , 2)+""+rs.getString("kpi_symbol")+"</td>";
					}
				}
				
				td+="<td class=\"b-r text-right\" style=\"width:120px;"+border+"\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
				//System.out.println(rs.getString("tot"));
				
				td+="</tr>";
	        	
				count+=1;
	        	if(count==(rowspan)) {
	        		count = 0;
	        	}
	        	
	        	count2+=1;
	        	if(count2==numPeriods) {
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

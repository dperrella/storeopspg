package pkg.hb;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.beans.Tables3;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DeskReportAdvancedExcel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String getXls(String idcus, String dtda, String dtal, String idstores, String kpi, 
			String comparison, String group, String upd, String tipoPeriod, String lang, String like4like, String id_advanced) throws FileNotFoundException {
		
		String nomeFile = "";
		
		List<Tables2> sto = new ArrayList<Tables2>();
		sto.clear();
		
		List<Tables3> chart = new ArrayList<Tables3>();
		chart.clear();
		
		List<Tables> datesList = new ArrayList<Tables>();
		datesList.clear();
		
		ArrayList<HashMap<String, String>> kpiList;
		kpiList = new ArrayList<>();
		kpiList.clear();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(lang+".properties");
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String oggiIta = dateIta.format(date);
		
		String currentYear = oggi.substring(0,4);
		String currentMonth = oggi.substring(4,6);
		String currentWeek = "";
		
		Connection connAdmin = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		int rowspan = 0;
		int nrstores = 0;
		int nrkpi = 0;
		
		try {
			
			conn = Utility.getConnData();
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(currentYear), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat my = new SimpleDateFormat("yy-MM-dd");
			  
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			System.out.println(currentWeek);
			// ========================
			
			like4like = Utility.noNull(like4like);
			
			boolean l4l = false;
			if(like4like.length()>0)
				l4l = true;
			
			sql   = "select * from advanced_comparison where id = '"+id_advanced+"' fetch first 1 rows only ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()) {
				idstores = rs.getString("stores");
				kpi = rs.getString("kpi");
				dtda = dateIta.format(rs.getDate("dtda"));
				dtal = dateIta.format(rs.getDate("dtal"));
				tipoPeriod = rs.getString("tgroup");
			}
			rs.close();
			st.close();
			
			idstores = idstores.replaceAll("\\*", ",");
			kpi = kpi.replaceAll("\\*", ",");
			
			int numPeriods = 0;
			
			group = ""+group;
			group = Utility.noNull(group);
			if(group.length()==0)
				group="4";
			
			
			String temp[]=idstores.split(",");
			nrstores = temp.length;
			
			String temp2[]=kpi.split(",");
			nrkpi = temp2.length;
			
			System.out.println(dtda+" "+dtal);
			
			
			// ============
			// REPORT
			// ============
			
			// kpi per sql selezione per giorno
			String kpi_in = "";
			String kpi_in2 = "";
			String kpi_in3 = "";
			
			String temp3[] = kpi.split(",");
			rowspan = 0;
			for(int i=0;i<temp3.length;i++) {
				rowspan+=1;
				kpi_in+="''"+temp3[i]+"'',";
				kpi_in2+="'"+temp3[i]+"','"+temp3[i]+"2',";
			}
			if(kpi_in.length()>0)
				kpi_in=kpi_in.substring(0,kpi_in.length()-1);
			if(kpi_in2.length()>0)
				kpi_in2=kpi_in2.substring(0,kpi_in2.length()-1);
			
			// Nuovo rowspan pe advanced
			sql   = "select dtda, dtal, ordinamento from advanced_comparison where id = '"+id_advanced+"' ";
			System.out.println(sql);
			numPeriods = 0;
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			String chartFormatter="";
			while(rs.next()) {
				numPeriods+=1;
				datesList.add(new Tables("DTA", my.format(rs.getDate("dtda"))+" "+my.format(rs.getDate("dtal")),rs.getString("ordinamento"),"","","",""));
				
				chartFormatter+=rs.getString("dtda")+"/"+rs.getString("dtal")+": {c"+(numPeriods-1)+"}<br />";
				
			}
			rs.close();
			st.close();
			rowspan = rowspan*numPeriods;
		
			for(int i=0;i<temp3.length;i++) {
				for(int d=0;d<datesList.size();d++) {
					String suff = "";
					if(d>0)
						suff = ""+(d+1);
					
					kpi_in3+="'"+temp3[i]+suff+"',";
				}
			}
			
			if(kpi_in3.length()>0)
				kpi_in3=kpi_in3.substring(0,kpi_in3.length()-1);
			
			
			sql   = "select * from advanced_report_grid('"+id_advanced+"') "
					+ "order by idcat, codkpi, ordine ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			// Var per grafico
	        String stores = "";
	        
			int count = 0;
	        int num = 0;
	        
	        String td = "";
	        
	        String descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Store</th>";
			
			switch (group) {
			case "1":
				descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Customer</th>";
				break;
			case "2":
				descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Nation</th>";
				break;
			case "3":
				descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Region</th>";
				break;
			}
			
			String idcat = "";
			String category = "";
			int count2= 0;
			
			String tot = "";
	        
			while(rs.next()) {
				
				idcat = rs.getString("idcat");
				category = rs.getString("category");
				
				if(count==0) {
	        		
	        		if(num>0) {
	        			
	        			//System.out.print(rs.getString("category")+" ");
	        			// Inserimento in array della riga
		        		sto.add(new Tables2("STO",""+num,td,
		        				idcat,category,"","","","","","","",""));
	        		}
	        		td="";
	        		
	        		if(count2==0) {
		        		
		        		if(group.equals("1")||group.equals("2")||group.equals("3")) {
		        			
		        			if(group.equals("1")) {
		        				//stores+="'"+rs.getString("category")+"',";
				        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','1')\"><b>"+Utility.getCustomerDescription(conn, rs.getString("category"))+"</b></a></td>";
			        		}
		        			else if(group.equals("2")) {
		        				//stores+="'"+rs.getString("category")+"',";
				        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','2')\"><b>"+Utility.getNationDescription(conn, rs.getString("category"))+"</b></a></td>";
			        		}
		        			else if(group.equals("3")) {
		        				//stores+="'"+rs.getString("category")+"',";
				        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','3')\"><b>"+Utility.getRegionDescription(conn, rs.getString("category"))+"</b></a></td>";		
		        			}
		        		}
		        		else {
			        		//stores+="'"+rs.getString("category").replaceAll("'", " ")+"',";
			        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><b>"+rs.getString("category")+"</b></td>";
		        		}
		        		
	        		}
	        		
				}
				if(count>=1&&count<=rowspan)
	        		td+="<tr>";
				
				if(count2==0) {
					tot="";
					td+="<td class=\"b-r\" style=\"width:120px\" rowspan=\""+numPeriods+"\">"+rs.getString("desckpi")+"</td>";
				}
				
				td+="<td class=\"b-r\">"
						+ rs.getString("tipo")+"</td>";
				
				for(int i=0;i<55;i++) {
					if(rs.getString((i+7))!=null) {
						//System.out.print(rs.getString((i+6))+" ");
						td+="<td class=\"b-r text-right\">"+Utility.round(rs.getDouble((i+7)) , 2)+"</td>";
					}
				}
				
				td+="<td class=\"b-r text-right\" style=\"width:120px;\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
				//System.out.println(rs.getString("tot"));
				
				td+="</tr>";
	        	
				count+=1;
	        	if(count==rowspan) {
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
			
			// Inserimento in array della riga (ULTIMA RIGA)
    		sto.add(new Tables2("STO",""+num,td,
    				idcat,category,"","","","","","","",""));
			
    		String th=descr+"<th style=\"border-top: 2px solid #E0E0E0;\">KPI</th><th style=\"border-top: 2px solid #E0E0E0;\">PERIOD</th>";
	        
    		// Intestazione
			
			System.out.println("*****"+tipoPeriod+"*****");
			
			//Formattazione data
			Date d1 = sdf.parse(dtda);
			dtda = iso.format(d1);
			Date d2 = sdf.parse(dtal);
			dtal = iso.format(d2);
				
			switch (tipoPeriod) {
         	case "Y": 
         		
         		
         		Calendar startCalendar = new GregorianCalendar();
         		startCalendar.setTime(iso.parse(dtda));
         		Calendar endCalendar = new GregorianCalendar();
         		endCalendar.setTime(iso.parse(dtal));

         		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
         		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
         		
         		for(int i=0;i<=diffMonth;i++) {
         			
         			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
         			//System.out.println(iso.format(startCalendar.getTime()));
         			
         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
         		
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
         			
         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">W"+(i+1)+"</th>";
         		
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
         			//System.out.println(iso.format(startCalendar.getTime()));
         			
         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
	         		
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
         			
         			//System.out.println(iso.format(startCalendar.getTime()));
         			
         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
	         		
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
         			
         			//System.out.println(iso.format(startCalendar.getTime()));
         			
         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
         			
         			startCalendar.add(Calendar.DATE, 1);
         			
         		}
         		
         		break;
         		
         	case "D":
         		for(int i=0;i<24;i++) {
         			String hh = "00"+i;
         			hh=hh.substring(hh.length()-2,hh.length());
         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+hh+":00</th>";
         		}
         		/*
         		th="<th style=\"border-top: 2px solid #E0E0E0;\">08:00</th>"
         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">09:00</th>"
         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">10:00</th>"
         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">11:00</th>"
         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">12:00</th>"
         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">13:00</th>"
         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">14:00</th>"
     				+ "<th style=\"border-top: 2px solid #E0E0E0;\">15:00</th>"
     				+ "<th style=\"border-top: 2px solid #E0E0E0;\">16:00</th>"
     				+ "<th style=\"border-top: 2px solid #E0E0E0;\">17:00</th>"
     				+ "<th style=\"border-top: 2px solid #E0E0E0;\">18:00</th>"
     				+ "<th style=\"border-top: 2px solid #E0E0E0;\">19:00</th>"
     				+ "<th style=\"border-top: 2px solid #E0E0E0;\">20:00</th>"
     				+ "<th style=\"border-top: 2px solid #E0E0E0;\">21:00</th>";
         		*/
         		break;
			}
			
			th+="<th style=\"border-top: 2px solid #E0E0E0;\">Total</th>";
			
			// Total / avg
			String total = ReportTotalAdvanced.getTotal(conn, rowspan, numPeriods, id_advanced);
				
			nomeFile = "<table>"+total+"<tr>"+th+"</tr><tbody>";
			
			for(int i=0;i<sto.size();i++) {
				nomeFile+=sto.get(i).getXdtab();
			}
			
			nomeFile+="</tbody></table>";
			
						
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
				if(connAdmin!=null)
					connAdmin.close();
			}
			catch(Exception e) {}
			
		}
		
		return nomeFile;
		
		
		
	}
	
	
}

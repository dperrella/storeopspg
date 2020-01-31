package pkg.hb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pkg.utility.Utility;

public class CreateDefaultCalendar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection conn = Utility.getConnData();
		String customer_id = "9999";
		
		createCalendar(conn, customer_id);
		
	}
	
	public static void createCalendar(Connection conn, String customer_id) {
		
		
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		
		Date date = new Date();
		String oggi = dateNum.format(date);
		
		String caltype = "";
		String year = oggi.substring(0, 4);
		
		String monthstart = "0";
		
		// Campi database
		String $fromdate="";
		String $todate="";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		int num = 0;
		try {
			
			sql   = "select count(*) num from financial_calendar2 where year = ? and caltype= ? and customer_id = ? ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, Integer.parseInt(year));
			st.setString(2, "M");
			st.setInt(3, Integer.parseInt(customer_id));
			
			// Esegue l'sql
			rs = st.executeQuery();
			
			num = 0;
			if(rs.next()) {
				num = rs.getInt("num");
			}
			rs.close();
			
			if(num==0) {
				
				caltype = "M";
				System.out.println("calendario 'M' non esiste per customer "+customer_id+"... inserisco");
				
				// Inserimento
				System.out.println("INS...");
				
				int mm = 0;
				String yearCal=year;
				boolean yearChange = false;
				for(int i=0;i<=11;i++) {
					
					mm = i+(Integer.parseInt(monthstart));
					if(mm>11) {
						mm=mm-12;
						if(yearChange==false) {
							yearCal = ""+(Integer.parseInt(yearCal)+1);
							yearChange=true;
						}
					}
					Calendar c = Calendar.getInstance();
					c.set(Integer.parseInt(yearCal), mm, 1);
					
					$fromdate = iso.format(c.getTime());
					
					// fine mese
					c.set(Integer.parseInt(yearCal), (mm+1), 1);
					c.add(Calendar.DAY_OF_MONTH, -1);
					
					$todate = iso.format(c.getTime());
					
					
					System.out.println(mm+" "+$fromdate+" "+$todate);
					
					
					sql   = "insert into financial_calendar2 "+
							"(caltype, year, calperiod, fromdate, todate, customer_id) "+
							"values "+
							"(?, ?, ?, ?, ?, ?) ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1,  caltype);
					st.setInt(2, Integer.parseInt(year));
					st.setInt(3, (mm+1));
					st.setDate(4, java.sql.Date.valueOf($fromdate));
					st.setDate(5, java.sql.Date.valueOf($todate));
					st.setInt(6, Integer.parseInt(customer_id));
					
					st.executeUpdate();
					
				}
			}
			else {
				System.out.println("calendario 'M' gia esistente per "+customer_id);
			}
			
			sql   = "select count(*) num from financial_calendar2 where year = ? and caltype= ? and customer_id = ? ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, Integer.parseInt(year));
			st.setString(2, "W");
			st.setInt(3, Integer.parseInt(customer_id));
			
			// Esegue l'sql
			rs = st.executeQuery();
			
			num = 0;
			if(rs.next()) {
				num = rs.getInt("num");
			}
			rs.close();
			
			if(num==0) {
				
				caltype = "W";
				System.out.println("calendario 'W' non esiste per customer "+customer_id+"... inserisco");
				
				// =======================
				// Settimane
				String optWE = "";
				
				Calendar c = Calendar.getInstance();
				c.set(Integer.parseInt(year), Integer.parseInt("01")-1, Integer.parseInt("01"));
				
				// Formato data
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				  
				// Numero di settimane
				int weeks = 52;
				
				
				// Per settimanale
				Locale lc = new Locale("en","UK");
				
				// Per settimanale
				Calendar cal = Calendar.getInstance(lc);
				// Imposta l'anno corrente
				cal.set(Integer.parseInt(year), Integer.parseInt("01")-1, Integer.parseInt("01"));
				cal.setFirstDayOfWeek(Calendar.MONDAY);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				String week = "";
				
				// campi tables
				String $xcdtb = "SET";
				String $xcdel = "";
				String $xdtab = "";
				String $cmp1 = "";
				
				String dtda = "";
				String dtal = "";
				for (int i=1; i<=weeks; i++) {
					  
					week = "00"+i;
					week = week.substring(week.length()-2,week.length());
					
					// valorizza xcdel col numero della settimana
					$xcdel = week;
					
					
					dtda = sdf.format(cal.getTime());
					
					// valorizza il primo giorno della settimana
					$xdtab=sdf.format(cal.getTime())+" - ";
					
					cal.add(Calendar.DATE, 6);  
					//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					
					// valorizza l'ultimo giorno della settimana
					$xdtab+=sdf.format(cal.getTime());
					
					dtal = sdf.format(cal.getTime());
					
					optWE+="<option value=\""+$xcdel+"\" "+$cmp1+">"+$xdtab+"</option>";
					
					
					sql   = "insert into financial_calendar2 "+
							"(caltype, year, calperiod, fromdate, todate, customer_id) "+
							"values "+
							"(?, ?, ?, ?, ?, ?) ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1,  caltype);
					st.setInt(2, Integer.parseInt(year));
					st.setInt(3, (i));
					st.setDate(4, java.sql.Date.valueOf(dtda));
					st.setDate(5, java.sql.Date.valueOf(dtal));
					st.setInt(6, Integer.parseInt(customer_id));
					
					st.executeUpdate();
					
					
					
					cal.add(Calendar.DATE, 1);  
					
					//*blank bariabili
					$xdtab = "";
					$cmp1 = "";
			  	}
				
			}
			else {
				System.out.println("calendario 'W' gia esistente per "+customer_id);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}

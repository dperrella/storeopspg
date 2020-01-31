package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Employee;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ControlList_BKP2 extends AbstractController {
	
	public static SimpleDateFormat hour = new SimpleDateFormat("kk:mm");
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();

		List<Employee> emp = new ArrayList<Employee>();
		List<Tables> day = new ArrayList<Tables>();
		List<Tables2> cnt = new ArrayList<Tables2>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		// Pulisce le lista
		emp.clear();
		day.clear();
		cnt.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st2 = null;
		ResultSet rs2 = null;
		
		String sql = "";
		String cond = "";
		
		String dsfun = "Lista Controlli per Dipartimento";
		
		String msg = "";
		int count = 0;
		
		// -----------------------
		// Anno
		String year = req.getParameter("year");
		String week = req.getParameter("week");
		String id_departments = req.getParameter("id_departments");
		
		try {
			
			// Imposta data inizio e data fine
			// dal periodo
			// Formato data
			String dtini = "";
			String dtfin = "";
			
			SimpleDateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
						
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(year), 0, 1);
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));        
			// dtini
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			dtini = dateIso.format(cal.getTime());
			// dtfin
			cal.add(Calendar.DATE, 6);  
			dtfin = dateIso.format(cal.getTime());
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			st2=conn.createStatement();
			
			
			
			
			
			
			
			
			
			
			
			// ====================
			// Lista utenti del dipartimento/ruolo
			sql  =  "select c.id, c.name, c.surname, d.description, id_departments from "+
					"hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
					"where a.id=b.id_roles "+
					"and b.id_employee = c.id "+ 
					"and b.id_departments = d.id "+
					"and d.id = '"+id_departments+"' and b.dateend is null ";
			rs=st.executeQuery(sql);
			
			// Tabella dettagli
			String table = "";
			
			while(rs.next()) {
				
				// Worked / Planned
				sql   = "select day_ref, type, xcdel, substr(xdtab , 3 , 100) xdtab, "+
						"ingresso, inizio_pausa, fine_pausa, uscita "+
						"from vw_control, ztables "+
						"where xcdtb = 'TPT' and trim(xcdel) = trim(type) "+
						"and id_employee = '"+rs.getString("id")+"' "+
						"and id_departments = '"+rs.getString("id_departments")+"' "+
						"and day_ref between '"+dtini+"' and '"+dtfin+"' "+
						// Solo Worked?
						"and type in('WRK') "+
						"order by day_ref, xdtab ";
				System.out.println(sql);
				rs2 = st2.executeQuery(sql);
				
				String rott = "";
				String day_ref = "";
				
				String hh1 = "";
				String hh2 = "";
				String hh3 = "";
				String hh4 = "";
				
				long tot1 = 0;
				long tot2 = 0;
				long tot = 0;
				
				long wtotale = 0;
				long ptotale= 0;
				
				String css = "";
				
				while(rs2.next()) {
					
					hh1 = ""+rs2.getString("ingresso");
					hh2 = ""+rs2.getString("inizio_pausa");
					hh3 = ""+rs2.getString("fine_pausa");
					hh4 = ""+rs2.getString("uscita");
					
					hh1 = Utility.noNull(hh1);
					hh2 = Utility.noNull(hh2);
					hh3 = Utility.noNull(hh3);
					hh4 = Utility.noNull(hh4);
					
					if(hh1.length()>0)
						hh1 = hour.format(rs2.getTime("ingresso"));
					else
						hh1 = "-";
					if(hh2.length()>0)
						hh2 = hour.format(rs2.getTime("inizio_pausa"));
					else
						hh2 = "-";
					if(hh3.length()>0)
						hh3 = hour.format(rs2.getTime("fine_pausa"));
					else
						hh3 = "-";
					if(hh4.length()>0)
						hh4 = hour.format(rs2.getTime("uscita"));
					else
						hh4 = "-";
					
					if(hh4.equals("-")) {
						Date date1 = hour.parse(hh1);
						Date date2 = hour.parse("24:00");
						
						tot1 = date2.getTime() - date1.getTime(); 
					}
					else {
						if(!hh1.equals("-")) {
							Date date1 = hour.parse(hh1);
							Date date2 = hour.parse(hh4);
						
							tot1 = date2.getTime() - date1.getTime(); 
						}
					}
					
					if(hh3.equals("-")||hh2.equals("-")) {
						// che faccio???
					}
					else {
						Date date1 = hour.parse(hh2);
						Date date2 = hour.parse(hh3);
						
						tot2 = date2.getTime() - date1.getTime(); 
					}
					
					tot = tot1-tot2;
					
					
					
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(tot);
					c.add(Calendar.HOUR, -1);
					
					if(!rott.equals(rs2.getString("day_ref"))) {
						day_ref = dateIta.format(rs2.getDate("day_ref"));
					}
					else {
						day_ref = "";
					}
					
					if(rs2.getString("xcdel").trim().equals("WRK")) {
						css = "bg-info";
						wtotale+=tot;
					}
					
					// Worked
					table+="<tr><td class=\"b-b\"><b>"+day_ref+"</b></td>"+
						   "<td class=\"b-b\"><span class=\"label "+css+"\">"+rs2.getString("xdtab")+"</span></td>"+
						   "<td class=\"b-b\">"+hh1+"</td>"+
						   "<td class=\"b-b\">"+hh2+"</td>"+
						   "<td class=\"b-b\">"+hh3+"</td>"+
						   "<td class=\"b-b\">"+hh4+"</td>"+
						   "<td class=\"b-b\" style=\"text-align:right\"><b>"+hour.format(c.getTime())+"</b></td></tr>";
					
					// Planned
					table+=getTypes(conn, rs.getString("id"), rs.getString("id_departments"), dateIso.format(rs2.getDate("day_ref")), "PLN");
					
					
					
					
					// Differenze
					table+="<tr><td class=\"b-b\">Differenze</td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td></tr>";
					
					table+="<tr><td class=\"b-b\">Notturno</td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td>"+
						   "<td class=\"b-b\"></td></tr>";
					
					table+="<tr><td class=\"b-2x b-b b-dark\">Monte Ore</td>"+
						   "<td class=\"b-2x b-b b-dark\"></td>"+
						   "<td class=\"b-2x b-b b-dark\"></td>"+
						   "<td class=\"b-2x b-b b-dark\"></td>"+
						   "<td class=\"b-2x b-b b-dark\"></td>"+
						   "<td class=\"b-2x b-b b-dark\"></td>"+
						   "<td class=\"b-2x b-b b-dark\"></td></tr>";
					
					
					
					css = "";
					
					rott = rs2.getString("day_ref");
					
				}
				rs2.close();
				
				
				
				
				// Data inizio
				Calendar ini = Calendar.getInstance();
				ini.setTimeInMillis(0);
				
				// Totale worked
				Calendar wc = Calendar.getInstance();
				wc.setTimeInMillis(wtotale);
				wc.add(Calendar.HOUR, -1);
				
				long diff = wc.getTimeInMillis() - ini.getTimeInMillis();
				long diffMinutes = diff / (60 * 1000);  
				int Hours = (int)diffMinutes/60;     
				int Minutes = (int)diffMinutes%60;
				
				// Totale planned
				Calendar pc = Calendar.getInstance();
				pc.setTimeInMillis(wtotale);
				pc.add(Calendar.HOUR, -1);
				
				long diff2 = wc.getTimeInMillis() - ini.getTimeInMillis();
				long diffMinutes2 = diff2 / (60 * 1000);
				int Hours2 = (int)diffMinutes2/60;     
				int Minutes2 = (int)diffMinutes2%60;
				
				
				emp.add(new Employee(rs.getString("id"),rs.getString("name"),rs.getString("surname"),""+Hours+":"+Minutes,""+Hours2+":"+Minutes2,"","","","",
						"","","","","","",rs.getString("description"),rs.getString("id_departments"),table,"","","","",""));
				
				table = "";
				
				wtotale = 0;
				ptotale = 0;
			}
			rs.close();
			
			st2.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", dsfun);
		obj.put("week", week);
		obj.put("year", year);
		obj.put("id_departments", id_departments);
		
		obj.put("emp", emp);
		obj.put("day", day);
		obj.put("cnt", cnt);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/app/control/control-list", obj);
	}
	
	
	public static String getTypes(Connection conn, String id_employee, 
			String id_departments, String day_ref, String type) throws SQLException {
		
		String table = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			st=conn.createStatement();
			// Worked / Planned
			sql   = "select day_ref, type, xcdel, substr(xdtab , 3 , 100) xdtab, "+
					"ingresso, inizio_pausa, fine_pausa, uscita "+
					"from vw_control, ztables "+
					"where xcdtb = 'TPT' and trim(xcdel) = trim(type) "+
					"and id_employee = '"+id_employee+"' "+
					"and id_departments = '"+id_departments+"' "+
					"and day_ref = '"+day_ref+"' "+
					// Solo Worked?
					"and type in('PLN') "+
					"order by day_ref, xdtab ";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			String hh1 = "";
			String hh2 = "";
			String hh3 = "";
			String hh4 = "";
			
			long tot1 = 0;
			long tot2 = 0;
			long tot = 0;
			
			String css = "";
			
			while(rs.next()) {
				
				hh1 = ""+rs.getString("ingresso");
				hh2 = ""+rs.getString("inizio_pausa");
				hh3 = ""+rs.getString("fine_pausa");
				hh4 = ""+rs.getString("uscita");
				
				hh1 = Utility.noNull(hh1);
				hh2 = Utility.noNull(hh2);
				hh3 = Utility.noNull(hh3);
				hh4 = Utility.noNull(hh4);
				
				if(hh1.length()>0)
					hh1 = hour.format(rs.getTime("ingresso"));
				else
					hh1 = "-";
				if(hh2.length()>0)
					hh2 = hour.format(rs.getTime("inizio_pausa"));
				else
					hh2 = "-";
				if(hh3.length()>0)
					hh3 = hour.format(rs.getTime("fine_pausa"));
				else
					hh3 = "-";
				if(hh4.length()>0)
					hh4 = hour.format(rs.getTime("uscita"));
				else
					hh4 = "-";
				
				if(hh4.equals("-")) {
					Date date1 = hour.parse(hh1);
					Date date2 = hour.parse("24:00");
					
					tot1 = date2.getTime() - date1.getTime(); 
				}
				else {
					Date date1 = hour.parse(hh1);
					Date date2 = hour.parse(hh4);
					
					tot1 = date2.getTime() - date1.getTime(); 
				}
				
				if(hh3.equals("-")||hh2.equals("-")) {
					// che faccio???
				}
				else {
					Date date1 = hour.parse(hh2);
					Date date2 = hour.parse(hh3);
					
					tot2 = date2.getTime() - date1.getTime(); 
				}
				
				tot = tot1-tot2;
				
				
				
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(tot);
				c.add(Calendar.HOUR, -1);
				
				if(rs.getString("xcdel").trim().equals("PLN")) {
					css = "bg-success";
				}
				
				table+="<tr><td class=\"b-b\"></td>"+
					   "<td class=\"b-b\"><span class=\"label "+css+"\">"+rs.getString("xdtab")+"</span></td>"+
					   "<td class=\"b-b\">"+hh1+"</td>"+
					   "<td class=\"b-b\">"+hh2+"</td>"+
					   "<td class=\"b-b\">"+hh3+"</td>"+
					   "<td class=\"b-b\">"+hh4+"</td>"+
					   "<td class=\"b-b\" style=\"text-align:right\"><b>"+hour.format(c.getTime())+"</b></td></tr>";
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return table;
		
	}

}

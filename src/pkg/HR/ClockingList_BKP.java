package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Clocking;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ClockingList_BKP extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Clocking> clk = new ArrayList<Clocking>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		// Pulisce le lista
		clk.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String dsfun = "Lista Timbrature Dipendente";
		
		String msg = "";
		int count = 0;
		
		// -----------------------
		// Anno
		String year = req.getParameter("year");
		String week = req.getParameter("week");
		String id_departments = req.getParameter("id_departments");
		String id_employee = req.getParameter("id_employee");
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// Imposta data inizio e data fine
			// dal periodo
			// Formato data
			String dtini = "";
			String dtfin = "";
			
			SimpleDateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat hour = new SimpleDateFormat("kk:mm:ss");
			SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
						
			Calendar cal = Calendar.getInstance(Locale.GERMAN);   
			cal.set(Integer.parseInt(year), 0, 1);
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));        
			// dtini
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			dtini = dateIso.format(cal.getTime());
			// dtfin
			cal.add(Calendar.DATE, 6);  
			dtfin = dateIso.format(cal.getTime());
			
			// Campi database
			String $id_employee="";
			String $des_employee="";
			String $badge="";
			String $number="";
			String $reason="";
			String $timestamp="";
			String $note="";
			String $approved="";
			String $exported="";
			String $id_departments="";
			String $des_departments="";
			String $autoid="";
			String $timeins="";
			String $notejustificative = "";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			sql   = "select id_employee, name, surname, badge, note_justificative,"
					+ "number, reason, timestamp, approved, exported, id_departments, "
					+ "description, autoid, timeins from hr_clockings a, hr_employee b, hr_departments c, hr_badge d "+
					"where id_employee = '"+id_employee+"' "+
					//"and id_departments = '"+id_departments+"' "+
					"and timestamp between '"+dtini+"' and '"+dtfin+"' "+
					"and a.id_employee = b.id and a.id_departments = c.id "
					+ "and a.badge = d.number "
					+ "and c.id_customer = "+session.getAttribute("idcus")+" "
					+ "group by id_employee, name, surname, badge, note_justificative, " + 
					"number, reason, timestamp, approved, exported, id_departments, " + 
					"description, autoid, timeins ";
			
			
			sql   = "select id_employee, name, surname, max(note_justificative) note_justificative, max(approved) approved, "
					+ "reason, timestamp, exported, id_departments, "
					+ "description, autoid from hr_clockings a, hr_employee b, hr_departments c "+
					"where id_employee = '"+id_employee+"' "+
					//"and id_departments = '"+id_departments+"' "+
					"and timestamp between '"+dtini+"' and '"+dtfin+"' "+
					"and a.id_employee = b.id and a.id_departments = c.id "
					+ "and c.id_customer = "+session.getAttribute("idcus")+" "
					+ "group by id_employee, name, surname, " + 
					"reason, timestamp, exported, id_departments, " + 
					"description, autoid ";
			System.out.println(sql);
			/*
			sql   = "select * from hr_clockings a, hr_employee b, hr_departments c, hr_badge d "+
					"where "+
					"a.id_employee = b.id and a.id_departments = c.id and a.badge = d.id ";
			*/
			rs=st.executeQuery(sql);
			int counts = 0;
			while(rs.next()) {
				
				$id_employee=rs.getString("id_employee");
				$des_employee=rs.getString("name")+" "+rs.getString("surname");
				//$badge=rs.getString("badge");
				$notejustificative=rs.getString("note_justificative");
				
				$notejustificative = Utility.noNull(""+$notejustificative);
								
				//$number=rs.getString("number");
				$reason=rs.getString("reason");
				$timestamp=rs.getString("timestamp");
				$note=rs.getString("note_justificative");
				$approved=rs.getString("approved");
				$exported=rs.getString("exported");
				$id_departments=rs.getString("id_departments");
				$des_departments=rs.getString("description");
				$autoid=rs.getString("autoid");
				//$timeins=rs.getString("timeins");
				
				$cmp1 = hour.format(rs.getTime("timestamp"));
				
				Date dt = rs.getTimestamp("timestamp");
				long ts = dt.getTime();
				$cmp2 = day.format(rs.getDate("timestamp"));
				
				if (!"".equals($notejustificative)) {
					
					$notejustificative = $notejustificative;
					
				}
				
				$cmp3 = $notejustificative;
				
				
				if (!"".equals($notejustificative) && !$approved.equals("1")) {
					$cmp4 = "<input type='checkbox' id='chk_"+counts+"' name='chk_"+counts+"' onchange=approva(this,'"+$id_employee+"','"+ts+"') />";
				}else {
					$cmp4 = "";
				}
				
				clk.add(new Clocking($id_employee,$des_employee,$badge,$number,$reason,
						$timestamp,$note,$approved,$exported,$id_departments,
						$des_departments,$autoid,$timeins,$cmp1,$cmp2,$cmp3,$cmp4));
				
				counts++;
			}
			rs.close();
			
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
		obj.put("id_employee", id_employee);
		
		obj.put("clk", clk);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/control/clocking-list", obj);
	}

}

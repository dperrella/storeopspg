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
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ClockingDaAppList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Tables2> clk = new ArrayList<Tables2>();
		
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
		String id_departments = ""+req.getParameter("id_departments");
		id_departments = Utility.noNull(id_departments);
		
		String id_employee = ""+req.getParameter("id_employee");
		id_employee = Utility.noNull(id_employee);
		
		if(id_departments.length()>0) {
			cond+=" and id_departments = '"+id_departments+"' ";
		}
		if(id_employee.length()>0) {
			cond+=" and id_employee = '"+id_employee+"' ";
		}
		
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
			String $dt="";
			String $id_employee="";
			String $ds_employee="";
			String $id_departments="";
			String $ds_departments="";
			String $reason="";
			String $ho="";
			String $note="";
			String $approved="";
			String $timestamp = "";
			
			String $action="";
			
			sql   = "select distinct date(timestamp) dt, \r\n" + 
					"c.id_employee, e.name, e.surname, c.id_departments, d.description, c.reason, timestamp::time ho, \r\n" + 
					"note_justificative, approved, timestamp\r\n" + 
					"from hr_clockings c \r\n" + 
					"left join hr_departments d on c.id_departments = d.id "+
					//"and id_departments = '"+id_departments+"' \r\n" + 
					"join hr_employee e on e.id = c.id_employee\r\n" + 
					"where timestamp::date between '"+dtini+"' and '"+dtfin+"' "+
					"and e.id_customer = "+session.getAttribute("idcus")+" "+
					"and approved = '0' and char_length(note_justificative) > 0 "+cond+" "+
					"order by id_departments, id_employee, timestamp ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			int counts = 0;
			while(rs.next()) {
				
				$id_employee=rs.getString("id_employee");
				$ds_employee=rs.getString("name")+" "+rs.getString("surname");
				$id_departments=rs.getString("id_departments");
				$ds_departments=rs.getString("description");
				$reason=rs.getString("reason");
				$ho=rs.getString("ho");
				$note=""+rs.getString("note_justificative");
				$note=Utility.noNull($note);
				$approved=""+rs.getString("approved");
				$approved=Utility.noNull($approved);
				
				$timestamp=rs.getString("timestamp");
				
				Date dt = rs.getTimestamp("timestamp");
				long ts = dt.getTime();
				
				if($note.length()>0&&$approved.equals("0")) {
					$action = "<input type='checkbox' id='chk_"+counts+"' name='chk_"+counts+"' onchange=approva(this,'"+$id_employee+"','"+ts+"') />";
				}else {
					$action = "";
				}
				
				clk.add(new Tables2("CLK",$id_employee,$ds_employee,
						//1-4
						$id_departments,$ds_departments,$reason,$ho,
						//5-8
						$note,$approved,$timestamp,""+ts,
						//9-10
						day.format(rs.getDate("timestamp")),$action));
				
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
		
		return new ModelAndView("/hr/control/clocking-daapp-list", obj);
	}

}

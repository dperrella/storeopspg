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

import pkg.beans.HR.Departments;
import pkg.beans.Tables;
import pkg.beans.HR.Employee;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ControlRic extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		List<Tables> wk = new ArrayList<Tables>();
		List<Tables> mn = new ArrayList<Tables>();
		List<Departments> dep = new ArrayList<Departments>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		String currentMonth = oggi.substring(4,6);
		
		// Pulisce le lista
		wk.clear();
		mn.clear();
		dep.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String dsfun = "";
		String selPeriod = "";
		
		String msg = "";
		int count = 0;
		String currentWeek = "";
		
		// Tipo
		String tp = ""+req.getParameter("tp");
		tp=Utility.noNull(tp);
		obj.put("tp", tp);
		
		if(tp.trim().length()==0) {
			dsfun = propLang.get("cnt_controllo");
			selPeriod = propLang.get("pla_week");
		}
		else if(tp.equals("D")) {
			dsfun = propLang.get("cnt_contrDett");
			selPeriod = propLang.get("pla_week");
		}
		else if(tp.equals("M")) {
			dsfun = propLang.get("cnt_contrMese");
			selPeriod = propLang.get("cnt_selmese");
		}
		
		// -----------------------
		// Anno
		String year = ""+req.getParameter("year");
		year = Utility.noNull(year);
		if(year.length()==0)
			year = currentYear;
		
		String id_departments = ""+req.getParameter("id_departments");
		id_departments = Utility.noNull(id_departments);
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// =======================
			// Dipartimenti 
			String tipo = (String)session.getAttribute("tipo_hr");
			String username = (String)session.getAttribute("username");
			if(tipo.equals("M")||tipo.equals("S")||tipo.equals("D")) {
				
				sql  =  "select a.* "+
						"from hr_departments a, rel_dep_employee b, "+
						"hr_employee c, rel_emp_user d "+
						"where "+
						"("+
						"a.id = b.id_departments and c.id = b.id_employee "+
						"and d.id_employee = c.id and to_timestamp(b.dateend) >= now() "+
						"and d.id_user = '"+username+"' and a.id_customer = "+session.getAttribute("idcus")+" "+
						") "+
						"or "+
						"("+
						"a.id = b.id_departments and c.id = b.id_employee "+
						"and d.id_employee = c.id and to_timestamp(b.dateend) is null "+
						"and d.id_user = '"+username+"' and a.id_customer = "+session.getAttribute("idcus")+" "+
						")";
			}
			else if(tipo.equals("H")||tipo.equals("A")) {
				sql  =  "select * from hr_departments where id_customer = "+session.getAttribute("idcus")+" "+cond;
			}
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $description="";
			String $parentid="";
			String $address="";
			String $locality="";
			String $province="";
			String $zipcode="";
			String $state="";
			String $phone="";
			String $email="";
			String $department_code="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$description=rs.getString("description");
				$parentid=rs.getString("parent_id");
				$address=rs.getString("address");
				$locality=rs.getString("locality");
				$province=rs.getString("province");
				$zipcode=rs.getString("zipcode");
				$state=rs.getString("state");
				$phone=rs.getString("phone");
				$email=rs.getString("email");
				$department_code=rs.getString("department_code");
				
				dep.add(new Departments($id,$description,$parentid,$address,$locality,$province,$zipcode,
						$state,$phone,$email,$department_code,"","","",0,0,0,false,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			
			// =======================
			// Mesi
			String mm = "";
			String desMm = "";
			$cmp1 = "";
			$cmp2 = "";
			$cmp3 = "";
			$cmp4 = "";
			
			for(int i=1;i<=12;i++) {
				
				mm = "00"+i;
				mm = mm.substring(mm.length()-2, mm.length());
				
				if(mm.equals("01")) {
					desMm = propLang.get("gen");
				}
				else if(mm.equals("02")) {
					desMm = propLang.get("feb");
				}
				else if(mm.equals("03")) {
					desMm = propLang.get("mar2");
				}
				else if(mm.equals("04")) {
					desMm = propLang.get("apr");
				}
				else if(mm.equals("05")) {
					desMm = propLang.get("mag");
				}
				else if(mm.equals("06")) {
					desMm = propLang.get("giu");
				}
				else if(mm.equals("07")) {
					desMm = propLang.get("lug");
				}
				else if(mm.equals("08")) {
					desMm = propLang.get("ago");
				}
				else if(mm.equals("09")) {
					desMm = propLang.get("set");
				}
				else if(mm.equals("10")) {
					desMm = propLang.get("ott");
				}
				else if(mm.equals("11")) {
					desMm = propLang.get("nov");
				}
				else if(mm.equals("12")) {
					desMm = propLang.get("dic");
				}
				
				// Settimana attuale
				if(mm.equals(currentMonth)) {
					$cmp1 = "selected ";
				}
				
				// valorizza la lista
				mn.add(new Tables("MON",mm, desMm,$cmp1,"","",""));
				
				$cmp1 = "";
				
			}
			
			
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance(Locale.GERMAN);
			c.set(Integer.parseInt(currentYear), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			System.out.println(currentWeek);
			
			// Per settimanale
			Calendar cal = Calendar.getInstance(Locale.GERMAN);
			// Imposta l'anno corrente
			cal.set(Integer.parseInt(currentYear), 0, 1);
			
			String week = "";
			
			// campi tables
			String $xcdtb = "SET";
			String $xcdel = "";
			String $xdtab = "";
			$cmp1 = "";
			
			for (int i=1; i<=weeks; i++) {
				  
				week = "00"+i;
				week = week.substring(week.length()-2,week.length());
				
				// valorizza xcdel col numero della settimana
				$xcdel = week;
				
				cal.set(Integer.parseInt(currentYear), 0, 1);
				cal.set(Calendar.WEEK_OF_YEAR, i);        
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				// valorizza il primo giorno della settimana
				$xdtab=sdf.format(cal.getTime())+" - ";
				
				cal.add(Calendar.DATE, 6);  
				//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				// valorizza l'ultimo giorno della settimana
				$xdtab+=sdf.format(cal.getTime());
				
				// Settimana attuale
				if($xcdel.equals(currentWeek)) {
					$cmp1 = "selected ";
				}
				
				// valorizza la lista
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,"","",""));
				
				//*blank bariabili
				$xdtab = "";
				$cmp1 = "";
		  	}
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", dsfun);
		obj.put("selPeriod", selPeriod);
		obj.put("currentYear", currentYear);
		obj.put("currentWeek", currentWeek);
		obj.put("year", year);
		obj.put("id_departments", id_departments);
		
		obj.put("mn", mn);
		obj.put("weeks", wk);
		obj.put("dep", dep);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/control/control-ric", obj);
	}

}

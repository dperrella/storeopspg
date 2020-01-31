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
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Departments;
import pkg.beans.HR.Employee;
import pkg.beans.HR.Control;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ControlDayList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		SimpleDateFormat hour = new SimpleDateFormat("kk:mm:ss");
		hour.setTimeZone(TimeZone.getTimeZone("CET"));
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Control> cnt = new ArrayList<Control>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		// Pulisce le lista
		cnt.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String dsfun = "";
		
		String msg = "";
		int count = 0;
		
		// -----------------------
		// giorno
		String giorno = req.getParameter("day");
		Date giornoIso = dateIta.parse(giorno);
		String ggIso = dateIso.format(giornoIso);
		
		String year = ggIso.substring(0,4);
		
		Calendar cal = Calendar.getInstance(Locale.GERMAN);
		cal.setTime(dateIta.parse(giorno));
		
		
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		
		String table = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			dsfun = propLang.get("cnt_contrDett");
			
			sql   = "select day_ref, id_departments, d.description, c.id_employee, e.name, e.surname, " + 
					"type, xdtab, ingresso, inizio_pausa, fine_pausa, uscita, totale " + 
					"from control_anomaly c, hr_departments d, hr_employee e, ztables " + 
					"where d.id = c.id_departments and e.id = c.id_employee " + 
					"and day_ref = '"+ggIso+"' and xcdtb = 'TPT' and trim(xcdel) = trim(type) " + 
					"and d.id_customer = "+session.getAttribute("idcus")+" "+
					"order by id_departments, id_employee, xdtab ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String rott = "";
			
			String id_employee = "";
			String des_employee= "";
			String id_departments= "";
			String des_departments= "";
			String day_ref= "";
			String type= "";
			String des_type= "";
			String ingresso= "";
			String inizio_pausa= "";
			String fine_pausa= "";
			String uscita= "";
			String totale= "";
			
			String cmp1= "";
			String cmp2= "";
			String cmp3= "";
			String cmp4= "";
			
			String css = "";
			String cssBottom = "";
			String font = "";
			String bg1 = "";
			String bg2 = "";
			String bg3 = "";
			String bg4 = "";
			String bg5 = "";
			
			String rottEmp = "";
			String btn = "";
			
			while(rs.next()) {
				
				
				
				if(!rott.equals(rs.getString("id_departments"))) {
					// Scrive riga dipartimento
					table+= "<tr><td colspan=\"8\" class=\"bg-dark lter\"><b> >>> "+rs.getString("description")+" <<< </b></td></tr>"
							+ "<tr><td class=\"b-r\" style=\"width:30%\"><b>"+propLang.get("prof_dip") +"</b></td>"
							+ "<td class=\"b-r\" style=\"width:15%\"><b>"+propLang.get("cnt_tipo") +"</b></td>"
							+ "<td class=\"b-r\" style=\"width:10%\"><b>"+propLang.get("cnt_in") +"</b></td>"
							+ "<td class=\"b-r\" style=\"width:10%\"><b>"+propLang.get("cnt_pausaIni") +"</b></td>"
							+ "<td class=\"b-r\" style=\"width:10%\"><b>"+propLang.get("cnt_pausaFin") +"</b></td>"
							+ "<td class=\"b-r\" style=\"width:10%\"><b>"+propLang.get("cnt_out") +"</b></td>"
							+ "<td style=\"width:10%\"><b>"+propLang.get("cnt_totale") +"</b></td>"
							+ "<td style=\"width:5%\"></td></tr>";
				}
				
				id_employee = rs.getString("id_employee");
				//des_employee = rs.getString("name")+" "+rs.getString("surname");
				id_departments = rs.getString("id_departments");
				des_departments = rs.getString("description");
				day_ref = dateIta.format(rs.getDate("day_ref"));
				type = rs.getString("type");
				des_type = rs.getString("xdtab");
				des_type=des_type.replaceAll("\\.","");
				
				
				ingresso=""+rs.getString("ingresso");
				ingresso=Utility.noNull(ingresso);
				if(ingresso.length()>0) {
					if(!type.equals("DIF")) {
						ingresso = hour.format(rs.getTime("ingresso"));
					}
				}
				else {
					ingresso = "-";
				}
					
				inizio_pausa=""+rs.getString("inizio_pausa");
				inizio_pausa=Utility.noNull(inizio_pausa);
				if(inizio_pausa.length()>0) {
					if(!type.equals("DIF")) {
						inizio_pausa = hour.format(rs.getTime("inizio_pausa"));
					}
				}
				else {
					inizio_pausa = "-";
				}
				
				fine_pausa=""+rs.getString("fine_pausa");
				fine_pausa=Utility.noNull(fine_pausa);
				if(fine_pausa.length()>0) {
					if(!type.equals("DIF")) {
						fine_pausa = hour.format(rs.getTime("fine_pausa"));
					}
				}
				else {
					fine_pausa = "-";
				}
					
				uscita=""+rs.getString("Uscita");
				uscita=Utility.noNull(uscita);
				if(uscita.length()>0) {
					//if($type.equals("WRK")||$type.equals("PLN")) {
					if(!type.equals("DIF")) {
						uscita = hour.format(rs.getTime("uscita"));
					}
				}
				else {
					uscita = "-";
				}
				totale=""+rs.getString("totale");
				totale=Utility.noNull(totale);
				
				if(type.equals("PLN")) {
					css = "bg-light";
				}
				if(type.equals("WRK")) {
					css = "bg-light";
				}
				if(type.equals("DIF")) {
					css = "bg-light";
					cssBottom = "b-2x b-b b-dark";
					font = "style=\"font-weight: bold\" ";
					
					if(ingresso.length()>1) {
						if(ingresso.substring(0,1).equals("-")) {
							bg1 = "label bg-success";
						}
						else {
							if(ingresso.equals("00:00:00")) {
								bg1 = "";
								ingresso = "-";
							}
							else {
								bg1 = "label bg-danger ";
							}
							//bg1 = "label bg-danger ";
						}
					}
					
					if(inizio_pausa.length()>1) {
						if(inizio_pausa.substring(0,1).equals("-")) {
							bg2 = "label bg-danger";
						}
						else {
							if(inizio_pausa.equals("00:00:00")) {
								inizio_pausa = "-";
								bg2 = "";
							}
							else {
								bg2 = "label bg-success ";
							}
							//bg2 = "label bg-success ";
						}
					}
					
					if(fine_pausa.length()>1) {
						if(fine_pausa.substring(0,1).equals("-")) {
							bg3 = "label bg-success";
						}
						else {
							if(fine_pausa.equals("00:00:00")) {
								fine_pausa = "-";
								bg3 = "";
							}
							else {
								bg3 = "label bg-danger";
							}
							//bg3 = "label bg-danger ";
						}
					}
					
					if(uscita.length()>1) {
						if(uscita.substring(0,1).equals("-")) {
							bg4 = "label bg-danger";
						}
						else {
							if(uscita.equals("00:00:00")) {
								uscita = "-";
								bg4 = "";
							}
							else {
								bg4 = "label bg-success ";
							}
							//bg4 = "label bg-success ";
						}
					}
					
					if(totale.length()>1) {
						if(totale.substring(0,1).equals("-")) {
							bg5 = "label bg-danger";
						}
						else {
							if(totale.equals("00:00:00")) {
								
							}
							else {
								bg5 = "label bg-success ";
							}
						}
					}
					
				}
				
				if(!rottEmp.equals(rs.getString("id_employee"))) {
					des_employee = rs.getString("name")+" "+rs.getString("surname");
					btn = "<a href=\"../popup/addControlPlanification.jsp?id_departments="+id_departments+"&id_employee="+id_employee+"&day="+giorno+"&year="+year+"&week="+week+"\" "
							+ "class=\"btn btn-default btn-xs\" data-toggle=\"ajaxModal\"><i class=\"icon-settings\"></a>";
				}
				else {
					des_employee = "<span style=\"visibility:hidden\">"+rs.getString("name")+" "+rs.getString("surname")+"</span>";
					btn = "";
				}
				
				// table
				table+="<tr>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" "+font+">"+des_employee+"</td>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" "+font+">"+des_type+"</td>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg1+"\">"+ingresso+"</span></td>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg2+"\">"+inizio_pausa+"</span></td>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg3+"\">"+fine_pausa+"</span></td>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg4+"\">"+uscita+"</span></td>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" style=\"text-align:right;\"><span class=\""+bg5+"\"><b>"+totale+"</b></span></td>"+
					   "<td class=\""+type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg5+"\">"+btn+"</td></tr>";
				
				
				cssBottom = "";
				font = "";
				bg1 = "";
				bg2 = "";
				bg3 = "";
				bg4 = "";
				bg5 = "";
				
				
				
				
				
				
				rott = rs.getString("id_departments");
				rottEmp = rs.getString("id_employee");
				cmp1="";
			
			}
			rs.close();
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", dsfun);
		obj.put("day", giorno);
		obj.put("week", week);
		obj.put("year", year);
		obj.put("table", table);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/control/control-day-list", obj);
	}
	
	
	
	
	
}

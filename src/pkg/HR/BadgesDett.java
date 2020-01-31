package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class BadgesDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Employee> emp = new ArrayList<Employee>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		emp.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		String msgEli = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		
		try {
			
			// Campi database
			String $id="";
			String $number = "";
			String $nuovo = "";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$number = req.getParameter("number");
					$nuovo = ""+req.getParameter("nuovo");
					$nuovo = Utility.noNull($nuovo);
					
					try {
						
						if($number.length()==12) {
							$number = $number+Utility.getEAN13CheckDigit($number);
						}
						
						
						// controllo esistenza
						int count2 = 0;
						sql   = "select count(*) num from hr_badge where number = '"+$number+"' and id_customer = "+session.getAttribute("idcus")+" ";
						rs=st.executeQuery(sql);
						if(rs.next()) {
							count2 = rs.getInt("num");
						}
						rs.close();
						
						if(count2==0) {
						
							sql   = "insert into hr_badge (number, id_customer) values ('"+$number+"' , "+session.getAttribute("idcus")+") RETURNING id ";
							System.out.println(sql);
							
							rs = st.executeQuery(sql);
							if(rs.next()) {
								$id = rs.getString(1);
								System.out.println("ID: "+rs.getString(1));
							}
							else {
								$id = "-1";
								System.out.println("ID: -1");
							}
							rs.close();
							
							msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
							
							if($nuovo.length()==0) {
								tipoForm = "M";
							}
							else {
								$id = "";
								$number = "";
								$nuovo = "";
								tipoForm = "I";
							}
						}
						else {
							msg="<div class=\"alert alert-danger\"><strong>"+propLang.get("badge_exist")+"</strong></div>"+
									"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
							tipoForm = "I";
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						msg="<div class=\"alert alert-danger\"><strong>Duplicate Key</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						tipoForm = "I";
					}
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$number = req.getParameter("number");
					
					try {
						
						if($number.length()==12) {
							$number = $number+Utility.getEAN13CheckDigit($number);
						}
						
						sql  =  "update hr_badge "+
								"set number = '"+$number+"' "+
								"where id = '"+$id+"' ";
						st.executeUpdate(sql);
											
						msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
							"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					}
					catch(Exception e) {
						msg="<div class=\"alert alert-danger\"><strong>Duplicate Key</strong></div>"+
								"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					}
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from hr_badge where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					// *blank id
					$id = "";
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from hr_badge where id = '"+$id+"' ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$number = rs.getString("number");
				}
				rs.close();
				
				// ====================
				// Lista utenti con questo badge
				sql   = "select a.id, b.id_employ, c.name, c.surname, b.date_end "+
						"from hr_badge a "+
						"left join rel_emp_badge b on b.id_badge = a.id "+
						"left join hr_employee c on c.id = b.id_employ "+
						"where a.id = '"+$id+"' ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					emp.add(new Employee(rs.getString("id_employ"),rs.getString("name"),rs.getString("surname"),"","","","","","",
							"","","","","","","","","","","","","",""));
					
				}
				rs.close();
				
			}
			
			obj.put("id", $id);
			obj.put("number", $number);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Badge");
		obj.put("tipoForm", tipoForm);
		
		obj.put("emp", emp);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/employee/badges-dett", obj);
	}
	
}

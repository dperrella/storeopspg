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

public class ContractDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Employee> emp = new ArrayList<Employee>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		
		DateFormat dateItarev = new SimpleDateFormat("yyyy-MM-dd");
		Date daterev = new Date();
		String oggirev = dateItarev.format(daterev);
		
		// Pulisce la lista
		emp.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		String employees = "";
		
		String $emp="";
		String $dtda="";
		String $dtal="";
		String $more="";
		String $cont="";
		Date $dtal_ = null;
		
		try {
			
			// Campi database


			
			Date $dtda_app=null;
			Date $dtal_app=null;

			
			String $id_employee="";
			String $datada="";
			String $dataal="";
			String $montre="";
			
			String $id = "";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					System.out.println("Inserimento...");
					
					$emp=req.getParameter("id_employee");
					$dtda=req.getParameter("datada");
					$dtal=req.getParameter("dataal");
					$more=req.getParameter("monteore");
					
					$emp = Utility.noNull(""+$emp);
					$dtda = Utility.noNull(""+$dtda);
					$dtal = Utility.noNull(""+$dtal);
					$more = Utility.noNull(""+$more);

					
					
					sql   = "insert into hr_contracts "+
							"(id_employee, datada, dataal, monteore, "+
							"saldo_ore, data_saldo) values "+
							"('"+$emp.replaceAll("'", "''")+"', '"+$dtda+"', ";
					
							if (!"".equals($dtal)) {
								sql += "'"+$dtal+"', " ;
							}else {
								sql += "null, " ;
							}
							
							sql+=""+$more+",null,null) RETURNING id_contract ";
					System.out.println(sql);
					
					rs = st.executeQuery(sql);
					if(rs.next()) {
						$cont = rs.getString(1);
						System.out.println("ID: "+rs.getString(1));
					}
					else {
						$cont = "-1";
						System.out.println("ID: -1");
					}
					rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
				if(tipoForm.equals("M")) {
					
					System.out.println("Modifica...");
					
					$cont=req.getParameter("id_contract");
					$emp=req.getParameter("id_employee");
					$dtda=req.getParameter("datada");
					$dtal=req.getParameter("dataal");
					$more=req.getParameter("monteore");
					
					$emp = Utility.noNull(""+$emp);
					$dtda = Utility.noNull(""+$dtda);
					$dtal = Utility.noNull(""+$dtal);
					$more = Utility.noNull(""+$more);
					
					sql   = "update hr_contracts "+
							"set id_employee = '"+$emp.replaceAll("'", "''")+"', "+
							"datada = '"+$dtda+"', " ;
							
					if (!"".equals($dtal)) {
					sql +=  "dataal = '"+$dtal+"', ";
							}else {
					sql +=  "dataal = null, ";			
					}
							
					sql +=  "monteore = "+$more+" "+
							"where id_contract = '"+$cont+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				if(tipoForm.equals("E")) {
					
					System.out.println("Cancellazione...");
					$cont=req.getParameter("id_contract");
					sql   = "delete from hr_contracts where id_contract = '"+$cont+"' ";
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
				$cont=req.getParameter("id_contract");
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				// ===================
				// Dettaglio / Modifica / Elimina Dipartimento
				sql  =  "select * from hr_contracts "+
						"where id_contract = '"+$cont+"' ";
				System.out.println(sql);
				
				rs=st.executeQuery(sql);
				String name = "";
				String surname = "";
				
				if(rs.next()) {
					$id_employee=rs.getString("id_employee");
					$datada=rs.getString("datada");
					$dataal=rs.getString("dataal");
					$montre=rs.getString("monteore");
				}
				rs.close();
				

				
			}
			
			
			
			// ====================
			// Lista dipendenti del dipartimento
			employees = "<option value=''> -- Seleziona -- </option>";
			
			if(tipoForm.equals("I")) {
				sql  =  "select * from hr_employee where id_customer = "+session.getAttribute("idcus")+" "
						+ "and id not in(select id_employee from hr_contracts) order by surname";
			}
			else {
				sql  =  "select * from hr_employee where id_customer = "+session.getAttribute("idcus")+" "
						+ "order by surname";
			
			}
			System.out.println("hr_employee = " + sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				employees += "<option value='"+rs.getString("id")+"'>"+rs.getString("name")+" "+rs.getString("surname")+"</option>";
			}
			rs.close();
			
			
			st.close();
			conn.close();
			
			obj.put("Id_employee", $id_employee);
			obj.put("Datada", $datada);
			obj.put("Dataal", $dataal);
			obj.put("montre", $montre);
			obj.put("Employees", employees);
			obj.put("Contract", $cont);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Dipartimento");
		
		
		
		obj.put("emp", emp);
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		
		return new ModelAndView("/hr/contract/contract-dett", obj);
	}
	
}

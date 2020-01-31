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
import pkg.beans.Function;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelUserEmpDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		ReadProperties prop = new ReadProperties("connection.properties");
		String cdapp = prop.get("cdapp");
		
		List<Function> fun = new ArrayList<Function>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		fun.clear();
		
		Connection conn = null;
		Statement st = null;
		
		Connection connAdmin = null;
		Statement stAdmin = null;
		
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
			String $id_employee="";
			String $emp_description="";
			String $id_user="";
			String $user_description="";
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			connAdmin=Utility.getConnAdmin();
			stAdmin=connAdmin.createStatement();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$id_employee = req.getParameter("id_employee");
					$id_user = req.getParameter("id_user");
					
					sql   = "insert into rel_emp_user "+
							"(id_employee, id_user) "+
							"values "+
							"('"+$id_employee+"', '"+$id_user+"' ) RETURNING id ";
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
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$id_employee = req.getParameter("id_employee");
					$id_user = req.getParameter("id_user");
					
					sql  =  "update rel_emp_user "+
							"set id_employee = '"+$id_employee+"', "+
							"id_user = '"+$id_user+"' "+
							"where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from rel_emp_user where id = '"+$id+"' ";
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
				// Dettaglio / Modifica / Elimina 
				sql  =  "select a.id, a.id_employee, b.name, b.surname, a.id_user, c.nome, c.cogn "+
						"from rel_emp_user a, hr_employee b, zutenti c "+
						"where a.id = '"+$id+"' and b.id = a.id_employee and c.idusr = a.id_user "
						+ "and b.id_customer = "+session.getAttribute("idcus")+" ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$id_employee=rs.getString("id_employee");
					$emp_description=rs.getString("name")+" "+rs.getString("surname");
					$id_user = rs.getString("id_user");
					$user_description = rs.getString("nome")+" "+rs.getString("cogn");
					
				}
				rs.close();
				
				// ====================
				// Lista funzioni
				
				sql  =  "select a.cdfun, b.cdfun aut, l.descr "+
						"from zfunlang l, zfunzioni a "+
						"left join zutefunz b on a.cdfun = b.cdfun and b.idusr = '"+$id_user+"' "+
						"where l.cdfun = a.cdfun and l.lang = '"+(String)session.getAttribute("language")+"' "+
						"and cdapp = '"+cdapp+"' ";
						
				System.out.println(sql);
				rs=stAdmin.executeQuery(sql);
				
				String $cdfun = "";
				String $descr = "";
				String $aut = "";
				int nrRiga = 0;
				while(rs.next()) {
					
					nrRiga+=1;
					$cdfun = rs.getString("cdfun");
					$descr = rs.getString("descr");
					$aut = ""+rs.getString("aut");
					$aut = Utility.noNull($aut);
					
					if($aut.length()>0) {
						$aut = "checked";
					}
					
					fun.add(new Function($cdfun,$descr,"","","",$aut,""+nrRiga,"",""));
					
					$aut = "";
				}
				rs.close();
				
			}
			
			obj.put("id", $id);
			obj.put("id_employee", $id_employee);
			obj.put("emp_description", $emp_description);
			obj.put("id_user", $id_user);
			obj.put("user_description", $user_description);
			
			stAdmin.close();
			connAdmin.close();
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Relazione Utente / Dipendente");
		obj.put("tipoForm", tipoForm);
		
		obj.put("fun", fun);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/config/reluseremp-dett", obj);
	}
	
}
